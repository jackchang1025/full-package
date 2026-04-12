package p000;

import androidx.constraintlayout.core.widgets.analyzer.AbstractC0055a5;
import androidx.constraintlayout.core.widgets.analyzer.C0050a0;
import androidx.constraintlayout.core.widgets.analyzer.C0052a2;
import java.util.ArrayList;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class ps0 {

    /* renamed from: a0 */
    public AbstractC0055a5 f59333a0;

    /* renamed from: a1 */
    public ArrayList f59334a1;

    /* renamed from: a0 */
    public static long m214334a0(C0050a0 c0050a0, long j) {
        AbstractC0055a5 abstractC0055a5 = c0050a0.f44444a3;
        ArrayList arrayList = c0050a0.f44451b0;
        if (abstractC0055a5 instanceof C0052a2) {
            return j;
        }
        int size = arrayList.size();
        long jMin = j;
        for (int i = 0; i < size; i++) {
            InterfaceC1215sa interfaceC1215sa = (InterfaceC1215sa) arrayList.get(i);
            if (interfaceC1215sa instanceof C0050a0) {
                C0050a0 c0050a02 = (C0050a0) interfaceC1215sa;
                if (c0050a02.f44444a3 != abstractC0055a5) {
                    jMin = Math.min(jMin, m214334a0(c0050a02, c0050a02.f44446a5 + j));
                }
            }
        }
        C0050a0 c0050a03 = abstractC0055a5.f44465a8;
        C0050a0 c0050a04 = abstractC0055a5.f44464a7;
        if (c0050a0 != c0050a03) {
            return jMin;
        }
        long jMo209965a9 = j - abstractC0055a5.mo209965a9();
        return Math.min(Math.min(jMin, m214334a0(c0050a04, jMo209965a9)), jMo209965a9 - c0050a04.f44446a5);
    }

    /* renamed from: a1 */
    public static long m214335a1(C0050a0 c0050a0, long j) {
        AbstractC0055a5 abstractC0055a5 = c0050a0.f44444a3;
        ArrayList arrayList = c0050a0.f44451b0;
        if (abstractC0055a5 instanceof C0052a2) {
            return j;
        }
        int size = arrayList.size();
        long jMax = j;
        for (int i = 0; i < size; i++) {
            InterfaceC1215sa interfaceC1215sa = (InterfaceC1215sa) arrayList.get(i);
            if (interfaceC1215sa instanceof C0050a0) {
                C0050a0 c0050a02 = (C0050a0) interfaceC1215sa;
                if (c0050a02.f44444a3 != abstractC0055a5) {
                    jMax = Math.max(jMax, m214335a1(c0050a02, c0050a02.f44446a5 + j));
                }
            }
        }
        C0050a0 c0050a03 = abstractC0055a5.f44464a7;
        C0050a0 c0050a04 = abstractC0055a5.f44465a8;
        if (c0050a0 != c0050a03) {
            return jMax;
        }
        long jMo209965a9 = abstractC0055a5.mo209965a9() + j;
        return Math.max(Math.max(jMax, m214335a1(c0050a04, jMo209965a9)), jMo209965a9 - c0050a04.f44446a5);
    }
}
