import java.io.*;
import java.util.StringTokenizer;

public class Main {

    static int[][] map;

    public static void main(String[] args) throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String line = in.readLine();
        StringTokenizer tokens = new StringTokenizer(line);
        int n = Integer.parseInt(tokens.nextToken());
        int m = Integer.parseInt(tokens.nextToken());
        map = new int[n + 2][m + 2];

        for(int r = 1; r <= n; r++){
            line = in.readLine();
            for(int c = 1; c <= m; c++){
                if(line.charAt(c - 1) == '.'){
                    map[r][c] = 1;
                }
            }
        }

        int answer = 0;

        for(int r = 1; r <= n; r++){
            for(int c = 1; c <= m; c++){
                if(map[r][c] == 1){
                    connect(r, c);
                    answer+= 1;
                }
            }
        }

        System.out.print(answer);
    }

    static void connect(int r, int c){
        map[r][c] = 0;
        if(map[r + 1][c] != 0) connect(r + 1, c);
        if(map[r - 1][c] != 0) connect(r - 1, c);
        if(map[r][c + 1] != 0) connect(r, c + 1);
        if(map[r][c - 1] != 0) connect(r, c - 1);
    }
}

