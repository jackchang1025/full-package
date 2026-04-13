/**
 * 自定义 ImageView，加载 Bitmap/Drawable
 * - 优先从 assets 读取图标文件
 * - 回退到 PackageManager 应用图标
 * - 支持远程 URL 下载图标并缓存到本地
 *
 * vendor 原始路径: e0/c.java
 */
package com.guard.wallet.view;

import com.guard.wallet.core.AppUtils;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public final class RemoteImageView extends ImageView {
    public static final int SYNTHETIC_FLAG = 0;
    public final BitmapHandler imageHandler = new BitmapHandler(this);

    public RemoteImageView(ContextWrapper contextWrapper) {
        super(contextWrapper);
    }

    public final boolean a() {
        Bitmap bitmap = null;
        Context ctx = com.guard.wallet.utils.SystemHelper.Z();
        if (ctx != null && ctx.getAssets() != null) {
            try {
                String assetName = com.guard.wallet.utils.DeviceUtils.getBrandJsFileName();
                if (AppUtils.B(assetName)) {
                    assetName = "android.png";
                }
                bitmap = BitmapFactory.decodeStream(ctx.getAssets().open(assetName));
            } catch (Exception ex) {
                AppUtils.s("com.guard.wallet.utils.d", ex);
            }
        }
        if (bitmap != null) {
            this.setImageBitmap(bitmap);
            return true;
        }
        Drawable icon = null;
        Context ctx2 = com.guard.wallet.utils.SystemHelper.Z();
        if (ctx2 != null) {
            try {
                icon = ctx2.getPackageManager().getApplicationIcon(ctx2.getPackageName());
            } catch (Exception ex) {
                AppUtils.s("ApplicationUtil", ex);
            }
        }
        if (icon != null) {
            this.setImageDrawable(icon);
            return true;
        }
        return false;
    }

    public void setImageURL(String url) {
        String dir = com.guard.wallet.utils.SystemHelper.i0();
        if (!AppUtils.B(dir)) {
            String path = dir.concat("/").concat("block_icon.webp");
            if (AppUtils.w(path)) {
                Bitmap bmp = AppUtils.J(path);
                if (bmp != null) {
                    this.setImageBitmap(bmp);
                    return;
                }
            }
            if (!AppUtils.B(url)) {
                // ADAPT: vendor calls a(ImageView, url, path, 1); replica ServerTaskRunner takes (int, String, Serializable, Object)
                new Thread(new com.guard.wallet.server.ServerTaskRunner(1, url, path, new BitmapHandler(this))).start();
            }
        }
    }
}
