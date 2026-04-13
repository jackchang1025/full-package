package com.google.json.reflect;

import com.google.json.internal.$Gson$Types;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TypeToken<T> {
   private final int hashCode;
   private final Class<? super T> rawType;
   private final Type type;

   public TypeToken() {
      Type var1 = this.getTypeTokenTypeArgument();
      this.type = var1;
      this.rawType = (Class<? super T>)$Gson$Types.getRawType(var1);
      this.hashCode = var1.hashCode();
   }

   private TypeToken(Type var1) {
      Objects.requireNonNull(var1);
      var1 = $Gson$Types.canonicalize(var1);
      this.type = var1;
      this.rawType = (Class<? super T>)$Gson$Types.getRawType(var1);
      this.hashCode = var1.hashCode();
   }

   private static AssertionError buildUnexpectedTypeError(Type var0, Class<?>... var1) {
      StringBuilder var4 = new StringBuilder("Unexpected type. Expected one of: ");
      int var3 = var1.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var4.append(var1[var2].getName());
         var4.append(", ");
      }

      var4.append("but got: ");
      var4.append(var0.getClass().getName());
      var4.append(", for type token: ");
      var4.append(var0.toString());
      var4.append('.');
      return new AssertionError(var4.toString());
   }

   public static <T> TypeToken<T> get(Class<T> var0) {
      return new TypeToken<>(var0);
   }

   public static TypeToken<?> get(Type var0) {
      return new TypeToken(var0);
   }

   public static TypeToken<?> getArray(Type var0) {
      return new TypeToken($Gson$Types.arrayOf(var0));
   }

   public static TypeToken<?> getParameterized(Type var0, Type... var1) {
      Objects.requireNonNull(var0);
      Objects.requireNonNull(var1);
      if (!(var0 instanceof Class)) {
         StringBuilder var13 = new StringBuilder("rawType must be of type Class, but was ");
         var13.append(var0);
         throw new IllegalArgumentException(var13.toString());
      } else {
         Class var7 = (Class)var0;
         TypeVariable[] var6 = var7.getTypeParameters();
         int var4 = var6.length;
         int var2 = var1.length;
         if (var2 != var4) {
            StringBuilder var11 = new StringBuilder();
            var11.append(var7.getName());
            var11.append(" requires ");
            var11.append(var4);
            var11.append(" type arguments, but got ");
            var11.append(var2);
            throw new IllegalArgumentException(var11.toString());
         } else {
            for (int var14 = 0; var14 < var4; var14++) {
               Type var8 = var1[var14];
               Class var10 = $Gson$Types.getRawType(var8);
               TypeVariable var15 = var6[var14];
               Type[] var9 = var15.getBounds();
               int var5 = var9.length;

               for (int var3 = 0; var3 < var5; var3++) {
                  if (!$Gson$Types.getRawType(var9[var3]).isAssignableFrom(var10)) {
                     StringBuilder var12 = new StringBuilder("Type argument ");
                     var12.append(var8);
                     var12.append(" does not satisfy bounds for type variable ");
                     var12.append(var15);
                     var12.append(" declared by ");
                     var12.append(var0);
                     throw new IllegalArgumentException(var12.toString());
                  }
               }
            }

            return new TypeToken($Gson$Types.newParameterizedTypeWithOwner(null, var0, var1));
         }
      }
   }

   private Type getTypeTokenTypeArgument() {
      Type var1 = this.getClass().getGenericSuperclass();
      if (var1 instanceof ParameterizedType) {
         ParameterizedType var2 = (ParameterizedType)var1;
         if (var2.getRawType() == TypeToken.class) {
            return $Gson$Types.canonicalize(var2.getActualTypeArguments()[0]);
         }
      } else if (var1 == TypeToken.class) {
         throw new IllegalStateException(
            "TypeToken must be created with a type argument: new TypeToken<...>() {}; When using code shrinkers (ProGuard, R8, ...) make sure that generic signatures are preserved."
         );
      }

      throw new IllegalStateException("Must only create direct subclasses of TypeToken");
   }

   private static boolean isAssignableFrom(Type var0, GenericArrayType var1) {
      Type var2 = var1.getGenericComponentType();
      if (!(var2 instanceof ParameterizedType)) {
         return true;
      } else {
         Object var4;
         if (var0 instanceof GenericArrayType) {
            var4 = ((GenericArrayType)var0).getGenericComponentType();
         } else {
            var4 = var0;
            if (var0 instanceof Class) {
               Class var3 = (Class)var0;

               while (true) {
                  var4 = var3;
                  if (!var3.isArray()) {
                     break;
                  }

                  var3 = var3.getComponentType();
               }
            }
         }

         return isAssignableFrom((Type)var4, (ParameterizedType)var2, new HashMap<>());
      }
   }

   private static boolean isAssignableFrom(Type var0, ParameterizedType var1, Map<String, Type> var2) {
      byte var4 = 0;
      if (var0 == null) {
         return false;
      } else if (var1.equals(var0)) {
         return true;
      } else {
         Class var7 = $Gson$Types.getRawType(var0);
         ParameterizedType var11;
         if (var0 instanceof ParameterizedType) {
            var11 = (ParameterizedType)var0;
         } else {
            var11 = null;
         }

         if (var11 != null) {
            Type[] var10 = var11.getActualTypeArguments();
            TypeVariable[] var9 = var7.getTypeParameters();

            for (int var3 = 0; var3 < var10.length; var3++) {
               Type var6 = var10[var3];
               TypeVariable var8 = var9[var3];

               while (var6 instanceof TypeVariable) {
                  var6 = (Type)var2.get(((TypeVariable)var6).getName());
               }

               var2.put(var8.getName(), var6);
            }

            if (typeEquals(var11, var1, var2)) {
               return true;
            }
         }

         Type[] var12 = var7.getGenericInterfaces();
         int var5 = var12.length;

         for (int var13 = var4; var13 < var5; var13++) {
            if (isAssignableFrom(var12[var13], var1, new HashMap<>(var2))) {
               return true;
            }
         }

         return isAssignableFrom(var7.getGenericSuperclass(), var1, new HashMap<>(var2));
      }
   }

   private static boolean matches(Type var0, Type var1, Map<String, Type> var2) {
      boolean var3;
      if (var1.equals(var0) || var0 instanceof TypeVariable && var1.equals(var2.get(((TypeVariable)var0).getName()))) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   private static boolean typeEquals(ParameterizedType var0, ParameterizedType var1, Map<String, Type> var2) {
      if (var0.getRawType().equals(var1.getRawType())) {
         Type[] var4 = var0.getActualTypeArguments();
         Type[] var5 = var1.getActualTypeArguments();

         for (int var3 = 0; var3 < var4.length; var3++) {
            if (!matches(var4[var3], var5[var3], var2)) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   public final boolean equals(Object var1) {
      boolean var2;
      if (var1 instanceof TypeToken && $Gson$Types.equals(this.type, ((TypeToken)var1).type)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public final Class<? super T> getRawType() {
      return this.rawType;
   }

   public final Type getType() {
      return this.type;
   }

   @Override
   public final int hashCode() {
      return this.hashCode;
   }

   @Deprecated
   public boolean isAssignableFrom(TypeToken<?> var1) {
      return this.isAssignableFrom(var1.getType());
   }

   @Deprecated
   public boolean isAssignableFrom(Class<?> var1) {
      return this.isAssignableFrom(var1);
   }

   @Deprecated
   public boolean isAssignableFrom(Type var1) {
      boolean var3 = false;
      if (var1 == null) {
         return false;
      } else if (this.type.equals(var1)) {
         return true;
      } else {
         Type var4 = this.type;
         if (var4 instanceof Class) {
            return this.rawType.isAssignableFrom($Gson$Types.getRawType(var1));
         } else if (var4 instanceof ParameterizedType) {
            return isAssignableFrom(var1, (ParameterizedType)var4, new HashMap<>());
         } else if (var4 instanceof GenericArrayType) {
            boolean var2 = var3;
            if (this.rawType.isAssignableFrom($Gson$Types.getRawType(var1))) {
               var2 = var3;
               if (isAssignableFrom(var1, (GenericArrayType)this.type)) {
                  var2 = true;
               }
            }

            return var2;
         } else {
            throw buildUnexpectedTypeError(var4, Class.class, ParameterizedType.class, GenericArrayType.class);
         }
      }
   }

   @Override
   public final String toString() {
      return $Gson$Types.typeToString(this.type);
   }
}
