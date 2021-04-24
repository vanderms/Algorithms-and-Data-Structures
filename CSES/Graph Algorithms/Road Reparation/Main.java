import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Main {

    static int[] sets;

    public static void main(String[] args) throws IOException {

        Reader in = new Reader();
        StringBuilder answer = new StringBuilder();
        int n = in.nextInt();
        int m = in.nextInt();
        Edge[] edges = new Edge[m];
        sets = new int[n + 1];
        Arrays.fill(sets, -1);

        for(int i = 0; i < m; i++){
            int from = in.nextInt();
            int to = in.nextInt();
            int weigh = in.nextInt();
            edges[i] = new Edge(from, to, weigh);
        }

        sort(edges);

        long sum = 0;
        int counter = 0;

        for(Edge edge : edges){
            if(find(edge.to) != find(edge.from)){
                unite(edge.to, edge.from);
                sum+= edge.weigth;
                counter++;
            }
        }

        if(counter < n -1){
            System.out.print("IMPOSSIBLE");
            return;
        }

        System.out.print(sum);
    }


    static int find(int idx){
        return sets[idx] < 0 ? idx : (sets[idx] = find(sets[idx]));
    }

    static void unite(int a, int b) {
        a = find(a);
        b = find(b);
        if (sets[a] < sets[b]){
            sets[a] += sets[b];
            sets[b] = a;
        } else {
            sets[b] += sets[a];
            sets[a] = b;
        }
    }

    public static void sort(Edge[] a){
        int gap = 0;
        while(gap < a.length) gap = (gap * 3) + 1;
        gap/= 3;

        while (gap > 0) {
            for (int i = gap; i < a.length; i += 1) {

                Edge temp = a[i];
                int j = i;

                while(j >= gap && a[j - gap].weigth >  temp.weigth){
                    a[j] = a[j - gap];
                    j -= gap;
                }
                a[j] = temp;
            }
            gap/= 3;
        }
    }
}


class Edge {
    final int from;
    final int to;
    final int weigth;
    public Edge(int from, int to, int weigth){
        this.from = from;
        this.to = to;
        this.weigth = weigth;
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