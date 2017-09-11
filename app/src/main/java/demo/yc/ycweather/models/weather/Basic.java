package demo.yc.ycweather.models.weather;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/4/26 0026.
 */

public class Basic
{
    @SerializedName("city")
    public String cityName;

    @SerializedName("id")
    public String weatherId;

    public String cnty;

    public String lat;

    public String lon;

    public Update update;

    public class Update{

        public String loc;

        public String utc;
    }
}
