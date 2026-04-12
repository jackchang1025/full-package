package p000;

/* loaded from: classes2.dex */
public abstract class ci1 {
    private bi1 params;

    public abstract bi1 createParameters();

    public synchronized bi1 getParameters() {
        try {
            if (this.params == null) {
                this.params = createParameters();
            }
        } catch (Throwable th) {
            throw th;
        }
        return this.params;
    }
}
