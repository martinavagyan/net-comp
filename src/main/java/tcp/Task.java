package tcp;


import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;


public class Task {
    private long id;
    private int size;
    private ArrayList<Integer> contentList;

    public Task (int size, long id) {
        this.id = id;
        this.size = size;
        this.contentList = new ArrayList<>(this.size);
        populate();
    }

    private void populate() {
        for (int i = 0; i < contentList.size(); ++i) {
             addRandom();
        }
    }

    private void addRandom() {
        int randomNum = ThreadLocalRandom.current().nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
        contentList.add(randomNum);
    }

    public void execute() {
        Collections.sort(this.contentList);
    }
}
