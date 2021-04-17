import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {

    static int n;
    static int m;

    public static void main(String[] args) throws IOException {

        var input = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(input);
        StringTokenizer tokens = new StringTokenizer(in.readLine());

        n = Integer.parseInt(tokens.nextToken());
        m = Integer.parseInt(tokens.nextToken());
        City[] cities = new City[n + 1];

        for(int i = 1; i <= n; i++){
            cities[i] = new City();
        }

        for(int i = 0; i < m; i++){
            tokens = new StringTokenizer(in.readLine());
            int from = Integer.parseInt(tokens.nextToken());
            int to = Integer.parseInt(tokens.nextToken());
            int length = Integer.parseInt(tokens.nextToken());
            cities[from].flights.add(new Flight(to, length));
        }

        dijkstra(cities);

        StringBuilder answer = new StringBuilder(n * 32);

        for(int i = 1; i <= n; i++){
            answer.append(cities[i].distance);
            answer.append(" ");
        }
        answer.deleteCharAt(answer.length() - 1);
        System.out.print(answer);

    }

    public static void dijkstra(City[] cities){
        Queue<Flight> queue = new PriorityQueue<>();
        cities[1].distance = 0;
        queue.add(new Flight(1, 0));

        while(!queue.isEmpty()){
            Flight currentFlight = queue.poll();
            City current = cities[currentFlight.to];
            while(!current.flights.empty()) {
                var flight = current.flights.pop();
                City next = cities[flight.to];
                flight.distance += current.distance;
                if(flight.distance < next.distance){
                    next.distance = flight.distance;
                    queue.add(flight);
                }
            }
        }
    }
}

class City{
    protected long distance;
    protected Stack<Flight> flights;

    public City(){
        this.distance = Long.MAX_VALUE;
        this.flights = new Stack<>();
    }
}

class Flight implements Comparable<Flight> {
    protected int to;
    protected long distance;
    public Flight(int to, long distance){
        this.to = to;
        this.distance = distance;
    }
    @Override
    public int compareTo(Flight o){
        return Long.compare(this.distance, o.distance);
    }
}