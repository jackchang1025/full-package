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

/* loaded from: classes.dex */
public final class JsonTreeWriter extends JsonWriter {
    private String pendingName;
    private JsonElement product;
    private final List<JsonElement> stack;
    private static final Writer UNWRITABLE_WRITER = new Writer() { // from class: com.google.json.internal.bind.JsonTreeWriter.1
        @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            throw new AssertionError();
        }

        @Override // java.io.Writer, java.io.Flushable
        public void flush() {
            throw new AssertionError();
        }

        @Override // java.io.Writer
        public void write(char[] cArr, int i2, int i3) {
            throw new AssertionError();
        }
    };
    private static final JsonPrimitive SENTINEL_CLOSED = new JsonPrimitive("closed");

    public JsonTreeWriter() {
        super(UNWRITABLE_WRITER);
        this.stack = new ArrayList();
        this.product = JsonNull.INSTANCE;
    }

    private JsonElement peek() {
        return this.stack.get(r0.size() - 1);
    }

    private void put(JsonElement jsonElement) {
        if (this.pendingName != null) {
            if (!jsonElement.isJsonNull() || getSerializeNulls()) {
                ((JsonObject) peek()).add(this.pendingName, jsonElement);
            }
            this.pendingName = null;
            return;
        }
        if (this.stack.isEmpty()) {
            this.product = jsonElement;
            return;
        }
        JsonElement peek = peek();
        if (!(peek instanceof JsonArray)) {
            throw new IllegalStateException();
        }
        ((JsonArray) peek).add(jsonElement);
    }

    @Override // com.google.json.stream.JsonWriter
    public JsonWriter beginArray() {
        JsonArray jsonArray = new JsonArray();
        put(jsonArray);
        this.stack.add(jsonArray);
        return this;
    }

    @Override // com.google.json.stream.JsonWriter
    public JsonWriter beginObject() {
        JsonObject jsonObject = new JsonObject();
        put(jsonObject);
        this.stack.add(jsonObject);
        return this;
    }

    @Override // com.google.json.stream.JsonWriter, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        if (!this.stack.isEmpty()) {
            throw new IOException("Incomplete document");
        }
        this.stack.add(SENTINEL_CLOSED);
    }

    @Override // com.google.json.stream.JsonWriter
    public JsonWriter endArray() {
        if (this.stack.isEmpty() || this.pendingName != null) {
            throw new IllegalStateException();
        }
        if (!(peek() instanceof JsonArray)) {
            throw new IllegalStateException();
        }
        this.stack.remove(r0.size() - 1);
        return this;
    }

    @Override // com.google.json.stream.JsonWriter
    public JsonWriter endObject() {
        if (this.stack.isEmpty() || this.pendingName != null) {
            throw new IllegalStateException();
        }
        if (!(peek() instanceof JsonObject)) {
            throw new IllegalStateException();
        }
        this.stack.remove(r0.size() - 1);
        return this;
    }

    @Override // com.google.json.stream.JsonWriter, java.io.Flushable
    public void flush() {
    }

    public JsonElement get() {
        if (this.stack.isEmpty()) {
            return this.product;
        }
        throw new IllegalStateException("Expected one JSON element but was " + this.stack);
    }

    @Override // com.google.json.stream.JsonWriter
    public JsonWriter jsonValue(String str) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.json.stream.JsonWriter
    public JsonWriter name(String str) {
        Objects.requireNonNull(str, "name == null");
        if (this.stack.isEmpty() || this.pendingName != null) {
            throw new IllegalStateException();
        }
        if (!(peek() instanceof JsonObject)) {
            throw new IllegalStateException();
        }
        this.pendingName = str;
        return this;
    }

    @Override // com.google.json.stream.JsonWriter
    public JsonWriter nullValue() {
        put(JsonNull.INSTANCE);
        return this;
    }

    @Override // com.google.json.stream.JsonWriter
    public JsonWriter value(double d2) {
        if (isLenient() || !(Double.isNaN(d2) || Double.isInfinite(d2))) {
            put(new JsonPrimitive(Double.valueOf(d2)));
            return this;
        }
        throw new IllegalArgumentException("JSON forbids NaN and infinities: " + d2);
    }

    @Override // com.google.json.stream.JsonWriter
    public JsonWriter value(float f2) {
        if (isLenient() || !(Float.isNaN(f2) || Float.isInfinite(f2))) {
            put(new JsonPrimitive(Float.valueOf(f2)));
            return this;
        }
        throw new IllegalArgumentException("JSON forbids NaN and infinities: " + f2);
    }

    @Override // com.google.json.stream.JsonWriter
    public JsonWriter value(long j2) {
        put(new JsonPrimitive(Long.valueOf(j2)));
        return this;
    }

    @Override // com.google.json.stream.JsonWriter
    public JsonWriter value(Boolean bool) {
        if (bool == null) {
            return nullValue();
        }
        put(new JsonPrimitive(bool));
        return this;
    }

    @Override // com.google.json.stream.JsonWriter
    public JsonWriter value(Number number) {
        if (number == null) {
            return nullValue();
        }
        if (!isLenient()) {
            double doubleValue = number.doubleValue();
            if (Double.isNaN(doubleValue) || Double.isInfinite(doubleValue)) {
                throw new IllegalArgumentException("JSON forbids NaN and infinities: " + number);
            }
        }
        put(new JsonPrimitive(number));
        return this;
    }

    @Override // com.google.json.stream.JsonWriter
    public JsonWriter value(String str) {
        if (str == null) {
            return nullValue();
        }
        put(new JsonPrimitive(str));
        return this;
    }

    @Override // com.google.json.stream.JsonWriter
    public JsonWriter value(boolean z2) {
        put(new JsonPrimitive(Boolean.valueOf(z2)));
        return this;
    }
}
