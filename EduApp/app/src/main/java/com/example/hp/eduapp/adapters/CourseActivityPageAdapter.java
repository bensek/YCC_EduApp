package com.example.hp.eduapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.hp.eduapp.fragments.FilesFragment;
import com.example.hp.eduapp.fragments.LinksFragment;
import com.example.hp.eduapp.fragments.RemindersFragment;

/**
 * Created by radman on 9/15/2016.
 */

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class CourseActivityPageAdapter extends FragmentPagerAdapter {

    public CourseActivityPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return LinksFragment.newInstance(null, null);
            case 1:
                return FilesFragment.newInstance(null, null);
            case 2:
                return RemindersFragment.newInstance(null, null);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

}
