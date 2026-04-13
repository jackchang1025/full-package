package android.sun.misc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.nio.ByteBuffer;

/* loaded from: classes.dex */
public abstract class CharacterDecoder {
    public abstract int bytesPerAtom();

    public abstract int bytesPerLine();

    public void decodeAtom(PushbackInputStream pushbackInputStream, OutputStream outputStream, int i2) {
        throw new CEStreamExhausted();
    }

    public void decodeBuffer(InputStream inputStream, OutputStream outputStream) {
        PushbackInputStream pushbackInputStream = new PushbackInputStream(inputStream);
        decodeBufferPrefix(pushbackInputStream, outputStream);
        while (true) {
            try {
                int decodeLinePrefix = decodeLinePrefix(pushbackInputStream, outputStream);
                int i2 = 0;
                while (bytesPerAtom() + i2 < decodeLinePrefix) {
                    decodeAtom(pushbackInputStream, outputStream, bytesPerAtom());
                    bytesPerAtom();
                    i2 += bytesPerAtom();
                }
                if (bytesPerAtom() + i2 == decodeLinePrefix) {
                    decodeAtom(pushbackInputStream, outputStream, bytesPerAtom());
                    bytesPerAtom();
                } else {
                    decodeAtom(pushbackInputStream, outputStream, decodeLinePrefix - i2);
                }
                decodeLineSuffix(pushbackInputStream, outputStream);
            } catch (CEStreamExhausted unused) {
                decodeBufferSuffix(pushbackInputStream, outputStream);
                return;
            }
        }
    }

    public void decodeBufferPrefix(PushbackInputStream pushbackInputStream, OutputStream outputStream) {
    }

    public void decodeBufferSuffix(PushbackInputStream pushbackInputStream, OutputStream outputStream) {
    }

    public ByteBuffer decodeBufferToByteBuffer(InputStream inputStream) {
        return ByteBuffer.wrap(decodeBuffer(inputStream));
    }

    public int decodeLinePrefix(PushbackInputStream pushbackInputStream, OutputStream outputStream) {
        return bytesPerLine();
    }

    public void decodeLineSuffix(PushbackInputStream pushbackInputStream, OutputStream outputStream) {
    }

    public int readFully(InputStream inputStream, byte[] bArr, int i2, int i3) {
        for (int i4 = 0; i4 < i3; i4++) {
            int read = inputStream.read();
            if (read == -1) {
                if (i4 == 0) {
                    return -1;
                }
                return i4;
            }
            bArr[i4 + i2] = (byte) read;
        }
        return i3;
    }

    public byte[] decodeBuffer(InputStream inputStream) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        decodeBuffer(inputStream, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public ByteBuffer decodeBufferToByteBuffer(String str) {
        return ByteBuffer.wrap(decodeBuffer(str));
    }

    public byte[] decodeBuffer(String str) {
        byte[] bArr = new byte[str.length()];
        str.getBytes(0, str.length(), bArr, 0);
        InputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        decodeBuffer(byteArrayInputStream, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}
