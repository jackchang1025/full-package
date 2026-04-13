package android.sun.security.x509;

import java.util.Comparator;

/* loaded from: classes.dex */
class AVAComparator implements Comparator<AVA> {
    private static final Comparator<AVA> INSTANCE = new AVAComparator();

    private AVAComparator() {
    }

    public static Comparator<AVA> getInstance() {
        return INSTANCE;
    }

    @Override // java.util.Comparator
    public int compare(AVA ava, AVA ava2) {
        boolean hasRFC2253Keyword = ava.hasRFC2253Keyword();
        return hasRFC2253Keyword == ava2.hasRFC2253Keyword() ? ava.toRFC2253CanonicalString().compareTo(ava2.toRFC2253CanonicalString()) : hasRFC2253Keyword ? -1 : 1;
    }
}
