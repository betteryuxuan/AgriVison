package com.example.module.videoview.custom;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.module.videoview.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class CommentCommitFragment extends BottomSheetDialogFragment {

    private OnCommentDataListener onCommentDataListener;
    private EditText etComment;
    private TextView commit;

    // 设置接口回调
    public void setOnCommentDataListener(OnCommentDataListener listener) {
        this.onCommentDataListener = listener;
    }

    // 接口回调
    public interface OnCommentDataListener {
        void onCommentDataReturned(String s);
    }

    public static CommentCommitFragment newInstance() {
        CommentCommitFragment fragment = new CommentCommitFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bottom_sheet_commit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etComment = view.findViewById(R.id.et_video_input);
        commit = view.findViewById(R.id.tv_video_send);
        commit.setEnabled(false);

        etComment.requestFocus();
        etComment.postDelayed(() -> {
            InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.showSoftInput(etComment, InputMethodManager.SHOW_IMPLICIT);
            }
        }, 100);

        initView();
        initListener();
    }

    private void initView() {

    }

    private void initListener() {
        etComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                commit.setTextColor(getResources().getColor(R.color.white_shallow));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    commit.setBackgroundResource(R.drawable.send_corner_input);
                    commit.setTextColor(getResources().getColor(R.color.white));
                } else {
                    commit.setBackgroundResource(R.drawable.send_corner_uninput);
                    commit.setTextColor(getResources().getColor(R.color.gray));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    commit.setEnabled(true);
                } else  {
                    commit.setEnabled(false);
                }
            }
        });

        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = etComment.getText().toString();

                if (onCommentDataListener != null) {
                    onCommentDataListener.onCommentDataReturned(s);
                }
                dismiss();
            }
        });
    }

}
