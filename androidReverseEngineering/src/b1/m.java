package b1;

import io.github.muntashirakon.crypto.spake2.Spake2Context;
import java.util.Arrays;
import javax.security.auth.Destroyable;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.modes.GCMBlockCipher;
import org.bouncycastle.crypto.params.AEADParameters;
import org.bouncycastle.crypto.params.KeyParameter;

public final class m implements Destroyable {
   public static final byte[] g = com.guard.wallet.utils.g.Y("adb pair client\u0000");
   public static final byte[] h = com.guard.wallet.utils.g.Y("adb pair server\u0000");
   public static final byte[] i = com.guard.wallet.utils.g.Y("adb pairing_auth aes-128-gcm key");
   public final byte[] a;
   public final Spake2Context b;
   public final byte[] c = new byte[16];
   public long d = 0L;
   public long e = 0L;
   public boolean f = false;

   public m(Spake2Context var1, byte[] var2) {
      this.b = var1;
      this.a = var1.a(var2);
   }

   public final byte[] a(byte[] var1, byte[] var2, boolean var3) {
      if (this.f) {
         return null;
      } else {
         byte[] var5 = this.c;
         AEADParameters var8 = new AEADParameters(new KeyParameter(var5), var5.length * 8, var2);
         GCMBlockCipher var7 = new GCMBlockCipher(new AESEngine());
         var7.init(var3, var8);
         var5 = new byte[var7.getOutputSize(var1.length)];
         int var4 = var7.processBytes(var1, 0, var1.length, var5, 0);

         try {
            var7.doFinal(var5, var4);
            return var5;
         } catch (InvalidCipherTextException var6) {
            return null;
         }
      }
   }

   @Override
   public final void destroy() {
      this.f = true;
      Arrays.fill(this.c, (byte)0);
      this.b.destroy();
   }

   @Override
   public final boolean isDestroyed() {
      return this.f;
   }
}
