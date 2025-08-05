package com.example.communityfragment.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.communityfragment.R;
import com.example.communityfragment.bean.Post;
import com.example.module.libBase.utils.SPUtils;
import com.example.module.libBase.utils.TokenManager;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Post> mPostList = new ArrayList<>();
    private Context mContext;
    private View mHeaderView;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    public PostAdapter(Context context) {
        this.mContext = context;
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            return new MyHeadViewHolder(mHeaderView);
        } else if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post_layout, parent, false);
            return new MyViewHolder(view);
        } else {
            throw new IllegalArgumentException("Invalid view type");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder mHolder, int position) {
        if (getItemViewType(position) == TYPE_HEADER)
            return;

        // 强制类型转换
        MyViewHolder holder = (MyViewHolder) mHolder;

        int realPosition = mHeaderView == null ? position : position - 1;
        if (realPosition < 0 || realPosition >= mPostList.size())
            return;
        Post currentPost = mPostList.get(realPosition);
        Log.d("PostsFuctionTAG", "onBindViewHolder: " + position);

        holder.userName.setText(currentPost.getUserName());
        holder.postLikeCount.setText(currentPost.getLikeConunt());
        holder.postComment.setText(currentPost.getCommentCount());
        Glide.with(holder.itemView.getContext())
                .load(currentPost.getUserAvatar())
                .placeholder(R.drawable.default_user2)
                .error(R.drawable.default_user2)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.userAvatar);

        String content = currentPost.getContent();
        if (content.length() >= 100) {
            SpannableString spannableString = new SpannableString("...查看更多");
            spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(holder.itemView.getContext(), R.color.grenn1)), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            SpannableStringBuilder builder = new SpannableStringBuilder();
            builder.append(content.substring(0, 100));
            builder.append(spannableString);

            holder.postContent.setText(builder);
        } else {
            holder.postContent.setText(content);
        }

        String jsonImages = currentPost.getImageUrls();
        if (!TextUtils.isEmpty(jsonImages)) {
            holder.rlvImages.setVisibility(View.VISIBLE);

            List<String> imagesUrl = getImagesUrl(jsonImages);
            GridLayoutManager layoutManager;
            if (imagesUrl.size() <= 2) {
                layoutManager = new GridLayoutManager(holder.itemView.getContext(), 2);
            } else if (imagesUrl.size() == 3) {
                layoutManager = new GridLayoutManager(holder.itemView.getContext(), 3);
            } else if (imagesUrl.size() == 4) {
                layoutManager = new GridLayoutManager(holder.itemView.getContext(), 2);
            } else {
                layoutManager = new GridLayoutManager(holder.itemView.getContext(), 3);
            }
            ImageDisplayAdapter imageAdapter = new ImageDisplayAdapter(holder.itemView.getContext(), imagesUrl);
            holder.rlvImages.setLayoutManager(layoutManager);
            holder.rlvImages.setAdapter(imageAdapter);
        } else {
            holder.rlvImages.setVisibility(View.GONE);
        }

        Log.d("PostsFuctionTAG1111", "点赞: " + currentPost.getIsLiked() + "  " + currentPost.getContent());
        if (currentPost.getIsLiked()) {
            holder.postLike.setImageResource(R.drawable.ic_like_green);
            holder.postLikeCount.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.grenn1));
        } else {
            holder.postLike.setImageResource(R.drawable.ic_like_gray);
            holder.postLikeCount.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.gray3));
        }

        if (currentPost.getCommunityId().equals("1")) {
            holder.tvCommunityId.setText("农友杂谈");
            holder.communityPost.setImageResource(R.drawable.ic_communityid);
        } else if (currentPost.getCommunityId().equals("2")) {
            holder.tvCommunityId.setText("种植交流");
            holder.communityPost.setImageResource(R.drawable.ic_plant);
        } else if (currentPost.getCommunityId().equals("3")) {
            holder.tvCommunityId.setText("农业资讯");
            holder.communityPost.setImageResource(R.drawable.ic_news);
        }

        holder.cvPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/communityPageView/PostActivity")
                        .withSerializable("post", currentPost)
                        .withBoolean("focusCommentInput", false)
                        .navigation();
            }
        });

        holder.llComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/communityPageView/PostActivity")
                        .withSerializable("post", currentPost)
                        .withBoolean("focusCommentInput", true)
                        .navigation();
            }
        });


        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ObjectAnimator scaleX = ObjectAnimator.ofFloat(holder.postLike, "scaleX", 1f, 1.1f, 1f);
