package p012o;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

/* loaded from: classes.dex */
public final class c0 {

    /* renamed from: a */
    public final ExecutorService f854a = Executors.newFixedThreadPool(2);

    /* renamed from: b */
    public final AtomicBoolean f855b;

    /* renamed from: c */
    public final AtomicBoolean f856c;

    public c0() {
        new AtomicLong(0L);
        this.f855b = new AtomicBoolean(false);
        this.f856c = new AtomicBoolean(false);
    }

    /* renamed from: a */
    public static boolean m1052a(int i2) {
        return Objects.equals(Integer.valueOf(i2), 1) || Objects.equals(Integer.valueOf(i2), 2) || Objects.equals(Integer.valueOf(i2), 8388608) || Objects.equals(Integer.valueOf(i2), 8);
    }

    /* renamed from: b */
    public static boolean m1053b(int i2) {
        return Objects.equals(Integer.valueOf(i2), 2048) || Objects.equals(Integer.valueOf(i2), 32) || Objects.equals(Integer.valueOf(i2), 16384) || Objects.equals(Integer.valueOf(i2), 4096) || Objects.equals(Integer.valueOf(i2), 4);
    }
}
