import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(final String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(in.readLine());
        StringBuilder out = new StringBuilder();
        char[] code = new char[n];

        for(int i = 0; i < n; i++){
            code[i] = '0';
        }

        out.append(String.valueOf(code));

        for(int i = 1; i < (1 << n); i++){
            out.append("\n");
            int num = i ^ (i - 1);
            for(int j = 0; j < n; j++){
                int o = (num >> (n - 1 - j));
                if(o == 1){
                    code[j] = code[j] == '0' ? '1' : '0';
                    break;
                }
            }
            out.append(String.valueOf(code));
        }
        System.out.print(out);
    }
}

