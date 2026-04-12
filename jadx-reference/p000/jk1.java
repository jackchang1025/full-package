package p000;

import com.storm.safe.rock.inject.jbqfkndyx;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final /* synthetic */ class jk1 implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ int f57341a0;

    /* renamed from: a1 */
    public final /* synthetic */ jbqfkndyx f57342a1;

    public /* synthetic */ jk1(jbqfkndyx jbqfkndyxVar, int i) {
        this.f57341a0 = i;
        this.f57342a1 = jbqfkndyxVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.f57341a0) {
            case 0:
                jbqfkndyx.C0253a0.finishForPackage$lambda$3$lambda$2(this.f57342a1);
                break;
            case 1:
                jbqfkndyx.C0253a0.finishCurrent$lambda$1$lambda$0(this.f57342a1);
                break;
            default:
                jbqfkndyx jbqfkndyxVar = this.f57342a1;
                t60.m214695b6(jbqfkndyxVar, "this$0");
                jbqfkndyxVar.finishAndRemoveTask();
                break;
        }
    }
}
