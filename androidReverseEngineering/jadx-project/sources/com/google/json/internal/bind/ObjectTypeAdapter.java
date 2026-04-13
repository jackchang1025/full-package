package com.google.json.internal.bind;

import com.google.json.Gson;
import com.google.json.ToNumberPolicy;
import com.google.json.ToNumberStrategy;
import com.google.json.TypeAdapter;
import com.google.json.TypeAdapterFactory;
import com.google.json.internal.LinkedTreeMap;
import com.google.json.reflect.TypeToken;
import com.google.json.stream.JsonReader;
import com.google.json.stream.JsonToken;
import com.google.json.stream.JsonWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public final class ObjectTypeAdapter extends TypeAdapter<Object> {
    private static final TypeAdapterFactory DOUBLE_FACTORY = newFactory(ToNumberPolicy.DOUBLE);
    private final Gson gson;
    private final ToNumberStrategy toNumberStrategy;

    /* renamed from: com.google.json.internal.bind.ObjectTypeAdapter$2 */
    public static /* synthetic */ class C01562 {
        static final /* synthetic */ int[] $SwitchMap$com$google$json$stream$JsonToken;

        static {
            int[] iArr = new int[JsonToken.values().length];
            $SwitchMap$com$google$json$stream$JsonToken = iArr;
            try {
                iArr[JsonToken.BEGIN_ARRAY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$google$json$stream$JsonToken[JsonToken.BEGIN_OBJECT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$google$json$stream$JsonToken[JsonToken.STRING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$google$json$stream$JsonToken[JsonToken.NUMBER.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$google$json$stream$JsonToken[JsonToken.BOOLEAN.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$google$json$stream$JsonToken[JsonToken.NULL.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    private ObjectTypeAdapter(Gson gson, ToNumberStrategy toNumberStrategy) {
        this.gson = gson;
        this.toNumberStrategy = toNumberStrategy;
    }

    public static TypeAdapterFactory getFactory(ToNumberStrategy toNumberStrategy) {
        return toNumberStrategy == ToNumberPolicy.DOUBLE ? DOUBLE_FACTORY : newFactory(toNumberStrategy);
    }

    private static TypeAdapterFactory newFactory(final ToNumberStrategy toNumberStrategy) {
        return new TypeAdapterFactory() { // from class: com.google.json.internal.bind.ObjectTypeAdapter.1
            @Override // com.google.json.TypeAdapterFactory
            public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
                if (typeToken.getRawType() == Object.class) {
                    return new ObjectTypeAdapter(gson, ToNumberStrategy.this);
                }
                return null;
            }
        };
    }

    private Object readTerminal(JsonReader jsonReader, JsonToken jsonToken) {
        int i2 = C01562.$SwitchMap$com$google$json$stream$JsonToken[jsonToken.ordinal()];
        if (i2 == 3) {
            return jsonReader.nextString();
        }
        if (i2 == 4) {
            return this.toNumberStrategy.readNumber(jsonReader);
        }
        if (i2 == 5) {
            return Boolean.valueOf(jsonReader.nextBoolean());
        }
        if (i2 == 6) {
            jsonReader.nextNull();
            return null;
        }
        throw new IllegalStateException("Unexpected token: " + jsonToken);
    }

    private Object tryBeginNesting(JsonReader jsonReader, JsonToken jsonToken) {
        int i2 = C01562.$SwitchMap$com$google$json$stream$JsonToken[jsonToken.ordinal()];
        if (i2 == 1) {
            jsonReader.beginArray();
            return new ArrayList();
        }
        if (i2 != 2) {
            return null;
        }
        jsonReader.beginObject();
        return new LinkedTreeMap();
    }

    @Override // com.google.json.TypeAdapter
    public Object read(JsonReader jsonReader) {
        JsonToken peek = jsonReader.peek();
        Object tryBeginNesting = tryBeginNesting(jsonReader, peek);
        if (tryBeginNesting == null) {
            return readTerminal(jsonReader, peek);
        }
        ArrayDeque arrayDeque = new ArrayDeque();
        while (true) {
            if (jsonReader.hasNext()) {
                String nextName = tryBeginNesting instanceof Map ? jsonReader.nextName() : null;
                JsonToken peek2 = jsonReader.peek();
                Object tryBeginNesting2 = tryBeginNesting(jsonReader, peek2);
                boolean z2 = tryBeginNesting2 != null;
                if (tryBeginNesting2 == null) {
                    tryBeginNesting2 = readTerminal(jsonReader, peek2);
                }
                if (tryBeginNesting instanceof List) {
                    ((List) tryBeginNesting).add(tryBeginNesting2);
                } else {
                    ((Map) tryBeginNesting).put(nextName, tryBeginNesting2);
                }
                if (z2) {
                    arrayDeque.addLast(tryBeginNesting);
                    tryBeginNesting = tryBeginNesting2;
                }
            } else {
                if (tryBeginNesting instanceof List) {
                    jsonReader.endArray();
                } else {
                    jsonReader.endObject();
                }
                if (arrayDeque.isEmpty()) {
                    return tryBeginNesting;
                }
                tryBeginNesting = arrayDeque.removeLast();
            }
        }
    }

    @Override // com.google.json.TypeAdapter
    public void write(JsonWriter jsonWriter, Object obj) {
        if (obj == null) {
            jsonWriter.nullValue();
            return;
        }
        TypeAdapter adapter = this.gson.getAdapter(obj.getClass());
        if (!(adapter instanceof ObjectTypeAdapter)) {
            adapter.write(jsonWriter, obj);
        } else {
            jsonWriter.beginObject();
            jsonWriter.endObject();
        }
    }
}
