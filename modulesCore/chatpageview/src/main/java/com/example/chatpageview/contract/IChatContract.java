package com.example.chatpageview.contract;

import com.example.chatpageview.bean.Msg;

import java.util.List;

public interface IChatContract {
    interface View {
        void addMessage(Msg msg);

        void showToast(String message);

        void removeThinkingMsg();

        void stopRequest();
    }

    interface Presenter {
        List<Msg> initMessages();

        void sendMessage(String content);

        void detachView();

        void chat(String content);

        void cancelCurrentRequest();

        List<Msg> loadLocalMsg();

        void saveToLocal(List<Msg> msgList);

        void clearLocalMsg();

        boolean getLoginStatus();
    }


}
