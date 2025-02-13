package com.example.chatpageview;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

@Route(path = "/chatpageview/ChatPageFragment ")
public class ChatpageFragment extends Fragment {
    private static final String TAG = "ChatpageFragmentTAG";

    public ChatpageFragment() {
        Log.d(TAG, "ChatpageFragment: ");
    }

    public static ChatpageFragment newInstance() {
        ChatpageFragment fragment = new ChatpageFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        return inflater.inflate(R.layout.fragment_chatpage, container, false);
    }


}