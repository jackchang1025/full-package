package com.google.json.internal.sql;

import com.google.json.Gson;
import com.google.json.JsonSyntaxException;
import com.google.json.TypeAdapter;
import com.google.json.TypeAdapterFactory;
import com.google.json.reflect.TypeToken;
import com.google.json.stream.JsonReader;
import com.google.json.stream.JsonToken;
import com.google.json.stream.JsonWriter;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
final class SqlTimeTypeAdapter extends TypeAdapter<Time> {
    static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() { // from class: com.google.json.internal.sql.SqlTimeTypeAdapter.1
        @Override // com.google.json.TypeAdapterFactory
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
            if (typeToken.getRawType() == Time.class) {
                return new SqlTimeTypeAdapter();
            }
            return null;
        }
    };
    private final DateFormat format;

    private SqlTimeTypeAdapter() {
        this.format = new SimpleDateFormat("hh:mm:ss a");
    }

    @Override // com.google.json.TypeAdapter
    public Time read(JsonReader jsonReader) {
        Time time;
        if (jsonReader.peek() == JsonToken.NULL) {
            jsonReader.nextNull();
            return null;
        }
        String nextString = jsonReader.nextString();
        try {
            synchronized (this) {
                time = new Time(this.format.parse(nextString).getTime());
            }
            return time;
        } catch (ParseException e2) {
            StringBuilder m23s = AbstractC0000a.m23s("Failed parsing '", nextString, "' as SQL Time; at path ");
            m23s.append(jsonReader.getPreviousPath());
            throw new JsonSyntaxException(m23s.toString(), e2);
        }
    }

    @Override // com.google.json.TypeAdapter
    public void write(JsonWriter jsonWriter, Time time) {
        String format;
        if (time == null) {
            jsonWriter.nullValue();
            return;
        }
        synchronized (this) {
            format = this.format.format((Date) time);
        }
        jsonWriter.value(format);
    }
}
