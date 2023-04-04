package practice.samples;

import practice.PriorityMatrix;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * This example was GPT4 sample.
 * It does not use both levels of priority in the matrix. Uses capacity for both so not
 * demonstrating the full use of the data structure.
 */
class LoadBalancer {
    private final PriorityMatrix<Server> serverMatrix;

    public LoadBalancer() {
        Comparator<Server> serverComparator = Comparator.comparingInt(server -> server.capacity);
        this.serverMatrix = new PriorityMatrix<>(serverComparator);
    }

    public void addServer(Server server) {
        serverMatrix.insert(server, server.capacity);
    }


    public Server getServerWithCapacity() {
        return serverMatrix.getMin();
    }

    public void processTask(BalancerTask task) {
        Server server = getServerWithCapacity();
        if (server == null) {
            System.out.println("No available servers to process the task: " + task.name);
            return;
        }

        System.out.println("Processing task " + task.name + " with priority " + task.priority + " on server " + server.name);
        server.capacity--;

        serverMatrix.remove(server); // Remove the server before re-inserting it
        if (server.capacity > 0) {
            serverMatrix.insert(server, server.capacity);
        }
    }
}

public class LoadBalancerDemo {
    public static void main(String[] args) {
        LoadBalancer loadBalancer = new LoadBalancer();
        loadBalancer.addServer(new Server("Server1", 3));
        loadBalancer.addServer(new Server("Server2", 5));
        loadBalancer.addServer(new Server("Server3", 2));

        List<BalancerTask> tasks = new ArrayList<>();
        tasks.add(new BalancerTask("Task1", 5));
        tasks.add(new BalancerTask("Task2", 3));
        tasks.add(new BalancerTask("Task3", 7));
        tasks.add(new BalancerTask("Task4", 2));
        tasks.add(new BalancerTask("Task5", 4));
        tasks.add(new BalancerTask("Task6", 1));

        for (BalancerTask task : tasks) {
            loadBalancer.processTask(task);
        }
    }
}
