package p000;

import com.storm.safe.rock.service.modules.yw5xud.umrkmgrri;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final /* synthetic */ class bl1 implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ int f45904a0;

    /* renamed from: a1 */
    public final /* synthetic */ umrkmgrri f45905a1;

    public /* synthetic */ bl1(umrkmgrri umrkmgrriVar, int i) {
        this.f45904a0 = i;
        this.f45905a1 = umrkmgrriVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.f45904a0) {
            case 0:
                umrkmgrri umrkmgrriVar = this.f45905a1;
                umrkmgrri.C0373a0 c0373a0 = umrkmgrri.f55158a3;
                t60.m214726f4("PermReqActivity", "[权限请求] 超时，自动关闭Activity");
                umrkmgrri.f55159a4 = false;
                umrkmgrriVar.finish();
                break;
            default:
                umrkmgrri umrkmgrriVar2 = this.f45905a1;
                umrkmgrri.C0373a0 c0373a02 = umrkmgrri.f55158a3;
                t60.m214704c5("PermReqActivity", "[权限请求] 全部完成，关闭Activity");
                bl1 bl1Var = umrkmgrriVar2.f55162a1;
                if (bl1Var != null) {
                    umrkmgrriVar2.f55161a0.removeCallbacks(bl1Var);
                }
                umrkmgrriVar2.f55162a1 = null;
                umrkmgrri.f55159a4 = false;
                umrkmgrriVar2.finish();
                break;
        }
    }
}
