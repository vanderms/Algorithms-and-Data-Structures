import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static void main(final String[] args) throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(in.readLine());
        StringBuilder res = new StringBuilder(8 * n);

        for(int i = 0; i < n; i++){
            StringTokenizer tokens = new StringTokenizer(in.readLine());
            int a = Integer.parseInt(tokens.nextToken());
            int b = Integer.parseInt(tokens.nextToken());
            if((a + b) % 3 != 0 || a > (2 * b) || b > (2 * a)){
                res.append("NO");
            }
            else {
                res.append("YES");
            }
            if(i != (n - 1)){
                res.append("\n");
            }
        }
        System.out.print(res);
    }
}

