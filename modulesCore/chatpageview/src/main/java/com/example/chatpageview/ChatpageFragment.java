package com.example.chatpageview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.chatpageview.adapter.MsgAdapter;
import com.example.chatpageview.bean.Msg;
import com.example.chatpageview.contract.IChatContract;
import com.example.chatpageview.databinding.FragmentChatpageBinding;
import com.example.chatpageview.presenter.ChatPresenterImpl;
import com.example.module.libBase.AnimationUtils;

import java.util.List;
import java.util.Random;

@Route(path = "/chatpageview/chatPage")
public class ChatpageFragment extends Fragment implements IChatContract.View {
    private static final String TAG = "ChatpageFragmentTAG";
    private FragmentChatpageBinding binding;
    private MsgAdapter adapter;
    private IChatContract.Presenter mPresenter;
    private List<Msg> msgList;
    private boolean isRequestInProgress = false;

    public ChatpageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new ChatPresenterImpl(this, getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChatpageBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        msgList = mPresenter.initMessages();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        if (msgList.size() > 3) {
            layoutManager.setStackFromEnd(true);
        }
        binding.msgRecyclerView.setLayoutManager(layoutManager);
        adapter = new MsgAdapter(msgList);
        binding.msgRecyclerView.setAdapter(adapter);

        getRandomBackground();
        binding.imgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationUtils.setAnimateView(binding.imgSend);
                if (mPresenter.getLoginStatus()) {
                    String content = binding.etText.getText().toString();
                    if (vailInput()) {
                        binding.etText.setText("");
                        isRequestInProgress = true;
                        mPresenter.sendMessage(content);
                    } else {
                        showToast("正在回答中");
                    }
                } else {
                    showToast("请先登录");
                }
            }
        });

        binding.imgNewchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationUtils.setAnimateView(binding.imgNewchat);

                if (isRequestInProgress) {
                    mPresenter.cancelCurrentRequest();
                }

                mPresenter.clearLocalMsg();

                msgList.clear();
                msgList = mPresenter.initMessages();

                binding.msgRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                adapter = new MsgAdapter(msgList);
                binding.msgRecyclerView.setAdapter(adapter);
                getRandomBackground();
            }
        });
    }


    private boolean vailInput() {
        return !(msgList.get(msgList.size() - 1).getType() == Msg.TYPE_THINKING ||
                msgList.get(msgList.size() - 1).getType() == Msg.TYPE_SENT);
    }

    @Override
    public void addMessage(Msg msg) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
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
            }
        });
    }

    @Override
    public void showToast(String message) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void removeThinkingMsg() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (int i = msgList.size() - 1; i >= 0; i--) {
                    if (msgList.get(i).getType() == Msg.TYPE_THINKING) {
                        msgList.remove(i);
                        adapter.notifyItemRemoved(i);
                    }
                }
            }
        });
    }


    @Override
    public void stopRequest() {
        isRequestInProgress = false;
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.saveToLocal(msgList);
    }

    @Override
    public void onDestroy() {
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
}
