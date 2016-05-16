package le.erqi.binderpoolsample.CountDownLatch;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 作　　者：ErQi <br>
 * 创建日期：2016/3/31  17:04 <br>
 */
public class CountDownLatchDemo {

    public static void main(String[] args) {
        // 来了5位顾客,服务员要等五位顾客都吃完走人了才能收拾桌子
        CountDownLatch downLatch = new CountDownLatch(5);
        Waiter waiter = new Waiter(downLatch);
        Customer san = new Customer(downLatch, "张三");
        Customer si = new Customer(downLatch, "李四");
        Customer wu = new Customer(downLatch, "王五");
        Customer liu = new Customer(downLatch, "赵六");
        Customer qi = new Customer(downLatch, "宋七");

        ExecutorService executor = Executors.newCachedThreadPool();
        executor.execute(waiter);
        executor.execute(san);
        executor.execute(si);
        executor.execute(wu);
        executor.execute(liu);
        executor.execute(qi);
    }

    static class Waiter implements Runnable {
        CountDownLatch mLatch;

        public Waiter(CountDownLatch latch) {
            mLatch = latch;
        }

        public void work() {
            try {
                System.err.println("服务员在等待");
                mLatch.await();
                System.err.println("服务员开始收拾桌子了");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            work();
        }
    }


    static class Customer implements Runnable {
        CountDownLatch mLatch;
        String mName;

        public Customer(CountDownLatch latch, String name) {
            mLatch = latch;
            mName = name;
        }

        public Customer(CountDownLatch latch) {
            mLatch = latch;
        }

        public void eat() {
            try {
                System.err.println(mName + "开始吃了");
                TimeUnit.SECONDS.sleep(new Random().nextInt(10));
                System.err.println(mName + "吃完了");
                mLatch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            eat();
        }
    }
}
