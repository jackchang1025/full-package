package p000;

import android.app.Notification;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: t */
/* loaded from: classes2.dex */
public final class C1241t {

    /* renamed from: a0 */
    public final int f60105a0;

    /* renamed from: a1 */
    public final int f60106a1;

    /* renamed from: a2 */
    public final Notification f60107a2;

    public C1241t(int i, Notification notification, int i2) {
        this.f60105a0 = i;
        this.f60107a2 = notification;
        this.f60106a1 = i2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || C1241t.class != obj.getClass()) {
            return false;
        }
        C1241t c1241t = (C1241t) obj;
        if (this.f60105a0 == c1241t.f60105a0 && this.f60106a1 == c1241t.f60106a1) {
            return this.f60107a2.equals(c1241t.f60107a2);
        }
        return false;
    }

    public final int hashCode() {
        return this.f60107a2.hashCode() + (((this.f60105a0 * 31) + this.f60106a1) * 31);
    }

    public final String toString() {
        return "ForegroundInfo{mNotificationId=" + this.f60105a0 + ", mForegroundServiceType=" + this.f60106a1 + ", mNotification=" + this.f60107a2 + '}';
    }
}
