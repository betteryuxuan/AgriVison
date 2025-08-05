package com.example.communityfragment.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.communityfragment.adapter.MyPagerAdapter;
import com.example.communityfragment.databinding.FragmentCommunityBinding;
import com.example.module.libBase.inter.Scrollable;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

@Route(path = "/communityPageView/CommunityFragment")
public class CommunityFragemnt extends Fragment implements Scrollable {
    private static final String TAG = "CommunityFragemntTAG";
    private FragmentCommunityBinding binding;
    private MyPagerAdapter adapter;
    private List<Fragment> fragments;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCommunityBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<String> titles = List.of("全部", "热榜", "农友杂谈", "种植交流", "农业资讯");

        Fragment fragment1 = (Fragment) ARouter.getInstance().build("/communityPageView/PostsFragment").withString("category", titles.get(0)).navigation();
        Fragment fragment2 = (Fragment) ARouter.getInstance().build("/communityPageView/PostsFragment").withString("category", titles.get(1)).navigation();
        Fragment fragment3 = (Fragment) ARouter.getInstance().build("/communityPageView/PostsFragment").withString("category", titles.get(2)).navigation();
        Fragment fragment4 = (Fragment) ARouter.getInstance().build("/communityPageView/PostsFragment").withString("category", titles.get(3)).navigation();
        Fragment fragment5 = (Fragment) ARouter.getInstance().build("/communityPageView/PostsFragment").withString("category", titles.get(4)).navigation();

        fragments = List.of(fragment1, fragment2, fragment3, fragment4, fragment5);
        adapter = new MyPagerAdapter(this, fragments);
        binding.vpCommunity.setAdapter(adapter);
        binding.vpCommunity.setOffscreenPageLimit(1);

        new TabLayoutMediator(binding.tbCommunity, binding.vpCommunity,
                (tab, position) -> tab.setText(titles.get(position))
        ).attach();
    }

    @Override
    public void scrollToTop() {
        ((Scrollable) fragments.get(binding.vpCommunity.getCurrentItem())).scrollToTop();
    }
}