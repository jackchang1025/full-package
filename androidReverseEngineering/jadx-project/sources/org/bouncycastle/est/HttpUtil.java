package org.bouncycastle.est;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
class HttpUtil {

    public static class Headers extends HashMap<String, String[]> {
        private String actualKey(String str) {
            if (containsKey(str)) {
                return str;
            }
            for (String str2 : keySet()) {
                if (str.equalsIgnoreCase(str2)) {
                    return str2;
                }
            }
            return null;
        }

        private String[] copy(String[] strArr) {
            int length = strArr.length;
            String[] strArr2 = new String[length];
            System.arraycopy(strArr, 0, strArr2, 0, length);
            return strArr2;
        }

        private boolean hasHeader(String str) {
            return actualKey(str) != null;
        }

        public void add(String str, String str2) {
            put(str, HttpUtil.append(get(str), str2));
        }

        @Override // java.util.HashMap, java.util.AbstractMap
        public Object clone() {
            Headers headers = new Headers();
            for (Map.Entry<String, String[]> entry : entrySet()) {
                headers.put(entry.getKey(), copy(entry.getValue()));
            }
            return headers;
        }

        public void ensureHeader(String str, String str2) {
            if (containsKey(str)) {
                return;
            }
            set(str, str2);
        }

        public String getFirstValue(String str) {
            String[] values = getValues(str);
            if (values == null || values.length <= 0) {
                return null;
            }
            return values[0];
        }

        public String[] getValues(String str) {
            String actualKey = actualKey(str);
            if (actualKey == null) {
                return null;
            }
            return get(actualKey);
        }

        public void set(String str, String str2) {
            put(str, new String[]{str2});
        }
    }

    public static class PartLexer {
        int last = 0;

        /* renamed from: p */
        int f1349p = 0;
        private final String src;

        public PartLexer(String str) {
            this.src = str;
        }

        private String consumeAlpha() {
            char charAt = this.src.charAt(this.f1349p);
            while (this.f1349p < this.src.length() && ((charAt >= 'a' && charAt <= 'z') || (charAt >= 'A' && charAt <= 'Z'))) {
                int i2 = this.f1349p + 1;
                this.f1349p = i2;
                charAt = this.src.charAt(i2);
            }
            String substring = this.src.substring(this.last, this.f1349p);
            this.last = this.f1349p;
            return substring;
        }

        private boolean consumeIf(char c) {
            if (this.f1349p >= this.src.length() || this.src.charAt(this.f1349p) != c) {
                return false;
            }
            this.f1349p++;
            return true;
        }

        private String consumeUntil(char c) {
            while (this.f1349p < this.src.length() && this.src.charAt(this.f1349p) != c) {
                this.f1349p++;
            }
            String substring = this.src.substring(this.last, this.f1349p);
            this.last = this.f1349p;
            return substring;
        }

        private void discard() {
            this.last = this.f1349p;
        }

        private void skipWhiteSpace() {
            while (this.f1349p < this.src.length() && this.src.charAt(this.f1349p) < '!') {
                this.f1349p++;
            }
            this.last = this.f1349p;
        }

        public Map<String, String> Parse() {
            HashMap hashMap = new HashMap();
            while (this.f1349p < this.src.length()) {
                skipWhiteSpace();
                String consumeAlpha = consumeAlpha();
                if (consumeAlpha.length() == 0) {
                    throw new IllegalArgumentException("Expecting alpha label.");
                }
                skipWhiteSpace();
                if (!consumeIf('=')) {
                    throw new IllegalArgumentException("Expecting assign: '='");
                }
                skipWhiteSpace();
                if (!consumeIf('\"')) {
                    throw new IllegalArgumentException("Expecting start quote: '\"'");
                }
                discard();
                String consumeUntil = consumeUntil('\"');
                discard(1);
                hashMap.put(consumeAlpha, consumeUntil);
                skipWhiteSpace();
                if (!consumeIf(',')) {
                    break;
                }
                discard();
            }
            return hashMap;
        }

        private void discard(int i2) {
            int i3 = this.f1349p + i2;
            this.f1349p = i3;
            this.last = i3;
        }
    }

    public static String[] append(String[] strArr, String str) {
        if (strArr == null) {
            return new String[]{str};
        }
        int length = strArr.length;
        String[] strArr2 = new String[length + 1];
        System.arraycopy(strArr, 0, strArr2, 0, length);
        strArr2[length] = str;
        return strArr2;
    }

    public static String mergeCSL(String str, Map<String, String> map) {
        StringWriter stringWriter = new StringWriter();
        stringWriter.write(str);
        stringWriter.write(32);
        boolean z2 = false;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (z2) {
                stringWriter.write(44);
            } else {
                z2 = true;
            }
            stringWriter.write(entry.getKey());
            stringWriter.write("=\"");
            stringWriter.write(entry.getValue());
            stringWriter.write(34);
        }
        return stringWriter.toString();
    }

    public static Map<String, String> splitCSL(String str, String str2) {
        String trim = str2.trim();
        if (trim.startsWith(str)) {
            trim = trim.substring(str.length());
        }
        return new PartLexer(trim).Parse();
    }
}
