package f0;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.AbstractSelectableChannel;

public final class a0 extends n {
   public final int b;
   public final AbstractSelectableChannel c;

   @Override
   public final int read(ByteBuffer var1) {
      switch (this.b) {
         case 0:
            throw new IOException("Can't read ServerSocketChannel");
         default:
            return ((SocketChannel)this.c).read(var1);
      }
   }

   @Override
   public final long read(ByteBuffer[] var1) {
      switch (this.b) {
         case 0:
            throw new IOException("Can't read ServerSocketChannel");
         default:
            return ((SocketChannel)this.c).read(var1);
      }
   }

   @Override
   public final long read(ByteBuffer[] var1, int var2, int var3) {
      switch (this.b) {
         case 0:
            throw new IOException("Can't read ServerSocketChannel");
         default:
            return ((SocketChannel)this.c).read(var1, var2, var3);
      }
   }
}
