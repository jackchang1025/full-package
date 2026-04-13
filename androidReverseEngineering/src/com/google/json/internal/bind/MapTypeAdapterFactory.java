package com.google.json.internal.bind;

import com.google.json.Gson;
import com.google.json.JsonElement;
import com.google.json.JsonPrimitive;
import com.google.json.JsonSyntaxException;
import com.google.json.TypeAdapter;
import com.google.json.TypeAdapterFactory;
import com.google.json.internal.$Gson$Types;
import com.google.json.internal.ConstructorConstructor;
import com.google.json.internal.JsonReaderInternalAccess;
import com.google.json.internal.ObjectConstructor;
import com.google.json.internal.Streams;
import com.google.json.reflect.TypeToken;
import com.google.json.stream.JsonReader;
import com.google.json.stream.JsonToken;
import com.google.json.stream.JsonWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public final class MapTypeAdapterFactory implements TypeAdapterFactory {
   final boolean complexMapKeySerialization;
   private final ConstructorConstructor constructorConstructor;

   public MapTypeAdapterFactory(ConstructorConstructor var1, boolean var2) {
      this.constructorConstructor = var1;
      this.complexMapKeySerialization = var2;
   }

   private TypeAdapter<?> getKeyAdapter(Gson var1, Type var2) {
      TypeAdapter var3;
      if (var2 != boolean.class && var2 != Boolean.class) {
         var3 = var1.getAdapter(TypeToken.get(var2));
      } else {
         var3 = TypeAdapters.BOOLEAN_AS_STRING;
      }

      return var3;
   }

   @Override
   public <T> TypeAdapter<T> create(Gson var1, TypeToken<T> var2) {
      Type var3 = var2.getType();
      Class var4 = var2.getRawType();
      if (!Map.class.isAssignableFrom(var4)) {
         return null;
      } else {
         Type[] var7 = $Gson$Types.getMapKeyAndValueTypes(var3, var4);
         TypeAdapter var5 = this.getKeyAdapter(var1, var7[0]);
         TypeAdapter var8 = var1.getAdapter(TypeToken.get(var7[1]));
         ObjectConstructor var6 = this.constructorConstructor.get(var2);
         return new MapTypeAdapterFactory.Adapter(this, var1, var7[0], var5, var7[1], var8, var6);
      }
   }

   public final class Adapter<K, V> extends TypeAdapter<Map<K, V>> {
      private final ObjectConstructor<? extends Map<K, V>> constructor;
      private final TypeAdapter<K> keyTypeAdapter;
      final MapTypeAdapterFactory this$0;
      private final TypeAdapter<V> valueTypeAdapter;

      public Adapter(
         Gson var1, Type var2, TypeAdapter<K> var3, Type var4, TypeAdapter<V> var5, ObjectConstructor<? extends Map<K, V>> var6, ObjectConstructor var7
      ) {
         this.this$0 = var1;
         this.keyTypeAdapter = new TypeAdapterRuntimeTypeWrapper<>(var2, var4, var3);
         this.valueTypeAdapter = new TypeAdapterRuntimeTypeWrapper<>(var2, var6, var5);
         this.constructor = var7;
      }

      private String keyToString(JsonElement var1) {
         if (var1.isJsonPrimitive()) {
            JsonPrimitive var2 = var1.getAsJsonPrimitive();
            if (var2.isNumber()) {
               return String.valueOf(var2.getAsNumber());
            } else if (var2.isBoolean()) {
               return Boolean.toString(var2.getAsBoolean());
            } else if (var2.isString()) {
               return var2.getAsString();
            } else {
               throw new AssertionError();
            }
         } else if (var1.isJsonNull()) {
            return "null";
         } else {
            throw new AssertionError();
         }
      }

      public Map<K, V> read(JsonReader var1) {
         JsonToken var3 = var1.peek();
         if (var3 == JsonToken.NULL) {
            var1.nextNull();
            return null;
         } else {
            Map var2 = this.constructor.construct();
            if (var3 == JsonToken.BEGIN_ARRAY) {
               var1.beginArray();

               while (var1.hasNext()) {
                  var1.beginArray();
                  var3 = this.keyTypeAdapter.read(var1);
                  if (var2.put(var3, this.valueTypeAdapter.read(var1)) != null) {
                     StringBuilder var4 = new StringBuilder("duplicate key: ");
                     var4.append(var3);
                     throw new JsonSyntaxException(var4.toString());
                  }

                  var1.endArray();
               }

               var1.endArray();
            } else {
               var1.beginObject();

               while (var1.hasNext()) {
                  JsonReaderInternalAccess.INSTANCE.promoteNameToValue(var1);
                  var3 = this.keyTypeAdapter.read(var1);
                  if (var2.put(var3, this.valueTypeAdapter.read(var1)) != null) {
                     StringBuilder var5 = new StringBuilder("duplicate key: ");
                     var5.append(var3);
                     throw new JsonSyntaxException(var5.toString());
                  }
               }

               var1.endObject();
            }

            return var2;
         }
      }

      public void write(JsonWriter var1, Map<K, V> var2) {
         if (var2 == null) {
            var1.nullValue();
         } else if (!this.this$0.complexMapKeySerialization) {
            var1.beginObject();

            for (Entry var12 : var2.entrySet()) {
               var1.name(String.valueOf(var12.getKey()));
               this.valueTypeAdapter.write(var1, (V)var12.getValue());
            }

            var1.endObject();
         } else {
            ArrayList var7 = new ArrayList(var2.size());
            ArrayList var8 = new ArrayList(var2.size());
            Iterator var11 = var2.entrySet().iterator();
            byte var6 = 0;
            byte var5 = 0;
            boolean var3 = false;

            while (var11.hasNext()) {
               Entry var10 = (Entry)var11.next();
               JsonElement var9 = this.keyTypeAdapter.toJsonTree((K)var10.getKey());
               var7.add(var9);
               var8.add(var10.getValue());
               boolean var4;
               if (!var9.isJsonArray() && !var9.isJsonObject()) {
                  var4 = false;
               } else {
                  var4 = true;
               }

               var3 |= var4;
            }

            if (var3) {
               var1.beginArray();
               int var15 = var7.size();

               for (int var13 = var5; var13 < var15; var13++) {
                  var1.beginArray();
                  Streams.write((JsonElement)var7.get(var13), var1);
                  this.valueTypeAdapter.write(var1, (V)var8.get(var13));
                  var1.endArray();
               }

               var1.endArray();
            } else {
               var1.beginObject();
               int var16 = var7.size();

               for (int var14 = var6; var14 < var16; var14++) {
                  var1.name(this.keyToString((JsonElement)var7.get(var14)));
                  this.valueTypeAdapter.write(var1, (V)var8.get(var14));
               }

               var1.endObject();
            }
         }
      }
   }
}
