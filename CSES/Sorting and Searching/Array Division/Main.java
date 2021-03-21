import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class Main {

    static int n;
    static int k;

    public static void main(String[] args) throws IOException {

        Reader in = new Reader();
        n = in.nextInt();
        k = in.nextInt();
        int[] numbers = new int[n];
        long answer = 0;
        long total = 0;
        long max = 0;
        for(int i = 0; i < n; i++){
            int num = in.nextInt();
            numbers[i] = num;
            total+= num;
            max = Math.max(max, num);
        }

        long start = Math.max(max, (long) Math.ceil((double) total / n));

        long rest = total;
        int current = 0;

        for(int i = 0, j = 0; i < n && j < k; i++){
            if(numbers[i] + current <= start){
                current+= numbers[i];
            }
            else {
                j++;
                if(j >= k) break;
                current = numbers[i];
            }
            rest -= numbers[i];

        }

        long end = Math.max(current + rest, start);

        if(start == end){
            System.out.print(start);
            return;
        }

       while (start <= end) {
           answer = start + (end - start) / 2;

           int result = checkSum(numbers, total, answer);

           if (result == 0){
               break;
           }

           if (result == 1){
               end = answer - 1;
           }
           else {
               start = answer + 1;
           }
       }
        System.out.print(answer);
    }


   public static int checkSum(int[] numbers, long total, long sum){
        long rest = total;
        long lowerRest = total;
        long current = 0;

        for(int i = 0, j = 0; i < n && j < k; i++){
            if(numbers[i] + current <= sum){
                current+= numbers[i];
            }
            else {
                j++;
                if(j >= k) break;
                current = numbers[i];
            }
            rest -= numbers[i];
        }

        current = 0;

        for(int i = 0, j = 0; i < n && j < k; i++){
            if(numbers[i] + current <= (sum - 1)){
                current+= numbers[i];
            }
            else {
                j++;
                if(j >= k) break;
                current = numbers[i];
            }
            lowerRest -= numbers[i];
        }

        if(rest == 0 && lowerRest > 0){
            return 0;
        }
        else if(rest > 0){
            return - 1;
        }
        else return 1;

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





