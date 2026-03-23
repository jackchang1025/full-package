package com.vendor.rat.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

/**
 * Context 兼容的图片加载工具
 *
 * 从 GuideDialogHelper.loadImage() 提取，适配 AccessibilityService 上下文:
 * - 不依赖 Activity (用 Handler(MainLooper) 替代 runOnUiThread)
 * - WeakReference 防止 ImageView 泄漏
 *
 * 3 级加载策略:
 *   1. url 为空 → 加载 assets/{defaultAsset}
 *   2. url 以 http/https 开头 → 先加载 assets 兜底，再异步下载替换
 *   3. 其他 → BitmapFactory.decodeFile()，失败回退 assets
 */
public final class BlockImageLoader {

    private static final String TAG = "BlockImageLoader";

    private static final okhttp3.OkHttpClient HTTP_CLIENT = new okhttp3.OkHttpClient();

    private BlockImageLoader() {}

    /**
     * 加载图片到 ImageView
     *
     * @param ctx          Context (AccessibilityService 或 Application)
     * @param url          图片 URL (空=assets, http=网络, 其他=本地路径)
     * @param defaultAsset assets 目录下的默认文件名
     * @param target       目标 ImageView
     */
    public static void loadImage(Context ctx, String url, String defaultAsset, ImageView target) {
        if (url == null || url.isEmpty()) {
            loadImageFromAssets(ctx, defaultAsset, target);
        } else if (url.startsWith("http://") || url.startsWith("https://")) {
            // 先显示 assets 兜底
            loadImageFromAssets(ctx, defaultAsset, target);
            // 异步下载替换
            WeakReference<ImageView> targetRef = new WeakReference<>(target);
            new Thread(() -> {
                try {
                    okhttp3.Request req = new okhttp3.Request.Builder().url(url).build();
                    try (okhttp3.Response resp = HTTP_CLIENT.newCall(req).execute()) {
                        if (resp.isSuccessful() && resp.body() != null) {
                            Bitmap bmp = BitmapFactory.decodeStream(resp.body().byteStream());
                            if (bmp != null) {
                                ImageView iv = targetRef.get();
                                if (iv != null) {
                                    new Handler(Looper.getMainLooper()).post(() -> {
                                        ImageView iv2 = targetRef.get();
                                        if (iv2 != null) {
                                            iv2.setImageBitmap(bmp);
                                        }
                                    });
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Failed to load image from URL: " + url, e);
                }
            }, "block-img-loader").start();
        } else {
            // 本地文件路径
            try {
                Bitmap bmp = BitmapFactory.decodeFile(url);
                if (bmp != null) {
                    target.setImageBitmap(bmp);
                } else {
                    loadImageFromAssets(ctx, defaultAsset, target);
                }
            } catch (Exception e) {
                Log.e(TAG, "Failed to load local image: " + url, e);
                loadImageFromAssets(ctx, defaultAsset, target);
            }
        }
    }

    /**
     * 从 assets 加载图片
     */
    public static void loadImageFromAssets(Context ctx, String filename, ImageView target) {
        try (java.io.InputStream is = ctx.getAssets().open(filename)) {
            Bitmap bmp = BitmapFactory.decodeStream(is);
            if (bmp != null) {
                target.setImageBitmap(bmp);
            }
        } catch (Exception e) {
            Log.e(TAG, "Failed to load asset: " + filename, e);
        }
    }
}
