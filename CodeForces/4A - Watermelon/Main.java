import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int a = Integer.parseInt(in.readLine());
        if((a & 1) == 0 && a > 2){
            System.out.print("YES");
        } else {
            System.out.print("NO");
        }
    }
}

