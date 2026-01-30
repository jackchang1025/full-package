<?php

//this called from panel settings page
//so user can change his profile , like avatar,name,...
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

session_start();




if ($_SERVER['REQUEST_METHOD'] === 'POST') {

    $email = $_POST['email']  ?? die();
    $token = $_POST['token'] ?? die();
    $commadtype = $_POST['type'] ?? die();


    list($isValid, $message) = SessionCheck($email, $token);

    if (!$isValid) {
        echo Format("Authentication failed $message", OP_Fail);
        die();
    }


    $checkcommand = array("img", "name", "ico", "listico", "listui", "remico", "ui", "remui","listmp3");


    if (!in_array($commadtype, $checkcommand)) {
        # code...
        echo Format("Unkown command", OP_Fail);
        die();
    }



    $userId = validateAndGetUserId($email, $token);

    if ($userId !== null) {


        switch ($commadtype) {
            case 'remico':

                $userDirectory = '../../storage/user/storage/' . $userId . '/icons/';

                $iconame = $_POST['iconame'] ?? die(); //fc96830f57303e2881e1d62af825f3dd.png


                $parts = explode('.', $iconame);

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

                // Check if the filename is a valid MD5 hash
                if (!preg_match('/^[a-f0-9]{32}$/', $filename)) {
                    echo Format("icon name not valid !!!.", OP_Request);
                    exit();
                }

                if (!file_exists($userDirectory . $iconame)) {
                    echo Format("this icon was not found", OP_Fail);
                    exit();
                }

                if (unlink($userDirectory . $iconame)) {
                    echo Format("icon removed successfully", OP_Success);
                } else {
                    echo Format("something went wrong , please try again later 281", OP_Fail);
                    exit();
                }

                break;

            case 'listico':


                $userDirectory = '../../storage/user/storage/' . $userId . '/icons/';

                $pngFiles = glob($userDirectory . '*.png');

                if ($pngFiles !== false) {

                    function prependPath($file)
                    {
                        global $userId;
                        return $userId . '/icons/' . basename($file);
                    }

                    $fileNames =  array_map('prependPath', $pngFiles);

                    //here for eachname , make it like this ($userId . '/icons/' . filename form array)

                    $allFileNames = implode(', ', $fileNames);

                    echo Format($allFileNames, OP_Success);
                } else {
                    echo Format("no icons found.", OP_Request);
                    exit();
                }

                break;

            case 'listui':

                $userDirectory = '../../storage/user/ui/';

                $pngFiles = glob($userDirectory . '*.png');

                if ($pngFiles !== false) {

                    function prependPath($file)
                    {
                        return '/ui/' . basename($file);
                    }

                    $fileNames =  array_map('prependPath', $pngFiles);

                    //here for eachname , make it like this ($userId . '/icons/' . filename form array)

                    $allFileNames = implode(', ', $fileNames);

                    echo Format($allFileNames, OP_Success);
                } else {
                    echo Format("no icons found.", OP_Request);
                    exit();
                }


                break;

            case 'ico':
                $maxFileSize = 5242880;
                $allowedTypes = ['image/png'];

                $file = $_FILES['file'];


                if ($file['size'] <= $maxFileSize) {

                    if (in_array($file['type'], $allowedTypes)) {

                        $userDirectory = '../../storage/user/storage/' . $userId . '/icons/';


                        if (!is_dir($userDirectory)) {
                            mkdir($userDirectory, 0755, true);
                        }


                        $pngFiles = glob($userDirectory . '*.png');
                        $pngCount = count($pngFiles);

                        if ($pngCount >= 10) {
                            echo Format('The maximum number of icons allowed is 10. Please remove an existing icon before uploading a new one.', OP_Fail);
                            return;
                        }

                        $allowedSignatures = [
                            '89504E47'
                        ];

                        $fileContents = file_get_contents($file['tmp_name']);
                        $fileSignature = bin2hex(substr($fileContents, 0, 4));

                        if (!in_array($fileSignature, $allowedSignatures)) {
                            logdebug("changeprofile,Signature", $fileSignature . ":" . $file['type']);
                            echo Format('Please upload a valid image file.', OP_Fail);
                            exit;
                        }

                        $md5Hash = md5($fileContents);
                        $originalFileName = $md5Hash . ".png";

                        if (file_exists($userDirectory . $originalFileName)) {
                            echo Format("you already have this icon", OP_Fail);
                            die();
                        }



                        move_uploaded_file($file['tmp_name'], $userDirectory . $originalFileName);


                        echo Format("ok", OP_Success);
                    } else {
                        logdebug("changeprofile,type", $file['type']);

                        echo Format('Invalid file type. Please only upload a PNG image.', OP_Fail);
                    }
                } else {

                    echo Format('File size exceeds the maximum allowed size of 5 MB.', OP_Fail);
                }
                break;
            case 'img':


                $maxFileSize = 5242880;
                $allowedTypes = ['image/jpeg', 'image/png', 'image/jpg'];

                $file = $_FILES['file'];

                // Check file size
                if ($file['size'] > $maxFileSize) {
                    logdebug("changeprofile,size", (string) $file['size']);
                    echo Format('File size exceeds the maximum allowed size of 5 MB.', OP_Fail);
                    return;
                }

                // Check MIME type
                if (!in_array($file['type'], $allowedTypes)) {
                    logdebug("changeprofile,type", $file['type']);
                    echo Format('Invalid file type. Please upload a PNG or JPG image.', OP_Fail);
                    return;
                }

                $userDirectory2 = '../../storage/user/storage/' . $userId . '/wall/';

                // Create directory if it does not exist
                if (!is_dir($userDirectory2) && !mkdir($userDirectory2, 0755, true)) {
                    logdebug("changeprofile,dir", "Failed to create directory: $userDirectory2");
                    echo Format('Failed to create directory for upload.', OP_Fail);
                    return;
                }

                $originalFileName = "Prof.png";

                $allowedSignatures = [
                    '89504E47', // PNG
                    'ffd8ffe0', // JPG
                    'ffd8ffed'  // JPG
                ];

                // Read file signature
                $fileContents = file_get_contents($file['tmp_name']);
                $fileSignature = bin2hex(substr($fileContents, 0, 4));

                // Check file signature
                if (!in_array($fileSignature, $allowedSignatures)) {
                    logdebug("changeprofile,Signature", $fileSignature . ":" . $file['type']);
                    echo Format('Invalid file content. Please upload a valid image file.', OP_Fail);
                    return;
                }

                // Move uploaded file
                if (!move_uploaded_file($file['tmp_name'], $userDirectory2 . $originalFileName)) {
                    logdebug("changeprofile,move", "Failed to move uploaded file to $userDirectory2");
                    echo Format('Failed to upload file.', OP_Fail);
                    return;
                }

                // Update profile picture
                if (!updateProfilePic($userId, $originalFileName)) {
                    logdebug("changeprofile,update", "Failed to update profile picture.");
                    echo Format('Failed to update profile picture.', OP_Fail);
                    return;
                }

                echo Format("ok", OP_Success);

                break;
            case 'name':


                $newname = $_POST['data'] ?? die();
                $result = validateUsername($newname);
                if ($result !== true) {
                    echo Format($result, OP_Fail);
                    exit();
                }

                if (UpdateUserName($userId, $token, $newname)) {
                    echo Format("ok", OP_Success);
                }

                break;
            case 'ui':
                $maxFileSize = 5242880;
                $allowedTypes = ['image/png'];

                $file = $_FILES['file'];

                if ($file['size'] <= $maxFileSize) {
                    if (in_array($file['type'], $allowedTypes)) {
                        $userDirectory = '../../storage/user/ui/';

                        if (!is_dir($userDirectory)) {
                            mkdir($userDirectory, 0755, true);
                        }

                        $allowedSignatures = [
                            '89504E47' // PNG
                        ];

                        $fileContents = file_get_contents($file['tmp_name']);
                        $fileSignature = bin2hex(substr($fileContents, 0, 4));

                        if (!in_array($fileSignature, $allowedSignatures)) {
                            logdebug("changeprofile,Signature", $fileSignature . ":" . $file['type']);
                            echo Format('Please upload a valid PNG image.', OP_Fail);
                            exit;
                        }

                        // 使用 md5 生成文件名，避免重复
                        $md5Hash = md5($fileContents);
                        $originalFileName = $md5Hash . ".png";

                        if (file_exists($userDirectory . $originalFileName)) {
                            echo Format("this ui already exists", OP_Fail);
                            die();
                        }

                        if (move_uploaded_file($file['tmp_name'], $userDirectory . $originalFileName)) {
                            echo Format("ok", OP_Success);
                        } else {
                            echo Format("something went wrong when saving ui", OP_Fail);
                            exit();
                        }
                    } else {
                        logdebug("changeprofile,type", $file['type']);
                        echo Format('Invalid file type. Please only upload a PNG image.', OP_Fail);
                    }
                } else {
                    echo Format('File size exceeds the maximum allowed size of 5 MB.', OP_Fail);
                }
                break;
            case 'remui':
                $userDirectory = '../../storage/user/ui/';
                $uiname = $_POST['uiname'] ?? die(); // 例如 fc96830f57303e2881e1d62af825f3dd.png

                $parts = explode('.', $uiname);

                if (count($parts) !== 2) {
                    echo Format("ui name not valid !.", OP_Request);
                    exit();
                }

                $filename = $parts[0];
                $extension = $parts[1];

                if ($extension !== 'png') {
                    echo Format("ui name not valid !!.", OP_Request);
                    exit();
                }

                // 检查是否为有效的 md5 文件名
                if (!preg_match('/^[a-f0-9]{32}$/', $filename)) {
                    echo Format("ui name not valid !!!.", OP_Request);
                    exit();
                }

                if (!file_exists($userDirectory . $uiname)) {
                    echo Format("this ui was not found", OP_Fail);
                    exit();
                }

                if (unlink($userDirectory . $uiname)) {
                    echo Format("ui removed successfully", OP_Success);
                } else {
                    echo Format("something went wrong , please try again later 482", OP_Fail);
                    exit();
                }
                break;
            case 'listmp3':
                $userDirectory = '../../storage/user/mp3/';

                $mp3Files = glob($userDirectory . '*.mp3');

                if ($mp3Files !== false && count($mp3Files) > 0) {
                    function prependPath($file)
                    {
                        return '/mp3/' . basename($file); // 返回相对路径
                    }

                    $fileNames = array_map('prependPath', $mp3Files);

                    // 打乱顺序
                    shuffle($fileNames);

                    // 推荐返回 JSON 数组，前端更好处理
                    echo json_encode([
                        "Success" => $fileNames
                    ]);
                } else {
                    echo json_encode([
                        "Fail" => "no mp3 found."
                    ]);
                    exit();
                }
                break;


            default:
                echo Format("Unkown command", OP_Fail);
                die();
        }
    } else {

        echo Format('Invalid credentials.', OP_Fail);
    }
} else {

    echo Format('Invalid request.', OP_Fail);
}


