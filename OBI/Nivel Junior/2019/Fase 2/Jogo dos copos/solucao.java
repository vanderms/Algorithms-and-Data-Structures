import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class solucao {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(in.readLine());
        int[] glasses = new int[3];
        int pos = in.readLine().charAt(0) - 'A';
        glasses[pos] = 1;

        for(int i = 0; i < n; i++){
            int move = Integer.parseInt(in.readLine());
            swap(glasses, move);
        }

        if(glasses[0] == 1){
            System.out.print('A');
        } else if(glasses[1] == 1){
            System.out.print('B');
        } else{
            System.out.print('C');
        }
    }

    static void swap(int[] glasses, int move){
        int a = 0, b = 1;
        if(move == 2){
            a = 1;
            b = 2;
        } else if(move == 3){
            b = 2;
        }

        int temp = glasses[a];
        glasses[a] = glasses[b];
        glasses[b] = temp;
    }
}
