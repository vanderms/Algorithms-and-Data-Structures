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
        int nCities = Integer.parseInt(tokens.nextToken());
        int nRoads = Integer.parseInt(tokens.nextToken());
        Vertex[] cities = new Vertex[nCities + 1];
        for(int i = 1; i <= nCities; i++){
            cities[i] = new Vertex(i);
        }

        for(int i = 0; i < nRoads; i++){
            tokens = new StringTokenizer(in.readLine());
            int cityA = Integer.parseInt(tokens.nextToken());
            int cityB = Integer.parseInt(tokens.nextToken());
            cities[cityA].connected.add(cities[cityB]);
            cities[cityB].connected.add(cities[cityA]);
        }

        LinkedList<Vertex> headers = new LinkedList<>();
        Stack<Vertex> dfs = new Stack<>();

        for(int i = 1; i <= nCities; i++){
            if(!cities[i].visited){
                headers.add(cities[i]);
                dfs.add(cities[i]);
            }

            while(!dfs.empty()){ //if city is visited then !dfs.empty() == false
                Vertex current = dfs.pop();
                current.visited = true;
                for(var adjacent: current.connected){
                    if(!adjacent.visited){
                        dfs.add(adjacent);
                    }
                }
            }
        } //behold the pyramid of doom!

        StringBuilder answer = new StringBuilder(headers.size() * 64);
        Vertex previous = headers.poll();
        answer.append(headers.size());
        answer.append('\n');

        for(var current: headers){
            answer.append(previous.city);
            answer.append(' ');
            answer.append(current.city);
            answer.append('\n');
            previous = current;
        }
        answer.deleteCharAt(answer.length() - 1);
        System.out.print(answer);
    }
}


class Vertex {
    protected int city;
    protected boolean visited;
    protected List<Vertex> connected;

    public Vertex(int city){
        this.city = city;
        this.visited = false;
        this.connected = new LinkedList<>();
    }
}


