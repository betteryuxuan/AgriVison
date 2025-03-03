package com.example.module.videoview.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.module.videoview.R;
import com.example.module.videoview.custom.CommentFragment;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.PlayerView;

public class VideoShowFragment extends Fragment {

    private static final String TAG = "VideoShowFragment";

    private String url;
    private PlayerView playerView;
    private ExoPlayer exoPlayer;

    private ImageView like, collect, message;
    private TextView likeCount, collectCount;

    private ImageView pause;


    public VideoShowFragment(String url) {
        this.url = url;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.video_item, container, false);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
        if (exoPlayer != null) {
            exoPlayer.pause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        if (exoPlayer != null) {
            exoPlayer.play();
        }
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.d(TAG, "onHiddenChanged: ");
        if (hidden) {
            // 当 Fragment 被隐藏时暂停视频
            if (exoPlayer != null) {
                exoPlayer.pause();
            }
        } else {
            // 当 Fragment 重新显示时恢复视频播放
            if (exoPlayer != null) {
                exoPlayer.play();
            }
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        like = view.findViewById(R.id.iv_video_like);
        collect = view.findViewById(R.id.iv_video_collect);
        message = view.findViewById(R.id.iv_video_message);
        pause = view.findViewById(R.id.iv_video_pause);
        likeCount = view.findViewById(R.id.tv_video_like_count);
        collectCount = view.findViewById(R.id.tv_video_collect_count);

        playerView = view.findViewById(R.id.pv_video_show);
        exoPlayer = new ExoPlayer.Builder(getContext()).build();
        playerView.setPlayer(exoPlayer);
        MediaItem mediaItem = MediaItem.fromUri(url);
        exoPlayer.setMediaItem(mediaItem);
        exoPlayer.prepare();




        initView();
        initListener();
    }

    private void initListener() {
        like.setOnClickListener(v -> {
            if (like.getTag() != null && like.getTag().equals("liked")) {
                like.setImageResource(R.drawable.ic_heart);
                like.setTag("unliked");
                likeCount.setText(String.valueOf(Integer.parseInt(likeCount.getText().toString()) - 1));
            } else {
                like.setImageResource(R.drawable.ic_heart_full);
                showHeartAnimation(like, R.drawable.ic_heart_full);
                like.setTag("liked");
                likeCount.setText(String.valueOf(Integer.parseInt(likeCount.getText().toString()) + 1));

            }
        });

        collect.setOnClickListener(v -> {
            if (collect.getTag() != null && collect.getTag().equals("collected")) {
                collect.setImageResource(R.drawable.ic_collect);
                Toast.makeText(getContext(), "已取消收藏", Toast.LENGTH_LONG).show();
                collect.setTag("uncollected");
                collectCount.setText(String.valueOf(Integer.parseInt(collectCount.getText().toString()) - 1));
            } else {
                collect.setImageResource(R.drawable.ic_collected);
                showHeartAnimation(collect, R.drawable.ic_collected);
                collect.setTag("collected");
                collectCount.setText(String.valueOf(Integer.parseInt(collectCount.getText().toString()) + 1));
            }
        });

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentFragment commentFragment = CommentFragment.newInstance();
                commentFragment.show(getParentFragmentManager(), "comment");
            }
        });

        playerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (exoPlayer.isPlaying()) {
                    exoPlayer.pause();
                    pause.setVisibility(View.VISIBLE);
                } else {
                    exoPlayer.play();
                    pause.setVisibility(View.GONE);
                }
            }
        });

    }

    public void initView() {

    }

    private void showHeartAnimation(View likeButton, int drawableResId) {
        ImageView heart = new ImageView(likeButton.getContext());
        heart.setImageResource(drawableResId);

        // 获取 likeButton 在屏幕上的绝对位置
        int[] location = new int[2];
        likeButton.getLocationInWindow(location);
        int x = location[0] + likeButton.getWidth() / 2 - 50; // 让心形居中
        int y = location[1] - 50; // 上移一点，避免重叠

        // 创建 LayoutParams 并设置正确的位置
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(100, 100);
        params.leftMargin = x;
        params.topMargin = y;

        // 添加到父布局
        ViewGroup parent = (ViewGroup) likeButton.getRootView();
        parent.addView(heart, params);

        // 执行动画
        ValueAnimator animator = ValueAnimator.ofFloat(0f, -200f);
        animator.setDuration(800);
        animator.addUpdateListener(animation -> {
            heart.setTranslationY((float) animation.getAnimatedValue());
            heart.setAlpha(1 - animation.getAnimatedFraction());
        });

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                parent.removeView(heart); // 动画结束后移除
            }
        });

        animator.start();
    }



}
