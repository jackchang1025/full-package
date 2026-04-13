package com.google.json.internal.bind;

import com.google.json.Gson;
import com.google.json.JsonElement;
import com.google.json.JsonPrimitive;
import com.google.json.JsonSyntaxException;
import com.google.json.TypeAdapter;
import com.google.json.TypeAdapterFactory;
import com.google.json.internal.C$Gson$Types;
import com.google.json.internal.ConstructorConstructor;
import com.google.json.internal.JsonReaderInternalAccess;
import com.google.json.internal.ObjectConstructor;
import com.google.json.internal.Streams;
import com.google.json.reflect.TypeToken;
import com.google.json.stream.JsonReader;
import com.google.json.stream.JsonToken;
import com.google.json.stream.JsonWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

/* loaded from: classes.dex */
public final class MapTypeAdapterFactory implements TypeAdapterFactory {
    final boolean complexMapKeySerialization;
    private final ConstructorConstructor constructorConstructor;

    public final class Adapter<K, V> extends TypeAdapter<Map<K, V>> {
        private final ObjectConstructor<? extends Map<K, V>> constructor;
        private final TypeAdapter<K> keyTypeAdapter;
        private final TypeAdapter<V> valueTypeAdapter;

        public Adapter(Gson gson, Type type, TypeAdapter<K> typeAdapter, Type type2, TypeAdapter<V> typeAdapter2, ObjectConstructor<? extends Map<K, V>> objectConstructor) {
            this.keyTypeAdapter = new TypeAdapterRuntimeTypeWrapper(gson, typeAdapter, type);
            this.valueTypeAdapter = new TypeAdapterRuntimeTypeWrapper(gson, typeAdapter2, type2);
            this.constructor = objectConstructor;
        }

        private String keyToString(JsonElement jsonElement) {
            if (!jsonElement.isJsonPrimitive()) {
                if (jsonElement.isJsonNull()) {
                    return "null";
                }
                throw new AssertionError();
            }
            JsonPrimitive asJsonPrimitive = jsonElement.getAsJsonPrimitive();
            if (asJsonPrimitive.isNumber()) {
                return String.valueOf(asJsonPrimitive.getAsNumber());
            }
            if (asJsonPrimitive.isBoolean()) {
                return Boolean.toString(asJsonPrimitive.getAsBoolean());
            }
            if (asJsonPrimitive.isString()) {
                return asJsonPrimitive.getAsString();
            }
            throw new AssertionError();
        }

        @Override // com.google.json.TypeAdapter
        public Map<K, V> read(JsonReader jsonReader) {
            JsonToken peek = jsonReader.peek();
            if (peek == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            Map<K, V> construct = this.constructor.construct();
            if (peek == JsonToken.BEGIN_ARRAY) {
                jsonReader.beginArray();
                while (jsonReader.hasNext()) {
                    jsonReader.beginArray();
                    K read = this.keyTypeAdapter.read(jsonReader);
                    if (construct.put(read, this.valueTypeAdapter.read(jsonReader)) != null) {
                        throw new JsonSyntaxException("duplicate key: " + read);
                    }
                    jsonReader.endArray();
                }
                jsonReader.endArray();
            } else {
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {
                    JsonReaderInternalAccess.INSTANCE.promoteNameToValue(jsonReader);
                    K read2 = this.keyTypeAdapter.read(jsonReader);
                    if (construct.put(read2, this.valueTypeAdapter.read(jsonReader)) != null) {
                        throw new JsonSyntaxException("duplicate key: " + read2);
                    }
                }
                jsonReader.endObject();
            }
            return construct;
        }

        @Override // com.google.json.TypeAdapter
        public void write(JsonWriter jsonWriter, Map<K, V> map) {
            if (map == null) {
                jsonWriter.nullValue();
                return;
            }
            if (!MapTypeAdapterFactory.this.complexMapKeySerialization) {
                jsonWriter.beginObject();
                for (Map.Entry<K, V> entry : map.entrySet()) {
                    jsonWriter.name(String.valueOf(entry.getKey()));
                    this.valueTypeAdapter.write(jsonWriter, entry.getValue());
                }
                jsonWriter.endObject();
                return;
            }
            ArrayList arrayList = new ArrayList(map.size());
            ArrayList arrayList2 = new ArrayList(map.size());
            int i2 = 0;
            boolean z2 = false;
            for (Map.Entry<K, V> entry2 : map.entrySet()) {
                JsonElement jsonTree = this.keyTypeAdapter.toJsonTree(entry2.getKey());
                arrayList.add(jsonTree);
                arrayList2.add(entry2.getValue());
                z2 |= jsonTree.isJsonArray() || jsonTree.isJsonObject();
            }
            if (!z2) {
                jsonWriter.beginObject();
                int size = arrayList.size();
                while (i2 < size) {
                    jsonWriter.name(keyToString((JsonElement) arrayList.get(i2)));
                    this.valueTypeAdapter.write(jsonWriter, arrayList2.get(i2));
                    i2++;
                }
                jsonWriter.endObject();
                return;
            }
            jsonWriter.beginArray();
            int size2 = arrayList.size();
            while (i2 < size2) {
                jsonWriter.beginArray();
                Streams.write((JsonElement) arrayList.get(i2), jsonWriter);
                this.valueTypeAdapter.write(jsonWriter, arrayList2.get(i2));
                jsonWriter.endArray();
                i2++;
            }
            jsonWriter.endArray();
        }
    }

    public MapTypeAdapterFactory(ConstructorConstructor constructorConstructor, boolean z2) {
        this.constructorConstructor = constructorConstructor;
        this.complexMapKeySerialization = z2;
    }

    private TypeAdapter<?> getKeyAdapter(Gson gson, Type type) {
        return (type == Boolean.TYPE || type == Boolean.class) ? TypeAdapters.BOOLEAN_AS_STRING : gson.getAdapter(TypeToken.get(type));
    }

    @Override // com.google.json.TypeAdapterFactory
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
        Type type = typeToken.getType();
        Class<? super T> rawType = typeToken.getRawType();
        if (!Map.class.isAssignableFrom(rawType)) {
            return null;
        }
        Type[] mapKeyAndValueTypes = C$Gson$Types.getMapKeyAndValueTypes(type, rawType);
        return new Adapter(gson, mapKeyAndValueTypes[0], getKeyAdapter(gson, mapKeyAndValueTypes[0]), mapKeyAndValueTypes[1], gson.getAdapter(TypeToken.get(mapKeyAndValueTypes[1])), this.constructorConstructor.get(typeToken));
    }
}
