package com.storm.safe.rock.auto.filter;

import com.storm.safe.rock.auto.entity.UiNode;

/**
 * 节点过滤器接口 (模块 04)
 */
public interface NodeFilter {
    boolean accept(UiNode node);
}
