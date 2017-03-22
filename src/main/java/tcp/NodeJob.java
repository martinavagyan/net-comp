package tcp;

import java.io.Serializable;
import java.util.Stack;


public class NodeJob implements Serializable {
    private long jobID;
    private Task task;
    private Stack<NodeConnector> backTraceStack; // path to follow


    public NodeJob (Task task, NodeAnswer na, long jobID) {
        this.task = task;
        this.jobID = jobID;
        this.backTraceStack = na.getTraceStack();
    }

    public boolean validate(NodeConnector nc) {
        return backTraceStack.pop().equals(nc) && backTraceStack.isEmpty();
    }

    public Task getTask() {
        return task;
    }

    public NodeConnector popBackTraceStack() {
        return backTraceStack.pop();
    }

}

