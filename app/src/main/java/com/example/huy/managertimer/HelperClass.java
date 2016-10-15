package com.example.huy.managertimer;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.huy.managertimer.activity.MainActivity;
import com.example.huy.managertimer.fragment.TaskFragment;
import com.google.gson.Gson;

import java.util.ArrayList;

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
    public static void getTasks (Context mContext){
        SharedPreferences preferences = mContext.getSharedPreferences(mContext.getString(R.string.setting_pref), MODE_PRIVATE);
        int tasksSize = preferences.getInt("noTask", 0);
        ArrayList<Task> tasks = new ArrayList<>();
        SharedPreferences tasksPrefs = mContext.getSharedPreferences(mContext.getString(R.string.tasks_infos), Context.MODE_PRIVATE);
        Gson gson = new Gson();
        for (int i = 0; i < tasksSize; i++) {
            String json = tasksPrefs.getString("Task"+i, "");
            Task tmp = gson.fromJson(json, Task.class);
            if (tmp!=null){
                tasks.add(tmp);
            }

        }
        TaskFragment.tasks = tasks;
        String json = tasksPrefs.getString(mContext.getString(R.string.defaultTask), "");
        Task tmp = gson.fromJson(json, Task.class);
        if (tmp!=null){
            TaskFragment.defaultTask = tmp;
        }
        Log.d("getData", TaskFragment.defaultTask.getWTime()+"");
        for (int i = 0; i < TaskFragment.tasks.size(); i++) {
            Log.d("getData"+(i+1), TaskFragment.tasks.get(i).getWTime()+"");
        }
    }


}
