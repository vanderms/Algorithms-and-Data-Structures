import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class Main {

    static long[] nines = new long[19];

    public static void main(String[] args) throws IOException {

        Reader in = new Reader();

        long a = in.nextLong();
        long b = in.nextLong();

        int[] firstArr = arrayFromLong(a - 1);
        int[] lastArr = arrayFromLong(b);

        nines[0] = 1;
        for(int i = 1; i < nines.length; i++){
            nines[i] = nines[i - 1] * 9;
        }

        if(a == 0) {
            long value = count(lastArr);
            System.out.print(value);
        } else {
            long first = count(firstArr);
            long last = count(lastArr);
            System.out.print(last - first);
        }
    }


    static long count(int[] arr){

        final int NUMBER_OF_DIGITS = 10;
        long[][] memo = new long[arr.length][NUMBER_OF_DIGITS];

        //fill first index
        for(int i = 0; i <= arr[0]; i++){
            memo[0][i] = 1;
        }

        //then calc indexes 1 to arr.length - 1
        for(int idx = 1; idx < arr.length; idx++){
            int number = arr[idx];
            for(int digit = 0; digit <= number; digit++){
                if(number > digit){
                    memo[idx][digit] = nines[idx];
                }
                else { //number == digit, since digit cannot be bigger than number because of for condition
                    for(int i = 0; i <= arr[idx - 1]; i++){
                        if(digit != i){
                            memo[idx][digit] += memo[idx - 1][i];
                        }
                    }
                }
            }
        } // behold the pyramid of dooom!

        //sum last idx and nine base
        long res = 0;
        for(int i = 0; i <= arr[arr.length - 1]; i++){
            res+= memo[memo.length - 1][i];
        }

        //sum base
        for(int i = 0; i < arr.length - 1; i++){
            res+= nines[i];
        }

        return res;
    }


    static int[] arrayFromLong(long num){

        if(num <= 0){
            return new int[]{0};
        }

        int counter = 0;
        long temp = num;
        while(temp != 0){
            temp /= 10L;
            ++counter;
        }

        int[] res = new int[counter];

        temp = num;
        counter = 0;

        while(temp != 0){
            res[counter] = (int) (temp % 10L);
            temp /= 10L;
            counter++;
        }
        return res;
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