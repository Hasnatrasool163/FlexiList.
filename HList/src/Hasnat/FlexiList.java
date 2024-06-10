/*
 * Copyright (c) 2024,MuhammadHasnatRasool . All rights reserved.
 * CodingWorldHere PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */


package Hasnat;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.io.Serializable;
import java.util.*;

/**
 * {@code FlexiList} is a data structure that combines the benefits of arrays and linked lists.
 * Like an array, it allows for efficient access to elements by index. Like a linked list,
 * it allows for efficient insertion and deletion of elements at any position in the list.
 * A list implementation that provides efficient add, remove, and sorting operations.
 * The {@code key feature of a FlexiList} is its use of a doubly-linked list,
 * where each node points to both the previous and next nodes in the list.
 * This allows for efficient navigation in both directions, making it easy to insert and delete nodes at any position.
 * {@code Compared to arrays}, FlexiList has several advantages:
 * - {@code Efficient insertion and deletion}: FlexiList can insert or delete nodes at any position in the list in O(1) time, whereas arrays require shifting all elements after the insertion or deletion point.
 * - {@code Dynamic size}: FlexiList can grow or shrink dynamically as elements are added or removed, whereas arrays have a fixed size.
 * - {@code Good memory locality}: FlexiList nodes are stored in a contiguous block of memory, making it more cache-friendly than arrays.
 * {@code Compared to ArrayList}, FlexiList has several advantages:
 * - {@code Faster insertion and deletion}: FlexiList can insert or delete nodes at any position in the list in O(1) time, whereas ArrayList requires shifting all elements after the insertion or deletion point.
 * - {@code Better memory efficiency}: FlexiList uses less memory than ArrayList because it doesn't need to store a separate array of indices.
 *
 * @param <T> the type of elements in the list
 * @author MuhammadHasnatRasool
 * @version 0.1
 * @see java.io.Serializable
 * @see java.lang.Cloneable
 * @see java.util.Arrays
 * @see java.util.ArrayList
 * @see java.util.ListIterator
 */
public class FlexiList<T extends Comparable<T>> implements Serializable, Cloneable, Iterable<T>, RandomAccess {

    /**
     * The head of the list.
     */
    private Node<T> head;
    /**
     * The tail of the list.
     */
    private Node<T> tail;
    /**
     * The last node in the list.
     */
    private Node<T> lastNode;
    /**
     * The size of the list.
     */
    private int size;
    /**
     * The free node pool.
     */
    private Node<T> freeNode;
    /**
     * The number of free nodes.
     */
    private int freeCount;
    /**
     * The index array.
     */
    private Object[] indexArray;
    /**
     * The initial size of the index array.
     */
    private int indexArraySize = 1000;

    /**
     * Constructs a new FlexiList instance with provided initial size
     *
     * @param initialSize set the initial size of the list
     */
    public FlexiList(int initialSize) {
        if (initialSize > 0) {
            indexArraySize = initialSize;
        } else {
            throw new IllegalArgumentException("Initial size must be greater than 0");
        }
        head = null;
        tail = null;
        lastNode = null;
        size = 0;
        freeNode = null;
        freeCount = 0;
        indexArray = new Object[indexArraySize];
    }


    /**
     * Constructs a new FlexiList instance.
     */
    public FlexiList() {
        head = null;
        tail = null;
        lastNode = null;
        size = 0;
        freeNode = null;
        freeCount = 0;
        indexArray = new Object[indexArraySize];
    }

    /**
     * Adds an element to the list.
     *
     * @param element the element to add
     */
    public void add(T element) {
        Node<T> node;
        if (freeNode == null) {
            node = new Node<>(element);
        } else {
            node = freeNode;
            freeNode = freeNode.next;
            freeCount--;
            node.data = element;
        }
        if (head == null) {
            head = node;
        } else {
            tail.next = node;
            node.prev = tail;
        }
        tail = node;
        lastNode = node;
        size++;
        updateIndexArray(size - 1, node);
    }


