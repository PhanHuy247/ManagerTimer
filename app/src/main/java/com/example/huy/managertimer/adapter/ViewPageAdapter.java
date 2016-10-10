package com.example.huy.managertimer.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Huy on 10/10/2016.
 */

public class ViewPageAdapter extends FragmentPagerAdapter {
    private List<Fragment> lFragment = new ArrayList<>();
    private List<String> lString = new ArrayList<>();

    public ViewPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return lFragment.get(position);
    }

    @Override
    public int getCount() {
        return lFragment.size();
    }

    public void addFragment(Fragment fragment, String string){
        lFragment.add(fragment);
        lString.add(string);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return lString.get(position);
    }
}
