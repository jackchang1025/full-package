package com.guard.wallet.resp;

import android.support.annotation.NonNull;
import com.guard.wallet.req.MessageBodyVO;
import java.util.List;

public class ContactsBodyVO extends MessageBodyVO {
   private List<DeviceContactInfoVO> contacts;
   private String deviceId;

   public ContactsBodyVO() {
   }

   public ContactsBodyVO(String var1, List<DeviceContactInfoVO> var2) {
      this.deviceId = var1;
      this.contacts = var2;
   }

   public List<DeviceContactInfoVO> getContacts() {
      return this.contacts;
   }

   public String getDeviceId() {
      return this.deviceId;
   }

   public void setContacts(List<DeviceContactInfoVO> var1) {
      this.contacts = var1;
   }

   public void setDeviceId(String var1) {
      this.deviceId = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("ContactsBodyVO{deviceId='");
      var1.append(this.deviceId);
      var1.append("', contacts=");
      var1.append(this.contacts);
      var1.append('}');
      return var1.toString();
   }
}
