package tcp;

import java.io.Serializable;
import java.util.Stack;


public class NodeRequest implements Serializable{
    private long jobID;
    private Stack<NodeConnector> traceStack;


    public NodeRequest () {
        traceStack = new Stack<>();
    }

    public void pushTraceStack(NodeConnector nc) {
        traceStack.push(nc);
    }

    public Stack<NodeConnector> getTraceStack() {
        return (Stack<NodeConnector>)traceStack.clone();
    }

}
