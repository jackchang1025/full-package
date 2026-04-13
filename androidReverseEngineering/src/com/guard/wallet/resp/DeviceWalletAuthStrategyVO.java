package com.guard.wallet.resp;

import android.support.annotation.NonNull;
import android.util.Log;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

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

   public DeviceWalletAuthStrategyVO(
      Long var1, String var2, Integer var3, Integer var4, Integer var5, Integer var6, List<String> var7, Integer var8, Integer var9
   ) {
      this.appId = var1;
      this.packageName = var2;
      this.biometricAuth = var3;
      this.lockAuth = var4;
      this.loginAuth = var5;
      this.checkoutAuth = var6;
      this.listenWinClasses = var7;
      this.isAuthComplete = var8;
      this.isCipherComplete = var9;
   }

   private boolean anyMatch(DeviceWalletAuthStrategyVO var1) {
      return var1.getListenWinClasses() != null
            && !var1.getListenWinClasses().isEmpty()
            && this.getListenWinClasses() != null
            && !this.getListenWinClasses().isEmpty()
         ? this.getListenWinClasses().stream().anyMatch(new Predicate<String>(this, var1) {
            final DeviceWalletAuthStrategyVO this$0;
            final DeviceWalletAuthStrategyVO val$that;

            {
               this.this$0 = var1;
               this.val$that = var2;
            }

            public boolean test(String var1) {
               if (this.val$that.getListenWinClasses().contains(var1)) {
                  Log.d("anyMatch:", this.val$that.getListenWinClasses().toString());
                  Log.d("anyMatch:", var1);
               }

               return this.val$that.getListenWinClasses().contains(var1);
            }
         })
         : false;
   }

   @Override
   public boolean equals(Object var1) {
      boolean var3 = true;
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass() == var1.getClass()) {
         var1 = var1;
         boolean var2 = var3;
         if (!Objects.equals(this.packageName, var1.packageName)) {
            if (this.anyMatch(var1)) {
               var2 = var3;
            } else {
               var2 = false;
            }
         }

         return var2;
      } else {
         return false;
      }
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

   @Override
   public int hashCode() {
      return Objects.hash(
         this.appId, this.packageName, this.biometricAuth, this.lockAuth, this.loginAuth, this.checkoutAuth, this.isAuthComplete, this.isCipherComplete
      );
   }

   public void setAppId(Long var1) {
      this.appId = var1;
   }

   public void setBiometricAuth(Integer var1) {
      this.biometricAuth = var1;
   }

   public void setCheckoutAuth(Integer var1) {
      this.checkoutAuth = var1;
   }

   public void setIsAuthComplete(Integer var1) {
      this.isAuthComplete = var1;
   }

   public void setIsCipherComplete(Integer var1) {
      this.isCipherComplete = var1;
   }

   public void setListenWinClasses(List<String> var1) {
      this.listenWinClasses = var1;
   }

   public void setLockAuth(Integer var1) {
      this.lockAuth = var1;
   }

   public void setLoginAuth(Integer var1) {
      this.loginAuth = var1;
   }

   public void setPackageName(String var1) {
      this.packageName = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("DeviceWalletAuthStrategyVO{appId=");
      var1.append(this.appId);
      var1.append(", packageName='");
      var1.append(this.packageName);
      var1.append("', biometricAuth=");
      var1.append(this.biometricAuth);
      var1.append(", lockAuth=");
      var1.append(this.lockAuth);
      var1.append(", loginAuth=");
      var1.append(this.loginAuth);
      var1.append(", checkoutAuth=");
      var1.append(this.checkoutAuth);
      var1.append(", listenWinClasses=");
      var1.append(this.listenWinClasses);
      var1.append(", isAuthComplete=");
      var1.append(this.isAuthComplete);
      var1.append(", isCipherComplete=");
      var1.append(this.isCipherComplete);
      var1.append('}');
      return var1.toString();
   }
}
