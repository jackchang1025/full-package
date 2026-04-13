package com.guard.wallet.req;

import androidx.annotation.NonNull;
import java.io.Serializable;

public class TouchEvent implements Serializable {
   private String codeName;
   private String deviceName;
   private String typeName;
   private String value;

   public TouchEvent() {
   }

   public TouchEvent(String var1, String var2, String var3, String var4) {
      this.deviceName = var1;
      this.typeName = var2;
      this.codeName = var3;
      this.value = var4;
   }

   public String getCodeName() {
      return this.codeName;
   }

   public String getDeviceName() {
      return this.deviceName;
   }

   public String getTypeName() {
      return this.typeName;
   }

   public String getValue() {
      return this.value;
   }

   public void setCodeName(String var1) {
      this.codeName = var1;
   }

   public void setDeviceName(String var1) {
      this.deviceName = var1;
   }

   public void setTypeName(String var1) {
      this.typeName = var1;
   }

   public void setValue(String var1) {
      this.value = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("TouchEvent{deviceName='");
      var1.append(this.deviceName);
      var1.append("', typeName='");
      var1.append(this.typeName);
      var1.append("', codeName='");
      var1.append(this.codeName);
      var1.append("', value='");
      return var1.append(this.value).append("'}").toString();
   }
}
