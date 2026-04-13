package org.bouncycastle.tls;

import android.support.v4.view.ViewCompat;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import org.bouncycastle.tls.SessionParameters;
import org.bouncycastle.tls.crypto.TlsSecret;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Integers;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public abstract class TlsProtocol implements TlsCloseable {
    protected static final short ADS_MODE_0_N = 1;
    protected static final short ADS_MODE_0_N_FIRSTONLY = 2;
    protected static final short ADS_MODE_1_Nsub1 = 0;
    protected static final short CS_CLIENT_CERTIFICATE = 15;
    protected static final short CS_CLIENT_CERTIFICATE_VERIFY = 17;
    protected static final short CS_CLIENT_END_OF_EARLY_DATA = 13;
    protected static final short CS_CLIENT_FINISHED = 18;
    protected static final short CS_CLIENT_HELLO = 1;
    protected static final short CS_CLIENT_HELLO_RETRY = 3;
    protected static final short CS_CLIENT_KEY_EXCHANGE = 16;
    protected static final short CS_CLIENT_SUPPLEMENTAL_DATA = 14;
    protected static final short CS_END = 21;
    protected static final short CS_SERVER_CERTIFICATE = 7;
    protected static final short CS_SERVER_CERTIFICATE_REQUEST = 11;
    protected static final short CS_SERVER_CERTIFICATE_STATUS = 8;
    protected static final short CS_SERVER_CERTIFICATE_VERIFY = 9;
    protected static final short CS_SERVER_ENCRYPTED_EXTENSIONS = 5;
    protected static final short CS_SERVER_FINISHED = 20;
    protected static final short CS_SERVER_HELLO = 4;
    protected static final short CS_SERVER_HELLO_DONE = 12;
    protected static final short CS_SERVER_HELLO_RETRY_REQUEST = 2;
    protected static final short CS_SERVER_KEY_EXCHANGE = 10;
    protected static final short CS_SERVER_SESSION_TICKET = 19;
    protected static final short CS_SERVER_SUPPLEMENTAL_DATA = 6;
    protected static final short CS_START = 0;
    protected static final Integer EXT_RenegotiationInfo = Integers.valueOf(65281);
    protected static final Integer EXT_SessionTicket = Integers.valueOf(35);
    private ByteQueue alertQueue;
    private volatile boolean appDataReady;
    private volatile boolean appDataSplitEnabled;
    private volatile int appDataSplitMode;
    private ByteQueue applicationDataQueue;
    protected boolean blocking;
    protected Hashtable clientExtensions;
    private volatile boolean closed;
    protected short connection_state;
    protected boolean expectSessionTicket;
    private volatile boolean failedWithError;
    TlsHandshakeHash handshakeHash;
    private ByteQueue handshakeQueue;
    protected ByteQueueInputStream inputBuffers;
    private volatile boolean keyUpdateEnabled;
    private volatile boolean keyUpdatePendingSend;
    private int maxHandshakeMessageSize;
    protected ByteQueueOutputStream outputBuffer;
    protected boolean receivedChangeCipherSpec;
    final RecordStream recordStream;
    final Object recordWriteLock;
    private volatile boolean resumableHandshake;
    protected boolean resumedSession;
    protected byte[] retryCookie;
    protected int retryGroup;
    protected boolean selectedPSK13;
    protected Hashtable serverExtensions;
    protected TlsSecret sessionMasterSecret;
    protected SessionParameters sessionParameters;
    private TlsInputStream tlsInputStream;
    private TlsOutputStream tlsOutputStream;
    protected TlsSession tlsSession;

    public TlsProtocol() {
        this.applicationDataQueue = new ByteQueue(0);
        this.alertQueue = new ByteQueue(2);
        this.handshakeQueue = new ByteQueue(0);
        this.recordWriteLock = new Object();
        this.maxHandshakeMessageSize = -1;
        this.tlsInputStream = null;
        this.tlsOutputStream = null;
        this.closed = false;
        this.failedWithError = false;
        this.appDataReady = false;
        this.appDataSplitEnabled = true;
        this.keyUpdateEnabled = false;
        this.keyUpdatePendingSend = false;
        this.resumableHandshake = false;
        this.appDataSplitMode = 0;
        this.tlsSession = null;
        this.sessionParameters = null;
        this.sessionMasterSecret = null;
        this.retryCookie = null;
        this.retryGroup = -1;
        this.clientExtensions = null;
        this.serverExtensions = null;
        this.connection_state = (short) 0;
        this.resumedSession = false;
        this.selectedPSK13 = false;
        this.receivedChangeCipherSpec = false;
        this.expectSessionTicket = false;
        this.blocking = false;
        this.inputBuffers = new ByteQueueInputStream();
        ByteQueueOutputStream byteQueueOutputStream = new ByteQueueOutputStream();
        this.outputBuffer = byteQueueOutputStream;
        this.recordStream = new RecordStream(this, this.inputBuffers, byteQueueOutputStream);
    }

    public static void assertEmpty(ByteArrayInputStream byteArrayInputStream) {
        if (byteArrayInputStream.available() > 0) {
            throw new TlsFatalAlert((short) 50);
        }
    }

    public static byte[] createRandomBlock(boolean z2, TlsContext tlsContext) {
        byte[] generateNonce = tlsContext.getNonceGenerator().generateNonce(32);
        if (z2) {
            TlsUtils.writeGMTUnixTime(generateNonce, 0);
        }
        return generateNonce;
    }

    public static byte[] createRenegotiationInfo(byte[] bArr) {
        return TlsUtils.encodeOpaque8(bArr);
    }

    public static void establishMasterSecret(TlsContext tlsContext, TlsKeyExchange tlsKeyExchange) {
        TlsSecret generatePreMasterSecret = tlsKeyExchange.generatePreMasterSecret();
        if (generatePreMasterSecret == null) {
            throw new TlsFatalAlert((short) 80);
        }
        try {
            tlsContext.getSecurityParametersHandshake().masterSecret = TlsUtils.calculateMasterSecret(tlsContext, generatePreMasterSecret);
        } finally {
            generatePreMasterSecret.destroy();
        }
    }

    private void processAlertQueue() {
        while (this.alertQueue.available() >= 2) {
            byte[] removeData = this.alertQueue.removeData(2, 0);
            handleAlertMessage(removeData[0], removeData[1]);
        }
    }

    private void processApplicationDataQueue() {
    }

    private void processChangeCipherSpec(byte[] bArr, int i2, int i3) {
        ProtocolVersion serverVersion = getContext().getServerVersion();
        if (serverVersion == null || TlsUtils.isTLSv13(serverVersion)) {
            throw new TlsFatalAlert((short) 10);
        }
        for (int i4 = 0; i4 < i3; i4++) {
            if (TlsUtils.readUint8(bArr, i2 + i4) != 1) {
                throw new TlsFatalAlert((short) 50);
            }
            if (this.receivedChangeCipherSpec || this.alertQueue.available() > 0 || this.handshakeQueue.available() > 0) {
                throw new TlsFatalAlert((short) 10);
            }
            this.recordStream.notifyChangeCipherSpecReceived();
            this.receivedChangeCipherSpec = true;
            handleChangeCipherSpecMessage();
        }
    }

    private void processHandshakeQueue(ByteQueue byteQueue) {
        ProtocolVersion serverVersion;
        ProtocolVersion serverVersion2;
        while (byteQueue.available() >= 4) {
            int readInt32 = byteQueue.readInt32();
            short s2 = (short) (readInt32 >>> 24);
            if (!HandshakeType.isRecognized(s2)) {
                throw new TlsFatalAlert((short) 10, AbstractC0000a.m11g("Handshake message of unrecognized type: ", s2));
            }
            int i2 = readInt32 & ViewCompat.MEASURED_SIZE_MASK;
            if (i2 > this.maxHandshakeMessageSize) {
                throw new TlsFatalAlert((short) 80, "Handshake message length exceeds the maximum: " + HandshakeType.getText(s2) + ", " + i2 + " > " + this.maxHandshakeMessageSize);
            }
            int i3 = i2 + 4;
            if (byteQueue.available() < i3) {
                return;
            }
            if (s2 != 0 && ((serverVersion2 = getContext().getServerVersion()) == null || !TlsUtils.isTLSv13(serverVersion2))) {
                checkReceivedChangeCipherSpec(20 == s2);
            }
            HandshakeMessageInput readHandshakeMessage = byteQueue.readHandshakeMessage(i3);
            if (s2 != 0 && s2 != 1 && s2 != 2 && (s2 == 4 ? !((serverVersion = getContext().getServerVersion()) == null || TlsUtils.isTLSv13(serverVersion)) : !(s2 == 15 || s2 == 20 || s2 == 24))) {
                readHandshakeMessage.updateHash(this.handshakeHash);
            }
            readHandshakeMessage.skip(4L);
            handleHandshakeMessage(s2, readHandshakeMessage);
        }
    }

    public static Hashtable readExtensions(ByteArrayInputStream byteArrayInputStream) {
        if (byteArrayInputStream.available() < 1) {
            return null;
        }
        byte[] readOpaque16 = TlsUtils.readOpaque16(byteArrayInputStream);
        assertEmpty(byteArrayInputStream);
        return readExtensionsData(readOpaque16);
    }

    public static Hashtable readExtensionsData(byte[] bArr) {
        Hashtable hashtable = new Hashtable();
        if (bArr.length > 0) {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
            do {
                int readUint16 = TlsUtils.readUint16(byteArrayInputStream);
                if (hashtable.put(Integers.valueOf(readUint16), TlsUtils.readOpaque16(byteArrayInputStream)) != null) {
                    throw new TlsFatalAlert((short) 47, "Repeated extension: " + ExtensionType.getText(readUint16));
                }
            } while (byteArrayInputStream.available() > 0);
        }
        return hashtable;
    }

    public static Hashtable readExtensionsData13(int i2, byte[] bArr) {
        Hashtable hashtable = new Hashtable();
        if (bArr.length > 0) {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
            do {
                int readUint16 = TlsUtils.readUint16(byteArrayInputStream);
                if (!TlsUtils.isPermittedExtensionType13(i2, readUint16)) {
                    throw new TlsFatalAlert((short) 47, "Invalid extension: " + ExtensionType.getText(readUint16));
                }
                if (hashtable.put(Integers.valueOf(readUint16), TlsUtils.readOpaque16(byteArrayInputStream)) != null) {
                    throw new TlsFatalAlert((short) 47, "Repeated extension: " + ExtensionType.getText(readUint16));
                }
            } while (byteArrayInputStream.available() > 0);
        }
        return hashtable;
    }

    public static Hashtable readExtensionsDataClientHello(byte[] bArr) {
        int readUint16;
        Hashtable hashtable = new Hashtable();
        if (bArr.length > 0) {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
            boolean z2 = false;
            do {
                readUint16 = TlsUtils.readUint16(byteArrayInputStream);
                if (hashtable.put(Integers.valueOf(readUint16), TlsUtils.readOpaque16(byteArrayInputStream)) != null) {
                    throw new TlsFatalAlert((short) 47, "Repeated extension: " + ExtensionType.getText(readUint16));
                }
                z2 |= 41 == readUint16;
            } while (byteArrayInputStream.available() > 0);
            if (z2 && 41 != readUint16) {
                throw new TlsFatalAlert((short) 47, "'pre_shared_key' MUST be last in ClientHello");
            }
        }
        return hashtable;
    }

    public static Vector readSupplementalDataMessage(ByteArrayInputStream byteArrayInputStream) {
        byte[] readOpaque24 = TlsUtils.readOpaque24(byteArrayInputStream, 1);
        assertEmpty(byteArrayInputStream);
        ByteArrayInputStream byteArrayInputStream2 = new ByteArrayInputStream(readOpaque24);
        Vector vector = new Vector();
        while (byteArrayInputStream2.available() > 0) {
            vector.addElement(new SupplementalDataEntry(TlsUtils.readUint16(byteArrayInputStream2), TlsUtils.readOpaque16(byteArrayInputStream2)));
        }
        return vector;
    }

    public static void writeExtensions(OutputStream outputStream, Hashtable hashtable) {
        writeExtensions(outputStream, hashtable, 0);
    }

    public static void writeExtensionsData(Hashtable hashtable, int i2, ByteArrayOutputStream byteArrayOutputStream) {
        writeSelectedExtensions(byteArrayOutputStream, hashtable, true);
        writeSelectedExtensions(byteArrayOutputStream, hashtable, false);
        writePreSharedKeyExtension(byteArrayOutputStream, hashtable, i2);
    }

    public static void writePreSharedKeyExtension(OutputStream outputStream, Hashtable hashtable, int i2) {
        byte[] bArr = (byte[]) hashtable.get(TlsExtensionsUtils.EXT_pre_shared_key);
        if (bArr != null) {
            TlsUtils.checkUint16(41);
            TlsUtils.writeUint16(41, outputStream);
            int length = bArr.length + i2;
            TlsUtils.checkUint16(length);
            TlsUtils.writeUint16(length, outputStream);
            outputStream.write(bArr);
        }
    }

    public static void writeSelectedExtensions(OutputStream outputStream, Hashtable hashtable, boolean z2) {
        Enumeration keys = hashtable.keys();
        while (keys.hasMoreElements()) {
            Integer num = (Integer) keys.nextElement();
            int intValue = num.intValue();
            if (41 != intValue) {
                byte[] bArr = (byte[]) hashtable.get(num);
                if (z2 == (bArr.length == 0)) {
                    TlsUtils.checkUint16(intValue);
                    TlsUtils.writeUint16(intValue, outputStream);
                    TlsUtils.writeOpaque16(bArr, outputStream);
                }
            }
        }
    }

    public static void writeSupplementalData(OutputStream outputStream, Vector vector) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        for (int i2 = 0; i2 < vector.size(); i2++) {
            SupplementalDataEntry supplementalDataEntry = (SupplementalDataEntry) vector.elementAt(i2);
            int dataType = supplementalDataEntry.getDataType();
            TlsUtils.checkUint16(dataType);
            TlsUtils.writeUint16(dataType, byteArrayOutputStream);
            TlsUtils.writeOpaque16(supplementalDataEntry.getData(), byteArrayOutputStream);
        }
        TlsUtils.writeOpaque24(byteArrayOutputStream.toByteArray(), outputStream);
    }

    public int applicationDataAvailable() {
        return this.applicationDataQueue.available();
    }

    public void applyMaxFragmentLengthExtension(short s2) {
        if (s2 >= 0) {
            if (!MaxFragmentLength.isValid(s2)) {
                throw new TlsFatalAlert((short) 80);
            }
            this.recordStream.setPlaintextLimit(1 << (s2 + 8));
        }
    }

    public void beginHandshake(boolean z2) {
        AbstractTlsContext contextAdmin = getContextAdmin();
        TlsPeer peer = getPeer();
        this.maxHandshakeMessageSize = Math.max(1024, peer.getMaxHandshakeMessageSize());
        this.handshakeHash = new DeferredHash(contextAdmin);
        this.connection_state = (short) 0;
        this.resumedSession = false;
        this.selectedPSK13 = false;
        contextAdmin.handshakeBeginning(peer);
        SecurityParameters securityParametersHandshake = contextAdmin.getSecurityParametersHandshake();
        if (z2 != securityParametersHandshake.isRenegotiating()) {
            throw new TlsFatalAlert((short) 80);
        }
        securityParametersHandshake.extendedPadding = peer.shouldUseExtendedPadding();
    }

    public void blockForHandshake() {
        while (this.connection_state != 21) {
            if (isClosed()) {
                throw new TlsFatalAlert((short) 80);
            }
            safeReadRecord();
        }
    }

    public void checkReceivedChangeCipherSpec(boolean z2) {
        if (z2 != this.receivedChangeCipherSpec) {
            throw new TlsFatalAlert((short) 10);
        }
    }

    public void cleanupHandshake() {
        SecurityParameters securityParameters;
        TlsContext context = getContext();
        if (context != null && (securityParameters = context.getSecurityParameters()) != null) {
            securityParameters.clear();
        }
        this.tlsSession = null;
        this.sessionParameters = null;
        this.sessionMasterSecret = null;
        this.retryCookie = null;
        this.retryGroup = -1;
        this.clientExtensions = null;
        this.serverExtensions = null;
        this.resumedSession = false;
        this.selectedPSK13 = false;
        this.receivedChangeCipherSpec = false;
        this.expectSessionTicket = false;
    }

    @Override // org.bouncycastle.tls.TlsCloseable
    public void close() {
        handleClose(true);
    }

    public void closeConnection() {
        this.recordStream.close();
    }

    public void closeInput() {
        if (this.blocking) {
            throw new IllegalStateException("Cannot use closeInput() in blocking mode!");
        }
        if (this.closed) {
            return;
        }
        if (this.inputBuffers.available() > 0) {
            throw new EOFException();
        }
        if (!this.appDataReady) {
            throw new TlsFatalAlert((short) 40);
        }
        if (getPeer().requiresCloseNotify()) {
            handleFailure();
            throw new TlsNoCloseNotifyException();
        }
        handleClose(false);
    }

    public void completeHandshake() {
        try {
            AbstractTlsContext contextAdmin = getContextAdmin();
            SecurityParameters securityParametersHandshake = contextAdmin.getSecurityParametersHandshake();
            if (!contextAdmin.isHandshaking() || securityParametersHandshake.getLocalVerifyData() == null || securityParametersHandshake.getPeerVerifyData() == null) {
                throw new TlsFatalAlert((short) 80);
            }
            this.recordStream.finaliseHandshake();
            this.connection_state = (short) 21;
            this.handshakeHash = new DeferredHash(contextAdmin);
            this.alertQueue.shrink();
            this.handshakeQueue.shrink();
            ProtocolVersion negotiatedVersion = securityParametersHandshake.getNegotiatedVersion();
            this.appDataSplitEnabled = !TlsUtils.isTLSv11(negotiatedVersion);
            this.appDataReady = true;
            this.keyUpdateEnabled = TlsUtils.isTLSv13(negotiatedVersion);
            if (this.blocking) {
                this.tlsInputStream = new TlsInputStream(this);
                this.tlsOutputStream = new TlsOutputStream(this);
            }
            SessionParameters sessionParameters = this.sessionParameters;
            if (sessionParameters == null) {
                this.sessionMasterSecret = securityParametersHandshake.getMasterSecret();
                this.sessionParameters = new SessionParameters.Builder().setCipherSuite(securityParametersHandshake.getCipherSuite()).setCompressionAlgorithm(securityParametersHandshake.getCompressionAlgorithm()).setExtendedMasterSecret(securityParametersHandshake.isExtendedMasterSecret()).setLocalCertificate(securityParametersHandshake.getLocalCertificate()).setMasterSecret(contextAdmin.getCrypto().adoptSecret(this.sessionMasterSecret)).setNegotiatedVersion(securityParametersHandshake.getNegotiatedVersion()).setPeerCertificate(securityParametersHandshake.getPeerCertificate()).setPSKIdentity(securityParametersHandshake.getPSKIdentity()).setSRPIdentity(securityParametersHandshake.getSRPIdentity()).setServerExtensions(this.serverExtensions).build();
                this.tlsSession = TlsUtils.importSession(securityParametersHandshake.getSessionID(), this.sessionParameters);
            } else {
                securityParametersHandshake.localCertificate = sessionParameters.getLocalCertificate();
                securityParametersHandshake.peerCertificate = this.sessionParameters.getPeerCertificate();
                securityParametersHandshake.pskIdentity = this.sessionParameters.getPSKIdentity();
                securityParametersHandshake.srpIdentity = this.sessionParameters.getSRPIdentity();
            }
            contextAdmin.handshakeComplete(getPeer(), this.tlsSession);
        } finally {
            cleanupHandshake();
        }
    }

    public boolean establishSession(TlsSession tlsSession) {
        SessionParameters exportSessionParameters;
        this.tlsSession = null;
        this.sessionParameters = null;
        this.sessionMasterSecret = null;
        if (tlsSession == null || !tlsSession.isResumable() || (exportSessionParameters = tlsSession.exportSessionParameters()) == null) {
            return false;
        }
        if (!exportSessionParameters.isExtendedMasterSecret()) {
            TlsPeer peer = getPeer();
            if (!peer.allowLegacyResumption() || peer.requiresExtendedMasterSecret()) {
                return false;
            }
        }
        TlsSecret sessionMasterSecret = TlsUtils.getSessionMasterSecret(getContext().getCrypto(), exportSessionParameters.getMasterSecret());
        if (sessionMasterSecret == null) {
            return false;
        }
        this.tlsSession = tlsSession;
        this.sessionParameters = exportSessionParameters;
        this.sessionMasterSecret = sessionMasterSecret;
        return true;
    }

    public void flush() {
    }

    public int getAppDataSplitMode() {
        return this.appDataSplitMode;
    }

    public int getApplicationDataLimit() {
        return this.recordStream.getPlaintextLimit();
    }

    public int getAvailableInputBytes() {
        if (this.blocking) {
            throw new IllegalStateException("Cannot use getAvailableInputBytes() in blocking mode! Use getInputStream().available() instead.");
        }
        return applicationDataAvailable();
    }

    public int getAvailableOutputBytes() {
        if (this.blocking) {
            throw new IllegalStateException("Cannot use getAvailableOutputBytes() in blocking mode! Use getOutputStream() instead.");
        }
        return this.outputBuffer.getBuffer().available();
    }

    public abstract TlsContext getContext();

    public abstract AbstractTlsContext getContextAdmin();

    public InputStream getInputStream() {
        if (this.blocking) {
            return this.tlsInputStream;
        }
        throw new IllegalStateException("Cannot use InputStream in non-blocking mode! Use offerInput() instead.");
    }

    public OutputStream getOutputStream() {
        if (this.blocking) {
            return this.tlsOutputStream;
        }
        throw new IllegalStateException("Cannot use OutputStream in non-blocking mode! Use offerOutput() instead.");
    }

    public abstract TlsPeer getPeer();

    public int getRenegotiationPolicy() {
        return 0;
    }

    public void handleAlertMessage(short s2, short s3) {
        getPeer().notifyAlertReceived(s2, s3);
        if (s2 == 1) {
            handleAlertWarningMessage(s3);
        } else {
            handleFailure();
            throw new TlsFatalAlertReceived(s3);
        }
    }

    public void handleAlertWarningMessage(short s2) {
        if (s2 == 0) {
            if (!this.appDataReady) {
                throw new TlsFatalAlert((short) 40);
            }
            handleClose(false);
        } else {
            if (s2 == 41) {
                throw new TlsFatalAlert((short) 10);
            }
            if (s2 == 100) {
                throw new TlsFatalAlert((short) 40);
            }
        }
    }

    public void handleChangeCipherSpecMessage() {
    }

    public void handleClose(boolean z2) {
        if (this.closed) {
            return;
        }
        this.closed = true;
        if (!this.appDataReady) {
            cleanupHandshake();
            if (z2) {
                raiseAlertWarning((short) 90, "User canceled handshake");
            }
        }
        raiseAlertWarning((short) 0, "Connection closed");
        closeConnection();
    }

    public void handleException(short s2, String str, Throwable th) {
        if (((this.appDataReady || isResumableHandshake()) && (th instanceof InterruptedIOException)) || this.closed) {
            return;
        }
        raiseAlertFatal(s2, str, th);
        handleFailure();
    }

    public void handleFailure() {
        this.closed = true;
        this.failedWithError = true;
        invalidateSession();
        if (!this.appDataReady) {
            cleanupHandshake();
        }
        closeConnection();
    }

    public abstract void handleHandshakeMessage(short s2, HandshakeMessageInput handshakeMessageInput);

    /* JADX WARN: Removed duplicated region for block: B:14:0x0031  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x003c A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean handleRenegotiation() {
        int i2;
        SecurityParameters securityParametersConnection = getContext().getSecurityParametersConnection();
        if (securityParametersConnection != null && securityParametersConnection.isSecureRenegotiation()) {
            Certificate localCertificate = securityParametersConnection.getEntity() == 0 ? securityParametersConnection.getLocalCertificate() : securityParametersConnection.getPeerCertificate();
            if (localCertificate != null && !localCertificate.isEmpty()) {
                i2 = getRenegotiationPolicy();
                if (i2 != 1) {
                    return false;
                }
                if (i2 != 2) {
                    refuseRenegotiation();
                    return false;
                }
                beginHandshake(true);
                return true;
            }
        }
        i2 = 0;
        if (i2 != 1) {
        }
    }

    public void invalidateSession() {
        TlsSecret tlsSecret = this.sessionMasterSecret;
        if (tlsSecret != null) {
            tlsSecret.destroy();
            this.sessionMasterSecret = null;
        }
        SessionParameters sessionParameters = this.sessionParameters;
        if (sessionParameters != null) {
            sessionParameters.clear();
            this.sessionParameters = null;
        }
        TlsSession tlsSession = this.tlsSession;
        if (tlsSession != null) {
            tlsSession.invalidate();
            this.tlsSession = null;
        }
    }

    public boolean isApplicationDataReady() {
        return this.appDataReady;
    }

    public boolean isClosed() {
        return this.closed;
    }

    public boolean isConnected() {
        AbstractTlsContext contextAdmin;
        return (this.closed || (contextAdmin = getContextAdmin()) == null || !contextAdmin.isConnected()) ? false : true;
    }

    public boolean isHandshaking() {
        AbstractTlsContext contextAdmin;
        return (this.closed || (contextAdmin = getContextAdmin()) == null || !contextAdmin.isHandshaking()) ? false : true;
    }

    public boolean isLegacyConnectionState() {
        switch (this.connection_state) {
            case 0:
            case 1:
            case 4:
            case 6:
            case 7:
            case 8:
            case 10:
            case 11:
            case 12:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
                return true;
            case 2:
            case 3:
            case 5:
            case 9:
            case 13:
            default:
                return false;
        }
    }

    public boolean isResumableHandshake() {
        return this.resumableHandshake;
    }

    public boolean isTLSv13ConnectionState() {
        switch (this.connection_state) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 7:
            case 9:
            case 11:
            case 13:
            case 15:
            case 17:
            case 18:
            case 20:
            case 21:
                return true;
            case 6:
            case 8:
            case 10:
            case 12:
            case 14:
            case 16:
            case 19:
            default:
                return false;
        }
    }

    public void offerInput(byte[] bArr) {
        offerInput(bArr, 0, bArr.length);
    }

    public RecordPreview previewInputRecord(byte[] bArr) {
        if (this.blocking) {
            throw new IllegalStateException("Cannot use previewInputRecord() in blocking mode!");
        }
        if (this.inputBuffers.available() != 0) {
            throw new IllegalStateException("Can only use previewInputRecord() for record-aligned input.");
        }
        if (this.closed) {
            throw new IOException("Connection is closed, cannot accept any more input");
        }
        return safePreviewRecordHeader(bArr);
    }

    public RecordPreview previewOutputRecord(int i2) {
        if (!this.appDataReady) {
            throw new IllegalStateException("Cannot use previewOutputRecord() until initial handshake completed.");
        }
        if (this.blocking) {
            throw new IllegalStateException("Cannot use previewOutputRecord() in blocking mode!");
        }
        if (this.outputBuffer.getBuffer().available() != 0) {
            throw new IllegalStateException("Can only use previewOutputRecord() for record-aligned output.");
        }
        if (this.closed) {
            throw new IOException("Connection is closed, cannot produce any more output");
        }
        if (i2 < 1) {
            return new RecordPreview(0, 0);
        }
        if (this.appDataSplitEnabled) {
            int i3 = this.appDataSplitMode;
            if (i3 == 1 || i3 == 2) {
                return RecordPreview.combineAppData(this.recordStream.previewOutputRecord(0), this.recordStream.previewOutputRecord(i2));
            }
            RecordPreview previewOutputRecord = this.recordStream.previewOutputRecord(1);
            return i2 > 1 ? RecordPreview.combineAppData(previewOutputRecord, this.recordStream.previewOutputRecord(i2 - 1)) : previewOutputRecord;
        }
        RecordPreview previewOutputRecord2 = this.recordStream.previewOutputRecord(i2);
        if (!this.keyUpdateEnabled) {
            return previewOutputRecord2;
        }
        if (this.keyUpdatePendingSend || this.recordStream.needsKeyUpdate()) {
            return RecordPreview.extendRecordSize(previewOutputRecord2, this.recordStream.previewOutputRecordSize(HandshakeMessageOutput.getLength(1)));
        }
        return previewOutputRecord2;
    }

    public void process13FinishedMessage(ByteArrayInputStream byteArrayInputStream) {
        TlsContext context = getContext();
        SecurityParameters securityParametersHandshake = context.getSecurityParametersHandshake();
        boolean isServer = context.isServer();
        byte[] readFully = TlsUtils.readFully(securityParametersHandshake.getVerifyDataLength(), byteArrayInputStream);
        assertEmpty(byteArrayInputStream);
        byte[] calculateVerifyData = TlsUtils.calculateVerifyData(context, this.handshakeHash, !isServer);
        if (!Arrays.constantTimeAreEqual(calculateVerifyData, readFully)) {
            throw new TlsFatalAlert((short) 51);
        }
        securityParametersHandshake.peerVerifyData = calculateVerifyData;
        securityParametersHandshake.tlsUnique = null;
    }

    public void processFinishedMessage(ByteArrayInputStream byteArrayInputStream) {
        TlsContext context = getContext();
        SecurityParameters securityParametersHandshake = context.getSecurityParametersHandshake();
        boolean isServer = context.isServer();
        byte[] readFully = TlsUtils.readFully(securityParametersHandshake.getVerifyDataLength(), byteArrayInputStream);
        assertEmpty(byteArrayInputStream);
        byte[] calculateVerifyData = TlsUtils.calculateVerifyData(context, this.handshakeHash, !isServer);
        if (!Arrays.constantTimeAreEqual(calculateVerifyData, readFully)) {
            throw new TlsFatalAlert((short) 51);
        }
        securityParametersHandshake.peerVerifyData = calculateVerifyData;
        if ((!this.resumedSession || securityParametersHandshake.isExtendedMasterSecret()) && securityParametersHandshake.getLocalVerifyData() == null) {
            securityParametersHandshake.tlsUnique = calculateVerifyData;
        }
    }

    public short processMaxFragmentLengthExtension(Hashtable hashtable, Hashtable hashtable2, short s2) {
        short maxFragmentLengthExtension = TlsExtensionsUtils.getMaxFragmentLengthExtension(hashtable2);
        if (maxFragmentLengthExtension < 0 || (MaxFragmentLength.isValid(maxFragmentLengthExtension) && (this.resumedSession || maxFragmentLengthExtension == TlsExtensionsUtils.getMaxFragmentLengthExtension(hashtable)))) {
            return maxFragmentLengthExtension;
        }
        throw new TlsFatalAlert(s2);
    }

    public void processRecord(short s2, byte[] bArr, int i2, int i3) {
        switch (s2) {
            case 20:
                processChangeCipherSpec(bArr, i2, i3);
                return;
            case 21:
                this.alertQueue.addData(bArr, i2, i3);
                processAlertQueue();
                return;
            case 22:
                if (this.handshakeQueue.available() > 0) {
                    this.handshakeQueue.addData(bArr, i2, i3);
                    processHandshakeQueue(this.handshakeQueue);
                    return;
                }
                ByteQueue byteQueue = new ByteQueue(bArr, i2, i3);
                processHandshakeQueue(byteQueue);
                int available = byteQueue.available();
                if (available > 0) {
                    this.handshakeQueue.addData(bArr, (i2 + i3) - available, available);
                    return;
                }
                return;
            case 23:
                if (!this.appDataReady) {
                    throw new TlsFatalAlert((short) 10);
                }
                this.applicationDataQueue.addData(bArr, i2, i3);
                processApplicationDataQueue();
                return;
            default:
                throw new TlsFatalAlert((short) 10);
        }
    }

    public void raiseAlertFatal(short s2, String str, Throwable th) {
        getPeer().notifyAlertRaised((short) 2, s2, str, th);
        try {
            this.recordStream.writeRecord((short) 21, new byte[]{2, (byte) s2}, 0, 2);
        } catch (Exception unused) {
        }
    }

    public void raiseAlertWarning(short s2, String str) {
        getPeer().notifyAlertRaised((short) 1, s2, str, null);
        safeWriteRecord((short) 21, new byte[]{1, (byte) s2}, 0, 2);
    }

    public int readApplicationData(byte[] bArr, int i2, int i3) {
        if (i3 < 1) {
            return 0;
        }
        while (this.applicationDataQueue.available() == 0) {
            if (this.closed) {
                if (this.failedWithError) {
                    throw new IOException("Cannot read application data on failed TLS connection");
                }
                return -1;
            }
            if (!this.appDataReady) {
                throw new IllegalStateException("Cannot read application data until initial handshake completed.");
            }
            safeReadRecord();
        }
        int min = Math.min(i3, this.applicationDataQueue.available());
        this.applicationDataQueue.removeData(bArr, i2, min, 0);
        return min;
    }

    public int readInput(byte[] bArr, int i2, int i3) {
        if (this.blocking) {
            throw new IllegalStateException("Cannot use readInput() in blocking mode! Use getInputStream() instead.");
        }
        int min = Math.min(i3, this.applicationDataQueue.available());
        if (min < 1) {
            return 0;
        }
        this.applicationDataQueue.removeData(bArr, i2, min, 0);
        return min;
    }

    public int readOutput(byte[] bArr, int i2, int i3) {
        if (this.blocking) {
            throw new IllegalStateException("Cannot use readOutput() in blocking mode! Use getOutputStream() instead.");
        }
        int min = Math.min(getAvailableOutputBytes(), i3);
        this.outputBuffer.getBuffer().removeData(bArr, i2, min, 0);
        return min;
    }

    public void receive13KeyUpdate(ByteArrayInputStream byteArrayInputStream) {
        if (!this.appDataReady || !this.keyUpdateEnabled) {
            throw new TlsFatalAlert((short) 10);
        }
        short readUint8 = TlsUtils.readUint8(byteArrayInputStream);
        assertEmpty(byteArrayInputStream);
        if (!KeyUpdateRequest.isValid(readUint8)) {
            throw new TlsFatalAlert((short) 47);
        }
        boolean z2 = 1 == readUint8;
        TlsUtils.update13TrafficSecretPeer(getContext());
        this.recordStream.notifyKeyUpdateReceived();
        this.keyUpdatePendingSend = z2 | this.keyUpdatePendingSend;
    }

    public void refuseRenegotiation() {
        if (TlsUtils.isSSL(getContext())) {
            throw new TlsFatalAlert((short) 40);
        }
        raiseAlertWarning((short) 100, "Renegotiation not supported");
    }

    public void resumeHandshake() {
        if (!this.blocking) {
            throw new IllegalStateException("Cannot use resumeHandshake() in non-blocking mode!");
        }
        if (!isHandshaking()) {
            throw new IllegalStateException("No handshake in progress");
        }
        blockForHandshake();
    }

    public RecordPreview safePreviewRecordHeader(byte[] bArr) {
        try {
            return this.recordStream.previewRecordHeader(bArr);
        } catch (RuntimeException e2) {
            handleException((short) 80, "Failed to read record", e2);
            throw new TlsFatalAlert((short) 80, e2);
        } catch (TlsFatalAlert e3) {
            handleException(e3.getAlertDescription(), "Failed to read record", e3);
            throw e3;
        } catch (IOException e4) {
            handleException((short) 80, "Failed to read record", e4);
            throw e4;
        }
    }

    public boolean safeReadFullRecord(byte[] bArr, int i2, int i3) {
        try {
            return this.recordStream.readFullRecord(bArr, i2, i3);
        } catch (RuntimeException e2) {
            handleException((short) 80, "Failed to process record", e2);
            throw new TlsFatalAlert((short) 80, e2);
        } catch (TlsFatalAlert e3) {
            handleException(e3.getAlertDescription(), "Failed to process record", e3);
            throw e3;
        } catch (IOException e4) {
            handleException((short) 80, "Failed to process record", e4);
            throw e4;
        }
    }

    public void safeReadRecord() {
        try {
            if (this.recordStream.readRecord()) {
                return;
            }
            if (!this.appDataReady) {
                throw new TlsFatalAlert((short) 40);
            }
            if (getPeer().requiresCloseNotify()) {
                handleFailure();
                throw new TlsNoCloseNotifyException();
            }
            handleClose(false);
        } catch (RuntimeException e2) {
            handleException((short) 80, "Failed to read record", e2);
            throw new TlsFatalAlert((short) 80, e2);
        } catch (TlsFatalAlert e3) {
            handleException(e3.getAlertDescription(), "Failed to read record", e3);
            throw e3;
        } catch (TlsFatalAlertReceived e4) {
            throw e4;
        } catch (IOException e5) {
            handleException((short) 80, "Failed to read record", e5);
            throw e5;
        }
    }

    public void safeWriteRecord(short s2, byte[] bArr, int i2, int i3) {
        try {
            this.recordStream.writeRecord(s2, bArr, i2, i3);
        } catch (RuntimeException e2) {
            handleException((short) 80, "Failed to write record", e2);
            throw new TlsFatalAlert((short) 80, e2);
        } catch (TlsFatalAlert e3) {
            handleException(e3.getAlertDescription(), "Failed to write record", e3);
            throw e3;
        } catch (IOException e4) {
            handleException((short) 80, "Failed to write record", e4);
            throw e4;
        }
    }

    public void send13CertificateMessage(Certificate certificate) {
        if (certificate == null) {
            throw new TlsFatalAlert((short) 80);
        }
        TlsContext context = getContext();
        SecurityParameters securityParametersHandshake = context.getSecurityParametersHandshake();
        if (securityParametersHandshake.getLocalCertificate() != null) {
            throw new TlsFatalAlert((short) 80);
        }
        HandshakeMessageOutput handshakeMessageOutput = new HandshakeMessageOutput((short) 11);
        certificate.encode(context, handshakeMessageOutput, null);
        handshakeMessageOutput.send(this);
        securityParametersHandshake.localCertificate = certificate;
    }

    public void send13CertificateVerifyMessage(DigitallySigned digitallySigned) {
        HandshakeMessageOutput handshakeMessageOutput = new HandshakeMessageOutput((short) 15);
        digitallySigned.encode(handshakeMessageOutput);
        handshakeMessageOutput.send(this);
    }

    public void send13FinishedMessage() {
        TlsContext context = getContext();
        SecurityParameters securityParametersHandshake = context.getSecurityParametersHandshake();
        byte[] calculateVerifyData = TlsUtils.calculateVerifyData(context, this.handshakeHash, context.isServer());
        securityParametersHandshake.localVerifyData = calculateVerifyData;
        securityParametersHandshake.tlsUnique = null;
        HandshakeMessageOutput.send(this, (short) 20, calculateVerifyData);
    }

    public void send13KeyUpdate(boolean z2) {
        if (!this.appDataReady || !this.keyUpdateEnabled) {
            throw new TlsFatalAlert((short) 80);
        }
        HandshakeMessageOutput.send(this, (short) 24, TlsUtils.encodeUint8(z2 ? (short) 1 : (short) 0));
        TlsUtils.update13TrafficSecretLocal(getContext());
        this.recordStream.notifyKeyUpdateSent();
        this.keyUpdatePendingSend = (z2 ? 1 : 0) & (this.keyUpdatePendingSend ? 1 : 0);
    }

    public void sendCertificateMessage(Certificate certificate, OutputStream outputStream) {
        TlsContext context = getContext();
        SecurityParameters securityParametersHandshake = context.getSecurityParametersHandshake();
        if (securityParametersHandshake.getLocalCertificate() != null) {
            throw new TlsFatalAlert((short) 80);
        }
        if (certificate == null) {
            certificate = Certificate.EMPTY_CHAIN;
        }
        if (certificate.isEmpty() && !context.isServer() && securityParametersHandshake.getNegotiatedVersion().isSSL()) {
            raiseAlertWarning((short) 41, "SSLv3 client didn't provide credentials");
        } else {
            HandshakeMessageOutput handshakeMessageOutput = new HandshakeMessageOutput((short) 11);
            certificate.encode(context, handshakeMessageOutput, outputStream);
            handshakeMessageOutput.send(this);
        }
        securityParametersHandshake.localCertificate = certificate;
    }

    public void sendChangeCipherSpec() {
        sendChangeCipherSpecMessage();
        this.recordStream.enablePendingCipherWrite();
    }

    public void sendChangeCipherSpecMessage() {
        safeWriteRecord((short) 20, new byte[]{1}, 0, 1);
    }

    public void sendFinishedMessage() {
        TlsContext context = getContext();
        SecurityParameters securityParametersHandshake = context.getSecurityParametersHandshake();
        byte[] calculateVerifyData = TlsUtils.calculateVerifyData(context, this.handshakeHash, context.isServer());
        securityParametersHandshake.localVerifyData = calculateVerifyData;
        if ((!this.resumedSession || securityParametersHandshake.isExtendedMasterSecret()) && securityParametersHandshake.getPeerVerifyData() == null) {
            securityParametersHandshake.tlsUnique = calculateVerifyData;
        }
        HandshakeMessageOutput.send(this, (short) 20, calculateVerifyData);
    }

    public void sendSupplementalDataMessage(Vector vector) {
        HandshakeMessageOutput handshakeMessageOutput = new HandshakeMessageOutput((short) 23);
        writeSupplementalData(handshakeMessageOutput, vector);
        handshakeMessageOutput.send(this);
    }

    public void setAppDataSplitMode(int i2) {
        if (i2 < 0 || i2 > 2) {
            throw new IllegalArgumentException(AbstractC0000a.m11g("Illegal appDataSplitMode mode: ", i2));
        }
        this.appDataSplitMode = i2;
    }

    public void setResumableHandshake(boolean z2) {
        this.resumableHandshake = z2;
    }

    public void writeApplicationData(byte[] bArr, int i2, int i3) {
        if (!this.appDataReady) {
            throw new IllegalStateException("Cannot write application data until initial handshake completed.");
        }
        synchronized (this.recordWriteLock) {
            while (i3 > 0) {
                if (this.closed) {
                    throw new IOException("Cannot write application data on closed/failed TLS connection");
                }
                if (this.appDataSplitEnabled) {
                    int i4 = this.appDataSplitMode;
                    if (i4 != 1) {
                        if (i4 == 2) {
                            this.appDataSplitEnabled = false;
                        } else if (i3 > 1) {
                            safeWriteRecord((short) 23, bArr, i2, 1);
                            i2++;
                            i3--;
                        }
                    }
                    safeWriteRecord((short) 23, TlsUtils.EMPTY_BYTES, 0, 0);
                } else if (this.keyUpdateEnabled) {
                    if (this.keyUpdatePendingSend) {
                        send13KeyUpdate(false);
                    } else if (this.recordStream.needsKeyUpdate()) {
                        send13KeyUpdate(true);
                    }
                }
                int min = Math.min(i3, this.recordStream.getPlaintextLimit());
                safeWriteRecord((short) 23, bArr, i2, min);
                i2 += min;
                i3 -= min;
            }
        }
    }

    public void writeHandshakeMessage(byte[] bArr, int i2, int i3) {
        ProtocolVersion serverVersion;
        if (i3 < 4) {
            throw new TlsFatalAlert((short) 80);
        }
        short readUint8 = TlsUtils.readUint8(bArr, i2);
        if (readUint8 != 0 && readUint8 != 1 && (readUint8 == 4 ? !((serverVersion = getContext().getServerVersion()) == null || TlsUtils.isTLSv13(serverVersion)) : readUint8 != 24)) {
            this.handshakeHash.update(bArr, i2, i3);
        }
        int i4 = 0;
        do {
            int min = Math.min(i3 - i4, this.recordStream.getPlaintextLimit());
            safeWriteRecord((short) 22, bArr, i2 + i4, min);
            i4 += min;
        } while (i4 < i3);
    }

    public TlsProtocol(InputStream inputStream, OutputStream outputStream) {
        this.applicationDataQueue = new ByteQueue(0);
        this.alertQueue = new ByteQueue(2);
        this.handshakeQueue = new ByteQueue(0);
        this.recordWriteLock = new Object();
        this.maxHandshakeMessageSize = -1;
        this.tlsInputStream = null;
        this.tlsOutputStream = null;
        this.closed = false;
        this.failedWithError = false;
        this.appDataReady = false;
        this.appDataSplitEnabled = true;
        this.keyUpdateEnabled = false;
        this.keyUpdatePendingSend = false;
        this.resumableHandshake = false;
        this.appDataSplitMode = 0;
        this.tlsSession = null;
        this.sessionParameters = null;
        this.sessionMasterSecret = null;
        this.retryCookie = null;
        this.retryGroup = -1;
        this.clientExtensions = null;
        this.serverExtensions = null;
        this.connection_state = (short) 0;
        this.resumedSession = false;
        this.selectedPSK13 = false;
        this.receivedChangeCipherSpec = false;
        this.expectSessionTicket = false;
        this.blocking = true;
        this.recordStream = new RecordStream(this, inputStream, outputStream);
    }

    public static void writeExtensions(OutputStream outputStream, Hashtable hashtable, int i2) {
        if (hashtable == null || hashtable.isEmpty()) {
            return;
        }
        byte[] writeExtensionsData = writeExtensionsData(hashtable, i2);
        int length = writeExtensionsData.length + i2;
        TlsUtils.checkUint16(length);
        TlsUtils.writeUint16(length, outputStream);
        outputStream.write(writeExtensionsData);
    }

    public static byte[] writeExtensionsData(Hashtable hashtable) {
        return writeExtensionsData(hashtable, 0);
    }

    public void offerInput(byte[] bArr, int i2, int i3) {
        if (this.blocking) {
            throw new IllegalStateException("Cannot use offerInput() in blocking mode! Use getInputStream() instead.");
        }
        if (this.closed) {
            throw new IOException("Connection is closed, cannot accept any more input");
        }
        if (this.inputBuffers.available() == 0 && safeReadFullRecord(bArr, i2, i3)) {
            if (this.closed && !this.appDataReady) {
                throw new TlsFatalAlert((short) 80);
            }
            return;
        }
        this.inputBuffers.addBytes(bArr, i2, i3);
        while (this.inputBuffers.available() >= 5) {
            byte[] bArr2 = new byte[5];
            if (5 != this.inputBuffers.peek(bArr2)) {
                throw new TlsFatalAlert((short) 80);
            }
            if (this.inputBuffers.available() < safePreviewRecordHeader(bArr2).getRecordSize()) {
                return;
            }
            safeReadRecord();
            if (this.closed) {
                if (!this.appDataReady) {
                    throw new TlsFatalAlert((short) 80);
                }
                return;
            }
        }
    }

    public static byte[] writeExtensionsData(Hashtable hashtable, int i2) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        writeExtensionsData(hashtable, i2, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}
