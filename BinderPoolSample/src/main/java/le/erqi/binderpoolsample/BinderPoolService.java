package le.erqi.binderpoolsample;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class BinderPoolService extends Service {
    private static final String TAG = BinderPoolService.class.getSimpleName();

    private Binder mBinderPool = new BinderPool.BinderPoolImpl();
    @Override
    public IBinder onBind(Intent intent) {
        return mBinderPool;
    }
}
