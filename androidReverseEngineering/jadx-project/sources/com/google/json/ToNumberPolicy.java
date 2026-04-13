package com.google.json;

import com.google.json.internal.LazilyParsedNumber;
import com.google.json.stream.JsonReader;
import com.google.json.stream.MalformedJsonException;
import java.math.BigDecimal;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public enum ToNumberPolicy implements ToNumberStrategy {
    DOUBLE { // from class: com.google.json.ToNumberPolicy.1
        @Override // com.google.json.ToNumberStrategy
        public Double readNumber(JsonReader jsonReader) {
            return Double.valueOf(jsonReader.nextDouble());
        }
    },
    LAZILY_PARSED_NUMBER { // from class: com.google.json.ToNumberPolicy.2
        @Override // com.google.json.ToNumberStrategy
        public Number readNumber(JsonReader jsonReader) {
            return new LazilyParsedNumber(jsonReader.nextString());
        }
    },
    LONG_OR_DOUBLE { // from class: com.google.json.ToNumberPolicy.3
        @Override // com.google.json.ToNumberStrategy
        public Number readNumber(JsonReader jsonReader) {
            String nextString = jsonReader.nextString();
            try {
                try {
                    return Long.valueOf(Long.parseLong(nextString));
                } catch (NumberFormatException unused) {
                    Double valueOf = Double.valueOf(nextString);
                    if ((!valueOf.isInfinite() && !valueOf.isNaN()) || jsonReader.isLenient()) {
                        return valueOf;
                    }
                    throw new MalformedJsonException("JSON forbids NaN and infinities: " + valueOf + "; at path " + jsonReader.getPreviousPath());
                }
            } catch (NumberFormatException e2) {
                StringBuilder m23s = AbstractC0000a.m23s("Cannot parse ", nextString, "; at path ");
                m23s.append(jsonReader.getPreviousPath());
                throw new JsonParseException(m23s.toString(), e2);
            }
        }
    },
    BIG_DECIMAL { // from class: com.google.json.ToNumberPolicy.4
        @Override // com.google.json.ToNumberStrategy
        public BigDecimal readNumber(JsonReader jsonReader) {
            String nextString = jsonReader.nextString();
            try {
                return new BigDecimal(nextString);
            } catch (NumberFormatException e2) {
                StringBuilder m23s = AbstractC0000a.m23s("Cannot parse ", nextString, "; at path ");
                m23s.append(jsonReader.getPreviousPath());
                throw new JsonParseException(m23s.toString(), e2);
            }
        }
    }
}
