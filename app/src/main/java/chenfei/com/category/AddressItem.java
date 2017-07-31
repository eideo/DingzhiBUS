package chenfei.com.category;

import java.io.Serializable;

import com.baidu.mapapi.model.LatLng;

/** * @author  作者 E-mail: 
 * @date 创建时间：2015-7-21 上午10:37:46 
 * @version 1.0 
 * @parameter  
 * @since 
 * @return  */
public class AddressItem implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String title = "";
	public String text = "";//名字
	public String imagecheak = "";//地址确认，默认不显示
	public LatLng latLng;
	public AddressItem() {
		
	}

	public AddressItem(String title, String text, String imagecheak,
			LatLng latLng) {
		super();
		this.title = title;
		this.text = text;
		this.imagecheak = imagecheak;
		this.latLng = latLng;
	}

	public final  LatLng getLatLng() {
		return latLng;
	}

	public final  void setLatLng(LatLng latLng) {
		this.latLng = latLng;
	}

	public  final   String getTitle() {
		return title;
	}
	public  final  void setTitle(String title) {
		this.title = title;
	}
	public final   String getText() {
		return text;
	}
	public  final  void setText(String text) {
		this.text = text;
	}
	public  final  String getImagecheak() {
		return imagecheak;
	}
	public  final  void setImagecheak(String imagecheak) {
		this.imagecheak = imagecheak;
	}

}
