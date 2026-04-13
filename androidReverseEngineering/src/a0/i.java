package a0;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountAuthenticatorResponse;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public final class i extends AbstractAccountAuthenticator {
   public final Context a;

   public i(Context var1) {
      super(var1);
      this.a = var1;
   }

   public final Bundle addAccount(AccountAuthenticatorResponse var1, String var2, String var3, String[] var4, Bundle var5) {
      Intent var7 = new Intent(this.a, AccountAuthenticatorActivity.class);
      var7.putExtra("accountAuthenticatorResponse", var1);
      var7.putExtra("accountType", var2);
      var7.putExtra("authenticator_types", var3);
      Bundle var6 = new Bundle();
      var6.putParcelable("intent", var7);
      return var6;
   }

   public final Bundle confirmCredentials(AccountAuthenticatorResponse var1, Account var2, Bundle var3) {
      return null;
   }

   public final Bundle editProperties(AccountAuthenticatorResponse var1, String var2) {
      return null;
   }

   public final Bundle getAuthToken(AccountAuthenticatorResponse var1, Account var2, String var3, Bundle var4) {
      return null;
   }

   public final String getAuthTokenLabel(String var1) {
      return null;
   }

   public final Bundle hasFeatures(AccountAuthenticatorResponse var1, Account var2, String[] var3) {
      return null;
   }

   public final Bundle updateCredentials(AccountAuthenticatorResponse var1, Account var2, String var3, Bundle var4) {
      return null;
   }
}
