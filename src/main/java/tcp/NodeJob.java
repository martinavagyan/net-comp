package tcp;

import java.io.Serializable;
import java.util.Stack;


public class NodeJob implements Serializable {
    private Task task;
    private Stack<NodeConnector> traceStack;
    private Stack<NodeConnector> backTraceStack;


    public NodeJob (Task task, NodeAccept na) {
        this.task = task;
        this.traceStack =  new Stack<>();
        this.backTraceStack = na.getTraceStack();
    }

    public boolean validate(NodeConnector nc) {
        return backTraceStack.pop().equals(nc) && backTraceStack.isEmpty();
    }

    public Task getTask() {
        return task;
    }

    public void pushTraceStack(NodeConnector nc) {
        traceStack.push(nc);
    }

    public NodeConnector popBackTraceStack() {
        return backTraceStack.pop();
    }

    public Stack<NodeConnector> getTraceStack() {
        return (Stack<NodeConnector>) traceStack.clone();
    }
}

