package com.example.module.homepageview.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.module.homepageview.R;
import com.example.module.homepageview.contract.IHomePageContract;
import com.example.module.homepageview.model.CategoryModel;
import com.example.module.homepageview.model.HomeFirstModel;
import com.example.module.homepageview.presenter.CategoryPresenter;
import com.example.module.homepageview.presenter.HomeFirstPresenter;
import com.example.module.homepageview.view.adapter.MyViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

@Route(path = "/HomePageView/HomePageFragment")
public class HomePageFragment extends Fragment implements IHomePageContract.IHomePageView {

    private IHomePageContract.IHomePagePresenter mPresenter;
    private ViewPager2 viewPager2;
    private TabLayout tabLayout;
    private List<Fragment> fragmentList;
    private HomeFirstFragment homeFirstFragment;
    private HomeFirstModel homeFirstModel;
    private HomeFirstPresenter homeFirstPresenter;
    private CategoryFragment categoryFragment;
    private CategoryPresenter categoryPresenter;
    private CategoryModel categoryModel;

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


        initView();
        initListener();
    }

    @Override
    public void initView() {

        homeFirstFragment = new HomeFirstFragment();
        homeFirstModel = new HomeFirstModel(getContext());
        categoryFragment = new CategoryFragment();
        categoryModel = new CategoryModel(getContext());
        homeFirstPresenter = new HomeFirstPresenter(homeFirstFragment, homeFirstModel, getContext());
        categoryPresenter = new CategoryPresenter(categoryFragment, categoryModel);
        homeFirstFragment.setPresenter(homeFirstPresenter);
        categoryFragment.setPresenter(categoryPresenter);

        fragmentList = new ArrayList<>();
        fragmentList.add(homeFirstFragment);
        fragmentList.add(categoryFragment);

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


    }

    @Override
    public void setPresenter(IHomePageContract.IHomePagePresenter presenter) {
        this.mPresenter = presenter;
    }
}
