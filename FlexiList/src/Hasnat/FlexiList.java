package Hasnat;

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


import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.List;
import java.util.Queue;
import java.util.*;
import java.util.function.*;
import java.util.stream.Stream;

import static java.lang.Math.min;


/**
 * - {@code FlexiList} is a high-performance, optional(thread-safe) feature, and flexible data structure that combines the benefits of arrays and linked lists.
 * - Like an array, it allows for efficient access to elements by {@code index}. Like a linked list, it allows for efficient {@code insertion} and {@code deletion} of elements at any position in the list.Like an ArrayList it permits to add {@code null}
 * - A list implementation that provides efficient add, remove, and sorting operations.
 * - The {@code key feature of a FlexiList} is its use of a doubly-linked list, where each node points to both the previous and next nodes in the list.
 * - This allows for efficient navigation in {@code both directions}, making it easy to insert and delete nodes at any position.
 * -
 * - {@code FlexiList} also provides several advantages over arrays and ArrayList:
 * -     - {@code Efficient insertion and deletion}: FlexiList can insert or delete nodes at any position in the list in O(1) time, whereas arrays require shifting all elements after the insertion or deletion point.
 * -     - {@code Dynamic size}: FlexiList can grow or shrink dynamically as elements are added or removed, whereas arrays have a fixed size.
 * -     - {@code Good memory locality}: FlexiList nodes are stored in a contiguous block of memory, making it more cache-friendly than arrays.
 * -     - {@code (optional)Thread safety}: FlexiList can be  thread-safe, allowing multiple threads to access and modify the list concurrently without fear of data corruption.
 * -     - {@code High performance}: FlexiList is optimized for performance, providing fast access, insertion, and deletion operations.
 * -
 * - {@code FlexiList} also provides several constructors to create instances with different initial sizes, allow or disallow duplicates,set to thread safe , and initialize with elements from various sources such as arrays, collections, streams, and iterators.
 * -
 *
 * @param <T> the type of elements in the list
 * @author MuhammadHasnatRasool
 * @version 0.5
 * @see Serializable
 * @see Cloneable
 * @see Arrays
 * @see ArrayList
 * @see ListIterator
 * @see Stream
 * @see Iterator
 * @see Spliterator
 * @see List
 * @see Iterable
 * @see Collection
 * @see Queue
 * @see SequencedCollection
 */
public class FlexiList<T extends Comparable<T>> implements Serializable, Cloneable, Iterable<T>, RandomAccess, Spliterator<T>, List<T>, Queue<T>, SequencedCollection<T> {

    /**
     * The flag value to allow to disallow duplicate values
     */
    private final boolean allowDuplicates;
    /**
     * The lock object used for synchronization when the list is in thread-safe mode.
     */
    private final Object lock = new Object();
    /**
     * To allow passing comparator in constructor
     */
    private Comparator<T> comparator;
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
    private int indexArraySize;
    /**
     * A flag indicating whether the list is in thread-safe mode or not.
     * If true, the list will be thread-safe and all modifications will be synchronized.
     * If false, the list will not be thread-safe and modifications will not be synchronized.
     */
    private boolean threadSafe;

    /**
     * Constructs a new FlexiList instance with default initial size and allows duplicates.
     */
    public FlexiList() {
        // instance variables already initialized with default values;
        this(100, true);

    }

    /**
     * Constructs a new FlexiList instance that supports passing comparator and allow duplicates.
     *
     * @param comparator can be passed to sort list as required
     */
    public FlexiList(Comparator<T> comparator) {
        this(true);
        this.comparator = comparator;
    }

    /**
     * Constructs a new FlexiList instance from an Enumeration.
     *
     * @param enumeration the Enumeration to construct the list from
     */
    public FlexiList(Enumeration<T> enumeration) {
        this(true);
        while (enumeration.hasMoreElements()) {
            add(enumeration.nextElement());
        }
    }


    /**
     * possible new constructor that provides thread safety feature
     *
     * @param threadSafe      flag to indicate list is thread safe
     * @param allowDuplicates flag to indicate duplicate values not allowed
     */
    public FlexiList(boolean threadSafe, boolean allowDuplicates) {
        this(100, true);
        this.threadSafe = threadSafe;
    }

    /**
     * Constructs a new FlexiList instance with provided initial size and allows duplicates.
     *
     * @param initialSize set the initial size of the list
     */
    public FlexiList(int initialSize) {
//        instance variables already initialized with default values;
        this(initialSize, true);
    }

    /**
     * Constructs a new FlexiList instance with provided initial size and allows/disallows duplicates.
     *
     * @param initialSize     set the initial size of the list
     * @param allowDuplicates flag to indicate duplicate values not allowed
     */
    public FlexiList(int initialSize, boolean allowDuplicates) {
//        instance variables already initialized with default values;
        if (initialSize > 0) {
            indexArraySize = initialSize;
            indexArray = new Object[indexArraySize];
        } else {
            throw new IllegalArgumentException("Initial size must be greater than 0");
        }
        this.allowDuplicates = allowDuplicates;
    }

    /**
     * Constructs a new FlexiList instance with default initial size and allows/disallows duplicates.
     *
     * @param allowDuplicates flag to indicate duplicate values not allowed
     */
    public FlexiList(boolean allowDuplicates) {
//        instance variables already initialized with default values;
        this(100, allowDuplicates);
    }

    /**
     * Constructs a new FlexiList instance with provided elements.
     *
     * @param elements the elements to add
     */
    @SafeVarargs
    public FlexiList(T... elements) {
        //        instance variables already initialized with default values;
        this(100, true);
        this.addAll(Arrays.asList(elements));
    }

    /**
     * Constructs a new FlexiList instance with provided collection of elements.
     *
     * @param collection the collection of elements to add
     */
    public FlexiList(Collection<T> collection) {
        //        instance variables already initialized with default values;
        this(100, true);
        this.addAll(collection);
    }

    /**
     * Constructs a new FlexiList instance with provided sublist from an existing list.
     *
     * @param list  the existing list
     * @param start the start index of the sublist
     * @param end   the end index of the sublist
     */
    public FlexiList(FlexiList<T> list, int start, int end) {
        //        instance variables already initialized with default values;
        this(100, true);
        for (int i = start; i < end; i++) {
            add(list.get(i));
        }
    }

    /**
     * Constructs a new FlexiList instance with provided array of elements.
     * allow to set boolean value false to don't allow duplicate values .
     *
     * @param elements        the array of elements to add
     * @param allowDuplicates flag to indicate duplicate values not allowed
     */
    public FlexiList(T[] elements, boolean allowDuplicates) {
        //        instance variables already initialized with default values;
        this(allowDuplicates);
        this.addAll(Arrays.asList(elements));
    }

    /**
     * Constructs a new FlexiList instance with provided stream of elements.
     *
     * @param stream the stream of elements to add
     */
    public FlexiList(Stream<T> stream) {
        //        instance variables already initialized with default values;
        this(100, true);
        stream.forEach(this::add);
    }

    /**
     * Constructs a new FlexiList instance with provided iterator of elements.
     *
     * @param iterator the iterator of elements to add
     */
    public FlexiList(Iterator<T> iterator) {
        //        instance variables already initialized with default values;
        this(100, true);
        while (iterator.hasNext()) {
            add(iterator.next());
        }
    }


    /**
     * Adds an element to the list.
     *
     * @param element the element to add
     * @return false if element could not be added
     */
    public boolean add(T element) {
        if (!allowDuplicates && contains(element)) {
            return false;
        }
        Node<T> node;
        if (freeNode != null) {
            node = freeNode;
            freeNode = freeNode.next;
            freeCount--;
        } else {
            node = new Node<>(element);
        }
        node.data = element;
        if (threadSafe) {
            synchronized (lock) {
                addNode(node);
            }
        } else {
            addNode(node);
        }
        return true;
    }

