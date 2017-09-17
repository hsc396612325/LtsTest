package com.example.clientdome4;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class RegisterActivity extends AppCompatActivity {

    private EditText input_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        input_name = (EditText)findViewById(R.id.input_name);
        Button confirm = (Button)findViewById(R.id.confirm);

        confirm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Name.name = input_name.getText().toString();
                Name.IP = getLocalIpAddress() ;
                Log.e("RegisterActivity",Name.IP+Name.name);
                if(!Name.name.equals("")) {
                    Intent intent = new Intent(RegisterActivity.this, ChatroomActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    //获取本机ip
    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            Log.e("WifiPreference IpAddress", ex.toString());
        }

        return null;
    }
}
