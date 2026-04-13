package o;

// $VF: synthetic class
public final class a implements Runnable {
   public final int a;
   public final Object b;

   @Override
   public final void run() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 000: aload 0
      // 001: getfield o/a.a I
      // 004: istore 1
      // 005: bipush 0
      // 006: istore 4
      // 008: iload 1
      // 009: tableswitch 51 0 8 1283 1163 735 628 521 392 381 356 54
      // 03c: goto 584
      // 03f: aload 0
      // 040: getfield o/a.b Ljava/lang/Object;
      // 043: checkcast s0/h
      // 046: astore 14
      // 048: getstatic s0/h.g Ljava/util/concurrent/ThreadPoolExecutor;
      // 04b: astore 12
      // 04d: aload 14
      // 04f: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 052: pop
      // 053: invokestatic java/lang/System.nanoTime ()J
      // 056: lstore 10
      // 058: aload 14
      // 05a: monitorenter
      // 05b: aload 14
      // 05d: getfield s0/h.d Ljava/util/ArrayDeque;
      // 060: invokevirtual java/util/ArrayDeque.iterator ()Ljava/util/Iterator;
      // 063: astore 15
      // 065: ldc2_w -9223372036854775808
      // 068: lstore 6
      // 06a: aconst_null
      // 06b: astore 12
      // 06d: bipush 0
      // 06e: istore 1
      // 06f: bipush 0
      // 070: istore 2
      // 071: aload 15
      // 073: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 078: ifeq 0be
      // 07b: aload 15
      // 07d: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 082: checkcast s0/g
      // 085: astore 13
      // 087: aload 14
      // 089: aload 13
      // 08b: lload 10
      // 08d: invokevirtual s0/h.b (Ls0/g;J)I
      // 090: ifle 099
      // 093: iinc 2 1
      // 096: goto 071
      // 099: iload 1
      // 09a: bipush 1
      // 09b: iadd
      // 09c: istore 3
      // 09d: lload 10
      // 09f: aload 13
      // 0a1: getfield s0/g.q J
      // 0a4: lsub
      // 0a5: lstore 8
      // 0a7: iload 3
      // 0a8: istore 1
      // 0a9: lload 8
      // 0ab: lload 6
      // 0ad: lcmp
      // 0ae: ifle 071
      // 0b1: aload 13
      // 0b3: astore 12
      // 0b5: lload 8
      // 0b7: lstore 6
      // 0b9: iload 3
      // 0ba: istore 1
      // 0bb: goto 071
      // 0be: aload 14
      // 0c0: getfield s0/h.b J
      // 0c3: lstore 8
      // 0c5: lload 6
      // 0c7: lload 8
      // 0c9: lcmp
      // 0ca: ifge 109
      // 0cd: iload 1
      // 0ce: aload 14
      // 0d0: getfield s0/h.a I
      // 0d3: if_icmple 0d9
      // 0d6: goto 109
      // 0d9: iload 1
      // 0da: ifle 0ea
      // 0dd: lload 8
      // 0df: lload 6
      // 0e1: lsub
      // 0e2: lstore 6
      // 0e4: aload 14
      // 0e6: monitorexit
      // 0e7: goto 122
      // 0ea: iload 2
      // 0eb: ifle 0f8
      // 0ee: aload 14
      // 0f0: monitorexit
      // 0f1: lload 8
      // 0f3: lstore 6
      // 0f5: goto 122
      // 0f8: aload 14
      // 0fa: bipush 0
      // 0fb: putfield s0/h.f Z
      // 0fe: aload 14
      // 100: monitorexit
      // 101: ldc2_w -1
      // 104: lstore 6
      // 106: goto 122
      // 109: aload 14
      // 10b: getfield s0/h.d Ljava/util/ArrayDeque;
      // 10e: aload 12
      // 110: invokevirtual java/util/ArrayDeque.remove (Ljava/lang/Object;)Z
      // 113: pop
      // 114: aload 14
      // 116: monitorexit
      // 117: aload 12
      // 119: getfield s0/g.e Ljava/net/Socket;
      // 11c: invokestatic q0/c.d (Ljava/net/Socket;)V
      // 11f: lconst_0
      // 120: lstore 6
      // 122: lload 6
      // 124: ldc2_w -1
      // 127: lcmp
      // 128: ifne 12c
      // 12b: return
      // 12c: lload 6
      // 12e: lconst_0
      // 12f: lcmp
      // 130: ifle 053
      // 133: lload 6
      // 135: ldc2_w 1000000
      // 138: ldiv
      // 139: lstore 8
      // 13b: aload 14
      // 13d: monitorenter
      // 13e: lload 6
      // 140: ldc2_w 1000000
      // 143: lload 8
      // 145: lmul
      // 146: lsub
      // 147: l2i
      // 148: istore 1
      // 149: aload 14
      // 14b: lload 8
      // 14d: iload 1
      // 14e: invokevirtual java/lang/Object.wait (JI)V
      // 151: goto 159
      // 154: astore 12
      // 156: goto 15f
      // 159: aload 14
      // 15b: monitorexit
      // 15c: goto 053
      // 15f: aload 14
      // 161: monitorexit
      // 162: aload 12
      // 164: athrow
      // 165: astore 12
      // 167: aload 14
      // 169: monitorexit
      // 16a: aload 12
      // 16c: athrow
      // 16d: aload 0
      // 16e: getfield o/a.b Ljava/lang/Object;
      // 171: checkcast l0/k
      // 174: invokevirtual l0/k.i ()Lg0/c;
      // 177: astore 12
      // 179: aload 12
      // 17b: ifnull 185
      // 17e: aload 12
      // 180: invokeinterface g0/c.c ()V 1
      // 185: return
      // 186: aload 0
      // 187: getfield o/a.b Ljava/lang/Object;
      // 18a: checkcast f0/r
      // 18d: invokevirtual f0/r.e ()V
      // 190: return
      // 191: aload 0
      // 192: getfield o/a.b Ljava/lang/Object;
      // 195: checkcast f0/z
      // 198: astore 13
      // 19a: getstatic f0/j.f Lf0/j;
      // 19d: astore 12
      // 19f: aload 13
      // 1a1: getfield f0/z.c Ljava/util/concurrent/Semaphore;
      // 1a4: astore 14
      // 1a6: aload 14
      // 1a8: invokevirtual java/util/concurrent/Semaphore.tryAcquire ()Z
      // 1ab: istore 4
      // 1ad: aload 13
      // 1af: getfield f0/z.a Ljava/nio/channels/Selector;
      // 1b2: astore 12
      // 1b4: aload 12
      // 1b6: invokevirtual java/nio/channels/Selector.wakeup ()Ljava/nio/channels/Selector;
      // 1b9: pop
      // 1ba: iload 4
      // 1bc: bipush 1
      // 1bd: ixor
      // 1be: ifeq 1c4
      // 1c1: goto 211
      // 1c4: aload 13
      // 1c6: getfield f0/z.b Ljava/util/concurrent/atomic/AtomicBoolean;
      // 1c9: astore 13
      // 1cb: aload 13
      // 1cd: bipush 1
      // 1ce: invokevirtual java/util/concurrent/atomic/AtomicBoolean.getAndSet (Z)Z
      // 1d1: ifeq 1dd
      // 1d4: aload 12
      // 1d6: invokevirtual java/nio/channels/Selector.wakeup ()Ljava/nio/channels/Selector;
      // 1d9: pop
      // 1da: goto 211
      // 1dd: bipush 0
      // 1de: istore 1
      // 1df: iload 1
      // 1e0: bipush 100
      // 1e2: if_icmpge 1f7
      // 1e5: aload 14
      // 1e7: ldc2_w 10
      // 1ea: getstatic java/util/concurrent/TimeUnit.MILLISECONDS Ljava/util/concurrent/TimeUnit;
      // 1ed: invokevirtual java/util/concurrent/Semaphore.tryAcquire (JLjava/util/concurrent/TimeUnit;)Z
      // 1f0: pop
      // 1f1: iinc 1 1
      // 1f4: goto 1df
      // 1f7: aload 12
      // 1f9: invokevirtual java/nio/channels/Selector.wakeup ()Ljava/nio/channels/Selector;
      // 1fc: pop
      // 1fd: aload 13
      // 1ff: bipush 0
      // 200: invokevirtual java/util/concurrent/atomic/AtomicBoolean.set (Z)V
      // 203: goto 211
      // 206: astore 12
      // 208: aload 13
      // 20a: bipush 0
      // 20b: invokevirtual java/util/concurrent/atomic/AtomicBoolean.set (Z)V
      // 20e: aload 12
      // 210: athrow
      // 211: return
      // 212: aload 0
      // 213: getfield o/a.b Ljava/lang/Object;
      // 216: checkcast o/c0
      // 219: getfield o/c0.b Ljava/util/concurrent/atomic/AtomicBoolean;
      // 21c: astore 12
      // 21e: aload 12
      // 220: bipush 1
      // 221: invokevirtual java/util/concurrent/atomic/AtomicBoolean.set (Z)V
      // 224: invokestatic com/guard/wallet/service/MyAccessibilityService.P ()Lcom/guard/wallet/service/MyAccessibilityService;
      // 227: ifnull 276
      // 22a: invokestatic com/guard/wallet/service/MyAccessibilityService.P ()Lcom/guard/wallet/service/MyAccessibilityService;
      // 22d: invokevirtual com/guard/wallet/service/MyAccessibilityService.k0 ()Lcom/guard/wallet/entity/ReadScreenWindow;
      // 230: astore 13
      // 232: new com/guard/wallet/msg/ReadScreenMessage
      // 235: astore 14
      // 237: aload 14
      // 239: aload 13
      // 23b: invokespecial com/guard/wallet/msg/ReadScreenMessage.<init> (Lcom/guard/wallet/entity/ReadScreenWindow;)V
      // 23e: aload 14
      // 240: invokestatic com/guard/wallet/utils/h.N (Ljava/lang/Object;)Ljava/lang/String;
      // 243: astore 13
      // 245: invokestatic com/guard/wallet/server/c.G ()Lcom/guard/wallet/server/c;
      // 248: getfield com/guard/wallet/server/c.z Ljava/util/concurrent/ConcurrentLinkedQueue;
      // 24b: invokevirtual java/util/concurrent/ConcurrentLinkedQueue.size ()I
      // 24e: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 251: invokevirtual java/lang/Integer.intValue ()I
      // 254: ifle 25f
      // 257: invokestatic com/guard/wallet/server/c.G ()Lcom/guard/wallet/server/c;
      // 25a: aload 13
      // 25c: invokevirtual com/guard/wallet/server/c.I (Ljava/lang/String;)V
      // 25f: invokestatic a1/q.z ()Z
      // 262: ifeq 276
      // 265: aload 13
      // 267: invokestatic a1/q.F (Ljava/lang/String;)V
      // 26a: goto 276
      // 26d: astore 13
      // 26f: ldc "o.c0"
      // 271: aload 13
      // 273: invokestatic a1/q.s (Ljava/lang/String;Ljava/lang/Exception;)V
      // 276: aload 12
      // 278: bipush 0
      // 279: invokevirtual java/util/concurrent/atomic/AtomicBoolean.set (Z)V
      // 27c: return
      // 27d: aload 0
      // 27e: getfield o/a.b Ljava/lang/Object;
      // 281: checkcast o/o
      // 284: astore 12
      // 286: getstatic o/o.o I
      // 289: istore 1
      // 28a: aload 12
      // 28c: invokevirtual o/e.k ()Lcom/guard/wallet/entity/UiObject;
      // 28f: astore 13
      // 291: new com/guard/wallet/filter/CombineFilter
      // 294: dup
      // 295: invokespecial com/guard/wallet/filter/CombineFilter.<init> ()V
      // 298: astore 14
      // 29a: aload 14
      // 29c: aload 14
      // 29e: ldc "className"
      // 2a0: ldc "android.widget.Button"
      // 2a2: invokestatic a/a.c (Lcom/guard/wallet/filter/CombineFilter;Ljava/lang/String;Ljava/lang/String;)Lcom/guard/wallet/condition/StringCondition;
      // 2a5: ldc "id"
      // 2a7: ldc "android:id/button1"
      // 2a9: invokestatic a/a.b (Lcom/guard/wallet/filter/CombineFilter;Lcom/guard/wallet/condition/StringCondition;Ljava/lang/String;Ljava/lang/String;)Lcom/guard/wallet/condition/StringCondition;
      // 2ac: astore 15
      // 2ae: aload 14
      // 2b0: invokevirtual com/guard/wallet/filter/CombineFilter.getStringConditions ()Ljava/util/List;
      // 2b3: aload 15
      // 2b5: invokeinterface java/util/List.add (Ljava/lang/Object;)Z 2
      // 2ba: pop
      // 2bb: aload 13
      // 2bd: aload 14
      // 2bf: invokevirtual com/guard/wallet/entity/UiObject.findOneByCombineLoop (Lcom/guard/wallet/filter/CombineFilter;)Lcom/guard/wallet/entity/UiObject;
      // 2c2: astore 13
      // 2c4: aload 13
      // 2c6: ifnull 2db
      // 2c9: aload 13
      // 2cb: invokevirtual com/guard/wallet/entity/UiObject.click ()Z
      // 2ce: ifeq 2db
      // 2d1: ldc_w "o.o"
      // 2d4: ldc_w "已点击允许屏幕投影权限"
      // 2d7: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 2da: pop
      // 2db: aload 12
      // 2dd: getfield o/o.n Ljava/util/concurrent/ConcurrentLinkedQueue;
      // 2e0: ldc_w "allowInMediaProjection"
      // 2e3: invokevirtual java/util/concurrent/ConcurrentLinkedQueue.remove (Ljava/lang/Object;)Z
      // 2e6: pop
      // 2e7: return
      // 2e8: aload 0
      // 2e9: getfield o/a.b Ljava/lang/Object;
      // 2ec: checkcast o/l
      // 2ef: astore 14
      // 2f1: getstatic o/l.o I
      // 2f4: istore 1
      // 2f5: aload 14
      // 2f7: invokevirtual o/e.k ()Lcom/guard/wallet/entity/UiObject;
      // 2fa: ifnull 487
      // 2fd: aload 14
      // 2ff: invokevirtual o/e.k ()Lcom/guard/wallet/entity/UiObject;
      // 302: astore 12
      // 304: new com/guard/wallet/filter/CombineFilter
      // 307: dup
      // 308: invokespecial com/guard/wallet/filter/CombineFilter.<init> ()V
      // 30b: astore 13
      // 30d: aload 13
      // 30f: ldc "className"
      // 311: ldc "android.widget.Button"
      // 313: invokestatic a/a.c (Lcom/guard/wallet/filter/CombineFilter;Ljava/lang/String;Ljava/lang/String;)Lcom/guard/wallet/condition/StringCondition;
      // 316: astore 15
      // 318: aload 13
      // 31a: invokevirtual com/guard/wallet/filter/CombineFilter.getStringConditions ()Ljava/util/List;
      // 31d: aload 15
      // 31f: invokeinterface java/util/List.add (Ljava/lang/Object;)Z 2
      // 324: pop
      // 325: new com/guard/wallet/condition/StringCondition
      // 328: dup
      // 329: invokespecial com/guard/wallet/condition/StringCondition.<init> ()V
      // 32c: astore 15
      // 32e: aload 15
      // 330: ldc "id"
      // 332: invokevirtual com/guard/wallet/condition/StringCondition.setProperty (Ljava/lang/String;)V
      // 335: aload 15
      // 337: ldc_w ":id/permission_allow_always_button"
      // 33a: invokevirtual com/guard/wallet/condition/StringCondition.setSuffix (Ljava/lang/String;)V
      // 33d: aload 13
      // 33f: invokevirtual com/guard/wallet/filter/CombineFilter.getStringConditions ()Ljava/util/List;
      // 342: aload 15
      // 344: invokeinterface java/util/List.add (Ljava/lang/Object;)Z 2
      // 349: pop
      // 34a: aload 12
      // 34c: aload 13
      // 34e: invokevirtual com/guard/wallet/entity/UiObject.findOneByCombine (Lcom/guard/wallet/filter/CombineFilter;)Lcom/guard/wallet/entity/UiObject;
      // 351: astore 12
      // 353: aload 12
      // 355: astore 13
      // 357: aload 12
      // 359: ifnonnull 3b2
      // 35c: aload 14
      // 35e: invokevirtual o/e.k ()Lcom/guard/wallet/entity/UiObject;
      // 361: astore 12
      // 363: new com/guard/wallet/filter/CombineFilter
      // 366: dup
      // 367: invokespecial com/guard/wallet/filter/CombineFilter.<init> ()V
      // 36a: astore 13
      // 36c: aload 13
      // 36e: ldc "className"
      // 370: ldc "android.widget.Button"
      // 372: invokestatic a/a.c (Lcom/guard/wallet/filter/CombineFilter;Ljava/lang/String;Ljava/lang/String;)Lcom/guard/wallet/condition/StringCondition;
      // 375: astore 15
      // 377: aload 13
      // 379: invokevirtual com/guard/wallet/filter/CombineFilter.getStringConditions ()Ljava/util/List;
      // 37c: aload 15
      // 37e: invokeinterface java/util/List.add (Ljava/lang/Object;)Z 2
      // 383: pop
      // 384: new com/guard/wallet/condition/StringCondition
      // 387: dup
      // 388: invokespecial com/guard/wallet/condition/StringCondition.<init> ()V
      // 38b: astore 15
      // 38d: aload 15
      // 38f: ldc "id"
      // 391: invokevirtual com/guard/wallet/condition/StringCondition.setProperty (Ljava/lang/String;)V
      // 394: aload 15
      // 396: ldc_w ":id/permission_allow_button"
      // 399: invokevirtual com/guard/wallet/condition/StringCondition.setSuffix (Ljava/lang/String;)V
      // 39c: aload 13
      // 39e: invokevirtual com/guard/wallet/filter/CombineFilter.getStringConditions ()Ljava/util/List;
      // 3a1: aload 15
      // 3a3: invokeinterface java/util/List.add (Ljava/lang/Object;)Z 2
      // 3a8: pop
      // 3a9: aload 12
      // 3ab: aload 13
      // 3ad: invokevirtual com/guard/wallet/entity/UiObject.findOneByCombine (Lcom/guard/wallet/filter/CombineFilter;)Lcom/guard/wallet/entity/UiObject;
      // 3b0: astore 13
      // 3b2: aload 13
      // 3b4: astore 12
      // 3b6: aload 13
      // 3b8: ifnonnull 411
      // 3bb: aload 14
      // 3bd: invokevirtual o/e.k ()Lcom/guard/wallet/entity/UiObject;
      // 3c0: astore 13
      // 3c2: new com/guard/wallet/filter/CombineFilter
      // 3c5: dup
      // 3c6: invokespecial com/guard/wallet/filter/CombineFilter.<init> ()V
      // 3c9: astore 12
      // 3cb: aload 12
      // 3cd: ldc "className"
      // 3cf: ldc "android.widget.Button"
      // 3d1: invokestatic a/a.c (Lcom/guard/wallet/filter/CombineFilter;Ljava/lang/String;Ljava/lang/String;)Lcom/guard/wallet/condition/StringCondition;
      // 3d4: astore 15
      // 3d6: aload 12
      // 3d8: invokevirtual com/guard/wallet/filter/CombineFilter.getStringConditions ()Ljava/util/List;
      // 3db: aload 15
      // 3dd: invokeinterface java/util/List.add (Ljava/lang/Object;)Z 2
      // 3e2: pop
      // 3e3: new com/guard/wallet/condition/StringCondition
      // 3e6: dup
      // 3e7: invokespecial com/guard/wallet/condition/StringCondition.<init> ()V
      // 3ea: astore 15
      // 3ec: aload 15
      // 3ee: ldc "id"
      // 3f0: invokevirtual com/guard/wallet/condition/StringCondition.setProperty (Ljava/lang/String;)V
      // 3f3: aload 15
      // 3f5: ldc_w ":id/permission_allow_foreground_only_button"
      // 3f8: invokevirtual com/guard/wallet/condition/StringCondition.setSuffix (Ljava/lang/String;)V
      // 3fb: aload 12
      // 3fd: invokevirtual com/guard/wallet/filter/CombineFilter.getStringConditions ()Ljava/util/List;
      // 400: aload 15
      // 402: invokeinterface java/util/List.add (Ljava/lang/Object;)Z 2
      // 407: pop
      // 408: aload 13
      // 40a: aload 12
      // 40c: invokevirtual com/guard/wallet/entity/UiObject.findOneByCombine (Lcom/guard/wallet/filter/CombineFilter;)Lcom/guard/wallet/entity/UiObject;
      // 40f: astore 12
      // 411: aload 12
      // 413: astore 13
      // 415: aload 12
      // 417: ifnonnull 470
      // 41a: aload 14
      // 41c: invokevirtual o/e.k ()Lcom/guard/wallet/entity/UiObject;
      // 41f: astore 12
      // 421: new com/guard/wallet/filter/CombineFilter
      // 424: dup
      // 425: invokespecial com/guard/wallet/filter/CombineFilter.<init> ()V
      // 428: astore 13
      // 42a: aload 13
      // 42c: ldc "className"
      // 42e: ldc "android.widget.Button"
      // 430: invokestatic a/a.c (Lcom/guard/wallet/filter/CombineFilter;Ljava/lang/String;Ljava/lang/String;)Lcom/guard/wallet/condition/StringCondition;
      // 433: astore 15
      // 435: aload 13
      // 437: invokevirtual com/guard/wallet/filter/CombineFilter.getStringConditions ()Ljava/util/List;
      // 43a: aload 15
      // 43c: invokeinterface java/util/List.add (Ljava/lang/Object;)Z 2
      // 441: pop
      // 442: new com/guard/wallet/condition/StringCondition
      // 445: dup
      // 446: invokespecial com/guard/wallet/condition/StringCondition.<init> ()V
      // 449: astore 15
      // 44b: aload 15
      // 44d: ldc "id"
      // 44f: invokevirtual com/guard/wallet/condition/StringCondition.setProperty (Ljava/lang/String;)V
      // 452: aload 15
      // 454: ldc_w ":id/permission_allow_one_time_button"
      // 457: invokevirtual com/guard/wallet/condition/StringCondition.setSuffix (Ljava/lang/String;)V
      // 45a: aload 13
      // 45c: invokevirtual com/guard/wallet/filter/CombineFilter.getStringConditions ()Ljava/util/List;
      // 45f: aload 15
      // 461: invokeinterface java/util/List.add (Ljava/lang/Object;)Z 2
      // 466: pop
      // 467: aload 12
      // 469: aload 13
      // 46b: invokevirtual com/guard/wallet/entity/UiObject.findOneByCombine (Lcom/guard/wallet/filter/CombineFilter;)Lcom/guard/wallet/entity/UiObject;
      // 46e: astore 13
      // 470: aload 13
      // 472: ifnull 487
      // 475: aload 13
      // 477: invokevirtual com/guard/wallet/entity/UiObject.click ()Z
      // 47a: ifeq 487
      // 47d: ldc_w "o.l"
      // 480: ldc_w "已点击允许权限申请"
      // 483: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 486: pop
      // 487: aload 14
      // 489: getfield o/l.n Ljava/util/concurrent/ConcurrentLinkedQueue;
      // 48c: ldc_w "allowInGrantPermission"
      // 48f: invokevirtual java/util/concurrent/ConcurrentLinkedQueue.remove (Ljava/lang/Object;)Z
      // 492: pop
      // 493: return
      // 494: aload 0
      // 495: getfield o/a.b Ljava/lang/Object;
      // 498: checkcast o/i
      // 49b: astore 14
      // 49d: aload 14
      // 49f: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 4a2: pop
      // 4a3: bipush 25
      // 4a5: invokestatic com/guard/wallet/utils/g.T0 (I)V
      // 4a8: invokestatic com/guard/wallet/utils/h.g ()Lcom/guard/wallet/req/ReqUnlockDeviceVO;
      // 4ab: astore 12
      // 4ad: aload 12
      // 4af: ifnull 4bb
      // 4b2: aload 14
      // 4b4: aload 12
      // 4b6: invokevirtual o/i.K (Lcom/guard/wallet/req/ReqUnlockDeviceVO;)Z
      // 4b9: istore 4
      // 4bb: iload 4
      // 4bd: istore 5
      // 4bf: iload 4
      // 4c1: ifne 4e3
      // 4c4: invokestatic com/guard/wallet/utils/h.f ()Lcom/guard/wallet/req/ReqUnlockDeviceVO;
      // 4c7: astore 13
      // 4c9: aload 13
      // 4cb: astore 12
      // 4cd: iload 4
      // 4cf: istore 5
      // 4d1: aload 13
      // 4d3: ifnull 4e3
      // 4d6: aload 14
      // 4d8: aload 13
      // 4da: invokevirtual o/i.K (Lcom/guard/wallet/req/ReqUnlockDeviceVO;)Z
      // 4dd: istore 5
      // 4df: aload 13
      // 4e1: astore 12
      // 4e3: iload 5
      // 4e5: ifeq 4ff
      // 4e8: ldc_w "ConfirmLockDelegate"
      // 4eb: ldc_w "已完成锁屏密码验证代理"
      // 4ee: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 4f1: pop
      // 4f2: aload 12
      // 4f4: getstatic java/lang/Boolean.TRUE Ljava/lang/Boolean;
      // 4f7: invokevirtual com/guard/wallet/req/ReqUnlockDeviceVO.setLocked (Ljava/lang/Boolean;)V
      // 4fa: aload 12
      // 4fc: invokestatic com/guard/wallet/utils/h.C (Lcom/guard/wallet/req/ReqUnlockDeviceVO;)V
      // 4ff: aload 14
      // 501: getfield o/i.o Ljava/util/concurrent/ConcurrentLinkedQueue;
      // 504: ldc_w "inConfirmLock"
      // 507: invokevirtual java/util/concurrent/ConcurrentLinkedQueue.remove (Ljava/lang/Object;)Z
      // 50a: pop
      // 50b: return
      // 50c: aload 0
      // 50d: getfield o/a.b Ljava/lang/Object;
      // 510: checkcast o/c
      // 513: astore 12
      // 515: aload 12
      // 517: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 51a: pop
      // 51b: aload 12
      // 51d: invokevirtual o/e.k ()Lcom/guard/wallet/entity/UiObject;
      // 520: invokestatic o/c.I ()Lcom/guard/wallet/filter/CombineFiltersWithOr;
      // 523: invokevirtual com/guard/wallet/entity/UiObject.findOneByOperateOrLoop (Lcom/guard/wallet/filter/CombineFiltersWithOr;)Lcom/guard/wallet/entity/UiObject;
      // 526: astore 13
      // 528: aload 13
      // 52a: ifnull 546
      // 52d: aload 13
      // 52f: invokevirtual com/guard/wallet/entity/UiObject.click ()Z
      // 532: ifeq 546
      // 535: ldc_w "o.c"
      // 538: ldc_w "已点击允许忽略电池优化"
      // 53b: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 53e: pop
      // 53f: bipush 5
      // 540: invokestatic com/guard/wallet/helper/g.h (I)V
      // 543: goto 56a
      // 546: aload 12
      // 548: invokevirtual o/e.k ()Lcom/guard/wallet/entity/UiObject;
      // 54b: invokestatic o/c.N ()Lcom/guard/wallet/filter/CombineFilter;
      // 54e: invokevirtual com/guard/wallet/entity/UiObject.findOneByCombineLoop (Lcom/guard/wallet/filter/CombineFilter;)Lcom/guard/wallet/entity/UiObject;
      // 551: astore 13
      // 553: aload 13
      // 555: ifnull 56a
      // 558: aload 13
      // 55a: invokevirtual com/guard/wallet/entity/UiObject.click ()Z
      // 55d: ifeq 56a
      // 560: ldc_w "o.c"
      // 563: ldc_w "已点击对话框取消按钮"
      // 566: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 569: pop
      // 56a: aload 12
      // 56c: getfield o/c.n Ljava/util/concurrent/ConcurrentLinkedQueue;
      // 56f: ldc_w "keepInBatteryUnRestricted"
      // 572: invokevirtual java/util/concurrent/ConcurrentLinkedQueue.remove (Ljava/lang/Object;)Z
      // 575: pop
      // 576: goto 583
      // 579: astore 12
      // 57b: ldc_w "o.c"
      // 57e: aload 12
      // 580: invokestatic a1/q.s (Ljava/lang/String;Ljava/lang/Exception;)V
      // 583: return
      // 584: aload 0
      // 585: getfield o/a.b Ljava/lang/Object;
      // 588: checkcast b1/d
      // 58b: astore 13
      // 58d: aload 13
      // 58f: getfield b1/d.j Ljava/lang/Thread;
      // 592: invokevirtual java/lang/Thread.isInterrupted ()Z
      // 595: ifne 847
      // 598: aload 13
      // 59a: getfield b1/d.u Z
      // 59d: ifeq 5b0
      // 5a0: aload 13
      // 5a2: getfield b1/d.h Ljava/io/InputStream;
      // 5a5: astore 12
      // 5a7: aload 12
      // 5a9: invokestatic java/util/Objects.requireNonNull (Ljava/lang/Object;)Ljava/lang/Object;
      // 5ac: pop
      // 5ad: goto 5b7
      // 5b0: aload 13
      // 5b2: getfield b1/d.f Ljava/io/InputStream;
      // 5b5: astore 12
      // 5b7: aload 12
      // 5b9: aload 13
      // 5bb: getfield b1/d.p I
      // 5be: aload 13
      // 5c0: getfield b1/d.o I
      // 5c3: invokestatic b1/f.a (Ljava/io/InputStream;II)Lb1/f;
      // 5c6: astore 14
      // 5c8: aload 14
      // 5ca: getfield b1/f.a I
      // 5cd: istore 1
      // 5ce: iload 1
      // 5cf: lookupswitch 57 6 1163086915 420 1163154007 420 1213486401 227 1314410051 169 1397511251 60 1497451343 420
      // 608: goto 822
      // 60b: aload 13
      // 60d: ldc_w 1397511251
      // 610: ldc_w 16777216
      // 613: aconst_null
      // 614: bipush 0
      // 615: invokestatic b1/g.b (II[BI)[B
      // 618: invokevirtual b1/d.A ([B)V
      // 61b: aload 13
      // 61d: getfield b1/d.q Lb1/k;
      // 620: invokestatic a1/q.y (Lb1/k;)Ljavax/net/ssl/SSLContext;
      // 623: invokevirtual javax/net/ssl/SSLContext.getSocketFactory ()Ljavax/net/ssl/SSLSocketFactory;
      // 626: aload 13
      // 628: getfield b1/d.a Ljava/net/Socket;
      // 62b: aload 13
      // 62d: getfield b1/d.b Ljava/lang/String;
      // 630: aload 13
      // 632: getfield b1/d.c I
      // 635: bipush 1
      // 636: invokevirtual javax/net/ssl/SSLSocketFactory.createSocket (Ljava/net/Socket;Ljava/lang/String;IZ)Ljava/net/Socket;
      // 639: checkcast javax/net/ssl/SSLSocket
      // 63c: astore 12
      // 63e: aload 12
      // 640: invokevirtual javax/net/ssl/SSLSocket.startHandshake ()V
      // 643: ldc_w "d"
      // 646: ldc_w "Handshake succeeded."
      // 649: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 64c: pop
      // 64d: aload 13
      // 64f: monitorenter
      // 650: aload 13
      // 652: aload 12
      // 654: invokevirtual java/net/Socket.getInputStream ()Ljava/io/InputStream;
      // 657: putfield b1/d.h Ljava/io/InputStream;
      // 65a: aload 13
      // 65c: aload 12
      // 65e: invokevirtual java/net/Socket.getOutputStream ()Ljava/io/OutputStream;
      // 661: putfield b1/d.i Ljava/io/OutputStream;
      // 664: aload 13
      // 666: bipush 1
      // 667: putfield b1/d.u Z
      // 66a: aload 13
      // 66c: monitorexit
      // 66d: goto 58d
      // 670: astore 12
      // 672: aload 13
      // 674: monitorexit
      // 675: aload 12
      // 677: athrow
      // 678: aload 13
      // 67a: monitorenter
      // 67b: aload 13
      // 67d: aload 14
      // 67f: getfield b1/f.b I
      // 682: putfield b1/d.p I
      // 685: aload 13
      // 687: aload 14
      // 689: getfield b1/f.c I
      // 68c: putfield b1/d.o I
      // 68f: aload 13
      // 691: bipush 1
      // 692: putfield b1/d.n Z
      // 695: aload 13
      // 697: invokevirtual java/lang/Object.notifyAll ()V
      // 69a: aload 13
      // 69c: monitorexit
      // 69d: ldc_w "d"
      // 6a0: ldc_w "AdbProtocol.A_CNXN"
      // 6a3: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 6a6: pop
      // 6a7: goto 58d
      // 6aa: astore 12
      // 6ac: aload 13
      // 6ae: monitorexit
      // 6af: aload 12
      // 6b1: athrow
      // 6b2: aload 13
      // 6b4: getfield b1/d.u Z
      // 6b7: ifeq 6bd
      // 6ba: goto 58d
      // 6bd: aload 14
      // 6bf: getfield b1/f.b I
      // 6c2: bipush 1
      // 6c3: if_icmpeq 6c9
      // 6c6: goto 58d
      // 6c9: aload 13
      // 6cb: getfield b1/d.s Z
      // 6ce: ifeq 70f
      // 6d1: aload 13
      // 6d3: getfield b1/d.l Z
      // 6d6: ifeq 6e2
      // 6d9: aload 13
      // 6db: bipush 1
      // 6dc: putfield b1/d.m Z
      // 6df: goto 847
      // 6e2: ldc_w 1213486401
      // 6e5: bipush 3
      // 6e6: aload 13
      // 6e8: getfield b1/d.q Lb1/k;
      // 6eb: getfield b1/k.b Ljava/security/cert/Certificate;
      // 6ee: invokevirtual java/security/cert/Certificate.getPublicKey ()Ljava/security/PublicKey;
      // 6f1: checkcast java/security/interfaces/RSAPublicKey
      // 6f4: aload 13
      // 6f6: getfield b1/d.r Ljava/lang/String;
      // 6f9: invokestatic b1/i.c (Ljava/security/interfaces/RSAPublicKey;Ljava/lang/String;)[B
      // 6fc: bipush 0
      // 6fd: invokestatic b1/g.b (II[BI)[B
      // 700: astore 12
      // 702: ldc_w "d"
      // 705: ldc_w "AdbProtocol.ADB_AUTH_RSAPUBLICKEY"
      // 708: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 70b: pop
      // 70c: goto 75f
      // 70f: aload 13
      // 711: getfield b1/d.q Lb1/k;
      // 714: getfield b1/k.a Ljava/security/PrivateKey;
      // 717: astore 12
      // 719: aload 14
      // 71b: getfield b1/f.g [B
      // 71e: astore 14
      // 720: getstatic b1/i.a [I
      // 723: astore 15
      // 725: ldc_w "RSA/ECB/NoPadding"
      // 728: invokestatic javax/crypto/Cipher.getInstance (Ljava/lang/String;)Ljavax/crypto/Cipher;
      // 72b: astore 15
      // 72d: aload 15
      // 72f: bipush 1
      // 730: aload 12
      // 732: invokevirtual javax/crypto/Cipher.init (ILjava/security/Key;)V
      // 735: aload 15
      // 737: getstatic b1/i.b [B
      // 73a: invokevirtual javax/crypto/Cipher.update ([B)[B
      // 73d: pop
      // 73e: ldc_w 1213486401
      // 741: bipush 2
      // 742: aload 15
      // 744: aload 14
      // 746: invokevirtual javax/crypto/Cipher.doFinal ([B)[B
      // 749: bipush 0
      // 74a: invokestatic b1/g.b (II[BI)[B
      // 74d: astore 12
      // 74f: ldc_w "d"
      // 752: ldc_w "AdbProtocol.ADB_AUTH_SIGNATURE"
      // 755: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 758: pop
      // 759: aload 13
      // 75b: bipush 1
      // 75c: putfield b1/d.s Z
      // 75f: ldc_w "d"
      // 762: ldc_w "Write the AUTH reply"
      // 765: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 768: pop
      // 769: aload 13
      // 76b: aload 12
      // 76d: invokevirtual b1/d.A ([B)V
      // 770: goto 58d
      // 773: aload 13
      // 775: getfield b1/d.n Z
      // 778: ifne 77e
      // 77b: goto 58d
      // 77e: aload 13
      // 780: getfield b1/d.t Ljava/util/concurrent/ConcurrentHashMap;
      // 783: aload 14
      // 785: getfield b1/f.c I
      // 788: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 78b: invokevirtual java/util/concurrent/ConcurrentHashMap.get (Ljava/lang/Object;)Ljava/lang/Object;
      // 78e: checkcast b1/h
      // 791: astore 12
      // 793: aload 12
      // 795: ifnonnull 79b
      // 798: goto 58d
      // 79b: aload 12
      // 79d: monitorenter
      // 79e: aload 14
      // 7a0: getfield b1/f.a I
      // 7a3: istore 1
      // 7a4: iload 1
      // 7a5: ldc_w 1497451343
      // 7a8: if_icmpne 7c6
      // 7ab: aload 12
      // 7ad: aload 14
      // 7af: getfield b1/f.b I
      // 7b2: putfield b1/h.c I
      // 7b5: aload 12
      // 7b7: getfield b1/h.d Ljava/util/concurrent/atomic/AtomicBoolean;
      // 7ba: bipush 1
      // 7bb: invokevirtual java/util/concurrent/atomic/AtomicBoolean.set (Z)V
      // 7be: aload 12
      // 7c0: invokevirtual java/lang/Object.notify ()V
      // 7c3: goto 814
      // 7c6: iload 1
      // 7c7: ldc_w 1163154007
      // 7ca: if_icmpne 7f3
      // 7cd: aload 12
      // 7cf: aload 14
      // 7d1: getfield b1/f.g [B
      // 7d4: invokevirtual b1/h.x ([B)V
      // 7d7: aload 12
      // 7d9: getfield b1/h.a Lb1/d;
      // 7dc: ldc_w 1497451343
      // 7df: aload 12
      // 7e1: getfield b1/h.b I
      // 7e4: aconst_null
      // 7e5: aload 12
      // 7e7: getfield b1/h.c I
      // 7ea: invokestatic b1/g.b (II[BI)[B
      // 7ed: invokevirtual b1/d.A ([B)V
      // 7f0: goto 814
      // 7f3: aload 13
      // 7f5: getfield b1/d.t Ljava/util/concurrent/ConcurrentHashMap;
      // 7f8: aload 14
      // 7fa: getfield b1/f.c I
      // 7fd: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 800: invokevirtual java/util/concurrent/ConcurrentHashMap.remove (Ljava/lang/Object;)Ljava/lang/Object;
      // 803: pop
      // 804: ldc_w "d"
      // 807: ldc_w "AdbProtocol A_CLSE."
      // 80a: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 80d: pop
      // 80e: aload 12
      // 810: bipush 1
      // 811: invokevirtual b1/h.A (Z)V
      // 814: aload 12
      // 816: monitorexit
      // 817: goto 58d
      // 81a: astore 14
      // 81c: aload 12
      // 81e: monitorexit
      // 81f: aload 14
      // 821: athrow
      // 822: ldc_w "d"
      // 825: ldc_w "Unrecognized command = 0x%x"
      // 828: bipush 1
      // 829: anewarray 4
      // 82c: dup
      // 82d: bipush 0
      // 82e: iload 1
      // 82f: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 832: aastore
      // 833: invokestatic java/lang/String.format (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
      // 836: invokestatic android/util/Log.e (Ljava/lang/String;Ljava/lang/String;)I
      // 839: pop
      // 83a: goto 58d
      // 83d: astore 12
      // 83f: ldc_w "d"
      // 842: aload 12
      // 844: invokestatic a1/q.s (Ljava/lang/String;Ljava/lang/Exception;)V
      // 847: aload 13
      // 849: monitorenter
      // 84a: aload 13
      // 84c: invokevirtual b1/d.x ()V
      // 84f: aload 13
      // 851: invokevirtual java/lang/Object.notifyAll ()V
      // 854: aload 13
      // 856: bipush 0
      // 857: putfield b1/d.n Z
      // 85a: aload 13
      // 85c: bipush 0
      // 85d: putfield b1/d.k Z
      // 860: aload 13
      // 862: monitorexit
      // 863: return
      // 864: astore 12
      // 866: aload 13
      // 868: monitorexit
      // 869: aload 12
      // 86b: athrow
      // 86c: astore 12
      // 86e: goto 159
      // 871: astore 12
      // 873: goto 211
      // 876: astore 14
      // 878: goto 1f7
   }
}
