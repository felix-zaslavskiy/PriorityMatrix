package test;

import java.util.Comparator;
import java.util.List;

class Task {
    String name;
    int cpuPriority;
    int resourceRAMRequirement;

    public Task(String name, int cpuPriority, int resourceRAMRequirement) {
        this.name = name;
        this.cpuPriority = cpuPriority;
        this.resourceRAMRequirement = resourceRAMRequirement;
    }
}

class Resource {
    String name;

    // More is better.
    int availableResourcesRAM;

    // Higher is better.
    int cpuClassAvailability;

    public Resource(String name, int cpuAvailability, int availableResourcesRAM) {
        this.name = name;
        this.availableResourcesRAM = availableResourcesRAM;
        this.cpuClassAvailability =cpuAvailability;
    }
}
class TaskScheduler {
    private final PriorityMatrix<Resource> resourceMatrix;

    public TaskScheduler() {
        // Higher cpu
        Comparator<Resource> resourceComparator = Comparator.comparingInt(resource -> resource.cpuClassAvailability);
        this.resourceMatrix = new PriorityMatrix<>(resourceComparator);
    }

    public void addResource(Resource resource) {
        resourceMatrix.insert(resource, resource.availableResourcesRAM);
    }

    public void removeResource(Resource resource) {
        resourceMatrix.remove(resource);
    }

    public Resource getResourceForTask(Task task) {
        return resourceMatrix.getMin();
    }

    public void processTask(Task task) {
        Resource resource = getResourceForTask(task);
        if (resource == null) {
            System.out.println("No available resources to process the task: " + task.name);
            return;
        }

        System.out.println("Processing task " + task.name + " with priority " + task.cpuPriority + " using resource " + resource.name);
        resource.availableResourcesRAM -= task.resourceRAMRequirement;

        resourceMatrix.remove(resource);
        if (resource.availableResourcesRAM > 0) {
            resourceMatrix.insert(resource, resource.availableResourcesRAM);
        }
    }
}

public class TaskSchedulerDemo {
    public static void main(String[] args) {
        TaskScheduler scheduler = new TaskScheduler();
        // RAM in MB, CPU classes 1, 2, 3
        scheduler.addResource(new Resource("Resource1", 1, 10));
        scheduler.addResource(new Resource("Resource2", 1, 6));
        scheduler.addResource(new Resource("Resource3", 2, 10));

        // Available RAM is considered first. Higher is better.
        // CPU class is treated second higher is better.

        List<Task> tasks = List.of(
                new Task("Task1", 5, 2),
                new Task("Task2", 3, 4),
                new Task("Task3", 7, 6),
                new Task("Task4", 2, 1),
                new Task("Task5", 4, 3),
                new Task("Task6", 1, 2)
        );

        for (Task task : tasks) {
            scheduler.processTask(task);
        }
    }
}
