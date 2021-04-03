import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer line = new StringTokenizer(in.readLine());
        int a = Integer.parseInt(line.nextToken());
        int b = Integer.parseInt(line.nextToken());
        if(a > b){
            int temp = a;
            a = b;
            b = temp;
        }
        int[][] memo = new int[a + 1][b + 1];

        for(int r = 1; r <= a; r++){
            for(int c = 1; c <= b; c++){
                if(r == c){
                    memo[r][c] = 0;
                }
                else if(r == 1){
                    memo[r][c] = c - 1;
                }
                else if(c == 1){
                    memo[r][c] = r - 1;
                }
                else if(r > c){
                    memo[r][c] = memo[c][r];
                }
                else {
                    memo[r][c] = Integer.MAX_VALUE;
                    //first horizontal cuts
                    for(int i = 1; i <= r / 2; i++){
                        memo[r][c] = Math.min(memo[r][c], memo[i][c] + memo[r - i][c] + 1);
                    }
                    //then vertical cuts
                    for(int i = 1; i <= c / 2; i++){
                        memo[r][c] = Math.min(memo[r][c], memo[r][i] + memo[r][c - i] + 1);
                    }
                }
            }
        }

        System.out.print(memo[a][b]);
    }
}