package com.example.huy.managertimer.databases;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.huy.managertimer.R;
import com.example.huy.managertimer.fragment.TaskFragment;
import com.example.huy.managertimer.model.TaskItem;
import com.google.gson.Gson;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Laptop88 on 10/15/2016.
 */

public class HelperClass {
    public static void saveTasks(Context mContext) {
        SharedPreferences preferences = mContext.getSharedPreferences(mContext.getString(R.string.setting_pref), MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("noTask", TaskFragment.taskItems.size());
        editor.commit();
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(mContext.getString(R.string.tasks_infos), MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        Gson gson = new Gson();
        for (int i = 0; i < TaskFragment.taskItems.size(); i++) {
            String json = gson.toJson(TaskFragment.taskItems.get(i)); // myObject - instance of MyObject
            prefsEditor.putString("TaskItem" + i, json);
        }
        String json = gson.toJson(TaskFragment.defaultTaskItem);
        prefsEditor.putString(mContext.getString(R.string.defaultTask), json);
        prefsEditor.commit();
        Log.d("saveData", TaskFragment.defaultTaskItem.getWTime() + "");
        for (int i = 0; i < TaskFragment.taskItems.size(); i++) {
            Log.d("saveData" + (i + 1), TaskFragment.taskItems.get(i).getWTime() + "");
        }
    }

    public static void getTasks(Context mContext) {
        SharedPreferences preferences = mContext.getSharedPreferences(mContext.getString(R.string.setting_pref), MODE_PRIVATE);
        int tasksSize = preferences.getInt("noTask", 0);
        ArrayList<TaskItem> taskItems = new ArrayList<>();
        SharedPreferences tasksPrefs = mContext.getSharedPreferences(mContext.getString(R.string.tasks_infos), Context.MODE_PRIVATE);
        Gson gson = new Gson();
        for (int i = 0; i < tasksSize; i++) {
            String json = tasksPrefs.getString("TaskItem" + i, "");
            TaskItem tmp = gson.fromJson(json, TaskItem.class);
            if (tmp != null) {
                taskItems.add(tmp);
            }

        }
        TaskFragment.taskItems = taskItems;
        String json = tasksPrefs.getString(mContext.getString(R.string.defaultTask), "");
        TaskItem tmp = gson.fromJson(json, TaskItem.class);
        if (tmp != null) {
            TaskFragment.defaultTaskItem = tmp;
        }
        Log.d("getData", TaskFragment.defaultTaskItem.getWTime() + "");
        for (int i = 0; i < TaskFragment.taskItems.size(); i++) {
            Log.d("getData" + (i + 1), TaskFragment.taskItems.get(i).getWTime() + "");
        }
    }


}
