package test;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class PriorityMatrix<T> {
    private  final TreeMap<Integer, PriorityQueue<T>> matrix;
    private final Map<T, Integer> priorities;
    private final Comparator<T> comparator;

    public PriorityMatrix(Comparator<T> comparator) {
        this.matrix = new TreeMap<>();
        this.priorities = new HashMap<>();
        this.comparator = comparator;
    }

    public void insert(T element, int priority) {
        if (priorities.containsKey(element)) {
            throw new IllegalArgumentException("Element already exists in the Priority Matrix");
        }

        matrix.putIfAbsent(priority, new PriorityQueue<>(comparator));
        matrix.get(priority).add(element);
        priorities.put(element, priority);
    }

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
     * Get the Item with minimum priority. Does not remove item.
     * @return
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
     * Find items with minimum priority and remove it form matrix
     * @return
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

    public T getMax() {
        if (matrix.isEmpty()) {
            return null;
        }

        Integer maxPriority = matrix.lastKey();
        PriorityQueue<T> elements = matrix.get(maxPriority);
        return elements.peek();
    }

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
}
