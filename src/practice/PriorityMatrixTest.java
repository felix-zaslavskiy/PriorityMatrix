package practice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
}
