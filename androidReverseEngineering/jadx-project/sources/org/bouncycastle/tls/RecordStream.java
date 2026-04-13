package org.bouncycastle.tls;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import org.bouncycastle.tls.crypto.TlsCipher;
import org.bouncycastle.tls.crypto.TlsDecodeResult;
import org.bouncycastle.tls.crypto.TlsEncodeResult;
import org.bouncycastle.tls.crypto.TlsNullNullCipher;

/* loaded from: classes.dex */
class RecordStream {
    private static int DEFAULT_PLAINTEXT_LIMIT = 16384;
    private int ciphertextLimit;
    private TlsProtocol handler;
    private boolean ignoreChangeCipherSpec;
    private InputStream input;
    private final Record inputRecord;
    private OutputStream output;
    private TlsCipher pendingCipher = null;
    private int plaintextLimit;
    private TlsCipher readCipher;
    private TlsCipher readCipherDeferred;
    private final SequenceNumber readSeqNo;
    private TlsCipher writeCipher;
    private final SequenceNumber writeSeqNo;
    private ProtocolVersion writeVersion;

    public static class Record {
        volatile byte[] buf;
        private final byte[] header;
        volatile int pos;

        private Record() {
            byte[] bArr = new byte[5];
            this.header = bArr;
            this.buf = bArr;
            this.pos = 0;
        }

        private void resize(int i2) {
            if (this.buf.length < i2) {
                byte[] bArr = new byte[i2];
                System.arraycopy(this.buf, 0, bArr, 0, this.pos);
                this.buf = bArr;
            }
        }

        public void fillTo(InputStream inputStream, int i2) {
            while (this.pos < i2) {
                try {
                    int read = inputStream.read(this.buf, this.pos, i2 - this.pos);
                    if (read < 0) {
                        return;
                    } else {
                        this.pos += read;
                    }
                } catch (InterruptedIOException e2) {
                    this.pos += e2.bytesTransferred;
                    e2.bytesTransferred = 0;
                    throw e2;
                }
            }
        }

        public void readFragment(InputStream inputStream, int i2) {
            int i3 = i2 + 5;
            resize(i3);
            fillTo(inputStream, i3);
            if (this.pos < i3) {
                throw new EOFException();
            }
        }

        public boolean readHeader(InputStream inputStream) {
            fillTo(inputStream, 5);
            if (this.pos == 0) {
                return false;
            }
            if (this.pos >= 5) {
                return true;
            }
            throw new EOFException();
        }

        public void reset() {
            this.buf = this.header;
            this.pos = 0;
        }
    }

    public static class SequenceNumber {
        private boolean exhausted;
        private long value;

        private SequenceNumber() {
            this.value = 0L;
            this.exhausted = false;
        }

        public synchronized long currentValue() {
            return this.value;
        }

        public synchronized long nextValue(short s2) {
            long j2;
            if (this.exhausted) {
                throw new TlsFatalAlert(s2, "Sequence numbers exhausted");
            }
            j2 = this.value;
            long j3 = 1 + j2;
            this.value = j3;
            if (j3 == 0) {
                this.exhausted = true;
            }
            return j2;
        }

        public synchronized void reset() {
            this.value = 0L;
            this.exhausted = false;
        }
    }

    public RecordStream(TlsProtocol tlsProtocol, InputStream inputStream, OutputStream outputStream) {
        this.inputRecord = new Record();
        this.readSeqNo = new SequenceNumber();
        this.writeSeqNo = new SequenceNumber();
        TlsNullNullCipher tlsNullNullCipher = TlsNullNullCipher.INSTANCE;
        this.readCipher = tlsNullNullCipher;
        this.readCipherDeferred = null;
        this.writeCipher = tlsNullNullCipher;
        this.writeVersion = null;
        int i2 = DEFAULT_PLAINTEXT_LIMIT;
        this.plaintextLimit = i2;
        this.ciphertextLimit = i2;
        this.ignoreChangeCipherSpec = false;
        this.handler = tlsProtocol;
        this.input = inputStream;
        this.output = outputStream;
    }

    private void checkChangeCipherSpec(byte[] bArr, int i2, int i3) {
        if (1 == i3 && 1 == bArr[i2]) {
            return;
        }
        throw new TlsFatalAlert((short) 10, "Malformed " + ContentType.getText((short) 20));
    }

    private static void checkLength(int i2, int i3, short s2) {
        if (i2 > i3) {
            throw new TlsFatalAlert(s2);
        }
    }

