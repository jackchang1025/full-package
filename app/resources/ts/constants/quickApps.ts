import type { Component } from 'vue';
import {
    WalletOutline,
    GlobeOutline,
    KeyOutline,
    CashOutline,
    LogoAlipay,
} from '@vicons/ionicons5';
// LogoWechat 需要单独处理，ionicons5 没有微信图标，使用 ChatbubbleEllipsesOutline 替代
import { ChatbubbleEllipsesOutline as LogoWechat } from '@vicons/ionicons5';

/**
 * 快捷应用配置接口
 */
export interface QuickAppConfig {
    /** 用于映射的 key (与按钮显示名对应) */
    key: string;
    /** 应用显示名称 */
    name: string;
    /** Android 应用包名 */
    pkg: string;
    /** 图标组件 */
    icon: Component;
    /** 按钮颜色 */
    color: string;
}

/**
 * 快捷应用列表配置
 * 用于 UI 展示和包名映射
 */
export const quickApps: QuickAppConfig[] = [
    { key: 'TP', name: 'TokenPocket', pkg: 'vip.mytokenpocket', icon: WalletOutline, color: '#1677ff' },
    { key: 'IM', name: 'imToken', pkg: 'im.token.app', icon: WalletOutline, color: '#07c160' },
    { key: 'TG', name: 'Telegram', pkg: 'org.telegram.messenger', icon: GlobeOutline, color: '#0088cc' },
    { key: 'OneKey', name: 'OneKey', pkg: 'so.onekey.app.wallet', icon: KeyOutline, color: '#F59E0B' },
    { key: '波宝', name: '波宝Pro', pkg: 'com.tronlinkpro.wallet', icon: CashOutline, color: '#EF4444' },
    { key: '支付宝', name: '支付宝', pkg: 'com.eg.android.AlipayGphone', icon: LogoAlipay, color: '#1677ff' },
    { key: '微信', name: '微信', pkg: 'com.tencent.mm', icon: LogoWechat, color: '#07c160' },
];

/**
 * 快捷应用包名映射表
 * key -> { pkg, name }
 */
export const quickAppMap: Record<string, { pkg: string; name: string }> = Object.fromEntries(
    quickApps.map(app => [app.key, { pkg: app.pkg, name: app.name }])
);

/**
 * 根据 key 获取应用包名
 */
export function getAppPackageName(key: string): string | undefined {
    return quickAppMap[key]?.pkg;
}

/**
 * 根据 key 获取应用显示名
 */
export function getAppDisplayName(key: string): string | undefined {
    return quickAppMap[key]?.name;
}
