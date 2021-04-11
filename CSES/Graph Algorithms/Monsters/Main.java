import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int n;
    static int m;

    public static void main(String[] args) throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer tokens = new StringTokenizer(in.readLine());
        n = Integer.parseInt(tokens.nextToken());
        m = Integer.parseInt(tokens.nextToken());

        int[][] monstersGrid = new int[n + 2][m + 2];
        int[][] heroGrid = new int[n + 2][m + 2];

        List<Node> monsters = new LinkedList<>();
        List<Node> hero = new LinkedList<>();
        Node start = null;

        for(int r = 1; r <= n; r++){
            String line = in.readLine();
            for(int c = 1; c <= m; c++){
                char cell = line.charAt(c - 1);
                if(cell == '.'){
                    monstersGrid[r][c] = -1;
                    heroGrid[r][c] = -1;
                } else if(cell == 'M'){
                    monsters.add(new Node(r, c));
                } else if(cell == 'A'){
                    start = new Node(r, c);
                    hero.add(start);
                }
            }
        }

        if(start.r == 1 || start.r == n || start.c == 1 || start.c == m){
            System.out.print("YES\n0");
            return;
        }

        bfsMonster(monstersGrid, monsters);
        Node exit = bfsHero(heroGrid, monstersGrid, hero);

        if(exit == null){
            System.out.print("NO");
            return;
        }

        final int total = exit.r;
        System.out.println("YES");
        System.out.println(total);
        StringBuilder answerPath = new StringBuilder(total * 4);
        char[] path = new char[total];
        int idx = total - 1;

        while(idx >= 0){
            path[idx] = exit.move;
            exit = exit.previous;
            idx--;
        }
        answerPath.append(path);
        System.out.print(answerPath);
    }


    static Node bfsHero(int[][] grid, int[][] mGrid, List<Node>hero){

        while(hero.size() > 0){
            List<Node> temp = new LinkedList<>();
            for(var current : hero){

                int r = current.r;
                int c = current.c;
                int nDis = grid[r][c] + 1;

                if (grid[r + 1][c] < 0 && (mGrid[r + 1][c] == -1 || mGrid[r + 1][c] > nDis)){
                    grid[r + 1][c] = grid[r][c] + 1;
                    Node next = new Node(current, 'D', r + 1, c);
                    if(r + 1 == 1 || r + 1 == n){
                        next.r = nDis;
                        return next;
                    } else {
                        temp.add(next);
                    }
                }
                if (grid[r - 1][c] < 0 && (mGrid[r - 1][c] == -1 || mGrid[r - 1][c] > nDis)) {
                    grid[r - 1][c] = grid[r][c] + 1;
                    Node next = new Node(current, 'U', r - 1, c);
                    if(r - 1 == 1 || r - 1 == n){
                        next.r = nDis;
                        return next;
                    } else {
                        temp.add(next);
                    }
                }
                if (grid[r][c + 1] < 0 && (mGrid[r][c + 1] == -1 || mGrid[r][c + 1] > nDis)) {
                    grid[r][c + 1] = grid[r][c] + 1;
                    Node next = new Node(current, 'R', r, c + 1);
                    if(c + 1 == 1 || c + 1 == m){
                        next.r = nDis;
                        return next;
                    } else {
                        temp.add(next);
                    }
                }
                if (grid[r][c - 1] < 0 && (mGrid[r][c - 1] == -1 || mGrid[r][c - 1] > nDis)) {
                    grid[r][c - 1] = grid[r][c] + 1;
                    Node next = new Node(current, 'L', r, c - 1);
                    if(c - 1 == 1 || c - 1 == m){
                        next.r = nDis;
                        return next;
                    } else {
                        temp.add(next);
                    }
                }
            }
            hero = temp;
        }

        return null;
    }


    static void bfsMonster(int[][] grid, List<Node> monsters){

        while(monsters.size() > 0){
            List<Node> temp = new LinkedList<>();
            for(var current : monsters){

                int r = current.r;
                int c = current.c;

                if (grid[r + 1][c] < 0) {
                    grid[r + 1][c] = grid[r][c] + 1;
                    temp.add(new Node(r + 1, c));
                }
                if (grid[r - 1][c] < 0) {
                    grid[r - 1][c] = grid[r][c] + 1;
                    temp.add(new Node(r - 1, c));
                }
                if (grid[r][c + 1] < 0) {
                    grid[r][c + 1] = grid[r][c] + 1;
                    temp.add(new Node(r, c + 1));
                }
                if (grid[r][c - 1] < 0) {
                    grid[r][c - 1] = grid[r][c] + 1;
                    temp.add(new Node(r, c - 1));
                }
            }
            monsters = temp;
        }
    }
}

class Node {

    protected int r;
    protected int c;
    protected char move;
    protected Node previous;

    public Node(int r, int c){
        this.r = r;
        this.c = c;
    }

    public Node(Node previous, char move, int r, int c){
        this.r = r;
        this.c = c;
        this.move = move;
        this.previous = previous;
    }
}
