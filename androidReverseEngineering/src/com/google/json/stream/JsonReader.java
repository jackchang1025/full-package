package com.google.json.stream;

import a.a;
import com.google.json.internal.JsonReaderInternalAccess;
import com.google.json.internal.bind.JsonTreeReader;
import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.Objects;

public class JsonReader implements Closeable {
   static final int BUFFER_SIZE = 1024;
   private static final long MIN_INCOMPLETE_INTEGER = -922337203685477580L;
   private static final int NUMBER_CHAR_DECIMAL = 3;
   private static final int NUMBER_CHAR_DIGIT = 2;
   private static final int NUMBER_CHAR_EXP_DIGIT = 7;
   private static final int NUMBER_CHAR_EXP_E = 5;
   private static final int NUMBER_CHAR_EXP_SIGN = 6;
   private static final int NUMBER_CHAR_FRACTION_DIGIT = 4;
   private static final int NUMBER_CHAR_NONE = 0;
   private static final int NUMBER_CHAR_SIGN = 1;
   private static final int PEEKED_BEGIN_ARRAY = 3;
   private static final int PEEKED_BEGIN_OBJECT = 1;
   private static final int PEEKED_BUFFERED = 11;
   private static final int PEEKED_DOUBLE_QUOTED = 9;
   private static final int PEEKED_DOUBLE_QUOTED_NAME = 13;
   private static final int PEEKED_END_ARRAY = 4;
   private static final int PEEKED_END_OBJECT = 2;
   private static final int PEEKED_EOF = 17;
   private static final int PEEKED_FALSE = 6;
   private static final int PEEKED_LONG = 15;
   private static final int PEEKED_NONE = 0;
   private static final int PEEKED_NULL = 7;
   private static final int PEEKED_NUMBER = 16;
   private static final int PEEKED_SINGLE_QUOTED = 8;
   private static final int PEEKED_SINGLE_QUOTED_NAME = 12;
   private static final int PEEKED_TRUE = 5;
   private static final int PEEKED_UNQUOTED = 10;
   private static final int PEEKED_UNQUOTED_NAME = 14;
   private final char[] buffer;
   private final Reader in;
   private boolean lenient = false;
   private int limit;
   private int lineNumber;
   private int lineStart;
   private int[] pathIndices;
   private String[] pathNames;
   int peeked;
   private long peekedLong;
   private int peekedNumberLength;
   private String peekedString;
   private int pos;
   private int[] stack;
   private int stackSize;

   static {
      JsonReaderInternalAccess.INSTANCE = new JsonReaderInternalAccess() {
         @Override
         public void promoteNameToValue(JsonReader var1) {
            if (var1 instanceof JsonTreeReader) {
               ((JsonTreeReader)var1).promoteNameToValue();
            } else {
               int var3 = var1.peeked;
               int var2 = var3;
               if (var3 == 0) {
                  var2 = var1.doPeek();
               }

               byte var5;
               if (var2 == 13) {
                  var5 = 9;
               } else if (var2 == 12) {
                  var5 = 8;
               } else {
                  if (var2 != 14) {
                     StringBuilder var4 = new StringBuilder("Expected a name but was ");
                     var4.append(var1.peek());
                     var4.append(var1.locationString());
                     throw new IllegalStateException(var4.toString());
                  }

                  var5 = 10;
               }

               var1.peeked = var5;
            }
         }
      };
   }

   public JsonReader(Reader var1) {
      this.buffer = new char[1024];
      this.pos = 0;
      this.limit = 0;
      this.lineNumber = 0;
      this.lineStart = 0;
      this.peeked = 0;
      int[] var2 = new int[32];
      this.stack = var2;
      this.stackSize = 0 + 1;
      var2[0] = 6;
      this.pathNames = new String[32];
      this.pathIndices = new int[32];
      Objects.requireNonNull(var1, "in == null");
      this.in = var1;
   }

   private void checkLenient() {
      if (!this.lenient) {
         throw this.syntaxError("Use JsonReader.setLenient(true) to accept malformed JSON");
      }
   }

   private void consumeNonExecutePrefix() {
      this.nextNonWhitespace(true);
      int var1 = this.pos - 1;
      this.pos = var1;
      if (var1 + 5 <= this.limit || this.fillBuffer(5)) {
         var1 = this.pos;
         char[] var2 = this.buffer;
         if (var2[var1] == ')' && var2[var1 + 1] == ']' && var2[var1 + 2] == '}' && var2[var1 + 3] == '\'' && var2[var1 + 4] == '\n') {
            this.pos = var1 + 5;
         }
      }
   }

   private boolean fillBuffer(int var1) {
      char[] var5 = this.buffer;
      int var3 = this.lineStart;
      int var2 = this.pos;
      this.lineStart = var3 - var2;
      var3 = this.limit;
      if (var3 != var2) {
         var3 -= var2;
         this.limit = var3;
         System.arraycopy(var5, var2, var5, 0, var3);
      } else {
         this.limit = 0;
      }

      this.pos = 0;

      do {
         Reader var6 = this.in;
         var2 = this.limit;
         var2 = var6.read(var5, var2, var5.length - var2);
         if (var2 == -1) {
            return false;
         }

         var3 = this.limit + var2;
         this.limit = var3;
         var2 = var1;
         if (this.lineNumber == 0) {
            int var4 = this.lineStart;
            var2 = var1;
            if (var4 == 0) {
               var2 = var1;
               if (var3 > 0) {
                  var2 = var1;
                  if (var5[0] == '\ufeff') {
                     this.pos++;
                     this.lineStart = var4 + 1;
                     var2 = var1 + 1;
                  }
               }
            }
         }

         var1 = var2;
      } while (var3 < var2);

      return true;
   }

