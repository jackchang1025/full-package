package a0;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountAuthenticatorResponse;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/* renamed from: a0.i */
/* loaded from: classes.dex */
public final class C0009i extends AbstractAccountAuthenticator {

    /* renamed from: a */
    public final Context f7a;

    public C0009i(Context context) {
        super(context);
        this.f7a = context;
    }

    @Override // android.accounts.AbstractAccountAuthenticator
    public final Bundle addAccount(AccountAuthenticatorResponse accountAuthenticatorResponse, String str, String str2, String[] strArr, Bundle bundle) {
        Intent intent = new Intent(this.f7a, (Class<?>) AccountAuthenticatorActivity.class);
        intent.putExtra("accountAuthenticatorResponse", accountAuthenticatorResponse);
        intent.putExtra("accountType", str);
        intent.putExtra("authenticator_types", str2);
        Bundle bundle2 = new Bundle();
        bundle2.putParcelable("intent", intent);
        return bundle2;
    }

    @Override // android.accounts.AbstractAccountAuthenticator
    public final Bundle confirmCredentials(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, Bundle bundle) {
        return null;
    }

    @Override // android.accounts.AbstractAccountAuthenticator
    public final Bundle editProperties(AccountAuthenticatorResponse accountAuthenticatorResponse, String str) {
        return null;
    }

    @Override // android.accounts.AbstractAccountAuthenticator
    public final Bundle getAuthToken(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String str, Bundle bundle) {
        return null;
    }

    @Override // android.accounts.AbstractAccountAuthenticator
    public final String getAuthTokenLabel(String str) {
        return null;
    }

    @Override // android.accounts.AbstractAccountAuthenticator
    public final Bundle hasFeatures(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String[] strArr) {
        return null;
    }

    @Override // android.accounts.AbstractAccountAuthenticator
    public final Bundle updateCredentials(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String str, Bundle bundle) {
        return null;
    }
}
