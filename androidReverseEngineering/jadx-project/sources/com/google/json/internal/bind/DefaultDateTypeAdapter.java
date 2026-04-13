package com.google.json.internal.bind;

import com.google.json.JsonSyntaxException;
import com.google.json.TypeAdapter;
import com.google.json.TypeAdapterFactory;
import com.google.json.internal.JavaVersion;
import com.google.json.internal.PreJava9DateFormatProvider;
import com.google.json.internal.bind.util.ISO8601Utils;
import com.google.json.stream.JsonReader;
import com.google.json.stream.JsonToken;
import com.google.json.stream.JsonWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public final class DefaultDateTypeAdapter<T extends Date> extends TypeAdapter<T> {
    private static final String SIMPLE_NAME = "DefaultDateTypeAdapter";
    private final List<DateFormat> dateFormats;
    private final DateType<T> dateType;

    public static abstract class DateType<T extends Date> {
        public static final DateType<Date> DATE = new DateType<Date>(Date.class) { // from class: com.google.json.internal.bind.DefaultDateTypeAdapter.DateType.1
            @Override // com.google.json.internal.bind.DefaultDateTypeAdapter.DateType
            public Date deserialize(Date date) {
                return date;
            }
        };
        private final Class<T> dateClass;

        public DateType(Class<T> cls) {
            this.dateClass = cls;
        }

        private TypeAdapterFactory createFactory(DefaultDateTypeAdapter<T> defaultDateTypeAdapter) {
            return TypeAdapters.newFactory(this.dateClass, defaultDateTypeAdapter);
        }

        public final TypeAdapterFactory createAdapterFactory(int i2) {
            return createFactory(new DefaultDateTypeAdapter<>(this, i2));
        }

        public final TypeAdapterFactory createDefaultsAdapterFactory() {
            int i2 = 2;
            return createFactory(new DefaultDateTypeAdapter<>(this, i2, i2));
        }

        public abstract T deserialize(Date date);

        public final TypeAdapterFactory createAdapterFactory(int i2, int i3) {
            return createFactory(new DefaultDateTypeAdapter<>(this, i2, i3));
        }

        public final TypeAdapterFactory createAdapterFactory(String str) {
            return createFactory(new DefaultDateTypeAdapter<>(this, str));
        }
    }

    private DefaultDateTypeAdapter(DateType<T> dateType, int i2) {
        ArrayList arrayList = new ArrayList();
        this.dateFormats = arrayList;
        Objects.requireNonNull(dateType);
        this.dateType = dateType;
        Locale locale = Locale.US;
        arrayList.add(DateFormat.getDateInstance(i2, locale));
        if (!Locale.getDefault().equals(locale)) {
            arrayList.add(DateFormat.getDateInstance(i2));
        }
        if (JavaVersion.isJava9OrLater()) {
            arrayList.add(PreJava9DateFormatProvider.getUSDateFormat(i2));
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

    public String toString() {
        StringBuilder sb;
        String simpleName;
        DateFormat dateFormat = this.dateFormats.get(0);
        if (dateFormat instanceof SimpleDateFormat) {
            sb = new StringBuilder("DefaultDateTypeAdapter(");
            simpleName = ((SimpleDateFormat) dateFormat).toPattern();
        } else {
            sb = new StringBuilder("DefaultDateTypeAdapter(");
            simpleName = dateFormat.getClass().getSimpleName();
        }
        sb.append(simpleName);
        sb.append(')');
        return sb.toString();
    }

    private DefaultDateTypeAdapter(DateType<T> dateType, int i2, int i3) {
        ArrayList arrayList = new ArrayList();
        this.dateFormats = arrayList;
        Objects.requireNonNull(dateType);
        this.dateType = dateType;
        Locale locale = Locale.US;
        arrayList.add(DateFormat.getDateTimeInstance(i2, i3, locale));
        if (!Locale.getDefault().equals(locale)) {
            arrayList.add(DateFormat.getDateTimeInstance(i2, i3));
        }
        if (JavaVersion.isJava9OrLater()) {
            arrayList.add(PreJava9DateFormatProvider.getUSDateTimeFormat(i2, i3));
        }
    }

    @Override // com.google.json.TypeAdapter
    public T read(JsonReader jsonReader) {
        if (jsonReader.peek() == JsonToken.NULL) {
            jsonReader.nextNull();
            return null;
        }
        return this.dateType.deserialize(deserializeToDate(jsonReader));
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

    private DefaultDateTypeAdapter(DateType<T> dateType, String str) {
        ArrayList arrayList = new ArrayList();
        this.dateFormats = arrayList;
        Objects.requireNonNull(dateType);
        this.dateType = dateType;
        Locale locale = Locale.US;
        arrayList.add(new SimpleDateFormat(str, locale));
        if (Locale.getDefault().equals(locale)) {
            return;
        }
        arrayList.add(new SimpleDateFormat(str));
    }
}
