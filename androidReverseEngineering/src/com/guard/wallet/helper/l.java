package com.guard.wallet.helper;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public final class l implements OnClickListener {
   public final String a;
   public final String b;

   public l(String var1, String var2) {
      this.a = var1;
      this.b = var2;
   }

   public final void onClick(DialogInterface var1, int var2) {
      com.guard.wallet.utils.g.Y0(this.a, this.b);
   }
}
