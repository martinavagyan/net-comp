package tcp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ProxyNodeInitiator {

    public static ProxyNode initProxyNode(String filename) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(filename));

        String[] numConnectLine = in.readLine().split(" ");
        int numConnections = Integer.parseInt(numConnectLine[0]);

        ProxyNode proxyNode = new ProxyNode();

        for (int i=0; i < numConnections; ++i) {
            String line = in.readLine();
            String[] connector = line.split(" ");
            proxyNode.addNodeConnector(new NodeConnector(connector[0], Integer.parseInt(connector[1]), Integer.parseInt(connector[2])));
        }
        return proxyNode;
    }

}
