package com.google.json.internal;

import com.google.json.JsonElement;
import com.google.json.internal.bind.TypeAdapters;
import com.google.json.stream.JsonReader;
import com.google.json.stream.JsonWriter;
import java.io.Writer;
import java.util.Objects;

public final class Streams {
   private Streams() {
      throw new UnsupportedOperationException();
   }

   public static JsonElement parse(JsonReader param0) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 00: aload 0
      // 01: invokevirtual com/google/json/stream/JsonReader.peek ()Lcom/google/json/stream/JsonToken;
      // 04: pop
      // 05: bipush 0
      // 06: istore 1
      // 07: getstatic com/google/json/internal/bind/TypeAdapters.JSON_ELEMENT Lcom/google/json/TypeAdapter;
      // 0a: aload 0
      // 0b: invokevirtual com/google/json/TypeAdapter.read (Lcom/google/json/stream/JsonReader;)Ljava/lang/Object;
      // 0e: checkcast com/google/json/JsonElement
      // 11: astore 0
      // 12: aload 0
      // 13: areturn
      // 14: astore 0
      // 15: goto 39
      // 18: astore 0
      // 19: new com/google/json/JsonSyntaxException
      // 1c: dup
      // 1d: aload 0
      // 1e: invokespecial com/google/json/JsonSyntaxException.<init> (Ljava/lang/Throwable;)V
      // 21: athrow
      // 22: astore 0
      // 23: new com/google/json/JsonIOException
      // 26: dup
      // 27: aload 0
      // 28: invokespecial com/google/json/JsonIOException.<init> (Ljava/lang/Throwable;)V
      // 2b: athrow
      // 2c: astore 0
      // 2d: new com/google/json/JsonSyntaxException
      // 30: dup
      // 31: aload 0
      // 32: invokespecial com/google/json/JsonSyntaxException.<init> (Ljava/lang/Throwable;)V
      // 35: athrow
      // 36: astore 0
      // 37: bipush 1
      // 38: istore 1
      // 39: iload 1
      // 3a: ifeq 41
      // 3d: getstatic com/google/json/JsonNull.INSTANCE Lcom/google/json/JsonNull;
      // 40: areturn
      // 41: new com/google/json/JsonSyntaxException
      // 44: dup
      // 45: aload 0
      // 46: invokespecial com/google/json/JsonSyntaxException.<init> (Ljava/lang/Throwable;)V
      // 49: athrow
   }

   public static void write(JsonElement var0, JsonWriter var1) {
      TypeAdapters.JSON_ELEMENT.write(var1, var0);
   }

   public static Writer writerForAppendable(Appendable var0) {
      if (var0 instanceof Writer) {
         var0 = var0;
      } else {
         var0 = new Streams.AppendableWriter(var0);
      }

      return var0;
   }

   public static final class AppendableWriter extends Writer {
      private final Appendable appendable;
      private final Streams.AppendableWriter.CurrentWrite currentWrite = new Streams.AppendableWriter.CurrentWrite();

      public AppendableWriter(Appendable var1) {
         this.appendable = var1;
      }

      @Override
      public Writer append(CharSequence var1) {
         this.appendable.append(var1);
         return this;
      }

      @Override
      public Writer append(CharSequence var1, int var2, int var3) {
         this.appendable.append(var1, var2, var3);
         return this;
      }

      @Override
      public void close() {
      }

      @Override
      public void flush() {
      }

      @Override
      public void write(int var1) {
         this.appendable.append((char)var1);
      }

      @Override
      public void write(String var1, int var2, int var3) {
         Objects.requireNonNull(var1);
         this.appendable.append(var1, var2, var3 + var2);
      }

      @Override
      public void write(char[] var1, int var2, int var3) {
         this.currentWrite.setChars(var1);
         this.appendable.append(this.currentWrite, var2, var3 + var2);
      }

      public static class CurrentWrite implements CharSequence {
         private String cachedString;
         private char[] chars;

         private CurrentWrite() {
         }

         @Override
         public char charAt(int var1) {
            return this.chars[var1];
         }

         @Override
         public int length() {
            return this.chars.length;
         }

         public void setChars(char[] var1) {
            this.chars = var1;
            this.cachedString = null;
         }

         @Override
         public CharSequence subSequence(int var1, int var2) {
            return new String(this.chars, var1, var2 - var1);
         }

         @Override
         public String toString() {
            if (this.cachedString == null) {
               this.cachedString = new String(this.chars);
            }

            return this.cachedString;
         }
      }
   }
}