    private short checkRecordType(byte[] bArr, int i2) {
        short readUint8 = TlsUtils.readUint8(bArr, i2);
        TlsCipher tlsCipher = this.readCipherDeferred;
        if (tlsCipher != null && readUint8 == 23) {
            this.readCipher = tlsCipher;
            this.readCipherDeferred = null;
            this.ciphertextLimit = tlsCipher.getCiphertextDecodeLimit(this.plaintextLimit);
            this.readSeqNo.reset();
        } else if (!this.readCipher.usesOpaqueRecordType()) {
            switch (readUint8) {
                case 23:
                    if (!this.handler.isApplicationDataReady()) {
                        throw new TlsFatalAlert((short) 10, "Not ready for " + ContentType.getText((short) 23));
                    }
                case 20:
                case 21:
                case 22:
                    return readUint8;
                default:
                    throw new TlsFatalAlert((short) 10, "Unsupported " + ContentType.getText(readUint8));
            }
        } else if (23 != readUint8 && (!this.ignoreChangeCipherSpec || 20 != readUint8)) {
            throw new TlsFatalAlert((short) 10, "Opaque " + ContentType.getText(readUint8));
        }
        return readUint8;
    }

    public void close() {
        this.inputRecord.reset();
        try {
            this.input.close();
            e = null;
        } catch (IOException e2) {
            e = e2;
        }
        try {
            this.output.close();
        } catch (IOException e3) {
            if (e == null) {
                e = e3;
            }
        }
        if (e != null) {
            throw e;
        }
    }

    public TlsDecodeResult decodeAndVerify(short s2, ProtocolVersion protocolVersion, byte[] bArr, int i2, int i3) {
        TlsDecodeResult decodeCiphertext = this.readCipher.decodeCiphertext(this.readSeqNo.nextValue((short) 10), s2, protocolVersion, bArr, i2, i3);
        checkLength(decodeCiphertext.len, this.plaintextLimit, (short) 22);
        if (decodeCiphertext.len >= 1 || decodeCiphertext.contentType == 23) {
            return decodeCiphertext;
        }
        throw new TlsFatalAlert((short) 47);
    }

    public void enablePendingCipherRead(boolean z2) {
        TlsCipher tlsCipher = this.pendingCipher;
        if (tlsCipher == null) {
            throw new TlsFatalAlert((short) 80);
        }
        if (this.readCipherDeferred != null) {
            throw new TlsFatalAlert((short) 80);
        }
        if (z2) {
            this.readCipherDeferred = tlsCipher;
            return;
        }
        this.readCipher = tlsCipher;
        this.ciphertextLimit = tlsCipher.getCiphertextDecodeLimit(this.plaintextLimit);
        this.readSeqNo.reset();
    }

    public void enablePendingCipherWrite() {
        TlsCipher tlsCipher = this.pendingCipher;
        if (tlsCipher == null) {
            throw new TlsFatalAlert((short) 80);
        }
        this.writeCipher = tlsCipher;
        this.writeSeqNo.reset();
    }

    public void finaliseHandshake() {
        TlsCipher tlsCipher = this.readCipher;
        TlsCipher tlsCipher2 = this.pendingCipher;
        if (tlsCipher != tlsCipher2 || this.writeCipher != tlsCipher2) {
            throw new TlsFatalAlert((short) 40);
        }
        this.pendingCipher = null;
    }

    public int getPlaintextLimit() {
        return this.plaintextLimit;
    }

    public boolean needsKeyUpdate() {
        return this.writeSeqNo.currentValue() >= 1048576;
    }

    public void notifyChangeCipherSpecReceived() {
        if (this.pendingCipher == null) {
            throw new TlsFatalAlert((short) 10, "No pending cipher");
        }
        enablePendingCipherRead(false);
    }

    public void notifyKeyUpdateReceived() {
        this.readCipher.rekeyDecoder();
        this.readSeqNo.reset();
    }

    public void notifyKeyUpdateSent() {
        this.writeCipher.rekeyEncoder();
        this.writeSeqNo.reset();
    }

    public RecordPreview previewOutputRecord(int i2) {
        int max = Math.max(0, Math.min(this.plaintextLimit, i2));
        return new RecordPreview(previewOutputRecordSize(max), max);
    }

    public int previewOutputRecordSize(int i2) {
        return this.writeCipher.getCiphertextEncodeLimit(i2, this.plaintextLimit) + 5;
    }

