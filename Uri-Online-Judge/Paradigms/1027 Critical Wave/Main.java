import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static BufferedReader in;
    static StringBuilder out;
    public static void main(String[] args) throws IOException {

        in = new BufferedReader(new InputStreamReader(System.in));
        out = new StringBuilder(222 * 32);
        String str = null;
        while((str = in.readLine()) != null){
            solve(Integer.parseInt(str));
        }
        System.out.print(out);
    }

    static void solve(int n) throws IOException {

        Pair[] pairs = new Pair[n];
        Map<Integer, Wave> waves = new HashMap<>();

        //get data (n log n)
        for(int i = 0; i < n; i++){
            StringTokenizer line = new StringTokenizer(in.readLine());
            int x = Integer.parseInt(line.nextToken());
            int y = Integer.parseInt(line.nextToken());
            pairs[i] = new Pair(x, y);
            if(!waves.containsKey(y)){
                waves.put(y, new Wave(y));
            }
        }

        //(n log n)
        sort(pairs, (x, y) -> x.x - y.x);

        //(n log n)
        int answer = 0;
        for(int i = 0; i < n; i++){
            Pair pair = pairs[i];

            //check lower wave
            Wave lower = waves.get(pair.y);
            if(lower != null) {
                lower.update(pair);
                answer = Math.max(answer, lower.max());
            }

            Wave higher = waves.get(pair.y + 2);
            if(higher != null) {
                higher.update(pair);
                answer = Math.max(answer, higher.max());
            }
        }

        out.append(answer);
        out.append("\n");
    }

    public static <T> void sort(T[] a, Comparator<T> comp){
        int gap = 0;
        while(gap < a.length) gap = (gap * 3) + 1;
        gap/= 3;

        while (gap > 0) {
            for (int i = gap; i < a.length; i += 1) {

                T temp = a[i];
                int j = i;

                while(j >= gap && comp.compare(a[j - gap],  temp) > 0){
                    a[j] = a[j - gap];
                    j -= gap;
                }
                a[j] = temp;
            }
            gap/= 3;
        }
    }
}


class Pair {
    int x;
    int y;
    Pair(int x, int y){
        this.x = x;
        this.y = y;
    }
}

class Wave {
    int line;
    Pair up = null;
    Pair down = null;
    int upValue = 0;
    int downValue = 0;

    public Wave(int line){
        this.line = line;
    }

    void update(Pair pair){
        if(up == null && this.line == pair.y){
            up = pair;
            upValue++;
        } else if(up != null && (pair.y != up.y && pair.x != up.x)){
            up = pair;
            upValue ++;
        }

        if(down == null && this.line != pair.y){
            down = pair;
            downValue++;
        } else if(down != null && (pair.y != down.y && pair.x != down.x)){
            down = pair;
            downValue++;
        }
    }
    int max(){
        return Math.max(upValue, downValue);
    }
}


