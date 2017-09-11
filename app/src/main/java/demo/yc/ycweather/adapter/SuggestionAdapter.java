package demo.yc.ycweather.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import demo.yc.ycweather.R;
import demo.yc.ycweather.models.weather.Suggestion;
import demo.yc.ycweather.views.ISuggestionListener;

import static demo.yc.ycweather.adapter.SuggestionBaseHolder.CONTENT_VIEW;
import static demo.yc.ycweather.adapter.SuggestionBaseHolder.HEAD_VIEW;
import static demo.yc.ycweather.adapter.SuggestionBaseHolder.SPLIT_VIEW;

/**
 * Created by Administrator on 2017/5/15 0015.
 */

public class SuggestionAdapter extends RecyclerView.Adapter<SuggestionBaseHolder>
{

    private LayoutInflater inflater;
    private Suggestion suggestion;
    private ISuggestionListener listener;



    public SuggestionAdapter(Context context,Suggestion suggestion)
    {
        inflater = LayoutInflater.from(context);
        this.suggestion = suggestion;
    }

    @Override
    public SuggestionBaseHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view ;
        final SuggestionBaseHolder holder;

        if(viewType == HEAD_VIEW)
        {
            view = inflater.inflate(R.layout.suggestion_item_header,parent,false);
            holder = new HeaderHolder(view);
        }else if(viewType == CONTENT_VIEW)
        {
            view = inflater.inflate(R.layout.suggestion_item_content,parent,false);
            holder = new ContentHolder(view);
        }else
        {
            view = inflater.inflate(R.layout.suggestion_item_split_line,parent,false);
            holder = new SplitLineHolder(view);
        }

        view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                listener.onItemClick(view,holder.getAdapterPosition());
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(SuggestionBaseHolder holder, int position)
    {
        holder.bindDate(suggestion,position);
    }

    @Override
    public int getItemCount()
    {
        return 15;
    }

    @Override
    public int getItemViewType(int position)
    {

        if(position == 1 || position == 6
                || position == 9 || position == 12)
            return HEAD_VIEW;
        else if (position >= 2 && position <= 4
                || position == 7 || position == 10
                || position >=13)
            return CONTENT_VIEW;
        else
            return SPLIT_VIEW;
    }

    public void setOnItemClickListener(ISuggestionListener listener)
    {
        this.listener = listener;
    }

}
