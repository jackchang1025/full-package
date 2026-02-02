<?php

session_start();
require_once 'Eaod14881.php';

// Allow external access, remove IP whitelist restriction
// $whitelist = array(
//     '127.0.0.1',
//     '::1'
// );


// $visitoraddress = getClientIP();

// if (!in_array($visitoraddress, $whitelist)) {
//     header("HTTP/1.0 404 Not Found");
//     echo file_get_contents('../404.php');
//     exit();
// }

// Login validation
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $admin_key = $_POST['admin_key'];
    
    if ($admin_key === Admin_Key) {
        $_SESSION['admin_logged_in'] = true;
        header('Location: create.php');
        exit();
    } else {
        $error = '管理员密钥错误';
    }
}

?>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>管理员</title>
    <style>
        * {
            -webkit-tap-highlight-color: rgba(0, 0, 0, 0);
            -webkit-tap-highlight-color: transparent;
            -webkit-user-select: none;
            -khtml-user-select: none;
            -moz-user-select: none;
            -ms-user-select: none;
            user-select: none;
        }

        body {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            background-color: #171717;
            color: white;
        }

        .login-container {
            background-color: rgb(54, 54, 54);
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(255, 255, 255, 0.1);
            width: 100%;
            max-width: 400px;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
        }

        .login-container h2 {
            text-align: center;
            margin-bottom: 20px;
        }

        .login-container input {
            width: 100%;
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        .login-container button {
            width: 100%;
            padding: 10px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        .login-container button:hover {
            background-color: #45a049;
        }

        .error {
            color: red;
            margin: 10px 0;
        }

        /* Responsive styling */
        @media (max-width: 600px) {
            .login-container {
                padding: 15px;
            }
        }
    </style>
</head>
<body>
    <div class="login-container">
        <h2>飞鹰管理系统</h2>
        <?php if (isset($error)): ?>
            <div class="error"><?php echo $error; ?></div>
        <?php endif; ?>
        <form method="post">
            <input type="password" name="admin_key" placeholder="请输入管理密码" required>
            <button type="submit">登陆</button>
        </form>
    </div>
</body>
</html>