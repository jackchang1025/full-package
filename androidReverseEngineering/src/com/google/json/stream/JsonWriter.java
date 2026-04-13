package com.google.json.stream;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Pattern;

public class JsonWriter implements Closeable, Flushable {
   private static final String[] HTML_SAFE_REPLACEMENT_CHARS;
   private static final String[] REPLACEMENT_CHARS = new String[128];
   private static final Pattern VALID_JSON_NUMBER_PATTERN = Pattern.compile("-?(?:0|[1-9][0-9]*)(?:\\.[0-9]+)?(?:[eE][-+]?[0-9]+)?");
   private String deferredName;
   private boolean htmlSafe;
   private String indent;
   private boolean lenient;
   private final Writer out;
   private String separator;
   private boolean serializeNulls;
   private int[] stack = new int[32];
   private int stackSize = 0;

   static {
      for (int var0 = 0; var0 <= 31; var0++) {
         REPLACEMENT_CHARS[var0] = String.format("\\u%04x", var0);
      }

      String[] var1 = REPLACEMENT_CHARS;
      var1[34] = "\\\"";
      var1[92] = "\\\\";
      var1[9] = "\\t";
      var1[8] = "\\b";
      var1[10] = "\\n";
      var1[13] = "\\r";
      var1[12] = "\\f";
      var1 = (String[])var1.clone();
      HTML_SAFE_REPLACEMENT_CHARS = var1;
      var1[60] = "\\u003c";
      var1[62] = "\\u003e";
      var1[38] = "\\u0026";
      var1[61] = "\\u003d";
      var1[39] = "\\u0027";
   }

   public JsonWriter(Writer var1) {
      this.push(6);
      this.separator = ":";
      this.serializeNulls = true;
      Objects.requireNonNull(var1, "out == null");
      this.out = var1;
   }

   private void beforeName() {
      int var1 = this.peek();
      if (var1 == 5) {
         this.out.write(44);
      } else if (var1 != 3) {
         throw new IllegalStateException("Nesting problem.");
      }

      this.newline();
      this.replaceTop(4);
   }

   private void beforeValue() {
      int var1 = this.peek();
      if (var1 != 1) {
         if (var1 != 2) {
            if (var1 != 4) {
               if (var1 != 6) {
                  if (var1 != 7) {
                     throw new IllegalStateException("Nesting problem.");
                  }

                  if (!this.lenient) {
                     throw new IllegalStateException("JSON must have only one top-level value.");
                  }
               }

               this.replaceTop(7);
            } else {
               this.out.append(this.separator);
               this.replaceTop(5);
            }

            return;
         }

         this.out.append(',');
      } else {
         this.replaceTop(2);
      }

      this.newline();
   }

   private JsonWriter close(int var1, int var2, char var3) {
      int var4 = this.peek();
      if (var4 != var2 && var4 != var1) {
         throw new IllegalStateException("Nesting problem.");
      } else if (this.deferredName == null) {
         this.stackSize--;
         if (var4 == var2) {
            this.newline();
         }

         this.out.write(var3);
         return this;
      } else {
         StringBuilder var5 = new StringBuilder("Dangling name: ");
         var5.append(this.deferredName);
         throw new IllegalStateException(var5.toString());
      }
   }

