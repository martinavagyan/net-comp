package tcp;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AccessNodeInitiator {

    public static void main(String[] args) throws IOException {
        AccessNode wn = initAccessNode(args[0]);
        (new Thread(wn)).start();
    }

    public static AccessNode initAccessNode(String filename) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(filename));

        String[] portLine = in.readLine().split(" ");
        int port = Integer.parseInt(portLine[0]);

        String[] numConnectLine = in.readLine().split(" ");
        int numConnections = Integer.parseInt(numConnectLine[0]);

        String[] numWorkerNodesLine = in.readLine().split(" ");
        int numWorkerNodes = Integer.parseInt(numWorkerNodesLine[0]);

        String[] rmiLine = in.readLine().split(" ");
        AccessNode accessNode = new AccessNode(port, numWorkerNodes, rmiLine[0], Integer.parseInt(rmiLine[1]));

        for (int i=0; i < numConnections; ++i) {
            String line = in.readLine();
            String[] connector = line.split(" ");
            accessNode.addNodeConnector(new NodeConnector(connector[0], Integer.parseInt(connector[1]), Integer.parseInt(connector[2])));
        }
        return accessNode;
    }
}
