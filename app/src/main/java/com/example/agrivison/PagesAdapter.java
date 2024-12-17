package com.example.agrivison;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class PagesAdapter extends FragmentStateAdapter {

    private List<Fragment> list = new ArrayList<>();

    public PagesAdapter(@NonNull FragmentActivity fragmentActivity, List<Fragment> list) {
        super(fragmentActivity);
        this.list = list;
    }

    public PagesAdapter(@NonNull Fragment fragment, List<Fragment> list) {
        super(fragment);
        this.list = list;
    }

    public PagesAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, List<Fragment> list) {
        super(fragmentManager, lifecycle);
        this.list = list;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return list == null ? null : list.get(position);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }
}
