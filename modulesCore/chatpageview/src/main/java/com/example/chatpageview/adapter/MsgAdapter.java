package com.example.chatpageview.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatpageview.R;
import com.example.chatpageview.bean.Msg;

import java.util.List;

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.MyViewHolder> {
    private List<Msg> msgList;

    public MsgAdapter(List<Msg> msgList) {
        this.msgList = msgList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.msg_item, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Msg msg = msgList.get(position);
        if (msg.getType() == Msg.TYPE_RECEIVED) {
            holder.leftLayout.setVisibility(View.VISIBLE);
            holder.rightLayout.setVisibility(View.GONE);
            holder.thinkinglayout.setVisibility(View.GONE);
            holder.leftMsg.setText(msg.getContent());
            holder.leftTime.setText(msg.getTime());
        }else if(msg.getType() == Msg.TYPE_SENT){
            holder.rightLayout.setVisibility(View.VISIBLE);
            holder.leftLayout.setVisibility(View.GONE);
            holder.thinkinglayout.setVisibility(View.GONE);
            holder.rightMsg.setText(msg.getContent());
            holder.rightTime.setText(msg.getTime());
        }else if(msg.getType() == Msg.TYPE_THINKING){
            holder.leftLayout.setVisibility(View.GONE);
            holder.rightLayout.setVisibility(View.GONE);
            holder.thinkinglayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return msgList.size();
    }

    public void setMsgList(List<Msg> msgList) {
        this.msgList = msgList;
    }

    public void addMessage(Msg msg) {
        msgList.add(msg);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout leftLayout;
        private LinearLayout rightLayout;
        private LinearLayout thinkinglayout;
        private TextView leftMsg;
        private TextView rightMsg;
        private TextView rightTime;
        private TextView leftTime;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            leftLayout = itemView.findViewById(R.id.item_message_left);
            rightLayout = itemView.findViewById(R.id.item_message_right);
            thinkinglayout = itemView.findViewById(R.id.item_message_thinking);
            leftMsg = itemView.findViewById(R.id.left_message);
            rightMsg = itemView.findViewById(R.id.right_message);
            leftTime = itemView.findViewById(R.id.left_time);
            rightTime = itemView.findViewById(R.id.right_time);
        }
    }
}
