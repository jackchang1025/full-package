package a1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Logger;

public abstract class l {
   public static final Logger a = Logger.getLogger(l.class.getName());

   public static a a(Socket var0) {
      if (var0 != null) {
         if (var0.getOutputStream() != null) {
            s0.j var1 = new s0.j(var0, 2);
            OutputStream var2 = var0.getOutputStream();
            if (var2 != null) {
               return new a(var1, new a(var1, var2));
            } else {
               throw new IllegalArgumentException("out == null");
            }
         } else {
            throw new IOException("socket's output stream == null");
         }
      } else {
         throw new IllegalArgumentException("socket == null");
      }
   }

   public static b b(Socket var0) {
      if (var0 != null) {
         if (var0.getInputStream() != null) {
            s0.j var1 = new s0.j(var0, 2);
            InputStream var2 = var0.getInputStream();
            if (var2 != null) {
               return new b(var1, new b(var1, var2));
            } else {
               throw new IllegalArgumentException("in == null");
            }
         } else {
            throw new IOException("socket's input stream == null");
         }
      } else {
         throw new IllegalArgumentException("socket == null");
      }
   }
}
