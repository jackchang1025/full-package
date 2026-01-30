<?php

namespace ApkBuilder;

/**
 * 构建日志记录器
 */
class BuildLogger
{
    private array $logs = [];
    private string $logFile;
    private float $startTime;
    private array $stepTimes = [];

    public function __construct(string $logDir)
    {
        $this->startTime = microtime(true);
        
        // 尝试创建主日志目录
        if (!is_dir($logDir)) {
            @mkdir($logDir, 0755, true);
        }
        
        $logFileName = 'apk_build_' . date('Y-m-d') . '.log';
        $primaryLogFile = $logDir . '/' . $logFileName;
        $fallbackLogFile = '/tmp/' . $logFileName;
        
        // 检查文件是否可写：
        // 1. 文件存在 -> 检查文件是否可写
        // 2. 文件不存在 -> 检查目录是否可写
        if (file_exists($primaryLogFile)) {
            // 文件已存在，检查文件本身是否可写
            $this->logFile = is_writable($primaryLogFile) ? $primaryLogFile : $fallbackLogFile;
        } else {
            // 文件不存在，检查目录是否可写（能否创建新文件）
            $this->logFile = (is_dir($logDir) && is_writable($logDir)) ? $primaryLogFile : $fallbackLogFile;
        }
    }

    /**
     * 记录日志
     */
    public function log(string $message): void
    {
        $elapsed = round((microtime(true) - $this->startTime) * 1000);
        $entry = [
            'time' => date('Y-m-d H:i:s'),
            'elapsed_ms' => $elapsed,
            'message' => $message
        ];
        $this->logs[] = $entry;
        
        // 静默写入，失败不抛异常
        @file_put_contents(
            $this->logFile, 
            "[{$entry['time']}] [{$elapsed}ms] {$message}\n", 
            FILE_APPEND
        );
    }

    /**
     * 开始计时步骤
     */
    public function startStep(string $stepName): void
    {
        $this->stepTimes[$stepName] = microtime(true);
        $this->log("Starting: {$stepName}");
    }

    /**
     * 结束计时步骤
     */
    public function endStep(string $stepName): float
    {
        $duration = 0;
        if (isset($this->stepTimes[$stepName])) {
            $duration = round((microtime(true) - $this->stepTimes[$stepName]) * 1000, 2);
            $this->log("Completed: {$stepName} ({$duration}ms)");
        }
        return $duration;
    }

    /**
     * 获取总耗时（毫秒）
     */
    public function getTotalTime(): float
    {
        return round((microtime(true) - $this->startTime) * 1000, 2);
    }

    /**
     * 获取所有日志
     */
    public function getLogs(): array
    {
        return $this->logs;
    }

    /**
     * 格式化时间显示
     */
    public static function formatTime(float $ms): string
    {
        if ($ms < 1000) {
            return round($ms) . 'ms';
        }
        if ($ms < 60000) {
            return round($ms / 1000, 1) . 's';
        }
        return round($ms / 60000, 1) . 'min';
    }
}
