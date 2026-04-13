package b1;

import java.io.ByteArrayOutputStream;

public final class j extends ByteArrayOutputStream {
   public j(int var1) {
      super(var1);
   }

   @Override
   public final void close() {
   }

   @Override
   public final void write(byte[] var1) {
      this.write(var1, 0, var1.length);
   }
}
