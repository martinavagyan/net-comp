package tcp;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;

public class AccessNode implements Runnable{ // Refactor with TCPNode
    private Hashtable<Long, TaskAssigner> taskTable;
    private ArrayList<NodeConnector> connectionList;
    private ServerSocket ssocket;

    public AccessNode(int port) {
        try {
            this.ssocket = new ServerSocket(port, 10, InetAddress.getLocalHost());
        } catch (IOException e) {
            e.printStackTrace();
        }
        connectionList = new ArrayList<>();
        taskTable = new Hashtable<>();
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
                ta.addNodeAnswer(na);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addNewTask(int size, long jobID) {
        TaskAssigner ta = new TaskAssigner(new Task(size));
        taskTable.put(jobID, ta);
    }

    private void sendNodeJob(long jobID) { // method must be called by TaskAssigner thus -> eventlistener needed
        TaskAssigner ta = taskTable.remove(jobID);
        NodeJob nj = new NodeJob(ta.getTask(), ta.getBestNodeAnswer(), jobID);
        JobHandler jh = new JobHandler(nj);
        (new Thread(jh)).start();
    }
}
