package demo.yc.ycweather.views;

import demo.yc.ycweather.models.weather.Suggestion;

/**
 * Created by Administrator on 2017/6/7 0007.
 */

public interface SuggestionDetailView
{
    void showImage(int resId);

    void showICon(int resId);

    void showTitle(String title);

    void showContent(String content);

    void showIndex(String index);

    int getPosition();

    Suggestion getSuggestion();

    void showErrorDialog();

}
