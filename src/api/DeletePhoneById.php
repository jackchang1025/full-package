<?php
date_default_timezone_set('Asia/Shanghai');
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

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

require_once '../private/Eaod85401.php';

// 设置返回 JSON 格式
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    // 获取请求中的 phone_id
    $data = json_decode(file_get_contents('php://input'));

    // 确保传递了 phone_id
    if (!isset($data->phone_id) || empty($data->phone_id)) {
        http_response_code(400);
        echo json_encode(['status' => 'fail', 'message' => 'phone_id is required']);
        exit();
    }

    $phone_id = $data->phone_id;

    try {
        // 连接数据库
        $pdo = new PDO("mysql:host=" . DB_ServerName . ";dbname=" . DB_Name, DB_UserName, DB_Password);
        $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

        // 删除 phones 表中对应的 phone_id
        $stmt = $pdo->prepare("DELETE FROM phones WHERE phone_id = :phone_id");
        $stmt->bindParam(':phone_id', $phone_id, PDO::PARAM_STR);
        $stmt->execute();

        // 检查是否删除了数据
        if ($stmt->rowCount() > 0) {
            echo json_encode(['status' => 'success', 'message' => 'Device deleted successfully']);
        } else {
            // 如果没有找到设备，返回 404
            http_response_code(404);
            echo json_encode(['status' => 'fail', 'message' => 'Device not found']);
        }
    } catch (PDOException $e) {
        // 捕获数据库错误
        http_response_code(500);
        echo json_encode(['status' => 'fail', 'message' => 'Database error', 'error' => $e->getMessage()]);
    }
} else {
    // 如果请求方法不是 POST
    http_response_code(405);
    echo json_encode(['status' => 'fail', 'message' => 'Invalid request method']);
}
