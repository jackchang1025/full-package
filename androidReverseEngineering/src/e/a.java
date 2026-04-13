package e;

public final class a implements Runnable {
   public final int a;
   public final Object b;

   @Override
   public final void run() {
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
      // 01: getfield e/a.a I
      // 04: istore 1
      // 05: aload 0
      // 06: getfield e/a.b Ljava/lang/Object;
      // 09: astore 3
      // 0a: bipush 1
      // 0b: istore 2
      // 0c: iload 1
      // 0d: tableswitch 27 0 2 186 60 30
      // 28: goto ea
      // 2b: aload 3
      // 2c: checkcast o/k
      // 2f: astore 3
      // 30: aload 3
      // 31: getfield o/k.q Z
      // 34: ifeq 41
      // 37: aload 3
      // 38: getfield o/k.r Z
      // 3b: ifeq 41
      // 3e: goto 43
      // 41: bipush 0
      // 42: istore 2
      // 43: aload 3
      // 44: iload 2
      // 45: invokevirtual o/k.I (Z)V
      // 48: return
      // 49: aload 3
      // 4a: checkcast o/e
      // 4d: astore 4
      // 4f: aload 4
      // 51: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 54: pop
      // 55: invokestatic com/guard/wallet/service/MyAccessibilityService.P ()Lcom/guard/wallet/service/MyAccessibilityService;
      // 58: astore 5
      // 5a: aload 5
      // 5c: invokevirtual com/guard/wallet/service/MyAccessibilityService.R ()Lcom/guard/wallet/entity/RootInActiveWindowResult;
      // 5f: astore 3
      // 60: new java/util/concurrent/atomic/AtomicInteger
      // 63: dup
      // 64: bipush 10
      // 66: invokespecial java/util/concurrent/atomic/AtomicInteger.<init> (I)V
      // 69: astore 6
      // 6b: aload 3
      // 6c: invokevirtual com/guard/wallet/entity/RootInActiveWindowResult.isComplete ()Z
      // 6f: ifne 87
      // 72: aload 6
      // 74: invokevirtual java/util/concurrent/atomic/AtomicInteger.decrementAndGet ()I
      // 77: ifle 87
      // 7a: bipush 1
      // 7b: invokestatic com/guard/wallet/utils/g.T0 (I)V
      // 7e: aload 5
      // 80: invokevirtual com/guard/wallet/service/MyAccessibilityService.R ()Lcom/guard/wallet/entity/RootInActiveWindowResult;
      // 83: astore 3
      // 84: goto 6b
      // 87: aload 3
      // 88: invokevirtual com/guard/wallet/entity/RootInActiveWindowResult.getCurRoot ()Landroid/view/accessibility/AccessibilityNodeInfo;
      // 8b: astore 3
      // 8c: aload 3
      // 8d: ifnull 98
      // 90: aload 3
      // 91: invokestatic com/guard/wallet/entity/UiObject.createRoot (Landroid/view/accessibility/AccessibilityNodeInfo;)Lcom/guard/wallet/entity/UiObject;
      // 94: astore 3
      // 95: goto 9a
      // 98: aconst_null
      // 99: astore 3
      // 9a: aload 3
      // 9b: ifnull bd
      // 9e: aload 3
      // 9f: invokevirtual com/guard/wallet/entity/UiObject.packageName ()Ljava/lang/String;
      // a2: astore 5
      // a4: aload 4
      // a6: getfield o/e.j Ljava/util/concurrent/atomic/AtomicReference;
      // a9: invokevirtual java/util/concurrent/atomic/AtomicReference.get ()Ljava/lang/Object;
      // ac: aload 5
      // ae: invokestatic java/util/Objects.equals (Ljava/lang/Object;Ljava/lang/Object;)Z
      // b1: ifeq bd
      // b4: aload 4
      // b6: getfield o/e.h Ljava/util/concurrent/atomic/AtomicReference;
      // b9: aload 3
      // ba: invokevirtual java/util/concurrent/atomic/AtomicReference.set (Ljava/lang/Object;)V
      // bd: aload 4
      // bf: getfield o/e.i Ljava/util/concurrent/atomic/AtomicBoolean;
      // c2: bipush 1
      // c3: invokevirtual java/util/concurrent/atomic/AtomicBoolean.set (Z)V
      // c6: return
      // c7: invokestatic com/guard/wallet/utils/g.j ()Z
      // ca: ifne e9
      // cd: ldc com/guard/wallet/utils/h
      // cf: monitorenter
      // d0: ldc "adbCanWriteSecure"
      // d2: invokestatic com/guard/wallet/utils/h.e (Ljava/lang/String;)Z
      // d5: istore 2
      // d6: ldc com/guard/wallet/utils/h
      // d8: monitorexit
      // d9: iload 2
      // da: ifne e9
      // dd: invokestatic com/guard/wallet/utils/b.e ()V
      // e0: goto e9
      // e3: astore 3
      // e4: ldc com/guard/wallet/utils/h
      // e6: monitorexit
      // e7: aload 3
      // e8: athrow
      // e9: return
      // ea: aload 3
      // eb: checkcast com/guard/wallet/req/BlockViewVO
      // ee: invokestatic com/guard/wallet/helper/g.b (Lcom/guard/wallet/req/BlockViewVO;)V
      // f1: return
   }
}
