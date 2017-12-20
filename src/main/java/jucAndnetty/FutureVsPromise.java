package jucAndnetty;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

// https://stackoverflow.com/questions/14541975/difference-between-future-and-promise
public class FutureVsPromise {
    public static void main(String[] args) {
        Supplier<Integer> monPurse = () -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 100;
        };

        ExecutorService ex = Executors.newFixedThreadPool(10);
        CompletableFuture<Integer> promise = CompletableFuture.supplyAsync(monPurse, ex);

        promise.complete(10); // bad dad

        promise.thenAccept( u -> System.out.println("Thank you mom for $"+u));
    }
}
