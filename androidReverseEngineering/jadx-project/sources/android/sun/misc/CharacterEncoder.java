package android.sun.misc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.ByteBuffer;

/* loaded from: classes.dex */
public abstract class CharacterEncoder {
    protected PrintStream pStream;

    /* JADX WARN: Removed duplicated region for block: B:12:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0023  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private byte[] getBytes(ByteBuffer byteBuffer) {
        byte[] bArr;
        if (byteBuffer.hasArray()) {
            bArr = byteBuffer.array();
            if (bArr.length == byteBuffer.capacity() && bArr.length == byteBuffer.remaining()) {
                byteBuffer.position(byteBuffer.limit());
                if (bArr == null) {
                    return bArr;
                }
                byte[] bArr2 = new byte[byteBuffer.remaining()];
                byteBuffer.get(bArr2);
                return bArr2;
            }
        }
        bArr = null;
        if (bArr == null) {
        }
    }

    public abstract int bytesPerAtom();

    public abstract int bytesPerLine();

    public String encode(ByteBuffer byteBuffer) {
        return encode(getBytes(byteBuffer));
    }

    public abstract void encodeAtom(OutputStream outputStream, byte[] bArr, int i2, int i3);

    public String encodeBuffer(ByteBuffer byteBuffer) {
        return encodeBuffer(getBytes(byteBuffer));
    }

    public void encodeBufferPrefix(OutputStream outputStream) {
        this.pStream = new PrintStream(outputStream);
    }

    public void encodeBufferSuffix(OutputStream outputStream) {
    }

    public void encodeLinePrefix(OutputStream outputStream, int i2) {
    }

    public void encodeLineSuffix(OutputStream outputStream) {
        this.pStream.println();
    }

    public int readFully(InputStream inputStream, byte[] bArr) {
        for (int i2 = 0; i2 < bArr.length; i2++) {
            int read = inputStream.read();
            if (read == -1) {
                return i2;
            }
            bArr[i2] = (byte) read;
        }
        return bArr.length;
    }

    public String encode(byte[] bArr) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            encode(new ByteArrayInputStream(bArr), byteArrayOutputStream);
            return byteArrayOutputStream.toString("8859_1");
        } catch (Exception unused) {
            throw new Error("CharacterEncoder.encode internal error");
        }
    }

    public String encodeBuffer(byte[] bArr) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            encodeBuffer(new ByteArrayInputStream(bArr), byteArrayOutputStream);
            return byteArrayOutputStream.toString();
        } catch (Exception unused) {
            throw new Error("CharacterEncoder.encodeBuffer internal error");
        }
    }

    public void encode(InputStream inputStream, OutputStream outputStream) {
        byte[] bArr = new byte[bytesPerLine()];
        encodeBufferPrefix(outputStream);
        while (true) {
            int readFully = readFully(inputStream, bArr);
            if (readFully == 0) {
                break;
            }
            encodeLinePrefix(outputStream, readFully);
            int i2 = 0;
            while (i2 < readFully) {
                encodeAtom(outputStream, bArr, i2, bytesPerAtom() + i2 <= readFully ? bytesPerAtom() : readFully - i2);
                i2 += bytesPerAtom();
            }
            if (readFully < bytesPerLine()) {
                break;
            } else {
                encodeLineSuffix(outputStream);
            }
        }
        encodeBufferSuffix(outputStream);
    }

    public void encodeBuffer(InputStream inputStream, OutputStream outputStream) {
        int readFully;
        byte[] bArr = new byte[bytesPerLine()];
        encodeBufferPrefix(outputStream);
        do {
            readFully = readFully(inputStream, bArr);
            if (readFully == 0) {
                break;
            }
            encodeLinePrefix(outputStream, readFully);
            int i2 = 0;
            while (i2 < readFully) {
                encodeAtom(outputStream, bArr, i2, bytesPerAtom() + i2 <= readFully ? bytesPerAtom() : readFully - i2);
                i2 += bytesPerAtom();
            }
            encodeLineSuffix(outputStream);
        } while (readFully >= bytesPerLine());
        encodeBufferSuffix(outputStream);
    }

    public void encode(ByteBuffer byteBuffer, OutputStream outputStream) {
        encode(getBytes(byteBuffer), outputStream);
    }

    public void encodeBuffer(ByteBuffer byteBuffer, OutputStream outputStream) {
        encodeBuffer(getBytes(byteBuffer), outputStream);
    }

    public void encode(byte[] bArr, OutputStream outputStream) {
        encode(new ByteArrayInputStream(bArr), outputStream);
    }

    public void encodeBuffer(byte[] bArr, OutputStream outputStream) {
        encodeBuffer(new ByteArrayInputStream(bArr), outputStream);
    }
}
