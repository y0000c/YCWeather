package demo.yc.ycweather.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import demo.yc.ycweather.R;
import demo.yc.ycweather.models.weather.Suggestion;

/**
 * Created by Administrator on 2017/5/16 0016.
 */

public class ContentHolder extends SuggestionBaseHolder
{
    private ImageView contentIcon;
    private TextView contentTitle;
    private TextView contentDetail;
    private TextView ContentTag;

    private String title;
    private String detail;
    private String tag;

    public ContentHolder(View itemView)
    {
        super(itemView);
        contentIcon = (ImageView) itemView.findViewById(R.id.content_icon);
        contentTitle = (TextView) itemView.findViewById(R.id.content_title);
        contentDetail = (TextView) itemView.findViewById(R.id.content_detail);
        ContentTag = (TextView) itemView.findViewById(R.id.content_tag);
    }

    @Override
    public void bindDate(Suggestion suggestion,int pos)
    {
        switch (pos)
        {
//            case 1:
////                Glide.with(contentIcon.getContext()).load(R.drawable.air).into(contentIcon);
////                title = suggestion.air.brf;
////                detail = suggestion.air.txt;
////                tag = "空气";
//                break;
            case 2:
                Glide.with(contentIcon.getContext()).load(R.drawable.air).into(contentIcon);
                tag = "舒适度";
                title = suggestion.comf.brf;
                detail = suggestion.comf.txt;
                break;
            case 3:
                tag = "旅游";
                Glide.with(contentIcon.getContext()).load(R.drawable.travel).into(contentIcon);
                title = suggestion.trav.brf;
                detail = suggestion.trav.txt;
                break;
            case 4:
                tag = "穿衣";
                Glide.with(contentIcon.getContext()).load(R.drawable.dress).into(contentIcon);
                title = suggestion.drsg.brf;
                detail = suggestion.drsg.txt;
                break;
            case 7:
                tag = "运动";
                Glide.with(contentIcon.getContext()).load(R.drawable.sport).into(contentIcon);
                title = suggestion.sport.brf;
                detail = suggestion.sport.txt;
                break;
            case 10:
                tag = "洗车";
                Glide.with(contentIcon.getContext()).load(R.drawable.car).into(contentIcon);
                title = suggestion.cw.brf;
                detail = suggestion.cw.txt;
                break;
            case 13:
                Glide.with(contentIcon.getContext()).load(R.drawable.uv).into(contentIcon);
                tag = "紫外线";
                title = suggestion.uv.brf;
                detail = suggestion.uv.txt;
                break;
            case 14:
                Glide.with(contentIcon.getContext()).load(R.drawable.flu).into(contentIcon);

                tag = "感冒";
                title = suggestion.flu.brf;
                detail = suggestion.flu.txt;
                break;
        }
        ContentTag.setText(tag);
        contentTitle.setText(title);
        contentDetail.setText(detail);
    }
}
