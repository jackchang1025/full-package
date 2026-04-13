package p007j;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.util.LinkedList;
import org.bouncycastle.math.ec.Tnaf;

/* renamed from: j.d */
/* loaded from: classes.dex */
public final class C0349d {

    /* renamed from: m */
    public static String f674m;

    /* renamed from: n */
    public static String f675n;

    /* renamed from: o */
    public static C0349d f676o = new C0349d();

    /* renamed from: a */
    public C0347b f677a;

    /* renamed from: b */
    public volatile EnumC0348c f678b = EnumC0348c.IDLE;

    /* renamed from: c */
    public File f679c = null;

    /* renamed from: d */
    public File f680d = null;

    /* renamed from: e */
    public FileOutputStream f681e = null;

    /* renamed from: f */
    public FileOutputStream f682f = null;

    /* renamed from: g */
    public int f683g = 0;

    /* renamed from: h */
    public final LinkedList f684h = new LinkedList();

    /* renamed from: j */
    public int f686j = -1;

    /* renamed from: k */
    public long f687k = 0;

    /* renamed from: l */
    public final Handler f688l = new Handler(Looper.getMainLooper());

    /* renamed from: i */
    public C0350e f685i = new C0350e(0);

    public C0349d() {
        m882c(this.f678b, "录音空闲中");
    }

    /* renamed from: a */
    public static byte[] m880a(C0349d c0349d, long j2, int i2) {
        c0349d.getClass();
        long j3 = j2 + 36;
        long j4 = 88200 * i2;
        return new byte[]{82, 73, 70, 70, (byte) (j3 & 255), (byte) ((j3 >> 8) & 255), (byte) ((j3 >> 16) & 255), (byte) ((j3 >> 24) & 255), 87, 65, 86, 69, 102, 109, 116, 32, Tnaf.POW_2_WIDTH, 0, 0, 0, 1, 0, (byte) i2, 0, (byte) 68, (byte) 172, (byte) 0, (byte) 0, (byte) (j4 & 255), (byte) ((j4 >> 8) & 255), (byte) ((j4 >> 16) & 255), (byte) ((j4 >> 24) & 255), (byte) (i2 * 2), 0, Tnaf.POW_2_WIDTH, 0, 100, 97, 116, 97, (byte) (j2 & 255), (byte) ((j2 >> 8) & 255), (byte) ((j2 >> 16) & 255), (byte) ((j2 >> 24) & 255)};
    }

    /* renamed from: b */
    public static C0349d m881b() {
        if (f676o == null) {
            f676o = new C0349d();
        }
        return f676o;
    }

    /* renamed from: c */
    public final synchronized void m882c(EnumC0348c enumC0348c, String str) {
        if (this.f685i != null) {
            this.f688l.post(new RunnableC0346a(this, enumC0348c, str));
        }
    }

    /* renamed from: d */
    public final synchronized boolean m883d(int i2) {
        if (!this.f678b.equals(EnumC0348c.IDLE)) {
            Log.w("AudioRecordManager", "无法开始录制，当前状态为 " + this.f678b);
            return false;
        }
        C0347b c0347b = this.f677a;
        if (c0347b != null) {
            c0347b.interrupt();
            this.f677a = null;
        }
        if (i2 < 0 || i2 > 10) {
            this.f686j = 1;
        } else {
            this.f686j = i2;
        }
        this.f687k = System.currentTimeMillis() + 1800000;
        C0347b c0347b2 = new C0347b(this, this.f686j);
        this.f677a = c0347b2;
        c0347b2.start();
        return true;
    }

    /* renamed from: e */
    public final synchronized boolean m884e() {
        if (!this.f678b.equals(EnumC0348c.RECORDING)) {
            return false;
        }
        this.f678b = EnumC0348c.STOP_RECORD;
        m882c(this.f678b, "录音结束");
        return true;
    }
}
