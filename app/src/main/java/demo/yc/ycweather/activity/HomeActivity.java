package demo.yc.ycweather.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import demo.yc.ycweather.R;
import demo.yc.ycweather.adapter.ForecastAdapter;
import demo.yc.ycweather.adapter.SuggestionAdapter;
import demo.yc.ycweather.models.weather.AQI;
import demo.yc.ycweather.models.weather.Basic;
import demo.yc.ycweather.models.weather.Forecast;
import demo.yc.ycweather.models.weather.Now;
import demo.yc.ycweather.models.weather.Suggestion;
import demo.yc.ycweather.presenters.HomePresenter;
import demo.yc.ycweather.uis.AirQualityView;
import demo.yc.ycweather.views.HomeView;
import demo.yc.ycweather.views.IForecastCallBackListener;
import demo.yc.ycweather.views.ISuggestionListener;

public class HomeActivity extends BaseActivity implements HomeView
{

    public DrawerLayout drawerLayout;

    //toobar 相关控件布局
    private Toolbar toolbar;
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    private TextView temperatureText;   // 当前温度
    private TextView countryText;      // 当前城市
    private TextView weatherInfoText;  //当前天气
    private ImageView menuBtn;     //菜单btn
    private ImageView weatherBg;  // 主图片背景
    private AlertDialog dialog;  //提示dialog

    private TextView flInfoText; //体感温度
    private TextView humInfoText; //相对湿度
    private TextView spdInfoText; //风速
    private TextView dirInfoText; //风向

    private RecyclerView forecastListView;  //未来预报list
    private TextView forecastTextBtn;   // 未来预报btn
    private ForecastAdapter forecastAdapter; //adapter

    private RecyclerView suggestionListView; //各种建议list
    private SuggestionAdapter suggestionAdapter; // adapter
    private View airQualityLayout;       //空气质量布局
    private AirQualityView aqiInfo;
    private AirQualityView pmInfo;
    private TextView airQualityInfoText; // 空气质量text
    private TextView airQualityTextBtn;     //空气质量btn

    private SwipeRefreshLayout refreshLayout;
    public HomePresenter mPresenter;
    public static final String INTENT_TAG = "weather_id";

    private static final int MENU_REQUEST = 0x001;
    private static final int CHANGE_BG_REQUEST = 0x002;
    private static final int CHANGE_LOCATIONE_REQUESt = 0x003;


