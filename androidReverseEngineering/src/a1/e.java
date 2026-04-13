package a1;

import java.io.EOFException;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.charset.Charset;

public final class e implements g, f, Cloneable, ByteChannel {
   public static final byte[] c = new byte[]{48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102};
   public p a;
   public long b;

   public final long A(byte var1, long var2, long var4) {
      long var12 = 0L;
      if (var2 >= 0L && var4 >= var2) {
         long var8 = this.b;
         long var10;
         if (var4 > var8) {
            var10 = var8;
         } else {
            var10 = var4;
         }

         if (var2 == var10) {
            return -1L;
         } else {
            p var15 = this.a;
            if (var15 == null) {
               return -1L;
            } else {
               var4 = var12;
               p var14 = var15;
               if (var8 - var2 < var2) {
                  while (true) {
                     var4 = var8;
                     var14 = var15;
                     if (var8 <= var2) {
                        break;
                     }

                     var15 = var15.g;
                     var8 -= (long)(var15.c - var15.b);
                  }
               } else {
                  while (true) {
                     var8 = (long)(var14.c - var14.b) + var4;
                     if (var8 >= var2) {
                        break;
                     }

                     var14 = var14.f;
                     var4 = var8;
                  }
               }

               var2 = var4;

               for (long var18 = var2; var2 < var10; var18 = var2) {
                  byte[] var21 = var14.a;
                  int var7 = (int)Math.min((long)var14.c, (long)var14.b + var10 - var2);

                  for (int var6 = (int)((long)var14.b + var18 - var2); var6 < var7; var6++) {
                     if (var21[var6] == var1) {
                        return (long)(var6 - var14.b) + var2;
                     }
                  }

                  var2 += (long)(var14.c - var14.b);
                  var14 = var14.f;
               }

               return -1L;
            }
         }
      } else {
         throw new IllegalArgumentException(String.format("size=%s fromIndex=%s toIndex=%s", this.b, var2, var4));
      }
   }

   public final byte[] B(long var1) {
      w.a(this.b, 0L, var1);
      if (var1 <= 2147483647L) {
         int var4 = (int)var1;
         byte[] var7 = new byte[var4];
         int var3 = 0;

         while (var3 < var4) {
            int var5 = this.read(var7, var3, var4 - var3);
            if (var5 == -1) {
               throw new EOFException();
            }

            var3 += var5;
         }

         return var7;
      } else {
         StringBuilder var6 = new StringBuilder("byteCount > Integer.MAX_VALUE: ");
         var6.append(var1);
         throw new IllegalArgumentException(var6.toString());
      }
   }

   public final String C(long var1, Charset var3) {
      w.a(this.b, 0L, var1);
      if (var3 != null) {
         if (var1 <= 2147483647L) {
            if (var1 == 0L) {
               return "";
            } else {
               p var5 = this.a;
               int var4 = var5.b;
               if ((long)var4 + var1 > (long)var5.c) {
                  return new String(this.B(var1), var3);
               } else {
                  String var7 = new String(var5.a, var4, (int)var1, var3);
                  var4 = (int)((long)var5.b + var1);
                  var5.b = var4;
                  this.b -= var1;
                  if (var4 == var5.c) {
                     this.a = var5.a();
                     q.L(var5);
                  }

                  return var7;
               }
            }
         } else {
            StringBuilder var6 = new StringBuilder("byteCount > Integer.MAX_VALUE: ");
            var6.append(var1);
            throw new IllegalArgumentException(var6.toString());
         }
      } else {
         throw new IllegalArgumentException("charset == null");
      }
   }

   public final String D() {
      try {
         return this.C(this.b, w.a);
      } catch (EOFException var2) {
         throw new AssertionError(var2);
      }
   }

   public final String E(long var1) {
      if (var1 > 0L) {
         long var3 = var1 - 1L;
         if (this.z(var3) == 13) {
            String var6 = this.C(var3, w.a);
            this.skip(2L);
            return var6;
         }
      }

      String var5 = this.C(var1, w.a);
      this.skip(1L);
      return var5;
   }

