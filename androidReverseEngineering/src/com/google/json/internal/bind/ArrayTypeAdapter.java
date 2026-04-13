package com.google.json.internal.bind;

import com.google.json.Gson;
import com.google.json.TypeAdapter;
import com.google.json.TypeAdapterFactory;
import com.google.json.internal.$Gson$Types;
import com.google.json.reflect.TypeToken;
import com.google.json.stream.JsonReader;
import com.google.json.stream.JsonToken;
import com.google.json.stream.JsonWriter;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.util.ArrayList;

public final class ArrayTypeAdapter<E> extends TypeAdapter<Object> {
   public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() {
      @Override
      public <T> TypeAdapter<T> create(Gson var1, TypeToken<T> var2) {
         Type var3 = var2.getType();
         if (var3 instanceof GenericArrayType || var3 instanceof Class && ((Class)var3).isArray()) {
            Type var4 = $Gson$Types.getArrayComponentType(var3);
            return new ArrayTypeAdapter(var1, (TypeAdapter<E>)var1.getAdapter(TypeToken.get(var4)), (Class<E>)$Gson$Types.getRawType(var4));
         } else {
            return null;
         }
      }
   };
   private final Class<E> componentType;
   private final TypeAdapter<E> componentTypeAdapter;

   public ArrayTypeAdapter(Gson var1, TypeAdapter<E> var2, Class<E> var3) {
      this.componentTypeAdapter = new TypeAdapterRuntimeTypeWrapper<>(var1, var2, var3);
      this.componentType = var3;
   }

   @Override
   public Object read(JsonReader var1) {
      if (var1.peek() == JsonToken.NULL) {
         var1.nextNull();
         return null;
      } else {
         ArrayList var4 = new ArrayList();
         var1.beginArray();

         while (var1.hasNext()) {
            var4.add(this.componentTypeAdapter.read(var1));
         }

         var1.endArray();
         int var3 = var4.size();
         if (!this.componentType.isPrimitive()) {
            return var4.toArray((Object[])Array.newInstance(this.componentType, var3));
         } else {
            Object var5 = Array.newInstance(this.componentType, var3);

            for (int var2 = 0; var2 < var3; var2++) {
               Array.set(var5, var2, var4.get(var2));
            }

            return var5;
         }
      }
   }

   @Override
   public void write(JsonWriter var1, Object var2) {
      if (var2 == null) {
         var1.nullValue();
      } else {
         var1.beginArray();
         int var4 = Array.getLength(var2);

         for (int var3 = 0; var3 < var4; var3++) {
            Object var5 = Array.get(var2, var3);
            this.componentTypeAdapter.write(var1, (E)var5);
         }

         var1.endArray();
      }
   }
}
