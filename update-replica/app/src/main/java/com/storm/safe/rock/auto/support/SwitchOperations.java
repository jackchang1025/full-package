package com.storm.safe.rock.auto.support;

import android.util.Log;

import com.storm.safe.rock.auto.condition.CombineFilter;
import com.storm.safe.rock.auto.condition.StringCondition;
import com.storm.safe.rock.auto.entity.CheckedResult;
import com.storm.safe.rock.auto.entity.UiNode;
import com.storm.safe.rock.auto.filter.NodeFilter;


/**
 * Switch/CheckBox 操作 — 从 AutoEngine 提取
 * 对齐 vendor o/c.java P()/O()/R()/S() 方法
 */
public class SwitchOperations {

    private static final String TAG = "SwitchOperations";

    /** 坐标点击委托，由外部注入（如 AccessibilityService.dispatchGesture） */
    public interface TapAction {
        boolean tap(float x, float y);
    }
    public static TapAction tapAction = null;

    private final Runnable activateRootAction;

    public SwitchOperations(Runnable activateRootAction) {
        this.activateRootAction = activateRootAction;
    }

    /**
     * CompoundButton 查找+点击+验证
     * 对应 vendor: o/c.java P() 行 223-309
     *
     * 逻辑:
     *   1. 构建 className=CompoundButton 过滤器
     *   2. 从 target 向上遍历 parent (最多 2 层) 查找
     *   3. 如果 checked=false: click() -> T0(1)+refresh 重试最多 5 次
     *   4. 如果仍 unchecked: findParentUtilCombine(clickableFilter) -> click
     *   5. 返回 CheckedResult
     */
    public static CheckedResult compoundButtonClick(UiNode target) {
        CheckedResult result = new CheckedResult();
        try {
            // vendor: className == android.widget.CompoundButton
            NodeFilter compoundButtonFilter = StringCondition
                    .className("android.widget.CompoundButton");

            // 从 target 向上遍历 parent (最多 2 层)
            UiNode node = null;
            UiNode current = target;
            int depth = 0;
            while (current != null && node == null && depth <= 2) {
                node = current.findOneByCombine(compoundButtonFilter);
                if (node == null) {
                    current = current.parent();
                }
                depth++;
            }

            if (node == null) return result;

            boolean checked = node.checked();
            int retries = 5;

            if (!checked) {
                // 先尝试直接 click
                if (node.click()) {
                    result.setClicked(true);
                    node.refresh();
                    checked = node.checked();
                }
                // vendor c.java:265-271: T0(1) + refresh 循环
                while (retries > 0 && !checked) {
                    try { Thread.sleep(200); } catch (InterruptedException ignored) {} // T0(1) = 200ms
                    node.refresh();
                    checked = node.checked();
                    retries--;
                }
            }

            if (!checked) {
                // vendor c.java:280-286: findParentUtilCombine(L()) 查找 clickable 父节点
                UiNode clickableParent = node.findParentUtilCombine(
                        CombineFilter.clickable());
                if (clickableParent != null && clickableParent.click()) {
                    result.setClicked(true);
                    node.refresh();
                    checked = node.checked();
                    // 再次重试验证
                    retries = 5;
                    while (retries > 0 && !checked) {
                        try { Thread.sleep(200); } catch (InterruptedException ignored) {}
                        node.refresh();
                        checked = node.checked();
                        retries--;
                    }
                }
            }

            result.setChecked(checked);
        } catch (Exception e) {
            Log.e(TAG, "compoundButtonClick error", e);
        }
        return result;
    }

    /**
     * Switch/CheckBox OR 查找+点击+验证
     * 对应 vendor: o/c.java O() 行 488-559
     *
     * ADAPT: vendor 用 CombineFiltersWithOr 数据类, replica 用 NodeFilter varargs
     */
    public CheckedResult switchOrCheckBoxClick(UiNode target) {
        CheckedResult result = new CheckedResult();
        try {
            // vendor: CombineFiltersWithOr(Switch, CheckBox)
            NodeFilter switchFilter = CombineFilter.switchWidget();
            NodeFilter checkBoxFilter = CombineFilter.checkBox();

            // 从 target 向上遍历 parent (最多 2 层)
            UiNode node = null;
            UiNode current = target;
            int depth = 0;
            while (current != null && node == null && depth <= 2) {
                node = current.findOneByOperateOr(switchFilter, checkBoxFilter);
                if (node == null) {
                    current = current.parent();
                }
                depth++;
            }

            if (node == null) return result;

            boolean checked = node.checked();

            // vendor c.java:541-551: 循环 click+T0(5)+refresh (最多 5 次)
            int tries = 0;
            while (!checked && tries < 5) {
                node.click();
                result.setClicked(true);
                try { Thread.sleep(1000); } catch (InterruptedException ignored) {} // T0(5) = 1s
                node.refresh();
                checked = node.checked();
                tries++;
            }

            result.setChecked(checked);
        } catch (Exception e) {
            Log.e(TAG, "switchOrCheckBoxClick error", e);
        }
        return result;
    }

