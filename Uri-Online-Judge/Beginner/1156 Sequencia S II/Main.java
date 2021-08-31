import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
    static BufferedReader in;
    static StringBuilder out;

    public static void main(String[] args) throws IOException {

        double answer =  0.0;
        long denominator = 1;

        for(long i = 1; i <= 39; i+= 2){
            answer += (i / (double) (denominator));
            denominator *= 2;
        }
        System.out.printf("%.2f\n", answer);

    }
}

