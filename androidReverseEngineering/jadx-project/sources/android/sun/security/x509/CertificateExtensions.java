package android.sun.security.x509;

import android.sun.misc.HexDumpEncoder;
import android.sun.security.util.Debug;
import android.sun.security.util.DerInputStream;
import android.sun.security.util.DerOutputStream;
import android.sun.security.util.DerValue;
import android.sun.security.util.ObjectIdentifier;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.security.cert.CertificateException;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class CertificateExtensions implements CertAttrSet<Extension> {
    public static final String IDENT = "x509.info.extensions";
    public static final String NAME = "extensions";
    private Map<String, Extension> unparseableExtensions;
    private static final Debug debug = Debug.getInstance(X509CertImpl.NAME);
    private static Class[] PARAMS = {Boolean.class, Object.class};
    private Hashtable<String, Extension> map = new Hashtable<>();
    private boolean unsupportedCritExt = false;

    public CertificateExtensions() {
    }

    private void init(DerInputStream derInputStream) {
        for (DerValue derValue : derInputStream.getSequence(5)) {
            parseExtension(new Extension(derValue));
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void parseExtension(Extension extension) {
        try {
            Class cls = OIDMap.getClass(extension.getExtensionId());
            if (cls != null) {
                CertAttrSet certAttrSet = (CertAttrSet) cls.getConstructor(PARAMS).newInstance(Boolean.valueOf(extension.isCritical()), extension.getExtensionValue());
                if (this.map.put(certAttrSet.getName(), (Extension) certAttrSet) != null) {
                    throw new IOException("Duplicate extensions not allowed");
                }
            } else {
                if (extension.isCritical()) {
                    this.unsupportedCritExt = true;
                }
                if (this.map.put(extension.getExtensionId().toString(), extension) != null) {
                    throw new IOException("Duplicate extensions not allowed");
                }
            }
        } catch (IOException e2) {
            throw e2;
        } catch (InvocationTargetException e3) {
            Throwable targetException = e3.getTargetException();
            if (extension.isCritical()) {
                if (!(targetException instanceof IOException)) {
                    throw ((IOException) new IOException(targetException.toString()).initCause(targetException));
                }
                throw ((IOException) targetException);
            }
            if (this.unparseableExtensions == null) {
                this.unparseableExtensions = new HashMap();
            }
            this.unparseableExtensions.put(extension.getExtensionId().toString(), new UnparseableExtension(extension, targetException));
            Debug debug2 = debug;
            if (debug2 != null) {
                debug2.println("Error parsing extension: " + extension);
                targetException.printStackTrace();
                System.err.println(new HexDumpEncoder().encodeBuffer(extension.getExtensionValue()));
            }
        } catch (Exception e4) {
            throw ((IOException) new IOException(e4.toString()).initCause(e4));
        }
    }

    @Override // android.sun.security.x509.CertAttrSet
    public void delete(String str) {
        if (this.map.get(str) == null) {
            throw new IOException(AbstractC0000a.m15k("No extension found with name ", str));
        }
        this.map.remove(str);
    }

    @Override // android.sun.security.x509.CertAttrSet
    public void encode(OutputStream outputStream) {
        encode(outputStream, false);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CertificateExtensions)) {
            return false;
        }
        CertificateExtensions certificateExtensions = (CertificateExtensions) obj;
        Object[] array = certificateExtensions.getAllExtensions().toArray();
        int length = array.length;
        if (length != this.map.size()) {
            return false;
        }
        String str = null;
        for (int i2 = 0; i2 < length; i2++) {
            Object obj2 = array[i2];
            if (obj2 instanceof CertAttrSet) {
                str = ((CertAttrSet) obj2).getName();
            }
            Extension extension = (Extension) array[i2];
            if (str == null) {
                str = extension.getExtensionId().toString();
            }
            Extension extension2 = this.map.get(str);
            if (extension2 == null || !extension2.equals(extension)) {
                return false;
            }
        }
        return getUnparseableExtensions().equals(certificateExtensions.getUnparseableExtensions());
    }

    @Override // android.sun.security.x509.CertAttrSet
    public Object get(String str) {
        Extension extension = this.map.get(str);
        if (extension != null) {
            return extension;
        }
        throw new IOException(AbstractC0000a.m15k("No extension found with name ", str));
    }

    public Collection<Extension> getAllExtensions() {
        return this.map.values();
    }

    @Override // android.sun.security.x509.CertAttrSet
    public Enumeration<Extension> getElements() {
        return this.map.elements();
    }

    @Override // android.sun.security.x509.CertAttrSet
    public String getName() {
        return "extensions";
    }

    public String getNameByOid(ObjectIdentifier objectIdentifier) {
        for (String str : this.map.keySet()) {
            if (this.map.get(str).getExtensionId().equals(objectIdentifier)) {
                return str;
            }
        }
        return null;
    }

    public Map<String, Extension> getUnparseableExtensions() {
        Map<String, Extension> map = this.unparseableExtensions;
        return map == null ? Collections.emptyMap() : map;
    }

    public boolean hasUnsupportedCriticalExtension() {
        return this.unsupportedCritExt;
    }

    public int hashCode() {
        return getUnparseableExtensions().hashCode() + this.map.hashCode();
    }

    @Override // android.sun.security.x509.CertAttrSet
    public void set(String str, Object obj) {
        if (!(obj instanceof Extension)) {
            throw new IOException("Unknown extension type.");
        }
        this.map.put(str, (Extension) obj);
    }

    @Override // android.sun.security.x509.CertAttrSet
    public String toString() {
        return this.map.toString();
    }

    public CertificateExtensions(DerInputStream derInputStream) {
        init(derInputStream);
    }

    public void encode(OutputStream outputStream, boolean z2) {
        DerOutputStream derOutputStream = new DerOutputStream();
        for (Object obj : this.map.values().toArray()) {
            if (obj instanceof CertAttrSet) {
                ((CertAttrSet) obj).encode(derOutputStream);
            } else {
                if (!(obj instanceof Extension)) {
                    throw new CertificateException("Illegal extension object");
                }
                ((Extension) obj).encode(derOutputStream);
            }
        }
        DerOutputStream derOutputStream2 = new DerOutputStream();
        derOutputStream2.write((byte) 48, derOutputStream);
        if (!z2) {
            DerOutputStream derOutputStream3 = new DerOutputStream();
            derOutputStream3.write(DerValue.createTag(DerValue.TAG_CONTEXT, true, (byte) 3), derOutputStream2);
            derOutputStream2 = derOutputStream3;
        }
        outputStream.write(derOutputStream2.toByteArray());
    }
}
