package f0;

import java.io.Closeable;
import java.nio.channels.Selector;
import java.nio.channels.spi.AbstractSelector;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

public final class z implements Closeable {
   public final Selector a;
   public final AtomicBoolean b = new AtomicBoolean(false);
   public final Semaphore c = new Semaphore(0);

   public z(AbstractSelector var1) {
      this.a = var1;
   }

   @Override
   public final void close() {
      this.a.close();
   }
}
