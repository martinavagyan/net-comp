package tcp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


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
        tm = new TaskManager(getNodeConnector());
        (new Thread(tm)).start();

        connectionList = new ArrayList<>();
    }

    @Override
    public void run() {
        //noinspection InfiniteLoopStatement
        while (true) try {
            System.out.println("Patiently waiting...");
            System.out.println("IP: " + getIP() + "port: "+ getPort());
            Socket client = ssocket.accept();
            ObjectInputStream in = new ObjectInputStream(client.getInputStream());
            Object obj = in.readObject();

            if (obj instanceof NodeRequest) {
                NodeRequest nr = (NodeRequest) obj;

                NodeAnswer na = new NodeAnswer(nr, tm.getDelay());
                AnswerHandler ah = new AnswerHandler(getNodeConnector(), na);
                (new Thread(ah)).start(); // send back the node answer to request (i.e. the delay)

                connectionList.stream().filter(nc -> !nr.getTraceStack().contains(nc)).forEach(nc -> {
                    RequestHandler rh = new RequestHandler(getNodeConnector(), nc, nr);
                    (new Thread(rh)).start();
                });
            } else if (obj instanceof NodeAnswer) { // propagate the answer further
                NodeAnswer na = (NodeAnswer) obj;
                AnswerHandler ah = new AnswerHandler(getNodeConnector(), na);
                (new Thread(ah)).start(); // send the answer further
            } else if (obj instanceof NodeJob) {
                NodeJob nj = (NodeJob) obj;
                if (nj.validate(getNodeConnector())) { // job is for us
                    tm.addNodeJob(nj); // post to webservice when finished
                } else { // send it further
                    JobHandler jh = new JobHandler(nj);
                    (new Thread(jh)).start();
                }
            }
        } catch (IOException | ClassNotFoundException e) {
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
        return new NodeConnector(getIP(), getPort(), 0);
    }

    public void addNodeConnector(NodeConnector nc) { connectionList.add(nc); }
}
