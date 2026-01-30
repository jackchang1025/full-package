<?php
date_default_timezone_set('Asia/Shanghai');

// 设置 CORS 头信息
header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Headers: Content-Type, Authorization");
header("Access-Control-Allow-Methods: POST, GET, OPTIONS");
header("Content-Type: application/json");

// 处理 OPTIONS 请求
if ($_SERVER['REQUEST_METHOD'] === 'OPTIONS') {
    http_response_code(204);
    exit();
}

// 引入数据库配置文件（确保其中变量以 $ 开头）
require_once '../private/Eaod85401.php';

// 连接数据库
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    try {
        $data = json_decode(file_get_contents('php://input'));

        // 检查 JSON 是否解析成功
        if (json_last_error() !== JSON_ERROR_NONE) {
            http_response_code(400);
            echo json_encode(['error' => 'Invalid JSON format']);
            exit();
        }

        // 获取设备ID和设备状态
        $phone_id = $data->phone_id ?? '';   // 设备ID
        $phoneopen = $data->phoneopen ?? 0;  // 设备启用/禁用状态

        // 验证输入
        if (empty($phone_id)) {
            http_response_code(400);
            echo json_encode(['error' => 'Phone ID is required']);
            exit();
        }

        // 连接数据库
        $pdo = new PDO("mysql:host=" . DB_ServerName . ";dbname=" . DB_Name, DB_UserName, DB_Password);
        $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

        // 更新 phones 表中的设备状态
        $stmt_update_phones = $pdo->prepare("
            UPDATE phones SET phoneopen = :phoneopen WHERE phone_id = :phone_id
        ");
        $stmt_update_phones->bindParam(':phoneopen', $phoneopen, PDO::PARAM_INT);
        $stmt_update_phones->bindParam(':phone_id', $phone_id, PDO::PARAM_STR);
        $stmt_update_phones->execute();

        // 返回设备状态更新成功信息
        echo json_encode(['message' => 'Device status updated successfully']);
    } catch (PDOException $e) {
        http_response_code(500);
        echo json_encode(['error' => 'Server busy']);
        error_log($e->getMessage());
    }
} else {
    http_response_code(405);
    echo json_encode(['error' => 'Invalid request method.']);
}
