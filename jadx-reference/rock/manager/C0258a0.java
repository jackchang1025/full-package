package com.storm.safe.rock.manager;

import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.media.Image;
import android.media.ImageReader;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Size;
import com.storm.safe.rock.manager.C0258a0;
import com.storm.safe.rock.service.dqtvuisjd;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.AbstractC0767a0;
import kotlin.collections.EmptyList;
import p000.AbstractC0003a2;
import p000.AbstractC0134bh;
import p000.AbstractC0715je;
import p000.C0505fq;
import p000.C0506fr;
import p000.C0508ft;
import p000.C1214s9;
import p000.RunnableC0941o6;
import p000.h10;
import p000.t60;
import p000.w00;
import p000.y90;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: com.storm.safe.rock.manager.a0 */
/* loaded from: classes2.dex */
public final class C0258a0 {

    /* renamed from: a0 */
    public CameraDevice f52067a0;

    /* renamed from: a1 */
    public ImageReader f52068a1;

    /* renamed from: a2 */
    public CameraCaptureSession f52069a2;

    /* renamed from: a3 */
    public HandlerThread f52070a3;

    /* renamed from: a4 */
    public Handler f52071a4;

    /* renamed from: a5 */
    public final CameraManager f52072a5;

    /* renamed from: a6 */
    public String f52073a6;

    /* renamed from: a7 */
    public final List f52074a7;

    /* renamed from: a8 */
    public int f52075a8;

    /* renamed from: a9 */
    public final Semaphore f52076a9;

    /* renamed from: b0 */
    public boolean f52077b0;

    /* renamed from: b1 */
    public long f52078b1;

    /* renamed from: b2 */
    public final long f52079b2;

    /* renamed from: b3 */
    public volatile boolean f52080b3;

    /* renamed from: b4 */
    public volatile boolean f52081b4;

    /* renamed from: b5 */
    public volatile boolean f52082b5;

    /* renamed from: b6 */
    public int f52083b6;

    /* renamed from: b7 */
    public int f52084b7;

    /* renamed from: b8 */
    public final ArrayBlockingQueue f52085b8;

    /* renamed from: b9 */
    public final AtomicBoolean f52086b9;

    /* renamed from: c0 */
    public final y90 f52087c0;

    /* renamed from: c1 */
    public final ByteArrayOutputStream f52088c1;

    /* renamed from: c2 */
    public byte[] f52089c2;

    /* renamed from: c3 */
    public volatile long f52090c3;

    /* renamed from: c4 */
    public h10 f52091c4;

    /* renamed from: c5 */
    public final C0508ft f52092c5;

    /* renamed from: c6 */
    public final C0505fq f52093c6;

