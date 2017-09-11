package demo.yc.ycweather.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import demo.yc.ycweather.R;

public class AirQualityActivity extends AppCompatActivity
{




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_air_quality);
        ButterKnife.bind(this);
    }

}
