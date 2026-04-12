package com.storm.safe.rock.manager;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.util.StringUtil;
import java.util.Iterator;
import java.util.List;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import p000.AbstractC0716jf;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.kg1;
import p000.l10;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.manager.PermissionGranter$openMiuiPermissionEditor$1", m214403f = "PermissionGranter.kt", m214404l = {}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class PermissionGranter$openMiuiPermissionEditor$1 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public final /* synthetic */ C0260a2 f52022a1;

    /* renamed from: a2 */
    public final /* synthetic */ String f52023a2;

    /* renamed from: a3 */
    public final /* synthetic */ boolean f52024a3;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PermissionGranter$openMiuiPermissionEditor$1(C0260a2 c0260a2, String str, boolean z, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f52022a1 = c0260a2;
        this.f52023a2 = str;
        this.f52024a3 = z;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new PermissionGranter$openMiuiPermissionEditor$1(this.f52022a1, this.f52023a2, this.f52024a3, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) throws Throwable {
        PermissionGranter$openMiuiPermissionEditor$1 permissionGranter$openMiuiPermissionEditor$1 = (PermissionGranter$openMiuiPermissionEditor$1) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2);
        C1351vv c1351vv = C1351vv.f60710b1;
        permissionGranter$openMiuiPermissionEditor$1.invokeSuspend(c1351vv);
        return c1351vv;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        kg1.m213544f4(obj);
        C0260a2 c0260a2 = this.f52022a1;
        dqtvuisjd dqtvuisjdVar = c0260a2.f52109a1;
        try {
            Intent intent = new Intent();
            intent.addFlags(268435456);
            intent.addCategory("android.intent.category.DEFAULT");
            intent.setComponent(new ComponentName("com.android.permissioncontroller", "com.android.permissioncontroller.permission.ui.ManagePermissionsActivity"));
            intent.putExtra("android.intent.extra.PACKAGE_NAME", dqtvuisjdVar.getPackageName());
            Intent intent2 = new Intent();
            intent2.addFlags(268435456);
            intent2.addCategory("android.intent.category.DEFAULT");
            intent2.setComponent(new ComponentName("com.google.android.permissioncontroller", "com.android.permissioncontroller.permission.ui.ManagePermissionsActivity"));
            intent2.putExtra("android.intent.extra.PACKAGE_NAME", dqtvuisjdVar.getPackageName());
            Intent intent3 = new Intent();
            intent3.addFlags(268435456);
            intent3.addCategory("android.intent.category.DEFAULT");
            intent3.setComponent(new ComponentName("com.android.settings", "com.android.settings.Settings$AppPermissionSettingsActivity"));
            intent3.setData(Uri.parse("package:" + dqtvuisjdVar.getPackageName()));
            List listM213306g5 = AbstractC0716jf.m213306g5(intent, intent2, intent3);
            Intent intent4 = new Intent("miui.intent.action.APP_PERM_EDITOR");
            intent4.addFlags(268435456);
            intent4.addCategory("android.intent.category.DEFAULT");
            intent4.putExtra("extra_pkgname", dqtvuisjdVar.getPackageName());
            intent4.putExtra("com.miui.intent.extra.PACKAGE_NAME", dqtvuisjdVar.getPackageName());
            Intent intent5 = new Intent();
            intent5.addFlags(268435456);
            intent5.addCategory("android.intent.category.DEFAULT");
            intent5.setComponent(new ComponentName(StringUtil.m212470a0("KFYcdEAxGScZIi5aBChELBUtUj8/XAM="), "com.miui.permcenter.permissions.PermissionsEditorActivity"));
            intent5.putExtra("extra_pkgname", dqtvuisjdVar.getPackageName());
            Intent intent6 = new Intent();
            intent6.addFlags(268435456);
            intent6.addCategory("android.intent.category.DEFAULT");
            intent6.setComponent(new ComponentName(StringUtil.m212470a0("KFYcdEAxGScZIi5aBChELBUtUj8/XAM="), "com.miui.permcenter.permissions.AppPermissionsEditorActivity"));
            intent6.putExtra("extra_pkgname", dqtvuisjdVar.getPackageName());
            List listM213306g52 = AbstractC0716jf.m213306g5(intent4, intent5, intent6);
            Intent intent7 = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent7.addFlags(268435456);
            intent7.addCategory("android.intent.category.DEFAULT");
            intent7.setData(Uri.parse("package:" + dqtvuisjdVar.getPackageName()));
            Intent intent8 = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent8.addFlags(268435456);
            intent8.addCategory("android.intent.category.DEFAULT");
            intent8.setPackage("com.android.settings");
            intent8.setData(Uri.parse("package:" + dqtvuisjdVar.getPackageName()));
            Intent intent9 = new Intent("android.settings.MANAGE_APPLICATIONS_SETTINGS");
            intent9.addFlags(268435456);
            intent9.addCategory("android.intent.category.DEFAULT");
            intent9.setPackage("com.android.settings");
            List listM213306g53 = AbstractC0716jf.m213306g5(intent7, intent8, intent9);
            String str = this.f52023a2;
            if (this.f52024a3) {
                Iterator it = listM213306g52.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        Iterator it2 = listM213306g53.iterator();
                        while (true) {
                            if (it2.hasNext()) {
                                if (c0260a2.m211335h8((Intent) it2.next()).equals(Boolean.TRUE)) {
                                    c0260a2.m211328h1(str);
                                    break;
                                }
                            }
                        }
                    } else if (c0260a2.m211335h8((Intent) it.next()).equals(Boolean.TRUE)) {
                        c0260a2.m211328h1(str);
                        break;
                    }
                }
            } else {
                Iterator it3 = listM213306g5.iterator();
                while (true) {
                    if (!it3.hasNext()) {
                        Iterator it4 = listM213306g53.iterator();
                        while (true) {
                            if (it4.hasNext()) {
                                if (c0260a2.m211335h8((Intent) it4.next()).equals(Boolean.TRUE)) {
                                    c0260a2.m211328h1(str);
                                    break;
                                }
                            }
                        }
                    } else if (c0260a2.m211335h8((Intent) it3.next()).equals(Boolean.TRUE)) {
                        c0260a2.m211328h1(str);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            t60.m214705c6("PermissionGranter", "❌ [权限] 继续打开权限编辑页流程失败", e);
        }
        return C1351vv.f60710b1;
    }
}
