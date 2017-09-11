package demo.yc.ycweather.models.weather;

/**
 * Created by Administrator on 2017/4/26 0026.
 */

public class Now
{

    public Cond cond;

    public String tmp;

    public String fl;

    public String hum;

    public String pcpn;

    public String pres;

    public String vis;

    public Wind wind;

    public class Cond{
        public String code;

        public String txt;
    }

    public class Wind{

        public String deg;

        public String dir;

        public String sc;

        public String spd;
    }
}
