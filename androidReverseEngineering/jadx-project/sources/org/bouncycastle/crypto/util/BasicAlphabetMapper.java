package org.bouncycastle.crypto.util;

import android.support.v4.view.MotionEventCompat;
import java.util.HashMap;
import java.util.Map;
import org.bouncycastle.crypto.AlphabetMapper;

/* loaded from: classes.dex */
public class BasicAlphabetMapper implements AlphabetMapper {
    private Map<Integer, Character> charMap;
    private Map<Character, Integer> indexMap;

    public BasicAlphabetMapper(String str) {
        this(str.toCharArray());
    }

    @Override // org.bouncycastle.crypto.AlphabetMapper
    public char[] convertToChars(byte[] bArr) {
        char[] cArr;
        int i2 = 0;
        if (this.charMap.size() <= 256) {
            cArr = new char[bArr.length];
            while (i2 != bArr.length) {
                cArr[i2] = this.charMap.get(Integer.valueOf(bArr[i2] & 255)).charValue();
                i2++;
            }
        } else {
            if ((bArr.length & 1) != 0) {
                throw new IllegalArgumentException("two byte radix and input string odd length");
            }
            cArr = new char[bArr.length / 2];
            while (i2 != bArr.length) {
                cArr[i2 / 2] = this.charMap.get(Integer.valueOf(((bArr[i2] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[i2 + 1] & 255))).charValue();
                i2 += 2;
            }
        }
        return cArr;
    }

    @Override // org.bouncycastle.crypto.AlphabetMapper
    public byte[] convertToIndexes(char[] cArr) {
        byte[] bArr;
        int i2 = 0;
        if (this.indexMap.size() <= 256) {
            bArr = new byte[cArr.length];
            while (i2 != cArr.length) {
                bArr[i2] = this.indexMap.get(Character.valueOf(cArr[i2])).byteValue();
                i2++;
            }
        } else {
            bArr = new byte[cArr.length * 2];
            while (i2 != cArr.length) {
                int intValue = this.indexMap.get(Character.valueOf(cArr[i2])).intValue();
                int i3 = i2 * 2;
                bArr[i3] = (byte) ((intValue >> 8) & 255);
                bArr[i3 + 1] = (byte) (intValue & 255);
                i2++;
            }
        }
        return bArr;
    }

    @Override // org.bouncycastle.crypto.AlphabetMapper
    public int getRadix() {
        return this.indexMap.size();
    }

    public BasicAlphabetMapper(char[] cArr) {
        this.indexMap = new HashMap();
        this.charMap = new HashMap();
        for (int i2 = 0; i2 != cArr.length; i2++) {
            if (this.indexMap.containsKey(Character.valueOf(cArr[i2]))) {
                throw new IllegalArgumentException("duplicate key detected in alphabet: " + cArr[i2]);
            }
            this.indexMap.put(Character.valueOf(cArr[i2]), Integer.valueOf(i2));
            this.charMap.put(Integer.valueOf(i2), Character.valueOf(cArr[i2]));
        }
    }
}
