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

require_once '../private/Eaod85401.php';

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    try {
        $data = json_decode(file_get_contents('php://input'));
        $username = $data->usrname ?? '';
        $password = $data->password ?? '';

        if (empty($username) || empty($password)) {
            http_response_code(400);
            echo json_encode(['error' => 'Username and password are required']);
            exit();
        }

        $pdo = new PDO("mysql:host=" . DB_ServerName . ";dbname=" . DB_Name, DB_UserName, DB_Password);
        $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

        $stmt = $pdo->prepare("SELECT * FROM users WHERE usrname = :usrname");
        $stmt->bindParam(':usrname', $username, PDO::PARAM_STR);
        $stmt->execute();
        $user = $stmt->fetch(PDO::FETCH_ASSOC);

        if ($user) {
            if (password_verify($password, $user['password'])) {
                // 登录成功，生成新 token
                $token = bin2hex(random_bytes(16));
                $expiration = date('Y-m-d H:i:s', strtotime('+7 day'));

                // 更新数据库
                $updateStmt = $pdo->prepare("UPDATE users SET token = :token, token_expiration = :expiration WHERE usrname = :usrname");
                $updateStmt->bindParam(':token', $token);
                $updateStmt->bindParam(':expiration', $expiration);
                $updateStmt->bindParam(':usrname', $username);
                $updateStmt->execute();

                // 返回基本信息+token
                echo json_encode([
                    'code' => 200,
                    'msg' => '登录成功',
                    'usrname' => $user['usrname'],
                    'userid' => $user['userid'],
                    'email' => $user['email'],
                    'token' => $token,
                    'authorty' => $user['authorty'],
                ]);
            } else {
                http_response_code(401);
                echo json_encode(['error' => 'Invalid credentials']);
            }
        } else {
            http_response_code(404);
            echo json_encode(['error' => 'User not found']);
        }
    } catch (PDOException $e) {
        http_response_code(500);
        echo json_encode(['error' => 'Server busy']);
        error_log($e->getMessage());
    }
} else {
    http_response_code(405);
    echo json_encode(['error' => 'Invalid request method.']);
}
