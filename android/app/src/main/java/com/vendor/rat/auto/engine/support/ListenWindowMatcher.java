package com.vendor.rat.auto.engine.support;

import android.util.Log;

import com.vendor.rat.auto.condition.CombineFilter;
import com.vendor.rat.auto.entity.UiNode;
import com.vendor.rat.model.req.ListenWindow;

import java.util.List;

/**
 * ListenWindow 匹配 — 从 AutoEngine 提取
 * 对齐 vendor o/e.java p() + q()
 */
public class ListenWindowMatcher {

    private static final String TAG = "ListenWindowMatcher";

    /**
     * 节点提供者接口 — 解耦 AutoEngine 字段依赖
     */
    public interface NodeProvider {
        UiNode getCachedRoot();
        UiNode getRootNode();
        String getCurrentPackage();
        String getCurrentClassName();
    }

    private final NodeProvider nodeProvider;

    public ListenWindowMatcher(NodeProvider nodeProvider) {
        this.nodeProvider = nodeProvider;
    }

    /**
     * 单个 ListenWindow 匹配检查 (matchs + dismiss)
     * 对应 vendor: o/e.java p() 行 695-719
     *
     * @param lw   目标 ListenWindow (含 matchs/dismiss CombineFilter 列表)
     * @param root 当前界面根节点
     * @return true=匹配成功 (matchs 全通过 + dismiss 全不通过)
     */
    public boolean matchListenWindow(ListenWindow lw, UiNode root) {
        try {
            // vendor e.java:699-704: matchs 全部通过 (AND)
            List<CombineFilter> matchs = lw.getMatchs();
            if (matchs != null && !matchs.isEmpty() && root != null) {
                for (CombineFilter filter : matchs) {
                    if (root.findOneByCombine(filter) == null) {
                        return false;
                    }
                }
            }

            // vendor e.java:709-713: dismiss 全部不通过 (NOT ANY)
            List<CombineFilter> dismiss = lw.getDismiss();
            if (dismiss != null && !dismiss.isEmpty() && root != null) {
                for (CombineFilter filter : dismiss) {
                    if (root.findOneByCombine(filter) != null) {
                        return false;
                    }
                }
            }

            return true;
        } catch (Exception e) {
            Log.e(TAG, "matchListenWindow error", e);
            return true; // vendor: 异常时默认允许
        }
    }

    /**
     * 批量 ListenWindow 匹配
     * 对应 vendor: o/e.java q() 行 727-807
     *
     * 逻辑: 遍历列表, 用 currentPackage/currentClassName 比较 pkg/cls,
     *        匹配后检查 matchs/dismiss, 任一匹配即返回 true
     */
    public boolean matchListenWindows(List<ListenWindow> windows) {
        if (windows == null || windows.isEmpty()) return false;
        try {
            // vendor e.java:735: 刷新根节点
            UiNode root = nodeProvider.getCachedRoot();
            if (root != null) root.refresh();

            String currentPackage = nodeProvider.getCurrentPackage();
            String currentClassName = nodeProvider.getCurrentClassName();

            for (ListenWindow lw : windows) {
                // vendor e.java:740-745: 比较 packageName
                String lwPkg = lw.getPackageName();
                String lwCls = lw.getClassName();

                if (lwPkg != null && !lwPkg.equals(currentPackage)) {
                    continue;
                }

                // vendor e.java:746-750: 比较 className (null = 任意)
                if (lwCls != null && !lwCls.isEmpty() && !lwCls.equals(currentClassName)) {
                    continue;
                }

                // vendor e.java:752-770: 检查 matchs + dismiss
                if (matchListenWindow(lw, root != null ? root : nodeProvider.getRootNode())) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            Log.e(TAG, "matchListenWindows error", e);
            return false;
        }
    }
}
