package com.example.module.videoview.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.module.libBase.utils.SPUtils;
import com.example.module.videoview.R;
import com.example.module.videoview.custom.CommentCommitFragment;
import com.example.module.videoview.model.classes.Comment;
import com.example.module.videoview.view.adapter.CommentRecyclerViewAdapter;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ui.PlayerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VideoShowFragment extends Fragment {

    private static final String TAG = "VideoShowFragment";

    private String url;
    private PlayerView playerView;
    private ExoPlayer exoPlayer;

    private ImageView like, collect, message;
    private TextView likeCount, collectCount, messageCount, commentCount;

    private ImageView pause;
    private GestureDetector gestureDetector;
    private RecyclerView commentRecyclerView;
    private List<Comment> comments = new ArrayList<>();
    private ImageView close;
    private ConstraintLayout layout, commentLayout;
    private CommentRecyclerViewAdapter adapter;

    private boolean isCommentOpen = false;
    private float originalVideoY;

    private boolean isDoubleTapped = false;  // 标记是否双击
    private boolean isSingleTap = false;    // 标记是否是单击
    private Handler handler = new Handler();  // 用于延时
    public VideoShowFragment() {
    }

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
            pause.setVisibility(View.GONE);
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
    public void onDestroyView() {
        super.onDestroyView();
        if (exoPlayer != null) {
            exoPlayer.release();
            exoPlayer = null;
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
        messageCount = view.findViewById(R.id.tv_video_message_count);
        commentCount = view.findViewById(R.id.tv_video_comment_count);

        playerView = view.findViewById(R.id.pv_video_show);

        exoPlayer = new ExoPlayer.Builder(getContext()).build();
        playerView.setPlayer(exoPlayer);
        MediaItem mediaItem = MediaItem.fromUri(url);
        exoPlayer.setMediaItem(mediaItem);
        exoPlayer.prepare();


        commentRecyclerView = view.findViewById(R.id.rv_videoshow_comment);
        close = view.findViewById(R.id.iv_video_comment_close);
        layout = view.findViewById(R.id.cl_video_bottom_layout);
        commentLayout = view.findViewById(R.id.cl_video_comment);



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
                toggleCommentSection();
            }
        });

        playerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector != null ? gestureDetector.onTouchEvent(event) : false;
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleCommentSection();
            }
        });

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentCommitFragment commentCommitFragment = CommentCommitFragment.newInstance();
                commentCommitFragment.show(getChildFragmentManager(), "commentCommitFragment");
                commentCommitFragment.setOnCommentDataListener(new CommentCommitFragment.OnCommentDataListener() {
                    @Override
                    public void onCommentDataReturned(String s) {
                        String name = SPUtils.getString(getContext(), SPUtils.USERNAME_KEY);
                        String avatar = SPUtils.getString(getContext(), SPUtils.AVATAR_KEY);
                        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
                        String formattedDate = sdf.format(new Date());
                        comments.add(new Comment(avatar, name, s, formattedDate));
                        if (adapter != null) {
                            adapter.notifyDataSetChanged();
                        }
                        Pattern pattern = Pattern.compile("\\d+"); // 匹配数字
                        Matcher matcher = pattern.matcher(commentCount.getText().toString());

                        if (matcher.find()) { // 先检查是否匹配成功
                            int count = Integer.parseInt(matcher.group()); // 转换成整数
                            commentCount.setText(String.valueOf(count + 1) + "条评论"); // 递增后再设置
                            messageCount.setText(String.valueOf(count + 1));
                        } else {
                            commentCount.setText("1" + "条评论"); // 如果没有数字，则默认从1开始
                            messageCount.setText("1");
                        }
                        commentRecyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);

                    }
                });
            }
        });

        exoPlayer.addListener(new Player.Listener() {
            @Override
            public void onPlaybackStateChanged(int playbackState) {
                if (playbackState == Player.STATE_ENDED) {
                    // 视频播放完毕后，重新开始播放
                    exoPlayer.seekTo(0);  // 将播放进度条归零
                    exoPlayer.play();     // 重新播放
                }
            }
        });

    }

    private void toggleLike() {
        if (like.getTag() == null || like.getTag().equals("unliked")) {
            // 点赞
            like.setImageResource(R.drawable.ic_heart_full);
            like.setTag("liked");
            showHeartAnimation(like, R.drawable.ic_heart_full);
            // 更新点赞数量
            int count = Integer.parseInt(likeCount.getText().toString()) + 1;
            likeCount.setText(String.valueOf(count));
        } else if (like.getTag().equals("liked")) {
            // 已经点赞，再次点击取消动画（如果有取消动画的需求）
            showHeartAnimation(like, R.drawable.ic_heart_full);
        }

    }

    public void initView() {


        adapter = new CommentRecyclerViewAdapter(comments, getContext());

        commentRecyclerView.setAdapter(adapter);
        commentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return true; // 必须返回true才能接收到后续事件
            }
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                // 如果已经是双击事件，直接返回，不处理单击事件
                if (isDoubleTapped) {
                    return false;
                }

                // 标记为单击
                isSingleTap = true;

                // 延时执行单击操作，避免双击快速点击时触发单击
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (isSingleTap) {
                            handleClick();  // 执行暂停操作
                        }
                    }
                }, 200);  // 延时200ms执行单击操作

                Log.d(TAG, "onSingleTapUp: ");

                return false;
            }

            @Override
            public boolean onDoubleTap(@NonNull MotionEvent e) {
                // 双击时，立即执行点赞
                isDoubleTapped = true;
                toggleLike();

                // 重置单击标志，防止执行单击事件
                isSingleTap = false;

                // 延时清除双击标志
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isDoubleTapped = false;
                    }
                }, 300); // 延时300ms重置双击标志

                Log.d(TAG, "onDoubleTap: ");

                return true;
            }
        });
    }

    private void handleClick() {
        if (isCommentOpen) {
            toggleCommentSection();
        } else {
            if (exoPlayer.isPlaying()) {
                exoPlayer.pause();
                pause.setVisibility(View.VISIBLE);
            } else {
                exoPlayer.play();
                pause.setVisibility(View.GONE);
            }
        }
    }

    private void toggleCommentSection() {
        if (!isCommentOpen) {
            // 记录初始Y位置，方便回到全屏
            originalVideoY = playerView.getY();

            // 评论区从底部弹出，确保初始位置在屏幕外
            commentLayout.setTranslationY(commentLayout.getHeight());
            commentLayout.setVisibility(View.VISIBLE);

            ObjectAnimator translationUp = ObjectAnimator.ofFloat(commentLayout, "translationY", commentLayout.getHeight(), 0);

            ViewPager2 vp = getActivity().findViewById(R.id.vp_video_show);
            vp.setUserInputEnabled(false);

            // 不对视频进行缩放或位移操作
            AnimatorSet set = new AnimatorSet();
            set.play(translationUp);
            set.setDuration(300);
            set.start();
        } else {
            // 评论区隐藏
            ObjectAnimator translationDown = ObjectAnimator.ofFloat(commentLayout, "translationY", 0, commentLayout.getHeight());
            ViewPager2 vp = getActivity().findViewById(R.id.vp_video_show);
            vp.setUserInputEnabled(true);

            AnimatorSet set = new AnimatorSet();
            set.play(translationDown);
            set.setDuration(300);
            set.start();

            set.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    commentLayout.setVisibility(View.GONE);
                }
            });
        }

        isCommentOpen = !isCommentOpen;
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
