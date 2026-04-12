package p000;

import java.util.Map;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: bc */
/* loaded from: classes.dex */
public final class C0129bc extends AbstractC0395cy {

    /* renamed from: a3 */
    public final /* synthetic */ int f45805a3;

    /* renamed from: a4 */
    public final /* synthetic */ Object f45806a4;

    public /* synthetic */ C0129bc(int i, Object obj) {
        this.f45805a3 = i;
        this.f45806a4 = obj;
    }

    @Override // p000.AbstractC0395cy
    /* renamed from: a1 */
    public final void mo210650a1() {
        switch (this.f45805a3) {
            case 0:
                ((C0130bd) this.f45806a4).clear();
                break;
            default:
                ((C0132bf) this.f45806a4).clear();
                break;
        }
    }

    @Override // p000.AbstractC0395cy
    /* renamed from: a2 */
    public final Object mo210651a2(int i, int i2) {
        switch (this.f45805a3) {
            case 0:
                return ((C0130bd) this.f45806a4).f60116a1[(i << 1) + i2];
            default:
                return ((C0132bf) this.f45806a4).f45862a1[i];
        }
    }

    @Override // p000.AbstractC0395cy
    /* renamed from: a3 */
    public final Map mo210652a3() {
        switch (this.f45805a3) {
            case 0:
                return (C0130bd) this.f45806a4;
            default:
                throw new UnsupportedOperationException("not a map");
        }
    }

    @Override // p000.AbstractC0395cy
    /* renamed from: a4 */
    public final int mo210653a4() {
        switch (this.f45805a3) {
            case 0:
                return ((C0130bd) this.f45806a4).f60117a2;
            default:
                return ((C0132bf) this.f45806a4).f45863a2;
        }
    }

    @Override // p000.AbstractC0395cy
    /* renamed from: a5 */
    public final int mo210654a5(Object obj) {
        switch (this.f45805a3) {
            case 0:
                return ((C0130bd) this.f45806a4).m214676a4(obj);
            default:
                C0132bf c0132bf = (C0132bf) this.f45806a4;
                return obj == null ? c0132bf.m210686a3() : c0132bf.m210685a2(obj.hashCode(), obj);
        }
    }

    @Override // p000.AbstractC0395cy
    /* renamed from: a6 */
    public final int mo210655a6(Object obj) {
        switch (this.f45805a3) {
            case 0:
                return ((C0130bd) this.f45806a4).m214678a6(obj);
            default:
                C0132bf c0132bf = (C0132bf) this.f45806a4;
                return obj == null ? c0132bf.m210686a3() : c0132bf.m210685a2(obj.hashCode(), obj);
        }
    }

    @Override // p000.AbstractC0395cy
    /* renamed from: a7 */
    public final void mo210656a7(Object obj, Object obj2) {
        switch (this.f45805a3) {
            case 0:
                ((C0130bd) this.f45806a4).put(obj, obj2);
                break;
            default:
                ((C0132bf) this.f45806a4).add(obj);
                break;
        }
    }

    @Override // p000.AbstractC0395cy
    /* renamed from: a8 */
    public final void mo210657a8(int i) {
        switch (this.f45805a3) {
            case 0:
                ((C0130bd) this.f45806a4).m214680a8(i);
                break;
            default:
                ((C0132bf) this.f45806a4).m210687a4(i);
                break;
        }
    }

    @Override // p000.AbstractC0395cy
    /* renamed from: a9 */
    public final Object mo210658a9(int i, Object obj) {
        switch (this.f45805a3) {
            case 0:
                int i2 = (i << 1) + 1;
                Object[] objArr = ((C0130bd) this.f45806a4).f60116a1;
                Object obj2 = objArr[i2];
                objArr[i2] = obj;
                return obj2;
            default:
                throw new UnsupportedOperationException("not a map");
        }
    }
}
