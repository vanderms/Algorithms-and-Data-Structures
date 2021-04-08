import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class solucao {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int monicaAge = Integer.parseInt(in.readLine());
        int sonAAge = Integer.parseInt(in.readLine());
        int sonBAge = Integer.parseInt(in.readLine());

        int sonCAge = monicaAge - (sonAAge + sonBAge);
        System.out.print(Math.max(sonCAge, Math.max(sonAAge, sonBAge)));


    }


}
