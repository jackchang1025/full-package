package com.google.json.internal.bind;

import com.google.json.Gson;
import com.google.json.JsonSyntaxException;
import com.google.json.ToNumberPolicy;
import com.google.json.ToNumberStrategy;
import com.google.json.TypeAdapter;
import com.google.json.TypeAdapterFactory;
import com.google.json.reflect.TypeToken;
import com.google.json.stream.JsonReader;
import com.google.json.stream.JsonToken;
import com.google.json.stream.JsonWriter;

/* loaded from: classes.dex */
public final class NumberTypeAdapter extends TypeAdapter<Number> {
    private static final TypeAdapterFactory LAZILY_PARSED_NUMBER_FACTORY = newFactory(ToNumberPolicy.LAZILY_PARSED_NUMBER);
    private final ToNumberStrategy toNumberStrategy;

    /* renamed from: com.google.json.internal.bind.NumberTypeAdapter$2 */
    public static /* synthetic */ class C01542 {
        static final /* synthetic */ int[] $SwitchMap$com$google$json$stream$JsonToken;

        static {
            int[] iArr = new int[JsonToken.values().length];
            $SwitchMap$com$google$json$stream$JsonToken = iArr;
            try {
                iArr[JsonToken.NULL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$google$json$stream$JsonToken[JsonToken.NUMBER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$google$json$stream$JsonToken[JsonToken.STRING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    private NumberTypeAdapter(ToNumberStrategy toNumberStrategy) {
        this.toNumberStrategy = toNumberStrategy;
    }

    public static TypeAdapterFactory getFactory(ToNumberStrategy toNumberStrategy) {
        return toNumberStrategy == ToNumberPolicy.LAZILY_PARSED_NUMBER ? LAZILY_PARSED_NUMBER_FACTORY : newFactory(toNumberStrategy);
    }

    private static TypeAdapterFactory newFactory(ToNumberStrategy toNumberStrategy) {
        return new TypeAdapterFactory() { // from class: com.google.json.internal.bind.NumberTypeAdapter.1
            @Override // com.google.json.TypeAdapterFactory
            public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
                if (typeToken.getRawType() == Number.class) {
                    return NumberTypeAdapter.this;
                }
                return null;
            }
        };
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.google.json.TypeAdapter
    public Number read(JsonReader jsonReader) {
        JsonToken peek = jsonReader.peek();
        int i2 = C01542.$SwitchMap$com$google$json$stream$JsonToken[peek.ordinal()];
        if (i2 == 1) {
            jsonReader.nextNull();
            return null;
        }
        if (i2 == 2 || i2 == 3) {
            return this.toNumberStrategy.readNumber(jsonReader);
        }
        throw new JsonSyntaxException("Expecting number, got: " + peek + "; at path " + jsonReader.getPath());
    }

    @Override // com.google.json.TypeAdapter
    public void write(JsonWriter jsonWriter, Number number) {
        jsonWriter.value(number);
    }
}
