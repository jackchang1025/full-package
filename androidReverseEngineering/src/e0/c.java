package e0;

import a1.q;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public final class c extends ImageView {
   public static final int b = 0;
   public final b a = new b(this);

   public c(ContextWrapper var1) {
      super(var1);
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final boolean a() {
      Object var2;
      Bitmap var11;
      label55: {
         Integer var1 = com.guard.wallet.utils.d.a;
         Context var8 = com.guard.wallet.utils.g.Z();
         var2 = null;
         if (var8 != null && com.guard.wallet.utils.g.Z().getAssets() != null) {
            Exception var10000;
            label58: {
               String var3;
               try {
                  var3 = com.guard.wallet.utils.e.a();
               } catch (Exception var7) {
                  var10000 = var7;
                  boolean var10001 = false;
                  break label58;
               }

               String var9 = var3;

               label46: {
                  try {
                     if (!q.B(var3)) {
                        break label46;
                     }
                  } catch (Exception var6) {
                     var10000 = var6;
                     boolean var13 = false;
                     break label58;
                  }

                  var9 = "android.png";
               }

               try {
                  var11 = BitmapFactory.decodeStream(com.guard.wallet.utils.g.Z().getAssets().open(var9));
                  break label55;
               } catch (Exception var5) {
                  var10000 = var5;
                  boolean var14 = false;
               }
            }

            Exception var10 = var10000;
            q.s("com.guard.wallet.utils.d", var10);
         }

         var11 = null;
      }

      if (var11 != null) {
         this.setImageBitmap(var11);
         return true;
      } else {
         Drawable var12 = (Drawable)var2;
         if (com.guard.wallet.utils.g.Z() != null) {
            try {
               var12 = com.guard.wallet.utils.g.Z().getPackageManager().getApplicationIcon(com.guard.wallet.utils.g.Z().getPackageName());
            } catch (Exception var4) {
               q.s("ApplicationUtil", var4);
               var12 = (Drawable)var2;
            }
         }

         if (var12 != null) {
            this.setImageDrawable(var12);
            return true;
         } else {
            return false;
         }
      }
   }

   public void setImageURL(String var1) {
      String var2 = com.guard.wallet.utils.g.i0();
      if (!q.B(var2)) {
         String var3 = var2.concat("/").concat("block_icon.webp");
         if (q.w(var3)) {
            Bitmap var4 = q.J(var3);
            if (var4 != null) {
               this.setImageBitmap(var4);
               return;
            }
         }

         if (!q.B(var1)) {
            new Thread(new com.guard.wallet.server.a(this, var1, var3, 1)).start();
         }
      }
   }
}
