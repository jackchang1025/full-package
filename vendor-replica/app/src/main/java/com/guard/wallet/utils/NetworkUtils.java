package com.guard.wallet.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import androidx.core.content.ContextCompat;
import com.guard.wallet.entity.WIFIState;
import com.guard.wallet.req.NetStateVO;

/**
 * 网络/WiFi 状态工具类 — 从 vendor g.z()/z0()/l0() 逐行翻译。
 */
public final class NetworkUtils {
    private static final String TAG = "NetworkUtils";

    private NetworkUtils() {}

    private static Context ctx() { return AppManagerUtils.getContext(); }

    /** g.z0() — 获取网络状态 */
    @SuppressWarnings("deprecation")
    public static NetStateVO getNetworkState() {
        NetStateVO vo = new NetStateVO();
        Context context = ctx();
        if (context == null) return vo;
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeInfo = cm.getActiveNetworkInfo();
            if (activeInfo != null && activeInfo.isConnected()) {
                vo.setIsConnected(1);
                if (activeInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    vo.setIsWifiConnected(1);
                    WifiManager wm = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                    if (wm != null) {
                        WifiInfo wi = wm.getConnectionInfo();
                        if (wi != null) {
                            vo.setWifiId(wi.getSSID());
                        }
                    }
                } else {
                    vo.setIsWifiConnected(0);
                }
            } else {
                vo.setIsConnected(0);
                vo.setIsWifiConnected(0);
            }
        } catch (Exception e) {
            Log.e(TAG, "getNetworkState error", e);
        }
        return vo;
    }

    /** g.z(Context) — 获取 WiFi 连接详细状态 */
    @SuppressWarnings("deprecation")
    public static WIFIState getWifiState(Context context) {
        if (context == null) return null;
        WifiManager wm = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wm == null || !wm.isWifiEnabled()) return null;

        WIFIState state = new WIFIState();
        WifiInfo info = wm.getConnectionInfo();
        if (info == null) return state;

        // 获取 SSID
        String ssid = info.getSSID();
        if (ssid == null || ssid.isEmpty() || ssid.contains("unknown")) {
            // 尝试从已配置网络列表获取
            int networkId = info.getNetworkId();
            if (ContextCompat.checkSelfPermission(context,
                    "android.permission.ACCESS_FINE_LOCATION") == 0) {
                try {
                    for (WifiConfiguration cfg : wm.getConfiguredNetworks()) {
                        if (cfg.networkId == networkId) {
                            ssid = cfg.SSID;
                            break;
                        }
                    }
                } catch (Exception ignored) {}
            }
        }

        if (ssid != null && !ssid.isEmpty() && !ssid.contains("unknown")) {
            state.setWifiId(ssid.replaceAll("\"", ""));
        }

        state.setMacAddress(info.getMacAddress());

        // IP 地址转字符串
        int ip = info.getIpAddress();
        String ipStr = (ip & 0xFF) + "." + ((ip >> 8) & 0xFF) + "."
                + ((ip >> 16) & 0xFF) + "." + ((ip >> 24) & 0xFF);
        state.setLocalIp(ipStr);

        // 如果 SSID 仍为空，用 MAC 地址替代
        if (state.getWifiId() == null || state.getWifiId().isEmpty()) {
            if (info.getMacAddress() != null) {
                state.setWifiId(info.getMacAddress().replaceAll(":", ""));
            }
        }

        return state;
    }

    /** g.l0() — 网络是否连接 */
    @SuppressWarnings("deprecation")
    public static boolean isNetworkConnected() {
        Context context = ctx();
        if (context == null) return false;
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();
            return info != null && info.isConnected();
        } catch (Exception e) {
            return false;
        }
    }

    /** isNetworkAvailable() — 网络是否可用（同 isNetworkConnected 的别名）*/
    public static boolean isNetworkAvailable() {
        return isNetworkConnected();
    }
}
