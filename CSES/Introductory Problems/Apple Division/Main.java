import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
 
public class Main {
    static long min;
    static long sum;
    static long[] numbers;
 
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(in.readLine());
        StringTokenizer tokens = new StringTokenizer(in.readLine());
        numbers = new long[n];
        sum = 0;
 
        for(int i = 0; i < n; i++){
            int num = Integer.parseInt(tokens.nextToken());
            numbers[i] = num;
            sum+= num;
        }
 
        min = sum;
        solve(0, 0);
        System.out.print(min);
    }
 
    static void solve(int index, long currentSum){
 
        long currentMin = Math.abs(sum - 2 * currentSum);
        min = Math.min(min, currentMin);
 
        //avoid out of bound
        if(index == numbers.length){
             return;
        }
 
        if(sum < 2 * currentSum){
            return;
        }
 
        for(int i = index; i < numbers.length; i++){
            solve(i + 1, currentSum + numbers[index]);
        }
    }
}