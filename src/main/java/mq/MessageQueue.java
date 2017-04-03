package mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP;
import java.io.*;
import tcp.NodeJob;

/**
 * Created by jurgen on 24-3-17.
 */
public class MessageQueue {

    private final static String QUEUE_NAME = "netcomp";
    private final static String HOST = "localhost";

    public boolean offer(NodeJob nj) {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(HOST);

            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutput out = null;
            try {
                // convert NodeJob to byte array
                out = new ObjectOutputStream(bos);
                out.writeObject(nj);
                out.flush();
                byte[] bytes = bos.toByteArray();

                // publish task
                channel.basicPublish("", QUEUE_NAME, null, bytes);
                System.out.println(" [x] Sent Job '" + nj.getJobID() + "'");
            } finally {
              try {
                bos.close();
              } catch (IOException ex) {
                // ignore close exception
              }
            }
            channel.close();
            connection.close();
            return true;
        } catch (Exception e) {
            // An error has occured during the message queue definition
            e.printStackTrace();
            return false;
        }
    }

    public void listen(CallBack cb) {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(HOST);

            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            System.out.println(" [*] Waiting for queue messages.");

            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(
                        String consumerTag,
                        Envelope envelope,
                        AMQP.BasicProperties properties,
                        byte[] body
                ) throws IOException
                {
                    ByteArrayInputStream bis = new ByteArrayInputStream(body);
                    ObjectInput in = null;
                    NodeJob nj = null;
                    try {
                        // read byte array in a NodeJob instance
                        in = new ObjectInputStream(bis);
                        nj = (NodeJob) in.readObject();
                    } catch (ClassNotFoundException e) {
                        // Class NodeJob is not found
                        e.printStackTrace();
                    } finally {
                      try {
                        if (in != null) {
                          in.close();
                        }
                      } catch (IOException ex) {
                        // ignore close exception
                      }
                    }
                    if (nj != null) {
                        System.out.println(" [x] Received Job '" + nj.getJobID() + "'");
                        // make callback to the TaskManager
                        cb.runTask(nj);
                        // acknowledge the task
                        channel.basicAck(envelope.getDeliveryTag(), false);
                    }

                }
            };
            // Only remove a task from the queue if it is completed without errors
            boolean autoAck = false;
            // Listen for incoming tasks
            channel.basicConsume(QUEUE_NAME, autoAck, consumer);
        } catch (Exception e) {
            // An error has occured during initialiaztion of the message queue
            // or while reading a message
            e.printStackTrace();
        }
    }

    public int size() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);

        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            // Get queue length estimate
            AMQP.Queue.DeclareOk dok = channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            return dok.getMessageCount();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
