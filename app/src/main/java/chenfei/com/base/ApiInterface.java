package chenfei.com.base;

/**
 * Created by Administrator on 2016/7/9.
 */
public class ApiInterface {

    public static final String baseurl = "http://chenfei1988.applinzi.com/yichengbus/houtai/";
    //public static final String  baseurl="http://120.25.154.143:91/APP/";
    /**
     * 账号登录
     */
    public static final String AccountLogin = baseurl + "newlogin.php?";

    /**
     * 账号注册
     */
    public static final String AccountRegister = baseurl + "register.php?";
    /**
     * 包车订单提交
     */
    public static final String AddBaocheOrder = baseurl + "new/addbaocheorder.php?";
    /**
     * 计算路线
     */
    public static final String Calculatorlinedistance = baseurl + "new/calculatorlinedistance.php?";

    /**
     * 获取DRT路线
     */
    public static final String Getdrtlinelists = baseurl + "new/getdrtlinelists.php?";

    /**
     * 获取保存订单
     */
    public static final String Savedrtorder = baseurl + "new/savedrtorder.php?";
    /**
     * 保存收藏路线
     */
    public static final String Savemyfaveratedrtline = baseurl + "new/savemyfaveratedrtline.php?";

    /**
     * 更新用户信息
     */
    public static final String Updateuserinformation = baseurl + "new/updateuserinformation.php?";

    /**
     * 获取DRT线路上车辆的坐标坐标
     */
    public static final String Getdrtbuszuobiao = baseurl + "new/getdrtbuszuobiao.php?";

    /**
     * 获取路线
     */
    public static final String Getlinelists = baseurl + "new/getlinelists.php?";

    /**
     * 预约路线
     */
    public static final String Doyuyue = baseurl + "new/doyuyue.php?";
    /**
     * 获取班车订单
     */
    public static final String Getmyorderlist = baseurl + "new/getmyorderlist.php?";







}
