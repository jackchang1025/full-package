package f0;

import java.io.Closeable;
import java.nio.channels.Selector;
import java.nio.channels.spi.AbstractSelector;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

/* renamed from: f0.z */
/* loaded from: classes.dex */
public final class C0305z implements Closeable {

    /* renamed from: a */
    public final Selector f563a;

    /* renamed from: b */
    public final AtomicBoolean f564b = new AtomicBoolean(false);

    /* renamed from: c */
    public final Semaphore f565c = new Semaphore(0);

    public C0305z(AbstractSelector abstractSelector) {
        this.f563a = abstractSelector;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        this.f563a.close();
    }
}
