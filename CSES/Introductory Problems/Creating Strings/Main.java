import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Main {
    static StringBuilder out;
    static int counter = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String line = in.readLine();
        out = new StringBuilder(32 * 1024 * 1024);

        Map<Character, Integer> map = new TreeMap<>();

        for(int i = 0; i < line.length(); i++){
            char c = line.charAt(i);
            if(map.containsKey(c)){
                map.put(c, map.get(c) + 1);
            }
            else map.put(c, 1);
        }
        solve(0, new char[line.length()], map);
        out.deleteCharAt(out.length() - 1);
        System.out.println(counter);
        System.out.print(out);
    }

    public static void solve(int index, char[] word, Map<Character, Integer> map){
        if(index == word.length) {
            out.append(String.valueOf(word));
            out.append('\n');
            counter++;
            return;
        }

        for(char c : map.keySet()){
            int value = map.get(c);
            if(value > 0){
                word[index] = c;
                map.put(c, value - 1);
                solve(index + 1, word, map);
                map.put(c, value);
                word[index] = '\0';
            }
        }
    }
}

