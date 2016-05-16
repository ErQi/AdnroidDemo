package com.example.socketsample;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView mTextView;
    private EditText mEditText;
    private Button mButton;
    private PrintWriter mPrintWriter;
    private Socket mClientSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, TCPServiceService.class);
        startService(intent);
        mButton = (Button) findViewById(R.id.button);
        mEditText = (EditText) findViewById(R.id.editText);
        mTextView = (TextView) findViewById(R.id.textView);

        new Thread() {
            @Override
            public void run() {
                connectTCPServer();
            }
        }.start();
    }

    private void connectTCPServer() {
        Socket socket = null;

        while (socket == null) {
            try {
                socket = new Socket("localhost", 8688);
                mClientSocket = socket;
                mPrintWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mButton.setEnabled(true);
                    }
                });
            } catch (IOException e) {
                SystemClock.sleep(1000);
                Log.getStackTraceString(e);
            }
        }
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (!MainActivity.this.isFinishing()) {
                String msg = br.readLine();
                if (msg != null) {
                    String time = formatDateTime(System.currentTimeMillis());
                    final String showedMsg = "server " + time + " :" + msg + "\n";
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTextView.setText(mTextView.getText() + showedMsg);
                        }
                    });
                }
            }
            Log.e(TAG, "quit...");
            mPrintWriter.close();
            br.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String formatDateTime(long time) {
        return new SimpleDateFormat("(HH:mm:ss)").format(new Date(time));
    }

    @Override
    protected void onDestroy() {
        if (mClientSocket != null) {
            try {
                mClientSocket.shutdownInput();
                mClientSocket.close();
            } catch (IOException e) {
                Log.getStackTraceString(e);
            }
        }
        super.onDestroy();
    }

    public void send(View view) {
        String msg = mEditText.getText().toString();
        if (mPrintWriter != null) {
            mPrintWriter.println(msg);
            mEditText.setText("");
            String time = formatDateTime(System.currentTimeMillis());
            String showedMsg = "self " + time + " :" + msg + "\n";
            mTextView.setText(mTextView.getText() + showedMsg);
        }
    }
}