   private String getPath(boolean var1) {
      StringBuilder var7 = new StringBuilder("$");
      int var2 = 0;

      while (true) {
         int var5 = this.stackSize;
         if (var2 >= var5) {
            return var7.toString();
         }

         int var3 = this.stack[var2];
         if (var3 == 1 || var3 == 2) {
            int var4 = this.pathIndices[var2];
            var3 = var4;
            if (var1) {
               var3 = var4;
               if (var4 > 0) {
                  var3 = var4;
                  if (var2 == var5 - 1) {
                     var3 = var4 - 1;
                  }
               }
            }

            var7.append('[');
            var7.append(var3);
            var7.append(']');
         } else if (var3 == 3 || var3 == 4 || var3 == 5) {
            var7.append('.');
            String var6 = this.pathNames[var2];
            if (var6 != null) {
               var7.append(var6);
            }
         }

         var2++;
      }
   }

   private boolean isLiteral(char var1) {
      if (var1 != '\t' && var1 != '\n' && var1 != '\f' && var1 != '\r' && var1 != ' ') {
         if (var1 != '#') {
            if (var1 == ',') {
               return false;
            }

            if (var1 != '/' && var1 != '=') {
               if (var1 == '{' || var1 == '}' || var1 == ':') {
                  return false;
               }

               if (var1 != ';') {
                  switch (var1) {
                     case '[':
                     case ']':
                        return false;
                     case '\\':
                        break;
                     default:
                        return true;
                  }
               }
            }
         }

         this.checkLenient();
      }

      return false;
   }

   private int nextNonWhitespace(boolean var1) {
      char[] var7 = this.buffer;

      while (true) {
         int var2 = this.pos;

         label64:
         while (true) {
            int var5 = this.limit;

            while (true) {
               int var4 = var2;
               int var3 = var5;
               if (var2 == var5) {
                  this.pos = var2;
                  if (!this.fillBuffer(1)) {
                     if (!var1) {
                        return -1;
                     }

                     StringBuilder var11 = new StringBuilder("End of input");
                     var11.append(this.locationString());
                     throw new EOFException(var11.toString());
                  }

                  var4 = this.pos;
                  var3 = this.limit;
               }

               var2 = var4 + 1;
               char var10 = var7[var4];
               if (var10 == '\n') {
                  this.lineNumber++;
                  this.lineStart = var2;
               } else if (var10 != ' ' && var10 != '\r' && var10 != '\t') {
                  if (var10 != '/') {
                     this.pos = var2;
                     if (var10 != '#') {
                        return var10;
                     }

                     this.checkLenient();
                     break label64;
                  }

                  this.pos = var2;
                  if (var2 == var3) {
                     this.pos = var2 - 1;
                     boolean var6 = this.fillBuffer(2);
                     this.pos++;
                     if (!var6) {
                        return var10;
                     }
                  }

                  this.checkLenient();
                  var3 = this.pos;
                  char var8 = var7[var3];
                  if (var8 != '*') {
                     if (var8 != '/') {
                        return var10;
                     }

                     this.pos = var3 + 1;
                     break label64;
                  }

                  this.pos = var3 + 1;
                  if (!this.skipTo("*/")) {
                     throw this.syntaxError("Unterminated comment");
                  }

                  var2 = this.pos + 2;
                  break;
               }

               var5 = var3;
            }
         }

         this.skipToEndOfLine();
      }
   }

   private String nextQuotedValue(char var1) {
      char[] var8 = this.buffer;
      StringBuilder var6 = null;

      while (true) {
         int var3 = this.pos;
         int var5 = this.limit;
         int var2 = var3;

         while (true) {
            int var4 = var2;
            if (var2 >= var5) {
               StringBuilder var12 = var6;
               if (var6 == null) {
                  var12 = new StringBuilder(Math.max((var2 - var3) * 2, 16));
               }

               var12.append(var8, var3, var2 - var3);
               this.pos = var2;
               if (!this.fillBuffer(1)) {
                  throw this.syntaxError("Unterminated string");
               }

               var6 = var12;
               break;
            }

            var2++;
            char var11 = var8[var4];
            if (var11 == var1) {
               this.pos = var2;
               var1 = var2 - var3 - 1;
               if (var6 == null) {
                  return new String(var8, var3, var1);
               }

               var6.append(var8, var3, var1);
               return var6.toString();
            }

            if (var11 == '\\') {
               this.pos = var2;
               var2 = var2 - var3 - 1;
               StringBuilder var7 = var6;
               if (var6 == null) {
                  var7 = new StringBuilder(Math.max((var2 + 1) * 2, 16));
               }

               var7.append(var8, var3, var2);
               var7.append(this.readEscapeCharacter());
               var6 = var7;
               break;
            }

            if (var11 == '\n') {
               this.lineNumber++;
               this.lineStart = var2;
            }
         }
      }
   }

