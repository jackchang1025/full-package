package p000;

import java.io.File;
import java.util.ArrayDeque;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.AbstractC0769a0;
import kotlin.p030io.FileWalkDirection;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: ze */
/* loaded from: classes2.dex */
public final class C1514ze extends AbstractC0769a0 {

    /* renamed from: a2 */
    public final ArrayDeque f61512a2;

    /* renamed from: a3 */
    public final /* synthetic */ C1516zg f61513a3;

    public C1514ze(C1516zg c1516zg) {
        this.f61513a3 = c1516zg;
        ArrayDeque arrayDeque = new ArrayDeque();
        this.f61512a2 = arrayDeque;
        File file = (File) c1516zg.f61543a1;
        if (file.isDirectory()) {
            arrayDeque.push(m215395a1(file));
        } else if (file.isFile()) {
            arrayDeque.push(new C1512zc(file));
        } else {
            m213610a0();
        }
    }

    /* renamed from: a1 */
    public final AbstractC1510za m215395a1(File file) {
        int iOrdinal = ((FileWalkDirection) this.f61513a3.f61544a2).ordinal();
        if (iOrdinal == 0) {
            return new C1513zd(file);
        }
        if (iOrdinal == 1) {
            return new C1511zb(file);
        }
        throw new NoWhenBranchMatchedException();
    }
}
