package practice.samples;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import practice.PriorityMatrix;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

class PriorityMatrixTest {
    private PriorityMatrix<Resource> resourceMatrix;
    private Resource resource1;
    private Resource resource2;
    private Resource resource3;
    private Resource resource4;

    @BeforeEach
    void setUp() {
        Comparator<Resource> resourceComparator = Comparator.comparingInt(resource -> resource.availableResourcesRAM);
        resourceMatrix = new PriorityMatrix<>(resourceComparator.reversed());

        resource1 = new Resource("Node1", 1, 10);
        resource2 = new Resource("Node2", 1, 6);
        resource3 = new Resource("Node3", 2, 10);
        resource4 = new Resource("Node4", 2, 6);
    }

    @Test
    void insert() {
        resourceMatrix.insert(resource1, 1);
        assertEquals(resource1, resourceMatrix.getMin());

        resourceMatrix.insert(resource2, 1);
        assertEquals(resource1, resourceMatrix.getMin());

        resourceMatrix.insert(resource3, 2);
        assertEquals(resource1, resourceMatrix.getMin());

        resourceMatrix.insert(resource4, 2);
        assertEquals(resource1, resourceMatrix.getMin());
    }

    @Test
    void remove() {
        resourceMatrix.insert(resource1, 1);
        resourceMatrix.insert(resource2, 1);
        resourceMatrix.insert(resource3, 2);
        resourceMatrix.insert(resource4, 2);

        assertTrue(resourceMatrix.remove(resource1));
        assertEquals(resource2, resourceMatrix.getMin());

        assertFalse(resourceMatrix.remove(resource1));
    }

    @Test
    void getMin() {
        resourceMatrix.insert(resource1, 1);
        resourceMatrix.insert(resource2, 1);
        resourceMatrix.insert(resource3, 2);
        resourceMatrix.insert(resource4, 2);

        assertEquals(resource1, resourceMatrix.getMin());
    }

    @Test
    void extractMin() {
        resourceMatrix.insert(resource1, 1);
        resourceMatrix.insert(resource2, 1);
        resourceMatrix.insert(resource3, 2);
        resourceMatrix.insert(resource4, 2);

        assertEquals(resource1, resourceMatrix.extractMin());
        assertEquals(resource2, resourceMatrix.getMin());
    }

    @Test
    void getMax() {
        resourceMatrix.insert(resource1, 1);
        resourceMatrix.insert(resource2, 1);
        resourceMatrix.insert(resource3, 2);
        resourceMatrix.insert(resource4, 2);

        assertEquals(resource3, resourceMatrix.getMax());
    }

    @Test
    void extractMax() {
        resourceMatrix.insert(resource1, 1);
        resourceMatrix.insert(resource2, 1);
        resourceMatrix.insert(resource3, 2);
        resourceMatrix.insert(resource4, 2);

        assertEquals(resource3, resourceMatrix.extractMax());
        assertEquals(resource4, resourceMatrix.getMax());
    }

    @Test
    void isEmpty() {
        assertTrue(resourceMatrix.isEmpty());

        resourceMatrix.insert(resource1, 1);
        assertFalse(resourceMatrix.isEmpty());

        resourceMatrix.remove(resource1);
        assertTrue(resourceMatrix.isEmpty());
    }

    @Test
    void size() {
        assertEquals(0, resourceMatrix.size());

        resourceMatrix.insert(resource1, 1);
        assertEquals(1, resourceMatrix.size());

        resourceMatrix.insert(resource2, 1);
        assertEquals(2, resourceMatrix.size());

        resourceMatrix.remove(resource1);
        assertEquals(1, resourceMatrix.size());

        resourceMatrix.remove(resource2);
        assertEquals(0, resourceMatrix.size());
    }

    @Test
    void updatePriority() {
        resourceMatrix.insert(resource1, 1);
        resourceMatrix.insert(resource2, 1);
        resourceMatrix.insert(resource3, 2);
        resourceMatrix.insert(resource4, 2);

        resourceMatrix.updatePriority(resource1, 3);

        assertEquals(resource2, resourceMatrix.getMin());
        assertEquals(resource1, resourceMatrix.getMax());

        resource4.availableResourcesRAM=10;
        resourceMatrix.updatePriority(resource4, 1);
        assertEquals(resource4, resourceMatrix.getMin()); // Corrected this line
    }

    @Test
    void testRandomOperations() {
        assertTrue(resourceMatrix.isEmpty());
        assertEquals(0, resourceMatrix.size());

        resourceMatrix.insert(resource1, 1);
        resourceMatrix.insert(resource2, 1);
        resourceMatrix.insert(resource3, 2);

        assertFalse(resourceMatrix.isEmpty());
        assertEquals(3, resourceMatrix.size());
        assertEquals(resource1, resourceMatrix.getMin());
        assertEquals(resource3, resourceMatrix.getMax());

        resourceMatrix.updatePriority(resource2, 3);

        resource4.availableResourcesRAM=12;
        resourceMatrix.insert(resource4, 1);

        assertEquals(resource4, resourceMatrix.getMin());
        assertEquals(resource2, resourceMatrix.getMax());

        resourceMatrix.remove(resource4);
        assertEquals(resource1, resourceMatrix.getMin()); // Corrected this line
        assertEquals(3, resourceMatrix.size());

        resourceMatrix.extractMin();
        assertEquals(resource3, resourceMatrix.getMin());
        assertEquals(2, resourceMatrix.size());

        resourceMatrix.extractMax();
        assertEquals(1, resourceMatrix.size());
        assertEquals(resource3, resourceMatrix.getMin());
    }

    @Test
    void testVariousOperations() {
        assertTrue(resourceMatrix.isEmpty());

        resourceMatrix.insert(resource1, 1);
        resourceMatrix.insert(resource2, 1);
        resourceMatrix.insert(resource3, 2);

        assertEquals(resource1, resourceMatrix.getMin());

        resourceMatrix.updatePriority(resource1, 3);
        assertEquals(resource2, resourceMatrix.getMin());
        assertEquals(resource1, resourceMatrix.getMax());

        resourceMatrix.remove(resource2);
        assertEquals(resource3, resourceMatrix.getMin());
        assertEquals(2, resourceMatrix.size());

        resourceMatrix.insert(resource4, 1);
        assertEquals(resource4, resourceMatrix.getMin());
        assertEquals(resource1, resourceMatrix.getMax());

        resourceMatrix.extractMax();
        assertEquals(resource3, resourceMatrix.getMax());

        resourceMatrix.remove(resource4);
        assertEquals(resource3, resourceMatrix.getMin());
    }

}
