package com.example.frapptest;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.example.frapptest.adapters.ModelAdapter;
import com.example.frapptest.adapters.ViewPagerAdapter;
import com.example.frapptest.fragments.TabB;
import com.example.frapptest.models.Model;

public class MainActivity extends BaseActivity implements ModelAdapter.OnMarkFavorite{

    private TabLayout tabLayout;
    private ViewPager mViewPager;
    private ViewPagerAdapter adapter;
    @Override
    protected int getMainLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpTabLayout();
        setUpViewPager();
    }

    @Override
    protected String getActionBarTitle() {
        return getString(R.string.activity_main_title);
    }

    private void setUpTabLayout(){
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }

    private void setUpViewPager(){
        String titles[] = {"List", "Favourites"};
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setOffscreenPageLimit(2);
        adapter = new ViewPagerAdapter(this.getSupportFragmentManager(),
                titles.length,
                titles,
                this,
                new Bundle(),
                new Bundle()
        );
        mViewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onMarked(Model model, boolean mark) {
        TabB fragment = (TabB) adapter.getRegisteredFragment(1);
        if (fragment != null) {
            fragment.updateList(model, mark);
        }
    }
}
