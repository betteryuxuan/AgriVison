package com.example.module.videoview.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.module.videoview.R;
import com.example.module.videoview.model.classes.Comment;

import java.util.List;

public class CommentRecyclerViewAdapter extends RecyclerView.Adapter<CommentRecyclerViewAdapter.CommentViewHolder> {

    private List<Comment> comments;

    public CommentRecyclerViewAdapter(List<Comment> comments) {
        this.comments = comments;
    }

    @NonNull
    @Override
    public CommentRecyclerViewAdapter.CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentRecyclerViewAdapter.CommentViewHolder holder, int position) {
        Comment comment = comments.get(position);
        holder.image.setImageResource(comment.getImage());
        holder.name.setText(comment.getName());
        holder.comment.setText(comment.getComment());
        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.like.getTag() != null && holder.like.getTag().equals("liked")) {
                    holder.like.setImageResource(R.drawable.ic_heart_comment);
                    holder.like.setTag("unliked");
                    holder.likeCount.setText(String.valueOf(Integer.parseInt(holder.likeCount.getText().toString()) - 1));
                } else {
                    holder.like.setImageResource(R.drawable.ic_heart_full);
                    holder.like.setTag("liked");
                    holder.likeCount.setText(String.valueOf(Integer.parseInt(holder.likeCount.getText().toString()) + 1));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return comments == null ? 0 : comments.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;
        private ImageView like;
        private TextView name, comment, likeCount;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.iv_video_user);
            like = itemView.findViewById(R.id.iv_video_user_like);
            name = itemView.findViewById(R.id.tv_video_username);
            comment = itemView.findViewById(R.id.tv_video_user_text);
            likeCount = itemView.findViewById(R.id.tv_video_user_like_count);
        }
    }
}