   public final int F(m var1, boolean var2) {
      p var12 = this.a;
      if (var12 == null) {
         return var2 ? -2 : var1.indexOf(h.e);
      } else {
         int var4 = var12.b;
         int var3 = var12.c;
         int[] var15 = var1.b;
         byte[] var11 = var12.a;
         p var16 = var12;
         int var6 = -1;
         int var5 = 0;

         while (true) {
            int var8 = var5 + 1;
            int var10 = var15[var5];
            int var7 = var8 + 1;
            var5 = var15[var8];
            if (var5 != -1) {
               var6 = var5;
            }

            label75:
            if (var16 != null) {
               if (var10 < 0) {
                  var5 = var7;

                  while (true) {
                     int var9 = var4 + 1;
                     byte var17 = var11[var4];
                     var8 = var5 + 1;
                     if ((var17 & 255) != var15[var5]) {
                        return var6;
                     }

                     boolean var21;
                     if (var8 == var10 * -1 + var7) {
                        var21 = true;
                     } else {
                        var21 = false;
                     }

                     if (var9 == var3) {
                        var16 = var16.f;
                        var4 = var16.b;
                        var3 = var16.c;
                        var11 = var16.a;
                        if (var16 == var12) {
                           if (!var21) {
                              break label75;
                           }

                           var16 = null;
                        }
                     } else {
                        var4 = var9;
                     }

                     if (var21) {
                        var5 = var15[var8];
                        break;
                     }

                     var5 = var8;
                  }
               } else {
                  var5 = var4 + 1;
                  int var26 = var11[var4];
                  var4 = var7;

                  while (true) {
                     if (var4 == var7 + var10) {
                        return var6;
                     }

                     if ((var26 & 0xFF) == var15[var4]) {
                        var7 = var15[var4 + var10];
                        if (var5 == var3) {
                           p var14 = var16.f;
                           var26 = var14.b;
                           int var28 = var14.c;
                           byte[] var13 = var14.a;
                           var4 = var26;
                           var5 = var7;
                           var3 = var28;
                           var11 = var13;
                           var16 = var14;
                           if (var14 == var12) {
                              var16 = null;
                              var4 = var26;
                              var5 = var7;
                              var3 = var28;
                              var11 = var13;
                           }
                        } else {
                           var4 = var5;
                           var5 = var7;
                        }
                        break;
                     }

                     var4++;
                  }
               }

               if (var5 >= 0) {
                  return var5;
               }

               var5 = -var5;
               continue;
            }

            if (var2) {
               return -2;
            }

            return var6;
         }
      }
   }

   public final p G(int var1) {
      if (var1 >= 1 && var1 <= 8192) {
         p var2 = this.a;
         if (var2 == null) {
            var2 = q.P();
            this.a = var2;
            var2.g = var2;
            var2.f = var2;
            return var2;
         } else {
            p var3 = var2.g;
            if (var3.c + var1 <= 8192 && var3.e) {
               return var3;
            } else {
               var2 = q.P();
               var3.b(var2);
               return var2;
            }
         }
      } else {
         throw new IllegalArgumentException();
      }
   }

   public final void H(h var1) {
      if (var1 != null) {
         var1.n(this);
      } else {
         throw new IllegalArgumentException("byteString == null");
      }
   }

   public final void I(byte[] var1, int var2, int var3) {
      if (var1 == null) {
         throw new IllegalArgumentException("source == null");
      } else {
         long var7 = (long)var1.length;
         long var5 = (long)var2;
         long var9 = (long)var3;
         w.a(var7, var5, var9);
         var3 += var2;

         while (var2 < var3) {
            p var11 = this.G(1);
            int var4 = Math.min(var3 - var2, 8192 - var11.c);
            System.arraycopy(var1, var2, var11.a, var11.c, var4);
            var2 += var4;
            var11.c += var4;
         }

         this.b += var9;
      }
   }

   public final void J(int var1) {
      p var4 = this.G(1);
      int var3 = var4.c++;
      byte var2 = (byte)var1;
      var4.a[var3] = var2;
      this.b++;
   }

