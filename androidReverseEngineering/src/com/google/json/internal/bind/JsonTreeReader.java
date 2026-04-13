package com.google.json.internal.bind;

import com.google.json.JsonArray;
import com.google.json.JsonElement;
import com.google.json.JsonNull;
import com.google.json.JsonObject;
import com.google.json.JsonPrimitive;
import com.google.json.stream.JsonReader;
import com.google.json.stream.JsonToken;
import com.google.json.stream.MalformedJsonException;
import java.io.Reader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map.Entry;

public final class JsonTreeReader extends JsonReader {
   private static final Object SENTINEL_CLOSED = new Object();
   private static final Reader UNREADABLE_READER = new Reader() {
      @Override
      public void close() {
         throw new AssertionError();
      }

      @Override
      public int read(char[] var1, int var2, int var3) {
         throw new AssertionError();
      }
   };
   private int[] pathIndices;
   private String[] pathNames;
   private Object[] stack = new Object[32];
   private int stackSize = 0;

   public JsonTreeReader(JsonElement var1) {
      super(UNREADABLE_READER);
      this.pathNames = new String[32];
      this.pathIndices = new int[32];
      this.push(var1);
   }

   private void expect(JsonToken var1) {
      if (this.peek() != var1) {
         StringBuilder var2 = new StringBuilder("Expected ");
         var2.append(var1);
         var2.append(" but was ");
         var2.append(this.peek());
         var2.append(this.locationString());
         throw new IllegalStateException(var2.toString());
      }
   }

   private String getPath(boolean var1) {
      StringBuilder var6 = new StringBuilder("$");
      int var3 = 0;

      while (true) {
         int var5 = this.stackSize;
         if (var3 >= var5) {
            return var6.toString();
         }

         Object[] var7 = this.stack;
         Object var8 = var7[var3];
         int var2;
         if (var8 instanceof JsonArray) {
            var2 = ++var3;
            if (var3 < var5) {
               var2 = var3;
               if (var7[var3] instanceof Iterator) {
                  int var4 = this.pathIndices[var3];
                  var2 = var4;
                  if (var1) {
                     var2 = var4;
                     label32:
                     if (var4 > 0) {
                        if (var3 != var5 - 1) {
                           var2 = var4;
                           if (var3 != var5 - 2) {
                              break label32;
                           }
                        }

                        var2 = var4 - 1;
                     }
                  }

                  var6.append('[');
                  var6.append(var2);
                  var6.append(']');
                  var2 = var3;
               }
            }
         } else {
            var2 = var3;
            if (var8 instanceof JsonObject) {
               var2 = ++var3;
               if (var3 < var5) {
                  var2 = var3;
                  if (var7[var3] instanceof Iterator) {
                     var6.append('.');
                     String var12 = this.pathNames[var3];
                     var2 = var3;
                     if (var12 != null) {
                        var6.append(var12);
                        var2 = var3;
                     }
                  }
               }
            }
         }

         var3 = var2 + 1;
      }
   }

   private String locationString() {
      StringBuilder var1 = new StringBuilder(" at path ");
      var1.append(this.getPath());
      return var1.toString();
   }

   private String nextName(boolean var1) {
      this.expect(JsonToken.NAME);
      Entry var5 = (Entry)((Iterator)this.peekStack()).next();
      String var4 = (String)var5.getKey();
      String[] var6 = this.pathNames;
      int var2 = this.stackSize;
      String var3;
      if (var1) {
         var3 = "<skipped>";
      } else {
         var3 = var4;
      }

      var6[var2 - 1] = var3;
      this.push(var5.getValue());
      return var4;
   }

   private Object peekStack() {
      return this.stack[this.stackSize - 1];
   }

   private Object popStack() {
      Object[] var2 = this.stack;
      int var1 = this.stackSize - 1;
      this.stackSize = var1;
      Object var3 = var2[var1];
      var2[var1] = null;
      return var3;
   }

   private void push(Object var1) {
      int var2 = this.stackSize;
      Object[] var3 = this.stack;
      if (var2 == var3.length) {
         var2 *= 2;
         this.stack = Arrays.copyOf(var3, var2);
         this.pathIndices = Arrays.copyOf(this.pathIndices, var2);
         this.pathNames = Arrays.copyOf(this.pathNames, var2);
      }

      var3 = this.stack;
      var2 = this.stackSize++;
      var3[var2] = var1;
   }

