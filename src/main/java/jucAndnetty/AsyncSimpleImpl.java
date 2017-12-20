package jucAndnetty;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by WQS on 2017/9/1.
 * Mail: 1027738387@qq.com
 * Github: https://github.com/wannibar
 */

// 异步简单实现
// https://lingnanlu.github.io/2016/08/16/netty-asyc-callback
public class AsyncSimpleImpl {
    public static void main(String[] args) {

        final Person wang = new Person("wang");
        final Person li = new Person("li");

        li.start();
        wang.start();


        wang.submit(() -> System.out.println("task1 简单的数学题,很快做完" + Thread.currentThread().getName()));
        wang.submit(() -> {
            // 复杂的数学题,一时半会做不完,交给小李去做,小李把做完的结果封装成task插入到小王的任务队列
            li.submit(() -> {
                try {
                    Thread.sleep(3000);
                    System.out.println("task2 复杂的数学题,小李做了好久终于要做完了..准入插入结果到小王.." + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // 封装成task插入小王的队列
                wang.submit(() -> System.out.println("task2 复杂的数学题,耗费一些时间做完了" + Thread.currentThread().getName()));
            });
        });

        wang.submit(() -> System.out.println("task3 简单的数学题,很快做完" + Thread.currentThread().getName()));
    }

    public static class Person extends Thread {

        private String name;
        BlockingQueue<Runnable> taskQ = new LinkedBlockingQueue<>();

        public Person(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Runnable task = taskQ.take();
                    task.run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void submit(Runnable task) {
            taskQ.offer(task);
        }
    }
}
