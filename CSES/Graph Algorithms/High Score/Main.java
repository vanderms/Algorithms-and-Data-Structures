import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static final long INF = Long.MAX_VALUE / 2;
    static int n;

    public static void main(String[] args) throws IOException {

        var input = new InputStreamReader(System.in);
        var in = new BufferedReader(input);
        var tokens = new StringTokenizer(in.readLine());

        n = Integer.parseInt(tokens.nextToken());
        final int m = Integer.parseInt(tokens.nextToken());

        //scores
        long[] scores = new long[n + 1];
        Arrays.fill(scores, -INF);
        scores[1] = 0;

        //tunnes
        int[][] tunnels = new int[m][3];

        //get tunnels
        for(int i = 0; i < m; i++){
            tokens = new StringTokenizer(in.readLine());
            tunnels[i][0] = Integer.parseInt(tokens.nextToken());
            tunnels[i][1] = Integer.parseInt(tokens.nextToken());
            tunnels[i][2] = Integer.parseInt(tokens.nextToken());
        }

        Set<Integer> cicles = new HashSet<>();

        //Bellman-Ford algorithm
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < m; j++) {
                int from = tunnels[j][0];
                long pointsFrom = scores[from];
                if(pointsFrom == -INF){ continue; }
                int to = tunnels[j][1];
                int points = tunnels[j][2];
                long sum = pointsFrom + points;
                if(sum > scores[to]){
                    scores[to] = scores[from] + points;
                    if(i == n){
                        cicles.add(to);
                    }
                }
            }
        }

        if(cicles.isEmpty()){
            System.out.print(scores[n]);
            return;
        }

        //now the hard way: some depth first searchs
        Node[] nodes = new Node[n + 1];
        for(int i = 1; i <= n; i++){ nodes[i] = new Node(); }
        for(int i = 0; i < m; i++){
            nodes[tunnels[i][0]].adjacents.add(tunnels[i][1]);
        }

       boolean reachesN = false;

        while(!cicles.isEmpty() && !reachesN){

            Stack<Integer> visited = null;

            for(var node : cicles){
                visited = dfs(nodes, node, n);
                break;
            }

            while(!visited.isEmpty()){
                int node = visited.pop();
                if(node == n){
                    reachesN = true;
                    break;
                } else {
                    cicles.remove(node);
                }
            }
        }

        if(reachesN){
            System.out.print(-1);
        } else {
            System.out.print(scores[n]);
        }
    }

    static Stack<Integer> dfs(Node[] nodes, int start, int target){

        boolean[] visited = new boolean[n + 1];
        Stack<Integer> visitedNodes = new Stack<>();
        Stack<Integer> stack = new Stack<>();
        stack.add(start);
        while (!stack.empty()) {
            int current = stack.pop();
            for (var adjacent : nodes[current].adjacents) {
                if (visited[adjacent]){ continue; }
                visited[adjacent] = true;
                visitedNodes.add(adjacent);
                if(adjacent == target){
                    return visitedNodes;
                }
                stack.add(adjacent);
            }
        }
        return visitedNodes;
    }
}


class Node {
    protected List<Integer> adjacents = new LinkedList<>();
}