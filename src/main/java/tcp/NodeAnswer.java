package tcp;

import java.io.Serializable;
import java.util.Stack;

public class NodeAnswer implements Serializable {
    private long jobID;
    private long delay;
    private Stack<NodeConnector> traceStack; // path it creates
    private Stack<NodeConnector> backTraceStack; // path to follow


    public NodeAnswer(NodeRequest nr, long delay) {
        this.delay = nr.getDelay() + delay; // delay from request travel and current node
        this.jobID = nr.getJobID();
        traceStack = new Stack<>();
        backTraceStack = nr.getTraceStack();
    }

    public NodeConnector popBackTraceStack() {
        return backTraceStack.pop();
    } // next NodeConnector to follow

    public void pushTraceStack(NodeConnector nc) {traceStack.push(nc);}

    public Stack<NodeConnector> getTraceStack() {
        return (Stack<NodeConnector>)traceStack.clone();
    }

}
