package com.msx7.josn.tvServer.mima.common.util;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 * Created by xiaowei on 2015/12/9.
 */
public class IPUtil {
    public static String getIP(Context ctx) {
        WifiManager wifiService = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiinfo = wifiService.getConnectionInfo();
        return intToIp(wifiinfo.getIpAddress());
    }
    private static String intToIp(int i) {

        return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF)
                + "." + (i >> 24 & 0xFF);
    }
}
