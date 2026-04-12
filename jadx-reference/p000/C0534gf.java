package p000;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: gf */
/* loaded from: classes2.dex */
public final class C0534gf extends C0730jt {

    /* renamed from: a2 */
    public static final AtomicIntegerFieldUpdater f56453a2 = AtomicIntegerFieldUpdater.newUpdater(C0534gf.class, "_resumed");
    private volatile int _resumed;

    public C0534gf(C0530gb c0530gb, Throwable th, boolean z) {
        super(th, z);
        this._resumed = 0;
    }
}
