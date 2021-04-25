import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class Main {

    static Node[] nodes;
    static List<Integer> order;
    static boolean[] visited;

    public static void main(String[] args) throws IOException {

        Reader in = new Reader();

        int n = in.nextInt();
        int m = in.nextInt();

        nodes = new Node[n + 1];
        order = new ArrayList<>(n);

        for(int i = 1; i <= n; i++) nodes[i] = new Node();

        for(int i = 0; i < m; i++){
            int from = in.nextInt();
            int to = in.nextInt();
            nodes[from].to.add(to);
            nodes[to].from.add(from);
        }

        visited = new boolean[n + 1];
        for(int i = 1; i <= n; i++){
            if(!visited[i]){
                visited[i] = true;
                orderedDfs(i);
            }
        }

        Arrays.fill(visited, false);
        Collections.reverse(order);
        int component = 1;
        for(int i = 0; i < n; i++){
            int idx = order.get(i);
            if(nodes[idx].component <= 0){

                dfsReverse(idx, component);
                component++;
            }
        }

        StringBuilder answer = new StringBuilder(n * 32);
        answer.append(component - 1);
        answer.append("\n");
        for(int i = 1; i <= n; i++){
            answer.append(nodes[i].component);
            answer.append(" ");
        }

        answer.deleteCharAt(answer.length() - 1);
        System.out.print(answer);
    }

    static void orderedDfs(int idx){
        // don't like this solution because, at first, it allows stack overflow
        // however, recursive dfs, especially in this case,
        // is much more easy (compare with dfs reverse) and every time that I tried
        // to hack cses solutions with recursive stack overflow I failled
        for(var next : nodes[idx].to){
            if(!visited[next]){
                visited[next] = true;
                orderedDfs(next);
            }
        }
        order.add(idx);
    }

    static void dfsReverse(int idx, int component){

        Stack<Integer> stack = new Stack<>();
        stack.add(idx);
        visited[idx] = true;

        while(!stack.isEmpty()){
            int node = stack.pop();
            nodes[node].component = component;
            List<Integer> adjacents = nodes[node].from;
            for(int next : adjacents){
               if(!visited[next]){
                   visited[next] = true;
                   stack.add(next);
               }
            }
        }

    }

}


class Node {
    List<Integer> to = new LinkedList<>();
    List<Integer> from = new LinkedList<>();
    int component = 0;
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