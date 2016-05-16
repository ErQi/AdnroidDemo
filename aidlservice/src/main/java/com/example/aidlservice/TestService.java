package com.example.aidlservice;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import com.example.aidllibrary.Book;
import com.example.aidllibrary.IBookManager;
import com.example.aidllibrary.IOnNewBookArrivedListener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class TestService extends Service {
    private List<Book> mBooks;
    private RemoteCallbackList<IOnNewBookArrivedListener> mCallbackList;
    IBookManager.Stub mIBinder = new IBookManager.Stub() {
        @Override
        public void addBook(Book book) throws RemoteException {
            if (mBooks == null) {
                mBooks = new CopyOnWriteArrayList<>(); //
            }
            mBooks.add(book);
            int n = mCallbackList.beginBroadcast();
            for (int i = 0; i < n; i++) {
                mCallbackList.getBroadcastItem(i).OnNewBookArrivedListener(book);
            }
            mCallbackList.finishBroadcast();
        }

        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBooks;
        }

        @Override
        public void onUpdate(Book book, Book newBook) throws RemoteException {
            int size = mBooks.size();
            for (int i = 0; i < size; i++) {
                //                if (book == mBooks.get(i)) {  传递过来的对象时通过
                if (book.equals(mBooks.get(i))) {
                    mBooks.remove(i);
                    mBooks.add(i, newBook);
                    return;
                }
            }
        }

        @Override
        public boolean onDelete(Book book) throws RemoteException {
            return mBooks.remove(book);
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
            if (mCallbackList == null) {
                mCallbackList = new RemoteCallbackList<>();
            }
            mCallbackList.register(listener);
        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
            if (mCallbackList == null) {
                return;
            }
            mCallbackList.unregister(listener);
        }

    };

    @Override
    public IBinder onBind(Intent intent) {
        int check = checkCallingOrSelfPermission("com.example.aidlservice.permission.ACCESS_BOOK_SERVICE");
        if (check == PackageManager.PERMISSION_DENIED) Log.e("TestService", "不包含对应权限");
        else Log.e("TestService", "包含权限");
        return mIBinder;
    }

    @Override
    public void onCreate() {
        Log.e("TestService", "onCreate服务已被创建");
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Log.e("TestService", "onDestroy服务已被销毁");
        super.onDestroy();
    }
}
