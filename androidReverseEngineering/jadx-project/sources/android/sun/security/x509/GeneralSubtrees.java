package android.sun.security.x509;

import android.sun.security.util.DerOutputStream;
import android.sun.security.util.DerValue;
import android.sun.security.util.ObjectIdentifier;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.guard.wallet.entity.BuildConfig;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class GeneralSubtrees implements Cloneable {
    private static final int NAME_DIFF_TYPE = -1;
    private static final int NAME_MATCH = 0;
    private static final int NAME_NARROWS = 1;
    private static final int NAME_SAME_TYPE = 3;
    private static final int NAME_WIDENS = 2;
    private final List<GeneralSubtree> trees;

    public GeneralSubtrees() {
        this.trees = new ArrayList();
    }

    private GeneralSubtree createWidestSubtree(GeneralNameInterface generalNameInterface) {
        GeneralName generalName;
        try {
            switch (generalNameInterface.getType()) {
                case 0:
                    generalName = new GeneralName(new OtherName(((OtherName) generalNameInterface).getOID(), null));
                    break;
                case 1:
                    generalName = new GeneralName(new RFC822Name(BuildConfig.FLAVOR));
                    break;
                case 2:
                    generalName = new GeneralName(new DNSName(BuildConfig.FLAVOR));
                    break;
                case 3:
                    generalName = new GeneralName(new X400Address((byte[]) null));
                    break;
                case 4:
                    generalName = new GeneralName(new X500Name(BuildConfig.FLAVOR));
                    break;
                case 5:
                    generalName = new GeneralName(new EDIPartyName(BuildConfig.FLAVOR));
                    break;
                case 6:
                    generalName = new GeneralName(new URIName(BuildConfig.FLAVOR));
                    break;
                case 7:
                    generalName = new GeneralName(new IPAddressName((byte[]) null));
                    break;
                case 8:
                    generalName = new GeneralName(new OIDName(new ObjectIdentifier((int[]) null)));
                    break;
                default:
                    throw new IOException("Unsupported GeneralNameInterface type: " + generalNameInterface.getType());
            }
            return new GeneralSubtree(generalName, 0, -1);
        } catch (IOException e2) {
            throw new RuntimeException(AbstractC0000a.m13i("Unexpected error: ", e2), e2);
        }
    }

    private GeneralNameInterface getGeneralNameInterface(int i2) {
        return getGeneralNameInterface(get(i2));
    }

    private void minimize() {
        boolean z2;
        int i2 = 0;
        while (i2 < size()) {
            GeneralNameInterface generalNameInterface = getGeneralNameInterface(i2);
            int i3 = i2 + 1;
            while (i3 < size()) {
                int constrains = generalNameInterface.constrains(getGeneralNameInterface(i3));
                if (constrains != -1) {
                    if (constrains != 0) {
                        if (constrains == 1) {
                            remove(i3);
                            i3--;
                        } else if (constrains != 2) {
                            if (constrains != 3) {
                                break;
                            }
                        }
                    }
                    z2 = true;
                    break;
                }
                i3++;
            }
            z2 = false;
            if (z2) {
                remove(i2);
                i2--;
            }
            i2++;
        }
    }

    public void add(GeneralSubtree generalSubtree) {
        generalSubtree.getClass();
        this.trees.add(generalSubtree);
    }

    public Object clone() {
        return new GeneralSubtrees(this);
    }

    public boolean contains(GeneralSubtree generalSubtree) {
        generalSubtree.getClass();
        return this.trees.contains(generalSubtree);
    }

    public void encode(DerOutputStream derOutputStream) {
        DerOutputStream derOutputStream2 = new DerOutputStream();
        int size = size();
        for (int i2 = 0; i2 < size; i2++) {
            get(i2).encode(derOutputStream2);
        }
        derOutputStream.write((byte) 48, derOutputStream2);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof GeneralSubtrees) {
            return this.trees.equals(((GeneralSubtrees) obj).trees);
        }
        return false;
    }

    public GeneralSubtree get(int i2) {
        return this.trees.get(i2);
    }

    public int hashCode() {
        return this.trees.hashCode();
    }

    public GeneralSubtrees intersect(GeneralSubtrees generalSubtrees) {
        if (generalSubtrees == null) {
            throw new NullPointerException("other GeneralSubtrees must not be null");
        }
        GeneralSubtrees generalSubtrees2 = new GeneralSubtrees();
        GeneralSubtrees generalSubtrees3 = null;
        if (size() == 0) {
            union(generalSubtrees);
            return null;
        }
        minimize();
        generalSubtrees.minimize();
        int i2 = 0;
        while (i2 < size()) {
            GeneralNameInterface generalNameInterface = getGeneralNameInterface(i2);
            boolean z2 = false;
            for (int i3 = 0; i3 < generalSubtrees.size(); i3++) {
                GeneralSubtree generalSubtree = generalSubtrees.get(i3);
                int constrains = generalNameInterface.constrains(getGeneralNameInterface(generalSubtree));
                if (constrains != 0) {
                    if (constrains == 1) {
                        remove(i2);
                        i2--;
                        generalSubtrees2.add(generalSubtree);
                    } else if (constrains != 2) {
                        if (constrains == 3) {
                            z2 = true;
                        }
                    }
                }
                z2 = false;
            }
            if (z2) {
                boolean z3 = false;
                for (int i4 = 0; i4 < size(); i4++) {
                    GeneralNameInterface generalNameInterface2 = getGeneralNameInterface(i4);
                    if (generalNameInterface2.getType() == generalNameInterface.getType()) {
                        for (int i5 = 0; i5 < generalSubtrees.size(); i5++) {
                            int constrains2 = generalNameInterface2.constrains(generalSubtrees.getGeneralNameInterface(i5));
                            if (constrains2 == 0 || constrains2 == 2 || constrains2 == 1) {
                                z3 = true;
                                break;
                            }
                        }
                    }
                }
                if (!z3) {
                    if (generalSubtrees3 == null) {
                        generalSubtrees3 = new GeneralSubtrees();
                    }
                    GeneralSubtree createWidestSubtree = createWidestSubtree(generalNameInterface);
                    if (!generalSubtrees3.contains(createWidestSubtree)) {
                        generalSubtrees3.add(createWidestSubtree);
                    }
                }
                remove(i2);
                i2--;
            }
            i2++;
        }
        if (generalSubtrees2.size() > 0) {
            union(generalSubtrees2);
        }
        for (int i6 = 0; i6 < generalSubtrees.size(); i6++) {
            GeneralSubtree generalSubtree2 = generalSubtrees.get(i6);
            GeneralNameInterface generalNameInterface3 = getGeneralNameInterface(generalSubtree2);
            boolean z4 = false;
            for (int i7 = 0; i7 < size(); i7++) {
                int constrains3 = getGeneralNameInterface(i7).constrains(generalNameInterface3);
                if (constrains3 != -1) {
                    if (constrains3 == 0 || constrains3 == 1 || constrains3 == 2 || constrains3 == 3) {
                        z4 = false;
                        break;
                    }
                } else {
                    z4 = true;
                }
            }
            if (z4) {
                add(generalSubtree2);
            }
        }
        return generalSubtrees3;
    }

    public Iterator<GeneralSubtree> iterator() {
        return this.trees.iterator();
    }

    public void reduce(GeneralSubtrees generalSubtrees) {
        if (generalSubtrees == null) {
            return;
        }
        int size = generalSubtrees.size();
        for (int i2 = 0; i2 < size; i2++) {
            GeneralNameInterface generalNameInterface = generalSubtrees.getGeneralNameInterface(i2);
            int i3 = 0;
            while (i3 < size()) {
                int constrains = generalNameInterface.constrains(getGeneralNameInterface(i3));
                if (constrains == 0 || constrains == 1) {
                    remove(i3);
                    i3--;
                }
                i3++;
            }
        }
    }

    public void remove(int i2) {
        this.trees.remove(i2);
    }

    public int size() {
        return this.trees.size();
    }

    public String toString() {
        return "   GeneralSubtrees:\n" + this.trees.toString() + "\n";
    }

    public List<GeneralSubtree> trees() {
        return this.trees;
    }

    public void union(GeneralSubtrees generalSubtrees) {
        if (generalSubtrees != null) {
            int size = generalSubtrees.size();
            for (int i2 = 0; i2 < size; i2++) {
                add(generalSubtrees.get(i2));
            }
            minimize();
        }
    }

    public GeneralSubtrees(DerValue derValue) {
        this();
        if (derValue.tag != 48) {
            throw new IOException("Invalid encoding of GeneralSubtrees.");
        }
        while (derValue.data.available() != 0) {
            add(new GeneralSubtree(derValue.data.getDerValue()));
        }
    }

    private static GeneralNameInterface getGeneralNameInterface(GeneralSubtree generalSubtree) {
        return generalSubtree.getName().getName();
    }

    private GeneralSubtrees(GeneralSubtrees generalSubtrees) {
        this.trees = new ArrayList(generalSubtrees.trees);
    }
}
