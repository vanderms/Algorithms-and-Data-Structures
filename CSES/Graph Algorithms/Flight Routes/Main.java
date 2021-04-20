import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class Main {

    public static int n;

    public static void main(String[] args) throws IOException {

        Reader in = new Reader();

        n = in.nextInt();
        int m = in.nextInt();
        Node.k = in.nextInt();

        Node[] nodes = new Node[n + 1];
        for(int i = 1; i <= n; i++){ nodes[i] = new Node(i); }

        for(int i = 0; i < m; i++){
            int from = in.nextInt();
            int to = in.nextInt();
            int price = in.nextInt();
            nodes[from].adj.add(new Edge(to, price));
        }

        search(nodes);

        long[] sequences = new long[Node.k];

        for(int i = Node.k - 1; i >= 0; i--){
            sequences[i] = nodes[n].distances.poll();
        }

        StringBuilder answer = new StringBuilder(Node.k * 32);
        for(int i = 0; i < Node.k; i++){
            answer.append(sequences[i]);
            answer.append(" ");
        }
        answer.deleteCharAt(answer.length() - 1);
        System.out.print(answer);
    }

    public static void search(Node[] nodes){

        PriorityQueue<Edge> queue = new PriorityQueue<>();
        queue.add(new Edge(1, 0));

        while(!queue.isEmpty()){
            Edge current = queue.poll();
            List<Edge> adjacents = nodes[current.to].adj;
            for(var edge : adjacents){
                int to = edge.to;
                long distance = current.distance + edge.distance;
                if(nodes[edge.to].addDistance(distance)){
                    queue.add(new Edge(to, distance));
                }
            }
        }
    }
}

class Node {
    protected int id;
    protected PriorityQueue<Long> distances;
    protected List<Edge> adj;
    public static int k = 0;
    public static long max = Long.MAX_VALUE;

    public Node(int id){
        this.id = id;
        distances = new PriorityQueue<>(k, Node::compare);
        adj = new LinkedList<>();
    }

    public static int compare(Long x, Long y){
        if(x > y){
            return -1;
        } else if(x < y) {
            return 1;
        }
        return 0;
    }

    protected boolean addDistance(long distance){
        if(distance >= Node.max){
            return false;
        }

        if(distances.size() < k){
            distances.add(distance);
            if(id == Main.n && distances.size() == k){
                Node.max = distances.peek();
            }
            return true;
        }
        if(distance < distances.peek()){
            distances.poll();
            distances.add(distance);
            if(id == Main.n && distances.size() == k){
                Node.max = distances.peek();
            }
            return true;
        }
        return false;
    }
}

class Edge implements Comparable<Edge>{
    protected int to;
    protected long distance;

    public Edge(int to, long distance){
        this.to = to;
        this.distance = distance;
    }

    @Override
    public int compareTo(Edge o){
        return Long.compare(this.distance, o.distance);
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