package p000;

import android.R;
import android.os.Build;
import android.view.accessibility.AccessibilityNodeInfo;
import okhttp3.internal.http2.Http2;
import okio.Segment;
import okio.internal.Buffer;
import org.conscrypt.PSKKeyManager;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: k4 */
/* loaded from: classes.dex */
public final class C0745k4 {

    /* renamed from: a4 */
    public static final C0745k4 f57435a4 = new C0745k4(1);

    /* renamed from: a5 */
    public static final C0745k4 f57436a5 = new C0745k4(2);

    /* renamed from: a6 */
    public static final C0745k4 f57437a6;

    /* renamed from: a7 */
    public static final C0745k4 f57438a7;

    /* renamed from: a8 */
    public static final C0745k4 f57439a8;

    /* renamed from: a9 */
    public static final C0745k4 f57440a9;

    /* renamed from: b0 */
    public static final C0745k4 f57441b0;

    /* renamed from: b1 */
    public static final C0745k4 f57442b1;

    /* renamed from: b2 */
    public static final C0745k4 f57443b2;

    /* renamed from: b3 */
    public static final C0745k4 f57444b3;

    /* renamed from: b4 */
    public static final C0745k4 f57445b4;

    /* renamed from: a0 */
    public final Object f57446a0;

    /* renamed from: a1 */
    public final int f57447a1;

    /* renamed from: a2 */
    public final Class f57448a2;

    /* renamed from: a3 */
    public final InterfaceC0812l9 f57449a3;

    static {
        new C0745k4(4);
        new C0745k4(8);
        f57437a6 = new C0745k4(16);
        new C0745k4(32);
        new C0745k4(64);
        new C0745k4(128);
        new C0745k4(AbstractC0805l2.class, PSKKeyManager.MAX_KEY_LENGTH_BYTES);
        new C0745k4(AbstractC0805l2.class, 512);
        new C0745k4(AbstractC0806l3.class, Segment.SHARE_MINIMUM);
        new C0745k4(AbstractC0806l3.class, 2048);
        f57438a7 = new C0745k4(Buffer.SEGMENTING_THRESHOLD);
        f57439a8 = new C0745k4(Segment.SIZE);
        new C0745k4(Http2.INITIAL_MAX_FRAME_SIZE);
        new C0745k4(32768);
        new C0745k4(65536);
        new C0745k4(AbstractC0810l7.class, 131072);
        f57440a9 = new C0745k4(262144);
        f57441b0 = new C0745k4(524288);
        f57442b1 = new C0745k4(1048576);
        new C0745k4(AbstractC0811l8.class, 2097152);
        int i = Build.VERSION.SDK_INT;
        new C0745k4(AccessibilityNodeInfo.AccessibilityAction.ACTION_SHOW_ON_SCREEN, R.id.accessibilityActionShowOnScreen, null, null, null);
        new C0745k4(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_TO_POSITION, R.id.accessibilityActionScrollToPosition, null, null, AbstractC0808l5.class);
        f57443b2 = new C0745k4(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_UP, R.id.accessibilityActionScrollUp, null, null, null);
        new C0745k4(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_LEFT, R.id.accessibilityActionScrollLeft, null, null, null);
        f57444b3 = new C0745k4(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_DOWN, R.id.accessibilityActionScrollDown, null, null, null);
        new C0745k4(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_RIGHT, R.id.accessibilityActionScrollRight, null, null, null);
        new C0745k4(i >= 29 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_PAGE_UP : null, R.id.accessibilityActionPageUp, null, null, null);
        new C0745k4(i >= 29 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_PAGE_DOWN : null, R.id.accessibilityActionPageDown, null, null, null);
        new C0745k4(i >= 29 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_PAGE_LEFT : null, R.id.accessibilityActionPageLeft, null, null, null);
        new C0745k4(i >= 29 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_PAGE_RIGHT : null, R.id.accessibilityActionPageRight, null, null, null);
        new C0745k4(AccessibilityNodeInfo.AccessibilityAction.ACTION_CONTEXT_CLICK, R.id.accessibilityActionContextClick, null, null, null);
        f57445b4 = new C0745k4(AccessibilityNodeInfo.AccessibilityAction.ACTION_SET_PROGRESS, R.id.accessibilityActionSetProgress, null, null, AbstractC0809l6.class);
        new C0745k4(i >= 26 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_MOVE_WINDOW : null, R.id.accessibilityActionMoveWindow, null, null, AbstractC0807l4.class);
        new C0745k4(i >= 28 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_SHOW_TOOLTIP : null, R.id.accessibilityActionShowTooltip, null, null, null);
        new C0745k4(i >= 28 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_HIDE_TOOLTIP : null, R.id.accessibilityActionHideTooltip, null, null, null);
        new C0745k4(i >= 30 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_PRESS_AND_HOLD : null, R.id.accessibilityActionPressAndHold, null, null, null);
        new C0745k4(i >= 30 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_IME_ENTER : null, R.id.accessibilityActionImeEnter, null, null, null);
        new C0745k4(i >= 32 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_DRAG_START : null, R.id.accessibilityActionDragStart, null, null, null);
        new C0745k4(i >= 32 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_DRAG_DROP : null, R.id.accessibilityActionDragDrop, null, null, null);
        new C0745k4(i >= 32 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_DRAG_CANCEL : null, R.id.accessibilityActionDragCancel, null, null, null);
        new C0745k4(i >= 33 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_SHOW_TEXT_SUGGESTIONS : null, R.id.accessibilityActionShowTextSuggestions, null, null, null);
    }

    public C0745k4(int i) {
        this(null, i, null, null, null);
    }

    /* renamed from: a0 */
    public final int m213448a0() {
        return ((AccessibilityNodeInfo.AccessibilityAction) this.f57446a0).getId();
    }

    public final boolean equals(Object obj) {
        if (obj == null || !(obj instanceof C0745k4)) {
            return false;
        }
        Object obj2 = ((C0745k4) obj).f57446a0;
        Object obj3 = this.f57446a0;
        return obj3 == null ? obj2 == null : obj3.equals(obj2);
    }

    public final int hashCode() {
        Object obj = this.f57446a0;
        if (obj != null) {
            return obj.hashCode();
        }
        return 0;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("AccessibilityActionCompat: ");
        String strM213457a3 = C0748k7.m213457a3(this.f57447a1);
        if (strM213457a3.equals("ACTION_UNKNOWN")) {
            Object obj = this.f57446a0;
            if (((AccessibilityNodeInfo.AccessibilityAction) obj).getLabel() != null) {
                strM213457a3 = ((AccessibilityNodeInfo.AccessibilityAction) obj).getLabel().toString();
            }
        }
        sb.append(strM213457a3);
        return sb.toString();
    }

    public C0745k4(Class cls, int i) {
        this(null, i, null, null, cls);
    }

    public C0745k4(Object obj, int i, CharSequence charSequence, InterfaceC0812l9 interfaceC0812l9, Class cls) {
        this.f57447a1 = i;
        this.f57449a3 = interfaceC0812l9;
        if (obj == null) {
            this.f57446a0 = new AccessibilityNodeInfo.AccessibilityAction(i, charSequence);
        } else {
            this.f57446a0 = obj;
        }
        this.f57448a2 = cls;
    }
}
