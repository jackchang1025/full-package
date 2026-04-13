package org.bouncycastle.cert.dane;

/* loaded from: classes.dex */
public class DANEEntryStoreBuilder {
    private final DANEEntryFetcherFactory daneEntryFetcher;

    public DANEEntryStoreBuilder(DANEEntryFetcherFactory dANEEntryFetcherFactory) {
        this.daneEntryFetcher = dANEEntryFetcherFactory;
    }

    public DANEEntryStore build(String str) {
        return new DANEEntryStore(this.daneEntryFetcher.build(str).getEntries());
    }
}
