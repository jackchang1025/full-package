package com.google.json.internal.bind;

import com.google.json.JsonArray;
import com.google.json.JsonElement;
import com.google.json.JsonNull;
import com.google.json.JsonObject;
import com.google.json.JsonPrimitive;
import com.google.json.stream.JsonReader;
import com.google.json.stream.JsonToken;
import com.google.json.stream.MalformedJsonException;
import java.io.Reader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes.dex */
public final class JsonTreeReader extends JsonReader {
    private int[] pathIndices;
    private String[] pathNames;
    private Object[] stack;
    private int stackSize;
    private static final Reader UNREADABLE_READER = new Reader() { // from class: com.google.json.internal.bind.JsonTreeReader.1
        @Override // java.io.Reader, java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            throw new AssertionError();
        }

        @Override // java.io.Reader
        public int read(char[] cArr, int i2, int i3) {
            throw new AssertionError();
        }
    };
    private static final Object SENTINEL_CLOSED = new Object();

    /* renamed from: com.google.json.internal.bind.JsonTreeReader$2 */
    public static /* synthetic */ class C01512 {
        static final /* synthetic */ int[] $SwitchMap$com$google$json$stream$JsonToken;

        static {
            int[] iArr = new int[JsonToken.values().length];
            $SwitchMap$com$google$json$stream$JsonToken = iArr;
            try {
                iArr[JsonToken.NAME.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$google$json$stream$JsonToken[JsonToken.END_ARRAY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$google$json$stream$JsonToken[JsonToken.END_OBJECT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$google$json$stream$JsonToken[JsonToken.END_DOCUMENT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public JsonTreeReader(JsonElement jsonElement) {
        super(UNREADABLE_READER);
        this.stack = new Object[32];
        this.stackSize = 0;
        this.pathNames = new String[32];
        this.pathIndices = new int[32];
        push(jsonElement);
    }

    private void expect(JsonToken jsonToken) {
        if (peek() == jsonToken) {
            return;
        }
        throw new IllegalStateException("Expected " + jsonToken + " but was " + peek() + locationString());
    }

    private String locationString() {
        return " at path " + getPath();
    }

    private Object peekStack() {
        return this.stack[this.stackSize - 1];
    }

    private Object popStack() {
        Object[] objArr = this.stack;
        int i2 = this.stackSize - 1;
        this.stackSize = i2;
        Object obj = objArr[i2];
        objArr[i2] = null;
        return obj;
    }

    private void push(Object obj) {
        int i2 = this.stackSize;
        Object[] objArr = this.stack;
        if (i2 == objArr.length) {
            int i3 = i2 * 2;
            this.stack = Arrays.copyOf(objArr, i3);
            this.pathIndices = Arrays.copyOf(this.pathIndices, i3);
            this.pathNames = (String[]) Arrays.copyOf(this.pathNames, i3);
        }
        Object[] objArr2 = this.stack;
        int i4 = this.stackSize;
        this.stackSize = i4 + 1;
        objArr2[i4] = obj;
    }

    @Override // com.google.json.stream.JsonReader
    public void beginArray() {
        expect(JsonToken.BEGIN_ARRAY);
        push(((JsonArray) peekStack()).iterator());
        this.pathIndices[this.stackSize - 1] = 0;
    }

    @Override // com.google.json.stream.JsonReader
    public void beginObject() {
        expect(JsonToken.BEGIN_OBJECT);
        push(((JsonObject) peekStack()).entrySet().iterator());
    }

    @Override // com.google.json.stream.JsonReader, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        this.stack = new Object[]{SENTINEL_CLOSED};
        this.stackSize = 1;
    }

    @Override // com.google.json.stream.JsonReader
    public void endArray() {
        expect(JsonToken.END_ARRAY);
        popStack();
        popStack();
        int i2 = this.stackSize;
        if (i2 > 0) {
            int[] iArr = this.pathIndices;
            int i3 = i2 - 1;
            iArr[i3] = iArr[i3] + 1;
        }
    }

    @Override // com.google.json.stream.JsonReader
    public void endObject() {
        expect(JsonToken.END_OBJECT);
        this.pathNames[this.stackSize - 1] = null;
        popStack();
        popStack();
        int i2 = this.stackSize;
        if (i2 > 0) {
            int[] iArr = this.pathIndices;
            int i3 = i2 - 1;
            iArr[i3] = iArr[i3] + 1;
        }
    }

    @Override // com.google.json.stream.JsonReader
    public String getPath() {
        return getPath(false);
    }

    @Override // com.google.json.stream.JsonReader
    public String getPreviousPath() {
        return getPath(true);
    }

    @Override // com.google.json.stream.JsonReader
    public boolean hasNext() {
        JsonToken peek = peek();
        return (peek == JsonToken.END_OBJECT || peek == JsonToken.END_ARRAY || peek == JsonToken.END_DOCUMENT) ? false : true;
    }

    @Override // com.google.json.stream.JsonReader
    public boolean nextBoolean() {
        expect(JsonToken.BOOLEAN);
        boolean asBoolean = ((JsonPrimitive) popStack()).getAsBoolean();
        int i2 = this.stackSize;
        if (i2 > 0) {
            int[] iArr = this.pathIndices;
            int i3 = i2 - 1;
            iArr[i3] = iArr[i3] + 1;
        }
        return asBoolean;
    }

    @Override // com.google.json.stream.JsonReader
    public double nextDouble() {
        JsonToken peek = peek();
        JsonToken jsonToken = JsonToken.NUMBER;
        if (peek != jsonToken && peek != JsonToken.STRING) {
            throw new IllegalStateException("Expected " + jsonToken + " but was " + peek + locationString());
        }
        double asDouble = ((JsonPrimitive) peekStack()).getAsDouble();
        if (!isLenient() && (Double.isNaN(asDouble) || Double.isInfinite(asDouble))) {
            throw new MalformedJsonException("JSON forbids NaN and infinities: " + asDouble);
        }
        popStack();
        int i2 = this.stackSize;
        if (i2 > 0) {
            int[] iArr = this.pathIndices;
            int i3 = i2 - 1;
            iArr[i3] = iArr[i3] + 1;
        }
        return asDouble;
    }

    @Override // com.google.json.stream.JsonReader
    public int nextInt() {
        JsonToken peek = peek();
        JsonToken jsonToken = JsonToken.NUMBER;
        if (peek != jsonToken && peek != JsonToken.STRING) {
            throw new IllegalStateException("Expected " + jsonToken + " but was " + peek + locationString());
        }
        int asInt = ((JsonPrimitive) peekStack()).getAsInt();
        popStack();
        int i2 = this.stackSize;
        if (i2 > 0) {
            int[] iArr = this.pathIndices;
            int i3 = i2 - 1;
            iArr[i3] = iArr[i3] + 1;
        }
        return asInt;
    }

    public JsonElement nextJsonElement() {
        JsonToken peek = peek();
        if (peek != JsonToken.NAME && peek != JsonToken.END_ARRAY && peek != JsonToken.END_OBJECT && peek != JsonToken.END_DOCUMENT) {
            JsonElement jsonElement = (JsonElement) peekStack();
            skipValue();
            return jsonElement;
        }
        throw new IllegalStateException("Unexpected " + peek + " when reading a JsonElement.");
    }

    @Override // com.google.json.stream.JsonReader
    public long nextLong() {
        JsonToken peek = peek();
        JsonToken jsonToken = JsonToken.NUMBER;
        if (peek != jsonToken && peek != JsonToken.STRING) {
            throw new IllegalStateException("Expected " + jsonToken + " but was " + peek + locationString());
        }
        long asLong = ((JsonPrimitive) peekStack()).getAsLong();
        popStack();
        int i2 = this.stackSize;
        if (i2 > 0) {
            int[] iArr = this.pathIndices;
            int i3 = i2 - 1;
            iArr[i3] = iArr[i3] + 1;
        }
        return asLong;
    }

    @Override // com.google.json.stream.JsonReader
    public String nextName() {
        return nextName(false);
    }

    @Override // com.google.json.stream.JsonReader
    public void nextNull() {
        expect(JsonToken.NULL);
        popStack();
        int i2 = this.stackSize;
        if (i2 > 0) {
            int[] iArr = this.pathIndices;
            int i3 = i2 - 1;
            iArr[i3] = iArr[i3] + 1;
        }
    }

    @Override // com.google.json.stream.JsonReader
    public String nextString() {
        JsonToken peek = peek();
        JsonToken jsonToken = JsonToken.STRING;
        if (peek != jsonToken && peek != JsonToken.NUMBER) {
            throw new IllegalStateException("Expected " + jsonToken + " but was " + peek + locationString());
        }
        String asString = ((JsonPrimitive) popStack()).getAsString();
        int i2 = this.stackSize;
        if (i2 > 0) {
            int[] iArr = this.pathIndices;
            int i3 = i2 - 1;
            iArr[i3] = iArr[i3] + 1;
        }
        return asString;
    }

    @Override // com.google.json.stream.JsonReader
    public JsonToken peek() {
        if (this.stackSize == 0) {
            return JsonToken.END_DOCUMENT;
        }
        Object peekStack = peekStack();
        if (peekStack instanceof Iterator) {
            boolean z2 = this.stack[this.stackSize - 2] instanceof JsonObject;
            Iterator it = (Iterator) peekStack;
            if (!it.hasNext()) {
                return z2 ? JsonToken.END_OBJECT : JsonToken.END_ARRAY;
            }
            if (z2) {
                return JsonToken.NAME;
            }
            push(it.next());
            return peek();
        }
        if (peekStack instanceof JsonObject) {
            return JsonToken.BEGIN_OBJECT;
        }
        if (peekStack instanceof JsonArray) {
            return JsonToken.BEGIN_ARRAY;
        }
        if (peekStack instanceof JsonPrimitive) {
            JsonPrimitive jsonPrimitive = (JsonPrimitive) peekStack;
            if (jsonPrimitive.isString()) {
                return JsonToken.STRING;
            }
            if (jsonPrimitive.isBoolean()) {
                return JsonToken.BOOLEAN;
            }
            if (jsonPrimitive.isNumber()) {
                return JsonToken.NUMBER;
            }
            throw new AssertionError();
        }
        if (peekStack instanceof JsonNull) {
            return JsonToken.NULL;
        }
        if (peekStack == SENTINEL_CLOSED) {
            throw new IllegalStateException("JsonReader is closed");
        }
        throw new MalformedJsonException("Custom JsonElement subclass " + peekStack.getClass().getName() + " is not supported");
    }

    public void promoteNameToValue() {
        expect(JsonToken.NAME);
        Map.Entry entry = (Map.Entry) ((Iterator) peekStack()).next();
        push(entry.getValue());
        push(new JsonPrimitive((String) entry.getKey()));
    }

    @Override // com.google.json.stream.JsonReader
    public void skipValue() {
        int i2 = C01512.$SwitchMap$com$google$json$stream$JsonToken[peek().ordinal()];
        if (i2 == 1) {
            nextName(true);
            return;
        }
        if (i2 == 2) {
            endArray();
            return;
        }
        if (i2 == 3) {
            endObject();
            return;
        }
        if (i2 != 4) {
            popStack();
            int i3 = this.stackSize;
            if (i3 > 0) {
                int[] iArr = this.pathIndices;
                int i4 = i3 - 1;
                iArr[i4] = iArr[i4] + 1;
            }
        }
    }

    @Override // com.google.json.stream.JsonReader
    public String toString() {
        return "JsonTreeReader" + locationString();
    }

    private String getPath(boolean z2) {
        StringBuilder sb = new StringBuilder("$");
        int i2 = 0;
        while (true) {
            int i3 = this.stackSize;
            if (i2 >= i3) {
                return sb.toString();
            }
            Object[] objArr = this.stack;
            Object obj = objArr[i2];
            if (obj instanceof JsonArray) {
                i2++;
                if (i2 < i3 && (objArr[i2] instanceof Iterator)) {
                    int i4 = this.pathIndices[i2];
                    if (z2 && i4 > 0 && (i2 == i3 - 1 || i2 == i3 - 2)) {
                        i4--;
                    }
                    sb.append('[');
                    sb.append(i4);
                    sb.append(']');
                }
            } else if ((obj instanceof JsonObject) && (i2 = i2 + 1) < i3 && (objArr[i2] instanceof Iterator)) {
                sb.append('.');
                String str = this.pathNames[i2];
                if (str != null) {
                    sb.append(str);
                }
            }
            i2++;
        }
    }

    private String nextName(boolean z2) {
        expect(JsonToken.NAME);
        Map.Entry entry = (Map.Entry) ((Iterator) peekStack()).next();
        String str = (String) entry.getKey();
        this.pathNames[this.stackSize - 1] = z2 ? "<skipped>" : str;
        push(entry.getValue());
        return str;
    }
}
