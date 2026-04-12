package p000;

import java.io.File;
import java.util.Iterator;
import kotlin.jvm.internal.Lambda;
import kotlin.p030io.FileWalkDirection;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: zg */
/* loaded from: classes2.dex */
public final class C1516zg implements nz0 {

    /* renamed from: a0 */
    public final /* synthetic */ int f61542a0 = 0;

    /* renamed from: a1 */
    public final Object f61543a1;

    /* renamed from: a2 */
    public final Object f61544a2;

    public C1516zg(File file) {
        FileWalkDirection fileWalkDirection = FileWalkDirection.f57609a0;
        this.f61543a1 = file;
        this.f61544a2 = fileWalkDirection;
    }

    @Override // p000.nz0
    public final Iterator iterator() {
        switch (this.f61542a0) {
            case 0:
                return new C1514ze(this);
            case 1:
                return new t20(this);
            default:
                return new p71(this);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public C1516zg(nz0 nz0Var, h10 h10Var) {
        t60.m214695b6(nz0Var, "sequence");
        this.f61543a1 = nz0Var;
        this.f61544a2 = (Lambda) h10Var;
    }

    public C1516zg(w00 w00Var, h10 h10Var) {
        t60.m214695b6(h10Var, "getNextValue");
        this.f61543a1 = w00Var;
        this.f61544a2 = h10Var;
    }
}
