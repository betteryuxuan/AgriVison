package com.example.module.homepageview.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.module.homepageview.R;
import com.example.module.homepageview.contract.IHomeFirstContract;
import com.example.module.homepageview.custom.BookPageTransformer;
import com.example.module.homepageview.model.HomeFirstModel;
import com.example.module.homepageview.model.classes.News;
import com.example.module.homepageview.model.classes.Poetry;
import com.example.module.homepageview.model.classes.Proverb;
import com.example.module.homepageview.model.classes.Recommend;
import com.example.module.homepageview.presenter.HomeFirstPresenter;
import com.example.module.homepageview.view.adapter.CropRecyclerViewAdapter;
import com.example.module.homepageview.view.adapter.NewsRecyclerViewAdapter;
import com.example.module.homepageview.view.adapter.PoetryRecyclerViewAdapter;
import com.example.module.homepageview.view.adapter.ProverbViewPagerAdapter;
import com.example.module.homepageview.view.adapter.RecommendRecyclerViewAdapter;
import com.example.module.libBase.inter.Scrollable;
import com.example.module.libBase.utils.SPUtils;
import com.example.module.libBase.utils.TimeUtils;
import com.example.module.libBase.bean.Crop;
import com.example.module.libBase.bean.SpaceItemDecoration;
import com.example.module.libBase.bean.SwitchPageEvent;
import com.fangxu.library.DragContainer;
import com.fangxu.library.DragListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Route(path = "/HomePageView/HomeFirstFragment")
public class HomeFirstFragment extends Fragment implements IHomeFirstContract.IHomeFirstView, Scrollable {

    private final String TAG = "HomeFirstFragment";

    private NestedScrollView scrollView;
    private IHomeFirstContract.IHomeFirstPresenter mPresenter;
    private RecyclerView recommendRecyclerView, cropRecyclerView, newsRecyclerView, poetryRecyclerView;
    private ViewPager2 viewPager2;
    private DragContainer dragContainer;
    private TextView nameTextView, text3, poetryMore, cropMore;
    private Banner banner;
    private float startX = 0;
    private float startY = 0;

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
        scrollView = view.findViewById(R.id.ncv_homefirst_layout);
        nameTextView = view.findViewById(R.id.tv_homefirst_name_text);
        recommendRecyclerView = view.findViewById(R.id.rv_homefirst_recommend);
        cropRecyclerView = view.findViewById(R.id.rv_homepage_crop);
        newsRecyclerView = view.findViewById(R.id.rv_homepage_news);
        poetryRecyclerView = view.findViewById(R.id.rv_homefirst_poetry);
        viewPager2 = view.findViewById(R.id.vp_homepage_proverb);
        dragContainer = (DragContainer) view.findViewById(R.id.dc_home_drag);
        text3 = view.findViewById(R.id.homepage_text3);
        poetryMore = view.findViewById(R.id.tv_homefirst_poetry_more);
        cropMore = view.findViewById(R.id.tv_homefirst_crop_more);
        banner = view.findViewById(R.id.banner_homefirst_top);

