package chenfei.com.category;

import java.io.Serializable;

/**
 * * @author 作者 E-mail:
 * 
 * @date 创建时间：2015-7-23 下午9:45:36
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
public class LineItem implements Serializable {
	public String  lineid="";
	public String startstop = "";
	public String endstop = "";
	public String distance;
	public String starttime = "";
	public String endtime = "";
	public String runtime = "";
	public String paymoney = "";
	public String registerusertelephone = "";
	public String registerpersonnum = "";
	public String tujingdi="";
	public String type = "";
	public String getTujingdi() {
		return tujingdi;
	}

	public void setTujingdi(String tujingdi) {
		this.tujingdi = tujingdi;
	}
	public String city = "";
	public String faqiren = "";
	public String getFaqiren() {
		return faqiren;
	}

	public void setFaqiren(String faqiren) {
		this.faqiren = faqiren;
	}

	public LineItem() {
	}
	
	public LineItem(String lineid, String startstop, String endstop,
			String distance, String starttime, String endtime, String runtime,
			String paymoney, String registerusertelephone, String registerpersonnum,
			String type, String city,String faqiren,String tujingdi) {
		super();
		this.lineid = lineid;
		this.startstop = startstop;
		this.endstop = endstop;
		this.distance = distance;
		this.starttime = starttime;
		this.endtime = endtime;
		this.runtime = runtime;
		this.paymoney = paymoney;
		this.registerusertelephone = registerusertelephone;
		this.registerpersonnum = registerpersonnum;
		this.type = type;
		this.city = city;
		this.faqiren=faqiren;
		this.tujingdi=tujingdi;
	}

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
