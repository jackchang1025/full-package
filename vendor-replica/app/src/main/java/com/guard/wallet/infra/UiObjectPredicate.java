package com.guard.wallet.infra;

/**
 * UiObject 布尔属性访问接口。
 * 用于判断 UiObject 是否满足某个布尔属性条件。
 *
 * vendor 原始路径: b0/a.java
 */
public interface UiObjectPredicate {
    boolean test(Object uiObject);

    /** Get boolean property from UiObject */
    default boolean test(com.guard.wallet.entity.UiObject uiObject) {
        return test((Object) uiObject);
    }
}
