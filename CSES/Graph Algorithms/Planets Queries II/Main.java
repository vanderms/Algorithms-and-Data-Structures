import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class Main {

    static int[][] table;
    static Map<Integer, Integer> log;
    static Node[] nodes;
    static List<Integer> cyclesSize;

    public static void main(String[] args) throws IOException {
        Reader in = new Reader();
        int n = in.nextInt();
        int q = in.nextInt();

        //build and fill log
        log = new HashMap<>();
        for(int i = 0; i < 19; i++){
            log.put(1 << i, i);
        }

        //cyclesSize array
        cyclesSize = new ArrayList<>();

        //create nodes
        nodes = new Node[n + 1];
        for(int i = 1; i <= n; i++) nodes[i] = new Node();

        //get input data
        table = new int[19][n + 1];
        for(int i = 1; i <= n; i++){
            int to = in.nextInt();
            table[0][i] = to;
            nodes[to].from.add(i);
        }

        //build table
        for(int i = 1; i < 19; i++){
            for(int j = 1; j <= n; j++){
                table[i][j] = table[i - 1][table[i - 1][j]];
            }
        }

        //fill circles and trees
        for(int i = 1; i <= n; i++){
            int idx = table[18][i];
            if(nodes[idx].depth < 0){
                fillCycle(idx);
            }
        }

        StringBuilder answer = new StringBuilder(q * 32);

        for(int i = 0; i < q; i++){
            int fIdx = in.nextInt();
            int tIdx = in.nextInt();
            Node from = nodes[fIdx];
            Node to = nodes[tIdx];
            int dist = -1;
            if(fIdx == tIdx){
                dist = 0;
            } else if(from.group == to.group){
                if(to.isCycle){
                    if(from.isCycle){
                        dist = to.depth - from.depth;
                        if(dist < 0){
                            dist+= cyclesSize.get(to.group);
                        }
                    } else { //from is not cycle
                        int rootDepth = nodes[from.root].depth;
                        dist = from.depth + to.depth - rootDepth;
                        if(to.depth < rootDepth) {
                            dist += cyclesSize.get(to.group);
                        }
                    }
                } else if(to.depth < from.depth){
                    int idx = query(fIdx, from.depth - to.depth);
                    if(idx == tIdx){
                        dist = from.depth - to.depth;
                    }
                }
            }
            answer.append(dist);
            answer.append("\n");
        }


        answer.deleteCharAt(answer.length() - 1);
        System.out.print(answer);
    }


    static void fillCycle(final int idx){
        int depth = 1;
        int next = idx;
        int group = cyclesSize.size();

        while(nodes[next].depth < 0){
            Node node = nodes[next];
            node.depth = depth;
            node.group = group;
            node.isCycle = true;
            next = table[0][next];
            depth++;
        }

        cyclesSize.add(depth - 1);
        next = idx;

        do {
            Node node = nodes[next];
            if(node.from.size() > 1){
                fillTree(next, group);
            }
            next = table[0][next];
        } while(next != idx);
    }

    static void fillTree(int root, int group){


        List<Integer> list = new LinkedList<>();
        for(int i : nodes[root].from){
            if(!nodes[i].isCycle){
                list.add(i);
            }
        }

        int depth = 1;
        while(!list.isEmpty()){
            List<Integer> temp = new LinkedList<>();
            for(int next : list){
                nodes[next].depth = depth;
                nodes[next].group = group;
                nodes[next].root = root;
                temp.addAll(nodes[next].from);
            }
            depth++;
            list = temp;
        }
    }

    static int query(int x, int k){

        int res = x;

        while(k > 0){
            int step = k & -k;
            int row = log.get(step);
            res = table[row][res];
            k -= step;
        }
        return res;
    }
}

class Node {
    List<Integer> from = new LinkedList<>();
    boolean isCycle = false;
    int group = 0;
    int depth = -1;
    int root = 0;
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