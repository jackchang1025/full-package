package org.bouncycastle.jsse.provider;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSessionContext;
import org.bouncycastle.tls.SessionID;
import org.bouncycastle.tls.TlsSession;
import org.bouncycastle.tls.TlsUtils;
import org.bouncycastle.tls.crypto.impl.jcajce.JcaTlsCrypto;

/* loaded from: classes.dex */
class ProvSSLSessionContext implements SSLSessionContext {
    private static final Logger LOG = Logger.getLogger(ProvSSLSessionContext.class.getName());
    private static final int provSessionCacheSize = PropertyUtils.getIntegerSystemProperty("javax.net.ssl.sessionCacheSize", 20480, 0, Integer.MAX_VALUE);
    protected final ContextData contextData;
    protected final Map<SessionID, SessionEntry> sessionsByID = new LinkedHashMap<SessionID, SessionEntry>(16, 0.75f, true) { // from class: org.bouncycastle.jsse.provider.ProvSSLSessionContext.1
        @Override // java.util.LinkedHashMap
        public boolean removeEldestEntry(Map.Entry<SessionID, SessionEntry> entry) {
            boolean z2 = ProvSSLSessionContext.this.sessionCacheSize > 0 && size() > ProvSSLSessionContext.this.sessionCacheSize;
            if (z2) {
                ProvSSLSessionContext.this.removeSessionByPeer(entry.getValue());
            }
            return z2;
        }
    };
    protected final Map<String, SessionEntry> sessionsByPeer = new HashMap();
    protected final ReferenceQueue<ProvSSLSession> sessionsQueue = new ReferenceQueue<>();
    protected int sessionCacheSize = provSessionCacheSize;
    protected int sessionTimeoutSeconds = 86400;

    public static final class SessionEntry extends SoftReference<ProvSSLSession> {
        private final String peerKey;
        private final SessionID sessionID;

        public SessionEntry(SessionID sessionID, ProvSSLSession provSSLSession, ReferenceQueue<ProvSSLSession> referenceQueue) {
            super(provSSLSession, referenceQueue);
            if (sessionID == null || provSSLSession == null || referenceQueue == null) {
                throw null;
            }
            this.sessionID = sessionID;
            this.peerKey = ProvSSLSessionContext.makePeerKey(provSSLSession);
        }

        public String getPeerKey() {
            return this.peerKey;
        }

        public SessionID getSessionID() {
            return this.sessionID;
        }
    }

    public ProvSSLSessionContext(ContextData contextData) {
        this.contextData = contextData;
    }

    private ProvSSLSession accessSession(SessionEntry sessionEntry) {
        if (sessionEntry == null) {
            return null;
        }
        ProvSSLSession provSSLSession = sessionEntry.get();
        if (provSSLSession != null) {
            long currentTimeMillis = System.currentTimeMillis();
            if (!invalidateIfCreatedBefore(sessionEntry, getCreationTimeLimit(currentTimeMillis))) {
                provSSLSession.accessedAt(currentTimeMillis);
                return provSSLSession;
            }
        }
        removeSession(sessionEntry);
        return null;
    }

    private long getCreationTimeLimit(long j2) {
        int i2 = this.sessionTimeoutSeconds;
        if (i2 < 1) {
            return Long.MIN_VALUE;
        }
        return j2 - (i2 * 1000);
    }

    private boolean invalidateIfCreatedBefore(SessionEntry sessionEntry, long j2) {
        ProvSSLSession provSSLSession = sessionEntry.get();
        if (provSSLSession == null) {
            return true;
        }
        if (provSSLSession.getCreationTime() < j2) {
            provSSLSession.invalidatedBySessionContext();
        }
        return !provSSLSession.isValid();
    }

    private static String makePeerKey(String str, int i2) {
        if (str == null || i2 < 0) {
            return null;
        }
        return (str + ':' + Integer.toString(i2)).toLowerCase(Locale.ENGLISH);
    }

    private static SessionID makeSessionID(byte[] bArr) {
        if (TlsUtils.isNullOrEmpty(bArr)) {
            return null;
        }
        return new SessionID(bArr);
    }

    private static <K, V> void mapAdd(Map<K, V> map, K k2, V v2) {
        if (map == null || v2 == null) {
            throw null;
        }
        if (k2 != null) {
            map.put(k2, v2);
        }
    }

    private static <K, V> V mapGet(Map<K, V> map, K k2) {
        map.getClass();
        if (k2 == null) {
            return null;
        }
        return map.get(k2);
    }

    private static <K, V> V mapRemove(Map<K, V> map, K k2) {
        map.getClass();
        if (k2 == null) {
            return null;
        }
        return map.remove(k2);
    }

    private void processQueue() {
        int i2 = 0;
        while (true) {
            SessionEntry sessionEntry = (SessionEntry) this.sessionsQueue.poll();
            if (sessionEntry == null) {
                break;
            }
            removeSession(sessionEntry);
            i2++;
        }
        if (i2 > 0) {
            LOG.fine("Processed " + i2 + " session entries (soft references) from the reference queue");
        }
    }

    private void removeAllExpiredSessions() {
        processQueue();
        long creationTimeLimit = getCreationTimeLimit(System.currentTimeMillis());
        Iterator<SessionEntry> it = this.sessionsByID.values().iterator();
        while (it.hasNext()) {
            SessionEntry next = it.next();
            if (invalidateIfCreatedBefore(next, creationTimeLimit)) {
                it.remove();
                removeSessionByPeer(next);
            }
        }
    }

