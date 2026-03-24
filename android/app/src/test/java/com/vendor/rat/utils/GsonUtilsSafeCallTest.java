package com.vendor.rat.utils;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;

import java.lang.reflect.Type;
import java.util.List;

import static org.junit.Assert.*;

/**
 * GsonUtils 使用 SafeCall 后的行为不变性测试
 *
 * 纯 JVM 测试，无需 Android 框架
 */
public class GsonUtilsSafeCallTest {

    // ============ fromJson (TypeToken) ============

    @Test
    public void fromJson_validJson_returnsObject() {
        Type type = new TypeToken<List<String>>() {}.getType();
        Object result = GsonUtils.fromJson("[\"a\",\"b\"]", type);
        assertNotNull(result);
        @SuppressWarnings("unchecked")
        List<String> list = (List<String>) result;
        assertEquals(2, list.size());
        assertEquals("a", list.get(0));
    }

    @Test
    public void fromJson_invalidJson_returnsNull() {
        Type type = new TypeToken<List<String>>() {}.getType();
        Object result = GsonUtils.fromJson("{{{invalid", type);
        assertNull(result);
    }

    @Test
    public void fromJson_nullInput_returnsNull() {
        Type type = new TypeToken<List<String>>() {}.getType();
        assertNull(GsonUtils.fromJson(null, type));
    }

    @Test
    public void fromJson_emptyInput_returnsNull() {
        Type type = new TypeToken<List<String>>() {}.getType();
        assertNull(GsonUtils.fromJson("", type));
    }

    // ============ fromJsonString (Class) ============

    @Test
    public void fromJsonString_validJson_returnsTyped() {
        SimpleBean bean = GsonUtils.fromJsonString("{\"name\":\"test\",\"value\":42}", SimpleBean.class);
        assertNotNull(bean);
        assertEquals("test", bean.name);
        assertEquals(42, bean.value);
    }

    @Test
    public void fromJsonString_invalidJson_returnsNull() {
        SimpleBean result = GsonUtils.fromJsonString("not json at all", SimpleBean.class);
        assertNull(result);
    }

    // ============ parseJsonObject ============

    @Test
    public void parseJsonObject_validJson_returnsJsonObject() {
        JsonObject obj = GsonUtils.parseJsonObject("{\"key\":\"value\"}");
        assertNotNull(obj);
        assertEquals("value", obj.get("key").getAsString());
    }

    @Test
    public void parseJsonObject_invalidJson_returnsNull() {
        assertNull(GsonUtils.parseJsonObject("{broken"));
    }

    // ============ toJson ============

    @Test
    public void toJson_validObject_returnsString() {
        SimpleBean bean = new SimpleBean();
        bean.name = "hello";
        bean.value = 7;
        String json = GsonUtils.toJson(bean);
        assertNotNull(json);
        assertTrue(json.contains("\"name\":\"hello\""));
        assertTrue(json.contains("\"value\":7"));
    }

    @Test
    public void toJson_null_returnsEmptyObject() {
        assertEquals("{}", GsonUtils.toJson(null));
    }

    // ============ 辅助类 ============

    @SuppressWarnings("unused")
    static class SimpleBean {
        String name;
        int value;
    }
}
