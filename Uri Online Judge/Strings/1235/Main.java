import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
    static BufferedReader in;
    static StringBuilder out;
    public static void main(String[] args) throws IOException {

        in = new BufferedReader(new InputStreamReader(System.in));
        out = new StringBuilder();

        int n = Integer.parseInt(in.readLine());
        for(int i = 0; i < n; i++){
            solve();
            out.append('\n');
        }

        System.out.print(out);

    }

    public static void solve() throws  IOException{
        char[] input = in.readLine().toCharArray();
        final int middle = input.length / 2;

        for(int i = 0; i < middle / 2; i++){
            char temp = input[i];
            input[i] = input[middle - i - 1];
            input[middle - i - 1] = temp;

            temp = input[i + middle];
            input[i + middle] = input[input.length - i - 1];
            input[input.length - i - 1] = temp;
        }

        out.append(input);
    }
}