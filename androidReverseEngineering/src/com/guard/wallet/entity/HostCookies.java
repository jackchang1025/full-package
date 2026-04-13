package com.guard.wallet.entity;

import android.support.annotation.NonNull;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import p0.l;
import p0.m;
import q0.c;

public class HostCookies implements Serializable {
   private List<CookieVO> cookies;
   private String host;

   public HostCookies() {
      this.cookies = new LinkedList<>();
   }

   public HostCookies(String var1, List<CookieVO> var2) {
      new LinkedList();
      this.host = var1;
      this.cookies = var2;
   }

   public List<CookieVO> getCookies() {
      return this.cookies;
   }

   public String getHost() {
      return this.host;
   }

   public List<m> loadForRequest() {
      LinkedList var5 = new LinkedList();
      List var6 = this.cookies;
      if (var6 != null && !var6.isEmpty()) {
         for (CookieVO var12 : this.cookies) {
            l var7 = new l();
            String var9 = var12.getName();
            if (var9 == null) {
               throw new NullPointerException("name == null");
            }

            if (!var9.trim().equals(var9)) {
               throw new IllegalArgumentException("name is not trimmed");
            }

            var7.a = var9;
            var9 = var12.getValue();
            if (var9 == null) {
               throw new NullPointerException("value == null");
            }

            if (!var9.trim().equals(var9)) {
               throw new IllegalArgumentException("value is not trimmed");
            }

            var7.b = var9;
            var9 = var12.getDomain();
            if (var9 == null) {
               throw new NullPointerException("domain == null");
            }

            String var10 = c.a(var9);
            if (var10 == null) {
               throw new IllegalArgumentException("unexpected domain: ".concat(var9));
            }

            var7.d = var10;
            var7.i = false;
            var9 = var12.getPath();
            if (!var9.startsWith("/")) {
               throw new IllegalArgumentException("path must start with '/'");
            }

            var7.e = var9;
            long var3 = var12.getExpiresAt();
            long var1 = var3;
            if (var3 <= 0L) {
               var1 = Long.MIN_VALUE;
            }

            var3 = var1;
            if (var1 > 253402300799999L) {
               var3 = 253402300799999L;
            }

            var7.c = var3;
            var7.h = true;
            if (var12.getSecure()) {
               var7.f = true;
            }

            if (var12.getHttpOnly()) {
               var7.g = true;
            }

            if (var12.getHostOnly()) {
               var7.g = true;
            }

            var5.add(new m(var7));
         }
      }

      return var5;
   }

   public void setCookies(List<CookieVO> var1) {
      this.cookies = var1;
   }

   public void setHost(String var1) {
      this.host = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("HostCookies{host='");
      var1.append(this.host);
      var1.append("', cookies=");
      var1.append(this.cookies);
      var1.append('}');
      return var1.toString();
   }
}
