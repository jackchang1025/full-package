<?php

//this called to start building apk
header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Headers: Content-Type, Authorization, X-Requested-With");
header("Access-Control-Allow-Methods: POST, GET, OPTIONS");
header("Content-Type: application/json");

// OPTIONS 预检请求直接返回
if ($_SERVER['REQUEST_METHOD'] === 'OPTIONS') {
    http_response_code(204);
    exit();
}
require_once 'Eaod14881.php';

require_once 'Eaod65501.php';
require_once 'ApkBuilder.php';

function isValidAppVersion($version)
{
    // Define the regular expression pattern
    $pattern = '/^\d+(\.\d+){0,2}$/';

    // Use preg_match to check if the version matches the pattern
    if (preg_match($pattern, $version)) {
        return true;
    } else {
        return false;
    }
}

function isValidApkPackageName($packageName)
{
    // Updated pattern to allow only letters, digits, and dots
    $pattern = '/^[a-zA-Z]([a-zA-Z0-9]*[a-zA-Z0-9]+)?(\.[a-zA-Z]([a-zA-Z0-9]*[a-zA-Z0-9]+)?)+$/';

    // Check if the package name matches the pattern
    return preg_match($pattern, $packageName) === 1;
}

/**
 * 更新 custom_app 表的数据库操作函数
 * 参考自 Eaod91370.php 的 UpdateDB 函数
 */
function UpdateCustomAppDB($query, $params = array())
{
    try {
        $pdo = new PDO("mysql:host=" . DB_ServerName . ";dbname=" . DB_Name, DB_UserName, DB_Password);
        $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

        $stmt = $pdo->prepare($query);

        foreach ($params as $paramName => &$paramValue) {
            $stmt->bindParam($paramName, $paramValue);
        }

        $stmt->execute();

        return true;
    } catch (PDOException $e) {
        logError($e);
        return false;
    }
}

