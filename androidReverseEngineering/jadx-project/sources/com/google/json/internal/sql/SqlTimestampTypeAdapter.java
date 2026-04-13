package com.google.json.internal.sql;

import com.google.json.Gson;
import com.google.json.TypeAdapter;
import com.google.json.TypeAdapterFactory;
import com.google.json.reflect.TypeToken;
import com.google.json.stream.JsonReader;
import com.google.json.stream.JsonWriter;
import java.sql.Timestamp;
import java.util.Date;

/* loaded from: classes.dex */
class SqlTimestampTypeAdapter extends TypeAdapter<Timestamp> {
    static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() { // from class: com.google.json.internal.sql.SqlTimestampTypeAdapter.1
        @Override // com.google.json.TypeAdapterFactory
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
            if (typeToken.getRawType() == Timestamp.class) {
                return new SqlTimestampTypeAdapter(gson.getAdapter(Date.class));
            }
            return null;
        }
    };
    private final TypeAdapter<Date> dateTypeAdapter;

    private SqlTimestampTypeAdapter(TypeAdapter<Date> typeAdapter) {
        this.dateTypeAdapter = typeAdapter;
    }

    @Override // com.google.json.TypeAdapter
    public Timestamp read(JsonReader jsonReader) {
        Date read = this.dateTypeAdapter.read(jsonReader);
        if (read != null) {
            return new Timestamp(read.getTime());
        }
        return null;
    }

    @Override // com.google.json.TypeAdapter
    public void write(JsonWriter jsonWriter, Timestamp timestamp) {
        this.dateTypeAdapter.write(jsonWriter, timestamp);
    }
}
