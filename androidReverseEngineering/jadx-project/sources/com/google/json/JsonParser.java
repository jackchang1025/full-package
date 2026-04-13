package com.google.json;

import com.google.json.internal.Streams;
import com.google.json.stream.JsonReader;
import com.google.json.stream.JsonToken;
import com.google.json.stream.MalformedJsonException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/* loaded from: classes.dex */
public final class JsonParser {
    @Deprecated
    public JsonParser() {
    }

    public static JsonElement parseReader(JsonReader jsonReader) {
        boolean isLenient = jsonReader.isLenient();
        jsonReader.setLenient(true);
        try {
            try {
                return Streams.parse(jsonReader);
            } catch (OutOfMemoryError e2) {
                throw new JsonParseException("Failed parsing JSON source: " + jsonReader + " to Json", e2);
            } catch (StackOverflowError e3) {
                throw new JsonParseException("Failed parsing JSON source: " + jsonReader + " to Json", e3);
            }
        } finally {
            jsonReader.setLenient(isLenient);
        }
    }

    public static JsonElement parseString(String str) {
        return parseReader(new StringReader(str));
    }

    @Deprecated
    public JsonElement parse(JsonReader jsonReader) {
        return parseReader(jsonReader);
    }

    public static JsonElement parseReader(Reader reader) {
        try {
            JsonReader jsonReader = new JsonReader(reader);
            JsonElement parseReader = parseReader(jsonReader);
            if (!parseReader.isJsonNull() && jsonReader.peek() != JsonToken.END_DOCUMENT) {
                throw new JsonSyntaxException("Did not consume the entire document.");
            }
            return parseReader;
        } catch (MalformedJsonException e2) {
            throw new JsonSyntaxException(e2);
        } catch (IOException e3) {
            throw new JsonIOException(e3);
        } catch (NumberFormatException e4) {
            throw new JsonSyntaxException(e4);
        }
    }

    @Deprecated
    public JsonElement parse(Reader reader) {
        return parseReader(reader);
    }

    @Deprecated
    public JsonElement parse(String str) {
        return parseString(str);
    }
}
