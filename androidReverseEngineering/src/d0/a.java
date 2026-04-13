package d0;

import a1.q;
import android.graphics.Bitmap;
import android.media.MediaFormat;
import android.os.Build.VERSION;
import android.util.Log;
import com.guard.wallet.thread.d;
import com.guard.wallet.thread.i;
import com.guard.wallet.utils.g;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Objects;
import java.util.Timer;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public final class a {
   public static final ExecutorService i = Executors.newFixedThreadPool(5);
   public static final LinkedList j = new LinkedList();
   public final ConcurrentLinkedQueue a = new ConcurrentLinkedQueue();
   public final AtomicBoolean b = new AtomicBoolean(false);
   public final AtomicLong c = new AtomicLong(0L);
   public final Timer d = new Timer();
   public final d e;
   public final u.a f;
   public final String g;
   public final MediaFormat h;

   public a(int var1, int var2) {
      StringBuilder var7 = new StringBuilder();
      var7.append(com.guard.wallet.utils.g.i0());
      String var8 = a.a.n(var7, File.separator, "CacheVideos");
      File var11 = new File(var8);
      boolean var5 = var11.exists();
      String var12;
      if (!var5) {
         var5 = var11.mkdirs();
         var12 = String.format(Locale.CHINA, "创建Video目录:%s -> %b", var8, var5);
      } else {
         if (var11.listFiles() != null) {
            File[] var13 = var11.listFiles();
            Objects.requireNonNull(var13);

            for (File var9 : var13) {
               boolean var6 = var9.delete();
               Log.d("VideoRecordManager", String.format(Locale.CHINA, "删除Video文件:%s %b", var9.getName(), var6));
            }
         }

         var12 = String.format(Locale.CHINA, "Video目录:%s", var8);
      }

      Log.d("VideoRecordManager", var12);
      String var14;
      if (var5) {
         var14 = var8;
      } else {
         var14 = null;
      }

      this.g = var14;

      try {
         MediaFormat var15 = MediaFormat.createVideoFormat("video/avc", var1, var2);
         this.h = var15;
         var15.setInteger("color-format", 2130708361);
         var15.setInteger("bitrate", var1 * var2 * 10);
         var15.setInteger("frame-rate", 25);
         var15.setInteger("i-frame-interval", 1);
         if (VERSION.SDK_INT >= 30) {
            u.a var16 = new u.a(0.5F, 20);
            this.f = var16;
         }

         d var17 = new d(this, 2);
         this.e = var17;
      } catch (Exception var10) {
         q.s("VideoRecordManager", var10);
      }
   }

   public final void a() {
      ConcurrentLinkedQueue var4 = this.a;
      if (!var4.isEmpty()) {
         String var3 = this.g;
         String var7;
         if (!q.B(var3)) {
            SimpleDateFormat var2 = new SimpleDateFormat("yyMMdd_HHmmss", Locale.CHINA);
            StringBuilder var10 = a.a.p(var3);
            var10.append(File.separator);
            var10.append("v-");
            var10.append(var2.format(new Date()));
            var10.append(".mp4");
            var3 = var10.toString();
            StringBuilder var6 = new StringBuilder("tmp video file ");
            var6.append(var3);
            Log.d("VideoRecordManager", var6.toString());
            File var5 = new File(var3);
            var7 = var3;
            if (var5.exists()) {
               boolean var1 = var5.delete();
               Log.d("VideoRecordManager", String.format(Locale.CHINA, "删除Video文件:%s -> %b", var3, var1));
               var7 = var3;
            }
         } else {
            var7 = null;
         }

         i var8 = new i(var4.toArray(new Bitmap[0]), var7, this.h);
         Future var9 = i.submit(var8);
         j.add(var9);
         this.c.set(System.currentTimeMillis());
         var4.clear();
      }
   }
}
