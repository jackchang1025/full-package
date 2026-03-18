package com.vendor.rat.auto.filter;

import com.vendor.rat.auto.entity.UiNode;

/**
 * UI 节点属性枚举 + 属性提取
 * // ADAPT: 反混淆 j.e → UiNodeProperty, 合并 StringPropertyGetter/IntPropertyGetter 实现
 * vendor 中 j.e 是一个同时实现 t.a(int getter) 和 t.b(string getter) 的类,
 * 通过构造参数 propertyId 区分不同属性
 */
public enum UiNodeProperty implements StringPropertyGetter, IntPropertyGetter {
    CLASS_NAME(5),
    CONTENT_DESC(10),
    HINT_TEXT(12),
    RESOURCE_ID(13),
    PACKAGE_NAME(15),
    PANEL_TITLE(16),
    ROLE_DESC(18),
    STATE_DESC(22),
    TEXT(23),
    TOOLTIP(24),
    UNIQUE_ID(25);

    private final int propertyId;

    UiNodeProperty(int propertyId) {
        this.propertyId = propertyId;
    }

    public int getPropertyId() { return propertyId; }

    @Override
    public String get(UiNode node) {
        switch (propertyId) {
            case 5:  return node.getClassName();
            case 10: return node.getContentDescription();
            case 13: return node.getViewIdResourceName();
            case 15: return node.getPackageName();
            case 23: return node.getText();
            // ADAPT: 以下属性 UiNode 暂未暴露, 返回 ""
            case 12: return node.getHintText();
            case 16: return node.getPanelTitle();
            case 18: return node.getRoleDescription();
            case 22: return node.getStateDescription();
            case 24: return node.getTooltipText();
            case 25: return node.getUniqueId();
            default: return "";
        }
    }

    @Override
    public int getInt(UiNode node) { return 0; }
}
