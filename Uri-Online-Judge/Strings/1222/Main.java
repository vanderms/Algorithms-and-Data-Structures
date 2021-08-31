import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


public class Main {
    static BufferedReader in;
    public static void main(String[] args) throws IOException {

        in = new BufferedReader(new InputStreamReader(System.in));

        while(true){
            String line = in.readLine();
            if(line == null){
                break;
            }
            StringTokenizer tokens = new StringTokenizer(line);
            tokens.nextToken();
            final int MAX_LINES_PER_PAGE = Integer.parseInt(tokens.nextToken());
            final int MAX_CHARS_PER_LINE = Integer.parseInt(tokens.nextToken());
            solve(MAX_CHARS_PER_LINE, MAX_LINES_PER_PAGE);
        }
    }


    public static void solve(final int MAX_CHARS_PER_LINE, final int MAX_LINES_PER_PAGE) throws IOException{
        char c = 0;
        int wordLength = 0;
        int chars = 0;
        int lines = 1;
        int pages = 1;

        while(true){
            c = (char) in.read();
            if(c != ' ' && c != '\n'){
                wordLength++;
            }
            else{
                if(wordLength + chars > MAX_CHARS_PER_LINE){
                    lines++;
                    chars = wordLength + 1;
                    if(lines > MAX_LINES_PER_PAGE){
                        lines = 1;
                        pages++;
                    }
                } else {
                    chars+= wordLength + 1;
                }

                wordLength = 0;
            }
            if(c == '\n'){
                System.out.println(pages);
                return;
            }
        }
    }
}