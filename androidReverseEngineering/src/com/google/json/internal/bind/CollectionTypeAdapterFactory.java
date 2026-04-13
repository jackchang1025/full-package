package com.google.json.internal.bind;

import com.google.json.Gson;
import com.google.json.TypeAdapter;
import com.google.json.TypeAdapterFactory;
import com.google.json.internal.$Gson$Types;
import com.google.json.internal.ConstructorConstructor;
import com.google.json.internal.ObjectConstructor;
import com.google.json.reflect.TypeToken;
import com.google.json.stream.JsonReader;
import com.google.json.stream.JsonToken;
import com.google.json.stream.JsonWriter;
import java.lang.reflect.Type;
import java.util.Collection;

public final class CollectionTypeAdapterFactory implements TypeAdapterFactory {
   private final ConstructorConstructor constructorConstructor;

   public CollectionTypeAdapterFactory(ConstructorConstructor var1) {
      this.constructorConstructor = var1;
   }

   @Override
   public <T> TypeAdapter<T> create(Gson var1, TypeToken<T> var2) {
      Type var4 = var2.getType();
      Class var3 = var2.getRawType();
      if (!Collection.class.isAssignableFrom(var3)) {
         return null;
      } else {
         Type var5 = $Gson$Types.getCollectionElementType(var4, var3);
         return (TypeAdapter<T>)(new CollectionTypeAdapterFactory.Adapter<>(
            var1, var5, var1.getAdapter(TypeToken.get(var5)), this.constructorConstructor.get(var2)
         ));
      }
   }

   public static final class Adapter<E> extends TypeAdapter<Collection<E>> {
      private final ObjectConstructor<? extends Collection<E>> constructor;
      private final TypeAdapter<E> elementTypeAdapter;

      public Adapter(Gson var1, Type var2, TypeAdapter<E> var3, ObjectConstructor<? extends Collection<E>> var4) {
         this.elementTypeAdapter = new TypeAdapterRuntimeTypeWrapper<>(var1, var3, var2);
         this.constructor = var4;
      }

      public Collection<E> read(JsonReader var1) {
         if (var1.peek() == JsonToken.NULL) {
            var1.nextNull();
            return null;
         } else {
            Collection var2 = this.constructor.construct();
            var1.beginArray();

            while (var1.hasNext()) {
               var2.add(this.elementTypeAdapter.read(var1));
            }

            var1.endArray();
            return var2;
         }
      }

      public void write(JsonWriter var1, Collection<E> var2) {
         if (var2 == null) {
            var1.nullValue();
         } else {
            var1.beginArray();

            for (Object var3 : var2) {
               this.elementTypeAdapter.write(var1, (E)var3);
            }

            var1.endArray();
         }
      }
   }
}
