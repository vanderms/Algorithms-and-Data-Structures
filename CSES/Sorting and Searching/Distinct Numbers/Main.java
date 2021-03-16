import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


public class Main {

    // I tested hashset, treeset and sorting.
    // For my surprise, the sorting solution (n logn) was faster than the hashset solution O(n)
    // (The larger n in CSES is 200000, then max logn == 17-18).
    // Even so, in a normal situation I would prefer hashset since the intent is more clear.

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(in.readLine());
        StringTokenizer tokens = new StringTokenizer(in.readLine());
        int[] numbers = new int[n];

        for(int i = 0; i < n; i++){
            numbers[i] = Integer.parseInt(tokens.nextToken());
        }

        Arrays.sort(numbers);
        int counter = 1;
        int previous = numbers[0];

        for(int i = 1; i < numbers.length; i++){
            if(numbers[i] != previous){
                previous = numbers[i];
                counter++;
            }
        }

        System.out.print(counter);
    }
}