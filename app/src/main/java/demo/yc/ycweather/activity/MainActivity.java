package demo.yc.ycweather.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import demo.yc.ycweather.R;
import demo.yc.ycweather.fragments.SelectCityFrags;
import demo.yc.ycweather.presenters.HomePresenter;

public class MainActivity extends BaseActivity
{
    SelectCityFrags selectFrag;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = getSharedPreferences(HomePresenter.FILE_NAME,MODE_APPEND);
        String content = preferences.getString(HomePresenter.ID_KEY,"");
        if(!"".equals(content))
        {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra(HomeActivity.INTENT_TAG,content);
            startActivity(intent);
            this.finish();
        }

        selectFrag = (SelectCityFrags) getSupportFragmentManager().findFragmentById(R.id.main_select_frag);

        if(hasPermissions(Manifest.permission.ACCESS_COARSE_LOCATION))
        {
           BaseActivity.hasLocationPermission = true;
        }else
        {
            requestPermission(BaseActivity.LOCATION_CODE,Manifest.permission.ACCESS_COARSE_LOCATION);
        }
    }


    @Override
    public void onBackPressed()
    {
        if(selectFrag.backDown())
            super.onBackPressed();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        switch (requestCode)
        {
            case BaseActivity.LOCATION_CODE:
                Log.w("permission","申请回调处理："+grantResults.length +"  "+ grantResults[0]);
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    BaseActivity.hasLocationPermission = true;
                else
                    BaseActivity.hasLocationPermission = false;
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        }

    }

    private void showDialog(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .create()
                .show();
    }
}