   private String nextUnquotedValue() {
      StringBuilder var4 = null;
      byte var2 = 0;

      int var1;
      label83:
      while (true) {
         var1 = 0;

         label80:
         while (true) {
            int var3 = this.pos;
            if (var3 + var1 < this.limit) {
               char var6 = this.buffer[var3 + var1];
               if (var6 == '\t' || var6 == '\n' || var6 == '\f' || var6 == '\r' || var6 == ' ') {
                  break label83;
               }

               if (var6 == '#') {
                  break;
               }

               if (var6 == ',') {
                  break label83;
               }

               if (var6 == '/' || var6 == '=') {
                  break;
               }

               if (var6 == '{' || var6 == '}' || var6 == ':') {
                  break label83;
               }

               if (var6 == ';') {
                  break;
               }

               switch (var6) {
                  case '[':
                  case ']':
                     break label83;
                  case '\\':
                     break label80;
                  default:
                     var1++;
               }
            } else {
               if (var1 >= this.buffer.length) {
                  StringBuilder var5 = var4;
                  if (var4 == null) {
                     var5 = new StringBuilder(Math.max(var1, 16));
                  }

                  var5.append(this.buffer, this.pos, var1);
                  this.pos += var1;
                  var4 = var5;
                  if (this.fillBuffer(1)) {
                     continue label83;
                  }

                  var1 = var2;
                  var4 = var5;
                  break label83;
               }

               if (!this.fillBuffer(var1 + 1)) {
                  break label83;
               }
            }
         }

         this.checkLenient();
         break;
      }

      String var7;
      if (var4 == null) {
         var7 = new String(this.buffer, this.pos, var1);
      } else {
         var4.append(this.buffer, this.pos, var1);
         var7 = var4.toString();
      }

      this.pos += var1;
      return var7;
   }

   private int peekKeyword() {
      char var1 = this.buffer[this.pos];
      String var5;
      String var6;
      byte var7;
      if (var1 == 't' || var1 == 'T') {
         var5 = "true";
         var6 = "TRUE";
         var7 = 5;
      } else if (var1 != 'f' && var1 != 'F') {
         if (var1 != 'n' && var1 != 'N') {
            return 0;
         }

         var5 = "null";
         var6 = "NULL";
         var7 = 7;
      } else {
         var5 = "false";
         var6 = "FALSE";
         var7 = 6;
      }

      int var3 = var5.length();

      for (int var2 = 1; var2 < var3; var2++) {
         if (this.pos + var2 >= this.limit && !this.fillBuffer(var2 + 1)) {
            return 0;
         }

         char var4 = this.buffer[this.pos + var2];
         if (var4 != var5.charAt(var2) && var4 != var6.charAt(var2)) {
            return 0;
         }
      }

      if ((this.pos + var3 < this.limit || this.fillBuffer(var3 + 1)) && this.isLiteral(this.buffer[this.pos + var3])) {
         return 0;
      } else {
         this.pos += var3;
         this.peeked = var7;
         return var7;
      }
   }

   private int peekNumber() {
      char[] var14 = this.buffer;
      int var5 = this.pos;
      int var4 = this.limit;
      int var7 = 0;
      byte var2 = 0;
      byte var6 = var2;
      boolean var3 = true;
      long var10 = 0L;

      while (true) {
         int var9 = var5;
         int var8 = var4;
         if (var5 + var7 == var4) {
            if (var7 == var14.length) {
               return 0;
            }

            if (!this.fillBuffer(var7 + 1)) {
               break;
            }

            var9 = this.pos;
            var8 = this.limit;
         }

         char var1 = var14[var9 + var7];
         if (var1 != '+') {
            if (var1 != 'E' && var1 != 'e') {
               if (var1 != '-') {
                  int var18 = 3;
                  label143:
                  if (var1 != '.') {
                     if (var1 < '0' || var1 > '9') {
                        if (this.isLiteral(var1)) {
                           return 0;
                        }
                        break;
                     }

                     label177: {
                        long var12;
                        boolean var22;
                        if (var2 == 1 || var2 == 0) {
                           var12 = (long)(-(var1 - '0'));
                           var18 = (byte)2;
                           var22 = var3;
                        } else if (var2 == 2) {
                           if (var10 == 0L) {
                              return 0;
                           }

                           var12 = 10L * var10 - (long)(var1 - '0');
                           long var23;
                           var18 = (var23 = var10 - -922337203685477580L) == 0L ? 0 : (var23 < 0L ? -1 : 1);
                           boolean var20;
                           if (var18 > 0 || var18 == 0 && var12 < var10) {
                              var20 = true;
                           } else {
                              var20 = false;
                           }

                           var22 = var3 & var20;
                           var18 = var2;
                        } else {
                           if (var2 == 3) {
                              var2 = 4;
                              break label143;
                           }

                           if (var2 == 5) {
                              break label177;
                           }

                           var18 = var2;
                           var22 = var3;
                           var12 = var10;
                           if (var2 == 6) {
                              break label177;
                           }
                        }

                        var10 = var12;
                        var3 = var22;
                        var2 = var18;
                        break label143;
                     }

                     var2 = 7;
                  } else {
                     if (var2 != 2) {
                        return 0;
                     }

                     var2 = (byte)var18;
                  }
               } else {
                  byte var17 = 6;
                  if (var2 == 0) {
                     var2 = 1;
                     var6 = 1;
                  } else {
                     if (var2 != 5) {
                        return 0;
                     }

                     var2 = var17;
                  }
               }
            } else {
               if (var2 != 2 && var2 != 4) {
                  return 0;
               }

               var2 = 5;
            }
         } else {
            byte var16 = 6;
            if (var2 != 5) {
               return 0;
            }

            var2 = var16;
         }

         var7++;
         var5 = var9;
         var4 = var8;
      }

      if (var2 == 2 && var3 && (var10 != Long.MIN_VALUE || var6 != 0) && (var10 != 0L || var6 == 0)) {
         if (var6 == 0) {
            var10 = -var10;
         }

         this.peekedLong = var10;
         this.pos += var7;
         var2 = 15;
      } else {
         if (var2 != 2 && var2 != 4 && var2 != 7) {
            return 0;
         }

         this.peekedNumberLength = var7;
         var2 = 16;
      }

      this.peeked = var2;
      return var2;
   }

