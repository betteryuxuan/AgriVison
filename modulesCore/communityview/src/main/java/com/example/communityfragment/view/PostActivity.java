package com.example.communityfragment.view;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.communityfragment.DiffCallBack;
import com.example.communityfragment.R;
import com.example.communityfragment.SoftKeyBoardListener;
import com.example.communityfragment.adapter.ImageDisplayAdapter;
import com.example.communityfragment.adapter.MultiCommentAdapter;
import com.example.communityfragment.bean.Comment;
import com.example.communityfragment.bean.Post;
import com.example.communityfragment.contract.IPostContract;
import com.example.communityfragment.databinding.ActivityPostBinding;
import com.example.communityfragment.presenter.PostPresenter;
import com.example.communityfragment.utils.TimeUtils;
import com.example.module.libBase.utils.SPUtils;
import com.example.module.libBase.utils.TokenManager;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Route(path = "/communityPageView/PostActivity")
public class PostActivity extends AppCompatActivity implements IPostContract.View {
    public static final String TAG = "PostFunctionTAG";

    private ActivityPostBinding binding;
    private PostPresenter mPresenter = new PostPresenter(this);

    @Autowired(required = true)
    protected Post post;

    private MultiCommentAdapter adapter;
    private boolean focusCommentInput;

    // 当前需要回复的评论
    private boolean isReplyMode = false;
    private Comment currentReplyComment = null;

    // 展开状态
    private Set<Integer> expandedComments = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ARouter.getInstance().inject(this);
        post = (Post) getIntent().getSerializableExtra("post");
        focusCommentInput = getIntent().getBooleanExtra("focusCommentInput", false);
        Log.d(TAG, "onCreate: " + post.getId());

        if (focusCommentInput) {
            Log.d(TAG, "onCreate: " + focusCommentInput);
            showKeyboard(binding.etPostText);
        }

