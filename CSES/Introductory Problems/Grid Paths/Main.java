import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// This solution written in Java exceeds the time limit.
// However, when written in C++ the algorithm pass the test.

public class Main {
    static int counter = 0;
    static char[][] grid = new char[7][7];
    static char[] input;


    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        input = in.readLine().toCharArray();
        solve(0, 0, 6);
        System.out.print(counter);
    }

    static void solve(final int depth, final int x, final int y){
        if(depth == 48 && x == 0 && y == 0){
            counter++;
            return;
        }

        if(!canReach(depth, x, y)){
            return;
        }

        grid[x][y] = '1';

        if(canGoUp(depth, x, y)){
            solve(depth + 1, x, y + 1);
        }
        if(canGoRight(depth, x, y)){
            solve(depth + 1, x + 1, y);
        }
        if(canGoDown(depth, x, y)){
            solve(depth + 1, x, y - 1);
        }
        if(canGoLeft(depth, x, y)){
            solve(depth + 1, x - 1, y);
        }

        grid[x][y] = '\0';
    }

    static boolean canGoUp(int depth, int x, int y){
        if(y == 6 || grid[x][y + 1] == '1'){
            return false;
        }

        if(!(input[depth] == '?' || input[depth] == 'U')){
            return false;
        }
        return true;
    }

    static boolean canGoRight(int depth, int x, int y){
        if(x == 6 || grid[x + 1][y] == '1'){
            return false;
        }

        if(!(input[depth] == '?' || input[depth] == 'R')){
            return false;
        }
        return true;
    }

    static boolean canGoDown(int depth, int x, int y){
        if(y == 0 || grid[x][y - 1] == '1'){
            return false;
        }

        if(!(input[depth] == '?' || input[depth] == 'D')){
            return false;
        }
        return true;
    }

    static boolean canGoLeft(int depth, int x, int y){
        if(x == 0 || grid[x - 1][y] == '1'){
            return false;
        }

        if(!(input[depth] == '?' || input[depth] == 'L')){
            return false;
        }
        return true;
    }

    static boolean canReach(int depth, int x, int y){

        if( y > 0 && y < 6 && x > 0 && x < 6){
            boolean verticalFree = grid[x][y + 1] == '\0' && grid[x][y - 1] == '\0';
            boolean verticalBlocked = grid[x][y + 1] == '1' && grid[x][y - 1] == '1';
            boolean HorizontalFree = grid[x + 1][y] == '\0' && grid[x - 1][y] == '\0';
            boolean HorizontalBlocked = grid[x + 1][y] == '1' && grid[x - 1][y] == '1';

            if(verticalFree && HorizontalBlocked){
                return false;
            }

            if(verticalBlocked && HorizontalFree){
                return false;
            }
        }

        //grid walls
        if(y == 6 && x > 0 && grid[x - 1][y] == '\0'){
            return false;
        }

        if(x == 6 && y < 6 && grid[x][y + 1] == '\0'){
            return false;
        }

        if(y == 0 && x < 6 && grid[x + 1][y] == '\0'){
            return false;
        }

        if(x == 0 && y < 6 && grid[x][y + 1] == '\0'){
            return false;
        }

        int distance = x + y;
        int remaining = 48 - depth;

        if(remaining < distance){
            return false;
        }

        return true;
    }
}