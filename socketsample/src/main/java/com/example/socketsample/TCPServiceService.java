package com.example.socketsample;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class TCPServiceService extends Service {
    private static final String TAG = TCPServiceService.class.getSimpleName();
    private boolean mIsServiceDestroyed = false;
    private String[] mDefinedMessages = new String[]{"你好啊,哈哈", "我有病,你有药么?", "今天天气好好啊,在家睡觉吧", "我可以多重有丝分裂哟", "给你说个笑话,你长的好好笑啊,哈哈哈哈"};

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        new Thread(new TcpServer()).start();
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        mIsServiceDestroyed = true;
        super.onDestroy();
    }

    private class TcpServer implements Runnable {
        @Override
        public void run() {
            ServerSocket serverSocket = null;
            try {
                // 监听本地8688端口
                serverSocket = new ServerSocket(8688);
            } catch (IOException e) {
                Log.getStackTraceString(e);
                return;
            }
            while (!mIsServiceDestroyed) {
                try {
                    // 接受客户端的请求
                    final Socket client = serverSocket.accept();
                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                responseClient(client);
                            } catch (IOException e) {
                                Log.getStackTraceString(e);
                            }
                        }
                    }.start();
                } catch (IOException e) {
                    Log.getStackTraceString(e);
                }
            }
        }
    }

    private void responseClient(Socket client) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
        out.println("欢迎来作死");
        while (!mIsServiceDestroyed) {
            String line = in.readLine();
            Log.e(TAG, "msg from client:" + line);
            if (line == null) {
                // 客户端已断开连接
                break;
            }
            int i = new Random().nextInt(mDefinedMessages.length);
            String msg = mDefinedMessages[i];
            out.println(msg);
            Log.e(TAG, "send :" + msg);
        }
        Log.e(TAG,"client quit.");
        out.close();
        in.close();
        client.close();
    }
}
