package f0;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.AbstractSelectableChannel;

/* loaded from: classes.dex */
public final class a0 extends AbstractC0293n {

    /* renamed from: b */
    public final /* synthetic */ int f487b;

    /* renamed from: c */
    public final AbstractSelectableChannel f488c;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ a0(AbstractSelectableChannel abstractSelectableChannel, int i2) {
        super(abstractSelectableChannel);
        this.f487b = i2;
        this.f488c = abstractSelectableChannel;
    }

    @Override // java.nio.channels.ReadableByteChannel
    public final int read(ByteBuffer byteBuffer) {
        switch (this.f487b) {
            case 0:
                throw new IOException("Can't read ServerSocketChannel");
            default:
                return ((SocketChannel) this.f488c).read(byteBuffer);
        }
    }

    @Override // java.nio.channels.ScatteringByteChannel
    public final long read(ByteBuffer[] byteBufferArr) {
        switch (this.f487b) {
            case 0:
                throw new IOException("Can't read ServerSocketChannel");
            default:
                return ((SocketChannel) this.f488c).read(byteBufferArr);
        }
    }

    @Override // java.nio.channels.ScatteringByteChannel
    public final long read(ByteBuffer[] byteBufferArr, int i2, int i3) {
        switch (this.f487b) {
            case 0:
                throw new IOException("Can't read ServerSocketChannel");
            default:
                return ((SocketChannel) this.f488c).read(byteBufferArr, i2, i3);
        }
    }
}
