import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class Main {

    static int[] machines;
    static int target;

    public static void main(String[] args) throws IOException {

        Reader in = new Reader();
        int n = in.nextInt();
        target = in.nextInt();
        machines = new int[n];

        for(int i = 0; i < n; i++){
            machines[i] = in.nextInt();
        }

        long start = 1;
        long end = 9_223_372_036_854_775_807L;

        long time = 0;

        while (start <= end) {
            time = start + (end - start) / 2;

            int result = checkTime(time);

            if (result == 0){
                break;
            }

            if (result == 1){
                end = time - 1;
            }
            else {
                start = time + 1;
            }
        }

        System.out.print(time);
    }

    public static int checkTime(long time){
        long upperSum = 0;
        long lowerSum = 0;

        for(int i = 0; i < machines.length && upperSum <= target; i++){
            upperSum+= time / machines[i];
        }

        for(int i = 0; i < machines.length && lowerSum <= target; i++){
            lowerSum+= (time - 1) / machines[i];
        }

        if(upperSum >= target && lowerSum < target){
            return 0;
        }
        else if(upperSum >= target && lowerSum >= target){
            return 1;
        }
        else return -1;
    }



    public static <T> void sort(T[] a, Comparator<T> comp){
        int gap = 0;
        while(gap < a.length) gap = (gap * 3) + 1;
        gap/= 3;

        while (gap > 0) {
            for (int i = gap; i < a.length; i += 1) {

                T temp = a[i];
                int j = i;

                while(j >= gap && comp.compare(a[j - gap],  temp) > 0){
                    a[j] = a[j - gap];
                    j -= gap;
                }
                a[j] = temp;
            }
            gap/= 3;
        }
    }
}



class Customer {
    public int arrival;
    public int departure;
    public int room;
    public Customer(int arrival, int departure){
        this.arrival = arrival;
        this.departure = departure;
    }
}



class SegmentTree {

    private final int[] arr;
    private final int size;
    private final int height;

    public SegmentTree(int size){
        int log = 1;
        while(1 << log < size){ log++; }
        this.height = log + 1;
        this.size = 1 << log;
        this.arr = new int[2 * this.size];
    }

    public int get(int index){
        return arr[this.size + index];
    }

    public void update(int index, final int value){

        if(index < 0 || index >= this.size){
            throw new IndexOutOfBoundsException();
        }

        index += this.size;
        arr[index] = value;

        for(int i = 1; i < this.height; i++){
            index /= 2;
            int nodeA = index << 1;
            int nodeB = nodeA + 1;
            arr[index] = Math.min(arr[nodeA], arr[nodeB]);
        }
    }

    public int query(int start, int end){
        if(start < 0 || end >= this.size || start > end){
            throw new IndexOutOfBoundsException();
        }
        if(start == end){
            return get(start);
        }
        else return query(start, end, 1, 1);
    }

    public int query(int start, int end, int node, int depth){
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
        else return Math.min(
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





