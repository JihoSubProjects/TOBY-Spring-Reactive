package toby.live.examples;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Java 9부터 Deprecated 되었다.
// 취소선 보기 숭하니까 추가한다.
@SuppressWarnings("deprecation")
public class ObservableExample {

    // Publisher
    // Observable
    static class IntObservable extends Observable implements Runnable {
        @Override
        public void run() {
            for (int i = 1; i <= 10; i++) {
                setChanged();
                // Duality 측면에서 Iterable next()
                notifyObservers(i);
            }
        }
    }

    public static void main(String[] args) {

        // Observable?
        // source -> event/data -> Observer

        // Subscriber
        // Observer
        Observer observer = new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                System.out.println(Thread.currentThread().getName() + " : " + arg);
            }
        };

        // Publisher
        // Observable
        IntObservable observable = new IntObservable();

        // Register
        observable.addObserver(observer);

        observable.run();

        // Non-Main Thread
        ExecutorService es = Executors.newSingleThreadExecutor();

        es.execute(observable);
        System.out.println(Thread.currentThread().getName() + " : " + "EXIT");
        es.shutdown();

    }

}
