package le.erqi.binderpoolsample;

import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Thread() {
            @Override
            public void run() {
                doWork();
            }
        }.start();
    }

    private void doWork() {
        BinderPool pool = BinderPool.getInstance(this);
        IBinder securityBinder = pool.queryBinder(BinderPool.BINDER_SECURITY_CENTER);
        ISecurityCenter securityCenter = SecurityCenterImpl.asInterface(securityBinder);
        String msg = "hello world - 安卓";
        try {
            String encrypt = securityCenter.encrypt(msg);
            Log.e(TAG, encrypt);
            Log.e(TAG, securityCenter.decrypt(encrypt));
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        IBinder computerBinder = pool.queryBinder(BinderPool.BINDER_COMPUTE);
        ICompute compute = ComputeImpl.asInterface(computerBinder);
        try {
            Log.e(TAG, String.valueOf(compute.add(3, 5)));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
