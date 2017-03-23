package tcp;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class AccessNode implements Runnable{ // Refactor with TCPNode
    private HashMap<Long, TaskAssigner> taskTable;
    private ArrayList<NodeConnector> connectionList;
    private ServerSocket ssocket;
    private final int size;

    public int getSize() {
        return size;
    }

    public AccessNode(int port, int size) {
        try {
            this.ssocket = new ServerSocket(port, 10, InetAddress.getLocalHost());
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.size = size;
        connectionList = new ArrayList<>();
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
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public synchronized void addNewTask(int size, long jobID) {
        TaskAssigner ta = new TaskAssigner(new Task(size), jobID, this);
        taskTable.put(jobID, ta);
        sendNodeRequest(new NodeRequest(jobID));
    }

    private void sendNodeRequest(NodeRequest nr) {
        for (NodeConnector nc : connectionList) {
            RequestHandler rh = new RequestHandler(getNodeConnector(), nc, nr);
            (new Thread(rh)).start();
        }
    }

    public synchronized void sendNodeJob(long jobID) { // method must be called by TaskAssigner thus
        TaskAssigner ta = taskTable.remove(jobID);
        NodeJob nj = new NodeJob(ta.getTask(), ta.getBestNodeAnswer(), jobID);
        JobHandler jh = new JobHandler(nj);
        (new Thread(jh)).start();
    }

    private String getIP() {
        return ssocket.getInetAddress().getHostAddress();
    }

    private Integer getPort() {
        return ssocket.getLocalPort();
    }

    private NodeConnector getNodeConnector() {
        return new NodeConnector(getIP(), getPort(), 0);
    }

    public void addNodeConnector(NodeConnector nc) { connectionList.add(nc); }
}
