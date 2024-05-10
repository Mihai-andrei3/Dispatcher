/* Implement this class. */

import java.sql.SQLOutput;
import java.util.List;

public class MyDispatcher extends Dispatcher {

    public MyDispatcher(SchedulingAlgorithm algorithm, List<Host> hosts) {
        super(algorithm, hosts);
    }

    int index = 0; //index of the host for the Round Robin policy

    @Override
    public synchronized void addTask(Task task) {

        if (this.algorithm == SchedulingAlgorithm.ROUND_ROBIN) { //Round Robin policy
            int n = hosts.size();
            int host = (index) % n; //pick the host for the task with the formula from the assignment
            index++; //increment the index
            hosts.get(host).addTask(task); //add the task to the host

        } else if (algorithm == SchedulingAlgorithm.SHORTEST_QUEUE) { //Shortest Queue policy
            int shortest = hosts.get(0).getQueueSize(); //get the size of the first host's queue
            int shortestIndex = 0; //the index of the host with the shortest queue
            for (int i = 0; i < hosts.size(); i++) {
                if (hosts.get(i).getQueueSize() < shortest) {
                    //if the current host has a shorter queue, update the shortest queue and it s index
                    shortest = hosts.get(i).getQueueSize();
                    shortestIndex = i;
                }
            }
            hosts.get(shortestIndex).addTask(task);

        } else if (algorithm == SchedulingAlgorithm.SIZE_INTERVAL_TASK_ASSIGNMENT) { //SITA policy
            if (task.getType() == TaskType.SHORT) {
                hosts.get(0).addTask(task); //add the task to the host for short tasks
            }
            if (task.getType() == TaskType.MEDIUM) {
                hosts.get(1).addTask(task); //add the task to the host for medium tasks
            }
            if (task.getType() == TaskType.LONG) {
                hosts.get(2).addTask(task); //add the task to host for long tasks
            }

        } else if (algorithm == SchedulingAlgorithm.LEAST_WORK_LEFT) {//LWL policy
            long minim_work = hosts.get(0).getWorkLeft(); //store the minimun work left in this variable
            int index = 0; //the index of the host with the minimun work left
            for (int i = 0; i < hosts.size(); i++) {
                if (hosts.get(i).getWorkLeft() < minim_work) {
                    //if the current host has a smaller ammount of  work left, update the shortest queue and it s index
                    minim_work = hosts.get(i).getQueueSize();
                    index = i;
                }

            }
            hosts.get(index).addTask(task);
        }
    }
}
