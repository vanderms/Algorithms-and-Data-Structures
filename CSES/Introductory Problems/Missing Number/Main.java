import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException{
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        long n = Integer.parseInt(in.readLine());
        StringTokenizer tokens = new StringTokenizer(in.readLine());
        long sum = 0;

        while(tokens.hasMoreTokens()){
            sum+= Integer.parseInt(tokens.nextToken());
        }

        long arithmeticSum = ((n + 1) * n) / 2;
        long res = arithmeticSum - sum;
        System.out.print(res);
    }
}
