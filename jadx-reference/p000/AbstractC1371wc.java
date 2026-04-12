package p000;

import android.graphics.Rect;
import android.view.View;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: wc */
/* loaded from: classes.dex */
public abstract class AbstractC1371wc {

    /* renamed from: a0 */
    public int f60887a0;

    /* renamed from: a1 */
    public final Object f60888a1;

    /* renamed from: a2 */
    public final Object f60889a2;

    public AbstractC1371wc(pq0 pq0Var) {
        this.f60887a0 = Integer.MIN_VALUE;
        this.f60889a2 = new Rect();
        this.f60888a1 = pq0Var;
    }

    /* renamed from: a0 */
    public static AbstractC1371wc m215045a0(pq0 pq0Var, int i) {
        if (i == 0) {
            return new sl0(pq0Var, 0);
        }
        if (i == 1) {
            return new sl0(pq0Var, 1);
        }
        throw new IllegalArgumentException("invalid orientation");
    }

    /* renamed from: a1 */
    public abstract int mo214621a1(View view);

    /* renamed from: a2 */
    public abstract int mo214622a2(View view);

    /* renamed from: a3 */
    public abstract int mo214623a3(View view);

    /* renamed from: a4 */
    public abstract int mo214624a4(View view);

    /* renamed from: a5 */
    public abstract int mo214625a5();

    /* renamed from: a6 */
    public abstract int mo214626a6();

    /* renamed from: a7 */
    public abstract int mo214627a7();

    /* renamed from: a8 */
    public abstract int mo214628a8();

    /* renamed from: a9 */
    public abstract int mo214629a9();

    /* renamed from: b0 */
    public abstract int mo214630b0();

    /* renamed from: b1 */
    public abstract int mo214631b1();

    /* renamed from: b2 */
    public abstract int mo214632b2(View view);

    /* renamed from: b3 */
    public abstract int mo214633b3(View view);

    /* renamed from: b4 */
    public abstract void mo214634b4(int i);

    public AbstractC1371wc(InterfaceC1374wf interfaceC1374wf) {
        this.f60887a0 = 0;
        this.f60889a2 = new C1157qv();
        this.f60888a1 = interfaceC1374wf;
    }
}
