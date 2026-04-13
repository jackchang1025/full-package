package com.guard.wallet.condition;

import a.a;
import android.support.annotation.NonNull;
import java.io.Serializable;

public class StringCondition implements Serializable {
   private String contains;
   private String equals;
   private String prefix;
   private String property;
   private String regex;
   private String suffix;

   public StringCondition() {
   }

   public StringCondition(String var1, String var2, String var3, String var4, String var5, String var6) {
      this.property = var1;
      this.equals = var2;
      this.contains = var3;
      this.prefix = var4;
      this.suffix = var5;
      this.regex = var6;
   }

   public String getContains() {
      return this.contains;
   }

   public String getEquals() {
      return this.equals;
   }

   public String getPrefix() {
      return this.prefix;
   }

   public String getProperty() {
      return this.property;
   }

   public String getRegex() {
      return this.regex;
   }

   public String getSuffix() {
      return this.suffix;
   }

   public void setContains(String var1) {
      this.contains = var1;
   }

   public void setEquals(String var1) {
      this.equals = var1;
   }

   public void setPrefix(String var1) {
      this.prefix = var1;
   }

   public void setProperty(String var1) {
      this.property = var1;
   }

   public void setRegex(String var1) {
      this.regex = var1;
   }

   public void setSuffix(String var1) {
      this.suffix = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("StringCondition{property='");
      var1.append(this.property);
      var1.append("', equals='");
      var1.append(this.equals);
      var1.append("', contains='");
      var1.append(this.contains);
      var1.append("', prefix='");
      var1.append(this.prefix);
      var1.append("', suffix='");
      var1.append(this.suffix);
      var1.append("', regex='");
      return a.n(var1, this.regex, "'}");
   }
}
