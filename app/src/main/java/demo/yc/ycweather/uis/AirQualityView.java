package demo.yc.ycweather.uis;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import demo.yc.ycweather.R;

/**
 * Created by Administrator on 2017/5/16 0016.
 */

public class AirQualityView extends View
{
    //底部主标题
    private String mainTitle;
    private int mainTitleColor;
    private float mainTitleSize;

    //内部次标题
    private String subTitle;
    private int subTitleColor;
    private float subTitleSize;

    //总数，当前数字
    private int totalNum;
    private int currentNum;
    private int currentNumColor;
    private float currentNumSize;

    //圆大小,颜色
    private float cycleWidth;
    private int cycleColor;
    private int cycleBg;


    private Paint mPaint;

    private Paint textPaint;
    private Rect mRect;

    private Paint subPaint;
    private Rect subRect;

    private Paint numPaint;
    private Rect numRect;


    private int process = 0;


    public AirQualityView(Context context)
    {
        this(context,null);
    }

    public AirQualityView(Context context, AttributeSet attrs)
    {
        this(context, attrs,0);
    }

    public AirQualityView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.AirQualityView,0,0);
        mainTitle = ta.getString(R.styleable.AirQualityView_mainTitle);
        mainTitleColor = ta.getColor(R.styleable.AirQualityView_mainTitleColor, Color.GRAY);
        mainTitleSize = ta.getDimension(R.styleable.AirQualityView_mainTitleSize,15);

        subTitle = ta.getString(R.styleable.AirQualityView_subTitle);
        subTitleColor = ta.getColor(R.styleable.AirQualityView_subTitleColor, Color.GRAY);
        subTitleSize = ta.getDimension(R.styleable.AirQualityView_subTitleSize,13);

        currentNum = ta.getInt(R.styleable.AirQualityView_currentNum,0);
        currentNumSize = ta.getDimension(R.styleable.AirQualityView_currentNumSize,20);
        currentNumColor = ta.getColor(R.styleable.AirQualityView_currentNumColor,Color.GREEN);

        totalNum = ta.getInt(R.styleable.AirQualityView_totalNum,360);

        cycleWidth = ta.getDimension(R.styleable.AirQualityView_cycleWidth,10);
        cycleColor = ta.getColor(R.styleable.AirQualityView_cycleColor,Color.GREEN);
        cycleBg = ta.getColor(R.styleable.AirQualityView_cyclebg,Color.GRAY);


        ta.recycle();

        mPaint = new Paint();
        mRect = new Rect();
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(mainTitleSize);
        textPaint.setColor(mainTitleColor);
        textPaint.getTextBounds(mainTitle,0,mainTitle.length(),mRect);

        subPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        subRect = new Rect();
        subPaint.setTextSize(subTitleSize);
        subPaint.setColor(subTitleColor);
        subPaint.getTextBounds(subTitle,0,subTitle.length(),subRect);

        numPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        numRect = new Rect();
        numPaint.setTextSize(currentNumSize);
        numPaint.setColor(currentNumColor);
        ;


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        int center = (int) (getWidth()/2.2f);

        int smallRadius = (int) (center - cycleWidth);
        mPaint.setStrokeWidth(cycleWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        RectF oval = new RectF(center-smallRadius,center-smallRadius,
                center+smallRadius,center+smallRadius);

        //画背景
        mPaint.setColor(cycleBg);
        canvas.drawArc(oval,135,270,false,mPaint);

        //画显示颜色
        mPaint.setColor(cycleColor);
        canvas.drawArc(oval,135,process,false,mPaint);

        //画mainTitle
        canvas.drawText(mainTitle,(center-mRect.width()/2),
                (center*1.9f + mRect.height()/2),textPaint);

        //画num
        numPaint.getTextBounds(currentNum+"",0,new String(currentNum+"").length(),numRect);
        canvas.drawText(currentNum+"",(center-numRect.width()/2),
                (center+numRect.height()/2),numPaint);

        //画subTitle
        canvas.drawText(subTitle,(center-subRect.width()/2),
                (center+numRect.height()/2+subRect.height()*1.2f),subPaint);
    }

    public void setCurrentNum(int num)
    {
        if(num > totalNum)
            num = totalNum;

        process = (int) (num*1.0f/totalNum*270);

        currentNum = num;

        invalidate();

    }

}
