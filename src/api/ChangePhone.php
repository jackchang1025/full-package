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
    // 获取请求中的 usrname
    $data = json_decode(file_get_contents('php://input'));

    // 打印请求数据，便于调试
    error_log(print_r($data, true));

    // 确保传递了 usrname
    if (!isset($data->usrname) || empty($data->usrname)) {
        http_response_code(400);
        echo json_encode(['status' => 'fail', 'message' => 'usrname is required']);
        exit();
    }

    $usrname = $data->usrname;

    try {
        // 连接数据库
        $pdo = new PDO("mysql:host=" . DB_ServerName . ";dbname=" . DB_Name, DB_UserName, DB_Password);
        $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

        // 查询 users 表中是否有匹配的 usrname
        $stmt = $pdo->prepare("SELECT email FROM users WHERE usrname = :usrname");
        $stmt->bindParam(':usrname', $usrname, PDO::PARAM_STR);
        $stmt->execute();

        // 获取查询结果
        $user = $stmt->fetch(PDO::FETCH_ASSOC);

        // 如果找到了用户，返回 email
        if ($user) {
            echo json_encode(['status' => 'success', 'email' => $user['email']]);
        } else {
            // 如果没有找到用户
            http_response_code(404);
            echo json_encode(['status' => 'fail', 'message' => 'User not found']);
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
?>
