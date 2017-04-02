package tcp;


import java.util.ArrayList;
import java.util.List;
import mq.CallBack;
import mq.MessageQueue;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

/** Class that handles the execution of NodeJobs and updates the web service upon task completion. */
public class TaskManager implements Runnable, CallBack {
    private MessageQueue mq;
    private NodeConnector nc; // own node connector, used for identification when logging
    private String host;
    private static final long SYSTEM_DELAY_CONSTANT = 100;

    public TaskManager (NodeConnector nc, String host) {
        this.host = host;
        this.nc = nc;
        mq = new MessageQueue();
    }

    public synchronized boolean addNodeJob(NodeJob nj) {
        return this.mq.offer(nj);
    }

    @Override
    public void run() {
        mq.listen(this);
    }

    public void runTask(NodeJob nj) {
        System.out.println("Running task with ID: " + nj.getJobID());
        nj.getTask().execute();

        // update webservice that job is done
        System.out.println("Finished job with ID: " + nj.getJobID());

        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(host + "api/status/update/" + nj.getJobID());

        List<NameValuePair> params = new ArrayList<NameValuePair>(1);
        params.add(new BasicNameValuePair("status", "Finished"));
        try {
            httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = entity.getContent().toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public long getDelay() {
        return mq.size()*SYSTEM_DELAY_CONSTANT;
    }
}
