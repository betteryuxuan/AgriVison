package com.example.module.homepageview.view.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.module.homepageview.custom.ProverbFragment;
import com.example.module.homepageview.model.classes.Proverb;

import java.util.List;

public class ProverbViewPagerAdapter extends FragmentStateAdapter {

    private List<Proverb.ProverbData> proverbList;

    // 定义一个接口来传递点击事件
    public interface OnProverbClickListener {
        void onProverbClick(Proverb proverb);
    }

    public ProverbViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, List<Proverb.ProverbData> proverbList) {
        super(fragmentActivity);
        this.proverbList = proverbList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return ProverbFragment.newInstance(proverbList.get(position).getSentence(), proverbList.get(position).getAnnotation());
    }

    @Override
    public int getItemCount() {
        return proverbList == null ? 0 : proverbList.size();
    }
}
