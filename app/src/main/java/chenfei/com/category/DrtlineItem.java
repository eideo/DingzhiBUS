package chenfei.com.category;

import java.io.Serializable;

/** * @author  作者 E-mail: 
 * @date 创建时间：2015-7-21 上午10:37:46 
 * @version 1.0 
 * @parameter  
 * @since 
 * @return  */
public class DrtlineItem implements Serializable{

	/**
	 * 
	 */
	public String lineid = "";
	public String startstop = "";//开始站
	public String endstop = "";//终点站
	public String passpointname = "";//经过点的名字
	public String passpointlating = "";//经过点的经纬度
	public String fahuilineid = "";//折返路线id	
	public DrtlineItem() {
		
	}
	
	public DrtlineItem(String lineid, String startstop, String endstop,
			String passpointname, String passpointlating, String fahuilineid) {
		super();
		this.lineid = lineid;
		this.startstop = startstop;
		this.endstop = endstop;
		this.passpointname = passpointname;
		this.passpointlating = passpointlating;
		this.fahuilineid = fahuilineid;
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
	public String getPasspointname() {
		return passpointname;
	}
	public void setPasspointname(String passpointname) {
		this.passpointname = passpointname;
	}
	public String getPasspointlating() {
		return passpointlating;
	}
	public void setPasspointlating(String passpointlating) {
		this.passpointlating = passpointlating;
	}
	public String getFahuilineid() {
		return fahuilineid;
	}
	public void setFahuilineid(String fahuilineid) {
		this.fahuilineid = fahuilineid;
	}
	

}
