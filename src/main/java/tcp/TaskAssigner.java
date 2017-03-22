package tcp;


import java.util.Comparator;
import java.util.PriorityQueue;

public class TaskAssigner {
    Task task;
    PriorityQueue<NodeAnswer> answerQueue;


    public TaskAssigner(Task task) {
        this.task = task;
        answerQueue = new PriorityQueue<NodeAnswer>(new Comparator<NodeAnswer>() {
            @Override
            public int compare(NodeAnswer o1, NodeAnswer o2) {
                return o1.compareTo(o2);
            }
        });
    }

    public boolean addNodeAnswer(NodeAnswer na) {
        return answerQueue.offer(na);
    }

    public Task getTask() { return this.task; }

    public NodeAnswer getBestNodeAnswer() {
        return answerQueue.poll();
    }
}
