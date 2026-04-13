package f0;

import java.nio.channels.ReadableByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.channels.spi.AbstractSelectableChannel;

/* renamed from: f0.n */
/* loaded from: classes.dex */
public abstract class AbstractC0293n implements ReadableByteChannel, ScatteringByteChannel {

    /* renamed from: a */
    public final AbstractSelectableChannel f542a;

    public AbstractC0293n(AbstractSelectableChannel abstractSelectableChannel) {
        abstractSelectableChannel.configureBlocking(false);
        this.f542a = abstractSelectableChannel;
    }

    @Override // java.nio.channels.Channel, java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        this.f542a.close();
    }

    @Override // java.nio.channels.Channel
    public final boolean isOpen() {
        return this.f542a.isOpen();
    }
}
