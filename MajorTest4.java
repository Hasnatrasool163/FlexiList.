package allinterfaces;

import java.util.Arrays;
import java.util.List;

public class MajorTest4 {

    public static void main(String[] args) {

        // Test 1: Default constructor
        FlexiList<Double> doubles1 = new FlexiList<>();
        doubles1.add(1.1);
        doubles1.add(2.2);
        doubles1.add(3.3);
        System.out.println("expected [1.1, 2.2, 3.3]" + doubles1); // [1.1, 2.2, 3.3]

// Test 2: Constructor with initial size
        FlexiList<Double> doubles2 = new FlexiList<>(5);
        doubles2.add(4.4);
        doubles2.add(5.5);
        doubles2.add(6.6);
        System.out.println("expected [4.4, 5.5, 6.6]" + doubles2); // [4.4, 5.5, 6.6]

// Test 3: Constructor with allowDuplicates
        FlexiList<Double> doubles3 = new FlexiList<>(true);
        doubles3.add(1.1);
        doubles3.add(1.1);
        doubles3.add(2.2);
        System.out.println("expected [1.1, 1.1, 2.2]" + doubles3); // [1.1, 1.1, 2.2]

// Test 4: Constructor with collection
        List<Double> list = Arrays.asList(1.1, 2.2, 3.3);
        FlexiList<Double> doubles4 = new FlexiList<>(list);
        System.out.println("expected [1.1, 2.2, 3.3]" + doubles4); // [1.1, 2.2, 3.3]

// Test 5: add() method
        FlexiList<Double> doubles5 = new FlexiList<>();
        doubles5.add(1.1);
        doubles5.add(2.2);
        doubles5.add(3.3);
        System.out.println("expected [1.1, 2.2, 3.3]" + doubles5); // [1.1, 2.2, 3.3]

// Test 6: addAll() method
        FlexiList<Double> doubles6 = new FlexiList<>();
        doubles6.addAll(Arrays.asList(1.1, 2.2, 3.3));
        System.out.println("expected [1.1, 2.2, 3.3]" + doubles6); // [1.1, 2.2, 3.3]

// Test 7: get() method
        FlexiList<Double> doubles7 = new FlexiList<>(Arrays.asList(1.1, 2.2, 3.3));
        System.out.println("expected 1.1" + doubles7.getFirst()); // 1.1

// Test 8: set() method
        FlexiList<Double> doubles8 = new FlexiList<>(Arrays.asList(1.1, 2.2, 3.3));
        doubles8.set(0, 4.4);
        System.out.println("expected [4.4, 2.2, 3.3]" + doubles8); // [4.4, 2.2, 3.3]

// Test 9: remove() method
        FlexiList<Double> doubles9 = new FlexiList<>(Arrays.asList(1.1, 2.2, 3.3));
        doubles9.removeFirst();
        System.out.println("expected [2.2, 3.3]" + doubles9); // [2.2, 3.3]

// Test 10: size() method
        FlexiList<Double> doubles10 = new FlexiList<>(Arrays.asList(1.1, 2.2, 3.3));
        System.out.println("expected 3" + doubles10.size()); // 3

// Test 11: isEmpty() method
        FlexiList<Double> doubles11 = new FlexiList<>();
        System.out.println("expected true" + doubles11.isEmpty()); // true

// Test 12: contains() method
        FlexiList<Double> doubles12 = new FlexiList<>(Arrays.asList(1.1, 2.2, 3.3));
        System.out.println("expected true" + doubles12.contains(2.2)); // true

// Test 13: indexOf() method
        FlexiList<Double> doubles13 = new FlexiList<>(Arrays.asList(1.1, 2.2, 3.3));
        System.out.println("expected 1" + doubles13.indexOf(2.2)); // 1

// Test 14: lastIndexOf() method
        FlexiList<Double> doubles14 = new FlexiList<>(Arrays.asList(1.1, 2.2, 3.3));
        System.out.println("expected 1" + doubles14.lastIndexOf(2.2)); // 1

// Test 15: sort() method
        FlexiList<Double> doubles15 = new FlexiList<>(Arrays.asList(3.3, 2.2, 1.1));
        doubles15.sort(null);
        System.out.println("expected [1.1, 2.2, 3.3]" + doubles15); // [1.1, 2.2, 3.3]

// Test 16: shuffle() method
        FlexiList<Double> doubles16 = new FlexiList<>(Arrays.asList(1.1, 2.2, 3.3));
        doubles16.shuffle();
        System.out.println("expected [3.3, 1.1, 2.2]" + doubles16); // [3.3, 1.1, 2.2] (or some other permutation)

// Test 17: subList() method
        FlexiList<Double> doubles17 = new FlexiList<>(Arrays.asList(1.1, 2.2, 3.3, 4.4, 5.5));
        FlexiList<Double> subList = doubles17.subList(1, 3);
        System.out.println("expected [2.2, 3.3]" + subList); // [2.2, 3.3]

// Test 18: toArray() method
        FlexiList<Double> doubles18 = new FlexiList<>(Arrays.asList(1.1, 2.2, 3.3));
        Double[] array = doubles18.toArray(new Double[0]);
        System.out.println("expected [1.1, 2.2, 3.3]" + Arrays.toString(array)); // [1.1, 2.2, 3.3]

// Test 19: toCSV() method
        FlexiList<Double> doubles19 = new FlexiList<>(Arrays.asList(1.1, 2.2, 3.3));
        String csv = doubles19.toCSV();
        System.out.println("expected 1.1,2.2,3.3" + csv); // "1.1,2.2,3.3"

// Test 20: toJSON() method
        FlexiList<Double> doubles20 = new FlexiList<>(Arrays.asList(1.1, 2.2, 3.3));
        String json = doubles20.toJSON();
        System.out.println("expected [1.1, 2.2, 3.3]" + json); // "[1.1,2.2,3.3]"

// Test 21: toMap() method
        FlexiList<Double> doubles21 = new FlexiList<>(Arrays.asList(1.1, 2.2, 3.3));
        doubles21.toMap();
        System.out.println("expected {1.1=1.1, 2.2=2.2, 3.3=3.3}" + doubles21); // {1.1=1.1, 2.2=2.2, 3.3=3.3}

// Test 22: toQueue() method
        FlexiList<Double> doubles22 = new FlexiList<>(Arrays.asList(1.1, 2.2, 3.3));
        doubles22.toQueue();
        System.out.println("expected [1.1, 2.2, 3.3]" + doubles22); // [1.1, 2.2, 3.3]

// Test 23: toSet() method
        FlexiList<Double> doubles23 = new FlexiList<>(Arrays.asList(1.1, 2.2, 2.2, 3.3));
        doubles23.toStack();
        System.out.println("expected [1.1, 2.2, 3.3]" + doubles23); // [1.1, 2.2, 3.3]

// Test 24: toStack() method
        FlexiList<Double> doubles24 = new FlexiList<>(Arrays.asList(1.1, 2.2, 3.3));
        doubles24.toStack();
        System.out.println("expected [1.1, 2.2, 3.3]" + doubles24); // [1.1, 2.2, 3

// Test 25: transform() method
        FlexiList<Double> doubles25 = new FlexiList<>(Arrays.asList(1.1, 2.2, 3.3));
        FlexiList<Double> transformed = doubles25.transform(x -> x * 2);
        System.out.println("expected [2.2, 4.4, 6.6]" + transformed); // [2.2, 4.4, 6.6]

// Test 26: trimToSize() method
        FlexiList<Double> doubles26 = new FlexiList<>(Arrays.asList(1.1, 2.2, 3.3));
        doubles26.trimToSize();
        System.out.println("expected 3" + doubles26.size()); // 3

// Test 27: visualize() method
        FlexiList<Double> doubles27 = new FlexiList<>(Arrays.asList(1.1, 2.2, 3.3));
        doubles27.visualize();
// Visual representation of the list

// Test 28: toArray() method with specified array
        FlexiList<Double> doubles28 = new FlexiList<>(Arrays.asList(1.1, 2.2, 3.3));
        Double[] array1 = new Double[5];
        Double[] result = doubles28.toArray(array1);
        System.out.println("expected [1.1, 2.2, 3.3]" + Arrays.toString(result)); // [1.1, 2.2, 3.3]
        System.out.println(array1.length);
        System.out.println(array1[4]); // 3 , 4 null
    }

    }

