package com.guard.wallet.condition;

import a1.q;
import android.support.annotation.NonNull;
import android.util.Log;
import com.guard.wallet.filter.IntFilter;
import j.e;
import java.io.Serializable;

public class IntCondition implements Serializable {
   private static final String TAG = "IntCondition";
   private String compare;
   private boolean filterEnabled;
   private String filterKey;
   private int filterValue;

   public IntCondition() {
   }

   public IntCondition(String var1, boolean var2, int var3, String var4) {
      this.filterKey = var1;
      this.filterEnabled = var2;
      this.filterValue = var3;
      this.compare = var4;
   }

   public String getCompare() {
      return this.compare;
   }

   public String getFilterKey() {
      return this.filterKey;
   }

   public int getFilterValue() {
      return this.filterValue;
   }

   public boolean isFilterEnabled() {
      return this.filterEnabled;
   }

   public void setCompare(String var1) {
      this.compare = var1;
   }

   public void setFilterEnabled(boolean var1) {
      this.filterEnabled = var1;
   }

   public void setFilterKey(String var1) {
      this.filterKey = var1;
   }

   public void setFilterValue(int var1) {
      this.filterValue = var1;
   }

   public IntFilter toIntFilter() {
      boolean var3 = this.filterEnabled;
      Object var6 = null;
      IntFilter var5 = (IntFilter)var6;
      if (var3) {
         var5 = (IntFilter)var6;
         if (!q.B(this.filterKey)) {
            var5 = (IntFilter)var6;
            if (this.filterValue >= 0) {
               String var4 = this.filterKey;
               var4.getClass();
               int var2 = var4.hashCode();
               byte var1 = -1;
               switch (var2) {
                  case -2105498688:
                     if (var4.equals("columnSpan")) {
                        var1 = 0;
                     }
                     break;
                  case -1591577989:
                     if (var4.equals("regionCount")) {
                        var1 = 1;
                     }
                     break;
                  case -1354837162:
                     if (var4.equals("column")) {
                        var1 = 2;
                     }
                     break;
                  case -860736679:
                     if (var4.equals("columnCount")) {
                        var1 = 3;
                     }
                     break;
                  case -713407024:
                     if (var4.equals("drawingOrder")) {
                        var1 = 4;
                     }
                     break;
                  case 113114:
                     if (var4.equals("row")) {
                        var1 = 5;
                     }
                     break;
                  case 17743701:
                     if (var4.equals("rowCount")) {
                        var1 = 6;
                     }
                     break;
                  case 95472323:
                     if (var4.equals("depth")) {
                        var1 = 7;
                     }
                     break;
                  case 346647841:
                     if (var4.equals("indexInParent")) {
                        var1 = 8;
                     }
                     break;
                  case 1386522692:
                     if (var4.equals("rowSpan")) {
                        var1 = 9;
                     }
               }

               e var7;
               switch (var1) {
                  case 0:
                     var7 = new e(8);
                     break;
                  case 1:
                     var7 = new e(17);
                     break;
                  case 2:
                     var7 = new e(7);
                     break;
                  case 3:
                     var7 = new e(6);
                     break;
                  case 4:
                     var7 = new e(11);
                     break;
                  case 5:
                     var7 = new e(20);
                     break;
                  case 6:
                     var7 = new e(19);
                     break;
                  case 7:
                     var7 = new e(9);
                     break;
                  case 8:
                     var7 = new e(14);
                     break;
                  case 9:
                     var7 = new e(21);
                     break;
                  default:
                     Log.d("IntCondition", "未识别整型条件");
                     var7 = null;
               }

               var5 = (IntFilter)var6;
               if (var7 != null) {
                  var5 = new IntFilter(var7, this.filterValue);
               }
            }
         }
      }

      return var5;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("IntCondition{filterKey='");
      var1.append(this.filterKey);
      var1.append("', filterEnabled=");
      var1.append(this.filterEnabled);
      var1.append(", filterValue=");
      var1.append(this.filterValue);
      var1.append('}');
      return var1.toString();
   }
}