        binding.rvMypostReply.setLayoutManager(new LinearLayoutManager(PostActivity.this));
        adapter = new MultiCommentAdapter(new DiffCallBack(), PostActivity.this, new MultiCommentAdapter.OnItemClickListenser() {
            @Override
            public void onItemClick(Comment comment) {
                currentReplyComment = comment;
                isReplyMode = true;
                binding.etPostText.setHint("回复 @" + comment.getUserName());
                binding.etPostText.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!binding.etPostText.hasFocus()) {
                            binding.etPostText.setFocusable(true);
                            binding.etPostText.setFocusableInTouchMode(true);
                            binding.etPostText.requestFocus();
                        }
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        if (imm != null) {
                            imm.showSoftInput(binding.etPostText, InputMethodManager.SHOW_IMPLICIT);
                        }
                    }
                }, 300);
            }

            @Override
            public void onItemLongClick(Comment comment) {
                LayoutInflater inflater = LayoutInflater.from(PostActivity.this);
                View customView = inflater.inflate(R.layout.dialog_comment_layout, null);
                AlertDialog dialog = new AlertDialog.Builder(PostActivity.this)
                        .setView(customView)
                        .create();
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.default_dialog_background);
                Button btnDelete = customView.findViewById(R.id.btn_dialog_comment_delete);
                Button btnShare = customView.findViewById(R.id.btn_dialog_comment_share);
                Button btnCopy = customView.findViewById(R.id.btn_dialog_comment_copy);
                TextView tvComment = customView.findViewById(R.id.tv_dialog_comment_content);
                TextView tvCretedTime = customView.findViewById(R.id.tv_dialog_comment_time);

                tvComment.setText(comment.getContent());
                tvCretedTime.setText(comment.getTime());
                if (isCommentOwner(comment)) {
                    btnDelete.setVisibility(View.VISIBLE);
                    btnDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(v.getContext(), "删除成功", Toast.LENGTH_SHORT).show();
                            mPresenter.deleteComment(comment);
                            dialog.dismiss();
                        }
                    });
                } else {
                    btnDelete.setVisibility(View.GONE);
                }
                btnShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent sendIntent = new Intent(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, comment.getContent());
                        sendIntent.setType("text/plain");
                        Intent shareIntent = Intent.createChooser(sendIntent, "title");
                        if (sendIntent.resolveActivity(v.getContext().getPackageManager()) != null) {
                            v.getContext().startActivity(shareIntent);
                        }
                        dialog.dismiss();
                    }
                });

                btnCopy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(v.getContext(), "复制成功", Toast.LENGTH_SHORT).show();
                        ClipboardManager clipboard = (ClipboardManager) v.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData content = ClipData.newPlainText("content", comment.getContent());
                        clipboard.setPrimaryClip(content);
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }

            @Override
            public void onMoreClick(Comment comment) {
                Log.d(TAG, "onMoreClick: " + comment.getId());

                List<Comment> currentList = new ArrayList<>(adapter.getCurrentList());
                boolean found = false;

                for (int i = 0; i < currentList.size(); i++) {
                    Comment curComment = currentList.get(i);

                    if (curComment.getId() == comment.getId()) {
                        Log.d(TAG, "onMoreClick: " + curComment.getChilders());

                        // 如果已经展开，则折叠
                        if (comment.isExpanded()) {
                            // 移除子评论
                            int removeCount = curComment.getChilders().size() - 2;
                            currentList.subList(i + 3, i + 3 + removeCount).clear();
                            comment.setExpanded(false);
                            expandedComments.remove(comment.getId());
                        }
                        // 如果未展开，则展开
                        else {
                            // 添加子评论
                            currentList.subList(i + 1, i + 3).clear();
                            currentList.addAll(i + 1, curComment.getChilders());
                            comment.setExpanded(true);
                            expandedComments.add(comment.getId());
                        }
                        found = true;
                        break;
                    }
                }

                if (found) {
                    adapter.updateData(currentList);
                }
            }
        });
        binding.rvMypostReply.setAdapter(adapter);

        mPresenter.getComments(post.getId());

        if (!TokenManager.getLoginStatus(PostActivity.this)) {
            binding.tvMypostEmpty.setText("登录后查看评论");
        }

        binding.imgMypostBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.tvMypostContent.setText(post.getContent());
        Glide.with(this)
                .load(post.getUserAvatar())
                .placeholder(R.drawable.default_user2)
                .error(R.drawable.default_user2)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .thumbnail(0.8f)
                .into(binding.imgMypostAvatar);
        binding.tvMypostUsername.setText(post.getUserName());
        binding.tvMypostCreatetime.setText(TimeUtils.getFormatTime(post.getCreatedTime()));
        binding.tvMypostReply.setText(String.format("共 %s 条回复", post.getCommentCount()));

        String jsonImages = post.getImageUrls();
        if (!TextUtils.isEmpty(jsonImages)) {
            binding.rlvMypostImage.setVisibility(View.VISIBLE);
            List<String> imagesUrl = getImagesUrl(jsonImages);
            GridLayoutManager layoutManager;
            if (imagesUrl.size() <= 2) {
                layoutManager = new GridLayoutManager(PostActivity.this, 2);
            } else if (imagesUrl.size() == 3) {
                layoutManager = new GridLayoutManager(PostActivity.this, 3);
            } else if (imagesUrl.size() == 4) {
                layoutManager = new GridLayoutManager(PostActivity.this, 2);
            } else {
                layoutManager = new GridLayoutManager(PostActivity.this, 3);
            }
            ImageDisplayAdapter imageAdapter = new ImageDisplayAdapter(PostActivity.this, imagesUrl);
            binding.rlvMypostImage.setLayoutManager(layoutManager);
            binding.rlvMypostImage.setAdapter(imageAdapter);
        }

        SoftKeyBoardListener softKeyBoardListener = new SoftKeyBoardListener(this);
        softKeyBoardListener.setOnSoftKeyBoardChangeListener(new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
            }

            @Override
            public void keyBoardHide(int height) {
                // 点击回复某人后如果文本框无内容恢复评论帖子状态
                String content = binding.etPostText.getText().toString();
                if (content.trim().isEmpty()) {
                    isReplyMode = false;
                    currentReplyComment = null;
                    binding.etPostText.setHint("说说你的想法");
                }
            }
        });

        binding.tvPostSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TokenManager.getLoginStatus(PostActivity.this)) {
                    Toast.makeText(PostActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                    return;
                }
                String content = binding.etPostText.getText().toString();
                if (content.trim().isEmpty()) {
                    Toast.makeText(PostActivity.this, "请输入内容", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (isReplyMode && currentReplyComment != null) {
                    // 回复模式
                    if (currentReplyComment.getParentId() == 0 && currentReplyComment.getRootId() == 0) {
                        mPresenter.comment(post.getId(), content, currentReplyComment.getId(), currentReplyComment.getId());
                    } else {
                        mPresenter.comment(post.getId(), content, currentReplyComment.getId(), currentReplyComment.getRootId());
                    }
                } else {
                    // 普通评论
                    mPresenter.comment(post.getId(), content, 0, 0);
                }
                // 重置状态和提示
                isReplyMode = false;
                currentReplyComment = null;
                binding.etPostText.setHint("说说你的想法");
                binding.etPostText.setText("");
            }
        });

        binding.imgMypostMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPostOwner(post)) {
                    PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                    popupMenu.getMenuInflater().inflate(R.menu.popup_menu_share, popupMenu.getMenu());

                    popupMenu.show();
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            if (item.getItemId() == R.id.item_post1_share) {
                                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                                sendIntent.putExtra(Intent.EXTRA_TEXT, post.getContent());
                                sendIntent.setType("text/plain");
                                Intent shareIntent = Intent.createChooser(sendIntent, "title");
                                if (sendIntent.resolveActivity(v.getContext().getPackageManager()) != null) {
                                    v.getContext().startActivity(shareIntent);
                                }
                                return true;
                            } else if (item.getItemId() == R.id.item_post1_refresh) {
                                Toast.makeText(PostActivity.this, "刷新成功", Toast.LENGTH_SHORT).show();
                                mPresenter.getComments(post.getId());
                            }
                            return false;
                        }
                    });
                } else {
                    PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                    popupMenu.getMenuInflater().inflate(R.menu.popup_menu_share2, popupMenu.getMenu());

                    popupMenu.show();
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            if (item.getItemId() == R.id.item_post2_share) {
                                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                                sendIntent.putExtra(Intent.EXTRA_TEXT, post.getContent());
                                sendIntent.setType("text/plain");
                                Intent shareIntent = Intent.createChooser(sendIntent, "title");
                                if (sendIntent.resolveActivity(v.getContext().getPackageManager()) != null) {
                                    v.getContext().startActivity(shareIntent);
                                }
                                return true;
                            } else if (item.getItemId() == R.id.item_post2_delete) {
                                mPresenter.deletePost(post.getId());
                                return true;
                            } else if (item.getItemId() == R.id.item_post2_refresh) {
                                Toast.makeText(PostActivity.this, "刷新成功", Toast.LENGTH_SHORT).show();
                                mPresenter.getComments(post.getId());
                            }
                            return false;
                        }
                    });
                }
            }
        });
    }

    // 获取评论
    @Override
    public void onCommentsSuccess(List<Comment> comments) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (comments == null || comments.isEmpty()) {
                    binding.tvMypostEmpty.setVisibility(View.VISIBLE);
                    binding.rvMypostReply.setVisibility(View.GONE);
                } else {
                    binding.tvMypostEmpty.setVisibility(View.GONE);
                    binding.rvMypostReply.setVisibility(View.VISIBLE);

                    if (!expandedComments.isEmpty()) {
                        for (Comment c : comments) {
                            if (expandedComments.contains(c.getId()) && c.isMoreIndicator()) {
                                c.setExpanded(true);
                            }
                        }
                        List<Comment> expandedList = buildExpandedList(comments);
                        adapter.updateData(expandedList);
                    } else {
                        adapter.updateData(comments);
                    }
                }
            }
        });
    }

    private List<Comment> buildExpandedList(List<Comment> comments) {
        for (int i = 0; i < comments.size(); i++) {
            Comment curComment = comments.get(i);

            for (int commentId : expandedComments) {
                if (commentId == curComment.getId() && !curComment.isMoreIndicator()) {
                    comments.subList(i + 1, i + 3).clear();
                    comments.addAll(i + 1, curComment.getChilders());
                }
            }
        }
        return comments;
    }

    @Override
    public void onCommentsFailure() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                binding.tvMypostEmpty.setVisibility(View.VISIBLE);
                binding.rvMypostReply.setVisibility(View.GONE);
            }
        });
    }

    // 发步评论
    @Override
    public void onPublishCommentSuccess(Comment comment) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(binding.etPostText.getWindowToken(), 0);

                Parcelable recyclerViewState = binding.rvMypostReply.getLayoutManager().onSaveInstanceState();
                binding.etPostText.setText("");

                mPresenter.getComments(post.getId());

