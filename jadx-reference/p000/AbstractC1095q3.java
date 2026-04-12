package p000;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: q3 */
/* loaded from: classes2.dex */
public abstract class AbstractC1095q3 {

    /* renamed from: a0 */
    public static final CopyOnWriteArrayList f59370a0 = new CopyOnWriteArrayList();

    /* renamed from: a1 */
    public static final AtomicBoolean f59371a1 = new AtomicBoolean(false);

    /* renamed from: a2 */
    public static final ExecutorService f59372a2 = Executors.newSingleThreadExecutor();

    /* renamed from: a3 */
    public static final Handler f59373a3 = new Handler(Looper.getMainLooper());
}
