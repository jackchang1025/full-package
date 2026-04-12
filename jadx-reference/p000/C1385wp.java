package p000;

import android.util.SparseArray;
import java.nio.ByteBuffer;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: wp */
/* loaded from: classes.dex */
public final class C1385wp {

    /* renamed from: a0 */
    public int f60955a0 = 1;

    /* renamed from: a1 */
    public final ag0 f60956a1;

    /* renamed from: a2 */
    public ag0 f60957a2;

    /* renamed from: a3 */
    public ag0 f60958a3;

    /* renamed from: a4 */
    public int f60959a4;

    /* renamed from: a5 */
    public int f60960a5;

    public C1385wp(ag0 ag0Var) {
        this.f60956a1 = ag0Var;
        this.f60957a2 = ag0Var;
    }

    /* renamed from: a0 */
    public final int m215086a0(int i) {
        SparseArray sparseArray = this.f60957a2.f43653a0;
        ag0 ag0Var = sparseArray == null ? null : (ag0) sparseArray.get(i);
        int i2 = 1;
        int i3 = 2;
        if (this.f60955a0 == 2) {
            if (ag0Var != null) {
                this.f60957a2 = ag0Var;
                this.f60960a5++;
            } else if (i == 65038) {
                m215087a1();
            } else if (i != 65039) {
                ag0 ag0Var2 = this.f60957a2;
                if (ag0Var2.f43654a1 != null) {
                    i3 = 3;
                    if (this.f60960a5 != 1) {
                        this.f60958a3 = ag0Var2;
                        m215087a1();
                    } else if (m215088a2()) {
                        this.f60958a3 = this.f60957a2;
                        m215087a1();
                    } else {
                        m215087a1();
                    }
                } else {
                    m215087a1();
                }
            }
            i2 = i3;
        } else if (ag0Var == null) {
            m215087a1();
        } else {
            this.f60955a0 = 2;
            this.f60957a2 = ag0Var;
            this.f60960a5 = 1;
            i2 = i3;
        }
        this.f60959a4 = i;
        return i2;
    }

    /* renamed from: a1 */
    public final void m215087a1() {
        this.f60955a0 = 1;
        this.f60957a2 = this.f60956a1;
        this.f60960a5 = 0;
    }

    /* renamed from: a2 */
    public final boolean m215088a2() {
        yf0 yf0VarM215084a1 = this.f60957a2.f43654a1.m215084a1();
        int iM215362a0 = yf0VarM215084a1.m215362a0(6);
        return !(iM215362a0 == 0 || ((ByteBuffer) yf0VarM215084a1.f61458a3).get(iM215362a0 + yf0VarM215084a1.f61455a0) == 0) || this.f60959a4 == 65039;
    }
}
