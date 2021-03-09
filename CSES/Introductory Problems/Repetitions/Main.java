import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException{
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        final String line = in.readLine();
        final long LENGTH = line.length();
        long maxSequenceLength = 0;
        long currentSequenceLength = 0;
        char previous = '\0';

        for(int i = 0; i < LENGTH; i++){
            char current = line.charAt(i);
            if(current == previous){
                currentSequenceLength++;
            }
            else{
                if(currentSequenceLength > maxSequenceLength){
                    maxSequenceLength = currentSequenceLength;
                }
                previous = current;
                currentSequenceLength = 1;
            }
        }

        if(currentSequenceLength > maxSequenceLength){
            maxSequenceLength = currentSequenceLength;
        }
        System.out.print(maxSequenceLength);
    }
}
