package com.sample;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by rhseeger on 2/23/15.
 */
public class Integrated_IT {
    @Rule public TestName testName = new TestName();

    @BeforeClass
    public static void beforeClass() {
        System.out.println("Running tests from class [" + Integrated_IT.class.getName() + "]");
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
    public void title_google() {
        Integrated result = Integrated.Static.importPageByUrl("http://www.google.com");

        assertTrue(result != null);
        assertEquals("Google", result.getTitle());
    }

}
