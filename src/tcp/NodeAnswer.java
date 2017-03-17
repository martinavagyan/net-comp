package tcp;


import java.io.Serializable;
import java.util.Stack;

public class NodeAnswer implements Serializable {
    private Task task;
    private Stack<NodeConnector> backTraceStack; // where the request was accepted


    public NodeAnswer (NodeJob nj) {
        this.task = nj.getTask();
        this.backTraceStack = nj.getBackTraceStack();
    }

    public NodeConnector popNodeConnector() {
        return backTraceStack.pop();
    }
}
