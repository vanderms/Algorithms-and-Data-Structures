import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


public class solucao {

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(in.readLine());
        List<Lateral> laterals = new ArrayList<>(2 * n);

        for(int i = 0; i < n; i++){
            StringTokenizer tokens = new StringTokenizer(in.readLine());
            int x1 = Integer.parseInt(tokens.nextToken());
            int y2 = Integer.parseInt(tokens.nextToken());
            int x2 = Integer.parseInt(tokens.nextToken());
            int y1 = Integer.parseInt(tokens.nextToken());
            laterals.add(new Lateral(x1, y1, y2, Side.LEFT));
            laterals.add(new Lateral(x2, y1, y2, Side.RIGHT));
        }

        int answer = 0;
        Collections.sort(laterals);

        TreeSet<Integer> tree = new TreeSet<>();

        for(int i = 0; i < 2 * n; i++){
            Lateral lateral = laterals.get(i);
            if(lateral.side == Side.LEFT){
                tree.add(lateral.y1);
                removeRange(tree, lateral.y1, lateral.y2);
            }
            else{
                Integer higher = tree.higher(lateral.y1);
                if(higher == null || higher > lateral.y2){
                    answer++;
                }
            }
        }

        System.out.print(answer);
    }

    static void removeRange(TreeSet<Integer> tree, int next, int end){
        Integer higher = tree.higher(next);
        if(higher == null || higher > end){
            return;
        }
        tree.remove(higher);
        removeRange(tree, higher, end);
    }
}


enum Side{ RIGHT, LEFT }

class Lateral implements Comparable<Lateral>{
    int x;
    int y1;
    int y2;
    Side side;

    public Lateral(int x, int y1, int y2, Side side){
        this.x = x;
        this.y1 = y1;
        this.y2 = y2;
        this.side = side;
    }

    @Override
    public int compareTo(Lateral o) {
        return Integer.compare(x, o.x);
    }
}

