package com.guard.wallet.http;

import com.koushikdutta.async.util.TaggedList;
import java.util.List;

/**
 * QueryParameterMap 子类 — createValueList() 返回 TaggedList（扩展 ArrayList）。
 * 用于 HTTP 头部构建器（http/h.java mode=4）支持带标签的值列表。
 * 源自 vendor: i0/c.java
 */
public final class TaggedQueryMap extends QueryParameterMap {
    @Override
    public List createValueList() {
        return new TaggedList();
    }
}
