package com.guard.wallet;

import android.app.Application;
import android.content.Context;

public class MyApp extends Application {
   public final void attachBaseContext(Context var1) {
      super.attachBaseContext(var1);
   }

   public final void onCreate() {
      super.onCreate();
      MainApplication.init(this);
   }

   public final void onTerminate() {
      super.onTerminate();
      MainApplication.destroy(this);
   }
}
