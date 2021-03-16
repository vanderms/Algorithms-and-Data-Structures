import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
    static int counter = 0;
    static boolean[][] grid = new boolean[9][9];
    static char[] in;
    static int s = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        in = input.readLine().toCharArray();
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                if(i == 0 || i == 8 || j == 0 || j == 8){
                    grid[i][j] = true;
                }                
                else grid[i][j] = false;
            }
        }
        
        solve(1, 7);
        System.out.print(counter);
    }

    static void solve(final int x, final int y){
        if(x == 1 && y == 1){
            if(s == 48){
                counter++;
            }
            return;
        }

        if(!grid[x][y + 1] && !grid[x][y - 1] && grid[x + 1][y] && grid[x - 1][y]){
            return;
        }

        if(grid[x][y + 1] && grid[x][y - 1] && !grid[x + 1][y] && !grid[x - 1][y]){
            return;
        }

        if((x + y) - 2 > 48 - s){
            return;
        }

        grid[x][y] = true;

        if(in[s] == '?'){
            if(!grid[x][y + 1]){
                s++;
                solve(x, y + 1);
                s--;
            }
            if(!grid[x + 1][y]){
                s++;
                solve(x + 1, y);
                s--;
            }
            if(!grid[x][y - 1]){
                s++;
                solve( x, y - 1);
                s--;
            }
            if(!grid[x - 1][y]){
                s++;
                solve(x - 1, y);
                s--;
            }
        }
        else if(in[s] == 'U' && !grid[x][y + 1]){
            s++;
            solve(x, y + 1);
            s--;
        }
        else if(in[s] == 'R' && !grid[x + 1][y]){
            s++;
            solve(x + 1, y);
            s--;
        }
        else if(in[s] == 'D' && !grid[x][y - 1]){
            s++;
            solve( x, y - 1);
            s--;
        }
        else if(in[s] == 'L' && !grid[x - 1][y]){
            s++;
            solve(x - 1, y);
            s--;
        }
        grid[x][y] = false;
    }
}