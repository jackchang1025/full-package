package com.vendor.rat.keepalive.service;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.AccountAuthenticatorResponse;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

/**
 * 账号认证服务 (模块 07)
 *
 * 利用 Android AccountManager + SyncAdapter 保活
 */
public class AccountAuthenticatorService extends Service {

    private Authenticator authenticator;

    @Override
    public void onCreate() {
        super.onCreate();
        authenticator = new Authenticator(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return authenticator.getIBinder();
    }

    private static class Authenticator extends AbstractAccountAuthenticator {
        public Authenticator(android.content.Context context) {
            super(context);
        }

        @Override public Bundle addAccount(AccountAuthenticatorResponse r,
                String t, String a, String[] f, Bundle o) { return null; }
        @Override public Bundle getAuthToken(AccountAuthenticatorResponse r,
                android.accounts.Account a, String t, Bundle o) { return null; }
        @Override public String getAuthTokenLabel(String t) { return null; }
        @Override public Bundle editProperties(AccountAuthenticatorResponse r,
                String t) { return null; }
        @Override public Bundle confirmCredentials(AccountAuthenticatorResponse r,
                android.accounts.Account a, Bundle o) { return null; }
        @Override public Bundle updateCredentials(AccountAuthenticatorResponse r,
                android.accounts.Account a, String t, Bundle o) { return null; }
        @Override public Bundle hasFeatures(AccountAuthenticatorResponse r,
                android.accounts.Account a, String[] f) { return null; }
    }
}
