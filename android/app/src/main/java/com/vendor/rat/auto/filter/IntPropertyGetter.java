package com.vendor.rat.auto.filter;

import com.vendor.rat.auto.entity.UiNode;

/**
 * int 属性提取器
 * // ADAPT: 反混淆 t.a → IntPropertyGetter
 */
public interface IntPropertyGetter {
    int getInt(UiNode node);
}
