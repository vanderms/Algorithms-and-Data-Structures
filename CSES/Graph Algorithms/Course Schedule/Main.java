import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Main {

    public static void main(String[] args) throws IOException {

        Reader in = new Reader();
        int n = in.nextInt();
        int m = in.nextInt();
        Node[] nodes = new Node[n + 1];
        for(int i = 1; i <= n; i++) { nodes[i] = new Node(); }

        for(int i = 0; i < m; i++){
            int requirement = in.nextInt();
            int course = in.nextInt();
            nodes[requirement].adj.add(new Edge(requirement, course));
        }

        List<Integer> sequence = new ArrayList<>();
        boolean isPossible = true;

        for(int i = 1; i <= n; i++){
            if(nodes[i].status == Node.UNVISITED && !dfs(nodes, sequence, i)){
                isPossible = false;
                break;
            }
        }

        if(!isPossible){
            System.out.print("IMPOSSIBLE");
            return;
        }
        Collections.reverse(sequence);

        StringBuilder answer = new StringBuilder(n * 32);
        for(int i = 0; i < n; i++){
            answer.append(sequence.get(i));
            answer.append(" ");
        }

        answer.deleteCharAt(answer.length() - 1);
        System.out.print(answer);


    }

    static boolean dfs(Node[] nodes, List<Integer> sequence, int idx){

        Stack<Edge> stack = new Stack<>();
        Stack<Integer> temp = new Stack();

        stack.add(new Edge(0, idx));
        temp.add(0);

        while(!stack.empty()){
            Edge cEdge = stack.pop();

            while(cEdge.from != temp.peek()){
                int node = temp.pop();
                nodes[node].status = Node.PROCESSED;
                sequence.add(node);
            }

            Node cNode = nodes[cEdge.to];

            if(cNode.status != Node.UNVISITED){
                continue;
            }

            cNode.status = Node.VISITING;
            temp.add(cEdge.to);

            while(!cNode.adj.isEmpty()){
                Edge edge = cNode.adj.pop();
                if(nodes[edge.to].status == Node.VISITING){
                    return false;
                }
                stack.add(edge);
            }
        }

        while(temp.size() > 1){
            int node = temp.pop();
            nodes[node].status = Node.PROCESSED;
            sequence.add(node);
        }

        return true;
    }

}


class Node {
    int status = 0;
    protected Stack<Edge> adj = new Stack<>();
    static final int UNVISITED = 0;
    static final int VISITING = 1;
    static final int PROCESSED = 2;
}

class Edge {
    int from;
    int to;
    public Edge(int from, int to){
        this.from = from;
        this.to = to;
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