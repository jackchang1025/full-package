package p012o;

import com.guard.wallet.entity.UiObject;
import java.io.Serializable;

/* loaded from: classes.dex */
public final class j0 implements Serializable {

    /* renamed from: a */
    public final UiObject f915a;

    /* renamed from: b */
    public final int f916b;

    /* renamed from: c */
    public final String f917c;

    /* renamed from: d */
    public final String f918d;

    /* renamed from: e */
    public final String f919e;

    /* renamed from: g */
    public final long f921g = System.nanoTime();

    /* renamed from: f */
    public final String f920f = null;

    public j0(UiObject uiObject, int i2, String str, String str2, String str3) {
        this.f915a = uiObject;
        this.f916b = i2;
        this.f917c = str;
        this.f918d = str2;
        this.f919e = str3;
    }

    public final int hashCode() {
        int i2 = 31 + this.f916b;
        UiObject uiObject = this.f915a;
        if (uiObject != null) {
            i2 = (i2 * 31) + uiObject.hashCode();
        }
        String str = this.f917c;
        if (str != null) {
            i2 = (i2 * 31) + str.hashCode();
        }
        String str2 = this.f918d;
        return str2 != null ? (i2 * 31) + str2.hashCode() : i2;
    }

    public final String toString() {
        return "WaitAccessibilityEvent{eventSource=" + this.f915a + ", eventType='" + this.f916b + "', rootPackageName='" + this.f917c + "', windowClassName='" + this.f918d + "', beforeText='" + this.f919e + "', eventText='" + this.f920f + "', timestamp='" + this.f921g + "'}";
    }
}
