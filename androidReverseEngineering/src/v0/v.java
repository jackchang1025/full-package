package v0;

import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class v implements Closeable {
   public static final Logger e = Logger.getLogger(g.class.getName());
   public final a1.g a;
   public final u b;
   public final boolean c;
   public final d d;

   public v(a1.g var1, boolean var2) {
      this.a = var1;
      this.c = var2;
      u var3 = new u(var1);
      this.b = var3;
      this.d = new d(var3);
   }

   public static int x(int var0, byte var1, short var2) {
      int var3 = var0;
      if ((var1 & 8) != 0) {
         var3 = var0 - 1;
      }

      if (var2 <= var3) {
         return (short)(var3 - var2);
      } else {
         g.b(new Object[]{var2, var3}, "PROTOCOL_ERROR padding %s > remaining length %s");
         throw null;
      }
   }

   public final void A(q param1, int param2, int param3) {
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
      // 000: bipush 0
      // 001: istore 4
      // 003: iload 2
      // 004: bipush 8
      // 006: if_icmplt 149
      // 009: iload 3
      // 00a: ifne 13e
      // 00d: aload 0
      // 00e: getfield v0/v.a La1/g;
      // 011: invokeinterface a1/g.readInt ()I 1
      // 016: istore 3
      // 017: aload 0
      // 018: getfield v0/v.a La1/g;
      // 01b: invokeinterface a1/g.readInt ()I 1
      // 020: istore 5
      // 022: iload 2
      // 023: bipush 8
      // 025: isub
      // 026: istore 7
      // 028: invokestatic v0/b.values ()[Lv0/b;
      // 02b: astore 9
      // 02d: aload 9
      // 02f: arraylength
      // 030: istore 6
      // 032: bipush 0
      // 033: istore 2
      // 034: iload 2
      // 035: iload 6
      // 037: if_icmpge 053
      // 03a: aload 9
      // 03c: iload 2
      // 03d: aaload
      // 03e: astore 8
      // 040: aload 8
      // 042: getfield v0/b.a I
      // 045: iload 5
      // 047: if_icmpne 04d
      // 04a: goto 056
      // 04d: iinc 2 1
      // 050: goto 034
      // 053: aconst_null
      // 054: astore 8
      // 056: aload 8
      // 058: ifnull 12b
      // 05b: getstatic a1/h.e La1/h;
      // 05e: astore 8
      // 060: iload 7
      // 062: ifle 073
      // 065: aload 0
      // 066: getfield v0/v.a La1/g;
      // 069: iload 7
      // 06b: i2l
      // 06c: invokeinterface a1/g.h (J)La1/h; 3
      // 071: astore 8
      // 073: aload 1
      // 074: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 077: pop
      // 078: aload 8
      // 07a: invokevirtual a1/h.j ()I
      // 07d: pop
      // 07e: aload 1
      // 07f: getfield v0/q.d Ljava/lang/Object;
      // 082: checkcast v0/s
      // 085: astore 8
      // 087: aload 8
      // 089: monitorenter
      // 08a: aload 1
      // 08b: getfield v0/q.d Ljava/lang/Object;
      // 08e: checkcast v0/s
      // 091: getfield v0/s.c Ljava/util/LinkedHashMap;
      // 094: invokevirtual java/util/LinkedHashMap.values ()Ljava/util/Collection;
      // 097: aload 1
      // 098: getfield v0/q.d Ljava/lang/Object;
      // 09b: checkcast v0/s
      // 09e: getfield v0/s.c Ljava/util/LinkedHashMap;
      // 0a1: invokeinterface java/util/Map.size ()I 1
      // 0a6: anewarray 130
      // 0a9: invokeinterface java/util/Collection.toArray ([Ljava/lang/Object;)[Ljava/lang/Object; 2
      // 0ae: checkcast [Lv0/y;
      // 0b1: astore 9
      // 0b3: aload 1
      // 0b4: getfield v0/q.d Ljava/lang/Object;
      // 0b7: checkcast v0/s
      // 0ba: bipush 1
      // 0bb: putfield v0/s.g Z
      // 0be: aload 8
      // 0c0: monitorexit
      // 0c1: aload 9
      // 0c3: arraylength
      // 0c4: istore 5
      // 0c6: iload 4
      // 0c8: istore 2
      // 0c9: iload 2
      // 0ca: iload 5
      // 0cc: if_icmpge 124
      // 0cf: aload 9
      // 0d1: iload 2
      // 0d2: aaload
      // 0d3: astore 8
      // 0d5: aload 8
      // 0d7: getfield v0/y.c I
      // 0da: iload 3
      // 0db: if_icmple 11e
      // 0de: aload 8
      // 0e0: invokevirtual v0/y.f ()Z
      // 0e3: ifeq 11e
      // 0e6: getstatic v0/b.f Lv0/b;
      // 0e9: astore 10
      // 0eb: aload 8
      // 0ed: monitorenter
      // 0ee: aload 8
      // 0f0: getfield v0/y.k Lv0/b;
      // 0f3: ifnonnull 102
      // 0f6: aload 8
      // 0f8: aload 10
      // 0fa: putfield v0/y.k Lv0/b;
      // 0fd: aload 8
      // 0ff: invokevirtual java/lang/Object.notifyAll ()V
      // 102: aload 8
      // 104: monitorexit
      // 105: aload 1
      // 106: getfield v0/q.d Ljava/lang/Object;
      // 109: checkcast v0/s
      // 10c: aload 8
      // 10e: getfield v0/y.c I
      // 111: invokevirtual v0/s.B (I)Lv0/y;
      // 114: pop
      // 115: goto 11e
      // 118: astore 1
      // 119: aload 8
      // 11b: monitorexit
      // 11c: aload 1
      // 11d: athrow
      // 11e: iinc 2 1
      // 121: goto 0c9
      // 124: return
      // 125: astore 1
      // 126: aload 8
      // 128: monitorexit
      // 129: aload 1
      // 12a: athrow
      // 12b: bipush 1
      // 12c: anewarray 4
      // 12f: dup
      // 130: bipush 0
      // 131: iload 5
      // 133: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 136: aastore
      // 137: ldc "TYPE_GOAWAY unexpected error code: %d"
      // 139: invokestatic v0/g.b ([Ljava/lang/Object;Ljava/lang/String;)V
      // 13c: aconst_null
      // 13d: athrow
      // 13e: bipush 0
      // 13f: anewarray 4
      // 142: ldc "TYPE_GOAWAY streamId != 0"
      // 144: invokestatic v0/g.b ([Ljava/lang/Object;Ljava/lang/String;)V
      // 147: aconst_null
      // 148: athrow
      // 149: bipush 1
      // 14a: anewarray 4
      // 14d: dup
      // 14e: bipush 0
      // 14f: iload 2
      // 150: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 153: aastore
      // 154: ldc "TYPE_GOAWAY length < 8: %s"
      // 156: invokestatic v0/g.b ([Ljava/lang/Object;Ljava/lang/String;)V
      // 159: aconst_null
      // 15a: athrow
   }

   public final ArrayList B(int var1, short var2, byte var3, int var4) {
      u var7 = this.b;
      var7.e = var1;
      var7.b = var1;
      var7.f = var2;
      var7.c = var3;
      var7.d = var4;

      while (true) {
         d var17 = this.d;
         a1.o var9 = var17.b;
         boolean var6 = var9.n();
         ArrayList var8 = var17.a;
         if (var6) {
            ArrayList var21 = new ArrayList(var8);
            var8.clear();
            return var21;
         }

         var1 = var9.readByte() & 255;
         if (var1 == 128) {
            throw new IOException("index == 0");
         }

         int var15 = 0;
         if ((var1 & 128) == 128) {
            int var5 = var17.e(var1, 127) - 1;
            int var12 = var15;
            if (var5 >= 0) {
               var12 = var15;
               if (var5 <= f.a.length - 1) {
                  var12 = 1;
               }
            }

            c var20;
            if (var12) {
               var20 = f.a[var5];
            } else {
               label74: {
                  var12 = f.a.length;
                  var12 = var17.f + 1 + (var5 - var12);
                  if (var12 >= 0) {
                     c[] var18 = var17.e;
                     if (var12 < var18.length) {
                        var20 = var18[var12];
                        break label74;
                     }
                  }

                  StringBuilder var19 = new StringBuilder("Header index too large ");
                  var19.append(var5 + 1);
                  throw new IOException(var19.toString());
               }
            }

            var8.add(var20);
         } else if (var1 == 64) {
            a1.h var23 = var17.d();
            f.a(var23);
            var17.c(new c(var23, var17.d()));
         } else if ((var1 & 64) == 64) {
            var17.c(new c(var17.b(var17.e(var1, 63) - 1), var17.d()));
         } else if ((var1 & 32) == 32) {
            var1 = var17.e(var1, 31);
            var17.d = var1;
            if (var1 < 0 || var1 > var17.c) {
               StringBuilder var22 = new StringBuilder("Invalid dynamic table size update ");
               var22.append(var17.d);
               throw new IOException(var22.toString());
            }

            var15 = var17.h;
            if (var1 < var15) {
               if (var1 == 0) {
                  Arrays.fill(var17.e, null);
                  var17.f = var17.e.length - 1;
                  var17.g = 0;
                  var17.h = 0;
               } else {
                  var17.a(var15 - var1);
               }
            }
         } else if (var1 != 16 && var1 != 0) {
            var8.add(new c(var17.b(var17.e(var1, 15) - 1), var17.d()));
         } else {
            a1.h var24 = var17.d();
            f.a(var24);
            var8.add(new c(var24, var17.d()));
         }
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final void C(q var1, int var2, byte var3, int var4) {
      boolean var5 = false;
      if (var2 != 8) {
         g.b(new Object[]{var2}, "TYPE_PING length != 8: %s");
         throw null;
      } else if (var4 == 0) {
         int var6 = this.a.readInt();
         var4 = this.a.readInt();
         boolean var50 = var5;
         if ((var3 & 1) != 0) {
            var50 = true;
         }

         var1.getClass();
         if (!var50) {
            try {
               Object var49 = var1.d;
               ScheduledThreadPoolExecutor var8 = ((s)var49).h;
               p var52 = new p((s)var49, var6, var4);
               var8.execute(var52);
            } catch (RejectedExecutionException var39) {
            }
         } else {
            s var7;
            Throwable var10000;
            label248: {
               var7 = (s)var1.d;
               synchronized (var7){} // $VF: monitorenter 
               if (var6 == 1) {
                  try {
                     s var45 = (s)var1.d;
                     var45.l++;
                  } catch (Throwable var44) {
                     var10000 = var44;
                     boolean var10001 = false;
                     break label248;
                  }
               } else if (var6 == 2) {
                  try {
                     s var46 = (s)var1.d;
                     var46.n++;
                  } catch (Throwable var43) {
                     var10000 = var43;
                     boolean var53 = false;
                     break label248;
                  }
               } else if (var6 == 3) {
                  try {
                     Object var47 = var1.d;
                     ((s)var47).getClass();
                     ((s)var47).notifyAll();
                  } catch (Throwable var42) {
                     var10000 = var42;
                     boolean var54 = false;
                     break label248;
                  }
               }

               label237:
               try {
                  // $VF: monitorexit
                  return;
               } catch (Throwable var41) {
                  var10000 = var41;
                  boolean var55 = false;
                  break label237;
               }
            }

            while (true) {
               Throwable var48 = var10000;

               try {
                  // $VF: monitorexit
                  throw var48;
               } catch (Throwable var40) {
                  var10000 = var40;
                  boolean var56 = false;
                  continue;
               }
            }
         }
      } else {
         g.b(new Object[0], "TYPE_PING streamId != 0");
         throw null;
      }
   }

   public final void D(q param1, int param2, int param3) {
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
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.processStatement(ExprProcessor.java:112)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.getFinallyInformation(FinallyProcessor.java:136)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:85)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 00: iload 2
      // 01: bipush 4
      // 02: if_icmpne a5
      // 05: aload 0
      // 06: getfield v0/v.a La1/g;
      // 09: invokeinterface a1/g.readInt ()I 1
      // 0e: i2l
      // 0f: ldc2_w 2147483647
      // 12: land
      // 13: lstore 4
      // 15: lload 4
      // 17: lconst_0
      // 18: lcmp
      // 19: istore 2
      // 1a: iload 2
      // 1b: ifeq 91
      // 1e: iload 3
      // 1f: ifne 59
      // 22: aload 1
      // 23: getfield v0/q.d Ljava/lang/Object;
      // 26: checkcast v0/s
      // 29: astore 6
      // 2b: aload 6
      // 2d: monitorenter
      // 2e: aload 1
      // 2f: getfield v0/q.d Ljava/lang/Object;
      // 32: astore 1
      // 33: aload 1
      // 34: checkcast v0/s
      // 37: astore 7
      // 39: aload 7
      // 3b: aload 7
      // 3d: getfield v0/s.q J
      // 40: lload 4
      // 42: ladd
      // 43: putfield v0/s.q J
      // 46: aload 1
      // 47: checkcast v0/s
      // 4a: invokevirtual java/lang/Object.notifyAll ()V
      // 4d: aload 6
      // 4f: monitorexit
      // 50: goto 90
      // 53: astore 1
      // 54: aload 6
      // 56: monitorexit
      // 57: aload 1
      // 58: athrow
      // 59: aload 1
      // 5a: getfield v0/q.d Ljava/lang/Object;
      // 5d: checkcast v0/s
      // 60: iload 3
      // 61: invokevirtual v0/s.z (I)Lv0/y;
      // 64: astore 6
      // 66: aload 6
      // 68: ifnull 90
      // 6b: aload 6
      // 6d: monitorenter
      // 6e: aload 6
      // 70: aload 6
      // 72: getfield v0/y.b J
      // 75: lload 4
      // 77: ladd
      // 78: putfield v0/y.b J
      // 7b: iload 2
      // 7c: ifle 84
      // 7f: aload 6
      // 81: invokevirtual java/lang/Object.notifyAll ()V
      // 84: aload 6
      // 86: monitorexit
      // 87: goto 90
      // 8a: astore 1
      // 8b: aload 6
      // 8d: monitorexit
      // 8e: aload 1
      // 8f: athrow
      // 90: return
      // 91: bipush 1
      // 92: anewarray 4
      // 95: dup
      // 96: bipush 0
      // 97: lload 4
      // 99: invokestatic java/lang/Long.valueOf (J)Ljava/lang/Long;
      // 9c: aastore
      // 9d: ldc_w "windowSizeIncrement was 0"
      // a0: invokestatic v0/g.b ([Ljava/lang/Object;Ljava/lang/String;)V
      // a3: aconst_null
      // a4: athrow
      // a5: bipush 1
      // a6: anewarray 4
      // a9: dup
      // aa: bipush 0
      // ab: iload 2
      // ac: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // af: aastore
      // b0: ldc_w "TYPE_WINDOW_UPDATE length !=4: %s"
      // b3: invokestatic v0/g.b ([Ljava/lang/Object;Ljava/lang/String;)V
      // b6: aconst_null
      // b7: athrow
   }

   @Override
   public final void close() {
      this.a.close();
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final boolean y(boolean var1, q var2) {
      try {
         this.a.r(9L);
      } catch (EOFException var521) {
         return false;
      }

      a1.g var16 = this.a;
      int var6 = var16.readByte();
      int var7 = var16.readByte();
      int var8 = var16.readByte() & 255 | (var6 & 0xFF) << 16 | (var7 & 0xFF) << 8;
      if (var8 >= 0 && var8 <= 16384) {
         byte var3 = (byte)(this.a.readByte() & 255);
         if (var1 && var3 != 4) {
            g.b(new Object[]{var3}, "Expected a SETTINGS frame but was %s");
            throw null;
         } else {
            byte var4 = (byte)(this.a.readByte() & 255);
            var7 = this.a.readInt() & 2147483647;
            Logger var578 = e;
            if (var578.isLoggable(Level.FINE)) {
               var578.fine(g.a(true, var7, var8, var3, var4));
            }

            switch (var3) {
               case 0:
                  if (var7 == 0) {
                     g.b(new Object[0], "PROTOCOL_ERROR: TYPE_DATA streamId == 0");
                     throw null;
                  }

                  if ((var4 & 1) != 0) {
                     var1 = true;
                  } else {
                     var1 = false;
                  }

                  boolean var564;
                  if ((var4 & 32) != 0) {
                     var564 = 1;
                  } else {
                     var564 = 0;
                  }

                  if (var564) {
                     g.b(new Object[0], "PROTOCOL_ERROR: FLAG_COMPRESSED without SETTINGS_COMPRESS_DATA");
                     throw null;
                  }

                  short var557;
                  if ((var4 & 8) != 0) {
                     var557 = (short)(this.a.readByte() & 255);
                  } else {
                     var557 = 0;
                  }

                  var8 = x(var8, var4, var557);
                  var16 = this.a;
                  ((s)var2.d).getClass();
                  if (var7 != 0 && (var7 & 1) == 0) {
                     var564 = (boolean)1;
                  } else {
                     var564 = (boolean)0;
                  }

                  label6333: {
                     if (var564) {
                        s var596 = (s)var2.d;
                        var596.getClass();
                        a1.e var552 = new a1.e();
                        long var12 = (long)var8;
                        var16.r(var12);
                        var16.u(var552, var12);
                        if (var552.b != var12) {
                           StringBuilder var588 = new StringBuilder();
                           var588.append(var552.b);
                           var588.append(" != ");
                           var588.append(var8);
                           throw new IOException(var588.toString());
                        }

                        var596.A(new l(var596, new Object[]{var596.d, var7}, var7, var552, var8, var1));
                     } else {
                        y var597 = ((s)var2.d).z(var7);
                        if (var597 != null) {
                           x var603 = var597.g;
                           long var575 = (long)var8;

                           while (true) {
                              if (var575 <= 0L) {
                                 var603.getClass();
                                 break;
                              }

                              y var554 = var603.f;
                              synchronized (var554){} // $VF: monitorenter 

                              boolean var11;
                              label6403: {
                                 Throwable var608;
                                 label6321: {
                                    label6320: {
                                       label6319: {
                                          try {
                                             var11 = var603.e;
                                             if (var603.b.b + var575 > var603.c) {
                                                break label6319;
                                             }
                                          } catch (Throwable var539) {
                                             var608 = var539;
                                             boolean var623 = false;
                                             break label6321;
                                          }

                                          var569 = false;
                                          break label6320;
                                       }

                                       var569 = true;
                                    }

                                    label6313:
                                    try {
                                       // $VF: monitorexit
                                       break label6403;
                                    } catch (Throwable var538) {
                                       var608 = var538;
                                       boolean var624 = false;
                                       break label6313;
                                    }
                                 }

                                 while (true) {
                                    Throwable var589 = var608;

                                    try {
                                       // $VF: monitorexit
                                       throw var589;
                                    } catch (Throwable var522) {
                                       var608 = var522;
                                       boolean var625 = false;
                                       continue;
                                    }
                                 }
                              }

                              if (var569) {
                                 var16.skip(var575);
                                 var603.f.e(v0.b.e);
                                 break;
                              }

                              if (var11) {
                                 var16.skip(var575);
                                 break;
                              }

                              long var14 = var16.u(var603.a, var575);
                              if (var14 == -1L) {
                                 throw new EOFException();
                              }

                              var14 = var575 - var14;
                              y var555 = var603.f;
                              synchronized (var555){} // $VF: monitorenter 

                              label6404: {
                                 Throwable var609;
                                 label6416: {
                                    label6405: {
                                       try {
                                          if (var603.d) {
                                             a1.e var606 = var603.a;
                                             var575 = var606.b;
                                             var606.x();
                                             break label6405;
                                          }
                                       } catch (Throwable var530) {
                                          var609 = var530;
                                          boolean var626 = false;
                                          break label6416;
                                       }

                                       a1.e var605;
                                       label6219: {
                                          label6218: {
                                             try {
                                                var605 = var603.b;
                                                if (var605.b == 0L) {
                                                   break label6218;
                                                }
                                             } catch (Throwable var529) {
                                                var609 = var529;
                                                boolean var627 = false;
                                                break label6416;
                                             }

                                             var570 = false;
                                             break label6219;
                                          }

                                          var570 = true;
                                       }

                                       try {
                                          var605.d(var603.a);
                                       } catch (Throwable var528) {
                                          var609 = var528;
                                          boolean var628 = false;
                                          break label6416;
                                       }

                                       if (var570) {
                                          try {
                                             var603.f.notifyAll();
                                          } catch (Throwable var527) {
                                             var609 = var527;
                                             boolean var629 = false;
                                             break label6416;
                                          }
                                       }

                                       var575 = 0L;
                                    }

                                    label6205:
                                    try {
                                       // $VF: monitorexit
                                       break label6404;
                                    } catch (Throwable var526) {
                                       var609 = var526;
                                       boolean var630 = false;
                                       break label6205;
                                    }
                                 }

                                 while (true) {
                                    Throwable var590 = var609;

                                    try {
                                       // $VF: monitorexit
                                       throw var590;
                                    } catch (Throwable var525) {
                                       var609 = var525;
                                       boolean var631 = false;
                                       continue;
                                    }
                                 }
                              }

                              if (var575 > 0L) {
                                 var603.f.d.D(var575);
                              }

                              var575 = var14;
                           }

                           var564 = var557;
                           if (var1) {
                              var597.h(q0.c.c, true);
                              var564 = var557;
                           }
                           break label6333;
                        }

                        ((s)var2.d).F(var7, v0.b.c);
                        s var553 = (s)var2.d;
                        long var574 = (long)var8;
                        var553.D(var574);
                        var16.skip(var574);
                     }

                     var564 = var557;
                  }

                  this.a.skip((long)var564);
                  break;
               case 1:
                  if (var7 == 0) {
                     g.b(new Object[0], "PROTOCOL_ERROR: TYPE_HEADERS streamId == 0");
                     throw null;
                  }

                  if ((var4 & 1) != 0) {
                     var1 = true;
                  } else {
                     var1 = false;
                  }

                  short var556;
                  if ((var4 & 8) != 0) {
                     var556 = (short)(this.a.readByte() & 255);
                  } else {
                     var556 = 0;
                  }

                  var6 = var8;
                  if ((var4 & 32) != 0) {
                     var16 = this.a;
                     var16.readInt();
                     var16.readByte();
                     var2.getClass();
                     var6 = var8 - 5;
                  }

                  ArrayList var594 = this.B(x(var6, var4, var556), var556, var4, var7);
                  ((s)var2.d).getClass();
                  boolean var563;
                  if (var7 != 0 && (var7 & 1) == 0) {
                     var563 = true;
                  } else {
                     var563 = false;
                  }

                  if (!var563) {
                     s var586 = (s)var2.d;
                     synchronized (var586){} // $VF: monitorenter 

                     Throwable var607;
                     label6297: {
                        y var598;
                        try {
                           var598 = ((s)var2.d).z(var7);
                        } catch (Throwable var537) {
                           var607 = var537;
                           boolean var615 = false;
                           break label6297;
                        }

                        if (var598 == null) {
                           label6417: {
                              label6418: {
                                 try {
                                    var599 = var2.d;
                                    if (((s)var599).g) {
                                       break label6418;
                                    }
                                 } catch (Throwable var535) {
                                    var607 = var535;
                                    boolean var616 = false;
                                    break label6417;
                                 }

                                 try {
                                    if (var7 <= ((s)var599).e) {
                                       break label6418;
                                    }
                                 } catch (Throwable var534) {
                                    var607 = var534;
                                    boolean var617 = false;
                                    break label6417;
                                 }

                                 try {
                                    if (var7 % 2 == ((s)var599).f % 2) {
                                       break label6418;
                                    }
                                 } catch (Throwable var533) {
                                    var607 = var533;
                                    boolean var618 = false;
                                    break label6417;
                                 }

                                 try {
                                    p0.s var600 = q0.c.t(var594);
                                    y var595 = new y(var7, (s)var2.d, false, var1, var600);
                                    Object var601 = var2.d;
                                    ((s)var601).e = var7;
                                    ((s)var601).c.put(var7, var595);
                                    ThreadPoolExecutor var604 = s.x;
                                    var601 = new q(var2, new Object[]{((s)var2.d).d, var7}, var595);
                                    var604.execute((Runnable)var601);
                                 } catch (Throwable var532) {
                                    var607 = var532;
                                    boolean var619 = false;
                                    break label6417;
                                 }
                              }

                              label6269:
                              try {
                                 // $VF: monitorexit
                              } catch (Throwable var531) {
                                 var607 = var531;
                                 boolean var620 = false;
                                 break label6269;
                              }
                           }
                        } else {
                           label6293: {
                              try {
                                 // $VF: monitorexit
                              } catch (Throwable var536) {
                                 var607 = var536;
                                 boolean var621 = false;
                                 break label6293;
                              }

                              var598.h(q0.c.t(var594), var1);
                           }
                        }
                        break;
                     }

                     while (true) {
                        Throwable var551 = var607;

                        try {
                           // $VF: monitorexit
                           throw var551;
                        } catch (Throwable var524) {
                           var607 = var524;
                           boolean var622 = false;
                           continue;
                        }
                     }
                  } else {
                     s var585 = (s)var2.d;
                     var585.getClass();

                     try {
                        k var550 = new k(var585, new Object[]{var585.d, var7}, var7, var594, var1);
                        var585.A(var550);
                     } catch (RejectedExecutionException var542) {
                        boolean var614 = false;
                     }
                     break;
                  }
               case 2:
                  if (var8 != 5) {
                     g.b(new Object[]{var8}, "TYPE_PRIORITY length: %d != 5");
                     throw null;
                  }

                  if (var7 == 0) {
                     g.b(new Object[0], "TYPE_PRIORITY streamId == 0");
                     throw null;
                  }

                  var16 = this.a;
                  var16.readInt();
                  var16.readByte();
                  var2.getClass();
                  break;
               case 3:
                  if (var8 != 4) {
                     g.b(new Object[]{var8}, "TYPE_RST_STREAM length: %d != 4");
                     throw null;
                  }

                  if (var7 == 0) {
                     g.b(new Object[0], "TYPE_RST_STREAM streamId == 0");
                     throw null;
                  }

                  var8 = this.a.readInt();
                  b[] var593 = v0.b.values();
                  int var573 = var593.length;
                  var6 = 0;

                  while (true) {
                     if (var6 >= var573) {
                        var582 = null;
                        break;
                     }

                     var582 = var593[var6];
                     if (var582.a == var8) {
                        break;
                     }

                     var6++;
                  }

                  if (var582 == null) {
                     g.b(new Object[]{var8}, "TYPE_RST_STREAM unexpected error code: %d");
                     throw null;
                  }

                  s var548 = (s)var2.d;
                  var548.getClass();
                  boolean var561;
                  if (var7 != 0 && (var7 & 1) == 0) {
                     var561 = true;
                  } else {
                     var561 = false;
                  }

                  if (var561) {
                     var548.A(new h(var548, "OkHttp %s Push Reset[%s]", new Object[]{var548.d, var7}, var7, var582, 1));
                  } else {
                     y var549 = var548.B(var7);
                     if (var549 != null) {
                        synchronized (var549) {
                           if (var549.k == null) {
                              var549.k = var582;
                              var549.notifyAll();
                           }
                        }
                     }
                  }
                  break;
               case 4:
                  if (var7 != 0) {
                     g.b(new Object[0], "TYPE_SETTINGS streamId != 0");
                     throw null;
                  }

                  if ((var4 & 1) != 0) {
                     if (var8 != 0) {
                        g.b(new Object[0], "FRAME_SIZE_ERROR ack frame should be empty!");
                        throw null;
                     }

                     var2.getClass();
                  } else {
                     if (var8 % 6 != 0) {
                        g.b(new Object[]{var8}, "TYPE_SETTINGS length %% 6 != 0: %s");
                        throw null;
                     }

                     z.d var581 = new z.d();

                     for (byte var568 = 0; var568 < var8; var568 += 6) {
                        a1.g var591 = this.a;
                        int var9 = var591.readShort() & '\uffff';
                        int var10 = var591.readInt();
                        if (var9 != 2) {
                           if (var9 != 3) {
                              if (var9 != 4) {
                                 if (var9 != 5) {
                                    var6 = var9;
                                 } else {
                                    if (var10 < 16384 || var10 > 16777215) {
                                       g.b(new Object[]{var10}, "PROTOCOL_ERROR SETTINGS_MAX_FRAME_SIZE: %s");
                                       throw null;
                                    }

                                    var6 = var9;
                                 }
                              } else {
                                 if (var10 < 0) {
                                    g.b(new Object[0], "PROTOCOL_ERROR SETTINGS_INITIAL_WINDOW_SIZE > 2^31 - 1");
                                    throw null;
                                 }

                                 var6 = 7;
                              }
                           } else {
                              var6 = 4;
                           }
                        } else {
                           var6 = var9;
                           if (var10 != 0) {
                              if (var10 != 1) {
                                 g.b(new Object[0], "PROTOCOL_ERROR SETTINGS_ENABLE_PUSH != 0 or 1");
                                 throw null;
                              }

                              var6 = var9;
                           }
                        }

                        var581.e(var6, var10);
                     }

                     var2.getClass();

                     try {
                        Object var592 = var2.d;
                        ScheduledThreadPoolExecutor var19 = ((s)var592).h;
                        r var18 = new r(var2, new Object[]{((s)var592).d}, var581);
                        var19.execute(var18);
                     } catch (RejectedExecutionException var541) {
                        boolean var613 = false;
                     }
                  }
                  break;
               case 5:
                  if (var7 == 0) {
                     g.b(new Object[0], "PROTOCOL_ERROR: TYPE_PUSH_PROMISE streamId == 0");
                     throw null;
                  }

                  short var5;
                  if ((var4 & 8) != 0) {
                     var5 = (short)(this.a.readByte() & 255);
                  } else {
                     var5 = 0;
                  }

                  var6 = this.a.readInt() & 2147483647;
                  ArrayList var579 = this.B(x(var8 - 4, var4, var5), var5, var4, var7);
                  s var547 = (s)var2.d;
                  synchronized (var547){} // $VF: monitorenter 

                  Throwable var10000;
                  label6414: {
                     try {
                        if (var547.w.contains(var6)) {
                           var547.F(var6, v0.b.c);
                           // $VF: monitorexit
                           break;
                        }
                     } catch (Throwable var544) {
                        var10000 = var544;
                        boolean var10001 = false;
                        break label6414;
                     }

                     try {
                        var547.w.add(var6);
                        // $VF: monitorexit
                     } catch (Throwable var543) {
                        var10000 = var543;
                        boolean var610 = false;
                        break label6414;
                     }

                     try {
                        h var17 = new h(var547, "OkHttp %s Push Request[%s]", new Object[]{var547.d, var6}, var6, var579, 2);
                        var547.A(var17);
                     } catch (RejectedExecutionException var540) {
                        boolean var612 = false;
                     }
                     break;
                  }

                  while (true) {
                     Throwable var580 = var10000;

                     try {
                        // $VF: monitorexit
                        throw var580;
                     } catch (Throwable var523) {
                        var10000 = var523;
                        boolean var611 = false;
                        continue;
                     }
                  }
               case 6:
                  this.C(var2, var8, var4, var7);
                  break;
               case 7:
                  this.A(var2, var8, var7);
                  break;
               case 8:
                  this.D(var2, var8, var7);
                  break;
               default:
                  this.a.skip((long)var8);
            }

            return true;
         }
      } else {
         g.b(new Object[]{var8}, "FRAME_SIZE_ERROR: %s");
         throw null;
      }
   }

   public final void z(q var1) {
      if (this.c) {
         if (!this.y(true, var1)) {
            g.b(new Object[0], "Required SETTINGS preface not received");
            throw null;
         }
      } else {
         a1.h var5 = g.a;
         long var2 = (long)var5.a.length;
         a1.h var6 = this.a.h(var2);
         Level var4 = Level.FINE;
         Logger var7 = e;
         if (var7.isLoggable(var4)) {
            var7.fine(String.format("<< CONNECTION %s", var6.f()));
         }

         if (!var5.equals(var6)) {
            g.b(new Object[]{var6.m()}, "Expected a connection header but was %s");
            throw null;
         }
      }
   }
}
