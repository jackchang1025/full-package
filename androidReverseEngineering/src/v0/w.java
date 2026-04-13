package v0;

public final class w implements a1.s {
   public final a1.e a;
   public boolean b;
   public boolean c;
   public final y d;

   public w(y var1) {
      this.d = var1;
      this.a = new a1.e();
   }

   @Override
   public final a1.v a() {
      return this.d.j;
   }

   @Override
   public final void close() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield v0/w.d Lv0/y;
      // 04: astore 2
      // 05: aload 2
      // 06: monitorenter
      // 07: aload 0
      // 08: getfield v0/w.b Z
      // 0b: ifeq 11
      // 0e: aload 2
      // 0f: monitorexit
      // 10: return
      // 11: aload 2
      // 12: monitorexit
      // 13: aload 0
      // 14: getfield v0/w.d Lv0/y;
      // 17: astore 2
      // 18: aload 2
      // 19: getfield v0/y.h Lv0/w;
      // 1c: getfield v0/w.c Z
      // 1f: ifne 5b
      // 22: aload 0
      // 23: getfield v0/w.a La1/e;
      // 26: getfield a1/e.b J
      // 29: lconst_0
      // 2a: lcmp
      // 2b: ifle 33
      // 2e: bipush 1
      // 2f: istore 1
      // 30: goto 35
      // 33: bipush 0
      // 34: istore 1
      // 35: iload 1
      // 36: ifeq 4d
      // 39: aload 0
      // 3a: getfield v0/w.a La1/e;
      // 3d: getfield a1/e.b J
      // 40: lconst_0
      // 41: lcmp
      // 42: ifle 5b
      // 45: aload 0
      // 46: bipush 1
      // 47: invokevirtual v0/w.x (Z)V
      // 4a: goto 39
      // 4d: aload 2
      // 4e: getfield v0/y.d Lv0/s;
      // 51: aload 2
      // 52: getfield v0/y.c I
      // 55: bipush 1
      // 56: aconst_null
      // 57: lconst_0
      // 58: invokevirtual v0/s.E (IZLa1/e;J)V
      // 5b: aload 0
      // 5c: getfield v0/w.d Lv0/y;
      // 5f: astore 2
      // 60: aload 2
      // 61: monitorenter
      // 62: aload 0
      // 63: bipush 1
      // 64: putfield v0/w.b Z
      // 67: aload 2
      // 68: monitorexit
      // 69: aload 0
      // 6a: getfield v0/w.d Lv0/y;
      // 6d: getfield v0/y.d Lv0/s;
      // 70: invokevirtual v0/s.flush ()V
      // 73: aload 0
      // 74: getfield v0/w.d Lv0/y;
      // 77: invokevirtual v0/y.a ()V
      // 7a: return
      // 7b: astore 3
      // 7c: aload 2
      // 7d: monitorexit
      // 7e: aload 3
      // 7f: athrow
      // 80: astore 3
      // 81: aload 2
      // 82: monitorexit
      // 83: aload 3
      // 84: athrow
   }

   @Override
   public final void flush() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield v0/w.d Lv0/y;
      // 04: astore 1
      // 05: aload 1
      // 06: monitorenter
      // 07: aload 0
      // 08: getfield v0/w.d Lv0/y;
      // 0b: invokevirtual v0/y.b ()V
      // 0e: aload 1
      // 0f: monitorexit
      // 10: aload 0
      // 11: getfield v0/w.a La1/e;
      // 14: getfield a1/e.b J
      // 17: lconst_0
      // 18: lcmp
      // 19: ifle 2e
      // 1c: aload 0
      // 1d: bipush 0
      // 1e: invokevirtual v0/w.x (Z)V
      // 21: aload 0
      // 22: getfield v0/w.d Lv0/y;
      // 25: getfield v0/y.d Lv0/s;
      // 28: invokevirtual v0/s.flush ()V
      // 2b: goto 10
      // 2e: return
      // 2f: astore 2
      // 30: aload 1
      // 31: monitorexit
      // 32: aload 2
      // 33: athrow
   }

   @Override
   public final void i(a1.e var1, long var2) {
      a1.e var4 = this.a;
      var4.i(var1, var2);

      while (var4.b >= 16384L) {
         this.x(false);
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final void x(boolean var1) {
      y var4 = this.d;
      synchronized (var4){} // $VF: monitorenter 

      long var2;
      y var67;
      label417: {
         Throwable var10000;
         label412: {
            try {
               this.d.j.i();
            } catch (Throwable var61) {
               var10000 = var61;
               boolean var10001 = false;
               break label412;
            }

            while (true) {
               boolean var48 = false /* VF: Semaphore variable */;

               try {
                  var48 = true;
                  var67 = this.d;
                  if (var67.b <= 0L) {
                     if (!this.c) {
                        if (!this.b) {
                           if (var67.k == null) {
                              var67.i();
                              var48 = false;
                              continue;
                           }

                           var48 = false;
                        } else {
                           var48 = false;
                        }
                     } else {
                        var48 = false;
                     }
                  } else {
                     var48 = false;
                  }
               } finally {
                  if (var48) {
                     try {
                        this.d.j.o();
                     } catch (Throwable var59) {
                        var10000 = var59;
                        boolean var75 = false;
                        break;
                     }
                  }
               }

               try {
                  var67.j.o();
                  this.d.b();
                  var2 = Math.min(this.d.b, this.a.b);
                  var67 = this.d;
                  var67.b -= var2;
                  // $VF: monitorexit
                  break label417;
               } catch (Throwable var60) {
                  var10000 = var60;
                  boolean var76 = false;
                  break;
               }
            }
         }

         while (true) {
            Throwable var66 = var10000;

            try {
               // $VF: monitorexit
               throw var66;
            } catch (Throwable var56) {
               var10000 = var56;
               boolean var77 = false;
               continue;
            }
         }
      }

      label392: {
         Throwable var74;
         label391: {
            label390: {
               label389: {
                  var67.j.i();
                  if (var1) {
                     try {
                        if (var2 == this.a.b) {
                           break label389;
                        }
                     } catch (Throwable var58) {
                        var74 = var58;
                        boolean var78 = false;
                        break label391;
                     }
                  }

                  var1 = false;
                  break label390;
               }

               var1 = true;
            }

            label382:
            try {
               var4 = this.d;
               var4.d.E(var4.c, var1, this.a, var2);
               break label392;
            } catch (Throwable var57) {
               var74 = var57;
               boolean var79 = false;
               break label382;
            }
         }

         Throwable var64 = var74;
         this.d.j.o();
         throw var64;
      }

      this.d.j.o();
   }
}
