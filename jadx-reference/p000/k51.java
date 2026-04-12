package p000;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class k51 extends i51 {

    /* renamed from: a2 */
    public final Runnable f57457a2;

    public k51(Runnable runnable, long j, j51 j51Var) {
        super(j, j51Var);
        this.f57457a2 = runnable;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            this.f57457a2.run();
        } finally {
            this.f56799a1.getClass();
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("Task[");
        Runnable runnable = this.f57457a2;
        sb.append(runnable.getClass().getSimpleName());
        sb.append('@');
        sb.append(AbstractC1117qo.m214435d1(runnable));
        sb.append(", ");
        sb.append(this.f56798a0);
        sb.append(", ");
        sb.append(this.f56799a1);
        sb.append(']');
        return sb.toString();
    }
}
