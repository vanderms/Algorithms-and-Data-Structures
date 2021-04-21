import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {

        Reader in = new Reader();
        int n = in.nextInt();
        int m = in.nextInt();
        Node[] nodes = new Node[n + 1];
        for(int i = 1; i <= n; i++) { nodes[i] = new Node(i); }

        for(int i = 0; i < m; i++){
            int from = in.nextInt();
            int to = in.nextInt();
            nodes[from].out.add(to);
            nodes[to].in++;
        }

        Queue<Node> queue = new LinkedList<>();
        queue.add(nodes[1]);
        nodes[1].distance = 1;

        for(int i = 2; i <= n; i++){
            if(nodes[i].in == 0){
                queue.add(nodes[i]);
            }
        }

        Node end = null;

        while(!queue.isEmpty() && end == null){
            Node node = queue.poll();
            for(int edge : node.out){
                Node next = nodes[edge];
                if(next.distance < node.distance + 1){
                    next.distance = node.distance + 1;
                    next.previous = node;
                }
                next.in--;
                if(next.in == 0){
                    if(next.id == n){
                        end = next;
                        break;
                    }
                    queue.add(next);
                }
            }
        }

        if(end == null || end.distance < 0){
            System.out.print("IMPOSSIBLE");
            return;
        }

        Stack<Integer> sequence = new Stack<>();
        StringBuilder answer = new StringBuilder(n * 32);

        while(end != null){
            sequence.add(end.id);
            end = end.previous;
        }

        answer.append(sequence.size());
        answer.append("\n");

        while(!sequence.isEmpty()){
            answer.append(sequence.pop());
            answer.append(" ");
        }

        answer.deleteCharAt(answer.length() - 1);
        System.out.print(answer);
    }
}


class Node {
    int id;
    int in;
    protected List<Integer> out;
    int distance;
    Node previous;
    public Node(int id){
        this.id = id;
        this.in = 0;
        this.out = new LinkedList<>();
        this.distance = Integer.MIN_VALUE;
        this.previous = null;
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