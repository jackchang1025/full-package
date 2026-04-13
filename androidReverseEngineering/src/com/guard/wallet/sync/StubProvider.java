package com.guard.wallet.sync;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

public class StubProvider extends ContentProvider {
   public final int delete(Uri var1, String var2, String[] var3) {
      return 0;
   }

   public final String getType(Uri var1) {
      return null;
   }

   public final Uri insert(Uri var1, ContentValues var2) {
      return null;
   }

   public final boolean onCreate() {
      return true;
   }

   public final Cursor query(Uri var1, String[] var2, String var3, String[] var4, String var5) {
      return null;
   }

   public final int update(Uri var1, ContentValues var2, String var3, String[] var4) {
      return 0;
   }
}
