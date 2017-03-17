package tcp;

import java.io.Serializable;
import java.util.Stack;


public class NodeRequest implements Serializable{
    private Stack<NodeConnector> traceStack;


    public NodeRequest (NodeConnector nc) {
        traceStack = new Stack<>();
        traceStack.push(nc); // origin of request.
    }

    public void pushNodeConnector(NodeConnector nc) {
        traceStack.push(nc);
    }

    public Stack<NodeConnector> getTraceStack() {
        return traceStack;
    }

}
