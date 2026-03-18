package com.vendor.rat.control.plug;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * 按属性类型分类 ListenPropResponse (text/id/desc)
 * vendor: com.guard.wallet.plug.b
 */
public final class PropCategoryPredicate implements Predicate<Object> {

    private final int mode;
    private final List textList;
    private final List idList;
    private final List descList;
    private final Object target;

    public PropCategoryPredicate(Object target, LinkedList textList,
                                  LinkedList idList, LinkedList descList, int mode) {
        this.mode = mode;
        this.target = target;
        this.textList = textList;
        this.idList = idList;
        this.descList = descList;
    }

    @SuppressWarnings("unchecked")
    public void categorize(Object listenPropResponse) {
        // ADAPT: vendor 使用 ListenPropResponse.getProp() 分类
        // prop == "text" → textList
        // prop == "id"   → idList
        // prop == "desc" → descList
        // TODO: VENDOR_VERIFY - 需要 ListenPropResponse VO
    }

    @Override
    public boolean test(Object obj) {
        categorize(obj);
        return true;
    }
}
