package demo.yc.ycweather.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import demo.yc.ycweather.R;
import demo.yc.ycweather.models.weather.Forecast;
import demo.yc.ycweather.views.IForecastCallBackListener;

/**
 * Created by Administrator on 2017/5/15 0015.
 */

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastHolder>
{
    private LayoutInflater inflater;
    private List<Forecast> list;
    private IForecastCallBackListener listener;
    public ForecastAdapter(Context context, List<Forecast> list)
    {
        this.inflater = LayoutInflater.from(context);
        this.list = list;
    }

    public void setOnItemClickListener(IForecastCallBackListener listener)
    {
        this.listener = listener;
    }

    @Override
    public ForecastHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = inflater.inflate(R.layout.forecast_item,parent,false);
        view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
               // if(listener != null)
                   // listener.onClick();
            }
        });
        ForecastHolder holder = new ForecastHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ForecastHolder holder, int position)
    {
        Forecast f = list.get(position);
        String start = f.cond.code_d.toString();
        if(start.startsWith("1"))
            holder.icon.setImageResource(R.drawable.sunny);
        else if(start.startsWith("2"))
            holder.icon.setImageResource(R.drawable.windy);
        else if(start.startsWith("3"))
            holder.icon.setImageResource(R.drawable.rainy);
        else if(start.startsWith("4"))
            holder.icon.setImageResource(R.drawable.snowy);
        else if(start.startsWith("5"))
            holder.icon.setImageResource(R.drawable.foggy);
        else
            holder.icon.setImageResource(R.drawable.unknow);
        if(position == 0)
            holder.day.setText(f.date+("今天"));
        else
            holder.day.setText(f.date);
        holder.detail.setText(f.cond.txt_d+" | "+f.wind.dir);
        holder.max.setText(f.tmp.max+"° / ");
        holder.min.setText(f.tmp.min+"°");
    }

    @Override
    public int getItemCount()
    {
        if(list == null)
            return 0;

        return list.size();

    }

    class ForecastHolder extends RecyclerView.ViewHolder
   {
       public ImageView icon;
       public TextView max;
       public TextView min;
       public TextView day;
       public TextView detail;
       public ForecastHolder(View itemView)
       {
           super(itemView);
           icon = (ImageView) itemView.findViewById(R.id.forecast_item_icon);
           max = (TextView) itemView.findViewById(R.id.forecast_item_max);
           min = (TextView) itemView.findViewById(R.id.forecast_item_min);
           day = (TextView) itemView.findViewById(R.id.forecast_item_day);
           detail = (TextView) itemView.findViewById(R.id.forecast_item_detail);
       }
   }


}
