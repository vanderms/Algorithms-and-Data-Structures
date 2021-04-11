import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer tokens = new StringTokenizer(in.readLine());
        int n = Integer.parseInt(tokens.nextToken());
        int r = Integer.parseInt(tokens.nextToken());

        Vertex[] cities = new Vertex[n + 1];
        for(int i = 1; i <= n; i++) {
            cities[i] = new Vertex(i);
        }

        for(int i = 0; i < r; i++){
            tokens = new StringTokenizer(in.readLine());
            int cityA = Integer.parseInt(tokens.nextToken());
            int cityB = Integer.parseInt(tokens.nextToken());
            cities[cityA].adjacents.add(cities[cityB]);
            cities[cityB].adjacents.add(cities[cityA]);
        }

        Vertex res = null;

        for(int i = 1; i <= n; i++){
            if(!cities[i].visited){
                res = search(cities, i);
                if(res != null){
                    break;
                }
            }
        }

        if(res == null){
            System.out.print("IMPOSSIBLE");
            return;
        }

        int start = res.city;
        res = res.previous;

        StringBuilder path = new StringBuilder(n * 32);
        path.append(start);
        path.append(' ');
        int counter = 2;
        while(res.city != start){
            path.append(res.city);
            path.append(' ');
            res = res.previous;
            counter++;
        }
        path.append(start);
        System.out.println(counter);
        System.out.print(path);
    }


    static Vertex search(Vertex[] cities, int idx){

        Stack<Vertex> vertexes = new Stack<>();
        vertexes.add(cities[idx]);

        while(!vertexes.empty()){
            Vertex current = vertexes.pop();
            current.visited = true;

            for(var adjacent : current.adjacents) {
                if(!adjacent.visited) {
                    adjacent.previous = current;
                    vertexes.add(adjacent);
                }
                else if (current.previous != adjacent) {
                    adjacent.previous = current;
                    return adjacent;
                }
            }
        }
        return null;
    }
}


class Vertex {
    protected int city;
    protected boolean visited;
    protected List<Vertex> adjacents;
    protected Vertex previous;
    public Vertex(int city){
        this.city = city;
        this.visited = false;
        this.adjacents = new LinkedList<>();
    }
}

