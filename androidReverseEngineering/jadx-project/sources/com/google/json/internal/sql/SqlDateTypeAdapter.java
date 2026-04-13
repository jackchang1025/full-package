package com.google.json.internal.sql;

import com.google.json.Gson;
import com.google.json.JsonSyntaxException;
import com.google.json.TypeAdapter;
import com.google.json.TypeAdapterFactory;
import com.google.json.reflect.TypeToken;
import com.google.json.stream.JsonReader;
import com.google.json.stream.JsonToken;
import com.google.json.stream.JsonWriter;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
final class SqlDateTypeAdapter extends TypeAdapter<Date> {
    static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() { // from class: com.google.json.internal.sql.SqlDateTypeAdapter.1
        @Override // com.google.json.TypeAdapterFactory
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
            if (typeToken.getRawType() == Date.class) {
                return new SqlDateTypeAdapter();
            }
            return null;
        }
    };
    private final DateFormat format;

    private SqlDateTypeAdapter() {
        this.format = new SimpleDateFormat("MMM d, yyyy");
    }

    @Override // com.google.json.TypeAdapter
    public Date read(JsonReader jsonReader) {
        java.util.Date parse;
        if (jsonReader.peek() == JsonToken.NULL) {
            jsonReader.nextNull();
            return null;
        }
        String nextString = jsonReader.nextString();
        try {
            synchronized (this) {
                parse = this.format.parse(nextString);
            }
            return new Date(parse.getTime());
        } catch (ParseException e2) {
            StringBuilder m23s = AbstractC0000a.m23s("Failed parsing '", nextString, "' as SQL Date; at path ");
            m23s.append(jsonReader.getPreviousPath());
            throw new JsonSyntaxException(m23s.toString(), e2);
        }
    }

    @Override // com.google.json.TypeAdapter
    public void write(JsonWriter jsonWriter, Date date) {
        String format;
        if (date == null) {
            jsonWriter.nullValue();
            return;
        }
        synchronized (this) {
            format = this.format.format((java.util.Date) date);
        }
        jsonWriter.value(format);
    }
}
