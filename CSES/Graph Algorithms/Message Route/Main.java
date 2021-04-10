import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer tokens = new StringTokenizer(in.readLine());
        int nComputers = Integer.parseInt(tokens.nextToken());
        int nConnections = Integer.parseInt(tokens.nextToken());

        Vertex[] computers = new Vertex[nComputers + 1];
        for(int i = 1; i <= nComputers; i++){
            computers[i] = new Vertex(i);
        }

        for(int i = 1; i <= nConnections; i++){
            tokens = new StringTokenizer(in.readLine());
            int computerA = Integer.parseInt(tokens.nextToken());
            int computerB = Integer.parseInt(tokens.nextToken());
            computers[computerA].connections.add(computers[computerB]);
            computers[computerB].connections.add(computers[computerA]);
        }

        int minConnections = bfs(computers[1], nComputers);

        if(minConnections == -1){
            System.out.print("IMPOSSIBLE");
            return;
        }

        StringBuilder answer = new StringBuilder(minConnections * 32);
        answer.append(minConnections);
        answer.append('\n');

        int[] path = new int[minConnections];
        Vertex node = computers[nComputers];
        for(int i = minConnections - 1; i >= 0; i--){
            path[i] = node.computer;
            node = node.previous;
        }

        for(int i = 0; i < path.length; i++){
            answer.append(path[i]);
            answer.append(" ");
        }

        answer.deleteCharAt(answer.length() - 1);
        System.out.print(answer);
    }

    public static int bfs(Vertex start, int end){

        int distance = 1;
        List<Vertex> vertexes = new LinkedList<>();
        start.visited = true;
        vertexes.add(start);

        while(!vertexes.isEmpty()){
            List<Vertex> temp = new LinkedList<>();
            distance+= 1;
            for(var vertex: vertexes){
                for(var connection: vertex.connections){
                    if(connection.visited){
                        continue;
                    }
                    connection.previous = vertex;
                    connection.visited = true;
                    if(connection.computer == end){
                        return distance;
                    }
                    temp.add(connection);
                }
            }
            vertexes = temp;
        }

        return -1;
    }
}


class Vertex {
    protected int computer;
    protected boolean visited;
    protected List<Vertex> connections;
    protected Vertex previous;

    public Vertex(int computer){
        this.computer = computer;
        this.visited = false;
        this.connections = new LinkedList<>();
        this.previous = null;
    }
}


