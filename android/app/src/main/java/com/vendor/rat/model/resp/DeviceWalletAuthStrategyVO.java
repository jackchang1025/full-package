package com.vendor.rat.model.resp;
import androidx.annotation.NonNull;
import android.util.Log;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
/* loaded from: classes.dex */
public class DeviceWalletAuthStrategyVO implements Serializable {
    private Long appId;
    private Integer biometricAuth;
    private Integer checkoutAuth;
    private Integer isAuthComplete;
    private Integer isCipherComplete;
    private List<String> listenWinClasses;
    private Integer lockAuth;
    private Integer loginAuth;
    private String packageName;
    public DeviceWalletAuthStrategyVO() {
    }
    public DeviceWalletAuthStrategyVO(Long l2, String str, Integer num, Integer num2, Integer num3, Integer num4, List<String> list, Integer num5, Integer num6) {
        this.appId = l2;
        this.packageName = str;
        this.biometricAuth = num;
        this.lockAuth = num2;
        this.loginAuth = num3;
        this.checkoutAuth = num4;
        this.listenWinClasses = list;
        this.isAuthComplete = num5;
        this.isCipherComplete = num6;
    }
    private boolean anyMatch(final DeviceWalletAuthStrategyVO deviceWalletAuthStrategyVO) {
        if (deviceWalletAuthStrategyVO.getListenWinClasses() == null || deviceWalletAuthStrategyVO.getListenWinClasses().isEmpty() || getListenWinClasses() == null || getListenWinClasses().isEmpty()) {
            return false;
        }
        return getListenWinClasses().stream().anyMatch(new Predicate<String>() { // from class: com.guard.wallet.resp.DeviceWalletAuthStrategyVO.1
            @Override // java.util.function.Predicate
            public boolean test(String str) {
                if (deviceWalletAuthStrategyVO.getListenWinClasses().contains(str)) {
                    Log.d("anyMatch:", deviceWalletAuthStrategyVO.getListenWinClasses().toString());
                    Log.d("anyMatch:", str);
                }
                return deviceWalletAuthStrategyVO.getListenWinClasses().contains(str);
            }
        });
    }
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        DeviceWalletAuthStrategyVO deviceWalletAuthStrategyVO = (DeviceWalletAuthStrategyVO) obj;
        return Objects.equals(this.packageName, deviceWalletAuthStrategyVO.packageName) || anyMatch(deviceWalletAuthStrategyVO);
    }
    public Long getAppId() {
        return this.appId;
    }
    public Integer getBiometricAuth() {
        return this.biometricAuth;
    }
    public Integer getCheckoutAuth() {
        return this.checkoutAuth;
    }
    public Integer getIsAuthComplete() {
        return this.isAuthComplete;
    }
    public Integer getIsCipherComplete() {
        return this.isCipherComplete;
    }
    public List<String> getListenWinClasses() {
        return this.listenWinClasses;
    }
    public Integer getLockAuth() {
        return this.lockAuth;
    }
    public Integer getLoginAuth() {
        return this.loginAuth;
    }
    public String getPackageName() {
        return this.packageName;
    }
    public int hashCode() {
        return Objects.hash(this.appId, this.packageName, this.biometricAuth, this.lockAuth, this.loginAuth, this.checkoutAuth, this.isAuthComplete, this.isCipherComplete);
    }
    public void setAppId(Long l2) {
        this.appId = l2;
    }
    public void setBiometricAuth(Integer num) {
        this.biometricAuth = num;
    }
    public void setCheckoutAuth(Integer num) {
        this.checkoutAuth = num;
    }
    public void setIsAuthComplete(Integer num) {
        this.isAuthComplete = num;
    }
    public void setIsCipherComplete(Integer num) {
        this.isCipherComplete = num;
    }
    public void setListenWinClasses(List<String> list) {
        this.listenWinClasses = list;
    }
    public void setLockAuth(Integer num) {
        this.lockAuth = num;
    }
    public void setLoginAuth(Integer num) {
        this.loginAuth = num;
    }
    public void setPackageName(String str) {
        this.packageName = str;
    }
    @NonNull
    public String toString() {
        return "DeviceWalletAuthStrategyVO{appId=" + this.appId + ", packageName='" + this.packageName + "', biometricAuth=" + this.biometricAuth + ", lockAuth=" + this.lockAuth + ", loginAuth=" + this.loginAuth + ", checkoutAuth=" + this.checkoutAuth + ", listenWinClasses=" + this.listenWinClasses + ", isAuthComplete=" + this.isAuthComplete + ", isCipherComplete=" + this.isCipherComplete + '}';
    }
}
