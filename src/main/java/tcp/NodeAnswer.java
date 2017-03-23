package tcp;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Stack;

public class NodeAnswer implements Serializable, Comparable<NodeAnswer> {
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

    public void pushTraceStack(NodeConnector nc) { traceStack.push(nc); }

    public Stack<NodeConnector> getTraceStack() { return traceStack; }

    public long getDelay() {
        return delay;
    }

    public long getJobID() {
        return jobID;
    }

    @Override
    public int compareTo(NodeAnswer that) {
        if (this.getDelay() > that.getDelay()) return 1;
        else if (this.getDelay() == that.getDelay()) return 0;
        return -1;
    }
}
