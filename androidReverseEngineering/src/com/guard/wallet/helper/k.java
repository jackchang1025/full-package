package com.guard.wallet.helper;

import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;

public final class k implements OnDismissListener {
   public final int a;

   public final void onDismiss(DialogInterface var1) {
      switch (this.a) {
         case 0:
            n.a = null;
            return;
         default:
            com.guard.wallet.utils.b.a = null;
      }
   }
}
