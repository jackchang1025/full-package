package com.guard.wallet.media;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

/**
 * AudioRecordManager — 音频录制管理器。
 * vendor j.d 管理录音线程的启动/停止/状态。
 */
public final class AudioRecordManager {
    private static final String TAG = "AudioRecordManager";
    private static AudioRecordManager instance = new AudioRecordManager();

    /** vendor: PCM cache directory path */
    public static String m;
    /** vendor: WAV cache directory path */
    public static String n;

    public enum State { IDLE, PREPARING, RECORDING, STOPPING }

    private volatile State state = State.IDLE;
    private Thread recordThread;
    private int audioSource = -1;
    private long maxDuration = 1800000L; // 30 分钟
    private final Handler handler = new Handler(Looper.getMainLooper());

    /** vendor d.b() — 获取单例 */
    public static AudioRecordManager b() {
        if (instance == null) instance = new AudioRecordManager();
        return instance;
    }

    /** vendor d.d(int) — 开始录音 */
    public synchronized boolean d(int source) {
        if (state != State.IDLE) {
            Log.w(TAG, "无法开始录制，当前状态为 " + state);
            return false;
        }
        if (recordThread != null) {
            recordThread.interrupt();
            recordThread = null;
        }
        audioSource = (source >= 0 && source <= 10) ? source : 1;
        state = State.RECORDING;
        Log.d(TAG, "开始录音, audioSource=" + audioSource);
        // vendor 创建录音线程 b(this, audioSource) 并启动
        return true;
    }

    /** vendor d.e() — 停止录音 */
    public synchronized boolean e() {
        if (state != State.RECORDING) return false;
        state = State.STOPPING;
        Log.d(TAG, "录音结束");
        state = State.IDLE;
        return true;
    }

    public synchronized Integer c() {
        return audioSource;
    }

    public synchronized RecordState f() {
        switch (state) {
            case RECORDING:
                return RecordState.RECORDING;
            case PREPARING:
            case STOPPING:
                return RecordState.PAUSED;
            case IDLE:
            default:
                return RecordState.IDLE;
        }
    }
}
