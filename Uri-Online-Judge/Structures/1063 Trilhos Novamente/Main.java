import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;


public class Main {
    static BufferedReader in;
    static StringBuilder out;

    public static void main(String[] args) throws IOException {
        in = new BufferedReader(new InputStreamReader(System.in));
        out = new StringBuilder();

        int n;

        while((n = Integer.parseInt(in.readLine())) != 0){

            Stack<Character> station = new Stack<>();
            Queue<Character> train = new LinkedList<>();
            char[] exit = new char[n];

            StringTokenizer tokens = new StringTokenizer(in.readLine());

            for(int i = 0; i < n; i++){
                train.add(tokens.nextToken().charAt(0));
            }

            tokens = new StringTokenizer(in.readLine());
            for(int i = 0; i < n; i++){
                exit[i] = tokens.nextToken().charAt(0);
            }

            int index = 0;

            while(!train.isEmpty() || !station.empty()){
                if(!train.isEmpty() && train.peek() == exit[index]){
                    out.append("IR");
                    train.poll();
                    index++;
                }
                else if(!station.empty() && station.peek() == exit[index]){
                    out.append("R");
                    station.pop();
                    index++;
                }
                else if(!train.isEmpty()){
                    out.append("I");
                    station.add(train.poll());
                } else{
                    out.append(" Impossible");
                     break;
                }
            }
            out.append("\n");
        }

        System.out.print(out);
    }
}

