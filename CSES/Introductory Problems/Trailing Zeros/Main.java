import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {


    public static void main(final String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(in.readLine());
        int res = 0;

        for(int i = 5; i <= n; i*=5){
            res += n / i;
        }

        System.out.print(res);

    }
}
