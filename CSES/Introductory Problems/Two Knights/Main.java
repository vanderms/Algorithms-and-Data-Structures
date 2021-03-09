import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static void main(final String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(in.readLine());
        StringBuilder res = new StringBuilder(n * 64);
        res.append("0");
        if (n >= 2) {
            res.append("\n6");
        }
        if (n >= 3) {
            res.append("\n28");
        }
        if (n >= 4) {
            res.append("\n96");
        }

        for (int i = 5; i <= n; i++) {
            res.append("\n");
            final long squares = i * i;
            long n3 = (squares - 3) * 4;
            long n4 = (squares - 4) * 8;
            long n5 = (squares - 5) * (4 + 4 * (i - 4));
            long n7 = (squares - 7) * (4 * (i - 4));
            long n9 = (squares - 9) * ((i - 4) * (i - 4));
            long possibilities = (n3 + n4 + n5 + n7 + n9) / 2;
            res.append(possibilities);
        }
        System.out.print(res);
    }
}
