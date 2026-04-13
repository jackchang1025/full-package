package v0;

import java.io.IOException;

public final class x implements a1.t {
   public final a1.e a;
   public final a1.e b;
   public final long c;
   public boolean d;
   public boolean e;
   public final y f;

   public x(y var1, long var2) {
      this.f = var1;
      this.a = new a1.e();
      this.b = new a1.e();
      this.c = var2;
   }

   @Override
   public final a1.v a() {
      return this.f.i;
   }

   @Override
   public final void close() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield v0/x.f Lv0/y;
      // 04: astore 3
      // 05: aload 3
      // 06: monitorenter
      // 07: aload 0
      // 08: bipush 1
      // 09: putfield v0/x.d Z
      // 0c: aload 0
      // 0d: getfield v0/x.b La1/e;
      // 10: astore 4
      // 12: aload 4
      // 14: getfield a1/e.b J
      // 17: lstore 1
      // 18: aload 4
      // 1a: invokevirtual a1/e.x ()V
      // 1d: aload 0
      // 1e: getfield v0/x.f Lv0/y;
      // 21: invokevirtual java/lang/Object.notifyAll ()V
      // 24: aload 3
      // 25: monitorexit
      // 26: lload 1
      // 27: lconst_0
      // 28: lcmp
      // 29: ifle 37
      // 2c: aload 0
      // 2d: getfield v0/x.f Lv0/y;
      // 30: getfield v0/y.d Lv0/s;
      // 33: lload 1
      // 34: invokevirtual v0/s.D (J)V
      // 37: aload 0
      // 38: getfield v0/x.f Lv0/y;
      // 3b: invokevirtual v0/y.a ()V
      // 3e: return
      // 3f: astore 4
      // 41: aload 3
      // 42: monitorexit
      // 43: aload 4
      // 45: athrow
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public final long u(a1.e var1, long var2) {
      if (var2 < 0L) {
         StringBuilder var198 = new StringBuilder("byteCount < 0: ");
         var198.append(var2);
         throw new IllegalArgumentException(var198.toString());
      } else {
         while (true) {
            y var9 = this.f;
            synchronized (var9){} // $VF: monitorenter 

            Throwable var10000;
            label1195: {
               try {
                  this.f.i.i();
               } catch (Throwable var192) {
                  var10000 = var192;
                  boolean var10001 = false;
                  break label1195;
               }

               label1196: {
                  y var201;
                  label1184: {
                     label1183: {
                        try {
                           var201 = this.f;
                           if (var201.k == null) {
                              break label1183;
                           }

                           var201 = var201.l;
                        } catch (Throwable var191) {
                           var10000 = var191;
                           boolean var203 = false;
                           break label1196;
                        }

                        if (var201 == null) {
                           try {
                              var201 = new d0(this.f.k);
                           } catch (Throwable var187) {
                              var10000 = var187;
                              boolean var204 = false;
                              break label1196;
                           }
                        }
                        break label1184;
                     }

                     var201 = null;
                  }

                  label1202: {
                     long var4;
                     a1.e var10;
                     try {
                        if (this.d) {
                           break label1202;
                        }

                        var10 = this.b;
                        var4 = var10.b;
                     } catch (Throwable var190) {
                        var10000 = var190;
                        boolean var205 = false;
                        break label1196;
                     }

                     if (var4 > 0L) {
                        long var6;
                        try {
                           var4 = var10.u(var1, Math.min(var2, var4));
                           var193 = this.f;
                           var6 = var193.a + var4;
                           var193.a = var6;
                        } catch (Throwable var186) {
                           var10000 = var186;
                           boolean var209 = false;
                           break label1196;
                        }

                        var2 = var4;
                        label1160:
                        if (var201 == null) {
                           var2 = var4;

                           try {
                              if (var6 < (long)(var193.d.r.d() / 2)) {
                                 break label1160;
                              }

                              y var194 = this.f;
                              var194.d.G(var194.c, var194.a);
                              this.f.a = 0L;
                           } catch (Throwable var188) {
                              var10000 = var188;
                              boolean var210 = false;
                              break label1196;
                           }

                           var2 = var4;
                        }
                     } else {
                        label1167: {
                           try {
                              if (this.e) {
                                 break label1167;
                              }
                           } catch (Throwable var189) {
                              var10000 = var189;
                              boolean var206 = false;
                              break label1196;
                           }

                           if (var201 == null) {
                              try {
                                 this.f.i();
                              } catch (Throwable var185) {
                                 var10000 = var185;
                                 boolean var207 = false;
                                 break label1196;
                              }

                              try {
                                 this.f.i.o();
                                 // $VF: monitorexit
                                 continue;
                              } catch (Throwable var182) {
                                 var10000 = var182;
                                 boolean var208 = false;
                                 break label1195;
                              }
                           }
                        }

                        var2 = -1L;
                     }

                     try {
                        this.f.i.o();
                        // $VF: monitorexit
                     } catch (Throwable var183) {
                        var10000 = var183;
                        boolean var211 = false;
                        break label1195;
                     }

                     if (var2 != -1L) {
                        this.f.d.D(var2);
                        return var2;
                     }

                     if (var201 == null) {
                        return -1L;
                     }

                     throw var201;
                  }

                  label1143:
                  try {
                     IOException var197 = new IOException("stream closed");
                     throw var197;
                  } catch (Throwable var184) {
                     var10000 = var184;
                     boolean var212 = false;
                     break label1143;
                  }
               }

               Throwable var195 = var10000;

               label1135:
               try {
                  this.f.i.o();
                  throw var195;
               } catch (Throwable var181) {
                  var10000 = var181;
                  boolean var213 = false;
                  break label1135;
               }
            }

            while (true) {
               Throwable var196 = var10000;

               try {
                  // $VF: monitorexit
                  throw var196;
               } catch (Throwable var180) {
                  var10000 = var180;
                  boolean var214 = false;
                  continue;
               }
            }
         }
      }
   }
}