    /**
     * Reverses the order of the elements in the list.
     */
    public void reverseList() {

        // working of the method
//        1. Node<T> curr = head; -> sets the curr variable to the first node in the list (the head node).
//        2. Node<T> prev = null; -> sets the prev variable to null, which will be used to keep track of the previous node in the list.
//        3. while (curr != null) { -> starts a loop that will continue until the end of the list is reached (i.e., until curr is null).
//        4. Node<T> next = curr.next; -> sets the next variable to the next node in the list (the node that comes after the current node).
//        5. curr.next = prev; -> sets the next pointer of the current node to the previous node. This effectively reverses the link between the current node and the next node.
//        6. prev = curr; -> sets the prev variable to the current node. This keeps track of the previous node in the list.
//        7. curr = next; -> sets the curr variable to the next node in the list. This moves the loop to the next node.
//        8. head = prev; -> sets the head variable to the last node in the list (the node that was previously the first node). This effectively reverses the list.
//        9. tail = curr; -> sets the tail variable to the first node in the list (the node that was previously the last node).

        // example
        // Integer list    [1, 9, 5, 6, 8]
        // required result [8, 6, 5, 9, 1]

        // steps

//        - set current (curr) to first node -> 1 (first value in the list)
//                - set prev -> null
//                - loop start -> while (curr) move till end and reach null in the list
//                - Iteration 1:
//        - set curr.next -> 9 (next value in the list)
//        - set prev -> 1 (value of curr)
//        - set curr -> next -> 9
//                - Iteration 2:
//        - set curr.next -> 5 (next value in the list)
//        - set prev -> 9 (value of curr)
//        - set curr -> next -> 5
//                - Iteration 3:
//        - set curr.next -> 6 (next value in the list)
//        - set prev -> 5 (value of curr)
//        - set curr -> next -> 6
//                - Iteration 4:
//        - set curr.next -> 8 (next value in the list)
//        - set prev -> 6 (value of curr)
//        - set curr -> next -> 8
//                - Iteration 5:
//        - set curr.next -> null (end of list)
//        - set prev -> 8 (value of curr)
//        - set curr -> null (end of list)
//        - loop ends
//                - head -> prev -> 8
//                - tail -> curr -> null
//
//        Result: [8, 6, 5, 9, 1]

        // final note
        // each iteration, the prev variable is set to the current node,
        // and the curr variable is set to the next node in the list

        // daily life examples to further explain

        // 1. Reversing a line of people: Imagine a line of people waiting for a concert.
        // If you want to reverse the order of the line, you would need to move each person
        // to the opposite end of the line, one by one. This is similar to how the reverseList() method works,
        // where each node is moved to the opposite end of the list.

        //2. Reversing a stack of plates: Imagine a stack of plates in a kitchen. If you want to reverse the order
        // of the plates, you would need to take each plate off the stack and put it on the bottom of the stack.
        // This is similar to how the reverseList() method works, where each node is moved to the opposite end of the list.

        //3. Reversing a sentence: Imagine a sentence written on a piece of paper.
        // If you want to reverse the order of the words in the sentence,
        // you would need to move each word to the opposite end of the sentence.
        // This is similar to how the reverseList() method works, where each node is moved to the opposite end of the list.


        Node<T> curr = head;
        Node<T> prev = null;
//        System.out.println("value of head 1st element: "+head.data + " value of previous: "+prev);
        while (curr != null) {
            Node<T> next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
//            if(curr !=null){
//            System.out.println( "prev value: "+prev.data + "curr new value: "+ curr.data);

//                }
        }
//        System.out.println(list);
//        System.out.println("Current value of head: "+head.data +"= prev: "+ prev.data+"current value of tail: "+tail.data );
        head = prev;   // most imp line that moves last(element) to 1st and thus all address updated and linked list will be reversed.
//         System.out.println(list);
        tail = curr;
    }

    /**
     * Inserts the specified element at the specified index in the list.
     *
     * @param index   the index at which the element is to be inserted
     * @param element the element to be inserted
     */

    public void add(int index, T element) {
        Node<T> node = new Node<>(element);
        if (index == 0) {
            if (head == null) {
                head = node;
                tail = node;
            } else {
                node.next = head;
                head.prev = node;
                head = node;
            }
        } else {
            Node<T> curr = (Node<T>) indexArray[index - 1];
            node.next = curr.next;
            if (curr.next != null) {
                curr.next.prev = node;
            } else {
                tail = node;
            }
            curr.next = node;
            node.prev = curr;
        }
        size++;
        updateIndexArray(index, node);
    }


    private void updateIndexArray(int index, Node<T> node) {
        if (index >= indexArray.length) {
            indexArray = Arrays.copyOf(indexArray, indexArraySize * 2);
            indexArraySize *= 2;
        }
        indexArray[index] = node;
    }

    /**
     * Removes the last element from the list and returns it.
     *
     * @return the last element in the list
     */
    public T removeLast() {
        Node<T> curr = tail;
        if (curr.prev != null) {
            curr.prev.next = null;
        } else {
            head = null;
        }
        tail = curr.prev;
        size--;
        return curr.data;
    }

