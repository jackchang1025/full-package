package s0;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.lang.ref.Reference;
import java.net.Socket;
import java.util.concurrent.TimeUnit;
import p0.b0;
import p0.e0;
import p0.f0;
import p0.q;

public final class l {
   public final b0 a;
   public final h b;
   public final e0 c;
   public final q d;
   public final j e;
   public Object f;
   public f0 g;
   public f h;
   public g i;
   public e j;
   public boolean k;
   public boolean l;
   public boolean m;
   public boolean n;
   public boolean o;

   public l(b0 var1, e0 var2) {
      j var3 = new j(this, 0);
      this.e = var3;
      this.a = var1;
      q var4 = q.c;
      com.guard.wallet.http.h var5 = var1.p;
      var4.getClass();
      this.b = (h)var5.e;
      this.c = var2;
      this.d = (q)var1.f.d;
      var3.g((long)var1.u, TimeUnit.MILLISECONDS);
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final void a() {
      h var2 = this.b;
      synchronized (var2){} // $VF: monitorenter 

      e var3;
      g var34;
      label218: {
         Throwable var10000;
         label219: {
            try {
               this.m = true;
               var3 = this.j;
               var1 = this.h;
            } catch (Throwable var33) {
               var10000 = var33;
               boolean var10001 = false;
               break label219;
            }

            label209: {
               if (var1 != null) {
                  try {
                     var34 = var1.g;
                  } catch (Throwable var32) {
                     var10000 = var32;
                     boolean var36 = false;
                     break label219;
                  }

                  if (var34 != null) {
                     break label209;
                  }
               }

               try {
                  var34 = this.i;
               } catch (Throwable var31) {
                  var10000 = var31;
                  boolean var37 = false;
                  break label219;
               }
            }

            label201:
            try {
               // $VF: monitorexit
               break label218;
            } catch (Throwable var30) {
               var10000 = var30;
               boolean var38 = false;
               break label201;
            }
         }

         while (true) {
            Throwable var35 = var10000;

            try {
               // $VF: monitorexit
               throw var35;
            } catch (Throwable var29) {
               var10000 = var29;
               boolean var39 = false;
               continue;
            }
         }
      }

      if (var3 != null) {
         var3.d.cancel();
      } else if (var34 != null) {
         q0.c.d(var34.d);
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final void b() {
      h var1 = this.b;
      synchronized (var1){} // $VF: monitorenter 

      Throwable var10000;
      label92: {
         try {
            if (!this.o) {
               this.j = null;
               // $VF: monitorexit
               return;
            }
         } catch (Throwable var14) {
            var10000 = var14;
            boolean var10001 = false;
            break label92;
         }

         label86:
         try {
            IllegalStateException var15 = new IllegalStateException();
            throw var15;
         } catch (Throwable var13) {
            var10000 = var13;
            boolean var16 = false;
            break label86;
         }
      }

      while (true) {
         Throwable var2 = var10000;

         try {
            // $VF: monitorexit
            throw var2;
         } catch (Throwable var12) {
            var10000 = var12;
            boolean var17 = false;
            continue;
         }
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final IOException c(e var1, boolean var2, boolean var3, IOException var4) {
      h var8 = this.b;
      synchronized (var8){} // $VF: monitorenter 

      Throwable var10000;
      label561: {
         e var9;
         try {
            var9 = this.j;
         } catch (Throwable var99) {
            var10000 = var99;
            boolean var10001 = false;
            break label561;
         }

         if (var1 != var9) {
            label527:
            try {
               // $VF: monitorexit
               return var4;
            } catch (Throwable var92) {
               var10000 = var92;
               boolean var104 = false;
               break label527;
            }
         } else {
            label565: {
               boolean var7 = true;
               boolean var5;
               if (var2) {
                  try {
                     var5 = this.k ^ true;
                     this.k = true;
                  } catch (Throwable var98) {
                     var10000 = var98;
                     boolean var105 = false;
                     break label565;
                  }
               } else {
                  var5 = false;
               }

               boolean var6 = var5;
               if (var3) {
                  label550: {
                     try {
                        if (this.l) {
                           break label550;
                        }
                     } catch (Throwable var97) {
                        var10000 = var97;
                        boolean var106 = false;
                        break label565;
                     }

                     var5 = true;
                  }

                  try {
                     this.l = true;
                  } catch (Throwable var96) {
                     var10000 = var96;
                     boolean var107 = false;
                     break label565;
                  }

                  var6 = var5;
               }

               label542: {
                  label541: {
                     try {
                        if (!this.k || !this.l) {
                           break label541;
                        }
                     } catch (Throwable var95) {
                        var10000 = var95;
                        boolean var108 = false;
                        break label565;
                     }

                     if (var6) {
                        try {
                           g var100 = var9.a();
                           var100.m++;
                           this.j = null;
                        } catch (Throwable var94) {
                           var10000 = var94;
                           boolean var109 = false;
                           break label565;
                        }

                        var5 = var7;
                        break label542;
                     }
                  }

                  var5 = false;
               }

               try {
                  // $VF: monitorexit
               } catch (Throwable var93) {
                  var10000 = var93;
                  boolean var110 = false;
                  break label565;
               }

               IOException var102 = var4;
               if (var5) {
                  var102 = this.d(var4, false);
               }

               return var102;
            }
         }
      }

      while (true) {
         Throwable var101 = var10000;

         try {
            // $VF: monitorexit
            throw var101;
         } catch (Throwable var91) {
            var10000 = var91;
            boolean var111 = false;
            continue;
         }
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final IOException d(IOException var1, boolean var2) {
      h var6;
      Throwable var10000;
      label821: {
         label824: {
            var6 = this.b;
            synchronized (var6){} // $VF: monitorenter 
            if (var2) {
               try {
                  if (this.j != null) {
                     break label824;
                  }
               } catch (Throwable var116) {
                  var10000 = var116;
                  boolean var10001 = false;
                  break label821;
               }
            }

            g var5;
            try {
               var5 = this.i;
            } catch (Throwable var115) {
               var10000 = var115;
               boolean var121 = false;
               break label821;
            }

            Socket var4;
            label827: {
               label807:
               if (var5 != null) {
                  try {
                     if (this.j != null) {
                        break label807;
                     }
                  } catch (Throwable var114) {
                     var10000 = var114;
                     boolean var122 = false;
                     break label821;
                  }

                  if (!var2) {
                     try {
                        if (!this.o) {
                           break label807;
                        }
                     } catch (Throwable var113) {
                        var10000 = var113;
                        boolean var123 = false;
                        break label821;
                     }
                  }

                  try {
                     var4 = this.f();
                     break label827;
                  } catch (Throwable var112) {
                     var10000 = var112;
                     boolean var124 = false;
                     break label821;
                  }
               }

               var4 = null;
            }

            label793: {
               try {
                  if (this.i == null) {
                     break label793;
                  }
               } catch (Throwable var111) {
                  var10000 = var111;
                  boolean var125 = false;
                  break label821;
               }

               var5 = null;
            }

            boolean var3;
            label787: {
               label786: {
                  try {
                     if (this.o && this.j == null) {
                        break label786;
                     }
                  } catch (Throwable var110) {
                     var10000 = var110;
                     boolean var126 = false;
                     break label821;
                  }

                  var3 = false;
                  break label787;
               }

               var3 = true;
            }

            try {
               // $VF: monitorexit
            } catch (Throwable var109) {
               var10000 = var109;
               boolean var127 = false;
               break label821;
            }

            q0.c.d(var4);
            if (var5 != null) {
               this.d.getClass();
            }

            Object var119 = var1;
            if (var3) {
               if (!this.n && this.e.l()) {
                  InterruptedIOException var120 = new InterruptedIOException("timeout");
                  if (var1 != null) {
                     var120.initCause((Throwable)var1);
                  }

                  var1 = var120;
               }

               this.d.getClass();
               var119 = var1;
            }

            return (IOException)var119;
         }

         label774:
         try {
            IllegalStateException var118 = new IllegalStateException("cannot release connection while it is in use");
            throw var118;
         } catch (Throwable var108) {
            var10000 = var108;
            boolean var128 = false;
            break label774;
         }
      }

      while (true) {
         Throwable var117 = var10000;

         try {
            // $VF: monitorexit
            throw var117;
         } catch (Throwable var107) {
            var10000 = var107;
            boolean var129 = false;
            continue;
         }
      }
   }

   public final IOException e(IOException param1) {
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
      // 01: getfield s0/l.b Ls0/h;
      // 04: astore 2
      // 05: aload 2
      // 06: monitorenter
      // 07: aload 0
      // 08: bipush 1
      // 09: putfield s0/l.o Z
      // 0c: aload 2
      // 0d: monitorexit
      // 0e: aload 0
      // 0f: aload 1
      // 10: bipush 0
      // 11: invokevirtual s0/l.d (Ljava/io/IOException;Z)Ljava/io/IOException;
      // 14: areturn
      // 15: astore 1
      // 16: aload 2
      // 17: monitorexit
      // 18: aload 1
      // 19: athrow
   }

   public final Socket f() {
      int var3 = this.i.p.size();
      boolean var2 = false;
      int var1 = 0;

      while (true) {
         if (var1 >= var3) {
            var1 = -1;
            break;
         }

         if (((Reference)this.i.p.get(var1)).get() == this) {
            break;
         }

         var1++;
      }

      if (var1 == -1) {
         throw new IllegalStateException();
      } else {
         g var5 = this.i;
         var5.p.remove(var1);
         this.i = null;
         if (var5.p.isEmpty()) {
            var5.q = System.nanoTime();
            h var4 = this.b;
            var4.getClass();
            boolean var6;
            if (!var5.k && var4.a != 0) {
               var4.notifyAll();
               var6 = var2;
            } else {
               var4.d.remove(var5);
               var6 = true;
            }

            if (var6) {
               return var5.e;
            }
         }

         return null;
      }
   }
}
