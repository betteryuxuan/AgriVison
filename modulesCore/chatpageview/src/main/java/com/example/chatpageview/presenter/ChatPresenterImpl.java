package com.example.chatpageview.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.chatpageview.bean.Msg;
import com.example.chatpageview.contract.IChatContract;
import com.example.module.libBase.SPUtils;
import com.example.module.libBase.TimeUtils;
import com.example.module.libBase.TokenManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChatPresenterImpl implements IChatContract.Presenter {
    private static final String TAG = "ChatPresenterImplTAG";

    private static final OkHttpClient client = new OkHttpClient();
    private static final String AI_URL = "http://101.200.122.3:8080/ai";

    private IChatContract.View mView;
    private Context mContext;
    private List<Msg> msgList;
    private Random random = new Random();
    private Call currentCall;
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    public ChatPresenterImpl(IChatContract.View view, Context context) {
        this.mView = view;
        this.mContext = context;
    }


    @Override
    public List<Msg> initMessages() {
        msgList = loadLocalMsg();
        if (msgList == null || msgList.isEmpty()) {
            String[] welcomeMessages = {
                    "您好！我是您的农业小助手，很高兴为您服务。请问您想了解哪方面的农业知识呢？无论是作物种植、土壤管理，还是农业新技术，我都会耐心为您解答！🌾",
                    "您好！我是您的农业小助手，期待为您提供帮助。关于农业的任何问题，如作物栽培、土壤维护或最新的农业科技，欢迎向我咨询！🌱",
                    "您好！我是您的农业小助手，很高兴为您服务。无论您对农业的哪个方面感兴趣，例如作物培育、土壤改良，还是先进的农业技术，我都在这里为您解答！🌿"
            };

            int index = random.nextInt(3);
            String selectedMessage = welcomeMessages[index];

            Msg msg = new Msg(selectedMessage, TimeUtils.getFormattedTime(), Msg.TYPE_RECEIVED);
            msgList = new ArrayList<>();
            msgList.add(msg);
        }

        return msgList;
    }

    @Override
    public void sendMessage(String content) {
        if (content == null || content.trim().isEmpty()) {
            if (mView != null) {
                mView.showToast("请输入内容");
            }
            return;
        }
        Msg msg = new Msg(content, TimeUtils.getFormattedTime(), Msg.TYPE_SENT);
        if (mView != null) {
            mView.addMessage(msg);
            addThinkingMsg();
            chat(content);
        }
    }

    private void addThinkingMsg() {

        Runnable thinkingRunnable = new Runnable() {
            @Override
            public void run() {
                Msg thinkingMsg = new Msg("", TimeUtils.getFormattedTime(), Msg.TYPE_THINKING);
                mView.addMessage(thinkingMsg);
            }
        };

        new Handler(Looper.getMainLooper()).postDelayed(thinkingRunnable, 800);
    }


    @Override
    public void chat(String content) {
        String token = TokenManager.getToken(mContext);

        JSONObject json = new JSONObject();
        try {
            json.put("user_input", content);
        } catch (Exception e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json.toString());
        Request.Builder builder = new Request.Builder();
        builder.url(AI_URL).post(body);
        if (token != null) {
            builder.addHeader("Authorization", "Bearer " + token);
        }
        Request request = builder.build();

        currentCall = client.newCall(request);
        currentCall.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
                mainHandler.post(() -> {
                    mainHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mView.removeThinkingMsg();

                        }
                    }, 1200);
                    Msg msg = new Msg("服务器繁忙，请稍后再试。", TimeUtils.getFormattedTime(), Msg.TYPE_RECEIVED);
                    mView.addMessage(msg);
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonResponse = response.body().string();
                Log.d(TAG, "onResponse: " + jsonResponse);

                try {
                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    int code = jsonObject.getInt("code");

                    if (code == 1) {
                        JSONObject dataObject = jsonObject.getJSONObject("data");
                        String answer = dataObject.getString("answer");
                        Msg msg = new Msg(answer, TimeUtils.getFormattedTime(), Msg.TYPE_RECEIVED);

                        mainHandler.post(() -> {
                            mView.removeThinkingMsg();
                            if (mView != null) {
                                mView.addMessage(msg);
                            }
                        });
                    } else {
                        Msg msg = new Msg("出了点问题", TimeUtils.getFormattedTime(), Msg.TYPE_RECEIVED);
                        if (mView != null) {
                            mView.removeThinkingMsg();
                            mView.addMessage(msg);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

   public boolean getLoginStatus(){
        return TokenManager.getLoginStatus(mContext);
    }

    @Override
    public void cancelCurrentRequest() {
        // 取消当前请求
        if (currentCall != null) {
            currentCall.cancel();
            currentCall = null;
            mView.stopRequest();
        }
    }

    @Override
    public List<Msg> loadLocalMsg() {
        String json = SPUtils.getString(mContext, SPUtils.MSGLIST_KEY);
        if (json != null) {
            Gson gson = new Gson();
            List<Msg> msgList = gson.fromJson(json, new TypeToken<List<Msg>>() {
            }.getType());
            return msgList;
        } else {
            return null;
        }
    }

    @Override
    public void saveToLocal(List<Msg> msgList) {
        if (msgList != null && msgList.size() >= 3) {
            Gson gson = new Gson();
            String json = gson.toJson(msgList);

            SPUtils.putString(mContext, SPUtils.MSGLIST_KEY, json);
        }
    }

    @Override
    public void clearLocalMsg() {
        SPUtils.clearMsgList(mContext);
    }

    @Override
    public void detachView() {
        this.mView = null;
        if (currentCall != null) {
            currentCall.cancel(); // 取消当前请求，防止内存泄漏
        }
    }
}