import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static BufferedReader in;
    static StringBuilder out;
    public static void main(String[] args) throws IOException {

        in = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(in.readLine());
        out = new StringBuilder(n * 32);
        Pair[] memo = new Pair[40];
        memo[0] = new Pair(0, 1);
        memo[1] = new Pair(1, 1);

        for(int i = 0; i < n; i++){
            int num = Integer.parseInt(in.readLine());
            Pair fibo = fibonacci(memo, num);
            out.append("fib(");
            out.append(num);
            out.append(") = ");
            out.append(fibo.calls - 1);
            out.append(" calls = ");
            out.append(fibo.value);
            out.append("\n");
        }
        System.out.print(out);
    }

    static Pair fibonacci(Pair[] memo, int x){
        if(memo[x] != null){
            return memo[x];
        }
        Pair pair = new Pair();
        Pair smaller = fibonacci(memo, x - 2);
        Pair larger = fibonacci(memo, x - 1);
        pair.calls = smaller.calls + larger.calls + 1;
        pair.value = smaller.value + larger.value;
        memo[x] = pair;
        return pair;
    }
}

class Pair {
    int value;
    int calls;
    public Pair(){};
    public Pair(int value, int calls){
        this.value = value;
        this.calls = calls;
    }
}