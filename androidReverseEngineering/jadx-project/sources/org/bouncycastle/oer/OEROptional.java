package org.bouncycastle.oer;

import java.security.AccessController;
import java.security.PrivilegedAction;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class OEROptional extends ASN1Object {
    public static final OEROptional ABSENT = new OEROptional(false, null);
    private final boolean defined;
    private final ASN1Encodable value;

    private OEROptional(boolean z2, ASN1Encodable aSN1Encodable) {
        this.defined = z2;
        this.value = aSN1Encodable;
    }

    public static OEROptional getInstance(Object obj) {
        return obj instanceof OEROptional ? (OEROptional) obj : obj instanceof ASN1Encodable ? new OEROptional(true, (ASN1Encodable) obj) : ABSENT;
    }

    public static <T> T getValue(Class<T> cls, Object obj) {
        OEROptional oEROptional = getInstance(obj);
        if (oEROptional.defined) {
            return (T) oEROptional.getObject(cls);
        }
        return null;
    }

    @Override // org.bouncycastle.asn1.ASN1Object
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass() || !super.equals(obj)) {
            return false;
        }
        OEROptional oEROptional = (OEROptional) obj;
        if (this.defined != oEROptional.defined) {
            return false;
        }
        ASN1Encodable aSN1Encodable = this.value;
        ASN1Encodable aSN1Encodable2 = oEROptional.value;
        return aSN1Encodable != null ? aSN1Encodable.equals(aSN1Encodable2) : aSN1Encodable2 == null;
    }

    public ASN1Encodable get() {
        return !this.defined ? ABSENT : this.value;
    }

    public <T> T getObject(final Class<T> cls) {
        if (this.defined) {
            return this.value.getClass().isInstance(cls) ? cls.cast(this.value) : (T) AccessController.doPrivileged(new PrivilegedAction<T>() { // from class: org.bouncycastle.oer.OEROptional.1
                @Override // java.security.PrivilegedAction
                public T run() {
                    try {
                        return (T) cls.cast(cls.getMethod("getInstance", Object.class).invoke(null, OEROptional.this.value));
                    } catch (Exception e2) {
                        throw new IllegalStateException(AbstractC0000a.m9e(e2, new StringBuilder("could not invoke getInstance on type ")), e2);
                    }
                }
            });
        }
        return null;
    }

    @Override // org.bouncycastle.asn1.ASN1Object
    public int hashCode() {
        int hashCode = ((super.hashCode() * 31) + (this.defined ? 1 : 0)) * 31;
        ASN1Encodable aSN1Encodable = this.value;
        return hashCode + (aSN1Encodable != null ? aSN1Encodable.hashCode() : 0);
    }

    public boolean isDefined() {
        return this.defined;
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        if (this.defined) {
            return get().toASN1Primitive();
        }
        throw new RuntimeException("bang");
    }

    public String toString() {
        if (!this.defined) {
            return "ABSENT";
        }
        return "OPTIONAL(" + this.value + ")";
    }
}