    /**
     * Returns the index of the last occurrence of the specified element in the list, or -1 if the element is not found.
     *
     * @param element the element to search for
     * @return the index of the last occurrence of the element, or -1 if not found
     */
    public int lastIndexOf(T element) {
        int index = -1;
        Node<T> curr = head;
        int i = 0;
        while (curr != null) {
            if (curr.data.equals(element)) {
                index = i;
            }
            curr = curr.next;
            i++;
        }
        return index;
    }

    /**
     * Removes all elements from the list starting from the specified fromIndex to the specified toIndex.
     *
     * @param fromIndex the index at which to start removing elements
     * @param toIndex   the index at which to stop removing elements
     */
    public void removeRange(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > size || fromIndex > toIndex) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> curr = head;
        Node<T> prev = null;
        int i = 0;
        while (curr != null && i < fromIndex) {
            prev = curr;
            curr = curr.next;
            i++;
        }
        Node<T> next = curr;
        while (curr != null && i < toIndex) {
            next = curr.next;
            curr.next = freeNode;
            freeNode = curr;
            freeCount++;
            curr = next;
            i++;
        }
        if (prev != null) {
            prev.next = next;
        } else {
            head = next;
        }
        if (next != null) {
            next.prev = prev;
        } else {
            tail = prev;
        }
        size -= (toIndex - fromIndex);
    }

    /**
     * Retains only the elements in the list that are contained in the specified list.
     *
     * @param list the list of elements to retain
     */
    public void retainAll(FlexiList<T> list) {
        Node<T> curr = head;
        while (curr != null) {
            if (!list.contains(curr.data)) {
                Node<T> next = curr.next;
                removeByElement(curr.data);
                curr = next;
            } else {
                curr = curr.next;
            }
        }
    }

    /**
     * Removes all elements in the list that are contained in the specified list.
     *
     * @param list the list of elements to remove
     */
    public void removeAll(FlexiList<T> list) {
        if (list == this) {
            clear();
        } else {
            Node<T> curr = head;
            while (curr != null) {
                if (list.contains(curr.data)) {
                    Node<T> next = curr.next;
                    removeByElement(curr.data);
                    curr = next;
                } else {
                    curr = curr.next;
                }
            }
        }
    }


    /**
     * Replaces all occurrences of the specified old value with the specified new value.
     *
     * @param oldVal the old value to replace
     * @param newVal the new value to replace with
     */
    public void replaceAll(T oldVal, T newVal) {
        Node<T> curr = head;
        while (curr != null) {
            if (curr.data.equals(oldVal)) {
                curr.data = newVal;
            }
            curr = curr.next;
        }
    }

    /**
     * Sorts the elements in the list according to the specified comparator.
     *
     * @param comparator the comparator to use for sorting
     */
    public void sort(Comparator<T> comparator) {
        Node<T> curr = head;
        Object[] array = new Object[size];
        int i = 0;
        while (curr != null) {
            array[i] = curr.data;
            curr = curr.next;
            i++;
        }
        Arrays.sort(array);
        curr = head;
        i = 0;
        while (curr != null) {
            curr.data = (T) array[i];
            curr = curr.next;
            i++;
        }
    }

    /**
     * Returns a new list containing the elements from the specified fromIndex to the specified toIndex.
     *
     * @param fromIndex the index at which to start the sublist
     * @param toIndex   the index at which to end the sublist
     * @return the sublist
     */
    public FlexiList<T> subList(int fromIndex, int toIndex) {
        FlexiList<T> sublist = new FlexiList<>();
        Node<T> curr = head;
        int i = 0;
        while (curr != null && i < fromIndex) {
            curr = curr.next;
            i++;
        }
        while (curr != null && i < toIndex) {
            sublist.add(curr.data);
            curr = curr.next;
            i++;
        }
        return sublist;
    }

    /**
     * Returns an array containing all elements in the list.
     *
     * @return the array of elements
     */
    public T[] toArray() {
        T[] array = (T[]) java.lang.reflect.Array.newInstance(head.data.getClass(), size);
        Node<T> curr = head;
        int i = 0;
        while (curr != null) {
            array[i] = curr.data;
            curr = curr.next;
            i++;
        }
        return array;
    }


    /**
     * - Trims the capacity of the list to its current size.
     */
    public void trimToSize() {
        if (size < indexArray.length) {
            indexArray = Arrays.copyOf(indexArray, size);
            indexArraySize = size;
        }
    }


    /**
     * Gets the element at the specified index.
     *
     * @param index the index of the element to retrieve
     * @return the element at the specified index
     */
    public T get(int index) {
        Node<T> curr;
        if (index < size / 2) {
            curr = head;
            for (int i = 0; i < index; i++) {
                curr = curr.next;
            }
        } else {
            curr = tail;
            for (int i = size - 1; i > index; i--) {
                curr = curr.prev;
            }
        }
        return curr.data;
    }

    /**
     * Sets the element at the specified index.
     *
     * @param index   the index of the element to set
     * @param element the new element value
     */
