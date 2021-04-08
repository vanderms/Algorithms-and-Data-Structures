import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class solucao {
    public static void main(String[] args) throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int grade = Integer.parseInt(in.readLine());
        int average = Integer.parseInt(in.readLine());
        System.out.print((average * 2) - grade);
    }


}
