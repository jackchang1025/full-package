package b1;

import a1.AbstractC0026q;
import android.os.Build;
import android.util.Log;
import io.github.muntashirakon.crypto.spake2.Spake2Context;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;
import java.util.Objects;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSocket;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.generators.HKDFBytesGenerator;
import org.bouncycastle.crypto.params.HKDFParameters;
import org.conscrypt.Conscrypt;
import p000a.AbstractC0000a;
import p014r.AbstractC0888a;

/* renamed from: b1.p */
/* loaded from: classes.dex */
public final class C0094p implements Closeable {

    /* renamed from: a */
    public final String f160a;

    /* renamed from: b */
    public final int f161b;

    /* renamed from: c */
    public final byte[] f162c;

    /* renamed from: d */
    public final C0093o f163d;

    /* renamed from: e */
    public final SSLContext f164e;

    /* renamed from: f */
    public DataInputStream f165f;

    /* renamed from: g */
    public DataOutputStream f166g;

    /* renamed from: h */
    public C0091m f167h;

    /* renamed from: i */
    public int f168i = 1;

    public C0094p(String str, int i2, byte[] bArr, C0089k c0089k) {
        Objects.requireNonNull(str);
        this.f160a = str;
        this.f161b = i2;
        Objects.requireNonNull(bArr);
        this.f162c = bArr;
        this.f163d = new C0093o((byte) 0, AbstractC0087i.m323c((RSAPublicKey) c0089k.f144b.getPublicKey(), "com.guard.wallet"));
        this.f164e = AbstractC0026q.m192y(c0089k);
    }

    /* renamed from: x */
    public static boolean m325x(byte b, byte b2) {
        if (b == b2) {
            return true;
        }
        Log.e("p", "Unexpected header type (expected=" + ((int) b) + " actual=" + ((int) b2) + ")");
        return false;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        Arrays.fill(this.f162c, (byte) 0);
        try {
            this.f165f.close();
        } catch (IOException unused) {
        }
        try {
            this.f166g.close();
        } catch (IOException unused2) {
        }
        if (this.f168i != 1) {
            this.f167h.destroy();
        }
    }

