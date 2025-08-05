package com.example.module.homepageview.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.module.homepageview.R;
import com.example.module.homepageview.contract.IHomePageContract;
import com.example.module.homepageview.view.adapter.MyViewPagerAdapter;
import com.example.module.libBase.inter.Scrollable;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

@Route(path = "/HomePageView/HomePageFragment")
public class HomePageFragment extends Fragment implements IHomePageContract.IHomePageView, Scrollable {

    private IHomePageContract.IHomePagePresenter mPresenter;
    private ViewPager2 viewPager2;
    private TabLayout tabLayout;
    private List<Fragment> fragmentList;
    private HomeFirstFragment homeFirstFragment;
    private LinearLayout layout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.homepage_base_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager2 = view.findViewById(R.id.vp_homepage_main);
        tabLayout = view.findViewById(R.id.tl_homepage_main);
        layout = view.findViewById(R.id.ll_homepage_search);


        initView();
        initListener();
    }

    @Override
    public void initView() {

        homeFirstFragment = new HomeFirstFragment();
        Fragment classificationFragment = (Fragment) ARouter.getInstance().build("/classificationView/ClassificationFragment").navigation(getContext());

        fragmentList = new ArrayList<>();
        fragmentList.add(homeFirstFragment);
        fragmentList.add(classificationFragment);

        MyViewPagerAdapter adapter = new MyViewPagerAdapter(this, fragmentList);
        viewPager2.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("推荐");
                        break;
                    case 1:
                        tab.setText("分类");
                        break;
                }
            }
        }).attach();

    }

    @Override
    public void initListener() {
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/HomePageView/SearchActivity").navigation(getContext());
            }
        });

    }

    @Override
    public void setPresenter(IHomePageContract.IHomePagePresenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void scrollToTop() {
        ((Scrollable) fragmentList.get(viewPager2.getCurrentItem())).scrollToTop();
    }
}