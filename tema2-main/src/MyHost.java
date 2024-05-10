/* Implement this class. */

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.concurrent.PriorityBlockingQueue;

public class MyHost extends Host {

    //comparator used to sort the tasks by priority
    class Task_comparator implements Comparator<Task> {
        public int compare(Task t1, Task t2)
        {
         return t2.getPriority() - t1.getPriority();
        }
    }


    //queue for tasks, sorted by priority
    private PriorityBlockingQueue<Task> tasks = new PriorityBlockingQueue<>(50,new Task_comparator());
    private volatile int shutdown = 0; //flag that signals the hosts to shutdown

    public Task CurrentTask = null; //the task that is running


    @Override
    public void run() {
        while (shutdown == 0) {
            if (!tasks.isEmpty()) {
                CurrentTask = tasks.poll();
                while(CurrentTask.getLeft() >= 1000) { //if the task still has work to do

                     Task new_task = tasks.peek(); //check the queue for a new task

                    //if the new task has a higher priority than the current task, preempt it
                    if (CurrentTask.isPreemptible() && new_task != null && new_task.getPriority() > CurrentTask.getPriority()) {
                        tasks.offer(CurrentTask); //push the current task back into the queue
                        CurrentTask = new_task;  //the new task becomes the running task
                        tasks.remove(new_task); //remove the new task from the queue
                    }
                    try {
                        sleep(1000); //slep for a second
                        CurrentTask.setLeft(CurrentTask.getLeft() - 1000); //decrement the work left
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                //if the task has done its work finish it
                CurrentTask.finish();
                CurrentTask = null;
            }

            }
        }



    @Override
    public void addTask(Task task) {
        tasks.add(task); //add the task in the queue
    }

    @Override
    public int getQueueSize() {
        int running = 0;
        if(CurrentTask != null){
            running = 1;
        }
        return tasks.size() + running; //add 1 if there is a task that is currently running
    }

    @Override
    public long getWorkLeft() {
        long workLeft = 0; //the total work left in the queue
        Iterator<Task> t = tasks.iterator();
        while(t.hasNext()) { //iterate through all the tasks and add the work left from each
            workLeft += t.next().getLeft();
        }
        if(CurrentTask != null) { //if there is a running task
            return CurrentTask.getLeft() + workLeft; //add it s work left to the total work left
        } else return workLeft;
    }

    @Override
    public void shutdown() {
        shutdown = 1; //signal the host to shutdown
    }
}
