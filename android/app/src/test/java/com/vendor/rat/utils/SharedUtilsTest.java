package com.vendor.rat.utils;

import static org.junit.Assert.*;

import android.app.Application;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 30, application = Application.class)
public class SharedUtilsTest {

    @Before
    public void setUp() {
        SharedUtils.init(RuntimeEnvironment.getApplication());
    }

    @Test
    public void testSaveAndGetString() {
        SharedUtils.save("test_value", "test_key");
        assertEquals("test_value", SharedUtils.getString("test_key"));
    }

    @Test
    public void testSaveAndGetBoolean() {
        SharedUtils.save(true, "bool_key");
        assertTrue(SharedUtils.getBoolean("bool_key"));
    }

    @Test
    public void testSaveAndGetInt() {
        SharedUtils.save(42, "int_key");
        assertEquals(42, SharedUtils.getInt("int_key"));
    }

    @Test
    public void testSaveAndGetLong() {
        SharedUtils.save(123456789L, "long_key");
        assertEquals(123456789L, SharedUtils.getLong("long_key"));
    }

    @Test
    public void testSaveAndGetFloat() {
        SharedUtils.save(3.14f, "float_key");
        assertEquals(3.14f, SharedUtils.getFloat("float_key"), 0.001f);
    }

    @Test
    public void testGetDefaultWhenKeyNotExists() {
        assertNull(SharedUtils.getString("nonexistent"));
        assertFalse(SharedUtils.getBoolean("nonexistent"));
        assertEquals(0, SharedUtils.getInt("nonexistent"));
        assertEquals(0L, SharedUtils.getLong("nonexistent"));
        assertEquals(0f, SharedUtils.getFloat("nonexistent"), 0.001f);
    }

    @Test
    public void testRemove() {
        SharedUtils.save("value", "remove_key");
        assertEquals("value", SharedUtils.getString("remove_key"));
        SharedUtils.remove("remove_key");
        assertNull(SharedUtils.getString("remove_key"));
    }

    @Test
    public void testInitWithNullContextDoesNotCrash() {
        // Should not throw
        SharedUtils.init(null);
    }
}
