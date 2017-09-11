package demo.yc.ycweather.presenters;

import android.os.Handler;
import android.os.Looper;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import demo.yc.ycweather.activity.BaseActivity;
import demo.yc.ycweather.models.City;
import demo.yc.ycweather.models.Country;
import demo.yc.ycweather.models.Province;
import demo.yc.ycweather.utils.HttpUtils;
import demo.yc.ycweather.utils.JsonUtils;
import demo.yc.ycweather.views.SelectCityView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static org.litepal.crud.DataSupport.findAll;

/**
 *
 * Created by Administrator on 2017/4/25 0025.
 */

public class SelectCityPresenter
{
    public static final int LEVEL_PROVINCE = 0;

    public static final int LEVEL_CITY = 1;

    public static final int LEVEL_COUNTRY = 2;

    private SelectCityView mSelectCityView;

    private Handler mHandler;

    private List<String> dataList;

    private List<Province> provinceList;

    private List<City> cityList;

    private List<Country> countryList;

    private Province selectedProvince;

    private City selectedCity;

    private int currentLevel;

    /**
     * 构造函数
     * @param selectCityView
     */
    public SelectCityPresenter(SelectCityView selectCityView)
    {
        this.mSelectCityView = selectCityView;
        this.mHandler = new Handler(Looper.getMainLooper());
        this.dataList = new ArrayList<>();
    }


    /**
     * 处理listView 点击事件
     * @param pos
     */
    public void onItemClick(int pos)
    {
        if(currentLevel == LEVEL_PROVINCE)
        {
            selectedProvince = provinceList.get(pos);
            queryCity();
        }else if(currentLevel == LEVEL_CITY)
        {
            selectedCity = cityList.get(pos);
            queryCountry();
        }else if(currentLevel == LEVEL_COUNTRY)
        {
            mSelectCityView.startToActivity(countryList.get(pos).getWeatherId());
        }
    }

    /**
     * 处理back键的事件
     * @return
     */
    public boolean onBackClick()
    {
        if(currentLevel == LEVEL_COUNTRY)
        {
            queryCity();
        }else if(currentLevel == LEVEL_CITY)
        {
            queryProvince();
        }else if(currentLevel == LEVEL_PROVINCE)
        {
            return true;
        }
        return false;
    }

    /**
     * 处理定位按钮点击事件
     * @return
     */
    public boolean onLocateClick(){
        if(BaseActivity.hasLocationPermission)
        {
            mSelectCityView.showToastMessage("自动定位成功");
            mSelectCityView.startToActivity("");
            return true;
        }
        mSelectCityView.showToastMessage("自动定位失败");
        return false;
    }

    /**
     * 从数据库获取省信息，如果没有，则去网络请求
     */
    public void queryProvince(){

        provinceList = findAll(Province.class);
        if(provinceList.size() > 0)
        {
            dataList.clear();
            for(Province p:provinceList)
                dataList.add(p.getProvinceName());
            mSelectCityView.refreshList(dataList);
            currentLevel = LEVEL_PROVINCE;
            mSelectCityView.hideBackBtn();
            mSelectCityView.setTitleText("中国");
        }else
        {
            String url = "http://guolin.tech/api/china";
            queryFromServer(url,LEVEL_PROVINCE);
        }
    }

    /**
     * 从数据库获取市信息，如果没有，则去网络请求
     */
    public void queryCity(){

        cityList = DataSupport.where("provinceId = ?",String.valueOf(selectedProvince.getId()))
                .find(City.class);
        if(cityList.size() > 0)
        {
            dataList.clear();
            for(City c:cityList)
                dataList.add(c.getCityName());
            mSelectCityView.refreshList(dataList);
            currentLevel = LEVEL_CITY;
            mSelectCityView.setTitleText(selectedProvince.getProvinceName());
            mSelectCityView.showBackBtn();
        }else
        {
            String url = "http://guolin.tech/api/china/"+selectedProvince.getProvinceCode();
            queryFromServer(url,LEVEL_CITY);
        }
    }

    /**
     * 从数据库获取县信息，如果没有，则去网络请求
     */
    public void queryCountry(){

        countryList = DataSupport.where("cityId = ?",String.valueOf(selectedCity.getId()))
                .find(Country.class);
        if(countryList.size() > 0)
        {
            dataList.clear();
            for(Country c:countryList)
                dataList.add(c.getCountryName());
            mSelectCityView.refreshList(dataList);
            currentLevel = LEVEL_COUNTRY;
            mSelectCityView.setTitleText(selectedCity.getCityName());
            mSelectCityView.showBackBtn();
        }else
        {
            String url = "http://guolin.tech/api/china/"+selectedProvince.getProvinceCode()
                    +"/"+selectedCity.getCityCode();
            queryFromServer(url,LEVEL_COUNTRY);
        }
    }

    /**
     * 去后台服务器获取数据
     * @param url
     * @param level  根据level 选择请求的数据类型  省，市，县
     */
    private void queryFromServer(String url,final int level)
    {
        mSelectCityView.showDialog();
        HttpUtils.sendHttpRequest(url, new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                mHandler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {

                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                String responseText = response.body().string();
                boolean result = false;
                if(level == LEVEL_PROVINCE)
                {
                    result = JsonUtils.handleProvinceResponse(responseText);
                }else if(level == LEVEL_CITY)
                {
                    result = JsonUtils.handleCityResponse(responseText,selectedProvince.getId());
                }else if(level == LEVEL_COUNTRY)
                {
                    result = JsonUtils.handleCountryResponse(responseText,selectedCity.getId());
                }

                final boolean resultFlag = result;
                mHandler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        mSelectCityView.hideDialog();
                        if(resultFlag)
                        {
                            if(level == LEVEL_PROVINCE)
                                queryProvince();
                            else if(level == LEVEL_CITY)
                                queryCity();
                            else if(level == LEVEL_COUNTRY)
                                queryCountry();
                        }else
                        {
                        }
                    }
                });

            }
        });
    }

    private void getCurrentLocation(){

    }
}
