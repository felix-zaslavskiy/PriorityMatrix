package practice.samples;

import practice.PriorityMatrix;

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

    @Override
    public String toString() {
        return "Resource{" +
                "name='" + name + '\'' +
                ", availableResourcesRAM=" + availableResourcesRAM +
                ", cpuClassAvailability=" + cpuClassAvailability +
                '}';
    }
}
class TaskScheduler {
    private final PriorityMatrix<Resource> resourceMatrix;

    public TaskScheduler() {
        // Higher RAM is better
        Comparator<Resource> resourceComparator = Comparator.comparingInt(resource -> resource.availableResourcesRAM);
        this.resourceMatrix = new PriorityMatrix<>(resourceComparator.reversed());
    }

    public void addResource(Resource resource) {
        resourceMatrix.insert(resource, resource.cpuClassAvailability);
    }

    public void removeResource(Resource resource) {
        resourceMatrix.remove(resource);
    }

    public Resource getResourceForTask() {
        // Lowest CPU class is better.
        return resourceMatrix.getMin();
    }

    public void processTask(Task task) {
        // For now the task CPU priority is not considered.
        Resource resource = getResourceForTask();
        if (resource == null) {
            System.out.println("No available resources to process the task: " + task.name);
            return;
        }

        System.out.println("Processing task " + task.name + " with cpuPriority: " + task.cpuPriority + ", with RAM requirement: " + task.resourceRAMRequirement + ", using resource " + resource.name);
        // Decrease resource RAM usage.
        resource.availableResourcesRAM -= task.resourceRAMRequirement;

        // Reinsert the resource into matrix so both cpu and RAM are placed property in matrix.
        resourceMatrix.remove(resource);
        if (resource.availableResourcesRAM > 0) {
            resourceMatrix.insert(resource, resource.cpuClassAvailability);
        }
    }
    public String toString(){
        return resourceMatrix.toString();
    }
}

public class TaskSchedulerDemo {
    public static void main(String[] args) {
        TaskScheduler scheduler = new TaskScheduler();
        // RAM in MB, CPU classes 1, 2, 3
        scheduler.addResource(new Resource("Node1", 1, 10));
        scheduler.addResource(new Resource("Node2", 1, 6));
        scheduler.addResource(new Resource("Node3", 2, 10));
        scheduler.addResource(new Resource("Node4", 2, 6));

        // CPU class considered first, lower is better
        // Available RAM is considered second. Uses the Resource with the highest RAM first.

        List<Task> tasks = List.of(
                // CPU priority 1 tasks are used first, nodes with the highest RAM used first

                // node 1 (has 5 left)
                new Task("Task1", 1, 5),

                // node 2 (0 ram so removed)
                new Task("Task2", 1, 6),

                // Node 1 (0 ram so removed)
                new Task("Task3", 1, 5),

                // node 3 (has 5 ram left)
                new Task("Task4", 1, 5),

                // node 4 has 3 ram left)
                new Task("Task5", 1, 3),
                new Task("Task6", 1, 2)
        );

        for (Task task : tasks) {
            scheduler.processTask(task);
            System.out.println(scheduler);
        }


    }
}
