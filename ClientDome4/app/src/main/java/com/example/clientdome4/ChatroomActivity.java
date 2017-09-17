package com.example.clientdome4;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class ChatroomActivity extends AppCompatActivity {

    private List<Msg> msgList = new ArrayList<>();
    private EditText inputTest;
    private Button send;
    private RecyclerView msgRecyclerView;
    private MsgAdapter adapter;
    private ClientThread mClientThread;


    private Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);

        //各种赋值初始化
        inputTest = (EditText)findViewById(R.id.input_text);
        send = (Button)findViewById(R.id.send);
        msgRecyclerView =(RecyclerView)findViewById(R.id.msg_recycler_view);
        //创建一个LinearLayoutManager（线性布局）对象将它设置到RecyclerView
        LinearLayoutManager layoutmanager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(layoutmanager);
        //调用构造方法创造实例,参数消息集合
        adapter = new MsgAdapter(msgList);
        msgRecyclerView.setAdapter(adapter);

        //重写Handler类handleMessage方法，并将对象赋给mHandler
        //该Handler绑定主线程，如果主线程的what为0则更新输入
        mHandler=new Handler() {
            @Override
            public void handleMessage(Message handleMsg) {
                if (handleMsg.what == 0) {
                    //接受到消息后的操作
                    String content = handleMsg.obj.toString();
                    Log.d( "xjj",content);
                    String [] arr = content.split("#\\$#");
                    String ip =arr[0];
                    String name =arr[1];
                    String str =arr[2];
                    Log.d( "xjj",ip + name + str);
                    Msg msg;
                    if(ip.equals(Name.IP)) {
                        msg = new Msg(name, str, Msg.TYPE_SENT);
                    }else {
                        msg = new Msg(name, str, Msg.TYPE_RECEIVED);
                    }
                    msgList.add(msg);
                    adapter.notifyItemInserted(msgList.size() - 1);//当有新消息时，刷新RecyclView中的显示
                    msgRecyclerView.scrollToPosition(msgList.size() - 1);//将RecyclerView定位到最后一行
                    inputTest.setText("");//清空输入框*/
                }
            }
        };

        //发送按钮监听器
        send.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                String content = inputTest.getText().toString();
                if(!"".equals(content)){
                    try {
                        //将输入框的信息传递给msg，并标记
                        Message handleMsg = new Message();
                        handleMsg.what = 1;
                        handleMsg.obj = inputTest.getText().toString();
                        //将msg传递给发送子线程
                        mClientThread.revHandler.sendMessage(handleMsg);
                        //输入框变空
                        inputTest.setText("");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        //创建实例，将mHandler作为参数传递给mClientThread实例
        mClientThread = new ClientThread(mHandler);
        //启动子线程
        new Thread(mClientThread).start();
    }
}
