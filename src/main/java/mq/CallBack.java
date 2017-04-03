package mq;

import tcp.NodeJob;

/**
 * Created by jurgen on 30-3-17.
 */
public interface CallBack {
    // Callback used by the MessageQueue listener
    void runTask(NodeJob nj);
}
