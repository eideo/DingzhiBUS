package chenfei.com.category;

import java.util.List;

/**
 * Created by Administrator on 2016/8/18.
 */
public class GetLinesInfo {


    /**
     * urlflag : success
     * status : 1
     * data : [{"lineid":"4","startstop":"重庆北站","endstop":"菜园坝火车站","distance":"34.4","starttime":"7:30","endtime":"9:03","runtime":"44","paymoney":"0","registerusertelephone":"1,4543,","registerpersonnum":"5","faqiren":"","tujingdi":"","type":"1","city":"重庆市"},{"lineid":"7","startstop":"南京路","endstop":"菜园坝火车","distance":"23.4","starttime":"7:30","endtime":"9:03","runtime":"44","paymoney":"0","registerusertelephone":"1,2,4543,","registerpersonnum":"9","faqiren":"","tujingdi":"","type":"1","city":"重庆市"},{"lineid":"13","startstop":"兴隆路26号数码大厦","endstop":"高庙村-地铁站","distance":"11.0","starttime":"09:58","endtime":"10:22","runtime":"24","paymoney":"","registerusertelephone":"136284534,","registerpersonnum":"1","faqiren":"1","tujingdi":"高庙村,石桥铺","type":"1","city":"重庆市"},{"lineid":"25","startstop":"重庆北站北广场-公交车站","endstop":"朝天门","distance":"14.0","starttime":"11:39","endtime":"12:09","runtime":"30","paymoney":"","registerusertelephone":"136284534,","registerpersonnum":"1","faqiren":"1","tujingdi":"高庙村,石桥铺","type":"1","city":"重庆市"},{"lineid":"30","startstop":"兴隆路/北城天街(路口)","endstop":"金银湾小区","distance":"9.0","starttime":"14:27","endtime":"14:50","runtime":"23","paymoney":"","registerusertelephone":"136284534,","registerpersonnum":"1","faqiren":"1","tujingdi":"高庙村,石桥铺","type":"1","city":"重庆市"},{"lineid":"5678","startstop":"翠林路-道路","endstop":"北桥头立交-道路","distance":"","starttime":"16:10","endtime":"","runtime":"","paymoney":"","registerusertelephone":"13628453418,","registerpersonnum":"1","faqiren":"11","tujingdi":"","type":"1","city":"重庆市"},{"lineid":"321","startstop":"兴隆路/北城天街(路口)","endstop":"金银湾小区","distance":"9.0","starttime":"14:27","endtime":"14:50","runtime":"23","paymoney":"","registerusertelephone":"136284534,","registerpersonnum":"1","faqiren":"1","tujingdi":"高庙村,石桥铺","type":"1","city":"重庆市"},{"lineid":"999","startstop":"兴隆路/北城天街(路口4)","endstop":"金银湾小区","distance":"9.0","starttime":"14:27","endtime":"14:50","runtime":"23","paymoney":"","registerusertelephone":"13628453418,","registerpersonnum":"1","faqiren":"11","tujingdi":"高庙村,石桥铺","type":"1","city":"重庆市"},{"lineid":"888","startstop":"兴隆路/北城天街(路口)","endstop":"金银湾小区","distance":"9.0","starttime":"14:27","endtime":"14:50","runtime":"23","paymoney":"","registerusertelephone":"13628453418,","registerpersonnum":"1","faqiren":"1","tujingdi":"高庙村,石桥铺","type":"1","city":"重庆市"},{"lineid":"5677","startstop":"北城天街-道路","endstop":"重庆南坪汽车站","distance":"10","starttime":"15:02","endtime":"15:29","runtime":"27","paymoney":"","registerusertelephone":"13628453418,","registerpersonnum":"1","faqiren":"11","tujingdi":"","type":"1","city":"重庆市"},{"lineid":"5701","startstop":"兴隆路/北城天街(路口)","endstop":"黄花园大桥北-公交车站","distance":"4","starttime":"09:38","endtime":"","runtime":"8","paymoney":"","registerusertelephone":"13628453418,","registerpersonnum":"1","faqiren":"11","tujingdi":"","type":"1","city":"重庆市"},{"lineid":"5667","startstop":"兴隆路26号数码大厦","endstop":"北桥头立交-道路","distance":"4","starttime":"09:12","endtime":"09:22","runtime":"10","paymoney":"","registerusertelephone":"13628453419,","registerpersonnum":"1","faqiren":"11","tujingdi":"","type":"1","city":"重庆市"},{"lineid":"5669","startstop":"嘉陵江滨江路","endstop":"南滨路钟楼广场","distance":"5","starttime":"11:42","endtime":"11:53","runtime":"11","paymoney":"","registerusertelephone":"13628453418,","registerpersonnum":"1","faqiren":"11","tujingdi":"","type":"1","city":"重庆市"},{"lineid":"5668","startstop":"嘉陵江滨江路","endstop":"南滨路钟楼广场","distance":"5","starttime":"11:42","endtime":"11:54","runtime":"12","paymoney":"","registerusertelephone":"13628453418,","registerpersonnum":"1","faqiren":"11","tujingdi":"","type":"1","city":"重庆市"},{"lineid":"5702","startstop":"兴隆路/北城天街(路口)","endstop":"黄花园大桥北-公交车站","distance":"4","starttime":"09:38","endtime":"","runtime":"8","paymoney":"","registerusertelephone":"13628453418,","registerpersonnum":"1","faqiren":"11","tujingdi":"","type":"1","city":"重庆市"},{"lineid":"5679","startstop":"经二路-道路","endstop":"汽车东站","distance":"","starttime":"22:17","endtime":"","runtime":"","paymoney":"","registerusertelephone":"13628453418,","registerpersonnum":"1","faqiren":"11","tujingdi":"","type":"1","city":"宝鸡市"},{"lineid":"5680","startstop":"经二路-道路","endstop":"宝鸡市经二路派出所","distance":"","starttime":"22:17","endtime":"","runtime":"","paymoney":"","registerusertelephone":"13628453418,","registerpersonnum":"1","faqiren":"11","tujingdi":"","type":"1","city":"宝鸡市"},{"lineid":"5681","startstop":"经二路小学","endstop":"经二路邮政支局","distance":"2","starttime":"13:19","endtime":"","runtime":"4","paymoney":"","registerusertelephone":"13628453418,","registerpersonnum":"1","faqiren":"11","tujingdi":"","type":"1","city":"宝鸡市"},{"lineid":"5682","startstop":"经二路小学","endstop":"经二路邮政支局","distance":"2","starttime":"13:19","endtime":"","runtime":"4","paymoney":"","registerusertelephone":"13628453418,","registerpersonnum":"1","faqiren":"11","tujingdi":"","type":"1","city":"宝鸡市"},{"lineid":"5703","startstop":"陈家坪公交枢纽站","endstop":"嘉州路-地铁站","distance":"13","starttime":"21:00","endtime":"","runtime":"27","paymoney":"","registerusertelephone":"13628453418,","registerpersonnum":"1","faqiren":"11","tujingdi":"","type":"1","city":"重庆市"},{"lineid":"5704","startstop":"猪八戒众创空间","endstop":"重庆渝澳嘉陵江大桥-道路","distance":"9","starttime":"09:58","endtime":"","runtime":"22","paymoney":"","registerusertelephone":"13628453418,","registerpersonnum":"1","faqiren":"11","tujingdi":"","type":"1","city":"重庆市"}]
     */

