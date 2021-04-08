import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class solucao {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(in.readLine());

        int current = Integer.parseInt(in.readLine());
        int answer = 1;

        for(int i = 1; i < n; i++) {
            int num = Integer.parseInt(in.readLine());
            if(num != current){
                answer++;
                current = num;
            }
        }

        System.out.print(answer);
    }


}
