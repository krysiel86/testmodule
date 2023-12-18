
package io.github.krysiel86.utils;

import android.text.TextUtils;
import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URL;

public class HttpTools {

    private static final String TAG = HttpTools.class.getName();

    public static void httpGetURL(final String url) {

        if (!TextUtils.isEmpty(url)) {
            new Thread() {

                @Override
                public void run() {

                    HttpURLConnection conn = null;

                    try {
//                        VASTLog.v(TAG, "connection to URL:" + url);
                        URL httpUrl = new URL(url);

                        HttpURLConnection.setFollowRedirects(true);
                        conn = (HttpURLConnection) httpUrl.openConnection();
//                        httpGet.setProtocolVersion(HttpVersion.HTTP_1_1);

                        conn.setConnectTimeout(5000);
                        conn.setRequestProperty("Connection", "close");
                        conn.setRequestMethod("GET");
                        conn.connect();

                        int code = conn.getResponseCode();
//                        VASTLog.v(TAG, "response code : " + code + ", for URL : " + url);

                        conn.getInputStream().close();
                        conn.getOutputStream().close();

                    } catch (Exception e) {

                        Log.w(TAG, url + " / Exception : " + e.getMessage() + ":" + e.toString());

                    } finally {
                        if (conn != null) {
                            conn.disconnect();
                        }
                    }

                }
            }.start();

        } else {

            VASTLog.w(TAG, "url is null or empty");
        }
    }
}
