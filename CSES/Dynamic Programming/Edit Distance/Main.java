import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        char[] first = in.readLine().toCharArray();
        char[] second = in.readLine().toCharArray();

        int[] memo = new int[first.length + 1];

        for(int i = 0; i < memo.length; i++) {
            memo[i] = i;
        }

        for(int i = 1; i <= second.length; i++){
            int[] temp = new int[memo.length];
            temp[0] = i;
            char current = second[i - 1];
            for(int j = 1; j < temp.length; j++){
                int rCost = current == first[j - 1] ? 0 : 1;
                temp[j] = Math.min(temp[j - 1] + 1, Math.min(memo[j - 1] + rCost, memo[j] + 1));
            }
            memo = temp;
        }

        System.out.print(memo[memo.length - 1]);
    }
}