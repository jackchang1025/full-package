package com.google.json.internal.bind;

import com.google.json.Gson;
import com.google.json.JsonDeserializer;
import com.google.json.JsonSerializer;
import com.google.json.TypeAdapter;
import com.google.json.TypeAdapterFactory;
import com.google.json.annotations.JsonAdapter;
import com.google.json.internal.ConstructorConstructor;
import com.google.json.reflect.TypeToken;

public final class JsonAdapterAnnotationTypeAdapterFactory implements TypeAdapterFactory {
   private final ConstructorConstructor constructorConstructor;

   public JsonAdapterAnnotationTypeAdapterFactory(ConstructorConstructor var1) {
      this.constructorConstructor = var1;
   }

   @Override
   public <T> TypeAdapter<T> create(Gson var1, TypeToken<T> var2) {
      JsonAdapter var3 = var2.getRawType().getAnnotation(JsonAdapter.class);
      return (TypeAdapter<T>)(var3 == null ? null : this.getTypeAdapter(this.constructorConstructor, var1, var2, var3));
   }

   public TypeAdapter<?> getTypeAdapter(ConstructorConstructor var1, Gson var2, TypeToken<?> var3, JsonAdapter var4) {
      Object var7 = var1.get(TypeToken.get(var4.value())).construct();
      boolean var5 = var4.nullSafe();
      Object var8;
      if (var7 instanceof TypeAdapter) {
         var8 = (TypeAdapter)var7;
      } else if (var7 instanceof TypeAdapterFactory) {
         var8 = ((TypeAdapterFactory)var7).create(var2, var3);
      } else {
         boolean var6 = var7 instanceof JsonSerializer;
         if (!var6 && !(var7 instanceof JsonDeserializer)) {
            StringBuilder var10 = new StringBuilder("Invalid attempt to bind an instance of ");
            var10.append(var7.getClass().getName());
            var10.append(" as a @JsonAdapter for ");
            var10.append(var3.toString());
            var10.append(". @JsonAdapter value must be a TypeAdapter, TypeAdapterFactory, JsonSerializer or JsonDeserializer.");
            throw new IllegalArgumentException(var10.toString());
         }

         JsonSerializer var9;
         if (var6) {
            var9 = (JsonSerializer)var7;
         } else {
            var9 = null;
         }

         JsonDeserializer var12;
         if (var7 instanceof JsonDeserializer) {
            var12 = (JsonDeserializer)var7;
         } else {
            var12 = null;
         }

         var8 = new TreeTypeAdapter(var9, var12, var2, var3, null, var5);
         var5 = false;
      }

      Object var11 = var8;
      if (var8 != null) {
         var11 = var8;
         if (var5) {
            var11 = ((TypeAdapter)var8).nullSafe();
         }
      }

      return (TypeAdapter<?>)var11;
   }
}
