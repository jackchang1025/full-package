package p021y;

import a1.AbstractC0026q;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import com.guard.wallet.thread.CallableC0232a;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* renamed from: y.e */
/* loaded from: classes.dex */
public final class C0976e extends ContentObserver {

    /* renamed from: c */
    public static final ConcurrentHashMap f2312c = new ConcurrentHashMap(5);

    /* renamed from: a */
    public final ExecutorService f2313a;

    /* renamed from: b */
    public final Integer f2314b;

    public C0976e() {
        super(new Handler(Looper.getMainLooper()));
        this.f2313a = Executors.newFixedThreadPool(5);
        this.f2314b = 0;
        this.f2314b = 1;
    }

    @Override // android.database.ContentObserver
    public final void onChange(boolean z2, Uri uri) {
        if (uri == null || this.f2314b.intValue() != 1) {
            return;
        }
        String uri2 = uri.toString();
        if (AbstractC0026q.m151B(uri2)) {
            return;
        }
        ConcurrentHashMap concurrentHashMap = f2312c;
        if (concurrentHashMap.containsKey(uri2)) {
            return;
        }
        concurrentHashMap.put(uri2, uri);
        this.f2313a.submit(new CallableC0232a(uri, 2));
    }
}
