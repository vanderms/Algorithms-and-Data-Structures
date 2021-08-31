import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class Main {
    static BufferedReader in;
    static StringBuilder out;
    public static void main(String[] args) throws IOException {

        in = new BufferedReader(new InputStreamReader(System.in));
        out = new StringBuilder();

        int n = Integer.parseInt(in.readLine());
        for(int i = 0; i < n; i++){
            solve();
            out.append('\n');
        }

        System.out.print(out);

    }

    public static void solve() throws  IOException{
        StringTokenizer tokens = new StringTokenizer(in.readLine());
        char[] first = tokens.nextToken().toCharArray();
        char[] second = tokens.nextToken().toCharArray();
        char[] answer = new char[first.length + second.length];
        int f = 0;
        int s = 0;
        int i = 0;

        while(f < first.length && s < second.length){
            answer[i] = first[f];
            answer[i + 1] = second[s];
            f++;
            s++;
            i+= 2;
        }

        while(s < second.length){
            answer[i] = second[s];
            i++;
            s++;
        }

        while(f < first.length){
            answer[i] = first[f];
            i++;
            f++;
        }

        out.append(answer);
    }
}