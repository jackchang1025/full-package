package com.storm.safe.rock.auto.filter;

import com.storm.safe.rock.auto.entity.UiNode;

/**
 * Boolean 属性提取器
 * // ADAPT: 反混淆 b0.a → BooleanPropertyGetter
 */
public interface BooleanPropertyGetter {
    Boolean get(UiNode node);
}
