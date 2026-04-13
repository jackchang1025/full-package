package com.google.json;

import com.google.json.internal.bind.JsonTreeReader;
import com.google.json.internal.bind.JsonTreeWriter;
import com.google.json.stream.JsonReader;
import com.google.json.stream.JsonToken;
import com.google.json.stream.JsonWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

public abstract class TypeAdapter<T> {
   public final T fromJson(Reader var1) {
      return this.read(new JsonReader(var1));
   }

   public final T fromJson(String var1) {
      return this.fromJson(new StringReader(var1));
   }

   public final T fromJsonTree(JsonElement var1) {
      try {
         JsonTreeReader var2 = new JsonTreeReader(var1);
         return this.read(var2);
      } catch (IOException var3) {
         throw new JsonIOException(var3);
      }
   }

   public final TypeAdapter<T> nullSafe() {
      return new TypeAdapter<T>(this) {
         final TypeAdapter this$0;

         {
            this.this$0 = var1;
         }

         @Override
         public T read(JsonReader var1) {
            if (var1.peek() == JsonToken.NULL) {
               var1.nextNull();
               return null;
            } else {
               return (T)this.this$0.read(var1);
            }
         }

         @Override
         public void write(JsonWriter var1, T var2) {
            if (var2 == null) {
               var1.nullValue();
            } else {
               this.this$0.write(var1, var2);
            }
         }
      };
   }

   public abstract T read(JsonReader var1);

   public final String toJson(T var1) {
      StringWriter var2 = new StringWriter();

      try {
         this.toJson(var2, (T)var1);
      } catch (IOException var3) {
         throw new JsonIOException(var3);
      }

      return var2.toString();
   }

   public final void toJson(Writer var1, T var2) {
      this.write(new JsonWriter(var1), (T)var2);
   }

   public final JsonElement toJsonTree(T var1) {
      try {
         JsonTreeWriter var2 = new JsonTreeWriter();
         this.write(var2, (T)var1);
         return var2.get();
      } catch (IOException var3) {
         throw new JsonIOException(var3);
      }
   }

   public abstract void write(JsonWriter var1, T var2);
}