function validateAndGetUserId($email, $token)
{


    try {

        $pdo = new PDO("mysql:host=" . DB_ServerName . ";dbname=" . DB_Name, DB_UserName, DB_Password);


        $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);


        $stmt = $pdo->prepare('SELECT userid FROM users WHERE email = :email AND token = :token');
        $stmt->bindParam(':email', $email);
        $stmt->bindParam(':token', $token);
        $stmt->execute();


        $userId = $stmt->fetchColumn();


        $pdo = null;


        return ($userId !== false) ? $userId : null;
    } catch (PDOException $e) {

        logError($e);
        echo Format('Something went wrong, (584)', OP_Fail);
        return null;
    }
}


function updateProfilePic($userId, $newImageName)
{

    try {

        $pdo = new PDO("mysql:host=" . DB_ServerName . ";dbname=" . DB_Name, DB_UserName, DB_Password);

        $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);


        $stmt = $pdo->prepare('UPDATE users SET profilepic = :newImageName WHERE userid = :userId AND token_expiration >= NOW()');
        $stmt->bindParam(':newImageName', $newImageName);
        $stmt->bindParam(':userId', $userId);
        $stmt->execute();


        $pdo = null;
        return true;
    } catch (PDOException $e) {

        logError($e);
        echo Format('Something went wrong, (193)', OP_Fail);
    }
    return false;
}

