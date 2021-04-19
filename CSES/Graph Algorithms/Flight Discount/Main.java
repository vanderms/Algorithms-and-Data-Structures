import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static final long INF = Long.MAX_VALUE / 2;

    public static void main(String[] args) throws IOException {

        var input = new InputStreamReader(System.in);
        var in = new BufferedReader(input);
        var tokens = new StringTokenizer(in.readLine());

        int n = Integer.parseInt(tokens.nextToken());
        int m = Integer.parseInt(tokens.nextToken());
        City[] cities = new City[n + 1];

        for(int i = 1; i <= n; i++){
            cities[i] = new City();
        }

        long[] prices = new long[n + 1];
        Arrays.fill(prices, INF);
        prices[1] = 0;

        int[] discounts = new int[n + 1];

        for(int i = 0; i < m; i++){
            tokens = new StringTokenizer(in.readLine());
            int a = Integer.parseInt(tokens.nextToken());
            int b = Integer.parseInt(tokens.nextToken());
            int price = Integer.parseInt(tokens.nextToken());
            cities[a].flights.add(new Flight(b, price, 0));
        }

        PriorityQueue<Flight> queue = new PriorityQueue<>(m);
        queue.add(new Flight(1, 0, 0));

        while(!queue.isEmpty()){
            Flight current = queue.poll();
            List<Flight> flights = cities[current.to].flights;
            for(var flight : flights){

                int to = flight.to;
                int discount = (int)(flight.price - flight.price / 2);
                long price = flight.price;

                if(discount > current.discount){
                    price += (current.price + current.discount - discount);
                } else {
                    price += current.price;
                    discount = current.discount;
                }

                if(to == n){
                    prices[n] = Math.min(prices[n], price);
                } else if(price < prices[to] || price + discount < prices[to] + discounts[to]){
                    prices[to] = price;
                    discounts[to] = discount;
                    queue.add(new Flight(to, price, discount));
                }
            }
        }

        System.out.print(prices[n]);
    }


}


class City {
    protected List<Flight> flights = new LinkedList<>();
}

class Flight implements Comparable<Flight>{

    protected int to;
    protected long price;
    protected int discount;

    public Flight(int to, long price, int discount){
        this.to = to;
        this.price = price;
        this.discount = discount;
    }

    @Override
    public int compareTo(Flight o ){
        if(this.price != o.price){
            return Long.compare(this.price, o.price);
        } else {
            return this.discount - o.discount;
        }
    }
}