   private void push(int var1) {
      int var2 = this.stackSize;
      int[] var3 = this.stack;
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

   private char readEscapeCharacter() {
      if (this.pos == this.limit && !this.fillBuffer(1)) {
         throw this.syntaxError("Unterminated escape sequence");
      } else {
         char[] var6 = this.buffer;
         int var2 = this.pos;
         int var3 = var2 + 1;
         this.pos = var3;
         char var1 = var6[var2];
         if (var1 != '\n') {
            if (var1 != '"' && var1 != '\'' && var1 != '/' && var1 != '\\') {
               if (var1 == 'b') {
                  return '\b';
               }

               if (var1 == 'f') {
                  return '\f';
               }

               if (var1 == 'n') {
                  return '\n';
               }

               if (var1 == 'r') {
                  return '\r';
               }

               if (var1 == 't') {
                  return '\t';
               }

               if (var1 != 'u') {
                  throw this.syntaxError("Invalid escape sequence");
               }

               if (var3 + 4 > this.limit && !this.fillBuffer(4)) {
                  throw this.syntaxError("Unterminated escape sequence");
               }

               var3 = this.pos;
               var1 = '\u0000';

               for (int var8 = var3; var8 < var3 + 4; var8 += 1) {
                  int var9 = this.buffer[var8];
                  char var5 = (char)(var1 << 4);
                  if (var9 >= 48 && var9 <= 57) {
                     var9 -= 48;
                  } else {
                     if (var9 >= 97 && var9 <= 102) {
                        var9 -= 97;
                     } else {
                        if (var9 < 65 || var9 > 70) {
                           throw new NumberFormatException("\\u".concat(new String(this.buffer, this.pos, 4)));
                        }

                        var9 -= 65;
                     }

                     var9 += 10;
                  }

                  var1 = (char)(var9 + var5);
               }

               this.pos += 4;
               return var1;
            }
         } else {
            this.lineNumber++;
            this.lineStart = var3;
         }

         return var1;
      }
   }

   private void skipQuotedValue(char var1) {
      char[] var5 = this.buffer;

      label32:
      while (true) {
         int var2 = this.pos;
         int var4 = this.limit;

         while (var2 < var4) {
            int var3 = var2 + 1;
            char var6 = var5[var2];
            if (var6 == var1) {
               this.pos = var3;
               return;
            }

            if (var6 == '\\') {
               this.pos = var3;
               this.readEscapeCharacter();
               continue label32;
            }

            if (var6 == '\n') {
               this.lineNumber++;
               this.lineStart = var3;
            }

            var2 = var3;
         }

         this.pos = var2;
         if (!this.fillBuffer(1)) {
            throw this.syntaxError("Unterminated string");
         }
      }
   }

   private boolean skipTo(String var1) {
      int var3 = var1.length();

      while (true) {
         int var5 = this.pos;
         int var4 = this.limit;
         int var2 = 0;
         if (var5 + var3 > var4 && !this.fillBuffer(var3)) {
            return false;
         }

         char[] var6 = this.buffer;
         var4 = this.pos;
         if (var6[var4] == '\n') {
            this.lineNumber++;
            this.lineStart = var4 + 1;
         } else {
            while (true) {
               if (var2 >= var3) {
                  return true;
               }

               if (this.buffer[this.pos + var2] != var1.charAt(var2)) {
                  break;
               }

               var2++;
            }
         }

         this.pos++;
      }
   }

   private void skipToEndOfLine() {
      while (this.pos < this.limit || this.fillBuffer(1)) {
         char[] var3 = this.buffer;
         int var2 = this.pos;
         int var1 = var2 + 1;
         this.pos = var1;
         char var4 = var3[var2];
         if (var4 == '\n') {
            this.lineNumber++;
            this.lineStart = var1;
         } else if (var4 != '\r') {
            continue;
         }
         break;
      }
   }

   private void skipUnquotedValue() {
      label59:
      while (true) {
         int var1 = 0;

         label56:
         while (true) {
            int var2 = this.pos;
            if (var2 + var1 >= this.limit) {
               this.pos = var2 + var1;
               if (this.fillBuffer(1)) {
                  continue label59;
               }

               return;
            }

            char var3 = this.buffer[var2 + var1];
            if (var3 == '\t' || var3 == '\n' || var3 == '\f' || var3 == '\r' || var3 == ' ') {
               break;
            }

            if (var3 != '#') {
               if (var3 == ',') {
                  break;
               }

               if (var3 != '/' && var3 != '=') {
                  if (var3 == '{' || var3 == '}' || var3 == ':') {
                     break;
                  }

                  if (var3 != ';') {
                     switch (var3) {
                        case '[':
                        case ']':
                           break label56;
                        case '\\':
                           break;
                        default:
                           var1++;
                           continue;
                     }
                  }
               }
            }

            this.checkLenient();
            break;
         }

         this.pos += var1;
         return;
      }
   }

   private IOException syntaxError(String var1) {
      StringBuilder var2 = a.p(var1);
      var2.append(this.locationString());
      throw new MalformedJsonException(var2.toString());
   }

   public void beginArray() {
      int var2 = this.peeked;
      int var1 = var2;
      if (var2 == 0) {
         var1 = this.doPeek();
      }

      if (var1 == 3) {
         this.push(1);
         this.pathIndices[this.stackSize - 1] = 0;
         this.peeked = 0;
      } else {
         StringBuilder var3 = new StringBuilder("Expected BEGIN_ARRAY but was ");
         var3.append(this.peek());
         var3.append(this.locationString());
         throw new IllegalStateException(var3.toString());
      }
   }

   public void beginObject() {
      int var2 = this.peeked;
      int var1 = var2;
      if (var2 == 0) {
         var1 = this.doPeek();
      }

      if (var1 == 1) {
         this.push(3);
         this.peeked = 0;
      } else {
         StringBuilder var3 = new StringBuilder("Expected BEGIN_OBJECT but was ");
         var3.append(this.peek());
         var3.append(this.locationString());
         throw new IllegalStateException(var3.toString());
      }
   }

   @Override
   public void close() {
      this.peeked = 0;
      this.stack[0] = 8;
      this.stackSize = 1;
      this.in.close();
   }

   public int doPeek() {
      int var4;
      label149: {
         int[] var3 = this.stack;
         int var2 = this.stackSize;
         var4 = var3[var2 - 1];
         if (var4 == 1) {
            var3[var2 - 1] = 2;
         } else if (var4 == 2) {
            var2 = this.nextNonWhitespace(true);
            if (var2 != 44) {
               if (var2 != 59) {
                  if (var2 == 93) {
                     this.peeked = 4;
                     return 4;
                  }

                  throw this.syntaxError("Unterminated array");
               }

               this.checkLenient();
            }
         } else {
            if (var4 == 3 || var4 == 5) {
               var3[var2 - 1] = 4;
               if (var4 == 5) {
                  var2 = this.nextNonWhitespace(true);
                  if (var2 != 44) {
                     if (var2 != 59) {
                        if (var2 == 125) {
                           this.peeked = 2;
                           return 2;
                        }

                        throw this.syntaxError("Unterminated object");
                     }

                     this.checkLenient();
                  }
               }

               var2 = this.nextNonWhitespace(true);
               if (var2 != 34) {
                  if (var2 != 39) {
                     if (var2 == 125) {
                        if (var4 != 5) {
                           this.peeked = 2;
                           return 2;
                        }

                        throw this.syntaxError("Expected name");
                     }

                     this.checkLenient();
                     this.pos--;
                     if (!this.isLiteral((char)var2)) {
                        throw this.syntaxError("Expected name");
                     }

                     var4 = 14;
                  } else {
                     this.checkLenient();
                     var4 = 12;
                  }
               } else {
                  var4 = 13;
               }
               break label149;
            }

            if (var4 == 4) {
               var3[var2 - 1] = 5;
               var2 = this.nextNonWhitespace(true);
               if (var2 != 58) {
                  if (var2 != 61) {
                     throw this.syntaxError("Expected ':'");
                  }

                  this.checkLenient();
                  if (this.pos < this.limit || this.fillBuffer(1)) {
                     char[] var13 = this.buffer;
                     var2 = this.pos;
                     if (var13[var2] == '>') {
                        this.pos = var2 + 1;
                     }
                  }
               }
            } else if (var4 == 6) {
               if (this.lenient) {
                  this.consumeNonExecutePrefix();
               }

               this.stack[this.stackSize - 1] = 7;
            } else if (var4 == 7) {
               if (this.nextNonWhitespace(false) == -1) {
                  var4 = 17;
                  break label149;
               }

               this.checkLenient();
               this.pos--;
            } else if (var4 == 8) {
               throw new IllegalStateException("JsonReader is closed");
            }
         }

         var2 = this.nextNonWhitespace(true);
         if (var2 != 34) {
            if (var2 == 39) {
               this.checkLenient();
               this.peeked = 8;
               return 8;
            }

            if (var2 != 44 && var2 != 59) {
               if (var2 == 91) {
                  this.peeked = 3;
                  return 3;
               }

               if (var2 != 93) {
                  if (var2 == 123) {
                     this.peeked = 1;
                     return 1;
                  }

                  this.pos--;
                  var4 = this.peekKeyword();
                  if (var4 != 0) {
                     return var4;
                  }

                  var4 = this.peekNumber();
                  if (var4 != 0) {
                     return var4;
                  }

                  if (!this.isLiteral(this.buffer[this.pos])) {
                     throw this.syntaxError("Expected value");
                  }

                  this.checkLenient();
                  var4 = 10;
                  break label149;
               }

               if (var4 == 1) {
                  this.peeked = 4;
                  return 4;
               }
            }

            if (var4 != 1 && var4 != 2) {
               throw this.syntaxError("Unexpected value");
            }

            this.checkLenient();
            this.pos--;
            this.peeked = 7;
            return 7;
         } else {
            var4 = 9;
         }
      }

      this.peeked = var4;
      return var4;
   }

   public void endArray() {
      int var2 = this.peeked;
      int var1 = var2;
      if (var2 == 0) {
         var1 = this.doPeek();
      }

      if (var1 == 4) {
         var1 = this.stackSize - 1;
         this.stackSize = var1;
         int[] var6 = this.pathIndices;
         var1--;
         var6[var1]++;
         this.peeked = 0;
      } else {
         StringBuilder var3 = new StringBuilder("Expected END_ARRAY but was ");
         var3.append(this.peek());
         var3.append(this.locationString());
         throw new IllegalStateException(var3.toString());
      }
   }

   public void endObject() {
      int var2 = this.peeked;
      int var1 = var2;
      if (var2 == 0) {
         var1 = this.doPeek();
      }

      if (var1 == 2) {
         var1 = this.stackSize - 1;
         this.stackSize = var1;
         this.pathNames[var1] = null;
         int[] var6 = this.pathIndices;
         var1--;
         var6[var1]++;
         this.peeked = 0;
      } else {
         StringBuilder var3 = new StringBuilder("Expected END_OBJECT but was ");
         var3.append(this.peek());
         var3.append(this.locationString());
         throw new IllegalStateException(var3.toString());
      }
   }

   public String getPath() {
      return this.getPath(false);
   }

   public String getPreviousPath() {
      return this.getPath(true);
   }

   public boolean hasNext() {
      int var2 = this.peeked;
      int var1 = var2;
      if (var2 == 0) {
         var1 = this.doPeek();
      }

      boolean var3;
      if (var1 != 2 && var1 != 4 && var1 != 17) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public final boolean isLenient() {
      return this.lenient;
   }

   public String locationString() {
      int var1 = this.lineNumber;
      int var2 = this.pos;
      int var3 = this.lineStart;
      StringBuilder var4 = new StringBuilder(" at line ");
      var4.append(var1 + 1);
      var4.append(" column ");
      var4.append(var2 - var3 + 1);
      var4.append(" path ");
      var4.append(this.getPath());
      return var4.toString();
   }

   public boolean nextBoolean() {
      int var2 = this.peeked;
      int var1 = var2;
      if (var2 == 0) {
         var1 = this.doPeek();
      }

      if (var1 == 5) {
         this.peeked = 0;
         int[] var7 = this.pathIndices;
         var1 = this.stackSize - 1;
         var7[var1]++;
         return true;
      } else if (var1 == 6) {
         this.peeked = 0;
         int[] var6 = this.pathIndices;
         var1 = this.stackSize - 1;
         var6[var1]++;
         return false;
      } else {
         StringBuilder var3 = new StringBuilder("Expected a boolean but was ");
         var3.append(this.peek());
         var3.append(this.locationString());
         throw new IllegalStateException(var3.toString());
      }
   }

   public double nextDouble() {
      int var5 = this.peeked;
      int var4 = var5;
      if (var5 == 0) {
         var4 = this.doPeek();
      }

      if (var4 == 15) {
         this.peeked = 0;
         int[] var12 = this.pathIndices;
         var4 = this.stackSize - 1;
         var12[var4]++;
         return (double)this.peekedLong;
      } else {
         if (var4 == 16) {
            this.peekedString = new String(this.buffer, this.pos, this.peekedNumberLength);
            this.pos = this.pos + this.peekedNumberLength;
         } else {
            label58: {
               String var6;
               if (var4 != 8 && var4 != 9) {
                  if (var4 != 10) {
                     if (var4 != 11) {
                        StringBuilder var11 = new StringBuilder("Expected a double but was ");
                        var11.append(this.peek());
                        var11.append(this.locationString());
                        throw new IllegalStateException(var11.toString());
                     }
                     break label58;
                  }

                  var6 = this.nextUnquotedValue();
               } else {
                  char var1;
                  if (var4 == 8) {
                     var1 = '\'';
                  } else {
                     var1 = '"';
                  }

                  var6 = this.nextQuotedValue(var1);
               }

               this.peekedString = var6;
            }
         }

         this.peeked = 11;
         double var2 = Double.parseDouble(this.peekedString);
         if (this.lenient || !Double.isNaN(var2) && !Double.isInfinite(var2)) {
            this.peekedString = null;
            this.peeked = 0;
            int[] var10 = this.pathIndices;
            var4 = this.stackSize - 1;
            var10[var4]++;
            return var2;
         } else {
            StringBuilder var9 = new StringBuilder("JSON forbids NaN and infinities: ");
            var9.append(var2);
            var9.append(this.locationString());
            throw new MalformedJsonException(var9.toString());
         }
      }
   }

   public int nextInt() {
      int var5 = this.peeked;
      int var4 = var5;
      if (var5 == 0) {
         var4 = this.doPeek();
      }

      if (var4 == 15) {
         long var6 = this.peekedLong;
         var5 = (int)var6;
         if (var6 == (long)var5) {
            this.peeked = 0;
            int[] var21 = this.pathIndices;
            var4 = this.stackSize - 1;
            var21[var4]++;
            return var5;
         } else {
            StringBuilder var20 = new StringBuilder("Expected an int but was ");
            var20.append(this.peekedLong);
            var20.append(this.locationString());
            throw new NumberFormatException(var20.toString());
         }
      } else {
         if (var4 == 16) {
            this.peekedString = new String(this.buffer, this.pos, this.peekedNumberLength);
            this.pos = this.pos + this.peekedNumberLength;
         } else {
            label60: {
               if (var4 != 8 && var4 != 9 && var4 != 10) {
                  StringBuilder var19 = new StringBuilder("Expected an int but was ");
                  var19.append(this.peek());
                  var19.append(this.locationString());
                  throw new IllegalStateException(var19.toString());
               }

               String var8;
               if (var4 == 10) {
                  var8 = this.nextUnquotedValue();
               } else {
                  char var1;
                  if (var4 == 8) {
                     var1 = '\'';
                  } else {
                     var1 = '"';
                  }

                  var8 = this.nextQuotedValue(var1);
               }

               this.peekedString = var8;

               try {
                  var5 = Integer.parseInt(this.peekedString);
                  this.peeked = 0;
                  var18 = this.pathIndices;
                  var4 = this.stackSize - 1;
               } catch (NumberFormatException var9) {
                  break label60;
               }

               var18[var4]++;
               return var5;
            }
         }

         this.peeked = 11;
         double var2 = Double.parseDouble(this.peekedString);
         var4 = (int)var2;
         if ((double)var4 == var2) {
            this.peekedString = null;
            this.peeked = 0;
            int[] var17 = this.pathIndices;
            var5 = this.stackSize - 1;
            var17[var5]++;
            return var4;
         } else {
            StringBuilder var16 = new StringBuilder("Expected an int but was ");
            var16.append(this.peekedString);
            var16.append(this.locationString());
            throw new NumberFormatException(var16.toString());
         }
      }
   }

   public long nextLong() {
      int var5 = this.peeked;
      int var4 = var5;
      if (var5 == 0) {
         var4 = this.doPeek();
      }

      if (var4 == 15) {
         this.peeked = 0;
         int[] var18 = this.pathIndices;
         var4 = this.stackSize - 1;
         var18[var4]++;
         return this.peekedLong;
      } else {
         if (var4 == 16) {
            this.peekedString = new String(this.buffer, this.pos, this.peekedNumberLength);
            this.pos = this.pos + this.peekedNumberLength;
         } else {
            label56: {
               if (var4 != 8 && var4 != 9 && var4 != 10) {
                  StringBuilder var17 = new StringBuilder("Expected a long but was ");
                  var17.append(this.peek());
                  var17.append(this.locationString());
                  throw new IllegalStateException(var17.toString());
               }

               String var8;
               if (var4 == 10) {
                  var8 = this.nextUnquotedValue();
               } else {
                  char var1;
                  if (var4 == 8) {
                     var1 = '\'';
                  } else {
                     var1 = '"';
                  }

                  var8 = this.nextQuotedValue(var1);
               }

               this.peekedString = var8;

               long var13;
               try {
                  var13 = Long.parseLong(this.peekedString);
                  this.peeked = 0;
                  var16 = this.pathIndices;
                  var4 = this.stackSize - 1;
               } catch (NumberFormatException var9) {
                  break label56;
               }

               var16[var4]++;
               return var13;
            }
         }

         this.peeked = 11;
         double var2 = Double.parseDouble(this.peekedString);
         long var6 = (long)var2;
         if ((double)var6 == var2) {
            this.peekedString = null;
            this.peeked = 0;
            int[] var15 = this.pathIndices;
            var4 = this.stackSize - 1;
            var15[var4]++;
            return var6;
         } else {
            StringBuilder var14 = new StringBuilder("Expected a long but was ");
            var14.append(this.peekedString);
            var14.append(this.locationString());
            throw new NumberFormatException(var14.toString());
         }
      }
   }

   public String nextName() {
      int var3 = this.peeked;
      int var2 = var3;
      if (var3 == 0) {
         var2 = this.doPeek();
      }

      String var4;
      if (var2 == 14) {
         var4 = this.nextUnquotedValue();
      } else {
         char var1;
         if (var2 == 12) {
            var1 = '\'';
         } else {
            if (var2 != 13) {
               StringBuilder var5 = new StringBuilder("Expected a name but was ");
               var5.append(this.peek());
               var5.append(this.locationString());
               throw new IllegalStateException(var5.toString());
            }

            var1 = '"';
         }

         var4 = this.nextQuotedValue(var1);
      }

      this.peeked = 0;
      this.pathNames[this.stackSize - 1] = var4;
      return var4;
   }

   public void nextNull() {
      int var2 = this.peeked;
      int var1 = var2;
      if (var2 == 0) {
         var1 = this.doPeek();
      }

      if (var1 == 7) {
         this.peeked = 0;
         int[] var5 = this.pathIndices;
         var1 = this.stackSize - 1;
         var5[var1]++;
      } else {
         StringBuilder var3 = new StringBuilder("Expected null but was ");
         var3.append(this.peek());
         var3.append(this.locationString());
         throw new IllegalStateException(var3.toString());
      }
   }

   public String nextString() {
      int var3 = this.peeked;
      int var2 = var3;
      if (var3 == 0) {
         var2 = this.doPeek();
      }

      String var4;
      if (var2 == 10) {
         var4 = this.nextUnquotedValue();
      } else {
         label30: {
            char var1;
            if (var2 == 8) {
               var1 = '\'';
            } else {
               if (var2 != 9) {
                  if (var2 == 11) {
                     var4 = this.peekedString;
                     this.peekedString = null;
                  } else if (var2 == 15) {
                     var4 = Long.toString(this.peekedLong);
                  } else {
                     if (var2 != 16) {
                        StringBuilder var7 = new StringBuilder("Expected a string but was ");
                        var7.append(this.peek());
                        var7.append(this.locationString());
                        throw new IllegalStateException(var7.toString());
                     }

                     var4 = new String(this.buffer, this.pos, this.peekedNumberLength);
                     this.pos = this.pos + this.peekedNumberLength;
                  }
                  break label30;
               }

               var1 = '"';
            }

            var4 = this.nextQuotedValue(var1);
         }
      }

      this.peeked = 0;
      int[] var5 = this.pathIndices;
      var2 = this.stackSize - 1;
      var5[var2]++;
      return var4;
   }

   public JsonToken peek() {
      int var2 = this.peeked;
      int var1 = var2;
      if (var2 == 0) {
         var1 = this.doPeek();
      }

      switch (var1) {
         case 1:
            return JsonToken.BEGIN_OBJECT;
         case 2:
            return JsonToken.END_OBJECT;
         case 3:
            return JsonToken.BEGIN_ARRAY;
         case 4:
            return JsonToken.END_ARRAY;
         case 5:
         case 6:
            return JsonToken.BOOLEAN;
         case 7:
            return JsonToken.NULL;
         case 8:
         case 9:
         case 10:
         case 11:
            return JsonToken.STRING;
         case 12:
         case 13:
         case 14:
            return JsonToken.NAME;
         case 15:
         case 16:
            return JsonToken.NUMBER;
         case 17:
            return JsonToken.END_DOCUMENT;
         default:
            throw new AssertionError();
      }
   }

   public final void setLenient(boolean var1) {
      this.lenient = var1;
   }

   public void skipValue() {
      int var2 = 0;

      int var5;
      do {
         int var3 = this.peeked;
         var5 = var3;
         if (var3 == 0) {
            var5 = this.doPeek();
         }

         label38: {
            switch (var5) {
               case 1:
                  this.push(3);
                  break;
               case 2:
                  if (var2 == 0) {
                     this.pathNames[this.stackSize - 1] = null;
                  }
               case 4:
                  this.stackSize--;
                  var5 = var2 - 1;
                  break label38;
               case 3:
                  this.push(1);
                  break;
               case 5:
               case 6:
               case 7:
               case 11:
               case 15:
               default:
                  var5 = var2;
                  break label38;
               case 8:
                  this.skipQuotedValue('\'');
                  var5 = var2;
                  break label38;
               case 9:
                  this.skipQuotedValue('"');
                  var5 = var2;
                  break label38;
               case 10:
                  this.skipUnquotedValue();
                  var5 = var2;
                  break label38;
               case 12:
                  this.skipQuotedValue('\'');
                  var5 = var2;
                  if (var2 == 0) {
                     this.pathNames[this.stackSize - 1] = "<skipped>";
                     var5 = var2;
                  }
                  break label38;
               case 13:
                  this.skipQuotedValue('"');
                  var5 = var2;
                  if (var2 == 0) {
                     this.pathNames[this.stackSize - 1] = "<skipped>";
                     var5 = var2;
                  }
                  break label38;
               case 14:
                  this.skipUnquotedValue();
                  var5 = var2;
                  if (var2 == 0) {
                     this.pathNames[this.stackSize - 1] = "<skipped>";
                     var5 = var2;
                  }
                  break label38;
               case 16:
                  this.pos = this.pos + this.peekedNumberLength;
                  var5 = var2;
                  break label38;
               case 17:
                  return;
            }

            var5 = var2 + 1;
         }

         this.peeked = 0;
         var2 = var5;
      } while (var5 > 0);

      int[] var4 = this.pathIndices;
      var5 = this.stackSize - 1;
      var4[var5]++;
   }

   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append(this.getClass().getSimpleName());
      var1.append(this.locationString());
      return var1.toString();
   }
}
