package p000;

import androidx.constraintlayout.core.widgets.analyzer.AbstractC0055a5;
import androidx.constraintlayout.core.widgets.analyzer.C0050a0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class p30 extends AbstractC0055a5 {
    @Override // p000.InterfaceC1215sa
    /* renamed from: a0 */
    public final void mo209948a0(InterfaceC1215sa interfaceC1215sa) {
        C0050a0 c0050a0 = this.f44464a7;
        if (c0050a0.f44443a2 && !c0050a0.f44450a9) {
            c0050a0.mo209951a3((int) ((((C0050a0) c0050a0.f44452b1.get(0)).f44447a6 * ((o30) this.f44458a1).f58726h2) + 0.5f));
        }
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.AbstractC0055a5
    /* renamed from: a3 */
    public final void mo209952a3() {
        C0829lq c0829lq = this.f44458a1;
        o30 o30Var = (o30) c0829lq;
        int i = o30Var.f58727h3;
        int i2 = o30Var.f58728h4;
        int i3 = o30Var.f58730h6;
        C0050a0 c0050a0 = this.f44464a7;
        if (i3 == 1) {
            if (i != -1) {
                c0050a0.f44452b1.add(c0829lq.f58108e7.f58064a3.f44464a7);
                this.f44458a1.f58108e7.f58064a3.f44464a7.f44451b0.add(c0050a0);
                c0050a0.f44446a5 = i;
            } else if (i2 != -1) {
                c0050a0.f44452b1.add(c0829lq.f58108e7.f58064a3.f44465a8);
                this.f44458a1.f58108e7.f58064a3.f44465a8.f44451b0.add(c0050a0);
                c0050a0.f44446a5 = -i2;
            } else {
                c0050a0.f44442a1 = true;
                c0050a0.f44452b1.add(c0829lq.f58108e7.f58064a3.f44465a8);
                this.f44458a1.f58108e7.f58064a3.f44465a8.f44451b0.add(c0050a0);
            }
            m214239b2(this.f44458a1.f58064a3.f44464a7);
            m214239b2(this.f44458a1.f58064a3.f44465a8);
            return;
        }
        if (i != -1) {
            c0050a0.f44452b1.add(c0829lq.f58108e7.f58065a4.f44464a7);
            this.f44458a1.f58108e7.f58065a4.f44464a7.f44451b0.add(c0050a0);
            c0050a0.f44446a5 = i;
        } else if (i2 != -1) {
            c0050a0.f44452b1.add(c0829lq.f58108e7.f58065a4.f44465a8);
            this.f44458a1.f58108e7.f58065a4.f44465a8.f44451b0.add(c0050a0);
            c0050a0.f44446a5 = -i2;
        } else {
            c0050a0.f44442a1 = true;
            c0050a0.f44452b1.add(c0829lq.f58108e7.f58065a4.f44465a8);
            this.f44458a1.f58108e7.f58065a4.f44465a8.f44451b0.add(c0050a0);
        }
        m214239b2(this.f44458a1.f58065a4.f44464a7);
        m214239b2(this.f44458a1.f58065a4.f44465a8);
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.AbstractC0055a5
    /* renamed from: a4 */
    public final void mo209953a4() {
        C0829lq c0829lq = this.f44458a1;
        int i = ((o30) c0829lq).f58730h6;
        C0050a0 c0050a0 = this.f44464a7;
        if (i == 1) {
            c0829lq.f58113f2 = c0050a0.f44447a6;
        } else {
            c0829lq.f58114f3 = c0050a0.f44447a6;
        }
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.AbstractC0055a5
    /* renamed from: a5 */
    public final void mo209954a5() {
        this.f44464a7.m209950a2();
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.AbstractC0055a5
    /* renamed from: b0 */
    public final boolean mo209955b0() {
        return false;
    }

    /* renamed from: b2 */
    public final void m214239b2(C0050a0 c0050a0) {
        C0050a0 c0050a02 = this.f44464a7;
        c0050a02.f44451b0.add(c0050a0);
        c0050a0.f44452b1.add(c0050a02);
    }
}
