package a1;

import java.io.EOFException;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

public final class k implements t {
   public final g a;
   public final Inflater b;
   public int c;
   public boolean d;

   public k(o var1, Inflater var2) {
      this.a = var1;
      this.b = var2;
   }

   @Override
   public final v a() {
      return this.a.a();
   }

   @Override
   public final void close() {
      if (!this.d) {
         this.b.end();
         this.d = true;
         this.a.close();
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   @Override
   public final long u(e var1, long var2) {
      long var41;
      int var4 = (var41 = var2 - 0L) == 0L ? 0 : (var41 < 0L ? -1 : 1);
      if (var4 < 0) {
         StringBuilder var22 = new StringBuilder("byteCount < 0: ");
         var22.append(var2);
         throw new IllegalArgumentException(var22.toString());
      } else if (this.d) {
         throw new IllegalStateException("closed");
      } else if (var4 == 0) {
         return 0L;
      } else {
         while (true) {
            Inflater var9;
            g var10;
            label73: {
               var9 = this.b;
               boolean var8 = var9.needsInput();
               var10 = this.a;
               if (var8) {
                  var4 = this.c;
                  if (var4 != 0) {
                     var4 -= var9.getRemaining();
                     this.c -= var4;
                     var10.skip((long)var4);
                  }

                  if (var9.getRemaining() != 0) {
                     throw new IllegalStateException("?");
                  }

                  if (var10.n()) {
                     var27 = true;
                     break label73;
                  }

                  p var11 = var10.f().a;
                  int var5 = var11.c;
                  var4 = var11.b;
                  var5 -= var4;
                  this.c = var5;
                  var9.setInput(var11.a, var4, var5);
               }

               var27 = false;
            }

            DataFormatException var10000;
            label108: {
               int var32;
               p var33;
               try {
                  var33 = var1.G(1);
                  var32 = (int)Math.min(var2, (long)(8192 - var33.c));
                  var32 = var9.inflate(var33.a, var33.c, var32);
               } catch (DataFormatException var19) {
                  var10000 = var19;
                  boolean var10001 = false;
                  break label108;
               }

               label105:
               if (var32 > 0) {
                  try {
                     var33.c += var32;
                     var2 = var1.b;
                  } catch (DataFormatException var13) {
                     var10000 = var13;
                     boolean var34 = false;
                     break label105;
                  }

                  long var6 = (long)var32;

                  try {
                     var1.b = var2 + var6;
                     return var6;
                  } catch (DataFormatException var12) {
                     var10000 = var12;
                     boolean var35 = false;
                  }
               } else {
                  label117: {
                     label119: {
                        try {
                           if (!var9.finished() && !var9.needsDictionary()) {
                              break label119;
                           }
                        } catch (DataFormatException var18) {
                           var10000 = var18;
                           boolean var36 = false;
                           break label117;
                        }

                        try {
                           var4 = this.c;
                        } catch (DataFormatException var17) {
                           var10000 = var17;
                           boolean var37 = false;
                           break label117;
                        }

                        if (var4 != 0) {
                           try {
                              var4 -= var9.getRemaining();
                              this.c -= var4;
                              var10.skip((long)var4);
                           } catch (DataFormatException var16) {
                              var10000 = var16;
                              boolean var38 = false;
                              break label117;
                           }
                        }

                        try {
                           if (var33.b == var33.c) {
                              var1.a = var33.a();
                              q.L(var33);
                           }

                           return -1L;
                        } catch (DataFormatException var15) {
                           var10000 = var15;
                           boolean var39 = false;
                           break label117;
                        }
                     }

                     if (!var27) {
                        continue;
                     }

                     try {
                        EOFException var21 = new EOFException("source exhausted prematurely");
                        throw var21;
                     } catch (DataFormatException var14) {
                        var10000 = var14;
                        boolean var40 = false;
                     }
                  }
               }
            }

            DataFormatException var20 = var10000;
            throw new IOException(var20);
         }
      }
   }
}
