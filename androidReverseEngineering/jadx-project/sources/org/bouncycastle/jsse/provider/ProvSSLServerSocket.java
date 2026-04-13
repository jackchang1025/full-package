package org.bouncycastle.jsse.provider;

import java.net.InetAddress;
import java.net.Socket;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLServerSocket;

/* loaded from: classes.dex */
class ProvSSLServerSocket extends SSLServerSocket {
    protected final ContextData contextData;
    protected boolean enableSessionCreation;
    protected final ProvSSLParameters sslParameters;
    protected boolean useClientMode;

    public ProvSSLServerSocket(ContextData contextData) {
        this.enableSessionCreation = true;
        this.useClientMode = false;
        this.contextData = contextData;
        this.sslParameters = contextData.getContext().getDefaultSSLParameters(this.useClientMode);
    }

    @Override // java.net.ServerSocket
    public synchronized Socket accept() {
        ProvSSLSocketDirect create;
        create = SSLSocketUtil.create(this.contextData, this.enableSessionCreation, this.useClientMode, this.sslParameters.copy());
        implAccept(create);
        create.notifyConnected();
        return create;
    }

    @Override // javax.net.ssl.SSLServerSocket
    public synchronized boolean getEnableSessionCreation() {
        return this.enableSessionCreation;
    }

    @Override // javax.net.ssl.SSLServerSocket
    public synchronized String[] getEnabledCipherSuites() {
        return this.sslParameters.getCipherSuites();
    }

    @Override // javax.net.ssl.SSLServerSocket
    public synchronized String[] getEnabledProtocols() {
        return this.sslParameters.getProtocols();
    }

    @Override // javax.net.ssl.SSLServerSocket
    public synchronized boolean getNeedClientAuth() {
        return this.sslParameters.getNeedClientAuth();
    }

    @Override // javax.net.ssl.SSLServerSocket
    public synchronized SSLParameters getSSLParameters() {
        return SSLParametersUtil.getSSLParameters(this.sslParameters);
    }

    @Override // javax.net.ssl.SSLServerSocket
    public synchronized String[] getSupportedCipherSuites() {
        return this.contextData.getContext().getSupportedCipherSuites();
    }

    @Override // javax.net.ssl.SSLServerSocket
    public synchronized String[] getSupportedProtocols() {
        return this.contextData.getContext().getSupportedProtocols();
    }

    @Override // javax.net.ssl.SSLServerSocket
    public synchronized boolean getUseClientMode() {
        return this.useClientMode;
    }

    @Override // javax.net.ssl.SSLServerSocket
    public synchronized boolean getWantClientAuth() {
        return this.sslParameters.getWantClientAuth();
    }

    @Override // javax.net.ssl.SSLServerSocket
    public synchronized void setEnableSessionCreation(boolean z2) {
        this.enableSessionCreation = z2;
    }

    @Override // javax.net.ssl.SSLServerSocket
    public synchronized void setEnabledCipherSuites(String[] strArr) {
        this.sslParameters.setCipherSuites(strArr);
    }

    @Override // javax.net.ssl.SSLServerSocket
    public synchronized void setEnabledProtocols(String[] strArr) {
        this.sslParameters.setProtocols(strArr);
    }

    @Override // javax.net.ssl.SSLServerSocket
    public synchronized void setNeedClientAuth(boolean z2) {
        this.sslParameters.setNeedClientAuth(z2);
    }

    @Override // javax.net.ssl.SSLServerSocket
    public synchronized void setSSLParameters(SSLParameters sSLParameters) {
        SSLParametersUtil.setSSLParameters(this.sslParameters, sSLParameters);
    }

    @Override // javax.net.ssl.SSLServerSocket
    public synchronized void setUseClientMode(boolean z2) {
        if (this.useClientMode != z2) {
            this.contextData.getContext().updateDefaultSSLParameters(this.sslParameters, z2);
            this.useClientMode = z2;
        }
    }

    @Override // javax.net.ssl.SSLServerSocket
    public synchronized void setWantClientAuth(boolean z2) {
        this.sslParameters.setWantClientAuth(z2);
    }

    public ProvSSLServerSocket(ContextData contextData, int i2) {
        super(i2);
        this.enableSessionCreation = true;
        this.useClientMode = false;
        this.contextData = contextData;
        this.sslParameters = contextData.getContext().getDefaultSSLParameters(this.useClientMode);
    }

    public ProvSSLServerSocket(ContextData contextData, int i2, int i3) {
        super(i2, i3);
        this.enableSessionCreation = true;
        this.useClientMode = false;
        this.contextData = contextData;
        this.sslParameters = contextData.getContext().getDefaultSSLParameters(this.useClientMode);
    }

    public ProvSSLServerSocket(ContextData contextData, int i2, int i3, InetAddress inetAddress) {
        super(i2, i3, inetAddress);
        this.enableSessionCreation = true;
        this.useClientMode = false;
        this.contextData = contextData;
        this.sslParameters = contextData.getContext().getDefaultSSLParameters(this.useClientMode);
    }
}
