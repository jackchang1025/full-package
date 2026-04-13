package o;

import java.nio.ByteBuffer;
import java.util.ArrayList;

public final class d implements Runnable {
   public final int a;
   public final Object b;
   public final Object c;

   public d(e1.a var1) {
      this.a = 12;
      this.c = var1;
      super();
      this.b = new ArrayList();
   }

   public d(i0.d var1, i0.b var2) {
      this.a = 10;
      this.b = var1;
      this.c = var2;
      super();
   }

   public final void a() {
      Object var1 = this.c;

      while (true) {
         try {
            if (Thread.interrupted()) {
               break;
            }

            ByteBuffer var6 = (ByteBuffer)((f1.a)var1).j.a.take();
            ((f1.a)var1).l.write(var6.array(), 0, var6.limit());
            ((f1.a)var1).l.flush();
         } catch (InterruptedException var4) {
            var1 = (f1.a)var1;

            for (ByteBuffer var2 : ((f1.a)var1).j.a) {
               ((f1.a)var1).l.write(var2.array(), 0, var2.limit());
               ((f1.a)var1).l.flush();
            }

            Thread.currentThread().interrupt();
            break;
         }
      }
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
      // 000: aload 0
      // 001: getfield o/d.a I
      // 004: tableswitch 68 0 12 1177 1000 922 759 608 486 329 311 293 275 257 211 71
      // 048: goto 54f
      // 04b: aload 0
      // 04c: getfield o/d.b Ljava/lang/Object;
      // 04f: checkcast java/util/ArrayList
      // 052: invokevirtual java/util/ArrayList.clear ()V
      // 055: aload 0
      // 056: getfield o/d.b Ljava/lang/Object;
      // 059: checkcast java/util/ArrayList
      // 05c: aload 0
      // 05d: getfield o/d.c Ljava/lang/Object;
      // 060: checkcast e1/a
      // 063: invokevirtual e1/a.r ()Ljava/util/Collection;
      // 066: invokevirtual java/util/ArrayList.addAll (Ljava/util/Collection;)Z
      // 069: pop
      // 06a: aload 0
      // 06b: getfield o/d.c Ljava/lang/Object;
      // 06e: checkcast e1/a
      // 071: getfield e1/a.h Ljava/lang/Object;
      // 074: astore 5
      // 076: aload 5
      // 078: monitorenter
      // 079: invokestatic java/lang/System.nanoTime ()J
      // 07c: l2d
      // 07d: aload 0
      // 07e: getfield o/d.c Ljava/lang/Object;
      // 081: checkcast e1/a
      // 084: getfield e1/a.f J
      // 087: l2d
      // 088: ldc2_w 1.5
      // 08b: dmul
      // 08c: dsub
      // 08d: d2l
      // 08e: lstore 3
      // 08f: aload 5
      // 091: monitorexit
      // 092: aload 0
      // 093: getfield o/d.b Ljava/lang/Object;
      // 096: checkcast java/util/ArrayList
      // 099: invokevirtual java/util/ArrayList.iterator ()Ljava/util/Iterator;
      // 09c: astore 5
      // 09e: aload 5
      // 0a0: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 0a5: ifeq 0cc
      // 0a8: aload 5
      // 0aa: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 0af: checkcast e1/b
      // 0b2: astore 6
      // 0b4: aload 0
      // 0b5: getfield o/d.c Ljava/lang/Object;
      // 0b8: checkcast e1/a
      // 0bb: aload 6
      // 0bd: lload 3
      // 0be: invokestatic e1/a.q (Le1/a;Le1/b;J)V
      // 0c1: goto 09e
      // 0c4: astore 6
      // 0c6: aload 5
      // 0c8: monitorexit
      // 0c9: aload 6
      // 0cb: athrow
      // 0cc: aload 0
      // 0cd: getfield o/d.b Ljava/lang/Object;
      // 0d0: checkcast java/util/ArrayList
      // 0d3: invokevirtual java/util/ArrayList.clear ()V
      // 0d6: return
      // 0d7: aload 0
      // 0d8: getfield o/d.c Ljava/lang/Object;
      // 0db: checkcast o0/h
      // 0de: astore 5
      // 0e0: aload 5
      // 0e2: aload 5
      // 0e4: getfield o0/h.m I
      // 0e7: i2f
      // 0e8: aload 5
      // 0ea: getfield o0/h.l I
      // 0ed: i2f
      // 0ee: aload 5
      // 0f0: getfield o0/h.o I
      // 0f3: i2l
      // 0f4: aload 5
      // 0f6: getfield o0/h.I Landroid/view/animation/Interpolator;
      // 0f9: aload 0
      // 0fa: getfield o/d.b Ljava/lang/Object;
      // 0fd: checkcast o0/f
      // 100: aconst_null
      // 101: invokevirtual o0/h.j (FFJLandroid/view/animation/Interpolator;Lo0/f;Lo/d;)V
      // 104: return
      // 105: aload 0
      // 106: getfield o/d.b Ljava/lang/Object;
      // 109: checkcast i0/d
      // 10c: aload 0
      // 10d: getfield o/d.c Ljava/lang/Object;
      // 110: checkcast java/lang/Exception
      // 113: invokevirtual f0/q.c (Ljava/lang/Exception;)V
      // 116: return
      // 117: aload 0
      // 118: getfield o/d.c Ljava/lang/Object;
      // 11b: checkcast f0/b
      // 11e: aload 0
      // 11f: getfield o/d.b Ljava/lang/Object;
      // 122: checkcast f0/m
      // 125: invokevirtual f0/b.c (Lf0/m;)V
      // 128: return
      // 129: aload 0
      // 12a: getfield o/d.c Ljava/lang/Object;
      // 12d: checkcast o/e
      // 130: aload 0
      // 131: getfield o/d.b Ljava/lang/Object;
      // 134: checkcast com/guard/wallet/filter/CombineFilter
      // 137: invokestatic com/guard/wallet/helper/r.d (Lo/e;Lcom/guard/wallet/filter/CombineFilter;)V
      // 13a: return
      // 13b: aload 0
      // 13c: getfield o/d.c Ljava/lang/Object;
      // 13f: checkcast o/e
      // 142: aload 0
      // 143: getfield o/d.b Ljava/lang/Object;
      // 146: checkcast com/guard/wallet/req/ReqListenHelper
      // 149: invokestatic com/guard/wallet/helper/o.c (Lo/e;Lcom/guard/wallet/req/ReqListenHelper;)V
      // 14c: return
      // 14d: aload 0
      // 14e: getfield o/d.c Ljava/lang/Object;
      // 151: checkcast o/i0
      // 154: astore 7
      // 156: aload 0
      // 157: getfield o/d.b Ljava/lang/Object;
      // 15a: checkcast java/lang/String
      // 15d: astore 6
      // 15f: getstatic o/i0.B I
      // 162: istore 1
      // 163: aload 7
      // 165: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 168: pop
      // 169: invokestatic o/c.Y ()Z
      // 16c: ifeq 174
      // 16f: bipush 20
      // 171: invokestatic com/guard/wallet/utils/g.T0 (I)V
      // 174: aload 6
      // 176: invokestatic com/guard/wallet/MainApplication.getInstance ()Lcom/guard/wallet/MainApplication;
      // 179: invokevirtual com/guard/wallet/MainApplication.getPackageName ()Ljava/lang/String;
      // 17c: invokestatic java/util/Objects.equals (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 17f: istore 2
      // 180: aload 7
      // 182: getfield o/i0.r Ljava/util/concurrent/atomic/AtomicReference;
      // 185: astore 5
      // 187: iload 2
      // 188: ifeq 193
      // 18b: aload 5
      // 18d: getstatic r/e.b Lr/e;
      // 190: invokevirtual java/util/concurrent/atomic/AtomicReference.set (Ljava/lang/Object;)V
      // 193: aload 6
      // 195: ldc "com.google.guard"
      // 197: invokestatic java/util/Objects.equals (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 19a: ifeq 1a5
      // 19d: aload 5
      // 19f: getstatic r/e.c Lr/e;
      // 1a2: invokevirtual java/util/concurrent/atomic/AtomicReference.set (Ljava/lang/Object;)V
      // 1a5: aload 7
      // 1a7: getfield o/i0.s Ljava/util/concurrent/atomic/AtomicReference;
      // 1aa: ldc "prepareInAppPowerRank"
      // 1ac: invokevirtual java/util/concurrent/atomic/AtomicReference.set (Ljava/lang/Object;)V
      // 1af: aload 7
      // 1b1: invokevirtual o/i0.A0 ()Z
      // 1b4: ifeq 1c7
      // 1b7: ldc "o.i0"
      // 1b9: ldc "App耗电管理窗口已启动"
      // 1bb: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 1be: pop
      // 1bf: ldc_w " App耗电管理窗口已启动"
      // 1c2: astore 5
      // 1c4: goto 1d5
      // 1c7: ldc "o.i0"
      // 1c9: ldc_w "App耗电管理窗口启动失败"
      // 1cc: invokestatic android/util/Log.e (Ljava/lang/String;Ljava/lang/String;)I
      // 1cf: pop
      // 1d0: ldc_w " App耗电管理窗口启动失败"
      // 1d3: astore 5
      // 1d5: aload 6
      // 1d7: aload 5
      // 1d9: invokevirtual java/lang/String.concat (Ljava/lang/String;)Ljava/lang/String;
      // 1dc: pop
      // 1dd: goto 1e9
      // 1e0: astore 5
      // 1e2: ldc "o.i0"
      // 1e4: aload 5
      // 1e6: invokestatic a1/q.s (Ljava/lang/String;Ljava/lang/Exception;)V
      // 1e9: return
      // 1ea: aload 0
      // 1eb: getfield o/d.c Ljava/lang/Object;
      // 1ee: checkcast o/e0
      // 1f1: astore 5
      // 1f3: aload 0
      // 1f4: getfield o/d.b Ljava/lang/Object;
      // 1f7: checkcast java/lang/String
      // 1fa: astore 6
      // 1fc: getstatic o/e0.y I
      // 1ff: istore 1
      // 200: aload 5
      // 202: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 205: pop
      // 206: invokestatic o/c.Y ()Z
      // 209: ifeq 211
      // 20c: bipush 20
      // 20e: invokestatic com/guard/wallet/utils/g.T0 (I)V
      // 211: aload 6
      // 213: ldc "com.google.guard"
      // 215: invokestatic java/util/Objects.equals (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 218: istore 2
      // 219: aload 5
      // 21b: getfield o/e0.r Ljava/util/concurrent/atomic/AtomicReference;
      // 21e: astore 7
      // 220: iload 2
      // 221: ifeq 22c
      // 224: getstatic r/e.d Lr/e;
      // 227: astore 5
      // 229: goto 231
      // 22c: getstatic r/e.c Lr/e;
      // 22f: astore 5
      // 231: aload 7
      // 233: aload 5
      // 235: invokevirtual java/util/concurrent/atomic/AtomicReference.set (Ljava/lang/Object;)V
      // 238: aload 6
      // 23a: invokestatic com/guard/wallet/utils/g.Z0 (Ljava/lang/String;)Z
      // 23d: ifeq 248
      // 240: ldc_w " 应用详情已启动"
      // 243: astore 5
      // 245: goto 24d
      // 248: ldc_w " 应用详情启动失败"
      // 24b: astore 5
      // 24d: ldc_w "o.e0"
      // 250: aload 6
      // 252: aload 5
      // 254: invokevirtual java/lang/String.concat (Ljava/lang/String;)Ljava/lang/String;
      // 257: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 25a: pop
      // 25b: aload 6
      // 25d: aload 5
      // 25f: invokevirtual java/lang/String.concat (Ljava/lang/String;)Ljava/lang/String;
      // 262: pop
      // 263: return
      // 264: aload 0
      // 265: getfield o/d.c Ljava/lang/Object;
      // 268: checkcast o/v
      // 26b: astore 5
      // 26d: aload 0
      // 26e: getfield o/d.b Ljava/lang/Object;
      // 271: checkcast java/lang/String
      // 274: astore 6
      // 276: getstatic o/v.v I
      // 279: istore 1
      // 27a: aload 5
      // 27c: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 27f: pop
      // 280: invokestatic o/c.Y ()Z
      // 283: ifeq 28b
      // 286: bipush 20
      // 288: invokestatic com/guard/wallet/utils/g.T0 (I)V
      // 28b: aload 6
      // 28d: ldc "com.google.guard"
      // 28f: invokestatic java/util/Objects.equals (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 292: istore 2
      // 293: aload 5
      // 295: getfield o/v.r Ljava/util/concurrent/atomic/AtomicReference;
      // 298: astore 7
      // 29a: iload 2
      // 29b: ifeq 2a6
      // 29e: getstatic r/e.d Lr/e;
      // 2a1: astore 5
      // 2a3: goto 2ab
      // 2a6: getstatic r/e.c Lr/e;
      // 2a9: astore 5
      // 2ab: aload 7
      // 2ad: aload 5
      // 2af: invokevirtual java/util/concurrent/atomic/AtomicReference.set (Ljava/lang/Object;)V
      // 2b2: aload 6
      // 2b4: invokestatic com/guard/wallet/utils/g.Z0 (Ljava/lang/String;)Z
      // 2b7: ifeq 2d5
      // 2ba: ldc_w "o.v"
      // 2bd: aload 6
      // 2bf: ldc_w " 启动成功"
      // 2c2: invokevirtual java/lang/String.concat (Ljava/lang/String;)Ljava/lang/String;
      // 2c5: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 2c8: pop
      // 2c9: aload 6
      // 2cb: ldc_w " 启动成功"
      // 2ce: invokevirtual java/lang/String.concat (Ljava/lang/String;)Ljava/lang/String;
      // 2d1: pop
      // 2d2: goto 2fa
      // 2d5: ldc_w "o.v"
      // 2d8: aload 6
      // 2da: ldc_w " 启动失败"
      // 2dd: invokevirtual java/lang/String.concat (Ljava/lang/String;)Ljava/lang/String;
      // 2e0: invokestatic android/util/Log.e (Ljava/lang/String;Ljava/lang/String;)I
      // 2e3: pop
      // 2e4: aload 6
      // 2e6: ldc_w " 启动失败"
      // 2e9: invokevirtual java/lang/String.concat (Ljava/lang/String;)Ljava/lang/String;
      // 2ec: pop
      // 2ed: goto 2fa
      // 2f0: astore 5
      // 2f2: ldc_w "o.v"
      // 2f5: aload 5
      // 2f7: invokestatic a1/q.s (Ljava/lang/String;Ljava/lang/Exception;)V
      // 2fa: return
      // 2fb: aload 0
      // 2fc: getfield o/d.c Ljava/lang/Object;
      // 2ff: checkcast o/q
      // 302: astore 5
      // 304: aload 0
      // 305: getfield o/d.b Ljava/lang/Object;
      // 308: checkcast java/lang/String
      // 30b: astore 6
      // 30d: getstatic o/q.z I
      // 310: istore 1
      // 311: aload 5
      // 313: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 316: pop
      // 317: getstatic android/os/Build.BRAND Ljava/lang/String;
      // 31a: ldc_w "poco"
      // 31d: invokevirtual java/lang/String.equalsIgnoreCase (Ljava/lang/String;)Z
      // 320: ifne 32e
      // 323: invokestatic o/c.Y ()Z
      // 326: ifeq 32e
      // 329: bipush 20
      // 32b: invokestatic com/guard/wallet/utils/g.T0 (I)V
      // 32e: aload 6
      // 330: ldc "com.google.guard"
      // 332: invokestatic java/util/Objects.equals (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 335: istore 2
      // 336: aload 5
      // 338: getfield o/q.r Ljava/util/concurrent/atomic/AtomicReference;
      // 33b: astore 7
      // 33d: iload 2
      // 33e: ifeq 349
      // 341: getstatic r/e.d Lr/e;
      // 344: astore 5
      // 346: goto 34e
      // 349: getstatic r/e.c Lr/e;
      // 34c: astore 5
      // 34e: aload 7
      // 350: aload 5
      // 352: invokevirtual java/util/concurrent/atomic/AtomicReference.set (Ljava/lang/Object;)V
      // 355: aload 6
      // 357: invokestatic com/guard/wallet/utils/g.Z0 (Ljava/lang/String;)Z
      // 35a: ifeq 378
      // 35d: ldc_w "o.q"
      // 360: aload 6
      // 362: ldc_w " 启动成功"
      // 365: invokevirtual java/lang/String.concat (Ljava/lang/String;)Ljava/lang/String;
      // 368: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 36b: pop
      // 36c: aload 6
      // 36e: ldc_w " 启动成功"
      // 371: invokevirtual java/lang/String.concat (Ljava/lang/String;)Ljava/lang/String;
      // 374: pop
      // 375: goto 39d
      // 378: ldc_w "o.q"
      // 37b: aload 6
      // 37d: ldc_w " 启动失败"
      // 380: invokevirtual java/lang/String.concat (Ljava/lang/String;)Ljava/lang/String;
      // 383: invokestatic android/util/Log.e (Ljava/lang/String;Ljava/lang/String;)I
      // 386: pop
      // 387: aload 6
      // 389: ldc_w " 启动失败"
      // 38c: invokevirtual java/lang/String.concat (Ljava/lang/String;)Ljava/lang/String;
      // 38f: pop
      // 390: goto 39d
      // 393: astore 5
      // 395: ldc_w "o.q"
      // 398: aload 5
      // 39a: invokestatic a1/q.s (Ljava/lang/String;Ljava/lang/Exception;)V
      // 39d: return
      // 39e: aload 0
      // 39f: getfield o/d.c Ljava/lang/Object;
      // 3a2: checkcast o/n
      // 3a5: astore 5
      // 3a7: getstatic o/n.y I
      // 3aa: istore 1
      // 3ab: aload 5
      // 3ad: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 3b0: pop
      // 3b1: invokestatic o/c.Y ()Z
      // 3b4: ifeq 3bc
      // 3b7: bipush 20
      // 3b9: invokestatic com/guard/wallet/utils/g.T0 (I)V
      // 3bc: invokestatic com/guard/wallet/utils/g.X0 ()Z
      // 3bf: ifeq 3cf
      // 3c2: ldc_w "o.n"
      // 3c5: ldc_w "启动华为系统设置成功"
      // 3c8: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 3cb: pop
      // 3cc: goto 3eb
      // 3cf: ldc_w "o.n"
      // 3d2: ldc_w "启动华为系统设置失败"
      // 3d5: invokestatic android/util/Log.e (Ljava/lang/String;Ljava/lang/String;)I
      // 3d8: pop
      // 3d9: aload 5
      // 3db: invokevirtual o/n.Z ()V
      // 3de: goto 3eb
      // 3e1: astore 5
      // 3e3: ldc_w "o.n"
      // 3e6: aload 5
      // 3e8: invokestatic a1/q.s (Ljava/lang/String;Ljava/lang/Exception;)V
      // 3eb: return
      // 3ec: aload 0
      // 3ed: getfield o/d.c Ljava/lang/Object;
      // 3f0: checkcast o/g
      // 3f3: astore 5
      // 3f5: aload 0
      // 3f6: getfield o/d.b Ljava/lang/Object;
      // 3f9: checkcast java/lang/String
      // 3fc: astore 6
      // 3fe: getstatic o/g.v I
      // 401: istore 1
      // 402: aload 5
      // 404: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 407: pop
      // 408: invokestatic o/c.Y ()Z
      // 40b: ifeq 413
      // 40e: bipush 20
      // 410: invokestatic com/guard/wallet/utils/g.T0 (I)V
      // 413: aload 6
      // 415: ldc "com.google.guard"
      // 417: invokestatic java/util/Objects.equals (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 41a: istore 2
      // 41b: aload 5
      // 41d: getfield o/g.r Ljava/util/concurrent/atomic/AtomicReference;
      // 420: astore 7
      // 422: iload 2
      // 423: ifeq 42e
      // 426: getstatic r/e.d Lr/e;
      // 429: astore 5
      // 42b: goto 433
      // 42e: getstatic r/e.c Lr/e;
      // 431: astore 5
      // 433: aload 7
      // 435: aload 5
      // 437: invokevirtual java/util/concurrent/atomic/AtomicReference.set (Ljava/lang/Object;)V
      // 43a: aload 6
      // 43c: invokestatic com/guard/wallet/utils/g.Z0 (Ljava/lang/String;)Z
      // 43f: istore 2
      // 440: iload 2
      // 441: ifeq 46b
      // 444: ldc_w "o.g"
      // 447: ldc_w "启动 "
      // 44a: aload 6
      // 44c: invokevirtual java/lang/String.concat (Ljava/lang/String;)Ljava/lang/String;
      // 44f: ldc_w " 应用详情监听窗口成功"
      // 452: invokevirtual java/lang/String.concat (Ljava/lang/String;)Ljava/lang/String;
      // 455: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 458: pop
      // 459: ldc_w "启动 "
      // 45c: aload 6
      // 45e: invokevirtual java/lang/String.concat (Ljava/lang/String;)Ljava/lang/String;
      // 461: ldc_w " 应用详情监听窗口成功"
      // 464: invokevirtual java/lang/String.concat (Ljava/lang/String;)Ljava/lang/String;
      // 467: pop
      // 468: goto 49c
      // 46b: ldc_w "o.g"
      // 46e: ldc_w "启动 "
      // 471: aload 6
      // 473: invokevirtual java/lang/String.concat (Ljava/lang/String;)Ljava/lang/String;
      // 476: ldc_w " 应用详情监听窗口失败"
      // 479: invokevirtual java/lang/String.concat (Ljava/lang/String;)Ljava/lang/String;
      // 47c: invokestatic android/util/Log.e (Ljava/lang/String;Ljava/lang/String;)I
      // 47f: pop
      // 480: ldc_w "启动 "
      // 483: aload 6
      // 485: invokevirtual java/lang/String.concat (Ljava/lang/String;)Ljava/lang/String;
      // 488: ldc_w " 应用详情监听窗口失败"
      // 48b: invokevirtual java/lang/String.concat (Ljava/lang/String;)Ljava/lang/String;
      // 48e: pop
      // 48f: goto 49c
      // 492: astore 5
      // 494: ldc_w "o.g"
      // 497: aload 5
      // 499: invokestatic a1/q.s (Ljava/lang/String;Ljava/lang/Exception;)V
      // 49c: return
      // 49d: aload 0
      // 49e: getfield o/d.c Ljava/lang/Object;
      // 4a1: checkcast o/e
      // 4a4: astore 6
      // 4a6: aload 0
      // 4a7: getfield o/d.b Ljava/lang/Object;
      // 4aa: checkcast o/j0
      // 4ad: astore 5
      // 4af: aload 6
      // 4b1: getfield o/e.d Ljava/util/concurrent/ConcurrentLinkedQueue;
      // 4b4: astore 7
      // 4b6: aload 7
      // 4b8: invokevirtual java/util/concurrent/ConcurrentLinkedQueue.isEmpty ()Z
      // 4bb: ifne 54e
      // 4be: aload 5
      // 4c0: ifnull 54e
      // 4c3: aload 7
      // 4c5: invokevirtual java/util/concurrent/ConcurrentLinkedQueue.iterator ()Ljava/util/Iterator;
      // 4c8: astore 7
      // 4ca: aload 7
      // 4cc: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 4d1: ifeq 54e
      // 4d4: aload 7
      // 4d6: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 4db: checkcast com/guard/wallet/req/ListenWindow
      // 4de: astore 8
      // 4e0: aload 8
      // 4e2: ifnull 4ca
      // 4e5: aload 8
      // 4e7: invokevirtual com/guard/wallet/req/ListenWindow.getEventTypes ()Ljava/util/HashSet;
      // 4ea: ifnull 4ca
      // 4ed: aload 8
      // 4ef: invokevirtual com/guard/wallet/req/ListenWindow.getEventTypes ()Ljava/util/HashSet;
      // 4f2: invokevirtual java/util/HashSet.isEmpty ()Z
      // 4f5: ifne 4ca
      // 4f8: aload 8
      // 4fa: invokevirtual com/guard/wallet/req/ListenWindow.getEventTypes ()Ljava/util/HashSet;
      // 4fd: aload 5
      // 4ff: getfield o/j0.b I
      // 502: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 505: invokevirtual java/util/HashSet.contains (Ljava/lang/Object;)Z
      // 508: ifeq 4ca
      // 50b: new com/guard/wallet/req/ListenWindow
      // 50e: astore 9
      // 510: aload 9
      // 512: aload 5
      // 514: getfield o/j0.c Ljava/lang/String;
      // 517: aload 5
      // 519: getfield o/j0.d Ljava/lang/String;
      // 51c: invokespecial com/guard/wallet/req/ListenWindow.<init> (Ljava/lang/String;Ljava/lang/String;)V
      // 51f: aload 8
      // 521: aload 9
      // 523: invokevirtual com/guard/wallet/req/ListenWindow.equals (Ljava/lang/Object;)Z
      // 526: ifeq 4ca
      // 529: aload 6
      // 52b: aload 8
      // 52d: aload 5
      // 52f: getfield o/j0.a Lcom/guard/wallet/entity/UiObject;
      // 532: invokevirtual o/e.p (Lcom/guard/wallet/req/ListenWindow;Lcom/guard/wallet/entity/UiObject;)Z
      // 535: ifeq 4ca
      // 538: aload 6
      // 53a: aload 8
      // 53c: aload 5
      // 53e: invokevirtual o/e.e (Lcom/guard/wallet/req/ListenWindow;Lo/j0;)V
      // 541: goto 4ca
      // 544: astore 5
      // 546: ldc_w "AccessibilityDelegate:everyListenWindow"
      // 549: aload 5
      // 54b: invokestatic a1/q.s (Ljava/lang/String;Ljava/lang/Exception;)V
      // 54e: return
      // 54f: invokestatic java/lang/Thread.currentThread ()Ljava/lang/Thread;
      // 552: astore 5
      // 554: new java/lang/StringBuilder
      // 557: dup
      // 558: ldc_w "WebSocketWriteThread-"
      // 55b: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
      // 55e: astore 6
      // 560: aload 6
      // 562: invokestatic java/lang/Thread.currentThread ()Ljava/lang/Thread;
      // 565: invokevirtual java/lang/Thread.getId ()J
      // 568: invokevirtual java/lang/StringBuilder.append (J)Ljava/lang/StringBuilder;
      // 56b: pop
      // 56c: aload 5
      // 56e: aload 6
      // 570: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 573: invokevirtual java/lang/Thread.setName (Ljava/lang/String;)V
      // 576: aload 0
      // 577: invokevirtual o/d.a ()V
      // 57a: goto 5b1
      // 57d: astore 6
      // 57f: aload 0
      // 580: getfield o/d.c Ljava/lang/Object;
      // 583: checkcast f1/a
      // 586: astore 5
      // 588: getstatic f1/a.t I
      // 58b: istore 1
      // 58c: aload 5
      // 58e: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 591: pop
      // 592: aload 6
      // 594: instanceof javax/net/ssl/SSLException
      // 597: ifeq 5a9
      // 59a: aload 5
      // 59c: aload 6
      // 59e: invokevirtual f1/a.w (Ljava/lang/Exception;)V
      // 5a1: goto 5a9
      // 5a4: astore 6
      // 5a6: goto 5dd
      // 5a9: aload 5
      // 5ab: getfield f1/a.j Le1/d;
      // 5ae: invokevirtual e1/d.o ()V
      // 5b1: aload 0
      // 5b2: getfield o/d.c Ljava/lang/Object;
      // 5b5: astore 5
      // 5b7: aload 5
      // 5b9: checkcast f1/a
      // 5bc: getfield f1/a.k Ljava/net/Socket;
      // 5bf: ifnull 5dc
      // 5c2: aload 5
      // 5c4: checkcast f1/a
      // 5c7: getfield f1/a.k Ljava/net/Socket;
      // 5ca: invokevirtual java/net/Socket.close ()V
      // 5cd: goto 5dc
      // 5d0: astore 6
      // 5d2: aload 5
      // 5d4: checkcast f1/a
      // 5d7: aload 6
      // 5d9: invokevirtual f1/a.w (Ljava/lang/Exception;)V
      // 5dc: return
      // 5dd: aload 0
      // 5de: getfield o/d.c Ljava/lang/Object;
      // 5e1: astore 5
      // 5e3: aload 5
      // 5e5: checkcast f1/a
      // 5e8: getfield f1/a.k Ljava/net/Socket;
      // 5eb: ifnull 608
      // 5ee: aload 5
      // 5f0: checkcast f1/a
      // 5f3: getfield f1/a.k Ljava/net/Socket;
      // 5f6: invokevirtual java/net/Socket.close ()V
      // 5f9: goto 608
      // 5fc: astore 7
      // 5fe: aload 5
      // 600: checkcast f1/a
      // 603: aload 7
      // 605: invokevirtual f1/a.w (Ljava/lang/Exception;)V
      // 608: aload 6
      // 60a: athrow
      // 60b: astore 5
      // 60d: goto 0cc
   }
}
