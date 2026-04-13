package io.github.muntashirakon.crypto.spake2;

import android.support.annotation.Nullable;
import javax.security.auth.Destroyable;
import r.a;

public class Spake2Context implements Destroyable {
   public final long a;
   public final byte[] b = new byte[32];
   public boolean c;

   static {
      System.loadLibrary("spake2");
   }

   public Spake2Context(byte[] var1, byte[] var2) {
      long var3 = allocNewContext(r.a.a(1), var1, var2);
      this.a = var3;
      if (var3 == 0L) {
         throw new UnsupportedOperationException("Could not allocate native context");
      }
   }

   private static native long allocNewContext(int var0, byte[] var1, byte[] var2);

   private static native void destroy(long var0);

   @Nullable
   private static native byte[] generateMessage(long var0, byte[] var2);

   @Nullable
   private static native byte[] processMessage(long var0, byte[] var2);

   public final byte[] a(byte[] var1) {
      if (!this.c) {
         var1 = generateMessage(this.a, var1);
         if (var1 != null) {
            System.arraycopy(var1, 0, this.b, 0, 32);
            return var1;
         } else {
            throw new IllegalStateException("Generated empty message");
         }
      } else {
         throw new IllegalStateException("The context was destroyed.");
      }
   }

   public final byte[] b(byte[] var1) {
      if (!this.c) {
         var1 = processMessage(this.a, var1);
         if (var1 != null) {
            return var1;
         } else {
            throw new IllegalStateException("No key was returned");
         }
      } else {
         throw new IllegalStateException("The context was destroyed.");
      }
   }

   @Override
   public final void destroy() {
      this.c = true;
      destroy(this.a);
   }

   @Override
   public final boolean isDestroyed() {
      return this.c;
   }
}
