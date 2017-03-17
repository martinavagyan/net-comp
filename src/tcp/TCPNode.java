package tcp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;


public class TCPNode implements Runnable {
    private TaskManager tm;
    private ArrayList<NodeConnector> connectionList;
    private ServerSocket ssocket;

    public TCPNode (int port) {
        try {
            this.ssocket = new ServerSocket(port, 10, InetAddress.getLocalHost());
        } catch (IOException e) {
            e.printStackTrace();
        }
        tm = new TaskManager();
        (new Thread(tm)).start();
        connectionList = new ArrayList<>();
    }

    @Override
    public void run() {
        //noinspection InfiniteLoopStatement
        while (true) try {
            Socket client = ssocket.accept();
            ObjectInputStream in = new ObjectInputStream(client.getInputStream());
            Object obj = in.readObject();

            if (obj instanceof NodeRequest) {
                NodeRequest nr = (NodeRequest) obj;
                if (tm.available()) { // if task manager is available, accept
                    NodeAccept na = new NodeAccept(nr, getNodeConnector());
                    AcceptHandler arh = new AcceptHandler(na);
                    (new Thread(arh)).start(); // send back the accept request
                } else {
                    int randomNode = ThreadLocalRandom.current().nextInt(0, connectionList.size());
                    NodeConnector nc = connectionList.get(randomNode);
                    RequestHandler rh = new RequestHandler(nc, nr);
                    (new Thread(rh)).start();
                }
            }
            if (obj instanceof NodeAccept) { // propagate the accept further
                NodeAccept na = (NodeAccept) obj;
                AcceptHandler ah = new AcceptHandler(na);
                (new Thread(ah)).start(); // send back the accept request
            }
            if (obj instanceof NodeJob) {
                NodeJob nj = (NodeJob) obj;
                if (nj.validate(getNodeConnector())) { // job is for us
                    tm.addNodeJob(nj); // send back NodeAnswer when job is finished
                } else {
                    JobHandler jh = new JobHandler(nj);
                    (new Thread(jh)).start();
                }
            }
            if (obj instanceof NodeAnswer) {
                NodeAnswer na = (NodeAnswer) obj;
                AnswerHandler ah = new AnswerHandler(na);
                (new Thread(ah)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String getIP() {
        return ssocket.getInetAddress().getHostAddress();
    }

    private Integer getPort() {
        return ssocket.getLocalPort();
    }

    private NodeConnector getNodeConnector() {
        return new NodeConnector(getIP(), getPort());
    }
}
