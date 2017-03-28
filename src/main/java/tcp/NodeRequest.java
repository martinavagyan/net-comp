package tcp;

import java.io.Serializable;
import java.util.Stack;


public class NodeRequest implements Serializable{
    private long jobID;
    private long delay;
    private NodeConnector origin;
    private Stack<NodeConnector> traceStack; // path it creates


    public NodeRequest (long jobID, NodeConnector origin) {
        this.jobID = jobID;
        this.delay = 0;
        this.origin = origin;
        traceStack = new Stack<>();
    }

    public void pushTraceStack(NodeConnector nc) {
        traceStack.push(nc);
    }

    public Stack<NodeConnector> getTraceStack() { return traceStack; }

    public NodeConnector getOrigin() { return this.origin;}

    public long getDelay() { return this.delay; }

    public long getJobID() { return this.jobID; }

    public void addDelay(long delay) {
        this.delay += delay;
    }
}
