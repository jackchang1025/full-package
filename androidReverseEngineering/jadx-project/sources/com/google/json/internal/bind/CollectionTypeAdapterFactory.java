package com.google.json.internal.bind;

import com.google.json.Gson;
import com.google.json.TypeAdapter;
import com.google.json.TypeAdapterFactory;
import com.google.json.internal.C$Gson$Types;
import com.google.json.internal.ConstructorConstructor;
import com.google.json.internal.ObjectConstructor;
import com.google.json.reflect.TypeToken;
import com.google.json.stream.JsonReader;
import com.google.json.stream.JsonToken;
import com.google.json.stream.JsonWriter;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Iterator;

/* loaded from: classes.dex */
public final class CollectionTypeAdapterFactory implements TypeAdapterFactory {
    private final ConstructorConstructor constructorConstructor;

    public static final class Adapter<E> extends TypeAdapter<Collection<E>> {
        private final ObjectConstructor<? extends Collection<E>> constructor;
        private final TypeAdapter<E> elementTypeAdapter;

        public Adapter(Gson gson, Type type, TypeAdapter<E> typeAdapter, ObjectConstructor<? extends Collection<E>> objectConstructor) {
            this.elementTypeAdapter = new TypeAdapterRuntimeTypeWrapper(gson, typeAdapter, type);
            this.constructor = objectConstructor;
        }

        @Override // com.google.json.TypeAdapter
        public Collection<E> read(JsonReader jsonReader) {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            Collection<E> construct = this.constructor.construct();
            jsonReader.beginArray();
            while (jsonReader.hasNext()) {
                construct.add(this.elementTypeAdapter.read(jsonReader));
            }
            jsonReader.endArray();
            return construct;
        }

        @Override // com.google.json.TypeAdapter
        public void write(JsonWriter jsonWriter, Collection<E> collection) {
            if (collection == null) {
                jsonWriter.nullValue();
                return;
            }
            jsonWriter.beginArray();
            Iterator<E> it = collection.iterator();
            while (it.hasNext()) {
                this.elementTypeAdapter.write(jsonWriter, it.next());
            }
            jsonWriter.endArray();
        }
    }

    public CollectionTypeAdapterFactory(ConstructorConstructor constructorConstructor) {
        this.constructorConstructor = constructorConstructor;
    }

    @Override // com.google.json.TypeAdapterFactory
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
        Type type = typeToken.getType();
        Class<? super T> rawType = typeToken.getRawType();
        if (!Collection.class.isAssignableFrom(rawType)) {
            return null;
        }
        Type collectionElementType = C$Gson$Types.getCollectionElementType(type, rawType);
        return new Adapter(gson, collectionElementType, gson.getAdapter(TypeToken.get(collectionElementType)), this.constructorConstructor.get(typeToken));
    }
}
