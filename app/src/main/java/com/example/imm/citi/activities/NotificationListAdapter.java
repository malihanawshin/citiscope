package com.example.imm.citi.activities;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.imm.citi.R;
import com.example.imm.citi.technicalClasses.Notification;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by imm on 7/14/2017.
 */

public class NotificationListAdapter extends RecyclerView.Adapter{

    private List<Notification> notifications;
    private Context nContext;
    private NotificationClickCallback nCallback;

    public interface NotificationClickCallback{
        public void onCrossClick(int position);
    }

    public NotificationListAdapter(Context nContext,List<Notification> notifications,NotificationClickCallback nCallback){

        this.nContext = nContext;
        this.notifications = notifications;
        this.nCallback = nCallback;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(nContext).inflate(R.layout.notification_item,parent,false);
        return new NotificationHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((NotificationHolder)holder).bind(notifications.get(position));
    }

    @Override
    public int getItemCount() {return notifications.size(); }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class NotificationHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.text_notification)TextView notificationText;
        @BindView(R.id.btnToRemoveNotification)ImageButton cancel;

        public NotificationHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        void bind(Notification notification){
            notificationText.setText(notification.text);
            cancel.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.btnToRemoveNotification:
                    cancelNotification();
                    break;
            }
    }

        private void cancelNotification() {
            if ( nCallback!= null) {
                int position = getAdapterPosition();
                nCallback.onCrossClick(position);
            }
        }
    }

}
