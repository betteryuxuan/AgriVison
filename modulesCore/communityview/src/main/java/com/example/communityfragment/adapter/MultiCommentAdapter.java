package com.example.communityfragment.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.communityfragment.DiffCallBack;
import com.example.communityfragment.R;
import com.example.communityfragment.bean.Comment;
import com.example.communityfragment.utils.TimeUtils;
import com.example.communityfragment.view.PostActivity;

import java.util.List;

// parentid == 0 && rootid == 0 是一级评论
// parentid == rootid 是二级评论
// parentid != rootid != 0 是多级评论
public class MultiCommentAdapter extends ListAdapter<Comment, RecyclerView.ViewHolder> {
    private static final String TAG = "CommentAdapterTAG";
    private static final int TYPE_LEVEL_ONE = 0;
    private static final int TYPE_LEVEL_TWO = 1;
    private static final int TYPE_LEVEL_MORE = 2;
    private Context mContext;

    private OnItemClickListenser onItemClickListenser;

    public interface OnItemClickListenser {
        void onItemClick(Comment comment);

        void onItemLongClick(Comment comment);

        void onMoreClick(Comment comment);
    }

    public MultiCommentAdapter(@NonNull DiffUtil.ItemCallback<Comment> diffCallback, PostActivity context, OnItemClickListenser onItemClickListenser) {
        super(diffCallback);
        this.mContext = context;
        this.onItemClickListenser = onItemClickListenser;
    }


    @Override
    public int getItemViewType(int position) {
        Comment comment = getItem(position);
        if (comment.isMoreIndicator()) {
            return TYPE_LEVEL_MORE;
        } else if (comment.getRootId() == 0) {
            Log.d(TAG, "getItemViewType: 1");
            // 一级
            return TYPE_LEVEL_ONE;
        } else if (comment.getRootId() != 0) {
            Log.d(TAG, "getItemViewType: 2");
            // 多级
            return TYPE_LEVEL_TWO;
        }
        return TYPE_LEVEL_ONE;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_LEVEL_ONE) {
            Log.d(TAG, "onCreateViewHolder: ");
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment_layout, parent, false);
            return new LevelOneViewHolder(view);
        } else if (viewType == TYPE_LEVEL_TWO) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_child_comment_layout, parent, false);
            return new LevelTwoViewHolder(view);
        } else if (viewType == TYPE_LEVEL_MORE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_more_layout, parent, false);
            return new MoreViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Comment comment = getItem(position);
        if (holder instanceof LevelOneViewHolder) {
            ((LevelOneViewHolder) holder).bindData(comment);
        } else if (holder instanceof LevelTwoViewHolder) {
            ((LevelTwoViewHolder) holder).bindData(comment);
        } else if (holder instanceof MoreViewHolder) {
            ((MoreViewHolder) holder).bindData(comment);
        }
    }


    public void updateData(List<Comment> comments) {
        submitList(comments);
    }

    class LevelOneViewHolder extends RecyclerView.ViewHolder {
        private TextView content, UserName, time;
        private ImageView avatar;
        private ConstraintLayout parentLayout;

        public LevelOneViewHolder(View itemView) {
            super(itemView);
            parentLayout = itemView.findViewById(R.id.cl_comment);
            content = itemView.findViewById(R.id.tv_comment_content);
            avatar = itemView.findViewById(R.id.img_comment_useravatar);
            UserName = itemView.findViewById(R.id.tv_comment_username);
            time = itemView.findViewById(R.id.tv_comment_time);
        }

        public void bindData(Comment comment) {
            content.setText(comment.getContent());
            Glide.with(mContext)
                    .load(comment.getUserAavatar())
                    .placeholder(R.drawable.default_user2)
                    .into(avatar);
            UserName.setText(comment.getUserName());
            time.setText(TimeUtils.getRelativeTime(comment.getTime()));

            parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListenser.onItemClick(comment);
                }
            });
            parentLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemClickListenser.onItemLongClick(comment);
                    return true;
                }
            });
        }
    }

    class LevelTwoViewHolder extends RecyclerView.ViewHolder {
        private TextView content, UserName, time, parentIcon, parentUserName;
        private ImageView avatar;
        private ConstraintLayout parentLayout;

        public LevelTwoViewHolder(View itemView) {
            super(itemView);
            parentLayout = itemView.findViewById(R.id.cl_child_comment);
            content = itemView.findViewById(R.id.tv_child_comment_content);
            avatar = itemView.findViewById(R.id.img_child_comment_useravatar);
            UserName = itemView.findViewById(R.id.tv_child_comment_username);
            time = itemView.findViewById(R.id.tv_child_comment_time);
            parentIcon = itemView.findViewById(R.id.tv_child_icon);
            parentUserName = itemView.findViewById(R.id.tv_child_parent);
        }

        public void bindData(Comment comment) {
            content.setText(comment.getContent());
            Glide.with(mContext)
                    .load(comment.getUserAavatar())
                    .placeholder(R.drawable.default_user2)
                    .into(avatar);
            UserName.setText(comment.getUserName());
            time.setText(TimeUtils.getRelativeTime(comment.getTime()));

            if (comment.getParentId() == comment.getRootId()) {
                parentIcon.setVisibility(View.GONE);
                parentUserName.setVisibility(View.GONE);
            } else if (comment.getParentId() != comment.getRootId()) {
                parentIcon.setVisibility(View.VISIBLE);
                parentUserName.setVisibility(View.VISIBLE);
                parentUserName.setText(comment.getParentUserName());
            }
            parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListenser.onItemClick(comment);
                }
            });
            parentLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemClickListenser.onItemLongClick(comment);
                    return true;
                }
            });
        }
    }


    class MoreViewHolder extends RecyclerView.ViewHolder {
        private TextView more;
        private ConstraintLayout constraintLayout;

        public MoreViewHolder(@NonNull View itemView) {
            super(itemView);
            more = itemView.findViewById(R.id.tv_more_content);
            constraintLayout = itemView.findViewById(R.id.cl_more_comment);
        }

        public void bindData(Comment comment) {
            if (comment.isExpanded()) {
                comment.setContent("收起");
                more.setText(comment.getContent());
            } else {
                comment.setContent("展开" + comment.getRepliesCount() + "条回复");
                more.setText(String.format(comment.getContent()));
            }
            constraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListenser != null) {
                        onItemClickListenser.onMoreClick(comment);
                    }
                    if (comment.isExpanded()) {
                        comment.setContent("收起");
                        more.setText(comment.getContent());
                    } else {
                        comment.setContent("展开" + comment.getRepliesCount() + "条回复");
                        more.setText(String.format(comment.getContent()));
                    }
                }
            });
        }
    }
}
