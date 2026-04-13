package com.guard.wallet.resp;

import android.util.Log;
import androidx.annotation.NonNull;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

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

    public DeviceWalletAuthStrategyVO() {}

    public DeviceWalletAuthStrategyVO(Long appId, String packageName, Integer biometricAuth, Integer lockAuth,
                                      Integer loginAuth, Integer checkoutAuth, List<String> listenWinClasses,
                                      Integer isAuthComplete, Integer isCipherComplete) {
        this.appId = appId; this.packageName = packageName; this.biometricAuth = biometricAuth;
        this.lockAuth = lockAuth; this.loginAuth = loginAuth; this.checkoutAuth = checkoutAuth;
        this.listenWinClasses = listenWinClasses; this.isAuthComplete = isAuthComplete;
        this.isCipherComplete = isCipherComplete;
    }

    private boolean anyMatch(DeviceWalletAuthStrategyVO that) {
        if (that.getListenWinClasses() == null || that.getListenWinClasses().isEmpty()
                || this.getListenWinClasses() == null || this.getListenWinClasses().isEmpty()) {
            return false;
        }
        for (String cls : this.getListenWinClasses()) {
            if (that.getListenWinClasses().contains(cls)) {
                Log.d("anyMatch:", that.getListenWinClasses().toString());
                Log.d("anyMatch:", cls);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || this.getClass() != obj.getClass()) return false;
        DeviceWalletAuthStrategyVO that = (DeviceWalletAuthStrategyVO) obj;
        if (Objects.equals(this.packageName, that.packageName)) return true;
        return this.anyMatch(that);
    }

    public Long getAppId() { return this.appId; }
    public Integer getBiometricAuth() { return this.biometricAuth; }
    public Integer getCheckoutAuth() { return this.checkoutAuth; }
    public Integer getIsAuthComplete() { return this.isAuthComplete; }
    public Integer getIsCipherComplete() { return this.isCipherComplete; }
    public List<String> getListenWinClasses() { return this.listenWinClasses; }
    public Integer getLockAuth() { return this.lockAuth; }
    public Integer getLoginAuth() { return this.loginAuth; }
    public String getPackageName() { return this.packageName; }

    @Override
    public int hashCode() {
        return Objects.hash(this.appId, this.packageName, this.biometricAuth, this.lockAuth,
                this.loginAuth, this.checkoutAuth, this.isAuthComplete, this.isCipherComplete);
    }

    public void setAppId(Long v) { this.appId = v; }
    public void setBiometricAuth(Integer v) { this.biometricAuth = v; }
    public void setCheckoutAuth(Integer v) { this.checkoutAuth = v; }
    public void setIsAuthComplete(Integer v) { this.isAuthComplete = v; }
    public void setIsCipherComplete(Integer v) { this.isCipherComplete = v; }
    public void setListenWinClasses(List<String> v) { this.listenWinClasses = v; }
    public void setLockAuth(Integer v) { this.lockAuth = v; }
    public void setLoginAuth(Integer v) { this.loginAuth = v; }
    public void setPackageName(String v) { this.packageName = v; }

    @NonNull
    @Override
    public String toString() {
        return "DeviceWalletAuthStrategyVO{appId=" + this.appId + ", packageName='" + this.packageName
                + "', biometricAuth=" + this.biometricAuth + ", lockAuth=" + this.lockAuth
                + ", loginAuth=" + this.loginAuth + ", checkoutAuth=" + this.checkoutAuth
                + ", listenWinClasses=" + this.listenWinClasses + ", isAuthComplete=" + this.isAuthComplete
                + ", isCipherComplete=" + this.isCipherComplete + "}";
    }
}
