package com.guard.wallet.utils;

import android.graphics.Bitmap;
import java.io.ByteArrayOutputStream;

/**
 * 位图处理工具类。
 */
public final class BitmapUtils {
    private BitmapUtils() {}

    /** g.J0(Bitmap) — 安全回收 Bitmap */
    public static void recycleBitmap(Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
    }

    /** g.k0(Bitmap, double) — 按目标宽度缩放 */
    public static Bitmap scaleBitmap(Bitmap src, double targetWidth) {
        if (src == null || targetWidth <= 0) return src;
        double scale = targetWidth / src.getWidth();
        int newW = (int) (src.getWidth() * scale);
        int newH = (int) (src.getHeight() * scale);
        return Bitmap.createScaledBitmap(src, newW, newH, true);
    }

    /** g.M0(Bitmap, float, int) — 压缩为字节数组 */
    public static byte[] compressBitmapToBytes(Bitmap bitmap, float scale, int quality) {
        if (bitmap == null) return new byte[0];
        Bitmap target = bitmap;
        if (scale > 0 && scale < 1.0f) {
            int w = (int) (bitmap.getWidth() * scale);
            int h = (int) (bitmap.getHeight() * scale);
            target = Bitmap.createScaledBitmap(bitmap, w, h, true);
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        target.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        return baos.toByteArray();
    }

    /** g.y(Bitmap) — Bitmap 格式转换 */
    public static Bitmap convertBitmap(Bitmap src) {
        if (src == null) return null;
        return src.copy(Bitmap.Config.ARGB_8888, false);
    }
}
