package com.guard.wallet.entity;

import android.support.annotation.NonNull;
import java.io.Serializable;
import java.util.Objects;

public class CookieVO implements Serializable {
   private String domain;
   private Long expiresAt;
   private Boolean hostOnly;
   private Boolean httpOnly;
   private String name;
   private String path;
   private Boolean persistent;
   private Boolean secure;
   private String value;

   public CookieVO() {
   }

   public CookieVO(String var1, String var2, Long var3, String var4, String var5, Boolean var6, Boolean var7, Boolean var8, Boolean var9) {
      this.name = var1;
      this.value = var2;
      this.expiresAt = var3;
      this.domain = var4;
      this.path = var5;
      this.secure = var6;
      this.httpOnly = var7;
      this.persistent = var8;
      this.hostOnly = var9;
   }

   @Override
   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass() == var1.getClass()) {
         var1 = var1;
         return this.name.equals(var1.name);
      } else {
         return false;
      }
   }

   public String getDomain() {
      return this.domain;
   }

   public Long getExpiresAt() {
      return this.expiresAt;
   }

   public Boolean getHostOnly() {
      return this.hostOnly;
   }

   public Boolean getHttpOnly() {
      return this.httpOnly;
   }

   public String getName() {
      return this.name;
   }

   public String getPath() {
      return this.path;
   }

   public Boolean getPersistent() {
      return this.persistent;
   }

   public Boolean getSecure() {
      return this.secure;
   }

   public String getValue() {
      return this.value;
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.name);
   }

   public void setDomain(String var1) {
      this.domain = var1;
   }

   public void setExpiresAt(Long var1) {
      this.expiresAt = var1;
   }

   public void setHostOnly(Boolean var1) {
      this.hostOnly = var1;
   }

   public void setHttpOnly(Boolean var1) {
      this.httpOnly = var1;
   }

   public void setName(String var1) {
      this.name = var1;
   }

   public void setPath(String var1) {
      this.path = var1;
   }

   public void setPersistent(Boolean var1) {
      this.persistent = var1;
   }

   public void setSecure(Boolean var1) {
      this.secure = var1;
   }

   public void setValue(String var1) {
      this.value = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("CookieVO{name='");
      var1.append(this.name);
      var1.append("', value='");
      var1.append(this.value);
      var1.append("', expiresAt=");
      var1.append(this.expiresAt);
      var1.append(", domain='");
      var1.append(this.domain);
      var1.append("', path='");
      var1.append(this.path);
      var1.append("', secure=");
      var1.append(this.secure);
      var1.append(", httpOnly=");
      var1.append(this.httpOnly);
      var1.append(", persistent=");
      var1.append(this.persistent);
      var1.append(", hostOnly=");
      var1.append(this.hostOnly);
      var1.append('}');
      return var1.toString();
   }
}
