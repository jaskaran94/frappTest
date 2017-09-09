package com.example.frapptest.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.example.frapptest.fragments.TabA;
import com.example.frapptest.fragments.TabB;

/**
 * Created by jaskaranhome on 09/09/17.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    SparseArray<Fragment> registeredFragments = new SparseArray<>();
    int tabCount;
    String[] titles;
    Context mContext;

    Bundle tabA, tabB;
    public ViewPagerAdapter(FragmentManager fm, int count, String[] titles, Context context, Bundle tabABundle, Bundle tabBBundle) {
        super(fm);
        this.tabCount = count;
        this.titles = titles;
        this.mContext = context;
        this.tabA = tabABundle;
        this.tabB = tabBBundle;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return TabA.newInstance(tabA);
            case 1:
                return TabB.newInstance(tabB);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public Fragment getRegisteredFragment(int position){
        return registeredFragments.get(position);
    }
}
