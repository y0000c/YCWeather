package demo.yc.ycweather.presenters;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import demo.yc.ycweather.models.Weather;
import demo.yc.ycweather.utils.HttpUtils;
import demo.yc.ycweather.utils.JsonUtils;
import demo.yc.ycweather.views.HomeView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/5/3 0003.
 */

public class HomePresenter
{

    private SharedPreferences preferences;
    private HomeView mHomeView;
    private Context mContext;
    private Handler mHandler;
    private String weatherID = "";


    public static final String FILE_KEY = "weather";
    public static final String ID_KEY = "weatherID";
    private static final String IMAGE_DATE = "day";
    private static final String IMAGE_PATH = "url";

    public static final String FILE_NAME = "weather_info";
    public HomePresenter(Context mContext, HomeView mHomeView)
    {

        preferences = mContext.getSharedPreferences(FILE_NAME,Context.MODE_APPEND);
        this.mHomeView = mHomeView;
        this.mContext = mContext;
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 从本地文件获取天气信息
     */
    public void getWeatherInfoFromLocal()
    {
        mHomeView.showDialog();
        String weatherString = preferences.getString(FILE_KEY,"");
        String weatherId = preferences.getString(ID_KEY,"");
        Log.w("weather","string---fromLocal: "+weatherString);
        Log.w("weather","id----fromLocal: "+weatherId);
        Log.w("weather","id----getWeather: "+mHomeView.getWeatherID());
        if("".equals(weatherString) || !weatherId.equals(mHomeView.getWeatherID()))
        {
            Log.w("weather","request.....string");
            requestWeatherInfo(mHomeView.getWeatherID());
        }
        else
        {
            mHomeView.hideDialog();
            Weather weather = JsonUtils.handleWeatherResponse(weatherString);
            showWeatherInfo(weather);
        }

    }

    /**
     * 获取主背景图片
     */
    public void requestWeatherImage()
    {
        Log.w("image","request.....image");
        Date date = new Date();
        SimpleDateFormat spd = new SimpleDateFormat("yyyy-MM-dd");
        final String curDay = spd.format(date);
        String day = preferences.getString(IMAGE_DATE,"");
        String path = preferences.getString(IMAGE_PATH,"");

        Log.w("image",curDay+"-----"+day +"----"+path);
        if(!curDay.equals(day))
        {
            Log.w("image","start request-----image");
            HttpUtils.sendHttpRequest("http://guolin.tech/api/bing_pic", new Callback()
            {
                @Override
                public void onFailure(Call call, IOException e)
                {
                    mHandler.post(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                           // mHomeView.showToastMessage("图片获取失败");
                        }
                    });
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException
                {

                    final String imageUrl = response.body().string();
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(IMAGE_DATE,curDay);
                    editor.putString(IMAGE_PATH,imageUrl);
                    editor.apply();
                    Log.w("image", "image url-->"+imageUrl);
                    mHandler.post(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            mHomeView.showImageBg(imageUrl);
                        }
                    });
                }
            });
        }else {
            Log.w("image","from local -----image"+path);
            mHomeView.showImageBg(path);
        }

    }


    /**
     * 从网络获取天气信息
     * @param weatherID
     */
    public void requestWeatherInfo(final String weatherID){
        mHomeView.showDialog();
        HttpUtils.sendHttpRequest("http://guolin.tech/api/weather?" +
                "cityid=" + weatherID +
                "&key=bc0418b57b2d4918819d3974ac1285d9", new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                mHandler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        mHomeView.hideDialog();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                final String resultString = response.body().string();
                final Weather weather = JsonUtils.handleWeatherResponse(resultString);
                Log.w("weather","from server: "+resultString);
                mHandler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        mHomeView.hideDialog();
                        if(weather != null &&weather.status.equals("ok"))
                        {
                            showWeatherInfo(weather);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString(FILE_KEY,resultString);
                            editor.putString(ID_KEY,weatherID);
                            editor.apply();
                        }else
                        {
                            mHomeView.showErrorDialog();
                        }
                    }
                });

            }
        });
    }



    private void showWeatherInfo(Weather weather)
    {
        mHomeView.showBasicInfo(weather.basic);
        mHomeView.showNowInfo(weather.now);
        mHomeView.showTitle();
        mHomeView.showAirQuality(weather.aqi);
        mHomeView.showForecast(weather.daily_forecast);
        mHomeView.showSuggestion(weather.suggestion);
    }


    public void getResentLocalData()
    {
        mHomeView.showDialog();
        String weatherString = preferences.getString(FILE_KEY,"");
        if("".equals(weatherString))
        {
            mHomeView.showErrorDialog();
        }else
        {
            Weather weather = JsonUtils.handleWeatherResponse(weatherString);
            mHomeView.hideDialog();
            showWeatherInfo(weather);
        }

    }






}
