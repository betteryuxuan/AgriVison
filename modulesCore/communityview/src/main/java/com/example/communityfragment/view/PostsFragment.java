package com.example.communityfragment.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.communityfragment.R;
import com.example.communityfragment.adapter.PostAdapter;
import com.example.communityfragment.bean.Post;
import com.example.communityfragment.bean.PostPublishedEvent;
import com.example.communityfragment.contract.IPostsContract;
import com.example.communityfragment.databinding.FragmentPostsBinding;
import com.example.communityfragment.presenter.PostsPresenter;
import com.example.module.libBase.inter.Scrollable;
import com.example.module.libBase.utils.TokenManager;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

@Route(path = "/communityPageView/PostsFragment")
public class PostsFragment extends Fragment implements IPostsContract.View, Scrollable {
    public static final String TAG = "PostsFuctionTAG";
    private PostsPresenter mPresenter;
    private FragmentPostsBinding binding;
    private PostAdapter adapter = new PostAdapter(getContext());
    @Autowired
    public String category;

    private int currentPage = 1;
    private static final int PAGE_SIZE = 5;
    private boolean isLastPage = false;

    public PostsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new PostsPresenter(this, getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPostsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ARouter.getInstance().inject(this);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
//        if (category != null)
//            Log.d("MyPagerAdapterTAG", "onViewCreated: " + category);

        adapter = new PostAdapter(getContext());
//        adapter.setHasStableIds(true);
        binding.rlvPosts.setLayoutManager(manager);
        binding.rlvPosts.setAdapter(adapter);

        if (category.equals("全部")) {
            View headerView = LayoutInflater.from(getContext()).inflate(R.layout.header_ai, binding.rlvPosts, false);
            adapter.setHeaderView(headerView);
        }

        adapter.setOnPostActionListener(new PostAdapter.OnPostActionListener() {
            @Override
            public void onLikeClick(int postId, boolean isLiked) {
                mPresenter.votePost(postId, isLiked);
            }

            @Override
            public void onDeleteClick(int postId) {
                mPresenter.deletePost(postId);
            }
        });

        binding.swipePostsRefresh.setRefreshHeader(new ClassicsHeader(getContext()));
        binding.swipePostsRefresh.setRefreshFooter(new ClassicsFooter(getContext()));

        binding.swipePostsRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                currentPage = 1;
                isLastPage = false;
                binding.swipePostsRefresh.setNoMoreData(false);
                if (TokenManager.getLoginStatus(getContext())) {
                    mPresenter.getData(getCommunityId(), currentPage, PAGE_SIZE);
                } else {
                    mPresenter.getGuestData(getCommunityId(), currentPage, PAGE_SIZE);
                }
            }
        });
        binding.swipePostsRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (!isLastPage) {
                    currentPage++;
                    if (TokenManager.getLoginStatus(getContext())) {
                        mPresenter.getData(getCommunityId(), currentPage, PAGE_SIZE);
                    } else {
                        mPresenter.getGuestData(getCommunityId(), currentPage, PAGE_SIZE);
                    }
                } else {
                    binding.swipePostsRefresh.finishLoadMore();
                }
            }
        });

        if (TokenManager.getLoginStatus(getContext())) {
            mPresenter.getData(getCommunityId(), currentPage, PAGE_SIZE);
        } else {
            mPresenter.getGuestData(getCommunityId(), currentPage, PAGE_SIZE);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "register: ");
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "unregister: ");
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onPostPublished(PostPublishedEvent event) {
        binding.rlvPosts.smoothScrollToPosition(0);
        binding.swipePostsRefresh.autoRefresh();
    }


    private int getCommunityId() {
        switch (category) {
            case "农友杂谈":
                return 1;
            case "种植交流":
                return 2;
            case "农业资讯":
                return 3;
            case "热榜":
                return 4;
            case "全部":
                return 5;
            case "帖子":
                return 6;
            case "赞过":
                return 7;
        }
        return 0;
    }

    @Override
    public void onDataReceived(List<Post> postList) {
        Log.d(TAG, "onDataReceived: " + postList.toString());
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (postList.isEmpty() && currentPage == 1) {
                    isLastPage = true;
                    binding.swipePostsRefresh.finishLoadMoreWithNoMoreData();
                    binding.swipePostsRefresh.finishRefresh(true);
                    if (category.equals("赞过")) {
                        binding.tvPostsEmpty.setText("当前暂无赞过的帖子");
                    } else if (category.equals("帖子")) {
                        binding.tvPostsEmpty.setText("把田里的经验写成故事！\uD83D\uDCDA\n分享您的种植笔记\uD83C\uDF31");
                    } else {
                        binding.tvPostsEmpty.setText("当前暂无帖子");
                    }
                    binding.rlvPosts.setVisibility(View.GONE);
                    binding.tvPostsEmpty.setVisibility(View.VISIBLE);
                } else {
                    binding.tvPostsEmpty.setVisibility(View.GONE);
                    binding.rlvPosts.setVisibility(View.VISIBLE);
                    binding.swipePostsRefresh.finishLoadMore(true);
                    binding.swipePostsRefresh.finishRefresh(true);
                }

                if (currentPage == 1) {
                    adapter.setData(postList);
                } else {
                    adapter.addData(postList);
                }

                if (postList.size() < PAGE_SIZE) {
                    isLastPage = true;
                    binding.swipePostsRefresh.finishLoadMoreWithNoMoreData();
                } else {
                    binding.swipePostsRefresh.finishLoadMore(true);
                }
            }
        });
    }

    @Override
    public void onDataReceivedFailure() {
        if (currentPage > 1) {
            currentPage--;
        }

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                binding.swipePostsRefresh.finishLoadMore(true);
                binding.swipePostsRefresh.finishRefresh(true);
                if (currentPage == 2) {
                    adapter.clearData();
                    binding.rlvPosts.setVisibility(View.GONE);
                    binding.tvPostsEmpty.setVisibility(View.VISIBLE);
                    binding.tvPostsEmpty.setText("加载失败，请稍后重试");
                } else {
                    Toast.makeText(getContext(), "加载失败，请稍后重试", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void deletePostSuccess(int postId) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.removeData(postId);
            }
        });
    }

    @Override
    public void scrollToTop() {
        binding.rlvPosts.smoothScrollToPosition(0);
        binding.rlvPosts.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int firstVisiblePos = layoutManager.findFirstCompletelyVisibleItemPosition();
                if (firstVisiblePos == 0) {
                    recyclerView.removeOnScrollListener(this);
                    binding.swipePostsRefresh.autoRefresh();
                }
            }
        });

    }
}