package demo.yc.ycweather.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import demo.yc.ycweather.R;
import demo.yc.ycweather.models.weather.Suggestion;
import demo.yc.ycweather.presenters.SuggestionDetailPresenter;
import demo.yc.ycweather.views.SuggestionDetailView;

public class ShowSuggestionDetailActivity extends BaseActivity implements SuggestionDetailView
{
    private static final String POS_TAG = "position";
    private static final String MODEL_TAG = "suggestion";


    private ImageView mainImage;
    private ImageView iconImage;
    private ImageView backImage;

    private TextView titleTv;
    private TextView contentTv;
    private TextView indexTv;


    private SuggestionDetailPresenter mPresenter;

    private int position;

    private Suggestion suggestion;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_suggestion_detail);
        setUI();
        getDateFromIntent();
        mPresenter = new SuggestionDetailPresenter(this);
        mPresenter.getDataFromView();
    }

    public void getDateFromIntent()
    {
        Intent intent = getIntent();

        if(intent.getExtras() != null)
        {
            position =  intent.getExtras().getInt(POS_TAG);
            suggestion = (Suggestion) intent.getSerializableExtra(MODEL_TAG);
        }
    }


    private void setUI()
    {
       mainImage = (ImageView) findViewById(R.id.suggestion_detail_image);
       iconImage = (ImageView) findViewById(R.id.suggestion_detail_icon);
       backImage = (ImageView) findViewById(R.id.suggestion_detail_back);
       contentTv = (TextView) findViewById(R.id.suggestion_detail_content);
       indexTv = (TextView) findViewById(R.id.suggestion_detail_index);
       titleTv = (TextView) findViewById(R.id.suggestion_detail_title);

        setListener();
    }

    @Override
    public int getPosition()
    {
        return position;
    }

    @Override
    public Suggestion getSuggestion()
    {
        return suggestion;
    }

    private void setListener()
    {
        backImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ShowSuggestionDetailActivity.this.finish();
            }
        });
    }


    public static void setIntentData(Context context, int position, Suggestion suggestion)
    {
        Intent intent = new Intent(context,ShowSuggestionDetailActivity.class);
        intent.putExtra(POS_TAG,position);
        intent.putExtra(MODEL_TAG,suggestion);
        context.startActivity(intent);
    }


    @Override
    public void showImage(int resId)
    {
        Glide.with(this).load(resId).placeholder(R.drawable.home_bg)
                .error(R.drawable.home_bg).into(mainImage);
    }

    @Override
    public void showICon(int resId)
    {
        Glide.with(this).load(resId).placeholder(R.drawable.home_bg)
                .error(R.drawable.home_bg).into(iconImage);
    }

    @Override
    public void showTitle(String title)
    {
        titleTv.setText(title);
    }

    @Override
    public void showContent(String content)
    {
        contentTv.setText(content);
    }

    @Override
    public void showIndex(String index)
    {
        indexTv.setText(index);
    }

    @Override
    public void showErrorDialog()
    {

    }

}
