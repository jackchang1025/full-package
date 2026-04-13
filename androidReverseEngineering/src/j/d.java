package j;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.util.LinkedList;

public final class d {
   public static String m;
   public static String n;
   public static d o = new d();
   public b a;
   public volatile c b;
   public File c;
   public File d;
   public FileOutputStream e;
   public FileOutputStream f;
   public int g;
   public final LinkedList h;
   public e i;
   public int j;
   public long k;
   public final Handler l;

   public d() {
      this.b = j.c.b;
      this.c = null;
      this.d = null;
      this.e = null;
      this.f = null;
      this.g = 0;
      this.h = new LinkedList();
      this.j = -1;
      this.k = 0L;
      this.l = new Handler(Looper.getMainLooper());
      this.i = new e(0);
      this.c(this.b, "录音空闲中");
   }

   public static byte[] a(d var0, long var1, int var3) {
      var0.getClass();
      long var4 = var1 + 36L;
      long var6 = 88200L * (long)var3;
      return new byte[]{
         82,
         73,
         70,
         70,
         (byte)((int)(var4 & 255L)),
         (byte)((int)(var4 >> 8 & 255L)),
         (byte)((int)(var4 >> 16 & 255L)),
         (byte)((int)(var4 >> 24 & 255L)),
         87,
         65,
         86,
         69,
         102,
         109,
         116,
         32,
         16,
         0,
         0,
         0,
         1,
         0,
         (byte)var3,
         0,
         (byte)((int)68L),
         (byte)((int)172L),
         (byte)((int)0L),
         (byte)((int)0L),
         (byte)((int)(var6 & 255L)),
         (byte)((int)(var6 >> 8 & 255L)),
         (byte)((int)(var6 >> 16 & 255L)),
         (byte)((int)(var6 >> 24 & 255L)),
         (byte)(var3 * 2),
         0,
         16,
         0,
         100,
         97,
         116,
         97,
         (byte)((int)(var1 & 255L)),
         (byte)((int)(var1 >> 8 & 255L)),
         (byte)((int)(var1 >> 16 & 255L)),
         (byte)((int)(var1 >> 24 & 255L))
      };
   }

   public static d b() {
      if (o == null) {
         o = new d();
      }

      return o;
   }

   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final void c(c var1, String var2) {
      synchronized (this){} // $VF: monitorenter 

      try {
         if (this.i != null) {
            Handler var4 = this.l;
            a var3 = new a(this, var1, var2);
            var4.post(var3);
         }
      } finally {
         // $VF: monitorexit
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final boolean d(int var1) {
      synchronized (this){} // $VF: monitorenter 

      label269: {
         Throwable var10000;
         label274: {
            try {
               if (!this.b.equals(j.c.b)) {
                  StringBuilder var47 = new StringBuilder("无法开始录制，当前状态为 ");
                  var47.append(this.b);
                  Log.w("AudioRecordManager", var47.toString());
                  break label269;
               }
            } catch (Throwable var44) {
               var10000 = var44;
               boolean var10001 = false;
               break label274;
            }

            b var2;
            try {
               var2 = this.a;
            } catch (Throwable var43) {
               var10000 = var43;
               boolean var48 = false;
               break label274;
            }

            if (var2 != null) {
               try {
                  var2.interrupt();
                  this.a = null;
               } catch (Throwable var42) {
                  var10000 = var42;
                  boolean var49 = false;
                  break label274;
               }
            }

            if (var1 >= 0 && var1 <= 10) {
               try {
                  this.j = var1;
               } catch (Throwable var41) {
                  var10000 = var41;
                  boolean var51 = false;
                  break label274;
               }
            } else {
               try {
                  this.j = 1;
               } catch (Throwable var40) {
                  var10000 = var40;
                  boolean var50 = false;
                  break label274;
               }
            }

            try {
               this.k = System.currentTimeMillis() + 1800000L;
               var2 = new b(this, this.j);
               this.a = var2;
               var2.start();
            } catch (Throwable var39) {
               var10000 = var39;
               boolean var52 = false;
               break label274;
            }

            // $VF: monitorexit
            return true;
         }

         Throwable var45 = var10000;
         // $VF: monitorexit
         throw var45;
      }

      // $VF: monitorexit
      return false;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final boolean e() {
      synchronized (this){} // $VF: monitorenter 

      Throwable var10000;
      label60: {
         boolean var1;
         try {
            var1 = this.b.equals(j.c.c);
         } catch (Throwable var8) {
            var10000 = var8;
            boolean var10001 = false;
            break label60;
         }

         if (!var1) {
            // $VF: monitorexit
            return false;
         }

         try {
            this.b = j.c.d;
            this.c(this.b, "录音结束");
         } catch (Throwable var7) {
            var10000 = var7;
            boolean var9 = false;
            break label60;
         }

         // $VF: monitorexit
         return true;
      }

      Throwable var2 = var10000;
      // $VF: monitorexit
      throw var2;
   }
}
