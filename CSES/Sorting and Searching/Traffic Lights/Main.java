import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.TreeSet;

public class Main {

    public static void main(String[] args) throws IOException {

        Reader in = new Reader();
        int maxLength = in.nextInt();
        int n = in.nextInt();
        StringBuilder out = new StringBuilder(n * 64);

        TreeSet<Light> lights = new TreeSet<>();
        lights.add(new Light(0, maxLength, 0));

        SegmentTree tree = new SegmentTree(n, Math::max);
        tree.update(0, maxLength);

        for(int i = 1; i <= n; i++){

            Light current = new Light(in.nextInt(), 0, i);
            Light lower = lights.lower(current);
            lights.add(current);

            current.length = lower.point + lower.length - current.point;
            lower.length = current.point - lower.point;

            tree.update(current.index, current.length);

            tree.update(lower.index, lower.length);

            out.append(tree.query(0, i));
            out.append(" ");
        }

        out.deleteCharAt(out.length() - 1);
        System.out.print(out);

    }
}

class Light implements Comparable<Light>{

    public final int point;
    public final int index;
    public int length;

    public Light(int point, int length, int index){
        this.point = point;
        this.length = length;
        this.index = index;
    }
    @Override
    public int compareTo(Light o){
        return this.point - o.point;
    }
}


interface SegmentTreeCriteria {
    long apply(long lhs, long rhs);
}

class SegmentTree {
    private final SegmentTreeCriteria criteria;
    private final long[] arr;
    private final int size;
    private final int height;

    public SegmentTree(int size, SegmentTreeCriteria criteria){
        this.criteria = criteria;
        int log = 1;
        while(1 << log < size){ log++; }
        this.height = log + 1;
        this.size = 1 << log;
        this.arr = new long[2 * this.size];
    }

    public long get(int index){
        return arr[this.size + index];
    }

    public void update(int index, final long value){

        if(index < 0 || index >= this.size){
            throw new IndexOutOfBoundsException();
        }

        index += this.size;
        arr[index] = value;

        for(int i = 1; i < this.height; i++){
            index /= 2;
            int nodeA = index << 1;
            int nodeB = nodeA + 1;
            arr[index] = criteria.apply(arr[nodeA], arr[nodeB]);
        }
    }

    public long query(int start, int end){
        if(start < 0 || end >= this.size || start > end){
            throw new IndexOutOfBoundsException();
        }
        if(start == end){
            return get(start);
        }
        else return query(start, end, 1, 1);
    }

    public long query(int start, int end, int node, int depth){
        int range = 1 << (this.height - depth);
        int left = (node - (1 << (depth - 1))) * range;
        int right = left + (range - 1);
        int middle = left + (range/2);

        if(start <= left && end >= right){
            return this.arr[node];
        }
        else if(end < middle){
            return query(start, end, node * 2, depth + 1);
        }
        else if(start >= middle){
            return query(start, end, node * 2 + 1, depth + 1);
        }
        else return criteria.apply(
                    query(start, end, node * 2, depth + 1),
                    query(start, end, node * 2 + 1, depth + 1)
            );
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