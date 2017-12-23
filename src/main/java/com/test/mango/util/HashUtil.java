package com.test.mango.util;

import java.math.BigInteger;

public class HashUtil {

    private static final BigInteger INIT32  = new BigInteger("811c9dc5", 16);
    private static final BigInteger PRIME32 = new BigInteger("01000193", 16);
    private static final BigInteger MOD32   = new BigInteger("2").pow(32);

    public static int fnv1_31(String str) {
        BigInteger bi32 = fnv1_32(str.getBytes());
        return bi32.intValue() & 0x7fffffff;
    }

    private static BigInteger fnv1_32(byte[] data) {
        BigInteger hash = INIT32;

        for (byte b : data) {
            hash = hash.multiply(PRIME32).mod(MOD32);
            hash = hash.xor(BigInteger.valueOf((int) b & 0xff));
        }

        return hash;
    }

}
