import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {


    public static void main(final String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(in.readLine());

        int mod = (int) Math.pow(10, 9) + 7;
        int res = 1;
        for(int i = 0; i < n; i++){
            res = (res * 2) % mod;
        }
        System.out.print(res);
    }
}
