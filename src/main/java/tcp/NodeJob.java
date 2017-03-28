package tcp;

import java.io.Serializable;
import java.util.Stack;


public class NodeJob implements Serializable {
    private long jobID;
    private Task task;
    private NodeConnector destination;


    public NodeJob (Task task, NodeAnswer na, long jobID) {
        this.task = task;
        this.jobID = jobID;
        this.destination = na.getOrigin();
    }

    public boolean validate(NodeConnector nc) { return destination.equals(nc); }

    public Task getTask() {
        return task;
    }

    public long getJobID() { return jobID; }

    public NodeConnector getDestination() { return destination; }
}

