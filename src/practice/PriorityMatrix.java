package practice;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

/**
 * PriorityMatrix is a data structure that organizes elements based on their priorities.
 * It combines a TreeMap and PriorityQueues to allow efficient access to elements
 * with minimum and maximum priorities. The PriorityMatrix also supports insertion and removal of elements.
 *
 * @param <T> the type of elements stored in the PriorityMatrix
 */
public class PriorityMatrix<T> {
    private final TreeMap<Integer, PriorityQueue<T>> matrix;
    private final Map<T, Integer> priorities;
    private final Comparator<T> comparator;

    /**
     * Constructs a new PriorityMatrix with the specified comparator to order elements within the same priority level.
     *
     * @param comparator the comparator that will be used to order elements within the same priority level
     */
    public PriorityMatrix(Comparator<T> comparator) {
        this.matrix = new TreeMap<>();
        this.priorities = new HashMap<>();
        this.comparator = comparator;
    }

    /**
     * Inserts the specified element with the specified priority into the PriorityMatrix.
     *
     * @param element  the element to be inserted
     * @param priority the priority associated with the element
     * @throws IllegalArgumentException if the element already exists in the PriorityMatrix
     */
    public void insert(T element, int priority) {
        if (priorities.containsKey(element)) {
            throw new IllegalArgumentException("Element already exists in the Priority Matrix");
        }

        matrix.putIfAbsent(priority, new PriorityQueue<>(comparator));
        matrix.get(priority).add(element);
        priorities.put(element, priority);
    }

    /**
     * Removes the specified element from the PriorityMatrix.
     *
     * @param element the element to be removed
     * @return true if the element was removed, false if the element was not found
     */
    public boolean remove(T element) {
        Integer priority = priorities.remove(element);
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

        Integer minPriority = matrix.firstKey();
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

        Integer minPriority = matrix.firstKey();
        PriorityQueue<T> elements = matrix.get(minPriority);
        T minElement = elements.poll();

        if (elements.isEmpty()) {
            matrix.remove(minPriority);
        }

        priorities.remove(minElement);
        return minElement;
    }

    /**
     * Retrieves the element with the maximum priority, but does not remove it from the PriorityMatrix.
     *
     * @return the element with the maximum priority, or null if the PriorityMatrix is empty
     */    public T getMax() {
        if (matrix.isEmpty()) {
            return null;
        }

        Integer maxPriority = matrix.lastKey();
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

        Integer maxPriority = matrix.lastKey();
        PriorityQueue<T> elements = matrix.get(maxPriority);
        T maxElement = elements.poll();

        if (elements.isEmpty()) {
            matrix.remove(maxPriority);
        }

        priorities.remove(maxElement);
        return maxElement;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PriorityMatrix{");

        for (Map.Entry<Integer, PriorityQueue<T>> entry : matrix.entrySet()) {
            Integer priority = entry.getKey();
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
