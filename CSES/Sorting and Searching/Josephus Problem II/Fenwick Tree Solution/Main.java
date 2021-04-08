import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int log;
    public static void main(String[] args) throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer tokens = new StringTokenizer(in.readLine());
        int n = Integer.parseInt(tokens.nextToken());
        int k = Integer.parseInt(tokens.nextToken());
        StringBuilder out = new StringBuilder(n * 64);

        //find ceil of log of n: O(logn)
        log = 1;
        while(1 << log <= n) {
            log+= 1;
        }

        //create and fill fenwick tree: O(n)
        int[] fenwick = new int[(1 << log) + 1];
        for(int i = 1; i < fenwick.length; i++){
            fenwick[i] = i & -i;
        }

        //solve: O(n logn)
        int step = k + 1;
        int next = 1;

        for(int i = n; i > 0; i--){
            next = ((next - 1) + step) % i;
            if(next == 0){
                next = i;
            }
            out.append(query(fenwick, next));
            out.append(" ");
        }
        out.deleteCharAt(out.length() - 1);
        System.out.print(out);
    }

    public static int query(int[] fenwick, int next){
        int idx = 1 << log;
        int sum = 0;

        for(int depth = log - 1; depth >= 0; --depth){
            if((fenwick[idx] + sum) >= next){
                fenwick[idx] -= 1;
                idx -= 1 << depth;

            } else {
                sum += fenwick[idx];
                idx += 1 << depth;
            }
        }

        if((fenwick[idx] + sum) >= next){
            fenwick[idx] -= 1;
            return idx;
        } else {
           return idx + 1;
        }
    }
}



