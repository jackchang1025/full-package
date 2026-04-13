package com.guard.wallet.helper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.util.Log;
import java.lang.ref.WeakReference;
import java.util.Objects;

public final class j implements OnClickListener {
   public final int a;

   public final void onClick(DialogInterface var1, int var2) {
      switch (this.a) {
         case 0:
            com.guard.wallet.utils.g.n1();
            return;
         case 1:
            Log.d("AccessibilityUtils", "NeutralButton click");
            WeakReference var4 = com.guard.wallet.utils.b.a;
            if (var4 != null && var4.get() != null) {
               ((AlertDialog)com.guard.wallet.utils.b.a.get()).dismiss();
            }

            com.guard.wallet.utils.b.a();
            return;
         default:
            WeakReference var3 = com.guard.wallet.utils.b.a;
            if (var3 != null && var3.get() != null) {
               ((AlertDialog)com.guard.wallet.utils.b.a.get()).dismiss();
               com.guard.wallet.utils.b.b.set(false);
            }

            if (Objects.equals(0, com.guard.wallet.utils.d.g()) && e.b.a != null && com.guard.wallet.utils.e.l()) {
               e.b.e();
            }

            com.guard.wallet.utils.g.V0();
      }
   }
}