    private void addNode(Node<T> node) {
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
     * Removes the first occurrence of the specified element from this list,
     * if it is present (optional operation).  If this list does not contain
     * the element, it is unchanged.  More formally, removes the element with
     * the lowest index {@code i} such that
     * {@code Objects.equals(o, get(i))}
     * (if such an element exists).  Returns {@code true} if this list
     * contained the specified element (or equivalently, if this list changed
     * as a result of the call).
     *
     * @param o element to be removed from this list, if present
     * @return {@code true} if this list contained the specified element
     * @throws ClassCastException            if the type of the specified element
     *                                       is incompatible with this list
     *                                       (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException          if the specified element is null and this
     *                                       list does not permit null elements
     *                                       (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws UnsupportedOperationException if the {@code remove} operation
     *                                       is not supported by this list
     */
    @Override
    public boolean remove(Object o) {
        if (threadSafe) {
            synchronized (lock) {
                Node<T> curr = head;
                while (curr != null) {
                    if (curr.data.equals(o)) {
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
                        return true;
                    }
                    curr = curr.next;
                }
            }
        } else {
            Node<T> curr = head;
            while (curr != null) {
                if (curr.data.equals(o)) {
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
                    return true;
                }
                curr = curr.next;
            }
        }
        return false;
    }

    /**
     * Returns {@code true} if this list contains all the elements of the
     * specified collection.
     *
     * @param c collection to be checked for containment in this list
     * @return {@code true} if this list contains all the elements of the
     * specified collection
     * @throws ClassCastException   if the types of one or more elements
     *                              in the specified collection are incompatible with this
     *                              list
     *                              (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified collection contains one
     *                              or more null elements and this list does not permit null
     *                              elements
     *                              (<a href="Collection.html#optional-restrictions">optional</a>),
     *                              or if the specified collection is null
     * @see #contains(Object)
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object element : c) {
            if (!contains(element)) {
                return false;
            }
        }
        return true;
    }


    /**
     * Appends all the elements in the specified collection to the end of
     * this list, in the order that they are returned by the specified
     * collection's iterator (optional operation).  The behavior of this
     * operation is undefined if the specified collection is modified while
     * the operation is in progress.  (Note that this will occur if the
     * specified collection is this list, and it's nonempty.)
     *
     * @param c collection containing elements to be added to this list
     * @return {@code true} if this list changed as a result of the call
     * @throws UnsupportedOperationException if the {@code addAll} operation
     *                                       is not supported by this list
     * @throws ClassCastException            if the class of an element of the specified
     *                                       collection prevents it from being added to this list
     * @throws NullPointerException          if the specified collection contains one
     *                                       or more null elements and this list does not permit null
     *                                       elements, or if the specified collection is null
     * @throws IllegalArgumentException      if some property of an element of the
     *                                       specified collection prevents it from being added to this list
     */
    @Override
    public boolean addAll(Collection<? extends T> c) {
        if (c == null) {
            throw new NullPointerException();
        }
        if (c.isEmpty()) {
            return false;
        }
        for (T element : c) {
            add(element);
        }
        return true;
    }

    /**
     * Inserts all the elements in the specified collection into this
     * list at the specified position (optional operation).  Shifts the
     * element currently at that position (if any) and any subsequent
     * elements to the right (increases their indices).  The new elements
     * will appear in this list in the order that they are returned by the
     * specified collection's iterator.  The behavior of this operation is
     * undefined if the specified collection is modified while the
     * operation is in progress.  (Note that this will occur if the specified
     * collection is this list, and it's nonempty.)
     *
     * @param index index at which to insert the first element from the
     *              specified collection
     * @param c     collection containing elements to be added to this list
     * @return {@code true} if this list changed as a result of the call
     * @throws UnsupportedOperationException if the {@code addAll} operation
     *                                       is not supported by this list
     * @throws ClassCastException            if the class of an element of the specified
     *                                       collection prevents it from being added to this list
     * @throws NullPointerException          if the specified collection contains one
     *                                       or more null elements and this list does not permit null
     *                                       elements, or if the specified collection is null
     * @throws IllegalArgumentException      if some property of an element of the
     *                                       specified collection prevents it from being added to this list
     * @throws IndexOutOfBoundsException     if the index is out of range
     *                                       ({@code index < 0 || index > size()})
     */
    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        if (c == null) {
            throw new NullPointerException();
        }
        if (c.isEmpty()) {
            return false;
        }
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        int i = index;
        for (T element : c) {
            add(i, element);
            i++;
        }
        return true;
    }


    /**
     * Removes from this list all of its elements that are contained in the
     * specified collection (optional operation).
     *
     * @param c collection containing elements to be removed from this list
     * @return {@code true} if this list changed as a result of the call
     * @throws UnsupportedOperationException if the {@code removeAll} operation
     *                                       is not supported by this list
     * @throws ClassCastException            if the class of an element of this list
     *                                       is incompatible with the specified collection
     *                                       (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException          if this list contains a null element and the
     *                                       specified collection does not permit null elements
     *                                       (<a href="Collection.html#optional-restrictions">optional</a>),
     *                                       or if the specified collection is null
     * @see #remove(Object)
     * @see #contains(Object)
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        if (c == this) {
            clear();
        } else if (threadSafe) {
            synchronized (lock) {
                Node<T> curr = head;
                while (curr != null) {
                    if (c.contains(curr.data)) {
                        Node<T> next = curr.next;
                        removeByElement(curr.data);
                        curr = next;
                    } else {
                        curr = curr.next;
                    }
                }
            }
        } else {
            Node<T> curr = head;
            while (curr != null) {
                if (c.contains(curr.data)) {
                    Node<T> next = curr.next;
                    removeByElement(curr.data);
                    curr = next;
                } else {
                    curr = curr.next;
                }
            }
        }
        return false;
    }

    /**
     * Retains only the elements in this list that are contained in the
     * specified collection (optional operation).  In other words, removes
     * from this list all of its elements that are not contained in the
     * specified collection.
     *
     * @param c collection containing elements to be retained in this list
     * @return {@code true} if this list changed as a result of the call
     * @throws UnsupportedOperationException if the {@code retainAll} operation
     *                                       is not supported by this list
     * @throws ClassCastException            if the class of an element of this list
     *                                       is incompatible with the specified collection
     *                                       (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException          if this list contains a null element and the
     *                                       specified collection does not permit null elements
     *                                       (<a href="Collection.html#optional-restrictions">optional</a>),
     *                                       or if the specified collection is null
     * @see #remove(Object)
     * @see #contains(Object)
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        Node<T> curr = head;
        while (curr != null) {
            if (!c.contains(curr.data)) {
                Node<T> next = curr.next;
                removeByElement(curr.data);
                curr = next;
            } else {
                curr = curr.next;
            }
        }
        return true;
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
        if (!allowDuplicates && contains(element)) {
            return;
        }
        if (threadSafe) {
            synchronized (lock) {
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
        } else {
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

    }

    /**
     * Removes the element at the specified position in this list (optional
     * operation).  Shifts any subsequent elements to the left (subtracts one
     * from their indices).  Returns the element that was removed from the
     * list.
     *
     * @param index the index of the element to be removed
     * @return the element previously at the specified position
     * @throws UnsupportedOperationException if the {@code remove} operation
     *                                       is not supported by this list
     * @throws IndexOutOfBoundsException     if the index is out of range
     *                                       ({@code index < 0 || index >= size()})
     */
    @Override
    public T remove(int index) {
        if (threadSafe) {
            synchronized (lock) {
                return removeUnsafe(index);
            }
        } else {
            return removeUnsafe(index);
        }
    }

    private T removeUnsafe(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> curr;
        if (index <= size / 2) {
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
        size--;
        updateIndexArray(index);
        return curr.data;
    }

    /**
     * Returns the index of the first occurrence of the specified element
     * in this list, or -1 if this list does not contain the element.
     * More formally, returns the lowest index {@code i} such that
     * {@code Objects.equals(o, get(i))},
     * or -1 if there is no such index.
     *
     * @param o element to search for
     * @return the index of the first occurrence of the specified element in
     * this list, or -1 if this list does not contain the element
     * @throws ClassCastException   if the type of the specified element
     *                              is incompatible with this list
     *                              (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified element is null and this
     *                              list does not permit null elements
     *                              (<a href="Collection.html#optional-restrictions">optional</a>)
     */
    @Override
    public int indexOf(Object o) {
        int index = 0;
        Node<T> curr = head;
        while (curr != null && curr != o) {
            curr = curr.next;
            index++;
        }
        return curr == null ? -1 : index;
    }
//    @Override
//    public int indexOf(Object o) {
//        int index = 0;
//        for (FlexiList.Node<T> curr = head; curr != null; curr = curr.next, index++) {
//            if (curr.data.equals(o)) {
//                indexArray[index] = curr;
//                return index;
//            }
//        }
//        return -1;
//    }

    /**
     * Returns the index of the last occurrence of the specified element
     * in this list, or -1 if this list does not contain the element.
     * More formally, returns the highest index {@code i} such that
     * {@code Objects.equals(o, get(i))},
     * or -1 if there is no such index.
     *
     * @param o element to search for
     * @return the index of the last occurrence of the specified element in
     * this list, or -1 if this list does not contain the element
     * @throws ClassCastException   if the type of the specified element
     *                              is incompatible with this list
     *                              (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified element is null and this
     *                              list does not permit null elements
     *                              (<a href="Collection.html#optional-restrictions">optional</a>)
     */
    @Override
    public int lastIndexOf(Object o) {
        int index = -1;
        Node<T> curr = head;
        int i = 0;
        while (curr != null) {
            if (curr.data.equals(o)) {
                index = i;
            }
            curr = curr.next;
            i++;
        }
        return index;
    }

    /**
     * Returns a list iterator over the elements in this list (in proper
     * sequence).
     *
     * @return a list iterator over the elements in this list (in proper
     * sequence)
     */
    @Override
    public ListIterator<T> listIterator() {
        return new FlexiIterator();
//        return (ListIterator<T>) new FlexiListIterator();
    }

    /**
     * Returns a list iterator over the elements in this list (in proper
     * sequence), starting at the specified position in the list.
     * The specified index indicates the first element that would be
     * returned by an initial call to {@link ListIterator#next next}.
     * An initial call to {@link ListIterator#previous previous} would
     * return the element with the specified index minus one.
     *
     * @param index index of the first element to be returned from the
     *              list iterator (by a call to {@link ListIterator#next next})
     * @return a list iterator over the elements in this list (in proper
     * sequence), starting at the specified position in the list
     * @throws IndexOutOfBoundsException if the index is out of range
     *                                   ({@code index < 0 || index > size()})
     */
    @Override
    public ListIterator<T> listIterator(int index) {
        return new FlexiIterator(index);
    }


    private void updateIndexArray(int index, Node<T> node) {
        if (index >= indexArray.length) {
            indexArray = Arrays.copyOf(indexArray, indexArraySize * 2);
            indexArraySize *= 2;
        }
        indexArray[index] = node;
        if (index < size - 1) {
            indexArray[index + 1] = node.next;
        }
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
        } else if (threadSafe) {
            synchronized (lock) {
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
//    public void sort(Comparator<T> comparator) {
//        Node<T> curr = head;
//        Object[] array = new Object[size];
//        int i = 0;
//        while (curr != null) {
//            array[i] = curr.data;
//            curr = curr.next;
//            i++;
//        }
//        Arrays.sort(array);
//        curr = head;
//        i = 0;
//        while (curr != null) {
//            curr.data = (T) array[i];
//            curr = curr.next;
//            i++;
//        }
//    }
    public void sort(Comparator<? super T> comparator) {
        Node<T> curr = head;
        Object[] array = new Object[size];
        int i = 0;
        while (curr != null) {
            array[i] = curr.data;
            curr = curr.next;
            i++;
        }
        //  comparator = (Comparator<? super T>) String.CASE_INSENSITIVE_ORDER;
        if (comparator == null) {
            comparator = Comparator.naturalOrder();
        }
        Comparator<? super T> finalComparator = comparator;
        Arrays.sort(array, (a, b) -> {
            if (a == null && b == null) {
                return 0; // Treat two nulls as equal
            }
            if (a == null) {
                return 1; // Nulls come after non-null values
            }
            if (b == null) {
                return -1; // Non-null values come before nulls
            }
            return finalComparator.compare((T) a, (T) b);
        });
        curr = head;
        i = 0;
        while (curr != null) {
            curr.data = (T) array[i];
            curr = curr.next;
            i++;
        }
    }

    /**
     * Method that gives true if all elements are strings
     * It was added for sorting strings .
     * @return true if all elements are strings
     */
    public boolean containsOnlyStrings() {
        Node<T> curr = head;
        while (curr != null) {
            if (!(curr.data instanceof String)) {
                return false;
            }
            curr = curr.next;
        }
        return true;
    }

    /**
     * Method that gives true if all elements are characters.
     * @return true if all elements are characters.
     */
    public boolean containsOnlyCharacters() {
        Node<T> curr = head;
        while (curr != null) {
            if (!(curr.data instanceof Character)) {
                return false;
            }
            curr = curr.next;
        }
        return true;
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
        T[] array = (T[]) Array.newInstance(head.data.getClass(), size);
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
     * Returns an array containing all  the elements in this list in
     * proper sequence (from first to last element); the runtime type of
     * the returned array is that of the specified array.  If the list fits
     * in the specified array, it is returned therein.  Otherwise, a new
     * array is allocated with the runtime type of the specified array and
     * the size of this list.
     *
     * <p>If the list fits in the specified array with room to spare (i.e.,
     * the array has more elements than the list), the element in the array
     * immediately following the end of the list is set to {@code null}.
     * (This is useful in determining the length of the list <i>only</i> if
     * the caller knows that the list does not contain any null elements.)
     *
     * <p>Like the {@link #toArray()} method, this method acts as bridge between
     * array-based and collection-based APIs.  Further, this method allows
     * precise control over the runtime type of the output array, and may,
     * under certain circumstances, be used to save allocation costs.
     *
     * <p>Suppose {@code x} is a list known to contain only strings.
     * The following code can be used to dump the list into a newly
     * allocated array of {@code String}:
     *
     * <pre>{@code
     *     String[] y = x.toArray(new String[0]);
     * }</pre>
     * <p>
     * Note that {@code toArray(new Object[0])} is identical in function to
     * {@code toArray()}.
     *
     * @param a the array into which the elements of this list are to
     *          be stored, if it is big enough; otherwise, a new array of the
     *          same runtime type is allocated for this purpose.
     * @return an array containing the elements of this list
     * @throws ArrayStoreException  if the runtime type of the specified array
     *                              is not a supertype of the runtime type of every element in
     *                              this list
     * @throws NullPointerException if the specified array is null
     */
    @Override
    public <T1> T1[] toArray(T1[] a) {
        T1[] array = (T1[]) Array.newInstance(head.data.getClass(), size);
        Node<T> curr = head;
        int i = 0;
        while (curr != null) {
            array[i] = (T1) curr.data;
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
     * @throws IndexOutOfBoundsException if the index is out of range (index less than  0 || index >= size)
     */
    //    public T get(int index) {
    //
    //        // bidirectional traversal to get element works for only doubly linked list
    //
    //        // - If the index is less than half the size of the list,
    //        // it starts from the first node and traverses forward until it reaches the desired index.
    //
    //        //- If the index is greater than half the size of the list,
    //        // it starts from the last node and traverses backward until it reaches the desired index.
    //
    //        Node<T> node = node(index);
    //        return node != null ? node.data : null;
    //    }
    //
    //    Node<T> node(int index) {
    //        Node<T> x;
    //        // size / 2  equivalent used  size >> 1 for faster check
    //        //  It's a good way to divide an integer by 2.
    //        if (index < (size >> 1)) {
    //            x = head;
    //            for (int i = 0; i < index; i++)
    //                x = x.next;
    //        } else {
    //            x = tail;
    //            for (int i = size - 1; i > index; i--)
    //                x = x.prev;
    //        }
    //        return x;
    //    }
    public T get(int index) {
        Node<T> node = node(index);
        return node != null ? node.data : null;
    }

    Node<T> node(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        Node<T> x;
        if (index <= size / 2) {
            x = head;
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
        } else {
            x = tail;
            for (int i = size - 1; i > index; i--) {
                x = x.prev;
            }
        }
        return x;
    }


    /**
     * Sets the element at the specified index.
     *
     * @param index   the index of the element to set
     * @param element the new element value
     * @return element set on specific index.
     */
//    @SuppressWarnings("unchecked")
    public T set(int index, T element) {
        if (threadSafe) {
            synchronized (lock) {

                Node<T> curr = getNode(index);
                if (curr != null) {
                    T oldData = curr.data;
                    curr.data = element;
                    return oldData;
                } else {
                    throw new IndexOutOfBoundsException("Index " + index + " is out of bounds");
                }
            }

        } else {
            Node<T> curr = getNode(index);
            if (curr != null) {
                T oldData = curr.data;
                curr.data = element;
                return oldData;
            } else {
                throw new IndexOutOfBoundsException("Index " + index + " is out of bounds");
            }
        }
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
        if (threadSafe) {
            synchronized (lock) {
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
        } else {
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
    }

    /**
     * Removes the specific element first occurrence
     *
     * @param element the element to remove
     */
    public void removeByElement(T element) {
        if (threadSafe) {
            synchronized (lock) {
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
        } else {
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
        // debug statements added to check working of freenode functionality on day -> 16/6/2024
//        System.out.println("free count value: "+freeCount);
//        System.out.println("free node data: "+node.data);
    }

    /**
     * Utility method to update array index for remove element by Index
     *
     * @param index of array
     */

    private void updateIndexArray(int index) {
        for (int i = index; i < size - 1; i++) {
            indexArray[i] = indexArray[i + 1];
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
     * Returns {@code true} if this list contains the specified element.
     * More formally, returns {@code true} if and only if this list contains
     * at least one element {@code e} such that
     * {@code Objects.equals(o, e)}.
     *
     * @param o element whose presence in this list is to be tested
     * @return {@code true} if this list contains the specified element
     * @throws ClassCastException   if the type of the specified element
     *                              is incompatible with this list
     *                              (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified element is null and this
     *                              list does not permit null elements
     *                              (<a href="Collection.html#optional-restrictions">optional</a>)
     */
    @Override
    public boolean contains(Object o) {
        int index = indexOf(o);
        return index >= 0 && indexArray[index] != null;
    }

    /**
     * Removes all elements from this list that satisfy the given predicate.
     *
     * @param predicate the predicate to test elements against
     * @return false if something went wrong
     */
    @Override
    public boolean removeIf(Predicate<? super T> predicate) {
        Node<T> curr = head;
        Node<T> prev = null;
        while (curr != null) {
            if (predicate.test(curr.data)) {
                if (prev == null) {
                    head = curr.next;
                } else {
                    prev.next = curr.next;
                }
                if (curr == lastNode) {
                    lastNode = prev;
                }
                size--;
            } else {
                prev = curr;
            }
            curr = curr.next;
        }
        return false;
    }

    /**
     * Removes all elements from this list that satisfy the given predicate.
     * It uses Spliterator.
     *
     * @param predicate the predicate to test elements against
     */
    public void removeIfi(Predicate<T> predicate) {
        Spliterator<T> spliterator = this.spliterator();
        while (spliterator.tryAdvance(element -> {
            if (predicate.test(element)) {
                removeByElement(element);
            }
        })) {

        }
    }

    /**
     * Returns whether the list contains the specified element.
     *
     * @param element the element to search for
     * @return whether the list contains the specified element
     */
    public boolean contains(T element) {
        Node<T> curr = head;
        Node<T> tailCurr = tail;
        while (curr != null && tailCurr != null) {
            if (curr.data.equals(element)) {
                return true;
            }
            if (tailCurr.data.equals(element)) {
                return true;
            }
            curr = curr.next;
            tailCurr = tailCurr.prev;
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
        int index = 0;
        for (Node<T> curr = head; curr != null; curr = curr.next, index++) {
            if (curr.data.equals(element)) {
                indexArray[index] = curr;
                return index;
            }
        }
        return -1;
    }


    /**
     * Clears the list.
     */
    public void clear() {
        if (threadSafe) {
            synchronized (lock) {
                head = null;
                tail = null;
                lastNode = null;
                size = 0;
                freeNode = null;
                freeCount = 0;
                indexArray = new Object[indexArraySize];
            }
        } else {
            head = null;
            tail = null;
            lastNode = null;
            size = 0;
            freeNode = null;
            freeCount = 0;
            indexArray = new Object[indexArraySize];
        }
    }

    /**
     * Returns a sequential {@code Stream} with this collection as its source.
     *
     * <p>This method should be overridden when the {@link #spliterator()}
     * method cannot return a spliterator that is {@code IMMUTABLE},
     * {@code CONCURRENT}, or <em>late-binding</em>. (See {@link #spliterator()}
     * for details.)
     *
     * @return a sequential {@code Stream} over the elements in this collection
     * {@code implSpec The default implementation creates a sequential} {@code Stream} from the
     * collection's {@code Spliterator}.
     * @since 1.8
     */
    @Override
    public Stream<T> stream() {
        return List.super.stream();
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
        this.addAll(list);
    }

    /**
     * Merges two sorted lists into a single sorted list.
     *
     * @param left  the first sorted list
     * @param right the second sorted list
     */
    private void merge(FlexiList<T> left, FlexiList<T> right) {
        if (left.isEmpty() || right.isEmpty()) {
            return;
        }
        // Merge the two sorted lists into a single sorted list
        while (!left.isEmpty() && !right.isEmpty()) {
            if (left.getFirst().compareTo(right.getFirst()) <= 0) {
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

    /**
     * Return list formatted as a string.
     *
     * @return toString representation of list
     * @see #print()
     * @see #printList()
     */
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
//            return null;
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
            // Perform shallow copy
            clone = (FlexiList<T>) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }

        // Initialize clone attributes
        clone.head = null;
        clone.tail = null;
        clone.lastNode = null;
        clone.size = 0;
        clone.freeNode = null;
        clone.freeCount = 0;
        clone.indexArray = new Object[indexArraySize];

        // Iterate through the original list and add each element to the clone
        Node<T> curr = head;
        while (curr != null) {
            clone.add(curr.data);
            curr = curr.next;
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
     * Swaps the elements at the specified indices in the list.
     * This method swaps the elements at the specified indices in the list.
     * It first validates the indices to ensure they are within the bounds of the list.
     * If the indices are the same, indicating no need to swap, the method returns without performing any action.
     * Otherwise, it locates the nodes corresponding to the given indices.
     * If the nodes are adjacent, it swaps them by updating their respective next and prev pointers.
     * If the nodes are not adjacent, it swaps them by updating their next and prev pointers as well as the pointers of adjacent nodes.
     * <p>
     * After the swap, the method updates the head and tail pointers if necessary to maintain the integrity of the list.
     *
     * @param index1 the index of the first element to swap
     * @param index2 the index of the second element to swap
     * @throws IndexOutOfBoundsException if the indices are out of bounds
     */
    public void swap(int index1, int index2) {

        // Suppose we have a list: 10 <-> 20 <-> 30 <-> 40 <-> 50
        //
        //Now, let's swap elements at indices 1 and 3:
        //
        //Initially, the list looks like: 10 <-> 20 <-> 30 <-> 40 <-> 50
        //After swapping, the list becomes: 10 <-> 40 <-> 30 <-> 20 <-> 50

        if (index1 < 0 || index1 >= size || index2 < 0 || index2 >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }

        if (index1 == index2) {
            // Indices are the same, no need to swap
            return;
        }

        // Find the nodes at the given indices
        Node<T> node1 = getNode(index1);
        Node<T> node2 = getNode(index2);

        // Swap the nodes
        if (node1.next == node2) {
            // Nodes are adjacent
            Node<T> temp = node2.next;
            node2.next = node1;
            node1.prev = node2;
            node1.next = temp;
            if (temp != null) {
                temp.prev = node1;
            }
            node2.prev = null;
            head = node2;
            if (node1.next == null) {
                tail = node1;
            }
        } else if (node2.next == node1) {
            // Nodes are adjacent
            Node<T> temp = node1.next;
            node1.next = node2;
            node2.prev = node1;
            node2.next = temp;
            if (temp != null) {
                temp.prev = node2;
            }
            node1.prev = null;
            head = node1;
            if (node2.next == null) {
                tail = node2;
            }
        } else {
            // Nodes are not adjacent
            Node<T> tempPrev1 = node1.prev;
            Node<T> tempNext1 = node1.next;
            Node<T> tempPrev2 = node2.prev;
            Node<T> tempNext2 = node2.next;

            node1.prev = tempPrev2;
            node1.next = tempNext2;
            if (tempNext2 != null) {
                tempNext2.prev = node1;
            }
            if (tempPrev2 != null) {
                tempPrev2.next = node1;
            }

            node2.prev = tempPrev1;
            node2.next = tempNext1;
            if (tempNext1 != null) {
                tempNext1.prev = node2;
            }
            if (tempPrev1 != null) {
                tempPrev1.next = node2;
            }

            if (node1.prev == null) {
                head = node1;
            }
            if (node1.next == null) {
                tail = node1;
            }
            if (node2.prev == null) {
                head = node2;
            }
            if (node2.next == null) {
                tail = node2;
            }
        }
    }

    private Node<T> getNode(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        if (index == size) {
            return tail;
        }
        // check whether to start traversal from head or tail
        Node<T> current;

        if (index < (size >> 1)) {   // if index less than size ->Start from the head and move forward
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {     // Start from the tail and move backward
            current = tail;
            for (int i = size - 1; current != null && i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    /**
     * If a remaining element exists: performs the given action on it,
     * returning {@code true}; else returns {@code false}.  If this
     * Spliterator is {@link #ORDERED} the action is performed on the
     * next element in encounter order.  Exceptions thrown by the
     * action are relayed to the caller.
     * <p>
     * Subsequent behavior of a spliterator is unspecified if the action throws
     * an exception.
     *
     * @param action The action whose operation is performed at-most once
     * @return {@code false} if no remaining elements existed
     * upon entry to this method, else {@code true}.
     * @throws NullPointerException if the specified action is null
     */
    @Override
    public boolean tryAdvance(Consumer<? super T> action) {
        if (lastNode != null) {
            T data = lastNode.data;
            lastNode = lastNode.prev;
            action.accept(data);
            return true;
        }
        return false;
    }

    /**
     * If this spliterator can be partitioned, returns a Spliterator
     * covering elements, that will, upon return from this method, not
     * be covered by this Spliterator.
     *
     * <p>If this Spliterator is {@link #ORDERED}, the returned Spliterator
     * must cover a strict prefix of the elements.
     *
     * <p>Unless this Spliterator covers an infinite number of elements,
     * repeated calls to {@code trySplit()} must eventually return {@code null}.
     * Upon non-null return:
     * <ul>
     * <li>the value reported for {@code estimateSize()} before splitting,
     * must, after splitting, be greater than or equal to {@code estimateSize()}
     * for this and the returned Spliterator; and</li>
     * <li>if this Spliterator is {@code SUBSIZED}, then {@code estimateSize()}
     * for this spliterator before splitting must be equal to the sum of
     * {@code estimateSize()} for this and the returned Spliterator after
     * splitting.</li>
     * </ul>
     *
     * <p>This method may return {@code null} for any reason,
     * including emptiness, inability to split after traversal has
     * commenced, data structure constraints, and efficiency
     * considerations.
     *
     * @return a {@code Spliterator} covering some portion of the
     * elements, or {@code null} if this spliterator cannot be split
     * {@code apiNote An ideal} {@code trySplit} method efficiently (without
     * traversal) divides its elements exactly in half, allowing
     * balanced parallel computation.  Many departures from this ideal
     * remain highly effective; for example, only approximately
     * splitting an approximately balanced tree, or for a tree in
     * which leaf nodes may contain either one or two elements,
     * failing to further split these nodes.  However, large
     * deviations in balance and/or overly inefficient {@code
     * trySplit} mechanics typically result in poor parallel
     * performance.
     */
    @Override
    public Spliterator<T> trySplit() {
        if (size > 1) {
            int mid = size / 2;
            FlexiList<T> newSplit = new FlexiList<>();
            newSplit.head = getNode(mid);
            newSplit.tail = tail;
            newSplit.lastNode = newSplit.tail;
            newSplit.size = size - mid;
            tail = getNode(mid - 1);
            size = mid;
            return newSplit.spliterator();
        }
        return null;
    }


    /**
     * Returns an estimate of the number of elements that would be
     * encountered by a {@link #forEachRemaining} traversal, or returns {@link
     * Long#MAX_VALUE} if infinite, unknown, or too expensive to compute.
     *
     * <p>If this Spliterator is {@link #SIZED} and has not yet been partially
     * traversed or split, or this Spliterator is {@link #SUBSIZED} and has
     * not yet been partially traversed, this estimate must be an accurate
     * count of elements that would be encountered by a complete traversal.
     * Otherwise, this estimate may be arbitrarily inaccurate, but must decrease
     * as specified across invocations of {@link #trySplit}.
     *
     * @return the estimated size, or {@code Long.MAX_VALUE} if infinite,
     * unknown, or too expensive to compute.
     * {@code apiNote Even an inexact estimate} is often useful and inexpensive to compute.
     * For example, a sub-spliterator of an approximately balanced binary tree
     * may return a value that estimates the number of elements to be half of
     * that of its parent; if the root Spliterator does not maintain an
     * accurate count, it could estimate size to be the power of two
     * corresponding to its maximum depth.
     */
    @Override
    public long estimateSize() {
        return size;
    }

    /**
     * Returns a set of characteristics of this Spliterator and its
     * elements. The result is represented as ORed values from {@link
     * #ORDERED}, {@link #DISTINCT}, {@link #SORTED}, {@link #SIZED},
     * {@link #NONNULL}, {@link #IMMUTABLE}, {@link #CONCURRENT},
     * {@link #SUBSIZED}.  Repeated calls to {@code characteristics()} on
     * a given spliterator, prior to or in-between calls to {@code trySplit},
     * should always return the same result.
     *
     * <p>If a Spliterator reports an inconsistent set of
     * characteristics (either those returned from a single invocation
     * or across multiple invocations), no guarantees can be made
     * about any computation using this Spliterator.
     *
     * @return a representation of characteristics
     * {@code apiNote} The characteristics of a given spliterator before splitting
     * may differ from the characteristics after splitting.  For specific
     * examples see the characteristic values {@link #SIZED}, {@link #SUBSIZED}
     * and {@link #CONCURRENT}.
     */
    @Override
    public int characteristics() {
        return Spliterator.ORDERED | Spliterator.SIZED | Spliterator.SUBSIZED;
    }

    /**
     * Performs the given action for each element of the {@code Iterable}
     * until all elements have been processed or the action throws an
     * exception.  Actions are performed in the order of iteration, if that
     * order is specified.  Exceptions thrown by the action are relayed to the
     * caller.
     * <p>
     * The behavior of this method is unspecified if the action performs
     * side-effects that modify the underlying source of elements, unless an
     * overriding class has specified a concurrent modification policy.
     *
     * @param action The action to be performed for each element
     * @throws NullPointerException if the specified action is null
     *                              {@code implSpec} <p>The default implementation behaves as if:
     *                              <pre>{@code
     *                                                                                            for (T t : this)
     *                                                                                                action.accept(t);
     *                                                                                        }</pre>
     * @since 1.8
     */
    @Override
    public void forEachRemaining(Consumer<? super T> action) {
        Node<T> current = head;
        while (current != null) {
            action.accept(current.data);
            current = current.next;
        }
    }


    /**
     * Returns key passed for searching
     *
     * @param key passed to search inside the list
     * @return key
     */
    public Node<T> search(T key) {
        Node<T> current = head;
        int low = 0;
        int high = size - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            Node<T> midNode = head;
            for (int i = 0; i < mid; i++) {
                midNode = midNode.next;
            }
            if (midNode == null) {
                break;
            }
            if (midNode.data.equals(key)) {
                return midNode;
            } else if (midNode.data.compareTo(key) < 0) {
                low = mid + 1;
                current = midNode.next;
            } else {
                high = mid - 1;
            }
        }
        return null; // not found
    }


    /**
     * Returns nodes form the end of the list based on integer passed
     *
     * @param n integer passed
     * @return nth node based on integer passed
     */
    public Node<T> findNthNodeFromEnd(int n) {

//       //Use case of the method findNthNodeFromEnd:

//- Find the second last node in the list: Node<T> secondLast = findNthNodeFromEnd(2);
//- Find the third last node in the list: Node<T> thirdLast = findNthNodeFromEnd(3);
//- Find the last node in the list: Node<T> last = findNthNodeFromEnd(1);
//
//This method can be useful in situations where you need to access nodes from the end of the list, such as:
//
//- Finding the last node in a list
//- Finding the second last node in a list
//- Finding the third last node in a list
//- Finding the nth last node in a list
//
//For example, in a game, you might need to access the last node in a list of players to determine the winner. Or, in a social media app, you might need to access the second last node in a list of posts to display the most recent post.
        Node<T> slow = head;
        Node<T> fast = head;
        for (int i = 0; i < n; i++) {
            if (fast == null) {
                return null; // not enough nodes
            }
            fast = fast.next;
        }
        while (fast != null) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }

    /**
     * - Finds the middle element of the list.
     *
     * @return the middle element of the list
     */
    public T findMiddle() {

        // The findMiddle method uses the "tortoise and hare" algorithm to find the
        // middle element of the list. The "slow" variable moves one step at a time,
        // while the "fast" variable moves two steps at a time. When the "fast" variable
        // reaches the end of the list, the "slow" variable will be at the middle element.

//        Node<T> slow = head;
//        Node<T> fast = head;
//        while (fast != null && fast.next != null) {
//            slow = slow.next;
//            fast = fast.next.next;
//        }
//        assert slow != null;
//        return slow.data;
        Node<T> right = tail;
        Node<T> left = head;
        while (left != right && left.next != right) {
            left = left.next;
            right = right.prev;
        }
        return left.data;
    }

    /**
     * Pushes an element onto the stack.
     *
     * @param element the element to push
     */
    public void push(T element) {
        if (!allowDuplicates && contains(element)) {
            return;
        }
        Node<T> node = new Node<>(element);
        node.next = head;
        head = node;
        size++;
        updateIndexArray(size - 1, node);
    }

    /**
     * Pops an element from the stack.
     *
     * @return the popped element
     * @throws NoSuchElementException {
     *                                if element does not exist
     *                                }
     */
    public T pop() {
        if (head == null) {
            throw new NoSuchElementException();
        }
        T element = head.data;
        head = head.next;
        size--;
        return element;
    }

    /**
     * Enqueues an element onto the queue.
     *
     * @param element the element to enqueue
     */
    public void enqueue(T element) {
        if (!allowDuplicates && contains(element)) {
            return;
        }
        Node<T> node = new Node<>(element);
        if (tail == null) {
            head = node;
        } else {
            tail.next = node;
        }
        tail = node;
        size++;
    }

    /**
     * Dequeues an element from the queue.
     *
     * @return the dequeued element
     */
    public T dequeue() {
        T element = head.data;
        head = head.next;
        size--;
        return element;
    }

    /**
     * Removes all duplicates from the list.
     */
    public void deleteDuplicates() {
        Node<T> current = head;
        Node<T> previous = null;
        HashSet<T> seen = new HashSet<>();

        while (current != null) {
            if (seen.contains(current.data)) {
                if (previous != null) {
                    previous.next = current.next;
                    if (current.next != null) {
                        current.next.prev = previous;
                    } else {
                        tail = previous; // Update tail if the last duplicate node is removed
                    }
                } else {
                    head = current.next;
                    if (current.next != null) {
                        current.next.prev = null;
                    } else {
                        tail = null; // Update tail if all nodes are duplicates
                    }
                }
            } else {
                seen.add(current.data);
                previous = current;
            }
            current = current.next;
        }
    }

    /**
     * insertion sort algorithm to sort the list.
     */
    public void insertionSort() {
        if (head == null || head.next == null) {
            return; // List is empty or has only one element
        }

        Node<T> curr = head.next;
        while (curr != null) {
            T key = curr.data;
            Node<T> prev = curr.prev;
            while (prev != null && key.compareTo(prev.data) < 0) {
                prev.next.data = prev.data;
                prev = prev.prev;
            }
            if (prev == null) {
                head.data = key;
            } else {
                prev.next.data = key;
            }
            curr = curr.next;
        }
    }

    /**
     * Returns the head node of the list
     *
     * @return the head node of the list
     */
    public Node<T> getHead() {
        return head;
    }

    /**
     * Returns the tail node of the list.
     *
     * @return the tail node of the list
     */
    public Node<T> getTail() {
        return tail;
    }

    /**
     * Sorts the elements in the list using the quicksort algorithm.
     */
    public void quicksort() {
        if (size > 1) {
            quicksort(head, tail);
        }
    }

    /**
     * Recursively sorts the elements in the list using the quicksort algorithm.
     *
     * @param low  the lower bound of the sublist to be sorted
     * @param high the upper bound of the sublist to be sorted
     */
    private void quicksort(Node<T> low, Node<T> high) {
        if (low != null && high != null && low != high && low.prev != high) {
            Node<T> pivot = partition(low, high);
            quicksort(low, pivot.prev);
            quicksort(pivot.next, high);
        }
    }

    /**
     * Partitions the sublist into two parts, placing elements smaller than the pivot to its left
     * and elements larger than the pivot to its right.
     *
     * @param low  the lower bound of the sublist
     * @param high the upper bound of the sublist
     * @return the pivot node after partitioning
     */
    private Node<T> partition(Node<T> low, Node<T> high) {
        T pivot = high.data;
        Node<T> i = low.prev;
        for (Node<T> j = low; j != high; j = j.next) {
            if (j.data.compareTo(pivot) < 0) {
                i = (i == null) ? low : i.next;
                swapData(i, j);
            }
        }
        i = (i == null) ? low : i.next;
        swapData(i, high);
        return i;
    }

    /**
     * Swaps the data of two nodes.
     *
     * @param a the first node
     * @param b the second node
     */
    private void swapData(Node<T> a, Node<T> b) {
        T temp = a.data;
        a.data = b.data;
        b.data = temp;
    }

    /**
     * To improve quick sort for doubly linked list
     * helper method for a sort algorithm
     *
     * @return max value
     */
    private int findMax() {
        int max = Integer.MAX_VALUE;
        Node<T> curr = head;
        while (curr != null) {
            max = Math.max(max, (Integer) curr.data);
            curr = curr.next;
        }
        return max;
    }

    /**
     * Returns top element without removing it
     *
     * @return top element -> first element in the list
     */
    public T peek() {
        if (head == null) {
            return null;
        }
        return head.data;
    }

    /**
     * Method that checks if head null ,add element to head else add to tail
     *
     * @param element to add at last of the list
     * @return true is added successfully.
     */
    public boolean offer(T element) {
        if (head == null) {
            head = new Node<>(element);
            tail = head;
        } else {
            Node<T> newNode = new Node<>(element);
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
        updateIndexArray(size - 1, tail);
        return true;
    }

    /**
     * Retrieves and removes the head of this queue.  This method differs
     * from {@link #poll() poll()} only in that it throws an exception if
     * this queue is empty.
     *
     * @return the head of this queue
     * @throws NoSuchElementException if this queue is empty
     */
    @Override
    public T remove() {
        if (head == null) {
            return null;
        }
        T element = head.data;
        head = head.next;
        if (head == null) {
            tail = null;
        } else {
            head.prev = null;
        }
        size--;
        updateIndexArray(0);
        return element;
    }

    /**
     * Method that remove head element
     *
     * @return head element removed
     */
    public T poll() {
        if (head == null) {
            return null;
        }
        T element = head.data;
        head = head.next;
        if (head == null) {
            tail = null;
        } else {
            head.prev = null;
        }
        size--;
        updateIndexArray(0);
        return element;
    }

    /**
     * Retrieves, but does not remove, the head of this queue.  This method
     * differs from {@link #peek peek} only in that it throws an exception
     * if this queue is empty.
     *
     * @return the head of this queue
     * @throws NoSuchElementException if this queue is empty
     */
    @Override
    public T element() {
        if (head == null) {
            return null;
        }
        return head.data;
    }

    /**
     * Inserts the specified element at the front of this list.
     *
     * @param element the element to insert
     * @return {@code true} (as specified by {@link Deque#offerFirst})
     */
    public boolean offerFirst(T element) {
        Node<T> newNode = new Node<>(element);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        size++;
        updateIndexArray(0, head);
        return true;
    }

    /**
     * Inserts the specified element at the end of this list.
     *
     * @param element the element to insert
     * @return {@code true} (as specified by {@link Deque#offerLast})
     */
    public boolean offerLast(T element) {
        offer(element);
        return true;
    }

    /**
     * - Returns the first free node in the list.
     * -
     *
     * @return the first free node in the list
     */
    public Node<T> getFreeNode() {
        return freeNode;
    }

    /**
     * - Returns the number of free nodes in the list.
     * -
     *
     * @return the number of free nodes in the list
     */
    public int getFreeCount() {
        return freeCount;
    }

    /**
     * Alternative method to print list
     */
    public void printList() {
        System.out.println(this);
    }

    /**
     * Method that converts FlexiList to set .
     *
     * @return a set representation of list
     */
    public Set<T> toSet() {
        Set<T> set = new HashSet<>();
        Node<T> curr = head;
        while (curr != null) {
            set.add(curr.data);
            curr = curr.next;
        }
        return set;
    }

    /**
     * Method that converts FlexiList to map.
     *
     * @return map representation of list.
     */
    public Map<T, Integer> toMap() {
        Map<T, Integer> map = new HashMap<>();
        Node<T> curr = head;
        int index = 0;
        while (curr != null) {
            map.put(curr.data, index);
            curr = curr.next;
            index++;
        }
        return map;
    }

    /**
     * Method that works for numbers to calculate sum.
     *
     * @return sum in the form of double.
     */
    public Number sum() {
        double sum = 0;
        Node<T> curr = head;
        while (curr != null) {
            sum += ((Number) curr.data).doubleValue();
            curr = curr.next;
        }
        return sum;
    }

    // could be used for optimal searching ,sorting algorithms

//    private int binarySearch(int index, T key) {
//        int low = 0;
//        int high = index;
//        while (low <= high) {
//            int mid = (low + high) / 2;
//            if (get(mid).compareTo(key) <= 0) {
//                low = mid + 1;
//            } else {
//                high = mid - 1;
//            }
//        }
//        return low;
//    }

    /**
     * - Shuffles the elements in the list randomly.
     */
    public void shuffle() {
        Random rand = new Random();
        int size = size();
        for (int i = 0; i < size; i++) {
            int index = rand.nextInt(size);
            T temp = get(i);
            set(i, get(index));
            set(index, temp);
        }
    }

    /**
     * - Rotates the elements in the list by the specified distance.
     *
     * @param distance the distance to rotate the list
     */
    public void rotate(int distance) {
        int size = size();
        distance = distance % size;
        if (distance < 0) {
            distance += size;
        }
        Node<T> current = head;
        for (int i = 0; i < size - distance - 1; i++) {
            current = current.next;
        }
        Node<T> newHead = current.next;
        current.next = null;
        tail.next = head;
        head = newHead;
    }

    /**
     * - Removes all null elements from the list.
     */
    public void removeNulls() {
        Node<T> current = head;
        while (current != null) {
            Node<T> next = current.next;
            if (current.data == null) {
                remove(current);
            }
            current = next;
        }
    }

    /**
     * - Removes all empty strings from the list.
     */
    public void removeEmptyStrings() {
        Node<T> current = head;
        while (current != null) {
            Node<T> next = current.next;
            if (current.data instanceof String && ((String) current.data).isEmpty()) {
                remove(current);
            }
            current = next;
        }
    }

    /**
     * - Performs the specified action on each element, passing the element and its index as arguments.
     * -
     *
     * @param action the action to perform on each element
     */
    public void forEachWithIndex(ObjIntConsumer<T> action) {
        int index = 0;
        Node<T> current = head;
        while (current != null) {
            action.accept(current.data, index);
            current = current.next;
            index++;
        }
    }

    /**
     * Replaces all occurrences of the specified old value with the specified new value.
     *
     * @param oldVal the old value to replace
     * @param newVal the new value to replace with
     */
    public void replaceAllOfSame(T oldVal, T newVal) {
        Node<T> current = head;
        while (current != null) {
            if (current.data.equals(oldVal)) {
                current.data = newVal;
            }
            current = current.next;
        }
    }

    /**
     * - Converts the FlexiList to a Stack.
     * -
     *
     * @return a Stack containing all elements in the FlexiList
     */
    public Stack<T> toStack() {
        Stack<T> stack = new Stack<>();
        Node<T> current = tail; // Start from the tail node
        while (current != null) {
            stack.push(current.data);
            current = current.prev; // Move to the previous node
        }
        return stack;
    }

    /**
     * - Converts the FlexiList to a Queue.
     * -
     *
     * @return a Queue containing all elements in the FlexiList
     */
    public Queue<T> toQueue() {
        Queue<T> queue = new LinkedList<>();
        Node<T> current = head; // Start from the head node
        while (current != null) {
            queue.offer(current.data);
            current = current.next; // Move to the next node
        }
        return queue;
    }

    /**
     * - Returns the frequency of the specified element in the FlexiList.
     * -
     *
     * @param element the element to search for
     * @return the frequency of the element
     */
    public int frequency(T element) {
        int count = 0;
        Node<T> current = head;
        while (current != null) {
            if (current.data.equals(element)) {
                count++;
            }
            current = current.next;
        }
        return count;
    }

    /**
     * - Returns true if all elements in the FlexiList match the specified predicate.
     * -
     *
     * @param predicate the predicate to test
     * @return true if all elements match, false otherwise
     */
    public boolean allMatch(Predicate<T> predicate) {
        Node<T> current = head;
        while (current != null) {
            if (!predicate.test(current.data)) {
                return false;
            }
            current = current.next;
        }
        return true;
    }

    /**
     * - Returns true if any element in the FlexiList matches the specified predicate.
     * -
     *
     * @param predicate the predicate to test
     * @return true if any element matches, false otherwise
     */
    public boolean anyMatch(Predicate<T> predicate) {
        Node<T> current = head;
        while (current != null) {
            if (predicate.test(current.data)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    /**
     * - Returns true if no elements in the FlexiList match the specified predicate.
     * -
     *
     * @param predicate the predicate to test
     * @return true if no elements match, false otherwise
     */
    public boolean noneMatch(Predicate<T> predicate) {
        Node<T> current = head;
        while (current != null) {
            if (predicate.test(current.data)) {
                return false;
            }
            current = current.next;
        }
        return true;
    }

    /**
     * - Sorts the list in ascending order.
     */
    public void sortAscending() {

        flexiMergeSort();
//        Node<T> current = head;
//        while (current != null) {
//            Node<T> next = current.next;
//            while (next != null) {
//                if (current.data.compareTo(next.data) > 0) {
//                    T temp = current.data;
//                    current.data = next.data;
//                    next.data = temp;
//                }
//                next = next.next;
//            }
//            current = current.next;
//        }
    }

    /**
     * - Sorts the list in descending order.
     */
    public void sortDescending() {
        Node<T> current = head;
        while (current != null) {
            Node<T> next = current.next;
            while (next != null) {
                if (current.data.compareTo(next.data) < 0) {
                    T temp = current.data;
                    current.data = next.data;
                    next.data = temp;
                }
                next = next.next;
            }
            current = current.next;
        }
    }

    /**
     * - Filters the list based on a specific condition.
     * -
     *
     * @param condition the condition to test
     * @return a new list containing only the elements that match the condition
     */
    public FlexiList<T> filter(Predicate<T> condition) {
        FlexiList<T> result = new FlexiList<>();
        Node<T> current = head;
        while (current != null) {
            if (condition.test(current.data)) {
                result.add(current.data);
            }
            current = current.next;
        }
        return result;
    }

    /**
     * - Groups the list by a specific criteria.
     * -
     *
     * @param criteria the criteria to group by
     * @return a map where the keys are the unique values of the criteria and the values are lists of elements that match each key
     */
    public Map<T, List<T>> groupBy(Function<T, T> criteria) {
        Map<T, List<T>> result = new HashMap<>();
        Node<T> current = head;
        while (current != null) {
            T key = criteria.apply(current.data);
            if (!result.containsKey(key)) {
                result.put(key, new ArrayList<>());
            }
            result.get(key).add(current.data);
            current = current.next;
        }
        return result;
    }

    /**
     * - Transforms the list by applying a transformation to each element.
     * -
     *
     * @param transformation the transformation to apply
     * @return a new list containing the transformed elements
     */
    public FlexiList<T> transform(Function<T, T> transformation) {
//        if (containsNonNumbers()) {
//            throw new IllegalArgumentException("List must contain only numbers");
//        }
        FlexiList<T> result = new FlexiList<>();
        Node<T> current = head;
        while (current != null) {
            result.add(transformation.apply(current.data));
            current = current.next;
        }
        return result;
    }

    /**
     * -Generates a histogram of the list.
     * -
     *
     * @return a map where the keys are the unique elements in the list and the values are their frequencies
     */
    public Map<T, Integer> generateHistogram() {
        if (containsNonNumbers()) {
            throw new IllegalArgumentException("List must contain only numbers");
        }
        Map<T, Integer> result = new HashMap<>();
        Node<T> current = head;
        while (current != null) {
            T key = current.data;
            if (!result.containsKey(key)) {
                result.put(key, 0);
            }
            result.put(key, result.get(key) + 1);
            current = current.next;
        }
        return result;
    }

    /**
     * Calculates the mean of the list.
     *
     * @return the mean of the list
     */
    public double mean() {
        if (containsNonNumbers()) {
            throw new IllegalArgumentException("List must contain only numbers");
        }
        double sum = 0;
        int count = 0;
        Node<T> current = head;
        while (current != null) {
            sum += (double) (Double) current.data;
            count++;
            current = current.next;
        }
        return sum / count;
    }

    /**
     * Checks if all elements in the list are numbers.
     *
     * @return true false if all numbers and true otherwise
     */
    public boolean containsNonNumbers() {
        Node<T> current = head;
        while (current != null) {
            if (!(current.data instanceof Number)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    /**
     * Calculates the mode of the list.
     *
     * @return the mode of the list
     */
    public T mode() {
        if (containsNonNumbers()) {
            throw new IllegalArgumentException("List must contain only numbers");
        }
        Map<T, Integer> frequencyMap = new HashMap<>();
        Node<T> current = head;
        while (current != null) {
            T element = current.data;
            frequencyMap.put(element, frequencyMap.getOrDefault(element, 0) + 1);
            current = current.next;
        }
        T mode = null;
        int maxFrequency = 0;
        for (Map.Entry<T, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() > maxFrequency) {
                mode = entry.getKey();
                maxFrequency = entry.getValue();
            }
        }
        return mode;
    }

    /**
     * Calculates the standard deviation of the list.
     *
     * @return the standard deviation of the list
     */
    public double standardDeviation() {
        if (containsNonNumbers()) {
            throw new IllegalArgumentException("List must contain only numbers");
        }
        double mean = mean();
        double sum = 0;
        int count = 0;
        Node<T> current = head;
        while (current != null) {
            double diff = (Double) current.data - mean;
            sum += diff * diff;
            count++;
            current = current.next;
        }
        return Math.sqrt(sum / count);
    }

    /**
     * Performs a breadth-first search on the list.
     *
     * @param start the starting node
     * @return the list of nodes in the order they were visited
     */
    public List<T> breadthFirstSearch(T start) {
        List<T> visited = new ArrayList<>();
        Queue<Node<T>> queue = new LinkedList<>();
        Node<T> current = head;
        while (current != null) {
            if (current.data.equals(start)) {
                queue.add(current);
                break;
            }
            current = current.next;
        }
        while (!queue.isEmpty()) {
            Node<T> node = queue.poll();
            visited.add(node.data);
            Node<T> next = node.next;
            while (next != null) {
                if (!visited.contains(next.data)) {
                    queue.add(next);
                }
                next = next.next;
            }
        }
        return visited;
    }

    /**
     * Performs a depth-first search on the list.
     * It uses a stack to keep track of the nodes to visit, and a list to store the visited nodes.
     *
     * @param start the starting node
     * @return the list of nodes in the order they were visited
     */
    public List<T> depthFirstSearch(T start) {
        List<T> visited = new ArrayList<>();
        Stack<Node<T>> stack = new Stack<>();
        Node<T> current = head;
        while (current != null) {
            if (current.data.equals(start)) {
                stack.push(current);
                break;
            }
            current = current.next;
        }
        while (!stack.isEmpty()) {
            Node<T> node = stack.pop();
            visited.add(node.data);
            Node<T> next = node.next;
            while (next != null) {
                if (!visited.contains(next.data)) {
                    stack.push(next);
                }
                next = next.next;
            }
        }
        return visited;
    }

    /**
     * Concatenates two lists.
     *
     * @param other the other list
     * @return the concatenated list
     */
    public FlexiList<T> concat(FlexiList<T> other) {
        FlexiList<T> result = new FlexiList<>();
        Node<T> current = head;
        while (current != null) {
            result.add(current.data);
            current = current.next;
        }
        current = other.head;
        while (current != null) {
            result.add(current.data);
            current = current.next;
        }
        return result;
    }

    /**
     * Splits the list into substrings.
     *
     * @param separator the separator
     * @return the list of substrings
     */
    public List<String> split(T separator) {
        List<String> result = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        Node<T> current = head;
        while (current != null) {
            if (current.data.equals(separator)) {
                result.add(sb.toString());
                sb = new StringBuilder();
            } else {
                sb.append(current.data);
            }
            current = current.next;
        }
        if (!sb.isEmpty()) {
            result.add(sb.toString());
        }
        return result;
    }

    /**
     * Duplicate all elements inside the list
     */
    public void duplicate() {
        Node<T> current = head;
        while (current != null) {
            Node<T> newNode = new Node<>(current.data);
            newNode.next = current.next;
            current.next = newNode;
            current = newNode.next;
        }
    }

    /**
     * Converts the list to a CSV string.
     *
     * @return the list as a CSV string
     */
    public String toCSV() {
        StringBuilder csv = new StringBuilder();
        for (T element : this) {
            csv.append(element.toString()).append(",");
        }
        return csv.substring(0, csv.length() - 1); // remove trailing comma
    }

    /**
     * Converts the list to a JSON string.
     *
     * @return the list as a JSON string
     */
    public String toJSON() {
        StringBuilder json = new StringBuilder("[");
        for (T element : this) {
            json.append(element.toString()).append(",");
        }
        return json.substring(0, json.length() - 1) + "]"; // remove trailing comma and add closing bracket
    }

    /**
     * Calculates the variance of the list.
     *
     * @return the variance of the list
     */
    public double variance() {
        double mean = mean();
        double sum = 0;
        for (T element : this) {
            sum += Math.pow((Double) element - mean, 2);
        }
        return sum / size();
    }

    /**
     * Bubble sort algorithm to sort the elements in the FlexiList in ascending order
     * It compares each pair of adjacent elements and swaps them if they are in the wrong order.
     * The process is repeated until no more swaps are needed, indicating that the list is sorted.
     */
    public void bubbleSort() {
        Node<T> current = head;
        boolean swapped;
        while (current != null) {
            Node<T> nextNode = current.next;
            swapped = false;
            while (nextNode != null) {
                if (current.data.compareTo(nextNode.data) > 0) {
                    T temp = current.data;
                    current.data = nextNode.data;
                    nextNode.data = temp;
                    swapped = true;
                }
                nextNode = nextNode.next;
            }
            if (!swapped) {
                break;
            }
            current = current.next;
        }
    }

    /**
     * Finds and returns a list of duplicate elements in the list.
     *
     * @return a list of duplicate elements
     */
    public List<T> findDuplicates() {
        List<T> duplicates = new ArrayList<>();
        Map<T, Integer> countMap = new HashMap<>();
        Node<T> current = head;
        while (current != null) {
            T data = current.data;
            countMap.put(data, countMap.getOrDefault(data, 0) + 1);
            current = current.next;
        }
        for (Map.Entry<T, Integer> entry : countMap.entrySet()) {
            if (entry.getValue() > 1) {
                duplicates.add(entry.getKey());
            }
        }
        return duplicates;
    }

    /**
     * Generates and returns a list of all permutations of the elements in the list.
     *
     * @return a list of all permutations
     */
    public List<List<T>> generatePermutations() {
        List<List<T>> permutations = new ArrayList<>();
        List<T> list = new ArrayList<>();
        Node<T> current = head;
        while (current != null) {
            list.add(current.data);
            current = current.next;
        }
        generatePermutationsRecursive(list, 0, permutations);
        return permutations;
    }

    /**
     * Recursive helper method for generating permutations.
     *
     * @param list         the list of elements
     * @param start        the starting index
     * @param permutations the list of permutations
     */
    private void generatePermutationsRecursive(List<T> list, int start, List<List<T>> permutations) {
        if (start == list.size()) {
            permutations.add(new ArrayList<>(list));
        } else {
            for (int i = start; i < list.size(); i++) {
                Collections.swap(list, start, i);
                generatePermutationsRecursive(list, start + 1, permutations);
                Collections.swap(list, start, i);
            }
        }
    }

    /**
     * Generates and returns a list of all combinations of r elements from the list.
     *
     * @param r the number of elements to combine
     * @return a list of all combinations
     */
    public List<List<T>> generateCombinations(int r) {
        List<List<T>> combinations = new ArrayList<>();
        List<T> list = new ArrayList<>();
        Node<T> current = head;
        while (current != null) {
            list.add(current.data);
            current = current.next;
        }
        generateCombinationsRecursive(list, r, new ArrayList<>(), combinations);
        return combinations;
    }

    /**
     * Recursive helper method for generating combinations.
     *
     * @param list         the list of elements
     * @param r            the number of elements to combine
     * @param current      the current combination
     * @param combinations the list of combinations
     */
    private void generateCombinationsRecursive(List<T> list, int r, List<T> current, List<List<T>> combinations) {
        if (current.size() == r) {
            combinations.add(new ArrayList<>(current));
        } else {
            for (int i = 0; i < list.size(); i++) {
                current.add(list.get(i));
                generateCombinationsRecursive(list, r, current, combinations);
                current.removeLast();
            }
        }
    }


    /**
     * - Encrypts the entire list by shifting each character in each element by 255 places.
     */
    public void encryptList() {
        for (int i = 0; i < size(); i++) {
            set(i, encrypt(get(i)));
        }
    }

    /**
     * - Decrypts the entire list by shifting each character in each element back by 255 places.
     */
    public void decryptList() {
        for (int i = 0; i < size(); i++) {
            set(i, decrypt(get(i)));
        }
    }

    /**
     * - Encrypts a single element by shifting each character by 255 places
     *
     * @param data the elements to encrypt
     * @return the encrypted elements
     */
    private T encrypt(T data) {
        int shift = 255;
        String str = data.toString();
        StringBuilder encrypted = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            encrypted.append((char) (c + shift));
        }
        return (T) encrypted.toString();
    }

    /**
     * - Decrypts a single element by shifting each character back by 255 places.
     * -
     *
     * @param data the elements to decrypt
     * @return the decrypted elements
     */
    private T decrypt(T data) {
        int shift = 255;
        String str = data.toString();
        StringBuilder decrypted = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            decrypted.append((char) (c - shift));
        }
        return (T) decrypted.toString();
    }

    /**
     * Checks if all elements in the list are numeric.
     *
     * @return true if all elements are numeric, false otherwise
     */
    public boolean isNumeric() {
        for (T element : this) {
            if (!(element instanceof Number)) {
                return false;
            }
        }
        return true;
    }

    /**
     * - Predicts the next element in the list based on a linear regression model.
     * -
     *
     * @return the predicted next element
     * @throws IllegalArgumentException      if the list has less than two elements
     * @throws UnsupportedOperationException if the list contains non-numeric data
     */
    public double predictNext() {
        if (isNumeric()) {
            if (size < 2) {
                throw new IllegalArgumentException("List must have at least two elements");
            }
            // Get the last two elements
            Node<T> last = getNode(size - 1);
            Node<T> secondLast = getNode(size - 2);
            // Calculate the slope (m) and y-intercept (b) of the linear regression line
            double m = ((Number) last.data).doubleValue() - ((Number) secondLast.data).doubleValue();
            double b = ((Number) last.data).doubleValue() - m * (size - 1);
            // Predict the next value
            return m * (size + 1) + b;
        } else {

            throw new UnsupportedOperationException("PredictNext only supports numeric data");
        }
    }

    /**
     * - Draws a node in the list with its connections.
     * -
     *
     * @param g2d  the graphics context
     * @param node the node to draw
     * @param x    the x-coordinate of the node
     * @param y    the y-coordinate of the node
     */
    private void drawNode(Graphics2D g2d, Node<T> node, int x, int y) {
        // Draw the node and its connections
        g2d.setColor(Color.GREEN);
        g2d.fillOval(x, y, 22, 22);
        g2d.setColor(Color.BLACK);
        g2d.drawString(node.getData().toString(), x + 5, y + 15);
        if (node.getNext() != null) {
            g2d.drawLine(x + 25, y + 10, x + 45, y + 10);
        }
    }

    /**
     * - Visualizes the list as a graph.
     */
    public void visualize() {
        JFrame frame = new JFrame("Doubly-Linked List Visualization");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int x = 50;
                int y = 50;
                Node<T> current = head;
                int nodeCount = 0;
                int maxNodesPerLine = 15;
                while (current != null) {
                    drawNode(g2d, current, x, y);
                    current = current.getNext();
                    x += 50;
                    nodeCount++;
                    if (nodeCount >= maxNodesPerLine) {
                        x = 50;
                        y += 50;
                        nodeCount = 0;
                    }
                }
            }
        };

        int numNodes = this.size();
        int preferredHeight = 50 * numNodes;
        panel.setPreferredSize(new Dimension(820, preferredHeight));

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setPreferredSize(new Dimension(820, preferredHeight));
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * - Preprocesses text data by converting to lowercase, removing punctuation, and removing extra whitespace.
     * -
     *
     * @param textData the text data to preprocess
     * @return the preprocessed text data
     */
    public List<String> preprocessText(List<String> textData) {
        List<String> preprocessedText = new ArrayList<>();
        for (String text : textData) {
            String tokens = text.toLowerCase(); // convert to lowercase
            tokens = tokens.replaceAll("\\p{Punct}", ""); // remove punctuation
            tokens = tokens.replaceAll("\\s+", " "); // remove extra whitespace
            preprocessedText.add(tokens);
        }
        return preprocessedText;
    }

    /**
     * - Reduces the list to a single value using a binary operator.
     * -
     *
     * @param reducer the binary operator to use
     * @return the reduced value
     */
    public T reduce(BinaryOperator<T> reducer) {
        T result = get(0);
        for (int i = 1; i < size(); i++) {
            result = reducer.apply(result, get(i));
        }
        return result;
    }

    /**
     * Returns a list of distinct elements in the list.
     *
     * @return a list of distinct elements
     */
    public List<T> distinct() {
        Set<T> uniqueElements = new HashSet<>();
        List<T> distinctElements = new ArrayList<>();

        for (Node<T> current = head; current != null; current = current.getNext()) {
            if (uniqueElements.add(current.getData())) {
                distinctElements.add(current.getData());
            }
        }

        return distinctElements;
    }

    /**
     * Returns the element before a specified element in the list.
     *
     * @param element the element to find
     * @return the element before the specified element, or null if not found
     */
    public T elementBefore(T element) {
        Node<T> current = head;

        while (current != null && current.getNext() != null) {
            if (current.getNext().getData().equals(element)) {
                return current.getData();
            }
            current = current.getNext();
        }

        return null;
    }

    /**
     * Returns the element after a specified element in the list.
     *
     * @param element the element to find
     * @return the element after the specified element, or null if not found
     */
    public T elementAfter(T element) {
        Node<T> current = head;

        while (current != null) {
            if (current.getData().equals(element)) {
                if (current.getNext() != null) {
                    return current.getNext().getData();
                } else {
                    return null;
                }
            }
            current = current.getNext();
        }

        return null;
    }

    /**
     * Returns the element before a specified index in the list.
     *
     * @param index the index to find
     * @return the element before the specified index, or null if not found
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public T elementBeforeByIndex(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size());
        }
        Node<T> current = head;
        int currentIndex = 0;
        while (current != null && currentIndex < index) {
            current = current.getNext();
            currentIndex++;
        }
        return current.getData();
    }

    /**
     * Returns the element after a specified index in the list.
     *
     * @param index the index to find
     * @return the element after the specified index, or null if not found
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public T elementAfterByIndex(int index) {
        if (index < 0 || index >= size() - 1) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size());
        }
        Node<T> current = head;
        int currentIndex = 0;
        while (current != null && currentIndex <= index) {
            current = current.getNext();
            currentIndex++;
        }
        return current.getData();
    }


    /**
     * A simple guess game method
     */
    private void guessGame() {
        System.out.println("Welcome to the Guess the Next Number game!");
        System.out.println("I'm thinking of a sequence of numbers. Can you guess the next number in the sequence?");

        List<Integer> sequence = new ArrayList<>();
        int formula = (int) (Math.random() * 5); // random formula
        for (int i = 0; i < 5; i++) {
            switch (formula) {
                case 0:
                    sequence.add(i * 2 + 1); // Formula 1: 2x + 1
                    break;
                case 1:
                    sequence.add(i * i + 2); // Formula 2: x^2 + 2
                    break;
                case 2:
                    sequence.add(i * 3 - 1); // Formula 3: 3x - 1
                    break;
                case 3:
                    sequence.add(i * i * i + 1); // Formula 4: x^3 + 1
                    break;
                case 4:
                    sequence.add(i * 2 * 2 + 3); // Formula 5: 2x^2 + 3
                    break;
            }
        }

        // Print the sequence
        System.out.println("Here is the sequence: " + sequence);

        // Ask the user to guess the next number
        System.out.print("Guess the next number: ");
        int guess = Integer.parseInt(System.console().readLine());

        // Check if the guess is correct
        int nextNumber = switch (formula) {
            case 0 -> sequence.getLast() + 2; // Formula 1: 2x + 1
            case 1 -> sequence.getLast() + 3; // Formula 2: x^2 + 2
            case 2 -> sequence.getLast() + 3; // Formula 3: 3x - 1
            case 3 -> sequence.getLast() + 4; // Formula 4: x^3 + 1
            case 4 -> sequence.getLast() + 5;
            default -> 0; // Formula 5: 2x^2 + 3
        };

        if (guess == nextNumber) {
            System.out.println("Congratulations! You guessed correctly!");
        } else {
            System.out.println("Sorry, that's not correct. The next number in the sequence is " + nextNumber);
        }
    }

    /**
     * Sorts the doubly-linked list in-place using the FlexiListSort algorithm.
     * Time complexity of O(n log n) and a space complexity of O(1).
     */
    public void flexiMergeSort() {
        head = FlexiListSort(getHead(), null);
    }

    /**
     * The FlexiListSort algorithm is a recursive sorting algorithm that works specifically for doubly-linked lists.
     * It uses a divide-and-conquer approach to sort the list in-place.
     *
     * @param node the current node to sort
     * @param end  the end node of the list
     * @return the sorted list
     */
    private Node<T> FlexiListSort(Node<T> node, Node<T> end) {
        if (node == null || node == end) {
            return node;
        }

//        Find the middle node of the list using the slow-fast pointer technique.

        Node<T> middle = findMiddleNode(node, end);

        // Split the list into two halves at the middle node.
        Node<T> left = node;
        Node<T> right = middle.next;
        middle.next = null;

        // Recursively sort the left and right halves.

        left = FlexiListSort(left, middle);
        right = FlexiListSort(right, end);

        // Merge the sorted halves using the merge algorithm.
        return merge(left, right);
    }

    /**
     * Finds the middle node of the list using the slow-fast pointer technique.
     *
     * @param node the current node
     * @param end  the end node of the list
     * @return the middle node
     */
    private Node<T> findMiddleNode(Node<T> node, Node<T> end) {
        Node<T> slow = node;
        Node<T> fast = node;
        while (fast != end && fast.next != end) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    /**
     * Merges two sorted lists into a single sorted list.
     *
     * @param left  the left list
     * @param right the right list
     * @return the merged list
     */
    private Node<T> merge(Node<T> left, Node<T> right) {
        Node<T> dummy = new Node<T>(null);
        Node<T> current = dummy;
        while (left != null && right != null) {
            if (left.data.compareTo(right.data) <= 0) {
                current.next = left;
                left = left.next;
            } else {
                current.next = right;
                right = right.next;
            }
            current = current.next;
        }
        if (left != null) {
            current.next = left;
        } else {
            current.next = right;
        }
        return dummy.next;
    }

    /**
     * A private static class representing a node in the list.
     *
     * @param <T> the type of data stored in the node
     */
    static class Node<T extends Comparable<T>> implements Serializable, Cloneable , Comparable<Node<T>>{
        private T data;
        private Node<T> next;
        private Node<T> prev;

        /**
         * Constructs a new node with the given data.
         *
         * @param data the data to store in the node
         */
        public Node(T data) {
            this.data = data;
        }

        /**
         * Returns the data stored in this node.
         *
         * @return the data stored in this node
         */
        public T getData() {
            return data;
        }

        /**
         * Returns the next node in the list.
         *
         * @return the next node in the list
         */
        public Node<T> getNext() {
            return next;
        }

        /**
         * Returns the previous node in the list.
         *
         * @return the previous node in the list
         */
        public Node<T> getPrev() {
            return prev;
        }


        /**
         * Compares this node to another object for equality.
         *
         * @param obj the object to compare to
         * @return true if this node is equal to the object, false otherwise
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Node<?> node = (Node<?>) obj;
            return data.equals(node.data);
        }

        /**
         * Returns a hash code value for this node.
         *
         * @return a hash code value for this node
         */
        @Override
        public int hashCode() {
            return data.hashCode();
        }


        /**
         * Returns a string representation of this node.
         *
         * @return a string representation of this node
         */
        @Override
        public String toString() {
            return String.valueOf(data);
        }

        /**
         * Creates a deep copy of this node.
         *
         * @return a deep copy of this node
         */
        @Override
        public Node<T> clone() {
            try {
                Node<T> clone = (Node<T>) super.clone();
                clone.next = null;
                clone.prev = null;
                return clone;
            } catch (CloneNotSupportedException e) {
                throw new AssertionError();
            }
        }

        /**
         * Compares this object with the specified object for order.  Returns a
         * negative integer, zero, or a positive integer as this object is less
         * than, equal to, or greater than the specified object.
         *
         * <p>The implementor must ensure {@link Integer#signum
         * signum}{@code (x.compareTo(y)) == -signum(y.compareTo(x))} for
         * all {@code x} and {@code y}.  (This implies that {@code
         * x.compareTo(y)} must throw an exception if and only if {@code
         * y.compareTo(x)} throws an exception.)
         *
         * <p>The implementor must also ensure that the relation is transitive:
         * {@code (x.compareTo(y) > 0 && y.compareTo(z) > 0)} implies
         * {@code x.compareTo(z) > 0}.
         *
         * <p>Finally, the implementor must ensure that {@code
         * x.compareTo(y)==0} implies that {@code signum(x.compareTo(z))
         * == signum(y.compareTo(z))}, for all {@code z}.
         *
         * @param other the object to be compared.
         * @return a negative integer, zero, or a positive integer as this object
         * is less than, equal to, or greater than the specified object.
         * @throws NullPointerException if the specified object is null
         * @throws ClassCastException   if the specified object's type prevents it
         *                              from being compared to this object.
         * @apiNote It is strongly recommended, but <i>not</i> strictly required that
         * {@code (x.compareTo(y)==0) == (x.equals(y))}.  Generally speaking, any
         * class that implements the {@code Comparable} interface and violates
         * this condition should clearly indicate this fact.  The recommended
         * language is "Note: this class has a natural ordering that is
         * inconsistent with equals."
         */
        @Override
        public int compareTo(Node<T> other) {
            // Compare the data values using the compareTo method
            return data.compareTo(other.getData());
        }
    }

    /**
     * Class to support bidirectional iteration.
     *
     * @param <T> the type of data stored in the list
     */
    public static class BidirectionalIterator<T extends Comparable<T>> implements Iterator<T> {
        private Node<T> nextNode;
        private Node<T> prevNode;

        /**
         * Constructs a new bidirectional iterator.
         *
         * @param head the head node of the list
         * @param tail the tail node of the list
         */
        public BidirectionalIterator(Node<T> head, Node<T> tail) {
            this.nextNode = head;
            this.prevNode = tail;
        }

        /**
         * Checks if there are more elements in the forward direction.
         *
         * @return true if there are more elements, otherwise false
         */
        @Override
        public boolean hasNext() {
            return nextNode != null;
        }

        /**
         * Retrieves the next element in the forward direction.
         *
         * @return the next element
         * @throws NoSuchElementException if there are no more elements
         */
        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements in the list");
            }
            T data = nextNode.data;
            nextNode = nextNode.next;
            return data;
        }

        /**
         * Checks if there are more elements in the reverse direction.
         *
         * @return true if there are more elements, otherwise false
         */
        public boolean hasPrevious() {
            return prevNode != null;
        }

        /**
         * Retrieves the previous element in the reverse direction.
         *
         * @return the previous element
         * @throws NoSuchElementException if there are no more elements
         */
        public T previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException("No more elements in the list");
            }
            T data = prevNode.data;
            prevNode = prevNode.prev;
            return data;
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

        public FlexiIterator() {
            this(0);
        }

        public FlexiIterator(int index) {
            this.index = index;
        }

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

    /**
     * Sorts the list using the heap sort algorithm.
     *
     * @see #buildHeap()
     * @see #heapifyDown(int, int)
     */
    public void heapSort() {
        buildHeap();
        int end = size();
        for (int i = 0; i < end; i++) {
            swap(0, end - i - 1);
            heapifyDown(0, end - i - 1);
        }
    }

    /**
     * Builds a heap from the list.
     *
     * @see #heapifyUp(int, int)
     */
    private void buildHeap() {
        int end = size();
        for (int i = end / 2 - 1; i >= 0; i--) {
            heapifyUp(i, end);
        }
    }

    /**
     * Heapifies the list upwards from the given start index.
     *
     * @param start the starting index
     * @param end the ending index
     */
    private void heapifyUp(int start, int end) {
        int root = start;
        while (true) {
            int child = 2 * root + 1;
            if (child >= end) {
                break;
            }
            if (child + 1 < end && getNode(child).getData().compareTo(getNode(child + 1).getData()) < 0) {
                child++;
            }
            if (getNode(root).getData().compareTo(getNode(child).getData()) >= 0) {
                break;
            }
            swap(root, child);
            root = child;
        }
    }

    /**
     * Heapifies the list downwards from the given start index.
     *
     * @param start the starting index
     * @param end the ending index
     */
    private void heapifyDown(int start, int end) {
        int root = start;
        while (true) {
            int child = 2 * root + 1;
            if (child >= end) {
                break;
            }
            if (child + 1 < end && getNode(child).getData().compareTo(getNode(child + 1).getData()) < 0) {
                child++;
            }
            if (getNode(root).getData().compareTo(getNode(child).getData()) >= 0) {
                break;
            }
            swap(root, child);
            root = child;
        }
    }

    /**
     * Method that returns double pointers "( "arrow"-"arrow" )" representation of list
     * It also show null at the end , indicating list is end
     * @return doubly-linked list representation of the list
     * @see #printList()
     * @see #toString()
     * @since 0.5
     */
    public String print(){
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            Node<T> current = head;
            while (current != null) {
                sb.append(current.getData());
                current = current.next;
                if (current != null) {
                    sb.append(" <-> ");
                }
            }
            sb.append(" <-> null]");
            return sb.toString();
    }

    /**
     * Option sort method does not work in all cases
     */
    public void timSort() {
        int n = size();
        if (n <= 1) {
            return;
        }
        int runLen = 32;
        for (int i = 0; i < n; i += runLen) {
            insertionSort(i, min((i + runLen - 1), n - 1));
        }
        for (int size = runLen; size < n; size = 2 * size) {
            for (int left = 0; left < n; left += 2 * size) {
                int mid = left + size - 1;
                int right = min((left + 2 * size - 1), n - 1);
                FlexiList<T> leftList = new FlexiList<T>();
                FlexiList<T> rightList = new FlexiList<T>();
                for (int i = left; i <= mid; i++) {
                    leftList.add(get(i));
                }
                for (int i = mid + 1; i <= right; i++) {
                    rightList.add(get(i));
                }
                merge(leftList, rightList);
            }
        }
    }

    /**
     * Sorts a portion of the list using the insertion sort algorithm.
     *
     * @param left the starting index of the portion to sort
     * @param right the ending index of the portion to sort
     */
    private void insertionSort(int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            T temp = get(i);
            int j = i - 1;
            while (j >= left && get(j).compareTo(temp) > 0) {
                set(j + 1, get(j));
                j--;
            }
            set(j + 1, temp);
        }
    }





// to do ensure all these have locks for option thread-safety
    //1. add(int index, T element)
    //2. add(T element)
    //3. addAll(int index, Collection<? extends T> c)
    //4. addAll(Collection<? extends T> c)
    //5. clear()
    //6. clone()
    //7. contains(Object o)
    //8. contains(T element)
    //9. containsAll(Collection<?> c)
    //10. deleteDuplicates()
    //11. dequeue()
    //12. element()
    //13. enqueue(T element)
    //14. forEachRemaining(Consumer<? super T> action)
    //15. get(int index)
    //16. getFirst()
    //17. getLast()
    //18. indexOf(Object o)
    //19. indexOf(T element)
    //20. insertionSort()
    //21. isEmpty()
    //22. iterator()
    //23. lastIndexOf(Object o)
    //24. lastIndexOf(T element)
    //25. listIterator()
    //26. listIterator(int index)
    //27. mergeSort()
    //28. offer(T element)
    //29. offerFirst(T element)
    //30. offerLast(T element)
    //31. peek()
    //32. poll()
    //33. pop()
    //34. push(T element)
    //35. quicksort()
    //36. remove()
    //37. remove(int index)
    //38. remove(Object o)
    //39. removeAll(FlexiList<T> list)
    //40. removeAll(Collection<?> c)
    //41. removeByElement(T element)
    //42. removeByIndex(int index)
    //43. removeIf(Predicate<? super T> predicate)
    //44. removeLast()
    //45. removeRange(int fromIndex, int toIndex)
    //46. replaceAll(T oldVal, T newVal)
    //47. retainAll(FlexiList<T> list)
    //48. retainAll(Collection<?> c)
    //49. reverseList()
    //50. search(T key)
    //51. set(int index, T element)
    //52. size()
    //53. sort(Comparator<? super T> comparator)
    //54. stream()
    //55. subList(int fromIndex, int toIndex)
    //56. swap(int index1, int index2)
    //57. toArray()
    //58. toArray(T1[] a)
    //59. toString()
    //60. trimToSize()
}


// end of FlexiList----------------------------------------------------------------------
// last modified 6.01 pm 6-16-2024



