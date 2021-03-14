import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int[][] board;
    static int possibilities;

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        board = new int[8][8];
        possibilities = 0;

        /*
         0: free
         1 << 0: reserved
         1 << 1: column
         1 << 2: descending diagonal
         1 << 3: ascending diagonal
         */

        for(int i = 0; i < 8; i++){
            String str = in.readLine();
            for(int j = 0; j < 8; j++){
                char cell = str.charAt(j);
                board[i][j] = cell == '.' ? 0 : 1 << 0;
            }
        }
        solve(0);
        System.out.print(possibilities);
    }

    static void solve(int line){
        if(line == 8){
            possibilities++;
            return;
        }

        for(int i = 0; i < 8; i++){
            if(board[line][i] == 0){
                propagate(line, i);

                solve(line + 1);

                undoPropagation(line, i);
                int debug = 0;
            }
        }
    }

    static void propagate(int line, int index){
        board[line][index] |= 1 << 5;

        if(line < 7){
            board[line + 1][index] |= 1 << 1;
            if(index != 0){
                board[line + 1][index - 1] |= 1 << 2;
            }
            if(index != 7){
                board[line + 1][index + 1] |= 1 << 3;
            }

            for(int i = 0; i < 8; i++){
                if((board[line][i] & 1 << 1) == 1 << 1){
                    board[line + 1][i] |= 1 << 1;
                }
                if(i != 0 && (board[line][i] & 1 << 2) == 1 << 2){
                    board[line + 1][i - 1] |= 1 << 2;
                }
                if(i != 7 && (board[line][i] & 1 << 3) == 1 << 3){
                    board[line + 1][i + 1] |= 1 << 3;
                }
            }
        }
    }

    static void undoPropagation(int line, int index){

        if((board[line][index] & 1 << 0) == 1 << 0){
            board[line][index] = 1 << 0;
        }
        else  board[line][index] = 0;

        if(line < 7){
            for(int i = 0; i < 8; i++){
                if((board[line + 1][i] & 1 << 0) == 1 << 0){
                    board[line + 1][i] = 1 << 0;
                }
                else  board[line + 1][i] = 0;
            }
        }
    }
}

