package tcp;

import java.io.Serializable;
import java.util.Stack;

/** Class used for representation of a packet that contains the job for a worker node. */
public class NodeJob extends AbstractNodePacket implements Serializable {
    private Task task;


    public NodeJob (Task task, NodeAnswer na, long jobID) {
        super(jobID, null, na.getOrigin());
        this.task = task;
    }

    public Task getTask() {
        return task;
    }

    public boolean validate(NodeConnector nc) { return nc.equals(getDestination());  }
}

