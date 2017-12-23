# mango-test 
基于mango写的测试demo

Mango地址：http://mango.jfaster.org/

# 分库分表注意事项：
### 1.首先确定数量级别
比如一个B2C电商平台，主要有三个级别的数据
  1. 店铺数据，对应主键merchantNo
  2. 订单数据，对应主键orderNO
  
通过对数据进行预测：
  1. 店铺规模在1万家以内
  2. 一个店铺的订单总数据在千万条

基于上面的分析，我们认为系统未来要承受非常大的数据，以及并发需求。

### 2. 项目初期就确定好分库分表策略
我们面临一个问题，如果按照最大评估的量级实施分库分表策略，成本有点高
比如项目初期，一次性上8库，服务器成本高，开发维护测试成本也非常高。
但是如果你一开始不做好策略，以后就很难分了
比如所有的主键都用自增数据，效率杠杠的，但是以后真想分库分表就傻眼了。
因此建议是先设定分库分表策略，但是可以随着业务增长逐渐扩容

# 如何做？
### 1. 订单号生成
```
    public static String makeOrderNumber(String merchantNo) {
          String orderNo=
                  "P" //系统号
                  +"1"//版本号
                  +ShardUtil.getShardKeyByID(merchantNo,MangoContant.ORDER_SPLIT_ORDER_COUNT,MangoContant.ORDER_SPLIT_TABLE_COUNT,MangoContant.DB_SHARD_LENGTH,MangoContant.TABLE_SAHRD_LENGTH)//分库分表Shard值
                  +sdf.format(new Date())//时间戳
                  + RandomCodeUtil.getRandomNum(MangoContant.ORDER_NO_RANDOWM_LENGTH) ;//随机数，可以用数据库或者其他方式生成
          return orderNo;
      }
```
我们以订单数据为例，订单号不再使用数字自增ID，而是生成一个字符串，其中包含了很多信息，其中
```
ShardUtil.getShardKeyByID()
```
以上个量级的数据字段『店铺数据』merchantNo为输入生成的
getShardKeyByID时要固定未来最大的分库分表的规模
代码中我们设定了未来8库10表的规模
切记，这个是不能改动的，想想为啥呢？

### 2.分库分表的路由

由步骤1的方法我们生成了一个订单号 P10604201712231334054319
对应的shardId九十0604. 这个对于所有merchantNo都是一致的
这样的好处是确保一个商家的订单都在同个库，同个表中。

下面是通过这个shardId，我们要落实到具体的数据库和表中
```
public class MangoTestShardingStrategy implements ShardingStrategy<String, String> {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(MangoTestShardingStrategy.class);

    @Override
    public String getDataSourceFactoryName(String shardID) {

        //String dbname="dsf0";//返回固定库
        String dbname="dsf"+String.valueOf(Integer.valueOf(shardID.substring(0,2))%2);//2个库
        logger.info("-----------db:"+dbname);
        return dbname;
    }

    @Override
    public String getTargetTable(String table, String shardID) {
        //String tablename=table+"_0";//放到一个表里
        String tablename=table+"_"+String.valueOf(Integer.valueOf(shardID.substring(2,4))%10);//10个表
        logger.info("-----------table:"+tablename);
        return tablename;
    }

}
```
上面的代码帮我们实现了这一功能。
比如项目初期，我们只有一个库，完全可以这样
```
public String getDataSourceFactoryName(String shardID) {
        String dbname="dsf0";
        return dbname;
    }
```
//等我们有了8个库，可以这样
```
public String getDataSourceFactoryName(String shardID) {
        String dbname="dsf"+String.valueOf(Integer.valueOf(shardID.substring(0,2))%8);//2个库
        return dbname;
    }
```

### 3.如何扩容
我们以库扩容为例（不考虑原有从库）
我们当前只有1库10个表，要扩展成2个库10个表
 1. 当前的A库增加一个从库B库（DBA操作）
 2. 同步成功后，A B 改为双写（DBA操作）
 3. 业务双写
在没有新业务数据写入前 A B库的数据是完全一致的
这样的好处是由dba负责数据的迁移，业务不需要做逻辑导数据
坏处当然也是有的，就是数据冗余。

### 4.其他问题
我们目前的策略都是确保一个商户/店铺的订单数据都在一个表里。
这样在商户层面上可以正常各种查询。
但是如果我们按照系统层面上查询，比如查询系统里昨天所有的数据，就要跨库和跨表了。
这有很多实现方法，比如mysql的sharding。
或者干脆做一个大表或者nosql的去存储一个快照。

还有一个问题，如果一个商家的订单数上亿了，那我们就不能用一张表存了，这就涉及到我们最初考虑的问题
从一开始就要确定我们系统的最大容量。