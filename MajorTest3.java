package allinterfaces;

import java.util.Arrays;
import java.util.List;

public class MajorTest3 {

    public static void main(String[] args) {


// Test 1: Default constructor
        FlexiList<Float> floats1 = new FlexiList<>();
        floats1.add(1.1f);
        floats1.add(2.2f);
        floats1.add(3.3f);
        System.out.println("expected [1.1, 2.2, 3.3]" + floats1); // [1.1, 2.2, 3.3]

// Test 2: Constructor with initial size
        FlexiList<Float> floats2 = new FlexiList<>(5);
        floats2.add(4.4f);
        floats2.add(5.5f);
        floats2.add(6.6f);
        System.out.println("expected [4.4, 5.5, 6.6]" + floats2); // [4.4, 5.5, 6.6]

// Test 3: Constructor with allowDuplicates
        FlexiList<Float> floats3 = new FlexiList<>(true);
        floats3.add(1.1f);
        floats3.add(1.1f);
        floats3.add(2.2f);
        System.out.println("expected [1.1, 1.1, 2.2]" + floats3); // [1.1, 1.1, 2.2]

// Test 4: Constructor with collection
        List<Float> list = Arrays.asList(1.1f, 2.2f, 3.3f);
        FlexiList<Float> floats4 = new FlexiList<>(list);
        System.out.println("expected [1.1, 2.2, 3.3]" + floats4); // [1.1, 2.2, 3.3]

// Test 5: add() method
        FlexiList<Float> floats5 = new FlexiList<>();
        floats5.add(1.1f);
        floats5.add(2.2f);
        floats5.add(3.3f);
        System.out.println("expected [1.1, 2.2, 3.3]" + floats5); // [1.1, 2.2, 3.3]

// Test 6: addAll() method
        FlexiList<Float> floats6 = new FlexiList<>();
        floats6.addAll(Arrays.asList(1.1f, 2.2f, 3.3f));
        System.out.println("expected [1.1, 2.2, 3.3]" + floats6); // [1.1, 2.2, 3.3]

// Test 7: get() method
        FlexiList<Float> floats7 = new FlexiList<>(Arrays.asList(1.1f, 2.2f, 3.3f));
        System.out.println("expected 1.1" + floats7.get(0)); // 1.1

// Test 8: set() method
        FlexiList<Float> floats8 = new FlexiList<>(Arrays.asList(1.1f, 2.2f, 3.3f));
        floats8.set(0, 4.4f);
        System.out.println("expected [4.4, 2.2, 3.3]" + floats8); // [4.4, 2.2, 3.3]

// Test 9: remove() method
        FlexiList<Float> floats9 = new FlexiList<>(Arrays.asList(1.1f, 2.2f, 3.3f));
        floats9.remove(0);
        System.out.println("expected [2.2, 3.3]" + floats9); // [2.2, 3.3]

// Test 10: size() method
        FlexiList<Float> floats10 = new FlexiList<>(Arrays.asList(1.1f, 2.2f, 3.3f));
        System.out.println("expected 3" + floats10.size()); // 3

// Test 11: isEmpty() method
        FlexiList<Float> floats11 = new FlexiList<>();
        System.out.println("expected true" + floats11.isEmpty()); // true

// Test 12: contains() method
        FlexiList<Float> floats12 = new FlexiList<>(Arrays.asList(1.1f, 2.2f, 3.3f));
        System.out.println("expected true" + floats12.contains(2.2f)); // true

// Test 13: indexOf() method
        FlexiList<Float> floats13 = new FlexiList<>(Arrays.asList(1.1f, 2.2f, 3.3f));
        System.out.println("expected 1" + floats13.indexOf(2.2f)); // 1

// Test 14: lastIndexOf() method
        FlexiList<Float> floats14 = new FlexiList<>(Arrays.asList(1.1f, 2.2f, 3.3f));
        System.out.println("expected 1" + floats14.lastIndexOf(2.2f)); // 1

// Test 15: sort() method
        FlexiList<Float> floats15 = new FlexiList<>(Arrays.asList(3.3f, 2.2f, 1.1f));
        floats15.sort(null);
        System.out.println("expected [1.1, 2.2, 3.3]" + floats15); // [1.1, 2.2, 3.3]

// Test 16: shuffle() method
        FlexiList<Float> floats16 = new FlexiList<>(Arrays.asList(1.1f, 2.2f, 3.3f));
        floats16.shuffle();
        System.out.println("expected [3.3, 1.1, 2.2]" + floats16); // [3.3, 1.1, 2.2] (or some other permutation)

// Test 17: subList() method
        FlexiList<Float> floats17 = new FlexiList<>(Arrays.asList(1.1f, 2.2f, 3.3f, 4.4f, 5.5f));
        FlexiList<Float> subList = floats17.subList(1, 3);
        System.out.println("expected [2.2, 3.3]" + subList); // [2.2, 3.3]

// Test 18: toArray() method
        FlexiList<Float> floats18 = new FlexiList<>(Arrays.asList(1.1f, 2.2f, 3.3f));
        Float[] array = floats18.toArray(new Float[0]);
        System.out.println("expected [1.1, 2.2, 3.3]" + Arrays.toString(array)); // [1.1, 2.2, 3.3]

// Test 19: toCSV() method
        FlexiList<Float> floats19 = new FlexiList<>(Arrays.asList(1.1f, 2.2f, 3.3f));
        String csv = floats19.toCSV();
        System.out.println("expected 1.1,2.2,3.3" + csv); // "1.1,2.2,3.3"

// Test 20: toJSON() method
        FlexiList<Float> floats20 = new FlexiList<>(Arrays.asList(1.1f, 2.2f, 3.3f));
        String json = floats20.toJSON();
        System.out.println("expected [1.1, 2.2, 3.3]" + json); // "[1.1,2.2,3.3]"

// Test 21: toMap() method
        FlexiList<Float> floats21 = new FlexiList<>(Arrays.asList(1.1f, 2.2f, 3.3f));
        floats21.toMap();
        System.out.println("expected {1.1=1.1, 2.2=2.2, 3.3=3.3}" + floats21); // {1.1=1.1, 2.2=2.2, 3.3=3.3}

// Test 22: toQueue() method
        FlexiList<Float> floats22 = new FlexiList<>(Arrays.asList(1.1f, 2.2f, 3.3f));
        floats22.toQueue();
        System.out.println("expected [1.1, 2.2, 3.3]" + floats22); // [1.1, 2.2, 3.3]

// Test 23: toSet() method
        FlexiList<Float> floats23 = new FlexiList<>(Arrays.asList(1.1f, 2.2f, 2.2f, 3.3f));
        floats23.toStack();
        System.out.println("expected [1.1, 2.2, 3.3]" + floats23); // [1.1, 2.2, 3.3]

// Test 24: toStack() method
        FlexiList<Float> floats24 = new FlexiList<>(Arrays.asList(1.1f, 2.2f, 3.3f));
        floats24.toStack();
        System.out.println("expected [1.1, 2.2, 3.3]" + floats24); // [1.1, 2.2, 3

// Test 25: transform() method
        FlexiList<Float> floats25 = new FlexiList<>(Arrays.asList(1.1f, 2.2f, 3.3f));
        FlexiList<Float> transformed = floats25.transform(x -> x * 2);
        System.out.println("expected [2.2, 4.4, 6.6]" + transformed); // [2.2, 4.4, 6.6]

// Test 26: trimToSize() method
        FlexiList<Float> floats26 = new FlexiList<>(Arrays.asList(1.1f, 2.2f, 3.3f));
        floats26.trimToSize();
        System.out.println("expected 3" + floats26.size()); // 3

// Test 27: visualize() method
        FlexiList<Float> floats27 = new FlexiList<>(Arrays.asList(1.1f, 2.2f, 3.3f));
        floats27.visualize();
// Visual representation of the list

// Test 28: toArray() method with specified array
        FlexiList<Float> floats28 = new FlexiList<>(Arrays.asList(1.1f, 2.2f, 3.3f));
        Float[] array1 = new Float[5];
        Float[] result = floats28.toArray(array1);
        System.out.println("expected [1.1, 2.2, 3.3]" + Arrays.toString(result)); // [1.1, 2.2, 3.3]
        System.out.println(array1.length);
        System.out.println(array1[4]);// null
    }
}