   @Override
   public void beginArray() {
      this.expect(JsonToken.BEGIN_ARRAY);
      this.push(((JsonArray)this.peekStack()).iterator());
      this.pathIndices[this.stackSize - 1] = 0;
   }

   @Override
   public void beginObject() {
      this.expect(JsonToken.BEGIN_OBJECT);
      this.push(((JsonObject)this.peekStack()).entrySet().iterator());
   }

   @Override
   public void close() {
      this.stack = new Object[]{SENTINEL_CLOSED};
      this.stackSize = 1;
   }

   @Override
   public void endArray() {
      this.expect(JsonToken.END_ARRAY);
      this.popStack();
      this.popStack();
      int var1 = this.stackSize;
      if (var1 > 0) {
         int[] var2 = this.pathIndices;
         var1--;
         var2[var1]++;
      }
   }

   @Override
   public void endObject() {
      this.expect(JsonToken.END_OBJECT);
      this.pathNames[this.stackSize - 1] = null;
      this.popStack();
      this.popStack();
      int var1 = this.stackSize;
      if (var1 > 0) {
         int[] var2 = this.pathIndices;
         var1--;
         var2[var1]++;
      }
   }

   @Override
   public String getPath() {
      return this.getPath(false);
   }

   @Override
   public String getPreviousPath() {
      return this.getPath(true);
   }