   public final e K(long var1) {
      long var13;
      int var5 = (var13 = var1 - 0L) == 0L ? 0 : (var13 < 0L ? -1 : 1);
      if (var5 == 0) {
         this.J(48);
         return this;
      } else {
         int var3 = 1;
         boolean var4 = false;
         long var7 = var1;
         if (var5 < 0) {
            var7 = -var1;
            if (var7 < 0L) {
               this.O("-9223372036854775808", 0, 20);
               return this;
            }

            var4 = true;
         }

         if (var7 < 100000000L) {
            if (var7 < 10000L) {
               if (var7 < 100L) {
                  if (var7 >= 10L) {
                     var3 = 2;
                  }
               } else if (var7 < 1000L) {
                  var3 = 3;
               } else {
                  var3 = 4;
               }
            } else if (var7 < 1000000L) {
               if (var7 < 100000L) {
                  var3 = 5;
               } else {
                  var3 = 6;
               }
            } else if (var7 < 10000000L) {
               var3 = 7;
            } else {
               var3 = 8;
            }
         } else if (var7 < 1000000000000L) {
            if (var7 < 10000000000L) {
               if (var7 < 1000000000L) {
                  var3 = 9;
               } else {
                  var3 = 10;
               }
            } else if (var7 < 100000000000L) {
               var3 = 11;
            } else {
               var3 = 12;
            }
         } else if (var7 < 1000000000000000L) {
            if (var7 < 10000000000000L) {
               var3 = 13;
            } else if (var7 < 100000000000000L) {
               var3 = 14;
            } else {
               var3 = 15;
            }
         } else if (var7 < 100000000000000000L) {
            if (var7 < 10000000000000000L) {
               var3 = 16;
            } else {
               var3 = 17;
            }
         } else if (var7 < 1000000000000000000L) {
            var3 = 18;
         } else {
            var3 = 19;
         }

         var5 = var3;
         if (var4) {
            var5 = var3 + 1;
         }

         p var10 = this.G(var5);
         var3 = var10.c + var5;

         while (true) {
            byte[] var9 = var10.a;
            if (var7 == 0L) {
               if (var4) {
                  var9[var3 - 1] = 45;
               }

               var10.c += var5;
               this.b += (long)var5;
               return this;
            }

            int var6 = (int)(var7 % 10L);
            var3--;
            var9[var3] = c[var6];
            var7 /= 10L;
         }
      }
   }

   public final e L(long var1) {
      if (var1 == 0L) {
         this.J(48);
         return this;
      } else {
         int var5 = Long.numberOfTrailingZeros(Long.highestOneBit(var1)) / 4 + 1;
         p var7 = this.G(var5);
         int var6 = var7.c;

         for (int var4 = var6 + var5; --var4 >= var6; var1 >>>= 4) {
            byte var3 = c[(int)(15L & var1)];
            var7.a[var4] = var3;
         }

         var7.c += var5;
         this.b += (long)var5;
         return this;
      }
   }

   public final void M(int var1) {
      p var6 = this.G(4);
      int var4 = var6.c;
      int var3 = var4 + 1;
      byte var2 = (byte)(var1 >>> 24 & 0xFF);
      byte[] var5 = var6.a;
      var5[var4] = var2;
      var4 = var3 + 1;
      var5[var3] = (byte)(var1 >>> 16 & 0xFF);
      var3 = var4 + 1;
      var5[var4] = (byte)(var1 >>> 8 & 0xFF);
      var5[var3] = (byte)(var1 & 0xFF);
      var6.c = var3 + 1;
      this.b += 4L;
   }

   public final void N(int var1) {
      p var6 = this.G(2);
      int var4 = var6.c;
      int var3 = var4 + 1;
      byte var2 = (byte)(var1 >>> 8 & 0xFF);
      byte[] var5 = var6.a;
      var5[var4] = var2;
      var5[var3] = (byte)(var1 & 0xFF);
      var6.c = var3 + 1;
      this.b += 2L;
   }

