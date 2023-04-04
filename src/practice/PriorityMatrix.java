package practice;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

/**
 * PriorityMatrix is a data structure that organizes elements based on their priorities.
 * It combines a TreeMap and PriorityQueues to allow efficient access to elements
 * with minimum and maximum priorities. The PriorityMatrix also supports insertion, removal,
 * and updating priorities of elements.
 *
 * <p>
 * Example:
 * Consider a PriorityMatrix of Resources, where the rows are CPU priority classes and columns are resources sorted by available RAM.
 * <p>
 * 1: {R1(12GB), R2(8GB)}
 * <p>
 * 2: {R3(16GB), R4(10GB)}
 * <p>
 * One may think of priority type P as the rows of the matrix and the secondary
 * ordering passed in as a Comparator as the column order of items in the matrix.
 *
 * <p>
 * In this example, getMin() would return R1 and getMax() would return R3.
 *
 * @param <T> the type of elements stored in the PriorityMatrix
 * @param <P> the type of priorities associated with the elements in the PriorityMatrix, which must extend Comparable<P>
 */
public class PriorityMatrix<T, P extends Comparable<P>> implements Iterable<T> {
    private final TreeMap<P, PriorityQueue<T>> matrix;
    private final Map<T, P> elementToPriorityMap;
    private final Comparator<T> comparator;

    /**
     * Constructs a new PriorityMatrix with the specified comparator to order elements within the same priority level.
     *
     * @param comparator the comparator that will be used to order elements within the same priority level
     */
    public PriorityMatrix(Comparator<T> comparator) {
        this.matrix = new TreeMap<>();
        this.elementToPriorityMap = new HashMap<>();
        this.comparator = comparator;
    }

    /**
     * Inserts the specified element with the specified priority into the PriorityMatrix.
     *
     * @param element  the element to be inserted
     * @param priority the priority associated with the element
     * @throws IllegalArgumentException if the element already exists in the PriorityMatrix
     */
    public void insert(T element, P priority) {
        if (elementToPriorityMap.containsKey(element)) {
            throw new IllegalArgumentException("Element already exists in the Priority Matrix");
        }

        matrix.putIfAbsent(priority, new PriorityQueue<>(comparator));
        matrix.get(priority).add(element);
        elementToPriorityMap.put(element, priority);
    }

    /**
     * Updates the priority of the specified element in the PriorityMatrix.
     *
     * @param element The element to update
     * @param newPriority The new priority value of the element
     */
    public void updatePriority(T element, P newPriority) {
        remove(element);
        insert(element, newPriority);
    }

    /**
     * Checks if the PriorityMatrix has any elements.
     *
     * @return true if the PriorityMatrix is empty, false otherwise
     */
    public boolean isEmpty() {
        return matrix.isEmpty();
    }

    /**
     * Returns the number of elements in the PriorityMatrix.
     *
     * @return The total number of elements in the PriorityMatrix
     */
    public int size() {
        return elementToPriorityMap.size();
    }

    /**
     * Removes the specified element from the PriorityMatrix.
     *
     * @param element the element to be removed
     * @return true if the element was removed, false if the element was not found
     */
    public boolean remove(T element) {
        P priority = elementToPriorityMap.remove(element);
        if (priority == null) {
            return false;
        }

        PriorityQueue<T> elements = matrix.get(priority);
        boolean removed = elements.remove(element);

        if (elements.isEmpty()) {
            matrix.remove(priority);
        }

        return removed;
    }

    /**
     * Retrieves the element with the minimum priority, but does not remove it from the PriorityMatrix.
     *
     * @return the element with the minimum priority, or null if the PriorityMatrix is empty
     */
    public T getMin() {
        if (matrix.isEmpty()) {
            return null;
        }

        P minPriority = matrix.firstKey();
        PriorityQueue<T> elements = matrix.get(minPriority);
        return elements.peek();
    }

    /**
     * Retrieves and removes the element with the minimum priority from the PriorityMatrix.
     *
     * @return the element with the minimum priority, or null if the PriorityMatrix is empty
     */
    public T extractMin() {
        if (matrix.isEmpty()) {
            return null;
        }

        P minPriority = matrix.firstKey();
        PriorityQueue<T> elements = matrix.get(minPriority);
        T minElement = elements.poll();

        if (elements.isEmpty()) {
            matrix.remove(minPriority);
        }

        elementToPriorityMap.remove(minElement);
        return minElement;
    }

    /**
     * Retrieves the element with the maximum priority, but does not remove it from the PriorityMatrix.
     *
     * @return the element with the maximum priority, or null if the PriorityMatrix is empty
     */
    public T getMax() {
        if (matrix.isEmpty()) {
            return null;
        }

        P maxPriority = matrix.lastKey();
        PriorityQueue<T> elements = matrix.get(maxPriority);
        return elements.peek();
    }


    /**
     * Retrieves and removes the element with the maximum priority from the PriorityMatrix.
     *
     * @return the element with the maximum priority, or null if the PriorityMatrix is empty
     */
    public T extractMax() {
        if (matrix.isEmpty()) {
            return null;
        }

        P maxPriority = matrix.lastKey();
        PriorityQueue<T> elements = matrix.get(maxPriority);
        T maxElement = elements.poll();

        if (elements.isEmpty()) {
            matrix.remove(maxPriority);
        }

        elementToPriorityMap.remove(maxElement);
        return maxElement;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private final Iterator<Map.Entry<P, PriorityQueue<T>>> outerIterator = matrix.entrySet().iterator();
            private Iterator<T> innerIterator = null;

            @Override
            public boolean hasNext() {
                while ((innerIterator == null || !innerIterator.hasNext()) && outerIterator.hasNext()) {
                    innerIterator = outerIterator.next().getValue().iterator();
                }
                return innerIterator != null && innerIterator.hasNext();
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new IllegalStateException("No more elements to iterate.");
                }
                return innerIterator.next();
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PriorityMatrix{");

        for (Map.Entry<P, PriorityQueue<T>> entry: matrix.entrySet()) {
            P priority = entry.getKey();
            PriorityQueue<T> elements = entry.getValue();

            sb.append("\nPriority ").append(priority).append(": ");
            boolean isFirst = true;
            for (T element : elements) {
                if (!isFirst) {
                    sb.append(", ");
                }
                sb.append(element);
                isFirst = false;
            }
        }

        sb.append("\n}");
        return sb.toString();
    }
}