        initView();
        initListener();
    }

    @Override
    public void initView() {

        String username = SPUtils.getString(getContext(), "username");
        String time = TimeUtils.getTimeNormal();
        if (username != null) {
            nameTextView.setText(username + "，" + time + "好");
        } else {
            nameTextView.setText(time + "好");
        }


        mPresenter.loadRecommendRecyclerViewDatas();
        mPresenter.loadCropRecyclerViewDatas();
        mPresenter.loadNewsRecyclerViewDatas();
        mPresenter.loadProverbViewPagerDatas();
        mPresenter.loadPoetryRecyclerViewDatas();
        setupBanner();
    }

    private void setupBanner() {
        List<Integer> list = new ArrayList<>();
        list.add(R.drawable.banner_img1);
        list.add(R.drawable.banner_img2);
        list.add(R.drawable.banner_img3);
        banner.setAdapter(new BannerImageAdapter<Integer>(list) {
                    @Override
                    public void onBindView(BannerImageHolder holder, Integer data, int position, int size) {
                        holder.imageView.setImageResource(data);
                    }
                }).addBannerLifecycleObserver(this)
                .setIndicator(new CircleIndicator(getContext()))
                .addBannerLifecycleObserver(this)
                .setLoopTime(3000);
    }


    @Override
    public void initListener() {

        dragContainer.setDragListener(new DragListener() {
            @Override
            public void onDragEvent() {
                ViewPager2 viewPager2 = (ViewPager2) getActivity().findViewById(R.id.vp_homepage_main);
                viewPager2.setCurrentItem(1);
            }
        });

        poetryMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/HomePageView/PoetryActivity").navigation(getContext());
            }
        });


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
    public void setupRecommendRecyclerView(List<Recommend> list) {
        recommendRecyclerView.setAdapter(new RecommendRecyclerViewAdapter(list, getContext(), new RecommendRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                switch (position) {
                    case 0:
                        ViewPager2 viewPager2 = (ViewPager2) getActivity().findViewById(R.id.vp_homepage_main);
                        viewPager2.setCurrentItem(1);
                        break;
                    case 1:
                        ARouter.getInstance().build("/HomePageView/PoetryActivity").navigation(getContext());
                        break;
                    case 2:
                        scrollView.smoothScrollTo(0, text3.getTop());
                        break;
                    case 3:
                        EventBus.getDefault().post(new SwitchPageEvent(1));
                        break;
                    default:
                        break;
                }
            }
        }));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recommendRecyclerView.setLayoutManager(linearLayoutManager);
        int space = 20;
        recommendRecyclerView.addItemDecoration(new SpaceItemDecoration(space));
    }

    @Override
    public void setupCropRecyclerView(List<Crop.DataItem> list) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        cropRecyclerView.setAdapter(new CropRecyclerViewAdapter(list, new CropRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Crop.DataItem crop) {
                ARouter.getInstance()
                        .build("/HomePageView/CropDetailsActivity")
                        .withParcelable("dataItem", crop)
                        .navigation();
            }
        }, getContext()));
        cropRecyclerView.setLayoutManager(linearLayoutManager);
        int space = 35;
        cropRecyclerView.addItemDecoration(new SpaceItemDecoration(space));
        cropRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // 获取布局管理器
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                // 获取最后一个可见项的位置
                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();

                // 获取总数据的数量
                int totalItemCount = layoutManager.getItemCount();

                // 判断是否滚动到了最后一个位置
                if (lastVisibleItemPosition == totalItemCount - 1) {
                    cropMore.setVisibility(View.VISIBLE);
                } else {
                    cropMore.setVisibility(View.GONE);
                }
            }
        });
        String jsonList = SPUtils.getString(getContext(), SPUtils.CROP_DETAIL_LIST_KEY, "");
        // 使用 Gson 将 JSON 字符串转换回 List
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Crop.CropDetail>>() {
        }.getType();
        List<Crop.CropDetail> cropDetailList = gson.fromJson(jsonList, listType);
        Log.d(TAG, "cropDetailList: " + cropDetailList);
        if (cropDetailList == null) {
            cropDetailList = new ArrayList<>();
            cropDetailList.add(list.get(0).getCropDetail().get(1));
            cropDetailList.add(list.get(1).getCropDetail().get(1));
            cropDetailList.add(list.get(2).getCropDetail().get(0));
            cropDetailList.add(list.get(2).getCropDetail().get(1));
            cropDetailList.add(list.get(3).getCropDetail().get(0));
            cropDetailList.add(list.get(3).getCropDetail().get(1));
            cropDetailList.add(list.get(4).getCropDetail().get(0));
            cropDetailList.add(list.get(4).getCropDetail().get(1));
            String jsonListAfterAdd = gson.toJson(cropDetailList);
            SPUtils.putString(getContext(), SPUtils.CROP_DETAIL_LIST_KEY, jsonListAfterAdd);
        }
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
        viewPager2.setPageTransformer(new BookPageTransformer());
    }

    @Override
    public void setupPoetryRecyclerView(List<Poetry.Item> list) {
        poetryRecyclerView.setAdapter(new PoetryRecyclerViewAdapter(list, new PoetryRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Poetry.Item poetry) {
                ARouter.getInstance().build("/HomePageView/PoetryDetailsActivity")
                        .withOptionsCompat(ActivityOptionsCompat.makeScaleUpAnimation(
                                poetryRecyclerView, poetryRecyclerView.getWidth() / 2, poetryRecyclerView.getHeight() / 2, 0, 0
                        ))
                        .withParcelable("item", poetry)
                        .navigation(getContext());
            }
        }, getContext()));
        poetryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void setPresenter(IHomeFirstContract.IHomeFirstPresenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void scrollToTop() {
        scrollView.smoothScrollTo(0, 0);
    }
}
