package com.example.communityfragment.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.module.libBase.inter.Scrollable;

import java.util.List;

public class MyPagerAdapter extends FragmentStateAdapter {
    public static final String TAG = "MyPagerAdapterTAG";
    private List<Fragment> fragments;
    public MyPagerAdapter(@NonNull Fragment fragment, List<Fragment> fragments) {
        super(fragment);
        this.fragments = fragments;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }
}