   @Override
   public boolean hasNext() {
      JsonToken var2 = this.peek();
      boolean var1;
      if (var2 != JsonToken.END_OBJECT && var2 != JsonToken.END_ARRAY && var2 != JsonToken.END_DOCUMENT) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @Override
   public boolean nextBoolean() {
      this.expect(JsonToken.BOOLEAN);
      boolean var2 = ((JsonPrimitive)this.popStack()).getAsBoolean();
      int var1 = this.stackSize;
      if (var1 > 0) {
         int[] var3 = this.pathIndices;
         var1--;
         var3[var1]++;
      }

      return var2;
   }

   @Override
   public double nextDouble() {
      JsonToken var5 = this.peek();
      JsonToken var6 = JsonToken.NUMBER;
      if (var5 != var6 && var5 != JsonToken.STRING) {
         StringBuilder var9 = new StringBuilder("Expected ");
         var9.append(var6);
         var9.append(" but was ");
         var9.append(var5);
         var9.append(this.locationString());
         throw new IllegalStateException(var9.toString());
      } else {
         double var1 = ((JsonPrimitive)this.peekStack()).getAsDouble();
         if (this.isLenient() || !Double.isNaN(var1) && !Double.isInfinite(var1)) {
            this.popStack();
            int var3 = this.stackSize;
            if (var3 > 0) {
               int[] var8 = this.pathIndices;
               var3--;
               var8[var3]++;
            }

            return var1;
         } else {
            StringBuilder var4 = new StringBuilder("JSON forbids NaN and infinities: ");
            var4.append(var1);
            throw new MalformedJsonException(var4.toString());
         }
      }
   }

   @Override
   public int nextInt() {
      JsonToken var3 = this.peek();
      JsonToken var4 = JsonToken.NUMBER;
      if (var3 != var4 && var3 != JsonToken.STRING) {
         StringBuilder var5 = new StringBuilder("Expected ");
         var5.append(var4);
         var5.append(" but was ");
         var5.append(var3);
         var5.append(this.locationString());
         throw new IllegalStateException(var5.toString());
      } else {
         int var1 = ((JsonPrimitive)this.peekStack()).getAsInt();
         this.popStack();
         int var2 = this.stackSize;
         if (var2 > 0) {
            int[] var7 = this.pathIndices;
            var2--;
            var7[var2]++;
         }

         return var1;
      }
   }

   public JsonElement nextJsonElement() {
      JsonToken var2 = this.peek();
      if (var2 != JsonToken.NAME && var2 != JsonToken.END_ARRAY && var2 != JsonToken.END_OBJECT && var2 != JsonToken.END_DOCUMENT) {
         JsonElement var3 = (JsonElement)this.peekStack();
         this.skipValue();
         return var3;
      } else {
         StringBuilder var1 = new StringBuilder("Unexpected ");
         var1.append(var2);
         var1.append(" when reading a JsonElement.");
         throw new IllegalStateException(var1.toString());
      }
   }

   @Override
   public long nextLong() {
      JsonToken var5 = this.peek();
      JsonToken var6 = JsonToken.NUMBER;
      if (var5 != var6 && var5 != JsonToken.STRING) {
         StringBuilder var8 = new StringBuilder("Expected ");
         var8.append(var6);
         var8.append(" but was ");
         var8.append(var5);
         var8.append(this.locationString());
         throw new IllegalStateException(var8.toString());
      } else {
         long var2 = ((JsonPrimitive)this.peekStack()).getAsLong();
         this.popStack();
         int var1 = this.stackSize;
         if (var1 > 0) {
            int[] var4 = this.pathIndices;
            var1--;
            var4[var1]++;
         }

         return var2;
      }
   }

   @Override
   public String nextName() {
      return this.nextName(false);
   }

   @Override
   public void nextNull() {
      this.expect(JsonToken.NULL);
      this.popStack();
      int var1 = this.stackSize;
      if (var1 > 0) {
         int[] var2 = this.pathIndices;
         var1--;
         var2[var1]++;
      }
   }

   @Override
   public String nextString() {
      JsonToken var3 = this.peek();
      JsonToken var2 = JsonToken.STRING;
      if (var3 != var2 && var3 != JsonToken.NUMBER) {
         StringBuilder var4 = new StringBuilder("Expected ");
         var4.append(var2);
         var4.append(" but was ");
         var4.append(var3);
         var4.append(this.locationString());
         throw new IllegalStateException(var4.toString());
      } else {
         String var6 = ((JsonPrimitive)this.popStack()).getAsString();
         int var1 = this.stackSize;
         if (var1 > 0) {
            int[] var7 = this.pathIndices;
            var1--;
            var7[var1]++;
         }

         return var6;
      }
   }

   @Override
   public JsonToken peek() {
      if (this.stackSize == 0) {
         return JsonToken.END_DOCUMENT;
      } else {
         JsonToken var2 = (JsonToken)this.peekStack();
         if (var2 instanceof Iterator) {
            boolean var1 = this.stack[this.stackSize - 2] instanceof JsonObject;
            var2 = (Iterator)var2;
            if (var2.hasNext()) {
               if (var1) {
                  return JsonToken.NAME;
               } else {
                  this.push(var2.next());
                  return this.peek();
               }
            } else {
               if (var1) {
                  var2 = JsonToken.END_OBJECT;
               } else {
                  var2 = JsonToken.END_ARRAY;
               }

               return var2;
            }
         } else if (var2 instanceof JsonObject) {
            return JsonToken.BEGIN_OBJECT;
         } else if (var2 instanceof JsonArray) {
            return JsonToken.BEGIN_ARRAY;
         } else if (var2 instanceof JsonPrimitive) {
            JsonPrimitive var4 = (JsonPrimitive)var2;
            if (var4.isString()) {
               return JsonToken.STRING;
            } else if (var4.isBoolean()) {
               return JsonToken.BOOLEAN;
            } else if (var4.isNumber()) {
               return JsonToken.NUMBER;
            } else {
               throw new AssertionError();
            }
         } else if (var2 instanceof JsonNull) {
            return JsonToken.NULL;
         } else if (var2 == SENTINEL_CLOSED) {
            throw new IllegalStateException("JsonReader is closed");
         } else {
            StringBuilder var3 = new StringBuilder("Custom JsonElement subclass ");
            var3.append(var2.getClass().getName());
            var3.append(" is not supported");
            throw new MalformedJsonException(var3.toString());
         }
      }
   }

   public void promoteNameToValue() {
      this.expect(JsonToken.NAME);
      Entry var1 = (Entry)((Iterator)this.peekStack()).next();
      this.push(var1.getValue());
      this.push(new JsonPrimitive((String)var1.getKey()));
   }

   @Override
   public void skipValue() {
      JsonToken var2 = this.peek();
      int var1 = var2.ordinal();
      if (var1 != 1) {
         if (var1 != 2) {
            if (var1 != 3) {
               if (var1 != 4) {
                  this.popStack();
                  var1 = this.stackSize;
                  if (var1 > 0) {
                     int[] var5 = this.pathIndices;
                     var1--;
                     var5[var1]++;
                  }
               }
            } else {
               this.endObject();
            }
         } else {
            this.endArray();
         }
      } else {
         this.nextName(true);
      }
   }

   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("JsonTreeReader");
      var1.append(this.locationString());
      return var1.toString();
   }
}