   public final void O(String var1, int var2, int var3) {
      if (var1 == null) {
         throw new IllegalArgumentException("string == null");
      } else if (var2 < 0) {
         throw new IllegalArgumentException(a.a.g("beginIndex < 0: ", var2));
      } else if (var3 < var2) {
         StringBuilder var11 = new StringBuilder("endIndex < beginIndex: ");
         var11.append(var3);
         var11.append(" < ");
         var11.append(var2);
         throw new IllegalArgumentException(var11.toString());
      } else if (var3 > var1.length()) {
         StringBuilder var20 = a.a.q("endIndex > string.length: ", var3, " > ");
         var20.append(var1.length());
         throw new IllegalArgumentException(var20.toString());
      } else {
         while (var2 < var3) {
            char var7 = var1.charAt(var2);
            if (var7 < 128) {
               p var9 = this.G(1);
               int var17 = var9.c - var2;
               int var8 = Math.min(var3, 8192 - var17);
               int var14 = var2 + 1;
               byte var4 = (byte)var7;
               byte[] var10 = var9.a;
               var10[var2 + var17] = var4;
               var2 = var14;

               while (var2 < var8) {
                  var7 = var1.charAt(var2);
                  if (var7 >= 128) {
                     break;
                  }

                  var14 = var2 + 1;
                  var10[var2 + var17] = (byte)var7;
                  var2 = var14;
               }

               var14 = var9.c;
               var17 = var17 + var2 - var14;
               var9.c = var14 + var17;
               this.b += (long)var17;
            } else {
               int var5;
               if (var7 < 2048) {
                  var5 = var7 >> 6 | 192;
               } else {
                  if (var7 >= '\ud800' && var7 <= '\udfff') {
                     int var6 = var2 + 1;
                     char var12;
                     if (var6 < var3) {
                        var12 = var1.charAt(var6);
                     } else {
                        var12 = 0;
                     }

                     if (var7 <= '\udbff' && var12 >= 56320 && var12 <= 57343) {
                        var12 = ((var7 & -55297) << 10 | -56321 & var12) + 65536;
                        this.J(var12 >> 18 | 240);
                        this.J(var12 >> 12 & 63 | 128);
                        this.J(var12 >> 6 & 63 | 128);
                        this.J(var12 & 63 | 128);
                        var2 += 2;
                        continue;
                     }

                     this.J(63);
                     var2 = var6;
                     continue;
                  }

                  this.J(var7 >> '\f' | 224);
                  var5 = var7 >> 6 & 63 | 128;
               }

               this.J(var5);
               this.J(var7 & '?' | 128);
               var2++;
            }
         }
      }
   }

   public final void P(int var1) {
      if (var1 >= 128) {
         int var2;
         if (var1 < 2048) {
            var2 = var1 >> 6 | 192;
         } else {
            if (var1 < 65536) {
               if (var1 >= 55296 && var1 <= 57343) {
                  this.J(63);
                  return;
               }

               var2 = var1 >> 12 | 224;
            } else {
               if (var1 > 1114111) {
                  StringBuilder var3 = new StringBuilder("Unexpected code point: ");
                  var3.append(Integer.toHexString(var1));
                  throw new IllegalArgumentException(var3.toString());
               }

               this.J(var1 >> 18 | 240);
               var2 = var1 >> 12 & 63 | 128;
            }

            this.J(var2);
            var2 = var1 >> 6 & 63 | 128;
         }

         this.J(var2);
         var1 = var1 & 63 | 128;
      }

      this.J(var1);
   }

   @Override
   public final v a() {
      return v.d;
   }

   @Override
   public final int b(m var1) {
      int var2 = this.F(var1, false);
      if (var2 == -1) {
         return -1;
      } else {
         long var3 = (long)var1.a[var2].j();

         try {
            this.skip(var3);
            return var2;
         } catch (EOFException var5) {
            throw new AssertionError();
         }
      }
   }

   @Override
   public final Object clone() {
      e var2 = new e();
      if (this.b != 0L) {
         p var1 = this.a.c();
         var2.a = var1;
         var1.g = var1;
         var1.f = var1;
         var1 = this.a;

         while (true) {
            var1 = var1.f;
            if (var1 == this.a) {
               var2.b = this.b;
               break;
            }

            var2.a.g.b(var1.c());
         }
      }

      return var2;
   }

   @Override
   public final void close() {
   }

