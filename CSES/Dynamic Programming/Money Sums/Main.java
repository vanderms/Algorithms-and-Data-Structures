import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        Reader in = new Reader();
        int n = in.nextInt();
        int[] coins = new int[n];


        for(int i = 0; i < n; i++){
            coins[i] = in.nextInt();
        }

        Set<Integer> sums = new HashSet<>();
        sums.add(0);

        for(int i = 0; i < n; i++){
            List<Integer> buffer = new ArrayList<>(sums.size());
            for(var sum : sums){
                int current = sum + coins[i];
                if(!sums.contains(current)){
                    buffer.add(current);
                }

            }
            for(int j = 0; j < buffer.size(); j++){
                sums.add(buffer.get(j));
            }
        }

        sums.remove(0);
        int[] allSums = new int[sums.size()];
        int counter = 0;
        for(var sum : sums){
            allSums[counter] = sum;
            counter++;
        }
        sort(allSums);

        StringBuilder answer = new StringBuilder(allSums.length * 64);

        answer.append(allSums.length);
        answer.append("\n");

        for(int i =0; i < allSums.length; i++){
            answer.append(allSums[i]);
            answer.append(" ");
        }

        answer.deleteCharAt(answer.length() - 1);
        System.out.print(answer);

    }


    public static void sort(int[] a){
        int gap = 0;
        while(gap < a.length) gap = (gap * 3) + 1;
        gap/= 3;

        while (gap > 0) {
            for (int i = gap; i < a.length; i += 1) {

                int temp = a[i];
                int j = i;

                while(j >= gap && a[j - gap] >  temp){
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