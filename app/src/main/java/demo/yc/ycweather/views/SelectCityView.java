package demo.yc.ycweather.views;

import java.util.List;

/**
 * Created by Administrator on 2017/4/25 0025.
 */

public interface SelectCityView extends RootView
{

    void refreshList(List<String> data);

    void setTitleText(String title);

    void showBackBtn();

    void hideBackBtn();

    void startToActivity(String weatherId);


}
