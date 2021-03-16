import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {
    
    static int[] collectables;
    static int[] indexes;

    public static void main(String[] args) throws IOException {
        Reader in = new Reader();

        int n = in.nextInt();
        int nSwaps = in.nextInt();
        StringBuilder out = new StringBuilder(nSwaps * 64);

        indexes = new int[n + 2];
        collectables = new int[n + 2];

        collectables[0] = indexes[0] = 0;
        collectables[n + 1] = indexes[n + 1] = n + 1;

        for(int i = 1; i <= n; i++){
            int num = in.nextInt();
            indexes[num] = i;
            collectables[i] = num;
        }

        int rounds = 1;
        for(int i = 1; i <= n; i++){
            if(indexes[i] > indexes[i + 1]){
                rounds++;
            }
        }

        for(int i = 0; i < nSwaps; i++){
            int a = in.nextInt();
            int b = in.nextInt();
            int indexA = Math.min(collectables[a], collectables[b]);
            int indexB = Math.max(collectables[a], collectables[b]);

            if(indexB - indexA > 2){
                rounds-= calcSegmentRounds(indexA - 1, indexA + 1);
                rounds-= calcSegmentRounds(indexB - 1, indexB + 1);
            }
            else {
                rounds-= calcSegmentRounds(indexA - 1, indexB + 1);
            }
            //swap indexes
            int temp = indexes[collectables[a]];
            indexes[collectables[a]] = indexes[collectables[b]];
            indexes[collectables[b]] = temp;
            //swap collectables
            temp = collectables[a];
            collectables[a] = collectables[b];
            collectables[b] = temp;

            if(indexB - indexA > 2){
                rounds+= calcSegmentRounds(indexA - 1, indexA + 1);
                rounds+= calcSegmentRounds(indexB - 1, indexB + 1);
            }
            else {
                rounds+= calcSegmentRounds(indexA - 1, indexB + 1);
            }

            out.append(rounds);
            out.append("\n");
        }

        out.deleteCharAt(out.length() - 1);
        System.out.print(out);
    }

    public static int calcSegmentRounds(int start, int end){
        int rounds = 1;
        for(int i = start; i < end; i++){
            if(indexes[i] > indexes[i + 1]){
                rounds++;
            }
        }
        return rounds;
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