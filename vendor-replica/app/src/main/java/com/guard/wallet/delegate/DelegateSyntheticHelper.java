package com.guard.wallet.delegate;

import com.guard.wallet.condition.StringCondition;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.req.ListenWindow;
import java.util.HashSet;

/**
 * vendor o/b — 委托合成辅助类 (DelegateSyntheticHelper)。
 *
 * ListenWindow 事件类型和 CombineFilter 构造的业务逻辑辅助。
 * 注意: 原始 CFR 中的加密/BouncyCastle/Conscrypt 方法（A-D, s, t, u, w-z 等）
 * 属于第三方库合成，不做复刻 — 仅包含被其他 o/ 类实际引用的方法。
 */
public abstract class DelegateSyntheticHelper {

    /**
     * vendor q(eventType, eventTypesSet, listenWindow)
     * — 添加 eventType 到 HashSet，返回 listenWindow 的 eventTypes。
     */
    public static HashSet q(int var0, HashSet var1, ListenWindow var2) {
        var1.add(var0);
        return var2.getEventTypes();
    }

    /**
     * vendor r(listenWindow)
     * — 初始化 listenWindow 的 eventTypes 为新 HashSet，返回它。
     */
    public static HashSet r(ListenWindow var0) {
        var0.setEventTypes(new HashSet<>());
        return var0.getEventTypes();
    }

    /**
     * vendor b(CombineFilter, StringCondition, property)
     * — 添加已有条件到 filter，创建带指定 property 的新条件。
     */
    public static StringCondition b(CombineFilter var0, StringCondition var1, String var2) {
        var0.getStringConditions().add(var1);
        StringCondition var3 = new StringCondition();
        var3.setProperty(var2);
        return var3;
    }

    /**
     * vendor v(equalsKey, condition, filter, conditionToAdd)
     * — 从配置设置 condition 的 equals 值，添加 conditionToAdd 到 filter。
     */
    public static void v(String var0, StringCondition var1, CombineFilter var2, StringCondition var3) {
        var1.setEquals(com.guard.wallet.utils.LocateValuesUtils.getValue(var0));
        var2.getStringConditions().add(var3);
    }
}