   private static boolean isTrustedNumberType(Class<? extends Number> var0) {
      boolean var1;
      if (var0 != Integer.class
         && var0 != Long.class
         && var0 != Double.class
         && var0 != Float.class
         && var0 != Byte.class
         && var0 != Short.class
         && var0 != BigDecimal.class
         && var0 != BigInteger.class
         && var0 != AtomicInteger.class
         && var0 != AtomicLong.class) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   private void newline() {
      if (this.indent != null) {
         this.out.write(10);
         int var2 = this.stackSize;

         for (int var1 = 1; var1 < var2; var1++) {
            this.out.write(this.indent);
         }
      }
   }

   private JsonWriter open(int var1, char var2) {
      this.beforeValue();
      this.push(var1);
      this.out.write(var2);
      return this;
   }

   private int peek() {
      int var1 = this.stackSize;
      if (var1 != 0) {
         return this.stack[var1 - 1];
      } else {
         throw new IllegalStateException("JsonWriter is closed.");
      }
   }

   private void push(int var1) {
      int var2 = this.stackSize;
      int[] var3 = this.stack;
      if (var2 == var3.length) {
         this.stack = Arrays.copyOf(var3, var2 * 2);
      }

      var3 = this.stack;
      var2 = this.stackSize++;
      var3[var2] = var1;
   }

   private void replaceTop(int var1) {
      this.stack[this.stackSize - 1] = var1;
   }

   private void string(String var1) {
      String[] var8;
      if (this.htmlSafe) {
         var8 = HTML_SAFE_REPLACEMENT_CHARS;
      } else {
         var8 = REPLACEMENT_CHARS;
      }

      this.out.write(34);
      int var5 = var1.length();
      int var4 = 0;
      int var2 = 0;

      while (var4 < var5) {
         int var3;
         label44: {
            char var6 = var1.charAt(var4);
            String var7;
            if (var6 < 128) {
               String var9 = var8[var6];
               var7 = var9;
               if (var9 == null) {
                  var3 = var2;
                  break label44;
               }
            } else if (var6 == 8232) {
               var7 = "\\u2028";
            } else {
               var3 = var2;
               if (var6 != 8233) {
                  break label44;
               }

               var7 = "\\u2029";
            }

            if (var2 < var4) {
               this.out.write(var1, var2, var4 - var2);
            }

            this.out.write(var7);
            var3 = var4 + 1;
         }

         var4++;
         var2 = var3;
      }

      if (var2 < var5) {
         this.out.write(var1, var2, var5 - var2);
      }

      this.out.write(34);
   }

   private void writeDeferredName() {
      if (this.deferredName != null) {
         this.beforeName();
         this.string(this.deferredName);
         this.deferredName = null;
      }
   }

   public JsonWriter beginArray() {
      this.writeDeferredName();
      return this.open(1, '[');
   }

   public JsonWriter beginObject() {
      this.writeDeferredName();
      return this.open(3, '{');
   }

   @Override
   public void close() {
      this.out.close();
      int var1 = this.stackSize;
      if (var1 > 1 || var1 == 1 && this.stack[var1 - 1] != 7) {
         throw new IOException("Incomplete document");
      } else {
         this.stackSize = 0;
      }
   }

   public JsonWriter endArray() {
      return this.close(1, 2, ']');
   }

   public JsonWriter endObject() {
      return this.close(3, 5, '}');
   }

   @Override
   public void flush() {
      if (this.stackSize != 0) {
         this.out.flush();
      } else {
         throw new IllegalStateException("JsonWriter is closed.");
      }
   }

   public final boolean getSerializeNulls() {
      return this.serializeNulls;
   }

   public final boolean isHtmlSafe() {
      return this.htmlSafe;
   }

   public boolean isLenient() {
      return this.lenient;
   }

   public JsonWriter jsonValue(String var1) {
      if (var1 == null) {
         return this.nullValue();
      } else {
         this.writeDeferredName();
         this.beforeValue();
         this.out.append(var1);
         return this;
      }
   }

   public JsonWriter name(String var1) {
      Objects.requireNonNull(var1, "name == null");
      if (this.deferredName == null) {
         if (this.stackSize != 0) {
            this.deferredName = var1;
            return this;
         } else {
            throw new IllegalStateException("JsonWriter is closed.");
         }
      } else {
         throw new IllegalStateException();
      }
   }

   public JsonWriter nullValue() {
      if (this.deferredName != null) {
         if (!this.serializeNulls) {
            this.deferredName = null;
            return this;
         }

         this.writeDeferredName();
      }

      this.beforeValue();
      this.out.write("null");
      return this;
   }

   public final void setHtmlSafe(boolean var1) {
      this.htmlSafe = var1;
   }

   public final void setIndent(String var1) {
      if (var1.length() == 0) {
         this.indent = null;
         var1 = ":";
      } else {
         this.indent = var1;
         var1 = ": ";
      }

      this.separator = var1;
   }

   public final void setLenient(boolean var1) {
      this.lenient = var1;
   }

   public final void setSerializeNulls(boolean var1) {
      this.serializeNulls = var1;
   }

   public JsonWriter value(double var1) {
      this.writeDeferredName();
      if (this.lenient || !Double.isNaN(var1) && !Double.isInfinite(var1)) {
         this.beforeValue();
         this.out.append(Double.toString(var1));
         return this;
      } else {
         StringBuilder var3 = new StringBuilder("Numeric values must be finite, but was ");
         var3.append(var1);
         throw new IllegalArgumentException(var3.toString());
      }
   }

   public JsonWriter value(float var1) {
      this.writeDeferredName();
      if (this.lenient || !Float.isNaN(var1) && !Float.isInfinite(var1)) {
         this.beforeValue();
         this.out.append(Float.toString(var1));
         return this;
      } else {
         StringBuilder var2 = new StringBuilder("Numeric values must be finite, but was ");
         var2.append(var1);
         throw new IllegalArgumentException(var2.toString());
      }
   }

   public JsonWriter value(long var1) {
      this.writeDeferredName();
      this.beforeValue();
      this.out.write(Long.toString(var1));
      return this;
   }

   public JsonWriter value(Boolean var1) {
      if (var1 == null) {
         return this.nullValue();
      } else {
         this.writeDeferredName();
         this.beforeValue();
         Writer var2 = this.out;
         String var3;
         if (var1) {
            var3 = "true";
         } else {
            var3 = "false";
         }

         var2.write(var3);
         return this;
      }
   }

   public JsonWriter value(Number var1) {
      if (var1 == null) {
         return this.nullValue();
      } else {
         this.writeDeferredName();
         String var2 = var1.toString();
         if (!var2.equals("-Infinity") && !var2.equals("Infinity") && !var2.equals("NaN")) {
            Class var3 = var1.getClass();
            if (!isTrustedNumberType(var3) && !VALID_JSON_NUMBER_PATTERN.matcher(var2).matches()) {
               StringBuilder var4 = new StringBuilder("String created by ");
               var4.append(var3);
               var4.append(" is not a valid JSON number: ");
               var4.append(var2);
               throw new IllegalArgumentException(var4.toString());
            }
         } else if (!this.lenient) {
            throw new IllegalArgumentException("Numeric values must be finite, but was ".concat(var2));
         }

         this.beforeValue();
         this.out.append(var2);
         return this;
      }
   }

   public JsonWriter value(String var1) {
      if (var1 == null) {
         return this.nullValue();
      } else {
         this.writeDeferredName();
         this.beforeValue();
         this.string(var1);
         return this;
      }
   }

   public JsonWriter value(boolean var1) {
      this.writeDeferredName();
      this.beforeValue();
      Writer var3 = this.out;
      String var2;
      if (var1) {
         var2 = "true";
      } else {
         var2 = "false";
      }

      var3.write(var2);
      return this;
   }
}