    static {
        new C0506fr(null);
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x009a, code lost:
    
        if (r6.f52074a7.isEmpty() != false) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x009c, code lost:
    
        r6.f52075a8 = 0;
        r7 = (java.lang.String) r6.f52074a7.get(0);
        r6.f52073a6 = r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x00a8, code lost:
    
        if (r7 != null) goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x00ab, code lost:
    
        r7 = r6.f52072a5.getCameraCharacteristics(r7);
        p000.t60.m214694b5(r7, "cameraManager.getCameraCharacteristics(id)");
        r7 = (java.lang.Integer) r7.get(android.hardware.camera2.CameraCharacteristics.LENS_FACING);
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x00bc, code lost:
    
        if (r7 != null) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x00c3, code lost:
    
        if (r7.intValue() != 0) goto L27;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x00c5, code lost:
    
        r2 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x00c6, code lost:
    
        r6.f52082b5 = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x00c9, code lost:
    
        r6.f52082b5 = true;
     */
    /* JADX WARN: Type inference failed for: r7v5, types: [fq] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public C0258a0(dqtvuisjd dqtvuisjdVar) throws CameraAccessException {
        Object systemService = dqtvuisjdVar.getSystemService("camera");
        t60.m214693b4(systemService, "null cannot be cast to non-null type android.hardware.camera2.CameraManager");
        CameraManager cameraManager = (CameraManager) systemService;
        this.f52072a5 = cameraManager;
        this.f52074a7 = EmptyList.f57568a0;
        this.f52076a9 = new Semaphore(1);
        this.f52079b2 = 500L;
        this.f52082b5 = true;
        this.f52083b6 = 640;
        this.f52084b7 = 480;
        this.f52085b8 = new ArrayBlockingQueue(15);
        boolean z = false;
        this.f52086b9 = new AtomicBoolean(false);
        this.f52087c0 = AbstractC0767a0.m213609a0(new w00() { // from class: com.storm.safe.rock.manager.CameraManager$sendExecutor$2
            @Override // p000.w00
            public final Object invoke() {
                return Executors.newSingleThreadExecutor();
            }
        });
        this.f52088c1 = new ByteArrayOutputStream(65536);
        try {
            String[] cameraIdList = cameraManager.getCameraIdList();
            t60.m214694b5(cameraIdList, "cameraManager.cameraIdList");
            List listM210733f6 = AbstractC0134bh.m210733f6(cameraIdList);
            this.f52074a7 = listM210733f6;
            int size = listM210733f6.size();
            int i = 0;
            while (true) {
                if (i >= size) {
                    break;
                }
                String str = (String) this.f52074a7.get(i);
                CameraCharacteristics cameraCharacteristics = this.f52072a5.getCameraCharacteristics(str);
                t60.m214694b5(cameraCharacteristics, "cameraManager.getCameraCharacteristics(id)");
                Integer num = (Integer) cameraCharacteristics.get(CameraCharacteristics.LENS_FACING);
                if (num != null && num.intValue() == 0) {
                    this.f52075a8 = i;
                    this.f52073a6 = str;
                    this.f52082b5 = true;
                    break;
                }
                i++;
            }
        } catch (Exception e) {
            t60.m214705c6("CameraManager", "初始化摄像头列表失败", e);
        }
        this.f52092c5 = new C0508ft(this);
        this.f52093c6 = new ImageReader.OnImageAvailableListener() { // from class: fq
            @Override // android.media.ImageReader.OnImageAvailableListener
            public final void onImageAvailable(ImageReader imageReader) {
                C0258a0 c0258a0 = this.f56312a0;
                if (!c0258a0.f52077b0) {
                    if (imageReader.acquireLatestImage() != null) {
                        return;
                    } else {
                        return;
                    }
                }
                Image imageAcquireLatestImage = imageReader.acquireLatestImage();
                if (imageAcquireLatestImage == null) {
                    return;
                }
                long jCurrentTimeMillis = System.currentTimeMillis();
                if (jCurrentTimeMillis - c0258a0.f52090c3 < 200) {
                    return;
                }
                c0258a0.f52090c3 = jCurrentTimeMillis;
                try {
                    byte[] bArrM211250a9 = c0258a0.m211250a9(imageAcquireLatestImage);
                    if (bArrM211250a9 != null) {
                        c0258a0.f52085b8.offer(bArrM211250a9);
                    }
                } catch (Exception e2) {
                    t60.m214705c6("CameraManager", "处理帧失败", e2);
                } finally {
                    imageAcquireLatestImage.close();
                }
            }
        };
    }

    /* renamed from: a1 */
    public static Size m211241a1(Size[] sizeArr) {
        Size size;
        if (sizeArr == null || sizeArr.length == 0) {
            return new Size(640, 480);
        }
        int length = sizeArr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                size = null;
                break;
            }
            size = sizeArr[i];
            if (size.getWidth() == 640 && size.getHeight() == 480) {
                break;
            }
            i++;
        }
        if (size != null) {
            return size;
        }
        ArrayList arrayList = new ArrayList();
        for (Size size2 : sizeArr) {
            if (size2.getWidth() <= 640 && size2.getHeight() <= 480) {
                arrayList.add(size2);
            }
        }
        Size size3 = (Size) AbstractC0715je.m213291h8(AbstractC0715je.m213300i7(arrayList, new C1214s9(1)));
        return size3 == null ? new Size(640, 480) : size3;
    }

    /* renamed from: a0 */
    public final void m211242a0() {
        CameraCaptureSession cameraCaptureSession = this.f52069a2;
        if (cameraCaptureSession != null) {
            cameraCaptureSession.close();
        }
        this.f52069a2 = null;
        CameraDevice cameraDevice = this.f52067a0;
        if (cameraDevice != null) {
            cameraDevice.close();
        }
        this.f52067a0 = null;
        ImageReader imageReader = this.f52068a1;
        if (imageReader != null) {
            imageReader.close();
        }
        this.f52068a1 = null;
    }

    /* renamed from: a2 */
    public final void m211243a2() throws CameraAccessException {
        String str = this.f52073a6;
        if (str == null) {
            t60.m214704c5("CameraManager", "没有可用的摄像头");
            return;
        }
        this.f52078b1 = System.currentTimeMillis();
        this.f52080b3 = true;
        HandlerThread handlerThread = new HandlerThread("CameraBackground");
        handlerThread.start();
        this.f52070a3 = handlerThread;
        this.f52071a4 = new Handler(handlerThread.getLooper());
        try {
            if (!this.f52076a9.tryAcquire(2500L, TimeUnit.MILLISECONDS)) {
                this.f52080b3 = false;
                throw new RuntimeException("等待摄像头超时");
            }
            this.f52072a5.openCamera(str, this.f52092c5, this.f52071a4);
            m211247a6();
            this.f52081b4 = true;
        } catch (SecurityException e) {
            t60.m214705c6("CameraManager", "摄像头权限被拒绝", e);
            this.f52080b3 = false;
        } catch (Exception e2) {
            t60.m214705c6("CameraManager", "启动摄像头失败", e2);
            this.f52076a9.release();
            this.f52080b3 = false;
        }
    }

    /* renamed from: a3 */
    public final void m211244a3() throws InterruptedException {
        this.f52078b1 = System.currentTimeMillis();
        this.f52080b3 = true;
        this.f52077b0 = false;
        this.f52086b9.set(false);
        this.f52085b8.clear();
        this.f52089c2 = null;
        try {
            try {
                this.f52076a9.acquire();
                m211242a0();
                this.f52081b4 = false;
            } catch (InterruptedException e) {
                t60.m214705c6("CameraManager", "停止摄像头时中断", e);
            } catch (Exception e2) {
                t60.m214705c6("CameraManager", "停止摄像头失败", e2);
            }
            HandlerThread handlerThread = this.f52070a3;
            if (handlerThread != null) {
                handlerThread.quitSafely();
            }
            try {
                HandlerThread handlerThread2 = this.f52070a3;
                if (handlerThread2 != null) {
                    handlerThread2.join();
                }
                this.f52070a3 = null;
                this.f52071a4 = null;
            } catch (InterruptedException e3) {
                t60.m214705c6("CameraManager", "停止后台线程失败", e3);
            }
        } finally {
            this.f52076a9.release();
            this.f52080b3 = false;
        }
    }

    /* renamed from: a4 */
    public final String m211245a4() throws CameraAccessException {
        try {
            String str = this.f52073a6;
            if (str == null) {
                return "无可用摄像头";
            }
            CameraCharacteristics cameraCharacteristics = this.f52072a5.getCameraCharacteristics(str);
            t60.m214694b5(cameraCharacteristics, "cameraManager.getCameraCharacteristics(cameraId)");
            Integer num = (Integer) cameraCharacteristics.get(CameraCharacteristics.LENS_FACING);
            return ((num != null && num.intValue() == 0) ? "前置" : (num != null && num.intValue() == 1) ? "后置" : "未知") + " 摄像头 (" + this.f52073a6 + ")";
        } catch (Exception e) {
            return AbstractC0003a2.m48c9("获取摄像头信息失败: ", e.getMessage());
        }
    }

    /* renamed from: a5 */
    public final void m211246a5() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis - this.f52078b1 < this.f52079b2) {
            t60.m214726f4("CameraManager", "⏳ 摄像头操作太频繁，忽略");
            return;
        }
        if (this.f52080b3) {
            t60.m214726f4("CameraManager", "⏳ 摄像头正在操作中，忽略启动请求");
            return;
        }
        if (this.f52081b4) {
            return;
        }
        String str = this.f52073a6;
        if (str == null) {
            t60.m214704c5("CameraManager", "没有可用的摄像头");
            return;
        }
        this.f52078b1 = jCurrentTimeMillis;
        this.f52080b3 = true;
        HandlerThread handlerThread = new HandlerThread("CameraBackground");
        handlerThread.start();
        this.f52070a3 = handlerThread;
        this.f52071a4 = new Handler(handlerThread.getLooper());
        try {
            if (!this.f52076a9.tryAcquire(2500L, TimeUnit.MILLISECONDS)) {
                this.f52080b3 = false;
                throw new RuntimeException("等待摄像头超时");
            }
            this.f52072a5.openCamera(str, this.f52092c5, this.f52071a4);
            m211247a6();
            this.f52081b4 = true;
            this.f52080b3 = false;
        } catch (SecurityException e) {
            t60.m214705c6("CameraManager", "摄像头权限被拒绝", e);
            this.f52080b3 = false;
        } catch (Exception e2) {
            t60.m214705c6("CameraManager", "启动摄像头失败", e2);
            this.f52076a9.release();
            this.f52080b3 = false;
        }
    }

    /* renamed from: a6 */
    public final void m211247a6() {
        this.f52077b0 = true;
        if (this.f52086b9.getAndSet(true)) {
            return;
        }
        ((ExecutorService) this.f52087c0.getValue()).submit(new RunnableC0941o6(4, this));
    }

    /* renamed from: a7 */
    public final void m211248a7() {
        if (System.currentTimeMillis() - this.f52078b1 < this.f52079b2) {
            t60.m214726f4("CameraManager", "⏳ 摄像头操作太频繁，忽略");
        } else if (this.f52080b3) {
            t60.m214726f4("CameraManager", "⏳ 摄像头正在操作中，忽略停止请求");
        } else if (this.f52081b4) {
            m211244a3();
        }
    }

    /* renamed from: a8 */
    public final void m211249a8() throws InterruptedException, CameraAccessException {
        boolean z = true;
        if (this.f52074a7.size() <= 1) {
            t60.m214726f4("CameraManager", "只有一个或没有可用摄像头，无法切换");
            return;
        }
        try {
            m211244a3();
            int size = (this.f52075a8 + 1) % this.f52074a7.size();
            this.f52075a8 = size;
            String str = (String) this.f52074a7.get(size);
            this.f52073a6 = str;
            if (str == null) {
                return;
            }
            CameraCharacteristics cameraCharacteristics = this.f52072a5.getCameraCharacteristics(str);
            t60.m214694b5(cameraCharacteristics, "cameraManager.getCameraCharacteristics(cameraId)");
            Integer num = (Integer) cameraCharacteristics.get(CameraCharacteristics.LENS_FACING);
            if (num == null || num.intValue() != 0) {
                z = false;
            }
            this.f52082b5 = z;
            String str2 = this.f52082b5 ? "前置" : "后置";
            t60.m214714d6("CameraManager", "切换到" + str2 + "摄像头: " + this.f52073a6);
            Thread.sleep(200L);
            m211243a2();
        } catch (Exception e) {
            t60.m214705c6("CameraManager", "切换摄像头失败", e);
        }
    }

    /* renamed from: a9 */
    public final synchronized byte[] m211250a9(Image image) {
        try {
            try {
                int width = image.getWidth();
                int height = image.getHeight();
                Image.Plane plane = image.getPlanes()[0];
                Image.Plane plane2 = image.getPlanes()[1];
                Image.Plane plane3 = image.getPlanes()[2];
                ByteBuffer byteBufferDuplicate = plane.getBuffer().duplicate();
                ByteBuffer byteBufferDuplicate2 = plane2.getBuffer().duplicate();
                ByteBuffer byteBufferDuplicate3 = plane3.getBuffer().duplicate();
                byteBufferDuplicate.rewind();
                byteBufferDuplicate2.rewind();
                byteBufferDuplicate3.rewind();
                int rowStride = plane.getRowStride();
                int rowStride2 = plane2.getRowStride();
                int pixelStride = plane2.getPixelStride();
                int i = ((width * height) * 3) / 2;
                byte[] bArr = this.f52089c2;
                if (bArr == null) {
                    bArr = new byte[i];
                    this.f52089c2 = bArr;
                } else {
                    if (bArr.length != i) {
                        bArr = null;
                    }
                    if (bArr == null) {
                        bArr = new byte[i];
                        this.f52089c2 = bArr;
                    }
                }
                int i2 = 0;
                for (int i3 = 0; i3 < height; i3++) {
                    byteBufferDuplicate.position(i3 * rowStride);
                    byteBufferDuplicate.get(bArr, i2, width);
                    i2 += width;
                }
                int i4 = height / 2;
                int i5 = width / 2;
                for (int i6 = 0; i6 < i4; i6++) {
                    for (int i7 = 0; i7 < i5; i7++) {
                        int i8 = (i7 * pixelStride) + (i6 * rowStride2);
                        int i9 = i2 + 1;
                        bArr[i2] = byteBufferDuplicate3.get(i8);
                        i2 += 2;
                        bArr[i9] = byteBufferDuplicate2.get(i8);
                    }
                }
                YuvImage yuvImage = new YuvImage(bArr, 17, width, height, null);
                this.f52088c1.reset();
                yuvImage.compressToJpeg(new Rect(0, 0, width, height), 80, this.f52088c1);
            } catch (Exception e) {
                t60.m214705c6("CameraManager", "YUV转JPEG失败", e);
                return null;
            }
        } catch (Throwable th) {
            throw th;
        }
        return this.f52088c1.toByteArray();
    }
}
