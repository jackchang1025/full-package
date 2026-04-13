package com.google.json.internal.bind;

import com.google.json.JsonArray;
import com.google.json.JsonElement;
import com.google.json.JsonNull;
import com.google.json.JsonObject;
import com.google.json.JsonPrimitive;
import com.google.json.stream.JsonWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class JsonTreeWriter extends JsonWriter {
   private static final JsonPrimitive SENTINEL_CLOSED = new JsonPrimitive("closed");
   private static final Writer UNWRITABLE_WRITER = new Writer() {
      @Override
      public void close() {
         throw new AssertionError();
      }

      @Override
      public void flush() {
         throw new AssertionError();
      }

      @Override
      public void write(char[] var1, int var2, int var3) {
         throw new AssertionError();
      }
   };
   private String pendingName;
   private JsonElement product;
   private final List<JsonElement> stack = new ArrayList<>();

   public JsonTreeWriter() {
      super(UNWRITABLE_WRITER);
      this.product = JsonNull.INSTANCE;
   }

   private JsonElement peek() {
      List var1 = this.stack;
      return (JsonElement)var1.get(var1.size() - 1);
   }

   private void put(JsonElement var1) {
      if (this.pendingName != null) {
         if (!var1.isJsonNull() || this.getSerializeNulls()) {
            ((JsonObject)this.peek()).add(this.pendingName, var1);
         }

         this.pendingName = null;
      } else if (this.stack.isEmpty()) {
         this.product = var1;
      } else {
         JsonElement var2 = this.peek();
         if (!(var2 instanceof JsonArray)) {
            throw new IllegalStateException();
         }

         ((JsonArray)var2).add(var1);
      }
   }

   @Override
   public JsonWriter beginArray() {
      JsonArray var1 = new JsonArray();
      this.put(var1);
      this.stack.add(var1);
      return this;
   }

   @Override
   public JsonWriter beginObject() {
      JsonObject var1 = new JsonObject();
      this.put(var1);
      this.stack.add(var1);
      return this;
   }

   @Override
   public void close() {
      if (this.stack.isEmpty()) {
         this.stack.add(SENTINEL_CLOSED);
      } else {
         throw new IOException("Incomplete document");
      }
   }

   @Override
   public JsonWriter endArray() {
      if (this.stack.isEmpty() || this.pendingName != null) {
         throw new IllegalStateException();
      } else if (this.peek() instanceof JsonArray) {
         List var1 = this.stack;
         var1.remove(var1.size() - 1);
         return this;
      } else {
         throw new IllegalStateException();
      }
   }

   @Override
   public JsonWriter endObject() {
      if (this.stack.isEmpty() || this.pendingName != null) {
         throw new IllegalStateException();
      } else if (this.peek() instanceof JsonObject) {
         List var1 = this.stack;
         var1.remove(var1.size() - 1);
         return this;
      } else {
         throw new IllegalStateException();
      }
   }

   @Override
   public void flush() {
   }

   public JsonElement get() {
      if (this.stack.isEmpty()) {
         return this.product;
      } else {
         StringBuilder var1 = new StringBuilder("Expected one JSON element but was ");
         var1.append(this.stack);
         throw new IllegalStateException(var1.toString());
      }
   }

   @Override
   public JsonWriter jsonValue(String var1) {
      throw new UnsupportedOperationException();
   }

   @Override
   public JsonWriter name(String var1) {
      Objects.requireNonNull(var1, "name == null");
      if (this.stack.isEmpty() || this.pendingName != null) {
         throw new IllegalStateException();
      } else if (this.peek() instanceof JsonObject) {
         this.pendingName = var1;
         return this;
      } else {
         throw new IllegalStateException();
      }
   }

   @Override
   public JsonWriter nullValue() {
      this.put(JsonNull.INSTANCE);
      return this;
   }

   @Override
   public JsonWriter value(double var1) {
      if (this.isLenient() || !Double.isNaN(var1) && !Double.isInfinite(var1)) {
         this.put(new JsonPrimitive(var1));
         return this;
      } else {
         StringBuilder var3 = new StringBuilder("JSON forbids NaN and infinities: ");
         var3.append(var1);
         throw new IllegalArgumentException(var3.toString());
      }
   }

   @Override
   public JsonWriter value(float var1) {
      if (this.isLenient() || !Float.isNaN(var1) && !Float.isInfinite(var1)) {
         this.put(new JsonPrimitive(var1));
         return this;
      } else {
         StringBuilder var2 = new StringBuilder("JSON forbids NaN and infinities: ");
         var2.append(var1);
         throw new IllegalArgumentException(var2.toString());
      }
   }

   @Override
   public JsonWriter value(long var1) {
      this.put(new JsonPrimitive(var1));
      return this;
   }

   @Override
   public JsonWriter value(Boolean var1) {
      if (var1 == null) {
         return this.nullValue();
      } else {
         this.put(new JsonPrimitive(var1));
         return this;
      }
   }

   @Override
   public JsonWriter value(Number var1) {
      if (var1 == null) {
         return this.nullValue();
      } else {
         if (!this.isLenient()) {
            double var2 = var1.doubleValue();
            if (Double.isNaN(var2) || Double.isInfinite(var2)) {
               StringBuilder var4 = new StringBuilder("JSON forbids NaN and infinities: ");
               var4.append(var1);
               throw new IllegalArgumentException(var4.toString());
            }
         }

         this.put(new JsonPrimitive(var1));
         return this;
      }
   }

   @Override
   public JsonWriter value(String var1) {
      if (var1 == null) {
         return this.nullValue();
      } else {
         this.put(new JsonPrimitive(var1));
         return this;
      }
   }

   @Override
   public JsonWriter value(boolean var1) {
      this.put(new JsonPrimitive(var1));
      return this;
   }
}
