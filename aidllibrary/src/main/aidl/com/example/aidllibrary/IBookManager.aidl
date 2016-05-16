// IBookManaget.aidl
package com.example.aidllibrary;
import com.example.aidllibrary.Book;
import com.example.aidllibrary.IOnNewBookArrivedListener;

interface IBookManager {
    void addBook(in Book book);
    List<Book> getBookList();
    void onUpdate(in Book book,in Book newbook);
    boolean onDelete(in Book book);
    void registerListener(in IOnNewBookArrivedListener listener);
    void unregisterListener(in IOnNewBookArrivedListener listener);
}
