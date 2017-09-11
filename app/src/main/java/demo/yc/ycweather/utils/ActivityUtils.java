package demo.yc.ycweather.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/25 0025.
 */

public class ActivityUtils
{
    private static List<Activity> mActivityList = new ArrayList<>();

    public static void add(Activity a)
    {
        mActivityList.add(a);
    }

    public static void remove(Activity a)
    {
        mActivityList.remove(a);
    }

    public static void clear()
    {
        for(Activity a:mActivityList)
        {
            if(a != null && a.isFinishing())
                a.finish();
            mActivityList.remove(a);
        }
    }

}
