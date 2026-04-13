package com.google.json;

import com.google.json.internal.Streams;
import com.google.json.stream.JsonReader;
import com.google.json.stream.JsonToken;
import com.google.json.stream.MalformedJsonException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Iterator;
import java.util.NoSuchElementException;

public final class JsonStreamParser implements Iterator<JsonElement> {
   private final Object lock;
   private final JsonReader parser;

   public JsonStreamParser(Reader var1) {
      JsonReader var2 = new JsonReader(var1);
      this.parser = var2;
      var2.setLenient(true);
      this.lock = new Object();
   }

   public JsonStreamParser(String var1) {
      this(new StringReader(var1));
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public boolean hasNext() {
      Object var2 = this.lock;
      synchronized (var2){} // $VF: monitorenter 

      Throwable var10000;
      label207: {
         JsonToken var48;
         JsonToken var51;
         label202: {
            label201: {
               try {
                  try {
                     var48 = this.parser.peek();
                     var51 = JsonToken.END_DOCUMENT;
                     break label202;
                  } catch (MalformedJsonException var44) {
                     var50 = var44;
                  } catch (IOException var45) {
                     var4 = var45;
                     break label201;
                  }
               } catch (Throwable var46) {
                  var10000 = var46;
                  boolean var10001 = false;
                  break label207;
               }

               try {
                  JsonSyntaxException var47 = new JsonSyntaxException(var50);
                  throw var47;
               } catch (Throwable var42) {
                  var10000 = var42;
                  boolean var53 = false;
                  break label207;
               }
            }

            try {
               JsonIOException var3 = new JsonIOException(var4);
               throw var3;
            } catch (Throwable var41) {
               var10000 = var41;
               boolean var52 = false;
               break label207;
            }
         }

         boolean var1;
         if (var48 != var51) {
            var1 = true;
         } else {
            var1 = false;
         }

         label193:
         try {
            // $VF: monitorexit
            return var1;
         } catch (Throwable var43) {
            var10000 = var43;
            boolean var54 = false;
            break label193;
         }
      }

      while (true) {
         Throwable var49 = var10000;

         try {
            // $VF: monitorexit
            throw var49;
         } catch (Throwable var40) {
            var10000 = var40;
            boolean var55 = false;
            continue;
         }
      }
   }

   public JsonElement next() {
      if (this.hasNext()) {
         try {
            return Streams.parse(this.parser);
         } catch (StackOverflowError var2) {
            throw new JsonParseException("Failed parsing JSON source to Json", var2);
         } catch (OutOfMemoryError var3) {
            throw new JsonParseException("Failed parsing JSON source to Json", var3);
         }
      } else {
         throw new NoSuchElementException();
      }
   }

   @Override
   public void remove() {
      throw new UnsupportedOperationException();
   }
}