//                ObjectAnimator scaleY = ObjectAnimator.ofFloat(holder.postLike, "scaleY", 1f, 1.1f, 1f);
//                AnimatorSet set = new AnimatorSet();
//                set.playTogether(scaleX, scaleY);
//                set.setDuration(300).start();
                if(!TokenManager.getLoginStatus(holder.itemView.getContext())){
                   Toast.makeText(holder.itemView.getContext(), "请先登录", Toast.LENGTH_SHORT).show();
                }

                if (currentPost.getIsLiked()) {
                    holder.postLike.setImageResource(R.drawable.ic_like_gray);
                    holder.postLikeCount.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.gray3));
                    currentPost.setLiked(false);
                    String likeConunt = String.valueOf(Integer.parseInt(currentPost.getLikeConunt()) - 1);
                    currentPost.setLikeConunt(likeConunt);
                    holder.postLikeCount.setText(likeConunt);
                } else {
                    holder.postLike.setImageResource(R.drawable.ic_like_green);
                    holder.postLikeCount.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.grenn1));
                    currentPost.setLiked(true);
                    String likeConunt = String.valueOf(Integer.parseInt(currentPost.getLikeConunt()) + 1);
                    currentPost.setLikeConunt(likeConunt);
                    holder.postLikeCount.setText(likeConunt);
                }
                if (mActionListener != null) {
                    mActionListener.onLikeClick(currentPost.getId(), currentPost.getIsLiked());
                }
            }
        };

        holder.llLike.setOnClickListener(listener);


        if (!isPostOwner(currentPost)) {
            holder.postMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                    popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

                    popupMenu.setOnMenuItemClickListener(item -> {
                        if (item.getItemId() == R.id.item_copy) {
                            Toast.makeText(v.getContext(), "复制成功", Toast.LENGTH_SHORT).show();
                            ClipboardManager clipboard = (ClipboardManager) v.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                            ClipData content = ClipData.newPlainText("content", currentPost.getContent());
                            clipboard.setPrimaryClip(content);
                            return true;
                        }
                        return false;
                    });
                    popupMenu.show();
                }
            });
        } else {
            holder.postMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                    popupMenu.getMenuInflater().inflate(R.menu.popup_menu_2, popupMenu.getMenu());

                    popupMenu.setOnMenuItemClickListener(item -> {
                        if (item.getItemId() == R.id.item_copy) {
                            Toast.makeText(v.getContext(), "复制成功", Toast.LENGTH_SHORT).show();
                            ClipboardManager clipboard = (ClipboardManager) v.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                            ClipData content = ClipData.newPlainText("content", currentPost.getContent());
                            clipboard.setPrimaryClip(content);
                            return true;
                        } else if (item.getItemId() == R.id.item_delete) {
                            if (mActionListener != null) {
                                mActionListener.onDeleteClick(currentPost.getId());
                            }
                        }
                        return false;
                    });
                    popupMenu.show();
                }
            });
        }


        holder.llShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, currentPost.getContent());
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, "title");
                if (sendIntent.resolveActivity(v.getContext().getPackageManager()) != null) {
                    v.getContext().startActivity(shareIntent);
                }

            }
        });


        holder.rlvImages.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    // 根据触摸点的坐标判断是否点击到了子项上
                    View child = holder.rlvImages.findChildViewUnder(event.getX(), event.getY());
                    if (child == null) {
                        // 若 child 为 null，则表示触摸点不在任何子项上，即点击了空白区域
                        holder.cvPost.performClick();
                        return true;
                    }
                }
                return false;
            }
        });

    }

    private boolean isPostOwner(Post currentPost) {
        String username = SPUtils.getString((mContext), SPUtils.USERNAME_KEY, "");
        String avatar = SPUtils.getString((mContext), SPUtils.AVATAR_KEY, "");
        Log.d("PostAdapter", "isPostOwner: " + username + " " + avatar + " " + currentPost.getUserName() + " " + currentPost.getUserAvatar());
        return username.equals(currentPost.getUserName()) && avatar.equals(currentPost.getUserAvatar());
    }

    private List<String> getImagesUrl(String jsonImages) {
        List<String> images = new ArrayList<>();
        try {
            JSONArray object = new JSONArray(jsonImages);
            for (int i = 0; i < object.length(); i++) {
                images.add(object.getString(i));
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return images;
    }

    @Override
    public int getItemCount() {
        return mHeaderView == null ? mPostList.size() : mPostList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView != null && position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void clearData() {
        mPostList.clear();
        notifyDataSetChanged();
    }

    public void setData(List<Post> postList) {
        mPostList.clear();
        mPostList = postList;
        notifyDataSetChanged();
    }

    public void addData(List<Post> postList) {
        int startPos = mPostList.size();
        mPostList.addAll(postList);
        notifyItemRangeInserted(mHeaderView != null ? startPos + 1 : startPos, postList.size());
    }

    public void removeData(int postId) {
        for (int i = 0; i < mPostList.size(); i++) {
            if (mPostList.get(i).getId() == postId) {
                mPostList.remove(i);
                notifyItemRemoved(mHeaderView == null ? i : i + 1);
                break;
            }
        }
    }


    public interface OnPostActionListener {
        void onLikeClick(int postId, boolean isLiked);

        void onDeleteClick(int postId);
    }

    private OnPostActionListener mActionListener;

    public void setOnPostActionListener(OnPostActionListener listener) {
        this.mActionListener = listener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private CardView cvPost;
        private CircleImageView userAvatar;
        private TextView userName;
        private TextView postContent;
        private TextView postComment;
        private TextView postLikeCount;
        private TextView tvCommunityId;
        private ImageView postLike;
        private ImageView postMore;
        private ImageView communityPost;
        private LinearLayout llLike;
        private LinearLayout llComment;
        private LinearLayout llShare;
        private RecyclerView rlvImages;

        public MyViewHolder(View view) {
            super(view);
            cvPost = view.findViewById(R.id.cv_post);
            userAvatar = view.findViewById(R.id.img_post_useravatar);
            userName = view.findViewById(R.id.tv_post_username);
            postContent = view.findViewById(R.id.tv_post_content);
            postComment = view.findViewById(R.id.tv_post_commentcount);
            postLike = view.findViewById(R.id.img_post_like);
            postLikeCount = view.findViewById(R.id.tv_post_likecount);
            tvCommunityId = view.findViewById(R.id.tv_post_communityid);
            postMore = view.findViewById(R.id.img_post_more);
            llLike = view.findViewById(R.id.ll_post_like);
            llComment = view.findViewById(R.id.ll_post_comment);
            llShare = view.findViewById(R.id.ll_post_share);
            rlvImages = view.findViewById(R.id.rlv_post_image);
            communityPost = view.findViewById(R.id.img_post_community);
        }
    }

    private class MyHeadViewHolder extends MyViewHolder {
        private HorizontalScrollView mNestedScrollView;
        private CardView cvAi1;
        private CardView cvAi2;
        private CardView cvAi3;
        private CardView cvAi4;

        public MyHeadViewHolder(View mHeaderView) {
            super(mHeaderView);
            mNestedScrollView = mHeaderView.findViewById(R.id.card_layout);
            cvAi1 = mHeaderView.findViewById(R.id.card_ai_1);
            cvAi2 = mHeaderView.findViewById(R.id.card_ai_2);
            cvAi3 = mHeaderView.findViewById(R.id.card_ai_3);
            cvAi4 = mHeaderView.findViewById(R.id.card_ai_4);
            cvAi1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ARouter.getInstance()
                            .build("/chatpageview/ChatPageActivity")
                            .withInt("role", 2)
                            .navigation();
                }
            });
            cvAi2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ARouter.getInstance()
                            .build("/chatpageview/ChatPageActivity")
                            .withInt("role", 1)
                            .navigation();
                }
            });
            cvAi3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ARouter.getInstance()
                            .build("/chatpageview/ChatPageActivity")
                            .withInt("role", 3)
                            .navigation();
                }
            });
            cvAi4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ARouter.getInstance()
                            .build("/chatpageview/ChatPageActivity")
                            .withInt("role", 4)
                            .navigation();
                }
            });


        }
    }
}
