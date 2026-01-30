<?php

date_default_timezone_set('Asia/Shanghai');
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json");

// 获取设备名和日志内容
$deviceName = isset($_POST['devicename']) ? preg_replace('/[^a-zA-Z0-9_\-]/', '_', $_POST['devicename']) : 'unknown_device';
$log = isset($_POST['log']) ? $_POST['log'] : '';

if (!$log) {
    http_response_code(400);
    echo json_encode(['error' => 'Missing log data']);
    exit;
}

$dir = __DIR__ . '/error_logs/' . $deviceName;
if (!file_exists($dir)) {
    mkdir($dir, 0777, true);
}

$filename = $dir . '/' . date('Y-m-d_H-i-s') . '.txt';
file_put_contents($filename, $log);

echo json_encode(['success' => true, 'saved_as' => $filename]);
