package com.google.json.internal.bind;

import a.a;
import com.google.json.Gson;
import com.google.json.JsonArray;
import com.google.json.JsonElement;
import com.google.json.JsonIOException;
import com.google.json.JsonNull;
import com.google.json.JsonObject;
import com.google.json.JsonPrimitive;
import com.google.json.JsonSyntaxException;
import com.google.json.TypeAdapter;
import com.google.json.TypeAdapterFactory;
import com.google.json.annotations.SerializedName;
import com.google.json.internal.LazilyParsedNumber;
import com.google.json.reflect.TypeToken;
import com.google.json.stream.JsonReader;
import com.google.json.stream.JsonToken;
import com.google.json.stream.JsonWriter;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Currency;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

public final class TypeAdapters {
   public static final TypeAdapter<AtomicBoolean> ATOMIC_BOOLEAN;
   public static final TypeAdapterFactory ATOMIC_BOOLEAN_FACTORY;
   public static final TypeAdapter<AtomicInteger> ATOMIC_INTEGER;
   public static final TypeAdapter<AtomicIntegerArray> ATOMIC_INTEGER_ARRAY;
   public static final TypeAdapterFactory ATOMIC_INTEGER_ARRAY_FACTORY;
   public static final TypeAdapterFactory ATOMIC_INTEGER_FACTORY;
   public static final TypeAdapter<BigDecimal> BIG_DECIMAL = new TypeAdapter<BigDecimal>() {
      public BigDecimal read(JsonReader var1) {
         if (var1.peek() == JsonToken.NULL) {
            var1.nextNull();
            return null;
         } else {
            String var3 = var1.nextString();

            try {
               return new BigDecimal(var3);
            } catch (NumberFormatException var4) {
               StringBuilder var5 = a.s("Failed parsing '", var3, "' as BigDecimal; at path ");
               var5.append(var1.getPreviousPath());
               throw new JsonSyntaxException(var5.toString(), var4);
            }
         }
      }

      public void write(JsonWriter var1, BigDecimal var2) {
         var1.value(var2);
      }
   };
   public static final TypeAdapter<BigInteger> BIG_INTEGER = new TypeAdapter<BigInteger>() {
      public BigInteger read(JsonReader var1) {
         if (var1.peek() == JsonToken.NULL) {
            var1.nextNull();
            return null;
         } else {
            String var3 = var1.nextString();

            try {
               return new BigInteger(var3);
            } catch (NumberFormatException var4) {
               StringBuilder var5 = a.s("Failed parsing '", var3, "' as BigInteger; at path ");
               var5.append(var1.getPreviousPath());
               throw new JsonSyntaxException(var5.toString(), var4);
            }
         }
      }

      public void write(JsonWriter var1, BigInteger var2) {
         var1.value(var2);
      }
   };
   public static final TypeAdapter<BitSet> BIT_SET;
   public static final TypeAdapterFactory BIT_SET_FACTORY;
   public static final TypeAdapter<Boolean> BOOLEAN;
   public static final TypeAdapter<Boolean> BOOLEAN_AS_STRING = new TypeAdapter<Boolean>() {
      public Boolean read(JsonReader var1) {
         if (var1.peek() == JsonToken.NULL) {
            var1.nextNull();
            return null;
         } else {
            return Boolean.valueOf(var1.nextString());
         }
      }

      public void write(JsonWriter var1, Boolean var2) {
         String var3;
         if (var2 == null) {
            var3 = "null";
         } else {
            var3 = var2.toString();
         }

         var1.value(var3);
      }
   };
   public static final TypeAdapterFactory BOOLEAN_FACTORY;
   public static final TypeAdapter<Number> BYTE;
   public static final TypeAdapterFactory BYTE_FACTORY;
   public static final TypeAdapter<Calendar> CALENDAR;
   public static final TypeAdapterFactory CALENDAR_FACTORY;
   public static final TypeAdapter<Character> CHARACTER;
   public static final TypeAdapterFactory CHARACTER_FACTORY;
   public static final TypeAdapter<Class> CLASS;
   public static final TypeAdapterFactory CLASS_FACTORY;
   public static final TypeAdapter<Currency> CURRENCY;
   public static final TypeAdapterFactory CURRENCY_FACTORY;
   public static final TypeAdapter<Number> DOUBLE = new TypeAdapter<Number>() {
      public Number read(JsonReader var1) {
         if (var1.peek() == JsonToken.NULL) {
            var1.nextNull();
            return null;
         } else {
            return var1.nextDouble();
         }
      }

      public void write(JsonWriter var1, Number var2) {
         if (var2 == null) {
            var1.nullValue();
         } else {
            var1.value(var2.doubleValue());
         }
      }
   };
   public static final TypeAdapterFactory ENUM_FACTORY = new TypeAdapterFactory() {
      @Override
      public <T> TypeAdapter<T> create(Gson var1, TypeToken<T> var2) {
         Class var4 = var2.getRawType();
         if (Enum.class.isAssignableFrom(var4) && var4 != Enum.class) {
            Class var3 = var4;
            if (!var4.isEnum()) {
               var3 = var4.getSuperclass();
            }

            return new TypeAdapters.EnumTypeAdapter(var3);
         } else {
            return null;
         }
      }
   };
   public static final TypeAdapter<Number> FLOAT = new TypeAdapter<Number>() {
      public Number read(JsonReader var1) {
         if (var1.peek() == JsonToken.NULL) {
            var1.nextNull();
            return null;
         } else {
            return (float)var1.nextDouble();
         }
      }

      public void write(JsonWriter var1, Number var2) {
         if (var2 == null) {
            var1.nullValue();
         } else {
            if (!(var2 instanceof Float)) {
               var2 = var2.floatValue();
            }

            var1.value((Number)var2);
         }
      }
   };
   public static final TypeAdapter<InetAddress> INET_ADDRESS;
   public static final TypeAdapterFactory INET_ADDRESS_FACTORY;
   public static final TypeAdapter<Number> INTEGER;
   public static final TypeAdapterFactory INTEGER_FACTORY;
   public static final TypeAdapter<JsonElement> JSON_ELEMENT;
   public static final TypeAdapterFactory JSON_ELEMENT_FACTORY;
   public static final TypeAdapter<LazilyParsedNumber> LAZILY_PARSED_NUMBER = new TypeAdapter<LazilyParsedNumber>() {
      public LazilyParsedNumber read(JsonReader var1) {
         if (var1.peek() == JsonToken.NULL) {
            var1.nextNull();
            return null;
         } else {
            return new LazilyParsedNumber(var1.nextString());
         }
      }

      public void write(JsonWriter var1, LazilyParsedNumber var2) {
         var1.value(var2);
      }
   };
   public static final TypeAdapter<Locale> LOCALE;
   public static final TypeAdapterFactory LOCALE_FACTORY;
   public static final TypeAdapter<Number> LONG = new TypeAdapter<Number>() {
      public Number read(JsonReader var1) {
         if (var1.peek() == JsonToken.NULL) {
            var1.nextNull();
            return null;
         } else {
            long var2;
            try {
               var2 = var1.nextLong();
            } catch (NumberFormatException var4) {
               throw new JsonSyntaxException(var4);
            }

            return var2;
         }
      }

      public void write(JsonWriter var1, Number var2) {
         if (var2 == null) {
            var1.nullValue();
         } else {
            var1.value(var2.longValue());
         }
      }
   };
   public static final TypeAdapter<Number> SHORT;
   public static final TypeAdapterFactory SHORT_FACTORY;
   public static final TypeAdapter<String> STRING;
   public static final TypeAdapter<StringBuffer> STRING_BUFFER;
   public static final TypeAdapterFactory STRING_BUFFER_FACTORY;
   public static final TypeAdapter<StringBuilder> STRING_BUILDER;
   public static final TypeAdapterFactory STRING_BUILDER_FACTORY;
   public static final TypeAdapterFactory STRING_FACTORY;
   public static final TypeAdapter<URI> URI;
   public static final TypeAdapterFactory URI_FACTORY;
   public static final TypeAdapter<URL> URL;
   public static final TypeAdapterFactory URL_FACTORY;
   public static final TypeAdapter<UUID> UUID;
   public static final TypeAdapterFactory UUID_FACTORY;