//                // 遍历一下链表找到rootid，如何一级评论数量大于2就自动展开
//                List<Comment> comments = new ArrayList<>(adapter.getCurrentList());
//                for (Comment c : comments) {
//                    if (c.getId() == comment.getRootId()&& !c.isMoreIndicator()) {
//                        if (c.getChilders().size() > 2 && !expandedComments.contains(c.getId())) {
//                            expandedComments.add(c.getId());
//                        }
//                    }
//                }
//                List<Comment> expandedList = buildExpandedList(comments);
//                adapter.updateData(expandedList);

                binding.rvMypostReply.getLayoutManager().onRestoreInstanceState(recyclerViewState);
                post.setCommentCount(String.valueOf(Integer.parseInt(post.getCommentCount()) + 1));
                binding.tvMypostReply.setText(String.format("共 %s 条回复", post.getCommentCount()));

//                mPresenter.getComments(post.getId());
//                List<Comment> comments = adapter.getCurrentList();
//                List<Comment> finalList = new ArrayList<>(comments);
//                Log.d("PostActivity", "onPublishCommentSuccess: " + comments);
//                if (finalList.isEmpty()) {
//                    binding.tvMypostEmpty.setVisibility(View.GONE);
//                    binding.rvMypostReply.setVisibility(View.VISIBLE);
//                    finalList.add(comment);
//                    adapter.updateData(finalList);
//                } else {
//                    finalList.add(0, comment);
//                    // 删除展开收起的，重新处理
//                    finalList.removeIf(Comment::isMoreIndicator);
//                    finalList = mPresenter.flattenComments(finalList);
//                    adapter.updateData(finalList);
//                }
            }
        });
    }

    @Override
    public void onPublishCommentFailure() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(PostActivity.this, "评论失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDeletePostSuccess(int postId) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        });
    }

    @Override
    public void onDeleteCommentSuccess(Comment comment) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mPresenter.getComments(post.getId());
