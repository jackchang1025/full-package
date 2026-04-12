package p000;

import android.os.SystemClock;
import android.view.Choreographer;
import java.util.ArrayList;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: t5 */
/* loaded from: classes.dex */
public final class ChoreographerFrameCallbackC1247t5 implements Choreographer.FrameCallback {

    /* renamed from: a0 */
    public final /* synthetic */ zg1 f60140a0;

    public ChoreographerFrameCallbackC1247t5(zg1 zg1Var) {
        this.f60140a0 = zg1Var;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0050  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0136  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x019d A[SYNTHETIC] */
    @Override // android.view.Choreographer.FrameCallback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void doFrame(long j) {
        long j2;
        int i;
        float f;
        float f2;
        boolean z;
        C1248t6 c1248t6 = (C1248t6) ((tg0) this.f60140a0.f61551a0).f60218a1;
        long jUptimeMillis = SystemClock.uptimeMillis();
        ArrayList arrayList = c1248t6.f60144a1;
        long jUptimeMillis2 = SystemClock.uptimeMillis();
        int i2 = 0;
        while (i2 < arrayList.size()) {
            m11 m11Var = (m11) arrayList.get(i2);
            if (m11Var == null) {
                i = i2;
            } else {
                t01 t01Var = c1248t6.f60143a0;
                Long l = (Long) t01Var.getOrDefault(m11Var, null);
                if (l != null) {
                    if (l.longValue() < jUptimeMillis2) {
                        t01Var.remove(m11Var);
                        j2 = m11Var.f58234a6;
                        if (j2 != 0) {
                            m11Var.f58234a6 = jUptimeMillis;
                            m11Var.m213930a0(m11Var.f58229a1);
                        } else {
                            long j3 = jUptimeMillis - j2;
                            m11Var.f58234a6 = jUptimeMillis;
                            if (m11Var.f58240b2) {
                                float f3 = m11Var.f58239b1;
                                if (f3 != Float.MAX_VALUE) {
                                    i = i2;
                                    m11Var.f58238b0.f58427a8 = f3;
                                    m11Var.f58239b1 = Float.MAX_VALUE;
                                } else {
                                    i = i2;
                                }
                                m11Var.f58229a1 = (float) m11Var.f58238b0.f58427a8;
                                m11Var.f58228a0 = 0.0f;
                                m11Var.f58240b2 = false;
                                f = Float.MAX_VALUE;
                            } else {
                                i = i2;
                                if (m11Var.f58239b1 != Float.MAX_VALUE) {
                                    n11 n11Var = m11Var.f58238b0;
                                    double d = n11Var.f58427a8;
                                    long j4 = j3 / 2;
                                    C1311us c1311usM214028a0 = n11Var.m214028a0(m11Var.f58229a1, m11Var.f58228a0, j4);
                                    n11 n11Var2 = m11Var.f58238b0;
                                    n11Var2.f58427a8 = m11Var.f58239b1;
                                    m11Var.f58239b1 = Float.MAX_VALUE;
                                    C1311us c1311usM214028a02 = n11Var2.m214028a0(c1311usM214028a0.f60506a0, c1311usM214028a0.f60507a1, j4);
                                    m11Var.f58229a1 = c1311usM214028a02.f60506a0;
                                    m11Var.f58228a0 = c1311usM214028a02.f60507a1;
                                    f = Float.MAX_VALUE;
                                    f2 = -3.4028235E38f;
                                } else {
                                    f = Float.MAX_VALUE;
                                    f2 = -3.4028235E38f;
                                    C1311us c1311usM214028a03 = m11Var.f58238b0.m214028a0(m11Var.f58229a1, m11Var.f58228a0, j3);
                                    m11Var.f58229a1 = c1311usM214028a03.f60506a0;
                                    m11Var.f58228a0 = c1311usM214028a03.f60507a1;
                                }
                                float fMax = Math.max(m11Var.f58229a1, f2);
                                m11Var.f58229a1 = fMax;
                                m11Var.f58229a1 = Math.min(fMax, f);
                                float f4 = m11Var.f58228a0;
                                n11 n11Var3 = m11Var.f58238b0;
                                n11Var3.getClass();
                                if (Math.abs(f4) >= n11Var3.f58423a4 || Math.abs(r11 - ((float) n11Var3.f58427a8)) >= n11Var3.f58422a3) {
                                    z = false;
                                    float fMin = Math.min(m11Var.f58229a1, f);
                                    m11Var.f58229a1 = fMin;
                                    float fMax2 = Math.max(fMin, -3.4028235E38f);
                                    m11Var.f58229a1 = fMax2;
                                    m11Var.m213930a0(fMax2);
                                    if (z) {
                                        continue;
                                    } else {
                                        ArrayList arrayList2 = m11Var.f58236a8;
                                        m11Var.f58233a5 = false;
                                        ThreadLocal threadLocal = C1248t6.f60142a5;
                                        if (threadLocal.get() == null) {
                                            threadLocal.set(new C1248t6());
                                        }
                                        C1248t6 c1248t62 = (C1248t6) threadLocal.get();
                                        c1248t62.f60143a0.remove(m11Var);
                                        ArrayList arrayList3 = c1248t62.f60144a1;
                                        int iIndexOf = arrayList3.indexOf(m11Var);
                                        if (iIndexOf >= 0) {
                                            arrayList3.set(iIndexOf, null);
                                            c1248t62.f60147a4 = true;
                                        }
                                        m11Var.f58234a6 = 0L;
                                        m11Var.f58230a2 = false;
                                        for (int i3 = 0; i3 < arrayList2.size(); i3++) {
                                            if (arrayList2.get(i3) != null) {
                                                arrayList2.get(i3).getClass();
                                                throw new ClassCastException();
                                            }
                                        }
                                        for (int size = arrayList2.size() - 1; size >= 0; size--) {
                                            if (arrayList2.get(size) == null) {
                                                arrayList2.remove(size);
                                            }
                                        }
                                    }
                                } else {
                                    m11Var.f58229a1 = (float) m11Var.f58238b0.f58427a8;
                                    m11Var.f58228a0 = 0.0f;
                                }
                            }
                            z = true;
                            float fMin2 = Math.min(m11Var.f58229a1, f);
                            m11Var.f58229a1 = fMin2;
                            float fMax22 = Math.max(fMin2, -3.4028235E38f);
                            m11Var.f58229a1 = fMax22;
                            m11Var.m213930a0(fMax22);
                            if (z) {
                            }
                        }
                    }
                    i = i2;
                } else {
                    j2 = m11Var.f58234a6;
                    if (j2 != 0) {
                    }
                }
            }
            i2 = i + 1;
        }
        if (c1248t6.f60147a4) {
            for (int size2 = arrayList.size() - 1; size2 >= 0; size2--) {
                if (arrayList.get(size2) == null) {
                    arrayList.remove(size2);
                }
            }
            c1248t6.f60147a4 = false;
        }
        if (arrayList.size() > 0) {
            if (c1248t6.f60146a3 == null) {
                c1248t6.f60146a3 = new zg1(c1248t6.f60145a2);
            }
            zg1 zg1Var = c1248t6.f60146a3;
            ((Choreographer) zg1Var.f61552a1).postFrameCallback((ChoreographerFrameCallbackC1247t5) zg1Var.f61553a2);
        }
    }
}
