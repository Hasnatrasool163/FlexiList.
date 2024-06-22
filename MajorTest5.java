package allinterfaces;

import java.util.Arrays;
import java.util.List;

import static java.lang.Character.toUpperCase;
import static java.util.Arrays.asList;

public class MajorTest5 {

    public static void main(String[] args) {


// Test 1: Default constructor
        FlexiList<Character> characters1 = new FlexiList<>();
        characters1.add('a');
        characters1.add('b');
        characters1.add('c');
        System.out.println("expected [a, b, c]" + characters1); // [a, b, c]
        System.out.println(characters1.containsOnlyCharacters());

// Test 2: Constructor with initial size
        FlexiList<Character> characters2 = new FlexiList<>(5);
        characters2.add('d');
        characters2.add('e');
        characters2.add('f');
        System.out.println("expected [d, e, f]" + characters2); // [d, e, f]

// Test 3: Constructor with allowDuplicates
        FlexiList<Character> characters3 = new FlexiList<>(true);
        characters3.add('a');
        characters3.add('a');
        characters3.add('b');
        System.out.println("expected [a, a, b]" + characters3); // [a, a, b]

// Test 4: Constructor with collection
        List<Character> list = asList('a', 'b', 'c');
        FlexiList<Character> characters4 = new FlexiList<>(list);
        System.out.println("expected [a, b, c]" + characters4); // [a, b, c]

// Test 5: add() method
        FlexiList<Character> characters5 = new FlexiList<>();
        characters5.add('a');
        characters5.add('b');
        characters5.add('c');
        System.out.println("expected [a, b, c]" + characters5); // [a, b, c]

// Test 6: addAll() method
        FlexiList<Character> characters6 = new FlexiList<>();
        characters6.addAll(asList('a', 'b', 'c'));
        System.out.println("expected [a, b, c]" + characters6); // [a, b, c]

// Test 7: get() method
        FlexiList<Character> characters7 = new FlexiList<>('a', 'b', 'c');
        System.out.println("expected a" + characters7.getFirst()); // a

// Test 8: set() method
        FlexiList<Character> characters8 = new FlexiList<>(asList('a', 'b', 'c'));
        characters8.set(0, 'd');
        System.out.println("expected [d, b, c]" + characters8); // [d, b, c]

// Test 9: remove() method
        FlexiList<Character> characters9 = new FlexiList<>(asList('a', 'b', 'c'));
        characters9.removeFirst();
        System.out.println("expected [b, c]" + characters9); // [b, c]

// Test 10: size() method
        FlexiList<Character> characters10 = new FlexiList<>(asList('a', 'b', 'c'));
        System.out.println("expected 3" + characters10.size()); // 3

// Test 11: isEmpty() method
        FlexiList<Character> characters11 = new FlexiList<>();
        System.out.println("expected true" + characters11.isEmpty()); // true

// Test 12: contains() method
        FlexiList<Character> characters12 = new FlexiList<>(asList('a', 'b', 'c'));
        System.out.println("expected true" + characters12.contains('b')); // true

// Test 13: indexOf() method
        FlexiList<Character> characters13 = new FlexiList<>(asList('a', 'b', 'c'));
        System.out.println("expected 1" + characters13.indexOf('b')); // 1

// Test 14: lastIndexOf() method
        FlexiList<Character> characters14 = new FlexiList<>(asList('a', 'b', 'c'));
        System.out.println("expected 1" + characters14.lastIndexOf('b')); // 1

// Test 15: sort() method
        FlexiList<Character> characters15 = new FlexiList<>(asList('c', 'b', 'a'));
        characters15.sort(null);
        System.out.println("compare expected [a, b, c]" + characters15); // [a, b, c]

// Test 16: shuffle() method
        FlexiList<Character> characters16 = new FlexiList<>(asList('a', 'b', 'c'));
        characters16.shuffle();
        System.out.println("expected [c, a, b]" + characters16); // [c, a, b] (or some other permutation)

// Test 17: subList() method
        FlexiList<Character> characters17 = new FlexiList<>(asList('a', 'b', 'c', 'd', 'e'));
        FlexiList<Character> subList = characters17.subList(1, 3);
        System.out.println("expected [b, c]" + subList); // [b, c]

// Test 19: toArray() method
        FlexiList<Character> characters19 = new FlexiList<>(asList('a', 'b', 'c'));
        Character[] array = characters19.toArray(new Character[0]);
        System.out.println("expected [a, b, c]" + Arrays.toString(array)); // [a, b, c]

// Test 20: toCSV() method
        FlexiList<Character> characters20 = new FlexiList<>(asList('a', 'b', 'c'));
        String csv = characters20.toCSV();
        System.out.println("expected a,b,c" + csv); // 'a,b,c'

// Test 21: toJSON() method
        FlexiList<Character> characters21 = new FlexiList<>(asList('a', 'b', 'c'));
        String json = characters21.toJSON();
        System.out.println("expected [a, b, c]" + json); // '[a,b,c]"

// Test 22: toMap() method
        FlexiList<Character> characters22 = new FlexiList<>(asList('a', 'b', 'c'));
        characters22.toMap();
        System.out.println("expected {a=a, b=b, c=c}" + characters22); // {a=a, b=b, c=c}

// Test 23: toQueue() method
        FlexiList<Character> characters23 = new FlexiList<>(asList('a', 'b', 'c'));
        characters23.toQueue();
        System.out.println("expected [a, b, c]" + characters23); // [a, b, c]

// Test 24: toSet() method
        FlexiList<Character> characters24 = new FlexiList<>(asList('a', 'b', 'b', 'c'));
        characters24.toSet();
        System.out.println("expected [a, b, c]" + characters24); // [a, b, c]

// Test 25: toStack() method
        FlexiList<Character> characters25 = new FlexiList<>(asList('a', 'b', 'c'));
        characters25.toStack();
        System.out.println("expected [a, b, c]" + characters25); // [a, b, c]

// Test 26: transform() method
        FlexiList<Character> characters26 = new FlexiList<>(asList('a', 'b', 'c'));
        FlexiList<Character> transformed = characters26.transform(Character::toUpperCase);
        System.out.println("expected [A, B, C]" + transformed); // [A, B, C]

// Test 27: trimToSize() method
        FlexiList<Character> characters27 = new FlexiList<>(asList('a', 'b', 'c'));
        characters27.trimToSize();
        System.out.println("expected 3" + characters27.size()); // 3

// Test 28: visualize() method
        FlexiList<Character> characters28 = new FlexiList<>(asList('a', 'b', 'c'));
        characters28.visualize();
// Visual representation of the list

    }
}