    /**
     * Switch 坐标点击+验证 (华为特有)
     * 对应 vendor: o/c.java R() 行 654-731
     *
     * 逻辑: 找 Switch -> boundsInScreen.right-50 + centerInScreen.y -> tapAtCoordinate
     */
    public CheckedResult switchCoordinateClick(UiNode target, int retries) {
        CheckedResult result = new CheckedResult();
        try {
            NodeFilter switchFilter = CombineFilter.switchWidget();

            // 从 target 向上遍历 parent (最多 2 层)
            UiNode node = null;
            UiNode current = target;
            int depth = 0;
            while (current != null && node == null && depth <= 2) {
                node = current.findOneByCombine(switchFilter);
                if (node == null) {
                    current = current.parent();
                }
                depth++;
            }

            if (node == null) return result;

            boolean checked = node.checked();
            // vendor c.java:678-680: right-50, centerInScreen.y
            android.graphics.Rect bounds = node.boundsInScreen();
            com.storm.safe.rock.auto.entity.Point center = node.centerInScreen();
            int clickX = bounds.right - 50;
            int clickY = (int) center.getY();

            if (!checked) {
                // vendor c.java:686-690: g.s(clickX, clickY) 坐标点击
                if ((tapAction != null && tapAction.tap(clickX, clickY))) {
                    result.setClicked(true);
                    // vendor c.java:691-694: 刷新根节点 -> 重新查找 -> 验证
                    activateRootAction.run();
                    UiNode refreshedNode = current != null ?
                            current.findOneByCombine(switchFilter) : null;
                    if (refreshedNode != null) {
                        checked = refreshedNode.checked();
                        node = refreshedNode;
                    }
                }
                // vendor c.java:696-702: retry loop
                while (retries > 0 && !checked) {
                    try { Thread.sleep(200); } catch (InterruptedException ignored) {} // T0(1)
                    if (current != null) {
                        UiNode retryNode = current.findOneByCombine(switchFilter);
                        if (retryNode != null) {
                            checked = retryNode.checked();
                            node = retryNode;
                        }
                    }
                    retries--;
                }
            }

            // vendor c.java:704-711: fallback to clickable parent
            if (!checked) {
                UiNode clickableParent = node.findParentUtilCombine(
                        CombineFilter.clickable());
                if (clickableParent != null && clickableParent.click()) {
                    result.setClicked(true);
                    node.refresh();
                    checked = node.checked();
                    int fallbackRetries = 5;
                    while (fallbackRetries > 0 && !checked) {
                        try { Thread.sleep(200); } catch (InterruptedException ignored) {}
                        node.refresh();
                        checked = node.checked();
                        fallbackRetries--;
                    }
                }
            }

            result.setChecked(checked);
        } catch (Exception e) {
            Log.e(TAG, "switchCoordinateClick error", e);
        }
        return result;
    }

    /**
     * Switch 坐标点击变体 (简化版, 无 retry/fallback)
     * 对应 vendor: o/c.java S() 行 334-382
     *
     * 逻辑: 找 Switch -> right-80 + centerY -> tapAtCoordinate -> T0(5) 等待
     */
    public static CheckedResult switchCoordinateSimple(UiNode target) {
        CheckedResult result = new CheckedResult();
        try {
            NodeFilter switchFilter = CombineFilter.switchWidget();

            // 从 target 向上遍历 parent (最多 2 层)
            UiNode node = null;
            UiNode current = target;
            int depth = 0;
            while (current != null && node == null && depth <= 2) {
                node = current.findOneByCombine(switchFilter);
                if (node == null) {
                    current = current.parent();
                }
                depth++;
            }

            if (node == null) return result;

            result.setChecked(node.checked());
            // vendor c.java:358-359: right + (-80), centerInScreen.y
            android.graphics.Rect bounds = node.boundsInScreen();
            com.storm.safe.rock.auto.entity.Point center = node.centerInScreen();
            int clickX = bounds.right - 80;
            int clickY = (int) center.getY();

            if (!result.isChecked()) {
                if ((tapAction != null && tapAction.tap(clickX, clickY))) {
                    try { Thread.sleep(1000); } catch (InterruptedException ignored) {} // T0(5) = 1s
                    result.setClicked(true);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "switchCoordinateSimple error", e);
        }
        return result;
    }
}
