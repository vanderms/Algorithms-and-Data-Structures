import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static final long INF = Long.MAX_VALUE / 2;

    public static void main(String[] args) throws IOException {

        var input = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(input);
        StringTokenizer tokens = new StringTokenizer(in.readLine());

        int n = Integer.parseInt(tokens.nextToken());
        int m = Integer.parseInt(tokens.nextToken());
        int q = Integer.parseInt(tokens.nextToken());

        long[][] cities = new long[n + 1][n + 1];

        //Floyd-Warshall algorithm
        for(int r = 2; r <= n; r++){
            for(int c = 1; c < r; c++){
                cities[r][c] = INF;
                cities[c][r] = INF;
            }
        }

        for(int i = 0; i < m; i++){
            tokens = new StringTokenizer(in.readLine());
            int a = Integer.parseInt(tokens.nextToken());
            int b = Integer.parseInt(tokens.nextToken());
            cities[a][b] = Math.min(cities[a][b], Integer.parseInt(tokens.nextToken()));
            cities[b][a] = cities[a][b];
        }

        for(int k = 1; k <= n; k++){
            for(int r = 2; r <= n; r++){
                if(r == k){ continue; }
                for(int c = 1; c < r; c++){
                    if(c == k) continue;
                    long distance = cities[r][k] + cities[c][k];
                    if(distance < cities[r][c]){
                        cities[r][c] = distance;
                        cities[c][r] = distance;
                    }
                }
            }
        }

        StringBuilder answer = new StringBuilder(q * 32);

        for(int i = 0; i < q; i++){
            tokens = new StringTokenizer(in.readLine());
            int a = Integer.parseInt(tokens.nextToken());
            int b = Integer.parseInt(tokens.nextToken());
            long distance = cities[a][b];
            if(distance == INF) distance = -1;
            answer.append(distance);
            answer.append("\n");
        }

        answer.deleteCharAt(answer.length() - 1);
        System.out.print(answer);
    }
}