//    @SuppressWarnings("unchecked")
    public void set(int index, T element) {
        Node<T> curr = (Node<T>) indexArray[index];
        curr.data = element;
    }

    /**
     * Returns the size of the list.
     *
     * @return the size of the list
     */
    public int size() {
        return size;
    }

    /**
     * Removes the element at the specified index.
     *
     * @param index the index of the element to remove
     * @return the removed element
     */
    public T removeByIndex(int index) {
        Node<T> curr = (Node<T>) indexArray[index];
        if (curr.prev != null) {
            curr.prev.next = curr.next;
        } else {
            head = curr.next;
        }
        if (curr.next != null) {
            curr.next.prev = curr.prev;
        } else {
            tail = curr.prev;
        }

        updateFreeNode(curr);
        updateIndexArray(index);

        return curr.data;
    }

    /**
     * Removes the specific element first occurrence
     *
     * @param element the element to remove
     */
    public void removeByElement(T element) {
        Node<T> curr = head;
        while (curr != null) {
            if (curr.data.equals(element)) {
                if (curr.prev != null) {
                    curr.prev.next = curr.next;
                } else {
                    head = curr.next;
                }
                if (curr.next != null) {
                    curr.next.prev = curr.prev;
                } else {
                    tail = curr.prev;
                }

                int index = indexOf(curr);
                if (index != -1) {
                    updateFreeNode(curr);
                    updateIndexArray(curr);
                }
                return;
            }
            curr = curr.next;
        }
    }

    /**
     * Utility method to free node and increase freeCount
     *
     * @param node of list
     */
    private void updateFreeNode(Node<T> node) {
        node.next = freeNode;
        freeNode = node;
        freeCount++;
    }

    /**
     * Utility method to update array index for remove element by Index
     *
     * @param index of array
     */
    private void updateIndexArray(int index) {
        if (index >= 0 && index < size) {
            for (int i = index; i < size; i++) {
                indexArray[i] = indexArray[i + 1];
            }
        }
    }

    /**
     * Utility method to update array index  for remove element by Element
     *
     * @param node of list
     */
    private void updateIndexArray(Node<T> node) {
        int index = indexOf(node);
        updateIndexArray(index);
    }

    /**
     * Utility method to return the index
     *
     * @param node of list
     */
    private int indexOf(Node<T> node) {
        int index = 0;
        Node<T> curr = head;
        while (curr != null && curr != node) {
            curr = curr.next;
            index++;
        }
        return curr == null ? -1 : index;
    }

    /**
     * Returns whether the list is empty.
     *
     * @return whether the list is empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns whether the list contains the specified element.
     *
     * @param element the element to search for
     * @return whether the list contains the specified element
     */
    public boolean contains(T element) {
        Node<T> curr = head;
        while (curr != null) {
            if (curr.data.equals(element)) {
                return true;
            }
            curr = curr.next;
        }
        return false;
    }

    /**
     * Returns the index of the specified element.
     *
     * @param element the element to search for
     * @return the index of the specified element, or -1 if not found
     */
    public int indexOf(T element) {
        Node<T> curr = head;
        int index = 0;
        while (curr != null) {
            if (curr.data.equals(element)) {
                return index;
            }
            curr = curr.next;
            index++;
        }
        return -1;
    }

    /**
     * Clears the list.
     */
    public void clear() {
        head = null;
        tail = null;
        lastNode = null;
        size = 0;
        freeNode = null;
        freeCount = 0;
        indexArray = new Object[indexArraySize];
    }

    /**
     * Sorts the list using the merge sort algorithm.
     */
    public void mergeSort() {
        List<T> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(get(i));
        }
        Collections.sort(list);
        clear();
        for (T element : list) {
            add(element);
        }
    }

    /**
     * Merges two sorted lists into a single sorted list.
     *
     * @param left  the first sorted list
     * @param right the second sorted list
     */
    private void merge(FlexiList<T> left, FlexiList<T> right) {
        // Merge the two sorted lists into a single sorted list
        while (!left.isEmpty() && !right.isEmpty()) {
            if (left.get(0).compareTo(right.get(0)) <= 0) {
                add(left.removeByIndex(0));
            } else {
                add(right.removeByIndex(0));
            }
        }
        // Add any remaining elements from the left or right lists
        while (!left.isEmpty()) {
            add(left.removeByIndex(0));
        }
        while (!right.isEmpty()) {
            add(right.removeByIndex(0));
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<T> curr = head;
        while (curr != null) {
            sb.append(curr.data);
            curr = curr.next;
            if (curr != null) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Returns first element in a constant O(1) time .
     *
     * @return first element.
     * @throws NoSuchElementException {
     *                                if size == 0
     *                                }
     */
    public T getFirst() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return head.data;
    }

    /**
     * Returns last element in a constant O(1) time .
     *
     * @return last element.
     * @throws NoSuchElementException {
     *                                if size == 0
     *                                }
     */
    public T getLast() {
        if (size == 0) {
            throw new NoSuchElementException();
        } else {
            return tail.data;
        }
    }


    /**
     * method to write serialized objects to file
     *
     * @param out {
     *            variable of Java.io.OutPutStream out
     *            }
     * @throws IOException {
     *                     unexpected writing operation errors
     *                     }
     */
    @Serial
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeInt(size);
        System.out.println("Writing size: " + size);
//    for (int i = 0; i < size; i++) {
//        out.writeObject(get(i));
//        System.out.println("Writing element: " + get(i));
//    }
    }

    /**
     * method provided to clone the list
     *
     * @return clone object of list
     */
    @Override
    public FlexiList<T> clone() {
        FlexiList<T> clone;
        try {
            clone = (FlexiList<T>) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
        clone.head = null;
        clone.tail = null;
        clone.lastNode = null;
        clone.size = 0;
        clone.freeNode = null;
        clone.freeCount = 0;
        clone.indexArray = new Object[indexArraySize];
        for (int i = 0; i < size; i++) {
            clone.add(get(i));
        }
        return clone;
    }
// using the default readObject method for serialization.

    /**
     * Returns initial size of the list
     *
     * @return initialSize
     */
    public int getInitialSize() {
        return indexArraySize;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return a new FLexiListIterator.
     */
    @Override
    public Iterator<T> iterator() {
        return new FlexiIterator();
//        return new FlexiIterator();  // choice between iterator and List iterator
    }

    /**
     * to do
     * method to swap two elements in the list
     *
     * @param index1 first index
     * @param index2 second index
     */
    public void swap(int index1, int index2) {
        if (index1 < 0 || index1 >= size || index2 < 0 || index2 >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }

        // If the indices are the same, no need to swap
        if (index1 == index2) {
            return;
        }

        // Find the nodes at the given indices
        Node<T> node1 = getNode(index1);
        Node<T> node2 = getNode(index2);

        // Swap the data in the nodes
        T temp = node1.data;
        node1.data = node2.data;
        node2.data = temp;
    }

    private Node<T> getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }

        // Determine whether to start traversal from head or tail
        Node<T> current;
        if (index < size / 2) {
            // Start from the head and move forward
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            // Start from the tail and move backward
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    /**
     * A private static class representing a node in the list.
     *
     * @param <T> the type of data stored in the node
     */
    private static class Node<T> implements Serializable {
        T data;
        Node<T> next;
        Node<T> prev;

        /**
         * Constructs a new node with the given data.
         *
         * @param data the data to store in the node
         */
        public Node(T data) {
            this.data = data;
        }
    }

    /**
     * A  private class for supporting for each loop
     */

    private class FlexiListIterator implements Iterator<T> {
        private Node<T> current = head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T element = current.data;
            current = current.next;
            return element;
        }
    }

    /**
     * A Private class for efficient way of  iterating over the elements of the FlexiList.
     * support for enhance for loop.
     */

    private class FlexiIterator implements ListIterator<T> {
        private Node<T> current = head;
        private Node<T> lastReturned = null;
        private int index = 0;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            lastReturned = current;
            T data = current.data;
            current = current.next;
            index++;
            return data;
        }

        @Override
        public boolean hasPrevious() {
            return lastReturned != null;
        }

        @Override
        public T previous() {
            current = lastReturned;
            lastReturned = current.prev;
            T data = current.data;
            index--;
            return data;
        }

        @Override
        public int nextIndex() {
            return index;
        }

        @Override
        public int previousIndex() {
            return index - 1;
        }

        @Override
        public void remove() {
            if (lastReturned == null) {
                throw new IllegalStateException();
            }
            FlexiList.this.removeByElement(lastReturned.data);
            lastReturned = null;
        }

        @Override
        public void set(T element) {
            if (lastReturned == null) {
                throw new IllegalStateException();
            }
            lastReturned.data = element;
        }

        @Override
        public void add(T element) {
            FlexiList.this.add(index, element);
            lastReturned = null;
        }


    }

}

// end of FlexiList----------------------------------------------------------------------


