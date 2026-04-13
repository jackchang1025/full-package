package org.bouncycastle.tls;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/* loaded from: classes.dex */
public class UDPTransport implements DatagramTransport {
    protected static final int MAX_IP_OVERHEAD = 84;
    protected static final int MIN_IP_OVERHEAD = 20;
    protected static final int UDP_OVERHEAD = 8;
    protected final int receiveLimit;
    protected final int sendLimit;
    protected final DatagramSocket socket;

    public UDPTransport(DatagramSocket datagramSocket, int i2) {
        if (!datagramSocket.isBound() || !datagramSocket.isConnected()) {
            throw new IllegalArgumentException("'socket' must be bound and connected");
        }
        this.socket = datagramSocket;
        this.receiveLimit = (i2 - 20) - 8;
        this.sendLimit = (i2 - 84) - 8;
    }

    @Override // org.bouncycastle.tls.TlsCloseable
    public void close() {
        this.socket.close();
    }

    @Override // org.bouncycastle.tls.DatagramReceiver
    public int getReceiveLimit() {
        return this.receiveLimit;
    }

    @Override // org.bouncycastle.tls.DatagramSender
    public int getSendLimit() {
        return this.sendLimit;
    }

    @Override // org.bouncycastle.tls.DatagramReceiver
    public int receive(byte[] bArr, int i2, int i3, int i4) {
        this.socket.setSoTimeout(i4);
        DatagramPacket datagramPacket = new DatagramPacket(bArr, i2, i3);
        this.socket.receive(datagramPacket);
        return datagramPacket.getLength();
    }

    @Override // org.bouncycastle.tls.DatagramSender
    public void send(byte[] bArr, int i2, int i3) {
        if (i3 > getSendLimit()) {
            throw new TlsFatalAlert((short) 80);
        }
        this.socket.send(new DatagramPacket(bArr, i2, i3));
    }
}
