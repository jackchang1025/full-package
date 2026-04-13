package k0;

import f0.m;
import f0.o;
import f0.q;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.zip.Inflater;

public class g extends q {
   public final Inflater i;
   public final m j = new m();

   public g(Inflater var1) {
      this.i = var1;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   @Override
   public void b(o var1, m var2) {
      Inflater var5 = this.i;

      Exception var10000;
      label91: {
         try {
            var18 = m.g(var2.c * 2);
         } catch (Exception var13) {
            var10000 = var13;
            boolean var10001 = false;
            break label91;
         }

         label90:
         while (true) {
            int var3;
            try {
               var3 = var2.a.size();
            } catch (Exception var9) {
               var10000 = var9;
               boolean var23 = false;
               break;
            }

            m var7 = this.j;
            if (var3 <= 0) {
               try {
                  ((Buffer)var18).flip();
                  var7.a(var18);
                  a1.q.p(this, var7);
                  return;
               } catch (Exception var8) {
                  var10000 = var8;
                  boolean var31 = false;
                  break;
               }
            }

            ByteBuffer var6;
            try {
               var6 = var2.l();
            } catch (Exception var12) {
               var10000 = var12;
               boolean var24 = false;
               break;
            }

            ByteBuffer var4 = var18;

            label98: {
               try {
                  if (!var6.hasRemaining()) {
                     break label98;
                  }

                  var6.remaining();
                  var5.setInput(var6.array(), var6.arrayOffset() + var6.position(), var6.remaining());
               } catch (Exception var17) {
                  var10000 = var17;
                  boolean var25 = false;
                  break;
               }

               var4 = var18;

               while (true) {
                  try {
                     var3 = var5.inflate(var4.array(), var4.arrayOffset() + var4.position(), var4.remaining());
                     ((Buffer)var4).position(var4.position() + var3);
                  } catch (Exception var11) {
                     var10000 = var11;
                     boolean var26 = false;
                     break label90;
                  }

                  var19 = var4;

                  try {
                     if (!var4.hasRemaining()) {
                        ((Buffer)var4).flip();
                        var7.a(var4);
                        var19 = m.g(var4.capacity() * 2);
                     }
                  } catch (Exception var15) {
                     var10000 = var15;
                     boolean var27 = false;
                     break label90;
                  }

                  var4 = var19;

                  try {
                     if (var5.needsInput()) {
                        break label98;
                     }
                  } catch (Exception var14) {
                     var10000 = var14;
                     boolean var28 = false;
                     break label90;
                  }

                  var4 = var19;

                  try {
                     if (var5.finished()) {
                        break;
                     }
                  } catch (Exception var16) {
                     var10000 = var16;
                     boolean var29 = false;
                     break label90;
                  }
               }

               var4 = var19;
            }

            try {
               m.j(var6);
            } catch (Exception var10) {
               var10000 = var10;
               boolean var30 = false;
               break;
            }

            var18 = var4;
         }
      }

      Exception var20 = var10000;
      this.c(var20);
   }

   @Override
   public final void c(Exception var1) {
      Inflater var3 = this.i;
      var3.end();
      Object var2 = var1;
      if (var1 != null) {
         var2 = var1;
         if (var3.getRemaining() > 0) {
            var2 = new i0.b(var1);
         }
      }

      super.c((Exception)var2);
   }
}
