package com.example.chatpageview.model;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.chatpageview.bean.Msg;
import com.example.chatpageview.contract.IChatContract;
import com.example.chatpageview.presenter.ChatPresenterImpl;
import com.example.module.libBase.utils.SPUtils;
import com.example.module.libBase.utils.TimeUtils;
import com.example.module.libBase.utils.TokenManager;
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

public class ChatModelImpl implements IChatContract.Model {
    private static final String TAG = "ChatModelImpl";
    private ChatPresenterImpl mPresenter;
    private Context mContext;
    private static final OkHttpClient client = new OkHttpClient();
    private static final String AI_URL = "http://101.200.122.3:8080/ai";
    private Call currentCall;
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    public ChatModelImpl(ChatPresenterImpl presenter, Context context) {
        mPresenter = presenter;
        mContext = context;
    }

    @Override
    public List<Msg> initMessages(int role) {
        List<Msg> msgList = loadLocalMsg(role);

        String[] welcomeMessages = getInitWelcomeMessages(role);

        if (msgList == null || msgList.isEmpty()) {
            Random random = new Random();
            int index = random.nextInt(3);
            String selectedMessage = welcomeMessages[index];

            Msg msg = new Msg(selectedMessage, TimeUtils.getFormattedTime(), Msg.TYPE_RECEIVED);
            msgList = new ArrayList<>();
            msgList.add(msg);
        }
        return msgList;
    }

    private String[] getInitWelcomeMessages(int role) {
        if (role == 2) {
            return new String[]{
                    "您好！我是您的农业小助手，很高兴为您服务。请问您想了解哪方面的农业知识呢？无论是作物种植、土壤管理，还是农业新技术，我都会耐心为您解答！🌾",
                    "您好！我是您的农业小助手，期待为您提供帮助。关于农业的任何问题，如作物栽培、土壤维护或最新的农业科技，欢迎向我咨询！🌱",
                    "您好！我是您的农业小助手，很高兴为您服务。无论您对农业的哪个方面感兴趣，例如作物培育、土壤改良，还是先进的农业技术，我都在这里为您解答！🌿"
            };
        } else if (role == 1) {
            return new String[]{
                    "在下乃古代农学研究者，精通《齐民要术》，致力于传承农学智慧。愿为君解读古籍，启迪思考，助君领略农业之道。欢迎君共探农学奥秘！《齐民要术》强调“顺天时，量地利”，教导我们根据自然规律耕作，方能事半功倍，收获丰盈🌾",
                    "在下乃古代农学研究者，精通《齐民要术》，致力于传承农学智慧。愿为君解读古籍，启迪思考，助君领略农业之道。欢迎君共探农学奥秘！🌱",
                    "在下乃古代农学研究者，精通《齐民要术》，致力于传承农学智慧。愿为君解读古籍，启迪思考，助君领略农业之道。欢迎君共探农学奥秘！书中详细记载了轮作、绿肥等技术，提倡用地与养地结合，为现代农业的可持续发展提供了宝贵经验🌿"
            };
        } else if (role == 3) {
            return new String[]{
                    "我是一位农耕文明的守护者，自然诗篇的吟唱者。穿越时空，将古人的智慧与现代思考交织，传递二十四节气的农耕魅力。欢迎你，与我一同感受自然律动，品味农耕智慧。🌾",
                    "我是一位农耕文明的守护者，自然诗篇的吟唱者。在春分的阳光下，我播下希望的种子；在秋分的微风中，我收获丰硕的果实。穿越时空，将古人的智慧与现代思考交织，传递二十四节气的农耕魅力。欢迎你，与我一同感受自然律动，品味农耕智慧🌱",
                    "我是一位农耕文明的守护者，自然诗篇的吟唱者。在清明的细雨里，我祭奠先人的智慧；在霜降的晨露中，我迎接冬日的脚步。穿越时空，将古人的智慧与现代思考交织，传递二十四节气的农耕魅力。欢迎你，与我一同感受自然律动，品味农耕智慧🌿"
            };
        } else if (role == 4) {
            return new String[]{
                    "我是一位农业文化的传承者，用细腻笔触描绘各地农业风貌，讲述农作物故事。欢迎您踏入这片充满地域特色的农耕世界，感受农业文化的魅力。🌾",
                    "作为行走的农耕史官，我用镜头定格梯田的指纹，以文字缝合节气的裂痕。每一株作物都是打开文明的密码，邀您共赴一场跨越千年的田野对话。🌱",
                    "在传统与现代的阡陌间摆渡，我收集方言里的农谚、抚摸农具上的包浆。当收割机与耧车在文字里相遇，愿为您展开这幅正在生长的农业文明长卷。🌿"
            };
        }
        return null;
    }

    @Override
    public void chat(String content, int role) {
        String token = TokenManager.getToken(mContext);

        JSONObject json = new JSONObject();
        try {
            json.put("user_input", content);
            json.put("role", role);
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
                            mPresenter.removeThinkingMsg();

                        }
                    }, 1200);
                    Msg msg = new Msg("服务器繁忙，请稍后再试。", TimeUtils.getFormattedTime(), Msg.TYPE_RECEIVED);
                    mPresenter.addMessage(msg);
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
                            mPresenter.removeThinkingMsg();
                            if (mPresenter != null) {
                                mPresenter.addMessage(msg);
                            }
                        });
                    } else {
                        Msg msg = new Msg("出了点问题", TimeUtils.getFormattedTime(), Msg.TYPE_RECEIVED);
                        if (mPresenter != null) {
                            mPresenter.removeThinkingMsg();
                            mPresenter.addMessage(msg);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private List<Msg> loadLocalMsg(int role) {
        String json = null;
        if (role == 1) {
            json = SPUtils.getString(mContext, SPUtils.MSGLIST_1_KEY);
        } else if (role == 2) {
            json = SPUtils.getString(mContext, SPUtils.MSGLIST_2_KEY);
        } else if (role == 3) {
            json = SPUtils.getString(mContext, SPUtils.MSGLIST_3_KEY);
        } else if (role == 4) {
            json = SPUtils.getString(mContext, SPUtils.MSGLIST_4_KEY);
        }
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
    public void saveToLocal(List<Msg> msgList, int role) {
        if (msgList != null && msgList.size() >= 3) {
            Gson gson = new Gson();
            String json = gson.toJson(msgList);
            if (role == 1) {
                SPUtils.putString(mContext, SPUtils.MSGLIST_1_KEY, json);
            } else if (role == 2) {
                SPUtils.putString(mContext, SPUtils.MSGLIST_2_KEY, json);
            } else if (role == 3) {
                SPUtils.putString(mContext, SPUtils.MSGLIST_3_KEY, json);
            } else if (role == 4) {
                SPUtils.putString(mContext, SPUtils.MSGLIST_4_KEY, json);
            }
        }
    }

    @Override
    public void clearLocalMsg(int role) {
        SPUtils.clearMsgList(mContext, role);
    }

    @Override
    public boolean getLoginStatus() {
        return TokenManager.getLoginStatus(mContext);
    }

    @Override
    public void stopRequest() {
        // 取消当前请求
        if (currentCall != null) {
            currentCall.cancel();
            currentCall = null;
            if (mPresenter != null)
                mPresenter.stopRequest();
        }
    }
}
