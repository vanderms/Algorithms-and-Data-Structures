import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(final String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        char[] string = in.readLine().toCharArray();
        int [] map = new int[26];


        for (int i = 0; i < string.length; i++) {
            map[string[i] - 'A']++;
        }

        int odd = 0;
        char pivot = '\0';

        for(int i = 0; i < map.length; i++){
            if(map[i] % 2 != 0){
                odd++;
                pivot = (char) ('A' + i);
            }
        }

        int first = 0;
        int last = string.length - 1;

        if (string.length % 2 == odd) {

            if (odd == 1) {
                string[string.length / 2] = pivot;
                map[pivot - 'A']--;
            }

            for (int i = 0; i < map.length; i++) {
                int num = map[i];
                char c = (char) ('A' + i);
                for (int j = 0; j < num; j+=2) {
                    string[first] = c;
                    string[last] = c;
                    first++;
                    last--;
                }
            }

            System.out.print(String.valueOf(string));
        } else {
            System.out.print("NO SOLUTION");
        }

    }
}

