package org.bouncycastle.jcajce.io;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import javax.crypto.Cipher;
import org.bouncycastle.crypto.io.InvalidCipherTextIOException;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class CipherOutputStream extends FilterOutputStream {
    private final Cipher cipher;
    private final byte[] oneByte;

    public CipherOutputStream(OutputStream outputStream, Cipher cipher) {
        super(outputStream);
        this.oneByte = new byte[1];
        this.cipher = cipher;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(5:0|(4:1|2|(1:4)|6)|7|8|(1:10)(1:12)) */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x002e, code lost:
    
        r1 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x002f, code lost:
    
        if (r0 == null) goto L15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0031, code lost:
    
        r0 = r1;
     */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0034 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0035  */
    @Override // java.io.FilterOutputStream, java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void close() {
        IOException iOException;
        IOException iOException2;
        try {
            byte[] doFinal = this.cipher.doFinal();
            if (doFinal != null) {
                ((FilterOutputStream) this).out.write(doFinal);
            }
            iOException2 = null;
        } catch (GeneralSecurityException e2) {
            iOException = new InvalidCipherTextIOException("Error during cipher finalisation", e2);
            iOException2 = iOException;
            flush();
            ((FilterOutputStream) this).out.close();
            if (iOException2 != null) {
            }
        } catch (Exception e3) {
            iOException = new IOException(AbstractC0000a.m14j("Error closing stream: ", e3));
            iOException2 = iOException;
            flush();
            ((FilterOutputStream) this).out.close();
            if (iOException2 != null) {
            }
        }
        flush();
        ((FilterOutputStream) this).out.close();
        if (iOException2 != null) {
            throw iOException2;
        }
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream, java.io.Flushable
    public void flush() {
        ((FilterOutputStream) this).out.flush();
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream
    public void write(int i2) {
        byte[] bArr = this.oneByte;
        bArr[0] = (byte) i2;
        write(bArr, 0, 1);
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream
    public void write(byte[] bArr, int i2, int i3) {
        byte[] update = this.cipher.update(bArr, i2, i3);
        if (update != null) {
            ((FilterOutputStream) this).out.write(update);
        }
    }
}
