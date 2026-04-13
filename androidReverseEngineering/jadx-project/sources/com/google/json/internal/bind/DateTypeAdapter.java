package com.google.json.internal.bind;

import com.google.json.Gson;
import com.google.json.JsonSyntaxException;
import com.google.json.TypeAdapter;
import com.google.json.TypeAdapterFactory;
import com.google.json.internal.JavaVersion;
import com.google.json.internal.PreJava9DateFormatProvider;
import com.google.json.internal.bind.util.ISO8601Utils;
import com.google.json.reflect.TypeToken;
import com.google.json.stream.JsonReader;
import com.google.json.stream.JsonToken;
import com.google.json.stream.JsonWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public final class DateTypeAdapter extends TypeAdapter<Date> {
    public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() { // from class: com.google.json.internal.bind.DateTypeAdapter.1
        @Override // com.google.json.TypeAdapterFactory
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
            if (typeToken.getRawType() == Date.class) {
                return new DateTypeAdapter();
            }
            return null;
        }
    };
    private final List<DateFormat> dateFormats;

    public DateTypeAdapter() {
        ArrayList arrayList = new ArrayList();
        this.dateFormats = arrayList;
        Locale locale = Locale.US;
        arrayList.add(DateFormat.getDateTimeInstance(2, 2, locale));
        if (!Locale.getDefault().equals(locale)) {
            arrayList.add(DateFormat.getDateTimeInstance(2, 2));
        }
        if (JavaVersion.isJava9OrLater()) {
            arrayList.add(PreJava9DateFormatProvider.getUSDateTimeFormat(2, 2));
        }
    }

    private Date deserializeToDate(JsonReader jsonReader) {
        String nextString = jsonReader.nextString();
        synchronized (this.dateFormats) {
            Iterator<DateFormat> it = this.dateFormats.iterator();
            while (it.hasNext()) {
                try {
                    return it.next().parse(nextString);
                } catch (ParseException unused) {
                }
            }
            try {
                return ISO8601Utils.parse(nextString, new ParsePosition(0));
            } catch (ParseException e2) {
                StringBuilder m23s = AbstractC0000a.m23s("Failed parsing '", nextString, "' as Date; at path ");
                m23s.append(jsonReader.getPreviousPath());
                throw new JsonSyntaxException(m23s.toString(), e2);
            }
        }
    }

    @Override // com.google.json.TypeAdapter
    public Date read(JsonReader jsonReader) {
        if (jsonReader.peek() != JsonToken.NULL) {
            return deserializeToDate(jsonReader);
        }
        jsonReader.nextNull();
        return null;
    }

    @Override // com.google.json.TypeAdapter
    public void write(JsonWriter jsonWriter, Date date) {
        String format;
        if (date == null) {
            jsonWriter.nullValue();
            return;
        }
        DateFormat dateFormat = this.dateFormats.get(0);
        synchronized (this.dateFormats) {
            format = dateFormat.format(date);
        }
        jsonWriter.value(format);
    }
}
