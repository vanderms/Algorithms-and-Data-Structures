import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static StringBuilder out;

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(in.readLine());
        out = new StringBuilder(8 * (1 << n));
        out.append((1 << n) - 1);
        solveTowerOfHannoi(n, 1, 2, 3);
        System.out.print(out);
    }

    public static void solveTowerOfHannoi(int n, int A, int B, int C){
        if(n > 0){
            solveTowerOfHannoi(n - 1, A, C, B);
            out.append("\n");
            out.append(A);
            out.append(" ");
            out.append(C);
            solveTowerOfHannoi(n -1, B, A, C);
        }
    }
}

