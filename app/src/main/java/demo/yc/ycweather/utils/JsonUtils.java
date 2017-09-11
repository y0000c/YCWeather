package demo.yc.ycweather.utils;

import android.text.TextUtils;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import demo.yc.ycweather.models.City;
import demo.yc.ycweather.models.Country;
import demo.yc.ycweather.models.Province;
import demo.yc.ycweather.models.Weather;

/**
 * Created by Administrator on 2017/4/25 0025.
 */

public class JsonUtils
{
    /**
     * 解析服务器返回的省级json 数据 并存储到数据库
     * @param response
     * @return
     */
    public static boolean handleProvinceResponse(String response)
    {
        if(!TextUtils.isEmpty(response))
        {
            try
            {
                JSONArray allProvinces = new JSONArray(response);
                for(int i=0;i<allProvinces.length();i++)
                {
                    JSONObject object = (JSONObject) allProvinces.get(i);
                    Province p = new Province();
                    p.setProvinceCode(object.getInt("id"));
                    p.setProvinceName(object.getString("name"));
                    p.save();
                }
                return true;
            }catch (Exception e)
            {
                e.printStackTrace();
            }

        }
        return false;
    }

    /**
     * 解析服务器返回的县级 json 数据 并存储到数据库
     * @param response
     * @return
     */
    public static boolean handleCityResponse(String response,int provinceId)
    {
        if(!TextUtils.isEmpty(response))
        {
            try
            {
                JSONArray allCitis = new JSONArray(response);
                for(int i=0;i<allCitis.length();i++)
                {
                    JSONObject object = (JSONObject) allCitis.get(i);
                    City c = new City();
                    c.setCityCode(object.getInt("id"));
                    c.setCityName(object.getString("name"));
                    c.setProvinceId(provinceId);
                    c.save();
                }
                return true;
            }catch (Exception e)
            {
                e.printStackTrace();
            }

        }
        return false;
    }


    /**
     * 解析服务器返回的市级json 数据 并存储到数据库
     * @param response
     * @return
     */
    public static boolean handleCountryResponse(String response,int cityId)
    {
        if(!TextUtils.isEmpty(response))
        {
            try
            {
                JSONArray allCountries = new JSONArray(response);
                for(int i=0;i<allCountries.length();i++)
                {
                    JSONObject object = (JSONObject) allCountries.get(i);
                    Country c = new Country();
                    c.setWeatherId(object.getString("weather_id"));
                    c.setCityId(cityId);
                    c.setCountryName(object.getString("name"));
                    c.save();
                }
                return true;
            }catch (Exception e)
            {
                e.printStackTrace();
            }

        }
        return false;
    }

    /**
     * 解析服务器返回的 天气预报json数据
     * @param response
     * @return    返回 Weather 对象
     */
    public static Weather handleWeatherResponse(String response)
    {
        if(!TextUtils.isEmpty(response))
        {
            try{
                JSONObject object = new JSONObject(response);
                JSONArray array = object.getJSONArray("HeWeather");
                String weatherStr = array.get(0).toString();
                return new Gson().fromJson(weatherStr,Weather.class);
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }
}
