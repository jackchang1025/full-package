package j1;

public final class a {
   @Override
   public final boolean equals(Object var1) {
      boolean var2;
      if (this == var1 || var1 != null && a.class == var1.getClass()) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   @Override
   public final int hashCode() {
      return a.class.hashCode();
   }

   @Override
   public final String toString() {
      return a.class.getSimpleName();
   }
}
