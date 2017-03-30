package mq;

import tcp.NodeJob;

/**
 * Created by jurgen on 30-3-17.
 */
public interface CallBack {
    void runTask(NodeJob nj);
}
