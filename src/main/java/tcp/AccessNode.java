package tcp;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.HashMap;

public class AccessNode extends TCPAbstractNode {
    private HashMap<Long, TaskAssigner> taskTable;
    private final int numWorkerNodes;


    public AccessNode(int port, int numWorkerNodes) {
        super(port);
        this.numWorkerNodes = numWorkerNodes;
        taskTable = new HashMap<>();
    }

    @Override
    public void run() {
        //noinspection InfiniteLoopStatement
        while (true) try {
            Socket client = ssocket.accept();
            ObjectInputStream in = new ObjectInputStream(client.getInputStream());
            Object obj = in.readObject();

            if (obj instanceof NodeAnswer) {
                NodeAnswer na = (NodeAnswer)obj;
                Long jobID = na.getJobID();
                TaskAssigner ta = taskTable.get(jobID);
                if (ta != null) { ta.addNodeAnswer(na); }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public synchronized void addNewTask(int size, long jobID) {
        TaskAssigner ta = new TaskAssigner(new Task(size), jobID, this);
        taskTable.put(jobID, ta);
        sendNodeRequest(new NodeRequest(jobID, getNodeConnector()));
    }

    private void sendNodeRequest(NodeRequest nr) {
        for (NodeConnector nc : connectionList) {
            RequestHandler rh = new RequestHandler(getNodeConnector(), nc, nr);
            (new Thread(rh)).start();
        }
    }

    public synchronized void sendNodeJob(long jobID) {
        System.out.println("Sending nodejob with id: "+ jobID);
        TaskAssigner ta = taskTable.remove(jobID);
        NodeJob nj = new NodeJob(ta.getTask(), ta.getBestNodeAnswer(), jobID);
        JobHandler jh = new JobHandler(nj);
        (new Thread(jh)).start();
    }

    public int getNumWorkerNodes() {
        return numWorkerNodes;
    }
}
