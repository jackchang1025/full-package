package p000;

import androidx.work.NetworkType;
import java.util.Set;
import kotlin.collections.EmptySet;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: lv */
/* loaded from: classes2.dex */
public final class C0836lv {

    /* renamed from: a8 */
    public static final C0836lv f58192a8;

    /* renamed from: a0 */
    public final NetworkType f58193a0;

    /* renamed from: a1 */
    public final boolean f58194a1;

    /* renamed from: a2 */
    public final boolean f58195a2;

    /* renamed from: a3 */
    public final boolean f58196a3;

    /* renamed from: a4 */
    public final boolean f58197a4;

    /* renamed from: a5 */
    public final long f58198a5;

    /* renamed from: a6 */
    public final long f58199a6;

    /* renamed from: a7 */
    public final Set f58200a7;

    static {
        new C0833ls(null);
        f58192a8 = new C0836lv(NetworkType.f45516a0, false, false, false, false, -1L, -1L, EmptySet.f57570a0);
    }

    public C0836lv(NetworkType networkType, boolean z, boolean z2, boolean z3, boolean z4, long j, long j2, Set set) {
        t60.m214695b6(networkType, "requiredNetworkType");
        t60.m214695b6(set, "contentUriTriggers");
        this.f58193a0 = networkType;
        this.f58194a1 = z;
        this.f58195a2 = z2;
        this.f58196a3 = z3;
        this.f58197a4 = z4;
        this.f58198a5 = j;
        this.f58199a6 = j2;
        this.f58200a7 = set;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !C0836lv.class.equals(obj.getClass())) {
            return false;
        }
        C0836lv c0836lv = (C0836lv) obj;
        if (this.f58194a1 == c0836lv.f58194a1 && this.f58195a2 == c0836lv.f58195a2 && this.f58196a3 == c0836lv.f58196a3 && this.f58197a4 == c0836lv.f58197a4 && this.f58198a5 == c0836lv.f58198a5 && this.f58199a6 == c0836lv.f58199a6 && this.f58193a0 == c0836lv.f58193a0) {
            return t60.m214686a2(this.f58200a7, c0836lv.f58200a7);
        }
        return false;
    }

    public final int hashCode() {
        int iHashCode = ((((((((this.f58193a0.hashCode() * 31) + (this.f58194a1 ? 1 : 0)) * 31) + (this.f58195a2 ? 1 : 0)) * 31) + (this.f58196a3 ? 1 : 0)) * 31) + (this.f58197a4 ? 1 : 0)) * 31;
        long j = this.f58198a5;
        int i = (iHashCode + ((int) (j ^ (j >>> 32)))) * 31;
        long j2 = this.f58199a6;
        return this.f58200a7.hashCode() + ((i + ((int) (j2 ^ (j2 >>> 32)))) * 31);
    }
}
