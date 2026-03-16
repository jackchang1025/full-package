package com.vendor.rat.keepalive;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;

/**
 * JobScheduler 保活服务 (模块 07)
 */
public class KeepAliveJobService extends JobService {

    private static final String TAG = "KeepAliveJob";
    private static final int JOB_ID = 1001;

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "Job started");
        KeepAliveManager.getInstance()
            .ensureServicesRunning(getApplicationContext());
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true; // 被中断时重试
    }

    public static void scheduleJob(Context context) {
        JobScheduler scheduler = (JobScheduler)
            context.getSystemService(Context.JOB_SCHEDULER_SERVICE);

        JobInfo jobInfo = new JobInfo.Builder(
            JOB_ID,
            new ComponentName(context, KeepAliveJobService.class)
        )
            .setPersisted(true)
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
            .setPeriodic(15 * 60 * 1000L) // 最小 15 分钟
            .build();

        scheduler.schedule(jobInfo);
    }
}
