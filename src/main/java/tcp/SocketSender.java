package tcp;


import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class SocketSender {

    public static void send(NodeConnector nc, Object obj) {
        try {
            Socket s = new Socket(InetAddress.getByName(nc.getIp()), nc.getPort());
            ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
            out.writeObject(obj);
            out.flush();
            out.close();
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
