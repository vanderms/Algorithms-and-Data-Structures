import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
    static BufferedReader in;
    static StringBuilder out;

    public static void main(String[] args) throws IOException {
        in = new BufferedReader(new InputStreamReader(System.in));
        out = new StringBuilder();

        String line;

        while((line = in.readLine()) != null){

            int open = 0;

            for(int i = 0; i < line.length(); i++){
                if(line.charAt(i) == '('){
                    open+= 1;
                }
                else if(line.charAt(i) == ')'){
                    if(open <= 0){
                        open = -1;
                        break;
                    }
                    open--;
                }
            }
            //System.out.println(open == 0 ? "correct" : "incorrect");
            out.append(open == 0 ? "correct\n" : "incorrect\n");
        }

        System.out.print(out);
    }
}