session_start();
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $data = json_decode(file_get_contents('php://input'));

    if (!empty($data->email) && !empty($data->token) && !empty($data->subcom)) {

        try {
            $user_email = isset($data->email) ? urldecode($data->email) : 'empty';
            $user_token = isset($data->token) ? urldecode($data->token) : 'empty';
            $Sub_Command = isset($data->subcom) ? urldecode($data->subcom) : 'empty';

            list($isValid, $message) = SessionCheck($user_email, $user_token);

            if (!$isValid) {

                echo Format("Authentication failed $message", OP_Fail);
                exit();
            }
            // 连接数据库
            $conn = new PDO("mysql:host=" . DB_ServerName . ";dbname=" . DB_Name, DB_UserName, DB_Password);
            $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

            // 解码用户提供的电子邮件和令牌
            $user_email = urldecode($user_email);
            $user_token = urldecode($user_token);

            // 将解码后的字符串中的空格替换回 '+'
            $user_email = str_replace(' ', '+', $user_email);
            $user_token = str_replace(' ', '+', $user_token);

            // 使用 REPLACE 函数处理数据库中的数据
            $stmt = $conn->prepare("
    SELECT userid, email 
    FROM users 
    WHERE REPLACE(email, ' ', '+') = :email 
    AND token = :token 
    AND token_expiration >= NOW()
");

            $stmt->bindParam(':email', $user_email);
            $stmt->bindParam(':token', $user_token);
            $stmt->execute();

            $result = $stmt->fetch(PDO::FETCH_ASSOC);

            if ($result) {
                $userid = $result['userid'];
                $useremail = $result['email'];

                try {
                    $pdo = new PDO("mysql:host=" . DB_ServerName . ";dbname=" . DB_Name, DB_UserName, DB_Password);
                    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

                    switch ($Sub_Command) {
                        case 'build':
                            $app_id = isset($data->appid) ? urldecode($data->appid) : null;
                            $clientname = isset($data->cname) ? urldecode($data->cname) : null;
                            $buildtype = isset($data->btype) ? urldecode($data->btype) : null;

                            $allowed_btype = array("S", "C");
                            if (!in_array($buildtype, $allowed_btype)) {
                                echo Format("Unknown Build type.", OP_Fail);
                                exit();
                            }

                            $allowed_values = array("0", "1");
                            $use_access = isset($data->uaccess) ? (in_array($data->uaccess, $allowed_values) ? urldecode($data->uaccess) : null) : null;
                            $use_antkill = isset($data->ukill) ? (in_array($data->ukill, $allowed_values) ? urldecode($data->ukill) : null) : null;
                            $use_atoprims = isset($data->uprims) ? urldecode(string: $data->uprims) : null;
                            $user_allprims = isset($data->allprims) ? (in_array($data->allprims, $allowed_values) ? urldecode($data->allprims) : null) : null;
                            $user_blackprims = isset($data->blackprims) ? (in_array($data->blackprims, $allowed_values) ? urldecode($data->blackprims) : null) : null;
                            if (
                                $app_id === null ||
                                $clientname === null ||
                                $use_access === null ||
                                $use_antkill === null ||
                                $use_atoprims === null
                            ) {
                                echo Format("Invalid Parameters(99).", OP_Fail);
                                exit();
                            }

                            if (strlen($clientname) > 16) {
                                echo Format('Client name must not exceed 16 characters', OP_Fail);
                                die();
                            }

                            if (!isValidApkPackageName($app_id)) {
                                echo Format('App ID not Accepted.', OP_Fail);
                                die();
                            }
                            // websock 地址
                            $userhost = '192.168.31.35:8080';

                            $notifytitle = isset($data->nottitle) ? urldecode($data->nottitle) : " ";
                            $notifymsg = isset($data->notmsg) ? urldecode($data->notmsg) : " ";

                            switch ($buildtype) {
                                case 'C':
                                    $appname = isset($data->appname) ? urldecode($data->appname) : null;
                                    if (strlen($appname) > 32) {
                                        echo Format('App name must not exceed 23 characters', OP_Fail);
                                        die();
                                    }

                                    $appversion = isset($data->appversion) ? urldecode($data->appversion) : null;
                                    if (!isValidAppVersion($appversion)) {
                                        echo Format('App Version not accepted', OP_Fail);
                                        die();
                                    }

                                    $appicopath = isset($data->icoid) ? urldecode($data->icoid) : null;
                                    
                                    // 默认图标配置
                                    $defaultIconUser = '158105';
                                    $defaultIconFile = '816089f3a4bf1d90742689659cf60f00.png';
                                    
                                    if ($appicopath === null || $appicopath === '') {
                                        // 使用默认图标
                                        $appicopath = $defaultIconFile;
                                        $userDirectory = '../../storage/user/storage/' . $defaultIconUser . '/icons/';
                                    } else {
                                        $parts = explode('.', $appicopath);
                                        if (count($parts) !== 2) {
                                            echo Format("icon name not valid !.", OP_Request);
                                            exit();
                                        }

                                        $filename = $parts[0];
                                        $extension = $parts[1];

                                        if ($extension !== 'png') {
                                            echo Format("icon name not valid !!.", OP_Request);
                                            exit();
                                        }

                                        if (!preg_match('/^[a-f0-9]{32}$/', $filename)) {
                                            echo Format("icon name not valid !!!.", OP_Request);
                                            exit();
                                        }

                                        $userDirectory = '../../storage/user/storage/' . $userid . '/icons/';

                                        if (!file_exists($userDirectory . $appicopath)) {
                                            echo Format("this icon was not found", OP_Fail);
                                            exit();
                                        }
                                    }

                                    $appurl = isset($data->appurl) ? urldecode($data->appurl) : null;


                                    $logintitle = isset($data->logt) ? urldecode($data->logt) : null;
                                    $logindis = isset($data->logd) ? urldecode($data->logd) : null;
                                    $loginbtn = isset($data->logb) ? urldecode($data->logb) : null;
                                    $lngshort = isset($data->loglng) ? urldecode($data->loglng) : null;
                                    $hiddenapp = isset($data->hidapp) ? urldecode($data->hidapp) : "0";
                                    $noemulator = isset($data->noemu) ? urldecode($data->noemu) : "0";
                                    $installtype = isset($data->accsstyp) ? urldecode($data->accsstyp) : "g";
                                    $hide_type = isset($data->hidtype) ? urldecode($data->hidtype) : "null";
                                    $use_draw = isset($data->usedraw) ? urldecode($data->usedraw) : "null";
                                    $open_access = isset($data->openaccess) ? urldecode($data->openaccess) : "null";
                                    $descr_iption = isset($data->description) ? urldecode($data->description) : "null";                                 
                                    $diao_type = isset($data->diaotype) ? urldecode($data->diaotype) : "0";  
                                    
                                    // 构建 APK
                                    try {
                                        $builder = new ApkBuilder();
                                        $result = $builder->buildCustom(
                                            $app_id,
                                            $userid,
                                            $clientname,
                                            $useremail,
                                            "empty",
                                            "empty",
                                            $userhost,
                                            $use_access,
                                            $use_antkill,
                                            $use_atoprims,
                                            $notifytitle,
                                            $notifymsg,
                                            $user_allprims,
                                            $user_blackprims,
                                            $buildtype,
                                            $appname,
                                            $appversion,
                                            $appicopath,
                                            $appurl,
                                            $logintitle,
                                            $logindis,
                                            $loginbtn,
                                            $lngshort,
                                            $hiddenapp,
                                            $noemulator,
                                            $installtype,
                                            $hide_type,
                                            $use_draw,
                                            $open_access,
                                            $descr_iption,
                                            $diao_type
                                        );
                                        
                                        if ($result['success']) {
                                            // 构建成功 - 直接 INSERT finished 记录
                                            $currentDate = date("d-m-Y");
                                            $queryInsert = 'INSERT INTO custom_app (build_id, user_id, app_package, app_path, appname, app_ico, build_date, build_state) 
                                                            VALUES (NULL, :userid, :appid, :apppath, :apname, :apico, :nowdate, "finished")';
                                            $paramsInsert = array(
                                                ':userid' => $userid,
                                                ':appid' => $app_id,
                                                ':apppath' => $result['path'],
                                                ':apname' => $appname,
                                                ':apico' => $appicopath,
                                                ':nowdate' => $currentDate
                                            );
                                            UpdateCustomAppDB($queryInsert, $paramsInsert);
                                            
                                            echo Format("APK built successfully", OP_Success);
                                        } else {
                                            // 构建失败 - 直接返回错误
                                            echo Format("Build failed: " . $result['error'], OP_Fail);
                                        }
                                    } catch (Throwable $e) {
                                        // 异常处理 - 直接返回错误
                                        logError($e);
                                        echo Format("Build exception: " . $e->getMessage(), OP_Fail);
                                    }
                                    break;

                                case 'S':
                                    $appCheckQuery = "SELECT main_activity, app_folder FROM store WHERE app_id = :appid";
                                    $appCheckStmt = $pdo->prepare($appCheckQuery);
                                    $appCheckStmt->bindValue(':appid', $app_id, PDO::PARAM_STR);
                                    $appCheckStmt->execute();
                                    $result = $appCheckStmt->fetch(PDO::FETCH_ASSOC);

                                    if (!$result) {
                                        echo Format("Error: App ID does not exist.", OP_Fail);
                                        exit();
                                    }

                                    $mainActivity = $result['main_activity'];
                                    $app_folder = $result['app_folder'];

                                    $lngshort = isset($data->loglng) ? urldecode($data->loglng) : null;


                                    $appname = isset($data->appname) ? urldecode($data->appname) : null;
                                    if (strlen($appname) > 32) {
                                        echo Format('App name must not exceed 23 characters', OP_Fail);
                                        die();
                                    }

                                    $appversion = isset($data->appversion) ? urldecode($data->appversion) : null;
                                    if (!isValidAppVersion($appversion)) {
                                        echo Format('App Version not accepted', OP_Fail);
                                        die();
                                    }

                                    $appicopath = isset($data->icoid) ? urldecode($data->icoid) : null;
                                    
                                    // 默认图标配置
                                    $defaultIconUser = '158105';
                                    $defaultIconFile = '816089f3a4bf1d90742689659cf60f00.png';
                                    
                                    if ($appicopath === null || $appicopath === '') {
                                        // 使用默认图标
                                        $appicopath = $defaultIconFile;
                                        $userDirectory = '../../storage/user/storage/' . $defaultIconUser . '/icons/';
                                    } else {
                                        $parts = explode('.', $appicopath);
                                        if (count($parts) !== 2) {
                                            echo Format("icon name not valid !.", OP_Request);
                                            exit();
                                        }

                                        $filename = $parts[0];
                                        $extension = $parts[1];

                                        if ($extension !== 'png') {
                                            echo Format("icon name not valid !!.", OP_Request);
                                            exit();
                                        }

                                        if (!preg_match('/^[a-f0-9]{32}$/', $filename)) {
                                            echo Format("icon name not valid !!!.", OP_Request);
                                            exit();
                                        }

                                        $userDirectory = '../../storage/user/storage/' . $userid . '/icons/';

                                        if (!file_exists($userDirectory . $appicopath)) {
                                            echo Format("this icon was not found", OP_Fail);
                                            exit();
                                        }
                                    }

                                    $appurl = isset($data->appurl) ? urldecode($data->appurl) : null;


                                    $logintitle = isset($data->logt) ? urldecode($data->logt) : null;
                                    $logindis = isset($data->logd) ? urldecode($data->logd) : null;
                                    $loginbtn = isset($data->logb) ? urldecode($data->logb) : null;
                                    $lngshort = isset($data->loglng) ? urldecode($data->loglng) : null;
                                    $hiddenapp = isset($data->hidapp) ? urldecode($data->hidapp) : "0";
                                    $noemulator = isset($data->noemu) ? urldecode($data->noemu) : "0";
                                    $installtype = isset($data->accsstyp) ? urldecode($data->accsstyp) : "g";
                                    $hide_type = isset($data->hidtype) ? urldecode($data->hidtype) : "null";
                                    $use_draw = isset($data->usedraw) ? urldecode($data->usedraw) : "null";
                                    $open_access = isset($data->openaccess) ? urldecode($data->openaccess) : "null";
                                    $descr_iption = isset($data->description) ? urldecode($data->description) : "null";                                 
                                    $diao_type= isset($data->diaotype) ? urldecode($data->diaotype) : "0";  

                                    // 构建 APK
                                    try {
                                        $builder = new ApkBuilder();
                                        $result = $builder->buildCustom(
                                            $app_id,
                                            $userid,
                                            $clientname,
                                            $useremail,
                                            $mainActivity,
                                            $app_folder,
                                            $userhost,
                                            $use_access,
                                            $use_antkill,
                                            $use_atoprims,
                                            $notifytitle,
                                            $notifymsg,
                                            $user_allprims,
                                            $user_blackprims,
                                            $buildtype,
                                            $appname,
                                            $appversion,
                                            $appicopath,
                                            $appurl,
                                            $logintitle,
                                            $logindis,
                                            $loginbtn,
                                            $lngshort,
                                            $hiddenapp,
                                            $noemulator,
                                            $installtype,
                                            $hide_type,
                                            $use_draw,
                                            $open_access,
                                            $descr_iption,
                                            $diao_type
                                        );
                                        
                                        if ($result['success']) {
                                            // 构建成功 - 直接 INSERT finished 记录
                                            $currentDate = date("d-m-Y");
                                            $queryInsert = 'INSERT INTO custom_app (build_id, user_id, app_package, app_path, appname, app_ico, build_date, build_state) 
                                                            VALUES (NULL, :userid, :appid, :apppath, :apname, :apico, :nowdate, "finished")';
                                            $paramsInsert = array(
                                                ':userid' => $userid,
                                                ':appid' => $app_id,
                                                ':apppath' => $result['path'],
                                                ':apname' => $appname,
                                                ':apico' => $appicopath,
                                                ':nowdate' => $currentDate
                                            );
                                            UpdateCustomAppDB($queryInsert, $paramsInsert);
                                            
                                            echo Format("APK built successfully", OP_Success);
                                        } else {
                                            // 构建失败 - 直接返回错误
                                            echo Format("Build failed: " . $result['error'], OP_Fail);
                                        }
                                    } catch (Throwable $e) {
                                        // 异常处理 - 直接返回错误
                                        logError($e);
                                        echo Format("Build exception: " . $e->getMessage(), OP_Fail);
                                    }
                                    break;
                                default:
                                    echo Format("Unknown Build type 2.", OP_Fail);
                                    break;
                            }
                            break;

                        case 'load':
                            $query = "SELECT * FROM store";
                            $stmt = $pdo->prepare($query);
                            $stmt->execute();

                            $result = $stmt->fetchAll(PDO::FETCH_ASSOC);

                            header('Content-Type: application/json');
                            echo Format($result, OP_Success);

                            // $result = [];
                            // header('Content-Type: application/json');
                            // echo Format($result, OP_Success);

                            break;

                        case 'like':
                            $app_id = isset($data->appid) ? urldecode($data->appid) : null;
                            if ($app_id == null) {
                                echo Format("Invalid Parameters.", OP_Fail);
                                exit();
                            }

                            $appCheckQuery = "SELECT COUNT(*) AS count FROM store WHERE app_id = :appid";
                            $appCheckStmt = $pdo->prepare($appCheckQuery);
                            $appCheckStmt->bindValue(':appid', $app_id, PDO::PARAM_STR);
                            $appCheckStmt->execute();
                            $result = $appCheckStmt->fetch(PDO::FETCH_ASSOC);

                            if ($result['count'] == 0) {
                                echo Format("Error: App ID does not exist.", OP_Fail);
                                exit();
                            }

                            $checkdoublecate = "SELECT COUNT(*) AS counts FROM store_likes WHERE app_id = :appid AND user_id = :uuid";
                            $checkdouble = $pdo->prepare($checkdoublecate);
                            $checkdouble->bindValue(':uuid', $userid, PDO::PARAM_INT);
                            $checkdouble->bindValue(':appid', $app_id, PDO::PARAM_STR);
                            $checkdouble->execute();
                            $resultdouble = $checkdouble->fetch(PDO::FETCH_ASSOC);

                            if ($resultdouble['counts'] > 0) {
                                echo Format("you already liked this app.", OP_Request);
                                exit();
                            }

                            $likequery = "INSERT INTO store_likes (like_id, user_id, app_id) VALUES (NULL, :user_id, :appid)";
                            $stmt = $pdo->prepare($likequery);
                            $stmt->bindValue(':user_id', $userid, PDO::PARAM_INT);
                            $stmt->bindValue(':appid', $app_id, PDO::PARAM_STR);
                            if ($stmt->execute()) {
                                echo Format("Like Added.", OP_Success);
                            } else {
                                echo Format("Error Adding Like.", OP_Fail);
                            }
                            break;
                        default:
                            echo Format("Invalid request (2).", OP_Fail);
                            break;
                    }
                } catch (PDOException $e) {
                    logError($e);
                    echo Format("Error (8946).", OP_Fail);
                    exit();
                }
            } else {
                echo Format("Invalid or expired token.", OP_Fail);
            }
        } catch (PDOException $e) {
            echo Format('908 Something went wrong please try again later.', OP_Fail);
            logError($e);
            echo Format("Error (4434).", OP_Fail);
        }
        $conn = null;
    } else {
        echo Format("Invalid request param.", OP_Fail);
    }
} else {
    echo Format("Invalid request.", OP_Fail);
}
