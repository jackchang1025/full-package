package com.google.json.internal.bind;

import com.google.json.Gson;
import com.google.json.TypeAdapter;
import com.google.json.reflect.TypeToken;
import com.google.json.stream.JsonReader;
import com.google.json.stream.JsonWriter;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

final class TypeAdapterRuntimeTypeWrapper<T> extends TypeAdapter<T> {
   private final Gson context;
   private final TypeAdapter<T> delegate;
   private final Type type;

   public TypeAdapterRuntimeTypeWrapper(Gson var1, TypeAdapter<T> var2, Type var3) {
      this.context = var1;
      this.delegate = var2;
      this.type = var3;
   }

   private static Type getRuntimeTypeIfMoreSpecific(Type var0, Object var1) {
      Object var2 = var0;
      if (var1 != null) {
         if (!(var0 instanceof Class) && !(var0 instanceof TypeVariable)) {
            return var0;
         }

         var2 = var1.getClass();
      }

      return (Type)var2;
   }

   private static boolean isReflective(TypeAdapter<?> var0) {
      while (var0 instanceof SerializationDelegatingTypeAdapter) {
         TypeAdapter var1 = ((SerializationDelegatingTypeAdapter)var0).getSerializationDelegate();
         if (var1 != var0) {
            var0 = var1;
            continue;
         }
         break;
      }

      return var0 instanceof ReflectiveTypeAdapterFactory.Adapter;
   }

   @Override
   public T read(JsonReader var1) {
      return this.delegate.read(var1);
   }

   @Override
   public void write(JsonWriter var1, T var2) {
      TypeAdapter var3 = this.delegate;
      Type var4 = getRuntimeTypeIfMoreSpecific(this.type, var2);
      if (var4 != this.type) {
         var3 = this.context.getAdapter(TypeToken.get(var4));
         if (var3 instanceof ReflectiveTypeAdapterFactory.Adapter && !isReflective(this.delegate)) {
            var3 = this.delegate;
         }
      }

      var3.write(var1, var2);
   }
}
