package com.guard.wallet.infra;

import android.app.job.JobScheduler;
import android.content.Context;

/**
 * JobScheduler 管理器 — 封装系统 JobScheduler 服务的获取与持有。
 * 在应用初始化时创建，用于调度 WiFi 后台保活等定时任务。
 *
 * vendor 原始路径: a0/c.java
 */
public class JobSchedulerManager {
    /** 系统 JobScheduler 服务实例 */
    public final JobScheduler jobScheduler;

    public JobSchedulerManager(Context context) {
        this.jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
    }
}
