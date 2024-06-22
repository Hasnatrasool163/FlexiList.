package allinterfaces;

import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;

public class MajorTest2 {

    public static void main(String[] args) {

        // Test 1: Default constructor
        FlexiList<String> strings1 = new FlexiList<>();
        strings1.add("a");
        strings1.add("b");
        strings1.add("c");
        System.out.println("expected [a, b, c]" + strings1); // [a, b, c]
        System.out.println(strings1.containsOnlyStrings());

// Test 2: Constructor with initial size
        FlexiList<String> strings2 = new FlexiList<>(5);
        strings2.add("d");
        strings2.add("e");
        strings2.add("f");
        System.out.println("expected [d, e, f]" + strings2); // [d, e, f]

// Test 3: Constructor with allowDuplicates
        FlexiList<String> strings3 = new FlexiList<>(true);
        strings3.add("a");
        strings3.add("a");
        strings3.add("b");
        System.out.println("expected [a, a, b]" + strings3); // [a, a, b]

// Test 4: Constructor with collection
        List<String> list = asList("a", "b", "c");
        FlexiList<String> strings4 = new FlexiList<>(list);
        System.out.println("expected [a, b, c]" + strings4); // [a, b, c]

// Test 5: add() method
        FlexiList<String> strings5 = new FlexiList<>();
        strings5.add("a");
        strings5.add("b");
        strings5.add("c");
        System.out.println("expected [a, b, c]" + strings5); // [a, b, c]

// Test 6: addAll() method
        FlexiList<String> strings6 = new FlexiList<>();
        strings6.addAll(asList("a", "b", "c"));
        System.out.println("expected [a, b, c]" + strings6); // [a, b, c]

// Test 7: get() method
        FlexiList<String> strings7 = new FlexiList<>("a", "b", "c");
        System.out.println("expected a" + strings7.getFirst()); // a

// Test 8: set() method
        FlexiList<String> strings8 = new FlexiList<>(asList("a", "b", "c"));
        strings8.set(0, "d");
        System.out.println("expected [d, b, c]" + strings8); // [d, b, c]

// Test 9: remove() method
        FlexiList<String> strings9 = new FlexiList<>(asList("a", "b", "c"));
        strings9.removeFirst();
        System.out.println("expected [b, c]" + strings9); // [b, c]

// Test 10: size() method
        FlexiList<String> strings10 = new FlexiList<>(asList("a", "b", "c"));
        System.out.println("expected 3" + strings10.size()); // 3

// Test 11: isEmpty() method
        FlexiList<String> strings11 = new FlexiList<>();
        System.out.println("expected true" + strings11.isEmpty()); // true

// Test 12: contains() method
        FlexiList<String> strings12 = new FlexiList<>(asList("a", "b", "c"));
        System.out.println("expected true" + strings12.contains("b")); // true

// Test 13: indexOf() method
        FlexiList<String> strings13 = new FlexiList<>(asList("a", "b", "c"));
        System.out.println("expected 1" + strings13.indexOf("b")); // 1

// Test 14: lastIndexOf() method
        FlexiList<String> strings14 = new FlexiList<>(asList("a", "b", "c"));
        System.out.println("expected 1" + strings14.lastIndexOf("b")); // 1

// Test 15: sort() method
        FlexiList<String> strings15 = new FlexiList<>(asList("c", "b", "a"));
        strings15.sort(null);
        System.out.println("compare expected [a, b, c]" + strings15); // [a, b, c]

// Test 16: shuffle() method
        FlexiList<String> strings16 = new FlexiList<>(asList("a", "b", "c"));
        strings16.shuffle();
        System.out.println("expected [c, a, b]" + strings16); // [c, a, b] (or some other permutation)

// Test 17: subList() method
        FlexiList<String> strings17 = new FlexiList<>(asList("a", "b", "c", "d", "e"));
        FlexiList<String> subList = strings17.subList(1, 3);
        System.out.println("expected [b, c]" + subList); // [b, c]

// Test 19: toArray() method
        FlexiList<String> strings19 = new FlexiList<>(asList("a", "b", "c"));
        String[] array = strings19.toArray(new String[0]);
        System.out.println("expected [a, b, c]" + Arrays.toString(array)); // [a, b, c]

// Test 20: toCSV() method
        FlexiList<String> strings20 = new FlexiList<>(asList("a", "b", "c"));
        String csv = strings20.toCSV();
        System.out.println("expected a,b,c" + csv); // "a,b,c"

// Test 21: toJSON() method
        FlexiList<String> strings21 = new FlexiList<>(asList("a", "b", "c"));
        String json = strings21.toJSON();
        System.out.println("expected [a, b, c]" + json); // "[a,b,c]"

// Test 22: toMap() method
        FlexiList<String> strings22 = new FlexiList<>(asList("a", "b", "c"));
        strings22.toMap();
        System.out.println("expected {a=a, b=b, c=c}" + strings22); // {a=a, b=b, c=c}

// Test 23: toQueue() method
        FlexiList<String> strings23 = new FlexiList<>(asList("a", "b", "c"));
        strings23.toQueue();
        System.out.println("expected [a, b, c]" + strings23); // [a, b, c]

// Test 24: toSet() method
        FlexiList<String> strings24 = new FlexiList<>(asList("a", "b", "b", "c"));
        strings24.toSet();
        System.out.println("expected [a, b, c]" + strings24); // [a, b, c]

// Test 25: toStack() method
        FlexiList<String> strings25 = new FlexiList<>(asList("a", "b", "c"));
        strings25.toStack();
        System.out.println("expected [a, b, c]" + strings25); // [a, b, c]

// Test 26: transform() method
        FlexiList<String> strings26 = new FlexiList<>(asList("a", "b", "c"));
        FlexiList<String> transformed = strings26.transform(String::toUpperCase);
        System.out.println("expected [A, B, C]" + transformed); // [A, B, C]

// Test 27: trimToSize() method
        FlexiList<String> strings27 = new FlexiList<>(asList("a", "b", "c"));
        strings27.trimToSize();
        System.out.println("expected 3" + strings27.size()); // 3

// Test 28: visualize() method
        FlexiList<String> strings28 = new FlexiList<>(asList("a", "b", "c"));
        strings28.visualize();
// Visual representation of the list


    }
}