    public RecordPreview previewRecordHeader(byte[] bArr) {
        int i2 = 0;
        short checkRecordType = checkRecordType(bArr, 0);
        int readUint16 = TlsUtils.readUint16(bArr, 3);
        checkLength(readUint16, this.ciphertextLimit, (short) 22);
        int i3 = readUint16 + 5;
        if (23 == checkRecordType && this.handler.isApplicationDataReady()) {
            i2 = Math.max(0, Math.min(this.plaintextLimit, this.readCipher.getPlaintextLimit(readUint16)));
        }
        return new RecordPreview(i3, i2);
    }

    public boolean readFullRecord(byte[] bArr, int i2, int i3) {
        if (i3 < 5) {
            return false;
        }
        int readUint16 = TlsUtils.readUint16(bArr, i2 + 3);
        if (i3 != readUint16 + 5) {
            return false;
        }
        short checkRecordType = checkRecordType(bArr, i2 + 0);
        ProtocolVersion readVersion = TlsUtils.readVersion(bArr, i2 + 1);
        checkLength(readUint16, this.ciphertextLimit, (short) 22);
        if (this.ignoreChangeCipherSpec && 20 == checkRecordType) {
            checkChangeCipherSpec(bArr, i2 + 5, readUint16);
            return true;
        }
        TlsDecodeResult decodeAndVerify = decodeAndVerify(checkRecordType, readVersion, bArr, i2 + 5, readUint16);
        this.handler.processRecord(decodeAndVerify.contentType, decodeAndVerify.buf, decodeAndVerify.off, decodeAndVerify.len);
        return true;
    }

    public boolean readRecord() {
        if (!this.inputRecord.readHeader(this.input)) {
            return false;
        }
        short checkRecordType = checkRecordType(this.inputRecord.buf, 0);
        ProtocolVersion readVersion = TlsUtils.readVersion(this.inputRecord.buf, 1);
        int readUint16 = TlsUtils.readUint16(this.inputRecord.buf, 3);
        checkLength(readUint16, this.ciphertextLimit, (short) 22);
        this.inputRecord.readFragment(this.input, readUint16);
        try {
            if (this.ignoreChangeCipherSpec && 20 == checkRecordType) {
                checkChangeCipherSpec(this.inputRecord.buf, 5, readUint16);
                return true;
            }
            TlsDecodeResult decodeAndVerify = decodeAndVerify(checkRecordType, readVersion, this.inputRecord.buf, 5, readUint16);
            this.inputRecord.reset();
            this.handler.processRecord(decodeAndVerify.contentType, decodeAndVerify.buf, decodeAndVerify.off, decodeAndVerify.len);
            return true;
        } finally {
            this.inputRecord.reset();
        }
    }

    public void setIgnoreChangeCipherSpec(boolean z2) {
        this.ignoreChangeCipherSpec = z2;
    }

    public void setPendingCipher(TlsCipher tlsCipher) {
        this.pendingCipher = tlsCipher;
    }

    public void setPlaintextLimit(int i2) {
        this.plaintextLimit = i2;
        this.ciphertextLimit = this.readCipher.getCiphertextDecodeLimit(i2);
    }

    public void setWriteVersion(ProtocolVersion protocolVersion) {
        this.writeVersion = protocolVersion;
    }

    public void writeRecord(short s2, byte[] bArr, int i2, int i3) {
        if (this.writeVersion == null) {
            return;
        }
        checkLength(i3, this.plaintextLimit, (short) 80);
        if (i3 < 1 && s2 != 23) {
            throw new TlsFatalAlert((short) 80);
        }
        long nextValue = this.writeSeqNo.nextValue((short) 80);
        ProtocolVersion protocolVersion = this.writeVersion;
        TlsEncodeResult encodePlaintext = this.writeCipher.encodePlaintext(nextValue, s2, protocolVersion, 5, bArr, i2, i3);
        int i4 = encodePlaintext.len - 5;
        TlsUtils.checkUint16(i4);
        TlsUtils.writeUint8(encodePlaintext.recordType, encodePlaintext.buf, encodePlaintext.off + 0);
        TlsUtils.writeVersion(protocolVersion, encodePlaintext.buf, encodePlaintext.off + 1);
        TlsUtils.writeUint16(i4, encodePlaintext.buf, encodePlaintext.off + 3);
        try {
            this.output.write(encodePlaintext.buf, encodePlaintext.off, encodePlaintext.len);
            this.output.flush();
        } catch (InterruptedIOException e2) {
            throw new TlsFatalAlert((short) 80, (Throwable) e2);
        }
    }
}
