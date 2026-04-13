package c0;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;

public final class a extends AbstractThreadedSyncAdapter {
   public a(Context var1) {
      super(var1, true);
   }

   public final void onPerformSync(Account var1, Bundle var2, String var3, ContentProviderClient var4, SyncResult var5) {
      Log.d("SyncAdapter", "-----------account sync onPerformSync--------");
   }
}
