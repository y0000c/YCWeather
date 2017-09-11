package demo.yc.ycweather.presenters;

import demo.yc.ycweather.R;
import demo.yc.ycweather.models.weather.Suggestion;
import demo.yc.ycweather.views.SuggestionDetailView;

/**
 * Created by Administrator on 2017/6/7 0007.
 */

public class SuggestionDetailPresenter
{

    private SuggestionDetailView mView;

    private Suggestion suggestion ;
    private int position;

    private String title;
    private String content;
    private String index;

    private int mainImageId;
    private int iconId;


    public SuggestionDetailPresenter(SuggestionDetailView mView)
    {
        this.mView = mView;


    }

    public void getDataFromView()
    {

        position = mView.getPosition();

        suggestion = mView.getSuggestion();

        if(suggestion == null)
            mView.showErrorDialog();
        else
            dealDate();
    }


    /**
     * 2 舒适度  3旅游 4  穿衣
     * 7 运动  10  洗车
     * 13 紫外线  14 感冒
     */
    private void dealDate()
    {
        switch (position)
        {
            case 2:
                title = "舒适度";
                content = suggestion.comf.txt;
                index = suggestion.comf.brf;
                mainImageId = R.drawable.conf_bg;
                iconId = R.drawable.air;
                showDetailInfo();
                break;
            case 3:
                title = "旅游";
                content = suggestion.trav.txt;
                index = suggestion.trav.brf;
                mainImageId = R.drawable.travel_bg;
                iconId = R.drawable.travel;
                showDetailInfo();
                break;
            case 4:
                title = "穿衣";
                content = suggestion.drsg.txt;
                index = suggestion.drsg.brf;
                mainImageId = R.drawable.dress_bg;
                iconId = R.drawable.dress;
                showDetailInfo();
                break;
            case 7:
                title = "运动";
                content = suggestion.sport.txt;
                index = suggestion.sport.brf;
                iconId = R.drawable.sport;
                mainImageId = R.drawable.sport_bg;
                showDetailInfo();
                break;
            case 10:
                title = "洗车";
                content = suggestion.cw.txt;
                index = suggestion.cw.brf;
                iconId = R.drawable.car;
                showDetailInfo();
                break;
            case 13:
                title = "紫外线";
                content = suggestion.uv.txt;
                index = suggestion.uv.brf;
                iconId = R.drawable.uv;
                mainImageId = R.drawable.uv_bg;
                showDetailInfo();
                break;
            case 14:
                title = "感冒";
                content = suggestion.flu.txt;
                index = suggestion.flu.brf;
                iconId = R.drawable.flu;
                showDetailInfo();
                break;
            default:
                mView.showErrorDialog();
        }
    }

    private void showDetailInfo()
    {
        mView.showTitle(title);

        mView.showContent(content);

        mView.showIndex(index);

        mView.showICon(iconId);

        mView.showImage(mainImageId);
    }
}
