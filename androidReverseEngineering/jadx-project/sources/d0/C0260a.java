package d0;

import a1.AbstractC0026q;
import android.graphics.Bitmap;
import android.media.MediaFormat;
import android.os.Build;
import android.util.Log;
import com.guard.wallet.thread.C0235d;
import com.guard.wallet.thread.CallableC0240i;
import com.guard.wallet.utils.AbstractC0251g;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Objects;
import java.util.Timer;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import p000a.AbstractC0000a;
import p017u.C0918a;

/* renamed from: d0.a */
/* loaded from: classes.dex */
public final class C0260a {

    /* renamed from: i */
    public static final ExecutorService f421i = Executors.newFixedThreadPool(5);

    /* renamed from: j */
    public static final LinkedList f422j = new LinkedList();

    /* renamed from: a */
    public final ConcurrentLinkedQueue f423a = new ConcurrentLinkedQueue();

    /* renamed from: b */
    public final AtomicBoolean f424b = new AtomicBoolean(false);

    /* renamed from: c */
    public final AtomicLong f425c = new AtomicLong(0);

    /* renamed from: d */
    public final Timer f426d = new Timer();

    /* renamed from: e */
    public final C0235d f427e;

    /* renamed from: f */
    public final C0918a f428f;

    /* renamed from: g */
    public final String f429g;

    /* renamed from: h */
    public final MediaFormat f430h;

    public C0260a(int i2, int i3) {
        String format;
        StringBuilder sb = new StringBuilder();
        sb.append(AbstractC0251g.i0());
        String m18n = AbstractC0000a.m18n(sb, File.separator, "CacheVideos");
        File file = new File(m18n);
        boolean exists = file.exists();
        int i4 = 2;
        if (exists) {
            if (file.listFiles() != null) {
                File[] listFiles = file.listFiles();
                Objects.requireNonNull(listFiles);
                for (File file2 : listFiles) {
                    Log.d("VideoRecordManager", String.format(Locale.CHINA, "删除Video文件:%s %b", file2.getName(), Boolean.valueOf(file2.delete())));
                }
            }
            format = String.format(Locale.CHINA, "Video目录:%s", m18n);
        } else {
            exists = file.mkdirs();
            format = String.format(Locale.CHINA, "创建Video目录:%s -> %b", m18n, Boolean.valueOf(exists));
        }
        Log.d("VideoRecordManager", format);
        this.f429g = exists ? m18n : null;
        try {
            MediaFormat createVideoFormat = MediaFormat.createVideoFormat("video/avc", i2, i3);
            this.f430h = createVideoFormat;
            createVideoFormat.setInteger("color-format", 2130708361);
            createVideoFormat.setInteger("bitrate", i2 * i3 * 10);
            createVideoFormat.setInteger("frame-rate", 25);
            createVideoFormat.setInteger("i-frame-interval", 1);
            if (Build.VERSION.SDK_INT >= 30) {
                this.f428f = new C0918a(Float.valueOf(0.5f), 20);
            }
            this.f427e = new C0235d(this, i4);
        } catch (Exception e2) {
            AbstractC0026q.m186s("VideoRecordManager", e2);
        }
    }

    /* renamed from: a */
    public final void m734a() {
        String str;
        ConcurrentLinkedQueue concurrentLinkedQueue = this.f423a;
        if (concurrentLinkedQueue.isEmpty()) {
            return;
        }
        String str2 = this.f429g;
        if (AbstractC0026q.m151B(str2)) {
            str = null;
        } else {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMdd_HHmmss", Locale.CHINA);
            StringBuilder m20p = AbstractC0000a.m20p(str2);
            m20p.append(File.separator);
            m20p.append("v-");
            m20p.append(simpleDateFormat.format(new Date()));
            m20p.append(".mp4");
            str = m20p.toString();
            Log.d("VideoRecordManager", "tmp video file " + str);
            File file = new File(str);
            if (file.exists()) {
                Log.d("VideoRecordManager", String.format(Locale.CHINA, "删除Video文件:%s -> %b", str, Boolean.valueOf(file.delete())));
            }
        }
        f422j.add(f421i.submit(new CallableC0240i((Bitmap[]) concurrentLinkedQueue.toArray(new Bitmap[0]), str, this.f430h)));
        this.f425c.set(System.currentTimeMillis());
        concurrentLinkedQueue.clear();
    }
}
