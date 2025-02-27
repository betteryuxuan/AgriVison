package com.example.module.homepageview.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.module.homepageview.R;
import com.example.module.homepageview.contract.IHomeFirstContract;
import com.example.module.libBase.bean.Crop;
import com.example.module.homepageview.model.HomeFirstModel;
import com.example.module.homepageview.model.classes.News;
import com.example.module.homepageview.model.classes.Proverb;
import com.example.module.homepageview.presenter.HomeFirstPresenter;
import com.example.module.homepageview.view.adapter.CropRecyclerViewAdapter;
import com.example.module.homepageview.view.adapter.NewsRecyclerViewAdapter;
import com.example.module.homepageview.view.adapter.ProverbViewPagerAdapter;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.config.IndicatorConfig;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import java.util.List;

public class HomeFirstFragment extends Fragment implements IHomeFirstContract.IHomeFirstView {

    private final String TAG = "HomeFirstFragment";

    private Banner banner;
    private IHomeFirstContract.IHomeFirstPresenter mPresenter;
    private RecyclerView cropRecyclerView, newsRecyclerView;
    private ViewPager2 viewPager2;

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

        // 确保 mPresenter 被初始化
        if (mPresenter == null) {
            // 如果 mPresenter 为 null，初始化它
            mPresenter = new HomeFirstPresenter(this, new HomeFirstModel(getContext()), getContext());
        }

        banner = view.findViewById(R.id.banner_homepage_top);
        cropRecyclerView = view.findViewById(R.id.rv_homepage_crop);
        newsRecyclerView = view.findViewById(R.id.rv_homepage_news);
        viewPager2 = view.findViewById(R.id.vp_homepage_recommend);
        initView();
        initListener();
    }

    @Override
    public void initView() {
        mPresenter.loadBannerDatas();
        mPresenter.loadCropRecyclerViewDatas();
        mPresenter.loadNewsRecyclerViewDatas();
        mPresenter.loadProverbViewPagerDatas();
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initAinm() {
//        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.my_anim);
//        LayoutAnimationController layoutAnimationController = new LayoutAnimationController(animation);
//        layoutAnimationController.setOrder(LayoutAnimationController.ORDER_NORMAL);
//        layoutAnimationController.setDelay(0.2f);
//        newsRecyclerView.setLayoutAnimation(layoutAnimationController);
    }

    @Override
    public void setupBanner(List<Integer> list) {
        banner.setAdapter(new BannerImageAdapter<Integer>(list) {
            @Override
            public void onBindView(BannerImageHolder holder, Integer data, int position, int size) {
                holder.imageView.setImageResource(data);
            }
        }).setIndicator(new CircleIndicator(getContext()))
                .addBannerLifecycleObserver(this)
                .setIndicatorSelectedColor(getResources().getColor(R.color.white))
                .setIndicatorNormalColor(getResources().getColor(R.color.white_shallow))
                .setIndicatorGravity(IndicatorConfig.Direction.RIGHT)
                .setLoopTime(3000);
    }

    @Override
    public void setupCropRecyclerView(List<Crop> list) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        cropRecyclerView.setAdapter(new CropRecyclerViewAdapter(list, new CropRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Crop crop) {
                ARouter.getInstance()
                        .build("/HomePageView/CropDetailsActivity")
                        .navigation();
            }
        }));
        cropRecyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void setupNewsRecyclerView(List<News.Item> list) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        newsRecyclerView.setAdapter(new NewsRecyclerViewAdapter(list, new NewsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(News.Item news) {
                Intent intent = new Intent(getContext(), NewsActivity.class);
                intent.putExtra("htmlContent", news.getContent());
                intent.putExtra("title", news.getTitle());
                intent.putExtra("image", news.getImage());
                startActivity(intent);            }
        }, getContext()));
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        initAinm();
    }

    @Override
    public void setupProverbViewPager(List<Proverb.ProverbData> list) {
        ProverbViewPagerAdapter adapter = new ProverbViewPagerAdapter(getActivity(), list);
        viewPager2.setAdapter(adapter);
    }

    @Override
    public void setPresenter(IHomeFirstContract.IHomeFirstPresenter presenter) {
        this.mPresenter = presenter;
    }

}
