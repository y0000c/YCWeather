package demo.yc.ycweather.activity;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import demo.yc.ycweather.utils.ActivityUtils;

public class BaseActivity extends AppCompatActivity
{


    /**
     *  定义一系列需要申请全权限的code
     */
    public static final int LOCATION_CODE = 0x001;  // 定位

    public static final int WRITE_SD_CODE = 0x112; // 访问sd卡

    public static boolean hasLocationPermission = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        initWindow();
        ActivityUtils.add(this);
    }


    /**
     * 为子类提供判断是否有权限的方法
     * @param permissions   权限字符串
     * @return
     */
    public boolean  hasPermissions(String...permissions)
    {
        Log.w("permission","当前手机sdk版本："+Build.VERSION.SDK_INT);
        // 6.0 以下直接返回 true
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;

        for(String p : permissions)
        {
            if((ContextCompat.checkSelfPermission(this,p) !=
                    PackageManager.PERMISSION_GRANTED))
            return false;
        }
        return true;
    }


    public void requestPermission(int code,String...permissions)
    {
        ActivityCompat.requestPermissions(this,permissions,code);
    }

    /**
     * 5.0及以上透明状态栏
     */
    private void initWindow(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    |View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

    }


    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        ActivityUtils.remove(this);
    }
}
