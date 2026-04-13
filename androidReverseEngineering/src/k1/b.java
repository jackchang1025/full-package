package k1;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;

public final class b extends c {
   public int i;
   public String j = "";

   public b() {
      super(6, 0);
      this.d();
      this.i = 1000;
      this.d();
   }

   @Override
   public final ByteBuffer a() {
      return this.i == 1005 ? ByteBuffer.allocate(0) : super.c;
   }

   @Override
   public final void b() {
      super.b();
      if (this.i == 1007 && this.j.isEmpty()) {
         throw new i1.c(1007, "Received text is no valid utf8 string!");
      } else if (this.i == 1005 && this.j.length() > 0) {
         throw new i1.c(1002, "A close frame must have a closecode if it has a reason");
      } else {
         int var1 = this.i;
         if (var1 > 1015 && var1 < 3000) {
            throw new i1.c(1002, "Trying to send an illegal close code!");
         } else if (var1 == 1006 || var1 == 1015 || var1 == 1005 || var1 > 4999 || var1 < 1000 || var1 == 1004) {
            StringBuilder var2 = new StringBuilder("closecode must not be sent over the wire: ");
            var2.append(this.i);
            throw new i1.d(var2.toString());
         }
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public final void c(ByteBuffer var1) {
      this.i = 1005;
      this.j = "";
      ((Buffer)var1).mark();
      short var2;
      if (var1.remaining() == 0) {
         var2 = 1000;
      } else {
         if (var1.remaining() != 1) {
            if (var1.remaining() >= 2) {
               ByteBuffer var3 = ByteBuffer.allocate(4);
               ((Buffer)var3).position(2);
               var3.putShort(var1.getShort());
               ((Buffer)var3).position(0);
               this.i = var3.getInt();
            }

            ((Buffer)var1).reset();

            label137: {
               try {
                  var2 = var1.position();
               } catch (i1.c var21) {
                  boolean var10001 = false;
                  break label137;
               }

               label138: {
                  Throwable var10000;
                  label121: {
                     try {
                        try {
                           ((Buffer)var1).position(var1.position() + 2);
                           this.j = o1.a.b(var1);
                           break label138;
                        } catch (IllegalArgumentException var19) {
                        }
                     } catch (Throwable var20) {
                        var10000 = var20;
                        boolean var25 = false;
                        break label121;
                     }

                     label112:
                     try {
                        i1.c var24 = new i1.c(1007);
                        throw var24;
                     } catch (Throwable var17) {
                        var10000 = var17;
                        boolean var26 = false;
                        break label112;
                     }
                  }

                  Throwable var23 = var10000;

                  try {
                     ((Buffer)var1).position(var2);
                     throw var23;
                  } catch (i1.c var16) {
                     boolean var27 = false;
                     break label137;
                  }
               }

               try {
                  ((Buffer)var1).position(var2);
                  return;
               } catch (i1.c var18) {
                  boolean var28 = false;
               }
            }

            this.i = 1007;
            this.j = null;
            return;
         }

         var2 = 1002;
      }

      this.i = var2;
   }

   public final void d() {
      String var1 = this.j;
      CodingErrorAction var2 = o1.a.a;
      byte[] var5 = var1.getBytes(StandardCharsets.UTF_8);
      ByteBuffer var4 = ByteBuffer.allocate(4);
      var4.putInt(this.i);
      ((Buffer)var4).position(2);
      ByteBuffer var3 = ByteBuffer.allocate(var5.length + 2);
      var3.put(var4);
      var3.put(var5);
      ((Buffer)var3).rewind();
      super.c = var3;
   }

   @Override
   public final boolean equals(Object var1) {
      boolean var2 = true;
      if (this == var1) {
         return true;
      } else if (var1 == null || b.class != var1.getClass()) {
         return false;
      } else if (!super.equals(var1)) {
         return false;
      } else {
         b var3 = (b)var1;
         if (this.i != var3.i) {
            return false;
         } else {
            var1 = this.j;
            String var5 = var3.j;
            if (var1 != null) {
               var2 = var1.equals(var5);
            } else if (var5 != null) {
               var2 = false;
            }

            return var2;
         }
      }
   }

   @Override
   public final int hashCode() {
      int var2 = super.hashCode();
      int var3 = this.i;
      String var4 = this.j;
      int var1;
      if (var4 != null) {
         var1 = var4.hashCode();
      } else {
         var1 = 0;
      }

      return (var2 * 31 + var3) * 31 + var1;
   }

   @Override
   public final String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append(super.toString());
      var1.append("code: ");
      var1.append(this.i);
      return var1.toString();
   }
}
