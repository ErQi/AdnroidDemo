// IBinderPll.aidl
package le.erqi.binderpoolsample;

// Declare any non-default types here with import statements

interface IBinderPool {
    IBinder queryBinder(int binderCode);
}
