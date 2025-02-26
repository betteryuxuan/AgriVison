package com.example.module.videoview.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.module.videoview.R;
import com.example.module.videoview.custom.CommentFragment;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.PlayerView;

public class VideoShowFragment extends Fragment {

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
        exoPlayer.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        exoPlayer.play();
        if (pause != null) {
            pause.setVisibility(View.GONE);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (exoPlayer != null) {
            exoPlayer.play();
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
        exoPlayer.play();




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
                like.setTag("liked");
                likeCount.setText(String.valueOf(Integer.parseInt(likeCount.getText().toString()) + 1));

            }
        });

        collect.setOnClickListener(v -> {
            if (collect.getTag() != null && collect.getTag().equals("collected")) {
                collect.setImageResource(R.drawable.ic_collect);
                collect.setTag("uncollected");
                collectCount.setText(String.valueOf(Integer.parseInt(collectCount.getText().toString()) - 1));
            } else {
                collect.setImageResource(R.drawable.ic_collected);
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
}
