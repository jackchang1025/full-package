package p0;

public final class j {
   public final boolean a;
   public String[] b;
   public String[] c;
   public boolean d;

   public j(k var1) {
      this.a = var1.a;
      this.b = var1.c;
      this.c = var1.d;
      this.d = var1.b;
   }

   public j(boolean var1) {
      this.a = var1;
   }

   public final void a(String... var1) {
      if (this.a) {
         if (var1.length != 0) {
            this.b = (String[])var1.clone();
         } else {
            throw new IllegalArgumentException("At least one cipher suite is required");
         }
      } else {
         throw new IllegalStateException("no cipher suites for cleartext connections");
      }
   }

   public final void b(i... var1) {
      if (!this.a) {
         throw new IllegalStateException("no cipher suites for cleartext connections");
      } else {
         String[] var3 = new String[var1.length];

         for (int var2 = 0; var2 < var1.length; var2++) {
            var3[var2] = var1[var2].a;
         }

         this.a(var3);
      }
   }

   public final void c(String... var1) {
      if (this.a) {
         if (var1.length != 0) {
            this.c = (String[])var1.clone();
         } else {
            throw new IllegalArgumentException("At least one TLS version is required");
         }
      } else {
         throw new IllegalStateException("no TLS versions for cleartext connections");
      }
   }

   public final void d(n0... var1) {
      if (!this.a) {
         throw new IllegalStateException("no TLS versions for cleartext connections");
      } else {
         String[] var3 = new String[var1.length];

         for (int var2 = 0; var2 < var1.length; var2++) {
            var3[var2] = var1[var2].a;
         }

         this.c(var3);
      }
   }
}
