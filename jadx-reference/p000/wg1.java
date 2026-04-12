package p000;

import androidx.work.BackoffPolicy;
import androidx.work.OutOfQuotaPolicy;
import androidx.work.WorkInfo$State;
import okhttp3.internal.http2.Http2;
import okio.Segment;
import okio.internal.Buffer;
import org.conscrypt.PSKKeyManager;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class wg1 {

    /* renamed from: a0 */
    public final String f60912a0;

    /* renamed from: a1 */
    public WorkInfo$State f60913a1;

    /* renamed from: a2 */
    public final String f60914a2;

    /* renamed from: a3 */
    public String f60915a3;

    /* renamed from: a4 */
    public final C1106qd f60916a4;

    /* renamed from: a5 */
    public final C1106qd f60917a5;

    /* renamed from: a6 */
    public final long f60918a6;

    /* renamed from: a7 */
    public long f60919a7;

    /* renamed from: a8 */
    public long f60920a8;

    /* renamed from: a9 */
    public C0836lv f60921a9;

    /* renamed from: b0 */
    public final int f60922b0;

    /* renamed from: b1 */
    public BackoffPolicy f60923b1;

    /* renamed from: b2 */
    public long f60924b2;

    /* renamed from: b3 */
    public long f60925b3;

    /* renamed from: b4 */
    public final long f60926b4;

    /* renamed from: b5 */
    public final long f60927b5;

    /* renamed from: b6 */
    public boolean f60928b6;

    /* renamed from: b7 */
    public final OutOfQuotaPolicy f60929b7;

    /* renamed from: b8 */
    public final int f60930b8;

    /* renamed from: b9 */
    public final int f60931b9;

    static {
        new ug1(null);
        t60.m214694b5(C1351vv.m214966b1("WorkSpec"), "tagWithPrefix(\"WorkSpec\")");
    }

    public wg1(String str, WorkInfo$State workInfo$State, String str2, String str3, C1106qd c1106qd, C1106qd c1106qd2, long j, long j2, long j3, C0836lv c0836lv, int i, BackoffPolicy backoffPolicy, long j4, long j5, long j6, long j7, boolean z, OutOfQuotaPolicy outOfQuotaPolicy, int i2, int i3) {
        t60.m214695b6(str, "id");
        t60.m214695b6(workInfo$State, "state");
        t60.m214695b6(str2, "workerClassName");
        t60.m214695b6(c1106qd, "input");
        t60.m214695b6(c1106qd2, "output");
        t60.m214695b6(c0836lv, "constraints");
        t60.m214695b6(backoffPolicy, "backoffPolicy");
        t60.m214695b6(outOfQuotaPolicy, "outOfQuotaPolicy");
        this.f60912a0 = str;
        this.f60913a1 = workInfo$State;
        this.f60914a2 = str2;
        this.f60915a3 = str3;
        this.f60916a4 = c1106qd;
        this.f60917a5 = c1106qd2;
        this.f60918a6 = j;
        this.f60919a7 = j2;
        this.f60920a8 = j3;
        this.f60921a9 = c0836lv;
        this.f60922b0 = i;
        this.f60923b1 = backoffPolicy;
        this.f60924b2 = j4;
        this.f60925b3 = j5;
        this.f60926b4 = j6;
        this.f60927b5 = j7;
        this.f60928b6 = z;
        this.f60929b7 = outOfQuotaPolicy;
        this.f60930b8 = i2;
        this.f60931b9 = i3;
    }

    /* renamed from: a1 */
    public static wg1 m215065a1(wg1 wg1Var, String str, WorkInfo$State workInfo$State, String str2, C1106qd c1106qd, int i, long j, int i2, int i3) {
        String str3 = (i3 & 1) != 0 ? wg1Var.f60912a0 : str;
        WorkInfo$State workInfo$State2 = (i3 & 2) != 0 ? wg1Var.f60913a1 : workInfo$State;
        String str4 = (i3 & 4) != 0 ? wg1Var.f60914a2 : str2;
        String str5 = wg1Var.f60915a3;
        C1106qd c1106qd2 = (i3 & 16) != 0 ? wg1Var.f60916a4 : c1106qd;
        C1106qd c1106qd3 = wg1Var.f60917a5;
        long j2 = wg1Var.f60918a6;
        long j3 = wg1Var.f60919a7;
        long j4 = wg1Var.f60920a8;
        C0836lv c0836lv = wg1Var.f60921a9;
        int i4 = (i3 & Segment.SHARE_MINIMUM) != 0 ? wg1Var.f60922b0 : i;
        BackoffPolicy backoffPolicy = wg1Var.f60923b1;
        long j5 = wg1Var.f60924b2;
        long j6 = (i3 & Segment.SIZE) != 0 ? wg1Var.f60925b3 : j;
        long j7 = wg1Var.f60926b4;
        long j8 = wg1Var.f60927b5;
        boolean z = wg1Var.f60928b6;
        OutOfQuotaPolicy outOfQuotaPolicy = wg1Var.f60929b7;
        int i5 = wg1Var.f60930b8;
        int i6 = (i3 & 524288) != 0 ? wg1Var.f60931b9 : i2;
        wg1Var.getClass();
        t60.m214695b6(str3, "id");
        t60.m214695b6(workInfo$State2, "state");
        t60.m214695b6(str4, "workerClassName");
        t60.m214695b6(c1106qd2, "input");
        t60.m214695b6(c1106qd3, "output");
        t60.m214695b6(c0836lv, "constraints");
        t60.m214695b6(backoffPolicy, "backoffPolicy");
        t60.m214695b6(outOfQuotaPolicy, "outOfQuotaPolicy");
        return new wg1(str3, workInfo$State2, str4, str5, c1106qd2, c1106qd3, j2, j3, j4, c0836lv, i4, backoffPolicy, j5, j6, j7, j8, z, outOfQuotaPolicy, i5, i6);
    }

    /* renamed from: a0 */
    public final long m215066a0() {
        int i;
        if (this.f60913a1 == WorkInfo$State.f45526a0 && (i = this.f60922b0) > 0) {
            long jScalb = this.f60923b1 == BackoffPolicy.f45496a1 ? this.f60924b2 * i : (long) Math.scalb(this.f60924b2, i - 1);
            long j = this.f60925b3;
            if (jScalb > 18000000) {
                jScalb = 18000000;
            }
            return j + jScalb;
        }
        boolean zM215068a3 = m215068a3();
        long j2 = this.f60918a6;
        if (!zM215068a3) {
            long jCurrentTimeMillis = this.f60925b3;
            if (jCurrentTimeMillis == 0) {
                jCurrentTimeMillis = System.currentTimeMillis();
            }
            return jCurrentTimeMillis + j2;
        }
        int i2 = this.f60930b8;
        long j3 = this.f60925b3;
        if (i2 == 0) {
            j3 += j2;
        }
        long j4 = this.f60920a8;
        long j5 = this.f60919a7;
        if (j4 != j5) {
            return j3 + j5 + (i2 == 0 ? (-1) * j4 : 0L);
        }
        return j3 + (i2 != 0 ? j5 : 0L);
    }

    /* renamed from: a2 */
    public final boolean m215067a2() {
        return !t60.m214686a2(C0836lv.f58192a8, this.f60921a9);
    }

    /* renamed from: a3 */
    public final boolean m215068a3() {
        return this.f60919a7 != 0;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof wg1)) {
            return false;
        }
        wg1 wg1Var = (wg1) obj;
        return t60.m214686a2(this.f60912a0, wg1Var.f60912a0) && this.f60913a1 == wg1Var.f60913a1 && t60.m214686a2(this.f60914a2, wg1Var.f60914a2) && t60.m214686a2(this.f60915a3, wg1Var.f60915a3) && t60.m214686a2(this.f60916a4, wg1Var.f60916a4) && t60.m214686a2(this.f60917a5, wg1Var.f60917a5) && this.f60918a6 == wg1Var.f60918a6 && this.f60919a7 == wg1Var.f60919a7 && this.f60920a8 == wg1Var.f60920a8 && t60.m214686a2(this.f60921a9, wg1Var.f60921a9) && this.f60922b0 == wg1Var.f60922b0 && this.f60923b1 == wg1Var.f60923b1 && this.f60924b2 == wg1Var.f60924b2 && this.f60925b3 == wg1Var.f60925b3 && this.f60926b4 == wg1Var.f60926b4 && this.f60927b5 == wg1Var.f60927b5 && this.f60928b6 == wg1Var.f60928b6 && this.f60929b7 == wg1Var.f60929b7 && this.f60930b8 == wg1Var.f60930b8 && this.f60931b9 == wg1Var.f60931b9;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        int iM214801a1 = tz0.m214801a1((this.f60913a1.hashCode() + (this.f60912a0.hashCode() * 31)) * 31, 31, this.f60914a2);
        String str = this.f60915a3;
        int iHashCode = (Long.hashCode(this.f60927b5) + ((Long.hashCode(this.f60926b4) + ((Long.hashCode(this.f60925b3) + ((Long.hashCode(this.f60924b2) + ((this.f60923b1.hashCode() + tz0.m214800a0(this.f60922b0, (this.f60921a9.hashCode() + ((Long.hashCode(this.f60920a8) + ((Long.hashCode(this.f60919a7) + ((Long.hashCode(this.f60918a6) + ((this.f60917a5.hashCode() + ((this.f60916a4.hashCode() + ((iM214801a1 + (str == null ? 0 : str.hashCode())) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31, 31)) * 31)) * 31)) * 31)) * 31)) * 31;
        boolean z = this.f60928b6;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        return Integer.hashCode(this.f60931b9) + tz0.m214800a0(this.f60930b8, (this.f60929b7.hashCode() + ((iHashCode + i) * 31)) * 31, 31);
    }

    public final String toString() {
        return "{WorkSpec: " + this.f60912a0 + '}';
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ wg1(String str, WorkInfo$State workInfo$State, String str2, String str3, C1106qd c1106qd, C1106qd c1106qd2, long j, long j2, long j3, C0836lv c0836lv, int i, BackoffPolicy backoffPolicy, long j4, long j5, long j6, long j7, boolean z, OutOfQuotaPolicy outOfQuotaPolicy, int i2, int i3, int i4) {
        C1106qd c1106qd3;
        C1106qd c1106qd4;
        WorkInfo$State workInfo$State2 = (i3 & 2) != 0 ? WorkInfo$State.f45526a0 : workInfo$State;
        String str4 = (i3 & 8) != 0 ? null : str3;
        if ((i3 & 16) != 0) {
            C1106qd c1106qd5 = C1106qd.f59467a1;
            t60.m214694b5(c1106qd5, "EMPTY");
            c1106qd3 = c1106qd5;
        } else {
            c1106qd3 = c1106qd;
        }
        if ((i3 & 32) != 0) {
            C1106qd c1106qd6 = C1106qd.f59467a1;
            t60.m214694b5(c1106qd6, "EMPTY");
            c1106qd4 = c1106qd6;
        } else {
            c1106qd4 = c1106qd2;
        }
        this(str, workInfo$State2, str2, str4, c1106qd3, c1106qd4, (i3 & 64) != 0 ? 0L : j, (i3 & 128) != 0 ? 0L : j2, (i3 & PSKKeyManager.MAX_KEY_LENGTH_BYTES) != 0 ? 0L : j3, (i3 & 512) != 0 ? C0836lv.f58192a8 : c0836lv, (i3 & Segment.SHARE_MINIMUM) != 0 ? 0 : i, (i3 & 2048) != 0 ? BackoffPolicy.f45495a0 : backoffPolicy, (i3 & Buffer.SEGMENTING_THRESHOLD) != 0 ? 30000L : j4, (i3 & Segment.SIZE) != 0 ? 0L : j5, (i3 & Http2.INITIAL_MAX_FRAME_SIZE) != 0 ? 0L : j6, (32768 & i3) != 0 ? -1L : j7, (65536 & i3) != 0 ? false : z, (131072 & i3) != 0 ? OutOfQuotaPolicy.f45523a0 : outOfQuotaPolicy, (i3 & 262144) != 0 ? 0 : i2, 0);
    }
}
