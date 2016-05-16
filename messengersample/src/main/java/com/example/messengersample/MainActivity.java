package com.example.messengersample;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private Messenger mService;
    private int i = 0;
    private Messenger mMessenger = new Messenger(new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MessengerService.MESSAGE:
                    Log.e("MainActivity", "返回的消息:" + msg.getData().get(MessengerService.MSG));
                    break;
                default:
                    break;
            }
        }
    });
    private ServiceConnection mConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mService = new Messenger(service);
                Message message = Message.obtain();
                Bundle bundle = new Bundle();
                bundle.putString(MessengerService.MSG, "第一次连接成功");
                message.setData(bundle);
                message.what = MessengerService.MESSAGE;
                message.replyTo = mMessenger;
                try {
                    mService.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        Intent intent = new Intent(this, MessengerService.class);
        bindService(intent, mConnection, BIND_AUTO_CREATE);
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    while (i < 50) {
                        onMessenger(null);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        unbindService(mConnection);
        super.onDestroy();
    }

    public void onMessenger(View view) {
        Message message = Message.obtain();
        Bundle bundle = new Bundle();
        bundle.putString(MessengerService.MSG, "第" + i + "次发送消息;");
        message.setData(bundle);
        message.what = MessengerService.MESSAGE;
        message.replyTo = mMessenger;
        try {
            mService.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
            Log.getStackTraceString(e);
        }
        i++;
    }
}
