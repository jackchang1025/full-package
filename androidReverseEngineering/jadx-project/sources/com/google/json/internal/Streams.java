package com.google.json.internal;

import com.google.json.JsonElement;
import com.google.json.JsonIOException;
import com.google.json.JsonNull;
import com.google.json.JsonSyntaxException;
import com.google.json.internal.bind.TypeAdapters;
import com.google.json.stream.JsonReader;
import com.google.json.stream.JsonWriter;
import com.google.json.stream.MalformedJsonException;
import java.io.EOFException;
import java.io.IOException;
import java.io.Writer;
import java.util.Objects;

/* loaded from: classes.dex */
public final class Streams {

    public static final class AppendableWriter extends Writer {
        private final Appendable appendable;
        private final CurrentWrite currentWrite = new CurrentWrite();

        public static class CurrentWrite implements CharSequence {
            private String cachedString;
            private char[] chars;

            private CurrentWrite() {
            }

            @Override // java.lang.CharSequence
            public char charAt(int i2) {
                return this.chars[i2];
            }

            @Override // java.lang.CharSequence
            public int length() {
                return this.chars.length;
            }

            public void setChars(char[] cArr) {
                this.chars = cArr;
                this.cachedString = null;
            }

            @Override // java.lang.CharSequence
            public CharSequence subSequence(int i2, int i3) {
                return new String(this.chars, i2, i3 - i2);
            }

            @Override // java.lang.CharSequence
            public String toString() {
                if (this.cachedString == null) {
                    this.cachedString = new String(this.chars);
                }
                return this.cachedString;
            }
        }

        public AppendableWriter(Appendable appendable) {
            this.appendable = appendable;
        }

        @Override // java.io.Writer, java.lang.Appendable
        public Writer append(CharSequence charSequence) {
            this.appendable.append(charSequence);
            return this;
        }

        @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
        public void close() {
        }

        @Override // java.io.Writer, java.io.Flushable
        public void flush() {
        }

        @Override // java.io.Writer
        public void write(int i2) {
            this.appendable.append((char) i2);
        }

        @Override // java.io.Writer, java.lang.Appendable
        public Writer append(CharSequence charSequence, int i2, int i3) {
            this.appendable.append(charSequence, i2, i3);
            return this;
        }

        @Override // java.io.Writer
        public void write(String str, int i2, int i3) {
            Objects.requireNonNull(str);
            this.appendable.append(str, i2, i3 + i2);
        }

        @Override // java.io.Writer
        public void write(char[] cArr, int i2, int i3) {
            this.currentWrite.setChars(cArr);
            this.appendable.append(this.currentWrite, i2, i3 + i2);
        }
    }

    private Streams() {
        throw new UnsupportedOperationException();
    }

    public static JsonElement parse(JsonReader jsonReader) {
        boolean z2;
        try {
            try {
                jsonReader.peek();
                z2 = false;
            } catch (EOFException e2) {
                e = e2;
                z2 = true;
            }
            try {
                return TypeAdapters.JSON_ELEMENT.read(jsonReader);
            } catch (EOFException e3) {
                e = e3;
                if (z2) {
                    return JsonNull.INSTANCE;
                }
                throw new JsonSyntaxException(e);
            }
        } catch (MalformedJsonException e4) {
            throw new JsonSyntaxException(e4);
        } catch (IOException e5) {
            throw new JsonIOException(e5);
        } catch (NumberFormatException e6) {
            throw new JsonSyntaxException(e6);
        }
    }

    public static void write(JsonElement jsonElement, JsonWriter jsonWriter) {
        TypeAdapters.JSON_ELEMENT.write(jsonWriter, jsonElement);
    }

    public static Writer writerForAppendable(Appendable appendable) {
        return appendable instanceof Writer ? (Writer) appendable : new AppendableWriter(appendable);
    }
}
