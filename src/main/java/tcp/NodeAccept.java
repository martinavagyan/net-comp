package tcp;

import java.io.Serializable;
import java.util.Stack;

public class NodeAccept implements Serializable {
    private Stack<NodeConnector> traceStack;
    private Stack<NodeConnector> backTraceStack; // where the request was accepted


    public NodeAccept(NodeRequest nr) {
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
