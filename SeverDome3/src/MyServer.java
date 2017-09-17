import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by heshu on 2017/8/29.
 */
public class MyServer {
    public static ArrayList<Socket> mSocketList = new ArrayList<>() ;
    public static void main(String[] args){
        try {
            //创建服务器Socket
            ServerSocket ss = new ServerSocket(9733);
            while (true){
                //监听链接
                Socket s = ss.accept();
                //打印信息
                System.out.println("ip:"+ s.getInetAddress().getHostAddress() +"加入聊天室");
                //将s加入到线程池中
                mSocketList.add(s);
                //启动子线程
                new Thread(new ServerThread(s)).start();
            }
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("服务器已崩溃");
            e.printStackTrace();

        }
    }
}
