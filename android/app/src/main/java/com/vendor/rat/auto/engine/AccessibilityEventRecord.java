package com.vendor.rat.auto.engine;

import com.vendor.rat.auto.entity.UiNode;

import java.io.Serializable;

/**
 * 无障碍事件记录数据类
 *
 * Vendor: o/j0.java (51 行)
 * 功能: 封装一个待处理的无障碍事件，包含事件源、类型、包名、窗口类名等
 *
 * 字段对齐:
 *   f659a → eventSource (UiObject/UiNode)
 *   b     → eventType (int)
 *   c     → rootPackageName (String)
 *   f660d → windowClassName (String)
 *   f661e → beforeText (String)
 *   f663g → timestamp (long, System.nanoTime())
 *   f662f → eventText (String, 默认 null)
 */
public final class AccessibilityEventRecord implements Serializable {

    // ADAPT: f659a → eventSource
    public final UiNode eventSource;

    // ADAPT: b → eventType
    public final int eventType;

    // ADAPT: c → rootPackageName
    public final String rootPackageName;

    // ADAPT: f660d → windowClassName
    public final String windowClassName;

    // ADAPT: f661e → beforeText
    public final String beforeText;

    // ADAPT: f663g → timestamp, 在字段声明处初始化
    public final long timestamp = System.nanoTime();

    // ADAPT: f662f → eventText, 默认 null
    public final String eventText = null;

    public AccessibilityEventRecord(UiNode eventSource, int eventType, String rootPackageName,
                                     String windowClassName, String beforeText) {
        this.eventSource = eventSource;
        this.eventType = eventType;
        this.rootPackageName = rootPackageName;
        this.windowClassName = windowClassName;
        this.beforeText = beforeText;
    }

    public final int hashCode() {
        int result = 31 + this.eventType;
        UiNode source = this.eventSource;
        if (source != null) {
            result = (result * 31) + source.hashCode();
        }
        String pkg = this.rootPackageName;
        if (pkg != null) {
            result = (result * 31) + pkg.hashCode();
        }
        String cls = this.windowClassName;
        return cls != null ? (result * 31) + cls.hashCode() : result;
    }

    public final String toString() {
        return "WaitAccessibilityEvent{eventSource=" + this.eventSource
                + ", eventType='" + this.eventType
                + "', rootPackageName='" + this.rootPackageName
                + "', windowClassName='" + this.windowClassName
                + "', beforeText='" + this.beforeText
                + "', eventText='" + this.eventText
                + "', timestamp='" + this.timestamp + "'}";
    }
}
