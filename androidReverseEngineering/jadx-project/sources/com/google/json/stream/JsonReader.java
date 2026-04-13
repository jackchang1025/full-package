package com.google.json.stream;

import com.google.json.internal.JsonReaderInternalAccess;
import com.google.json.internal.bind.JsonTreeReader;
import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.Objects;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class JsonReader implements Closeable {
    static final int BUFFER_SIZE = 1024;
    private static final long MIN_INCOMPLETE_INTEGER = -922337203685477580L;
    private static final int NUMBER_CHAR_DECIMAL = 3;
    private static final int NUMBER_CHAR_DIGIT = 2;
    private static final int NUMBER_CHAR_EXP_DIGIT = 7;
    private static final int NUMBER_CHAR_EXP_E = 5;
    private static final int NUMBER_CHAR_EXP_SIGN = 6;
    private static final int NUMBER_CHAR_FRACTION_DIGIT = 4;
    private static final int NUMBER_CHAR_NONE = 0;
    private static final int NUMBER_CHAR_SIGN = 1;
    private static final int PEEKED_BEGIN_ARRAY = 3;
    private static final int PEEKED_BEGIN_OBJECT = 1;
    private static final int PEEKED_BUFFERED = 11;
    private static final int PEEKED_DOUBLE_QUOTED = 9;
    private static final int PEEKED_DOUBLE_QUOTED_NAME = 13;
    private static final int PEEKED_END_ARRAY = 4;
    private static final int PEEKED_END_OBJECT = 2;
    private static final int PEEKED_EOF = 17;
    private static final int PEEKED_FALSE = 6;
    private static final int PEEKED_LONG = 15;
    private static final int PEEKED_NONE = 0;
    private static final int PEEKED_NULL = 7;
    private static final int PEEKED_NUMBER = 16;
    private static final int PEEKED_SINGLE_QUOTED = 8;
    private static final int PEEKED_SINGLE_QUOTED_NAME = 12;
    private static final int PEEKED_TRUE = 5;
    private static final int PEEKED_UNQUOTED = 10;
    private static final int PEEKED_UNQUOTED_NAME = 14;
    private final Reader in;
    private int[] pathIndices;
    private String[] pathNames;
    private long peekedLong;
    private int peekedNumberLength;
    private String peekedString;
    private int[] stack;
    private boolean lenient = false;
    private final char[] buffer = new char[1024];
    private int pos = 0;
    private int limit = 0;
    private int lineNumber = 0;
    private int lineStart = 0;
    int peeked = 0;
    private int stackSize = 0 + 1;

    static {
        JsonReaderInternalAccess.INSTANCE = new JsonReaderInternalAccess() { // from class: com.google.json.stream.JsonReader.1
            @Override // com.google.json.internal.JsonReaderInternalAccess
            public void promoteNameToValue(JsonReader jsonReader) {
                int i2;
                if (jsonReader instanceof JsonTreeReader) {
                    ((JsonTreeReader) jsonReader).promoteNameToValue();
                    return;
                }
                int i3 = jsonReader.peeked;
                if (i3 == 0) {
                    i3 = jsonReader.doPeek();
                }
                if (i3 == 13) {
                    i2 = 9;
                } else if (i3 == 12) {
                    i2 = 8;
                } else {
                    if (i3 != 14) {
                        throw new IllegalStateException("Expected a name but was " + jsonReader.peek() + jsonReader.locationString());
                    }
                    i2 = 10;
                }
                jsonReader.peeked = i2;
            }
        };
    }

    public JsonReader(Reader reader) {
        int[] iArr = new int[32];
        this.stack = iArr;
        iArr[0] = 6;
        this.pathNames = new String[32];
        this.pathIndices = new int[32];
        Objects.requireNonNull(reader, "in == null");
        this.in = reader;
    }

    private void checkLenient() {
        if (!this.lenient) {
            throw syntaxError("Use JsonReader.setLenient(true) to accept malformed JSON");
        }
    }

    private void consumeNonExecutePrefix() {
        nextNonWhitespace(true);
        int i2 = this.pos - 1;
        this.pos = i2;
        if (i2 + 5 <= this.limit || fillBuffer(5)) {
            int i3 = this.pos;
            char[] cArr = this.buffer;
            if (cArr[i3] == ')' && cArr[i3 + 1] == ']' && cArr[i3 + 2] == '}' && cArr[i3 + 3] == '\'' && cArr[i3 + 4] == '\n') {
                this.pos = i3 + 5;
            }
        }
    }

    private boolean fillBuffer(int i2) {
        int i3;
        int i4;
        char[] cArr = this.buffer;
        int i5 = this.lineStart;
        int i6 = this.pos;
        this.lineStart = i5 - i6;
        int i7 = this.limit;
        if (i7 != i6) {
            int i8 = i7 - i6;
            this.limit = i8;
            System.arraycopy(cArr, i6, cArr, 0, i8);
        } else {
            this.limit = 0;
        }
        this.pos = 0;
        do {
            Reader reader = this.in;
            int i9 = this.limit;
            int read = reader.read(cArr, i9, cArr.length - i9);
            if (read == -1) {
                return false;
            }
            i3 = this.limit + read;
            this.limit = i3;
            if (this.lineNumber == 0 && (i4 = this.lineStart) == 0 && i3 > 0 && cArr[0] == 65279) {
                this.pos++;
                this.lineStart = i4 + 1;
                i2++;
            }
        } while (i3 < i2);
        return true;
    }

    private boolean isLiteral(char c) {
        if (c == '\t' || c == '\n' || c == '\f' || c == '\r' || c == ' ') {
            return false;
        }
        if (c != '#') {
            if (c == ',') {
                return false;
            }
            if (c != '/' && c != '=') {
                if (c == '{' || c == '}' || c == ':') {
                    return false;
                }
                if (c != ';') {
                    switch (c) {
                        case '[':
                        case ']':
                            return false;
                        case '\\':
                            break;
                        default:
                            return true;
                    }
                }
            }
        }
        checkLenient();
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x0050, code lost:
    
        if (r1 != '/') goto L55;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0052, code lost:
    
        r7.pos = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0055, code lost:
    
        if (r4 != r2) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0057, code lost:
    
        r7.pos = r4 - 1;
        r2 = fillBuffer(2);
        r7.pos++;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0064, code lost:
    
        if (r2 != false) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0066, code lost:
    
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0067, code lost:
    
        checkLenient();
        r2 = r7.pos;
        r3 = r0[r2];
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0070, code lost:
    
        if (r3 == '*') goto L38;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x007d, code lost:
    
        r7.pos = r2 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0087, code lost:
    
        if (skipTo("*\/") == false) goto L48;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0094, code lost:
    
        throw syntaxError("Unterminated comment");
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0072, code lost:
    
        if (r3 == '/') goto L36;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0075, code lost:
    
        r7.pos = r2 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x0074, code lost:
    
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x0095, code lost:
    
        r7.pos = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0099, code lost:
    
        if (r1 != '#') goto L50;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x009b, code lost:
    
        checkLenient();
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x009f, code lost:
    
        return r1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int nextNonWhitespace(boolean z2) {
        char[] cArr = this.buffer;
        while (true) {
            int i2 = this.pos;
            while (true) {
                int i3 = this.limit;
                while (true) {
                    if (i2 == i3) {
                        this.pos = i2;
                        if (!fillBuffer(1)) {
                            if (!z2) {
                                return -1;
                            }
                            throw new EOFException("End of input" + locationString());
                        }
                        i2 = this.pos;
                        i3 = this.limit;
                    }
                    int i4 = i2 + 1;
                    char c = cArr[i2];
                    if (c != '\n') {
                        if (c != ' ' && c != '\r' && c != '\t') {
                            break;
                        }
                    } else {
                        this.lineNumber++;
                        this.lineStart = i4;
                    }
                    i2 = i4;
                }
                i2 = this.pos + 2;
            }
            skipToEndOfLine();
        }
    }

    private String nextQuotedValue(char c) {
        char[] cArr = this.buffer;
        StringBuilder sb = null;
        while (true) {
            int i2 = this.pos;
            int i3 = this.limit;
            while (true) {
                if (i2 < i3) {
                    int i4 = i2 + 1;
                    char c2 = cArr[i2];
                    if (c2 == c) {
                        this.pos = i4;
                        int i5 = (i4 - i2) - 1;
                        if (sb == null) {
                            return new String(cArr, i2, i5);
                        }
                        sb.append(cArr, i2, i5);
                        return sb.toString();
                    }
                    if (c2 == '\\') {
                        this.pos = i4;
                        int i6 = (i4 - i2) - 1;
                        if (sb == null) {
                            sb = new StringBuilder(Math.max((i6 + 1) * 2, 16));
                        }
                        sb.append(cArr, i2, i6);
                        sb.append(readEscapeCharacter());
                    } else {
                        if (c2 == '\n') {
                            this.lineNumber++;
                            this.lineStart = i4;
                        }
                        i2 = i4;
                    }
                } else {
                    if (sb == null) {
                        sb = new StringBuilder(Math.max((i2 - i2) * 2, 16));
                    }
                    sb.append(cArr, i2, i2 - i2);
                    this.pos = i2;
                    if (!fillBuffer(1)) {
                        throw syntaxError("Unterminated string");
                    }
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:58:0x004a, code lost:
    
        checkLenient();
     */
    /* JADX WARN: Failed to find 'out' block for switch in B:54:0x0044. Please report as an issue. */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0080  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x008a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private String nextUnquotedValue() {
        String sb;
        StringBuilder sb2 = null;
        int i2 = 0;
        do {
            int i3 = 0;
            while (true) {
                int i4 = this.pos;
                if (i4 + i3 < this.limit) {
                    char c = this.buffer[i4 + i3];
                    if (c != '\t' && c != '\n' && c != '\f' && c != '\r' && c != ' ') {
                        if (c != '#') {
                            if (c != ',') {
                                if (c != '/' && c != '=') {
                                    if (c != '{' && c != '}' && c != ':') {
                                        if (c != ';') {
                                            switch (c) {
                                                case '[':
                                                case ']':
                                                    break;
                                                case '\\':
                                                    break;
                                                default:
                                                    i3++;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else if (i3 >= this.buffer.length) {
                    if (sb2 == null) {
                        sb2 = new StringBuilder(Math.max(i3, 16));
                    }
                    sb2.append(this.buffer, this.pos, i3);
                    this.pos += i3;
                } else if (fillBuffer(i3 + 1)) {
                }
            }
            i2 = i3;
            if (sb2 != null) {
                sb = new String(this.buffer, this.pos, i2);
            } else {
                sb2.append(this.buffer, this.pos, i2);
                sb = sb2.toString();
            }
            this.pos += i2;
            return sb;
        } while (fillBuffer(1));
        if (sb2 != null) {
        }
        this.pos += i2;
        return sb;
    }

    private int peekKeyword() {
        String str;
        String str2;
        int i2;
        char c = this.buffer[this.pos];
        if (c == 't' || c == 'T') {
            str = "true";
            str2 = "TRUE";
            i2 = 5;
        } else if (c == 'f' || c == 'F') {
            str = "false";
            str2 = "FALSE";
            i2 = 6;
        } else {
            if (c != 'n' && c != 'N') {
                return 0;
            }
            str = "null";
            str2 = "NULL";
            i2 = 7;
        }
        int length = str.length();
        for (int i3 = 1; i3 < length; i3++) {
            if (this.pos + i3 >= this.limit && !fillBuffer(i3 + 1)) {
                return 0;
            }
            char c2 = this.buffer[this.pos + i3];
            if (c2 != str.charAt(i3) && c2 != str2.charAt(i3)) {
                return 0;
            }
        }
        if ((this.pos + length < this.limit || fillBuffer(length + 1)) && isLiteral(this.buffer[this.pos + length])) {
            return 0;
        }
        this.pos += length;
        this.peeked = i2;
        return i2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0093, code lost:
    
        if (r9 != 2) goto L69;
     */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x0095, code lost:
    
        if (r10 == false) goto L69;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x009b, code lost:
    
        if (r11 != Long.MIN_VALUE) goto L60;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x009d, code lost:
    
        if (r13 == false) goto L69;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x00a3, code lost:
    
        if (r11 != 0) goto L63;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x00a5, code lost:
    
        if (r13 != false) goto L69;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x00a7, code lost:
    
        if (r13 == false) goto L65;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x00aa, code lost:
    
        r11 = -r11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x00ab, code lost:
    
        r18.peekedLong = r11;
        r18.pos += r8;
        r1 = 15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x00b4, code lost:
    
        r18.peeked = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x00b6, code lost:
    
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x00b7, code lost:
    
        if (r9 == 2) goto L77;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x00ba, code lost:
    
        if (r9 == 4) goto L77;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x00bd, code lost:
    
        if (r9 != 7) goto L75;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x00c0, code lost:
    
        return 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x00c2, code lost:
    
        r18.peekedNumberLength = r8;
        r1 = 16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x0091, code lost:
    
        if (isLiteral(r14) != false) goto L78;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x00c7, code lost:
    
        return 0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int peekNumber() {
        char c;
        char[] cArr = this.buffer;
        int i2 = this.pos;
        int i3 = this.limit;
        int i4 = 0;
        int i5 = 0;
        char c2 = 0;
        boolean z2 = false;
        boolean z3 = true;
        long j2 = 0;
        while (true) {
            if (i2 + i5 == i3) {
                if (i5 == cArr.length) {
                    return i4;
                }
                if (!fillBuffer(i5 + 1)) {
                    break;
                }
                i2 = this.pos;
                i3 = this.limit;
            }
            char c3 = cArr[i2 + i5];
            if (c3 != '+') {
                if (c3 == 'E' || c3 == 'e') {
                    i4 = 0;
                    if (c2 != 2 && c2 != 4) {
                        return 0;
                    }
                    c2 = 5;
                } else if (c3 != '-') {
                    c = 3;
                    if (c3 == '.') {
                        i4 = 0;
                        if (c2 != 2) {
                            return 0;
                        }
                    } else {
                        if (c3 < '0' || c3 > '9') {
                            break;
                        }
                        if (c2 == 1 || c2 == 0) {
                            j2 = -(c3 - '0');
                            c2 = 2;
                        } else if (c2 == 2) {
                            if (j2 == 0) {
                                return 0;
                            }
                            long j3 = (10 * j2) - (c3 - '0');
                            z3 &= j2 > MIN_INCOMPLETE_INTEGER || (j2 == MIN_INCOMPLETE_INTEGER && j3 < j2);
                            j2 = j3;
                        } else if (c2 == 3) {
                            i4 = 0;
                            c2 = 4;
                        } else if (c2 == 5 || c2 == 6) {
                            i4 = 0;
                            c2 = 7;
                        }
                        i4 = 0;
                    }
                } else {
                    c = 6;
                    i4 = 0;
                    if (c2 == 0) {
                        c2 = 1;
                        z2 = true;
                    } else if (c2 != 5) {
                        return 0;
                    }
                }
                i5++;
            } else {
                c = 6;
                i4 = 0;
                if (c2 != 5) {
                    return 0;
                }
            }
            c2 = c;
            i5++;
        }
    }

    private void push(int i2) {
        int i3 = this.stackSize;
        int[] iArr = this.stack;
        if (i3 == iArr.length) {
            int i4 = i3 * 2;
            this.stack = Arrays.copyOf(iArr, i4);
            this.pathIndices = Arrays.copyOf(this.pathIndices, i4);
            this.pathNames = (String[]) Arrays.copyOf(this.pathNames, i4);
        }
        int[] iArr2 = this.stack;
        int i5 = this.stackSize;
        this.stackSize = i5 + 1;
        iArr2[i5] = i2;
    }

    private char readEscapeCharacter() {
        int i2;
        int i3;
        if (this.pos == this.limit && !fillBuffer(1)) {
            throw syntaxError("Unterminated escape sequence");
        }
        char[] cArr = this.buffer;
        int i4 = this.pos;
        int i5 = i4 + 1;
        this.pos = i5;
        char c = cArr[i4];
        if (c == '\n') {
            this.lineNumber++;
            this.lineStart = i5;
        } else if (c != '\"' && c != '\'' && c != '/' && c != '\\') {
            if (c == 'b') {
                return '\b';
            }
            if (c == 'f') {
                return '\f';
            }
            if (c == 'n') {
                return '\n';
            }
            if (c == 'r') {
                return '\r';
            }
            if (c == 't') {
                return '\t';
            }
            if (c != 'u') {
                throw syntaxError("Invalid escape sequence");
            }
            if (i5 + 4 > this.limit && !fillBuffer(4)) {
                throw syntaxError("Unterminated escape sequence");
            }
            int i6 = this.pos;
            int i7 = i6 + 4;
            char c2 = 0;
            while (i6 < i7) {
                char c3 = this.buffer[i6];
                char c4 = (char) (c2 << 4);
                if (c3 < '0' || c3 > '9') {
                    if (c3 >= 'a' && c3 <= 'f') {
                        i2 = c3 - 'a';
                    } else {
                        if (c3 < 'A' || c3 > 'F') {
                            throw new NumberFormatException("\\u".concat(new String(this.buffer, this.pos, 4)));
                        }
                        i2 = c3 - 'A';
                    }
                    i3 = i2 + 10;
                } else {
                    i3 = c3 - '0';
                }
                c2 = (char) (i3 + c4);
                i6++;
            }
            this.pos += 4;
            return c2;
        }
        return c;
    }

    private void skipQuotedValue(char c) {
        char[] cArr = this.buffer;
        while (true) {
            int i2 = this.pos;
            int i3 = this.limit;
            while (true) {
                if (i2 < i3) {
                    int i4 = i2 + 1;
                    char c2 = cArr[i2];
                    if (c2 == c) {
                        this.pos = i4;
                        return;
                    }
                    if (c2 == '\\') {
                        this.pos = i4;
                        readEscapeCharacter();
                        break;
                    } else {
                        if (c2 == '\n') {
                            this.lineNumber++;
                            this.lineStart = i4;
                        }
                        i2 = i4;
                    }
                } else {
                    this.pos = i2;
                    if (!fillBuffer(1)) {
                        throw syntaxError("Unterminated string");
                    }
                }
            }
        }
    }

    private boolean skipTo(String str) {
        int length = str.length();
        while (true) {
            if (this.pos + length > this.limit && !fillBuffer(length)) {
                return false;
            }
            char[] cArr = this.buffer;
            int i2 = this.pos;
            if (cArr[i2] != '\n') {
                for (int i3 = 0; i3 < length; i3++) {
                    if (this.buffer[this.pos + i3] != str.charAt(i3)) {
                        break;
                    }
                }
                return true;
            }
            this.lineNumber++;
            this.lineStart = i2 + 1;
            this.pos++;
        }
    }

    private void skipToEndOfLine() {
        char c;
        do {
            if (this.pos >= this.limit && !fillBuffer(1)) {
                return;
            }
            char[] cArr = this.buffer;
            int i2 = this.pos;
            int i3 = i2 + 1;
            this.pos = i3;
            c = cArr[i2];
            if (c == '\n') {
                this.lineNumber++;
                this.lineStart = i3;
                return;
            }
        } while (c != '\r');
    }

    /* JADX WARN: Code restructure failed: missing block: B:33:0x0048, code lost:
    
        checkLenient();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void skipUnquotedValue() {
        do {
            int i2 = 0;
            while (true) {
                int i3 = this.pos;
                if (i3 + i2 < this.limit) {
                    char c = this.buffer[i3 + i2];
                    if (c != '\t' && c != '\n' && c != '\f' && c != '\r' && c != ' ') {
                        if (c != '#') {
                            if (c != ',') {
                                if (c != '/' && c != '=') {
                                    if (c != '{' && c != '}' && c != ':') {
                                        if (c != ';') {
                                            switch (c) {
                                                case '[':
                                                case ']':
                                                    break;
                                                case '\\':
                                                    break;
                                                default:
                                                    i2++;
                                            }
                                            return;
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    this.pos = i3 + i2;
                }
            }
            this.pos += i2;
            return;
        } while (fillBuffer(1));
    }

    private IOException syntaxError(String str) {
        StringBuilder m20p = AbstractC0000a.m20p(str);
        m20p.append(locationString());
        throw new MalformedJsonException(m20p.toString());
    }

    public void beginArray() {
        int i2 = this.peeked;
        if (i2 == 0) {
            i2 = doPeek();
        }
        if (i2 == 3) {
            push(1);
            this.pathIndices[this.stackSize - 1] = 0;
            this.peeked = 0;
        } else {
            throw new IllegalStateException("Expected BEGIN_ARRAY but was " + peek() + locationString());
        }
    }

    public void beginObject() {
        int i2 = this.peeked;
        if (i2 == 0) {
            i2 = doPeek();
        }
        if (i2 == 1) {
            push(3);
            this.peeked = 0;
        } else {
            throw new IllegalStateException("Expected BEGIN_OBJECT but was " + peek() + locationString());
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        this.peeked = 0;
        this.stack[0] = 8;
        this.stackSize = 1;
        this.in.close();
    }

    public int doPeek() {
        int i2;
        int nextNonWhitespace;
        int[] iArr = this.stack;
        int i3 = this.stackSize;
        int i4 = iArr[i3 - 1];
        if (i4 == 1) {
            iArr[i3 - 1] = 2;
        } else {
            if (i4 != 2) {
                if (i4 == 3 || i4 == 5) {
                    iArr[i3 - 1] = 4;
                    if (i4 == 5 && (nextNonWhitespace = nextNonWhitespace(true)) != 44) {
                        if (nextNonWhitespace != 59) {
                            if (nextNonWhitespace != 125) {
                                throw syntaxError("Unterminated object");
                            }
                            this.peeked = 2;
                            return 2;
                        }
                        checkLenient();
                    }
                    int nextNonWhitespace2 = nextNonWhitespace(true);
                    if (nextNonWhitespace2 == 34) {
                        i2 = 13;
                    } else if (nextNonWhitespace2 == 39) {
                        checkLenient();
                        i2 = 12;
                    } else {
                        if (nextNonWhitespace2 == 125) {
                            if (i4 == 5) {
                                throw syntaxError("Expected name");
                            }
                            this.peeked = 2;
                            return 2;
                        }
                        checkLenient();
                        this.pos--;
                        if (!isLiteral((char) nextNonWhitespace2)) {
                            throw syntaxError("Expected name");
                        }
                        i2 = 14;
                    }
                } else if (i4 == 4) {
                    iArr[i3 - 1] = 5;
                    int nextNonWhitespace3 = nextNonWhitespace(true);
                    if (nextNonWhitespace3 != 58) {
                        if (nextNonWhitespace3 != 61) {
                            throw syntaxError("Expected ':'");
                        }
                        checkLenient();
                        if (this.pos < this.limit || fillBuffer(1)) {
                            char[] cArr = this.buffer;
                            int i5 = this.pos;
                            if (cArr[i5] == '>') {
                                this.pos = i5 + 1;
                            }
                        }
                    }
                } else if (i4 == 6) {
                    if (this.lenient) {
                        consumeNonExecutePrefix();
                    }
                    this.stack[this.stackSize - 1] = 7;
                } else if (i4 == 7) {
                    if (nextNonWhitespace(false) == -1) {
                        i2 = 17;
                    } else {
                        checkLenient();
                        this.pos--;
                    }
                } else if (i4 == 8) {
                    throw new IllegalStateException("JsonReader is closed");
                }
                this.peeked = i2;
                return i2;
            }
            int nextNonWhitespace4 = nextNonWhitespace(true);
            if (nextNonWhitespace4 != 44) {
                if (nextNonWhitespace4 != 59) {
                    if (nextNonWhitespace4 != 93) {
                        throw syntaxError("Unterminated array");
                    }
                    this.peeked = 4;
                    return 4;
                }
                checkLenient();
            }
        }
        int nextNonWhitespace5 = nextNonWhitespace(true);
        if (nextNonWhitespace5 != 34) {
            if (nextNonWhitespace5 == 39) {
                checkLenient();
                this.peeked = 8;
                return 8;
            }
            if (nextNonWhitespace5 != 44 && nextNonWhitespace5 != 59) {
                if (nextNonWhitespace5 == 91) {
                    this.peeked = 3;
                    return 3;
                }
                if (nextNonWhitespace5 != 93) {
                    if (nextNonWhitespace5 == 123) {
                        this.peeked = 1;
                        return 1;
                    }
                    this.pos--;
                    int peekKeyword = peekKeyword();
                    if (peekKeyword != 0) {
                        return peekKeyword;
                    }
                    int peekNumber = peekNumber();
                    if (peekNumber != 0) {
                        return peekNumber;
                    }
                    if (!isLiteral(this.buffer[this.pos])) {
                        throw syntaxError("Expected value");
                    }
                    checkLenient();
                    i2 = 10;
                } else if (i4 == 1) {
                    this.peeked = 4;
                    return 4;
                }
            }
            if (i4 != 1 && i4 != 2) {
                throw syntaxError("Unexpected value");
            }
            checkLenient();
            this.pos--;
            this.peeked = 7;
            return 7;
        }
        i2 = 9;
        this.peeked = i2;
        return i2;
    }

    public void endArray() {
        int i2 = this.peeked;
        if (i2 == 0) {
            i2 = doPeek();
        }
        if (i2 != 4) {
            throw new IllegalStateException("Expected END_ARRAY but was " + peek() + locationString());
        }
        int i3 = this.stackSize - 1;
        this.stackSize = i3;
        int[] iArr = this.pathIndices;
        int i4 = i3 - 1;
        iArr[i4] = iArr[i4] + 1;
        this.peeked = 0;
    }

    public void endObject() {
        int i2 = this.peeked;
        if (i2 == 0) {
            i2 = doPeek();
        }
        if (i2 != 2) {
            throw new IllegalStateException("Expected END_OBJECT but was " + peek() + locationString());
        }
        int i3 = this.stackSize - 1;
        this.stackSize = i3;
        this.pathNames[i3] = null;
        int[] iArr = this.pathIndices;
        int i4 = i3 - 1;
        iArr[i4] = iArr[i4] + 1;
        this.peeked = 0;
    }

    public String getPath() {
        return getPath(false);
    }

    public String getPreviousPath() {
        return getPath(true);
    }

    public boolean hasNext() {
        int i2 = this.peeked;
        if (i2 == 0) {
            i2 = doPeek();
        }
        return (i2 == 2 || i2 == 4 || i2 == 17) ? false : true;
    }

    public final boolean isLenient() {
        return this.lenient;
    }

    public String locationString() {
        return " at line " + (this.lineNumber + 1) + " column " + ((this.pos - this.lineStart) + 1) + " path " + getPath();
    }

    public boolean nextBoolean() {
        int i2 = this.peeked;
        if (i2 == 0) {
            i2 = doPeek();
        }
        if (i2 == 5) {
            this.peeked = 0;
            int[] iArr = this.pathIndices;
            int i3 = this.stackSize - 1;
            iArr[i3] = iArr[i3] + 1;
            return true;
        }
        if (i2 != 6) {
            throw new IllegalStateException("Expected a boolean but was " + peek() + locationString());
        }
        this.peeked = 0;
        int[] iArr2 = this.pathIndices;
        int i4 = this.stackSize - 1;
        iArr2[i4] = iArr2[i4] + 1;
        return false;
    }

    public double nextDouble() {
        String nextQuotedValue;
        int i2 = this.peeked;
        if (i2 == 0) {
            i2 = doPeek();
        }
        if (i2 == 15) {
            this.peeked = 0;
            int[] iArr = this.pathIndices;
            int i3 = this.stackSize - 1;
            iArr[i3] = iArr[i3] + 1;
            return this.peekedLong;
        }
        if (i2 == 16) {
            this.peekedString = new String(this.buffer, this.pos, this.peekedNumberLength);
            this.pos += this.peekedNumberLength;
        } else {
            if (i2 == 8 || i2 == 9) {
                nextQuotedValue = nextQuotedValue(i2 == 8 ? '\'' : '\"');
            } else if (i2 == 10) {
                nextQuotedValue = nextUnquotedValue();
            } else if (i2 != 11) {
                throw new IllegalStateException("Expected a double but was " + peek() + locationString());
            }
            this.peekedString = nextQuotedValue;
        }
        this.peeked = 11;
        double parseDouble = Double.parseDouble(this.peekedString);
        if (!this.lenient && (Double.isNaN(parseDouble) || Double.isInfinite(parseDouble))) {
            throw new MalformedJsonException("JSON forbids NaN and infinities: " + parseDouble + locationString());
        }
        this.peekedString = null;
        this.peeked = 0;
        int[] iArr2 = this.pathIndices;
        int i4 = this.stackSize - 1;
        iArr2[i4] = iArr2[i4] + 1;
        return parseDouble;
    }

    public int nextInt() {
        String nextQuotedValue;
        int i2 = this.peeked;
        if (i2 == 0) {
            i2 = doPeek();
        }
        if (i2 == 15) {
            long j2 = this.peekedLong;
            int i3 = (int) j2;
            if (j2 != i3) {
                throw new NumberFormatException("Expected an int but was " + this.peekedLong + locationString());
            }
            this.peeked = 0;
            int[] iArr = this.pathIndices;
            int i4 = this.stackSize - 1;
            iArr[i4] = iArr[i4] + 1;
            return i3;
        }
        if (i2 == 16) {
            this.peekedString = new String(this.buffer, this.pos, this.peekedNumberLength);
            this.pos += this.peekedNumberLength;
        } else {
            if (i2 != 8 && i2 != 9 && i2 != 10) {
                throw new IllegalStateException("Expected an int but was " + peek() + locationString());
            }
            if (i2 == 10) {
                nextQuotedValue = nextUnquotedValue();
            } else {
                nextQuotedValue = nextQuotedValue(i2 == 8 ? '\'' : '\"');
            }
            this.peekedString = nextQuotedValue;
            try {
                int parseInt = Integer.parseInt(this.peekedString);
                this.peeked = 0;
                int[] iArr2 = this.pathIndices;
                int i5 = this.stackSize - 1;
                iArr2[i5] = iArr2[i5] + 1;
                return parseInt;
            } catch (NumberFormatException unused) {
            }
        }
        this.peeked = 11;
        double parseDouble = Double.parseDouble(this.peekedString);
        int i6 = (int) parseDouble;
        if (i6 != parseDouble) {
            throw new NumberFormatException("Expected an int but was " + this.peekedString + locationString());
        }
        this.peekedString = null;
        this.peeked = 0;
        int[] iArr3 = this.pathIndices;
        int i7 = this.stackSize - 1;
        iArr3[i7] = iArr3[i7] + 1;
        return i6;
    }

    public long nextLong() {
        String nextQuotedValue;
        int i2 = this.peeked;
        if (i2 == 0) {
            i2 = doPeek();
        }
        if (i2 == 15) {
            this.peeked = 0;
            int[] iArr = this.pathIndices;
            int i3 = this.stackSize - 1;
            iArr[i3] = iArr[i3] + 1;
            return this.peekedLong;
        }
        if (i2 == 16) {
            this.peekedString = new String(this.buffer, this.pos, this.peekedNumberLength);
            this.pos += this.peekedNumberLength;
        } else {
            if (i2 != 8 && i2 != 9 && i2 != 10) {
                throw new IllegalStateException("Expected a long but was " + peek() + locationString());
            }
            if (i2 == 10) {
                nextQuotedValue = nextUnquotedValue();
            } else {
                nextQuotedValue = nextQuotedValue(i2 == 8 ? '\'' : '\"');
            }
            this.peekedString = nextQuotedValue;
            try {
                long parseLong = Long.parseLong(this.peekedString);
                this.peeked = 0;
                int[] iArr2 = this.pathIndices;
                int i4 = this.stackSize - 1;
                iArr2[i4] = iArr2[i4] + 1;
                return parseLong;
            } catch (NumberFormatException unused) {
            }
        }
        this.peeked = 11;
        double parseDouble = Double.parseDouble(this.peekedString);
        long j2 = (long) parseDouble;
        if (j2 != parseDouble) {
            throw new NumberFormatException("Expected a long but was " + this.peekedString + locationString());
        }
        this.peekedString = null;
        this.peeked = 0;
        int[] iArr3 = this.pathIndices;
        int i5 = this.stackSize - 1;
        iArr3[i5] = iArr3[i5] + 1;
        return j2;
    }

    public String nextName() {
        char c;
        String nextQuotedValue;
        int i2 = this.peeked;
        if (i2 == 0) {
            i2 = doPeek();
        }
        if (i2 == 14) {
            nextQuotedValue = nextUnquotedValue();
        } else {
            if (i2 == 12) {
                c = '\'';
            } else {
                if (i2 != 13) {
                    throw new IllegalStateException("Expected a name but was " + peek() + locationString());
                }
                c = '\"';
            }
            nextQuotedValue = nextQuotedValue(c);
        }
        this.peeked = 0;
        this.pathNames[this.stackSize - 1] = nextQuotedValue;
        return nextQuotedValue;
    }

    public void nextNull() {
        int i2 = this.peeked;
        if (i2 == 0) {
            i2 = doPeek();
        }
        if (i2 != 7) {
            throw new IllegalStateException("Expected null but was " + peek() + locationString());
        }
        this.peeked = 0;
        int[] iArr = this.pathIndices;
        int i3 = this.stackSize - 1;
        iArr[i3] = iArr[i3] + 1;
    }

    public String nextString() {
        String str;
        char c;
        int i2 = this.peeked;
        if (i2 == 0) {
            i2 = doPeek();
        }
        if (i2 == 10) {
            str = nextUnquotedValue();
        } else {
            if (i2 == 8) {
                c = '\'';
            } else if (i2 == 9) {
                c = '\"';
            } else if (i2 == 11) {
                str = this.peekedString;
                this.peekedString = null;
            } else if (i2 == 15) {
                str = Long.toString(this.peekedLong);
            } else {
                if (i2 != 16) {
                    throw new IllegalStateException("Expected a string but was " + peek() + locationString());
                }
                str = new String(this.buffer, this.pos, this.peekedNumberLength);
                this.pos += this.peekedNumberLength;
            }
            str = nextQuotedValue(c);
        }
        this.peeked = 0;
        int[] iArr = this.pathIndices;
        int i3 = this.stackSize - 1;
        iArr[i3] = iArr[i3] + 1;
        return str;
    }

    public JsonToken peek() {
        int i2 = this.peeked;
        if (i2 == 0) {
            i2 = doPeek();
        }
        switch (i2) {
            case 1:
                return JsonToken.BEGIN_OBJECT;
            case 2:
                return JsonToken.END_OBJECT;
            case 3:
                return JsonToken.BEGIN_ARRAY;
            case 4:
                return JsonToken.END_ARRAY;
            case 5:
            case 6:
                return JsonToken.BOOLEAN;
            case 7:
                return JsonToken.NULL;
            case 8:
            case 9:
            case 10:
            case 11:
                return JsonToken.STRING;
            case 12:
            case 13:
            case 14:
                return JsonToken.NAME;
            case 15:
            case 16:
                return JsonToken.NUMBER;
            case 17:
                return JsonToken.END_DOCUMENT;
            default:
                throw new AssertionError();
        }
    }

    public final void setLenient(boolean z2) {
        this.lenient = z2;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public void skipValue() {
        int i2 = 0;
        do {
            int i3 = this.peeked;
            if (i3 == 0) {
                i3 = doPeek();
            }
            switch (i3) {
                case 1:
                    push(3);
                    i2++;
                    this.peeked = 0;
                    break;
                case 2:
                    if (i2 == 0) {
                        this.pathNames[this.stackSize - 1] = null;
                    }
                    this.stackSize--;
                    i2--;
                    this.peeked = 0;
                    break;
                case 3:
                    push(1);
                    i2++;
                    this.peeked = 0;
                    break;
                case 4:
                    this.stackSize--;
                    i2--;
                    this.peeked = 0;
                    break;
                case 5:
                case 6:
                case 7:
                case 11:
                case 15:
                default:
                    this.peeked = 0;
                    break;
                case 8:
                    skipQuotedValue('\'');
                    this.peeked = 0;
                    break;
                case 9:
                    skipQuotedValue('\"');
                    this.peeked = 0;
                    break;
                case 10:
                    skipUnquotedValue();
                    this.peeked = 0;
                    break;
                case 12:
                    skipQuotedValue('\'');
                    if (i2 == 0) {
                        this.pathNames[this.stackSize - 1] = "<skipped>";
                    }
                    this.peeked = 0;
                    break;
                case 13:
                    skipQuotedValue('\"');
                    if (i2 == 0) {
                        this.pathNames[this.stackSize - 1] = "<skipped>";
                    }
                    this.peeked = 0;
                    break;
                case 14:
                    skipUnquotedValue();
                    if (i2 == 0) {
                        this.pathNames[this.stackSize - 1] = "<skipped>";
                    }
                    this.peeked = 0;
                    break;
                case 16:
                    this.pos += this.peekedNumberLength;
                    this.peeked = 0;
                    break;
                case 17:
                    break;
            }
            return;
        } while (i2 > 0);
        int[] iArr = this.pathIndices;
        int i4 = this.stackSize - 1;
        iArr[i4] = iArr[i4] + 1;
    }

    public String toString() {
        return getClass().getSimpleName() + locationString();
    }

    private String getPath(boolean z2) {
        StringBuilder sb = new StringBuilder("$");
        int i2 = 0;
        while (true) {
            int i3 = this.stackSize;
            if (i2 >= i3) {
                return sb.toString();
            }
            int i4 = this.stack[i2];
            if (i4 == 1 || i4 == 2) {
                int i5 = this.pathIndices[i2];
                if (z2 && i5 > 0 && i2 == i3 - 1) {
                    i5--;
                }
                sb.append('[');
                sb.append(i5);
                sb.append(']');
            } else if (i4 == 3 || i4 == 4 || i4 == 5) {
                sb.append('.');
                String str = this.pathNames[i2];
                if (str != null) {
                    sb.append(str);
                }
            }
            i2++;
        }
    }
}
