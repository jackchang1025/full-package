package p000;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: jj */
/* loaded from: classes2.dex */
public abstract class AbstractC0720jj extends AbstractC0719ji {
    /* renamed from: h0 */
    public static void m213312h0(List list) {
        if (list.size() > 1) {
            Collections.sort(list);
        }
    }

    /* renamed from: h1 */
    public static void m213313h1(List list, Comparator comparator) {
        if (list.size() > 1) {
            Collections.sort(list, comparator);
        }
    }
}
