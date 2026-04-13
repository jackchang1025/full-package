package com.guard.wallet.req;

public class MessageRecordVO<V extends MessageBodyVO> {
   private String deviceId;
   private V extraBody;
   private String intentCode;

   public MessageRecordVO() {
   }

   public MessageRecordVO(String var1, String var2, V var3) {
      this.intentCode = var1;
      this.deviceId = var2;
      this.extraBody = (V)var3;
   }

   public String getDeviceId() {
      return this.deviceId;
   }

   public V getExtraBody() {
      return this.extraBody;
   }

   public String getIntentCode() {
      return this.intentCode;
   }

   public void setDeviceId(String var1) {
      this.deviceId = var1;
   }

   public void setExtraBody(V var1) {
      this.extraBody = (V)var1;
   }

   public void setIntentCode(String var1) {
      this.intentCode = var1;
   }
}
