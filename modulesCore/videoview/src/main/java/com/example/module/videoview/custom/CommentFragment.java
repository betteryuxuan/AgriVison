package com.example.module.videoview.custom;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.module.videoview.R;
import com.example.module.videoview.model.classes.Comment;
import com.example.module.videoview.view.adapter.CommentRecyclerViewAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class CommentFragment extends BottomSheetDialogFragment {

    private RecyclerView commentRecyclerView;
    private List<Comment> comments = new ArrayList<>();
    private ImageView close;
    private ConstraintLayout layout;
    private CommentRecyclerViewAdapter adapter;

    public static CommentFragment newInstance() {
        CommentFragment fragment = new CommentFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bottom_sheet_video, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        commentRecyclerView = view.findViewById(R.id.rv_video_comment);
        close = view.findViewById(R.id.iv_video_comment_close);
        layout = view.findViewById(R.id.cl_video_bottom_layout);

        initView();
        initListener();
    }

    private void initView() {

        comments.add(new Comment(R.drawable.ic_my_lancher, "柳真阳", "这个视频真有用"));
        comments.add(new Comment(R.drawable.ic_my_lancher, "姚天博", "农视界真棒"));
        comments.add(new Comment(R.drawable.ic_my_lancher, "风雨炫", "爱了爱了"));

        adapter = new CommentRecyclerViewAdapter(comments);
        commentRecyclerView.setAdapter(adapter);
        commentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    private void initListener() {
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
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
                        comments.add(new Comment(R.drawable.ic_my_lancher, "嘻嘻", s));
                        if (adapter != null) {
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        });
    }
}
