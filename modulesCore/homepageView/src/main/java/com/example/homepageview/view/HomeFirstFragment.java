package com.example.homepageview.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homepageview.R;
import com.example.homepageview.contract.IHomeFirstContract;
import com.example.homepageview.model.Corn;
import com.example.homepageview.model.News;
import com.example.homepageview.view.adapter.CornRecyclerViewAdapter;
import com.example.homepageview.view.adapter.NewsRecyclerViewAdapter;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import java.util.List;

public class HomeFirstFragment extends Fragment implements IHomeFirstContract.IHomeFirstView {

    private final String TAG = "HomeFirstFragment";

    private Banner banner;
    private IHomeFirstContract.IHomeFirstPresenter mPresenter;
    private RecyclerView cornRecyclerView, newsRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.homepage_first_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        banner = view.findViewById(R.id.banner_homepage_top);
        cornRecyclerView = view.findViewById(R.id.rv_homepage_crop);
        newsRecyclerView = view.findViewById(R.id.rv_homepage_news);
        initView();
        initListener();
    }

    @Override
    public void initView() {
        mPresenter.loadBannerDatas();
        mPresenter.loadCornRecyclerViewDatas();
        mPresenter.loadNewsRecyclerViewDatas();
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initAinm() {
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.my_anim);
        LayoutAnimationController layoutAnimationController = new LayoutAnimationController(animation);
        layoutAnimationController.setOrder(LayoutAnimationController.ORDER_NORMAL);
        layoutAnimationController.setDelay(0.2f);
        newsRecyclerView.setLayoutAnimation(layoutAnimationController);
    }

    @Override
    public void setupBanner(List<Integer> list) {
        banner.setAdapter(new BannerImageAdapter<Integer>(list) {
            @Override
            public void onBindView(BannerImageHolder holder, Integer data, int position, int size) {
                holder.imageView.setImageResource(data);
            }
        }).setIndicator(new CircleIndicator(getContext()))
                .setIndicatorSelectedColor(getResources().getColor(R.color.white))
                .setIndicatorNormalColor(getResources().getColor(R.color.white_shallow));
    }

    @Override
    public void setupCornRecyclerView(List<Corn> list) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        cornRecyclerView.setAdapter(new CornRecyclerViewAdapter(list));
        cornRecyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void setupNewsRecyclerView(List<News> list) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        newsRecyclerView.setAdapter(new NewsRecyclerViewAdapter(list));
        newsRecyclerView.setLayoutManager(linearLayoutManager);
        initAinm();
    }

    @Override
    public void setPresenter(IHomeFirstContract.IHomeFirstPresenter presenter) {
        this.mPresenter = presenter;
    }

}
