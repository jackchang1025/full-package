package com.google.json;

import com.google.json.internal.bind.JsonTreeReader;
import com.google.json.internal.bind.JsonTreeWriter;
import com.google.json.stream.JsonReader;
import com.google.json.stream.JsonToken;
import com.google.json.stream.JsonWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

/* loaded from: classes.dex */
public abstract class TypeAdapter<T> {
    public final T fromJson(Reader reader) {
        return read(new JsonReader(reader));
    }

    public final T fromJsonTree(JsonElement jsonElement) {
        try {
            return read(new JsonTreeReader(jsonElement));
        } catch (IOException e2) {
            throw new JsonIOException(e2);
        }
    }

    public final TypeAdapter<T> nullSafe() {
        return new TypeAdapter<T>() { // from class: com.google.json.TypeAdapter.1
            @Override // com.google.json.TypeAdapter
            public T read(JsonReader jsonReader) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    return (T) TypeAdapter.this.read(jsonReader);
                }
                jsonReader.nextNull();
                return null;
            }

            @Override // com.google.json.TypeAdapter
            public void write(JsonWriter jsonWriter, T t2) {
                if (t2 == null) {
                    jsonWriter.nullValue();
                } else {
                    TypeAdapter.this.write(jsonWriter, t2);
                }
            }
        };
    }

    public abstract T read(JsonReader jsonReader);

    public final String toJson(T t2) {
        StringWriter stringWriter = new StringWriter();
        try {
            toJson(stringWriter, t2);
            return stringWriter.toString();
        } catch (IOException e2) {
            throw new JsonIOException(e2);
        }
    }

    public final JsonElement toJsonTree(T t2) {
        try {
            JsonTreeWriter jsonTreeWriter = new JsonTreeWriter();
            write(jsonTreeWriter, t2);
            return jsonTreeWriter.get();
        } catch (IOException e2) {
            throw new JsonIOException(e2);
        }
    }

    public abstract void write(JsonWriter jsonWriter, T t2);

    public final T fromJson(String str) {
        return fromJson(new StringReader(str));
    }

    public final void toJson(Writer writer, T t2) {
        write(new JsonWriter(writer), t2);
    }
}
