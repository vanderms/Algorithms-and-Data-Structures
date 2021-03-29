import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class Main {


    public static void main(String[] args) throws IOException {

        Reader in = new Reader();
        final int n = in.nextInt(), a = in.nextInt(), b = in.nextInt();

        long[] prefixes = new long[n];
        long prefixSum = 0;
        for(int i = 0; i < n; i++){
            prefixSum += in.nextInt();
            prefixes[i] = prefixSum;
        }

        TreeMap<Long, Integer> numbers = new TreeMap<>();

        for(int i = a - 1; i < b; i++){
            multisetAdd(numbers, prefixes[i]);
        }

        long max = numbers.lastKey();
        long current = 0;
        int first = a - 1;
        int last = b - 1;

        for(int i = 1; i + a <= n; i++){
            multisetRemove(numbers, prefixes[first]);
            first++;
            if(last + 1 < n) multisetAdd(numbers, prefixes[last + 1]);
            last++;

            current = numbers.lastKey() - prefixes[i - 1];
            max = Math.max(max, current);
        }
        System.out.print(max);
    }


    static <T> int multisetAdd(Map<T, Integer> map, T key){
        if(map.containsKey(key)){
            map.put(key, map.get(key) + 1);
        }
        else map.put(key, 1);
        return 1;
    }

    static <T> int multisetRemove(Map<T, Integer> map, T key){
        Integer quantity = map.get(key);
        if(quantity != null){
            if(quantity > 1){
                map.put(key, quantity  - 1);
            }
            else {
                map.remove(key);
            }
            return -1;
        }
        return 0;
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