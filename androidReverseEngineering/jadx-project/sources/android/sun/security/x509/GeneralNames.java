package android.sun.security.x509;

import android.sun.security.util.DerOutputStream;
import android.sun.security.util.DerValue;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class GeneralNames {
    private final List<GeneralName> names;

    public GeneralNames() {
        this.names = new ArrayList();
    }

    public GeneralNames add(GeneralName generalName) {
        generalName.getClass();
        this.names.add(generalName);
        return this;
    }

    public void encode(DerOutputStream derOutputStream) {
        if (isEmpty()) {
            return;
        }
        DerOutputStream derOutputStream2 = new DerOutputStream();
        Iterator<GeneralName> it = this.names.iterator();
        while (it.hasNext()) {
            it.next().encode(derOutputStream2);
        }
        derOutputStream.write((byte) 48, derOutputStream2);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof GeneralNames) {
            return this.names.equals(((GeneralNames) obj).names);
        }
        return false;
    }

    public GeneralName get(int i2) {
        return this.names.get(i2);
    }

    public int hashCode() {
        return this.names.hashCode();
    }

    public boolean isEmpty() {
        return this.names.isEmpty();
    }

    public Iterator<GeneralName> iterator() {
        return this.names.iterator();
    }

    public List<GeneralName> names() {
        return this.names;
    }

    public int size() {
        return this.names.size();
    }

    public String toString() {
        return this.names.toString();
    }

    public GeneralNames(DerValue derValue) {
        this();
        if (derValue.tag != 48) {
            throw new IOException("Invalid encoding for GeneralNames.");
        }
        if (derValue.data.available() == 0) {
            throw new IOException("No data available in passed DER encoded value.");
        }
        while (derValue.data.available() != 0) {
            add(new GeneralName(derValue.data.getDerValue()));
        }
    }
}
