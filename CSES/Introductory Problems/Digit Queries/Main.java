import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static StringBuilder out;

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(in.readLine());
        out = new StringBuilder(n * 8);

        for(int i = 0; i < n; i++){
            if(i > 0){ out.append("\n"); }
            long index = Long.parseLong(in.readLine());
            solve(index);
        }

        System.out.print(out);
    }

    static void solve(long index){
        long start = 0;
        long numberOfDigits = 1;
        long previous = 0;
        long current = 10;

        while(!(index < current && index >= previous)){
            previous = current;
            start= start == 0 ? 10 : start * 10;
            numberOfDigits++;
            current+= start * 9 * numberOfDigits;
        }

        long number = start + ((index - previous) / numberOfDigits);
        long digitPosition = ((index - previous) % numberOfDigits) + 1;
        digitPosition = (numberOfDigits - digitPosition) + 1;
        long digit = 0;

        while(digitPosition != 0){
            digit = number % 10;
            number /= 10;
            digitPosition--;
        }
        out.append(digit);
    }





}