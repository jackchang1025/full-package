package p000;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class po0 {

    /* renamed from: a4 */
    public static final oo0 f59312a4 = new oo0(null);

    /* renamed from: a5 */
    public static final HashMap f59313a5 = new HashMap();

    /* renamed from: a0 */
    public final boolean f59314a0;

    /* renamed from: a1 */
    public final File f59315a1;

    /* renamed from: a2 */
    public final Lock f59316a2;

    /* renamed from: a3 */
    public FileChannel f59317a3;

    public po0(String str, File file, boolean z) {
        this.f59314a0 = z;
        File file2 = new File(file, str.concat(".lck"));
        this.f59315a1 = file2;
        String absolutePath = file2.getAbsolutePath();
        t60.m214694b5(absolutePath, "lockFile.absolutePath");
        this.f59316a2 = f59312a4.getThreadLock(absolutePath);
    }

    /* renamed from: a0 */
    public final void m214300a0(boolean z) throws IOException {
        File file = this.f59315a1;
        this.f59316a2.lock();
        if (z) {
            try {
                File parentFile = file.getParentFile();
                if (parentFile != null) {
                    parentFile.mkdirs();
                }
                FileChannel channel = new FileOutputStream(file).getChannel();
                channel.lock();
                this.f59317a3 = channel;
            } catch (IOException unused) {
                this.f59317a3 = null;
            }
        }
    }

    /* renamed from: a1 */
    public final void m214301a1() throws IOException {
        try {
            FileChannel fileChannel = this.f59317a3;
            if (fileChannel != null) {
                fileChannel.close();
            }
        } catch (IOException unused) {
        }
        this.f59316a2.unlock();
    }
}
