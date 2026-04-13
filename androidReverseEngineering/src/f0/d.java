package f0;

import android.util.Log;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public final class d implements Runnable {
   public final InetAddress a;
   public final int b;
   public final l0.e c;
   public final com.guard.wallet.http.h d;
   public final j e;

   public d(j var1, l0.e var2, com.guard.wallet.http.h var3) {
      this.e = var1;
      this.a = null;
      this.b = 7910;
      this.c = var2;
      this.d = var3;
      super();
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   @Override
   public final void run() {
      l0.e var5 = this.c;

      IOException var2;
      a0 var3;
      ServerSocketChannel var4;
      label48: {
         try {
            var4 = ServerSocketChannel.open();
         } catch (IOException var12) {
            var2 = var12;
            var3 = null;
            var4 = null;
            break label48;
         }

         try {
            var3 = new a0(var4, 0);
         } catch (IOException var11) {
            var2 = var11;
            var3 = null;
            break label48;
         }

         IOException var10000;
         label36: {
            int var1 = this.b;
            InetAddress var13 = this.a;
            InetSocketAddress var14;
            if (var13 == null) {
               try {
                  var14 = new InetSocketAddress(var1);
               } catch (IOException var10) {
                  var10000 = var10;
                  boolean var10001 = false;
                  break label36;
               }
            } else {
               try {
                  var14 = new InetSocketAddress(var13, var1);
               } catch (IOException var9) {
                  var10000 = var9;
                  boolean var17 = false;
                  break label36;
               }
            }

            try {
               var4.socket().bind(var14);
               Selector var15 = this.e.a.a;
               SelectionKey var16 = ((ServerSocketChannel)var3.c).register(var15, 16);
               var16.attach(var5);
               com.guard.wallet.http.h var7 = this.d;
               c var6 = new c(var3, var16);
               var7.e = var6;
               var5.d.b.add(var6);
               return;
            } catch (IOException var8) {
               var10000 = var8;
               boolean var18 = false;
            }
         }

         var2 = var10000;
      }

      Log.e("NIO", "wtf", var2);
      a1.q.h(var3, var4);
      var5.a(var2);
   }
}
