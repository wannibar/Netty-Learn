package jucAndnetty;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * Created by WQS on 2017/9/1.
 * Mail: 1027738387@qq.com
 * Github: https://github.com/wannibar
 */
public class AtomicIntegerFieldUpdaterTest {
    public static void main(String[] args) throws Exception {

        Resource res = new Resource();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; ++i)
                res.atomicInc();
                //res.inc();
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; ++i)
                res.atomicInc();
                //res.inc();
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println(res);
    }


    public static class Resource {

        private volatile int cnt = 0;
        AtomicIntegerFieldUpdater<Resource> atomicCnt = AtomicIntegerFieldUpdater.newUpdater(Resource.class, "cnt");

        public void atomicInc() {
            atomicCnt.getAndIncrement(this);
        }

        public void inc() {
            cnt++;
        }

        @Override
        public String toString() {
            return "cnt: " + cnt;
        }
    }
}
