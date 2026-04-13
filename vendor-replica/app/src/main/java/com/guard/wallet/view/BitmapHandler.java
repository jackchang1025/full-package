/**
 * Bitmap 消息 Handler，接收 Bitmap 对象并设置到 ImageView。
 * vendor 原始路径: e0/b.java
 */
package com.guard.wallet.view;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

public final class BitmapHandler extends Handler {
    public final ImageView imageView;

    public BitmapHandler(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    public final void handleMessage(Message msg) {
        Object obj = msg.obj;
        if (obj != null) {
            this.imageView.setImageBitmap((Bitmap) obj);
        }
    }
}