   static {
      TypeAdapter var0 = (new TypeAdapter<Class>() {
         public Class read(JsonReader var1) {
            throw new UnsupportedOperationException("Attempted to deserialize a java.lang.Class. Forgot to register a type adapter?");
         }

         public void write(JsonWriter var1, Class var2) {
            StringBuilder var3 = new StringBuilder("Attempted to serialize java.lang.Class: ");
            var3.append(var2.getName());
            var3.append(". Forgot to register a type adapter?");
            throw new UnsupportedOperationException(var3.toString());
         }
      }).nullSafe();
      CLASS = var0;
      CLASS_FACTORY = newFactory(Class.class, var0);
      var0 = (new TypeAdapter<BitSet>() {
         public BitSet read(JsonReader var1) {
            BitSet var6 = new BitSet();
            var1.beginArray();
            JsonToken var5 = var1.peek();

            for (int var2 = 0; var5 != JsonToken.END_ARRAY; var5 = var1.peek()) {
               int var3 = var5.ordinal();
               boolean var4 = true;
               if (var3 != 1 && var3 != 2) {
                  if (var3 != 3) {
                     StringBuilder var9 = new StringBuilder("Invalid bitset value type: ");
                     var9.append(var5);
                     var9.append("; at path ");
                     var9.append(var1.getPath());
                     throw new JsonSyntaxException(var9.toString());
                  }

                  var4 = var1.nextBoolean();
               } else {
                  var3 = var1.nextInt();
                  if (var3 == 0) {
                     var4 = false;
                  } else if (var3 != 1) {
                     StringBuilder var8 = a.q("Invalid bitset value ", var3, ", expected 0 or 1; at path ");
                     var8.append(var1.getPreviousPath());
                     throw new JsonSyntaxException(var8.toString());
                  }
               }

               if (var4) {
                  var6.set(var2);
               }

               var2++;
            }

            var1.endArray();
            return var6;
         }

         public void write(JsonWriter var1, BitSet var2) {
            var1.beginArray();
            int var4 = var2.length();

            for (int var3 = 0; var3 < var4; var3++) {
               var1.value((long)var2.get(var3));
            }

            var1.endArray();
         }
      }).nullSafe();
      BIT_SET = var0;
      BIT_SET_FACTORY = newFactory(BitSet.class, var0);
      var0 = new TypeAdapter<Boolean>() {
         public Boolean read(JsonReader var1) {
            JsonToken var2 = var1.peek();
            if (var2 == JsonToken.NULL) {
               var1.nextNull();
               return null;
            } else {
               return var2 == JsonToken.STRING ? Boolean.parseBoolean(var1.nextString()) : var1.nextBoolean();
            }
         }

         public void write(JsonWriter var1, Boolean var2) {
            var1.value(var2);
         }
      };
      BOOLEAN = var0;
      BOOLEAN_FACTORY = newFactory(boolean.class, Boolean.class, var0);
      var0 = new TypeAdapter<Number>() {
         public Number read(JsonReader var1) {
            if (var1.peek() == JsonToken.NULL) {
               var1.nextNull();
               return null;
            } else {
               int var2;
               try {
                  var2 = var1.nextInt();
               } catch (NumberFormatException var4) {
                  throw new JsonSyntaxException(var4);
               }

               if (var2 <= 255 && var2 >= -128) {
                  return (byte)var2;
               } else {
                  StringBuilder var3 = a.q("Lossy conversion from ", var2, " to byte; at path ");
                  var3.append(var1.getPreviousPath());
                  throw new JsonSyntaxException(var3.toString());
               }
            }
         }

         public void write(JsonWriter var1, Number var2) {
            if (var2 == null) {
               var1.nullValue();
            } else {
               var1.value((long)var2.byteValue());
            }
         }
      };
      BYTE = var0;
      BYTE_FACTORY = newFactory(byte.class, Byte.class, var0);
      var0 = new TypeAdapter<Number>() {
         public Number read(JsonReader var1) {
            if (var1.peek() == JsonToken.NULL) {
               var1.nextNull();
               return null;
            } else {
               int var2;
               try {
                  var2 = var1.nextInt();
               } catch (NumberFormatException var4) {
                  throw new JsonSyntaxException(var4);
               }

               if (var2 <= 65535 && var2 >= -32768) {
                  return (short)var2;
               } else {
                  StringBuilder var3 = a.q("Lossy conversion from ", var2, " to short; at path ");
                  var3.append(var1.getPreviousPath());
                  throw new JsonSyntaxException(var3.toString());
               }
            }
         }

         public void write(JsonWriter var1, Number var2) {
            if (var2 == null) {
               var1.nullValue();
            } else {
               var1.value((long)var2.shortValue());
            }
         }
      };
      SHORT = var0;
      SHORT_FACTORY = newFactory(short.class, Short.class, var0);
      var0 = new TypeAdapter<Number>() {
         public Number read(JsonReader var1) {
            if (var1.peek() == JsonToken.NULL) {
               var1.nextNull();
               return null;
            } else {
               int var2;
               try {
                  var2 = var1.nextInt();
               } catch (NumberFormatException var3) {
                  throw new JsonSyntaxException(var3);
               }

               return var2;
            }
         }

         public void write(JsonWriter var1, Number var2) {
            if (var2 == null) {
               var1.nullValue();
            } else {
               var1.value((long)var2.intValue());
            }
         }
      };
      INTEGER = var0;
      INTEGER_FACTORY = newFactory(int.class, Integer.class, var0);
      var0 = (new TypeAdapter<AtomicInteger>() {
         public AtomicInteger read(JsonReader var1) {
            try {
               return new AtomicInteger(var1.nextInt());
            } catch (NumberFormatException var2) {
               throw new JsonSyntaxException(var2);
            }
         }

         public void write(JsonWriter var1, AtomicInteger var2) {
            var1.value((long)var2.get());
         }
      }).nullSafe();
      ATOMIC_INTEGER = var0;
      ATOMIC_INTEGER_FACTORY = newFactory(AtomicInteger.class, var0);
      var0 = (new TypeAdapter<AtomicBoolean>() {
         public AtomicBoolean read(JsonReader var1) {
            return new AtomicBoolean(var1.nextBoolean());
         }

         public void write(JsonWriter var1, AtomicBoolean var2) {
            var1.value(var2.get());
         }
      }).nullSafe();
      ATOMIC_BOOLEAN = var0;
      ATOMIC_BOOLEAN_FACTORY = newFactory(AtomicBoolean.class, var0);
      var0 = (new TypeAdapter<AtomicIntegerArray>() {
         public AtomicIntegerArray read(JsonReader var1) {
            ArrayList var4 = new ArrayList();
            var1.beginArray();

            while (var1.hasNext()) {
               try {
                  var4.add(var1.nextInt());
               } catch (NumberFormatException var5) {
                  throw new JsonSyntaxException(var5);
               }
            }

            var1.endArray();
            int var3 = var4.size();
            AtomicIntegerArray var6 = new AtomicIntegerArray(var3);

            for (int var2 = 0; var2 < var3; var2++) {
               var6.set(var2, (Integer)var4.get(var2));
            }

            return var6;
         }

         public void write(JsonWriter var1, AtomicIntegerArray var2) {
            var1.beginArray();
            int var4 = var2.length();

            for (int var3 = 0; var3 < var4; var3++) {
               var1.value((long)var2.get(var3));
            }

            var1.endArray();
         }
      }).nullSafe();
      ATOMIC_INTEGER_ARRAY = var0;
      ATOMIC_INTEGER_ARRAY_FACTORY = newFactory(AtomicIntegerArray.class, var0);
      var0 = new TypeAdapter<Character>() {
         public Character read(JsonReader var1) {
            if (var1.peek() == JsonToken.NULL) {
               var1.nextNull();
               return null;
            } else {
               String var2 = var1.nextString();
               if (var2.length() == 1) {
                  return var2.charAt(0);
               } else {
                  StringBuilder var3 = a.s("Expecting character, got: ", var2, "; at ");
                  var3.append(var1.getPreviousPath());
                  throw new JsonSyntaxException(var3.toString());
               }
            }
         }

         public void write(JsonWriter var1, Character var2) {
            String var3;
            if (var2 == null) {
               var3 = null;
            } else {
               var3 = String.valueOf(var2);
            }

            var1.value(var3);
         }
      };
      CHARACTER = var0;
      CHARACTER_FACTORY = newFactory(char.class, Character.class, var0);
      var0 = new TypeAdapter<String>() {
         public String read(JsonReader var1) {
            JsonToken var2 = var1.peek();
            if (var2 == JsonToken.NULL) {
               var1.nextNull();
               return null;
            } else {
               return var2 == JsonToken.BOOLEAN ? Boolean.toString(var1.nextBoolean()) : var1.nextString();
            }
         }

         public void write(JsonWriter var1, String var2) {
            var1.value(var2);
         }
      };
      STRING = var0;
      STRING_FACTORY = newFactory(String.class, var0);
      var0 = new TypeAdapter<StringBuilder>() {
         public StringBuilder read(JsonReader var1) {
            if (var1.peek() == JsonToken.NULL) {
               var1.nextNull();
               return null;
            } else {
               return new StringBuilder(var1.nextString());
            }
         }

         public void write(JsonWriter var1, StringBuilder var2) {
            String var3;
            if (var2 == null) {
               var3 = null;
            } else {
               var3 = var2.toString();
            }

            var1.value(var3);
         }
      };
      STRING_BUILDER = var0;
      STRING_BUILDER_FACTORY = newFactory(StringBuilder.class, var0);
      var0 = new TypeAdapter<StringBuffer>() {
         public StringBuffer read(JsonReader var1) {
            if (var1.peek() == JsonToken.NULL) {
               var1.nextNull();
               return null;
            } else {
               return new StringBuffer(var1.nextString());
            }
         }

         public void write(JsonWriter var1, StringBuffer var2) {
            String var3;
            if (var2 == null) {
               var3 = null;
            } else {
               var3 = var2.toString();
            }

            var1.value(var3);
         }
      };
      STRING_BUFFER = var0;
      STRING_BUFFER_FACTORY = newFactory(StringBuffer.class, var0);
      var0 = new TypeAdapter<URL>() {
         public URL read(JsonReader var1) {
            JsonToken var3 = var1.peek();
            JsonToken var4 = JsonToken.NULL;
            Object var2 = null;
            if (var3 == var4) {
               var1.nextNull();
               return null;
            } else {
               String var5 = var1.nextString();
               URL var6;
               if ("null".equals(var5)) {
                  var6 = (URL)var2;
               } else {
                  var6 = new URL(var5);
               }

               return var6;
            }
         }

         public void write(JsonWriter var1, URL var2) {
            String var3;
            if (var2 == null) {
               var3 = null;
            } else {
               var3 = var2.toExternalForm();
            }

            var1.value(var3);
         }
      };
      URL = var0;
      URL_FACTORY = newFactory(URL.class, var0);
      var0 = new TypeAdapter<URI>() {
         public URI read(JsonReader var1) {
            JsonToken var4 = var1.peek();
            JsonToken var3 = JsonToken.NULL;
            Object var2 = null;
            if (var4 == var3) {
               var1.nextNull();
               return null;
            } else {
               try {
                  String var6 = var1.nextString();
                  if (!"null".equals(var6)) {
                     return new URI(var6);
                  }
               } catch (URISyntaxException var5) {
                  throw new JsonIOException(var5);
               }

               return (URI)var2;
            }
         }

         public void write(JsonWriter var1, URI var2) {
            String var3;
            if (var2 == null) {
               var3 = null;
            } else {
               var3 = var2.toASCIIString();
            }

            var1.value(var3);
         }
      };
      URI = var0;
      URI_FACTORY = newFactory(URI.class, var0);
      var0 = new TypeAdapter<InetAddress>() {
         public InetAddress read(JsonReader var1) {
            if (var1.peek() == JsonToken.NULL) {
               var1.nextNull();
               return null;
            } else {
               return InetAddress.getByName(var1.nextString());
            }
         }

         public void write(JsonWriter var1, InetAddress var2) {
            String var3;
            if (var2 == null) {
               var3 = null;
            } else {
               var3 = var2.getHostAddress();
            }

            var1.value(var3);
         }
      };
      INET_ADDRESS = var0;
      INET_ADDRESS_FACTORY = newTypeHierarchyFactory(InetAddress.class, var0);
      var0 = new TypeAdapter<UUID>() {
         public UUID read(JsonReader var1) {
            if (var1.peek() == JsonToken.NULL) {
               var1.nextNull();
               return null;
            } else {
               String var3 = var1.nextString();

               try {
                  return java.util.UUID.fromString(var3);
               } catch (IllegalArgumentException var4) {
                  StringBuilder var5 = a.s("Failed parsing '", var3, "' as UUID; at path ");
                  var5.append(var1.getPreviousPath());
                  throw new JsonSyntaxException(var5.toString(), var4);
               }
            }
         }

         public void write(JsonWriter var1, UUID var2) {
            String var3;
            if (var2 == null) {
               var3 = null;
            } else {
               var3 = var2.toString();
            }

            var1.value(var3);
         }
      };
      UUID = var0;
      UUID_FACTORY = newFactory(UUID.class, var0);
      var0 = (new TypeAdapter<Currency>() {
         public Currency read(JsonReader var1) {
            String var2 = var1.nextString();

            try {
               return Currency.getInstance(var2);
            } catch (IllegalArgumentException var4) {
               StringBuilder var5 = a.s("Failed parsing '", var2, "' as Currency; at path ");
               var5.append(var1.getPreviousPath());
               throw new JsonSyntaxException(var5.toString(), var4);
            }
         }

         public void write(JsonWriter var1, Currency var2) {
            var1.value(var2.getCurrencyCode());
         }
      }).nullSafe();
      CURRENCY = var0;
      CURRENCY_FACTORY = newFactory(Currency.class, var0);
      var0 = new TypeAdapter<Calendar>() {
         private static final String DAY_OF_MONTH = "dayOfMonth";
         private static final String HOUR_OF_DAY = "hourOfDay";
         private static final String MINUTE = "minute";
         private static final String MONTH = "month";
         private static final String SECOND = "second";
         private static final String YEAR = "year";

         public Calendar read(JsonReader var1) {
            if (var1.peek() == JsonToken.NULL) {
               var1.nextNull();
               return null;
            } else {
               var1.beginObject();
               int var8 = 0;
               int var7 = 0;
               int var3 = var7;
               int var4 = var7;
               int var5 = var7;
               int var6 = var7;

               while (var1.peek() != JsonToken.END_OBJECT) {
                  String var9 = var1.nextName();
                  int var2 = var1.nextInt();
                  if ("year".equals(var9)) {
                     var8 = var2;
                  } else if ("month".equals(var9)) {
                     var7 = var2;
                  } else if ("dayOfMonth".equals(var9)) {
                     var6 = var2;
                  } else if ("hourOfDay".equals(var9)) {
                     var3 = var2;
                  } else if ("minute".equals(var9)) {
                     var4 = var2;
                  } else if ("second".equals(var9)) {
                     var5 = var2;
                  }
               }

               var1.endObject();
               return new GregorianCalendar(var8, var7, var6, var3, var4, var5);
            }
         }

         public void write(JsonWriter var1, Calendar var2) {
            if (var2 == null) {
               var1.nullValue();
            } else {
               var1.beginObject();
               var1.name("year");
               var1.value((long)var2.get(1));
               var1.name("month");
               var1.value((long)var2.get(2));
               var1.name("dayOfMonth");
               var1.value((long)var2.get(5));
               var1.name("hourOfDay");
               var1.value((long)var2.get(11));
               var1.name("minute");
               var1.value((long)var2.get(12));
               var1.name("second");
               var1.value((long)var2.get(13));
               var1.endObject();
            }
         }
      };
      CALENDAR = var0;
      CALENDAR_FACTORY = newFactoryForMultipleTypes(Calendar.class, GregorianCalendar.class, var0);
      var0 = new TypeAdapter<Locale>() {
         public Locale read(JsonReader var1) {
            JsonToken var4 = var1.peek();
            JsonToken var2 = JsonToken.NULL;
            String var3 = null;
            if (var4 == var2) {
               var1.nextNull();
               return null;
            } else {
               StringTokenizer var7 = new StringTokenizer(var1.nextString(), "_");
               String var5;
               if (var7.hasMoreElements()) {
                  var5 = var7.nextToken();
               } else {
                  var5 = null;
               }

               String var6;
               if (var7.hasMoreElements()) {
                  var6 = var7.nextToken();
               } else {
                  var6 = null;
               }

               if (var7.hasMoreElements()) {
                  var3 = var7.nextToken();
               }

               if (var6 == null && var3 == null) {
                  return new Locale(var5);
               } else {
                  Locale var8 ;
                  if (var3 == null) {
                     var8 = new Locale(var5, var6);
                     return var8;
                  } else {
                     var8 = new Locale(var5, var6, var3);
                     return var8;
                  }
               }
            }
         }

         public void write(JsonWriter var1, Locale var2) {
            String var3;
            if (var2 == null) {
               var3 = null;
            } else {
               var3 = var2.toString();
            }

            var1.value(var3);
         }
      };
      LOCALE = var0;
      LOCALE_FACTORY = newFactory(Locale.class, var0);
      var0 = new TypeAdapter<JsonElement>() {
         private JsonElement readTerminal(JsonReader var1, JsonToken var2) {
            int var3 = var2.ordinal();
            if (var3 != 1) {
               if (var3 != 2) {
                  if (var3 != 3) {
                     if (var3 == 6) {
                        var1.nextNull();
                        return JsonNull.INSTANCE;
                     } else {
                        StringBuilder var4 = new StringBuilder("Unexpected token: ");
                        var4.append(var2);
                        throw new IllegalStateException(var4.toString());
                     }
                  } else {
                     return new JsonPrimitive(var1.nextBoolean());
                  }
               } else {
                  return new JsonPrimitive(var1.nextString());
               }
            } else {
               return new JsonPrimitive(new LazilyParsedNumber(var1.nextString()));
            }
         }

         private JsonElement tryBeginNesting(JsonReader var1, JsonToken var2) {
            int var3 = var2.ordinal();
            if (var3 != 4) {
               if (var3 != 5) {
                  return null;
               } else {
                  var1.beginObject();
                  return new JsonObject();
               }
            } else {
               var1.beginArray();
               return new JsonArray();
            }
         }

         public JsonElement read(JsonReader var1) {
            if (var1 instanceof JsonTreeReader) {
               return ((JsonTreeReader)var1).nextJsonElement();
            } else {
               JsonToken var4 = var1.peek();
               JsonElement var3 = this.tryBeginNesting(var1, var4);
               if (var3 == null) {
                  return this.readTerminal(var1, var4);
               } else {
                  ArrayDeque var7 = new ArrayDeque();

                  while (true) {
                     while (!var1.hasNext()) {
                        if (var3 instanceof JsonArray) {
                           var1.endArray();
                        } else {
                           var1.endObject();
                        }

                        if (var7.isEmpty()) {
                           return var3;
                        }

                        var3 = (JsonElement)var7.removeLast();
                     }

                     String var5;
                     if (var3 instanceof JsonObject) {
                        var5 = var1.nextName();
                     } else {
                        var5 = null;
                     }

                     JsonToken var8 = var1.peek();
                     JsonElement var6 = this.tryBeginNesting(var1, var8);
                     boolean var2;
                     if (var6 != null) {
                        var2 = true;
                     } else {
                        var2 = false;
                     }

                     JsonElement var9 = var6;
                     if (var6 == null) {
                        var9 = this.readTerminal(var1, var8);
                     }

                     if (var3 instanceof JsonArray) {
                        ((JsonArray)var3).add(var9);
                     } else {
                        ((JsonObject)var3).add(var5, var9);
                     }

                     if (var2) {
                        var7.addLast(var3);
                        var3 = var9;
                     }
                  }
               }
            }
         }

         public void write(JsonWriter var1, JsonElement var2) {
            if (var2 == null || var2.isJsonNull()) {
               var1.nullValue();
            } else if (var2.isJsonPrimitive()) {
               JsonPrimitive var5 = var2.getAsJsonPrimitive();
               if (var5.isNumber()) {
                  var1.value(var5.getAsNumber());
               } else if (var5.isBoolean()) {
                  var1.value(var5.getAsBoolean());
               } else {
                  var1.value(var5.getAsString());
               }
            } else if (var2.isJsonArray()) {
               var1.beginArray();
               Iterator var6 = var2.getAsJsonArray().iterator();

               while (var6.hasNext()) {
                  this.write(var1, (JsonElement)var6.next());
               }

               var1.endArray();
            } else {
               if (!var2.isJsonObject()) {
                  StringBuilder var4 = new StringBuilder("Couldn't write ");
                  var4.append(var2.getClass());
                  throw new IllegalArgumentException(var4.toString());
               }

               var1.beginObject();

               for (Entry var3 : var2.getAsJsonObject().entrySet()) {
                  var1.name((String)var3.getKey());
                  this.write(var1, (JsonElement)var3.getValue());
               }

               var1.endObject();
            }
         }
      };
      JSON_ELEMENT = var0;
      JSON_ELEMENT_FACTORY = newTypeHierarchyFactory(JsonElement.class, var0);
   }

