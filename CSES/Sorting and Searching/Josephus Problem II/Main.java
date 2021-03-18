import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        Reader in = new Reader();
        int n = in.nextInt();
        int step = in.nextInt() + 1;
        StringBuilder out = new StringBuilder();
        JosephusTree tree = new JosephusTree(n, step);
        tree.solve(out);
        System.out.print(out);
    }
}

class JosephusTree {
    
    private final int[] people;
    private final int size;
    private final int capacity;
    private final int height;
    private final int step;

    public JosephusTree(int size, int step){
        int log = 1;
        while(1 << log < (size + 1)){ log++; }
        this.step = step;
        this.capacity = 1 << log;
        this.size = size;
        this.height = log + 1;
        this.people = new int[2 * this.capacity];
    }

    public void solve(StringBuilder out){
        build();
        int previous = 1;

        for(int i = this.size; i > 0; i--){
            int value = (previous - 1 + this.step) % i;
            value = value != 0 ? value : i;
            out.append(remove(1, value));
            out.append(" ");
            previous = value;
        }
        out.deleteCharAt(out.length() - 1);
    }


    private void build(){
        for(int i = 1; i <= this.size; i++){
            update(i, 1);
        }
    }

    private void update(int index, final int value){

        index += this.capacity;
        people[index] = value;

        for(int i = 1; i < this.height; i++){
            index /= 2;
            int nodeA = index << 1;
            int nodeB = nodeA + 1;
            people[index] = people[nodeA] + people[nodeB];
        }
    }

    private int remove(int node, int value){
        people[node]--;
        if(node > this.capacity){
            return node - this.capacity;
        }
        int nodeL = node << 1;
        int nodeR = nodeL + 1;
        if(people[nodeL] >= value){
            return remove(nodeL, value);
        }
        else return remove(nodeR, value - people[nodeL]);
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

    public int nextint() throws IOException
    {
        int ret = 0;
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