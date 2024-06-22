package allinterfaces;

import java.util.*;

public class MajorTest {
    public static void main(String[] args) {
        // Test 1: Default constructor
        FlexiList<Integer> integers1 = new FlexiList<>();
        integers1.add(1);
        integers1.add(2);
        integers1.add(3);
        System.out.println("expected [1, 2, 3]" +integers1); // [1, 2, 3]
        System.out.println(integers1.containsNonNumbers());

        // Test 2: Constructor with initial size
        FlexiList<Integer> integers2 = new FlexiList<>(5);
        integers2.add(4);
        integers2.add(5);
        integers2.add(6);
        System.out.println("expected [4, 5, 6]" +integers2); // [4, 5, 6]

        // Test 3: Constructor with allowDuplicates
        FlexiList<Integer> integers3 = new FlexiList<>(true);
        integers3.add(1);
        integers3.add(1);
        integers3.add(2);
        System.out.println("expected [1, 1, 2]" +integers3); // [1, 1, 2]

        // Test 4: Constructor with collection
        List<Integer> list = Arrays.asList(1, 2, 3);
        FlexiList<Integer> integers4 = new FlexiList<>(list);
        System.out.println("expected [1, 2, 3]" +integers4); // [1, 2, 3]

        // Test 5: add() method
        FlexiList<Integer> integers5 = new FlexiList<>();
        integers5.add(1);
        integers5.add(2);
        integers5.add(3);
        System.out.println("expected [1, 2, 3]" +integers5); // [1, 2, 3]

        // Test 6: addAll() method
        FlexiList<Integer> integers6 = new FlexiList<>();
        integers6.addAll(Arrays.asList(1, 2, 3));
        System.out.println("expected [1, 2, 3]" +integers6); // [1, 2, 3]

        // Test 7: get() method
        FlexiList<Integer> integers7 = new FlexiList<>(Arrays.asList(1, 2, 3));
        System.out.println("expected [1]" +integers7.getFirst()); // 1

        // Test 8: set() method
        FlexiList<Integer> integers8 = new FlexiList<>(Arrays.asList(1, 2, 3));
        integers8.set(0, 4);
        System.out.println("expected [4, 2, 3]" +integers8); // [4, 2, 3]

        // Test 9: remove() method
        FlexiList<Integer> integers9 = new FlexiList<>(Arrays.asList(1, 2, 3));
        integers9.removeFirst();
        System.out.println("expected [2, 3]" +integers9); // [2, 3]

        // Test 10: size() method
        FlexiList<Integer> integers10 = new FlexiList<>(Arrays.asList(1, 2, 3));
        System.out.println("expected [3]" +integers10.size()); // 3

        // Test 11: isEmpty() method
        FlexiList<Integer> integers11 = new FlexiList<>();
        System.out.println("expected true" +integers11.isEmpty()); // true

        // Test 12: contains() method
        FlexiList<Integer> integers12 = new FlexiList<>(Arrays.asList(1, 2, 3));
        System.out.println("expected true " +integers12.contains(2)); // true

        // Test 13: indexOf() method
        FlexiList<Integer> integers13 = new FlexiList<>(Arrays.asList(1, 2, 3));
        System.out.println("expected 1 "+integers13.indexOf(2)); // 1

        // Test 14: lastIndexOf() method
        FlexiList<Integer> integers14 = new FlexiList<>(Arrays.asList(1, 2, 3));
        System.out.println("expected [1]" +integers14.lastIndexOf(2)); // 1

        // Test 15: sort() method
        FlexiList<Integer> integers15 = new FlexiList<>(Arrays.asList(3, 2, 1));
        integers15.sort(null);
        System.out.println("expected [1,2,3]" +integers15); // [1, 2, 3]

        // Test 16: shuffle() method
        FlexiList<Integer> integers16 = new FlexiList<>(Arrays.asList(1, 2, 3));
        integers16.shuffle();
        System.out.println("expected [3,1,2]" +integers16); // [3, 1, 2] (or some other permutation)

        // Test 17: subList() method
        FlexiList<Integer> integers17 = new FlexiList<>(Arrays.asList(1, 2, 3, 4, 5));
        FlexiList<Integer> subList = integers17.subList(1, 3);
        System.out.println("expected [2, 3]" +subList); // [2, 3]

        // one test missing here

        // Test 19: toArray() method
        FlexiList<Integer> integers19 = new FlexiList<>(Arrays.asList(1, 2, 3));
        Integer[] array = integers19.toArray(new Integer[0]);
        System.out.println("expected [1, 2, 3]" +Arrays.toString(array)); // [1, 2, 3]

        // Test 20: toCSV() method
        FlexiList<Integer> integers20 = new FlexiList<>(Arrays.asList(1, 2, 3));
        String csv = integers20.toCSV();
        System.out.println("expected 1,2,3" +csv); // "1,2,3"

        // Test 21: toJSON() method
        FlexiList<Integer> integers21 = new FlexiList<>(Arrays.asList(1, 2, 3));
        String json = integers21.toJSON();
        System.out.println("expected [1, 2, 3]" +json); // "[1,2,3]"

        // Test 22: toMap() method
        FlexiList<Integer> integers22 = new FlexiList<>(Arrays.asList(1, 2, 3));
        integers22.toMap();
        System.out.println("expected [1=1, 2=2, 3=3]" +integers22); // {1=1, 2=2, 3=3}

        // Test 23: toQueue() method
        FlexiList<Integer> integers23 = new FlexiList<>(Arrays.asList(1, 2, 3));
        integers23.toQueue();
        System.out.println("expected [1,2,3] "+integers23);
//        System.out.println(queue); // [1, 2, 3]

        // Test 24: toSet() method
        FlexiList<Integer> integers24 = new FlexiList<>(Arrays.asList(1, 2, 2, 3));
        integers24.toSet();
        System.out.println("expected [1, 2, 3]" +integers24); // [1, 2, 3]

        // Test 25: toStack() method
        FlexiList<Integer> integers25 = new FlexiList<>(Arrays.asList(1, 2, 3));
        integers25.toStack();
        System.out.println("expected [1, 2, 3]" +integers25); // [1, 2, 3]

        // Test 26: transform() method
        FlexiList<Integer> integers26 = new FlexiList<>(Arrays.asList(1, 2, 3));
        FlexiList<Integer> transformed = integers26.transform(x -> x * 2);
        System.out.println("expected [2, 4, 6]" +transformed); // [2, 4, 6]

        // Test 27: trimToSize() method
        FlexiList<Integer> integers27 = new FlexiList<>(Arrays.asList(1, 2, 3));
        integers27.trimToSize();
        System.out.println("expected [3]" +integers27.size()); // 3

        // Test 28: visualize() method
        FlexiList<Integer> integers28 = new FlexiList<>(Arrays.asList(1, 2, 3));
        integers28.visualize();
        // Visual representation of the list
    }
}

