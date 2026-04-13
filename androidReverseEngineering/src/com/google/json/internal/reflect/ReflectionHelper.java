package com.google.json.internal.reflect;

import a.a;
import com.google.json.JsonIOException;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionHelper {
   private static final ReflectionHelper.RecordHelper RECORD_HELPER;

   static {
      Object var0;
      try {
         var0 = new ReflectionHelper.RecordSupportedHelper();
      } catch (NoSuchMethodException var1) {
         var0 = new ReflectionHelper.RecordNotSupportedHelper();
      }

      RECORD_HELPER = (ReflectionHelper.RecordHelper)var0;
   }

   private ReflectionHelper() {
   }

   private static void appendExecutableParameters(AccessibleObject var0, StringBuilder var1) {
      var1.append('(');
      Class[] var3;
      if (var0 instanceof Method) {
         var3 = ((Method)var0).getParameterTypes();
      } else {
         var3 = ((Constructor)var0).getParameterTypes();
      }

      for (int var2 = 0; var2 < var3.length; var2++) {
         if (var2 > 0) {
            var1.append(", ");
         }

         var1.append(var3[var2].getSimpleName());
      }

      var1.append(')');
   }

   public static String constructorToString(Constructor<?> var0) {
      StringBuilder var1 = new StringBuilder(var0.getDeclaringClass().getName());
      appendExecutableParameters(var0, var1);
      return var1.toString();
   }

   private static RuntimeException createExceptionForRecordReflectionException(ReflectiveOperationException var0) {
      throw new RuntimeException(
         "Unexpected ReflectiveOperationException occurred (Gson 2.10.1). To support Java records, reflection is utilized to read out information about records. All these invocations happens after it is established that records exist in the JVM. This exception is unexpected behavior.",
         var0
      );
   }

   public static RuntimeException createExceptionForUnexpectedIllegalAccess(IllegalAccessException var0) {
      throw new RuntimeException(
         "Unexpected IllegalAccessException occurred (Gson 2.10.1). Certain ReflectionAccessFilter features require Java >= 9 to work correctly. If you are not using ReflectionAccessFilter, report this to the Gson maintainers.",
         var0
      );
   }

   public static String fieldToString(Field var0) {
      StringBuilder var1 = new StringBuilder();
      var1.append(var0.getDeclaringClass().getName());
      var1.append("#");
      var1.append(var0.getName());
      return var1.toString();
   }

   public static String getAccessibleObjectDescription(AccessibleObject var0, boolean var1) {
      label28: {
         label27: {
            String var2;
            if (var0 instanceof Field) {
               StringBuilder var3 = new StringBuilder("field '");
               var2 = fieldToString((Field)var0);
               var4 = var3;
            } else {
               if (var0 instanceof Method) {
                  Method var6 = (Method)var0;
                  StringBuilder var9 = new StringBuilder(var6.getName());
                  appendExecutableParameters(var6, var9);
                  String var14 = var9.toString();
                  StringBuilder var10 = new StringBuilder("method '");
                  var10.append(var6.getDeclaringClass().getName());
                  var10.append("#");
                  var10.append(var14);
                  var10.append("'");
                  var5 = var10.toString();
                  break label28;
               }

               if (!(var0 instanceof Constructor)) {
                  StringBuilder var8 = new StringBuilder("<unknown AccessibleObject> ");
                  var8.append(var0.toString());
                  var4 = var8;
                  break label27;
               }

               StringBuilder var7 = new StringBuilder("constructor '");
               String var13 = constructorToString((Constructor<?>)var0);
               var4 = var7;
               var2 = var13;
            }

            var4.append(var2);
            var4.append("'");
         }

         var5 = var4.toString();
      }

      String var11 = var5;
      if (var1) {
         var11 = var5;
         if (Character.isLowerCase(var5.charAt(0))) {
            StringBuilder var12 = new StringBuilder();
            var12.append(Character.toUpperCase(var5.charAt(0)));
            var12.append(var5.substring(1));
            var11 = var12.toString();
         }
      }

      return var11;
   }

   public static Method getAccessor(Class<?> var0, Field var1) {
      return RECORD_HELPER.getAccessor(var0, var1);
   }

   public static <T> Constructor<T> getCanonicalRecordConstructor(Class<T> var0) {
      return RECORD_HELPER.getCanonicalRecordConstructor(var0);
   }

   public static String[] getRecordComponentNames(Class<?> var0) {
      return RECORD_HELPER.getRecordComponentNames(var0);
   }

   public static boolean isRecord(Class<?> var0) {
      return RECORD_HELPER.isRecord(var0);
   }

   public static void makeAccessible(AccessibleObject var0) {
      try {
         var0.setAccessible(true);
      } catch (Exception var2) {
         throw new JsonIOException(
            a.l(
               "Failed making ",
               getAccessibleObjectDescription(var0, false),
               " accessible; either increase its visibility or write a custom TypeAdapter for its declaring type."
            ),
            var2
         );
      }
   }

   public static String tryMakeAccessible(Constructor<?> var0) {
      try {
         var0.setAccessible(true);
         return null;
      } catch (Exception var3) {
         StringBuilder var1 = new StringBuilder("Failed making constructor '");
         var1.append(constructorToString(var0));
         var1.append("' accessible; either increase its visibility or write a custom InstanceCreator or TypeAdapter for its declaring type: ");
         var1.append(var3.getMessage());
         return var1.toString();
      }
   }

   public abstract static class RecordHelper {
      private RecordHelper() {
      }

      public abstract Method getAccessor(Class<?> var1, Field var2);

      public abstract <T> Constructor<T> getCanonicalRecordConstructor(Class<T> var1);

      public abstract String[] getRecordComponentNames(Class<?> var1);

      public abstract boolean isRecord(Class<?> var1);
   }

   public static class RecordNotSupportedHelper extends ReflectionHelper.RecordHelper {
      private RecordNotSupportedHelper() {
      }

      @Override
      public Method getAccessor(Class<?> var1, Field var2) {
         throw new UnsupportedOperationException("Records are not supported on this JVM, this method should not be called");
      }

      @Override
      public <T> Constructor<T> getCanonicalRecordConstructor(Class<T> var1) {
         throw new UnsupportedOperationException("Records are not supported on this JVM, this method should not be called");
      }

      @Override
      public String[] getRecordComponentNames(Class<?> var1) {
         throw new UnsupportedOperationException("Records are not supported on this JVM, this method should not be called");
      }

      @Override
      public boolean isRecord(Class<?> var1) {
         return false;
      }
   }

   public static class RecordSupportedHelper extends ReflectionHelper.RecordHelper {
      private final Method getName;
      private final Method getRecordComponents;
      private final Method getType;
      private final Method isRecord = Class.class.getMethod("isRecord");

      private RecordSupportedHelper() {
         Method var1 = Class.class.getMethod("getRecordComponents");
         this.getRecordComponents = var1;
         Class var2 = var1.getReturnType().getComponentType();
         this.getName = var2.getMethod("getName");
         this.getType = var2.getMethod("getType");
      }

      @Override
      public Method getAccessor(Class<?> var1, Field var2) {
         try {
            return var1.getMethod(var2.getName());
         } catch (ReflectiveOperationException var3) {
            throw ReflectionHelper.createExceptionForRecordReflectionException(var3);
         }
      }

      // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
      @Override
      public <T> Constructor<T> getCanonicalRecordConstructor(Class<T> var1) {
         ReflectiveOperationException var10000;
         label34: {
            Object[] var3;
            Class[] var4;
            try {
               var3 = (Object[])this.getRecordComponents.invoke(var1);
               var4 = new Class[var3.length];
            } catch (ReflectiveOperationException var7) {
               var10000 = var7;
               boolean var10001 = false;
               break label34;
            }

            int var2 = 0;

            while (true) {
               try {
                  if (var2 >= var3.length) {
                     break;
                  }

                  var4[var2] = (Class)this.getType.invoke(var3[var2]);
               } catch (ReflectiveOperationException var6) {
                  var10000 = var6;
                  boolean var9 = false;
                  break label34;
               }

               var2++;
            }

            try {
               return var1.getDeclaredConstructor(var4);
            } catch (ReflectiveOperationException var5) {
               var10000 = var5;
               boolean var10 = false;
            }
         }

         ReflectiveOperationException var8 = var10000;
         throw ReflectionHelper.createExceptionForRecordReflectionException(var8);
      }

      // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
      @Override
      public String[] getRecordComponentNames(Class<?> var1) {
         ReflectiveOperationException var10000;
         label29: {
            String[] var3;
            try {
               var6 = (Object[])this.getRecordComponents.invoke(var1);
               var3 = new String[var6.length];
            } catch (ReflectiveOperationException var5) {
               var10000 = var5;
               boolean var10001 = false;
               break label29;
            }

            int var2 = 0;

            while (true) {
               try {
                  if (var2 >= var6.length) {
                     return var3;
                  }

                  var3[var2] = (String)this.getName.invoke(var6[var2]);
               } catch (ReflectiveOperationException var4) {
                  var10000 = var4;
                  boolean var8 = false;
                  break;
               }

               var2++;
            }
         }

         ReflectiveOperationException var7 = var10000;
         throw ReflectionHelper.createExceptionForRecordReflectionException(var7);
      }

      @Override
      public boolean isRecord(Class<?> var1) {
         try {
            return (Boolean)this.isRecord.invoke(var1);
         } catch (ReflectiveOperationException var3) {
            throw ReflectionHelper.createExceptionForRecordReflectionException(var3);
         }
      }
   }
}
