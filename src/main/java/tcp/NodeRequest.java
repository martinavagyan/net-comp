package tcp;

import java.io.Serializable;
import java.util.Stack;


public class NodeRequest extends AbstractNodePacket implements Serializable{
    private long delay;
    private Stack<NodeConnector> traceStack; // path it creates


    public NodeRequest (long jobID, NodeConnector origin) {
        super(jobID, origin, null);
        this.delay = 0;
        traceStack = new Stack<>();
    }

    public void pushTraceStack(NodeConnector nc) {
        traceStack.push(nc);
    }

    public Stack<NodeConnector> getTraceStack() { return traceStack; }

    public long getDelay() { return this.delay; }

    public void addDelay(long delay) {
        this.delay += delay;
    }
}
