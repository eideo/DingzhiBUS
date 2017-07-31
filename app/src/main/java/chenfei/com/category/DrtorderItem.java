package chenfei.com.category;

import java.io.Serializable;

/** * @author  作者 E-mail: 
 * @date 创建时间：2015-7-21 上午10:37:46 
 * @version 1.0 
 * @parameter  
 * @since 
 * @return  */
public class DrtorderItem implements Serializable{

	/**
	 * 
	 */
	
	public String orderid = "";
	public String lineid = "";
	public String userid = "";
	public String startstop = "";
	public String endstop = "";
	public String upstop = "";
	public String downstop = "";
	public String distance = "";
	public String runtime = "";
	public String paymoney = "";
	public String buytime = "";
	public String ticketnum = "";
	public String buytype = "";
	public String totolmoney = "";
	public String city="";
	public DrtorderItem(){
		
	}
	public DrtorderItem(String orderid, String lineid, String userid,String startstop,
			String endstop, String upstop, String downstop, String distance,
			String runtime, String paymoney, String buytime, String ticketnum,
			String buytype, String totolmoney, String city) {
		super();
		this.orderid = orderid;
		this.lineid = lineid;
		this.userid = userid;
		this.startstop = startstop;
		this.endstop = endstop;
		this.upstop = upstop;
		this.downstop = downstop;
		this.distance = distance;
		this.runtime = runtime;
		this.paymoney = paymoney;
		this.buytime = buytime;
		this.ticketnum = ticketnum;
		this.buytype = buytype;
		this.totolmoney = totolmoney;
		this.city = city;
	}
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
	public String getUpstop() {
		return upstop;
	}
	public void setUpstop(String upstop) {
		this.upstop = upstop;
	}
	public String getDownstop() {
		return downstop;
	}
	public void setDownstop(String downstop) {
		this.downstop = downstop;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
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
	

}
