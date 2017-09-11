package demo.yc.ycweather.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import demo.yc.ycweather.activity.HomeActivity;
import demo.yc.ycweather.activity.MainActivity;
import demo.yc.ycweather.R;
import demo.yc.ycweather.presenters.SelectCityPresenter;
import demo.yc.ycweather.views.SelectCityView;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectCityFrags extends Fragment implements View.OnClickListener
        ,AdapterView.OnItemClickListener,SelectCityView
{

    private ProgressDialog pd;

    private TextView titleText;

    private ImageView backBtn;

    private ImageView locateBtn;

    private ListView listView;

    private SelectCityPresenter presenter;


    public SelectCityFrags()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_select_city_frags, null, false);
        titleText = (TextView) view.findViewById(R.id.select_city_title);
        backBtn = (ImageView) view.findViewById(R.id.select_city_back);
        locateBtn = (ImageView) view.findViewById(R.id.select_city_locate);
        listView = (ListView) view.findViewById(R.id.select_city_list);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        presenter = new SelectCityPresenter(this);
        presenter.queryProvince();
        backBtn.setOnClickListener(this);
        locateBtn.setOnClickListener(this);
        listView.setOnItemClickListener(this);
        
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.select_city_back:
                presenter.onBackClick();
                break;
            case R.id.select_city_locate:
                presenter.onLocateClick();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l)
    {
        presenter.onItemClick(pos);
    }

    @Override
    public void showDialog()
    {
        if(pd == null)
        {
            pd = new ProgressDialog(getContext());
            pd.setMessage("wait...");
            pd.setCanceledOnTouchOutside(false);
        }
        pd.show();
    }

    @Override
    public void hideDialog()
    {

        if(pd != null)
            pd.dismiss();
    }

    @Override
    public void refreshList(List<String> data)
    {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1, data);
        listView.setAdapter(adapter);
        listView.setSelection(0);
    }

    @Override
    public void setTitleText(String title)
    {
        titleText.setText(title);
    }

    @Override
    public void showBackBtn()
    {
        backBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideBackBtn()
    {
        backBtn.setVisibility(View.GONE);
    }

    @Override
    public void showToastMessage(String msg)
    {
        if(msg != null && !msg.matches("\\s"))
        Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
    }


    @Override
    public void startToActivity(String weatherId)
    {
        if(getActivity() instanceof MainActivity)
        {
            Intent intent = new Intent(getContext(), HomeActivity.class);
            intent.putExtra(HomeActivity.INTENT_TAG, weatherId);
            startActivity(intent);
            getActivity().finish();
        }else if(getActivity() instanceof HomeActivity)
        {
            HomeActivity activity = (HomeActivity) getActivity();
            activity.drawerLayout.closeDrawers();
            activity.setWeatherID(weatherId);
            activity.hideDialog();
            activity.mPresenter.requestWeatherInfo(weatherId);
            activity.mPresenter.requestWeatherImage();

        }
    }


    public boolean backDown()
    {
        if(presenter.onBackClick())
            return true;
        return false;
    }


}
