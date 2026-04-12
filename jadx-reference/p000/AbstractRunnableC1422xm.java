package p000;

import kotlinx.coroutines.AbstractC0783a3;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: xm */
/* loaded from: classes2.dex */
public abstract class AbstractRunnableC1422xm implements Runnable, Comparable, InterfaceC1266tn {
    private volatile Object _heap;

    /* renamed from: a0 */
    public long f61155a0;

    /* renamed from: a1 */
    public int f61156a1 = -1;

    public AbstractRunnableC1422xm(long j) {
        this.f61155a0 = j;
    }

    /* renamed from: a0 */
    public final int m215197a0(long j, C1423xn c1423xn, AbstractC0783a3 abstractC0783a3) {
        synchronized (this) {
            if (this._heap == kj1.f57533a1) {
                return 2;
            }
            synchronized (c1423xn) {
                try {
                    AbstractRunnableC1422xm[] abstractRunnableC1422xmArr = c1423xn.f58459a0;
                    AbstractRunnableC1422xm abstractRunnableC1422xm = abstractRunnableC1422xmArr != null ? abstractRunnableC1422xmArr[0] : null;
                    if (AbstractC0783a3.f57661a8.get(abstractC0783a3) != 0) {
                        return 1;
                    }
                    if (abstractRunnableC1422xm == null) {
                        c1423xn.f61164a2 = j;
                    } else {
                        long j2 = abstractRunnableC1422xm.f61155a0;
                        if (j2 - j < 0) {
                            j = j2;
                        }
                        if (j - c1423xn.f61164a2 > 0) {
                            c1423xn.f61164a2 = j;
                        }
                    }
                    long j3 = this.f61155a0;
                    long j4 = c1423xn.f61164a2;
                    if (j3 - j4 < 0) {
                        this.f61155a0 = j4;
                    }
                    c1423xn.m214034a0(this);
                    return 0;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* renamed from: a1 */
    public final void m215198a1(C1423xn c1423xn) {
        if (this._heap == kj1.f57533a1) {
            throw new IllegalArgumentException("Failed requirement.");
        }
        this._heap = c1423xn;
    }

    @Override // p000.InterfaceC1266tn
    /* renamed from: a2 */
    public final void mo214761a2() {
        synchronized (this) {
            try {
                Object obj = this._heap;
                C1347vr c1347vr = kj1.f57533a1;
                if (obj == c1347vr) {
                    return;
                }
                C1423xn c1423xn = obj instanceof C1423xn ? (C1423xn) obj : null;
                if (c1423xn != null) {
                    synchronized (c1423xn) {
                        Object obj2 = this._heap;
                        if ((obj2 instanceof n61 ? (n61) obj2 : null) != null) {
                            c1423xn.m214035a1(this.f61156a1);
                        }
                    }
                }
                this._heap = c1347vr;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // java.lang.Comparable
    public final int compareTo(Object obj) {
        long j = this.f61155a0 - ((AbstractRunnableC1422xm) obj).f61155a0;
        if (j > 0) {
            return 1;
        }
        return j < 0 ? -1 : 0;
    }

    public String toString() {
        return "Delayed[nanos=" + this.f61155a0 + ']';
    }
}
