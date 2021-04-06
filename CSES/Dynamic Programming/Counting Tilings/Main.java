import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    static double MOD = 1_000_000_007.00;

    public static void main(String[] args) throws IOException {

        Reader in = new Reader();
        int n = in.nextInt();
        int m = in.nextInt();
        List<Integer>[] memo = buildMemo(n);
        long[] cumulative = new long[1 << n];

        for(var num: memo[0]) {
            cumulative[num] = 1;
        }

        for(int r = 1; r < m; r++){
            long[] temp = new long[cumulative.length];
            for(int c = 0; c < cumulative.length; c++){
                if(cumulative[c] == 0) {
                    continue;
                }
                for(int i = 0; i < memo[c].size(); i++){
                    int num = memo[c].get(i);
                    temp[num] += cumulative[c];
                    if(temp[num] > MOD){
                        temp[num] %= MOD;
                    }
                }
            }
            cumulative = temp;
        }

        System.out.print(cumulative[0]);
    }

    public static List<Integer>[] buildMemo(int n){
        final int p2n = 1 << n;
        List<Integer>[] memo = new List[p2n];
        for(int i = 0; i < p2n; i++){
            memo[i] = new ArrayList<>(p2n);
        }

        for(int r = 0; r < p2n; r++){
            for(int c = 0; c < p2n; c++){
                if(isCompatible(r, c, n)){
                    memo[r].add(c);
                };
            }
        }
        return memo;
    }

    public static boolean isCompatible(int r, int c, int n){

        boolean answer = true;
        int idx = n - 1;

        while(idx >= 0){
            int imask = 1 << idx;
            if((r & imask) == imask){
                if((c & imask) == imask){
                    answer = false;
                    break;
                }
                idx-= 1;
            } else { // r == 0
                if(idx == 0){
                    if((c & imask) == 0){
                        answer = false;
                        break;
                    }
                    idx-= 1;

                } else if((c & imask) == 0){
                    int nmask = 1 << (idx - 1);
                    if(!((r & nmask) == 0 && (c & nmask) == 0)){
                        answer = false;
                        break;

                    }
                    idx-= 2;
                } else {
                    idx-= 1;
                }
            }
        }
        return answer;
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