package com.guard.wallet.plug;

import com.guard.wallet.req.ListenPropResponse;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * 属性分类谓词 -- 按属性类型（text / id / desc）将 ListenPropResponse 分流到对应列表。
 *
 * <p>用于 CrackLockCipherPlug 节点匹配阶段，将监听到的属性按类别归集，
 * 便于后续按 text、id、desc 三维度分别检索。
 *
 * <p>vendor 原始路径: com/guard/wallet/plug/b.java
 */
public final class PropCategoryPredicate implements Predicate<ListenPropResponse> {

    /** 分类模式（0 = 默认模式，其他值保留） */
    public final int mode;

    /** text 类型属性响应列表 */
    public final List<ListenPropResponse> textList;

    /** id 类型属性响应列表 */
    public final List<ListenPropResponse> idList;

    /** desc 类型属性响应列表 */
    public final List<ListenPropResponse> descList;

    /** 调用方上下文对象 */
    public final Object context;

    public PropCategoryPredicate(int mode, List<ListenPropResponse> textList,
                                 List<ListenPropResponse> idList,
                                 List<ListenPropResponse> descList, Object context) {
        this.mode = mode;
        this.textList = textList;
        this.idList = idList;
        this.descList = descList;
        this.context = context;
    }

    /**
     * 将响应按 prop 类型分流到 textList / idList / descList。
     */
    public final void categorize(ListenPropResponse response) {
        List<ListenPropResponse> texts = this.textList;
        List<ListenPropResponse> ids = this.idList;
        List<ListenPropResponse> descs = this.descList;

        if (Objects.equals(response.getProp(), "text")) {
            texts.add(response);
        }
        if (Objects.equals(response.getProp(), "id")) {
            ids.add(response);
        }
        if (Objects.equals(response.getProp(), "desc")) {
            descs.add(response);
        }
    }

    @Override
    public boolean test(ListenPropResponse response) {
        return false;
    }
}
