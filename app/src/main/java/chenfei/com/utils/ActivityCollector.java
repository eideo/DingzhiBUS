package chenfei.com.utils;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

/** * @author  作�? E-mail: 
 * @date 创建时间�?015-7-30 上午10:16:05 
 * @version 1.0 
 * @parameter  
 * @since 
 * @return  */
public class ActivityCollector {
	public static List<Activity> activities = new ArrayList<Activity>();  
	 
    public static void addActivity(Activity activity) {  
        activities.add(activity);  
    }  
 
    public static void removeActivity(Activity activity) {  
        activities.remove(activity);  
    }  
 
    public static void finishAll() {  
        for (Activity activity : activities) {  
            if (!activity.isFinishing()) {  
                activity.finish();  
            }  
        }  
    }  

}
