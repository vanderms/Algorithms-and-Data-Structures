import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Stack;

public class Main {

    static int MOD = 1_000_000_007;

    public static void main(String[] args) throws IOException {



        Reader in = new Reader();
        int n = in.nextInt();
        int m = in.nextInt();
        Node[] nodes = new Node[n + 1];
        for(int i = 1; i <= n; i++) { nodes[i] = new Node(i); }

        for(int i = 0; i < m; i++){
            int from = in.nextInt();
            int to = in.nextInt();
            int price = in.nextInt();
            nodes[from].adjs.add(new Edge(from, to, price));
        }

        query(nodes);

        String answer = (nodes[n].price + " " + nodes[n].routes +
                " " + nodes[n].min + " " + nodes[n].max
        );

        System.out.print(answer);
    }


    public static void query(Node[] nodes){

        PriorityQueue<Edge> queue = new PriorityQueue<>();
        queue.add(new Edge(0, 1, 0));
        nodes[0] = new Node(0);
        nodes[0].routes = 1;
        nodes[0].min = -1;
        nodes[0].max = -1;

        while(!queue.isEmpty()){
            Edge edge = queue.poll();
            Node previous = nodes[edge.from];
            Node node = nodes[edge.to];
            if(edge.price > node.price){ continue; }

            node.price = edge.price;
            node.routes += previous.routes;
            if(node.routes > MOD){
                node.routes -= MOD;
            }
            node.min = Math.min(node.min, previous.min + 1);
            node.max = Math.max(node.max, previous.max + 1);

            while(!node.adjs.isEmpty()){
                Edge next = node.adjs.pop();
                next.price += node.price;
                queue.add(next);
            }
        }
    }
}


class Node {
    int id;
    Stack<Edge> adjs;
    long price;
    int routes;
    int min;
    int max;

    public Node(int id){
        this.id = id;
        this.adjs = new Stack<>();
        this.price = Long.MAX_VALUE;
        this.routes = 0;
        this.min = Integer.MAX_VALUE;
        this.max = 0;
    }
}

class Edge implements Comparable<Edge>{

    final int from;
    final int to;
    long price;

    public Edge(int from, int to, long price){
        this.from = from;
        this.to = to;
        this.price = price;
    }

    @Override
    public int compareTo(Edge o){
        return Long.compare(this.price, o.price);
    }
}



// The Reader Class was designed by Rishabh Mahrsee.
// See: Article: Fast IO in Java in Competitive Programming
// Website: GeeksForGeeks
// Url: https://www.geeksforgeeks.org/fast-io-in-java-in-competitive-programming/
class Reader {
    final private int BUFFER_SIZE = 1 << 16;
    private DataInputStream din;
    private byte[] buffer;
    private int bufferPointer, bytesRead;

    public Reader()
    {
        din = new DataInputStream(System.in);
        buffer = new byte[BUFFER_SIZE];
        bufferPointer = bytesRead = 0;
    }

    public Reader(String file_name) throws IOException
    {
        din = new DataInputStream(
                new FileInputStream(file_name));
        buffer = new byte[BUFFER_SIZE];
        bufferPointer = bytesRead = 0;
    }

    public String readLine() throws IOException
    {
        byte[] buf = new byte[64]; // line length
        int cnt = 0, c;
        while ((c = read()) != -1) {
            if (c == '\n') {
                if (cnt != 0) {
                    break;
                }
                else {
                    continue;
                }
            }
            buf[cnt++] = (byte)c;
        }
        return new String(buf, 0, cnt);
    }

    public int nextInt() throws IOException
    {
        int ret = 0;
        byte c = read();
        while (c <= ' ') {
            c = read();
        }
        boolean neg = (c == '-');
        if (neg)
            c = read();
        do {
            ret = ret * 10 + c - '0';
        } while ((c = read()) >= '0' && c <= '9');

        if (neg)
            return -ret;
        return ret;
    }

    public long nextLong() throws IOException
    {
        long ret = 0;
        byte c = read();
        while (c <= ' ')
            c = read();
        boolean neg = (c == '-');
        if (neg)
            c = read();
        do {
            ret = ret * 10 + c - '0';
        } while ((c = read()) >= '0' && c <= '9');
        if (neg)
            return -ret;
        return ret;
    }

    public double nextDouble() throws IOException
    {
        double ret = 0, div = 1;
        byte c = read();
        while (c <= ' ')
            c = read();
        boolean neg = (c == '-');
        if (neg)
            c = read();

        do {
            ret = ret * 10 + c - '0';
        } while ((c = read()) >= '0' && c <= '9');

        if (c == '.') {
            while ((c = read()) >= '0' && c <= '9') {
                ret += (c - '0') / (div *= 10);
            }
        }

        if (neg)
            return -ret;
        return ret;
    }

    private void fillBuffer() throws IOException
    {
        bytesRead = din.read(buffer, bufferPointer = 0,
                BUFFER_SIZE);
        if (bytesRead == -1)
            buffer[0] = -1;
    }

    private byte read() throws IOException
    {
        if (bufferPointer == bytesRead)
            fillBuffer();
        return buffer[bufferPointer++];
    }

    public void close() throws IOException
    {
        if (din == null)
            return;
        din.close();
    }
}