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
        long[] distances = new long[n + 1];
        Arrays.fill(distances, INF);
        distances[1] = 0;
        int[] ancestors = new int[n + 1];

        Edge[] edges = new Edge[m];

        for (int i = 0; i < m; i++) {
            tokens = new StringTokenizer(in.readLine());
            int from = Integer.parseInt(tokens.nextToken());
            int to = Integer.parseInt(tokens.nextToken());
            int length = Integer.parseInt(tokens.nextToken());
            edges[i] = new Edge(from, to, length);
        }

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < m; j++) {
                Edge edge = edges[j];
                long sum = distances[edge.from] + edge.length;
                if (sum < distances[edge.to]) {
                    distances[edge.to] = sum;
                    ancestors[edge.to] = edge.from;
                }
            }
        }

        int start = -1;
        for (int j = 0; j < m; j++) {
            Edge edge = edges[j];
            long sum = distances[edge.from] + edge.length;
            if (sum < distances[edge.to]) {
               distances[edge.to] = sum;
               ancestors[edge.to] = edge.from;
               start = edge.from;
               break;
            }
        }

        if(start == -1){
            System.out.print("NO");
            return;
        }

        Set<Integer> set = new HashSet<>();

        int next = start;
        Stack<Integer> sequence = new Stack<>();
        sequence.add(next);
        set.add(next);

        while(true){
            next = ancestors[next];
            sequence.add(next);
            if(set.contains(next)){
                start = next;
                break;
            }
            set.add(next);
        }

        StringBuilder answer = new StringBuilder(sequence.size() * 32);
        answer.append("YES\n");
        answer.append(sequence.pop());
        answer.append(" ");

        while(true){
            next = sequence.pop();
            answer.append(next);
            if(next == start){
                break;
            } else {
                answer.append(" ");
            }
        }
        System.out.print(answer);
    }
}


class Edge {
    protected int from;
    protected int to;
    protected int length;

    public Edge(int from, int to, int length){
        this.from = from;
        this.to = to;
        this.length = length;
    }
}