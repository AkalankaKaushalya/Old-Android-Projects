package com.will_dev.vpn_app.fromanother;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.will_dev.vpn_app.fromanother.fragment.RewardCurrentFragment;
import com.will_dev.vpn_app.fromanother.fragment.URMoneyFragment;

public class ViewpagerRewardAdapter extends FragmentPagerAdapter {

    int Favourite_NumOfTabs;
    public Activity activity;

    public ViewpagerRewardAdapter(FragmentManager fm, int favourite_NumOfTabs, Activity activity) {
        super(fm);
        Favourite_NumOfTabs = favourite_NumOfTabs;
        this.activity = activity;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new RewardCurrentFragment();

            case 1:
                return new URMoneyFragment();

            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return Favourite_NumOfTabs;
    }

}
