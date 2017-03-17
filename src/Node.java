import java.util.ArrayList;
import static spark.Spark.*;

public class Node {

    public Node() {

    }

    public static void main(String[] args)
    {

        get("/hello", (req, res) -> "Hello World!");
    }
}
