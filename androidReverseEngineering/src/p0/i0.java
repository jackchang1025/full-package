package p0;

public final class i0 {
   public f0 a;
   public c0 b;
   public int c = -1;
   public String d;
   public r e;
   public f f;
   public l0 g;
   public j0 h;
   public j0 i;
   public j0 j;
   public long k;
   public long l;
   public s0.e m;

   public i0() {
      this.f = new f();
   }

   public i0(j0 var1) {
      this.a = var1.a;
      this.b = var1.b;
      this.c = var1.c;
      this.d = var1.d;
      this.e = var1.e;
      this.f = var1.f.e();
      this.g = var1.g;
      this.h = var1.h;
      this.i = var1.i;
      this.j = var1.j;
      this.k = var1.k;
      this.l = var1.l;
      this.m = var1.m;
   }

   public static void b(String var0, j0 var1) {
      if (var1.g == null) {
         if (var1.h == null) {
            if (var1.i == null) {
               if (var1.j != null) {
                  throw new IllegalArgumentException(var0.concat(".priorResponse != null"));
               }
            } else {
               throw new IllegalArgumentException(var0.concat(".cacheResponse != null"));
            }
         } else {
            throw new IllegalArgumentException(var0.concat(".networkResponse != null"));
         }
      } else {
         throw new IllegalArgumentException(var0.concat(".body != null"));
      }
   }

   public final j0 a() {
      if (this.a != null) {
         if (this.b != null) {
            if (this.c >= 0) {
               if (this.d != null) {
                  return new j0(this);
               } else {
                  throw new IllegalStateException("message == null");
               }
            } else {
               StringBuilder var1 = new StringBuilder("code < 0: ");
               var1.append(this.c);
               throw new IllegalStateException(var1.toString());
            }
         } else {
            throw new IllegalStateException("protocol == null");
         }
      } else {
         throw new IllegalStateException("request == null");
      }
   }
}