    private String weatherID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        weatherID = getIntent().getStringExtra(INTENT_TAG);
        setUI();
        mPresenter = new HomePresenter(this,this);
        mPresenter.getWeatherInfoFromLocal();
        mPresenter.requestWeatherImage();
    }

    private void setUI()
    {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        refreshLayout.setColorSchemeColors(Color.RED,Color.GREEN,Color.BLUE);

        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener()
        {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset)
            {

            }

            @Override
            public void onDrawerOpened(View drawerView)
            {

            }

            @Override
            public void onDrawerClosed(View drawerView)
            {

            }

            @Override
            public void onDrawerStateChanged(int newState)
            {

            }
        });
        // 与toolBar 相关的控件
        toolbar = (Toolbar) findViewById(R.id.home_toolbar);
        appBarLayout = (AppBarLayout) findViewById(R.id.home_appbar);
        collapsingToolbarLayout = (CollapsingToolbarLayout)
                findViewById(R.id.home_collasping_layout);
        setSupportActionBar(toolbar);
        collapsingToolbarLayout.setExpandedTitleColor(Color.TRANSPARENT);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);

        //主背景
        temperatureText = (TextView) findViewById(R.id.weather_tmp_text);
        countryText = (TextView) findViewById(R.id.weather_country_text);
        weatherInfoText = (TextView) findViewById(R.id.weather_info_text);
        menuBtn = (ImageView) findViewById(R.id.weather_menu_btn);
        weatherBg = (ImageView) findViewById(R.id.home_weather_image);

        //底部栏目
        dirInfoText = (TextView) findViewById(R.id.dirInfo);
        spdInfoText = (TextView) findViewById(R.id.spdInfo);
        flInfoText = (TextView) findViewById(R.id.flInfo);
        humInfoText = (TextView) findViewById(R.id.humInfo);

        // 空气质量
        airQualityInfoText = (TextView) findViewById(R.id.airQualityInfo);
        airQualityLayout = findViewById(R.id.airQuality_layout);
        aqiInfo = (AirQualityView) findViewById(R.id.aqiInfo);
        pmInfo = (AirQualityView) findViewById(R.id.pmInfo);
        airQualityTextBtn = (TextView) findViewById(R.id.airQuality_text_btn);

        //未来天气预报
        forecastTextBtn = (TextView) findViewById(R.id.forecast_text_btn);
        forecastListView = (RecyclerView) findViewById(R.id.forecast_days_list);

        //各种建议
        suggestionListView = (RecyclerView) findViewById(R.id.suggestion_list);

        setListener();
    }

    public void setWeatherID(String weatherID)
    {
        this.weatherID = weatherID;
    }
    private void setListener()
    {

        forecastTextBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
               // startActivity(new Intent(HomeActivity.this,ForecastDetailActivity.class));
            }
        });

        airQualityTextBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

            }
        });

        airQualityLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

            }
        });

        //通过判断头部折叠栏的折叠大小，决定能否下拉刷新
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener()
        {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset)
            {
                if(verticalOffset == 0)
                    refreshLayout.setEnabled(true);
                else
                {
                    if(!refreshLayout.isRefreshing())
                        refreshLayout.setEnabled(false);
                }
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                mPresenter.getWeatherInfoFromLocal();
            }
        });


    }

    @Override
    public String getWeatherID(){

        return weatherID;
    }

    @Override
    public void showErrorDialog()
    {
        if(dialog == null)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("数据异常")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i)
                        {
                            mPresenter.getResentLocalData();
                        }
                    });
            dialog = builder.create();
        }
        dialog.show();
    }

    @Override
    public void showBasicInfo(Basic basic)
    {
        countryText.setText(basic.cityName);
    }

    @Override
    public void showNowInfo(Now now)
    {
        temperatureText.setText(now.tmp+"°");
        weatherInfoText.setText(now.cond.txt);
        humInfoText.setText(now.hum+"%");
        spdInfoText.setText(now.wind.spd+"级");
        dirInfoText.setText(now.wind.dir);
        flInfoText.setText(now.fl+"°");

    }

    @Override
    public void showForecast(List<Forecast> forecasts)
    {
        Log.w("weather","未来天气预测："+forecasts.size());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        forecastAdapter = new ForecastAdapter(this,forecasts);
        forecastListView.setLayoutManager(layoutManager);
        forecastListView.setAdapter(forecastAdapter);
        forecastAdapter.setOnItemClickListener(new IForecastCallBackListener()
        {
            @Override
            public void onClick()
            {
                startActivity(new Intent(HomeActivity.this,ForecastDetailActivity.class));
            }
        });
    }

    @Override
    public void showTitle()
    {
        collapsingToolbarLayout.setTitle(countryText.getText().toString()
                +" "+weatherInfoText.getText().toString());
    }


    @Override
    public void showImageBg(String imageUrl)
    {
        Glide.with(this).load(imageUrl).placeholder(R.drawable.home_bg)
                .error(R.drawable.home_bg).into(weatherBg);
    }

    @Override
    public void showAirQuality(AQI aqi)
    {
        airQualityInfoText.setText(aqi.city.qlty);
        try
        {
            int pmNum = Integer.parseInt(aqi.city.pm25);
            pmInfo.setCurrentNum(pmNum);
            int aqiNum = Integer.parseInt(aqi.city.aqi);
            aqiInfo.setCurrentNum(aqiNum);
        }catch (Exception e)
        {
//            pmInfo.setCurrentNum(10);
//            aqiInfo.setCurrentNum(10);
        }
    }

    @Override
    public void showSuggestion(final Suggestion suggestion)
    {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        suggestionAdapter = new SuggestionAdapter(this,suggestion);
        suggestionListView.setLayoutManager(layoutManager);
        suggestionListView.setAdapter(suggestionAdapter);
        suggestionAdapter.setOnItemClickListener(new ISuggestionListener()
        {
            @Override
            public void onItemClick(View view, int position)
            {
               ShowSuggestionDetailActivity.setIntentData(HomeActivity.this
                       ,position,suggestion);
            }
        });
    }

    @Override
    public void showToastMessage(String msg)
    {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDialog()
    {
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void hideDialog()
    {
      refreshLayout.setRefreshing(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(resultCode == RESULT_OK)
        {
            switch (requestCode)
            {
                case MENU_REQUEST:
                    break;
                case CHANGE_BG_REQUEST:
                    break;
                case CHANGE_LOCATIONE_REQUESt:
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onBackPressed()
    {
        if(refreshLayout.isRefreshing())
        {
            refreshLayout.setRefreshing(false);
        }else if(drawerLayout.isDrawerOpen(Gravity.START))
        {
            drawerLayout.closeDrawer(Gravity.START);
        }else
            super.onBackPressed();
    }

}
