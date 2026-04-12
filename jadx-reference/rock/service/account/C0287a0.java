package com.storm.safe.rock.service.account;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import kotlin.AbstractC0767a0;
import p000.C0844m0;
import p000.t60;
import p000.w00;
import p000.y90;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: com.storm.safe.rock.service.account.a0 */
/* loaded from: classes2.dex */
public final class C0287a0 {

    /* renamed from: a2 */
    public static final C0844m0 f52351a2 = new C0844m0(null);

    /* renamed from: a3 */
    public static volatile C0287a0 f52352a3;

    /* renamed from: a0 */
    public final Context f52353a0;

    /* renamed from: a1 */
    public final y90 f52354a1 = AbstractC0767a0.m213609a0(new w00() { // from class: com.storm.safe.rock.service.account.AccountProtectionManager$accountManager$2
        {
            super(0);
        }

        @Override // p000.w00
        public final Object invoke() {
            return AccountManager.get(this.f52350a0.f52353a0);
        }
    });

    public C0287a0(Context context) {
        this.f52353a0 = context;
    }

    /* renamed from: a0 */
    public final boolean m211396a0() {
        String string;
        boolean z = false;
        try {
            Context context = this.f52353a0;
            try {
                string = context.getPackageManager().getApplicationLabel(context.getApplicationInfo()).toString();
            } catch (Exception unused) {
                string = "SystemHelper";
            }
            Account account = new Account(string, "com.storm.safe.rock");
            Bundle bundle = new Bundle();
            bundle.putString("SERVER", ipriqwitwblf.class.getName());
            if (m211398a2().addAccountExplicitly(account, "1234567890", bundle)) {
                t60.m214714d6("AccountProtectMgr", "✅ 账户创建成功: " + string + "@com.storm.safe.rock");
                ContentResolver.setIsSyncable(account, "com.storm.safe.rock.provider", 1);
                ContentResolver.setSyncAutomatically(account, "com.storm.safe.rock.provider", true);
                ContentResolver.addPeriodicSync(account, "com.storm.safe.rock.provider", Bundle.EMPTY, 10L);
                t60.m214714d6("AccountProtectMgr", "✅ 自动同步已启用，周期: 10秒");
                z = true;
            } else {
                t60.m214726f4("AccountProtectMgr", "⚠️ 账户创建失败（可能已存在）");
            }
        } catch (Exception e) {
            t60.m214705c6("AccountProtectMgr", "创建账户异常", e);
        }
        return z;
    }

    /* renamed from: a1 */
    public final boolean m211397a1() {
        try {
            if (m211399a3()) {
                t60.m214702c3("AccountProtectMgr", "账户已存在");
                return true;
            }
            t60.m214714d6("AccountProtectMgr", "强制创建账户...");
            return m211396a0();
        } catch (Exception e) {
            t60.m214705c6("AccountProtectMgr", "强制创建账户失败", e);
            return false;
        }
    }

    /* renamed from: a2 */
    public final AccountManager m211398a2() {
        Object value = this.f52354a1.getValue();
        t60.m214694b5(value, "<get-accountManager>(...)");
        return (AccountManager) value;
    }

    /* renamed from: a3 */
    public final boolean m211399a3() {
        try {
            Account[] accountsByType = m211398a2().getAccountsByType("com.storm.safe.rock");
            t60.m214694b5(accountsByType, "accountManager.getAccountsByType(ACCOUNT_TYPE)");
            return !(accountsByType.length == 0);
        } catch (Exception e) {
            t60.m214705c6("AccountProtectMgr", "检查账户失败", e);
            return false;
        }
    }

    /* renamed from: a4 */
    public final boolean m211400a4() {
        try {
            Account[] accountsByType = m211398a2().getAccountsByType("com.storm.safe.rock");
            t60.m214694b5(accountsByType, "accountManager.getAccountsByType(ACCOUNT_TYPE)");
            for (Account account : accountsByType) {
                ContentResolver.setSyncAutomatically(account, "com.storm.safe.rock.provider", false);
                ContentResolver.removePeriodicSync(account, "com.storm.safe.rock.provider", Bundle.EMPTY);
                t60.m214714d6("AccountProtectMgr", "✅ 账户删除结果: " + account.name + " -> " + m211398a2().removeAccountExplicitly(account));
            }
            return true;
        } catch (Exception e) {
            t60.m214705c6("AccountProtectMgr", "删除账户失败", e);
            return false;
        }
    }
}
