# FlexiList.
FlexiList is a Java data structure that combines the benefits of arrays and linked lists. Like an array, it allows for efficient access to elements by index. Like a linked list, it allows for efficient insertion and deletion of elements at any position in the list.


## Benefits Over Arrays and ArrayList
->Efficient Insertion and Deletion: FlexiList can insert or delete nodes at any position in the list in O(1) time, whereas arrays require shifting all elements after the insertion or deletion point.

->Dynamic Size: FlexiList can grow or shrink dynamically as elements are added or removed, whereas arrays have a fixed size.

->Good Memory Locality: FlexiList nodes are stored in a contiguous block of memory, making it more cache-friendly than arrays.

->Faster Insertion and Deletion: FlexiList can insert or delete nodes at any position in the list in O(1) time, whereas ArrayList requires shifting all elements after the insertion or deletion point.

->Better Memory Efficiency: FlexiList uses less memory than ArrayList because it doesn't need to store a separate array of indices.

## Class: FlexiList<T extends Comparable<T>>

## Constructors
public FlexiList(int initialSize): Constructs a new FlexiList instance with the provided initial size.

public FlexiList(): Constructs a new FlexiList instance.

## Methods
public void add(T element): Adds an element to the list.

public void add(int index, T element): Inserts the specified element at the specified index in the list.

public T get(int index): Gets the element at the specified index.

public T getFirst(): Returns the first element in constant O(1) time.

public T getLast(): Returns the last element in constant O(1) time.

public int getInitialSize(): Returns the initial size of the list.

public int indexOf(T element): Returns the index of the specified element.

public boolean isEmpty(): Returns whether the list is empty.

public boolean contains(T element): Returns whether the list contains the specified element.

public int lastIndexOf(T element): Returns the index of the last occurrence of the specified element in the list, or -1 if the element is not found.

public void mergeSort(): Sorts the list using the merge sort algorithm.

public void remove(int index): Removes the element at the specified index.

public void clear(): Clears the list.

public void reverseList(): Reverses the order of the elements in the list.

public void set(int index, T element): Sets the element at the specified index.

public int size(): Returns the size of the list.

public void sort(Comparator<T> comparator): Sorts the elements in the list according to the specified comparator.

public FlexiList<T> subList(int fromIndex, int toIndex): Returns a new list containing the elements from the specified fromIndex to the specified toIndex.

public void swap(int index1, int index2): Method to swap two elements in the list.

public T[] toArray(): Returns an array containing all elements in the list.

public void trimToSize(): Trims the capacity of the list to its current size.

public Iterator<T> iterator(): Returns an iterator over elements of type T.

public FlexiList<T> clone(): Method provided to clone the list.

## Contributions 
Everyone welcome for contributions  

## Contact 
hasnatrasool163@gmail.com

## Author
## Muhammad Hasnat Rasool

## Version
0.1

## MIT License all rights reserved @2024.
