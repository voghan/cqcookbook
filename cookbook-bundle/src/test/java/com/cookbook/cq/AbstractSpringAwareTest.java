package com.cookbook.cq;

import junit.framework.TestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public abstract class AbstractSpringAwareTest extends TestCase {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractSpringAwareTest.class);
    private static ApplicationContext context;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        try {
            if (context == null) {
                context = new ClassPathXmlApplicationContext("local-app-context.xml");
            }

        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            fail(e.getMessage());
        }
    }

    protected ApplicationContext getApplicationContext() {
        return context;
    }

}

