package tcp;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class AccessNode implements Runnable{ // Refactor with TCPNode
    private ArrayList<NodeConnector> connectionList;
    private ServerSocket ssocket;

    public AccessNode(int port) {
        try {
            this.ssocket = new ServerSocket(port, 10, InetAddress.getLocalHost());
        } catch (IOException e) {
            e.printStackTrace();
        }
        connectionList = new ArrayList<>();
    }

    @Override
    public void run() {
        //noinspection InfiniteLoopStatement
        while (true) try {
            Socket client = ssocket.accept();
            ObjectInputStream in = new ObjectInputStream(client.getInputStream());
            Object obj = in.readObject();

            if (obj instanceof NodeAnswer) {
                // A hash table that stores tasks based on jobID,
                // when a new NodeAnswer comes in, we look in the hash table at the appropriate jobID and replace the
                // NodeAnswer there if this one has a smaller delay
                // the  object in the HashTable that stores the task and the best NodeAnswer, notifies the AccessNode
                // when it is satisfied with a specific NodeAnswer - for example after it has received a specific amount
                // of them.
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void addNewTask(int size, long jobID) {
        Task task = new Task(size);
        // add it to hash table at the jobID key
    }

    private void sendNodeJob(long jobID) {
        // extract the task from the hash table, built the NodeJob and send it using a JobHandler
    }
}