//                List<Comment> comments = adapter.getCurrentList();
//                List<Comment> finalList = new ArrayList<>(comments);
//                finalList.remove(comment);
//                if (comment.isMoreIndicator()) {
//                    // 如果是展开的，把展开的都删了
//                    expandedComments.remove(comment.getId());
//                }
//                // 把删掉评论的所有子评论或者子子评论都删了
//                Iterator<Comment> it = finalList.iterator();
//                while (it.hasNext()) {
//                    Comment c = it.next();
//                    if (c.getRootId() == comment.getId() || c.getParentId() == comment.getId()) {
//                        it.remove();
//                    }
//                }
//                adapter.updateData(finalList);
                post.setCommentCount(String.valueOf(Integer.parseInt(post.getCommentCount()) - 1));
                binding.tvMypostReply.setText(String.format("共 %s 条回复", post.getCommentCount()));
            }
        });
    }

    private void showKeyboard(EditText editText) {
        editText.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!editText.hasFocus()) {
                    editText.setFocusable(true);
                    editText.setFocusableInTouchMode(true);
                    editText.requestFocus();
                }
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
                }
            }
        }, 300);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) ev.getRawX(), (int) ev.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean isPostOwner(Post currentPost) {
        String username = SPUtils.getString(this, SPUtils.USERNAME_KEY, "");
        String avatar = SPUtils.getString(this, SPUtils.AVATAR_KEY, "");
        return username.equals(currentPost.getUserName()) && avatar.equals(currentPost.getUserAvatar());
    }

    private boolean isCommentOwner(Comment comment) {
        String username = SPUtils.getString(this, SPUtils.USERNAME_KEY, "");
        String avatar = SPUtils.getString(this, SPUtils.AVATAR_KEY, "");
        return username.equals(comment.getUserName()) && avatar.equals(comment.getUserAavatar());
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
}