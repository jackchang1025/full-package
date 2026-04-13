package org.bouncycastle.jsse.provider;

import java.security.cert.CertPathBuilder;
import java.security.cert.PKIXBuilderParameters;
import java.security.cert.PKIXCertPathChecker;
import java.security.cert.PKIXRevocationChecker;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import javax.net.ssl.SNIHostName;
import javax.net.ssl.SNIMatcher;
import javax.net.ssl.SNIServerName;
import org.bouncycastle.jsse.BCApplicationProtocolSelector;
import org.bouncycastle.jsse.BCSNIHostName;
import org.bouncycastle.jsse.BCSNIMatcher;
import org.bouncycastle.jsse.BCSNIServerName;
import org.bouncycastle.jsse.provider.JsseUtils;

/* loaded from: classes.dex */
abstract class JsseUtils_8 extends JsseUtils_7 {

    public static class ExportAPSelector<T> implements BiFunction<T, List<String>, String> {
        private final BCApplicationProtocolSelector<T> selector;

        public ExportAPSelector(BCApplicationProtocolSelector<T> bCApplicationProtocolSelector) {
            this.selector = bCApplicationProtocolSelector;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.function.BiFunction
        public /* bridge */ /* synthetic */ String apply(Object obj, List<String> list) {
            return apply2((ExportAPSelector<T>) obj, list);
        }

        public BCApplicationProtocolSelector<T> unwrap() {
            return this.selector;
        }

        /* renamed from: apply, reason: avoid collision after fix types in other method */
        public String apply2(T t2, List<String> list) {
            return this.selector.select(t2, list);
        }
    }

    public static class ExportSNIMatcher extends SNIMatcher {
        private final BCSNIMatcher matcher;

        public ExportSNIMatcher(BCSNIMatcher bCSNIMatcher) {
            super(bCSNIMatcher.getType());
            this.matcher = bCSNIMatcher;
        }

        @Override // javax.net.ssl.SNIMatcher
        public boolean matches(SNIServerName sNIServerName) {
            return this.matcher.matches(JsseUtils_8.importSNIServerName(sNIServerName));
        }

        public BCSNIMatcher unwrap() {
            return this.matcher;
        }
    }

    public static class ImportAPSelector<T> implements BCApplicationProtocolSelector<T> {
        private final BiFunction<T, List<String>, String> selector;

        public ImportAPSelector(BiFunction<T, List<String>, String> biFunction) {
            this.selector = biFunction;
        }

        @Override // org.bouncycastle.jsse.BCApplicationProtocolSelector
        public String select(T t2, List<String> list) {
            return this.selector.apply(t2, list);
        }

        public BiFunction<T, List<String>, String> unwrap() {
            return this.selector;
        }
    }

    public static class ImportSNIMatcher extends BCSNIMatcher {
        private final SNIMatcher matcher;

        public ImportSNIMatcher(SNIMatcher sNIMatcher) {
            super(sNIMatcher.getType());
            this.matcher = sNIMatcher;
        }

        @Override // org.bouncycastle.jsse.BCSNIMatcher
        public boolean matches(BCSNIServerName bCSNIServerName) {
            return this.matcher.matches(JsseUtils_8.exportSNIServerName(bCSNIServerName));
        }

        public SNIMatcher unwrap() {
            return this.matcher;
        }
    }

    public static class UnknownServerName extends SNIServerName {
        public UnknownServerName(int i2, byte[] bArr) {
            super(i2, bArr);
        }
    }

    public static void addStatusResponses(CertPathBuilder certPathBuilder, PKIXBuilderParameters pKIXBuilderParameters, Map<X509Certificate, byte[]> map) {
        if (map.isEmpty()) {
            return;
        }
        List<PKIXCertPathChecker> certPathCheckers = pKIXBuilderParameters.getCertPathCheckers();
        PKIXRevocationChecker firstRevocationChecker = getFirstRevocationChecker(certPathCheckers);
        if (firstRevocationChecker != null) {
            Map<X509Certificate, byte[]> ocspResponses = firstRevocationChecker.getOcspResponses();
            if (putAnyAbsent(ocspResponses, map) > 0) {
                firstRevocationChecker.setOcspResponses(ocspResponses);
                pKIXBuilderParameters.setCertPathCheckers(certPathCheckers);
                return;
            }
            return;
        }
        if (pKIXBuilderParameters.isRevocationEnabled()) {
            PKIXRevocationChecker pKIXRevocationChecker = (PKIXRevocationChecker) certPathBuilder.getRevocationChecker();
            pKIXRevocationChecker.setOcspResponses(map);
            pKIXBuilderParameters.addCertPathChecker(pKIXRevocationChecker);
        }
    }

