package demo.yc.ycweather.adapter;

import android.view.View;
import android.widget.TextView;

import demo.yc.ycweather.R;
import demo.yc.ycweather.models.weather.Suggestion;

/**
 * Created by Administrator on 2017/5/16 0016.
 */

public class HeaderHolder extends SuggestionBaseHolder
{
    private TextView headerTitle;
    public HeaderHolder(View itemView)
    {
        super(itemView);
        headerTitle = (TextView) itemView.findViewById(R.id.header_title);
    }
    @Override
    public void bindDate(Suggestion suggestion,int pos)
    {
        if(pos == 1)
            headerTitle.setText("出行建议");
        else if (pos == 6)
            headerTitle.setText("运动建议");
        else if(pos == 9)
            headerTitle.setText("驾车建议");
        else
            headerTitle.setText("注意事项");
    }
}