   private TypeAdapters() {
      throw new UnsupportedOperationException();
   }

   public static <TT> TypeAdapterFactory newFactory(TypeToken<TT> var0, TypeAdapter<TT> var1) {
      return new TypeAdapterFactory(var0, var1) {
         final TypeToken val$type;
         final TypeAdapter val$typeAdapter;

         {
            this.val$type = var1;
            this.val$typeAdapter = var2;
         }

         @Override
         public <T> TypeAdapter<T> create(Gson var1, TypeToken<T> var2) {
            TypeAdapter var3;
            if (var2.equals(this.val$type)) {
               var3 = this.val$typeAdapter;
            } else {
               var3 = null;
            }

            return var3;
         }
      };
   }

   public static <TT> TypeAdapterFactory newFactory(Class<TT> var0, TypeAdapter<TT> var1) {
      return new TypeAdapterFactory(var0, var1) {
         final Class val$type;
         final TypeAdapter val$typeAdapter;

         {
            this.val$type = var1;
            this.val$typeAdapter = var2;
         }

         @Override
         public <T> TypeAdapter<T> create(Gson var1, TypeToken<T> var2) {
            TypeAdapter var3;
            if (var2.getRawType() == this.val$type) {
               var3 = this.val$typeAdapter;
            } else {
               var3 = null;
            }

            return var3;
         }

         @Override
         public String toString() {
            StringBuilder var1 = new StringBuilder("Factory[type=");
            var1.append(this.val$type.getName());
            var1.append(",adapter=");
            var1.append(this.val$typeAdapter);
            var1.append("]");
            return var1.toString();
         }
      };
   }

