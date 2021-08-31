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

        for(int i = 1; i <= n / 2; i++){
            if(n % i == 0){
                out.append(i);
                out.append("\n");
            }

        }

        out.append(n);
        out.append("\n");

        System.out.print(out);
    }
}

