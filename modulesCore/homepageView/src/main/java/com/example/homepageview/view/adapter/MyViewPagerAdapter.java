package com.example.homepageview.view.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class MyViewPagerAdapter extends FragmentStateAdapter {

    List<Fragment> fragmentList;

    public MyViewPagerAdapter(@NonNull Fragment fragment, List<Fragment> fragmentList) {
        super(fragment);
        this.fragmentList = fragmentList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentList == null ? null : fragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return fragmentList == null ? 0 : fragmentList.size();
    }
}
