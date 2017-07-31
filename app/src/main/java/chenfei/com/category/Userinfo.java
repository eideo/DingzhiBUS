package chenfei.com.category;

import java.util.List;

/**
 * Created by chenfei on 2016/7/23.
 */
public class Userinfo  {


    /**
     * urlflag : success
     * status : 1
     * data : [{"userid":"11","username":"陈飞","password":"c4ca4238a0b923820dcc509a6f75849b","sex":"男","telephone":"13628453418","address":"","yue":"0.00","email":"457771023@qq.com","faqilinenum":"","yuyuelinenum":"","homebaiduzuobiao":"","homeaddress":"","companybaiduzuobiao":"","companyaddress":""}]
     */

    private String urlflag;
    private String status;
    /**
     * userid : 11
     * username : 陈飞
     * password : c4ca4238a0b923820dcc509a6f75849b
     * sex : 男
     * telephone : 13628453418
     * address :
     * yue : 0.00
     * email : 457771023@qq.com
     * faqilinenum :
     * yuyuelinenum :
     * homebaiduzuobiao :
     * homeaddress :
     * companybaiduzuobiao :
     * companyaddress :
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
        private String userid;
        private String username;
        private String password;
        private String sex;
        private String telephone;
        private String address;
        private String yue;
        private String email;
        private String faqilinenum;
        private String yuyuelinenum;
        private String homebaiduzuobiao;
        private String homeaddress;
        private String companybaiduzuobiao;
        private String companyaddress;

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getYue() {
            return yue;
        }

        public void setYue(String yue) {
            this.yue = yue;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFaqilinenum() {
            return faqilinenum;
        }

        public void setFaqilinenum(String faqilinenum) {
            this.faqilinenum = faqilinenum;
        }

        public String getYuyuelinenum() {
            return yuyuelinenum;
        }

        public void setYuyuelinenum(String yuyuelinenum) {
            this.yuyuelinenum = yuyuelinenum;
        }

        public String getHomebaiduzuobiao() {
            return homebaiduzuobiao;
        }

        public void setHomebaiduzuobiao(String homebaiduzuobiao) {
            this.homebaiduzuobiao = homebaiduzuobiao;
        }

        public String getHomeaddress() {
            return homeaddress;
        }

        public void setHomeaddress(String homeaddress) {
            this.homeaddress = homeaddress;
        }

        public String getCompanybaiduzuobiao() {
            return companybaiduzuobiao;
        }

        public void setCompanybaiduzuobiao(String companybaiduzuobiao) {
            this.companybaiduzuobiao = companybaiduzuobiao;
        }

        public String getCompanyaddress() {
            return companyaddress;
        }

        public void setCompanyaddress(String companyaddress) {
            this.companyaddress = companyaddress;
        }
    }
}
