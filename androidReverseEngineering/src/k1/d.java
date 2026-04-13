package k1;

import java.nio.ByteBuffer;

public abstract class d {
   public boolean a;
   public final int b;
   public ByteBuffer c;
   public boolean d;
   public boolean e;
   public boolean f;
   public boolean g;

   public d(int var1) {
      this.b = var1;
      this.c = ByteBuffer.allocate(0);
      this.a = true;
      this.d = false;
      this.e = false;
      this.f = false;
      this.g = false;
   }

   public ByteBuffer a() {
      return this.c;
   }

   public abstract void b();

   public void c(ByteBuffer var1) {
      this.c = var1;
   }

   @Override
   public boolean equals(Object var1) {
      boolean var2 = true;
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass() == var1.getClass()) {
         d var3 = (d)var1;
         if (this.a != var3.a) {
            return false;
         } else if (this.d != var3.d) {
            return false;
         } else if (this.e != var3.e) {
            return false;
         } else if (this.f != var3.f) {
            return false;
         } else if (this.g != var3.g) {
            return false;
         } else if (this.b != var3.b) {
            return false;
         } else {
            var1 = this.c;
            ByteBuffer var5 = var3.c;
            if (var1 != null) {
               var2 = var1.equals(var5);
            } else if (var5 != null) {
               var2 = false;
            }

            return var2;
         }
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      byte var3 = this.a;
      int var2 = r.a.a(this.b);
      ByteBuffer var4 = this.c;
      int var1;
      if (var4 != null) {
         var1 = var4.hashCode();
      } else {
         var1 = 0;
      }

      return (((((var2 + var3 * 31) * 31 + var1) * 31 + this.d) * 31 + this.e) * 31 + this.f) * 31 + this.g;
   }

   @Override
   public String toString() {
      StringBuilder var2 = new StringBuilder("Framedata{ opcode:");
      var2.append(a.a.E(this.b));
      var2.append(", fin:");
      var2.append(this.a);
      var2.append(", rsv1:");
      var2.append(this.e);
      var2.append(", rsv2:");
      var2.append(this.f);
      var2.append(", rsv3:");
      var2.append(this.g);
      var2.append(", payload length:[pos:");
      var2.append(this.c.position());
      var2.append(", len:");
      var2.append(this.c.remaining());
      var2.append("], payload:");
      String var1;
      if (this.c.remaining() > 1000) {
         var1 = "(too big to display)";
      } else {
         var1 = new String(this.c.array());
      }

      var2.append(var1);
      var2.append('}');
      return var2.toString();
   }
}
