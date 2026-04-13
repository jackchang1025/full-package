package f0;

import java.nio.channels.ReadableByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.channels.spi.AbstractSelectableChannel;

public abstract class n implements ReadableByteChannel, ScatteringByteChannel {
   public final AbstractSelectableChannel a;

   public n(AbstractSelectableChannel var1) {
      var1.configureBlocking(false);
      this.a = var1;
   }

   @Override
   public final void close() {
      this.a.close();
   }

   @Override
   public final boolean isOpen() {
      return this.a.isOpen();
   }
}
