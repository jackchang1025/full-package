package p000;

import org.conscrypt.PSKKeyManager;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public class vn0 {

    /* renamed from: a0 */
    public final Object[] f60660a0;

    /* renamed from: a1 */
    public int f60661a1;

    public vn0(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("The max pool size must be > 0");
        }
        this.f60660a0 = new Object[i];
    }

    /* renamed from: a0 */
    public Object mo214932a0() {
        int i = this.f60661a1;
        if (i <= 0) {
            return null;
        }
        int i2 = i - 1;
        Object[] objArr = this.f60660a0;
        Object obj = objArr[i2];
        objArr[i2] = null;
        this.f60661a1 = i - 1;
        return obj;
    }

    /* renamed from: a1 */
    public void m214933a1(C0131be c0131be) {
        int i = this.f60661a1;
        Object[] objArr = this.f60660a0;
        if (i < objArr.length) {
            objArr[i] = c0131be;
            this.f60661a1 = i + 1;
        }
    }

    /* renamed from: a2 */
    public boolean mo214934a2(Object obj) {
        int i = 0;
        while (true) {
            int i2 = this.f60661a1;
            Object[] objArr = this.f60660a0;
            if (i >= i2) {
                if (i2 >= objArr.length) {
                    return false;
                }
                objArr[i2] = obj;
                this.f60661a1 = i2 + 1;
                return true;
            }
            if (objArr[i] == obj) {
                throw new IllegalStateException("Already in the pool!");
            }
            i++;
        }
    }

    public vn0() {
        this.f60660a0 = new Object[PSKKeyManager.MAX_KEY_LENGTH_BYTES];
    }
}
