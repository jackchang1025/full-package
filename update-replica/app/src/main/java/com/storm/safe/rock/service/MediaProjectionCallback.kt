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
        // vendor: JADX C0281a1.onStop → sets f52306c4=false, posts ve0(service,1) → calls m211389a2()
        // MediaDisplayService is a skeleton; full cleanup deferred to MediaDisplayService integration.
    }
}
