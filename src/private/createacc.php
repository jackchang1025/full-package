<?php
// Start session and check admin login status
session_start();
if (!isset($_SESSION['admin_logged_in']) || $_SESSION['admin_logged_in'] !== true) {
    echo json_encode(['Fail' => '未授权访问，请先登录']);
    exit();
}

require_once __DIR__ . '/Eaod28491.php';
require_once __DIR__ . '/Eaod45021.php';
require_once __DIR__ . '/Eaod57561.php';
require_once __DIR__ . '/Eaod41561.php';
require_once __DIR__ . '/Eaod29251.php';
require_once __DIR__ . '/Eaod84941.php';
require_once __DIR__ . '/Eaod30651.php';
require_once __DIR__ . '/Eaod40971.php';
require_once __DIR__ . '/Eaod26821.php';
require_once __DIR__ . '/Eaod85401.php';

function isUserIDTaken($userID)
{
    try {
        $pdo = new PDO("mysql:host=" . DB_ServerName . ";dbname=" . DB_Name, DB_UserName, DB_Password);
        $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

        $stmt = $pdo->prepare('SELECT COUNT(*) AS count FROM users WHERE userid = :uuid');
        $stmt->bindParam(':uuid', $userID);
        $stmt->execute();

        $result = $stmt->fetch(PDO::FETCH_ASSOC);
        return $result['count'] > 0;
    } catch (PDOException $e) {
        logError($e);
        echo Format("服务器错误，请稍后再试 (21)", OP_Fail);
        die();
    }
}

// Create user folder
function Creatuserfolder($UserID)
{
    $baseDirectory = '../user/storage/';
    $AppsDirectory = '../user/apps/';
    $assetsImagePath = '../assets/imgs/useracc.png';
    $userwalldir = $baseDirectory . $UserID . '/wall';

    if (!is_dir($userwalldir)) {
        mkdir($userwalldir, 0777, true);
        logdebug('Filesystem', 'User wall directory created: ' . $userwalldir);
    }

    $destinationImage = $userwalldir . '/Prof.png';
    if (file_exists($assetsImagePath)) {
        copy($assetsImagePath, $destinationImage);
    }

    $userDirectory = $baseDirectory . $UserID . '/';
    $userAppsDirectory = $AppsDirectory . $UserID . '/';
    $userIconDir = $baseDirectory . $UserID . '/icons/';

    if (!is_dir($userDirectory)) mkdir($userDirectory, 0777, true);
    if (!is_dir($userAppsDirectory)) mkdir($userAppsDirectory, 0777, true);
    if (!is_dir($userIconDir)) mkdir($userIconDir, 0777, true);

    file_put_contents($userDirectory . '.htaccess', "Order Deny,Allow\nDeny from all\n");
    file_put_contents($userAppsDirectory . '.htaccess', "Order Deny,Allow\nDeny from all\n");
}

// Validate password
function validatePassword($password)
{
    return true; // Can customize password validation as needed
}

header('Content-Type: application/json');

