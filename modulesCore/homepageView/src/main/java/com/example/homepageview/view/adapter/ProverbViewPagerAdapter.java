package com.example.homepageview.view.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.homepageview.custom.ProverbFragment;
import com.example.homepageview.model.classes.Proverb;

import java.util.List;

public class ProverbViewPagerAdapter extends FragmentStateAdapter {

    List<Proverb> proverbList;

    public ProverbViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, List<Proverb> proverbList) {
        super(fragmentActivity);
        this.proverbList = proverbList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return ProverbFragment.newInstance(proverbList.get(position).getProverb());
    }

    @Override
    public int getItemCount() {
        return proverbList == null ? 0 : proverbList.size();
    }
}
