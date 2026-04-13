/**
 * 空占位 LinearLayout，显示等待图标和提示文字。
 * vendor 原始路径: e0/a.java
 */
package com.guard.wallet.view;

import com.guard.wallet.core.AppUtils;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.Objects;

public final class ViewPlaceholder extends LinearLayout {

    public ViewPlaceholder(Activity activity, String hintText) {
        super(activity);
        LayoutParams params = new LayoutParams(-1, -1);
        params.gravity = 1;
        setOrientation(VERTICAL);
        setGravity(17);
        setLayoutParams(params);

        RemoteImageView iconView = new RemoteImageView(activity);
        if (!iconView.a()) {
            iconView.setImageURL(com.guard.wallet.utils.ConfigManager.getBlockIconUrl());
        }
        iconView.setTag("waiting-icon-image");
        addView(iconView, 800, 160);

        if (!AppUtils.B(hintText)) {
            TextView textView = new TextView(activity);
            textView.setTag("waiting-hint-text");
            textView.setText(hintText);
            textView.setSingleLine(false);
            textView.setTextColor(-1);
            textView.setBackgroundColor(0);
            textView.setTextAlignment(TEXT_ALIGNMENT_CENTER);
            textView.setGravity(8388611);
            textView.setTextSize(2, 16.0f);
            textView.setTypeface(Typeface.defaultFromStyle(1), 1);
            addView(textView, 800, 260);
        }
    }

    @Override
    public final void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int childCount = getChildCount();
        if (childCount > 0) {
            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);
                if (Objects.equals(child.getTag(), "waiting-icon-image")) {
                    int width = right - left;
                    int offset = (width - (int) (width * 0.4f)) / 2;
                    child.layout(offset, 5, right - offset, 165);
                }
                if (Objects.equals(child.getTag(), "waiting-hint-text")) {
                    int margin = ((right - left) - 800) / 2;
                    child.layout(margin, 180, right - margin, 440);
                }
            }
        }
    }
}
