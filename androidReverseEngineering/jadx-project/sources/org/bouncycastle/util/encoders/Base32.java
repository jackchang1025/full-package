package org.bouncycastle.util.encoders;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import org.bouncycastle.util.Strings;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class Base32 {
    private static final Encoder encoder = new Base32Encoder();

    public static int decode(String str, OutputStream outputStream) {
        return encoder.decode(str, outputStream);
    }

    public static int encode(byte[] bArr, int i2, int i3, OutputStream outputStream) {
        return encoder.encode(bArr, i2, i3, outputStream);
    }

    public static String toBase32String(byte[] bArr) {
        return toBase32String(bArr, 0, bArr.length);
    }

    public static int decode(byte[] bArr, int i2, int i3, OutputStream outputStream) {
        try {
            return encoder.decode(bArr, i2, i3, outputStream);
        } catch (Exception e2) {
            throw new DecoderException(AbstractC0000a.m9e(e2, new StringBuilder("unable to decode base32 data: ")), e2);
        }
    }

    public static int encode(byte[] bArr, OutputStream outputStream) {
        return encoder.encode(bArr, 0, bArr.length, outputStream);
    }

    public static String toBase32String(byte[] bArr, int i2, int i3) {
        return Strings.fromByteArray(encode(bArr, i2, i3));
    }

    public static byte[] encode(byte[] bArr) {
        return encode(bArr, 0, bArr.length);
    }

    public static byte[] encode(byte[] bArr, int i2, int i3) {
        Encoder encoder2 = encoder;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(encoder2.getEncodedLength(i3));
        try {
            encoder2.encode(bArr, i2, i3, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e2) {
            throw new EncoderException(AbstractC0000a.m9e(e2, new StringBuilder("exception encoding base32 string: ")), e2);
        }
    }

    public static byte[] decode(String str) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream((str.length() / 8) * 5);
        try {
            encoder.decode(str, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e2) {
            throw new DecoderException(AbstractC0000a.m9e(e2, new StringBuilder("unable to decode base32 string: ")), e2);
        }
    }

    public static byte[] decode(byte[] bArr) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream((bArr.length / 8) * 5);
        try {
            encoder.decode(bArr, 0, bArr.length, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e2) {
            throw new DecoderException(AbstractC0000a.m9e(e2, new StringBuilder("unable to decode base32 data: ")), e2);
        }
    }
}
