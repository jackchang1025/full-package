package com.vendor.rat.model.resp;

// ADAPT: vendor = com.guard.wallet.resp.DeviceInfoVO
import androidx.annotation.NonNull;
import com.vendor.rat.model.req.BatteryLevelVO;
import com.vendor.rat.model.req.LockPatternVO;
import com.vendor.rat.model.req.ScreenMetricsVO;
import java.io.Serializable;
import java.util.List;

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

    public DeviceInfoVO() {
    }

    public DeviceInfoVO(String deviceId, String deviceUid, String packageName, String deviceToken,
                        String displayId, String board, String brandCode, String optimalABI,
                        String device, String fingerPrint, String serial, String manufacturer,
                        String model, String hardwareName, String product, String codeName,
                        String incremental, String release, Integer apiGrade, String osVersion,
                        String osName, String osArch, String langCode, ScreenMetricsVO screen,
                        DeviceAdminVO deviceAdmin, LockPatternVO lockPattern,
                        BatteryLevelVO batteryLevel, Integer isRoot, Integer enableDevelopment,
                        Integer enableDebug, Integer enableWifiDebug, String phoneNumber,
                        String factoryTime, String trusteeId) {
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

    // ADAPT: vendor of() static factory method omitted
    // It references vendor-specific utilities (e.c(), h.l(), g.C0(), g.B0(), q.d(), etc.)
    // Callers should construct and populate fields directly

    public Integer getApiGrade() {
        return this.apiGrade;
    }

    public BatteryLevelVO getBatteryLevel() {
        return this.batteryLevel;
    }

    public String getBoard() {
        return this.board;
    }

    public String getBrandCode() {
        return this.brandCode;
    }

    public String getCodeName() {
        return this.codeName;
    }

    public String getDevice() {
        return this.device;
    }

    public DeviceAdminVO getDeviceAdmin() {
        return this.deviceAdmin;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public String getDeviceToken() {
        return this.deviceToken;
    }

    public String getDeviceUid() {
        return this.deviceUid;
    }

    public String getDisplayId() {
        return this.displayId;
    }

    public Integer getEnableDebug() {
        return this.enableDebug;
    }

    public Integer getEnableDevelopment() {
        return this.enableDevelopment;
    }

    public Integer getEnableWifiDebug() {
        return this.enableWifiDebug;
    }

    public String getFactoryTime() {
        return this.factoryTime;
    }

    public String getFingerPrint() {
        return this.fingerPrint;
    }

    public String getHardwareName() {
        return this.hardwareName;
    }

    public String getIncremental() {
        return this.incremental;
    }

    public Integer getIsRoot() {
        return this.isRoot;
    }

    public String getLangCode() {
        return this.langCode;
    }

    public LockPatternVO getLockPattern() {
        return this.lockPattern;
    }

    public String getManufacturer() {
        return this.manufacturer;
    }

    public String getModel() {
        return this.model;
    }

    public String getOptimalABI() {
        return this.optimalABI;
    }

    public String getOsArch() {
        return this.osArch;
    }

    public String getOsName() {
        return this.osName;
    }

    public String getOsVersion() {
        return this.osVersion;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public String getProduct() {
        return this.product;
    }

    public String getRelease() {
        return this.release;
    }

    public ScreenMetricsVO getScreen() {
        return this.screen;
    }

    public String getSerial() {
        return this.serial;
    }

    public List<String> getSupportABI() {
        return this.supportABI;
    }

    public String getTrusteeId() {
        return this.trusteeId;
    }

    public void setApiGrade(Integer apiGrade) {
        this.apiGrade = apiGrade;
    }

    public void setBatteryLevel(BatteryLevelVO batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public void setDeviceAdmin(DeviceAdminVO deviceAdmin) {
        this.deviceAdmin = deviceAdmin;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public void setDeviceUid(String deviceUid) {
        this.deviceUid = deviceUid;
    }

    public void setDisplayId(String displayId) {
        this.displayId = displayId;
    }

    public void setEnableDebug(Integer enableDebug) {
        this.enableDebug = enableDebug;
    }

    public void setEnableDevelopment(Integer enableDevelopment) {
        this.enableDevelopment = enableDevelopment;
    }

    public void setEnableWifiDebug(Integer enableWifiDebug) {
        this.enableWifiDebug = enableWifiDebug;
    }

    public void setFactoryTime(String factoryTime) {
        this.factoryTime = factoryTime;
    }

    public void setFingerPrint(String fingerPrint) {
        this.fingerPrint = fingerPrint;
    }

    public void setHardwareName(String hardwareName) {
        this.hardwareName = hardwareName;
    }

    public void setIncremental(String incremental) {
        this.incremental = incremental;
    }

    public void setIsRoot(Integer isRoot) {
        this.isRoot = isRoot;
    }

    public void setLangCode(String langCode) {
        this.langCode = langCode;
    }

    public void setLockPattern(LockPatternVO lockPattern) {
        this.lockPattern = lockPattern;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setOptimalABI(String optimalABI) {
        this.optimalABI = optimalABI;
    }

    public void setOsArch(String osArch) {
        this.osArch = osArch;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public void setScreen(ScreenMetricsVO screen) {
        this.screen = screen;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public void setSupportABI(List<String> supportABI) {
        this.supportABI = supportABI;
    }

    public void setTrusteeId(String trusteeId) {
        this.trusteeId = trusteeId;
    }

    @NonNull
    public String toString() {
        return "DeviceInfoVO{deviceUid='" + this.deviceUid
                + "', deviceId='" + this.deviceId
                + "', packageName='" + this.packageName
                + "', deviceToken='" + this.deviceToken
                + "', displayId='" + this.displayId
                + "', board='" + this.board
                + "', brandCode='" + this.brandCode
                + "', supportABI='" + this.supportABI
                + "', optimalABI='" + this.optimalABI
                + "', device='" + this.device
                + "', fingerPrint='" + this.fingerPrint
                + "', serial='" + this.serial
                + "', manufacturer='" + this.manufacturer
                + "', model='" + this.model
                + "', hardwareName='" + this.hardwareName
                + "', product='" + this.product
                + "', codeName='" + this.codeName
                + "', incremental='" + this.incremental
                + "', release='" + this.release
                + "', apiGrade=" + this.apiGrade
                + ", osVersion='" + this.osVersion
                + "', osName='" + this.osName
                + "', osArch='" + this.osArch
                + "', langCode='" + this.langCode
                + "', screen=" + this.screen
                + "', deviceAdmin=" + this.deviceAdmin
                + "', lockPattern=" + this.lockPattern
                + "', isRoot=" + this.isRoot
                + "', enableDevelopment=" + this.enableDevelopment
                + "', enableDebug=" + this.enableDebug
                + "', enableWifiDebug=" + this.enableWifiDebug
                + "', phoneNumber=" + this.phoneNumber
                + "', factoryTime=" + this.factoryTime
                + "', trusteeId=" + this.trusteeId + "'}";
    }
}
