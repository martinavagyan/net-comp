package tcp;

import java.io.Serializable;
import java.util.Stack;


public class NodeRequest implements Serializable{
    private long jobID;
    private long delay;
    private Stack<NodeConnector> traceStack;


    public NodeRequest (long jobID) {
        this.jobID = jobID;
        this.delay = 0;
        traceStack = new Stack<>();
    }

    public void pushTraceStack(NodeConnector nc) {
        traceStack.push(nc);
    }

    public Stack<NodeConnector> getTraceStack() {
        return (Stack<NodeConnector>)traceStack.clone();
    }

    public long getDelay() { return this.delay; }

    public long getJobID() { return this.jobID; }

    public void addDelay(long delay) {
        this.delay += delay;
    }
}
