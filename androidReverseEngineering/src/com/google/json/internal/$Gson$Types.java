package com.google.json.internal;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Properties;

public final class $Gson$Types {
   static final boolean $assertionsDisabled = false;
   static final Type[] EMPTY_TYPE_ARRAY = new Type[0];

   private $Gson$Types() {
      throw new UnsupportedOperationException();
   }

   public static GenericArrayType arrayOf(Type var0) {
      return new $Gson$Types.GenericArrayTypeImpl(var0);
   }

   public static Type canonicalize(Type var0) {
      if (var0 instanceof Class) {
         Class var1 = (Class)var0;
         var0 = var1;
         if (var1.isArray()) {
            var0 = new $Gson$Types.GenericArrayTypeImpl(canonicalize(var1.getComponentType()));
         }

         return var0;
      } else if (var0 instanceof ParameterizedType) {
         ParameterizedType var3 = (ParameterizedType)var0;
         return new $Gson$Types.ParameterizedTypeImpl(var3.getOwnerType(), var3.getRawType(), var3.getActualTypeArguments());
      } else if (var0 instanceof GenericArrayType) {
         return new $Gson$Types.GenericArrayTypeImpl(((GenericArrayType)var0).getGenericComponentType());
      } else if (var0 instanceof WildcardType) {
         WildcardType var2 = (WildcardType)var0;
         return new $Gson$Types.WildcardTypeImpl(var2.getUpperBounds(), var2.getLowerBounds());
      } else {
         return var0;
      }
   }

   public static void checkNotPrimitive(Type var0) {
      boolean var1;
      if (var0 instanceof Class && ((Class)var0).isPrimitive()) {
         var1 = false;
      } else {
         var1 = true;
      }

      $Gson$Preconditions.checkArgument(var1);
   }

   private static Class<?> declaringClassOf(TypeVariable<?> var0) {
      GenericDeclaration var1 = var0.getGenericDeclaration();
      Class var2;
      if (var1 instanceof Class) {
         var2 = (Class)var1;
      } else {
         var2 = null;
      }

      return var2;
   }

   private static boolean equal(Object var0, Object var1) {
      return Objects.equals(var0, var1);
   }

   public static boolean equals(Type var0, Type var1) {
      boolean var2 = true;
      boolean var3 = true;
      boolean var4 = true;
      if (var0 == var1) {
         return true;
      } else if (var0 instanceof Class) {
         return var0.equals(var1);
      } else if (var0 instanceof ParameterizedType) {
         if (!(var1 instanceof ParameterizedType)) {
            return false;
         } else {
            ParameterizedType var8 = (ParameterizedType)var0;
            ParameterizedType var12 = (ParameterizedType)var1;
            if (equal(var8.getOwnerType(), var12.getOwnerType())
               && var8.getRawType().equals(var12.getRawType())
               && Arrays.equals((Object[])var8.getActualTypeArguments(), (Object[])var12.getActualTypeArguments())) {
               var2 = var4;
            } else {
               var2 = false;
            }

            return var2;
         }
      } else if (var0 instanceof GenericArrayType) {
         if (!(var1 instanceof GenericArrayType)) {
            return false;
         } else {
            GenericArrayType var7 = (GenericArrayType)var0;
            GenericArrayType var11 = (GenericArrayType)var1;
            return equals(var7.getGenericComponentType(), var11.getGenericComponentType());
         }
      } else if (var0 instanceof WildcardType) {
         if (!(var1 instanceof WildcardType)) {
            return false;
         } else {
            WildcardType var6 = (WildcardType)var0;
            WildcardType var10 = (WildcardType)var1;
            if (!Arrays.equals((Object[])var6.getUpperBounds(), (Object[])var10.getUpperBounds())
               || !Arrays.equals((Object[])var6.getLowerBounds(), (Object[])var10.getLowerBounds())) {
               var2 = false;
            }

            return var2;
         }
      } else if (var0 instanceof TypeVariable) {
         if (!(var1 instanceof TypeVariable)) {
            return false;
         } else {
            TypeVariable var5 = (TypeVariable)var0;
            TypeVariable var9 = (TypeVariable)var1;
            if (var5.getGenericDeclaration() == var9.getGenericDeclaration() && var5.getName().equals(var9.getName())) {
               var2 = var3;
            } else {
               var2 = false;
            }

            return var2;
         }
      } else {
         return false;
      }
   }