   public static <TT> TypeAdapterFactory newFactory(Class<TT> var0, Class<TT> var1, TypeAdapter<? super TT> var2) {
      return new TypeAdapterFactory(var0, var1, var2) {
         final Class val$boxed;
         final TypeAdapter val$typeAdapter;
         final Class val$unboxed;

         {
            this.val$unboxed = var1;
            this.val$boxed = var2;
            this.val$typeAdapter = var3;
         }

         @Override
         public <T> TypeAdapter<T> create(Gson var1, TypeToken<T> var2) {
            Class var3 = var2.getRawType();
            TypeAdapter var4;
            if (var3 != this.val$unboxed && var3 != this.val$boxed) {
               var4 = null;
            } else {
               var4 = this.val$typeAdapter;
            }

            return var4;
         }

         @Override
         public String toString() {
            StringBuilder var1 = new StringBuilder("Factory[type=");
            var1.append(this.val$boxed.getName());
            var1.append("+");
            var1.append(this.val$unboxed.getName());
            var1.append(",adapter=");
            var1.append(this.val$typeAdapter);
            var1.append("]");
            return var1.toString();
         }
      };
   }

   public static <TT> TypeAdapterFactory newFactoryForMultipleTypes(Class<TT> var0, Class<? extends TT> var1, TypeAdapter<? super TT> var2) {
      return new TypeAdapterFactory(var0, var1, var2) {
         final Class val$base;
         final Class val$sub;
         final TypeAdapter val$typeAdapter;

         {
            this.val$base = var1;
            this.val$sub = var2;
            this.val$typeAdapter = var3;
         }

         @Override
         public <T> TypeAdapter<T> create(Gson var1, TypeToken<T> var2) {
            Class var3 = var2.getRawType();
            TypeAdapter var4;
            if (var3 != this.val$base && var3 != this.val$sub) {
               var4 = null;
            } else {
               var4 = this.val$typeAdapter;
            }

            return var4;
         }

         @Override
         public String toString() {
            StringBuilder var1 = new StringBuilder("Factory[type=");
            var1.append(this.val$base.getName());
            var1.append("+");
            var1.append(this.val$sub.getName());
            var1.append(",adapter=");
            var1.append(this.val$typeAdapter);
            var1.append("]");
            return var1.toString();
         }
      };
   }