try {
    $conn = new PDO("mysql:host=" . DB_ServerName . ";dbname=" . DB_Name, DB_UserName, DB_Password);
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    $data = json_decode(file_get_contents('php://input'), true);
    $action = $data['action'] ?? (isset($_GET['action']) ? $_GET['action'] : '');

    if ($action == 'add') {
        $username = $data['username'] ?? '';
        $email = EN($data['email'] ?? '');
        $password = $data['password'] ?? '';
        $expire_date = $data['expire_date'] ?? '';
        $authority = $data['authority'] ?? 'user';
        $unlimited = $data['unlimited'] ?? false;

        $maxAccounts = 6;

        // 如果不是不受限制的添加，检查账号数量
        if (!$unlimited) {
            $countStmt = $conn->prepare("SELECT COUNT(*) FROM users");
            $countStmt->execute();
            $currentAccountCount = $countStmt->fetchColumn();

            if ($currentAccountCount >= $maxAccounts) {
                echo json_encode(['Fail' => "账号数量已达上限（{$maxAccounts}个），无法继续添加"]);
                exit();
            }
        }

        if (empty($username) || empty($email) || empty($password) || empty($expire_date)) {
            echo json_encode(['Fail' => '请填写所有必填字段']);
            exit();
        }

        // Validate password
        $result2 = validatePassword($password);
        if ($result2 !== true) {
            echo json_encode(['Fail' => $result2]);
            exit();
        }

        $pass = password_hash($password, PASSWORD_DEFAULT);
        $profilePic = 'Prof.png';
        $subtype = '12 Month';

        // 生成唯一用户ID
        do {
            $newid = substr("" . uniqid(rand()), 0, 6);
        } while (isUserIDTaken($newid));

        // 插入用户数据
        $stmt = $conn->prepare("INSERT INTO users (userid, usrname, profilepic, email, password, Expire, subtype, authorty) 
                                VALUES (:userid, :usrname, :profilepic, :email, :password, :expire, :subtype, :authority)");
        $stmt->bindParam(':userid', $newid);
        $stmt->bindParam(':usrname', $username);
        $stmt->bindParam(':profilepic', $profilePic);
        $stmt->bindParam(':email', $email);
        $stmt->bindParam(':password', $pass);
        $stmt->bindParam(':expire', $expire_date);
        $stmt->bindParam(':subtype', $subtype);
        $stmt->bindParam(':authority', $authority);

        if ($stmt->execute()) {
            // Create user folder
            Creatuserfolder($newid);
            echo json_encode(['Success' => '账号创建成功']);
        } else {
            echo json_encode(['Fail' => '账号创建失败']);
        }
    } elseif ($action == 'list') {
        $searchTerm = isset($_GET['search']) ? $_GET['search'] : '';
        
        // 检查hidden字段是否存在
        $stmtCheck = $conn->prepare("SHOW COLUMNS FROM users LIKE 'hidden'");
        $stmtCheck->execute();
        $hasHiddenField = $stmtCheck->rowCount() > 0;
        
        // Build query for fetching records
        if (!empty($searchTerm)) {
            if ($hasHiddenField) {
                $stmt = $conn->prepare("SELECT userid, usrname, email, password, Expire, authorty, hidden FROM users WHERE usrname LIKE CONCAT('%', :searchTerm, '%') OR email LIKE CONCAT('%', :searchTermEncrypted, '%')");
                $stmt->bindParam(':searchTerm', $searchTerm);
                $stmt->bindParam(':searchTermEncrypted', $searchTermEncrypted);
            } else {
                $stmt = $conn->prepare("SELECT userid, usrname, email, password, Expire, authorty FROM users WHERE usrname LIKE CONCAT('%', :searchTerm, '%') OR email LIKE CONCAT('%', :searchTermEncrypted, '%')");
                $stmt->bindParam(':searchTerm', $searchTerm);
                $stmt->bindParam(':searchTermEncrypted', $searchTermEncrypted);
            }
        } else {
            if ($hasHiddenField) {
                $stmt = $conn->prepare("SELECT userid, usrname, email, password, Expire, authorty, hidden FROM users");
            } else {
                $stmt = $conn->prepare("SELECT userid, usrname, email, password, Expire, authorty FROM users");
            }
        }
        
        $stmt->execute();
        $users = $stmt->fetchAll(PDO::FETCH_ASSOC);
        
        // Decrypt email and password for display
        foreach ($users as &$user) {
            $user['email'] = DE($user['email']);
            // Decrypt password for display
            $user['password'] = DE($user['password']);
            // 如果没有hidden字段，设置为0
            if (!$hasHiddenField) {
                $user['hidden'] = 0;
            }
        }
        
        echo json_encode(['Success' => true, 'accounts' => $users]);
    } elseif ($action == 'toggle_hide') {
        $userid = $data['userid'] ?? '';

        if (empty($userid)) {
            echo json_encode(['Fail' => '请提供用户ID']);
            exit();
        }

        // 检查hidden字段是否存在
        $stmtCheck = $conn->prepare("SHOW COLUMNS FROM users LIKE 'hidden'");
        $stmtCheck->execute();
        $hasHiddenField = $stmtCheck->rowCount() > 0;

        // 如果字段不存在，先添加
        if (!$hasHiddenField) {
            try {
                $alterStmt = $conn->prepare("ALTER TABLE users ADD COLUMN hidden TINYINT(1) DEFAULT 0");
                $alterStmt->execute();
            } catch (PDOException $e) {
                echo json_encode(['Fail' => '添加hidden字段失败: ' . $e->getMessage()]);
                exit();
            }
        }

        // 切换隐藏状态
        $sql = "UPDATE users SET hidden = NOT hidden WHERE userid = :userid";
        $stmt = $conn->prepare($sql);
        $stmt->bindParam(':userid', $userid);

        if ($stmt->execute()) {
            echo json_encode(['Success' => '账号隐藏状态已更新']);
        } else {
            echo json_encode(['Fail' => '账号隐藏状态更新失败']);
        }
    } elseif ($action == 'update_remark') {
        $userid = $data['userid'] ?? '';
        $remark = $data['remark'] ?? '';

        if (empty($userid)) {
            echo json_encode(['Fail' => '请提供用户ID']);
            exit();
        }

        // 使用JSON文件保存备注
        $remarkFile = __DIR__ . '/remarks.json';
        
        // 读取现有备注
        $remarks = [];
        if (file_exists($remarkFile)) {
            $jsonContent = file_get_contents($remarkFile);
            $remarks = json_decode($jsonContent, true) ?: [];
        }
        
        // 更新备注
        if ($remark === '') {
            unset($remarks[$userid]);
        } else {
            $remarks[$userid] = $remark;
        }
        
        // 保存到文件
        $jsonContent = json_encode($remarks, JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
        
        // 检查文件是否可写
        if (file_exists($remarkFile) && !is_writable($remarkFile)) {
            echo json_encode(['Fail' => '备注文件不可写']);
            exit();
        }
        
        // 检查目录是否可写
        if (!is_writable(dirname($remarkFile))) {
            echo json_encode(['Fail' => '备注目录不可写']);
            exit();
        }
        
        $result = file_put_contents($remarkFile, $jsonContent);
        
        if ($result !== false) {
            echo json_encode(['Success' => '备注更新成功']);
        } else {
            echo json_encode(['Fail' => '备注更新失败，文件写入错误']);
        }
    } elseif ($action == 'get_remarks') {
        // 获取所有备注
        $remarkFile = __DIR__ . '/remarks.json';
        
        $remarks = [];
        if (file_exists($remarkFile)) {
            $jsonContent = file_get_contents($remarkFile);
            $remarks = json_decode($jsonContent, true) ?: [];
        }
        
        echo json_encode(['Success' => true, 'remarks' => $remarks]);
    } elseif ($action == 'remove_email_unique_index') {
        // 删除email字段的唯一索引
        try {
            $stmt = $conn->prepare("ALTER TABLE users DROP INDEX email");
            if ($stmt->execute()) {
                echo json_encode(['Success' => 'email唯一索引删除成功']);
            } else {
                echo json_encode(['Fail' => 'email唯一索引删除失败']);
            }
        } catch (PDOException $e) {
            // 如果索引不存在，也视为成功
            if (strpos($e->getMessage(), "Can't DROP") !== false) {
                echo json_encode(['Success' => 'email唯一索引不存在']);
            } else {
                echo json_encode(['Fail' => $e->getMessage()]);
            }
        }
    } elseif ($action == 'renew') {
        $userid = $data['userid'] ?? '';
        $expire_date = $data['expire_date'] ?? '';

        if (empty($userid) || empty($expire_date)) {
            echo json_encode(['Fail' => '请提供用户ID和新的到期日期']);
            exit();
        }

        // Update expire date
        $stmt = $conn->prepare("UPDATE users SET Expire = :expire_date WHERE userid = :userid");
        $stmt->bindParam(':userid', $userid);
        $stmt->bindParam(':expire_date', $expire_date);

        if ($stmt->execute()) {
            echo json_encode(['Success' => '账号续费成功']);
        } else {
            echo json_encode(['Fail' => '账号续费失败']);
        }
    } elseif ($action == 'update_password') {
        $userid = $data['userid'] ?? '';
        $password = $data['password'] ?? '';

        if (empty($userid) || empty($password)) {
            echo json_encode(['Fail' => '请提供用户ID和新密码']);
            exit();
        }

        // Validate password
        $result2 = validatePassword($password);
        if ($result2 !== true) {
            echo json_encode(['Fail' => $result2]);
            exit();
        }

        $newPass = password_hash($password, PASSWORD_DEFAULT);

        // 更新密码
        $sql = "UPDATE users SET password = :password WHERE userid = :userid";
        $stmt = $conn->prepare($sql);
        $stmt->bindParam(':userid', $userid);
        $stmt->bindParam(':password', $newPass);

        if ($stmt->execute()) {
            echo json_encode(['Success' => '密码更新成功']);
        } else {
            echo json_encode(['Fail' => '密码更新失败']);
        }
    } elseif ($action == 'update_email') {
        $userid = $data['userid'] ?? '';
        $email = $data['email'] ?? '';

        if (empty($userid) || empty($email)) {
            echo json_encode(['Fail' => '请提供用户ID和新邮箱']);
            exit();
        }

        // Encrypt email
        $encryptedEmail = EN($email);

        // 更新邮箱
        $sql = "UPDATE users SET email = :email WHERE userid = :userid";
        $stmt = $conn->prepare($sql);
        $stmt->bindParam(':userid', $userid);
        $stmt->bindParam(':email', $encryptedEmail);

        if ($stmt->execute()) {
            echo json_encode(['Success' => '邮箱更新成功']);
        } else {
            echo json_encode(['Fail' => '邮箱更新失败']);
        }
    } elseif ($action == 'delete') {
        $userid = $data['userid'] ?? '';

        if (empty($userid)) {
            echo json_encode(['Fail' => '请提供用户ID']);
            exit();
        }

        // 先删除custom_app表中与该用户相关的记录，以解决外键约束问题
        $sql = "DELETE FROM custom_app WHERE user_id = :userid";
        $stmt = $conn->prepare($sql);
        $stmt->bindParam(':userid', $userid);
        $stmt->execute();

        // 然后删除用户记录
        $sql = "DELETE FROM users WHERE userid = :userid";
        $stmt = $conn->prepare($sql);
        $stmt->bindParam(':userid', $userid);

        if ($stmt->execute()) {
            echo json_encode(['Success' => '账号删除成功']);
        } else {
            echo json_encode(['Fail' => '账号删除失败']);
        }
    } else {
        echo json_encode(['Fail' => '无效操作']);
    }
} catch (PDOException $e) {
    echo json_encode(['Fail' => "数据库错误: " . $e->getMessage()]);
}
