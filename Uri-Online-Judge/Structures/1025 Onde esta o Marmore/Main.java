import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;


public class Main {
    static BufferedReader in;
    static StringBuilder out;

    public static void main(String[] args) throws IOException {
        in = new BufferedReader(new InputStreamReader(System.in));
        out = new StringBuilder();

        int counter = 1;

        while(true){

            StringTokenizer tokens = new StringTokenizer(in.readLine());
            int n = Integer.parseInt(tokens.nextToken());
            int q = Integer.parseInt(tokens.nextToken());
            if(n == 0 && q == 0){
                break;
            }
            out.append("CASE# ");
            out.append(counter);
            out.append(":\n");
            counter++;

            int[] numbers = new int[n];

            for(int i = 0; i < n; i++){
                numbers[i] = Integer.parseInt(in.readLine());
            }
            Arrays.sort(numbers);

            for(int i = 0; i < q; i++){
                int query = Integer.parseInt(in.readLine());
                Integer answer = search(numbers, query, 0, numbers.length);
                out.append(query);
                if(answer == null){
                    out.append(" not found\n");
                }
                else{
                    out.append(" found at ");
                    out.append(answer);
                    out.append("\n");
                }
            }
        }
        System.out.print(out);

    }


    public static Integer search(int[] numbers, int query, int start, int end){
        if(start >= end){
            return null;
        }
        int index = (start + end) / 2;
        if(numbers[index] == query && (index == 0 || numbers[index - 1] != query)){
            return index + 1;
        }
        if(numbers[index] > query || (index > 0 && numbers[index - 1] == query)){
            return search(numbers, query, start, index);
        }
        return search(numbers, query, index + 1, end);
    }
}

