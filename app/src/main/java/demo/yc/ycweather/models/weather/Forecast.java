package demo.yc.ycweather.models.weather;

/**
 * Created by Administrator on 2017/4/26 0026.
 */

public class Forecast
{
    public Astro astro;

    public Cond cond;

    public Tmp tmp;

    public Wind wind;

    public String date;

    public String hum;

    public String pcpn;

    public String pop;

    public String pres;

    public String uv;

    public String vis;

    public class Astro{
        public String mr;

        public String ms;

        public String sr;

        public String ss;
    }

    public class Cond{

        public String code_d;

        public String code_n;

        public String txt_d;

        public String txt_n;
    }

    public class Tmp{
        public String max;

        public String min;
    }

    public class Wind{
        public String deg;

        public String dir;

        public String sc;

        public String spd;
    }

}
