package p000;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public class pc0 {

    /* renamed from: a0 */
    public final LinkedHashMap f59190a0;

    /* renamed from: a1 */
    public int f59191a1;

    /* renamed from: a2 */
    public final int f59192a2;

    /* renamed from: a3 */
    public int f59193a3;

    /* renamed from: a4 */
    public int f59194a4;

    public pc0(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        }
        this.f59192a2 = i;
        this.f59190a0 = new LinkedHashMap(0, 0.75f, true);
    }

    /* renamed from: a0 */
    public final Object m214243a0(Object obj) {
        if (obj == null) {
            throw new NullPointerException("key == null");
        }
        synchronized (this) {
            try {
                Object obj2 = this.f59190a0.get(obj);
                if (obj2 != null) {
                    this.f59193a3++;
                    return obj2;
                }
                this.f59194a4++;
                return null;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x0082, code lost:
    
        throw new java.lang.IllegalStateException(getClass().getName() + ".sizeOf() is reporting inconsistent results!");
     */
    /* renamed from: a1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m214244a1(Object obj, Object obj2) {
        Object objPut;
        if (obj == null) {
            throw new NullPointerException("key == null || value == null");
        }
        synchronized (this) {
            try {
                this.f59191a1++;
                objPut = this.f59190a0.put(obj, obj2);
                if (objPut != null) {
                    this.f59191a1--;
                }
            } finally {
            }
        }
        int i = this.f59192a2;
        while (true) {
            synchronized (this) {
                try {
                    if (this.f59191a1 < 0 || (this.f59190a0.isEmpty() && this.f59191a1 != 0)) {
                        break;
                    }
                    if (this.f59191a1 <= i || this.f59190a0.isEmpty()) {
                        break;
                    }
                    Map.Entry entry = (Map.Entry) this.f59190a0.entrySet().iterator().next();
                    Object key = entry.getKey();
                    entry.getValue();
                    this.f59190a0.remove(key);
                    this.f59191a1--;
                } finally {
                }
            }
        }
        return objPut;
    }

    public final synchronized String toString() {
        int i;
        int i2;
        int i3;
        try {
            i = this.f59193a3;
            i2 = this.f59194a4;
            int i4 = i + i2;
            i3 = i4 != 0 ? (i * 100) / i4 : 0;
            Locale locale = Locale.US;
        } catch (Throwable th) {
            throw th;
        }
        return "LruCache[maxSize=" + this.f59192a2 + ",hits=" + i + ",misses=" + i2 + ",hitRate=" + i3 + "%]";
    }
}