    /* renamed from: y */
    public final C0092n m326y() {
        StringBuilder m21q;
        String str;
        byte[] bArr = new byte[6];
        this.f165f.readFully(bArr);
        ByteBuffer order = ByteBuffer.wrap(bArr).order(ByteOrder.BIG_ENDIAN);
        byte b = order.get();
        byte b2 = order.get();
        int i2 = order.getInt();
        if (b < 1 || b > 1) {
            m21q = AbstractC0000a.m21q("PairingPacketHeader version mismatch (us=1 them=", b, ")");
        } else {
            if (b2 == 0 || b2 == 1) {
                if (i2 > 0 && i2 <= 16384) {
                    return new C0092n(b, b2, i2);
                }
                str = AbstractC0000a.m12h("Header payload not within a safe payload size (size=", i2, ")");
                Log.e("p", str);
                return null;
            }
            m21q = new StringBuilder("Unknown PairingPacket type ");
            m21q.append((int) b2);
        }
        str = m21q.toString();
        Log.e("p", str);
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x01bf  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x01c3  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x024f  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0254 A[SYNTHETIC] */
    /* renamed from: z */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m327z() {
        Class cls;
        C0091m c0091m;
        boolean z2;
        String m17m;
        boolean z3 = true;
        if (this.f168i != 1) {
            throw new IOException("Connection is not ready yet.");
        }
        this.f168i = 2;
        String str = this.f160a;
        int i2 = this.f161b;
        Socket socket = new Socket(str, i2);
        socket.setTcpNoDelay(true);
        SSLSocket sSLSocket = (SSLSocket) this.f164e.getSocketFactory().createSocket(socket, str, i2, true);
        sSLSocket.startHandshake();
        Log.d("p", "Handshake succeeded.");
        this.f165f = new DataInputStream(sSLSocket.getInputStream());
        this.f166g = new DataOutputStream(sSLSocket.getOutputStream());
        try {
            if (AbstractC0026q.f63i) {
                cls = Conscrypt.class;
                int i3 = Conscrypt.f1661a;
            } else {
                if (Build.VERSION.SDK_INT < 29) {
                    throw new SSLException("TLSv1.3 isn't supported on your platform. Use custom Conscrypt library instead.");
                }
                cls = Class.forName("com.android.org.conscrypt.Conscrypt");
            }
            byte[] bArr = (byte[]) cls.getMethod("exportKeyingMaterial", SSLSocket.class, String.class, byte[].class, Integer.TYPE).invoke(null, sSLSocket, "adb-label\u0000", null, 64);
            byte[] bArr2 = this.f162c;
            byte[] bArr3 = new byte[bArr2.length + bArr.length];
            System.arraycopy(bArr2, 0, bArr3, 0, bArr2.length);
            System.arraycopy(bArr, 0, bArr3, bArr2.length, bArr.length);
            byte[] bArr4 = C0091m.f146g;
            try {
                c0091m = new C0091m(new Spake2Context(C0091m.f146g, C0091m.f147h), bArr3);
            } catch (IllegalArgumentException | IllegalStateException unused) {
                c0091m = null;
            }
            if (c0091m == null) {
                throw new IOException("Unable to create PairingAuthCtx.");
            }
            this.f167h = c0091m;
            while (true) {
                int m1325a = AbstractC0888a.m1325a(this.f168i);
                if (m1325a == 0) {
                    break;
                }
                if (m1325a == 1) {
                    byte[] bArr5 = this.f167h.f149a;
                    int length = bArr5.length;
                    ByteBuffer order = ByteBuffer.allocate(6).order(ByteOrder.BIG_ENDIAN);
                    order.put((byte) 1).put((byte) 0).putInt(length);
                    this.f166g.write(order.array());
                    this.f166g.write(bArr5);
                    C0092n m326y = m326y();
                    if (m326y != null && m325x((byte) 0, m326y.f156b)) {
                        byte[] bArr6 = new byte[m326y.f157c];
                        this.f165f.readFully(bArr6);
                        try {
                            C0091m c0091m2 = this.f167h;
                            if (!c0091m2.f154f) {
                                byte[] m877b = c0091m2.f150b.m877b(bArr6);
                                HKDFBytesGenerator hKDFBytesGenerator = new HKDFBytesGenerator(new SHA256Digest());
                                hKDFBytesGenerator.init(new HKDFParameters(m877b, null, C0091m.f148i));
                                byte[] bArr7 = c0091m2.f151c;
                                hKDFBytesGenerator.generateBytes(bArr7, 0, bArr7.length);
                                z2 = true;
                                if (z2) {
                                    this.f168i = 4;
                                    throw new IOException("Exchanging message wasn't successful.");
                                }
                                this.f168i = 3;
                            }
                        } catch (Exception e2) {
                            Log.e("p", "Unable to initialize pairing cipher");
                            throw ((IOException) new IOException().initCause(e2));
                        }
                    }
                    z2 = false;
                    if (z2) {
                    }
                } else if (m1325a == 2) {
                    ByteBuffer order2 = ByteBuffer.allocate(8192).order(ByteOrder.BIG_ENDIAN);
                    C0093o c0093o = this.f163d;
                    order2.put(c0093o.f158a).put(c0093o.f159b);
                    C0091m c0091m3 = this.f167h;
                    byte[] array = order2.array();
                    c0091m3.getClass();
                    ByteBuffer allocate = ByteBuffer.allocate(12);
                    ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;
                    ByteBuffer order3 = allocate.order(byteOrder);
                    long j2 = c0091m3.f153e;
                    c0091m3.f153e = j2 + 1;
                    byte[] m324a = c0091m3.m324a(array, order3.putLong(j2).array(), true);
                    if (m324a == null) {
                        m17m = "Failed to encrypt peer info";
                    } else {
                        int length2 = m324a.length;
                        ByteBuffer order4 = ByteBuffer.allocate(6).order(ByteOrder.BIG_ENDIAN);
                        order4.put((byte) 1).put((byte) 1).putInt(length2);
                        this.f166g.write(order4.array());
                        this.f166g.write(m324a);
                        C0092n m326y2 = m326y();
                        if (m326y2 != null && m325x((byte) 1, m326y2.f156b)) {
                            byte[] bArr8 = new byte[m326y2.f157c];
                            this.f165f.readFully(bArr8);
                            C0091m c0091m4 = this.f167h;
                            c0091m4.getClass();
                            ByteBuffer order5 = ByteBuffer.allocate(12).order(byteOrder);
                            long j3 = c0091m4.f152d;
                            c0091m4.f152d = 1 + j3;
                            byte[] m324a2 = c0091m4.m324a(bArr8, order5.putLong(j3).array(), false);
                            if (m324a2 == null) {
                                m17m = "Unsupported payload while decrypting peer info.";
                            } else {
                                if (m324a2.length == 8192) {
                                    ByteBuffer wrap = ByteBuffer.wrap(m324a2);
                                    byte b = wrap.get();
                                    byte[] bArr9 = new byte[8191];
                                    wrap.get(bArr9);
                                    byte[] bArr10 = new byte[8191];
                                    System.arraycopy(bArr9, 0, bArr10, 0, Math.min(8191, 8191));
                                    Log.d("p", "PeerInfo{type=" + ((int) b) + ", data=" + Arrays.toString(bArr10) + '}');
                                    if (z3) {
                                        this.f168i = 4;
                                        return;
                                    } else {
                                        this.f168i = 4;
                                        throw new IOException("Could not exchange peer info.");
                                    }
                                }
                                m17m = AbstractC0000a.m17m(new StringBuilder("Got size="), m324a2.length, " PeerInfo.size=8192");
                            }
                        }
                        z3 = false;
                        if (z3) {
                        }
                    }
                    Log.e("p", m17m);
                    z3 = false;
                    if (z3) {
                    }
                } else if (m1325a == 3) {
                    break;
                }
            }
            throw new IOException("Connection closed with errors.");
        } catch (SSLException e3) {
            throw e3;
        } catch (Throwable th) {
            throw new SSLException(th);
        }
    }
}