   public static Type getArrayComponentType(Type var0) {
      if (var0 instanceof GenericArrayType) {
         var0 = ((GenericArrayType)var0).getGenericComponentType();
      } else {
         var0 = ((Class)var0).getComponentType();
      }

      return var0;
   }

   public static Type getCollectionElementType(Type var0, Class<?> var1) {
      var0 = getSupertype(var0, var1, Collection.class);
      return (Type)(var0 instanceof ParameterizedType ? ((ParameterizedType)var0).getActualTypeArguments()[0] : Object.class);
   }

   private static Type getGenericSupertype(Type var0, Class<?> var1, Class<?> var2) {
      if (var2 == var1) {
         return var0;
      } else {
         if (var2.isInterface()) {
            Class[] var5 = var1.getInterfaces();
            int var4 = var5.length;

            for (int var3 = 0; var3 < var4; var3++) {
               Class var6 = var5[var3];
               if (var6 == var2) {
                  return var1.getGenericInterfaces()[var3];
               }

               if (var2.isAssignableFrom(var6)) {
                  return getGenericSupertype(var1.getGenericInterfaces()[var3], var5[var3], var2);
               }
            }
         }

         if (!var1.isInterface()) {
            while (var1 != Object.class) {
               Class var7 = var1.getSuperclass();
               if (var7 == var2) {
                  return var1.getGenericSuperclass();
               }

               if (var2.isAssignableFrom(var7)) {
                  return getGenericSupertype(var1.getGenericSuperclass(), var7, var2);
               }

               var1 = var7;
            }
         }

         return var2;
      }
   }

   public static Type[] getMapKeyAndValueTypes(Type var0, Class<?> var1) {
      if (var0 == Properties.class) {
         return new Type[]{String.class, String.class};
      } else {
         var0 = getSupertype(var0, var1, Map.class);
         return var0 instanceof ParameterizedType ? ((ParameterizedType)var0).getActualTypeArguments() : new Type[]{Object.class, Object.class};
      }
   }

   public static Class<?> getRawType(Type var0) {
      if (var0 instanceof Class) {
         return (Class<?>)var0;
      } else if (var0 instanceof ParameterizedType) {
         var0 = ((ParameterizedType)var0).getRawType();
         $Gson$Preconditions.checkArgument(var0 instanceof Class);
         return (Class<?>)var0;
      } else if (var0 instanceof GenericArrayType) {
         return Array.newInstance(getRawType(((GenericArrayType)var0).getGenericComponentType()), 0).getClass();
      } else if (var0 instanceof TypeVariable) {
         return Object.class;
      } else if (var0 instanceof WildcardType) {
         return getRawType(((WildcardType)var0).getUpperBounds()[0]);
      } else {
         String var1;
         if (var0 == null) {
            var1 = "null";
         } else {
            var1 = var0.getClass().getName();
         }

         StringBuilder var2 = new StringBuilder("Expected a Class, ParameterizedType, or GenericArrayType, but <");
         var2.append(var0);
         var2.append("> is of type ");
         var2.append(var1);
         throw new IllegalArgumentException(var2.toString());
      }
   }

   private static Type getSupertype(Type var0, Class<?> var1, Class<?> var2) {
      Type var3 = var0;
      if (var0 instanceof WildcardType) {
         var3 = ((WildcardType)var0).getUpperBounds()[0];
      }

      $Gson$Preconditions.checkArgument(var2.isAssignableFrom(var1));
      return resolve(var3, var1, getGenericSupertype(var3, var1, var2));
   }

   private static int indexOf(Object[] var0, Object var1) {
      int var3 = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         if (var1.equals(var0[var2])) {
            return var2;
         }
      }

