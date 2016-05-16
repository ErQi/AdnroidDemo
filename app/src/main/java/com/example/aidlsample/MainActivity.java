package com.example.aidlsample;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aidllibrary.Book;
import com.example.aidllibrary.IBookManager;
import com.example.aidllibrary.IOnNewBookArrivedListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity{
    private IOnNewBookArrivedListener mArrivedListener =new IOnNewBookArrivedListener.Stub() {
        @Override
        public void OnNewBookArrivedListener(Book newBook) throws RemoteException {
            Log.e("MainActivity", "添加了新的书:" + newBook.toString());
        }
    };

    IBookManager mManager;
    @Bind(R.id.money)
    EditText mId;
    @Bind(R.id.name)
    EditText mName;
    @Bind(R.id.add)
    Button mAdd;
    @Bind(R.id.update)
    Button mUpdate;
    @Bind(R.id.delete)
    Button mDelete;
    @Bind(R.id.list)
    RecyclerView mList;
    List<Book> mBooks;
    Book mBook = new Book();
    private IBinder.DeathRecipient mRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            Toast.makeText(MainActivity.this, "连接断开", Toast.LENGTH_SHORT).show();
            if (mManager == null) {
                return;
            }
            mManager.asBinder().unlinkToDeath(mRecipient, 0); // 用来解除死亡回调
            mManager = null; // 将对象置空
            bind(); // 再次绑定
        }
    };
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mManager = IBookManager.Stub.asInterface(service); // 获得调用对象本身
            Log.e("MainActivity", "连接成功");
            try {
                mManager.registerListener(mArrivedListener);
                service.linkToDeath(mRecipient, 0); // 注册连接死亡回调
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("MainActivity", "连接失败");
        }
    };
    private SampleAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        bind();
        mBooks = new ArrayList<>();
        mList.setLayoutManager(new LinearLayoutManager(this));
        mList.setHasFixedSize(true);
        mAdapter = new SampleAdapter();
        mList.setAdapter(mAdapter);
    }

    private void bind() {
        Log.e("MainActivity", "bind");
        Intent intent = new Intent("com.race604.remoteservice");
        bindService(intent, mConnection, BIND_AUTO_CREATE);
    }

    @OnClick(R.id.add)
    public void onAdd(View view) {
        if (mManager != null) {
            try {
                mManager.addBook(new Book(Integer.parseInt(String.valueOf(mId.getText())), String.valueOf(mName.getText())));
                onUpdateInfo();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @OnClick(R.id.delete)
    public void onDelete(View view) {
        if (mManager != null) {
            try {
                mManager.onDelete(mBook);
                onUpdateInfo();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }


    @OnClick(R.id.update)
    public void onUpdate(View view) {
        if (mManager != null) {
            try {
                mManager.onUpdate(mBook, new Book(Integer.parseInt(String.valueOf(mId.getText())), String.valueOf(mName.getText())));
                onUpdateInfo();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private void onUpdateInfo() throws RemoteException {
        mBooks = mManager.getBookList();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        unbindService(mConnection);
        super.onDestroy();
    }

    class SampleAdapter extends RecyclerView.Adapter<SampleHolder> {

        @Override
        public SampleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new SampleHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, null));
        }

        @Override
        public void onBindViewHolder(SampleHolder holder, int position) {
            final Book book = mBooks.get(position);
            holder.mMoney.setText(String.valueOf(book.getBookId()));
            holder.mName.setText(book.getBookName());
            holder.mCheckbox.setChecked(mBook.equals(book));
            holder.mCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        mBook = book;
                        notifyDataSetChanged();
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mBooks == null ? 0 : mBooks.size();
        }
    }

    class SampleHolder extends RecyclerView.ViewHolder {
        TextView mName;
        TextView mMoney;
        CheckBox mCheckbox;

        public SampleHolder(View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.name);
            mMoney = (TextView) itemView.findViewById(R.id.money);
            mCheckbox = (CheckBox) itemView.findViewById(R.id.checkbox);
        }
    }
}
