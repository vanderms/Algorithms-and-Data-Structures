import java.io.*;
import java.util.*;

public class Main {
    static final int MOD = 1_000_000_007;

    public static void main(String[] args) throws IOException {

        Reader in = new Reader();
        final int n = in.nextInt();
        final int maxValue = in.nextInt();
        int[] elements = new int[n];
        long[][] memo = new long[n][maxValue + 2];

        for(int i = 0; i < n; i++){
            elements[i] = in.nextInt();
        }

        int lower = 0, higher = 0;

        if(elements[0] == 0){
            Arrays.fill(memo[0], 1);
            memo[0][0] = 0;
            memo[0][maxValue + 1] = 0;
            lower = 1;
            higher = maxValue;
        } else {
            memo[0][elements[0]] = 1;
            lower = higher = elements[0];
        }

        for(int i = 1; i < n; i++){

            //adjust boundaries
            if(elements[i] == 0){
                if(lower > 1){
                    lower--;
                }
                if(higher < maxValue){
                    higher++;
                }
            } else {
                lower = higher = elements[i];
            }

            //calc possibilities
            for(int j = lower; j <= higher; j++){
                memo[i][j] = memo[i - 1][j + 1] +  memo[i - 1][j] + memo[i - 1][j - 1];
                if(memo[i][j] > MOD){
                    memo[i][j] %= MOD;
                }
            }
        }

        long answer = 0;
        for(int i = lower; i <= higher; i++){
            answer =  (answer + memo[n - 1][i]) % MOD;
        }

        System.out.print(answer);
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