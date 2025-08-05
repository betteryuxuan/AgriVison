package com.example.communityfragment;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.communityfragment.bean.Comment;

public class DiffCallBack extends DiffUtil.ItemCallback<Comment> {
    @Override
    public boolean areItemsTheSame(@NonNull Comment oldItem, @NonNull Comment newItem) {
        // 这里第二个是判断展开收起标志的
        return oldItem.getId() == newItem.getId() && oldItem.isExpanded() == newItem.isExpanded();
    }

    @Override
    public boolean areContentsTheSame(@NonNull Comment oldItem, @NonNull Comment newItem) {
        return oldItem.getContent().equals(newItem.getContent());
    }
}
