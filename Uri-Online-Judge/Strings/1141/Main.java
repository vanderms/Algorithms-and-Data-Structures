import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        while(true){
            int n = Integer.parseInt(in.readLine());
            if(n == 0){
                break;
            }
            String[] strings = new String[n];
            for(int i = 0; i < n; i++){
                strings[i] = in.readLine();
            }
            Arrays.sort(strings, (x, y) -> Integer.compare(x.length(), y.length()));
            solve(strings);
        }
    }

    public static void solve(String[] strings){
        List<List<Integer>> memo = new ArrayList<>();
        memo.add(null);
        int i = 0;
        int max = 1;
        memo.add(new ArrayList<>());
        while(i < strings.length && strings[i].length() == strings[0].length()){
            List<Integer> first = memo.get(1);
            first.add(i);
            i++;
        }

        while(i < strings.length){
            int value = 1;
            boolean found = false;
            for(int x = memo.size() - 1; x > 0; x--) {
                List<Integer> list = memo.get(x);
                for(int index : list){
                    if(strings[i].contains(strings[index])){
                        found = true;
                        value += x;
                        break;
                    }
                }
                if(found){
                    break;
                }
            }
            List<Integer> toInclude;
            if(value > max){
                max = value;
                toInclude = new ArrayList<>();
                memo.add(toInclude);

            } else{
                toInclude = memo.get(value);
            }
            toInclude.add(i);
            i++;
        }
        System.out.println(max);
    }
}
