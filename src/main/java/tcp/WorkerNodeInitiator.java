package tcp;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class WorkerNodeInitiator {

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(args[0]));

        int port = Integer.parseInt(in.readLine());
        int numConnections = Integer.parseInt(in.readLine());
        TCPNode tcpNode = new TCPNode(port);
        for (int i=0; i < numConnections; ++i) {
            String line = in.readLine();
            String[] connector = line.split(" ");
            tcpNode.addNodeConnector(new NodeConnector(connector[0], Integer.parseInt(connector[1]), Integer.parseInt(connector[2])));
        }
        (new Thread(tcpNode)).start();
    }
}
