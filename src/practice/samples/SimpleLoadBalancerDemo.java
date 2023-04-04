package practice.samples;

import java.util.ArrayList;
import java.util.List;

class BalancerTask {
    String name;
    int priority;

    public BalancerTask(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }
}


/**
 * Shows how load balancer works without PriorityMatrix
 */
class SimpleLoadBalancer {
    private final List<Server> servers;

    public SimpleLoadBalancer() {
        this.servers = new ArrayList<>();
    }

    public void addServer(Server server) {
        servers.add(server);
    }


    public Server getServerWithCapacity() {
        Server maxCapacityServer = null;
        int maxCapacity = -1;

        for (Server server : servers) {
            if (server.capacity > maxCapacity) {
                maxCapacity = server.capacity;
                maxCapacityServer = server;
            }
        }

        return maxCapacityServer;
    }

    public void processTask(BalancerTask task) {
        Server server = getServerWithCapacity();
        if (server == null) {
            System.out.println("No available servers to process the task: " + task.name);
            return;
        }

        System.out.println("Processing task " + task.name + " with priority " + task.priority + " on server " + server.name);
        server.capacity--;
    }

}

public class SimpleLoadBalancerDemo {
    public static void main(String[] args) {
        // Server are initialized as static values and are decremented
        // when ever a task is performed on a server.
        //
        // When a processes test.Task is called the server with the highest
        // capacity is looked up and executes the task. The capacity is decremented after
        // task is completed.
        //
        // The processTask has runtime of N (servers) because of search for highest capacity server.
        SimpleLoadBalancer loadBalancer = new SimpleLoadBalancer();
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

        System.out.println(loadBalancer);
    }
}
