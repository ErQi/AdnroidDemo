package com.example.messengersample;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

public class MessengerService extends Service {
    public static final String MSG = "msg";
    public static final int MESSAGE = 9;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MESSAGE:
                    Messenger client = msg.replyTo;
                    Log.e("MessengerService", "收到消息:" + msg.getData().get(MSG));
                    Message message = Message.obtain();
                    Bundle bundle = new Bundle();
                    bundle.putString(MSG, "收到消息了");
                    message.setData(bundle);
                    message.what = MESSAGE;
                    try {
                        client.send(message);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }
    };
    private Messenger mMessenger = new Messenger(mHandler);

    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }
}
