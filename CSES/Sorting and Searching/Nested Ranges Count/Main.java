import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {

        //long t0 = System.nanoTime();
        Reader in = new Reader();
        int n = in.nextInt();
        StringBuilder out = new StringBuilder(n * 64);

        Range[] ranges = new Range[n];
        List<Range> oranges = new ArrayList<>(n); // I know perfectly well that oranges are not ordered ranges, but in some way I thought it was funny

        for(int i = 0; i < n; i++){
            oranges.add(ranges[i] = new Range(in.nextInt(), in.nextInt()));
        }

        Collections.sort(oranges);
        Range header = new Range(0, 0);

        for(int i = 0; i < n; i++){
            Range self = oranges.get(i);
            Range other = header.next;
            Range last = header;

            while(other != null){
                if(other.end >= self.end){
                    other.contains = 1;
                    oranges.get(i).inside = 1;
                    if(other.inside == 1){
                        other.previous.next = other.next;
                        if(other.next != null) {
                            other.next.previous = other.previous;
                        }
                        other = other.previous;
                    }
                }
                else if(other.end < self.start){
                    other.previous.next = other.next;
                    if(other.next != null) {
                        other.next.previous = other.previous;
                    }
                    other = other.previous;
                }

                last = other;
                other = other.next;
            }

            last.next = self;
            self.previous = last;
        }

        for(int i = 0; i < ranges.length; i++){
            out.append(ranges[i].contains);
            out.append(" ");
        }

        out.setCharAt(out.length() - 1, '\n');

        for(int i = 0; i < ranges.length; i++){
            out.append(ranges[i].inside);
            out.append(" ");
        }

        out.deleteCharAt(out.length() - 1);
        System.out.print(out);
//        long t1 = System.nanoTime();
//        System.out.println("");
//        System.out.println((t1 - t0) / 1_000_000_000.0);

    }
}




class Range implements Comparable<Range>{
    public final int start;
    public final int end;
    public int contains;
    public int inside;
    public Range next;
    public Range previous;

    public Range(int start, int end){
        this.start = start;
        this.end = end;
        this.contains = 0;
        this.inside = 0;
        this.next = null;
        this.previous = null;
    }
    @Override
    public int compareTo(Range o){
        return this.start != o.start ? this.start - o.start : o.end - this.end;
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