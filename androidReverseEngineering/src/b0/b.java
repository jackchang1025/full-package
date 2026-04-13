package b0;

import com.guard.wallet.entity.UiObject;
import com.guard.wallet.thread.j;
import f0.l;
import f0.m;
import f0.o;
import f0.q;
import f0.t;
import h0.e;
import h0.h;
import java.util.Collections;
import java.util.List;
import p0.n;
import p0.u;

public class b implements a, g0.a, g0.b, n {
   public final int d;

   @Override
   public final void a(Exception var1) {
   }

   @Override
   public void b(o var1, m var2) {
      var2.k();
   }

   @Override
   public final Boolean c(UiObject var1) {
      switch (this.d) {
         case 0:
            return var1.canOpenPopup();
         case 1:
            return var1.checkable();
         case 2:
            return var1.checked();
         case 3:
            return var1.clickable();
         case 4:
            return var1.contentInvalid();
         case 5:
            return var1.contextClickable();
         case 6:
            return var1.dismissable();
         case 7:
            return var1.editable();
         case 8:
            return var1.enabled();
         case 9:
            return var1.focusable();
         case 10:
            return var1.focused();
         case 11:
            return var1.heading();
         case 12:
            return var1.importantForAccessibility();
         case 13:
            return var1.longClickable();
         case 14:
            return var1.multiLine();
         case 15:
            return var1.password();
         case 16:
            return var1.screenReaderFocusable();
         case 17:
            return var1.scrollable();
         case 18:
            return var1.selected();
         case 19:
            return var1.showingHintText();
         case 20:
            return var1.textEntryKey();
         case 21:
            return var1.textSelectable();
         default:
            return var1.visibleToUser();
      }
   }

   @Override
   public final List d(u var1) {
      return Collections.emptyList();
   }

   @Override
   public final void e(u var1, List var2) {
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final h f(q var1) {
      switch (this.d) {
         case 27:
            m var26 = new m();
            m0.a var27 = new m0.a(var1);
            var1.h(new j(this, var26, 8));
            var1.e = new t(this, var27, var26);
            return var27;
         default:
            h var3 = new com.guard.wallet.http.h(7, 0).j(var1);
            l var2 = new l(new m0.b());
            h var24 = new h();
            synchronized (var24){} // $VF: monitorenter 

            label151: {
               Throwable var10000;
               label152: {
                  label143: {
                     try {
                        if (var24.a) {
                           break label143;
                        }
                     } catch (Throwable var23) {
                        var10000 = var23;
                        boolean var10001 = false;
                        break label152;
                     }

                     try {
                        var24.c = var3;
                     } catch (Throwable var22) {
                        var10000 = var22;
                        boolean var28 = false;
                        break label152;
                     }
                  }

                  label136:
                  try {
                     // $VF: monitorexit
                     break label151;
                  } catch (Throwable var21) {
                     var10000 = var21;
                     boolean var29 = false;
                     break label136;
                  }
               }

               while (true) {
                  Throwable var25 = var10000;

                  try {
                     // $VF: monitorexit
                     throw var25;
                  } catch (Throwable var20) {
                     var10000 = var20;
                     boolean var30 = false;
                     continue;
                  }
               }
            }

            var3.f(null, new e(var24, var2));
            return var24;
      }
   }

   @Override
   public final String toString() {
      switch (this.d) {
         case 0:
            return "canOpenPopup";
         case 1:
            return "checkable";
         case 2:
            return "checked";
         case 3:
            return "clickable";
         case 4:
            return "contentInvalid";
         case 5:
            return "contextClickable";
         case 6:
            return "dismissable";
         case 7:
            return "editable";
         case 8:
            return "enabled";
         case 9:
            return "focusable";
         case 10:
            return "focused";
         case 11:
            return "heading";
         case 12:
            return "importantForAccessibility";
         case 13:
            return "longClickable";
         case 14:
            return "multiLine";
         case 15:
            return "password";
         case 16:
            return "screenReaderFocusable";
         case 17:
            return "scrollable";
         case 18:
            return "selected";
         case 19:
            return "showingHintText";
         case 20:
            return "textEntryKey";
         case 21:
            return "textSelectable";
         case 22:
            return "visibleToUser";
         default:
            return super.toString();
      }
   }
}
