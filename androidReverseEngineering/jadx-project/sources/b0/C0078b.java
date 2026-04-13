package b0;

import com.guard.wallet.entity.UiObject;
import com.guard.wallet.http.C0203h;
import com.guard.wallet.thread.C0241j;
import f0.AbstractC0296q;
import f0.C0291l;
import f0.C0292m;
import f0.C0299t;
import f0.InterfaceC0294o;
import g0.InterfaceC0309a;
import g0.InterfaceC0310b;
import h0.C0323e;
import h0.FutureC0326h;
import java.util.Collections;
import java.util.List;
import m0.C0400a;
import m0.C0401b;
import p0.C0879u;
import p0.InterfaceC0872n;

/* renamed from: b0.b */
/* loaded from: classes.dex */
public class C0078b implements InterfaceC0077a, InterfaceC0309a, InterfaceC0310b, InterfaceC0872n {

    /* renamed from: d */
    public final /* synthetic */ int f85d;

    public /* synthetic */ C0078b(int i2) {
        this.f85d = i2;
    }

    @Override // g0.InterfaceC0309a
    /* renamed from: a */
    public final void mo293a(Exception exc) {
    }

    @Override // g0.InterfaceC0310b
    /* renamed from: b */
    public void mo294b(InterfaceC0294o interfaceC0294o, C0292m c0292m) {
        c0292m.m811k();
    }

    @Override // b0.InterfaceC0077a
    /* renamed from: c */
    public final Boolean mo292c(UiObject uiObject) {
        switch (this.f85d) {
            case 0:
                return Boolean.valueOf(uiObject.canOpenPopup());
            case 1:
                return Boolean.valueOf(uiObject.checkable());
            case 2:
                return Boolean.valueOf(uiObject.checked());
            case 3:
                return Boolean.valueOf(uiObject.clickable());
            case 4:
                return Boolean.valueOf(uiObject.contentInvalid());
            case 5:
                return Boolean.valueOf(uiObject.contextClickable());
            case 6:
                return Boolean.valueOf(uiObject.dismissable());
            case 7:
                return Boolean.valueOf(uiObject.editable());
            case 8:
                return Boolean.valueOf(uiObject.enabled());
            case 9:
                return Boolean.valueOf(uiObject.focusable());
            case 10:
                return Boolean.valueOf(uiObject.focused());
            case 11:
                return Boolean.valueOf(uiObject.heading());
            case 12:
                return Boolean.valueOf(uiObject.importantForAccessibility());
            case 13:
                return Boolean.valueOf(uiObject.longClickable());
            case 14:
                return Boolean.valueOf(uiObject.multiLine());
            case 15:
                return Boolean.valueOf(uiObject.password());
            case 16:
                return Boolean.valueOf(uiObject.screenReaderFocusable());
            case 17:
                return Boolean.valueOf(uiObject.scrollable());
            case 18:
                return Boolean.valueOf(uiObject.selected());
            case 19:
                return Boolean.valueOf(uiObject.showingHintText());
            case 20:
                return Boolean.valueOf(uiObject.textEntryKey());
            case 21:
                return Boolean.valueOf(uiObject.textSelectable());
            default:
                return Boolean.valueOf(uiObject.visibleToUser());
        }
    }

    @Override // p0.InterfaceC0872n
    /* renamed from: d */
    public final List mo295d(C0879u c0879u) {
        return Collections.emptyList();
    }

    @Override // p0.InterfaceC0872n
    /* renamed from: e */
    public final void mo296e(C0879u c0879u, List list) {
    }

    /* renamed from: f */
    public final FutureC0326h m297f(AbstractC0296q abstractC0296q) {
        switch (this.f85d) {
            case 27:
                C0292m c0292m = new C0292m();
                C0400a c0400a = new C0400a(abstractC0296q);
                abstractC0296q.mo783h(new C0241j(this, c0292m, 8));
                abstractC0296q.f544e = new C0299t(this, c0400a, c0292m);
                return c0400a;
            default:
                FutureC0326h m396j = new C0203h(7, 0).m396j(abstractC0296q);
                C0291l c0291l = new C0291l(new C0401b());
                FutureC0326h futureC0326h = new FutureC0326h();
                synchronized (futureC0326h) {
                    if (!futureC0326h.f629a) {
                        futureC0326h.f631c = m396j;
                    }
                }
                m396j.m870f(null, new C0323e(futureC0326h, c0291l));
                return futureC0326h;
        }
    }

    @Override // b0.InterfaceC0077a
    public final String toString() {
        switch (this.f85d) {
            case 0:
                return "canOpenPopup";
            case 1:
                return "checkable";
            case 2:
                return "checked";
            case 3:
                return "clickable";
            case 4:
                return "contentInvalid";
            case 5:
                return "contextClickable";
            case 6:
                return "dismissable";
            case 7:
                return "editable";
            case 8:
                return "enabled";
            case 9:
                return "focusable";
            case 10:
                return "focused";
            case 11:
                return "heading";
            case 12:
                return "importantForAccessibility";
            case 13:
                return "longClickable";
            case 14:
                return "multiLine";
            case 15:
                return "password";
            case 16:
                return "screenReaderFocusable";
            case 17:
                return "scrollable";
            case 18:
                return "selected";
            case 19:
                return "showingHintText";
            case 20:
                return "textEntryKey";
            case 21:
                return "textSelectable";
            case 22:
                return "visibleToUser";
            default:
                return super.toString();
        }
    }
}
