package com.google.json.internal;

import com.google.json.ReflectionAccessFilter;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

public class ReflectionAccessFilterHelper {
   private ReflectionAccessFilterHelper() {
   }

   public static boolean canAccess(AccessibleObject var0, Object var1) {
      return ReflectionAccessFilterHelper.AccessChecker.INSTANCE.canAccess(var0, var1);
   }

   public static ReflectionAccessFilter.FilterResult getFilterResult(List<ReflectionAccessFilter> var0, Class<?> var1) {
      Iterator var2 = var0.iterator();

      while (var2.hasNext()) {
         ReflectionAccessFilter.FilterResult var3 = ((ReflectionAccessFilter)var2.next()).check(var1);
         if (var3 != ReflectionAccessFilter.FilterResult.INDECISIVE) {
            return var3;
         }
      }

      return ReflectionAccessFilter.FilterResult.ALLOW;
   }

   public static boolean isAndroidType(Class<?> var0) {
      return isAndroidType(var0.getName());
   }

   private static boolean isAndroidType(String var0) {
      boolean var1;
      if (!var0.startsWith("android.") && !var0.startsWith("androidx.") && !isJavaType(var0)) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public static boolean isAnyPlatformType(Class<?> var0) {
      String var2 = var0.getName();
      boolean var1;
      if (!isAndroidType(var2) && !var2.startsWith("kotlin.") && !var2.startsWith("kotlinx.") && !var2.startsWith("scala.")) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public static boolean isJavaType(Class<?> var0) {
      return isJavaType(var0.getName());
   }

   private static boolean isJavaType(String var0) {
      boolean var1;
      if (!var0.startsWith("java.") && !var0.startsWith("javax.")) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public abstract static class AccessChecker {
      public static final ReflectionAccessFilterHelper.AccessChecker INSTANCE;

      static {
         ReflectionAccessFilterHelper.AccessChecker var0;
         label20: {
            if (JavaVersion.isJava9OrLater()) {
               try {
                  Method var1 = AccessibleObject.class.getDeclaredMethod("canAccess", Object.class);
                  var0 = new ReflectionAccessFilterHelper.AccessChecker(var1) {
                     final Method val$canAccessMethod;

                     {
                        this.val$canAccessMethod = var1;
                     }

                     @Override
                     public boolean canAccess(AccessibleObject var1, Object var2) {
                        try {
                           return (Boolean)this.val$canAccessMethod.invoke(var1, var2);
                        } catch (Exception var4) {
                           throw new RuntimeException("Failed invoking canAccess", var4);
                        }
                     }
                  };
                  break label20;
               } catch (NoSuchMethodException var2) {
               }
            }

            var0 = null;
         }

         ReflectionAccessFilterHelper.AccessChecker var3 = var0;
         if (var0 == null) {
            var3 = new ReflectionAccessFilterHelper.AccessChecker() {
               @Override
               public boolean canAccess(AccessibleObject var1, Object var2) {
                  return true;
               }
            };
         }

         INSTANCE = var3;
      }

      private AccessChecker() {
      }

      public abstract boolean canAccess(AccessibleObject var1, Object var2);
   }
}
