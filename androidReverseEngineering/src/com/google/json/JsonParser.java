package com.google.json;

import com.google.json.internal.Streams;
import com.google.json.stream.JsonReader;
import com.google.json.stream.JsonToken;
import com.google.json.stream.MalformedJsonException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public final class JsonParser {
   public static JsonElement parseReader(JsonReader var0) {
      boolean var1 = var0.isLenient();
      var0.setLenient(true);

      JsonElement var12;
      try {
         var12 = Streams.parse(var0);
      } catch (StackOverflowError var8) {
         StringBuilder var13 = new StringBuilder("Failed parsing JSON source: ");
         var13.append(var0);
         var13.append(" to Json");
         JsonParseException var11 = new JsonParseException(var13.toString(), var8);
         throw var11;
      } catch (OutOfMemoryError var9) {
         StringBuilder var2 = new StringBuilder("Failed parsing JSON source: ");
         var2.append(var0);
         var2.append(" to Json");
         JsonParseException var4 = new JsonParseException(var2.toString(), var9);
         throw var4;
      } finally {
         var0.setLenient(var1);
      }

      return var12;
   }

   public static JsonElement parseReader(Reader var0) {
      try {
         JsonReader var1 = new JsonReader(var0);
         JsonElement var5 = parseReader(var1);
         if (!var5.isJsonNull() && var1.peek() != JsonToken.END_DOCUMENT) {
            JsonSyntaxException var6 = new JsonSyntaxException("Did not consume the entire document.");
            throw var6;
         } else {
            return var5;
         }
      } catch (MalformedJsonException var2) {
         throw new JsonSyntaxException(var2);
      } catch (IOException var3) {
         throw new JsonIOException(var3);
      } catch (NumberFormatException var4) {
         throw new JsonSyntaxException(var4);
      }
   }

   public static JsonElement parseString(String var0) {
      return parseReader(new StringReader(var0));
   }

   @Deprecated
   public JsonElement parse(JsonReader var1) {
      return parseReader(var1);
   }

   @Deprecated
   public JsonElement parse(Reader var1) {
      return parseReader(var1);
   }

   @Deprecated
   public JsonElement parse(String var1) {
      return parseString(var1);
   }
}
