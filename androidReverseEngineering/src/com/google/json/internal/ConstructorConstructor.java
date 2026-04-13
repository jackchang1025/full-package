package com.google.json.internal;

import a1.q;
import android.support.annotation.NonNull;
import com.google.json.InstanceCreator;
import com.google.json.JsonIOException;
import com.google.json.ReflectionAccessFilter;
import com.google.json.internal.reflect.ReflectionHelper;
import com.google.json.reflect.TypeToken;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public final class ConstructorConstructor {
   private static final String TAG = "com.google.json.internal.ConstructorConstructor";
   private final Map<Type, InstanceCreator<?>> instanceCreators;
   private final List<ReflectionAccessFilter> reflectionFilters;
   private final boolean useJdkUnsafe;

   public ConstructorConstructor(Map<Type, InstanceCreator<?>> var1, boolean var2, List<ReflectionAccessFilter> var3) {
      this.instanceCreators = var1;
      this.useJdkUnsafe = var2;
      this.reflectionFilters = var3;
   }

   public static String checkInstantiable(Class<?> var0) {
      int var1 = var0.getModifiers();
      String var2;
      String var3;
      if (Modifier.isInterface(var1)) {
         var3 = var0.getName();
         var2 = "Interfaces can't be instantiated! Register an InstanceCreator or a TypeAdapter for this type. Interface name: ";
      } else {
         if (!Modifier.isAbstract(var1)) {
            return null;
         }

         var3 = var0.getName();
         var2 = "Abstract classes can't be instantiated! Register an InstanceCreator or a TypeAdapter for this type. Class name: ";
      }

      return var2.concat(var3);
   }

   private static <T> ObjectConstructor<T> newDefaultConstructor(Class<? super T> var0, ReflectionAccessFilter.FilterResult var1) {
      if (Modifier.isAbstract(var0.getModifiers())) {
         return null;
      } else {
         boolean var3 = false;

         Constructor var4;
         try {
            var4 = var0.getDeclaredConstructor();
         } catch (NoSuchMethodException var6) {
            return null;
         }

         boolean var2;
         ReflectionAccessFilter.FilterResult var5;
         label35: {
            var5 = ReflectionAccessFilter.FilterResult.ALLOW;
            if (var1 != var5) {
               var2 = var3;
               if (!ReflectionAccessFilterHelper.canAccess(var4, null)) {
                  break label35;
               }

               if (var1 == ReflectionAccessFilter.FilterResult.BLOCK_ALL) {
                  var2 = var3;
                  if (!Modifier.isPublic(var4.getModifiers())) {
                     break label35;
                  }
               }
            }

            var2 = true;
         }

         if (!var2) {
            StringBuilder var8 = new StringBuilder("Unable to invoke no-args constructor of ");
            var8.append(var0);
            var8.append(
               "; constructor is not accessible and ReflectionAccessFilter does not permit making it accessible. Register an InstanceCreator or a TypeAdapter for this type, change the visibility of the constructor or adjust the access filter."
            );
            return new ObjectConstructor<T>(var8.toString()) {
               final String val$message;

               {
                  this.val$message = var1;
               }

               @Override
               public T construct() {
                  throw new JsonIOException(this.val$message);
               }
            };
         } else {
            if (var1 == var5) {
               String var7 = ReflectionHelper.tryMakeAccessible(var4);
               if (var7 != null) {
                  return new ObjectConstructor<T>(var7) {
                     final String val$exceptionMessage;

                     {
                        this.val$exceptionMessage = var1;
                     }

                     @Override
                     public T construct() {
                        throw new JsonIOException(this.val$exceptionMessage);
                     }
                  };
               }
            }

            return new ObjectConstructor<T>(var4) {
               final Constructor val$constructor;

               {
                  this.val$constructor = var1;
               }

               @Override
               public T construct() {
                  try {
                     return (T)this.val$constructor.newInstance();
                  } catch (InstantiationException var3) {
                     StringBuilder var6 = new StringBuilder("Failed to invoke constructor '");
                     var6.append(ReflectionHelper.constructorToString(this.val$constructor));
                     var6.append("' with no args");
                     throw new RuntimeException(var6.toString(), var3);
                  } catch (InvocationTargetException var4) {
                     StringBuilder var1 = new StringBuilder("Failed to invoke constructor '");
                     var1.append(ReflectionHelper.constructorToString(this.val$constructor));
                     var1.append("' with no args");
                     throw new RuntimeException(var1.toString(), var4.getCause());
                  } catch (IllegalAccessException var5) {
                     throw ReflectionHelper.createExceptionForUnexpectedIllegalAccess(var5);
                  }
               }
            };
         }
      }
   }

   private static <T> ObjectConstructor<T> newDefaultImplementationConstructor(Type var0, Class<? super T> var1) {
      if (Collection.class.isAssignableFrom(var1)) {
         if (SortedSet.class.isAssignableFrom(var1)) {
            return new ObjectConstructor<T>() {
               @Override
               public T construct() {
                  return (T)(new TreeSet());
               }
            };
         } else if (Set.class.isAssignableFrom(var1)) {
            return new ObjectConstructor<T>() {
               @Override
               public T construct() {
                  return (T)(new LinkedHashSet());
               }
            };
         } else {
            return Queue.class.isAssignableFrom(var1) ? new ObjectConstructor<T>() {
               @Override
               public T construct() {
                  return (T)(new ArrayDeque());
               }
            } : new ObjectConstructor<T>() {
               @Override
               public T construct() {
                  return (T)(new ArrayList());
               }
            };
         }
      } else if (Map.class.isAssignableFrom(var1)) {
         if (ConcurrentNavigableMap.class.isAssignableFrom(var1)) {
            return new ObjectConstructor<T>() {
               @Override
               public T construct() {
                  return (T)(new ConcurrentSkipListMap());
               }
            };
         } else if (ConcurrentMap.class.isAssignableFrom(var1)) {
            return new ObjectConstructor<T>() {
               @Override
               public T construct() {
                  return (T)(new ConcurrentHashMap());
               }
            };
         } else if (SortedMap.class.isAssignableFrom(var1)) {
            return new ObjectConstructor<T>() {
               @Override
               public T construct() {
                  return (T)(new TreeMap());
               }
            };
         } else {
            return var0 instanceof ParameterizedType
                  && !String.class.isAssignableFrom(TypeToken.get(((ParameterizedType)var0).getActualTypeArguments()[0]).getRawType())
               ? new ObjectConstructor<T>() {
                  @Override
                  public T construct() {
                     return (T)(new LinkedHashMap());
                  }
               }
               : new ObjectConstructor<T>() {
                  @Override
                  public T construct() {
                     return (T)(new LinkedTreeMap());
                  }
               };
         }
      } else {
         return null;
      }
   }

   private static <T> ObjectConstructor<T> newSpecialCollectionConstructor(Type var0, Class<? super T> var1) {
      if (EnumSet.class.isAssignableFrom(var1)) {
         return new ObjectConstructor<T>(var0) {
            final Type val$type;

            {
               this.val$type = var1;
            }

            @Override
            public T construct() {
               Type var1 = this.val$type;
               if (var1 instanceof ParameterizedType) {
                  var1 = ((ParameterizedType)var1).getActualTypeArguments()[0];
                  if (var1 instanceof Class) {
                     return (T)EnumSet.noneOf((Class)var1);
                  } else {
                     StringBuilder var4 = new StringBuilder("Invalid EnumSet type: ");
                     var4.append(this.val$type.toString());
                     throw new JsonIOException(var4.toString());
                  }
               } else {
                  StringBuilder var2 = new StringBuilder("Invalid EnumSet type: ");
                  var2.append(this.val$type.toString());
                  throw new JsonIOException(var2.toString());
               }
            }
         };
      } else {
         return var1 == EnumMap.class ? new ObjectConstructor<T>(var0) {
            final Type val$type;

            {
               this.val$type = var1;
            }

            @Override
            public T construct() {
               Type var1 = this.val$type;
               if (var1 instanceof ParameterizedType) {
                  var1 = ((ParameterizedType)var1).getActualTypeArguments()[0];
                  if (var1 instanceof Class) {
                     return (T)(new EnumMap((Class)var1));
                  } else {
                     StringBuilder var4 = new StringBuilder("Invalid EnumMap type: ");
                     var4.append(this.val$type.toString());
                     throw new JsonIOException(var4.toString());
                  }
               } else {
                  StringBuilder var2 = new StringBuilder("Invalid EnumMap type: ");
                  var2.append(this.val$type.toString());
                  throw new JsonIOException(var2.toString());
               }
            }
         } : null;
      }
   }

   private <T> ObjectConstructor<T> newUnsafeAllocator(Class<? super T> var1) {
      if (this.useJdkUnsafe) {
         return new ObjectConstructor<T>(this, var1) {
            final ConstructorConstructor this$0;
            final Class val$rawType;

            {
               this.this$0 = var1;
               this.val$rawType = var2;
            }

            @Override
            public T construct() {
               try {
                  return UnsafeAllocator.INSTANCE.newInstance(this.val$rawType);
               } catch (Exception var3) {
                  StringBuilder var2 = new StringBuilder("Unable to create instance of ");
                  var2.append(this.val$rawType);
                  var2.append(". Registering an InstanceCreator or a TypeAdapter for this type, or adding a no-args constructor may fix this problem.");
                  throw new RuntimeException(var2.toString(), var3);
               }
            }
         };
      } else {
         StringBuilder var2 = new StringBuilder("Unable to create instance of ");
         var2.append(var1);
         var2.append(
            "; usage of JDK Unsafe is disabled. Registering an InstanceCreator or a TypeAdapter for this type, adding a no-args constructor, or enabling usage of JDK Unsafe may fix this problem."
         );
         return new ObjectConstructor<T>(this, var2.toString()) {
            final ConstructorConstructor this$0;
            final String val$exceptionMessage;

            {
               this.this$0 = var1;
               this.val$exceptionMessage = var2;
            }

            @Override
            public T construct() {
               throw new JsonIOException(this.val$exceptionMessage);
            }
         };
      }
   }

   @Override
   public void finalize() {
      try {
         if (!this.instanceCreators.isEmpty()) {
            this.instanceCreators.clear();
         }

         if (!this.reflectionFilters.isEmpty()) {
            this.reflectionFilters.clear();
         }
      } catch (Exception var2) {
         q.s(TAG, var2);
      }

      super.finalize();
   }

   public <T> ObjectConstructor<T> get(TypeToken<T> var1) {
      Type var2 = var1.getType();
      Class var5 = var1.getRawType();
      InstanceCreator var3 = this.instanceCreators.get(var2);
      if (var3 != null) {
         return new ObjectConstructor<T>(this, var3, var2) {
            final ConstructorConstructor this$0;
            final Type val$type;
            final InstanceCreator val$typeCreator;

            {
               this.this$0 = var1;
               this.val$typeCreator = var2;
               this.val$type = var3;
            }

            @Override
            public T construct() {
               return (T)this.val$typeCreator.createInstance(this.val$type);
            }
         };
      } else {
         var3 = this.instanceCreators.get(var5);
         if (var3 != null) {
            return new ObjectConstructor<T>(this, var3, var2) {
               final ConstructorConstructor this$0;
               final InstanceCreator val$rawTypeCreator;
               final Type val$type;

               {
                  this.this$0 = var1;
                  this.val$rawTypeCreator = var2;
                  this.val$type = var3;
               }

               @Override
               public T construct() {
                  return (T)this.val$rawTypeCreator.createInstance(this.val$type);
               }
            };
         } else {
            ObjectConstructor var10 = newSpecialCollectionConstructor(var2, var5);
            if (var10 != null) {
               return var10;
            } else {
               ReflectionAccessFilter.FilterResult var11 = ReflectionAccessFilterHelper.getFilterResult(this.reflectionFilters, var5);
               ObjectConstructor var4 = newDefaultConstructor(var5, var11);
               if (var4 != null) {
                  return var4;
               } else {
                  ObjectConstructor var6 = newDefaultImplementationConstructor(var2, var5);
                  if (var6 != null) {
                     return var6;
                  } else {
                     String var7 = checkInstantiable(var5);
                     if (var7 != null) {
                        return new ObjectConstructor<T>(this, var7) {
                           final ConstructorConstructor this$0;
                           final String val$exceptionMessage;

                           {
                              this.this$0 = var1;
                              this.val$exceptionMessage = var2;
                           }

                           @Override
                           public T construct() {
                              throw new JsonIOException(this.val$exceptionMessage);
                           }
                        };
                     } else if (var11 == ReflectionAccessFilter.FilterResult.ALLOW) {
                        return this.newUnsafeAllocator(var5);
                     } else {
                        StringBuilder var8 = new StringBuilder("Unable to create instance of ");
                        var8.append(var5);
                        var8.append(
                           "; ReflectionAccessFilter does not permit using reflection or Unsafe. Register an InstanceCreator or a TypeAdapter for this type or adjust the access filter to allow using reflection."
                        );
                        return new ObjectConstructor<T>(this, var8.toString()) {
                           final ConstructorConstructor this$0;
                           final String val$message;

                           {
                              this.this$0 = var1;
                              this.val$message = var2;
                           }

                           @Override
                           public T construct() {
                              throw new JsonIOException(this.val$message);
                           }
                        };
                     }
                  }
               }
            }
         }
      }
   }

   @NonNull
   @Override
   public String toString() {
      return this.instanceCreators.toString();
   }
}
