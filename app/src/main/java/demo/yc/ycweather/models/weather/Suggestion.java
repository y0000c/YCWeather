package demo.yc.ycweather.models.weather;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/26 0026.
 */

public class Suggestion implements Serializable
{

    public Air air; // 空气指数

    public Comf comf;  //舒适度指数

    public Cw cw;  //洗车指数

    public Drsg drsg;  //穿衣指数

    public Flu flu;    //感冒指数

    public Sport sport;  //运动指数

    public Trav trav;  //旅游指数

    public Uv uv;   //紫外线


    public class Air implements Serializable{
        public String brf;

        public String txt;

    }

    public class Comf implements Serializable{
        public String brf;

        public String txt;

    }

    public class Cw implements Serializable{
        public String brf;

        public String txt;
    }


    public class Drsg implements Serializable{
        public String brf;

        public String txt;
    }

    public class Flu implements Serializable{
        public String brf;

        public String txt;
    }

    public class Sport implements Serializable{
        public String brf;

        public String txt;
    }

    public class Trav implements Serializable{
        public String brf;

        public String txt;
    }

    public class Uv implements Serializable{
        public String brf;

        public String txt;
    }


}
