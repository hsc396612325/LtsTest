package com.example.clientdome4;

/**
 * Created by heshu on 2017/9/2.
 */


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by heshu on 2017/8/29.
 */

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder>{
    private List<Msg> mMsgList;
    //ViewHolder内部类
    static class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout leftLayout;
        LinearLayout rightLayout;
        TextView leftMsg;
        TextView rightMsg;
        TextView leftname;
        //构造方法
        public ViewHolder(View view){
            super(view);
            leftLayout = (LinearLayout)view.findViewById(R.id.left_layout);
            rightLayout = (LinearLayout) view.findViewById(R.id.right_layout);
            leftMsg = (TextView)view.findViewById(R.id.left_msg);
            rightMsg = (TextView)view.findViewById(R.id.right_msg);
            leftname = (TextView) view.findViewById(R.id.left_name);
        }
    }

    //MsgAdapter构造方法,参数是消息类的集合
    public MsgAdapter(List<Msg> msgList){
        mMsgList = msgList;
    }

    @Override
    //创建ViewHolder实例
    public ViewHolder onCreateViewHolder(ViewGroup parent , int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_item ,parent,false);
        return new ViewHolder(view);
    }
    //当子项滚动到屏幕时，执行此方法
    @Override
    public void onBindViewHolder(ViewHolder holder ,int position){
        Msg msg = mMsgList.get(position);
        if(msg.getType() == Msg.TYPE_RECEIVED){
            //如果是收到的消息，则显示左边的消息布局，将右边的消息布局隐藏
            holder.leftLayout.setVisibility(View.VISIBLE);
            holder.rightLayout.setVisibility(View.GONE);
            holder.leftMsg.setText(msg.getContent());
            holder.leftname.setText(msg.getName());
        }else if(msg.getType()==Msg.TYPE_SENT){
            //如果是发出去的消息，则显示右边的消息布局，将左边的消息布局隐藏
            holder.rightLayout.setVisibility(View.VISIBLE);
            holder.leftLayout.setVisibility(View.GONE);
            holder.rightMsg.setText(msg.getContent());
        }
    }

    @Override
    public int getItemCount(){
        return mMsgList.size();
    }
    JSONObject
}
