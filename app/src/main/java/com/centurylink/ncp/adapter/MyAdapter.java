package com.centurylink.ncp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.centurylink.ncp.FirstFragment;
import com.centurylink.ncp.SecondFragment;

/**
 * Created by ab65363 on 8/2/2016.
 */
public class MyAdapter extends FragmentPagerAdapter {
    public MyAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: // Fragment # 0 - This will show FirstFragment
                return FirstFragment.newInstance(0, "Page # 1");
            case 1: // Fragment # 0 - This will show FirstFragment different title
                return FirstFragment.newInstance(1, "Page # 2");
            case 2: // Fragment # 1 - This will show SecondFragment
                return FirstFragment.newInstance(1, "Page # 2");
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 0;
    }
}
