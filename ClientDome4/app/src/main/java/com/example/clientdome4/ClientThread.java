package com.example.clientdome4;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by heshu on 2017/9/2.
 */

public class ClientThread implements Runnable {
    private Socket mSocket;
    private BufferedReader mBufferedReader = null;
    private OutputStream mOutputStream = null;
    private Handler mHandler;

    public Handler revHandler;

    public ClientThread(Handler handler) {
        mHandler = handler;
    }

    @Override
    public void run() {
        try {
            mSocket = new Socket("139.199.60.129", 9733);
            Log.d("xjj","connect success");
            //输入流管道到客户端
            mBufferedReader = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
            mOutputStream = mSocket.getOutputStream();//输出流，客户端到管道

            //接受子线程
            new Thread(){
                @Override
                public void run() {
                    super.run();
                    try {
                        String content = null;
                        //接收消息
                        while ((content = mBufferedReader.readLine()) != null) {
                            Log.d("xjj",content);
                            //将接受到的数据传递给msg对象，并标记
                            Message handleMsg = new Message();
                            handleMsg.what = 0;
                            handleMsg.obj = content;
                            mHandler.sendMessage(handleMsg);
                        }
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }.start();//启动

            //Looper类用来为一个线程开启一个消息循环。
            // 默认情况下android中新诞生的线程是没有开启消息循环的。
            // （主线程除外，主线程系统会自动为其创建Looper对象，开启消息循环。）
            Looper.prepare();
            //绑定发送线程的Handler
            revHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == 1) {
                        try {
                            //发送消息
                            String content;
                            content =Name.IP+"#$#" + Name.name+"#$#" + msg.obj.toString() + "\r\n";
                            mOutputStream.write(content.getBytes("utf-8"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            //Looper.loop(); 让Looper开始工作，从消息队列里取消息，处理消息。
            Looper.loop();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("xjj","");
        }
    }

}
