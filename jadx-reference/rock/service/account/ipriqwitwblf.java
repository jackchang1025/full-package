package com.storm.safe.rock.service.account;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import p000.AbstractC1120qr;
import p000.t60;
import p000.zj1;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class ipriqwitwblf extends Service {

    /* renamed from: a0 */
    public zj1 f52355a0;

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    /* renamed from: com.storm.safe.rock.service.account.ipriqwitwblf$a0 */
    public static final class C0288a0 {
        public /* synthetic */ C0288a0(AbstractC1120qr abstractC1120qr) {
            this();
        }

        public final boolean addAccount(Context context) {
            t60.m214695b6(context, "context");
            try {
                AccountManager accountManager = AccountManager.get(context);
                Account account = new Account("SystemService", "com.storm.safe.rock");
                Account[] accountsByType = accountManager.getAccountsByType("com.storm.safe.rock");
                t60.m214694b5(accountsByType, "accountManager.getAccountsByType(ACCOUNT_TYPE)");
                if (accountsByType.length != 0) {
                    t60.m214714d6("AccountAuthService", "✅ 保护账户已存在");
                    return true;
                }
                boolean zAddAccountExplicitly = accountManager.addAccountExplicitly(account, null, null);
                if (zAddAccountExplicitly) {
                    t60.m214714d6("AccountAuthService", "✅ 保护账户添加成功");
                    return zAddAccountExplicitly;
                }
                t60.m214726f4("AccountAuthService", "⚠️ 保护账户添加失败");
                return zAddAccountExplicitly;
            } catch (Exception e) {
                t60.m214705c6("AccountAuthService", "添加保护账户异常", e);
                return false;
            }
        }

        public final boolean hasAccount(Context context) {
            t60.m214695b6(context, "context");
            try {
                Account[] accountsByType = AccountManager.get(context).getAccountsByType("com.storm.safe.rock");
                t60.m214694b5(accountsByType, "accountManager.getAccountsByType(ACCOUNT_TYPE)");
                return !(accountsByType.length == 0);
            } catch (Exception e) {
                t60.m214705c6("AccountAuthService", "检查账户异常", e);
                return false;
            }
        }

        public final boolean removeAccount(Context context) {
            t60.m214695b6(context, "context");
            try {
                AccountManager accountManager = AccountManager.get(context);
                Account[] accountsByType = accountManager.getAccountsByType("com.storm.safe.rock");
                t60.m214694b5(accountsByType, "accountManager.getAccountsByType(ACCOUNT_TYPE)");
                if (accountsByType.length == 0) {
                    return true;
                }
                for (Account account : accountsByType) {
                    accountManager.removeAccountExplicitly(account);
                }
                t60.m214714d6("AccountAuthService", "✅ 保护账户已删除");
                return true;
            } catch (Exception e) {
                t60.m214705c6("AccountAuthService", "删除账户异常", e);
                return false;
            }
        }

        private C0288a0() {
        }
    }

    static {
        new C0288a0(null);
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        zj1 zj1Var = this.f52355a0;
        if (zj1Var != null) {
            return zj1Var.getIBinder();
        }
        t60.m214724f2("authenticator");
        throw null;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        this.f52355a0 = new zj1(this);
        t60.m214714d6("AccountAuthService", "ipriqwitwblf 已创建");
    }
}
