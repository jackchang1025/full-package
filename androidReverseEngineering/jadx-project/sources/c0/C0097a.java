package c0;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;

/* renamed from: c0.a */
/* loaded from: classes.dex */
public final class C0097a extends AbstractThreadedSyncAdapter {
    public C0097a(Context context) {
        super(context, true);
    }

    @Override // android.content.AbstractThreadedSyncAdapter
    public final void onPerformSync(Account account, Bundle bundle, String str, ContentProviderClient contentProviderClient, SyncResult syncResult) {
        Log.d("SyncAdapter", "-----------account sync onPerformSync--------");
    }
}