   @Override
   public final long d(t var1) {
      if (var1 == null) {
         throw new IllegalArgumentException("source == null");
      } else {
         long var2 = 0L;

         while (true) {
            long var4 = var1.u(this, 8192L);
            if (var4 == -1L) {
               return var2;
            }

            var2 += var4;
         }
      }
   }

   @Override
   public final boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof e)) {
         return false;
      } else {
         var1 = var1;
         long var8 = this.b;
         if (var8 != var1.b) {
            return false;
         } else {
            long var6 = 0L;
            if (var8 == 0L) {
               return true;
            } else {
               p var11 = this.a;
               p var14 = var1.a;
               int var3 = var11.b;
               int var2 = var14.b;

               while (var6 < this.b) {
                  var8 = (long)Math.min(var11.c - var3, var14.c - var2);

                  for (int var4 = 0; (long)var4 < var8; var2++) {
                     if (var11.a[var3] != var14.a[var2]) {
                        return false;
                     }

                     var4++;
                     var3++;
                  }

                  p var10 = var11;
                  int var15 = var3;
                  if (var3 == var11.c) {
                     var10 = var11.f;
                     var15 = var10.b;
                  }

                  int var5 = var2;
                  p var12 = var14;
                  if (var2 == var14.c) {
                     var12 = var14.f;
                     var5 = var12.b;
                  }

                  var6 += var8;
                  var11 = var10;
                  var3 = var15;
                  var2 = var5;
                  var14 = var12;
               }

               return true;
            }
         }
      }
   }

   @Override
   public final e f() {
      return this;
   }

   @Override
   public final void flush() {
   }

   @Override
   public final h h(long var1) {
      return new h(this.B(var1));
   }

   @Override
   public final int hashCode() {
      p var5 = this.a;
      if (var5 == null) {
         return 0;
      } else {
         int var3 = 1;

         int var2;
         p var6;
         do {
            int var1 = var5.b;
            int var4 = var5.c;

            for (var2 = var3; var1 < var4; var1++) {
               var2 = var2 * 31 + var5.a[var1];
            }

            var6 = var5.f;
            var5 = var6;
            var3 = var2;
         } while (var6 != this.a);

         return var2;
      }
   }

   @Override
   public final void i(e var1, long var2) {
      if (var1 == null) {
         throw new IllegalArgumentException("source == null");
      } else if (var1 == this) {
         throw new IllegalArgumentException("source == this");
      } else {
         w.a(var1.b, 0L, var2);

         while (var2 > 0L) {
            p var11 = var1.a;
            int var6 = var11.c - var11.b;
            long var8 = (long)var6;
            byte var5 = 0;
            if (var2 < var8) {
               p var10 = this.a;
               if (var10 != null) {
                  var10 = var10.g;
               } else {
                  var10 = null;
               }

               if (var10 != null && var10.e) {
                  var8 = (long)var10.c;
                  int var4;
                  if (var10.d) {
                     var4 = 0;
                  } else {
                     var4 = var10.b;
                  }

                  if (var8 + var2 - (long)var4 <= 8192L) {
                     var11.d(var10, (int)var2);
                     var1.b -= var2;
                     this.b += var2;
                     return;
                  }
               }

               int var13 = (int)var2;
               if (var13 <= 0 || var13 > var6) {
                  throw new IllegalArgumentException();
               }

               if (var13 >= 1024) {
                  var10 = var11.c();
               } else {
                  var10 = q.P();
                  var6 = var11.b;
                  byte[] var12 = var10.a;
                  System.arraycopy(var11.a, var6, var12, 0, var13);
               }

               var10.c = var10.b + var13;
               var11.b += var13;
               var11.g.b(var10);
               var1.a = var10;
            }

            p var21 = var1.a;
            var8 = (long)(var21.c - var21.b);
            var1.a = var21.a();
            var11 = this.a;
            if (var11 == null) {
               this.a = var21;
               var21.g = var21;
               var21.f = var21;
            } else {
               var11.g.b(var21);
               var11 = var21.g;
               if (var11 == var21) {
                  throw new IllegalStateException();
               }

               if (var11.e) {
                  int var7 = var21.c - var21.b;
                  var6 = var11.c;
                  int var14;
                  if (var11.d) {
                     var14 = var5;
                  } else {
                     var14 = var11.b;
                  }

                  if (var7 <= 8192 - var6 + var14) {
                     var21.d(var11, var7);
                     var21.a();
                     q.L(var21);
                  }
               }
            }

            var1.b -= var8;
            this.b += var8;
            var2 -= var8;
         }
      }
   }

   @Override
   public final boolean isOpen() {
      return true;
   }

   @Override
   public final String l() {
      return this.q(Long.MAX_VALUE);
   }

   @Override
   public final byte[] m() {
      try {
         return this.B(this.b);
      } catch (EOFException var2) {
         throw new AssertionError(var2);
      }
   }

   @Override
   public final boolean n() {
      boolean var1;
      if (this.b == 0L) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @Override
   public final f p(byte[] var1) {
      if (var1 != null) {
         this.I(var1, 0, var1.length);
         return this;
      } else {
         throw new IllegalArgumentException("source == null");
      }
   }

   @Override
   public final String q(long var1) {
      if (var1 >= 0L) {
         long var3 = Long.MAX_VALUE;
         if (var1 != Long.MAX_VALUE) {
            var3 = var1 + 1L;
         }

         long var5 = this.A((byte)10, 0L, var3);
         if (var5 != -1L) {
            return this.E(var5);
         } else if (var3 < this.b && this.z(var3 - 1L) == 13 && this.z(var3) == 10) {
            return this.E(var3);
         } else {
            e var8 = new e();
            this.y(var8, 0L, Math.min(32L, this.b));
            StringBuilder var9 = new StringBuilder("\\n not found: limit=");
            var9.append(Math.min(this.b, var1));
            var9.append(" content=");
            var9.append(new h(var8.m()).f());
            var9.append('…');
            throw new EOFException(var9.toString());
         }
      } else {
         StringBuilder var7 = new StringBuilder("limit < 0: ");
         var7.append(var1);
         throw new IllegalArgumentException(var7.toString());
      }
   }

   @Override
   public final void r(long var1) {
      if (this.b < var1) {
         throw new EOFException();
      }
   }

   @Override
   public final int read(ByteBuffer var1) {
      p var4 = this.a;
      if (var4 == null) {
         return -1;
      } else {
         int var3 = Math.min(var1.remaining(), var4.c - var4.b);
         var1.put(var4.a, var4.b, var3);
         int var2 = var4.b + var3;
         var4.b = var2;
         this.b -= (long)var3;
         if (var2 == var4.c) {
            this.a = var4.a();
            q.L(var4);
         }

         return var3;
      }
   }

   public final int read(byte[] var1, int var2, int var3) {
      w.a((long)var1.length, (long)var2, (long)var3);
      p var4 = this.a;
      if (var4 == null) {
         return -1;
      } else {
         var3 = Math.min(var3, var4.c - var4.b);
         System.arraycopy(var4.a, var4.b, var1, var2, var3);
         var2 = var4.b + var3;
         var4.b = var2;
         this.b -= (long)var3;
         if (var2 == var4.c) {
            this.a = var4.a();
            q.L(var4);
         }

         return var3;
      }
   }

   @Override
   public final byte readByte() {
      long var5 = this.b;
      if (var5 != 0L) {
         p var7 = this.a;
         int var3 = var7.b;
         int var4 = var7.c;
         int var2 = var3 + 1;
         byte var1 = var7.a[var3];
         this.b = var5 - 1L;
         if (var2 == var4) {
            this.a = var7.a();
            q.L(var7);
         } else {
            var7.b = var2;
         }

         return var1;
      } else {
         throw new IllegalStateException("size == 0");
      }
   }

   @Override
   public final int readInt() {
      long var7 = this.b;
      if (var7 >= 4L) {
         p var15 = this.a;
         int var2 = var15.b;
         int var1 = var15.c;
         if (var1 - var2 < 4) {
            return (this.readByte() & 0xFF) << 24 | (this.readByte() & 0xFF) << 16 | (this.readByte() & 0xFF) << 8 | this.readByte() & 0xFF;
         } else {
            int var3 = var2 + 1;
            byte[] var10 = var15.a;
            byte var11 = var10[var2];
            int var5 = var3 + 1;
            byte var12 = var10[var3];
            int var4 = var5 + 1;
            byte var14 = var10[var5];
            int var6 = var4 + 1;
            byte var13 = var10[var4];
            this.b = var7 - 4L;
            if (var6 == var1) {
               this.a = var15.a();
               q.L(var15);
            } else {
               var15.b = var6;
            }

            return (var11 & 0xFF) << 24 | (var12 & 0xFF) << 16 | (var14 & 0xFF) << 8 | var13 & 0xFF;
         }
      } else {
         StringBuilder var9 = new StringBuilder("size < 4: ");
         var9.append(this.b);
         throw new IllegalStateException(var9.toString());
      }
   }

   @Override
   public final short readShort() {
      long var5 = this.b;
      if (var5 >= 2L) {
         p var11 = this.a;
         int var2 = var11.b;
         int var1 = var11.c;
         if (var1 - var2 < 2) {
            return (short)((this.readByte() & 255) << 8 | this.readByte() & 255);
         } else {
            int var4 = var2 + 1;
            byte[] var8 = var11.a;
            byte var9 = var8[var2];
            int var3 = var4 + 1;
            byte var10 = var8[var4];
            this.b = var5 - 2L;
            if (var3 == var1) {
               this.a = var11.a();
               q.L(var11);
            } else {
               var11.b = var3;
            }

            return (short)((var9 & 255) << 8 | var10 & 255);
         }
      } else {
         StringBuilder var7 = new StringBuilder("size < 2: ");
         var7.append(this.b);
         throw new IllegalStateException(var7.toString());
      }
   }

   @Override
   public final f s(String var1) {
      this.O(var1, 0, var1.length());
      return this;
   }

   @Override
   public final void skip(long var1) {
      while (var1 > 0L) {
         p var8 = this.a;
         if (var8 == null) {
            throw new EOFException();
         }

         int var3 = (int)Math.min(var1, (long)(var8.c - var8.b));
         long var6 = this.b;
         long var4 = (long)var3;
         this.b = var6 - var4;
         var4 = var1 - var4;
         var8 = this.a;
         var3 = var8.b + var3;
         var8.b = var3;
         var1 = var4;
         if (var3 == var8.c) {
            this.a = var8.a();
            q.L(var8);
            var1 = var4;
         }
      }
   }

   @Override
   public final String toString() {
      long var2 = this.b;
      if (var2 <= 2147483647L) {
         int var1 = (int)var2;
         Object var5;
         if (var1 == 0) {
            var5 = h.e;
         } else {
            var5 = new r(this, var1);
         }

         return ((h)var5).toString();
      } else {
         StringBuilder var4 = new StringBuilder("size > Integer.MAX_VALUE: ");
         var4.append(this.b);
         throw new IllegalArgumentException(var4.toString());
      }
   }

   @Override
   public final long u(e var1, long var2) {
      if (var1 != null) {
         if (var2 >= 0L) {
            long var6 = this.b;
            if (var6 == 0L) {
               return -1L;
            } else {
               long var4 = var2;
               if (var2 > var6) {
                  var4 = var6;
               }

               var1.i(this, var4);
               return var4;
            }
         } else {
            StringBuilder var8 = new StringBuilder("byteCount < 0: ");
            var8.append(var2);
            throw new IllegalArgumentException(var8.toString());
         }
      } else {
         throw new IllegalArgumentException("sink == null");
      }
   }

   @Override
   public final long v() {
      if (this.b == 0L) {
         throw new IllegalStateException("size == 0");
      } else {
         int var4 = 0;
         boolean var1 = false;
         long var7 = 0L;

         int var2;
         long var9;
         do {
            p var12 = this.a;
            byte[] var11 = var12.a;
            int var3 = var12.b;
            int var6 = var12.c;
            var9 = var7;
            var2 = var4;

            byte var5;
            while (true) {
               var5 = var1;
               if (var3 >= var6) {
                  break;
               }

               var5 = var11[var3];
               if (var5 >= 48 && var5 <= 57) {
                  var4 = var5 - 48;
               } else {
                  if (var5 >= 97 && var5 <= 102) {
                     var4 = var5 - 97;
                  } else {
                     if (var5 < 65 || var5 > 70) {
                        if (var2 == 0) {
                           StringBuilder var16 = new StringBuilder("Expected leading [0-9a-fA-F] character but was 0x");
                           var16.append(Integer.toHexString(var5));
                           throw new NumberFormatException(var16.toString());
                        }

                        var5 = 1;
                        break;
                     }

                     var4 = var5 - 65;
                  }

                  var4 += 10;
               }

               if ((-1152921504606846976L & var9) != 0L) {
                  e var17 = new e();
                  var17.L(var9);
                  var17.J(var5);
                  throw new NumberFormatException("Number too large: ".concat(var17.D()));
               }

               var9 = var9 << 4 | (long)var4;
               var3++;
               var2++;
            }

            if (var3 == var6) {
               this.a = var12.a();
               q.L(var12);
            } else {
               var12.b = var3;
            }

            if (var5) {
               break;
            }

            var4 = var2;
            var1 = (boolean)var5;
            var7 = var9;
         } while (this.a != null);

         this.b -= (long)var2;
         return var9;
      }
   }

   @Override
   public final String w(Charset var1) {
      try {
         return this.C(this.b, var1);
      } catch (EOFException var2) {
         throw new AssertionError(var2);
      }
   }

   @Override
   public final int write(ByteBuffer var1) {
      if (var1 == null) {
         throw new IllegalArgumentException("source == null");
      } else {
         int var3 = var1.remaining();
         int var2 = var3;

         while (var2 > 0) {
            p var5 = this.G(1);
            int var4 = Math.min(var2, 8192 - var5.c);
            var1.get(var5.a, var5.c, var4);
            var2 -= var4;
            var5.c += var4;
         }

         this.b += (long)var3;
         return var3;
      }
   }

   public final void x() {
      try {
         this.skip(this.b);
      } catch (EOFException var2) {
         throw new AssertionError(var2);
      }
   }

   public final void y(e var1, long var2, long var4) {
      if (var1 == null) {
         throw new IllegalArgumentException("out == null");
      } else {
         w.a(this.b, var2, var4);
         if (var4 != 0L) {
            var1.b += var4;
            p var13 = this.a;

            while (true) {
               long var11 = (long)(var13.c - var13.b);
               p var14 = var13;
               long var7 = var2;
               long var9 = var4;
               if (var2 < var11) {
                  while (var9 > 0L) {
                     p var15 = var14.c();
                     int var6 = (int)((long)var15.b + var7);
                     var15.b = var6;
                     var15.c = Math.min(var6 + (int)var9, var15.c);
                     var13 = var1.a;
                     if (var13 == null) {
                        var15.g = var15;
                        var15.f = var15;
                        var1.a = var15;
                     } else {
                        var13.g.b(var15);
                     }

                     var9 -= (long)(var15.c - var15.b);
                     var14 = var14.f;
                     var7 = 0L;
                  }

                  return;
               }

               var2 -= var11;
               var13 = var13.f;
            }
         }
      }
   }

   public final byte z(long var1) {
      w.a(this.b, var1, 1L);
      long var5 = this.b;
      if (var5 - var1 > var1) {
         p var16 = this.a;

         while (true) {
            int var12 = var16.c;
            int var10 = var16.b;
            var5 = (long)(var12 - var10);
            if (var1 < var5) {
               var12 = (int)var1;
               return var16.a[var10 + var12];
            }

            var1 -= var5;
            var16 = var16.f;
         }
      } else {
         var1 -= var5;
         p var7 = this.a;

         int var3;
         p var8;
         do {
            var8 = var7.g;
            int var4 = var8.c;
            var3 = var8.b;
            var5 = var1 + (long)(var4 - var3);
            var7 = var8;
            var1 = var5;
         } while (var5 < 0L);

         int var11 = (int)var5;
         return var8.a[var3 + var11];
      }
   }
}
