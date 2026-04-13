package com.storm.safe.rock.service

import android.media.projection.MediaProjection
import android.util.Log

/**
 * MediaProjection.Callback that handles projection stop events.
 *
 * JADX reference: service/C0281a1.java (32 LOC)
 * Inner class associated with MediaDisplayService — handles the onStop() callback
 * when the media projection permission is revoked or stopped.
 */
class MediaProjectionCallback(
    val service: MediaDisplayService
) : MediaProjection.Callback() {

    companion object {
        private const val TAG = "ScreenProjectionSvc"
    }

    override fun onStop() {
        Log.w(TAG, "📺📺📺 [Callback] onStop() - 投屏权限已停止!")
        // ADAPT: In JADX source, this updates MediaDisplayService static state
        // and posts a runnable via handler. Simplified here since MediaDisplayService
        // is a skeleton in the current phase.
    }
}
