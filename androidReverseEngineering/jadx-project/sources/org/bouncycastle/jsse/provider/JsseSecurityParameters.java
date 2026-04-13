package org.bouncycastle.jsse.provider;

import java.security.Principal;
import java.util.List;
import org.bouncycastle.jsse.provider.NamedGroupInfo;

/* loaded from: classes.dex */
class JsseSecurityParameters {
    List<SignatureSchemeInfo> localSigSchemes;
    List<SignatureSchemeInfo> localSigSchemesCert;
    NamedGroupInfo.PerConnection namedGroups;
    List<SignatureSchemeInfo> peerSigSchemes;
    List<SignatureSchemeInfo> peerSigSchemesCert;
    List<byte[]> statusResponses;
    Principal[] trustedIssuers;

    public void clear() {
        this.namedGroups = null;
        this.localSigSchemes = null;
        this.localSigSchemesCert = null;
        this.peerSigSchemes = null;
        this.peerSigSchemesCert = null;
        this.statusResponses = null;
        this.trustedIssuers = null;
    }
}
