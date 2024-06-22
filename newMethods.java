package allinterfaces;

import java.util.*;

public class newMethods {

    public static void main(String[] args) {


            FlexiList<Integer> list = new FlexiList<>();
            list.add(1);
            list.add(2);
            list.add(3);
            list.add(4);
            list.add(5);

            System.out.println("Original list: " + list);

            list.shuffle(); // could use Collections.shuffle()
//            Collections.shuffle(list);
            System.out.println("Shuffled list: " + list);

            list.rotate(2);
            System.out.println("Rotated list: " + list);

            list.removeNulls();
            System.out.println("List after removing nulls: " + list);

            list.removeEmptyStrings();
            System.out.println("List after removing empty strings: " + list);

            list.forEachWithIndex((element, index) -> System.out.println("Element at index " + index + ": " + element));

            list.replaceAllOfSame(5,6);
            System.out.println(list);
            System.out.println(list.getFirst());

            // Test toStack()
            Stack<Integer> stack = list.toStack();
            System.out.println("Stack: " + stack);

            // Test toQueue()
            Queue<Integer> queue = list.toQueue();
            System.out.println("Queue: " + queue);

            // Test frequency()
            int frequency = list.frequency(3);
            System.out.println("Frequency of 3: " + frequency);

            // Test allMatch()
            boolean allMatch = list.allMatch(x -> x % 2 == 0);
            System.out.println("All match (even numbers): " + allMatch);

            // Test anyMatch()
            boolean anyMatch = list.anyMatch(x -> x == 3);
            System.out.println("Any match (number 3): " + anyMatch);

            // Test noneMatch()
            boolean noneMatch = list.noneMatch(x -> x == 6);
            System.out.println("None match (number 6): " + noneMatch);


            list.sortAscending();
            System.out.println(list); // [1, ,2,3,4,6]

            list.sortDescending();
            System.out.println(list); // [6,4,3,2,1]

            FlexiList<Integer> filteredList = list.filter(x -> x % 2 == 0);
            System.out.println(filteredList); // [6, 4,2]

            Map<Integer, List<Integer>> groupedList = list.groupBy(x -> x % 2);
            System.out.println(groupedList); // {0=[6,4 2], 1=[ 3,1]}

            FlexiList<Integer> transformedList = list.transform(x -> x * 2);
            System.out.println(transformedList); // [12, 8, 6, 4, 2]

            Map<Integer, Integer> histogram = list.generateHistogram();
            System.out.println(histogram); // {1=1, 2=1, 3=1,4=1,6=1}

            List<Integer> distinctElements = list.distinct();
            System.out.println("Distinct elements: " + distinctElements);

            // Test elementBefore()
            Integer elementBefore = list.elementBefore(4);
            System.out.println("Element before 4: " + elementBefore);

            // Test elementAfter()
            Integer elementAfter = list.elementAfter(4);
            System.out.println("Element after 4: " + elementAfter);

            FlexiList<String> list1 = new FlexiList<>();
            list1.add("Hello");
            list1.add("World");

            FlexiList<String> list2 = new FlexiList<>();
            list2.add("!");
            list2.add("How");
            list2.add("are");
            list2.add("you?");

            FlexiList<String> concatList = list1.concat(list2);
            System.out.println(concatList); // [Hello, World, ?, How, are, you?]

            List<String> splitList = concatList.split("?");
            System.out.println(splitList); // [Hello World, How are you?]

            FlexiList<String> list5 = new FlexiList<>();
            list5.add("A");
            list5.add("B");
            list5.add("C");
            list5.add("D");
            list5.add("E");

            List<String> result = list5.depthFirstSearch("B");
            System.out.println(result); // [B, C, D, E, A]

            FlexiList<Integer> list4 = new FlexiList<>();

            list4.add(5);
            list4.add(2);
            list4.add(8);
            list4.add(1);
            list4.add(9);

            System.out.println("Original list: " + list4);

            list4.bubbleSort();

            System.out.println("sorted list: " + list4);


    }
}
