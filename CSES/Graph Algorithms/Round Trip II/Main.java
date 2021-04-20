import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Stack;

public class Main {

    static int n;

    public static void main(String[] args) throws IOException {

        Reader in = new Reader();
        n = in.nextInt();
        int m = in.nextInt();
        Node[] nodes = new Node[n + 1];
        for(int i = 1; i <= n; i++){ nodes[i] = new Node(); }

        for(int i = 0; i < m; i++){
            int from = in.nextInt();
            int to = in.nextInt();
            nodes[from].adj.add(new Edge(from, to));
        }

        Stack<Integer> result = null;

        for(int i = 1; i <= n; i++){
            if(!nodes[i].visited){
                result = search(nodes, i);
                if(result != null){
                    break;
                }
            }
        }

        if(result == null){
            System.out.print("IMPOSSIBLE");
            return;
        }

        int end = result.pop();
        int next = end;

        Stack<Integer> sequence = new Stack<>();
        sequence.add(next);

        do {
            next = result.pop();
            sequence.add(next);
        } while(next != end);

        StringBuilder answer = new StringBuilder(sequence.size() * 32);
        answer.append(sequence.size());
        answer.append("\n");

        while(!sequence.isEmpty()){
            answer.append(sequence.pop());
            answer.append(" ");
        }

        answer.deleteCharAt(answer.length() - 1);
        System.out.print(answer);
    }

    static Stack<Integer> search(Node[] nodes, int idx){

        Stack<Edge> temp = new Stack<>();
        boolean[] inPath = new boolean[n + 1];
        Stack<Integer> sequence = new Stack<>();

        temp.add(new Edge(0, idx));
        sequence.add(0);

        while(!temp.empty()){
            Edge cEdge = temp.pop();

            while(cEdge.from != sequence.peek()){
                inPath[sequence.pop()] = false;
            }

            Node cNode = nodes[cEdge.to];
            sequence.add(cEdge.to);
            cNode.visited = true;
            inPath[cEdge.to] = true;

            while(!cNode.adj.isEmpty()){
                Edge edge = cNode.adj.pop();
                if(inPath[edge.to]){
                    sequence.add(edge.to);
                    return sequence;
                }
                temp.add(edge);
            }
        }
        return null;
    }


}


class Node {
    boolean visited = false;
    protected Stack<Edge> adj = new Stack<>();
}

class Edge {

    protected int to;
    protected int from;

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