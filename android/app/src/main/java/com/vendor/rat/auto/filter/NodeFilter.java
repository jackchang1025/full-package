package com.vendor.rat.auto.filter;

import com.vendor.rat.auto.entity.UiNode;

/**
 * 节点过滤器接口 (模块 04)
 */
public interface NodeFilter {
    boolean accept(UiNode node);
}
