package com.storm.safe.rock.activity

import android.app.Activity
import android.os.Bundle

/**
 * JADX: TransparentHelperActivity.java (14 lines)
 * Activity that immediately finishes in onCreate.
 */
class TransparentHelperActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        finish()
    }
}
