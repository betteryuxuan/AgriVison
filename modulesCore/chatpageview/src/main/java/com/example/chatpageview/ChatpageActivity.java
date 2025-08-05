package com.example.chatpageview;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.chatpageview.adapter.MsgAdapter;
import com.example.chatpageview.bean.Msg;
import com.example.chatpageview.contract.IChatContract;
import com.example.chatpageview.databinding.ActivityChatpageBinding;
import com.example.chatpageview.presenter.ChatPresenterImpl;
import com.example.module.libBase.utils.AnimationUtils;
import com.example.module.libBase.utils.SoftHideKeyBoardUtil;

import java.util.List;
import java.util.Random;

@Route(path = "/chatpageview/ChatPageActivity")
public class ChatpageActivity extends AppCompatActivity implements IChatContract.View {
    private ActivityChatpageBinding binding;
    private MsgAdapter adapter;
    private IChatContract.Presenter mPresenter;
    private List<Msg> msgList;
    private boolean isRequestInProgress = false;
    private AlertDialog.Builder builder;
    @Autowired
    public int role;
    private AlertDialog dialogNewChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatpageBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        SoftHideKeyBoardUtil.assistActivity(this);

        ARouter.getInstance().inject(this);

        mPresenter = new ChatPresenterImpl(this, this);
        msgList = mPresenter.initMessages(role);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.white));
        }

        if (role == 2) {
            binding.tvTitle.setText("农业百科");
            binding.tvSubtitle.setText("解答您关于农业的一切好奇");
        } else if (role == 1) {
            binding.tvTitle.setText("农学先贤");
            binding.tvSubtitle.setText("精通古代农业理论，传达古代智慧");
        } else if (role == 3) {
            binding.tvTitle.setText("四时节气");
            binding.tvSubtitle.setText("传递时间、自然与农事之间的密切关系");
        } else if (role == 4) {
            binding.tvTitle.setText("华夏农脉");
            binding.tvSubtitle.setText("聚焦各地农业风貌与地方农作物的特色");
        }


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        if (msgList.size() > 3) {
            layoutManager.setStackFromEnd(true);
        }
        binding.msgRecyclerView.setLayoutManager(layoutManager);
        adapter = new MsgAdapter(msgList);
        binding.msgRecyclerView.setAdapter(adapter);

        binding.imgSend.setOnClickListener(v -> {
            AnimationUtils.setAnimateView(binding.imgSend);
            if (mPresenter.getLoginStatus()) {
                String content = binding.etText.getText().toString();
                if (vailInput()) {
                    binding.etText.setText("");
                    isRequestInProgress = true;
                    mPresenter.sendMessage(content, role);
                } else {
                    showToast("正在回答中");
                }
            } else {
                showToast("请先登录");
            }
        });

        binding.imgNewchat.setOnClickListener(v -> {
            AnimationUtils.setAnimateView(binding.imgNewchat);
            showNewChatDialog();
        });
    }

    private void showNewChatDialog() {
        builder = new AlertDialog.Builder(ChatpageActivity.this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_newchat, null);
        Button btnCancel = dialogView.findViewById(R.id.btn_newchat_cancel);
        Button btnConfirm = dialogView.findViewById(R.id.btn_newchat_confirm);
        builder.setView(dialogView);
        dialogNewChat = builder.create();

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRequestInProgress) {
                    mPresenter.cancelCurrentRequest();
                }
                mPresenter.clearLocalMsg(role);
                msgList.clear();
                msgList = mPresenter.initMessages(role);
                binding.msgRecyclerView.setLayoutManager(new LinearLayoutManager(ChatpageActivity.this));
                adapter = new MsgAdapter(msgList);
                binding.msgRecyclerView.setAdapter(adapter);
                dialogNewChat.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogNewChat.dismiss();
            }
        });

        dialogNewChat.show();
    }

    private boolean vailInput() {
        return !(msgList.get(msgList.size() - 1).getType() == Msg.TYPE_THINKING ||
                msgList.get(msgList.size() - 1).getType() == Msg.TYPE_SENT);
    }

    @Override
    public void addMessage(Msg msg) {
        runOnUiThread(() -> {
            if (msg.getType() == Msg.TYPE_RECEIVED && isRequestInProgress) {
                msgList.add(msg);
                adapter.notifyItemInserted(msgList.size() - 1);
                binding.msgRecyclerView.scrollToPosition(msgList.size() - 1);
                isRequestInProgress = false;
            } else if (msg.getType() == Msg.TYPE_SENT || msg.getType() == Msg.TYPE_THINKING) {
                msgList.add(msg);
                adapter.notifyItemInserted(msgList.size() - 1);
                binding.msgRecyclerView.scrollToPosition(msgList.size() - 1);
            }
        });
    }

    @Override
    public void showToast(String message) {
        runOnUiThread(() -> Toast.makeText(ChatpageActivity.this, message, Toast.LENGTH_SHORT).show());
    }

    @Override
    public void removeThinkingMsg() {
        runOnUiThread(() -> {
            for (int i = msgList.size() - 1; i >= 0; i--) {
                if (msgList.get(i).getType() == Msg.TYPE_THINKING) {
                    msgList.remove(i);
                    adapter.notifyItemRemoved(i);
                }
            }
        });
    }

    @Override
    public void stopRequest() {
        isRequestInProgress = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.saveToLocal(msgList, role);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        if (isRequestInProgress) {
            mPresenter.cancelCurrentRequest();
        }
    }

    private void getRandomBackground() {
        int[] backgrounds = new int[]{
                R.drawable.pic_chatbg1,
                R.drawable.pic_chatbg2,
                R.drawable.pic_chatbg3,
                R.drawable.pic_chatbg4,
                R.drawable.pic_chatbg5,
                R.drawable.pic_chatbg6
        };

        int randomIndex = new Random().nextInt(backgrounds.length);
        int selectedBackground = backgrounds[randomIndex];
        binding.msgRecyclerView.setBackgroundResource(selectedBackground);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            // 判断当前焦点是否为EditText
            if (v instanceof EditText) {
                // 将outRect设置为视图根视图坐标空间中该视图的非剪裁区域的坐标,即 EditText 在屏幕上的可见区域
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                // 触摸点不在 EditText 的可见区域
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
}
