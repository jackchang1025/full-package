<?php

//this to store values , constant
//for example any where in the code you can use DB_ServerName , because of define('DB_ServerName', '127.0.0.1')


//DB 发布 (Docker: 使用服务名 'db')
define('DB_ServerName', getenv('DB_HOST') ?: 'db');

define('DB_UserName', getenv('DB_USER') ?: 'root');
define('DB_Password', "X5fG1hY7jK3lZ9mA2nS4pD6qF8rH0b");
define('DB_Name', 'clients');

//encryption
define('Secrit_Key', '@zxfNM=q>Drm`6VP)!:u-A~;92E<.?wR'); //example : @zxfNM=q>Drm`6VP)!:u-A~;92E<.?wR
define('SIV', 'G8v!h3*Y.P+pFm/;'); //example: G8v!h3*Y.P+pFm/;


define('SIV_jec', 'X5fG1hY7jK3lZ9mA2nS4pD6qF8rH0b'); //example: ;*vhF83GpY/m!+P.


define('Admin_Key', 'feiying8080.@@&');

//email
$currenthost = $_SERVER['HTTP_HOST'];
if (strpos($currenthost, '.site') !== false) {
    // Settings for the .site
    define('Email_Host', 'email.yoursite.site');
    define('My_Name', 'yoursite Support');
    define('Email_Name', 'support@yoursite.site');
    define('Email_Pass', 'xxx'); //your email password
} else {
    // Default settings (.com)
    define('Email_Host', 'email.yoursite.com');
    define('My_Name', 'yoursite Support');
    define('Email_Name', 'support@yoursite.com');
    define('Email_Pass', 'xxx'); //your email password
}

//slpits
define('SplitLINE', '[>L<]');
define('SplitARRAY', '[>A<]');


//imgs
define('IMG_OK', 'lime');
define('IMG_NO', 'red');
