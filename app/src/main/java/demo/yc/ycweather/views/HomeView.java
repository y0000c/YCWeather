package demo.yc.ycweather.views;

import java.util.List;

import demo.yc.ycweather.models.weather.AQI;
import demo.yc.ycweather.models.weather.Basic;
import demo.yc.ycweather.models.weather.Forecast;
import demo.yc.ycweather.models.weather.Now;
import demo.yc.ycweather.models.weather.Suggestion;

/**
 * Created by Administrator on 2017/5/3 0003.
 */

public interface HomeView extends RootView
{

    String getWeatherID();

    void showErrorDialog();

    void showBasicInfo(Basic basic);

    void showNowInfo(Now now);

    void showForecast(List<Forecast> forecasts);

    void showTitle();

    void showImageBg(String imageUrl);

    void showAirQuality(AQI aqi);

    void showSuggestion(Suggestion suggestion);
}
