package k0;

import f0.m;
import f0.o;
import f0.q;

public final class a extends q {
   public int i = 0;
   public int j = 0;
   public int k = 1;
   public final m l = new m();

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   @Override
   public final void b(o var1, m var2) {
      m var24 = this.l;
      if (this.k == 8) {
         var2.k();
      } else {
         while (true) {
            Exception var10000;
            while (true) {
               int var4;
               try {
                  if (var2.c <= 0) {
                     return;
                  }

                  var4 = r.a.a(this.k);
               } catch (Exception var21) {
                  var10000 = var21;
                  boolean var10001 = false;
                  break;
               }

               if (var4 == 0) {
                  char var3;
                  try {
                     var3 = var2.f();
                  } catch (Exception var19) {
                     var10000 = var19;
                     boolean var44 = false;
                     break;
                  }

                  if (var3 == '\r') {
                     try {
                        this.k = 2;
                     } catch (Exception var17) {
                        var10000 = var17;
                        boolean var48 = false;
                        break;
                     }
                  } else {
                     int var32;
                     try {
                        var32 = this.i * 16;
                        this.i = var32;
                     } catch (Exception var18) {
                        var10000 = var18;
                        boolean var45 = false;
                        break;
                     }

                     label224: {
                        byte var30;
                        if (var3 >= 'a' && var3 <= 'f') {
                           var30 = -97;
                        } else {
                           if (var3 >= '0' && var3 <= '9') {
                              var4 = var3 - '0' + var32;
                              break label224;
                           }

                           if (var3 < 'A' || var3 > 'F') {
                              try {
                                 StringBuilder var25 = new StringBuilder();
                                 var25.append("invalid chunk length: ");
                                 var25.append(var3);
                                 i0.b var27 = new i0.b(var25.toString());
                                 this.c(var27);
                                 return;
                              } catch (Exception var11) {
                                 var10000 = var11;
                                 boolean var46 = false;
                                 break;
                              }
                           }

                           var30 = -65;
                        }

                        var4 = a.a.a(var3, var30, 10, var32);
                     }

                     try {
                        this.i = var4;
                     } catch (Exception var16) {
                        var10000 = var16;
                        boolean var47 = false;
                        break;
                     }
                  }

                  try {
                     this.j = this.i;
                  } catch (Exception var9) {
                     var10000 = var9;
                     boolean var49 = false;
                     break;
                  }
               } else if (var4 != 1) {
                  if (var4 != 3) {
                     if (var4 != 4) {
                        if (var4 != 5) {
                           if (var4 == 6) {
                              return;
                           }
                        } else {
                           try {
                              if (!this.l(var2.f(), '\n')) {
                                 return;
                              }
                           } catch (Exception var20) {
                              var10000 = var20;
                              boolean var40 = false;
                              break;
                           }

                           label134: {
                              try {
                                 if (this.i > 0) {
                                    this.k = 1;
                                    break label134;
                                 }
                              } catch (Exception var13) {
                                 var10000 = var13;
                                 boolean var41 = false;
                                 break;
                              }

                              try {
                                 this.k = 7;
                                 this.c(null);
                              } catch (Exception var12) {
                                 var10000 = var12;
                                 boolean var42 = false;
                                 break;
                              }
                           }

                           try {
                              this.i = 0;
                           } catch (Exception var6) {
                              var10000 = var6;
                              boolean var43 = false;
                              break;
                           }
                        }
                     } else {
                        try {
                           if (!this.l(var2.f(), '\r')) {
                              return;
                           }
                        } catch (Exception var22) {
                           var10000 = var22;
                           boolean var38 = false;
                           break;
                        }

                        try {
                           this.k = 6;
                        } catch (Exception var7) {
                           var10000 = var7;
                           boolean var39 = false;
                           break;
                        }
                     }
                  } else {
                     int var5;
                     try {
                        int var28 = var2.c;
                        var4 = Math.min(this.j, var28);
                        var5 = this.j - var4;
                        this.j = var5;
                     } catch (Exception var15) {
                        var10000 = var15;
                        boolean var35 = false;
                        break;
                     }

                     if (var5 == 0) {
                        try {
                           this.k = 5;
                        } catch (Exception var14) {
                           var10000 = var14;
                           boolean var36 = false;
                           break;
                        }
                     }

                     if (var4 != 0) {
                        try {
                           var2.d(var24, var4);
                           a1.q.p(this, var24);
                        } catch (Exception var8) {
                           var10000 = var8;
                           boolean var37 = false;
                           break;
                        }
                     }
                  }
               } else {
                  try {
                     if (!this.l(var2.f(), '\n')) {
                        return;
                     }
                  } catch (Exception var23) {
                     var10000 = var23;
                     boolean var33 = false;
                     break;
                  }

                  try {
                     this.k = 4;
                  } catch (Exception var10) {
                     var10000 = var10;
                     boolean var34 = false;
                     break;
                  }
               }
            }

            Exception var26 = var10000;
            this.c(var26);
            return;
         }
      }
   }

   @Override
   public final void c(Exception var1) {
      Object var2 = var1;
      if (var1 == null) {
         var2 = var1;
         if (this.k != 7) {
            var2 = new i0.b("chunked input ended before final chunk");
         }
      }

      super.c((Exception)var2);
   }

   public final boolean l(char var1, char var2) {
      if (var1 != var2) {
         this.k = 8;
         StringBuilder var3 = new StringBuilder();
         var3.append(var2);
         var3.append(" was expected, got ");
         var3.append(var1);
         this.c(new i0.b(var3.toString()));
         return false;
      } else {
         return true;
      }
   }
}
