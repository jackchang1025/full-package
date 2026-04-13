package android.sun.security.x509;

import java.io.OutputStream;
import java.util.Enumeration;

/* loaded from: classes.dex */
public interface CertAttrSet<T> {
    void delete(String str);

    void encode(OutputStream outputStream);

    Object get(String str);

    Enumeration<T> getElements();

    String getName();

    void set(String str, Object obj);

    String toString();
}
