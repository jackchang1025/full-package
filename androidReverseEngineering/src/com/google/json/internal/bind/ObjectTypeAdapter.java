package com.google.json.internal.bind;

import com.google.json.Gson;
import com.google.json.ToNumberPolicy;
import com.google.json.ToNumberStrategy;
import com.google.json.TypeAdapter;
import com.google.json.TypeAdapterFactory;
import com.google.json.internal.LinkedTreeMap;
import com.google.json.reflect.TypeToken;
import com.google.json.stream.JsonReader;
import com.google.json.stream.JsonToken;
import com.google.json.stream.JsonWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class ObjectTypeAdapter extends TypeAdapter<Object> {
   private static final TypeAdapterFactory DOUBLE_FACTORY = newFactory(ToNumberPolicy.DOUBLE);
   private final Gson gson;
   private final ToNumberStrategy toNumberStrategy;

   private ObjectTypeAdapter(Gson var1, ToNumberStrategy var2) {
      this.gson = var1;
      this.toNumberStrategy = var2;
   }

   public static TypeAdapterFactory getFactory(ToNumberStrategy var0) {
      return var0 == ToNumberPolicy.DOUBLE ? DOUBLE_FACTORY : newFactory(var0);
   }

   private static TypeAdapterFactory newFactory(ToNumberStrategy var0) {
      return new TypeAdapterFactory(var0) {
         final ToNumberStrategy val$toNumberStrategy;

         {
            this.val$toNumberStrategy = var1;
         }

         @Override
         public <T> TypeAdapter<T> create(Gson var1, TypeToken<T> var2) {
            return var2.getRawType() == Object.class ? new ObjectTypeAdapter(var1, this.val$toNumberStrategy) : null;
         }
      };
   }

   private Object readTerminal(JsonReader var1, JsonToken var2) {
      int var3 = var2.ordinal();
      if (var3 != 3) {
         if (var3 != 4) {
            if (var3 != 5) {
               if (var3 == 6) {
                  var1.nextNull();
                  return null;
               } else {
                  StringBuilder var4 = new StringBuilder("Unexpected token: ");
                  var4.append(var2);
                  throw new IllegalStateException(var4.toString());
               }
            } else {
               return var1.nextBoolean();
            }
         } else {
            return this.toNumberStrategy.readNumber(var1);
         }
      } else {
         return var1.nextString();
      }
   }

   private Object tryBeginNesting(JsonReader var1, JsonToken var2) {
      int var3 = var2.ordinal();
      if (var3 != 1) {
         if (var3 != 2) {
            return null;
         } else {
            var1.beginObject();
            return new LinkedTreeMap();
         }
      } else {
         var1.beginArray();
         return new ArrayList();
      }
   }

   @Override
   public Object read(JsonReader var1) {
      JsonToken var4 = var1.peek();
      Object var3 = this.tryBeginNesting(var1, var4);
      if (var3 == null) {
         return this.readTerminal(var1, var4);
      } else {
         ArrayDeque var7 = new ArrayDeque();

         while (true) {
            while (!var1.hasNext()) {
               if (var3 instanceof List) {
                  var1.endArray();
               } else {
                  var1.endObject();
               }

               if (var7.isEmpty()) {
                  return var3;
               }

               var3 = var7.removeLast();
            }

            String var5;
            if (var3 instanceof Map) {
               var5 = var1.nextName();
            } else {
               var5 = null;
            }

            JsonToken var8 = var1.peek();
            Object var6 = this.tryBeginNesting(var1, var8);
            boolean var2;
            if (var6 != null) {
               var2 = true;
            } else {
               var2 = false;
            }

            var4 = (JsonToken)var6;
            if (var6 == null) {
               var4 = (JsonToken)this.readTerminal(var1, var8);
            }

            if (var3 instanceof List) {
               ((List)var3).add(var4);
            } else {
               ((Map)var3).put(var5, var4);
            }

            if (var2) {
               var7.addLast(var3);
               var3 = var4;
            }
         }
      }
   }

   @Override
   public void write(JsonWriter var1, Object var2) {
      if (var2 == null) {
         var1.nullValue();
      } else {
         TypeAdapter var3 = this.gson.getAdapter(var2.getClass());
         if (var3 instanceof ObjectTypeAdapter) {
            var1.beginObject();
            var1.endObject();
         } else {
            var3.write(var1, var2);
         }
      }
   }
}
