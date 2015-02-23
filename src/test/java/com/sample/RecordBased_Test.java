package com.sample;

import com.psddev.dari.db.Query;
import com.psddev.dari.util.ObjectUtils;
import org.apache.commons.lang.StringUtils;
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
public class RecordBased_Test {
    @Rule public TestName testName = new TestName();

    @BeforeClass
    public static void beforeClass() {
        System.out.println("Running tests from class [" + RecordBased_Test.class.getName() + "]");
    }

    @AfterClass
    public static void afterClass() {}

    @Before
    public void before() {
        System.out.println("Running test [" + testName.getMethodName() + "]");
    }

    @After
    public void after() {}

    /*
     * protected String generateDisplayTitle()
     */
    @Test
    public void generateDisplayTitle_source() {
        RecordBased object = new RecordBased();
        object.setSourceTitle("generateDisplayTitle_source");

        assertEquals("generateDisplayTitle_source", object.generateDisplayTitle());
    }

    @Test
    public void generateDisplayTitle_editorial() {
        RecordBased object = new RecordBased();
        object.setSourceTitle("generateDisplayTitle_editorial source");
        object.setEditorialTitle("generateDisplayTitle_editorial editorial");

        assertEquals("generateDisplayTitle_editorial editorial", object.generateDisplayTitle());
    }

    @Test
    public void generateDisplayTitle_none() {
        RecordBased object = new RecordBased();

        assertEquals(null, object.generateDisplayTitle());
    }

    /*
     * beforeSave - sets displayTitle
     */
    @Test
    public void beforeSave_title_source() {
        RecordBased original = new RecordBased();
        original.setSourceTitle("beforeSave_title_source");
        original.save();

        RecordBased queried = Query.from(RecordBased.class).where("displayTitle = ?", "beforeSave_title_source").first();
        assertTrue(ObjectUtils.equals(original, queried));
    }

    @Test
    public void beforeSave_title_editorial() {
        RecordBased original = new RecordBased();
        original.setSourceTitle("source beforeSave_title_editorial");
        original.setEditorialTitle("editorial beforeSave_title_editorial");
        original.save();

        RecordBased queried = Query.from(RecordBased.class).where("displayTitle = ?", "editorial beforeSave_title_editorial").first();
        assertTrue(ObjectUtils.equals(original, queried));
    }

    @Test
    public void beforeSave_title_none() {
        RecordBased original = new RecordBased();
        original.save();

        RecordBased queried = Query.from(RecordBased.class).where("displayTitle is missing").first();
        assertTrue(ObjectUtils.equals(original, queried));
    }

}
