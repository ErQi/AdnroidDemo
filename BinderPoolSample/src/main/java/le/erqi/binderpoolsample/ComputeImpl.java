package le.erqi.binderpoolsample;

import android.os.RemoteException;

/**
 * 作　　者：齐 涛  <br>
 * 创建日期：2016/3/31  11:48 <br>
 */
public class ComputeImpl extends ICompute.Stub {
    @Override
    public int add(int a, int b) throws RemoteException {
        return a + b;
    }
}
