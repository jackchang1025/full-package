package com.guard.wallet;

import android.app.Application;
import android.content.Context;

/* loaded from: classes.dex */
public class MyApp extends Application {
    @Override // android.content.ContextWrapper
    public final void attachBaseContext(Context context) {
        super.attachBaseContext(context);
    }

    @Override // android.app.Application
    public final void onCreate() {
        super.onCreate();
        MainApplication.init(this);
    }

    @Override // android.app.Application
    public final void onTerminate() {
        super.onTerminate();
        MainApplication.destroy(this);
    }
}
