package tcp;


import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;


public class Task {
    private ArrayList<Integer> contentList;
    private int size;

    public Task (int size) {
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