    public static <T> BiFunction<T, List<String>, String> exportAPSelector(BCApplicationProtocolSelector<T> bCApplicationProtocolSelector) {
        if (bCApplicationProtocolSelector == null) {
            return null;
        }
        return bCApplicationProtocolSelector instanceof ImportAPSelector ? ((ImportAPSelector) bCApplicationProtocolSelector).unwrap() : new ExportAPSelector(bCApplicationProtocolSelector);
    }

    public static SNIMatcher exportSNIMatcher(BCSNIMatcher bCSNIMatcher) {
        if (bCSNIMatcher == null) {
            return null;
        }
        return bCSNIMatcher instanceof ImportSNIMatcher ? ((ImportSNIMatcher) bCSNIMatcher).unwrap() : new ExportSNIMatcher(bCSNIMatcher);
    }

    public static List<SNIMatcher> exportSNIMatchers(Collection<BCSNIMatcher> collection) {
        if (collection == null || collection.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList(collection.size());
        Iterator<BCSNIMatcher> it = collection.iterator();
        while (it.hasNext()) {
            arrayList.add(exportSNIMatcher(it.next()));
        }
        return Collections.unmodifiableList(arrayList);
    }

    public static Object exportSNIMatchersDynamic(Collection<BCSNIMatcher> collection) {
        return exportSNIMatchers(collection);
    }

    public static SNIServerName exportSNIServerName(BCSNIServerName bCSNIServerName) {
        if (bCSNIServerName == null) {
            return null;
        }
        int type = bCSNIServerName.getType();
        byte[] encoded = bCSNIServerName.getEncoded();
        return type != 0 ? new UnknownServerName(type, encoded) : new SNIHostName(encoded);
    }

    public static List<SNIServerName> exportSNIServerNames(Collection<BCSNIServerName> collection) {
        if (collection == null || collection.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList(collection.size());
        Iterator<BCSNIServerName> it = collection.iterator();
        while (it.hasNext()) {
            arrayList.add(exportSNIServerName(it.next()));
        }
        return Collections.unmodifiableList(arrayList);
    }

    public static Object exportSNIServerNamesDynamic(Collection<BCSNIServerName> collection) {
        return exportSNIServerNames(collection);
    }

    public static PKIXRevocationChecker getFirstRevocationChecker(List<PKIXCertPathChecker> list) {
        for (PKIXCertPathChecker pKIXCertPathChecker : list) {
            if (pKIXCertPathChecker instanceof PKIXRevocationChecker) {
                return (PKIXRevocationChecker) pKIXCertPathChecker;
            }
        }
        return null;
    }

    public static <T> BCApplicationProtocolSelector<T> importAPSelector(BiFunction<T, List<String>, String> biFunction) {
        if (biFunction == null) {
            return null;
        }
        return biFunction instanceof ExportAPSelector ? ((ExportAPSelector) biFunction).unwrap() : new ImportAPSelector(biFunction);
    }

    public static BCSNIMatcher importSNIMatcher(SNIMatcher sNIMatcher) {
        if (sNIMatcher == null) {
            return null;
        }
        return sNIMatcher instanceof ExportSNIMatcher ? ((ExportSNIMatcher) sNIMatcher).unwrap() : new ImportSNIMatcher(sNIMatcher);
    }

    public static List<BCSNIMatcher> importSNIMatchers(Collection<SNIMatcher> collection) {
        if (collection == null || collection.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList(collection.size());
        Iterator<SNIMatcher> it = collection.iterator();
        while (it.hasNext()) {
            arrayList.add(importSNIMatcher(it.next()));
        }
        return Collections.unmodifiableList(arrayList);
    }

    public static List<BCSNIMatcher> importSNIMatchersDynamic(Object obj) {
        return importSNIMatchers((Collection) obj);
    }

    public static BCSNIServerName importSNIServerName(SNIServerName sNIServerName) {
        if (sNIServerName == null) {
            return null;
        }
        int type = sNIServerName.getType();
        byte[] encoded = sNIServerName.getEncoded();
        return type != 0 ? new JsseUtils.BCUnknownServerName(type, encoded) : new BCSNIHostName(encoded);
    }

    public static List<BCSNIServerName> importSNIServerNames(Collection<SNIServerName> collection) {
        if (collection == null || collection.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList(collection.size());
        Iterator<SNIServerName> it = collection.iterator();
        while (it.hasNext()) {
            arrayList.add(importSNIServerName(it.next()));
        }
        return Collections.unmodifiableList(arrayList);
    }

    public static List<BCSNIServerName> importSNIServerNamesDynamic(Object obj) {
        return importSNIServerNames((Collection) obj);
    }

    public static <K, V> int putAnyAbsent(Map<K, V> map, Map<K, V> map2) {
        int i2 = 0;
        for (Map.Entry<K, V> entry : map2.entrySet()) {
            if (map.putIfAbsent(entry.getKey(), entry.getValue()) == null) {
                i2++;
            }
        }
        return i2;
    }
}
