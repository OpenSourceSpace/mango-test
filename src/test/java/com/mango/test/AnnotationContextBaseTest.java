
package com.mango.test;
import org.junit.Before;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

@DirtiesContext
@ContextConfiguration({"classpath*:/spring/applicationContext-test.xml"})
public class AnnotationContextBaseTest extends AbstractJUnit4SpringContextTests {
    @Before
    public void setUp() {
    }


}