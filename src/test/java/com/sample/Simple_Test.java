package com.sample;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import static org.junit.Assert.assertEquals;

/**
 * Created by rhseeger on 2/23/15.
 */
public class Simple_Test {
    @Rule public TestName testName = new TestName();

    @BeforeClass
    public static void beforeClass() {
        System.out.println("Running tests from class [" + Simple_Test.class.getName() + "]");
    }

    @AfterClass
    public static void afterClass() {}

    @Before
    public void before() {
        System.out.println("Running test [" + testName.getMethodName() + "]");
    }

    @After
    public void after() {}

    @Test
    public void max_first() {
        assertEquals(2, Simple.max(2,1));
    }

    @Test
    public void max_second() {
        assertEquals(3, Simple.max(2,3));
    }

    @Test
    public void max_same() {
        assertEquals(4, Simple.max(4,4));
    }

}