   public static <T1> TypeAdapterFactory newTypeHierarchyFactory(Class<T1> var0, TypeAdapter<T1> var1) {
      return new TypeAdapterFactory(var0, var1) {
         final Class val$clazz;
         final TypeAdapter val$typeAdapter;

         {
            this.val$clazz = var1;
            this.val$typeAdapter = var2;
         }

         @Override
         public <T2> TypeAdapter<T2> create(Gson var1, TypeToken<T2> var2) {
            Class var3 = var2.getRawType();
            return (TypeAdapter<T2>)(!this.val$clazz.isAssignableFrom(var3) ? null : new TypeAdapter<T1>(this, var3) {
               final Object this$0;
               final Class val$requestedType;

               {
                  this.this$0 = var1;
                  this.val$requestedType = var2;
               }

               @Override
               public T1 read(JsonReader var1) {
                  Object var3 = this.this$0.val$typeAdapter.read(var1);
                  if (var3 != null && !this.val$requestedType.isInstance(var3)) {
                     StringBuilder var2 = new StringBuilder("Expected a ");
                     var2.append(this.val$requestedType.getName());
                     var2.append(" but was ");
                     var2.append(var3.getClass().getName());
                     var2.append("; at path ");
                     var2.append(var1.getPreviousPath());
                     throw new JsonSyntaxException(var2.toString());
                  } else {
                     return (T1)var3;
                  }
               }

               @Override
               public void write(JsonWriter var1, T1 var2) {
                  this.this$0.val$typeAdapter.write(var1, var2);
               }
            });
         }

         @Override
         public String toString() {
            StringBuilder var1 = new StringBuilder("Factory[typeHierarchy=");
            var1.append(this.val$clazz.getName());
            var1.append(",adapter=");
            var1.append(this.val$typeAdapter);
            var1.append("]");
            return var1.toString();
         }
      };
   }

