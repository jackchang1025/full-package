package q0;

/* renamed from: q0.a */
/* loaded from: classes.dex */
public abstract class AbstractRunnableC0885a implements Runnable {

    /* renamed from: a */
    public final String f1931a;

    public AbstractRunnableC0885a(Object[] objArr, String str) {
        this.f1931a = AbstractC0887c.m1312i(objArr, str);
    }

    /* renamed from: a */
    public abstract void mo1245a();

    @Override // java.lang.Runnable
    public final void run() {
        String name = Thread.currentThread().getName();
        Thread.currentThread().setName(this.f1931a);
        try {
            mo1245a();
        } finally {
            Thread.currentThread().setName(name);
        }
    }
}
