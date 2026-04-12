package io.socket.yeast;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import p000.AbstractC0003a2;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class Yeast {
    private static char[] alphabet;
    private static int length;
    private static Map<Character, Integer> map;
    private static String prev;
    private static int seed;

    static {
        char[] charArray = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz-_".toCharArray();
        alphabet = charArray;
        length = charArray.length;
        seed = 0;
        map = new HashMap(length);
        for (int i = 0; i < length; i++) {
            map.put(Character.valueOf(alphabet[i]), Integer.valueOf(i));
        }
    }

    private Yeast() {
    }

    public static long decode(String str) {
        long jIntValue = 0;
        for (int i = 0; i < str.toCharArray().length; i++) {
            jIntValue = (jIntValue * length) + map.get(Character.valueOf(r7[i])).intValue();
        }
        return jIntValue;
    }

    public static String encode(long j) {
        StringBuilder sb = new StringBuilder();
        do {
            sb.insert(0, alphabet[(int) (j % length)]);
            j /= length;
        } while (j > 0);
        return sb.toString();
    }

    public static String yeast() {
        String strEncode = encode(new Date().getTime());
        if (!strEncode.equals(prev)) {
            seed = 0;
            prev = strEncode;
            return strEncode;
        }
        StringBuilder sbM39c0 = AbstractC0003a2.m39c0(strEncode, ".");
        int i = seed;
        seed = i + 1;
        sbM39c0.append(encode(i));
        return sbM39c0.toString();
    }
}
