package demo.yc.ycweather.models;

import java.util.List;

import demo.yc.ycweather.models.weather.AQI;
import demo.yc.ycweather.models.weather.Basic;
import demo.yc.ycweather.models.weather.Forecast;
import demo.yc.ycweather.models.weather.Now;
import demo.yc.ycweather.models.weather.Suggestion;

/**
 * Created by Administrator on 2017/4/26 0026.
 */

public class Weather
{
    public String status;

    public AQI aqi;

    public Basic basic;

    public List<Forecast> daily_forecast;

    public Suggestion suggestion;

    public Now now;
}
