package android.sun.misc;

import java.io.OutputStream;
import java.io.PrintStream;

/* loaded from: classes.dex */
public class HexDumpEncoder extends CharacterEncoder {
    private int currentByte;
    private int offset;
    private byte[] thisLine = new byte[16];
    private int thisLineLength;

    public static void hexDigit(PrintStream printStream, byte b) {
        char c = (char) ((b >> 4) & 15);
        printStream.write((char) (c > '\t' ? (c - '\n') + 65 : c + '0'));
        char c2 = (char) (b & 15);
        printStream.write((char) (c2 > '\t' ? (c2 - '\n') + 65 : c2 + '0'));
    }

    @Override // android.sun.misc.CharacterEncoder
    public int bytesPerAtom() {
        return 1;
    }

    @Override // android.sun.misc.CharacterEncoder
    public int bytesPerLine() {
        return 16;
    }

    @Override // android.sun.misc.CharacterEncoder
    public void encodeAtom(OutputStream outputStream, byte[] bArr, int i2, int i3) {
        this.thisLine[this.currentByte] = bArr[i2];
        hexDigit(this.pStream, bArr[i2]);
        this.pStream.print(" ");
        int i4 = this.currentByte + 1;
        this.currentByte = i4;
        if (i4 == 8) {
            this.pStream.print("  ");
        }
    }

    @Override // android.sun.misc.CharacterEncoder
    public void encodeBufferPrefix(OutputStream outputStream) {
        this.offset = 0;
        super.encodeBufferPrefix(outputStream);
    }

    @Override // android.sun.misc.CharacterEncoder
    public void encodeLinePrefix(OutputStream outputStream, int i2) {
        hexDigit(this.pStream, (byte) ((this.offset >>> 8) & 255));
        hexDigit(this.pStream, (byte) (this.offset & 255));
        this.pStream.print(": ");
        this.currentByte = 0;
        this.thisLineLength = i2;
    }

    @Override // android.sun.misc.CharacterEncoder
    public void encodeLineSuffix(OutputStream outputStream) {
        int i2 = this.thisLineLength;
        if (i2 < 16) {
            while (i2 < 16) {
                this.pStream.print("   ");
                if (i2 == 7) {
                    this.pStream.print("  ");
                }
                i2++;
            }
        }
        this.pStream.print(" ");
        for (int i3 = 0; i3 < this.thisLineLength; i3++) {
            byte b = this.thisLine[i3];
            if (b < 32 || b > 122) {
                this.pStream.print(".");
            } else {
                this.pStream.write(b);
            }
        }
        this.pStream.println();
        this.offset += this.thisLineLength;
    }
}
