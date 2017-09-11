package demo.yc.ycweather.models;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017/4/25 0025.
 */

public class Country extends DataSupport
{
    private int id;

    private String CountryName;

    private String weatherId;

    private int cityId;

    public String getWeatherId()
    {
        return weatherId;
    }

    public void setWeatherId(String weatherId)
    {
        this.weatherId = weatherId;
    }

    public int getCityId()
    {
        return cityId;
    }

    public void setCityId(int cityId)
    {
        this.cityId = cityId;
    }

    public String getCountryName()
    {
        return CountryName;
    }

    public void setCountryName(String countryName)
    {
        CountryName = countryName;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }
}