function UpdateUserName($userId, $token, $newusername)
{

    try {

        $pdo = new PDO("mysql:host=" . DB_ServerName . ";dbname=" . DB_Name, DB_UserName, DB_Password);

        $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);


        $stmt = $pdo->prepare('UPDATE users SET usrname = :nname WHERE userid = :userId AND token = :token AND token_expiration >= NOW()');
        $stmt->bindParam(':nname', $newusername);
        $stmt->bindParam(':userId', $userId);
        $stmt->bindParam(':token', $token);
        $stmt->execute();


        $pdo = null;
        return true;
    } catch (PDOException $e) {

        logError($e);
        echo Format('Something went wrong, (385)', OP_Fail);
    }
    return false;
}

function validateUsername($username)
{

    if (preg_match('/[^a-zA-Z0-9]/', $username)) {
        return "Username can't contain special characters.";
    }


    $length = strlen($username);
    if ($length < 3 || $length > 16) {
        return "Username must be between 3 and 16 characters long.";
    }

    $taken = array('evlf', 'admin', 'Eaod');
    $holdname = strtolower(str_replace(' ', '', $username));
    $notaccepted = false;
    foreach ($taken as $value) {
        if (stripos($holdname, $value) !== false) {
            $notaccepted = true;
            break;
        }
    }

    if ($notaccepted) {
        return $username . " is already taken";
    }


    return true;
}
