package j;

import a1.q;
import android.media.AudioRecord;
import android.media.audiofx.AutomaticGainControl;
import android.media.audiofx.NoiseSuppressor;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import com.guard.wallet.http.l;
import com.guard.wallet.utils.g;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

public final class b extends Thread {
   public final AudioRecord a;
   public final int b;
   public boolean c;
   public final d d;

   public b(d var1, int var2) {
      this.d = var1;
      this.b = 10240;
      this.c = false;
      if (var2 < 0 || var2 > 10) {
         var2 = 1;
      }

      int var3 = AudioRecord.getMinBufferSize(44100, 12, 2) * 1;
      this.b = var3;
      StringBuilder var5 = new StringBuilder("record buffer size = ");
      var5.append(var3);
      Log.d("AudioRecordManager", var5.toString());
      if (g.Z() != null && ContextCompat.checkSelfPermission(g.Z(), "android.permission.RECORD_AUDIO") == 0) {
         AudioRecord var4;
         label30: {
            var4 = new AudioRecord(var2, 44100, 12, 2, var3);
            this.a = var4;
            String var7;
            if (AutomaticGainControl.isAvailable()) {
               AutomaticGainControl var6 = AutomaticGainControl.create(var4.getAudioSessionId());
               if (var6 != null) {
                  var6.setEnabled(true);
                  break label30;
               }

               var7 = "AutomaticGainControl is NULL. 无法开启自动增益";
            } else {
               var7 = "AudioRecordThread: 不支持自动增益AutomaticGainControl";
            }

            Log.w("AudioRecordManager", var7);
         }

         if (NoiseSuppressor.isAvailable()) {
            NoiseSuppressor var8 = NoiseSuppressor.create(var4.getAudioSessionId());
            if (var8 != null) {
               var8.setEnabled(true);
               return;
            }
         }

         Log.w("AudioRecordManager", "AudioRecordThread: 不支持噪声抑制");
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final void a(boolean var1) {
      Exception var10000;
      label52: {
         RandomAccessFile var3;
         StringBuilder var5;
         StringBuilder var6;
         byte[] var16;
         try {
            this.d.e.close();
            this.d.f.close();
            var3 = new RandomAccessFile(this.d.d, "rw");
            d var4 = this.d;
            var16 = j.d.a(var4, var4.c.length() - 44L, this.a.getChannelCount());
            var6 = new StringBuilder();
            var6.append("header: ");
            var5 = new StringBuilder();
         } catch (Exception var11) {
            var10000 = var11;
            boolean var10001 = false;
            break label52;
         }

         for (int var2 = 0; var2 < 44; var2++) {
            try {
               var5.append(Integer.toHexString(var16[var2]));
               var5.append(",");
            } catch (Exception var10) {
               var10000 = var10;
               boolean var17 = false;
               break label52;
            }
         }

         try {
            var6.append(var5.toString());
            Log.d("AudioRecordManager", var6.toString());
            var3.seek(0L);
            var3.write(var16);
            var3.close();
            StringBuilder var12 = new StringBuilder();
            var12.append("tmpWavFile.length: ");
            var12.append(this.d.d.length());
            Log.d("AudioRecordManager", var12.toString());
            d var13 = this.d;
            var13.h.add(var13.d);
         } catch (Exception var9) {
            var10000 = var9;
            boolean var18 = false;
            break label52;
         }

         if (var1) {
            try {
               if (this.d.h.size() < 2) {
                  return;
               }
            } catch (Exception var8) {
               var10000 = var8;
               boolean var19 = false;
               break label52;
            }
         }

         try {
            d var15 = this.d;
            var15.c(var15.b, "正在上传".concat(String.valueOf(this.d.h.size())).concat("个录音文件"));
            l.A(this.d.h);
            this.d.h.clear();
            return;
         } catch (Exception var7) {
            var10000 = var7;
            boolean var20 = false;
         }
      }

      Exception var14 = var10000;
      q.s("AudioRecordManager", var14);
   }

   public final boolean b() {
      try {
         d var14 = this.d;
         File var7 = new File(j.d.m);
         var14.c = File.createTempFile("recording", ".pcm", var7);
         SimpleDateFormat var4 = new SimpleDateFormat("yyMMdd_HHmmss", Locale.CHINA);
         d var5 = this.d;
         StringBuilder var15 = new StringBuilder();
         var15.append(j.d.n);
         var15.append(File.separator);
         var15.append("r");
         Date var18 = new Date();
         var15.append(var4.format(var18));
         var15.append(".wav");
         var7 = new File(var15.toString());
         var5.d = var7;
         StringBuilder var9 = new StringBuilder("tmp file ");
         var9.append(this.d.c.getName());
         Log.d("AudioRecordManager", var9.toString());
         var14 = this.d;
         FileOutputStream var10 = new FileOutputStream(this.d.c);
         var14.e = var10;
         d var11 = this.d;
         FileOutputStream var17 = new FileOutputStream(this.d.d);
         var11.f = var17;
         byte[] var12 = new byte[44];
         this.d.f.write(var12);
         d var13 = this.d;
         var13.g = 0;
         var13.c(var13.b, "已生成录音文件:".concat(this.d.d.getName()));
         return true;
      } catch (IOException var6) {
         q.s("AudioRecordManager", var6);
         d var3 = this.d;
         c var2 = var3.b;
         String var1;
         if (!q.B(var6.getMessage())) {
            var1 = var6.getMessage();
         } else if (var6.getCause() != null) {
            var1 = var6.getCause().toString();
         } else {
            var1 = Arrays.toString((Object[])var6.getStackTrace());
         }

         var3.c(var2, "生成录音文件失败:".concat(var1));
         return false;
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   @Override
   public final void run() {
      boolean var3 = this.b();
      this.c = var3;
      if (this.a != null && var3) {
         d var5 = this.d;
         c var4 = j.c.c;
         var5.b = var4;
         var5 = this.d;
         var5.c(var5.b, q.H());
         Log.d("AudioRecordManager", "录制开始");

         label53: {
            Exception var10000;
            label60: {
               int var1;
               byte[] var6;
               try {
                  this.a.startRecording();
                  var1 = this.b;
                  var6 = new byte[var1];
               } catch (Exception var9) {
                  var10000 = var9;
                  boolean var10001 = false;
                  break label60;
               }

               while (true) {
                  int var11;
                  try {
                     if (!this.d.b.equals(var4) || !this.c || this.d.k - System.currentTimeMillis() <= 0L || this.isInterrupted()) {
                        break;
                     }

                     int var2 = this.a.read(var6, 0, var1);
                     this.d.e.write(var6, 0, var2);
                     this.d.e.flush();
                     this.d.f.write(var6, 0, var2);
                     this.d.f.flush();
                     var5 = this.d;
                     var11 = var5.g + var2;
                     var5.g = var11;
                  } catch (Exception var10) {
                     var10000 = var10;
                     boolean var18 = false;
                     break label60;
                  }

                  if (var11 > 10485760) {
                     try {
                        this.a(true);
                        this.c = this.b();
                     } catch (Exception var8) {
                        var10000 = var8;
                        boolean var19 = false;
                        break label60;
                     }
                  }
               }

               try {
                  this.a.stop();
                  this.a(false);
                  StringBuilder var13 = new StringBuilder();
                  var13.append("audio tmp PCM file len: ");
                  var13.append(this.d.c.length());
                  Log.i("AudioRecordManager", var13.toString());
                  break label53;
               } catch (Exception var7) {
                  var10000 = var7;
                  boolean var20 = false;
               }
            }

            Exception var12 = var10000;
            q.s("AudioRecordManager", var12);
            this.d.c(j.c.a, q.H());
         }

         d var14 = this.d;
         var14.c(var14.b, q.H());
         this.d.b = j.c.b;
         d var15 = this.d;
         var15.c(var15.b, q.H());
         Log.d("AudioRecordManager", "录音结束");
      }
   }
}
