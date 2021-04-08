import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class solucao {
    static final int J = 0;
    static final int P = 1;
    static final int V = 2;
    static final int E = 3;
    static final int D = 4;
    static int[] data = new int[5];

    public static void main(String[] args) throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer tokens = new StringTokenizer(in.readLine());
        for(int i = 0; i < data.length; i++){
            data[i] = Integer.parseInt(tokens.nextToken());
        }

        //special case 1: impossible
        if(data[J] == -1 && data[D] == -1){
            return;
        }

        //special case 2: first find value for V then solve in ordinary cases
        if(data[V] == -1 && data[E] == -1) {
            data[V] = (data[P] - data[J] + data[D]) / 2;
        }

        //ordinary cases
        boolean solved = false;

        while(!solved){
            solved = true;
            if(data[J] == -1 && !solveJ()){
               solved = false;
            }
            if(data[P] == -1 && !solveP()){
              solved = false;
            }
            if(data[V] == -1 && !solveV()){
              solved = false;
            }
            if(data[E] == -1 && !solveE()){
                solved = false;
            }
            if(data[D] == -1 && !solveD()){
                solved = false;
            }
        }

        StringBuilder answer = new StringBuilder();
        for(int i = 0; i < data.length; i++){
            answer.append(data[i]);
            answer.append(" ");
        }
        answer.deleteCharAt(answer.length() - 1);

        System.out.print(answer);
    }

    static boolean solveJ(){
        if(data[V] != -1 && data[E] != -1 && data[D] != -1){
            data[J] = data[V] + data[E] + data[D];
            return true;
        }
        return false;
    }

    static boolean solveP(){
        if(data[V] != -1 && data[E] != -1){
            data[P] = 3 * data[V] + data[E];
            return true;
        }
        return false;
    }

    static boolean solveV(){
        if(data[J] != -1 && data[E] != -1 && data[D] != -1){
            data[V] = data[J] - (data[E] + data[D]);
            return true;
        } else if(data[P] != -1 && data[E] != -1){
            data[V] = (data[P] - data[E]) / 3;
            return true;
        }
        return false;
    }

    static boolean solveE(){
        if(data[J] != -1 && data[V] != -1 && data[D] != -1){
            data[E] = data[J] - (data[V] + data[D]);
            return true;
        } else if(data[P] != -1 && data[V] != -1){
            data[E] = data[P] - (data[V] * 3);
            return true;
        }
        return false;
    }

    static boolean solveD(){
        if(data[J] != -1 && data[V] != -1 && data[E] != -1) {
            data[D] = data[J] - (data[V] + data[E]);
            return true;
        }
       return false;
    }
}
