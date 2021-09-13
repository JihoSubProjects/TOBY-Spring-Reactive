package toby.live.examples;

import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.TimeUnit;

public class PubSub {

    public static void main(String[] args) throws InterruptedException {

        // Publisher : Observable
        // Subscriber : Observer

        Iterable<Integer> iterable = Arrays.asList(1, 2, 3, 4, 5);
        ExecutorService es = Executors.newCachedThreadPool();

        Publisher publisher = new Publisher() {

            Iterator<Integer> iterator = iterable.iterator();

            @Override
            public void subscribe(Subscriber subscriber) {
                subscriber.onSubscribe(new Subscription() {
                    @Override
                    public void request(long n) {
                        es.execute(() -> {
                            int i = 0;
                            try {
                                while (i++ < n) {
                                    if (iterator.hasNext()) {
                                        subscriber.onNext(iterator.next());
                                    } else {
                                        subscriber.onComplete();
                                        break;
                                    }
                                }
                            } catch (RuntimeException e) {
                                subscriber.onError(e);
                            }
                        });
                    }

                    @Override
                    public void cancel() {

                    }
                });
            }
        };

        Subscriber subscriber = new Subscriber() {

            Subscription subscription;
//            int bufferSize = 2;

            @Override
            public void onSubscribe(Subscription subscription) {
                System.out.println(Thread.currentThread().getName() + " [onSubscribe] " + subscription);
//                subscription.request(Long.MAX_VALUE);
                this.subscription = subscription;
//                this.subscription.request(this.bufferSize);
                this.subscription.request(1);
            }

            @Override
            public void onNext(Object item) {
                // like update() of Observer Pattern
                System.out.println(Thread.currentThread().getName() + " [onNext] " + item);
//                if (--this.bufferSize <= 0) {
//                    this.bufferSize = 2;
//                    this.subscription.request(this.bufferSize);
//                }
                this.subscription.request(1);
            }

            @Override
            public void onError(Throwable throwable) {
                // 별도의 try-catch 구문을 사용하지 않고
                // 이 메소드로 Exception 처리
                System.out.println(Thread.currentThread().getName() + " [onError] " + throwable.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println(Thread.currentThread().getName() + " [onComplete] Completed!");
            }
        };

        publisher.subscribe(subscriber);

        es.awaitTermination(10, TimeUnit.HOURS);
        es.shutdown();

    }

}
