package g1;

import i1.C0339b;
import i1.C0342e;
import java.nio.ByteBuffer;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import l1.AbstractC0392e;
import l1.C0390c;
import l1.C0391d;
import l1.InterfaceC0388a;
import l1.InterfaceC0389b;
import l1.InterfaceC0393f;
import o1.AbstractC0447a;
import com.guard.wallet.entity.BuildConfig;

/* renamed from: g1.a */
/* loaded from: classes.dex */
public abstract class AbstractC0312a {

    /* renamed from: a */
    public int f578a = 0;

    /* renamed from: b */
    public static List m827b(InterfaceC0389b interfaceC0389b) {
        String str;
        StringBuilder sb = new StringBuilder(100);
        if (interfaceC0389b instanceof InterfaceC0388a) {
            sb.append("GET ");
            sb.append(((C0390c) ((InterfaceC0388a) interfaceC0389b)).f787b);
            str = " HTTP/1.1";
        } else {
            if (!(interfaceC0389b instanceof InterfaceC0393f)) {
                throw new IllegalArgumentException("unknown role");
            }
            sb.append("HTTP/1.1 101 ");
            str = ((C0391d) ((InterfaceC0393f) interfaceC0389b)).f788b;
        }
        sb.append(str);
        sb.append("\r\n");
        AbstractC0392e abstractC0392e = (AbstractC0392e) interfaceC0389b;
        for (String str2 : Collections.unmodifiableSet(abstractC0392e.f789a.keySet())) {
            String m960a = abstractC0392e.m960a(str2);
            sb.append(str2);
            sb.append(": ");
            sb.append(m960a);
            sb.append("\r\n");
        }
        sb.append("\r\n");
        String sb2 = sb.toString();
        CodingErrorAction codingErrorAction = AbstractC0447a.f1052a;
        byte[] bytes = sb2.getBytes(StandardCharsets.US_ASCII);
        ByteBuffer allocate = ByteBuffer.allocate(bytes.length + 0);
        allocate.put(bytes);
        allocate.flip();
        return Collections.singletonList(allocate);
    }

    /* renamed from: c */
    public static String m828c(ByteBuffer byteBuffer) {
        ByteBuffer allocate = ByteBuffer.allocate(byteBuffer.remaining());
        byte b = 48;
        while (true) {
            if (!byteBuffer.hasRemaining()) {
                byteBuffer.position(byteBuffer.position() - allocate.position());
                allocate = null;
                break;
            }
            byte b2 = byteBuffer.get();
            allocate.put(b2);
            if (b == 13 && b2 == 10) {
                allocate.limit(allocate.position() - 2);
                allocate.position(0);
                break;
            }
            b = b2;
        }
        if (allocate == null) {
            return null;
        }
        byte[] array = allocate.array();
        int limit = allocate.limit();
        CodingErrorAction codingErrorAction = AbstractC0447a.f1052a;
        return new String(array, 0, limit, StandardCharsets.US_ASCII);
    }

    /* renamed from: a */
    public abstract C0313b mo829a();

    /* JADX WARN: Code restructure failed: missing block: B:31:0x00d9, code lost:
    
        if (r1 == null) goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x00db, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x00e1, code lost:
    
        throw new i1.C0339b();
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: d */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final AbstractC0392e m830d(ByteBuffer byteBuffer) {
        C0390c c0390c;
        String str;
        String replaceFirst;
        int i2 = this.f578a;
        String m828c = m828c(byteBuffer);
        if (m828c == null) {
            throw new C0339b(byteBuffer.capacity() + 128);
        }
        String[] split = m828c.split(" ", 3);
        if (split.length != 3) {
            throw new C0342e();
        }
        if (i2 == 1) {
            if (!"101".equals(split[1])) {
                throw new C0342e(String.format("Invalid status code received: %s Status line: %s", split[1], m828c));
            }
            if (!"HTTP/1.1".equalsIgnoreCase(split[0])) {
                throw new C0342e(String.format("Invalid status line received: %s Status line: %s", split[0], m828c));
            }
            C0391d c0391d = new C0391d();
            Short.parseShort(split[1]);
            c0391d.f788b = split[2];
            c0390c = c0391d;
        } else {
            if (!"GET".equalsIgnoreCase(split[0])) {
                throw new C0342e(String.format("Invalid request method received: %s Status line: %s", split[0], m828c));
            }
            if (!"HTTP/1.1".equalsIgnoreCase(split[2])) {
                throw new C0342e(String.format("Invalid status line received: %s Status line: %s", split[2], m828c));
            }
            C0390c c0390c2 = new C0390c();
            String str2 = split[1];
            if (str2 == null) {
                throw new IllegalArgumentException("http resource descriptor must not be null");
            }
            c0390c2.f787b = str2;
            c0390c = c0390c2;
        }
        while (true) {
            String m828c2 = m828c(byteBuffer);
            if (m828c2 == null || m828c2.length() <= 0) {
                break;
            }
            String[] split2 = m828c2.split(":", 2);
            if (split2.length != 2) {
                throw new C0342e("not an http header");
            }
            if (c0390c.f789a.containsKey(split2[0])) {
                str = split2[0];
                replaceFirst = c0390c.m960a(split2[0]) + "; " + split2[1].replaceFirst("^ +", BuildConfig.FLAVOR);
            } else {
                str = split2[0];
                replaceFirst = split2[1].replaceFirst("^ +", BuildConfig.FLAVOR);
            }
            c0390c.m961b(str, replaceFirst);
        }
    }

    public String toString() {
        return getClass().getSimpleName();
    }
}
