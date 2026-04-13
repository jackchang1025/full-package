package com.google.json.internal.bind.util;

import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import org.bouncycastle.pqc.math.linearalgebra.Matrix;
import com.guard.wallet.entity.BuildConfig;

/* loaded from: classes.dex */
public class ISO8601Utils {
    private static final String UTC_ID = "UTC";
    private static final TimeZone TIMEZONE_UTC = TimeZone.getTimeZone(UTC_ID);

    private static boolean checkOffset(String str, int i2, char c) {
        return i2 < str.length() && str.charAt(i2) == c;
    }

    public static String format(Date date) {
        return format(date, false, TIMEZONE_UTC);
    }

    private static int indexOfNonDigit(String str, int i2) {
        while (i2 < str.length()) {
            char charAt = str.charAt(i2);
            if (charAt < '0' || charAt > '9') {
                return i2;
            }
            i2++;
        }
        return str.length();
    }

    private static void padInt(StringBuilder sb, int i2, int i3) {
        String num = Integer.toString(i2);
        for (int length = i3 - num.length(); length > 0; length--) {
            sb.append('0');
        }
        sb.append(num);
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x00db A[Catch: IllegalArgumentException -> 0x01b4, NumberFormatException -> 0x01b6, IndexOutOfBoundsException -> 0x01b8, TRY_LEAVE, TryCatch #4 {IndexOutOfBoundsException -> 0x01b8, NumberFormatException -> 0x01b6, IllegalArgumentException -> 0x01b4, blocks: (B:3:0x000c, B:5:0x001e, B:6:0x0020, B:8:0x002c, B:9:0x002e, B:11:0x003e, B:13:0x0044, B:18:0x005b, B:20:0x006b, B:21:0x006d, B:23:0x0079, B:24:0x007b, B:26:0x0081, B:30:0x008b, B:35:0x009b, B:37:0x00a3, B:43:0x00d5, B:45:0x00db, B:52:0x01a4, B:95:0x01ac, B:96:0x01b3, B:97:0x00bd, B:98:0x00c0), top: B:2:0x000c }] */
    /* JADX WARN: Removed duplicated region for block: B:82:0x01bb  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x01d6  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x01bd  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x01ac A[Catch: IllegalArgumentException -> 0x01b4, NumberFormatException -> 0x01b6, IndexOutOfBoundsException -> 0x01b8, TryCatch #4 {IndexOutOfBoundsException -> 0x01b8, NumberFormatException -> 0x01b6, IllegalArgumentException -> 0x01b4, blocks: (B:3:0x000c, B:5:0x001e, B:6:0x0020, B:8:0x002c, B:9:0x002e, B:11:0x003e, B:13:0x0044, B:18:0x005b, B:20:0x006b, B:21:0x006d, B:23:0x0079, B:24:0x007b, B:26:0x0081, B:30:0x008b, B:35:0x009b, B:37:0x00a3, B:43:0x00d5, B:45:0x00db, B:52:0x01a4, B:95:0x01ac, B:96:0x01b3, B:97:0x00bd, B:98:0x00c0), top: B:2:0x000c }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Date parse(String str, ParsePosition parsePosition) {
        String str2;
        String message;
        int i2;
        int i3;
        int i4;
        int i5;
        int length;
        TimeZone timeZone;
        char charAt;
        try {
            int index = parsePosition.getIndex();
            int i6 = index + 4;
            int parseInt = parseInt(str, index, i6);
            if (checkOffset(str, i6, '-')) {
                i6++;
            }
            int i7 = i6 + 2;
            int parseInt2 = parseInt(str, i6, i7);
            if (checkOffset(str, i7, '-')) {
                i7++;
            }
            int i8 = i7 + 2;
            int parseInt3 = parseInt(str, i7, i8);
            boolean checkOffset = checkOffset(str, i8, 'T');
            if (!checkOffset && str.length() <= i8) {
                GregorianCalendar gregorianCalendar = new GregorianCalendar(parseInt, parseInt2 - 1, parseInt3);
                gregorianCalendar.setLenient(false);
                parsePosition.setIndex(i8);
                return gregorianCalendar.getTime();
            }
            if (checkOffset) {
                int i9 = i8 + 1;
                int i10 = i9 + 2;
                int parseInt4 = parseInt(str, i9, i10);
                if (checkOffset(str, i10, ':')) {
                    i10++;
                }
                int i11 = i10 + 2;
                i3 = parseInt(str, i10, i11);
                if (checkOffset(str, i11, ':')) {
                    i11++;
                }
                if (str.length() > i11 && (charAt = str.charAt(i11)) != 'Z' && charAt != '+' && charAt != '-') {
                    int i12 = i11 + 2;
                    i5 = parseInt(str, i11, i12);
                    if (i5 > 59 && i5 < 63) {
                        i5 = 59;
                    }
                    if (checkOffset(str, i12, '.')) {
                        int i13 = i12 + 1;
                        int indexOfNonDigit = indexOfNonDigit(str, i13 + 1);
                        int min = Math.min(indexOfNonDigit, i13 + 3);
                        int parseInt5 = parseInt(str, i13, min);
                        int i14 = min - i13;
                        if (i14 == 1) {
                            parseInt5 *= 100;
                        } else if (i14 == 2) {
                            parseInt5 *= 10;
                        }
                        i4 = parseInt5;
                        i8 = indexOfNonDigit;
                        i2 = parseInt4;
                    } else {
                        i2 = parseInt4;
                        i8 = i12;
                        i4 = 0;
                    }
                    if (str.length() > i8) {
                        throw new IllegalArgumentException("No time zone indicator");
                    }
                    char charAt2 = str.charAt(i8);
                    try {
                        if (charAt2 == 'Z') {
                            timeZone = TIMEZONE_UTC;
                            length = i8 + 1;
                        } else {
                            if (charAt2 != '+' && charAt2 != '-') {
                                throw new IndexOutOfBoundsException("Invalid time zone indicator '" + charAt2 + "'");
                            }
                            String substring = str.substring(i8);
                            if (substring.length() < 5) {
                                substring = substring.concat("00");
                            }
                            length = i8 + substring.length();
                            if (!"+0000".equals(substring) && !"+00:00".equals(substring)) {
                                String concat = "GMT".concat(substring);
                                TimeZone timeZone2 = TimeZone.getTimeZone(concat);
                                String id = timeZone2.getID();
                                if (!id.equals(concat) && !id.replace(":", BuildConfig.FLAVOR).equals(concat)) {
                                    throw new IndexOutOfBoundsException("Mismatching time zone indicator: " + concat + " given, resolves to " + timeZone2.getID());
                                }
                                timeZone = timeZone2;
                            }
                            timeZone = TIMEZONE_UTC;
                        }
                        GregorianCalendar gregorianCalendar2 = new GregorianCalendar(timeZone);
                        gregorianCalendar2.setLenient(false);
                        gregorianCalendar2.set(1, parseInt);
                        gregorianCalendar2.set(2, parseInt2 - 1);
                        gregorianCalendar2.set(5, parseInt3);
                        gregorianCalendar2.set(11, i2);
                        gregorianCalendar2.set(12, i3);
                        gregorianCalendar2.set(13, i5);
                        gregorianCalendar2.set(14, i4);
                        parsePosition.setIndex(length);
                        return gregorianCalendar2.getTime();
                    } catch (IllegalArgumentException e2) {
                        e = e2;
                        if (str == null) {
                            str2 = null;
                        } else {
                            str2 = "\"" + str + '\"';
                        }
                        message = e.getMessage();
                        if (message != null || message.isEmpty()) {
                            message = "(" + e.getClass().getName() + ")";
                        }
                        ParseException parseException = new ParseException("Failed to parse date [" + str2 + "]: " + message, parsePosition.getIndex());
                        parseException.initCause(e);
                        throw parseException;
                    } catch (IndexOutOfBoundsException e3) {
                        e = e3;
                        if (str == null) {
                        }
                        message = e.getMessage();
                        if (message != null) {
                        }
                        message = "(" + e.getClass().getName() + ")";
                        ParseException parseException2 = new ParseException("Failed to parse date [" + str2 + "]: " + message, parsePosition.getIndex());
                        parseException2.initCause(e);
                        throw parseException2;
                    } catch (NumberFormatException e4) {
                        e = e4;
                        if (str == null) {
                        }
                        message = e.getMessage();
                        if (message != null) {
                        }
                        message = "(" + e.getClass().getName() + ")";
                        ParseException parseException22 = new ParseException("Failed to parse date [" + str2 + "]: " + message, parsePosition.getIndex());
                        parseException22.initCause(e);
                        throw parseException22;
                    }
                }
                i2 = parseInt4;
                i8 = i11;
            } else {
                i2 = 0;
                i3 = 0;
            }
            i4 = 0;
            i5 = 0;
            if (str.length() > i8) {
            }
        } catch (IndexOutOfBoundsException e5) {
            e = e5;
        } catch (NumberFormatException e6) {
            e = e6;
        } catch (IllegalArgumentException e7) {
            e = e7;
        }
    }

    private static int parseInt(String str, int i2, int i3) {
        int i4;
        int i5;
        if (i2 < 0 || i3 > str.length() || i2 > i3) {
            throw new NumberFormatException(str);
        }
        if (i2 < i3) {
            i5 = i2 + 1;
            int digit = Character.digit(str.charAt(i2), 10);
            if (digit < 0) {
                throw new NumberFormatException("Invalid number: " + str.substring(i2, i3));
            }
            i4 = -digit;
        } else {
            i4 = 0;
            i5 = i2;
        }
        while (i5 < i3) {
            int i6 = i5 + 1;
            int digit2 = Character.digit(str.charAt(i5), 10);
            if (digit2 < 0) {
                throw new NumberFormatException("Invalid number: " + str.substring(i2, i3));
            }
            i4 = (i4 * 10) - digit2;
            i5 = i6;
        }
        return -i4;
    }

    public static String format(Date date, boolean z2) {
        return format(date, z2, TIMEZONE_UTC);
    }

    public static String format(Date date, boolean z2, TimeZone timeZone) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar(timeZone, Locale.US);
        gregorianCalendar.setTime(date);
        StringBuilder sb = new StringBuilder(19 + (z2 ? 4 : 0) + (timeZone.getRawOffset() == 0 ? 1 : 6));
        padInt(sb, gregorianCalendar.get(1), 4);
        sb.append('-');
        padInt(sb, gregorianCalendar.get(2) + 1, 2);
        sb.append('-');
        padInt(sb, gregorianCalendar.get(5), 2);
        sb.append('T');
        padInt(sb, gregorianCalendar.get(11), 2);
        sb.append(':');
        padInt(sb, gregorianCalendar.get(12), 2);
        sb.append(':');
        padInt(sb, gregorianCalendar.get(13), 2);
        if (z2) {
            sb.append('.');
            padInt(sb, gregorianCalendar.get(14), 3);
        }
        int offset = timeZone.getOffset(gregorianCalendar.getTimeInMillis());
        if (offset != 0) {
            int i2 = offset / 60000;
            int abs = Math.abs(i2 / 60);
            int abs2 = Math.abs(i2 % 60);
            sb.append(offset >= 0 ? '+' : '-');
            padInt(sb, abs, 2);
            sb.append(':');
            padInt(sb, abs2, 2);
        } else {
            sb.append(Matrix.MATRIX_TYPE_ZERO);
        }
        return sb.toString();
    }
}
