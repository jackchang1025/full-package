package com.vendor.rat.auto.filter;

import com.vendor.rat.auto.entity.UiNode;

/**
 * String 属性提取器
 * // ADAPT: 反混淆 t.b → StringPropertyGetter
 */
public interface StringPropertyGetter {
    String get(UiNode node);
}
