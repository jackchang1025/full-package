package com.google.json;

import com.google.json.internal.ReflectionAccessFilterHelper;

public interface ReflectionAccessFilter {
   ReflectionAccessFilter BLOCK_ALL_ANDROID = new ReflectionAccessFilter() {
      @Override
      public ReflectionAccessFilter.FilterResult check(Class<?> var1) {
         ReflectionAccessFilter.FilterResult var2;
         if (ReflectionAccessFilterHelper.isAndroidType(var1)) {
            var2 = ReflectionAccessFilter.FilterResult.BLOCK_ALL;
         } else {
            var2 = ReflectionAccessFilter.FilterResult.INDECISIVE;
         }

         return var2;
      }
   };
   ReflectionAccessFilter BLOCK_ALL_JAVA = new ReflectionAccessFilter() {
      @Override
      public ReflectionAccessFilter.FilterResult check(Class<?> var1) {
         ReflectionAccessFilter.FilterResult var2;
         if (ReflectionAccessFilterHelper.isJavaType(var1)) {
            var2 = ReflectionAccessFilter.FilterResult.BLOCK_ALL;
         } else {
            var2 = ReflectionAccessFilter.FilterResult.INDECISIVE;
         }

         return var2;
      }
   };
   ReflectionAccessFilter BLOCK_ALL_PLATFORM = new ReflectionAccessFilter() {
      @Override
      public ReflectionAccessFilter.FilterResult check(Class<?> var1) {
         ReflectionAccessFilter.FilterResult var2;
         if (ReflectionAccessFilterHelper.isAnyPlatformType(var1)) {
            var2 = ReflectionAccessFilter.FilterResult.BLOCK_ALL;
         } else {
            var2 = ReflectionAccessFilter.FilterResult.INDECISIVE;
         }

         return var2;
      }
   };
   ReflectionAccessFilter BLOCK_INACCESSIBLE_JAVA = new ReflectionAccessFilter() {
      @Override
      public ReflectionAccessFilter.FilterResult check(Class<?> var1) {
         ReflectionAccessFilter.FilterResult var2;
         if (ReflectionAccessFilterHelper.isJavaType(var1)) {
            var2 = ReflectionAccessFilter.FilterResult.BLOCK_INACCESSIBLE;
         } else {
            var2 = ReflectionAccessFilter.FilterResult.INDECISIVE;
         }

         return var2;
      }
   };

   ReflectionAccessFilter.FilterResult check(Class<?> var1);

   public static enum FilterResult {
      ALLOW,
      BLOCK_ALL,
      BLOCK_INACCESSIBLE,
      INDECISIVE;
      private static final ReflectionAccessFilter.FilterResult[] $VALUES = $values();
   }
}
