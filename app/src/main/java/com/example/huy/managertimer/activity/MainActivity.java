package com.example.huy.managertimer.activity;

import android.content.res.Resources;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.huy.managertimer.R;
import com.example.huy.managertimer.adapter.ViewPageAdapter;
import com.example.huy.managertimer.fragment.ClockFragment;
import com.example.huy.managertimer.fragment.StatisticsFragment;
import com.example.huy.managertimer.fragment.TaskFragment;

public class MainActivity extends AppCompatActivity {

    private TabLayout tlMain;
    private ViewPager vpMain;
    private Toolbar tbMain;

    Resources resources;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resources = getResources();
        tbMain = (Toolbar) findViewById(R.id.tbMain);
        setSupportActionBar(tbMain);

        vpMain = (ViewPager) findViewById(R.id.vpMain);
        setUpViewPage();
        tlMain = (TabLayout) findViewById(R.id.tlMain);
        tlMain.setupWithViewPager(vpMain);
        setupTabIcons();

    }

    private void setupTabIcons() {
        tlMain.getTabAt(0).setIcon(R.drawable.ic_alarm);
        tlMain.getTabAt(1).setIcon(R.drawable.ic_action_device_storage);
        tlMain.getTabAt(2).setIcon(R.drawable.ic_description);
    }

    private void setUpViewPage() {
        ViewPageAdapter adapter = new ViewPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new ClockFragment(),resources.getString(R.string.Time));
        adapter.addFragment(new TaskFragment(),resources.getString(R.string.Task));
        adapter.addFragment(new StatisticsFragment(),resources.getString(R.string.Chart));
        vpMain.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.mainactivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.setting:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