      throw new NoSuchElementException();
   }

   public static ParameterizedType newParameterizedTypeWithOwner(Type var0, Type var1, Type... var2) {
      return new $Gson$Types.ParameterizedTypeImpl(var0, var1, var2);
   }

   public static Type resolve(Type var0, Class<?> var1, Type var2) {
      return resolve(var0, var1, var2, new HashMap<>());
   }

   private static Type resolve(Type var0, Class<?> var1, Type var2, Map<TypeVariable<?>, Type> var3) {
      TypeVariable var10 = null;
      Type var9 = var2;

      while (true) {
         TypeVariable var29;
         if (var9 instanceof TypeVariable) {
            var29 = (TypeVariable)var9;
            Type var20 = (Type)var3.get(var29);
            if (var20 != null) {
               if (var20 != void.class) {
                  var9 = var20;
               }

               return var9;
            }

            var3.put(var29, void.class);
            TypeVariable var21 = var10;
            if (var10 == null) {
               var21 = var29;
            }

            Type var12 = resolveTypeVariable(var0, var1, var29);
            var10 = var21;
            var9 = var12;
            if (var12 != var29) {
               continue;
            }

            var29 = var21;
            var2 = var12;
         } else {
            label79: {
               label78: {
                  label92: {
                     if (var9 instanceof Class) {
                        var2 = (Class)var9;
                        if (var2.isArray()) {
                           Class var28 = var2.getComponentType();
                           Type var19 = resolve(var0, var1, var28, var3);
                           var0 = var19;
                           if (equal(var28, var19)) {
                              var29 = var10;
                              break label79;
                           }
                           break label92;
                        }
                     }

                     if (!(var9 instanceof GenericArrayType)) {
                        boolean var8 = var9 instanceof ParameterizedType;
                        int var6 = 0;
                        if (!var8) {
                           var29 = var10;
                           var2 = var9;
                           if (var9 instanceof WildcardType) {
                              WildcardType var27 = (WildcardType)var9;
                              Type[] var32 = var27.getLowerBounds();
                              Type[] var33 = var27.getUpperBounds();
                              if (var32.length == 1) {
                                 var0 = resolve(var0, var1, var32[0], var3);
                                 var29 = var10;
                                 var2 = var27;
                                 if (var0 != var32[0]) {
                                    var2 = supertypeOf(var0);
                                    var29 = var10;
                                 }
                              } else {
                                 var29 = var10;
                                 var2 = var27;
                                 if (var33.length == 1) {
                                    var0 = resolve(var0, var1, var33[0], var3);
                                    var29 = var10;
                                    var2 = var27;
                                    if (var0 != var33[0]) {
                                       var2 = subtypeOf(var0);
                                       var29 = var10;
                                    }
                                 }
                              }
                           }
                           break label79;
                        }

                        ParameterizedType var31 = (ParameterizedType)var9;
                        Type var23 = var31.getOwnerType();
                        Type var13 = resolve(var0, var1, var23, var3);
                        boolean var4 = equal(var13, var23) ^ true;
                        Type[] var26 = var31.getActualTypeArguments();
                        int var7 = var26.length;

                        while (var6 < var7) {
                           Type var30 = resolve(var0, var1, var26[var6], var3);
                           boolean var5 = var4;
                           Type[] var24 = var26;
                           if (!equal(var30, var26[var6])) {
                              var5 = var4;
                              var24 = var26;
                              if (!var4) {
                                 var24 = (Type[])var26.clone();
                                 var5 = true;
                              }

                              var24[var6] = var30;
                           }

                           var6++;
                           var4 = var5;
                           var26 = var24;
                        }

                        var29 = var10;
                        var2 = var31;
                        if (!var4) {
                           break label79;
                        }

                        var15 = newParameterizedTypeWithOwner(var13, var31.getRawType(), var26);
                        break label78;
                     }

                     var2 = (GenericArrayType)var9;
                     var9 = var2.getGenericComponentType();
                     Type var18 = resolve(var0, var1, var9, var3);
                     var0 = var18;
                     if (equal(var9, var18)) {
                        var29 = var10;
                        break label79;
                     }
                  }

                  var15 = arrayOf(var0);
               }

               var29 = var10;
               var2 = (GenericArrayType)var15;
            }
         }

         if (var29 != null) {
            var3.put(var29, var2);
         }

         return var2;
      }
   }

   private static Type resolveTypeVariable(Type var0, Class<?> var1, TypeVariable<?> var2) {
      Class var4 = declaringClassOf(var2);
      if (var4 == null) {
         return var2;
      } else {
         var0 = getGenericSupertype(var0, var1, var4);
         if (var0 instanceof ParameterizedType) {
            int var3 = indexOf(var4.getTypeParameters(), var2);
            return ((ParameterizedType)var0).getActualTypeArguments()[var3];
         } else {
            return var2;
         }
      }
   }

   public static WildcardType subtypeOf(Type var0) {
      Type[] var1;
      if (var0 instanceof WildcardType) {
         var1 = ((WildcardType)var0).getUpperBounds();
      } else {
         var1 = new Type[]{var0};
      }

      return new $Gson$Types.WildcardTypeImpl(var1, EMPTY_TYPE_ARRAY);
   }

   public static WildcardType supertypeOf(Type var0) {
      Type[] var1;
      if (var0 instanceof WildcardType) {
         var1 = ((WildcardType)var0).getLowerBounds();
      } else {
         var1 = new Type[]{var0};
      }

      return new $Gson$Types.WildcardTypeImpl(new Type[]{Object.class}, var1);
   }

   public static String typeToString(Type var0) {
      String var1;
      if (var0 instanceof Class) {
         var1 = ((Class)var0).getName();
      } else {
         var1 = var0.toString();
      }

      return var1;
   }

   public static final class GenericArrayTypeImpl implements GenericArrayType, Serializable {
      private static final long serialVersionUID = 0L;
      private final Type componentType;

      public GenericArrayTypeImpl(Type var1) {
         Objects.requireNonNull(var1);
         this.componentType = $Gson$Types.canonicalize(var1);
      }

      @Override
      public boolean equals(Object var1) {
         boolean var2;
         if (var1 instanceof GenericArrayType && $Gson$Types.equals(this, (GenericArrayType)var1)) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      @Override
      public Type getGenericComponentType() {
         return this.componentType;
      }

      @Override
      public int hashCode() {
         return this.componentType.hashCode();
      }

      @Override
      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append($Gson$Types.typeToString(this.componentType));
         var1.append("[]");
         return var1.toString();
      }
   }

   public static final class ParameterizedTypeImpl implements ParameterizedType, Serializable {
      private static final long serialVersionUID = 0L;
      private final Type ownerType;
      private final Type rawType;
      private final Type[] typeArguments;

      public ParameterizedTypeImpl(Type var1, Type var2, Type... var3) {
         Objects.requireNonNull(var2);
         boolean var7 = var2 instanceof Class;
         byte var5 = 0;
         if (var7) {
            Class var9 = (Class)var2;
            var7 = Modifier.isStatic(var9.getModifiers());
            boolean var8 = true;
            boolean var4;
            if (!var7 && var9.getEnclosingClass() != null) {
               var4 = false;
            } else {
               var4 = true;
            }

            var7 = var8;
            if (var1 == null) {
               if (var4) {
                  var7 = var8;
               } else {
                  var7 = false;
               }
            }

            $Gson$Preconditions.checkArgument(var7);
         }

         if (var1 == null) {
            var1 = null;
         } else {
            var1 = $Gson$Types.canonicalize(var1);
         }

         this.ownerType = var1;
         this.rawType = $Gson$Types.canonicalize(var2);
         Type[] var11 = (Type[])var3.clone();
         this.typeArguments = var11;
         int var6 = var11.length;

         for (int var13 = var5; var13 < var6; var13++) {
            Objects.requireNonNull(this.typeArguments[var13]);
            $Gson$Types.checkNotPrimitive(this.typeArguments[var13]);
            Type[] var12 = this.typeArguments;
            var12[var13] = $Gson$Types.canonicalize(var12[var13]);
         }
      }

      private static int hashCodeOrZero(Object var0) {
         int var1;
         if (var0 != null) {
            var1 = var0.hashCode();
         } else {
            var1 = 0;
         }

         return var1;
      }

      @Override
      public boolean equals(Object var1) {
         boolean var2;
         if (var1 instanceof ParameterizedType && $Gson$Types.equals(this, (ParameterizedType)var1)) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      @Override
      public Type[] getActualTypeArguments() {
         return (Type[])this.typeArguments.clone();
      }

      @Override
      public Type getOwnerType() {
         return this.ownerType;
      }

      @Override
      public Type getRawType() {
         return this.rawType;
      }

      @Override
      public int hashCode() {
         return Arrays.hashCode((Object[])this.typeArguments) ^ this.rawType.hashCode() ^ hashCodeOrZero(this.ownerType);
      }

      @Override
      public String toString() {
         int var2 = this.typeArguments.length;
         if (var2 == 0) {
            return $Gson$Types.typeToString(this.rawType);
         } else {
            StringBuilder var3 = new StringBuilder((var2 + 1) * 30);
            var3.append($Gson$Types.typeToString(this.rawType));
            var3.append("<");
            var3.append($Gson$Types.typeToString(this.typeArguments[0]));

            for (int var1 = 1; var1 < var2; var1++) {
               var3.append(", ");
               var3.append($Gson$Types.typeToString(this.typeArguments[var1]));
            }

            var3.append(">");
            return var3.toString();
         }
      }
   }

   public static final class WildcardTypeImpl implements WildcardType, Serializable {
      private static final long serialVersionUID = 0L;
      private final Type lowerBound;
      private final Type upperBound;

      public WildcardTypeImpl(Type[] var1, Type[] var2) {
         int var3 = var2.length;
         boolean var5 = true;
         boolean var4;
         if (var3 <= 1) {
            var4 = true;
         } else {
            var4 = false;
         }

         $Gson$Preconditions.checkArgument(var4);
         if (var1.length == 1) {
            var4 = true;
         } else {
            var4 = false;
         }

         $Gson$Preconditions.checkArgument(var4);
         if (var2.length == 1) {
            Objects.requireNonNull(var2[0]);
            $Gson$Types.checkNotPrimitive(var2[0]);
            if (var1[0] == Object.class) {
               var4 = var5;
            } else {
               var4 = false;
            }

            $Gson$Preconditions.checkArgument(var4);
            this.lowerBound = $Gson$Types.canonicalize(var2[0]);
            this.upperBound = Object.class;
         } else {
            Objects.requireNonNull(var1[0]);
            $Gson$Types.checkNotPrimitive(var1[0]);
            this.lowerBound = null;
            this.upperBound = $Gson$Types.canonicalize(var1[0]);
         }
      }

      @Override
      public boolean equals(Object var1) {
         boolean var2;
         if (var1 instanceof WildcardType && $Gson$Types.equals(this, (WildcardType)var1)) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      @Override
      public Type[] getLowerBounds() {
         Type var2 = this.lowerBound;
         Type[] var1;
         if (var2 != null) {
            var1 = new Type[]{var2};
         } else {
            var1 = $Gson$Types.EMPTY_TYPE_ARRAY;
         }

         return var1;
      }

      @Override
      public Type[] getUpperBounds() {
         return new Type[]{this.upperBound};
      }

      @Override
      public int hashCode() {
         Type var2 = this.lowerBound;
         int var1;
         if (var2 != null) {
            var1 = var2.hashCode() + 31;
         } else {
            var1 = 1;
         }

         return var1 ^ this.upperBound.hashCode() + 31;
      }

      @Override
      public String toString() {
         StringBuilder var1;
         Type var2;
         if (this.lowerBound != null) {
            var1 = new StringBuilder("? super ");
            var2 = this.lowerBound;
         } else {
            if (this.upperBound == Object.class) {
               return "?";
            }

            var1 = new StringBuilder("? extends ");
            var2 = this.upperBound;
         }

         var1.append($Gson$Types.typeToString(var2));
         return var1.toString();
      }
   }
}
