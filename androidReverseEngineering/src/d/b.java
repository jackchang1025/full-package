package d;

public abstract class b {
   public static Class a(Class var0) {
      return Class.forName(String.format("%s.%sParcelizer", var0.getPackage().getName(), var0.getSimpleName()), false, var0.getClassLoader());
   }

   public abstract boolean b(int var1);

   public final int c(int var1, int var2) {
      return !this.b(var2) ? var1 : ((c)this).b.readInt();
   }

   public abstract void d(int var1);
}
