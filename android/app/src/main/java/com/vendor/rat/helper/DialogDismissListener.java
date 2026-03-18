package com.vendor.rat.helper;

import android.content.DialogInterface;
import android.util.Log;

/**
 * Vendor: com.guard.wallet.helper.k
 * OnDismissListener for dialogs - clears dialog references on dismiss.
 * mode 0: clears WifiGuideDialogHelper reference
 * mode 1: clears AccessibilityUtils dialog reference
 */
public final class DialogDismissListener implements DialogInterface.OnDismissListener {

    public final int mode;

    public DialogDismissListener(int mode) {
        this.mode = mode;
    }

    @Override
    public final void onDismiss(DialogInterface dialogInterface) {
        switch (this.mode) {
            case 0:
                WifiGuideDialogHelper.dialogRef = null;
                return;
            default:
                // ADAPT: vendor clears utils.b dialog reference
                Log.d("DialogDismissListener", "dialog dismissed, mode=" + mode);
                return;
        }
    }
}
