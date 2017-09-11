package demo.yc.ycweather.utils;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Administrator on 2017/4/25 0025.
 */

public class HttpUtils
{

    /**
     * 天气预报数据url 传入 cityid 即可
     * http://guolin.tech/api/weather?cityid=CN101020100&key=bc0418b57b2d4918819d3974ac1285d9
     */
    private static OkHttpClient client = new OkHttpClient();

    public static void sendHttpRequest(String url, Callback callback)
    {
        Request request = new Request.Builder().get().url(url).build();
        client.newCall(request).enqueue(callback);

    }


}
