package chenfei.com.category;

import java.util.List;

/**
 * Created by Administrator on 2016/8/22.
 */
public class MyOrderInfo {


    /**
     * urlflag : success
     * status : 1
     * data : [{"orderid":"082922113718616","lineid":"1","linetype":"0","userid":"11","startstop":"高庙村地铁站","endstop":"朝天门","tujingdi":"","distance":"15.3","starttime":"8:30","endtime":"9:03","runtime":"33","paymoney":"5","buytime":"2015-08-29 22:11:38","chengchedate":"2015-08-29","ticketnum":"1张","buytype":"现金","totolmoney":"5元","city":"重庆市","buystatus":"0"},{"orderid":"082414484966583","lineid":"1","linetype":"0","userid":"11","startstop":"高庙村地铁站","endstop":"朝天门","tujingdi":"","distance":"15.3","starttime":"8:29","endtime":"9：03","runtime":"33","paymoney":"5","buytime":"2015-08-24 14:50:17","chengchedate":"2015-08-26","ticketnum":"1张","buytype":"现金","totolmoney":"5元","city":"重庆市","buystatus":"0"},{"orderid":"0825143104-1170","lineid":"1","linetype":"0","userid":"11","startstop":"高庙村地铁站","endstop":"朝天门","tujingdi":"","distance":"15.3","starttime":"8:30","endtime":"9:03","runtime":"33","paymoney":"5","buytime":"2015-08-25 14:32:34","chengchedate":"2015-08-26","ticketnum":"1张","buytype":"现金","totolmoney":"5元","city":"重庆市","buystatus":"0"},{"orderid":"081213365511997","lineid":"2","linetype":"0","userid":"11","startstop":"高庙村地铁站","endstop":"南坪地铁站","tujingdi":"石桥铺,石油路,","distance":"14.2","starttime":"8:30","endtime":"8：50","runtime":"28","paymoney":"4","buytime":"2015-08-13 13:38:43","chengchedate":"2015-08-14","ticketnum":"1张","buytype":"现金","totolmoney":"4元","city":"重庆市","buystatus":"1"},{"orderid":"081213365411994","lineid":"2","linetype":"0","userid":"11","startstop":"高庙村地铁站","endstop":"南坪地铁站","tujingdi":"石桥铺,石油路,","distance":"14.2","starttime":"8:29","endtime":"8：50","runtime":"28","paymoney":"4","buytime":"2015-08-12 13:38:43","chengchedate":"2015-08-12","ticketnum":"1张","buytype":"现金","totolmoney":"4元","city":"重庆市","buystatus":"1"},{"orderid":"081213365511994","lineid":"2","linetype":"0","userid":"11","startstop":"高庙村地铁站","endstop":"南坪地铁站","tujingdi":"石桥铺,石油路,","distance":"14.2","starttime":"8:40","endtime":"8：50","runtime":"28","paymoney":"4","buytime":"2015-08-11 13:38:43","chengchedate":"2015-08-11","ticketnum":"1张","buytype":"现金","totolmoney":"4元","city":"重庆市","buystatus":"1"}]
     */

    private String urlflag;
    private String status;
    /**
     * orderid : 082922113718616
     * lineid : 1
     * linetype : 0
     * userid : 11
     * startstop : 高庙村地铁站
     * endstop : 朝天门
     * tujingdi :
     * distance : 15.3
     * starttime : 8:30
     * endtime : 9:03
     * runtime : 33
     * paymoney : 5
     * buytime : 2015-08-29 22:11:38
     * chengchedate : 2015-08-29
     * ticketnum : 1张
     * buytype : 现金
     * totolmoney : 5元
     * city : 重庆市
     * buystatus : 0
     */

    private List<DataBean> data;

    public String getUrlflag() {
        return urlflag;
    }

    public void setUrlflag(String urlflag) {
        this.urlflag = urlflag;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String orderid;
        private String lineid;
        private String linetype;
        private String userid;
        private String startstop;
        private String endstop;
        private String tujingdi;
        private String distance;
        private String starttime;
        private String endtime;
        private String runtime;
        private String paymoney;
        private String buytime;
        private String chengchedate;
        private String ticketnum;
        private String buytype;
        private String totolmoney;
        private String city;
        private String buystatus;

        public String getOrderid() {
            return orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }

        public String getLineid() {
            return lineid;
        }

        public void setLineid(String lineid) {
            this.lineid = lineid;
        }

        public String getLinetype() {
            return linetype;
        }

        public void setLinetype(String linetype) {
            this.linetype = linetype;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getStartstop() {
            return startstop;
        }

        public void setStartstop(String startstop) {
            this.startstop = startstop;
        }

        public String getEndstop() {
            return endstop;
        }

        public void setEndstop(String endstop) {
            this.endstop = endstop;
        }

        public String getTujingdi() {
            return tujingdi;
        }

        public void setTujingdi(String tujingdi) {
            this.tujingdi = tujingdi;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getStarttime() {
            return starttime;
        }

        public void setStarttime(String starttime) {
            this.starttime = starttime;
        }

        public String getEndtime() {
            return endtime;
        }

        public void setEndtime(String endtime) {
            this.endtime = endtime;
        }

        public String getRuntime() {
            return runtime;
        }

        public void setRuntime(String runtime) {
            this.runtime = runtime;
        }

        public String getPaymoney() {
            return paymoney;
        }

        public void setPaymoney(String paymoney) {
            this.paymoney = paymoney;
        }

        public String getBuytime() {
            return buytime;
        }

        public void setBuytime(String buytime) {
            this.buytime = buytime;
        }

        public String getChengchedate() {
            return chengchedate;
        }

        public void setChengchedate(String chengchedate) {
            this.chengchedate = chengchedate;
        }

        public String getTicketnum() {
            return ticketnum;
        }

        public void setTicketnum(String ticketnum) {
            this.ticketnum = ticketnum;
        }

        public String getBuytype() {
            return buytype;
        }

        public void setBuytype(String buytype) {
            this.buytype = buytype;
        }

        public String getTotolmoney() {
            return totolmoney;
        }

        public void setTotolmoney(String totolmoney) {
            this.totolmoney = totolmoney;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getBuystatus() {
            return buystatus;
        }

        public void setBuystatus(String buystatus) {
            this.buystatus = buystatus;
        }
    }
}
