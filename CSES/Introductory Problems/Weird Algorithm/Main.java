import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException{
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        long n = Integer.parseInt(in.readLine());
        StringBuilder builder = new StringBuilder();
        builder.ensureCapacity(32 * 1024);

        while(n != 1){
            builder.append(n + " ");
            if(n % 2 == 0){
                n /= 2;
            }
            else{
                n = n * 3 + 1;
            }
        }
        builder.append(n);
        System.out.println(builder.toString());
    }
}
