package p000;

import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashSet;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class ib1 {

    /* renamed from: a0 */
    public final HashMap f56853a0 = new HashMap();

    /* renamed from: a1 */
    public final LinkedHashSet f56854a1 = new LinkedHashSet();

    /* renamed from: a2 */
    public volatile boolean f56855a2 = false;

    /* renamed from: a0 */
    public static void m213147a0(Object obj) throws IOException {
        if (obj instanceof Closeable) {
            try {
                ((Closeable) obj).close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /* renamed from: a1 */
    public void mo213148a1() {
    }
}
