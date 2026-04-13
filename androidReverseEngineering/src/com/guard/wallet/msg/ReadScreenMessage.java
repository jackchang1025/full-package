package com.guard.wallet.msg;

import android.support.annotation.NonNull;
import com.guard.wallet.entity.ReadScreenWindow;
import java.io.Serializable;

public class ReadScreenMessage implements Serializable {
   private ReadScreenWindow body;
   private final Integer type = 30;

   public ReadScreenMessage(ReadScreenWindow var1) {
      this.body = var1;
   }

   public ReadScreenWindow getBody() {
      return this.body;
   }

   public Integer getType() {
      return this.type;
   }

   public void setBody(ReadScreenWindow var1) {
      this.body = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("ReadScreenMessage{type=");
      var1.append(this.type);
      var1.append(", body=");
      var1.append(this.body);
      var1.append('}');
      return var1.toString();
   }
}
