package com.google.json.internal.bind;

import com.google.json.Gson;
import com.google.json.JsonDeserializationContext;
import com.google.json.JsonDeserializer;
import com.google.json.JsonElement;
import com.google.json.JsonSerializationContext;
import com.google.json.JsonSerializer;
import com.google.json.TypeAdapter;
import com.google.json.TypeAdapterFactory;
import com.google.json.internal.$Gson$Preconditions;
import com.google.json.internal.Streams;
import com.google.json.reflect.TypeToken;
import com.google.json.stream.JsonReader;
import com.google.json.stream.JsonWriter;
import java.lang.reflect.Type;

public final class TreeTypeAdapter<T> extends SerializationDelegatingTypeAdapter<T> {
   private final TreeTypeAdapter<T>.GsonContextImpl context = new TreeTypeAdapter.GsonContextImpl(this);
   private volatile TypeAdapter<T> delegate;
   private final JsonDeserializer<T> deserializer;
   final Gson gson;
   private final boolean nullSafe;
   private final JsonSerializer<T> serializer;
   private final TypeAdapterFactory skipPast;
   private final TypeToken<T> typeToken;

   public TreeTypeAdapter(JsonSerializer<T> var1, JsonDeserializer<T> var2, Gson var3, TypeToken<T> var4, TypeAdapterFactory var5) {
      this(var1, var2, var3, var4, var5, true);
   }

   public TreeTypeAdapter(JsonSerializer<T> var1, JsonDeserializer<T> var2, Gson var3, TypeToken<T> var4, TypeAdapterFactory var5, boolean var6) {
      this.serializer = var1;
      this.deserializer = var2;
      this.gson = var3;
      this.typeToken = var4;
      this.skipPast = var5;
      this.nullSafe = var6;
   }

   private TypeAdapter<T> delegate() {
      TypeAdapter var1 = this.delegate;
      if (var1 == null) {
         var1 = this.gson.getDelegateAdapter(this.skipPast, this.typeToken);
         this.delegate = var1;
      }

      return var1;
   }

   public static TypeAdapterFactory newFactory(TypeToken<?> var0, Object var1) {
      return new TreeTypeAdapter.SingleTypeFactory(var1, var0, false, null);
   }

   public static TypeAdapterFactory newFactoryWithMatchRawType(TypeToken<?> var0, Object var1) {
      boolean var2;
      if (var0.getType() == var0.getRawType()) {
         var2 = true;
      } else {
         var2 = false;
      }

      return new TreeTypeAdapter.SingleTypeFactory(var1, var0, var2, null);
   }

   public static TypeAdapterFactory newTypeHierarchyFactory(Class<?> var0, Object var1) {
      return new TreeTypeAdapter.SingleTypeFactory(var1, null, false, var0);
   }

   @Override
   public TypeAdapter<T> getSerializationDelegate() {
      Object var1;
      if (this.serializer != null) {
         var1 = this;
      } else {
         var1 = this.delegate();
      }

      return (TypeAdapter<T>)var1;
   }

   @Override
   public T read(JsonReader var1) {
      if (this.deserializer == null) {
         return this.delegate().read(var1);
      } else {
         JsonElement var2 = Streams.parse(var1);
         return this.nullSafe && var2.isJsonNull() ? null : this.deserializer.deserialize(var2, this.typeToken.getType(), this.context);
      }
   }

   @Override
   public void write(JsonWriter var1, T var2) {
      JsonSerializer var3 = this.serializer;
      if (var3 == null) {
         this.delegate().write(var1, (T)var2);
      } else if (this.nullSafe && var2 == null) {
         var1.nullValue();
      } else {
         Streams.write(var3.serialize(var2, this.typeToken.getType(), this.context), var1);
      }
   }

   public final class GsonContextImpl implements JsonSerializationContext, JsonDeserializationContext {
      final TreeTypeAdapter this$0;

      private GsonContextImpl(TreeTypeAdapter var1) {
         this.this$0 = var1;
      }

      @Override
      public <R> R deserialize(JsonElement var1, Type var2) {
         return this.this$0.gson.fromJson(var1, var2);
      }

      @Override
      public JsonElement serialize(Object var1) {
         return this.this$0.gson.toJsonTree(var1);
      }

      @Override
      public JsonElement serialize(Object var1, Type var2) {
         return this.this$0.gson.toJsonTree(var1, var2);
      }
   }

   public static final class SingleTypeFactory implements TypeAdapterFactory {
      private final JsonDeserializer<?> deserializer;
      private final TypeToken<?> exactType;
      private final Class<?> hierarchyType;
      private final boolean matchRawType;
      private final JsonSerializer<?> serializer;

      public SingleTypeFactory(Object var1, TypeToken<?> var2, boolean var3, Class<?> var4) {
         boolean var5 = var1 instanceof JsonSerializer;
         JsonDeserializer var7 = null;
         JsonSerializer var6;
         if (var5) {
            var6 = (JsonSerializer)var1;
         } else {
            var6 = null;
         }

         this.serializer = var6;
         if (var1 instanceof JsonDeserializer) {
            var7 = (JsonDeserializer)var1;
         }

         this.deserializer = var7;
         if (var6 == null && var7 == null) {
            var5 = false;
         } else {
            var5 = true;
         }

         $Gson$Preconditions.checkArgument(var5);
         this.exactType = var2;
         this.matchRawType = var3;
         this.hierarchyType = var4;
      }

      @Override
      public <T> TypeAdapter<T> create(Gson var1, TypeToken<T> var2) {
         TypeToken var4 = this.exactType;
         boolean var3;
         if (var4 != null) {
            if (var4.equals(var2) || this.matchRawType && this.exactType.getType() == var2.getRawType()) {
               var3 = true;
            } else {
               var3 = false;
            }
         } else {
            var3 = this.hierarchyType.isAssignableFrom(var2.getRawType());
         }

         TreeTypeAdapter var5;
         if (var3) {
            var5 = new TreeTypeAdapter<>(this.serializer, this.deserializer, var1, var2, this);
         } else {
            var5 = null;
         }

         return var5;
      }
   }
}
