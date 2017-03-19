package tcp;


import java.io.Serializable;
import java.util.Stack;

public class NodeAnswer implements Serializable {
    private Task task;
    private Stack<NodeConnector> backTraceStack; // to the initiator of the job request


    public NodeAnswer (NodeJob nj) {
        this.task = nj.getTask();
        this.backTraceStack = nj.getTraceStack();
    }

    public NodeConnector popBackTraceStack() {
        return backTraceStack.pop();
    }
}
