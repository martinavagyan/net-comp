package tcp;


import java.util.Objects;
import java.util.PriorityQueue;

public class TaskAssigner {
    private Task task;
    private long jobID;
    private AccessNode an;
    private PriorityQueue<NodeAnswer> answerQueue;


    public TaskAssigner(Task task, long jobID, AccessNode an) {
        this.task = task;
        this.jobID = jobID;

        this.an = an;
        answerQueue = new PriorityQueue<>((o1, o2) -> o1.compareTo(o2));
    }

    public boolean addNodeAnswer(NodeAnswer na) {
        if (answerQueue.offer(na)) {
            if (answerQueue.size() >= an.getNumWorkerNodes()) {
                an.sendNodeJob(answerQueue.peek().getJobID());
            }
            return true;
        }
        return false;
    }

    public Task getTask() { return this.task; }

    public NodeAnswer getBestNodeAnswer() {
        return answerQueue.poll();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskAssigner that = (TaskAssigner) o;
        return jobID == that.jobID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobID);
    }
}