    private String urlflag;
    private String status;
    /**
     * lineid : 4
     * startstop : 重庆北站
     * endstop : 菜园坝火车站
     * distance : 34.4
     * starttime : 7:30
     * endtime : 9:03
     * runtime : 44
     * paymoney : 0
     * registerusertelephone : 1,4543,
     * registerpersonnum : 5
     * faqiren :
     * tujingdi :
     * type : 1
     * city : 重庆市
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
        private String lineid;
        private String startstop;
        private String endstop;
        private String distance;
        private String starttime;
        private String endtime;
        private String runtime;
        private String paymoney;
        private String registerusertelephone;
        private String registerpersonnum;
        private String faqiren;
        private String tujingdi;
        private String type;
        private String city;

        public String getLineid() {
            return lineid;
        }

        public void setLineid(String lineid) {
            this.lineid = lineid;
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

        public String getRegisterusertelephone() {
            return registerusertelephone;
        }

        public void setRegisterusertelephone(String registerusertelephone) {
            this.registerusertelephone = registerusertelephone;
        }

        public String getRegisterpersonnum() {
            return registerpersonnum;
        }

        public void setRegisterpersonnum(String registerpersonnum) {
            this.registerpersonnum = registerpersonnum;
        }

        public String getFaqiren() {
            return faqiren;
        }

        public void setFaqiren(String faqiren) {
            this.faqiren = faqiren;
        }

        public String getTujingdi() {
            return tujingdi;
        }

        public void setTujingdi(String tujingdi) {
            this.tujingdi = tujingdi;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }
    }
}
