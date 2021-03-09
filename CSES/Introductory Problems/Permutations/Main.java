import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException{
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        final int n = Integer.parseInt(in.readLine());
        StringBuilder builder = new StringBuilder(1024 * 1024);

        if(n == 1){
            System.out.print(1);
        }
        else if(n == 2 || n == 3){
            System.out.print("NO SOLUTION");
        }
        else{
            for(int i = 2; i <= n; i+=2){
                builder.append(i);
                builder.append(" ");
            }
            for(int i = 1; i <= n; i+=2){
                builder.append(i);
                builder.append(" ");
            }
            System.out.print(builder.toString());
        }
    }
}
