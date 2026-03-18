package com.vendor.rat.auto.filter;

import com.vendor.rat.auto.entity.UiNode;

/**
 * Boolean 属性提取器
 * // ADAPT: 反混淆 b0.a → BooleanPropertyGetter
 */
public interface BooleanPropertyGetter {
    Boolean get(UiNode node);
}
