package com.example.module.videoview.view;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.module.videoview.R;
import com.example.module.videoview.model.VideoModel;
import com.example.module.videoview.presenter.VideoPresenter;

public class VideoActivity extends AppCompatActivity {

    private VideoFragment mVideoFragment;
    private VideoPresenter mVideoPresenter;
    private VideoModel mVideoModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_video);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mVideoFragment = new VideoFragment();
        mVideoModel = new VideoModel(this);
        mVideoPresenter = new VideoPresenter(mVideoFragment, mVideoModel);
        mVideoFragment.setPresenter(mVideoPresenter);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new VideoFragment())
                .commit();
    }
}