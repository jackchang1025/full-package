package com.vendor.rat.activity;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vendor.rat.credential.LockCredentialStore;

/**
 * PIN input Activity for lock credential collection.
 *
 * Displays a 6-digit PIN entry UI with:
 * - Title text
 * - Masked PIN display (dots)
 * - Numeric keypad (0-9)
 * - Delete and submit buttons
 *
 * On successful 6-digit submit: saves PIN via LockCredentialStore, sets RESULT_OK, finishes.
 * On cancel/back: sets RESULT_CANCELED, finishes.
 */
public class LockCredentialPromptActivity extends Activity {

    private static final String TAG = "LockCredentialPrompt";
    private static final int MAX_PIN_LENGTH = 6;
    private static final char MASK_CHAR = '\u25CF'; // ●

    private final StringBuilder pinBuffer = new StringBuilder();
    private TextView pinDisplayView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    setContentView(buildLayout());
    }

    /**
     * Append a digit to the current PIN if under max length.
     */
    void onDigitClicked(char digit) {
        if (pinBuffer.length() < MAX_PIN_LENGTH) {
    pinBuffer.append(digit);
            refreshPinDisplay();
        }
    }

    /**
     * Remove the last entered digit.
     */
    void onDeleteClicked() {
        if (pinBuffer.length() > 0) {
            pinBuffer.deleteCharAt(pinBuffer.length() - 1);
          refreshPinDisplay();
        }
    }

    /**
     * Validate and submit the entered PIN.
     * Requires exactly 6 digits; shows Toast error if short.
     */
    void onSubmitClicked() {
      if (pinBuffer.length() < MAX_PIN_LENGTH) {
     // "请输入完整的6位密码"
            Toast.makeText(this, "\u8BF7\u8F93\u5165\u5B8C\u6574\u76846\u4F4D\u5BC6\u7801",
                    Toast.LENGTH_SHORT).show();
      return;
        }
   String pin = pinBuffer.toString();
     // Zero out buffer immediately after reading (security hygiene)
        pinBuffer.delete(0, pinBuffer.length());
        LockCredentialStore.savePin(pin);
        Log.d(TAG, "PIN saved, finishing with RESULT_OK");
        setResult(RESULT_OK);
        finish();
  }

    /**
   * Return the current entered PIN string (for testing).
     */
    public String getCurrentPin() {
        return pinBuffer.toString();
    }

    /**
     * Return the masked display text (dots for each entered digit).
     */
    public String getPinDisplayText() {
        StringBuilder masked = new StringBuilder();
      for (int i = 0; i < pinBuffer.length(); i++) {
            masked.append(MASK_CHAR);
        }
        return masked.toString();
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "Back pressed, finishing with RESULT_CANCELED");
      setResult(RESULT_CANCELED);
        finish();
    }

    private void refreshPinDisplay() {
    if (pinDisplayView != null) {
            pinDisplayView.setText(getPinDisplayText());
        }
    }

    // ========== Programmatic Layout ==========

    private View buildLayout() {
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
   root.setGravity(Gravity.CENTER_HORIZONTAL);
   root.setPadding(dp(32), dp(48), dp(32), dp(24));
        root.setBackgroundColor(Color.WHITE);

        root.addView(buildTitleView());
        root.addView(buildPinDisplayView());
   root.addView(buildKeypadRow('1', '2', '3'));
        root.addView(buildKeypadRow('4', '5', '6'));
        root.addView(buildKeypadRow('7', '8', '9'));
 root.addView(buildBottomRow());

        return root;
    }

    private TextView buildTitleView() {
        TextView title = new TextView(this);
        // "输入锁屏密码"
        title.setText("\u8F93\u5165\u9501\u5C4F\u5BC6\u7801");
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
        title.setTextColor(Color.BLACK);
        title.setTypeface(Typeface.DEFAULT_BOLD);
        title.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT,
      LinearLayout.LayoutParams.WRAP_CONTENT);
        params.bottomMargin = dp(32);
        title.setLayoutParams(params);
  return title;
    }

  private TextView buildPinDisplayView() {
      pinDisplayView = new TextView(this);
        pinDisplayView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
     pinDisplayView.setTextColor(Color.BLACK);
        pinDisplayView.setGravity(Gravity.CENTER);
        pinDisplayView.setLetterSpacing(0.5f);
        pinDisplayView.setMinWidth(dp(200));
   pinDisplayView.setMinHeight(dp(48));
   LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
 LinearLayout.LayoutParams.WRAP_CONTENT,
    LinearLayout.LayoutParams.WRAP_CONTENT);
      params.bottomMargin = dp(24);
        pinDisplayView.setLayoutParams(params);
        return pinDisplayView;
    }

    private LinearLayout buildKeypadRow(char c1, char c2, char c3) {
LinearLayout row = new LinearLayout(this);
      row.setOrientation(LinearLayout.HORIZONTAL);
        row.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(
              LinearLayout.LayoutParams.MATCH_PARENT,
      LinearLayout.LayoutParams.WRAP_CONTENT);
        rowParams.bottomMargin = dp(8);
        row.setLayoutParams(rowParams);

      row.addView(createDigitButton(c1));
    row.addView(createDigitButton(c2));
        row.addView(createDigitButton(c3));
        return row;
    }

    private LinearLayout buildBottomRow() {
        LinearLayout row = new LinearLayout(this);
    row.setOrientation(LinearLayout.HORIZONTAL);
    row.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(
         LinearLayout.LayoutParams.MATCH_PARENT,
       LinearLayout.LayoutParams.WRAP_CONTENT);
 rowParams.bottomMargin = dp(8);
        row.setLayoutParams(rowParams);

   // Delete button ("删除")
        Button deleteBtn = createActionButton("\u5220\u9664");
        deleteBtn.setOnClickListener(new View.OnClickListener() {
   @Override
    public void onClick(View v) {
  onDeleteClicked();
    }
        });
        row.addView(deleteBtn);

 // 0 button
     row.addView(createDigitButton('0'));

    // Submit button ("确定")
     Button submitBtn = createActionButton("\u786E\u5B9A");
    submitBtn.setOnClickListener(new View.OnClickListener() {
 @Override
      public void onClick(View v) {
         onSubmitClicked();
 }
      });
        row.addView(submitBtn);

        return row;
    }

    private Button createDigitButton(final char digit) {
        Button btn = new Button(this);
        btn.setText(String.valueOf(digit));
     btn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
     dp(72), dp(56));
    params.setMargins(dp(4), 0, dp(4), 0);
        btn.setLayoutParams(params);
      btn.setOnClickListener(new View.OnClickListener() {
          @Override
  public void onClick(View v) {
              onDigitClicked(digit);
   }
        });
        return btn;
    }

    private Button createActionButton(String label) {
 Button btn = new Button(this);
      btn.setText(label);
        btn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
      LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
dp(72), dp(56));
        params.setMargins(dp(4), 0, dp(4), 0);
        btn.setLayoutParams(params);
    return btn;
    }

    private int dp(int value) {
        return (int) TypedValue.applyDimension(
       TypedValue.COMPLEX_UNIT_DIP, value,
    getResources().getDisplayMetrics());
    }
}
