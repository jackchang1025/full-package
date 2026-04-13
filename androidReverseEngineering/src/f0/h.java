package f0;

public final class h implements h0.a, Runnable {
   public final j a;
   public final Runnable b;
   public final long c;
   public boolean d;

   public h(j var1, Runnable var2, long var3) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
   }

   @Override
   public final boolean cancel() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield f0/h.a Lf0/j;
      // 04: astore 2
      // 05: aload 2
      // 06: monitorenter
      // 07: aload 0
      // 08: getfield f0/h.a Lf0/j;
      // 0b: getfield f0/j.d Ljava/util/PriorityQueue;
      // 0e: aload 0
      // 0f: invokevirtual java/util/PriorityQueue.remove (Ljava/lang/Object;)Z
      // 12: istore 1
      // 13: aload 0
      // 14: iload 1
      // 15: putfield f0/h.d Z
      // 18: aload 2
      // 19: monitorexit
      // 1a: iload 1
      // 1b: ireturn
      // 1c: astore 3
      // 1d: aload 2
      // 1e: monitorexit
      // 1f: aload 3
      // 20: athrow
   }

   @Override
   public final boolean isCancelled() {
      return this.d;
   }

   @Override
   public final void run() {
      this.b.run();
   }
}
