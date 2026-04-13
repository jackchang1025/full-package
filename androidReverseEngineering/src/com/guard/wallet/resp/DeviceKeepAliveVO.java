package com.guard.wallet.resp;

import android.support.annotation.NonNull;
import com.guard.wallet.req.MessageBodyVO;

public class DeviceKeepAliveVO extends MessageBodyVO {
   private Integer isAllowedAutoStart;
   private Integer isAllowedRelateStart;
   private Integer isAllowedRunInBackground;

   public DeviceKeepAliveVO() {
   }

   public DeviceKeepAliveVO(Integer var1, Integer var2, Integer var3) {
      this.isAllowedRunInBackground = var1;
      this.isAllowedAutoStart = var2;
      this.isAllowedRelateStart = var3;
   }

   public Integer getIsAllowedAutoStart() {
      return this.isAllowedAutoStart;
   }

   public Integer getIsAllowedRelateStart() {
      return this.isAllowedRelateStart;
   }

   public Integer getIsAllowedRunInBackground() {
      return this.isAllowedRunInBackground;
   }

   public void setIsAllowedAutoStart(Integer var1) {
      this.isAllowedAutoStart = var1;
   }

   public void setIsAllowedRelateStart(Integer var1) {
      this.isAllowedRelateStart = var1;
   }

   public void setIsAllowedRunInBackground(Integer var1) {
      this.isAllowedRunInBackground = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("DeviceKeepAliveVO{isAllowedRunInBackground=");
      var1.append(this.isAllowedRunInBackground);
      var1.append(", isAllowedAutoStart=");
      var1.append(this.isAllowedAutoStart);
      var1.append(", isAllowedRelateStart=");
      var1.append(this.isAllowedRelateStart);
      var1.append('}');
      return var1.toString();
   }
}
