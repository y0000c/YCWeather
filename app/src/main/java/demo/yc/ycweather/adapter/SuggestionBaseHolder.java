package demo.yc.ycweather.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import demo.yc.ycweather.models.weather.Suggestion;

/**
 * Created by Administrator on 2017/5/16 0016.
 */

public abstract class SuggestionBaseHolder extends RecyclerView.ViewHolder
{

    public static final int HEAD_VIEW = 0x111;
    public static final int CONTENT_VIEW = 0x112;
    public static final int SPLIT_VIEW = 0x113;

    public SuggestionBaseHolder(View itemView)
    {
        super(itemView);
        Log.w("view","itemView: "+itemView.toString());
    }

    public abstract void bindDate(Suggestion suggestion,int pos);
}
