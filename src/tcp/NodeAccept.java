package tcp;

import java.io.Serializable;
import java.util.Stack;

public class NodeAccept implements Serializable {
    private Stack<NodeConnector> traceStack;
    private Stack<NodeConnector> backTraceStack; // where the request was accepted


    public NodeAccept(NodeRequest nr, NodeConnector nc) {
        traceStack = nr.getTraceStack();
        backTraceStack.push(nc);
    }

    public NodeConnector popNodeConnector() {
        return traceStack.pop();
    }

    public Stack<NodeConnector> getBackTraceStack () {
        return backTraceStack;
    }
}
