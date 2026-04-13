package com.google.json.internal;

public final class $Gson$Preconditions {
   private $Gson$Preconditions() {
      throw new UnsupportedOperationException();
   }

   public static void checkArgument(boolean var0) {
      if (!var0) {
         throw new IllegalArgumentException();
      }
   }

   @Deprecated
   public static <T> T checkNotNull(T var0) {
      var0.getClass();
      return (T)var0;
   }
}
