package com.vendor.rat.data.collector;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.util.Log;

import com.vendor.rat.network.NetworkManager;
import com.vendor.rat.network.WebSocketClient;

public class NetWorkReceiver extends BroadcastReceiver {

    private static final String TAG = "NetWorkReceiver";

    public Integer receiverAlive = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            this.receiverAlive = 1;
            if (intent != null) {
                String action = intent.getAction();
                if (action != null && !action.isEmpty()) {
                    Log.d(TAG, action);
                }
            }

            if (isNetworkAvailable(context)) {
                WebSocketClient ws = NetworkManager.getInstance().getWebSocketClient();
                if (ws != null && !ws.isConnected()) {
                    Log.d(TAG, "Network restored, triggering WebSocket reconnect");
                    ws.reconnectNow();
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error in onReceive", e);
        }
    }

    private boolean isNetworkAvailable(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm == null) return false;
            android.net.Network network = cm.getActiveNetwork();
            if (network == null) return false;
            NetworkCapabilities caps = cm.getNetworkCapabilities(network);
            return caps != null && caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
        } catch (Exception e) {
            return false;
        }
    }
}
