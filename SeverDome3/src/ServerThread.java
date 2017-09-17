import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.Iterator;

/**
 * Created by heshu on 2017/8/29.
 */
public class ServerThread implements Runnable {
    private Socket mSocket = null;
    private BufferedReader mBufferedReader = null;
    //构造方法
    public ServerThread(Socket s)throws IOException{
        mSocket = s;
        //输入管道到服务器
        mBufferedReader = new BufferedReader(new InputStreamReader(s.getInputStream(), "utf-8"));
    }
    public void run(){
        try {
            String content = null;
            //循环接受服务器消息，如果没有接收到，说明该客户端下线，将其从线程池中删除
            while ((content = mBufferedReader.readLine())!=null){
                System.out.println("ip:"+ mSocket.getInetAddress().getHostAddress()+":"+content);

                //循环向其他线程发送消息
                for (Iterator<Socket> it = MyServer.mSocketList.iterator();
                    it.hasNext();) {
                Socket s = it.next();
                try {
                    OutputStream os = s.getOutputStream();
                    os.write((content + "\n").getBytes("utf-8"));
                } catch (SocketException e) {
                    e.printStackTrace();
                    it.remove();
                }
            }
        }
        }catch (IOException e){
            System.out.println("接收出错");
            try {
                mSocket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            MyServer.mSocketList.remove(mSocket);
            System.out.println("ip:"+ mSocket.getInetAddress().getHostAddress() +"退出聊天室");
        }
    }
}
