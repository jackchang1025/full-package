package com.guard.wallet.infra;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * StubAccountAuthenticator — 空壳账户认证器。
 * 为 SyncAdapter 框架提供必需的 AbstractAccountAuthenticator 实现，
 * 所有认证方法均返回空结果。
 *
 * vendor 原始路径: a0/i.java
 */
public final class StubAccountAuthenticator extends AbstractAccountAuthenticator {
    public final Context context;

    public StubAccountAuthenticator(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public final Bundle addAccount(AccountAuthenticatorResponse response, String accountType, String authTokenType, String[] requiredFeatures, Bundle options) {
        Bundle result = new Bundle();
        return result;
    }

    @Override
    public final Bundle confirmCredentials(AccountAuthenticatorResponse response, Account account, Bundle options) {
        return null;
    }

    @Override
    public final Bundle editProperties(AccountAuthenticatorResponse response, String accountType) {
        return null;
    }

    @Override
    public final Bundle getAuthToken(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) {
        return null;
    }

    @Override
    public final String getAuthTokenLabel(String authTokenType) {
        return null;
    }

    @Override
    public final Bundle hasFeatures(AccountAuthenticatorResponse response, Account account, String[] features) {
        return null;
    }

    @Override
    public final Bundle updateCredentials(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) {
        return null;
    }
}
