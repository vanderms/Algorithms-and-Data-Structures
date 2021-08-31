import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
    static BufferedReader in;
    static StringBuilder out;
    public static void main(String[] args) throws IOException {

        in = new BufferedReader(new InputStreamReader(System.in));
        out = new StringBuilder();
        int i;
        boolean italicOpen = true;
        boolean boldOpen = true;

        while((i = in.read()) != -1){
            char c = (char) i;
            if(c == '_'){
                out.append(italicOpen ? "<i>" : "</i>");
                italicOpen = !italicOpen;
            }
            else if(c == '*'){
                out.append(boldOpen ? "<b>" : "</b>");
                boldOpen = !boldOpen;
            }
            else{
                out.append(c);
            }
        }

        System.out.print(out);
    }

}