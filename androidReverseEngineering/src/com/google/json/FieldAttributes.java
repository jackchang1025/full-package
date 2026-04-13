package com.google.json;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

public final class FieldAttributes {
   private final Field field;

   public FieldAttributes(Field var1) {
      Objects.requireNonNull(var1);
      this.field = var1;
   }

   public <T extends Annotation> T getAnnotation(Class<T> var1) {
      return this.field.getAnnotation(var1);
   }

   public Collection<Annotation> getAnnotations() {
      return Arrays.asList(this.field.getAnnotations());
   }

   public Class<?> getDeclaredClass() {
      return this.field.getType();
   }

   public Type getDeclaredType() {
      return this.field.getGenericType();
   }

   public Class<?> getDeclaringClass() {
      return this.field.getDeclaringClass();
   }

   public String getName() {
      return this.field.getName();
   }

   public boolean hasModifier(int var1) {
      boolean var2;
      if ((var1 & this.field.getModifiers()) != 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   @Override
   public String toString() {
      return this.field.toString();
   }
}
