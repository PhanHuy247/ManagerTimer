package com.example.huy.managertimer.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.huy.managertimer.HelperClass;
import com.example.huy.managertimer.R;
import com.example.huy.managertimer.Task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Laptop88 on 10/11/2016.
 */

public class AddingTaskDialogFragment extends DialogFragment implements View.OnClickListener{
    EditText edt_title;
    ImageButton imb_cancel, imb_save;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialogfragment_task, container, false);
        getDialog().setTitle("Add task");
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        edt_title = (EditText) rootView.findViewById(R.id.edt_title);
        imb_cancel = (ImageButton) rootView.findViewById(R.id.imb_cancel);
        imb_save = (ImageButton) rootView.findViewById(R.id.imb_save);
        imb_cancel.setOnClickListener(this);
        imb_save.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imb_cancel:
                dismiss();
                break;
            case R.id.imb_save:
                if (!edt_title.getText().equals("")){
                    DateFormat dateFormat = new SimpleDateFormat(getString(R.string.dateFormat));
                    Date date = new Date();
                    Task nTask = new Task(0, edt_title.getText().toString(), dateFormat.format(date));
                    TaskFragment.tasks.add(nTask);
                    HelperClass.saveTasks(getActivity());
                    dismiss();
                }
                break;
        }
    }
}
