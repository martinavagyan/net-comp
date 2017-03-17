package tcp;

import java.io.Serializable;
import java.util.Stack;


public class NodeJob implements Serializable{
    private Task task;
    private Stack<NodeConnector> traceStack;
    private Stack<NodeConnector> backTraceStack;


    public NodeJob (Task task, Stack<NodeConnector> traceStack) {
        this.task = task;
        this.traceStack =  traceStack;
        this.backTraceStack = new Stack<>();
    }

    public boolean validate(NodeConnector nc) {
        return traceStack.pop().equals(nc) && traceStack.isEmpty();
    }

    public Task getTask() {
        return task;
    }

    public void pushbackTraceStack(NodeConnector nc) {
        backTraceStack.push(nc);
    }

    public NodeConnector popTraceStack() {
        return traceStack.pop();
    }

    public Stack<NodeConnector> getBackTraceStack() {
        return backTraceStack;
    }
}

