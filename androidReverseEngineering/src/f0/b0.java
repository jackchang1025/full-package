package f0;

import java.util.LinkedList;
import java.util.WeakHashMap;
import java.util.concurrent.Semaphore;

public final class b0 extends LinkedList {
   public static final WeakHashMap c = new WeakHashMap();
   public com.guard.wallet.http.h a;
   public final Semaphore b = new Semaphore(0);

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final Runnable a() {
      synchronized (this){} // $VF: monitorenter 

      Throwable var10000;
      label97: {
         Runnable var1;
         try {
            if (this.isEmpty()) {
               // $VF: monitorexit
               return null;
            }

            var1 = (Runnable)super.remove();
            // $VF: monitorexit
         } catch (Throwable var13) {
            var10000 = var13;
            boolean var10001 = false;
            break label97;
         }

         label90:
         try {
            return var1;
         } catch (Throwable var12) {
            var10000 = var12;
            boolean var15 = false;
            break label90;
         }
      }

      while (true) {
         Throwable var14 = var10000;

         try {
            // $VF: monitorexit
            throw var14;
         } catch (Throwable var11) {
            var10000 = var11;
            boolean var16 = false;
            continue;
         }
      }
   }

   @Override
   public final boolean add(Object param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.NullPointerException: Cannot read field "id" because the return value of "org.jetbrains.java.decompiler.modules.decompiler.flow.FlattenStatementsHelper.getDirectNode(org.jetbrains.java.decompiler.modules.decompiler.stats.Statement)" is null
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:179)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.processStatement(ExprProcessor.java:112)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.getFinallyInformation(FinallyProcessor.java:136)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:85)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 00: aload 1
      // 01: checkcast java/lang/Runnable
      // 04: astore 1
      // 05: aload 0
      // 06: monitorenter
      // 07: aload 0
      // 08: aload 1
      // 09: invokespecial java/util/LinkedList.add (Ljava/lang/Object;)Z
      // 0c: istore 2
      // 0d: aload 0
      // 0e: monitorexit
      // 0f: iload 2
      // 10: ireturn
      // 11: astore 1
      // 12: aload 0
      // 13: monitorexit
      // 14: aload 1
      // 15: athrow
   }

   @Override
   public final boolean remove(Object param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.NullPointerException: Cannot read field "id" because the return value of "org.jetbrains.java.decompiler.modules.decompiler.flow.FlattenStatementsHelper.getDirectNode(org.jetbrains.java.decompiler.modules.decompiler.stats.Statement)" is null
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:179)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.processStatement(ExprProcessor.java:112)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.getFinallyInformation(FinallyProcessor.java:136)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:85)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 00: aload 0
      // 01: monitorenter
      // 02: aload 0
      // 03: aload 1
      // 04: invokespecial java/util/LinkedList.remove (Ljava/lang/Object;)Z
      // 07: istore 2
      // 08: aload 0
      // 09: monitorexit
      // 0a: iload 2
      // 0b: ireturn
      // 0c: astore 1
      // 0d: aload 0
      // 0e: monitorexit
      // 0f: aload 1
      // 10: athrow
   }
}
