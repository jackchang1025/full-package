package com.guard.wallet.resp;

import android.support.annotation.NonNull;
import com.guard.wallet.req.MessageBodyVO;
import java.util.List;

public class SyncSmsBodyVO extends MessageBodyVO {
   private String deviceId;
   private List<SmsMessageVO> messages;

   public SyncSmsBodyVO() {
   }

   public SyncSmsBodyVO(String var1, List<SmsMessageVO> var2) {
      this.deviceId = var1;
      this.messages = var2;
   }

   public String getDeviceId() {
      return this.deviceId;
   }

   public List<SmsMessageVO> getMessages() {
      return this.messages;
   }

   public void setDeviceId(String var1) {
      this.deviceId = var1;
   }

   public void setMessages(List<SmsMessageVO> var1) {
      this.messages = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("SmsBodyVO{deviceId='");
      var1.append(this.deviceId);
      var1.append("', messages=");
      var1.append(this.messages);
      var1.append('}');
      return var1.toString();
   }
}
