package org.bouncycastle.tls;

import org.bouncycastle.tls.crypto.TlsCipher;

/* loaded from: classes.dex */
class DTLSEpoch {
    private final TlsCipher cipher;
    private final int epoch;
    private final DTLSReplayWindow replayWindow = new DTLSReplayWindow();
    private long sequenceNumber = 0;

    public DTLSEpoch(int i2, TlsCipher tlsCipher) {
        if (i2 < 0) {
            throw new IllegalArgumentException("'epoch' must be >= 0");
        }
        if (tlsCipher == null) {
            throw new IllegalArgumentException("'cipher' cannot be null");
        }
        this.epoch = i2;
        this.cipher = tlsCipher;
    }

    public synchronized long allocateSequenceNumber() {
        long j2;
        j2 = this.sequenceNumber;
        if (j2 >= 281474976710656L) {
            throw new TlsFatalAlert((short) 80);
        }
        this.sequenceNumber = 1 + j2;
        return j2;
    }

    public TlsCipher getCipher() {
        return this.cipher;
    }

    public int getEpoch() {
        return this.epoch;
    }

    public DTLSReplayWindow getReplayWindow() {
        return this.replayWindow;
    }

    public synchronized long getSequenceNumber() {
        return this.sequenceNumber;
    }

    public synchronized void setSequenceNumber(long j2) {
        this.sequenceNumber = j2;
    }
}
