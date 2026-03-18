package com.vendor.rat.helper;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;

/**
 * Vendor: e0.f — 进度条自定义 View
 * 白色背景圆角条 + 蓝色填充进度
 * 通过 Handler.sendMessage(what=1~100) 更新进度
 */
public final class BlockProgressBar extends View implements Handler.Callback {

    public final Handler handler;
    public Paint bgPaint;
    public Paint fgPaint;
    public RectF bgRect;
    public RectF fgRect;
    public int progress;       // vendor: f308f, 0~100
    public int barWidth;       // vendor: f309g = 380
    public int barHeight;      // vendor: f310h = 14

    public BlockProgressBar(android.content.Context context) {
        super(context);
        this.progress = 0;
        this.barWidth = 380;
        this.barHeight = 14;

        // vendor: 白色背景画笔
        Paint paint = new Paint();
        this.bgPaint = paint;
        paint.setColor(-1); // white
        this.bgPaint.setAntiAlias(true);
        this.bgPaint.setStyle(Paint.Style.FILL);
        this.bgPaint.setStrokeWidth(0.0f);

        // vendor: 蓝色前景画笔 #1677ff
        Paint paint2 = new Paint();
        this.fgPaint = paint2;
        paint2.setColor(Color.parseColor("#1677ff"));
        this.fgPaint.setAntiAlias(true);
        this.fgPaint.setStyle(Paint.Style.FILL);
        this.fgPaint.setStrokeWidth(0.0f);

        this.handler = new Handler(Looper.getMainLooper(), this);
    }

    @Override
    public final boolean handleMessage(Message message) {
        int what = message.what;
        if (what <= 0 || what <= this.progress || what > 100) {
            return false;
        }
        this.progress = what;
        invalidate();
        return false;
    }

    @Override
    public final void onDraw(Canvas canvas) {
        if (this.bgRect == null) {
            RectF rectF = new RectF();
            this.bgRect = rectF;
            rectF.set(0.0f, 0.0f, this.barWidth, this.barHeight);
        }
        if (this.fgRect == null) {
            this.fgRect = new RectF();
        }
        RectF bg = this.bgRect;
        float left = bg.left;
        this.fgRect.set(left, bg.top, ((bg.right - left) * (this.progress / 100.0f)) + left, bg.bottom);
        canvas.drawRoundRect(this.bgRect, 100.0f, 100.0f, this.bgPaint);
        canvas.drawRoundRect(this.fgRect, 100.0f, 100.0f, this.fgPaint);
    }

    @Override
    public final void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (this.bgRect == null) {
            RectF rectF = new RectF();
            this.bgRect = rectF;
            int height = b - t;
            int width = (int) ((r - l) * 0.4f);
            this.barWidth = width;
            float offsetX = ((r - l) - width) / 2.0f;
            float offsetY;
            if (height >= this.barHeight) {
                offsetY = (height - this.barHeight) / 2.0f;
            } else {
                this.barHeight = height;
                offsetY = 0.0f;
            }
            rectF.set(offsetX, offsetY, width + offsetX, this.barHeight + offsetY);
        }
    }

    @Override
    public final void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
