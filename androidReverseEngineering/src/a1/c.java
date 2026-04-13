package a1;

public final class c extends Thread {
   public c() {
      super("Okio Watchdog");
      this.setDaemon(true);
   }

   @Override
   public final void run() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 00: ldc a1/d
      // 02: monitorenter
      // 03: invokestatic a1/d.h ()La1/d;
      // 06: astore 1
      // 07: aload 1
      // 08: ifnonnull 11
      // 0b: ldc a1/d
      // 0d: monitorexit
      // 0e: goto 00
      // 11: aload 1
      // 12: getstatic a1/d.j La1/d;
      // 15: if_acmpne 20
      // 18: aconst_null
      // 19: putstatic a1/d.j La1/d;
      // 1c: ldc a1/d
      // 1e: monitorexit
      // 1f: return
      // 20: ldc a1/d
      // 22: monitorexit
      // 23: aload 1
      // 24: invokevirtual a1/d.n ()V
      // 27: goto 00
      // 2a: astore 1
      // 2b: ldc a1/d
      // 2d: monitorexit
      // 2e: aload 1
      // 2f: athrow
      // 30: astore 1
      // 31: goto 00
   }
}
