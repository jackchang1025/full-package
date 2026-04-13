package com.guard.wallet.condition;

import a1.q;
import android.support.annotation.NonNull;
import android.util.Log;
import b0.a;
import b0.b;
import com.guard.wallet.filter.BooleanFilter;
import j.e;
import java.io.Serializable;

public class BoolCondition implements Serializable {
   private static final String TAG = "BoolCondition";
   private boolean filterEnabled;
   private String filterKey;
   private boolean filterValue;

   public BoolCondition() {
   }

   public BoolCondition(String var1, boolean var2, boolean var3) {
      this.filterKey = var1;
      this.filterEnabled = var2;
      this.filterValue = var3;
   }

   public String getFilterKey() {
      return this.filterKey;
   }

   public boolean isFilterEnabled() {
      return this.filterEnabled;
   }

   public boolean isFilterValue() {
      return this.filterValue;
   }

   public void setFilterEnabled(boolean var1) {
      this.filterEnabled = var1;
   }

   public void setFilterKey(String var1) {
      this.filterKey = var1;
   }

   public void setFilterValue(boolean var1) {
      this.filterValue = var1;
   }

   public BooleanFilter toBooleanFilter() {
      if (this.filterEnabled && !q.B(this.filterKey)) {
         String var3 = this.filterKey;
         var3.getClass();
         int var2 = var3.hashCode();
         byte var1 = -1;
         switch (var2) {
            case -1979905218:
               if (var3.equals("contentInvalid")) {
                  var1 = 0;
               }
               break;
            case -1964681502:
               if (var3.equals("clickable")) {
                  var1 = 1;
               }
               break;
            case -1724171933:
               if (var3.equals("textSelectable")) {
                  var1 = 2;
               }
               break;
            case -1609594047:
               if (var3.equals("enabled")) {
                  var1 = 3;
               }
               break;
            case -1371475228:
               if (var3.equals("dismissable")) {
                  var1 = 4;
               }
               break;
            case -1207192371:
               if (var3.equals("multiLine")) {
                  var1 = 5;
               }
               break;
            case -994557277:
               if (var3.equals("screenReaderFocusable")) {
                  var1 = 6;
               }
               break;
            case -691041417:
               if (var3.equals("focused")) {
                  var1 = 7;
               }
               break;
            case -635423245:
               if (var3.equals("contextClickable")) {
                  var1 = 8;
               }
               break;
            case 66669991:
               if (var3.equals("scrollable")) {
                  var1 = 9;
               }
               break;
            case 398964322:
               if (var3.equals("checkable")) {
                  var1 = 10;
               }
               break;
            case 742313895:
               if (var3.equals("checked")) {
                  var1 = 11;
               }
               break;
            case 746986311:
               if (var3.equals("importantForAccessibility")) {
                  var1 = 12;
               }
               break;
            case 783360658:
               if (var3.equals("canOpenPopup")) {
                  var1 = 13;
               }
               break;
            case 795311618:
               if (var3.equals("heading")) {
                  var1 = 14;
               }
               break;
            case 918550520:
               if (var3.equals("visibleToUser")) {
                  var1 = 15;
               }
               break;
            case 997604294:
               if (var3.equals("longClickable")) {
                  var1 = 16;
               }
               break;
            case 1191572123:
               if (var3.equals("selected")) {
                  var1 = 17;
               }
               break;
            case 1216985755:
               if (var3.equals("password")) {
                  var1 = 18;
               }
               break;
            case 1602416228:
               if (var3.equals("editable")) {
                  var1 = 19;
               }
               break;
            case 1629011506:
               if (var3.equals("focusable")) {
                  var1 = 20;
               }
               break;
            case 1933057242:
               if (var3.equals("textEntryKey")) {
                  var1 = 21;
               }
               break;
            case 1976364617:
               if (var3.equals("accessibilityFocused")) {
                  var1 = 22;
               }
               break;
            case 2062895929:
               if (var3.equals("showingHintText")) {
                  var1 = 23;
               }
         }

         Object var4;
         switch (var1) {
            case 0:
               var4 = new b(4);
               break;
            case 1:
               var4 = new b(3);
               break;
            case 2:
               var4 = new b(21);
               break;
            case 3:
               var4 = new b(8);
               break;
            case 4:
               var4 = new b(6);
               break;
            case 5:
               var4 = new b(14);
               break;
            case 6:
               var4 = new b(16);
               break;
            case 7:
               var4 = new b(10);
               break;
            case 8:
               var4 = new b(5);
               break;
            case 9:
               var4 = new b(17);
               break;
            case 10:
               var4 = new b(1);
               break;
            case 11:
               var4 = new b(2);
               break;
            case 12:
               var4 = new b(12);
               break;
            case 13:
               var4 = new b(0);
               break;
            case 14:
               var4 = new b(11);
               break;
            case 15:
               var4 = new b(22);
               break;
            case 16:
               var4 = new b(13);
               break;
            case 17:
               var4 = new b(18);
               break;
            case 18:
               var4 = new b(15);
               break;
            case 19:
               var4 = new b(7);
               break;
            case 20:
               var4 = new b(9);
               break;
            case 21:
               var4 = new b(20);
               break;
            case 22:
               var4 = new e(29);
               break;
            case 23:
               var4 = new b(19);
               break;
            default:
               Log.d("BoolCondition", "未识别布尔条件");
               var4 = null;
         }

         if (var4 != null) {
            return new BooleanFilter((a)var4, this.filterValue);
         }
      }

      return null;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("BoolCondition{filterKey='");
      var1.append(this.filterKey);
      var1.append("', filterEnabled=");
      var1.append(this.filterEnabled);
      var1.append(", filterValue=");
      var1.append(this.filterValue);
      var1.append('}');
      return var1.toString();
   }
}
