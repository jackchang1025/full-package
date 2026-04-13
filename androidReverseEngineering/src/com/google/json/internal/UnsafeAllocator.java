package com.google.json.internal;

import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public abstract class UnsafeAllocator {
   public static final UnsafeAllocator INSTANCE = create();

   private static void assertInstantiable(Class<?> var0) {
      String var1 = ConstructorConstructor.checkInstantiable(var0);
      if (var1 != null) {
         throw new AssertionError("UnsafeAllocator is used for non-instantiable type: ".concat(var1));
      }
   }

   private static UnsafeAllocator create() {
      try {
         Class var8 = Class.forName("sun.misc.Unsafe");
         Field var2 = var8.getDeclaredField("theUnsafe");
         var2.setAccessible(true);
         Object var9 = var2.get(null);
         return new UnsafeAllocator(var8.getMethod("allocateInstance", Class.class), var9) {
            final Method val$allocateInstance;
            final Object val$unsafe;

            {
               this.val$allocateInstance = var1;
               this.val$unsafe = var2;
            }

            @Override
            public <T> T newInstance(Class<T> var1) {
               UnsafeAllocator.assertInstantiable(var1);
               return (T)this.val$allocateInstance.invoke(this.val$unsafe, var1);
            }
         };
      } catch (Exception var5) {
         try {
            Method var6 = ObjectStreamClass.class.getDeclaredMethod("getConstructorId", Class.class);
            var6.setAccessible(true);
            int var0 = (Integer)var6.invoke(null, Object.class);
            var6 = ObjectStreamClass.class.getDeclaredMethod("newInstance", Class.class, int.class);
            var6.setAccessible(true);
            return new UnsafeAllocator(var6, var0) {
               final int val$constructorId;
               final Method val$newInstance;

               {
                  this.val$newInstance = var1;
                  this.val$constructorId = var2;
               }

               @Override
               public <T> T newInstance(Class<T> var1) {
                  UnsafeAllocator.assertInstantiable(var1);
                  return (T)this.val$newInstance.invoke(null, var1, this.val$constructorId);
               }
            };
         } catch (Exception var4) {
            try {
               Method var1 = ObjectInputStream.class.getDeclaredMethod("newInstance", Class.class, Class.class);
               var1.setAccessible(true);
               return new UnsafeAllocator(var1) {
                  final Method val$newInstance;

                  {
                     this.val$newInstance = var1;
                  }

                  @Override
                  public <T> T newInstance(Class<T> var1) {
                     UnsafeAllocator.assertInstantiable(var1);
                     return (T)this.val$newInstance.invoke(null, var1, Object.class);
                  }
               };
            } catch (Exception var3) {
               return new UnsafeAllocator() {
                  @Override
                  public <T> T newInstance(Class<T> var1) {
                     StringBuilder var2 = new StringBuilder("Cannot allocate ");
                     var2.append(var1);
                     var2.append(". Usage of JDK sun.misc.Unsafe is enabled, but it could not be used. Make sure your runtime is configured correctly.");
                     throw new UnsupportedOperationException(var2.toString());
                  }
               };
            }
         }
      }
   }

   public abstract <T> T newInstance(Class<T> var1);
}
