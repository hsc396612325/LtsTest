package com.example.clientdome4;

/**
 * Created by heshu on 2017/9/2.
 */

public class Msg {
    public static final int TYPE_RECEIVED = 0;//收到的消息
    public static final int TYPE_SENT = 1;//发出去的消息
    private String name;
    private String content;
    private int type;
    //content表示消息内容，type表示类型
    public Msg(String name,String content ,int type){
        this.name = name;
        this.content = content;
        this.type = type;
    }
    public String getContent(){
        return content;
    }
    public int getType(){
        return type;
    }
    public String getName() {return name;}
}