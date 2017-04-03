package mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP;
import java.io.*;
import javax.security.auth.callback.Callback;
import tcp.NodeJob;

/**
 * Created by jurgen on 24-3-17.
 */
public class MessageQueue {

    private final static String QUEUE_NAME = "netcomp";
    private final static Boolean DURABLE = false;

    private Channel channel;

    public MessageQueue() {

    }

    public boolean offer(NodeJob nj) {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");

            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, DURABLE, false, false, null);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutput out = null;
            try {
                out = new ObjectOutputStream(bos);
                out.writeObject(nj);
                out.flush();
                byte[] bytes = bos.toByteArray();
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
            return false;
        }
    }

    public void listen(CallBack cb) {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");

            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

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
                      in = new ObjectInputStream(bis);
                      nj = (NodeJob) in.readObject();
                    } catch (ClassNotFoundException e) {
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
                        cb.runTask(nj);
                        channel.basicAck(envelope.getDeliveryTag(), false);
                    }

                }
            };
            boolean autoAck = false;
            channel.basicConsume(QUEUE_NAME, autoAck, consumer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int size() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            AMQP.Queue.DeclareOk dok = channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            return dok.getMessageCount();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
