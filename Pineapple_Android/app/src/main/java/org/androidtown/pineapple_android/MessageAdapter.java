package org.androidtown.pineapple_android;

import android.app.DialogFragment;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by hanhb on 2018-04-12.
 */


public class MessageAdapter extends RecyclerView.Adapter {


    private Context context;
    private DialogFragment dialogFragment;
    private ArrayList<Message> messageList = MainActivity.messageList;
    private SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");

    MessageAdapter(Context context, DialogFragment dialogFragment) {
        this.context = context;
        this.dialogFragment = dialogFragment;
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messageList.get(position);
        return message.getMessageType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        switch (viewType) {

            case GroupConstants.MY_MESSAGE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_message, parent, false);
                view.setOnClickListener(new OnTouchToFinishListener());
                return new MyMessageHolder(view);

            case GroupConstants.BOT_MESSAGE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bot_message, parent, false);
                view.setOnClickListener(new OnTouchToFinishListener());
                return new BotMessageHolder(view);

        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Message message = messageList.get(position);

        switch (holder.getItemViewType()) {

            case GroupConstants.MY_MESSAGE :
                ((MyMessageHolder)holder).bind(message);
                break;

            case GroupConstants.BOT_MESSAGE :
                ((BotMessageHolder)holder).bind(message);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public void setMessageList(ArrayList<Message> messageList) {
        this.messageList = messageList;
    }

    public void addMessage(Message message) {
        this.messageList.add(message);
    }


    //long 형의 타임스탬프를 HH:MM 형식의 스트링으로 변환한다
    private String getTimeString(long timestamp) {

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp);
        return fmt.format(cal.getTime());

    }


    /*
        View Holders
        MyMessageHolder : 내가 보낸 음성메시지
        BotMessageHolder : 봇
     */
    private class MyMessageHolder extends RecyclerView.ViewHolder {

        private TextView contentTextView;
        private TextView timeTextView;

        public MyMessageHolder(View itemView) {
            super(itemView);
            contentTextView = itemView.findViewById(R.id.content_my_tv);
            timeTextView = itemView.findViewById(R.id.time_my_tv);
        }

        public void bind(Message message) {
            contentTextView.setText(message.getMessageContent());
            String time = getTimeString(message.getTimeStamp());
            timeTextView.setText(time);

        }
    }

    private class BotMessageHolder extends RecyclerView.ViewHolder {

        private TextView contentTextView;
        private TextView timeTextView;

        public BotMessageHolder(View itemView) {
            super(itemView);
            contentTextView = itemView.findViewById(R.id.content_bot_tv);
            timeTextView = itemView.findViewById(R.id.time_bot_tv);

        }

        public void bind(Message message) {
            contentTextView.setText(message.getMessageContent());
            String time = getTimeString(message.getTimeStamp());
            timeTextView.setText(time);

        }
    }

    //터치 시 dismiss the dialog.
    private class OnTouchToFinishListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            dialogFragment.dismiss();
        }
    }
}
