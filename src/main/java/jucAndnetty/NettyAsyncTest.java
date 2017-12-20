package jucAndnetty;

import io.netty.util.concurrent.DefaultEventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.Promise;

/**
 * Created by WQS on 2017/9/1.
 * Mail: 1027738387@qq.com
 * Github: https://github.com/wannibar
 */
// https://lingnanlu.github.io/2016/08/16/netty-asyc-callback
public class NettyAsyncTest {
    public static void main(String[] args) {

        final DefaultEventExecutor wang = new DefaultEventExecutor();
        final DefaultEventExecutor li = new DefaultEventExecutor();

        wang.execute(() -> System.out.println("task1 简单的数学题,很快做完" + Thread.currentThread().getName()));

        wang.execute(() -> {
            // 和AsyncSimpleImpl不同之处在于，小李做完复杂任务后通知方式。
            // 这里使用回调函数通知，但是执行线程仍然是在小王里面，而不是小李线程

            // 使用wang.newPromise保证了promise的监听器一定是在wang里面执行
            final Promise<Integer> promise = wang.newPromise();

            promise.addListener(future -> {
                System.out.println("task2 复杂的数学题,小李做了好久终于做完了" + Thread.currentThread().getName());
            });

            li.execute(() -> {
                System.out.println("task2 复杂的数学题交给小李做" + Thread.currentThread().getName());
                try {
                    Thread.sleep(3000);
                    promise.setSuccess(10); //小李做完任务设置结果
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

        });

        /**
         * 一种错误的使用方式
         */
        wang.execute(() -> {

            // DefaultEventExecutor.submit方法将一个Callable包装成一个DefaultPromise
            // 并且将执行者(li)作为DefaultPromise的exectutor,因为提交者(wang)可能没有TaskQueue
            // 而使用newPromise就没有问题, 因为newPromise里面肯定有一个TaskQueue
            Future<String> result = li.submit(() -> {
                Thread.sleep(3000);
                System.out.println("task(E) 耗时的任务执行完毕" + Thread.currentThread().getName());

                // execute肯定是添加到wang的线程队列
                wang.execute(() -> {
                    System.out.println("task(E) RIGHT WAY 复杂的数学题,小李做了好久终于做完了" + Thread.currentThread().getName());
                });

                return null;
            });

            result.addListener(future -> System.out.println("task(E) 复杂的数学题,小李做了好久终于做完了" + Thread.currentThread().getName()));


        });

        wang.execute(() -> System.out.println("task3 简单的数学题,很快做完" + Thread.currentThread().getName()));
    }
}
