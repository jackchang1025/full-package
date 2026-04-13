package com.guard.wallet.filter;

import a1.q;
import android.support.annotation.NonNull;
import com.guard.wallet.entity.UiObject;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Selector implements Filter {
   private static final String TAG = "com.guard.wallet.filter.Selector";
   private final List<Filter> filters = new LinkedList<>();

   public void add(Filter var1) {
      this.filters.add(var1);
   }

   @Override
   public Boolean filter(UiObject var1) {
      try {
         if (!this.filters.isEmpty()) {
            Iterator var2 = this.filters.iterator();

            while (var2.hasNext()) {
               if (!((Filter)var2.next()).filter(var1)) {
                  return Boolean.FALSE;
               }
            }

            return Boolean.TRUE;
         }
      } catch (Exception var3) {
         q.s(TAG, var3);
      }

      return Boolean.FALSE;
   }

   public int filterCount() {
      return this.filters.size();
   }

   public void remove(Filter var1) {
      this.filters.remove(var1);
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder();
      Iterator var2 = this.filters.iterator();

      while (var2.hasNext()) {
         var1.append(((Filter)var2.next()).toString());
         var1.append(".");
      }

      if (!q.B(var1.toString())) {
         var1.deleteCharAt(var1.length() - 1);
      }

      return var1.toString();
   }
}
