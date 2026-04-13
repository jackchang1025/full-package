package com.google.json.internal;

import com.google.json.ExclusionStrategy;
import com.google.json.FieldAttributes;
import com.google.json.Gson;
import com.google.json.TypeAdapter;
import com.google.json.TypeAdapterFactory;
import com.google.json.annotations.Expose;
import com.google.json.annotations.Since;
import com.google.json.annotations.Until;
import com.google.json.reflect.TypeToken;
import com.google.json.stream.JsonReader;
import com.google.json.stream.JsonWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class Excluder implements TypeAdapterFactory, Cloneable {
   public static final Excluder DEFAULT = new Excluder();
   private static final double IGNORE_VERSIONS = -1.0;
   private List<ExclusionStrategy> deserializationStrategies;
   private int modifiers;
   private boolean requireExpose;
   private List<ExclusionStrategy> serializationStrategies;
   private boolean serializeInnerClasses;
   private double version = -1.0;

   public Excluder() {
      this.modifiers = 136;
      this.serializeInnerClasses = true;
      this.serializationStrategies = Collections.emptyList();
      this.deserializationStrategies = Collections.emptyList();
   }

   private boolean excludeClassChecks(Class<?> var1) {
      if (this.version != -1.0 && !this.isValidVersion(var1.getAnnotation(Since.class), var1.getAnnotation(Until.class))) {
         return true;
      } else {
         return !this.serializeInnerClasses && this.isInnerClass(var1) ? true : this.isAnonymousOrNonStaticLocal(var1);
      }
   }

   private boolean excludeClassInStrategy(Class<?> var1, boolean var2) {
      List var3;
      if (var2) {
         var3 = this.serializationStrategies;
      } else {
         var3 = this.deserializationStrategies;
      }

      Iterator var4 = var3.iterator();

      while (var4.hasNext()) {
         if (((ExclusionStrategy)var4.next()).shouldSkipClass(var1)) {
            return true;
         }
      }

      return false;
   }

   private boolean isAnonymousOrNonStaticLocal(Class<?> var1) {
      boolean var2;
      if (Enum.class.isAssignableFrom(var1) || this.isStatic(var1) || !var1.isAnonymousClass() && !var1.isLocalClass()) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   private boolean isInnerClass(Class<?> var1) {
      boolean var2;
      if (var1.isMemberClass() && !this.isStatic(var1)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   private boolean isStatic(Class<?> var1) {
      boolean var2;
      if ((var1.getModifiers() & 8) != 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   private boolean isValidSince(Since var1) {
      boolean var5 = true;
      boolean var4 = var5;
      if (var1 != null) {
         double var2 = var1.value();
         if (this.version >= var2) {
            var4 = var5;
         } else {
            var4 = false;
         }
      }

      return var4;
   }

   private boolean isValidUntil(Until var1) {
      boolean var5 = true;
      boolean var4 = var5;
      if (var1 != null) {
         double var2 = var1.value();
         if (this.version < var2) {
            var4 = var5;
         } else {
            var4 = false;
         }
      }

      return var4;
   }

   private boolean isValidVersion(Since var1, Until var2) {
      boolean var3;
      if (this.isValidSince(var1) && this.isValidUntil(var2)) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public Excluder clone() {
      try {
         return (Excluder)super.clone();
      } catch (CloneNotSupportedException var2) {
         throw new AssertionError(var2);
      }
   }

   @Override
   public <T> TypeAdapter<T> create(Gson var1, TypeToken<T> var2) {
      Class var5 = var2.getRawType();
      boolean var4 = this.excludeClassChecks(var5);
      boolean var3;
      if (!var4 && !this.excludeClassInStrategy(var5, true)) {
         var3 = false;
      } else {
         var3 = true;
      }

      if (!var4 && !this.excludeClassInStrategy(var5, false)) {
         var4 = false;
      } else {
         var4 = true;
      }

      return !var3 && !var4 ? null : new TypeAdapter<T>(this, var4, var3, var1, var2) {
         private TypeAdapter<T> delegate;
         final Excluder this$0;
         final Gson val$gson;
         final boolean val$skipDeserialize;
         final boolean val$skipSerialize;
         final TypeToken val$type;

         {
            this.this$0 = var1;
            this.val$skipDeserialize = var2;
            this.val$skipSerialize = var3;
            this.val$gson = var4;
            this.val$type = var5;
         }

         private TypeAdapter<T> delegate() {
            TypeAdapter var1 = this.delegate;
            if (var1 == null) {
               var1 = this.val$gson.getDelegateAdapter(this.this$0, this.val$type);
               this.delegate = var1;
            }

            return var1;
         }

         @Override
         public T read(JsonReader var1) {
            if (this.val$skipDeserialize) {
               var1.skipValue();
               return null;
            } else {
               return (T)this.delegate().read(var1);
            }
         }

         @Override
         public void write(JsonWriter var1, T var2) {
            if (this.val$skipSerialize) {
               var1.nullValue();
            } else {
               this.delegate().write(var1, var2);
            }
         }
      };
   }

   public Excluder disableInnerClassSerialization() {
      Excluder var1 = this.clone();
      var1.serializeInnerClasses = false;
      return var1;
   }

   public boolean excludeClass(Class<?> var1, boolean var2) {
      if (!this.excludeClassChecks(var1) && !this.excludeClassInStrategy(var1, var2)) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   public boolean excludeField(Field var1, boolean var2) {
      if ((this.modifiers & var1.getModifiers()) != 0) {
         return true;
      } else if (this.version != -1.0 && !this.isValidVersion(var1.getAnnotation(Since.class), var1.getAnnotation(Until.class))) {
         return true;
      } else if (var1.isSynthetic()) {
         return true;
      } else {
         if (this.requireExpose) {
            Expose var3 = var1.getAnnotation(Expose.class);
            if (var3 == null || (var2 ? !var3.serialize() : !var3.deserialize())) {
               return true;
            }
         }

         if (!this.serializeInnerClasses && this.isInnerClass(var1.getType())) {
            return true;
         } else if (this.isAnonymousOrNonStaticLocal(var1.getType())) {
            return true;
         } else {
            List var5;
            if (var2) {
               var5 = this.serializationStrategies;
            } else {
               var5 = this.deserializationStrategies;
            }

            if (!var5.isEmpty()) {
               FieldAttributes var4 = new FieldAttributes(var1);
               Iterator var6 = var5.iterator();

               while (var6.hasNext()) {
                  if (((ExclusionStrategy)var6.next()).shouldSkipField(var4)) {
                     return true;
                  }
               }
            }

            return false;
         }
      }
   }

   public Excluder excludeFieldsWithoutExposeAnnotation() {
      Excluder var1 = this.clone();
      var1.requireExpose = true;
      return var1;
   }

   public Excluder withExclusionStrategy(ExclusionStrategy var1, boolean var2, boolean var3) {
      Excluder var4 = this.clone();
      if (var2) {
         ArrayList var5 = new ArrayList<>(this.serializationStrategies);
         var4.serializationStrategies = var5;
         var5.add(var1);
      }

      if (var3) {
         ArrayList var6 = new ArrayList<>(this.deserializationStrategies);
         var4.deserializationStrategies = var6;
         var6.add(var1);
      }

      return var4;
   }

   public Excluder withModifiers(int... var1) {
      Excluder var4 = this.clone();
      int var2 = 0;
      var4.modifiers = 0;

      for (int var3 = var1.length; var2 < var3; var2++) {
         var4.modifiers = var1[var2] | var4.modifiers;
      }

      return var4;
   }

   public Excluder withVersion(double var1) {
      Excluder var3 = this.clone();
      var3.version = var1;
      return var3;
   }
}
