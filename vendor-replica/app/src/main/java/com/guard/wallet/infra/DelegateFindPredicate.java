package com.guard.wallet.infra;

import com.guard.wallet.delegate.AccessibilityDelegate;
import com.guard.wallet.service.AccessibilityDelegateManager;
import java.util.LinkedList;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * 委托查找谓词 — 根据匹配模式在委托队列中查找或过滤 Delegate。
 *
 * 支持两种模式:
 *   mode 0 → 按 delegateId (AccessibilityDelegate.c) 精确匹配并移除委托
 *   mode 1 (default) → 按 listenWindowUniqueId (package:class 格式) 模糊匹配,
 *                       支持 "NULL" 通配符, SoftInputWindow 特殊处理
 *
 * vendor 原始路径: a0/b.java
 */
public final class DelegateFindPredicate implements Predicate {

    public final /* synthetic */ int mode;
    public final /* synthetic */ String matchKey;
    public final /* synthetic */ AccessibilityDelegateManager manager;

    public /* synthetic */ DelegateFindPredicate(AccessibilityDelegateManager accessibilityDelegateManager, String str, int i2) {
        this.mode = i2;
        this.manager = accessibilityDelegateManager;
        this.matchKey = str;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i2 = this.mode;
        String str = this.matchKey;
        switch (i2) {
            case 0:
                AccessibilityDelegate eVar = (AccessibilityDelegate) obj;
                if (eVar == null || !Objects.equals(eVar.c, str)) {
                    return false;
                }
                eVar.d();
                this.manager.C(eVar.getClass().getName(), new LinkedList(eVar.d));
                return true;
            default:
                String[] split = str.split(":");
                String[] split2 = ((String) obj).split(":");
                if (split.length >= 2 && split2.length >= 2) {
                    boolean z2 = "NULL".equals(split[0]) || "NULL".equals(split2[0]) || Objects.equals(split[0], split2[0]);
                    boolean z3 = "NULL".equals(split[1]) || "NULL".equals(split2[1]) || Objects.equals(split[1], split2[1]);
                    if ("android.inputmethodservice.SoftInputWindow".equals(split[1])) {
                        z3 = true;
                    }
                    if (z2 && z3) {
                        return true;
                    }
                }
                return false;
        }
    }
}
