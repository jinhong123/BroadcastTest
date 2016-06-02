package com.jinhong.broadcastbestpractice;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jinhong on 2/6/16.
 */
public class ActivityCollector {
    public static List<Activity>activities = new ArrayList<Activity>();
    public static void addActivity(Activity activity){
        activities.add(activity);
    }
    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }
    public static void finishAll(){
        for(Activity activity :activities){
            if(!activity.isFinishing()){
                activity.finish();
            }
        }
    }
}
