import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException{
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        final int n = Integer.parseInt(in.readLine());
        StringTokenizer st = new StringTokenizer(in.readLine());
        int previous = Integer.parseInt(st.nextToken());
        long res = 0;

        for(int i = 1; i < n; i++){
            int num = Integer.parseInt(st.nextToken());
            if(num < previous){
                int moves = previous - num;
                res+= moves;
            }
            else {
                previous = num;
            }
        }
        System.out.print(res);
    }
}