   public static final class EnumTypeAdapter<T extends Enum<T>> extends TypeAdapter<T> {
      private final Map<T, String> constantToName;
      private final Map<String, T> nameToConstant = new HashMap<>();
      private final Map<String, T> stringToConstant = new HashMap<>();

      // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
      public EnumTypeAdapter(Class<T> var1) {
         this.constantToName = new HashMap<>();

         IllegalAccessException var10000;
         label62: {
            int var4;
            Field[] var7;
            try {
               PrivilegedAction var6 = new PrivilegedAction<Field[]>(this, var1) {
                  final TypeAdapters.EnumTypeAdapter this$0;
                  final Class val$classOfT;

                  {
                     this.this$0 = var1;
                     this.val$classOfT = var2;
                  }

                  public Field[] run() {
                     Field[] var5 = this.val$classOfT.getDeclaredFields();
                     ArrayList var3 = new ArrayList(var5.length);

                     for (Field var4 : var5) {
                        if (var4.isEnumConstant()) {
                           var3.add(var4);
                        }
                     }

                     Field[] var6 = var3.toArray(new Field[0]);
                     AccessibleObject.setAccessible(var6, true);
                     return var6;
                  }
               };
               var7 = AccessController.doPrivileged(var6);
               var4 = var7.length;
            } catch (IllegalAccessException var15) {
               var10000 = var15;
               boolean var10001 = false;
               break label62;
            }

            int var2 = 0;

            label53:
            while (true) {
               if (var2 >= var4) {
                  return;
               }

               Field var19 = var7[var2];

               Enum var8;
               String var9;
               SerializedName var10;
               try {
                  var8 = (Enum)var19.get(null);
                  var16 = var8.name();
                  var9 = var8.toString();
                  var10 = var19.getAnnotation(SerializedName.class);
               } catch (IllegalAccessException var14) {
                  var10000 = var14;
                  boolean var22 = false;
                  break;
               }

               if (var10 != null) {
                  int var5;
                  try {
                     var20 = var10.value();
                     var21 = var10.alternate();
                     var5 = var21.length;
                  } catch (IllegalAccessException var13) {
                     var10000 = var13;
                     boolean var23 = false;
                     break;
                  }

                  int var3 = 0;

                  while (true) {
                     var16 = var20;
                     if (var3 >= var5) {
                        break;
                     }

                     String var17 = var21[var3];

                     try {
                        this.nameToConstant.put(var17, (T)var8);
                     } catch (IllegalAccessException var12) {
                        var10000 = var12;
                        boolean var24 = false;
                        break label53;
                     }

                     var3++;
                  }
               }

               try {
                  this.nameToConstant.put(var16, (T)var8);
                  this.stringToConstant.put(var9, (T)var8);
                  this.constantToName.put((T)var8, var16);
               } catch (IllegalAccessException var11) {
                  var10000 = var11;
                  boolean var25 = false;
                  break;
               }

               var2++;
            }
         }

         IllegalAccessException var18 = var10000;
         throw new AssertionError(var18);
      }

      public T read(JsonReader var1) {
         if (var1.peek() == JsonToken.NULL) {
            var1.nextNull();
            return null;
         } else {
            String var3 = var1.nextString();
            Enum var2 = this.nameToConstant.get(var3);
            Enum var4 = var2;
            if (var2 == null) {
               var4 = this.stringToConstant.get(var3);
            }

            return (T)var4;
         }
      }

      public void write(JsonWriter var1, T var2) {
         String var3;
         if (var2 == null) {
            var3 = null;
         } else {
            var3 = this.constantToName.get(var2);
         }

         var1.value(var3);
      }
   }
}