    private void removeSession(SessionEntry sessionEntry) {
        mapRemove(this.sessionsByID, sessionEntry.getSessionID(), sessionEntry);
        removeSessionByPeer(sessionEntry);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean removeSessionByPeer(SessionEntry sessionEntry) {
        return mapRemove(this.sessionsByPeer, sessionEntry.getPeerKey(), sessionEntry);
    }

    public JcaTlsCrypto getCrypto() {
        return this.contextData.getCrypto();
    }

    @Override // javax.net.ssl.SSLSessionContext
    public synchronized Enumeration<byte[]> getIds() {
        ArrayList arrayList;
        removeAllExpiredSessions();
        arrayList = new ArrayList(this.sessionsByID.size());
        Iterator<SessionID> it = this.sessionsByID.keySet().iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getBytes());
        }
        return Collections.enumeration(arrayList);
    }

    public ProvSSLContextSpi getSSLContext() {
        return this.contextData.getContext();
    }

    @Override // javax.net.ssl.SSLSessionContext
    public SSLSession getSession(byte[] bArr) {
        if (bArr != null) {
            return getSessionImpl(bArr);
        }
        throw new NullPointerException("'sessionID' cannot be null");
    }

    @Override // javax.net.ssl.SSLSessionContext
    public synchronized int getSessionCacheSize() {
        return this.sessionCacheSize;
    }

    public synchronized ProvSSLSession getSessionImpl(String str, int i2) {
        ProvSSLSession accessSession;
        processQueue();
        SessionEntry sessionEntry = (SessionEntry) mapGet(this.sessionsByPeer, makePeerKey(str, i2));
        accessSession = accessSession(sessionEntry);
        if (accessSession != null) {
            this.sessionsByID.get(sessionEntry.getSessionID());
        }
        return accessSession;
    }

    @Override // javax.net.ssl.SSLSessionContext
    public synchronized int getSessionTimeout() {
        return this.sessionTimeoutSeconds;
    }

    public synchronized ProvSSLSession reportSession(String str, int i2, TlsSession tlsSession, JsseSessionParameters jsseSessionParameters, boolean z2) {
        processQueue();
        if (!z2) {
            return new ProvSSLSession(this, str, i2, tlsSession, jsseSessionParameters);
        }
        SessionID makeSessionID = makeSessionID(tlsSession.getSessionID());
        SessionEntry sessionEntry = (SessionEntry) mapGet(this.sessionsByID, makeSessionID);
        ProvSSLSession provSSLSession = sessionEntry == null ? null : sessionEntry.get();
        if (provSSLSession == null || provSSLSession.getTlsSession() != tlsSession) {
            ProvSSLSession provSSLSession2 = new ProvSSLSession(this, str, i2, tlsSession, jsseSessionParameters);
            if (makeSessionID != null) {
                sessionEntry = new SessionEntry(makeSessionID, provSSLSession2, this.sessionsQueue);
                this.sessionsByID.put(makeSessionID, sessionEntry);
            }
            provSSLSession = provSSLSession2;
        }
        if (sessionEntry != null) {
            mapAdd(this.sessionsByPeer, sessionEntry.getPeerKey(), sessionEntry);
        }
        return provSSLSession;
    }

    @Override // javax.net.ssl.SSLSessionContext
    public synchronized void setSessionCacheSize(int i2) {
        int size;
        if (this.sessionCacheSize == i2) {
            return;
        }
        if (i2 < 0) {
            throw new IllegalArgumentException("'size' cannot be < 0");
        }
        this.sessionCacheSize = i2;
        removeAllExpiredSessions();
        if (this.sessionCacheSize > 0 && (size = this.sessionsByID.size()) > this.sessionCacheSize) {
            Iterator<SessionEntry> it = this.sessionsByID.values().iterator();
            for (size = this.sessionsByID.size(); it.hasNext() && size > this.sessionCacheSize; size--) {
                SessionEntry next = it.next();
                it.remove();
                removeSessionByPeer(next);
            }
        }
    }

    @Override // javax.net.ssl.SSLSessionContext
    public synchronized void setSessionTimeout(int i2) {
        if (this.sessionTimeoutSeconds == i2) {
            return;
        }
        if (i2 < 0) {
            throw new IllegalArgumentException("'seconds' cannot be < 0");
        }
        this.sessionTimeoutSeconds = i2;
        removeAllExpiredSessions();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String makePeerKey(ProvSSLSession provSSLSession) {
        if (provSSLSession == null) {
            return null;
        }
        return makePeerKey(provSSLSession.getPeerHost(), provSSLSession.getPeerPort());
    }

    private static <K, V> boolean mapRemove(Map<K, V> map, K k2, V v2) {
        if (map == null || v2 == null) {
            throw null;
        }
        if (k2 == null) {
            return false;
        }
        V remove = map.remove(k2);
        if (remove == v2) {
            return true;
        }
        if (remove == null) {
            return false;
        }
        map.put(k2, remove);
        return false;
    }

    public synchronized ProvSSLSession getSessionImpl(byte[] bArr) {
        processQueue();
        return accessSession((SessionEntry) mapGet(this.sessionsByID, makeSessionID(bArr)));
    }

    public synchronized void removeSession(byte[] bArr) {
        SessionEntry sessionEntry = (SessionEntry) mapRemove(this.sessionsByID, makeSessionID(bArr));
        if (sessionEntry != null) {
            removeSessionByPeer(sessionEntry);
        }
    }
}
