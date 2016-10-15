package com.example.huy.managertimer;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.huy.managertimer.activity.MainActivity;
import com.example.huy.managertimer.fragment.TaskFragment;
import com.google.gson.Gson;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Laptop88 on 10/15/2016.
 */

public class HelperClass {
    public static void saveTasks(Context mContext){
        SharedPreferences preferences = mContext.getSharedPreferences(mContext.getString(R.string.setting_pref), MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("noTask", TaskFragment.tasks.size());
        editor.commit();
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(mContext.getString(R.string.tasks_infos), MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        Gson gson = new Gson();
        for (int i = 0; i < TaskFragment.tasks.size(); i++) {
            String json = gson.toJson(TaskFragment.tasks.get(i)); // myObject - instance of MyObject
            prefsEditor.putString("Task"+i, json);
        }
        String json = gson.toJson(TaskFragment.defaultTask);
        prefsEditor.putString(mContext.getString(R.string.defaultTask), json);
        prefsEditor.commit();
        Log.d("saveData", TaskFragment.defaultTask.getWTime()+"");
        for (int i = 0; i < TaskFragment.tasks.size(); i++) {
            Log.d("saveData"+(i+1), TaskFragment.tasks.get(i).getWTime()+"");
        }
    }

}
