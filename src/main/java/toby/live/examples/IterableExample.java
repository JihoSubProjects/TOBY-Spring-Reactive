package toby.live.examples;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class IterableExample {

    public static void main(String[] args) {

        System.out.println("\n>>>> Common");
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        for (Integer i : list) System.out.println(i);

        System.out.println("\n>>>> Iterable Class");
        Iterable<Integer> iter = Arrays.asList(1, 2, 3, 4, 5);
        for (Integer i : iter) System.out.println(i);

        System.out.println("\n>>>> Custom Iterable Class");
        // Anonymous Class
        Iterable<Integer> customIter = new Iterable<Integer>() {
            @Override
            public Iterator<Integer> iterator() {
                return null;
            }
        };

        // Lambda
        customIter = () -> new Iterator<Integer>() {

            int i = 0;
            final static int MAX = 10;

            @Override
            public boolean hasNext() {
                return i < MAX;
            }

            @Override
            public Integer next() {
                return ++i;
            }

        };

        for (Integer i : customIter) System.out.println(i);

        System.out.println("\n>>>> Before for-each");
        for (Iterator<Integer> i = customIter.iterator(); i.hasNext();)
            System.out.println(i.next());
        Iterator<Integer> i = customIter.iterator();
        while (i.hasNext()) System.out.println(i.next());

    }

}
